/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.resolver;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Gianmarco Brunialti Masera
 */
@ProviderType
public interface FDSRestApplicationURLParameterResolverRegistry {

	public FDSRestApplicationURLParameterResolver getResolver(
		String restApplication, String restSchema);

	public List<FDSRestApplicationURLParameterResolver> getResolvers();

}