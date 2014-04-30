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

package com.liferay.portlet.blogs.trackback;

import com.liferay.portal.kernel.comments.Comments;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.LinkbackConsumer;
import com.liferay.portlet.blogs.util.LinkbackConsumerUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.CallsRealMethods;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@PrepareForTest({LinkbackConsumerUtil.class, UserLocalServiceUtil.class})
@RunWith(PowerMockRunner.class)
public class TrackbackImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpLinkbackConsumer();
		setUpPortal();
		setUpThemeDisplay();
		setUpUserLocalService();
	}

	@Test
	public void testAddTrackback() throws Exception {
		long entryId = RandomTestUtil.randomLong();

		when(
			_blogsEntry.getEntryId()
		).thenReturn(
			entryId
		);

		long groupId = RandomTestUtil.randomLong();

		when(
			_blogsEntry.getGroupId()
		).thenReturn(
			groupId
		);

		when(
			_blogsEntry.getUrlTitle()
		).thenReturn(
			"__UrlTitle__"
		);

		when(
			_portal.getLayoutFullURL(_themeDisplay)
		).thenReturn(
			"__LayoutFullURL__"
		);

		when(
			_themeDisplay.translate("read-more")
		).thenReturn(
			"__read-more__"
		);

		long userId = RandomTestUtil.randomLong();

		when(
			_userLocalService.getDefaultUserId(Matchers.anyLong())
		).thenReturn(
			userId
		);

		long commentId = RandomTestUtil.randomLong();

		when(
			_comments.addComment(
				Matchers.anyLong(), Matchers.anyLong(), Matchers.anyString(),
				Matchers.anyLong(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(),
				(Function<String, ServiceContext>)Matchers.any()
			)
		).thenReturn(
			commentId
		);

		Trackback trackback = new TrackbackImpl(_comments);

		trackback.addTrackback(
			_blogsEntry, _themeDisplay, "__excerpt__", "__url__",
			"__blogName__", "__title__", _serviceContextFunction
		);

		Mockito.verify(
			_comments
		).addComment(
			Matchers.eq(userId), Matchers.eq(groupId),
			Matchers.eq(BlogsEntry.class.getName()), Matchers.eq(entryId),
			Matchers.eq("__blogName__"), Matchers.eq("__title__"),
			Matchers.eq(
				"[...] __excerpt__ [...] [url=__url__]__read-more__[/url]"),
			Matchers.same(_serviceContextFunction)
		);

		Mockito.verify(
			_linkbackConsumer
		).addNewTrackback(
			commentId, "__url__", "__LayoutFullURL__/-/blogs/__UrlTitle__"
		);
	}

	protected void setUpLinkbackConsumer() throws Exception {
		mockStatic(LinkbackConsumerUtil.class, Mockito.CALLS_REAL_METHODS);

		when(
			LinkbackConsumerUtil.getLinkbackConsumer()
		).thenReturn(
			_linkbackConsumer
		);
	}

	protected void setUpPortal() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	protected void setUpThemeDisplay() throws Exception {
		_themeDisplay = PowerMockito.mock(ThemeDisplay.class);
	}

	protected void setUpUserLocalService() {
		mockStatic(UserLocalServiceUtil.class, new CallsRealMethods());

		stub(
			method(UserLocalServiceUtil.class, "getService")
		).toReturn(
			_userLocalService
		);
	}

	@Mock
	private BlogsEntry _blogsEntry;

	@Mock
	private Comments _comments;

	@Mock
	private LinkbackConsumer _linkbackConsumer;

	@Mock
	private Portal _portal;

	@Mock
	private Function<String, ServiceContext> _serviceContextFunction;

	private ThemeDisplay _themeDisplay;

	@Mock
	private UserLocalService _userLocalService;

}