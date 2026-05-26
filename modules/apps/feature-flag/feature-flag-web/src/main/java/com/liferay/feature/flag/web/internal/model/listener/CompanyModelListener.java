/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.model.listener;

import com.liferay.feature.flag.web.internal.feature.flag.FeatureFlagsBag;
import com.liferay.feature.flag.web.internal.feature.flag.FeatureFlagsBagProvider;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.feature.flag.FeatureFlag;
import com.liferay.portal.kernel.feature.flag.FeatureFlagType;
import com.liferay.portal.kernel.feature.flag.constants.FeatureFlagConstants;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portlet.PortalPreferencesWrapper;

import java.util.List;
import java.util.function.Predicate;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 * @author Thiago Buarque
 */
@Component(service = ModelListener.class)
public class CompanyModelListener extends BaseModelListener<Company> {

	@Override
	public void onAfterCreate(Company company) throws ModelListenerException {
		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				_processDeprecationFeatureFlagsForCompany(
					company.getCompanyId());

				return null;
			});
	}

	@Activate
	protected void activate() {
		if (StartupHelperUtil.isDBNew()) {
			_processDeprecationFeatureFlagsForSystem();
		}

		_companyLocalService.forEachCompanyId(
			this::_processDeprecationFeatureFlagsForCompany);
	}

	private PortalPreferences _getPortalPreferences(long companyId) {
		PortalPreferencesWrapper portalPreferencesWrapper =
			(PortalPreferencesWrapper)
				_portalPreferencesLocalService.getPreferences(
					companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		return portalPreferencesWrapper.getPortalPreferencesImpl();
	}

	private void _processDeprecationFeatureFlags(
		long companyId, Predicate<FeatureFlag> enabledPredicate) {

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
				companyId, deprecationFeatureFlag.getKey(),
				enabledPredicate.test(deprecationFeatureFlag));
		}

		portalPreferences = _getPortalPreferences(companyId);

		portalPreferences.setValue(
			FeatureFlagConstants.PREFERENCE_NAMESPACE,
			FeatureFlagConstants.PREFERENCE_KEY_DEPRECATION_PROCESSED, "true");

		_portalPreferencesLocalService.updatePreferences(
			companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY, portalPreferences);
	}

	private void _processDeprecationFeatureFlagsForCompany(long companyId) {
		_processDeprecationFeatureFlags(
			companyId, deprecationFeatureFlag -> false);
	}

	private void _processDeprecationFeatureFlagsForSystem() {
		_processDeprecationFeatureFlags(
			CompanyConstants.SYSTEM, FeatureFlag::isEnabled);
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private FeatureFlagsBagProvider _featureFlagsBagProvider;

	@Reference
	private PortalPreferencesLocalService _portalPreferencesLocalService;

}