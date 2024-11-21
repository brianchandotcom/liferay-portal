/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v1_4_4.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.portlet.PortalPreferencesWrapper;
import com.liferay.release.feature.flag.ReleaseFeatureFlag;
import com.liferay.release.feature.flag.ReleaseFeatureFlagManager;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class LayoutPrivateLayoutsUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testUpgrade() throws Exception {
		boolean releaseFeatureFlagEnabled =
			_releaseFeatureFlagManager.isEnabled(
				ReleaseFeatureFlag.DISABLE_PRIVATE_LAYOUTS);

		String originalFeatureFlagValue = _updateFeatureFlagValue(
			Boolean.FALSE.toString());

		try {
			_releaseFeatureFlagManager.setEnabled(
				ReleaseFeatureFlag.DISABLE_PRIVATE_LAYOUTS, false);

			_runUpgrade();

			_assertFeatureFlagValue(Boolean.TRUE.toString());

			_updateFeatureFlagValue(Boolean.FALSE.toString());

			_releaseFeatureFlagManager.setEnabled(
				ReleaseFeatureFlag.DISABLE_PRIVATE_LAYOUTS, true);

			_runUpgrade();

			_assertFeatureFlagValue(Boolean.FALSE.toString());
		}
		finally {
			_releaseFeatureFlagManager.setEnabled(
				ReleaseFeatureFlag.DISABLE_PRIVATE_LAYOUTS,
				releaseFeatureFlagEnabled);

			_updateFeatureFlagValue(originalFeatureFlagValue);
		}
	}

	private void _assertFeatureFlagValue(String value) throws Exception {
		PortalPreferencesWrapper portalPreferencesWrapper =
			(PortalPreferencesWrapper)
				_portalPreferencesLocalService.getPreferences(
					TestPropsValues.getCompanyId(),
					PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		PortalPreferences portalPreferences =
			portalPreferencesWrapper.getPortalPreferencesImpl();

		Assert.assertEquals(
			value,
			portalPreferences.getValue(
				FeatureFlagConstants.PREFERENCE_NAMESPACE, "LPD-38869", null));
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess[] upgradeSteps = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(1, 4, 4));

		UpgradeProcess upgradeProcess = upgradeSteps[0];

		upgradeProcess.upgrade();

		_entityCache.clearCache();
		_multiVMPool.clear();
	}

	private String _updateFeatureFlagValue(String value) throws Exception {
		PortalPreferencesWrapper portalPreferencesWrapper =
			(PortalPreferencesWrapper)
				_portalPreferencesLocalService.getPreferences(
					TestPropsValues.getCompanyId(),
					PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		PortalPreferences portalPreferences =
			portalPreferencesWrapper.getPortalPreferencesImpl();

		String previousValue = portalPreferences.getValue(
			FeatureFlagConstants.PREFERENCE_NAMESPACE, "LPD-38869", null);

		portalPreferences = portalPreferencesWrapper.getPortalPreferencesImpl();

		portalPreferences.setValue(
			FeatureFlagConstants.PREFERENCE_NAMESPACE, "LPD-38869", value);

		_portalPreferencesLocalService.updatePreferences(
			TestPropsValues.getCompanyId(),
			PortletKeys.PREFS_OWNER_TYPE_COMPANY, portalPreferences);

		_assertFeatureFlagValue(value);

		return previousValue;
	}

	@Inject(
		filter = "(&(component.name=com.liferay.layout.internal.upgrade.registry.LayoutServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@Inject
	private EntityCache _entityCache;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	@Inject
	private ReleaseFeatureFlagManager _releaseFeatureFlagManager;

}