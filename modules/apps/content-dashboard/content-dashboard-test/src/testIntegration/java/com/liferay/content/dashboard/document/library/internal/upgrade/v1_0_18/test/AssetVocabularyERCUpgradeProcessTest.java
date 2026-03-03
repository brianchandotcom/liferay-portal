/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.document.library.internal.upgrade.v1_0_18.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.exportimport.kernel.service.StagingLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;

import jakarta.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Balázs Sáfrány-Kovalik
 */
@RunWith(Arquillian.class)
public class AssetVocabularyERCUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule liferayIntegrationTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgrade() throws Exception {
		Company company = CompanyTestUtil.addCompany();

		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setCompanyIdWithSafeCloseable(
					company.getCompanyId())) {

			Group companyGroup = _groupLocalService.getCompanyGroup(
				company.getCompanyId());

			String topicERC = _setRandomERC(companyGroup.getGroupId(), "topic");

			_setRandomERC(companyGroup.getGroupId(), "audience");
			_setRandomERC(companyGroup.getGroupId(), "stage");

			User user = UserTestUtil.getAdminUser(company.getCompanyId());

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			PrincipalThreadLocal.setName(user.getUserId());

			_stagingLocalService.enableLocalStaging(
				user.getUserId(), companyGroup, false, false,
				ServiceContextTestUtil.getServiceContext(
					companyGroup.getGroupId()));

			Group stagingGroup = companyGroup.getStagingGroup();

			Group group = GroupTestUtil.addGroupToCompany(
				company.getCompanyId());

			Layout layout = LayoutTestUtil.addTypePortletLayout(group);

			String portletId1 = LayoutTestUtil.addPortletToLayout(
				layout,
				"com_liferay_asset_categories_navigation_web_portlet_" +
					"AssetCategoriesNavigationPortlet");

			String defaultPreferences = StringBundler.concat(
				"<portlet-preferences><preference><name>",
				"assetVocabularyExternalReferenceCodes_L_GLOBAL</name><value>",
				topicERC, "</value></preference></portlet-preferences>");

			_portletPreferencesLocalService.addPortletPreferences(
				group.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				portletId1,
				_portletLocalService.fetchPortletById(
					group.getCompanyId(), portletId1),
				defaultPreferences);

			String portletId2 = LayoutTestUtil.addPortletToLayout(
				layout,
				"com_liferay_portal_search_web_category_facet_portlet_" +
					"CategoryFacetPortlet");

			defaultPreferences = StringBundler.concat(
				"<portlet-preferences><preference><name>",
				"groupVocabularyExternalReferenceCodes</name><value>L_GLOBAL",
				"&amp;&amp;", topicERC,
				"</value></preference></portlet-preferences>");

			_portletPreferencesLocalService.addPortletPreferences(
				group.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				portletId2,
				_portletLocalService.fetchPortletById(
					group.getCompanyId(), portletId2),
				defaultPreferences);

			_runUpgrade();

			_assertERCUpdated(companyGroup.getGroupId(), "audience");
			_assertERCUpdated(companyGroup.getGroupId(), "topic");
			_assertERCUpdated(companyGroup.getGroupId(), "stage");
			_assertERCUpdated(stagingGroup.getGroupId(), "audience");
			_assertERCUpdated(stagingGroup.getGroupId(), "topic");
			_assertERCUpdated(stagingGroup.getGroupId(), "stage");

			PortletPreferencesIds portletPreferencesIds =
				new PortletPreferencesIds(
					group.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
					portletId1);

			PortletPreferences jxPortletPreferences =
				_portletPreferencesLocalService.getPreferences(
					portletPreferencesIds);

			Assert.assertEquals(
				"L_TOPIC",
				jxPortletPreferences.getValue(
					"assetVocabularyExternalReferenceCodes_L_GLOBAL",
					StringPool.BLANK));

			portletPreferencesIds = new PortletPreferencesIds(
				group.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
				portletId2);

			jxPortletPreferences =
				_portletPreferencesLocalService.getPreferences(
					portletPreferencesIds);

			Assert.assertEquals(
				"L_GLOBAL&&L_TOPIC",
				jxPortletPreferences.getValue(
					"groupVocabularyExternalReferenceCodes", StringPool.BLANK));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

			PrincipalThreadLocal.setName(TestPropsValues.getUserId());
		}
	}

	private void _assertERCUpdated(long groupId, String name) {
		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.
				fetchAssetVocabularyByExternalReferenceCode(
					"L_" + TextFormatter.format(name, TextFormatter.A),
					groupId);

		Assert.assertNotNull(assetVocabulary);
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();
	}

	private String _setRandomERC(long groupId, String name) throws Exception {
		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.getGroupVocabulary(groupId, name);

		String erc = StringUtil.randomString();

		assetVocabulary.setExternalReferenceCode(erc);

		_assetVocabularyLocalService.updateAssetVocabulary(assetVocabulary);

		return erc;
	}

	private static final String _CLASS_NAME =
		"com.liferay.content.dashboard.document.library.internal.upgrade." +
			"v1_0_18.AssetVocabularyERCUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.content.dashboard.document.library.internal.upgrade.registry.ContentDashboardDocumentLibraryImplUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private PortletLocalService _portletLocalService;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Inject
	private StagingLocalService _stagingLocalService;

}