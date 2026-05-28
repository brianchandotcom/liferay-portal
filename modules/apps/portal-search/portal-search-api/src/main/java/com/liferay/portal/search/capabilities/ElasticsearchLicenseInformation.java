/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.capabilities;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Exposes the Elasticsearch cluster license information relevant to the
 * inference endpoint capability — currently whether the cluster license tier
 * (Trial or Enterprise) authorizes the use of Inference Endpoints.
 *
 * <p>
 * Implemented by the Elasticsearch connector module and consumed by
 * {@link ExternalEmbeddingCapabilityGate} via a snapshot reference so that
 * the search-api bundle does not depend on the connector at startup.
 * </p>
 *
 * @author Rodrigo Guedes de Souza
 */
@ProviderType
public interface ElasticsearchLicenseInformation {

	public boolean supportsInferenceAPI();

}