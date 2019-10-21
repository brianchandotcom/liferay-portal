/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.client;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.asah.connector.internal.util.AsahUtil;

import java.util.Optional;

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

		if (!AsahUtil.isAnalyticsEnabled(company.getCompanyId())) {
			if (_log.isInfoEnabled()) {
				_log.info("Unable to configure Asah Faro backend client");
			}

			return Optional.empty();
		}

		return Optional.of(
			new AsahFaroBackendClientImpl(
				_jsonWebServiceClient,
				AsahUtil.getAsahFaroBackendDataSourceId(company.getCompanyId()),
				AsahUtil.getAsahFaroBackendSecuritySignature(
					company.getCompanyId()),
				AsahUtil.getAsahFaroBackendURL(company.getCompanyId())));
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