/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.graphql.contributor;

/**
 * @author Javier de Arcos
 */
public interface GraphQLContributor {

	public default String getGraphQLNamespace() {
		return null;
	}

	public default Object getMutation() {
		return null;
	}

	public String getPath();

	public default Object getQuery() {
		return null;
	}

	public default boolean isJaxRsResourceInvocation() {
		return true;
	}

}