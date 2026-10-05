/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.theme;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyFactory;

import jakarta.portlet.PortletPreferences;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Joshua Cords
 */
public class PortletDisplayTest {

	@Before
	public void setUp() {
		_layout = Mockito.mock(Layout.class);

		Mockito.when(
			_layout.isTypeAssetDisplay()
		).thenReturn(
			false
		);

		Mockito.when(
			_layout.isTypeContent()
		).thenReturn(
			true
		);

		_portletPreferences = Mockito.mock(PortletPreferences.class);

		Mockito.when(
			_portletPreferences.getValue(
				"portletSetupPortletDecoratorId", StringPool.BLANK)
		).thenReturn(
			StringPool.BLANK
		);

		_themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.when(
			_themeDisplay.getLayout()
		).thenReturn(
			_layout
		);
	}

	@Test
	public void testIsShowPortletTitle() {
		PortletDisplay portletDisplay = new PortletDisplay();

		portletDisplay.setPortletDecoratorId("borderless");
		portletDisplay.setPortletPreferences(_portletPreferences);
		portletDisplay.setThemeDisplay(_themeDisplay);

		Assert.assertFalse(portletDisplay.isShowPortletTitle());
	}

	@Test
	public void testIsWebDAVEnabled() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(ProxyFactory.newDummyInstance(Portal.class));

		AtomicInteger atomicInteger = new AtomicInteger();

		PortletDisplay portletDisplay = new PortletDisplay();

		portletDisplay.setWebDAVEnabledSupplier(
			() -> {
				atomicInteger.incrementAndGet();

				return true;
			});

		PortletDisplay copyPortletDisplay = new PortletDisplay();

		portletDisplay.copyTo(copyPortletDisplay);

		portletDisplay.recycle();

		portletDisplay.copyFrom(copyPortletDisplay);

		Assert.assertEquals(0, atomicInteger.get());

		Assert.assertTrue(portletDisplay.isWebDAVEnabled());
		Assert.assertTrue(portletDisplay.isWebDAVEnabled());
		Assert.assertEquals(1, atomicInteger.get());

		copyPortletDisplay.recycle();

		Assert.assertFalse(copyPortletDisplay.isWebDAVEnabled());
		Assert.assertEquals(1, atomicInteger.get());
	}

	private Layout _layout;
	private PortletPreferences _portletPreferences;
	private ThemeDisplay _themeDisplay;

}