/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.PortletPreferenceValue;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.data.cleanup.LayoutDataCleanupPreupgradeProcess;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@DataGuard(autoDelete = false, scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class LayoutDataCleanupPreupgradeProcessTest
	extends LayoutDataCleanupPreupgradeProcess {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_layoutPageTemplateStructureRels =
			_layoutPageTemplateStructureRelLocalService.
				getLayoutPageTemplateStructureRels(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		_resourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@After
	public void tearDown() throws Exception {
		List<LayoutPageTemplateStructureRel> layoutPageTemplateStructureRels =
			ListUtil.remove(
				_layoutPageTemplateStructureRelLocalService.
					getLayoutPageTemplateStructureRels(
						QueryUtil.ALL_POS, QueryUtil.ALL_POS),
				_layoutPageTemplateStructureRels);

		for (LayoutPageTemplateStructureRel layoutPageTemplateStructureRel :
				layoutPageTemplateStructureRels) {

			_layoutPageTemplateStructureRelLocalService.
				deleteLayoutPageTemplateStructureRel(
					layoutPageTemplateStructureRel);
		}

		List<ResourcePermission> resourcePermissions = ListUtil.remove(
			_resourcePermissionLocalService.getResourcePermissions(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS),
			_resourcePermissions);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			_resourcePermissionLocalService.deleteResourcePermission(
				resourcePermission);
		}
	}

	@Test
	public void testUpgrade() throws Exception {
		long groupId = TestPropsValues.getGroupId();

		Layout contentPageLayout = _layoutLocalService.addLayout(
			null, TestPropsValues.getUserId(), groupId, false, 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), LayoutConstants.TYPE_CONTENT, false,
			"/" + RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(groupId));

		Layout widgetPageLayout = _layoutLocalService.addLayout(
			null, TestPropsValues.getUserId(), groupId, false, 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), LayoutConstants.TYPE_PORTLET, false,
			"/" + RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(groupId));

		PortletPreferences portletPreferences =
			_portletPreferencesLocalService.updatePreferences(
				0, PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				widgetPageLayout.getPlid(), RandomTestUtil.randomString(),
				getPortletPreferencesXML(
					RandomTestUtil.randomString(),
					new String[] {
						RandomTestUtil.randomString(),
						RandomTestUtil.randomString()
					}));

		runSQL(
			"delete from Layout where plid = " + contentPageLayout.getPlid());
		runSQL("delete from Layout where plid = " + widgetPageLayout.getPlid());

		upgrade();

		DynamicQuery dynamicQuery =
			_portletPreferenceValueLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"portletPreferencesId",
				portletPreferences.getPortletPreferencesId()));

		List<PortletPreferenceValue> portletPreferenceValues =
			_portletPreferenceValueLocalService.dynamicQuery(dynamicQuery);

		for (PortletPreferenceValue portletPreferenceValue :
				portletPreferenceValues) {

			_portletPreferenceValueLocalService.deletePortletPreferenceValue(
				portletPreferenceValue);
		}
	}

	protected String getPortletPreferencesXML(String name, String[] values) {
		StringBundler sb = new StringBundler();

		sb.append("<portlet-preferences>");

		if ((name != null) || (values != null)) {
			sb.append("<preference>");

			if (name != null) {
				sb.append("<name>");
				sb.append(name);
				sb.append("</name>");
			}

			if (values != null) {
				for (String value : values) {
					sb.append("<value>");
					sb.append(value);
					sb.append("</value>");
				}
			}

			sb.append("</preference>");
		}

		sb.append("</portlet-preferences>");

		return sb.toString();
	}

	private static List<LayoutPageTemplateStructureRel>
		_layoutPageTemplateStructureRels;
	private static List<ResourcePermission> _resourcePermissions;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Inject
	private PortletPreferenceValueLocalService
		_portletPreferenceValueLocalService;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}