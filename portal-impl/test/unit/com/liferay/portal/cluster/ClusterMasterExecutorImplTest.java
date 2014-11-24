/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.cluster;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.AddressSerializerUtil;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.cluster.ClusterMessageType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.ClusterResponseCallback;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.impl.LockImpl;
import com.liferay.portal.service.LockLocalServiceUtil;
import com.liferay.portal.service.impl.LockLocalServiceImpl;
import com.liferay.portal.uuid.PortalUUIDImpl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.net.InetAddress;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */

public class ClusterMasterExecutorImplTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			LockLocalServiceUtil.class, "_service", new MockLockLocalService());

		_TEST_TIME = System.currentTimeMillis();

		_TEST_ADDRESS =  new AddressImpl(new MockAddress(_TEST_TIME));
	}

	@After
	public void tearDown() {
		MockLockLocalService.resetLock();
	}

	@Test
	public void testClusterMasterTokenClusterEventListenerSuccess() {
		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterMasterExecutorImpl.class.getName(), Level.SEVERE);

		try {
			List<LogRecord> logRecords = captureHandler.getLogRecords();

			ClusterMasterExecutorImpl clusterMasterExecutorImpl =
				new ClusterMasterExecutorImpl();

			ClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

			clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);
			clusterMasterExecutorImpl.initialize();

			List<ClusterEventListener> clusterEventListeners =
				mockClusterExecutor.getClusterEventListeners();

			ClusterEventListener clusterEventListener =
				clusterEventListeners.get(0);

			clusterEventListener.processClusterEvent(null);

			Assert.assertTrue(logRecords.isEmpty());
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testClusterMasterTokenTransitionListeners() {
		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener1 =
				new MockClusterMasterTokenTransitionListener();

		clusterMasterExecutorImpl.registerClusterMasterTokenTransitionListener(
			clusterMasterTokenTransitionListener1);

		Set<ClusterMasterTokenTransitionListener>
			clusterMasterTokenTransitionListeners =
				ReflectionTestUtil.getFieldValue(
					clusterMasterExecutorImpl,
					"_clusterMasterTokenTransitionListeners");

		Assert.assertEquals(1, clusterMasterTokenTransitionListeners.size());

		Set<ClusterMasterTokenTransitionListener>
			clusterMasterTokenTransitionListenerSet =
				new HashSet<ClusterMasterTokenTransitionListener>();

		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener2 =
				new MockClusterMasterTokenTransitionListener();

		clusterMasterTokenTransitionListenerSet.add(
			clusterMasterTokenTransitionListener1);
		clusterMasterTokenTransitionListenerSet.add(
			clusterMasterTokenTransitionListener2);

		clusterMasterExecutorImpl.setClusterMasterTokenTransitionListeners(
			clusterMasterTokenTransitionListenerSet);

		Assert.assertEquals(2, clusterMasterTokenTransitionListeners.size());

		clusterMasterExecutorImpl.
			unregisterClusterMasterTokenTransitionListener(
				clusterMasterTokenTransitionListener2);

		Assert.assertEquals(1, clusterMasterTokenTransitionListeners.size());
	}

	@Test
	public void testDestroyClusterLinkDisabled() throws Exception {
		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		ClusterExecutor mockClusterExecutor = new MockClusterExecutor(false);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);
		clusterMasterExecutorImpl.initialize();

		Assert.assertFalse(clusterMasterExecutorImpl.isEnabled());

		clusterMasterExecutorImpl.destroy();
	}

	@Test
	public void testDestroyClusterLinkEnabled() throws Exception {
		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		ClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);
		clusterMasterExecutorImpl.initialize();

		Assert.assertTrue(clusterMasterExecutorImpl.isEnabled());

		clusterMasterExecutorImpl.destroy();
	}

	@Test
	public void testDestroyLogWarning() throws Exception {
		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterMasterExecutorImpl.class.getName(), Level.WARNING);

		try {
			List<LogRecord> logRecords = captureHandler.getLogRecords();

			ClusterMasterExecutorImpl clusterMasterExecutorImpl =
				new ClusterMasterExecutorImpl();

			ClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

			clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);
			clusterMasterExecutorImpl.initialize();

			Assert.assertTrue(clusterMasterExecutorImpl.isEnabled());

			MockLockLocalService.setUnlockError(true);

			clusterMasterExecutorImpl.destroy();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to destroy the cluster master executor",
				logRecord.getMessage());

			logRecords = captureHandler.resetLogLevel(Level.OFF);

			clusterMasterExecutorImpl.destroy();

			Assert.assertTrue(logRecords.isEmpty());
		}
		finally {
			captureHandler.close();

			MockLockLocalService.setUnlockError(false);
		}
	}

	@Test
	public void testExecuteOnMasterDisabled() throws Exception {
		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterMasterExecutorImpl.class.getName(), Level.WARNING);

		try {
			List<LogRecord> logRecords = captureHandler.getLogRecords();

			ClusterMasterExecutorImpl clusterMasterExecutorImpl =
				new ClusterMasterExecutorImpl();

			ClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

			clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

			Assert.assertFalse(clusterMasterExecutorImpl.isEnabled());

			String timeString = String.valueOf(_TEST_TIME);

			MethodHandler methodHandler = new MethodHandler(
				testMethodMethodKey, timeString);

			NoticeableFuture<String> noticeableFuture =
				clusterMasterExecutorImpl.executeOnMaster(methodHandler);

			Assert.assertSame(timeString, noticeableFuture.get());
			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Executing on the local node because the cluster master " +
					"executor is disabled",
				logRecord.getMessage());

			logRecords = captureHandler.resetLogLevel(Level.OFF);

			noticeableFuture = clusterMasterExecutorImpl.executeOnMaster(
				methodHandler);

			Assert.assertSame(timeString, noticeableFuture.get());
			Assert.assertTrue(logRecords.isEmpty());
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testExecuteOnMasterEnabled() throws Exception {
		PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();
		portalUUIDUtil.setPortalUUID(new PortalUUIDImpl());

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		ClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);
		clusterMasterExecutorImpl.initialize();

		Assert.assertTrue(clusterMasterExecutorImpl.isEnabled());

		String timeString = String.valueOf(_TEST_TIME);

		MethodHandler methodHandler = new MethodHandler(
			testMethodMethodKey, timeString);

		NoticeableFuture<String> noticeableFuture =
			clusterMasterExecutorImpl.executeOnMaster(methodHandler);

		Assert.assertSame(timeString, noticeableFuture.get());
	}

	@Test
	public void testExecuteOnMasterFailsOnLocal() throws Exception {
		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		ClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

		Assert.assertFalse(clusterMasterExecutorImpl.isEnabled());

		MethodHandler methodHandler = new MethodHandler(
			testMethodMethodKey, _TEST_TIME);

		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterMasterExecutorImpl.class.getName(), Level.WARNING);

		try {
			clusterMasterExecutorImpl.executeOnMaster(methodHandler);

			Assert.fail();
		}

		catch (SystemException e) {
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testExecuteOnMasterFailsOnMaster() throws Exception {
		PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();
		portalUUIDUtil.setPortalUUID(new PortalUUIDImpl());

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		ClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);
		clusterMasterExecutorImpl.initialize();

		String timeString = String.valueOf(_TEST_TIME);

		MethodHandler methodHandler = new MethodHandler(
			testMethodMethodKey, timeString);

		try {
			MockClusterExecutor.setBreak(true);

			clusterMasterExecutorImpl.executeOnMaster(methodHandler);

			Assert.fail();
		}
		catch (SystemException e) {
			String message = e.getMessage();

			Assert.assertEquals(
				"Unable to execute on master " + _TEST_ADDRESS.getDescription(),
				message);
		}
		finally {
			MockClusterExecutor.setBreak(false);
		}
	}

	@Test
	public void testGetMasterAddressStringOtherOwner() {
		CaptureHandler captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			ClusterMasterExecutorImpl.class.getName(), Level.INFO);

		try {
			List<LogRecord> logRecords = captureHandler.getLogRecords();

			ClusterMasterExecutorImpl clusterMasterExecutorImpl =
				new ClusterMasterExecutorImpl();

			ClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

			clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);
			clusterMasterExecutorImpl.initialize();

			// Set a different lock owner

			long otherTime = _TEST_TIME + 1;

			Address otherAddress =  new AddressImpl(new MockAddress(otherTime));

			String otherOwner = AddressSerializerUtil.serialize(otherAddress);

			MockLockLocalService.setLock(otherOwner);

			String owner = clusterMasterExecutorImpl.getMasterAddressString();

			_TEST_OWNER = AddressSerializerUtil.serialize(_TEST_ADDRESS);

			Assert.assertEquals(_TEST_OWNER, owner);
			Assert.assertEquals(2, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			String message = logRecord.getMessage();

			Assert.assertEquals(
				"Lock currently held by " + otherOwner, message);

			logRecord = logRecords.get(1);

			message = logRecord.getMessage();

			Assert.assertEquals(
				"Reattempting to acquire the cluster master lock", message);

			logRecords = captureHandler.resetLogLevel(Level.INFO);

			// No lock owner

			MockLockLocalService.setLock(null);

			owner = clusterMasterExecutorImpl.getMasterAddressString();

			Assert.assertEquals(_TEST_OWNER, owner);
			Assert.assertEquals(2, logRecords.size());

			logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to acquire the cluster master lock",
				logRecord.getMessage());

			logRecords = captureHandler.resetLogLevel(Level.OFF);

			MockLockLocalService.setLock(null);

			owner = clusterMasterExecutorImpl.getMasterAddressString();

			Assert.assertEquals(_TEST_OWNER, owner);
			Assert.assertTrue(logRecords.isEmpty());
		}
		finally {
			captureHandler.close();
		}
	}

	@Test
	public void testGetMasterAddressStringReleaseMaster() {
		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		ClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener =
				new MockClusterMasterTokenTransitionListener();

		clusterMasterExecutorImpl.registerClusterMasterTokenTransitionListener(
			clusterMasterTokenTransitionListener);

		clusterMasterExecutorImpl.initialize();

		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		long otherTime = _TEST_TIME + 1;

		Address otherAddress =  new AddressImpl(new MockAddress(otherTime));

		MockClusterExecutor.addClusterNodeAddress(otherAddress);

		String otherOwner = AddressSerializerUtil.serialize(otherAddress);

		MockLockLocalService.setLock(otherOwner);

		clusterMasterExecutorImpl.getMasterAddressString();

		Assert.assertFalse(clusterMasterExecutorImpl.isMaster());
	}

	@Test
	public void testGetMasterAddressStringSuccess() {
		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		ClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

		clusterMasterExecutorImpl.initialize();

		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener =
				new MockClusterMasterTokenTransitionListener() {

					@Override
					public void masterTokenReleased() {
						Assert.fail();
					}

				};

		clusterMasterExecutorImpl.registerClusterMasterTokenTransitionListener(
			clusterMasterTokenTransitionListener);

		String owner = clusterMasterExecutorImpl.getMasterAddressString();

		Address address = AddressSerializerUtil.deserialize(owner);

		Assert.assertEquals(_TEST_ADDRESS, address);
	}

	@Test
	public void testInitClusterLinkDisabled() {
		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		ClusterExecutor mockClusterExecutor = new MockClusterExecutor(false);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

		clusterMasterExecutorImpl.initialize();

		Assert.assertFalse(clusterMasterExecutorImpl.isEnabled());
		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());
	}

	@Test
	public void testInitClusterLinkEnabled() {
		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		ClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		clusterMasterExecutorImpl.initialize();

		Assert.assertTrue(clusterMasterExecutorImpl.isEnabled());
		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());
	}

	@Test
	public void testNotifyMasterTokenTransitionListenersTokenAcquired() {
		doNotifyMasterTokenTransitionListeners(true);
	}

	@Test
	public void testNotifyMasterTokenTransitionListenersTokenReleased() {
		doNotifyMasterTokenTransitionListeners(false);
	}

	protected void doNotifyMasterTokenTransitionListeners(boolean acquired) {
		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		ClusterMasterTokenTransitionListener
			clusterMasterTokenTransitionListener = null;

		if (acquired) {
			clusterMasterTokenTransitionListener =
				new MockClusterMasterTokenTransitionListener() {

				@Override
				public void masterTokenReleased() {
					Assert.fail();
				}

			};
		}
		else {
			clusterMasterTokenTransitionListener =
				new MockClusterMasterTokenTransitionListener() {

				@Override
				public void masterTokenAcquired() {
					Assert.fail();
				}

			};
		}

		clusterMasterExecutorImpl.registerClusterMasterTokenTransitionListener(
			clusterMasterTokenTransitionListener);
		clusterMasterExecutorImpl.notifyMasterTokenTransitionListeners(
			acquired);
	}

	protected static MethodKey testMethodMethodKey = new MethodKey(
		TestBean.class, "testMethod1", String.class);

	private static Address _TEST_ADDRESS;

	private static String _TEST_OWNER;

	private static long _TEST_TIME;

	private static class MockAddress implements org.jgroups.Address {

		public MockAddress() {
		}

		public MockAddress(long timestamp) {
			_timestamp = timestamp;
		}

		@Override
		public int compareTo(org.jgroups.Address jGroupsAddress) {
			return 0;
		}

		@Override
		public boolean equals(Object obj) {
			MockAddress mockAddress = (MockAddress)obj;

			if (_timestamp == mockAddress.getTimestamp()) {
				return true;
			}

			return false;
		}

		public long getTimestamp() {
			return _timestamp;
		}

		@Override
		public int hashCode() {
			return 11 * (int)_timestamp;
		}

		@Override
		public void readExternal(ObjectInput objectInput) throws IOException {
			_timestamp = objectInput.readLong();
		}

		@Override
		public void readFrom(DataInput dataInput) throws Exception {
			_timestamp = dataInput.readLong();
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public void writeExternal(ObjectOutput objectOutput)
			throws IOException {

			objectOutput.writeLong(_timestamp);
		}

		@Override
		public void writeTo(DataOutput dataOutput) throws Exception {
			dataOutput.writeLong(_timestamp);
		}

		private long _timestamp;

	}

	private static class MockClusterExecutor implements ClusterExecutor {

		public static void addClusterNodeAddress(Address address) {
			_addresses.add(address);
		}

		public static void setBreak(boolean brk) {
			_break = brk;
		}

		public static void setOtherAddress(Address address) {
			_otherAddress = address;
		}

		public MockClusterExecutor(boolean enabled) {
			_enabled = enabled;

			_localAddress = _TEST_ADDRESS;

			_addresses.add(_localAddress);
		}

		@Override
		public void addClusterEventListener(
			ClusterEventListener clusterEventListener) {

			_clusterEventListeners.add(clusterEventListener);
		}

		@Override
		public void destroy() {
			_addresses.clear();
		}

		@Override
		public FutureClusterResponses execute(ClusterRequest clusterRequest) {
			if (_break) {
				return null;
			}

			List<Address> addresses = new ArrayList<Address>();

			Collection<Address> clusterNodeAddresses =
				clusterRequest.getTargetClusterNodeAddresses();

			if (clusterNodeAddresses != null) {
				addresses.addAll(clusterNodeAddresses);
			}

			FutureClusterResponses futureClusterResponses =
				new FutureClusterResponses(addresses);

			for (Address address : addresses) {
				ClusterNodeResponse clusterNodeResponse =
					new ClusterNodeResponse();

				clusterNodeResponse.setAddress(address);
				clusterNodeResponse.setClusterMessageType(
					ClusterMessageType.EXECUTE);
				clusterNodeResponse.setMulticast(clusterRequest.isMulticast());
				clusterNodeResponse.setUuid(clusterRequest.getUuid());

				MockAddress mockAddress = (MockAddress)address.getRealAddress();

				try {
					clusterNodeResponse.setClusterNode(
						new ClusterNode(
							String.valueOf(mockAddress.getTimestamp()),
							InetAddress.getLocalHost()));

					clusterNodeResponse.setResult(
						(clusterRequest.getMethodHandler().invoke()));
				}
				catch (Exception e) {
				}

				futureClusterResponses.addClusterNodeResponse(
					clusterNodeResponse);
			}

			return futureClusterResponses;
		}

		@Override
		public FutureClusterResponses execute(
			ClusterRequest clusterRequest,
			ClusterResponseCallback clusterResponseCallback) {

			return null;
		}

		@Override
		public List<ClusterEventListener> getClusterEventListeners() {
			return Collections.unmodifiableList(_clusterEventListeners);
		}

		@Override
		public List<Address> getClusterNodeAddresses() {
			return Collections.unmodifiableList(_addresses);
		}

		@Override
		public List<ClusterNode> getClusterNodes() {
			return Collections.emptyList();
		}

		@Override
		public ClusterNode getLocalClusterNode() {
			return null;
		}

		@Override
		public Address getLocalClusterNodeAddress() {
			return _localAddress;
		}

		@Override
		public void initialize() {
		}

		@Override
		public boolean isClusterNodeAlive(Address address) {
			if (_break && address.equals(_otherAddress)) {
				throw new RuntimeException();
			}

			return _addresses.contains(address);
		}

		@Override
		public boolean isClusterNodeAlive(String clusterNodeId) {
			return false;
		}

		@Override
		public boolean isEnabled() {
			return _enabled;
		}

		@Override
		public void removeClusterEventListener(
			ClusterEventListener clusterEventListener) {

			_clusterEventListeners.remove(clusterEventListener);
		}

		private static final List<Address> _addresses =
			new ArrayList<Address>();
		private static boolean _break;
		private static Address _otherAddress;

		private final List<ClusterEventListener> _clusterEventListeners =
			new ArrayList<ClusterEventListener>();
		private final boolean _enabled;
		private final Address _localAddress;

	}

	private static class MockClusterMasterTokenTransitionListener
		implements ClusterMasterTokenTransitionListener {

		public MockClusterMasterTokenTransitionListener() {
		}

		@Override
		public void masterTokenAcquired() {
		}

		@Override
		public void masterTokenReleased() {
		}

	}

	private static class MockLockLocalService extends LockLocalServiceImpl {

		public static Lock getLock() {
			return _lock;
		}

		public static void override(Boolean override, String value) {
			_override = override;
			_value = value;
		}

		public static void resetLock() {
			if (_lock != null) {
				_lock = null;
			}
		}

		public static void setLock(String owner) {
			Lock lock = new LockImpl();

			lock.setOwner(owner);

			_lock = lock;
		}

		public static void setUnlockError(boolean error) {
			_errorOnUnlock = error;
		}

		@Override
		public Lock lock(String className, String key, String owner) {
			if (_lock == null) {
				Lock lock = new LockImpl();

				lock.setKey(key);

				if (_override) {
					lock.setOwner(_value);
				}
				else {
					lock.setOwner(owner);
				}

				_lock = lock;
			}

			return _lock;
		}

		@Override
		public Lock lock(
			String className, String key, String expectedOwner,
			String updatedOwner) {

			Lock lock = new LockImpl();

			lock.setKey(key);
			lock.setOwner(updatedOwner);

			_lock = lock;

			return lock;
		}

		@Override
		public void unlock(String className, String key, String owner) {
			if (_errorOnUnlock) {
				throw new SystemException();
			}

			_lock = null;
		}

		private static boolean _errorOnUnlock;
		private static Lock _lock;
		private static boolean _override;
		private static String _value;

	}

}