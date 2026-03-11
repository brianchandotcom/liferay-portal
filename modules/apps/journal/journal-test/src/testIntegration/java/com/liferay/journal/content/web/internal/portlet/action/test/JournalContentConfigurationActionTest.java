/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.content.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.portlet.PortletConfig;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class JournalContentConfigurationActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
	}

	@Test
	public void testProcessAction() throws Exception {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			JournalContentPortletKeys.JOURNAL_CONTENT + "_INSTANCE_" +
				RandomTestUtil.randomString());

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, null);

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		JournalArticle journalArticle1 = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			_portal.getClassNameId(JournalArticle.class.getName()),
			journalArticle1.getResourcePrimKey());

		mockLiferayPortletActionRequest.setParameter(
			"preferences--assetEntryId--",
			String.valueOf(assetEntry.getEntryId()));

		_configurationAction.processAction(
			portletConfig, mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		Map<String, String[]> portletPreferencesMap =
			(Map<String, String[]>)mockLiferayPortletActionRequest.getAttribute(
				WebKeys.PORTLET_PREFERENCES_MAP);

		Assert.assertArrayEquals(
			new String[] {journalArticle1.getExternalReferenceCode()},
			portletPreferencesMap.get("articleExternalReferenceCode"));
		Assert.assertNull(
			portletPreferencesMap.get("groupExternalReferenceCode"));

		JournalArticle journalArticle2 = JournalTestUtil.addArticle(
			_company.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			_portal.getClassNameId(JournalArticle.class.getName()),
			journalArticle2.getResourcePrimKey());

		mockLiferayPortletActionRequest.setParameter(
			"preferences--assetEntryId--",
			String.valueOf(assetEntry.getEntryId()));

		_configurationAction.processAction(
			portletConfig, mockLiferayPortletActionRequest,
			new MockLiferayPortletActionResponse());

		portletPreferencesMap =
			(Map<String, String[]>)mockLiferayPortletActionRequest.getAttribute(
				WebKeys.PORTLET_PREFERENCES_MAP);

		Assert.assertArrayEquals(
			new String[] {journalArticle2.getExternalReferenceCode()},
			portletPreferencesMap.get("articleExternalReferenceCode"));

		Group companyGroup = _company.getGroup();

		Assert.assertArrayEquals(
			new String[] {companyGroup.getExternalReferenceCode()},
			portletPreferencesMap.get("groupExternalReferenceCode"));
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.US);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "jakarta.portlet.name=" + JournalContentPortletKeys.JOURNAL_CONTENT
	)
	private ConfigurationAction _configurationAction;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Portal _portal;

}