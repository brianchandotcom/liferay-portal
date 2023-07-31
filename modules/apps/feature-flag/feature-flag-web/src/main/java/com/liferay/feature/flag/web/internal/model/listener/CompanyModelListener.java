/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.feature.flag.web.internal.model.listener;

import com.liferay.feature.flag.web.internal.company.feature.flags.CompanyFeatureFlags;
import com.liferay.feature.flag.web.internal.company.feature.flags.CompanyFeatureFlagsProvider;
import com.liferay.feature.flag.web.internal.model.FeatureFlag;
import com.liferay.feature.flag.web.internal.model.FeatureFlagType;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.List;

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
		CompanyFeatureFlags companyFeatureFlags =
			_companyFeatureFlagsProvider.getOrCreateCompanyFeatureFlags(
				company.getCompanyId());

		List<FeatureFlag> deprecationFeatureFlags =
			companyFeatureFlags.getFeatureFlags(
				FeatureFlagType.DEPRECATION.getPredicate());

		for (FeatureFlag deprecationFeatureFlag : deprecationFeatureFlags) {
			_companyFeatureFlagsProvider.setEnabled(
				company.getCompanyId(), deprecationFeatureFlag.getKey(), false);
		}
	}

	@Reference
	private CompanyFeatureFlagsProvider _companyFeatureFlagsProvider;

}