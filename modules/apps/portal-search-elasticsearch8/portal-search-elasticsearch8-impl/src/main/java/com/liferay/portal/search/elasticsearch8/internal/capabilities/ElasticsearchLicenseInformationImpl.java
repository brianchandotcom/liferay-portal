/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.capabilities;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.license.GetLicenseResponse;
import co.elastic.clients.elasticsearch.license.LicenseStatus;
import co.elastic.clients.elasticsearch.license.LicenseType;
import co.elastic.clients.elasticsearch.license.get.LicenseInformation;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.capabilities.ElasticsearchLicenseInformation;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchConnectionManager;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(service = ElasticsearchLicenseInformation.class)
public class ElasticsearchLicenseInformationImpl
	implements ElasticsearchLicenseInformation {

	@Override
	public boolean supportsInferenceAPI() {
		try {
			ElasticsearchClient elasticsearchClient =
				_elasticsearchConnectionManager.getElasticsearchClient();

			if (elasticsearchClient == null) {
				return false;
			}

			GetLicenseResponse getLicenseResponse = elasticsearchClient.license(
			).get();

			if (getLicenseResponse == null) {
				return false;
			}

			LicenseInformation licenseInformation =
				getLicenseResponse.license();

			if ((licenseInformation == null) ||
				(licenseInformation.status() != LicenseStatus.Active)) {

				return false;
			}

			LicenseType type = licenseInformation.type();

			if ((type == LicenseType.Trial) ||
				(type == LicenseType.Enterprise)) {

				return true;
			}

			return false;
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to query the Elasticsearch \"_license\" API",
					exception);
			}

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchLicenseInformationImpl.class);

	@Reference
	private ElasticsearchConnectionManager _elasticsearchConnectionManager;

}