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
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
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

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class ClusterMasterExecutorImplTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		ReflectionTestUtil.setFieldValue(
			LockLocalServiceUtil.class, "_service", _mockLockLocalService);
	}

	@Test
	public void testClusterMasterTokenClusterEventListener() {

		// Test 1, cluster event listener is invoked when lock is not changed

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		mockClusterExecutor.addClusterNodeAddress(_OTHER_ADDRESS);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

		clusterMasterExecutorImpl.initialize();

		List<ClusterEventListener> clusterEventListeners =
			mockClusterExecutor.getClusterEventListeners();

		ClusterEventListener clusterEventListener = clusterEventListeners.get(
			0);

		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		clusterEventListener.processClusterEvent(null);

		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		// Test 2, cluster event listener is invoked when lock is changed

		_mockLockLocalService.setLock(
			AddressSerializerUtil.serialize(_OTHER_ADDRESS));

		clusterEventListener.processClusterEvent(null);

		Assert.assertFalse(clusterMasterExecutorImpl.isMaster());
	}

	@Test
	public void testClusterMasterTokenTransitionListeners() {

		// Test 1, register cluster master token transition listener

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		Set<ClusterMasterTokenTransitionListener>
			clusterMasterTokenTransitionListeners =
				ReflectionTestUtil.getFieldValue(
					clusterMasterExecutorImpl,
					"_clusterMasterTokenTransitionListeners");

		Assert.assertTrue(clusterMasterTokenTransitionListeners.isEmpty());

		ClusterMasterTokenTransitionListener
			mockClusterMasterTokenTransitionListener =
				new MockClusterMasterTokenTransitionListener();

		clusterMasterExecutorImpl.registerClusterMasterTokenTransitionListener(
			mockClusterMasterTokenTransitionListener);

		Assert.assertEquals(1, clusterMasterTokenTransitionListeners.size());

		// Test 2, unregister cluster master token transition listener

		clusterMasterExecutorImpl.
			unregisterClusterMasterTokenTransitionListener(
				mockClusterMasterTokenTransitionListener);

		Assert.assertTrue(clusterMasterTokenTransitionListeners.isEmpty());

		// Test 3, set cluster master token transition listeners

		clusterMasterExecutorImpl.setClusterMasterTokenTransitionListeners(
			Collections.singleton(mockClusterMasterTokenTransitionListener));

		Assert.assertEquals(1, clusterMasterTokenTransitionListeners.size());
	}

	@Test
	public void testDestroy() {

		// Test 1, desctory when cluster link is enabled

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

		clusterMasterExecutorImpl.initialize();

		List<ClusterEventListener> clusterEventListeners =
			mockClusterExecutor.getClusterEventListeners();

		Assert.assertEquals(1, clusterEventListeners.size());
		Assert.assertNotNull(_mockLockLocalService.getLock());

		clusterMasterExecutorImpl.destroy();

		Assert.assertTrue(clusterEventListeners.isEmpty());
		Assert.assertNull(_mockLockLocalService.getLock());

		// Test 2, destory when cluster link is disabled

		clusterMasterExecutorImpl = new ClusterMasterExecutorImpl();

		clusterMasterExecutorImpl.setClusterExecutor(
			new MockClusterExecutor(false));

		clusterMasterExecutorImpl.initialize();

		clusterMasterExecutorImpl.destroy();

		// Test 3, destory with exception when log is enabled

		clusterMasterExecutorImpl = new ClusterMasterExecutorImpl();

		clusterMasterExecutorImpl.setClusterExecutor(
			new MockClusterExecutor(true));

		clusterMasterExecutorImpl.initialize();

		_mockLockLocalService.setUnlockError(true);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.WARNING)) {

			clusterMasterExecutorImpl.destroy();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to destroy the cluster master executor",
				logRecord.getMessage());
		}

		// Test 4, destory with exception when log is disabled

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.OFF)) {

			clusterMasterExecutorImpl.destroy();

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
		}
	}

	@Test
	public void testExecuteOnMasterDisabled() throws Exception {

		// Test 1, execute without exception when log is eanbled

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		clusterMasterExecutorImpl.setClusterExecutor(
			new MockClusterExecutor(false));

		clusterMasterExecutorImpl.initialize();

		Assert.assertFalse(clusterMasterExecutorImpl.isEnabled());

		String timeString = String.valueOf(System.currentTimeMillis());

		MethodHandler methodHandler = new MethodHandler(
			testMethodMethodKey, timeString);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.WARNING)) {

			NoticeableFuture<String> noticeableFuture =
				clusterMasterExecutorImpl.executeOnMaster(methodHandler);

			Assert.assertSame(timeString, noticeableFuture.get());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Executing on the local node because the cluster master " +
					"executor is disabled",
				logRecord.getMessage());
		}

		// Test 2, execute without exception when log is disabled

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.OFF)) {

			NoticeableFuture<String> noticeableFuture =
				clusterMasterExecutorImpl.executeOnMaster(methodHandler);

			Assert.assertSame(timeString, noticeableFuture.get());

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
		}

		// Test 3, execute with exception

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.WARNING)) {

			try {
				clusterMasterExecutorImpl.executeOnMaster(null);

				Assert.fail();
			}
			catch (SystemException se) {
				Throwable throwable = se.getCause();

				Assert.assertSame(
					NullPointerException.class, throwable.getClass());

				List<LogRecord> logRecords = captureHandler.getLogRecords();

				Assert.assertEquals(1, logRecords.size());

				LogRecord logRecord = logRecords.get(0);

				Assert.assertEquals(
					"Executing on the local node because the cluster master " +
						"executor is disabled",
					logRecord.getMessage());
			}
		}
	}

	@Test
	public void testExecuteOnMasterEnabled() throws Exception {

		// Test 1, execute without exception

		PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();

		portalUUIDUtil.setPortalUUID(new PortalUUIDImpl());

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		clusterMasterExecutorImpl.setClusterExecutor(
			new MockClusterExecutor(true));

		clusterMasterExecutorImpl.initialize();

		Assert.assertTrue(clusterMasterExecutorImpl.isEnabled());

		String timeString = String.valueOf(System.currentTimeMillis());

		NoticeableFuture<String> noticeableFuture =
			clusterMasterExecutorImpl.executeOnMaster(
				new MethodHandler(testMethodMethodKey, timeString));

		Assert.assertSame(timeString, noticeableFuture.get());

		// Test 2, execute with exception

		try {
			clusterMasterExecutorImpl.executeOnMaster(null);

			Assert.fail();
		}
		catch (SystemException se) {
			Assert.assertEquals(
				"Unable to execute on master " +
					_LOCAL_ADDRESS.getDescription(),
				se.getMessage());
		}
	}

	@Test
	public void testGetMasterAddressString() {

		// Test 1, master to slave

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		mockClusterExecutor.addClusterNodeAddress(_OTHER_ADDRESS);

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

		clusterMasterExecutorImpl.initialize();

		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		MockClusterMasterTokenTransitionListener
			mockClusterMasterTokenTransitionListener =
				new MockClusterMasterTokenTransitionListener();

		clusterMasterExecutorImpl.registerClusterMasterTokenTransitionListener(
			mockClusterMasterTokenTransitionListener);

		_mockLockLocalService.setLock(
			AddressSerializerUtil.serialize(_OTHER_ADDRESS));

		clusterMasterExecutorImpl.getMasterAddressString();

		Assert.assertFalse(clusterMasterExecutorImpl.isMaster());
		Assert.assertTrue(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenReleasedNotified());

		// Test 2, slave to master

		_mockLockLocalService.setLock(
			AddressSerializerUtil.serialize(_LOCAL_ADDRESS));

		clusterMasterExecutorImpl.getMasterAddressString();

		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());
		Assert.assertTrue(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenAcquiredNotified());
	}

	@Test
	public void testGetMasterAddressStringWithException() {

		// Test 1, current owner is not alive

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		clusterMasterExecutorImpl.setClusterExecutor(
			new MockClusterExecutor(true));

		clusterMasterExecutorImpl.initialize();

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.INFO)) {

			String otherOwner = AddressSerializerUtil.serialize(_OTHER_ADDRESS);

			_mockLockLocalService.setLock(otherOwner);

			Assert.assertEquals(
				_LOCAL_ADDRESS,
				AddressSerializerUtil.deserialize(
					clusterMasterExecutorImpl.getMasterAddressString()));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(2, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Lock currently held by " + otherOwner, logRecord.getMessage());

			logRecord = logRecords.get(1);

			Assert.assertEquals(
				"Reattempting to acquire the cluster master lock",
				logRecord.getMessage());
		}

		// Test 2, current owner is null and log is enabled

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.INFO)) {

			_mockLockLocalService.setLock(null);

			Assert.assertEquals(
				_LOCAL_ADDRESS,
				AddressSerializerUtil.deserialize(
					clusterMasterExecutorImpl.getMasterAddressString()));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(2, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to acquire the cluster master lock",
				logRecord.getMessage());

			logRecord = logRecords.get(1);

			Assert.assertEquals(
				"Reattempting to acquire the cluster master lock",
				logRecord.getMessage());
		}

		// Test 3, current owner is null and log is disabled

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ClusterMasterExecutorImpl.class.getName(), Level.OFF)) {

			_mockLockLocalService.setLock(null);

			Assert.assertEquals(
				_LOCAL_ADDRESS,
				AddressSerializerUtil.deserialize(
					clusterMasterExecutorImpl.getMasterAddressString()));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertTrue(logRecords.isEmpty());
		}
	}

	@Test
	public void testInitialize() {

		// Test 1, initialize when cluster link is disabled

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		clusterMasterExecutorImpl.setClusterExecutor(
			new MockClusterExecutor(false));

		clusterMasterExecutorImpl.initialize();

		Assert.assertFalse(clusterMasterExecutorImpl.isEnabled());
		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		// Test 2, initialize when cluster link is enabled and lock is null

		Assert.assertNull(_mockLockLocalService.getLock());

		clusterMasterExecutorImpl = new ClusterMasterExecutorImpl();

		clusterMasterExecutorImpl.setClusterExecutor(
			new MockClusterExecutor(true));

		clusterMasterExecutorImpl.initialize();

		Assert.assertTrue(clusterMasterExecutorImpl.isEnabled());
		Assert.assertTrue(clusterMasterExecutorImpl.isMaster());

		// Test 3, initialize when cluster link is enabled and lock is not null

		MockClusterExecutor mockClusterExecutor = new MockClusterExecutor(true);

		mockClusterExecutor.addClusterNodeAddress(_OTHER_ADDRESS);

		_mockLockLocalService.setLock(
			AddressSerializerUtil.serialize(_OTHER_ADDRESS));

		Assert.assertNotNull(_mockLockLocalService.getLock());

		clusterMasterExecutorImpl = new ClusterMasterExecutorImpl();

		clusterMasterExecutorImpl.setClusterExecutor(mockClusterExecutor);

		clusterMasterExecutorImpl.initialize();

		Assert.assertTrue(clusterMasterExecutorImpl.isEnabled());
		Assert.assertFalse(clusterMasterExecutorImpl.isMaster());
	}

	@Test
	public void testNotifyMasterTokenTransitionListeners() {

		// Test 1, notify when master is required

		ClusterMasterExecutorImpl clusterMasterExecutorImpl =
			new ClusterMasterExecutorImpl();

		MockClusterMasterTokenTransitionListener
			mockClusterMasterTokenTransitionListener =
				new MockClusterMasterTokenTransitionListener();

		clusterMasterExecutorImpl.registerClusterMasterTokenTransitionListener(
			mockClusterMasterTokenTransitionListener);

		clusterMasterExecutorImpl.notifyMasterTokenTransitionListeners(true);

		Assert.assertTrue(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenAcquiredNotified());
		Assert.assertFalse(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenReleasedNotified());

		// Test 2, notify when master is released

		clusterMasterExecutorImpl = new ClusterMasterExecutorImpl();

		mockClusterMasterTokenTransitionListener =
			new MockClusterMasterTokenTransitionListener();

		clusterMasterExecutorImpl.registerClusterMasterTokenTransitionListener(
			mockClusterMasterTokenTransitionListener);

		clusterMasterExecutorImpl.notifyMasterTokenTransitionListeners(false);

		Assert.assertFalse(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenAcquiredNotified());
		Assert.assertTrue(
			mockClusterMasterTokenTransitionListener.
				isMasterTokenReleasedNotified());
	}

	protected static MethodKey testMethodMethodKey = new MethodKey(
		TestBean.class, "testMethod1", String.class);

	private static final Address _LOCAL_ADDRESS = new AddressImpl(
		new MockAddress("_LOCAL_ADDRESS"));

	private static final Address _OTHER_ADDRESS = new AddressImpl(
		new MockAddress("_OTHER_ADDRESS"));

	private final MockLockLocalService _mockLockLocalService =
		new MockLockLocalService();

	private static class MockAddress implements org.jgroups.Address {

		public MockAddress() {
		}

		public MockAddress(String name) {
			_name = name;
		}

		@Override
		public int compareTo(org.jgroups.Address jGroupsAddress) {
			return 0;
		}

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof MockAddress)) {
				return false;
			}

			MockAddress mockAddress = (MockAddress)object;

			if (_name.equals(mockAddress._name)) {
				return true;
			}

			return false;
		}

		public String getName() {
			return _name;
		}

		@Override
		public int hashCode() {
			return _name.hashCode();
		}

		@Override
		public void readExternal(ObjectInput objectInput) throws IOException {
			_name = objectInput.readUTF();
		}

		@Override
		public void readFrom(DataInput dataInput) throws IOException {
			_name = dataInput.readUTF();
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public void writeExternal(ObjectOutput objectOutput)
			throws IOException {

			objectOutput.writeUTF(_name);
		}

		@Override
		public void writeTo(DataOutput dataOutput) throws IOException {
			dataOutput.writeUTF(_name);
		}

		private String _name;

	}

	private static class MockClusterExecutor implements ClusterExecutor {

		public MockClusterExecutor(boolean enabled) {
			_enabled = enabled;

			_addresses.add(_LOCAL_ADDRESS);
		}

		@Override
		public void addClusterEventListener(
			ClusterEventListener clusterEventListener) {

			_clusterEventListeners.add(clusterEventListener);
		}

		public void addClusterNodeAddress(Address address) {
			_addresses.add(address);
		}

		@Override
		public void destroy() {
			_addresses.clear();
		}

		@Override
		public FutureClusterResponses execute(ClusterRequest clusterRequest) {
			List<Address> addresses = new ArrayList<>();

			Collection<Address> clusterNodeAddresses =
				clusterRequest.getTargetClusterNodeAddresses();

			if (clusterNodeAddresses != null) {
				addresses.addAll(clusterNodeAddresses);
			}

			Set<String> clusterNodeIds = new HashSet<>();

			for (Address address : addresses) {
				MockAddress mockAddress = (MockAddress)address.getRealAddress();

				clusterNodeIds.add(mockAddress.getName());
			}

			FutureClusterResponses futureClusterResponses =
				new FutureClusterResponses(clusterNodeIds);

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
							mockAddress.getName(), InetAddress.getLocalHost()));

					MethodHandler methodHandler =
						clusterRequest.getMethodHandler();

					clusterNodeResponse.setResult(methodHandler.invoke());
				}
				catch (Exception e) {
					throw new RuntimeException(e);
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
			return _LOCAL_ADDRESS;
		}

		@Override
		public void initialize() {
		}

		@Override
		public boolean isClusterNodeAlive(Address address) {
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

		private final List<Address> _addresses = new ArrayList<>();
		private final List<ClusterEventListener> _clusterEventListeners =
			new ArrayList<>();
		private final boolean _enabled;

	}

	private static class MockClusterMasterTokenTransitionListener
		implements ClusterMasterTokenTransitionListener {

		public boolean isMasterTokenAcquiredNotified() {
			return _masterTokenAcquiredNotified;
		}

		public boolean isMasterTokenReleasedNotified() {
			return _masterTokenReleasedNotified;
		}

		@Override
		public void masterTokenAcquired() {
			_masterTokenAcquiredNotified = true;
		}

		@Override
		public void masterTokenReleased() {
			_masterTokenReleasedNotified = true;
		}

		private boolean _masterTokenAcquiredNotified;
		private boolean _masterTokenReleasedNotified;

	}

	private static class MockLockLocalService extends LockLocalServiceImpl {

		public Lock getLock() {
			return _lock;
		}

		@Override
		public Lock lock(String className, String key, String owner) {
			if (_lock == null) {
				_lock = new LockImpl();

				_lock.setKey(key);
				_lock.setOwner(owner);
			}

			return _lock;
		}

		@Override
		public Lock lock(
			String className, String key, String expectedOwner,
			String updatedOwner) {

			_lock = new LockImpl();

			_lock.setKey(key);
			_lock.setOwner(updatedOwner);

			return _lock;
		}

		public void setLock(String owner) {
			_lock = new LockImpl();

			_lock.setOwner(owner);
		}

		public void setUnlockError(boolean error) {
			_errorOnUnlock = error;
		}

		@Override
		public void unlock(String className, String key, String owner) {
			if (_errorOnUnlock) {
				throw new SystemException();
			}

			_lock = null;
		}

		private boolean _errorOnUnlock;
		private Lock _lock;

	}

}