/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletPreferenceValue;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_4_x.UpgradePortletPreferencesCompanyId;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class UpgradePortletPreferencesCompanyIdTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_testGroup = GroupTestUtil.addGroup();

		_testLayout = LayoutTestUtil.addTypePortletLayout(_testGroup);
	}

	@Test
	public void testUpgrade() throws Exception {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(
					CompanyConstants.SYSTEM)) {

			PortletPreferences portletPreferences =
				_portletPreferencesLocalService.addPortletPreferences(
					CompanyConstants.SYSTEM, PortletKeys.PREFS_OWNER_ID_DEFAULT,
					PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _testLayout.getPlid(),
					"test", null,
					_getPortletPreferencesXML(_NAME, _MULTIPLE_VALUES));

			Assert.assertEquals(
				CompanyConstants.SYSTEM, portletPreferences.getCompanyId());

			List<PortletPreferenceValue> portletPreferenceValues =
				_getPortletPreferenceValues(
					portletPreferences.getPortletPreferencesId());

			assertCompanyIds(CompanyConstants.SYSTEM, portletPreferenceValues);

			UpgradeProcess upgradeProcess =
				new UpgradePortletPreferencesCompanyId();

			upgradeProcess.upgrade();

			CacheRegistryUtil.clear();

			portletPreferences =
				_portletPreferencesLocalService.getPortletPreferences(
					portletPreferences.getPortletPreferencesId());

			Assert.assertEquals(
				TestPropsValues.getCompanyId(),
				portletPreferences.getCompanyId());

			portletPreferenceValues = _getPortletPreferenceValues(
				portletPreferences.getPortletPreferencesId());

			assertCompanyIds(
				TestPropsValues.getCompanyId(), portletPreferenceValues);
		}
	}

	protected void assertCompanyIds(
		long companyId, List<PortletPreferenceValue> portletPreferenceValues) {

		for (PortletPreferenceValue portletPreferenceValue :
				portletPreferenceValues) {

			Assert.assertEquals(
				companyId, portletPreferenceValue.getCompanyId());
		}
	}

	private String _getPortletPreferencesXML(String name, String[] values) {
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

	private List<PortletPreferenceValue> _getPortletPreferenceValues(
			long portletPreferencesId)
		throws Exception {

		DynamicQuery dynamicQuery =
			_portletPreferenceValueLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"portletPreferencesId", portletPreferencesId));

		return _portletPreferenceValueLocalService.dynamicQuery(dynamicQuery);
	}

	private static final String[] _MULTIPLE_VALUES = {"value1", "value2"};

	private static final String _NAME = "name";

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Inject
	private PortletPreferenceValueLocalService
		_portletPreferenceValueLocalService;

	@DeleteAfterTestRun
	private Group _testGroup;

	@DeleteAfterTestRun
	private Layout _testLayout;

}