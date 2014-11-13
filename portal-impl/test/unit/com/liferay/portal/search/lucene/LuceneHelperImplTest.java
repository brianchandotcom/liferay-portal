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

package com.liferay.portal.search.lucene;

import com.liferay.portal.cluster.AddressImpl;
import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterMessageType;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.ClusterResponseCallback;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.NewEnv;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.security.auth.TransientTokenUtil;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJNewEnvMethodRule;
import com.liferay.portal.util.PortalImpl;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.uuid.PortalUUIDImpl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class LuceneHelperImplTest {

	@Before
	public void setUp() throws Exception {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());

		PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();

		portalUUIDUtil.setPortalUUID(new PortalUUIDImpl());

		PortalInstances.addCompanyId(_COMPANY_ID);

		_localhostInetAddress = InetAddress.getLocalHost();

		_mockClusterExecutor = new MockClusterExecutor();

		ClusterExecutorUtil clusterExecutorUtil = new ClusterExecutorUtil();

		clusterExecutorUtil.setClusterExecutor(_mockClusterExecutor);

		Class<LuceneHelperImpl> luceneHelperImplClass = LuceneHelperImpl.class;

		Constructor<LuceneHelperImpl> luceneHelperImplConstructor =
			luceneHelperImplClass.getDeclaredConstructor();

		luceneHelperImplConstructor.setAccessible(true);

		_luceneHelperImpl = luceneHelperImplConstructor.newInstance();

		Field indexAccessorsField = luceneHelperImplClass.getDeclaredField(
			"_indexAccessors");

		indexAccessorsField.setAccessible(true);

		_mockIndexAccessor = new MockIndexAccessor();

		Map<Long, IndexAccessor> indexAccessorMap =
			(Map<Long, IndexAccessor>)indexAccessorsField.get(
				_luceneHelperImpl);

		indexAccessorMap.put(_COMPANY_ID, _mockIndexAccessor);

		LuceneHelperUtil luceneHelperUtil = new LuceneHelperUtil();

		luceneHelperUtil.setLuceneHelper(_luceneHelperImpl);

		_clusterNode = new ClusterNode(_CLUSER_NODE_ID, _localhostInetAddress);

		_captureHandler = JDKLoggerTestUtil.configureJDKLogger(
			LuceneHelperImpl.class.getName(), Level.ALL);
	}

	@After
	public void tearDown() {
		_captureHandler.close();
	}

	@AdviseWith(
		adviceClasses = {
			DisableIndexOnStartUpAdvice.class, EnableClusterLinkAdvice.class,
			EnableLuceneReplicateWriteAdvice.class,
		}
	)
	@Test
	public void testGetBootupClusterNodeObjectValuePair() throws Exception {
		_mockClusterExecutor.setNodeNumber(1);
		_mockClusterExecutor.setPort(1);
		_mockClusterExecutor.setPortalInetAddress(_localhostInetAddress);

		Method method = LuceneHelperImpl.class.getDeclaredMethod(
			"_getBootupClusterNodeObjectValuePair", Address.class);

		method.setAccessible(true);

		Object object = method.invoke(
			_luceneHelperImpl,
			_mockClusterExecutor.getLocalClusterNodeAddress());

		Assert.assertNotNull(object);

		ObjectValuePair<String, URL> result =
			(ObjectValuePair<String, URL>)object;

		URL url = result.getValue();

		Assert.assertEquals(
			_localhostInetAddress.getHostAddress(), url.getHost());
		Assert.assertEquals(1, url.getPort());
	}

	@AdviseWith(
		adviceClasses = {
			DisableIndexOnStartUpAdvice.class, EnableClusterLinkAdvice.class,
			EnableLuceneReplicateWriteAdvice.class,
			SkipGetBootupClusterNodeObjectValuePairAdvice.class
		}
	)
	@Test
	public void testGetLoadIndexesInputStreamFromCluster() throws Exception {
		URL url = new URL("http://127.0.0.1:80/lucene/dump");

		final MockURLConnection mockURLConnection = new MockURLConnection(url);

		ReflectionTestUtil.setFieldValue(
			url, "handler",
			new URLStreamHandler() {

				@Override
				protected URLConnection openConnection(URL url) {
					return mockURLConnection;
				}

			});

		SkipGetBootupClusterNodeObjectValuePairAdvice.setURL(url);

		InputStream inputStream =
			_luceneHelperImpl.getLoadIndexesInputStreamFromCluster(
				_COMPANY_ID, new AddressImpl(new MockAddress()));

		Assert.assertNotNull(inputStream);

		_mockIndexAccessor.loadIndex(inputStream);

		mockURLConnection.assertOutputContent(
			"transientToken=&companyId=" + _COMPANY_ID);

		Assert.assertArrayEquals(
			_RESPONSE_MESSAGE, _mockIndexAccessor.getResponseMessage());
	}

	@AdviseWith(
		adviceClasses = {
			DisableIndexOnStartUpAdvice.class, EnableClusterLinkAdvice.class,
			EnableLuceneReplicateWriteAdvice.class,
			LuceneClusterUtilAdvice.class
		}
	)
	@Test
	public void testLoadIndexClusterEventListener() throws Exception {

		// Test 1, 2 nodes in cluster

		ClusterEvent clusterEvent = ClusterEvent.join(_clusterNode);

		_mockClusterExecutor.reset();

		_mockClusterExecutor.setNodeNumber(2);

		_fireClusterEventListeners(clusterEvent);

		Assert.assertEquals(
			_COMPANY_ID, LuceneClusterUtilAdvice.getCompanyId());

		// Test 2, more than 2 nodes in cluster with debug enabled

		_mockClusterExecutor.reset();

		_mockClusterExecutor.setNodeNumber(3);

		List<LogRecord> logRecords = _captureHandler.resetLogLevel(Level.FINE);

		_fireClusterEventListeners(clusterEvent);

		Assert.assertEquals(1, logRecords.size());

		_assertLogger(
			logRecords.get(0),
			"Number of original cluster members is greater than one", null);

		Assert.assertNotEquals(
			_COMPANY_ID, LuceneClusterUtilAdvice.getCompanyId());

		// Test 3, more than 2 nodes in cluster with debug disabled

		_mockClusterExecutor.reset();

		_mockClusterExecutor.setNodeNumber(3);

		logRecords = _captureHandler.resetLogLevel(Level.INFO);

		_fireClusterEventListeners(clusterEvent);

		Assert.assertTrue(logRecords.isEmpty());

		Assert.assertNotEquals(
			_COMPANY_ID, LuceneClusterUtilAdvice.getCompanyId());
	}

	@AdviseWith(
		adviceClasses = {
			DisableIndexOnStartUpAdvice.class, EnableClusterLinkAdvice.class,
			EnableLuceneReplicateWriteAdvice.class,
			LuceneClusterUtilAdvice.class
		}
	)
	@Test
	public void testLoadIndexClusterEventListenerWithException() {
		Exception exception = new Exception();

		LuceneClusterUtilAdvice.setException(exception);

		_mockClusterExecutor.setNodeNumber(2);

		List<LogRecord> logRecords = _captureHandler.resetLogLevel(
			Level.SEVERE);

		ClusterEvent clusterEvent = ClusterEvent.join(_clusterNode);

		_fireClusterEventListeners(clusterEvent);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		_assertLogger(
			logRecord, "Unable to load indexes for company " + _COMPANY_ID,
			Exception.class);

		Assert.assertSame(exception, logRecord.getThrown());
	}

	@AdviseWith(
		adviceClasses = {
			DisableIndexOnStartUpAdvice.class, EnableClusterLinkAdvice.class,
			EnableLuceneReplicateWriteAdvice.class,
			SkipGetLoadIndexesInputStreamFromClusterAdvice.class
		}
	)
	@Test
	public void testLoadIndexFromCluster() throws Exception {
		_mockClusterExecutor.setNodeNumber(2);
		_mockClusterExecutor.setPort(1);
		_mockClusterExecutor.setPortalInetAddress(_localhostInetAddress);

		List<LogRecord> logRecords = _captureHandler.resetLogLevel(Level.INFO);

		_luceneHelperImpl.loadIndexesFromCluster(_COMPANY_ID);

		Assert.assertEquals(
			_COMPANY_ID,
			SkipGetLoadIndexesInputStreamFromClusterAdvice._companyId);

		List<Address> address = _mockClusterExecutor.getClusterNodeAddresses();

		Assert.assertTrue(
			address.contains(
				SkipGetLoadIndexesInputStreamFromClusterAdvice._bootupAddress));

		Assert.assertEquals(2, logRecords.size());

		_assertLogger(
			logRecords.get(0),
			"Start loading lucene index files from cluster node", null);
		_assertLogger(
			logRecords.get(1), "Lucene index files loaded successfully", null);

		Assert.assertArrayEquals(
			_RESPONSE_MESSAGE, _mockIndexAccessor.getResponseMessage());
	}

	@AdviseWith(
		adviceClasses = {
			DisableIndexOnStartUpAdvice.class, DisableClusterLinkAdvice.class,
			EnableLuceneReplicateWriteAdvice.class
		}
	)
	@Test
	public void testLoadIndexFromClusterWithClusterLinkDisabled() {
		List<LogRecord> logRecords = _captureHandler.resetLogLevel(Level.FINE);

		_luceneHelperImpl.loadIndexesFromCluster(0);

		Assert.assertEquals(1, logRecords.size());

		_assertLogger(
			logRecords.get(0), "Load index from cluster is not enabled", null);
	}

	@AdviseWith(
		adviceClasses = {
			DisableIndexOnStartUpAdvice.class, EnableClusterLinkAdvice.class,
			EnableLuceneReplicateWriteAdvice.class
		}
	)
	@Test
	public void testLoadIndexFromClusterWithException() throws Exception {

		// Test 1, unable to get response from cluster with debug enabled

		_mockClusterExecutor.reset();

		_mockClusterExecutor.setNodeNumber(3);
		_mockClusterExecutor.setAutoResponse(false);

		List<LogRecord> logRecords = _captureHandler.resetLogLevel(Level.FINE);

		_luceneHelperImpl.loadIndexesFromCluster(_COMPANY_ID);

		Assert.assertEquals(2, logRecords.size());

		_assertLogger(
			logRecords.get(0),
			"Unable to get cluster node response in 10000" +
				TimeUnit.MILLISECONDS,
			null);

		_assertLogger(
			logRecords.get(1),
			"Unable to get cluster node response in 10000" +
				TimeUnit.MILLISECONDS,
			null);

		// Test 2, unable to get response from cluster with debug disabled

		_mockClusterExecutor.reset();

		_mockClusterExecutor.setNodeNumber(3);
		_mockClusterExecutor.setAutoResponse(false);

		logRecords = _captureHandler.resetLogLevel(Level.INFO);

		_luceneHelperImpl.loadIndexesFromCluster(_COMPANY_ID);

		Assert.assertTrue(logRecords.isEmpty());

		// Test 3, unable to get address with debug enabled

		_mockClusterExecutor.reset();

		_mockClusterExecutor.setNodeNumber(2);

		logRecords = _captureHandler.resetLogLevel(Level.FINE);

		_luceneHelperImpl.loadIndexesFromCluster(_COMPANY_ID);

		Assert.assertEquals(1, logRecords.size());

		_assertLogger(logRecords.get(0), "invalid InetSocketAddress", null);

		// Test 4, unable to get address with debug disabled

		_mockClusterExecutor.reset();

		_mockClusterExecutor.setNodeNumber(2);

		logRecords = _captureHandler.resetLogLevel(Level.INFO);

		_luceneHelperImpl.loadIndexesFromCluster(_COMPANY_ID);

		Assert.assertTrue(logRecords.isEmpty());

		// Test 5, unable to load index

		_mockClusterExecutor.reset();

		_mockClusterExecutor.setNodeNumber(2);
		_mockClusterExecutor.setPort(1024);

		logRecords = _captureHandler.resetLogLevel(Level.FINE);

		_luceneHelperImpl.loadIndexesFromCluster(_COMPANY_ID);

		Assert.assertEquals(2, logRecords.size());

		_assertLogger(
			logRecords.get(0),
			"Start loading lucene index files from cluster node", null);
		_assertLogger(
			logRecords.get(1),
			"Unable to load index for company " + _COMPANY_ID,
			SystemException.class);

		// Test 6, unable to invoke method on other nodes with debug enabled

		_mockClusterExecutor.reset();

		_mockClusterExecutor.setNodeNumber(2);
		_mockClusterExecutor.setInvokeMethodThrowException(true);
		_mockClusterExecutor.setPort(1024);

		logRecords = _captureHandler.resetLogLevel(Level.FINE);

		_luceneHelperImpl.loadIndexesFromCluster(_COMPANY_ID);

		Assert.assertEquals(1, logRecords.size());

		_assertLogger(
			logRecords.get(0),
			"Suppress exception caused by remote method invocation",
			Exception.class);

		// Test 7, unable to invoke method on other nodes with debug disabled

		_mockClusterExecutor.reset();

		_mockClusterExecutor.setNodeNumber(2);
		_mockClusterExecutor.setInvokeMethodThrowException(true);
		_mockClusterExecutor.setPort(1024);

		logRecords = _captureHandler.resetLogLevel(Level.INFO);

		_luceneHelperImpl.loadIndexesFromCluster(_COMPANY_ID);

		Assert.assertTrue(logRecords.isEmpty());

		// Test 8, no need to load from cluster

		_mockClusterExecutor.reset();

		_mockClusterExecutor.setNodeNumber(1);

		logRecords = _captureHandler.resetLogLevel(Level.FINE);

		_luceneHelperImpl.loadIndexesFromCluster(_COMPANY_ID);

		Assert.assertEquals(1, logRecords.size());

		_assertLogger(
			logRecords.get(0),
			"Do not load indexes because there is either one portal " +
				"instance or no portal instances in the cluster",
			null);
	}

	@Rule
	public final AspectJNewEnvMethodRule aspectJNewEnvMethodRule =
		new AspectJNewEnvMethodRule();

	@Aspect
	public static class DisableClusterLinkAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.CLUSTER_LINK_ENABLED)")
		public Object disableClusterLink(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.FALSE});
		}

	}

	@Aspect
	public static class DisableIndexOnStartUpAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.INDEX_ON_STARTUP)")
		public Object disableIndexOnStartUp(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.FALSE});
		}

	}

	@Aspect
	public static class EnableClusterLinkAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.CLUSTER_LINK_ENABLED)")
		public Object enableClusterLink(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.TRUE});
		}

	}

	@Aspect
	public static class EnableLuceneReplicateWriteAdvice {

		@Around(
			"set(* com.liferay.portal.util.PropsValues.LUCENE_REPLICATE_WRITE)")
		public Object enableLuceneReplicateWrite(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.TRUE});
		}

	}

	@Aspect
	public static class LuceneClusterUtilAdvice {

		public static long getCompanyId() {
			long companyId = _companyId;

			_companyId = Long.MAX_VALUE;

			return companyId;
		}

		public static void setException(Exception exception) {
			_exception = exception;
		}

		@Around(
			"execution(* com.liferay.portal.search.lucene.cluster." +
				"LuceneClusterUtil.loadIndexesFromCluster(long))")
		public void loadIndexesFromCluster(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			if (_exception != null) {
				throw _exception;
			}

			Object[] arguments = proceedingJoinPoint.getArgs();

			_companyId = (long)arguments[0];
		}

		private static long _companyId;
		private static Exception _exception;

	}

	@Aspect
	public static class SkipGetBootupClusterNodeObjectValuePairAdvice {

		public static void setURL(URL url) {
			_url = url;
		}

		@Around(
			"execution(* com.liferay.portal.search.lucene.LuceneHelperImpl." +
				"_getBootupClusterNodeObjectValuePair(..))")
		public Object _getBootupClusterNodeObjectValuePair() {
			return new ObjectValuePair<String, URL>(StringPool.BLANK, _url);
		}

		private static URL _url;

	}

	@Aspect
	public static class SkipGetLoadIndexesInputStreamFromClusterAdvice {

		@Around(
			"execution(* com.liferay.portal.search.lucene.LuceneHelperImpl." +
				"getLoadIndexesInputStreamFromCluster(" +
					"long, com.liferay.portal.kernel.cluster.Address)) && " +
						"args(companyId, bootupAddress)")
		public Object getLoadIndexesInputStreamFromCluster(
			long companyId, Address bootupAddress) {

			_companyId = companyId;
			_bootupAddress = bootupAddress;

			return new UnsyncByteArrayInputStream(_RESPONSE_MESSAGE);
		}

		private static Address _bootupAddress;
		private static long _companyId;

	}

	private void _assertLogger(
		LogRecord logRecord, String message, Class<?> exceptionClass) {

		Assert.assertTrue(logRecord.getMessage().contains(message));

		if (exceptionClass == null) {
			Assert.assertNull(logRecord.getThrown());
		}
		else {
			Throwable throwable = logRecord.getThrown();

			Assert.assertEquals(exceptionClass, throwable.getClass());
		}
	}

	private void _fireClusterEventListeners(ClusterEvent clusterEvent) {
		ClusterExecutor clusterExecutor =
			ClusterExecutorUtil.getClusterExecutor();

		List<ClusterEventListener> clusterEventListeners =
			clusterExecutor.getClusterEventListeners();

		for (
			ClusterEventListener clusterEventListener : clusterEventListeners) {

			clusterEventListener.processClusterEvent(clusterEvent);
		}
	}

	private static final String _CLUSER_NODE_ID = "12345";

	private static final long _COMPANY_ID = 1;

	private static final long _LAST_GENERATION = 1;

	private static final byte[] _RESPONSE_MESSAGE =
		"Response Message".getBytes();

	private CaptureHandler _captureHandler;
	private ClusterNode _clusterNode;
	private InetAddress _localhostInetAddress;
	private LuceneHelperImpl _luceneHelperImpl;
	private MockClusterExecutor _mockClusterExecutor;
	private MockIndexAccessor _mockIndexAccessor;

	private static class MockURLConnection extends URLConnection {

		public MockURLConnection(URL url) {
			super(url);
		}

		public void assertOutputContent(String outputContent) {
			Assert.assertEquals(
				outputContent, _unsyncByteArrayOutputStream.toString());
		}

		@Override
		public void connect() {
		}

		@Override
		public InputStream getInputStream() {
			return new UnsyncByteArrayInputStream(_RESPONSE_MESSAGE);
		}

		@Override
		public OutputStream getOutputStream() {
			return _unsyncByteArrayOutputStream;
		}

		private final UnsyncByteArrayOutputStream _unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

	}

	private class MockAddress implements org.jgroups.Address {

		@Override
		public int compareTo(org.jgroups.Address jGroupsAddress) {
			return 0;
		}

		@Override
		public void readExternal(ObjectInput objectInput) {
		}

		@Override
		public void readFrom(DataInput dataInput) {
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public void writeExternal(ObjectOutput objectOutput) {
		}

		@Override
		public void writeTo(DataOutput dataOutput) {
		}

	}

	private class MockBlockingQueue<E> extends LinkedBlockingQueue<E> {

		public MockBlockingQueue(BlockingQueue<E> blockingQueue) {
			_blockingQueue = blockingQueue;
		}

		@Override
		public E poll(long timeout, TimeUnit unit) throws InterruptedException {
			return _blockingQueue.poll(1000, TimeUnit.MILLISECONDS);
		}

		private BlockingQueue<E> _blockingQueue;

	}

	private class MockClusterExecutor implements ClusterExecutor {

		@Override
		public void addClusterEventListener(
			ClusterEventListener clusterEventListener) {

			_clusterEventListeners.add(clusterEventListener);
		}

		@Override
		public void destroy() {
			_addresses.clear();
			_clusterEventListeners.clear();
		}

		@Override
		public FutureClusterResponses execute(ClusterRequest clusterRequest) {
			if (!_autoResponse) {
				return new FutureClusterResponses(
					Collections.<Address>emptyList());
			}

			FutureClusterResponses futureClusterResponses =
				new FutureClusterResponses(_addresses);

			for (Address address : _addresses) {
				ClusterNodeResponse clusterNodeResponse =
					new ClusterNodeResponse();

				clusterNodeResponse.setAddress(address);
				clusterNodeResponse.setClusterMessageType(
					ClusterMessageType.EXECUTE);
				clusterNodeResponse.setMulticast(clusterRequest.isMulticast());
				clusterNodeResponse.setUuid(clusterRequest.getUuid());

				ClusterNode clusterNode = new ClusterNode(
					String.valueOf(System.currentTimeMillis()),
					_localhostInetAddress);

				try {
					clusterNode.setPortalInetSocketAddress(
						new InetSocketAddress(_portalInetAddress, _port));
				}
				catch (IllegalArgumentException iae) {
				}

				clusterNodeResponse.setClusterNode(clusterNode);

				try {
					clusterNodeResponse.setResult(
						_invoke(clusterRequest.getMethodHandler()));
				}
				catch (Exception e) {
					clusterNodeResponse.setException(e);
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

			FutureClusterResponses futureClusterResponses = execute(
				clusterRequest);

			try {
				BlockingQueue<ClusterNodeResponse> blockingQueue =
					futureClusterResponses.get().getClusterResponses();

				MockBlockingQueue<ClusterNodeResponse> mockBlockingQueue =
					new MockBlockingQueue<ClusterNodeResponse>(blockingQueue);

				clusterResponseCallback.callback(mockBlockingQueue);
			}
			catch (InterruptedException ie) {
				throw new RuntimeException(ie);
			}

			return futureClusterResponses;
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
			return _addresses.get(0);
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
			return PropsValues.CLUSTER_LINK_ENABLED;
		}

		@Override
		public void removeClusterEventListener(
			ClusterEventListener clusterEventListener) {

			_clusterEventListeners.remove(clusterEventListener);
		}

		public void reset() {
			_addresses.clear();
			_autoResponse = true;
			_invokeMethodThrowException = false;
			_port = -1;
			_portalInetAddress = null;
		}

		public void setAutoResponse(boolean autoResponse) {
			_autoResponse = autoResponse;
		}

		public void setInvokeMethodThrowException(
			boolean invokeMethodThrowException) {

			_invokeMethodThrowException = invokeMethodThrowException;
		}

		public void setNodeNumber(int nodeNumber) {
			_addresses.clear();

			for (int i = 0; i < nodeNumber; i++) {
				_addresses.add(new AddressImpl(new MockAddress()));
			}
		}

		public void setPort(int port) {
			_port = port;
		}

		public void setPortalInetAddress(InetAddress portalInetAddress) {
			_portalInetAddress = portalInetAddress;
		}

		private Object _invoke(MethodHandler methodHandler) throws Exception {
			if (_invokeMethodThrowException) {
				throw new Exception();
			}

			MethodKey methodKey = methodHandler.getMethodKey();

			if (methodKey.equals(_createTokenMethodKey)) {
				long timeToLive = (Long)methodHandler.getArguments()[0];

				return TransientTokenUtil.createToken(timeToLive);
			}
			else if (methodKey.equals(_getLastGenerationMethodKey)) {
				return _LAST_GENERATION + 1;
			}

			return null;
		}

		private List<Address> _addresses = new ArrayList<Address>();
		private boolean _autoResponse = true;
		private final List<ClusterEventListener> _clusterEventListeners =
			new ArrayList<ClusterEventListener>();
		private final MethodKey _createTokenMethodKey = new MethodKey(
			TransientTokenUtil.class, "createToken", long.class);
		private final MethodKey _getLastGenerationMethodKey = new MethodKey(
			LuceneHelperUtil.class, "getLastGeneration", long.class);
		private boolean _invokeMethodThrowException = false;
		private int _port = -1;
		private InetAddress _portalInetAddress;

	}

	private class MockIndexAccessor implements IndexAccessor {

		@Override
		public IndexSearcher acquireIndexSearcher() {
			return null;
		}

		@Override
		public void addDocument(Document document) {
		}

		@Override
		public void addDocuments(Collection<Document> documents) {
		}

		@Override
		public void close() {
		}

		@Override
		public void delete() {
		}

		@Override
		public void deleteDocuments(Term term) {
		}

		@Override
		public void dumpIndex(OutputStream outputStream) {
		}

		@Override
		public long getCompanyId() {
			return _COMPANY_ID;
		}

		@Override
		public long getLastGeneration() {
			return _LAST_GENERATION;
		}

		@Override
		public Directory getLuceneDir() {
			return null;
		}

		public byte[] getResponseMessage() {
			return _bytes;
		}

		@Override
		public void invalidate() {
		}

		@Override
		public void loadIndex(InputStream inputStream) throws IOException {
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			StreamUtil.transfer(inputStream, unsyncByteArrayOutputStream);

			_bytes = unsyncByteArrayOutputStream.toByteArray();
		}

		@Override
		public void releaseIndexSearcher(IndexSearcher indexSearcher) {
		}

		@Override
		public void updateDocument(Term term, Document document) {
		}

		private byte[] _bytes;

	}

}