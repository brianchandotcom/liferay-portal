/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.feature.flag.web.internal.feature.flag;

import com.liferay.feature.flag.web.internal.company.feature.flags.CompanyFeatureFlags;
import com.liferay.feature.flag.web.internal.company.feature.flags.CompanyFeatureFlagsProvider;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManager;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = FeatureFlagManager.class)
public class FeatureFlagManagerImpl implements FeatureFlagManager {

	@Override
	public String getJSON(long companyId) {
		return _companyFeatureFlagsProvider.withCompanyFeatureFlags(
			companyId, CompanyFeatureFlags::getJSON);
	}

	@Override
	public boolean isEnabled(long companyId, String key) {
		return _companyFeatureFlagsProvider.withCompanyFeatureFlags(
			companyId,
			companyFeatureFlags -> companyFeatureFlags.isEnabled(key));
	}

	@Override
	public boolean isEnabled(String key) {
		return isEnabled(CompanyThreadLocal.getCompanyId(), key);
	}

	@Reference
	private CompanyFeatureFlagsProvider _companyFeatureFlagsProvider;

}