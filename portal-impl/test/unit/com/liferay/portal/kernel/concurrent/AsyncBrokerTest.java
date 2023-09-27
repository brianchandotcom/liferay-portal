/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.test.FinalizeManagerUtil;
import com.liferay.portal.kernel.test.GCUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class AsyncBrokerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testGetOpenBids() {
		AsyncBroker<String, String> asyncBroker = new AsyncBroker<>();

		Map<String, NoticeableFuture<String>> map = asyncBroker.getOpenBids();

		Assert.assertTrue(map.toString(), map.isEmpty());

		try {
			map.clear();

			Assert.fail();
		}
		catch (UnsupportedOperationException unsupportedOperationException) {
		}

		NoticeableFuture<String> noticeableFuture = asyncBroker.post(_KEY);

		Assert.assertEquals(map.toString(), 1, map.size());
		Assert.assertSame(noticeableFuture, map.get(_KEY));

		noticeableFuture.cancel(true);

		Assert.assertTrue(map.toString(), map.isEmpty());
	}

	@Test
	public void testOrphanCancellation() throws InterruptedException {
		AsyncBroker<String, String> asyncBroker = new AsyncBroker<>();

		NoticeableFuture<String> noticeableFuture = asyncBroker.post(_KEY);

		AtomicBoolean completedMarker = new AtomicBoolean();

		noticeableFuture.addFutureListener(future -> completedMarker.set(true));

		noticeableFuture = null;

		GCUtil.gc(true);

		FinalizeManagerUtil.drainPendingFinalizeActions();

		Assert.assertTrue(completedMarker.get());
	}

	@Test
	public void testPost() throws Exception {
		AsyncBroker<String, String> asyncBroker = new AsyncBroker<>();

		Map<String, DefaultNoticeableFuture<String>> defaultNoticeableFutures =
			ReflectionTestUtil.getFieldValue(
				asyncBroker, "_defaultNoticeableFutures");

		NoticeableFuture<String> noticeableFuture = asyncBroker.post(_KEY);

		Assert.assertEquals(
			defaultNoticeableFutures.toString(), 1,
			defaultNoticeableFutures.size());
		Assert.assertSame(noticeableFuture, defaultNoticeableFutures.get(_KEY));
		Assert.assertSame(noticeableFuture, asyncBroker.post(_KEY));
		Assert.assertEquals(
			defaultNoticeableFutures.toString(), 1,
			defaultNoticeableFutures.size());
		Assert.assertTrue(noticeableFuture.cancel(true));
		Assert.assertTrue(
			defaultNoticeableFutures.toString(),
			defaultNoticeableFutures.isEmpty());

		boolean[] newMarker = new boolean[1];

		noticeableFuture = asyncBroker.post(_KEY, newMarker);

		Assert.assertNotNull(noticeableFuture);

		Assert.assertTrue(Arrays.toString(newMarker), newMarker[0]);
		Assert.assertSame(noticeableFuture, asyncBroker.post(_KEY, newMarker));
		Assert.assertFalse(Arrays.toString(newMarker), newMarker[0]);
		Assert.assertTrue(noticeableFuture.cancel(true));
		Assert.assertTrue(
			defaultNoticeableFutures.toString(),
			defaultNoticeableFutures.isEmpty());
	}

	@Test
	public void testTake() {
		AsyncBroker<String, String> asyncBroker = new AsyncBroker<>();

		Map<String, DefaultNoticeableFuture<String>> defaultNoticeableFutures =
			ReflectionTestUtil.getFieldValue(
				asyncBroker, "_defaultNoticeableFutures");

		Assert.assertTrue(
			defaultNoticeableFutures.toString(),
			defaultNoticeableFutures.isEmpty());

		Assert.assertNull(asyncBroker.take(_KEY));

		NoticeableFuture<String> noticeableFuture = asyncBroker.post(_KEY);

		Assert.assertEquals(
			defaultNoticeableFutures.toString(), 1,
			defaultNoticeableFutures.size());
		Assert.assertSame(noticeableFuture, defaultNoticeableFutures.get(_KEY));
		Assert.assertSame(noticeableFuture, asyncBroker.take(_KEY));
		Assert.assertTrue(
			defaultNoticeableFutures.toString(),
			defaultNoticeableFutures.isEmpty());
		Assert.assertNull(asyncBroker.take(_KEY));
		Assert.assertTrue(noticeableFuture.cancel(true));
	}

	@Test
	public void testTakeWithException() throws Exception {
		AsyncBroker<String, String> asyncBroker = new AsyncBroker<>();

		Map<String, DefaultNoticeableFuture<String>> defaultNoticeableFutures =
			ReflectionTestUtil.getFieldValue(
				asyncBroker, "_defaultNoticeableFutures");

		Assert.assertTrue(
			defaultNoticeableFutures.toString(),
			defaultNoticeableFutures.isEmpty());

		Exception exception = new Exception();

		Assert.assertFalse(asyncBroker.takeWithException(_KEY, exception));

		NoticeableFuture<String> noticeableFuture = asyncBroker.post(_KEY);

		Assert.assertEquals(
			defaultNoticeableFutures.toString(), 1,
			defaultNoticeableFutures.size());
		Assert.assertSame(noticeableFuture, defaultNoticeableFutures.get(_KEY));
		Assert.assertTrue(asyncBroker.takeWithException(_KEY, exception));

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException executionException) {
			Assert.assertSame(exception, executionException.getCause());
		}

		Assert.assertTrue(
			defaultNoticeableFutures.toString(),
			defaultNoticeableFutures.isEmpty());
		Assert.assertFalse(asyncBroker.takeWithException(_KEY, exception));
	}

	@Test
	public void testTakeWithResult() throws Exception {
		AsyncBroker<String, String> asyncBroker = new AsyncBroker<>();

		Map<String, DefaultNoticeableFuture<String>> defaultNoticeableFutures =
			ReflectionTestUtil.getFieldValue(
				asyncBroker, "_defaultNoticeableFutures");

		Assert.assertTrue(
			defaultNoticeableFutures.toString(),
			defaultNoticeableFutures.isEmpty());

		Assert.assertFalse(asyncBroker.takeWithResult(_KEY, _VALUE));

		NoticeableFuture<String> noticeableFuture = asyncBroker.post(_KEY);

		Assert.assertEquals(
			defaultNoticeableFutures.toString(), 1,
			defaultNoticeableFutures.size());
		Assert.assertSame(noticeableFuture, defaultNoticeableFutures.get(_KEY));
		Assert.assertTrue(asyncBroker.takeWithResult(_KEY, _VALUE));
		Assert.assertEquals(_VALUE, noticeableFuture.get());
		Assert.assertTrue(
			defaultNoticeableFutures.toString(),
			defaultNoticeableFutures.isEmpty());
		Assert.assertFalse(asyncBroker.takeWithResult(_KEY, _VALUE));
	}

	private static final String _KEY = "testKey";

	private static final String _VALUE = "testValue";

}