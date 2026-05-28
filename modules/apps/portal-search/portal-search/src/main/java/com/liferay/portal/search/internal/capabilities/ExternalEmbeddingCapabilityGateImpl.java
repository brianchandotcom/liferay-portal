/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.capabilities;

import com.liferay.portal.kernel.license.util.LicenseManager;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.search.capabilities.ElasticsearchLicenseInformation;
import com.liferay.portal.search.capabilities.ExternalEmbeddingCapabilityGate;
import com.liferay.portal.search.capabilities.ExternalEmbeddingEligibility;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(service = ExternalEmbeddingCapabilityGate.class)
public class ExternalEmbeddingCapabilityGateImpl
	implements ExternalEmbeddingCapabilityGate {

	@Override
	public ExternalEmbeddingEligibility check() {
		if (_licenseManager.isFreeTier()) {
			return ExternalEmbeddingEligibility.unavailable(
				_REASON_MISSING_DXP_ENTERPRISE);
		}

		ElasticsearchLicenseInformation elasticsearchLicenseInformation =
			_elasticsearchLicenseInformationSnapshot.get();

		if ((elasticsearchLicenseInformation == null) ||
			!elasticsearchLicenseInformation.supportsInferenceAPI()) {

			return ExternalEmbeddingEligibility.unavailable(
				_REASON_MISSING_ELASTICSEARCH_LICENSE);
		}

		return ExternalEmbeddingEligibility.available();
	}

	private static final String _REASON_MISSING_DXP_ENTERPRISE =
		"semantic-search.external-embedding-capability.missing-dxp-enterprise";

	private static final String _REASON_MISSING_ELASTICSEARCH_LICENSE =
		"semantic-search.external-embedding-capability." +
			"missing-elasticsearch-license";

	private static final Snapshot<ElasticsearchLicenseInformation>
		_elasticsearchLicenseInformationSnapshot = new Snapshot<>(
			ExternalEmbeddingCapabilityGateImpl.class,
			ElasticsearchLicenseInformation.class);

	@Reference
	private LicenseManager _licenseManager;

}