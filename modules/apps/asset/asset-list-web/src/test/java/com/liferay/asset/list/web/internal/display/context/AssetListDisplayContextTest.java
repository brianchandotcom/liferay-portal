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

package com.liferay.asset.list.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.util.AssetRendererFactoryClassProvider;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

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
 * @author Mariano Álvaro Sáiz
 */
public class AssetListDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		_setUpPortalGetHttpServletRequest();
		_setUpPortalUtil();
		_setUpPortletPreferencesFactoryUtil();
	}

	@Test
	public void testGetClassName() throws Exception {
		_setUpAssetRendererFactoryClassProvider();

		AssetListDisplayContext assetListDisplayContext =
			new AssetListDisplayContext(
				_assetRendererFactoryClassProvider,
				Mockito.mock(RenderRequest.class),
				Mockito.mock(RenderResponse.class));

		Class<? extends AssetRendererFactory> clazz =
			_assetRendererFactory.getClass();

		Assert.assertEquals(
			clazz.getSimpleName(),
			assetListDisplayContext.getClassName(_assetRendererFactory));
	}

	private void _setUpAssetRendererFactoryClassProvider() {
		Mockito.doReturn(
			_assetRendererFactory.getClass()
		).when(
			_assetRendererFactoryClassProvider
		).getClass(
			_assetRendererFactory
		);
	}

	private HttpServletRequest _setUpPortalGetHttpServletRequest() {
		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.doReturn(
			httpServletRequest
		).when(
			_portal
		).getHttpServletRequest(
			Matchers.any(PortletRequest.class)
		);

		return httpServletRequest;
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	private void _setUpPortletPreferencesFactoryUtil() {
		PortletPreferencesFactoryUtil portletPreferencesFactoryUtil =
			new PortletPreferencesFactoryUtil();

		portletPreferencesFactoryUtil.setPortletPreferencesFactory(
			Mockito.mock(PortletPreferencesFactory.class));
	}

	@Mock
	private AssetRendererFactory<?> _assetRendererFactory;

	@Mock
	private AssetRendererFactoryClassProvider
		_assetRendererFactoryClassProvider;

	@Mock
	private Portal _portal;

}