/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.capabilities;

import com.liferay.portal.kernel.license.util.LicenseManager;
import com.liferay.portal.search.capabilities.ExternalEmbeddingCapabilityGate;
import com.liferay.portal.search.capabilities.ExternalEmbeddingEligibility;
import com.liferay.portal.search.engine.SearchEngineInformation;

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
				"semantic-search.external-embedding-capability." +
					"missing-dxp-enterprise");
		}

		if (!_searchEngineInformation.isInferenceAPISupported()) {
			return ExternalEmbeddingEligibility.unavailable(
				"semantic-search.external-embedding-capability." +
					"missing-elasticsearch-license");
		}

		return ExternalEmbeddingEligibility.available();
	}

	@Reference
	private LicenseManager _licenseManager;

	@Reference
	private SearchEngineInformation _searchEngineInformation;

}