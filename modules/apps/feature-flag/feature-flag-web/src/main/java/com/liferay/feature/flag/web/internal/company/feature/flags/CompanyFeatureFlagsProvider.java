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

package com.liferay.feature.flag.web.internal.company.feature.flags;

import com.liferay.feature.flag.web.internal.helper.FeatureFlagPreferencesHelper;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	service = {
		CompanyFeatureFlagsProvider.class, PortalInstanceLifecycleListener.class
	}
)
public class CompanyFeatureFlagsProvider
	implements PortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) {
		_companyFeatureFlagsMap.put(
			company.getCompanyId(),
			new CompanyFeatureFlags(
				company.getCompanyId(), _featureFlagPreferencesHelper,
				_language));
	}

	@Override
	public void portalInstanceUnregistered(Company company) {
		_companyFeatureFlagsMap.remove(company.getCompanyId());
	}

	public <T> T withCompanyFeatureFlags(
		long companyId,
		Function<CompanyFeatureFlags, T> companyFeatureFlagsFunction) {

		return companyFeatureFlagsFunction.apply(
			_companyFeatureFlagsMap.get(companyId));
	}

	@Activate
	protected void activate() {
		_companyFeatureFlagsMap.put(
			CompanyConstants.SYSTEM,
			new CompanyFeatureFlags(
				CompanyConstants.SYSTEM, _featureFlagPreferencesHelper,
				_language));
	}

	private final Map<Long, CompanyFeatureFlags> _companyFeatureFlagsMap =
		new ConcurrentHashMap<>();

	@Reference
	private FeatureFlagPreferencesHelper _featureFlagPreferencesHelper;

	@Reference
	private Language _language;

}