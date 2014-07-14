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

package com.liferay.portlet.blogs.util;

import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.DuplicateCommentException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.security.pacl.permission.PortalSocketPermission;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.xmlrpc.Fault;
import com.liferay.portal.kernel.xmlrpc.XmlRpc;
import com.liferay.portal.kernel.xmlrpc.XmlRpcConstants;
import com.liferay.portal.kernel.xmlrpc.XmlRpcUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalService;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * @author André de Oliveira
 */
@PrepareForTest( {
	BlogsEntryLocalServiceUtil.class, BlogsUtil.class,
	PortalSocketPermission.class, PortletLocalServiceUtil.class,
	PropsValues.class, UserLocalServiceUtil.class
})
@RunWith(PowerMockRunner.class)
public class PingbackMethodImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpBlogsEntry();
		setUpBlogsUtil();
		setUpHttp();
		setUpLanguage();
		setUpPortal();
		setUpPortlet();
		setUpUser();
		setUpXmlRpc();
	}

	@Test
	public void testAddPingbackWhenBlogEntryDisablesPingbacks()
		throws Exception {

		when(
			_blogsEntry.isAllowPingbacks()
		).thenReturn(
			false
		);

		execute();

		verifyFault(
			XmlRpcConstants.REQUESTED_METHOD_NOT_FOUND,
			"Pingbacks are disabled");
	}

	@Test
	public void testAddPingbackWhenPortalPropertyDisablesPingbacks()
		throws Exception {

		boolean previous = PropsValues.BLOGS_PINGBACK_ENABLED;

		Whitebox.setInternalState(
			PropsValues.class, "BLOGS_PINGBACK_ENABLED", false);

		try {
			execute();

			verifyFault(
				XmlRpcConstants.REQUESTED_METHOD_NOT_FOUND,
				"Pingbacks are disabled");
		}
		finally {
			Whitebox.setInternalState(
				PropsValues.class, "BLOGS_PINGBACK_ENABLED", previous);
		}
	}

	@Test
	public void testAddPingbackWithFriendlyURL() throws Exception {
		long plid = RandomTestUtil.randomLong();

		when(
			_portal.getPlidFromFriendlyURL(_COMPANY_ID, "/__blogs__")
		).thenReturn(
			plid
		);

		long groupId = RandomTestUtil.randomLong();

		when(
			_portal.getScopeGroupId(plid)
		).thenReturn(
			groupId
		);

		whenFriendlyURLMapperPopulateParams(
			"/__remainder-of-the-friendly-url__", "urlTitle", "__urlTitle__");

		String friendlyURL =
			"http://liferay.com/__blogs__/-/__remainder-of-the-friendly-url__";

		whenHttpURLToString(
			"<body><a href='" + friendlyURL + "'>Liferay</a></body>");

		execute(friendlyURL);

		verifySuccess();

		Mockito.verify(
			_blogsEntryLocalService
		).getEntry(
			groupId, "__urlTitle__"
		);
	}

	@Test
	public void testAddPingbackWithFriendlyURLParameterEntryId()
		throws Exception {

		testAddPingbackWithFriendlyURLParameterEntryId(null);
	}

	@Test
	public void testAddPingbackWithFriendlyURLParameterEntryIdInNamespace()
		throws Exception {

		String namespace = "__namespace__.";

		when(
			_portal.getPortletNamespace(PortletKeys.BLOGS)
		).thenReturn(
			namespace
		);

		testAddPingbackWithFriendlyURLParameterEntryId(namespace);
	}

	@Test
	public void testBuildServiceContext() throws Exception {
		PingbackMethodImpl pingbackMethodImpl = new PingbackMethodImpl();

		ServiceContext serviceContext = pingbackMethodImpl.buildServiceContext(
			_COMPANY_ID, _GROUP_ID, "__UrlTitle__");

		Assert.assertEquals(
			"__pingbackUserName__",
			serviceContext.getAttribute("pingbackUserName"));
		Assert.assertEquals(
			"__LayoutFullURL__/-/__FriendlyURLMapping__/__UrlTitle__",
			serviceContext.getAttribute("redirect"));
		Assert.assertEquals(
			"__LayoutFullURL__", serviceContext.getLayoutFullURL());
	}

	@Test
	public void testConvertDuplicateCommentExceptionToXmlRpcFault()
		throws Exception {

		Mockito.doThrow(
			DuplicateCommentException.class
		).when(
			_commentManager
		).addComment(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString(),
				Mockito.anyLong(), Mockito.anyString(),
				(ServiceContext)Mockito.any()
		);

		execute();

		verifyFault(
			PingbackMethodImpl.PINGBACK_ALREADY_REGISTERED,
			"Pingback previously registered");
	}

	@Test
	public void testExecuteWithSuccess() throws Exception {
		execute();

		verifySuccess();

		Mockito.verify(
			_commentManager
		).addComment(
			Matchers.eq(_USER_ID), Matchers.eq(_GROUP_ID),
			Matchers.eq(BlogsEntry.class.getName()), Matchers.eq(_ENTRY_ID),
			Matchers.eq(
				"[...] Liferay [...] [url=__sourceUri__]__read_more__[/url]"),
			(ServiceContext)Mockito.any()
		);
	}

	@Test
	public void testGetExcerpt() throws Exception {
		int previous = PropsValues.BLOGS_LINKBACK_EXCERPT_LENGTH;

		Whitebox.setInternalState(
			PropsValues.class, "BLOGS_LINKBACK_EXCERPT_LENGTH", 4);

		try {
			whenHttpURLToString(
				"<body><a href='http://liferay.com'>12345</a></body>");

			execute();

			verifyExcerpt("1...");
		}
		finally {
			Whitebox.setInternalState(
				PropsValues.class, "BLOGS_LINKBACK_EXCERPT_LENGTH", previous);
		}
	}

	@Test
	public void testGetExcerptWhenAnchorHasParent() throws Exception {
		whenHttpURLToString(
			"<body><p>" +
			"Visit <a href='http://liferay.com'>Liferay</a> to learn more" +
			"</p></body>");

		execute();

		verifyExcerpt("Visit Liferay to learn more");
	}

	@Test
	public void testGetExcerptWhenAnchorHasTwoParents() throws Exception {
		int previous = PropsValues.BLOGS_LINKBACK_EXCERPT_LENGTH;

		Whitebox.setInternalState(
			PropsValues.class, "BLOGS_LINKBACK_EXCERPT_LENGTH", 18);

		try {
			whenHttpURLToString(
				"<body>_____<p>12345<span>67890" +
				"<a href='http://liferay.com'>Liferay</a>" +
				"12345</span>67890</p>_____</body>");

			execute();

			verifyExcerpt("1234567890Lifer...");
		}
		finally {
			Whitebox.setInternalState(
				PropsValues.class, "BLOGS_LINKBACK_EXCERPT_LENGTH", previous);
		}
	}

	@Test
	public void testGetExcerptWhenAnchorIsMalformed() throws Exception {
		whenHttpURLToString("<a href='MALFORMED' />");

		execute("MALFORMED");

		verifyFault(
			PingbackMethodImpl.TARGET_URI_INVALID, "Error parsing target URI");
	}

	@Test
	public void testGetExcerptWhenAnchorIsMissing() throws Exception {
		whenHttpURLToString("");

		execute();

		verifyFault(
			PingbackMethodImpl.SOURCE_URI_INVALID,
			"Could not find target URI in source");
	}

	@Test
	public void testGetExcerptWhenReferrerIsUnavailable() throws Exception {
		when(
			_http.URLtoString("__sourceUri__")
		).thenThrow(
			IOException.class
		);

		execute();

		verifyFault(
			PingbackMethodImpl.SOURCE_URI_DOES_NOT_EXIST,
			"Error accessing source URI");
	}

	protected void execute() {
		execute("http://liferay.com");
	}

	protected void execute(String targetURI) {
		PingbackMethodImpl pingbackMethodImpl = new PingbackMethodImpl();
		pingbackMethodImpl.setCommentManager(_commentManager);

		pingbackMethodImpl.setArguments(
			new Object[] {"__sourceUri__", targetURI});

		pingbackMethodImpl.execute(_COMPANY_ID);
	}

	protected void setUpBlogsEntry() throws Exception {
		when(
			_blogsEntry.getEntryId()
		).thenReturn(
			_ENTRY_ID
		);

		when(
			_blogsEntry.getGroupId()
		).thenReturn(
			_GROUP_ID
		);

		when(
			_blogsEntry.getUrlTitle()
		).thenReturn(
			"__UrlTitle__"
		);

		when(
			_blogsEntry.isAllowPingbacks()
		).thenReturn(
			true
		);

		when(
			_blogsEntryLocalService.getEntry(
				Matchers.anyLong(), Matchers.anyString())
		).thenReturn(
			_blogsEntry
		);

		mockStatic(
			BlogsEntryLocalServiceUtil.class, Mockito.CALLS_REAL_METHODS);

		stub(
			method(BlogsEntryLocalServiceUtil.class, "getService")
		).toReturn(
			_blogsEntryLocalService
		);
	}

	protected void setUpBlogsUtil() {
		mockStatic(BlogsUtil.class, Mockito.RETURNS_SMART_NULLS);
	}

	protected void setUpHttp() throws Exception {
		whenHttpURLToString(
			"<body><a href='http://liferay.com'>Liferay</a></body>");

		mockStatic(PortalSocketPermission.class, Mockito.RETURNS_DEFAULTS);

		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(_http);
	}

	protected void setUpLanguage() {
		whenLanguageGet("pingback", "__pingbackUserName__");
		whenLanguageGet("read-more", "__read_more__");

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	protected void setUpPortal() throws Exception {
		when(
			_portal.getLayoutFullURL(
				Matchers.anyLong(), Matchers.eq(PortletKeys.BLOGS))
		).thenReturn(
			"__LayoutFullURL__"
		);

		when(
			_portal.getPlidFromFriendlyURL(
				Matchers.eq(_COMPANY_ID), Matchers.anyString())
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		when(
			_portal.getScopeGroupId(Matchers.anyLong())
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	protected void setUpPortlet() throws Exception {
		Portlet portlet = Mockito.mock(Portlet.class);

		when(
			portlet.getFriendlyURLMapperInstance()
		).thenReturn(
			_friendlyURLMapper
		);

		when(
			portlet.getFriendlyURLMapping()
		).thenReturn(
			"__FriendlyURLMapping__"
		);

		PortletLocalService portletLocalService = Mockito.mock(
			PortletLocalService.class);

		when(
			portletLocalService.getPortletById(PortletKeys.BLOGS)
		).thenReturn(
			portlet
		);

		when(
			portletLocalService.getPortletById(
				Matchers.anyLong(), Matchers.eq(PortletKeys.BLOGS))
		).thenReturn(
			portlet
		);

		mockStatic(PortletLocalServiceUtil.class, Mockito.CALLS_REAL_METHODS);

		stub(
			method(PortletLocalServiceUtil.class, "getService")
		).toReturn(
			portletLocalService
		);
	}

	protected void setUpUser() throws Exception {
		UserLocalService userLocalService = Mockito.mock(
			UserLocalService.class);

		when(
			userLocalService.getDefaultUserId(Matchers.anyLong())
		).thenReturn(
			_USER_ID
		);

		mockStatic(UserLocalServiceUtil.class, Mockito.CALLS_REAL_METHODS);

		stub(
			method(UserLocalServiceUtil.class, "getService")
		).toReturn(
			userLocalService
		);
	}

	protected void setUpXmlRpc() {
		Fault fault = Mockito.mock(Fault.class);

		when(
			_xmlRpc.createFault(Matchers.anyInt(), Matchers.anyString())
		).thenReturn(
			fault
		);

		XmlRpcUtil xmlRpcUtil = new XmlRpcUtil();

		xmlRpcUtil.setXmlRpc(_xmlRpc);
	}

	protected void testAddPingbackWithFriendlyURLParameterEntryId(
			String namespace)
		throws Exception {

		when(
			_blogsEntryLocalService.getEntry(Matchers.anyLong())
		).thenReturn(
			_blogsEntry
		);

		String name;

		if (namespace == null) {
			name = "entryId";
		}
		else {
			name = namespace + "entryId";
		}

		long entryId = RandomTestUtil.randomLong();

		whenFriendlyURLMapperPopulateParams("", name, String.valueOf(entryId));

		execute();

		verifySuccess();

		Mockito.verify(
			_blogsEntryLocalService
		).getEntry(
			entryId
		);
	}

	protected void verifyExcerpt(String excerpt) throws Exception {
		verifySuccess();

		Mockito.verify(
			_commentManager
		).addComment(
			Matchers.anyLong(), Matchers.anyLong(), Matchers.anyString(),
			Matchers.anyLong(),
			Matchers.eq(
				"[...] " + excerpt +
				" [...] [url=__sourceUri__]__read_more__[/url]"),
			(ServiceContext)Matchers.any()
		);
	}

	protected void verifyFault(int code, String description) {
		Mockito.verify(
			_xmlRpc
		).createFault(
			code, description
		);
	}

	protected void verifySuccess() {
		Mockito.verify(
			_xmlRpc
		).createSuccess(
			"Pingback accepted"
		);
	}

	protected void whenFriendlyURLMapperPopulateParams(
		String friendlyURL, final String name, final String value) {

		Mockito.doAnswer(
			new Answer<Void>() {

				@Override
				public Void answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Map<String, String[]> params = (Map<String, String[]>)
						invocationOnMock.getArguments()[1];

					params.put(name, new String[] {value});

					return null;
				}

			}
		).when(
			_friendlyURLMapper
		).populateParams(
			Matchers.eq(friendlyURL), Matchers.anyMap(), Matchers.anyMap()
		);
	}

	protected void whenHttpURLToString(String returnValue) throws Exception {
		when(
			_http.URLtoString("__sourceUri__")
		).thenReturn(
			returnValue
		);
	}

	protected void whenLanguageGet(String key, String returnValue) {
		when(
			_language.get((Locale)Matchers.any(), Matchers.eq(key))
		).thenReturn(
			returnValue
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _ENTRY_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final long _USER_ID = RandomTestUtil.randomLong();

	@Mock
	private BlogsEntry _blogsEntry;

	@Mock
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Mock
	private CommentManager _commentManager;

	@Mock
	private FriendlyURLMapper _friendlyURLMapper;

	@Mock
	private Http _http;

	@Mock
	private Language _language;

	@Mock
	private Portal _portal;

	@Mock
	private XmlRpc _xmlRpc;

}