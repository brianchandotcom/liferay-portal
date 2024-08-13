/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.rest.internal.resource.v1_0;

import com.liferay.portal.search.rest.resource.v1_0.EmbeddingModelResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Petteri Karttunen
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/embedding-model.properties",
	scope = ServiceScope.PROTOTYPE, service = EmbeddingModelResource.class
)
public class EmbeddingModelResourceImpl extends BaseEmbeddingModelResourceImpl {
}