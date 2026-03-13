/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.PortletPreferencesSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockPortletRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.PortletPreferencesImpl;

import jakarta.portlet.PortletPreferences;
import jakarta.portlet.PortletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class DLConfigurationActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group1 = GroupTestUtil.addGroup();
		_group2 = GroupTestUtil.addGroup();

		_repository = PortletFileRepositoryUtil.addPortletRepository(
			_group1.getGroupId(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(_group1.getGroupId()));
	}

	@Test
	public void testPostProcess() throws Exception {
		_testPostProcessWithDifferentGroupInThemeDisplay();
		_testPostProcessWithNoGroupInThemeDisplay();
		_testPostProcessWithSameGroupInThemeDisplay();
	}

	private void _assertPortletPreferences(
		String expectedSelectedGroupExternalReferenceCode,
		String expectedSelectedRepositoryExternalReferenceCode,
		PortletPreferences portletPreferences) {

		Assert.assertEquals(
			expectedSelectedGroupExternalReferenceCode,
			portletPreferences.getValue(
				"selectedGroupExternalReferenceCode", null));
		Assert.assertEquals(
			expectedSelectedRepositoryExternalReferenceCode,
			portletPreferences.getValue(
				"selectedRepositoryExternalReferenceCode", null));
	}

	private PortletPreferences _getPortletPreferences(
			UnsafeSupplier<ThemeDisplay, Exception> unsafeSupplier)
		throws Exception {

		PortletPreferences portletPreferences = new PortletPreferencesImpl();

		portletPreferences.setValue(
			"rootFolderId",
			String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));
		portletPreferences.setValue(
			"selectedRepositoryId",
			String.valueOf(_repository.getRepositoryId()));

		MockPortletRequest portletRequest = new MockPortletRequest();

		portletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, unsafeSupplier.get());

		ReflectionTestUtil.invoke(
			_configurationAction, "postProcess",
			new Class<?>[] {long.class, PortletRequest.class, Settings.class},
			TestPropsValues.getCompanyId(), portletRequest,
			_getSettings(portletPreferences));

		return portletPreferences;
	}

	private Settings _getSettings(PortletPreferences portletPreferences) {
		return new Settings() {

			@Override
			public ModifiableSettings getModifiableSettings() {
				return null;
			}

			@Override
			public Settings getParentSettings() {
				return new PortletPreferencesSettings(portletPreferences);
			}

			@Override
			public String getValue(String key, String defaultValue) {
				return StringPool.BLANK;
			}

			@Override
			public String[] getValues(String key, String[] defaultValue) {
				return new String[0];
			}

		};
	}

	private ThemeDisplay _getThemeDisplay(Group group) {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setScopeGroupId(group.getGroupId());

		return themeDisplay;
	}

	private void _testPostProcessWithDifferentGroupInThemeDisplay()
		throws Exception {

		_assertPortletPreferences(
			_group1.getExternalReferenceCode(),
			_repository.getExternalReferenceCode(),
			_getPortletPreferences(() -> _getThemeDisplay(_group2)));
	}

	private void _testPostProcessWithNoGroupInThemeDisplay() throws Exception {
		_assertPortletPreferences(
			_group1.getExternalReferenceCode(),
			_repository.getExternalReferenceCode(),
			_getPortletPreferences(() -> new ThemeDisplay()));
	}

	private void _testPostProcessWithSameGroupInThemeDisplay()
		throws Exception {

		_assertPortletPreferences(
			null, _repository.getExternalReferenceCode(),
			_getPortletPreferences(() -> _getThemeDisplay(_group1)));
	}

	@Inject(filter = "jakarta.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY)
	private ConfigurationAction _configurationAction;

	@DeleteAfterTestRun
	private Group _group1;

	@DeleteAfterTestRun
	private Group _group2;

	private Repository _repository;

}