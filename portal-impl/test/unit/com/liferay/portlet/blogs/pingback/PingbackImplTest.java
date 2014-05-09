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

package com.liferay.portlet.blogs.pingback;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalService;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalService;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.util.Locale;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.CallsRealMethods;
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
	BlogsEntryLocalServiceUtil.class, PortletLocalServiceUtil.class,
	PropsValues.class, UserLocalServiceUtil.class
})
@RunWith(PowerMockRunner.class)
public class PingbackImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpBlogsEntry();
		setUpLanguage();
		setUpPortal();
		setUpPortlet();
		setUpUser();
	}

	@Test(expected = DuplicateCommentException.class)
	public void testAddDuplicatePingback() throws Exception {
		Mockito.doThrow(
			DuplicateCommentException.class
		).when(
			_pingbackComments
		).addComment(
			Matchers.anyLong(), Matchers.anyLong(), Matchers.anyString(),
			Matchers.anyLong(), Matchers.anyString(),
			(Function<String, ServiceContext>)Matchers.any());

		execute();
	}

	@Test
	public void testAddPingback() throws Exception {
		Mockito.when(
			_excerptExtractor.getExcerpt()
		).thenReturn(
			"__excerpt__"
		);

		execute();

		Mockito.verify(
			_pingbackComments
		).addComment(
			Matchers.eq(USER_ID), Matchers.eq(GROUP_ID),
			Matchers.eq(BlogsEntry.class.getName()), Matchers.eq(ENTRY_ID),
			Matchers.eq(
				"[...] __excerpt__ [...]" +
					" [url=__sourceURI__]__read_more__[/url]"),
			(Function<String, ServiceContext>)Matchers.any());
	}

	@Test(expected = PingbackDisabledException.class)
	public void testAddPingbackWhenBlogEntryDisablesPingbacks()
		throws Exception {

		when(
			_blogsEntry.isAllowPingbacks()
		).thenReturn(
			false
		);

		execute();
	}

	@Test(expected = PingbackDisabledException.class)
	public void testAddPingbackWhenPortalPropertyDisablesPingbacks()
		throws Exception {

		boolean previous = PropsValues.BLOGS_PINGBACK_ENABLED;

		Whitebox.setInternalState(
			PropsValues.class, "BLOGS_PINGBACK_ENABLED", false);

		try {
			execute();
		}
		finally {
			Whitebox.setInternalState(
				PropsValues.class, "BLOGS_PINGBACK_ENABLED", previous);
		}
	}

	@Test
	public void testAddPingbackWithFriendlyURL() throws Exception {
		long plid = ServiceTestUtil.randomLong();

		when(
			_portal.getPlidFromFriendlyURL(COMPANY_ID, "/__blogs__")
		).thenReturn(
			plid
		);

		long groupId = ServiceTestUtil.randomLong();

		when(
			_portal.getScopeGroupId(plid)
		).thenReturn(
			groupId
		);

		whenFriendlyURLMapperPopulateParamsPut(
			"/__remainder-of-the-friendly-url__", "urlTitle", "__urlTitle__");

		String friendlyURL =
			"http://liferay.com/__blogs__/-/__remainder-of-the-friendly-url__";

		execute(friendlyURL);

		Mockito.verify(
			_blogsEntryLocalService
		).getEntry(
			groupId, "__urlTitle__"
		);
	}

	@Test
	public void testAddPingbackWithFriendlyURLParameterEntryId()
		throws Exception {

		doTestAddPingbackWithFriendlyURLParameterEntryId(null);
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

		doTestAddPingbackWithFriendlyURLParameterEntryId(namespace);
	}

	@Test(expected = InvalidSourceURIException.class)
	public void testAddPingbackWithInvalidSourceURI() throws Exception {
		Mockito.doThrow(
			new InvalidSourceURIException()
		).when(
			_excerptExtractor
		).validateSource();

		execute();
	}

	@Test(expected = UnavailableSourceURIException.class)
	public void testAddPingbackWithUnavailableSourceURI() throws Exception {
		Mockito.doThrow(
			new UnavailableSourceURIException(new NullPointerException())
		).when(
			_excerptExtractor
		).validateSource();

		execute();
	}

	@Test(expected = NullPointerException.class)
	public void testAddPingbackWithUnexpectedException() throws Exception {
		Mockito.doThrow(
			new NullPointerException()
		).when(
			_excerptExtractor
		).getExcerpt();

		execute();
	}

	@Test
	public void testSetSourceAndTargetURIs() throws Exception {
		PingbackImpl pingback = new PingbackImpl(
			_pingbackComments, _excerptExtractor);

		pingback.setSourceUri("__sourceURI__");
		pingback.setTargetUri("__targetURI__");

		Mockito.verify(
			_excerptExtractor
		).setSourceUri(
			"__sourceURI__"
		);
		Mockito.verify(
			_excerptExtractor
		).setTargetUri(
			"__targetURI__"
		);
	}

	protected void doTestAddPingbackWithFriendlyURLParameterEntryId(
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

		long entryId = ServiceTestUtil.randomLong();

		whenFriendlyURLMapperPopulateParamsPut(
			"", name, String.valueOf(entryId));

		execute();

		Mockito.verify(
			_blogsEntryLocalService
		).getEntry(
			entryId
		);
	}

	protected void execute() throws Exception {
		execute("http://liferay.com");
	}

	protected void execute(String targetURI) throws Exception {
		PingbackImpl pingback = new PingbackImpl(
			_pingbackComments, _excerptExtractor);

		pingback.setSourceUri("__sourceURI__");
		pingback.setTargetUri(targetURI);

		pingback.addPingback(COMPANY_ID);
	}

	protected void setUpBlogsEntry() throws Exception {
		when(
			_blogsEntry.getEntryId()
		).thenReturn(
			ENTRY_ID
		);

		when(
			_blogsEntry.getGroupId()
		).thenReturn(
			GROUP_ID
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

		mockStatic(BlogsEntryLocalServiceUtil.class, new CallsRealMethods());

		stub(
			method(BlogsEntryLocalServiceUtil.class, "getService")
		).toReturn(
			_blogsEntryLocalService
		);
	}

	protected void setUpLanguage() {
		whenLanguageGetThenReturn("read-more", "__read_more__");

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	protected void setUpPortal() throws PortalException, SystemException {
		when(
			_portal.getPlidFromFriendlyURL(
				Matchers.eq(COMPANY_ID), Matchers.anyString())
		).thenReturn(
			ServiceTestUtil.randomLong()
		);

		when(
			_portal.getScopeGroupId(Matchers.anyLong())
		).thenReturn(
			ServiceTestUtil.randomLong()
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	protected void setUpPortlet() throws SystemException {
		Portlet portlet = Mockito.mock(Portlet.class);

		when(
			portlet.getFriendlyURLMapperInstance()
		).thenReturn(
			_friendlyURLMapper
		);

		PortletLocalService portletLocalService = Mockito.mock(
			PortletLocalService.class);

		when(
			portletLocalService.getPortletById(PortletKeys.BLOGS)
		).thenReturn(
			portlet
		);

		mockStatic(PortletLocalServiceUtil.class, new CallsRealMethods());

		stub(
			method(PortletLocalServiceUtil.class, "getService")
		).toReturn(
			portletLocalService
		);
	}

	protected void setUpUser() throws PortalException, SystemException {
		UserLocalService userLocalService = Mockito.mock(
			UserLocalService.class);

		when(
			userLocalService.getDefaultUserId(Matchers.anyLong())
		).thenReturn(
			USER_ID
		);

		mockStatic(UserLocalServiceUtil.class, new CallsRealMethods());

		stub(
			method(UserLocalServiceUtil.class, "getService")
		).toReturn(
			userLocalService
		);
	}

	protected void whenFriendlyURLMapperPopulateParamsPut(
		String friendlyURLPath, final String name, final String value) {

		Mockito.doAnswer(
			new Answer<Void>() {
				@Override
				public Void answer(InvocationOnMock invocationOnMock)
					throws Throwable {

					Map<String, String[]> params = (Map<String, String[]>)
						invocationOnMock.getArguments()[1];

					params.put(name, new String[]{value});

					return null;
				}
			}
		).when(
			_friendlyURLMapper
		).populateParams(
			Matchers.eq(friendlyURLPath), Matchers.anyMap(), Matchers.anyMap()
		);
	}

	protected void whenLanguageGetThenReturn(String key, String toBeReturned) {
		when(
			_language.get((Locale)Matchers.any(), Matchers.eq(key))
		).thenReturn(
			toBeReturned
		);
	}

	private static final long COMPANY_ID = ServiceTestUtil.randomLong();

	private static final long ENTRY_ID = ServiceTestUtil.randomLong();

	private static final long GROUP_ID = ServiceTestUtil.randomLong();

	private static final long USER_ID = ServiceTestUtil.randomLong();

	@Mock
	private BlogsEntry _blogsEntry;

	@Mock
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Mock
	private PingbackExcerptExtractor _excerptExtractor;

	@Mock
	private FriendlyURLMapper _friendlyURLMapper;

	@Mock
	private Language _language;

	@Mock
	private PingbackComments _pingbackComments;

	@Mock
	private Portal _portal;

}