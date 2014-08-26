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

package com.liferay.portal.kernel.concurrent;

import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.ReflectionTestUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class AsyncBrokerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() throws Exception {
		_defaultNoticeableFutures =
			(ConcurrentMap<String, DefaultNoticeableFuture<String>>)
				ReflectionTestUtil.getFieldValue(
					_asyncBroker, "_defaultNoticeableFutures");
	}

	@Test
	public void testGetOpenBids() {
		Map<String, NoticeableFuture<String>> map = _asyncBroker.getOpenBids();

		Assert.assertTrue(map.isEmpty());

		try {
			map.clear();

			Assert.fail();
		}
		catch (UnsupportedOperationException uoe) {
		}

		NoticeableFuture<String> noticeableFuture = _asyncBroker.post(_key);

		Assert.assertEquals(1, map.size());
		Assert.assertSame(noticeableFuture, map.get(_key));

		noticeableFuture.cancel(true);

		Assert.assertTrue(map.isEmpty());
	}

	@Test
	public void testPost() throws Exception {
		NoticeableFuture<String> noticeableFuture = _asyncBroker.post(_key);

		Assert.assertEquals(1, _defaultNoticeableFutures.size());
		Assert.assertSame(
			noticeableFuture, _defaultNoticeableFutures.get(_key));
		Assert.assertSame(noticeableFuture, _asyncBroker.post(_key));
		Assert.assertEquals(1, _defaultNoticeableFutures.size());
		Assert.assertTrue(noticeableFuture.cancel(true));
		Assert.assertTrue(_defaultNoticeableFutures.isEmpty());
	}

	@Test
	public void testTakeWithException() throws Exception {
		Exception exception = new Exception();

		Assert.assertFalse(_asyncBroker.takeWithException(_key, exception));

		NoticeableFuture<String> noticeableFuture = _asyncBroker.post(_key);

		Assert.assertEquals(1, _defaultNoticeableFutures.size());
		Assert.assertSame(
			noticeableFuture, _defaultNoticeableFutures.get(_key));
		Assert.assertTrue(_asyncBroker.takeWithException(_key, exception));

		try {
			noticeableFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Assert.assertSame(exception, ee.getCause());
		}
	}

	@Test
	public void testTakeWithResult() throws Exception {
		Assert.assertFalse(_asyncBroker.takeWithResult(_key, _value));

		NoticeableFuture<String> noticeableFuture = _asyncBroker.post(_key);

		Assert.assertEquals(1, _defaultNoticeableFutures.size());
		Assert.assertSame(
			noticeableFuture, _defaultNoticeableFutures.get(_key));
		Assert.assertTrue(_asyncBroker.takeWithResult(_key, _value));
		Assert.assertEquals(_value, noticeableFuture.get());
		Assert.assertTrue(_defaultNoticeableFutures.isEmpty());
	}

	private final AsyncBroker<String, String> _asyncBroker =
		new AsyncBroker<String, String>();
	private ConcurrentMap<String, DefaultNoticeableFuture<String>>
		_defaultNoticeableFutures;
	private final String _key = "testKey";
	private final String _value = "testValue";

}