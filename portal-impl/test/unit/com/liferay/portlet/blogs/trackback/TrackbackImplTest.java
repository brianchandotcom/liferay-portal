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

import com.liferay.portal.kernel.comments.CommentManager;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.linkback.LinkbackConsumer;
import com.liferay.portlet.blogs.linkback.LinkbackConsumerUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.util.BlogsUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@PrepareForTest({BlogsUtil.class, UserLocalServiceUtil.class})
@RunWith(PowerMockRunner.class)
public class TrackbackImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpBlogsUtil();
		setUpPortalUtil();
		setUpThemeDisplay();
		setUpUserLocalServiceUtil();
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

		String urlTitle = RandomTestUtil.randomString();

		when(
			_blogsEntry.getUrlTitle()
		).thenReturn(
			urlTitle
		);

		String layoutFullURL = RandomTestUtil.randomString();

		when(
			_portal.getLayoutFullURL(_themeDisplay)
		).thenReturn(
			layoutFullURL
		);

		String readMore = RandomTestUtil.randomString();

		when(
			_themeDisplay.translate("read-more")
		).thenReturn(
			readMore
		);

		long userId = RandomTestUtil.randomLong();

		when(
			_userLocalService.getDefaultUserId(Matchers.anyLong())
		).thenReturn(
			userId
		);

		long commentId = RandomTestUtil.randomLong();

		when(
			_commentManager.addComment(
				Matchers.anyLong(), Matchers.anyLong(), Matchers.anyString(),
				Matchers.anyLong(), Matchers.anyString(), Matchers.anyString(),
				Matchers.anyString(),
				(Function<String, ServiceContext>)Matchers.any()
			)
		).thenReturn(
			commentId
		);

		Trackback trackback = new TrackbackImpl();

		trackback.setCommentManager(_commentManager);
		trackback.setLinkbackConsumer(_linkbackConsumer);

		String excerpt = RandomTestUtil.randomString();
		String url = RandomTestUtil.randomString();
		String blogName = RandomTestUtil.randomString();
		String title = RandomTestUtil.randomString();

		trackback.addTrackback(
			_blogsEntry, _themeDisplay, excerpt, url, blogName, title,
			_serviceContextFunction
		);

		Mockito.verify(
			_commentManager
		).addComment(
			userId, groupId, BlogsEntry.class.getName(), entryId, blogName,
			title,
			"[...] " + excerpt + " [...] [url=" + url + "]" + readMore +
				"[/url]",
			_serviceContextFunction
		);

		Mockito.verify(
			_linkbackConsumer
		).addNewTrackback(
			commentId, url, layoutFullURL + "/-/blogs/" + urlTitle
		);
	}

	protected void setUpBlogsUtil() {
		mockStatic(BlogsUtil.class, Mockito.RETURNS_SMART_NULLS);
	}

	protected void setUpLinkbackConsumer() throws Exception {
		mockStatic(LinkbackConsumerUtil.class, Mockito.CALLS_REAL_METHODS);

		when(
			LinkbackConsumerUtil.getLinkbackConsumer()
		).thenReturn(
			_linkbackConsumer
		);
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	protected void setUpThemeDisplay() throws Exception {
		_themeDisplay = PowerMockito.mock(ThemeDisplay.class);
	}

	protected void setUpUserLocalServiceUtil() {
		mockStatic(UserLocalServiceUtil.class, Mockito.CALLS_REAL_METHODS);

		stub(
			method(UserLocalServiceUtil.class, "getService")
		).toReturn(
			_userLocalService
		);
	}

	@Mock
	private BlogsEntry _blogsEntry;

	@Mock
	private CommentManager _commentManager;

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