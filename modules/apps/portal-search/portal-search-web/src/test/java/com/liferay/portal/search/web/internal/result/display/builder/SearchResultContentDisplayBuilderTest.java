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

package com.liferay.portal.search.web.internal.result.display.builder;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.search.web.internal.result.display.context.SearchResultContentDisplayContext;
import com.liferay.portlet.internal.MutableRenderParametersImpl;

import java.util.HashMap;
import java.util.HashSet;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
public class SearchResultContentDisplayBuilderTest {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpAssetEntry();
		setUpAssetRenderer();
		setUpAssetRendererFactory();
		setUpPortal();
		setUpRenderResponse();
	}

	@Test
	public void testEditPermission() throws Exception {
		String title = RandomTestUtil.randomString();

		Mockito.doReturn(
			title
		).when(
			_assetRenderer
		).getTitle(
			Mockito.anyObject()
		);

		String editPortletURLString = RandomTestUtil.randomString();

		Mockito.doReturn(
			editPortletURLString
		).when(
			_editPortletURL
		).toString();

		SearchResultContentDisplayContext searchResultContentDisplayContext =
			buildDisplayContext();

		Assert.assertTrue(
			searchResultContentDisplayContext.hasEditPermission());

		assertIcon(
			title, editPortletURLString, searchResultContentDisplayContext);
	}

	@Test
	public void testEditPermissionFalse() throws Exception {
		Mockito.doReturn(
			false
		).when(
			_assetRenderer
		).hasEditPermission(
			Mockito.anyObject()
		);

		SearchResultContentDisplayContext searchResultContentDisplayContext =
			buildDisplayContext();

		Assert.assertFalse(
			searchResultContentDisplayContext.hasEditPermission());

		assertIconMissing(searchResultContentDisplayContext);

		assertAssetDisplay(searchResultContentDisplayContext);
	}

	@Test
	public void testVisible() throws Exception {
		SearchResultContentDisplayContext searchResultContentDisplayContext =
			buildDisplayContext();

		Assert.assertTrue(searchResultContentDisplayContext.isVisible());

		assertAssetDisplay(searchResultContentDisplayContext);
	}

	@Test
	public void testVisibleFalseFromEntry() throws Exception {
		Mockito.doReturn(
			false
		).when(
			_assetEntry
		).isVisible();

		SearchResultContentDisplayContext searchResultContentDisplayContext =
			buildDisplayContext();

		Assert.assertFalse(searchResultContentDisplayContext.isVisible());
	}

	@Test
	public void testVisibleFalseFromViewPermission() throws Exception {
		Mockito.doReturn(
			false
		).when(
			_assetRenderer
		).hasViewPermission(
			Mockito.anyObject()
		);

		SearchResultContentDisplayContext searchResultContentDisplayContext =
			buildDisplayContext();

		Assert.assertFalse(searchResultContentDisplayContext.isVisible());
	}

	protected void assertAssetDisplay(
		SearchResultContentDisplayContext searchResultContentDisplayContext) {

		Assert.assertSame(
			_assetEntry, searchResultContentDisplayContext.getAssetEntry());

		Assert.assertSame(
			_assetRenderer,
			searchResultContentDisplayContext.getAssetRenderer());

		Assert.assertSame(
			_assetRendererFactory,
			searchResultContentDisplayContext.getAssetRendererFactory());
	}

	protected void assertIcon(
		String editTarget, String urlString,
		SearchResultContentDisplayContext searchResultContentDisplayContext) {

		Assert.assertEquals(
			editTarget, searchResultContentDisplayContext.getIconEditTarget());

		Assert.assertEquals(
			urlString, searchResultContentDisplayContext.getIconURLString());
	}

	protected void assertIconMissing(
		SearchResultContentDisplayContext searchResultContentDisplayContext) {

		Assert.assertNull(
			searchResultContentDisplayContext.getIconEditTarget());

		Assert.assertNull(searchResultContentDisplayContext.getIconURLString());
	}

	protected SearchResultContentDisplayContext buildDisplayContext()
		throws Exception {

		SearchResultContentDisplayBuilder searchResultContentDisplayBuilder =
			new SearchResultContentDisplayBuilder();

		searchResultContentDisplayBuilder.setAssetEntryId(
			RandomTestUtil.randomLong());
		searchResultContentDisplayBuilder.setAssetRendererFactoryLookup(
			_assetRendererFactoryLookup);
		searchResultContentDisplayBuilder.setLocale(LocaleUtil.US);
		searchResultContentDisplayBuilder.setPermissionChecker(
			_permissionChecker);
		searchResultContentDisplayBuilder.setPortal(_portal);
		searchResultContentDisplayBuilder.setRenderRequest(_renderRequest);
		searchResultContentDisplayBuilder.setRenderResponse(_renderResponse);
		searchResultContentDisplayBuilder.setType(
			RandomTestUtil.randomString());

		return searchResultContentDisplayBuilder.build();
	}

	protected void setUpAssetEntry() {
		Mockito.doReturn(
			_assetRenderer
		).when(
			_assetEntry
		).getAssetRenderer();

		Mockito.doReturn(
			true
		).when(
			_assetEntry
		).isVisible();
	}

	protected void setUpAssetRenderer() throws Exception, PortalException {
		Mockito.doReturn(
			_editPortletURL
		).when(
			_assetRenderer
		).getURLEdit(
			Mockito.anyObject(), Mockito.anyObject(), Mockito.anyObject(),
			Mockito.any(PortletURL.class)
		);

		Mockito.doReturn(
			true
		).when(
			_assetRenderer
		).hasEditPermission(
			Mockito.anyObject()
		);

		Mockito.doReturn(
			true
		).when(
			_assetRenderer
		).hasViewPermission(
			Mockito.anyObject()
		);
	}

	protected void setUpAssetRendererFactory() throws PortalException {
		Mockito.doReturn(
			_assetEntry
		).when(
			_assetRendererFactory
		).getAssetEntry(
			Mockito.anyLong()
		);

		Mockito.doReturn(
			_assetRendererFactory
		).when(
			_assetRendererFactoryLookup
		).getAssetRendererFactoryByType(
			Mockito.anyString()
		);
	}

	protected void setUpPortal() {
		Mockito.doReturn(
			Mockito.mock(LiferayPortletRequest.class)
		).when(
			_portal
		).getLiferayPortletRequest(
			Mockito.anyObject()
		);

		Mockito.doReturn(
			Mockito.mock(LiferayPortletResponse.class)
		).when(
			_portal
		).getLiferayPortletResponse(
			Mockito.anyObject()
		);
	}

	protected void setUpRenderResponse() {
		Mockito.doReturn(
			_renderPortletURL
		).when(
			_renderResponse
		).createRenderURL();

		Mockito.doReturn(
			new MutableRenderParametersImpl(new HashMap<>(), new HashSet<>())
		).when(
			_renderPortletURL
		).getRenderParameters();
	}

	@Mock
	private AssetEntry _assetEntry;

	@Mock
	private AssetRenderer<?> _assetRenderer;

	@Mock
	@SuppressWarnings("rawtypes")
	private AssetRendererFactory _assetRendererFactory;

	@Mock
	private AssetRendererFactoryLookup _assetRendererFactoryLookup;

	@Mock
	private PortletURL _editPortletURL;

	@Mock
	private PermissionChecker _permissionChecker;

	@Mock
	private Portal _portal;

	@Mock
	private PortletURL _renderPortletURL;

	@Mock
	private RenderRequest _renderRequest;

	@Mock
	private RenderResponse _renderResponse;

}