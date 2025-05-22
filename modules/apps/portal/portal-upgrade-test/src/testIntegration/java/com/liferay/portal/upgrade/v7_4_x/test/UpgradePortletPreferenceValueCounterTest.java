/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletPreferenceValue;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.service.PortletPreferenceValueLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_4_x.UpgradePortletPreferenceValueCounter;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class UpgradePortletPreferenceValueCounterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypePortletLayout(_group);
	}

	@Test
	@TestInfo("LPD-56218")
	public void testUpgrade() throws Exception {
		PortletPreferences portletPreferences =
			_portletPreferencesLocalService.addPortletPreferences(
				CompanyConstants.SYSTEM, PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, _layout.getPlid(),
				RandomTestUtil.randomString(), null,
				"<portlet-preferences><preference></preference>" +
					"</portlet-preferences>");

		long currentId = _counterLocalService.getCurrentId(
			PortletPreferenceValue.class.getName());

		PortletPreferenceValue portletPreferenceValue =
			_portletPreferenceValueLocalService.createPortletPreferenceValue(
				currentId + RandomTestUtil.randomInt(100, 1000));

		portletPreferenceValue.setCompanyId(TestPropsValues.getCompanyId());
		portletPreferenceValue.setPortletPreferencesId(
			portletPreferences.getPortletPreferencesId());
		portletPreferenceValue.setIndex(RandomTestUtil.nextInt());
		portletPreferenceValue.setLargeValue(RandomTestUtil.randomString());
		portletPreferenceValue.setName(RandomTestUtil.randomString());
		portletPreferenceValue.setReadOnly(RandomTestUtil.randomBoolean());
		portletPreferenceValue.setValue(RandomTestUtil.randomString());

		portletPreferenceValue =
			_portletPreferenceValueLocalService.addPortletPreferenceValue(
				portletPreferenceValue);

		Assert.assertTrue(
			portletPreferenceValue.getPortletPreferenceValueId() > currentId);

		List<PortletPreferenceValue> portletPreferenceValues =
			_getPortletPreferenceValues(
				portletPreferences.getPortletPreferencesId());

		Assert.assertEquals(
			portletPreferenceValues.toString(), 1,
			portletPreferenceValues.size());

		UpgradeProcess upgradeProcess =
			new UpgradePortletPreferenceValueCounter();

		upgradeProcess.upgrade();

		CacheRegistryUtil.clear();

		List<PortletPreferenceValue> curPortletPreferenceValues =
			_getPortletPreferenceValues(
				portletPreferences.getPortletPreferencesId());

		Assert.assertEquals(
			curPortletPreferenceValues.toString(), 1,
			curPortletPreferenceValues.size());

		PortletPreferenceValue curPortletPreferenceValue =
			curPortletPreferenceValues.get(0);

		Assert.assertEquals(
			portletPreferenceValue.getPortletPreferenceValueId(),
			curPortletPreferenceValue.getPortletPreferenceValueId());
		Assert.assertTrue(
			curPortletPreferenceValue.getPortletPreferenceValueId() <=
				_counterLocalService.getCurrentId(
					PortletPreferenceValue.class.getName()));
	}

	private List<PortletPreferenceValue> _getPortletPreferenceValues(
		long portletPreferencesId) {

		DynamicQuery dynamicQuery =
			_portletPreferenceValueLocalService.dynamicQuery();

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq(
				"portletPreferencesId", portletPreferencesId));

		return _portletPreferenceValueLocalService.dynamicQuery(dynamicQuery);
	}

	@Inject
	private CounterLocalService _counterLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private Layout _layout;

	@Inject
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Inject
	private PortletPreferenceValueLocalService
		_portletPreferenceValueLocalService;

}