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

package com.liferay.portlet.blogs.linkback;

import com.liferay.portal.kernel.comments.Comments;
import com.liferay.portal.kernel.security.pacl.permission.PortalSocketPermission;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.util.test.RandomTestUtil;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.DoesNothing;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@PrepareForTest({PortalSocketPermission.class})
@RunWith(PowerMockRunner.class)
public class LinkbackConsumerImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpHttp();

		_linkbackConsumer = new LinkbackConsumerImpl(_comments);
	}

	@Test
	public void testDeleteCommentIfBlogEntryURLNotInReferrer()
		throws Exception {

		Mockito.when(
			_http.URLtoString("__url__")
		).thenReturn(
			"__URLtoString_not_containing_entryURL__"
		);

		long messageId = RandomTestUtil.randomLong();

		_linkbackConsumer.addNewTrackback(messageId, "__url__", "__entryUrl__");

		_linkbackConsumer.verifyNewTrackbacks();

		Mockito.verify(
			_comments
		).deleteComment(
			messageId
		);

		Mockito.verify(
			_http
		).URLtoString(
			"__url__"
		);
	}

	@Test
	public void testDeleteCommentIfReferrerIsUnreachable() throws Exception {
		Mockito.doThrow(
			IOException.class
		).when(
			_http
		).URLtoString(
			"__PROBLEM_URL__"
		);

		long messageId = RandomTestUtil.randomLong();

		_linkbackConsumer.addNewTrackback(
			messageId, "__PROBLEM_URL__", "__entryUrl__");

		_linkbackConsumer.verifyNewTrackbacks();

		Mockito.verify(
			_comments
		).deleteComment(
			messageId
		);

		Mockito.verify(
			_http
		).URLtoString(
			"__PROBLEM_URL__"
		);
	}

	@Test
	public void testPreserveCommentIfBlogEntryURLIsInReferrer()
		throws Exception {

		Mockito.when(
			_http.URLtoString("__url__")
		).thenReturn(
			"__URLtoString_containing_**entryUrl**__"
		);

		_linkbackConsumer.addNewTrackback(
			RandomTestUtil.randomLong(), "__url__", "**entryUrl**");

		_linkbackConsumer.verifyNewTrackbacks();

		Mockito.verifyZeroInteractions(_comments);

		Mockito.verify(
			_http
		).URLtoString(
			"__url__"
		);
	}

	protected void setUpHttp() {
		mockStatic(PortalSocketPermission.class, new DoesNothing());

		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(_http);
	}

	@Mock
	private Comments _comments;

	@Mock
	private Http _http;

	private LinkbackConsumer _linkbackConsumer;

}