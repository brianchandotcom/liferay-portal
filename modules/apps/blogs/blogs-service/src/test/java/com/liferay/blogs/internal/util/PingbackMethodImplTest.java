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

package com.liferay.blogs.internal.util;

import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.DuplicateCommentException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFunction;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.xmlrpc.Fault;
import com.liferay.portal.kernel.xmlrpc.XmlRpc;
import com.liferay.portal.kernel.xmlrpc.XmlRpcConstants;
import com.liferay.portal.kernel.xmlrpc.XmlRpcUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.net.InetAddress;
import java.net.URI;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author André de Oliveira
 */
public class PingbackMethodImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_setUpBlogsEntryLocalService();
		_setUpHttpUtil();
		_setUpInetAddressLookup();
		_setUpLanguageUtil();
		_setUpPingbackProperties();
		_setUpPortalUtil();
		_setUpPortletIdLookup();
		_setUpPortletLocalService();
		_setUpPropsTestUtil();
		_setUpUserLocalService();
		_setUpXmlRpcUtil();
	}

	@Test
	public void testAddPingbackWhenBlogEntryDisablesPingbacks()
		throws Exception {

		Mockito.when(
			_blogsEntry.isAllowPingbacks()
		).thenReturn(
			false
		);

		execute();

		_verifyFault(
			XmlRpcConstants.REQUESTED_METHOD_NOT_FOUND,
			"Pingbacks are disabled");
	}

	@Test
	public void testAddPingbackWhenPortalPropertyDisablesPingbacks()
		throws Exception {

		Mockito.doReturn(
			false
		).when(
			_pingbackProperties
		).isPingbackEnabled();

		execute();

		_verifyFault(
			XmlRpcConstants.REQUESTED_METHOD_NOT_FOUND,
			"Pingbacks are disabled");
	}

	@Test
	public void testAddPingbackWithFriendlyURL() throws Exception {
		long plid = RandomTestUtil.randomLong();

		String friendlyURL = RandomTestUtil.randomString();

		Mockito.when(
			_portal.getPlidFromFriendlyURL(_COMPANY_ID, "/" + friendlyURL)
		).thenReturn(
			plid
		);

		long groupId = RandomTestUtil.randomLong();

		Mockito.when(
			_portal.getScopeGroupId(plid)
		).thenReturn(
			groupId
		);

		String friendlyURLPath = RandomTestUtil.randomString();

		_whenFriendlyURLMapperPopulateParams(
			"/" + friendlyURLPath, "urlTitle", _URL_TITLE);

		String targetURI = StringBundler.concat(
			"http://", RandomTestUtil.randomString(), "/", friendlyURL, "/-/",
			friendlyURLPath);

		_whenHttpURLToString(
			StringBundler.concat(
				"<body><a href='", targetURI, "'>",
				RandomTestUtil.randomString(), "</a></body>"));

		execute(targetURI);

		_verifySuccess();

		Mockito.verify(
			_blogsEntryLocalService
		).getEntry(
			groupId, _URL_TITLE
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

		String namespace = RandomTestUtil.randomString() + ".";

		Mockito.when(
			_portal.getPortletNamespace(BlogsPortletKeys.BLOGS)
		).thenReturn(
			namespace
		);

		testAddPingbackWithFriendlyURLParameterEntryId(namespace);
	}

	@Test
	public void testBuildServiceContext() throws Exception {
		ServiceContext serviceContext =
			(ServiceContext)ReflectionTestUtil.invoke(
				_getPingbackMethodImpl(), "_buildServiceContext",
				new Class<?>[] {long.class, long.class, String.class},
				_COMPANY_ID, _GROUP_ID, _URL_TITLE);

		Assert.assertEquals(
			_PINGBACK_USER_NAME,
			serviceContext.getAttribute("pingbackUserName"));
		Assert.assertEquals(
			StringBundler.concat(
				_LAYOUT_FULL_URL, "/-/", _FRIENDLY_URL_MAPPING, "/",
				_URL_TITLE),
			serviceContext.getAttribute("redirect"));
		Assert.assertEquals(
			_LAYOUT_FULL_URL, serviceContext.getLayoutFullURL());
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
			Mockito.<ServiceContextFunction>any()
		);

		execute();

		_verifyFault(
			PingbackMethodImpl.PINGBACK_ALREADY_REGISTERED,
			"Pingback is already registered: null");
	}

	@Test
	public void testExecuteWithSuccess() throws Exception {
		execute();

		_verifySuccess();

		Mockito.verify(
			_commentManager
		).addComment(
			Matchers.eq(_USER_ID), Matchers.eq(_GROUP_ID),
			Matchers.eq(BlogsEntry.class.getName()), Matchers.eq(_ENTRY_ID),
			Matchers.eq(
				StringBundler.concat(
					"[...] ", _EXCERPT_BODY, " [...] <a href=", _SOURCE_URI,
					">", _READ_MORE, "</a>")),
			Mockito.<ServiceContextFunction>any()
		);
	}

	@Test
	public void testGetExcerpt() throws Exception {
		Mockito.doReturn(
			4
		).when(
			_pingbackProperties
		).getLinkbackExcerptLength();

		_whenHttpURLToString(
			"<body><a href='http://" + _TARGET_URI + "'>12345</a></body>");

		execute();

		_verifyExcerpt("1...");
	}

	@Test
	public void testGetExcerptWhenAnchorHasParent() throws Exception {
		_whenHttpURLToString(
			StringBundler.concat(
				"<body><p>Visit <a href='http://", _TARGET_URI,
				"'>Liferay</a> to learn more</p></body>"));

		execute();

		_verifyExcerpt("Visit Liferay to learn more");
	}

	@Test
	public void testGetExcerptWhenAnchorHasTwoParents() throws Exception {
		Mockito.doReturn(
			18
		).when(
			_pingbackProperties
		).getLinkbackExcerptLength();

		_whenHttpURLToString(
			"<body>_____<p>12345<span>67890<a href='http://" + _TARGET_URI +
				"'>Liferay</a>12345</span>67890</p>_____</body>");

		execute();

		_verifyExcerpt("1234567890Lifer...");
	}

	@Test
	public void testGetExcerptWhenAnchorIsMalformed() throws Exception {
		_whenHttpURLToString("<a href='MALFORMED' />");

		execute("MALFORMED");

		_verifyFault(
			PingbackMethodImpl.TARGET_URI_INVALID,
			"Unable to parse target URI");
	}

	@Test
	public void testGetExcerptWhenAnchorIsMissing() throws Exception {
		_whenHttpURLToString("");

		execute();

		_verifyFault(
			PingbackMethodImpl.SOURCE_URI_INVALID,
			"Unable to find target URI in source");
	}

	@Test
	public void testGetExcerptWhenReferrerIsUnavailable() throws Exception {
		Mockito.when(
			_http.URLtoString(_SOURCE_URI)
		).thenThrow(
			IOException.class
		);

		execute();

		_verifyFault(
			PingbackMethodImpl.SOURCE_URI_DOES_NOT_EXIST,
			"Error accessing source URI");
	}

	@Test
	public void testLocalNetworkSSRF() throws Exception {
		for (int i = 0; i < _localAddresses.length; i++) {
			InetAddress inetAddress = _localAddresses[i];

			String sourceURL = "http://" + inetAddress.getHostAddress();

			Mockito.when(
				_http.URLtoString(sourceURL)
			).thenReturn(
				StringBundler.concat(
					"<body><a href='http://", _TARGET_URI, "'>", _EXCERPT_BODY,
					"</a></body>")
			);

			PingbackMethodImpl pingbackMethodImpl = _getPingbackMethodImpl();

			pingbackMethodImpl.setArguments(
				new Object[] {sourceURL, "http://" + _TARGET_URI});

			pingbackMethodImpl.execute(_COMPANY_ID);

			Mockito.verify(
				_xmlRpc, Mockito.times(i + 1)
			).createFault(
				PingbackMethodImpl.ACCESS_DENIED, "Access Denied"
			);
		}
	}

	protected void execute() {
		execute("http://" + _TARGET_URI);
	}

	protected void execute(String targetURI) {
		PingbackMethodImpl pingbackMethodImpl = _getPingbackMethodImpl();

		pingbackMethodImpl.setArguments(new Object[] {_SOURCE_URI, targetURI});

		pingbackMethodImpl.execute(_COMPANY_ID);
	}

	protected void testAddPingbackWithFriendlyURLParameterEntryId(
			String namespace)
		throws Exception {

		Mockito.when(
			_blogsEntryLocalService.getEntry(Matchers.anyLong())
		).thenReturn(
			_blogsEntry
		);

		String name = null;

		if (namespace == null) {
			name = "entryId";
		}
		else {
			name = namespace + "entryId";
		}

		long entryId = RandomTestUtil.randomLong();

		_whenFriendlyURLMapperPopulateParams("", name, String.valueOf(entryId));

		execute();

		_verifySuccess();

		Mockito.verify(
			_blogsEntryLocalService
		).getEntry(
			entryId
		);
	}

	private PingbackMethodImpl _getPingbackMethodImpl() {
		PingbackMethodImpl pingbackMethodImpl = new PingbackMethodImpl();

		pingbackMethodImpl.setInetAddressLookup(_inetAddressLookup);
		pingbackMethodImpl.setPingbackProperties(_pingbackProperties);
		pingbackMethodImpl.setPortletIdLookup(_portletIdLookup);

		ReflectionTestUtil.setFieldValue(
			pingbackMethodImpl, "_blogsEntryLocalService",
			_blogsEntryLocalService);
		ReflectionTestUtil.setFieldValue(
			pingbackMethodImpl, "_commentManager", _commentManager);
		ReflectionTestUtil.setFieldValue(
			pingbackMethodImpl, "_http", HttpUtil.getHttp());
		ReflectionTestUtil.setFieldValue(
			pingbackMethodImpl, "_portal", PortalUtil.getPortal());
		ReflectionTestUtil.setFieldValue(
			pingbackMethodImpl, "_portletLocalService", _portletLocalService);
		ReflectionTestUtil.setFieldValue(
			pingbackMethodImpl, "_userLocalService", _userLocalService);
		ReflectionTestUtil.setFieldValue(
			pingbackMethodImpl, "_xmlRpc", _xmlRpc);

		return pingbackMethodImpl;
	}

	private void _setUpBlogsEntryLocalService() throws Exception {
		Mockito.when(
			_blogsEntry.getEntryId()
		).thenReturn(
			_ENTRY_ID
		);

		Mockito.when(
			_blogsEntry.getGroupId()
		).thenReturn(
			_GROUP_ID
		);

		Mockito.when(
			_blogsEntry.getUrlTitle()
		).thenReturn(
			_URL_TITLE
		);

		Mockito.when(
			_blogsEntry.isAllowPingbacks()
		).thenReturn(
			true
		);

		Mockito.when(
			_blogsEntryLocalService.getEntry(
				Matchers.anyLong(), Matchers.anyString())
		).thenReturn(
			_blogsEntry
		);
	}

	private void _setUpHttpUtil() throws Exception {
		_whenHttpURLToString(
			StringBundler.concat(
				"<body><a href='http://", _TARGET_URI, "'>", _EXCERPT_BODY,
				"</a></body>"));

		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(_http);
	}

	private void _setUpInetAddressLookup() throws Exception {
		_localAddresses = new InetAddress[] {
			InetAddress.getByAddress(new byte[] {0, 0, 0, 0}),
			InetAddress.getByAddress(new byte[] {10, 0, 0, 1}),
			InetAddress.getByAddress(new byte[] {127, 0, 0, 1}),
			InetAddress.getByAddress(new byte[] {(byte)172, 16, 0, 1}),
			InetAddress.getByAddress(new byte[] {(byte)192, (byte)168, 0, 1})
		};

		InetAddress publicIpAddress = InetAddress.getByAddress(
			new byte[] {1, 2, 3, 4});

		URI sourceURI = new URI(_SOURCE_URI);

		Mockito.doReturn(
			publicIpAddress
		).when(
			_inetAddressLookup
		).getInetAddressByName(
			Mockito.eq(sourceURI.getHost())
		);

		for (InetAddress localAddress : _localAddresses) {
			Mockito.doAnswer(
				invocation -> InetAddress.getByName(
					invocation.getArgumentAt(0, String.class))
			).when(
				_inetAddressLookup
			).getInetAddressByName(
				Mockito.eq(localAddress.getHostAddress())
			);
		}
	}

	private void _setUpLanguageUtil() {
		_whenLanguageGet("pingback", _PINGBACK_USER_NAME);
		_whenLanguageGet("read-more", _READ_MORE);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	private void _setUpPingbackProperties() {
		Mockito.doReturn(
			200
		).when(
			_pingbackProperties
		).getLinkbackExcerptLength();

		Mockito.doReturn(
			true
		).when(
			_pingbackProperties
		).isPingbackEnabled();
	}

	private void _setUpPortalUtil() throws Exception {
		Mockito.when(
			_portal.getLayoutFullURL(
				Matchers.anyLong(), Matchers.eq(BlogsPortletKeys.BLOGS))
		).thenReturn(
			_LAYOUT_FULL_URL
		);

		Mockito.when(
			_portal.getPlidFromFriendlyURL(
				Matchers.eq(_COMPANY_ID), Matchers.anyString())
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			_portal.getScopeGroupId(Matchers.anyLong())
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	private void _setUpPortletIdLookup() {
		Mockito.doReturn(
			BlogsPortletKeys.BLOGS
		).when(
			_portletIdLookup
		).getPortletId(
			Mockito.anyString(), Mockito.any()
		);
	}

	private void _setUpPortletLocalService() {
		Portlet portlet = Mockito.mock(Portlet.class);

		Mockito.when(
			portlet.getFriendlyURLMapperInstance()
		).thenReturn(
			_friendlyURLMapper
		);

		Mockito.when(
			portlet.getFriendlyURLMapping()
		).thenReturn(
			_FRIENDLY_URL_MAPPING
		);

		Mockito.when(
			_portletLocalService.getPortletById(BlogsPortletKeys.BLOGS)
		).thenReturn(
			portlet
		);

		Mockito.when(
			_portletLocalService.getPortletById(
				Matchers.anyLong(), Matchers.eq(BlogsPortletKeys.BLOGS))
		).thenReturn(
			portlet
		);
	}

	private void _setUpPropsTestUtil() {
		PropsTestUtil.setProps(
			HashMapBuilder.<String, Object>put(
				PropsKeys.DNS_SECURITY_ADDRESS_TIMEOUT_SECONDS,
				String.valueOf(2)
			).put(
				PropsKeys.DNS_SECURITY_THREAD_LIMIT, String.valueOf(10)
			).build());
	}

	private void _setUpUserLocalService() throws Exception {
		Mockito.when(
			_userLocalService.getDefaultUserId(Matchers.anyLong())
		).thenReturn(
			_USER_ID
		);
	}

	private void _setUpXmlRpcUtil() {
		Fault fault = Mockito.mock(Fault.class);

		Mockito.when(
			_xmlRpc.createFault(Matchers.anyInt(), Matchers.anyString())
		).thenReturn(
			fault
		);

		XmlRpcUtil xmlRpcUtil = new XmlRpcUtil();

		xmlRpcUtil.setXmlRpc(_xmlRpc);
	}

	private void _verifyExcerpt(String excerpt) throws Exception {
		_verifySuccess();

		Mockito.verify(
			_commentManager
		).addComment(
			Matchers.anyLong(), Matchers.anyLong(), Matchers.anyString(),
			Matchers.anyLong(),
			Matchers.eq(
				StringBundler.concat(
					"[...] ", excerpt, " [...] <a href=", _SOURCE_URI, ">",
					_READ_MORE, "</a>")),
			Matchers.<ServiceContextFunction>any()
		);
	}

	private void _verifyFault(int code, String description) {
		Mockito.verify(
			_xmlRpc
		).createFault(
			code, description
		);
	}

	private void _verifySuccess() {
		Mockito.verify(
			_xmlRpc
		).createSuccess(
			"Pingback accepted"
		);
	}

	private void _whenFriendlyURLMapperPopulateParams(
		String friendlyURLPath, String name, String value) {

		Mockito.doAnswer(
			invocationOnMock -> {
				Map<String, String[]> params =
					(Map<String, String[]>)invocationOnMock.getArguments()[1];

				params.put(name, new String[] {value});

				return null;
			}
		).when(
			_friendlyURLMapper
		).populateParams(
			Matchers.eq(friendlyURLPath), Matchers.anyMap(), Matchers.anyMap()
		);
	}

	private void _whenHttpURLToString(String returnValue) throws Exception {
		Mockito.when(
			_http.URLtoString(_SOURCE_URI)
		).thenReturn(
			returnValue
		);
	}

	private void _whenLanguageGet(String key, String returnValue) {
		Mockito.when(
			_language.get((Locale)Matchers.any(), Matchers.eq(key))
		).thenReturn(
			returnValue
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final long _ENTRY_ID = RandomTestUtil.randomLong();

	private static final String _EXCERPT_BODY = RandomTestUtil.randomString();

	private static final String _FRIENDLY_URL_MAPPING =
		RandomTestUtil.randomString();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final String _LAYOUT_FULL_URL =
		RandomTestUtil.randomString();

	private static final String _PINGBACK_USER_NAME =
		RandomTestUtil.randomString();

	private static final String _READ_MORE = RandomTestUtil.randomString();

	private static final String _SOURCE_URI =
		"http://" + RandomTestUtil.randomString();

	private static final String _TARGET_URI = RandomTestUtil.randomString();

	private static final String _URL_TITLE = RandomTestUtil.randomString();

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
	private PingbackMethodImpl.InetAddressLookup _inetAddressLookup;

	@Mock
	private Language _language;

	private InetAddress[] _localAddresses;

	@Mock
	private PingbackMethodImpl.PingbackProperties _pingbackProperties;

	@Mock
	private Portal _portal;

	@Mock
	private PingbackMethodImpl.PortletIdLookup _portletIdLookup;

	@Mock
	private PortletLocalService _portletLocalService;

	@Mock
	private UserLocalService _userLocalService;

	@Mock
	private XmlRpc _xmlRpc;

}