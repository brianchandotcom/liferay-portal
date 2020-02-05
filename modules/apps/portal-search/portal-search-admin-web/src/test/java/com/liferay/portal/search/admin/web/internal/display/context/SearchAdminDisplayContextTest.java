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

package com.liferay.portal.search.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.search.index.IndexInformation;
import com.liferay.portal.util.HttpImpl;
import com.liferay.spring.mock.web.portlet.MockRenderRequest;
import com.liferay.spring.mock.web.portlet.MockRenderResponse;

import javax.portlet.RenderRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Adam Brandizzi
 */
public class SearchAdminDisplayContextTest {

	@Before
	public void setUp() {
		setUpHttpUtil();
		setUpIndexInformation();
		setUpLanguage();
		setUpParamUtil();
		setUpPortalUtil();
	}

	@Test
	public void testGetNavigationItemListWithIndexInformation() {
		SearchAdminDisplayBuilder searchAdminDisplayBuilder =
			new SearchAdminDisplayBuilder(
				_language, _portal, new MockRenderRequest(),
				new MockRenderResponse());

		searchAdminDisplayBuilder.setIndexInformation(
			Mockito.mock(IndexInformation.class));

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayBuilder.build();

		NavigationItemList navigationItemList =
			searchAdminDisplayContext.getNavigationItemList();

		Assert.assertEquals(
			navigationItemList.toString(), 3, navigationItemList.size());
	}

	@Test
	public void testGetNavigationItemListWithoutIndexInformation() {
		SearchAdminDisplayBuilder searchAdminDisplayBuilder =
			new SearchAdminDisplayBuilder(
				_language, _portal, new MockRenderRequest(),
				new MockRenderResponse());

		searchAdminDisplayBuilder.setIndexInformation(null);

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayBuilder.build();

		NavigationItemList navigationItemList =
			searchAdminDisplayContext.getNavigationItemList();

		Assert.assertEquals(
			navigationItemList.toString(), 2, navigationItemList.size());
	}

	@Test
	public void testGetTabDefault() {
		SearchAdminDisplayBuilder searchAdminDisplayBuilder =
			new SearchAdminDisplayBuilder(
				_language, _portal, new MockRenderRequest(),
				new MockRenderResponse());

		searchAdminDisplayBuilder.setIndexInformation(
			Mockito.mock(IndexInformation.class));

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayBuilder.build();

		Assert.assertEquals(
			"search-engine", searchAdminDisplayContext.getSelectedTab());
	}

	@Test
	public void testGetTabFieldMappings() {
		SearchAdminDisplayBuilder searchAdminDisplayBuilder =
			new SearchAdminDisplayBuilder(
				_language, _portal,
				getRenderRequestWithSelectedTab("field-mappings"),
				new MockRenderResponse());

		searchAdminDisplayBuilder.setIndexInformation(
			Mockito.mock(IndexInformation.class));

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayBuilder.build();

		Assert.assertEquals(
			"field-mappings", searchAdminDisplayContext.getSelectedTab());
	}

	@Test
	public void testGetTabFieldMappingsNoIndexInformation() {
		SearchAdminDisplayBuilder searchAdminDisplayBuilder =
			new SearchAdminDisplayBuilder(
				_language, _portal,
				getRenderRequestWithSelectedTab("field-mappings"),
				new MockRenderResponse());

		searchAdminDisplayBuilder.setIndexInformation(null);

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayBuilder.build();

		Assert.assertEquals(
			"search-engine", searchAdminDisplayContext.getSelectedTab());
	}

	@Test
	public void testGetTabIndexActions() {
		SearchAdminDisplayBuilder searchAdminDisplayBuilder =
			new SearchAdminDisplayBuilder(
				_language, _portal,
				getRenderRequestWithSelectedTab("index-actions"),
				new MockRenderResponse());

		searchAdminDisplayBuilder.setIndexInformation(
			Mockito.mock(IndexInformation.class));

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayBuilder.build();

		Assert.assertEquals(
			"index-actions", searchAdminDisplayContext.getSelectedTab());
	}

	@Test
	public void testGetTabSearchEngine() {
		SearchAdminDisplayBuilder searchAdminDisplayBuilder =
			new SearchAdminDisplayBuilder(
				_language, _portal,
				getRenderRequestWithSelectedTab("search-engine"),
				new MockRenderResponse());

		searchAdminDisplayBuilder.setIndexInformation(
			Mockito.mock(IndexInformation.class));

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayBuilder.build();

		Assert.assertEquals(
			"search-engine", searchAdminDisplayContext.getSelectedTab());
	}

	@Test
	public void testGetTabUnavailable() {
		SearchAdminDisplayBuilder searchAdminDisplayBuilder =
			new SearchAdminDisplayBuilder(
				_language, _portal,
				getRenderRequestWithSelectedTab(RandomTestUtil.randomString()),
				new MockRenderResponse());

		searchAdminDisplayBuilder.setIndexInformation(
			Mockito.mock(IndexInformation.class));

		SearchAdminDisplayContext searchAdminDisplayContext =
			searchAdminDisplayBuilder.build();

		Assert.assertEquals(
			"search-engine", searchAdminDisplayContext.getSelectedTab());
	}

	protected RenderRequest getRenderRequestWithSelectedTab(
		String selectedTab) {

		MockRenderRequest mockRenderRequest = new MockRenderRequest();

		mockRenderRequest.setParameter("tabs1", selectedTab);

		return mockRenderRequest;
	}

	protected void setUpHttpUtil() {
		http = new HttpImpl();
	}

	protected void setUpIndexInformation() {
		indexInformation = Mockito.mock(IndexInformation.class);

		Mockito.when(
			indexInformation.getIndexNames()
		).thenReturn(
			new String[] {"index1", "index2"}
		);

		Mockito.when(
			indexInformation.getCompanyIndexName(Matchers.anyLong())
		).thenAnswer(
			invocation -> "index" + invocation.getArguments()[0]
		);
	}

	protected void setUpLanguage() {
		_language = new LanguageImpl();
	}

	protected void setUpParamUtil() {
		PropsUtil.setProps(Mockito.mock(Props.class));
	}

	protected void setUpPortalUtil() {
		_portal = Mockito.mock(Portal.class);

		Mockito.doAnswer(
			invocation -> new String[] {
				invocation.getArgumentAt(0, String.class), StringPool.BLANK
			}
		).when(
			_portal
		).stripURLAnchor(
			Mockito.anyString(), Mockito.anyString()
		);

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	protected Http http;
	protected IndexInformation indexInformation;

	private Language _language;
	private Portal _portal;

}