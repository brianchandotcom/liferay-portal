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

package com.liferay.segments.asah.connector.internal.client;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;

import java.util.Optional;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(immediate = true, service = AsahFaroBackendClientFactory.class)
public class AsahFaroBackendClientFactory {

	public Optional<AsahFaroBackendClient> createAsahFaroBackendClient() {
		Company company = _companyLocalService.fetchCompany(
			_portal.getDefaultCompanyId());

		PortletPreferences portletPreferences =
			_portalPreferencesLocalService.getPreferences(
				company.getCompanyId(), PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		String asahFaroBackendDataSourceId = GetterUtil.getString(
			portletPreferences.getValue("liferayAnalyticsDataSourceId", null));
		String asahFaroBackendSecuritySignature = GetterUtil.getString(
			portletPreferences.getValue(
				"liferayAnalyticsFaroBackendSecuritySignature", null));
		String asahFaroBackendURL = GetterUtil.getString(
			portletPreferences.getValue(
				"liferayAnalyticsFaroBackendURL", null));

		if (Validator.isNull(asahFaroBackendDataSourceId) ||
			Validator.isNull(asahFaroBackendSecuritySignature) ||
			Validator.isNull(asahFaroBackendURL)) {

			if (_log.isInfoEnabled()) {
				_log.info("Unable to configure Asah Faro backend client");
			}

			return Optional.empty();
		}

		return Optional.of(
			new AsahFaroBackendClientImpl(
				_jsonWebServiceClient, asahFaroBackendDataSourceId,
				asahFaroBackendSecuritySignature, asahFaroBackendURL));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AsahFaroBackendClientFactory.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private JSONWebServiceClient _jsonWebServiceClient;

	@Reference
	private Portal _portal;

	@Reference
	private PortalPreferencesLocalService _portalPreferencesLocalService;

}