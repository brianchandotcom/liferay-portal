/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.taglib.internal.display.context;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.toolbar.contributor.PortletToolbarContributor;
import com.liferay.portal.kernel.servlet.taglib.ui.Menu;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.portlet.PortletRequest;
import jakarta.portlet.PortletResponse;
import jakarta.portlet.PortletURL;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Jürgen Kappler
 */
public class ItemSelectorRepositoryEntryManagementToolbarDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		_snapshot = _createSnapshot();

		_snapshotAutoCloseable =
			ReflectionTestUtil.setFieldValueWithAutoCloseable(
				ItemSelectorRepositoryEntryManagementToolbarDisplayContext.
					class,
				"_dlPortletToolbarContributorSnapshot", _snapshot);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_portletPreferencesFactoryUtilMockedStatic.close();
		_portletURLUtilMockedStatic.close();
		_snapshotAutoCloseable.close();
	}

	@Test
	@TestInfo("LPD-92720")
	public void testGetCreationMenu() {
		_testGetCreationMenuWhenContributorThrowsException();
		_testGetCreationMenuWithExistingCurrentURL();
		_testGetCreationMenuWithMissingCurrentURL();
		_testGetCreationMenuWithoutPortletURL();
	}

	@SuppressWarnings("unchecked")
	private static Snapshot<PortletToolbarContributor> _createSnapshot() {
		return Mockito.mock(Snapshot.class);
	}

	private PortletURL _getPortletURL(String url) {
		PortletURL portletURL = Mockito.mock(PortletURL.class);

		Mockito.when(
			portletURL.toString()
		).thenReturn(
			url
		);

		return portletURL;
	}

	private void _invokeGetCreationMenu(
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest,
		PortletToolbarContributor portletToolbarContributor,
		PortletURL portletURL) {

		Mockito.when(
			_snapshot.get()
		).thenReturn(
			portletToolbarContributor
		);

		HttpServletRequest httpServletRequest =
			mockLiferayPortletRenderRequest.getHttpServletRequest();

		if (portletURL != null) {
			httpServletRequest.setAttribute(
				"liferay-item-selector:repository-entry-browser:portletURL",
				portletURL);
		}

		ItemSelectorRepositoryEntryManagementToolbarDisplayContext
			itemSelectorRepositoryEntryManagementToolbarDisplayContext =
				new ItemSelectorRepositoryEntryManagementToolbarDisplayContext(
					httpServletRequest, mockLiferayPortletRenderRequest,
					Mockito.mock(LiferayPortletResponse.class),
					Mockito.mock(RepositoryEntryBrowserDisplayContext.class));

		itemSelectorRepositoryEntryManagementToolbarDisplayContext.
			getCreationMenu();
	}

	private void _testGetCreationMenuWhenContributorThrowsException() {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new TestMockLiferayPortletRenderRequest();

		TestPortletToolbarContributor testPortletToolbarContributor =
			new TestPortletToolbarContributor(true);

		Assert.assertThrows(
			RuntimeException.class,
			() -> _invokeGetCreationMenu(
				mockLiferayPortletRenderRequest, testPortletToolbarContributor,
				_getPortletURL(RandomTestUtil.randomString())));

		Assert.assertNull(
			mockLiferayPortletRenderRequest.getAttribute(WebKeys.CURRENT_URL));
	}

	private void _testGetCreationMenuWithExistingCurrentURL() {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new TestMockLiferayPortletRenderRequest();

		TestPortletToolbarContributor testPortletToolbarContributor =
			new TestPortletToolbarContributor(false);

		String currentURL = RandomTestUtil.randomString();

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.CURRENT_URL, currentURL);

		_invokeGetCreationMenu(
			mockLiferayPortletRenderRequest, testPortletToolbarContributor,
			_getPortletURL(RandomTestUtil.randomString()));

		Assert.assertEquals(
			currentURL,
			mockLiferayPortletRenderRequest.getAttribute(WebKeys.CURRENT_URL));
		Assert.assertEquals(
			currentURL, testPortletToolbarContributor.getCapturedCurrentURL());
	}

	private void _testGetCreationMenuWithMissingCurrentURL() {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new TestMockLiferayPortletRenderRequest();

		TestPortletToolbarContributor testPortletToolbarContributor =
			new TestPortletToolbarContributor(false);

		String currentURL = RandomTestUtil.randomString();

		_invokeGetCreationMenu(
			mockLiferayPortletRenderRequest, testPortletToolbarContributor,
			_getPortletURL(currentURL));

		Assert.assertNull(
			mockLiferayPortletRenderRequest.getAttribute(WebKeys.CURRENT_URL));

		Assert.assertEquals(
			currentURL, testPortletToolbarContributor.getCapturedCurrentURL());
	}

	private void _testGetCreationMenuWithoutPortletURL() {
		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new TestMockLiferayPortletRenderRequest();

		TestPortletToolbarContributor testPortletToolbarContributor =
			new TestPortletToolbarContributor(false);

		_invokeGetCreationMenu(
			mockLiferayPortletRenderRequest, testPortletToolbarContributor,
			null);

		Assert.assertNull(
			mockLiferayPortletRenderRequest.getAttribute(WebKeys.CURRENT_URL));

		Assert.assertNull(
			testPortletToolbarContributor.getCapturedCurrentURL());
	}

	private static final MockedStatic<PortletPreferencesFactoryUtil>
		_portletPreferencesFactoryUtilMockedStatic = Mockito.mockStatic(
			PortletPreferencesFactoryUtil.class);
	private static final MockedStatic<PortletURLUtil>
		_portletURLUtilMockedStatic = Mockito.mockStatic(PortletURLUtil.class);
	private static Snapshot<PortletToolbarContributor> _snapshot;
	private static AutoCloseable _snapshotAutoCloseable;

	private static class TestMockLiferayPortletRenderRequest
		extends MockLiferayPortletRenderRequest {

		@Override
		public void removeAttribute(String name) {
			HttpServletRequest httpServletRequest = getHttpServletRequest();

			httpServletRequest.removeAttribute(name);
		}

	}

	private static class TestPortletToolbarContributor
		implements PortletToolbarContributor {

		public TestPortletToolbarContributor(boolean throwException) {
			_throwException = throwException;
		}

		public Object getCapturedCurrentURL() {
			return _capturedCurrentURL;
		}

		@Override
		public List<Menu> getPortletTitleMenus(
			PortletRequest portletRequest, PortletResponse portletResponse) {

			_capturedCurrentURL = portletRequest.getAttribute(
				WebKeys.CURRENT_URL);

			if (_throwException) {
				throw new RuntimeException();
			}

			return Collections.emptyList();
		}

		private Object _capturedCurrentURL;
		private final boolean _throwException;

	}

}