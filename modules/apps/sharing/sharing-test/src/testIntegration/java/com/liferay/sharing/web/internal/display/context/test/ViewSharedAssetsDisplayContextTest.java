/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.web.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.util.BundleUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.renderer.SharingEntryEditRenderer;
import com.liferay.sharing.renderer.SharingEntryViewRenderer;
import com.liferay.sharing.security.permission.SharingEntryAction;
import com.liferay.sharing.service.SharingEntryLocalService;

import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Constructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Juanjo Fernandez
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class ViewSharedAssetsDisplayContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			ViewSharedAssetsDisplayContextTest.class);

		Bundle sharingWebBundle = BundleUtil.getBundle(
			bundle.getBundleContext(), "com.liferay.sharing.web");

		_constructor = sharingWebBundle.loadClass(
			"com.liferay.sharing.web.internal.display.context." +
				"ViewSharedAssetsDisplayContext"
		).getConstructors()[0];
	}

	@Before
	public void setUp() throws Exception {
		_fromUser = TestPropsValues.getUser();
		_toUser = UserTestUtil.addUser();
	}

	@Test
	public void testGetSharingEntryRowPortletURLReturnsBlankWhenInterpreterHasNoViewRenderer()
		throws Exception {

		SharingEntry sharingEntry = _addSharingEntry(
			TestPropsValues.getGroupId());

		Assert.assertEquals(
			StringPool.BLANK,
			ReflectionTestUtil.invoke(
				_newDisplayContext(_interpreterWithNullViewRenderer()),
				"getSharingEntryRowPortletURL",
				new Class<?>[] {SharingEntry.class}, sharingEntry));
	}

	@Test
	public void testIsSharingEntryVisibleForCompanyScopedSharingEntry()
		throws Exception {

		SharingEntry sharingEntry = _addSharingEntry(0);

		Assert.assertTrue(
			(boolean)ReflectionTestUtil.invoke(
				_newDisplayContext(_interpreterWithNullViewRenderer()),
				"isSharingEntryVisible", new Class<?>[] {SharingEntry.class},
				sharingEntry));
	}

	private SharingEntry _addSharingEntry(long groupId) throws Exception {
		return _sharingEntryLocalService.addSharingEntry(
			null, _fromUser.getUserId(), 0, 0, _toUser.getUserId(),
			_classNameLocalService.getClassNameId(User.class),
			_toUser.getUserId(), groupId, true,
			Arrays.asList(SharingEntryAction.VIEW), null,
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), _fromUser.getUserId()));
	}

	private HttpServletRequest _getHttpServletRequest() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_fromUser.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setUser(_fromUser);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	private Function<SharingEntry, SharingEntryInterpreter>
		_interpreterWithNullViewRenderer() {

		return sharingEntry -> new SharingEntryInterpreter() {

			@Override
			public String getAssetTypeTitle(
				SharingEntry sharingEntry, Locale locale) {

				return StringPool.BLANK;
			}

			@Override
			public SharingEntryEditRenderer getSharingEntryEditRenderer() {
				return null;
			}

			@Override
			public SharingEntryViewRenderer getSharingEntryViewRenderer() {
				return null;
			}

			@Override
			public String getTitle(SharingEntry sharingEntry, Locale locale) {
				return StringPool.BLANK;
			}

			@Override
			public Map<Locale, String> getTitleMap(SharingEntry sharingEntry) {
				return new HashMap<>();
			}

			@Override
			public boolean isVisible(SharingEntry sharingEntry)
				throws PortalException {

				return true;
			}

		};
	}

	private Object _newDisplayContext(
			Function<SharingEntry, SharingEntryInterpreter>
				sharingEntryInterpreterFunction)
		throws Exception {

		HttpServletRequest httpServletRequest = _getHttpServletRequest();

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest(
				(MockHttpServletRequest)httpServletRequest);

		mockLiferayPortletRenderRequest.setAttribute(
			StringBundler.concat(
				mockLiferayPortletRenderRequest.getPortletName(), "-",
				WebKeys.CURRENT_PORTLET_URL),
			new MockLiferayPortletURL());

		return _constructor.newInstance(
			_companyLocalService, _groupLocalService, null,
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse(), null,
			_sharingConfigurationFactory, null, null,
			sharingEntryInterpreterFunction, _sharingEntryLocalService, null);
	}

	private static Constructor<?> _constructor;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	private User _fromUser;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private SharingConfigurationFactory _sharingConfigurationFactory;

	@Inject
	private SharingEntryLocalService _sharingEntryLocalService;

	private User _toUser;

}