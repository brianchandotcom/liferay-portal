/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.display.context;

import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PortalImpl;

import java.net.URI;

import org.assertj.core.api.AbstractUriAssert;
import org.assertj.core.api.Assertions;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Marco Galluzzi
 */
public class DLViewEntriesDisplayContextTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_fileVersion = Mockito.mock(FileVersion.class);

		_setUpFrameworkUtil();
		_setUpPortalUtil();

		_setUpDLURLHelperUtil();
	}

	@AfterClass
	public static void tearDownClass() {
		_dlURLHelperUtilMockedStatic.close();
		_frameworkUtilMockedStatic.close();
	}

	@Test
	public void testGetThumbnailSrcWithDoAsUserIdParameter() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		String doAsUserId = RandomTestUtil.randomString();

		themeDisplay.setDoAsUserId(doAsUserId);

		DLViewEntriesDisplayContext dlViewEntriesDisplayContext =
			_getDLViewEntriesDisplayContext(themeDisplay);

		AbstractUriAssert<?> abstractUriAssert = Assertions.assertThat(
			new URI(dlViewEntriesDisplayContext.getThumbnailSrc(_fileVersion)));

		abstractUriAssert.hasParameter("doAsUserId", doAsUserId);
	}

	@Test
	public void testGetThumbnailSrcWithoutDoAsUserIdParameter()
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setDoAsUserId(null);

		DLViewEntriesDisplayContext dlViewEntriesDisplayContext =
			_getDLViewEntriesDisplayContext(themeDisplay);

		AbstractUriAssert<?> abstractUriAssert = Assertions.assertThat(
			new URI(dlViewEntriesDisplayContext.getThumbnailSrc(_fileVersion)));

		abstractUriAssert.hasNoParameter("doAsUserId");
	}

	private static void _setUpDLURLHelperUtil() {
		_dlURLHelperUtilMockedStatic = Mockito.mockStatic(
			DLURLHelperUtil.class);

		_dlURLHelperUtilMockedStatic.when(
			() -> DLURLHelperUtil.getThumbnailSrc(
				Mockito.nullable(FileEntry.class),
				Mockito.nullable(FileVersion.class),
				Mockito.nullable(ThemeDisplay.class))
		).thenReturn(
			"http://localhost/"
		);
	}

	private static void _setUpFrameworkUtil() {
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Mockito.when(
			FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);
	}

	private static void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(new PortalImpl());
	}

	private DLViewEntriesDisplayContext _getDLViewEntriesDisplayContext(
		ThemeDisplay themeDisplay) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return new DLViewEntriesDisplayContext(
			new MockLiferayPortletRenderRequest(mockHttpServletRequest),
			new MockLiferayPortletRenderResponse());
	}

	private static MockedStatic<DLURLHelperUtil> _dlURLHelperUtilMockedStatic;
	private static FileVersion _fileVersion;
	private static MockedStatic<FrameworkUtil> _frameworkUtilMockedStatic;

}