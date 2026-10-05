/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.persistent.resource;

import com.liferay.jenkins.results.parser.BuildDatabase;
import com.liferay.jenkins.results.parser.JenkinsAPIUtil;
import com.liferay.jenkins.results.parser.JenkinsCohort;
import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.JenkinsStopBuildUtil;
import com.liferay.jenkins.results.parser.RandomTestUtil;
import com.liferay.jenkins.results.parser.ReflectionTestUtil;

import java.io.IOException;

import java.lang.reflect.Method;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.json.JSONArray;
import org.json.JSONObject;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

/**
 * @author Michael Hashimoto
 */
public class BaseBundlePersistentResourceTest
	extends com.liferay.jenkins.results.parser.Test {

	@Test
	public void testSetStatus() {
		BaseBundlePersistentResource baseBundlePersistentResource =
			_getBaseBundlePersistentResource(
				Mockito.mock(JenkinsMaster.class), RandomTestUtil.randomLong());

		String why = RandomTestUtil.randomString();

		ReflectionTestUtil.setFieldValue(
			baseBundlePersistentResource, "_queueItemWhy", why);

		baseBundlePersistentResource.setStatus(
			PersistentResource.Status.IN_QUEUE);

		String statusMessage = baseBundlePersistentResource.getStatusMessage();

		Assert.assertTrue(statusMessage, statusMessage.endsWith(": " + why));

		baseBundlePersistentResource.setStatus(
			PersistentResource.Status.IN_PROGRESS);
		baseBundlePersistentResource.setStatus(
			PersistentResource.Status.IN_QUEUE);

		statusMessage = baseBundlePersistentResource.getStatusMessage();

		Assert.assertFalse(statusMessage, statusMessage.contains(why));
	}

	@Test
	public void testStart() {
		long queueId = _getQueueId();

		_testStart(
			queueId, PersistentResource.Status.IN_QUEUE, invocation -> queueId);

		_testStart(0, PersistentResource.Status.NOT_STARTED, invocation -> 0L);
		_testStart(
			0, PersistentResource.Status.NOT_STARTED,
			invocation -> {
				throw new RuntimeException(RandomTestUtil.randomString());
			});
	}

	@Test
	public void testUpdate() throws Exception {
		BaseBundlePersistentResource baseBundlePersistentResource =
			_getBaseBundlePersistentResource(
				Mockito.mock(JenkinsMaster.class), 0);

		AtomicReference<JSONObject> dataJSONObjectAtomicReference =
			new AtomicReference<>();

		Mockito.doAnswer(
			invocation -> dataJSONObjectAtomicReference.get()
		).when(
			baseBundlePersistentResource
		).getDataJSONObject();

		AtomicInteger startCount = new AtomicInteger();

		Mockito.doAnswer(
			invocation -> {
				startCount.incrementAndGet();

				Thread.sleep(500);

				dataJSONObjectAtomicReference.set(new JSONObject());

				return null;
			}
		).when(
			baseBundlePersistentResource
		).start();

		CountDownLatch countDownLatch = new CountDownLatch(1);
		AtomicReference<Throwable> throwableAtomicReference =
			new AtomicReference<>();

		Thread[] threads = new Thread[2];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(
				() -> {
					try {
						countDownLatch.await();

						baseBundlePersistentResource.update();
					}
					catch (Throwable throwable) {
						throwableAtomicReference.set(throwable);
					}
				});

			threads[i].start();
		}

		countDownLatch.countDown();

		for (Thread thread : threads) {
			thread.join();
		}

		Assert.assertNull(
			String.valueOf(throwableAtomicReference.get()),
			throwableAtomicReference.get());

		Assert.assertEquals(1, startCount.get());
	}

	@Test
	public void testUpdateCancelledQueueItem() throws Exception {
		JenkinsMaster jenkinsMaster = Mockito.mock(JenkinsMaster.class);
		long queueId = RandomTestUtil.randomLong();

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getBaseBundlePersistentResource(jenkinsMaster, queueId);

		JenkinsMaster.QueueItem queueItem = Mockito.mock(
			JenkinsMaster.QueueItem.class);

		Mockito.doReturn(
			true
		).when(
			queueItem
		).isCancelled();

		Mockito.doReturn(
			queueItem
		).when(
			jenkinsMaster
		).fetchQueueItem(
			queueId
		);

		baseBundlePersistentResource.update();

		Mockito.verify(
			baseBundlePersistentResource
		).start();

		for (int i = 0; i < 9; i++) {
			baseBundlePersistentResource.update();
		}

		Mockito.verify(
			baseBundlePersistentResource, Mockito.times(10)
		).start();

		Mockito.verify(
			baseBundlePersistentResource, Mockito.never()
		).setStatus(
			PersistentResource.Status.FAILED
		);

		baseBundlePersistentResource.update();

		Mockito.verify(
			baseBundlePersistentResource, Mockito.times(10)
		).start();

		Mockito.verify(
			baseBundlePersistentResource
		).setStatus(
			PersistentResource.Status.FAILED
		);

		Mockito.verify(
			baseBundlePersistentResource
		).save();
	}

	@Test
	public void testUpdateControllerHandover() {
		_testUpdateControllerHandover(
			true, RandomTestUtil.randomString(),
			PersistentResource.Status.IN_QUEUE);
		_testUpdateControllerHandover(
			false, RandomTestUtil.randomString(),
			PersistentResource.Status.IN_PROGRESS);
		_testUpdateControllerHandover(
			false, "", PersistentResource.Status.IN_QUEUE);
	}

	@Test
	public void testUpdateFailedInvocation() {
		JenkinsMaster jenkinsMaster = Mockito.mock(JenkinsMaster.class);

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getBaseBundlePersistentResource(jenkinsMaster, 0);

		_setUpInvocation(baseBundlePersistentResource, jenkinsMaster);

		ReflectionTestUtil.setFieldValue(
			baseBundlePersistentResource, "_status",
			PersistentResource.Status.NOT_STARTED);

		AtomicInteger invocationCount = new AtomicInteger();
		AtomicLong queueIdAtomicLong = new AtomicLong();

		try (MockedStatic<JenkinsResultsParserUtil>
				jenkinsResultsParserUtilMockedStatic =
					_mockJenkinsResultsParserUtil(
						Collections.singletonMap(
							"invokeJenkinsBuild",
							invocation -> {
								invocationCount.incrementAndGet();

								return queueIdAtomicLong.get();
							}))) {

			_update(baseBundlePersistentResource, 5);

			Assert.assertEquals(5, invocationCount.get());
			Assert.assertEquals(
				PersistentResource.Status.NOT_STARTED,
				baseBundlePersistentResource.getStatus());

			baseBundlePersistentResource.update();

			Assert.assertEquals(5, invocationCount.get());
			Assert.assertEquals(
				PersistentResource.Status.FAILED,
				baseBundlePersistentResource.getStatus());

			Mockito.verify(
				baseBundlePersistentResource
			).print(
				"No invocation attempts remaining"
			);

			baseBundlePersistentResource = _getBaseBundlePersistentResource(
				jenkinsMaster, 0);

			_setUpInvocation(baseBundlePersistentResource, jenkinsMaster);

			ReflectionTestUtil.setFieldValue(
				baseBundlePersistentResource, "_status",
				PersistentResource.Status.NOT_STARTED);

			_update(baseBundlePersistentResource, 4);

			queueIdAtomicLong.set(_getQueueId());

			baseBundlePersistentResource.update();

			Assert.assertEquals(
				PersistentResource.Status.IN_QUEUE,
				baseBundlePersistentResource.getStatus());

			queueIdAtomicLong.set(0);

			baseBundlePersistentResource.start();

			_update(baseBundlePersistentResource, 5);

			Assert.assertEquals(
				PersistentResource.Status.NOT_STARTED,
				baseBundlePersistentResource.getStatus());
		}
	}

	@Test
	public void testUpdateFollowerControllerFinished() {
		for (PersistentResource.Status status :
				new PersistentResource.Status[] {
					PersistentResource.Status.IN_PROGRESS,
					PersistentResource.Status.IN_QUEUE,
					PersistentResource.Status.NOT_STARTED
				}) {

			_testUpdateFollowerControllerFinished(true, 0, status);
			_testUpdateFollowerControllerFinished(true, 1, status);
			_testUpdateFollowerControllerFinished(false, 2, status);
		}

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getFollowerBaseBundlePersistentResource(
				_getDataJSONObject(0, PersistentResource.Status.IN_PROGRESS));

		try (MockedStatic<JenkinsAPIUtil> jenkinsAPIUtilMockedStatic =
				_mockJenkinsAPIUtil("")) {

			baseBundlePersistentResource.update();
		}

		Assert.assertEquals(
			PersistentResource.Status.IN_PROGRESS,
			baseBundlePersistentResource.getStatus());

		Mockito.verify(
			baseBundlePersistentResource, Mockito.never()
		).save();
	}

	@Test
	public void testUpdateFollowerFailed() {
		_testUpdateFollowerFailed(true, 0);
		_testUpdateFollowerFailed(true, 1);
		_testUpdateFollowerFailed(false, 2);
	}

	@Test
	public void testUpdateFollowerMissingArtifacts() {
		BaseBundlePersistentResource baseBundlePersistentResource =
			_getFollowerBaseBundlePersistentResource(
				_getDataJSONObject(0, PersistentResource.Status.SUCCESS));

		try (MockedStatic<JenkinsAPIUtil> jenkinsAPIUtilMockedStatic =
				_mockJenkinsAPIUtil("FAILURE");
			MockedStatic<JenkinsResultsParserUtil>
				jenkinsResultsParserUtilMockedStatic =
					_mockJenkinsResultsParserUtil(
						Collections.<String, Answer<Object>>emptyMap())) {

			baseBundlePersistentResource.update();

			Assert.assertEquals(
				PersistentResource.Status.IN_PROGRESS,
				baseBundlePersistentResource.getStatus());

			_setArtifactsAvailable(baseBundlePersistentResource, true);

			baseBundlePersistentResource.update();

			_setArtifactsAvailable(baseBundlePersistentResource, false);

			baseBundlePersistentResource.update();

			Mockito.verify(
				baseBundlePersistentResource, Mockito.never()
			).save();

			baseBundlePersistentResource.update();

			Mockito.verify(
				baseBundlePersistentResource
			).save();
		}

		baseBundlePersistentResource = _getFollowerBaseBundlePersistentResource(
			_getDataJSONObject(2, PersistentResource.Status.SUCCESS));

		try (MockedStatic<JenkinsAPIUtil> jenkinsAPIUtilMockedStatic =
				_mockJenkinsAPIUtil("FAILURE")) {

			_update(baseBundlePersistentResource, 2);
		}

		Assert.assertEquals(
			PersistentResource.Status.FAILED,
			baseBundlePersistentResource.getStatus());

		Mockito.verify(
			baseBundlePersistentResource, Mockito.never()
		).save();

		baseBundlePersistentResource = _getFollowerBaseBundlePersistentResource(
			_getDataJSONObject(0, PersistentResource.Status.SUCCESS));

		try (MockedStatic<JenkinsAPIUtil> jenkinsAPIUtilMockedStatic =
				_mockJenkinsAPIUtil("")) {

			_update(baseBundlePersistentResource, 3);
		}

		Assert.assertEquals(
			PersistentResource.Status.SUCCESS,
			baseBundlePersistentResource.getStatus());

		Mockito.verify(
			baseBundlePersistentResource, Mockito.never()
		).save();

		Mockito.verify(
			baseBundlePersistentResource, Mockito.times(3)
		).touch();
	}

	@Test
	public void testUpdateFollowerSuccess() {
		BaseBundlePersistentResource baseBundlePersistentResource =
			_getFollowerBaseBundlePersistentResource(
				_getDataJSONObject(0, PersistentResource.Status.SUCCESS));

		_setArtifactsAvailable(baseBundlePersistentResource, true);

		baseBundlePersistentResource.update();

		Assert.assertEquals(
			PersistentResource.Status.SUCCESS,
			baseBundlePersistentResource.getStatus());

		Mockito.verify(
			baseBundlePersistentResource
		).touch();

		Mockito.verify(
			baseBundlePersistentResource, Mockito.never()
		).save();
	}

	@Test
	public void testUpdateFoundBuild() {
		JenkinsMaster jenkinsMaster = Mockito.mock(JenkinsMaster.class);

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getBaseBundlePersistentResource(jenkinsMaster, _getQueueId());

		String buildURL =
			"https://" + RandomTestUtil.randomString() + "/job/" +
				RandomTestUtil.randomString() + "/1/";

		try (MockedStatic<JenkinsResultsParserUtil>
				jenkinsResultsParserUtilMockedStatic =
					_mockJenkinsResultsParserUtil(
						Collections.singletonMap(
							"fetchBuildURL", invocation -> buildURL))) {

			baseBundlePersistentResource.update();
		}

		Assert.assertEquals(
			buildURL, baseBundlePersistentResource.getProducerBuildURL());
		Assert.assertEquals(
			PersistentResource.Status.IN_PROGRESS,
			baseBundlePersistentResource.getStatus());

		Mockito.verify(
			baseBundlePersistentResource
		).save();
	}

	@Test
	public void testUpdateInQueue() throws Exception {
		JenkinsMaster jenkinsMaster = Mockito.mock(JenkinsMaster.class);
		long queueId = RandomTestUtil.randomLong();

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getBaseBundlePersistentResource(jenkinsMaster, queueId);

		JenkinsMaster.QueueItem queueItem = Mockito.mock(
			JenkinsMaster.QueueItem.class);

		Mockito.doReturn(
			queueId
		).when(
			queueItem
		).getId();

		long currentTimeMillis = System.currentTimeMillis();

		Mockito.doReturn(
			currentTimeMillis - (1000 * 60 * 30)
		).when(
			queueItem
		).getInQueueSince();

		Mockito.doReturn(
			jenkinsMaster
		).when(
			queueItem
		).getJenkinsMaster();

		String why = RandomTestUtil.randomString();

		Mockito.doReturn(
			why
		).when(
			queueItem
		).getWhy();

		Mockito.doReturn(
			Collections.singletonList(queueItem)
		).when(
			jenkinsMaster
		).getQueueItems();

		try (MockedStatic<JenkinsResultsParserUtil>
				jenkinsResultsParserUtilMockedStatic = Mockito.mockStatic(
					JenkinsResultsParserUtil.class, Mockito.CALLS_REAL_METHODS);
			MockedStatic<JenkinsStopBuildUtil>
				jenkinsStopBuildUtilMockedStatic = Mockito.mockStatic(
					JenkinsStopBuildUtil.class)) {

			jenkinsResultsParserUtilMockedStatic.when(
				() -> JenkinsResultsParserUtil.combine(Mockito.<String[]>any())
			).thenAnswer(
				invocation -> {
					StringBuilder sb = new StringBuilder();

					for (Object argument : invocation.getArguments()) {
						sb.append(argument);
					}

					return sb.toString();
				}
			);

			jenkinsResultsParserUtilMockedStatic.when(
				JenkinsResultsParserUtil::getCurrentTimeMillis
			).thenReturn(
				currentTimeMillis
			);

			baseBundlePersistentResource.update();

			String statusMessage =
				baseBundlePersistentResource.getStatusMessage();

			Assert.assertTrue(
				statusMessage, statusMessage.endsWith(": " + why));

			Mockito.verify(
				baseBundlePersistentResource, Mockito.never()
			).start();

			Mockito.doReturn(
				currentTimeMillis - (1000 * 60 * 30) - 1
			).when(
				queueItem
			).getInQueueSince();

			for (int i = 0; i < 3; i++) {
				baseBundlePersistentResource.update();
			}

			jenkinsStopBuildUtilMockedStatic.verify(
				() -> JenkinsStopBuildUtil.cancelQueueItem(
					jenkinsMaster, queueId),
				Mockito.times(2));

			Mockito.verify(
				baseBundlePersistentResource, Mockito.times(2)
			).start();
		}
	}

	@Test
	public void testUpdateLookupFailure() throws Exception {
		JenkinsMaster jenkinsMaster = Mockito.mock(JenkinsMaster.class);
		long queueId = _getQueueId();

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getBaseBundlePersistentResource(jenkinsMaster, queueId);

		AtomicReference<IOException> ioExceptionAtomicReference =
			new AtomicReference<>();

		try (MockedStatic<JenkinsResultsParserUtil>
				jenkinsResultsParserUtilMockedStatic =
					_mockJenkinsResultsParserUtil(
						Collections.singletonMap(
							"fetchBuildURL",
							invocation -> {
								IOException ioException =
									ioExceptionAtomicReference.get();

								if (ioException != null) {
									throw ioException;
								}

								return null;
							}))) {

			Mockito.doThrow(
				new RuntimeException(RandomTestUtil.randomString())
			).when(
				jenkinsMaster
			).getQueueItems();

			_update(baseBundlePersistentResource, 3);

			Mockito.doReturn(
				Collections.emptyList()
			).when(
				jenkinsMaster
			).getQueueItems();

			Mockito.doThrow(
				new IOException(RandomTestUtil.randomString())
			).when(
				jenkinsMaster
			).fetchQueueItem(
				queueId
			);

			_update(baseBundlePersistentResource, 3);

			Mockito.doReturn(
				null
			).when(
				jenkinsMaster
			).fetchQueueItem(
				queueId
			);

			ioExceptionAtomicReference.set(
				new IOException(RandomTestUtil.randomString()));

			_update(baseBundlePersistentResource, 3);

			Assert.assertEquals(
				PersistentResource.Status.IN_QUEUE,
				baseBundlePersistentResource.getStatus());

			Mockito.verify(
				baseBundlePersistentResource
			).print(
				Mockito.contains("(9 of 10)")
			);

			Mockito.verify(
				baseBundlePersistentResource, Mockito.never()
			).print(
				"WARNING: Unable to find queue item"
			);

			ioExceptionAtomicReference.set(null);

			baseBundlePersistentResource.update();

			ioExceptionAtomicReference.set(
				new IOException(RandomTestUtil.randomString()));

			_update(baseBundlePersistentResource, 9);

			Assert.assertEquals(
				PersistentResource.Status.IN_QUEUE,
				baseBundlePersistentResource.getStatus());

			Mockito.verify(
				baseBundlePersistentResource, Mockito.never()
			).save();

			baseBundlePersistentResource.update();

			Assert.assertEquals(
				PersistentResource.Status.FAILED,
				baseBundlePersistentResource.getStatus());

			Mockito.verify(
				baseBundlePersistentResource
			).print(
				"No lookup attempts remaining"
			);

			Mockito.verify(
				baseBundlePersistentResource
			).save();
		}
	}

	@Test
	public void testUpdateMissingQueueItem() {
		JenkinsMaster jenkinsMaster = Mockito.mock(JenkinsMaster.class);

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getBaseBundlePersistentResource(jenkinsMaster, -1);

		baseBundlePersistentResource.update();

		Mockito.verify(
			baseBundlePersistentResource, Mockito.never()
		).start();

		for (int i = 0; i < 4; i++) {
			baseBundlePersistentResource.update();
		}

		Mockito.verify(
			baseBundlePersistentResource, Mockito.times(2)
		).start();

		Mockito.verify(
			baseBundlePersistentResource, Mockito.never()
		).setStatus(
			PersistentResource.Status.FAILED
		);

		baseBundlePersistentResource.update();

		Mockito.verify(
			baseBundlePersistentResource, Mockito.times(2)
		).start();

		Mockito.verify(
			baseBundlePersistentResource
		).setStatus(
			PersistentResource.Status.FAILED
		);

		Mockito.verify(
			baseBundlePersistentResource
		).save();
	}

	@Test
	public void testUpdateMissingQueueItemReset() throws Exception {
		JenkinsMaster jenkinsMaster = Mockito.mock(JenkinsMaster.class);
		long queueId = _getQueueId();

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getBaseBundlePersistentResource(jenkinsMaster, queueId);

		JenkinsMaster.QueueItem queueItem = Mockito.mock(
			JenkinsMaster.QueueItem.class);

		Mockito.doReturn(
			queueId
		).when(
			queueItem
		).getId();

		Mockito.doReturn(
			System.currentTimeMillis()
		).when(
			queueItem
		).getInQueueSince();

		try (MockedStatic<JenkinsResultsParserUtil>
				jenkinsResultsParserUtilMockedStatic =
					_mockJenkinsResultsParserUtil(
						Collections.<String, Answer<Object>>emptyMap())) {

			baseBundlePersistentResource.update();

			Mockito.doReturn(
				Collections.singletonList(queueItem)
			).when(
				jenkinsMaster
			).getQueueItems();

			baseBundlePersistentResource.update();

			Mockito.doReturn(
				Collections.emptyList()
			).when(
				jenkinsMaster
			).getQueueItems();

			baseBundlePersistentResource.update();

			Mockito.doReturn(
				queueItem
			).when(
				jenkinsMaster
			).fetchQueueItem(
				queueId
			);

			baseBundlePersistentResource.update();

			Mockito.doReturn(
				null
			).when(
				jenkinsMaster
			).fetchQueueItem(
				queueId
			);

			baseBundlePersistentResource.update();

			Mockito.verify(
				baseBundlePersistentResource, Mockito.never()
			).start();

			baseBundlePersistentResource.update();

			Mockito.verify(
				baseBundlePersistentResource
			).start();
		}
	}

	@Test
	public void testUpdateRedispatch() {
		_testUpdateRedispatch(
			"Redispatching bundles (2 of 2)",
			PersistentResource.Status.IN_QUEUE, _getQueueId());
		_testUpdateRedispatch(
			"WARNING: Unable to redispatch bundles (2 of 2)",
			PersistentResource.Status.NOT_STARTED, 0);
	}

	@Test
	public void testUpdateStartedQueueItem() throws Exception {
		JenkinsMaster jenkinsMaster = Mockito.mock(JenkinsMaster.class);
		long queueId = RandomTestUtil.randomLong();

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getBaseBundlePersistentResource(jenkinsMaster, queueId);

		JenkinsMaster.QueueItem queueItem = Mockito.mock(
			JenkinsMaster.QueueItem.class);

		String executableURL = "https://" + RandomTestUtil.randomString();

		Mockito.doReturn(
			executableURL
		).when(
			queueItem
		).getExecutableURL();

		Mockito.doReturn(
			queueItem
		).when(
			jenkinsMaster
		).fetchQueueItem(
			queueId
		);

		baseBundlePersistentResource.update();

		Mockito.verify(
			baseBundlePersistentResource
		).setProducerBuildURL(
			executableURL
		);

		Mockito.verify(
			baseBundlePersistentResource
		).setStatus(
			PersistentResource.Status.IN_PROGRESS
		);

		Mockito.verify(
			baseBundlePersistentResource, Mockito.never()
		).start();
	}

	@Test
	public void testUpdateTransientFailure() {
		BaseBundlePersistentResource baseBundlePersistentResource =
			_getBaseBundlePersistentResource(
				Mockito.mock(JenkinsMaster.class), _getQueueId());

		ReflectionTestUtil.setFieldValue(
			baseBundlePersistentResource, "_status",
			PersistentResource.Status.NOT_STARTED);

		_update(baseBundlePersistentResource, 2);

		Mockito.verify(
			baseBundlePersistentResource, Mockito.times(2)
		).start();

		Assert.assertEquals(
			PersistentResource.Status.NOT_STARTED,
			baseBundlePersistentResource.getStatus());

		baseBundlePersistentResource.update();

		Mockito.verify(
			baseBundlePersistentResource, Mockito.times(2)
		).start();

		Assert.assertEquals(
			PersistentResource.Status.FAILED,
			baseBundlePersistentResource.getStatus());

		Mockito.verify(
			baseBundlePersistentResource
		).save();
	}

	@Test
	public void testUpdateWaitingQueueItem() throws Exception {
		JenkinsMaster jenkinsMaster = Mockito.mock(JenkinsMaster.class);
		long queueId = _getQueueId();

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getBaseBundlePersistentResource(jenkinsMaster, queueId);

		JenkinsMaster.QueueItem queueItem = Mockito.mock(
			JenkinsMaster.QueueItem.class);

		String why = RandomTestUtil.randomString();

		Mockito.doReturn(
			why
		).when(
			queueItem
		).getWhy();

		Mockito.doReturn(
			queueItem
		).when(
			jenkinsMaster
		).fetchQueueItem(
			queueId
		);

		_update(baseBundlePersistentResource, 3);

		Assert.assertEquals(
			PersistentResource.Status.IN_QUEUE,
			baseBundlePersistentResource.getStatus());

		String statusMessage = baseBundlePersistentResource.getStatusMessage();

		Assert.assertTrue(statusMessage, statusMessage.endsWith(": " + why));

		Mockito.verify(
			baseBundlePersistentResource, Mockito.never()
		).print(
			"WARNING: Unable to find queue item"
		);

		Mockito.verify(
			baseBundlePersistentResource, Mockito.never()
		).start();
	}

	private BaseBundlePersistentResource _getBaseBundlePersistentResource(
		JenkinsMaster jenkinsMaster, long queueId) {

		BaseBundlePersistentResource baseBundlePersistentResource =
			Mockito.mock(BaseBundlePersistentResource.class);

		Mockito.doCallRealMethod(
		).when(
			baseBundlePersistentResource
		).getControllerBuildURL();

		Mockito.doCallRealMethod(
		).when(
			baseBundlePersistentResource
		).getProducerBuildURL();

		Mockito.doCallRealMethod(
		).when(
			baseBundlePersistentResource
		).getProducerJenkinsMaster();

		Mockito.doCallRealMethod(
		).when(
			baseBundlePersistentResource
		).getProducerQueueId();

		Mockito.doCallRealMethod(
		).when(
			baseBundlePersistentResource
		).getStatus();

		Mockito.doCallRealMethod(
		).when(
			baseBundlePersistentResource
		).getStatusMessage();

		Mockito.doCallRealMethod(
		).when(
			baseBundlePersistentResource
		).isController();

		Mockito.doCallRealMethod(
		).when(
			baseBundlePersistentResource
		).setControllerBuildURL(
			Mockito.any()
		);

		Mockito.doCallRealMethod(
		).when(
			baseBundlePersistentResource
		).setProducerBuildURL(
			Mockito.any()
		);

		Mockito.doCallRealMethod(
		).when(
			baseBundlePersistentResource
		).setProducerJenkinsMaster(
			Mockito.any()
		);

		Mockito.doCallRealMethod(
		).when(
			baseBundlePersistentResource
		).setProducerQueueId(
			Mockito.anyLong()
		);

		Mockito.doCallRealMethod(
		).when(
			baseBundlePersistentResource
		).setStatus(
			Mockito.any()
		);

		Mockito.doCallRealMethod(
		).when(
			baseBundlePersistentResource
		).update();

		String currentTopLevelBuildURL =
			"https://" + RandomTestUtil.randomString() + "/job/" +
				RandomTestUtil.randomString() + "/1/";

		Mockito.doReturn(
			currentTopLevelBuildURL
		).when(
			baseBundlePersistentResource
		).getCurrentTopLevelBuildURL();

		Mockito.doReturn(
			new JSONObject()
		).when(
			baseBundlePersistentResource
		).getDataJSONObject();

		ReflectionTestUtil.setFieldValue(
			baseBundlePersistentResource, "_controllerBuildURL",
			currentTopLevelBuildURL);
		ReflectionTestUtil.setFieldValue(
			baseBundlePersistentResource, "_producerJenkinsMaster",
			jenkinsMaster);
		ReflectionTestUtil.setFieldValue(
			baseBundlePersistentResource, "_producerQueueId", queueId);
		ReflectionTestUtil.setFieldValue(
			baseBundlePersistentResource, "_status",
			PersistentResource.Status.IN_QUEUE);

		return baseBundlePersistentResource;
	}

	private JSONObject _getDataJSONObject(
		int redispatchAttempts, PersistentResource.Status status) {

		return new JSONObject(
		).put(
			"controller_build_url",
			"https://" + RandomTestUtil.randomString() + "/job/" +
				RandomTestUtil.randomString() + "/1/"
		).put(
			"redispatch_attempts", redispatchAttempts
		).put(
			"status", String.valueOf(status)
		);
	}

	private BaseBundlePersistentResource
		_getFollowerBaseBundlePersistentResource(JSONObject dataJSONObject) {

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getBaseBundlePersistentResource(
				Mockito.mock(JenkinsMaster.class), 0);

		Mockito.doReturn(
			dataJSONObject
		).when(
			baseBundlePersistentResource
		).getDataJSONObject();

		ReflectionTestUtil.setFieldValue(
			baseBundlePersistentResource, "_controllerBuildURL",
			dataJSONObject.getString("controller_build_url"));

		return baseBundlePersistentResource;
	}

	private long _getQueueId() {
		return Math.abs(RandomTestUtil.randomLong() % 1000000) + 1;
	}

	private MockedStatic<JenkinsAPIUtil> _mockJenkinsAPIUtil(String result) {
		return Mockito.mockStatic(
			JenkinsAPIUtil.class,
			invocation -> new JSONObject(
			).put(
				"result", result
			));
	}

	private MockedStatic<JenkinsResultsParserUtil>
		_mockJenkinsResultsParserUtil(Map<String, Answer<Object>> answers) {

		return Mockito.mockStatic(
			JenkinsResultsParserUtil.class,
			invocation -> {
				Method method = invocation.getMethod();

				String methodName = method.getName();

				Answer<Object> answer = answers.get(methodName);

				if (answer != null) {
					return answer.answer(invocation);
				}

				if (methodName.equals("fetchBuildURL") ||
					methodName.equals("sleep")) {

					return null;
				}

				if (methodName.equals("getBuildParameters")) {
					return new HashMap<>();
				}

				return invocation.callRealMethod();
			});
	}

	private void _setArtifactsAvailable(
		BaseBundlePersistentResource baseBundlePersistentResource,
		boolean artifactsAvailable) {

		Mockito.doReturn(
			artifactsAvailable
		).when(
			baseBundlePersistentResource
		).isArtifactsAvailable();
	}

	private void _setUpInvocation(
		BaseBundlePersistentResource baseBundlePersistentResource,
		JenkinsMaster jenkinsMaster) {

		JenkinsCohort jenkinsCohort = Mockito.mock(JenkinsCohort.class);

		Mockito.doReturn(
			jenkinsMaster
		).when(
			jenkinsCohort
		).getMostAvailableJenkinsMaster(
			Mockito.any(), Mockito.anyInt(), Mockito.anyString()
		);

		Mockito.doReturn(
			jenkinsCohort
		).when(
			jenkinsMaster
		).getJenkinsCohort();

		Mockito.doReturn(
			"https://" + RandomTestUtil.randomString() + "/"
		).when(
			jenkinsMaster
		).getRemoteURL();

		Mockito.doCallRealMethod(
		).when(
			baseBundlePersistentResource
		).start();

		Mockito.doReturn(
			Mockito.mock(BuildDatabase.class)
		).when(
			baseBundlePersistentResource
		).getBuildDatabase();

		Mockito.doReturn(
			new Properties()
		).when(
			baseBundlePersistentResource
		).getStartProperties();
	}

	private void _testStart(
		long expectedProducerQueueId, PersistentResource.Status expectedStatus,
		Answer<Object> invokeJenkinsBuildAnswer) {

		JenkinsMaster jenkinsMaster = Mockito.mock(JenkinsMaster.class);

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getBaseBundlePersistentResource(jenkinsMaster, 0);

		_setUpInvocation(baseBundlePersistentResource, jenkinsMaster);

		try (MockedStatic<JenkinsResultsParserUtil>
				jenkinsResultsParserUtilMockedStatic =
					_mockJenkinsResultsParserUtil(
						Collections.singletonMap(
							"invokeJenkinsBuild", invokeJenkinsBuildAnswer))) {

			baseBundlePersistentResource.start();
		}

		Assert.assertEquals(
			expectedProducerQueueId,
			baseBundlePersistentResource.getProducerQueueId());
		Assert.assertEquals(
			expectedStatus, baseBundlePersistentResource.getStatus());

		Mockito.verify(
			baseBundlePersistentResource
		).save();

		Mockito.verify(
			baseBundlePersistentResource,
			getVerificationMode(
				expectedStatus == PersistentResource.Status.IN_QUEUE)
		).print(
			Mockito.startsWith("Start building bundles at ")
		);
	}

	private void _testUpdateControllerHandover(
		boolean expectedCancelled, String otherControllerBuildURL,
		PersistentResource.Status status) {

		JenkinsMaster jenkinsMaster = Mockito.mock(JenkinsMaster.class);
		long queueId = _getQueueId();

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getBaseBundlePersistentResource(jenkinsMaster, queueId);

		String controllerBuildURL =
			baseBundlePersistentResource.getControllerBuildURL();

		Mockito.doReturn(
			new JSONObject(
			).put(
				"controller_build_url", otherControllerBuildURL
			)
		).when(
			baseBundlePersistentResource
		).getDataJSONObject();

		ReflectionTestUtil.setFieldValue(
			baseBundlePersistentResource, "_status", status);

		try (MockedStatic<JenkinsResultsParserUtil>
				jenkinsResultsParserUtilMockedStatic =
					_mockJenkinsResultsParserUtil(
						Collections.<String, Answer<Object>>emptyMap());
			MockedStatic<JenkinsStopBuildUtil>
				jenkinsStopBuildUtilMockedStatic = Mockito.mockStatic(
					JenkinsStopBuildUtil.class)) {

			baseBundlePersistentResource.update();

			jenkinsStopBuildUtilMockedStatic.verify(
				() -> JenkinsStopBuildUtil.cancelQueueItem(
					jenkinsMaster, queueId),
				getVerificationMode(expectedCancelled));
		}

		if (otherControllerBuildURL.isEmpty()) {
			Assert.assertEquals(
				controllerBuildURL,
				baseBundlePersistentResource.getControllerBuildURL());

			return;
		}

		Assert.assertEquals(
			otherControllerBuildURL,
			baseBundlePersistentResource.getControllerBuildURL());

		Assert.assertFalse(baseBundlePersistentResource.isController());
	}

	private void _testUpdateFollowerControllerFinished(
		boolean expectedRedispatched, int redispatchAttempts,
		PersistentResource.Status status) {

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getFollowerBaseBundlePersistentResource(
				_getDataJSONObject(redispatchAttempts, status));

		try (MockedStatic<JenkinsAPIUtil> jenkinsAPIUtilMockedStatic =
				_mockJenkinsAPIUtil("FAILURE");
			MockedStatic<JenkinsResultsParserUtil>
				jenkinsResultsParserUtilMockedStatic =
					_mockJenkinsResultsParserUtil(
						Collections.<String, Answer<Object>>emptyMap())) {

			baseBundlePersistentResource.update();
		}

		Mockito.verify(
			baseBundlePersistentResource,
			getVerificationMode(expectedRedispatched)
		).save();

		if (!expectedRedispatched) {
			Assert.assertEquals(
				PersistentResource.Status.FAILED,
				baseBundlePersistentResource.getStatus());
		}
	}

	private void _testUpdateFollowerFailed(
		boolean expectedRedispatched, int redispatchAttempts) {

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getFollowerBaseBundlePersistentResource(
				_getDataJSONObject(
					redispatchAttempts, PersistentResource.Status.FAILED));

		try (MockedStatic<JenkinsResultsParserUtil>
				jenkinsResultsParserUtilMockedStatic =
					_mockJenkinsResultsParserUtil(
						Collections.<String, Answer<Object>>emptyMap())) {

			baseBundlePersistentResource.update();
		}

		Mockito.verify(
			baseBundlePersistentResource,
			getVerificationMode(expectedRedispatched)
		).save();

		Mockito.verify(
			baseBundlePersistentResource,
			getVerificationMode(!expectedRedispatched)
		).print(
			"No redispatch attempts remaining"
		);
	}

	private void _testUpdateRedispatch(
		String expectedMessage, PersistentResource.Status expectedStatus,
		long queueId) {

		JSONArray redispatchHistoryJSONArray = new JSONArray();

		for (int i = 0; i < 2; i++) {
			redispatchHistoryJSONArray.put(
				new JSONObject(
				).put(
					"controller_build_url", RandomTestUtil.randomString()
				));
		}

		JSONObject dataJSONObject = _getDataJSONObject(
			1, PersistentResource.Status.FAILED);

		dataJSONObject.put("redispatch_history", redispatchHistoryJSONArray);

		BaseBundlePersistentResource baseBundlePersistentResource =
			_getFollowerBaseBundlePersistentResource(dataJSONObject);

		JenkinsMaster jenkinsMaster = Mockito.mock(JenkinsMaster.class);

		_setUpInvocation(baseBundlePersistentResource, jenkinsMaster);

		Mockito.doReturn(
			dataJSONObject,
			new JSONObject(
			).put(
				"controller_build_url",
				baseBundlePersistentResource.getCurrentTopLevelBuildURL()
			)
		).when(
			baseBundlePersistentResource
		).getDataJSONObject();

		Mockito.doCallRealMethod(
		).when(
			baseBundlePersistentResource
		).populateDataJSONObject(
			Mockito.any()
		);

		Properties buildProperties = new Properties();

		buildProperties.setProperty(
			"github.webhook.base.invocation.url", "http://test-1.liferay.com");

		JenkinsResultsParserUtil.setBuildProperties(buildProperties);

		JenkinsCohort jenkinsCohort = jenkinsMaster.getJenkinsCohort();

		try (MockedStatic<JenkinsCohort> jenkinsCohortMockedStatic =
				Mockito.mockStatic(
					JenkinsCohort.class, invocation -> jenkinsCohort);
			MockedStatic<JenkinsResultsParserUtil>
				jenkinsResultsParserUtilMockedStatic =
					_mockJenkinsResultsParserUtil(
						Collections.singletonMap(
							"invokeJenkinsBuild", invocation -> queueId))) {

			baseBundlePersistentResource.update();
		}

		Assert.assertEquals(
			expectedStatus, baseBundlePersistentResource.getStatus());

		Mockito.verify(
			baseBundlePersistentResource
		).print(
			Mockito.startsWith(expectedMessage)
		);

		JSONObject populatedDataJSONObject = new JSONObject();

		baseBundlePersistentResource.populateDataJSONObject(
			populatedDataJSONObject);

		Assert.assertEquals(
			2, populatedDataJSONObject.getInt("redispatch_attempts"));

		JSONArray populatedRedispatchHistoryJSONArray =
			populatedDataJSONObject.getJSONArray("redispatch_history");

		Assert.assertEquals(2, populatedRedispatchHistoryJSONArray.length());

		JSONObject redispatchHistoryJSONObject =
			populatedRedispatchHistoryJSONArray.getJSONObject(1);

		Assert.assertEquals(
			dataJSONObject.getString("controller_build_url"),
			redispatchHistoryJSONObject.getString("controller_build_url"));
	}

	private void _update(
		BaseBundlePersistentResource baseBundlePersistentResource, int count) {

		for (int i = 0; i < count; i++) {
			baseBundlePersistentResource.update();
		}
	}

}