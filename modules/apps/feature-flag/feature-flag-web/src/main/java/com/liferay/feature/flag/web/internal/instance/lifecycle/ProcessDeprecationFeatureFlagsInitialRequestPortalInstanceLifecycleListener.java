/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.instance.lifecycle;

import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.feature.flag.FeatureFlagsBag;
import com.liferay.portal.feature.flag.FeatureFlagsBagProvider;
import com.liferay.portal.instance.lifecycle.InitialRequestPortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.feature.flag.FeatureFlag;
import com.liferay.portal.kernel.feature.flag.FeatureFlagType;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portlet.PortalPreferencesWrapper;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 * @author Thiago Buarque
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class
	ProcessDeprecationFeatureFlagsInitialRequestPortalInstanceLifecycleListener
		extends InitialRequestPortalInstanceLifecycleListener {

	@Activate
	@Override
	protected void activate(BundleContext bundleContext) {
		super.activate(bundleContext);

		if (StartupHelperUtil.isDBNew()) {
			_processDeprecationFeatureFlags(CompanyConstants.SYSTEM);
		}
	}

	@Override
	protected void doPortalInstanceRegistered(long companyId) throws Exception {
		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				_processDeprecationFeatureFlags(companyId);

				return null;
			});
	}

	private PortalPreferences _getPortalPreferences(long companyId) {
		PortalPreferencesWrapper portalPreferencesWrapper =
			(PortalPreferencesWrapper)
				_portalPreferencesLocalService.getPreferences(
					companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		return portalPreferencesWrapper.getPortalPreferencesImpl();
	}

	private void _processDeprecationFeatureFlags(long companyId) {
		PortalPreferences portalPreferences = _getPortalPreferences(companyId);

		boolean processed = GetterUtil.getBoolean(
			portalPreferences.getValue(
				FeatureFlagConstants.PREFERENCE_NAMESPACE,
				FeatureFlagConstants.PREFERENCE_KEY_DEPRECATION_PROCESSED));

		if (processed) {
			return;
		}

		FeatureFlagsBag featureFlagsBag =
			_featureFlagsBagProvider.getOrCreateFeatureFlagsBag(companyId);

		List<FeatureFlag> deprecationFeatureFlags =
			featureFlagsBag.getFeatureFlags(
				FeatureFlagType.DEPRECATION.getPredicate());

		for (FeatureFlag deprecationFeatureFlag : deprecationFeatureFlags) {
			_featureFlagsBagProvider.setEnabled(
				companyId, deprecationFeatureFlag.getKey(), false);
		}

		portalPreferences = _getPortalPreferences(companyId);

		portalPreferences.setValue(
			FeatureFlagConstants.PREFERENCE_NAMESPACE,
			FeatureFlagConstants.PREFERENCE_KEY_DEPRECATION_PROCESSED, "true");

		_portalPreferencesLocalService.updatePreferences(
			companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY, portalPreferences);
	}

	@Reference
	private FeatureFlagsBagProvider _featureFlagsBagProvider;

	@Reference
	private PortalPreferencesLocalService _portalPreferencesLocalService;

}