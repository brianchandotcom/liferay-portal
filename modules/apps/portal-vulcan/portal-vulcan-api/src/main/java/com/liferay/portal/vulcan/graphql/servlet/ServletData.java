/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.graphql.servlet;

import com.liferay.portal.kernel.util.ObjectValuePair;

/**
 * @author Preston Crary
 */
public interface ServletData {

	public default String getApplicationName() {
		return null;
	}

	public default String getGraphQLNamespace() {
		return null;
	}

	public Object getMutation();

	public String getPath();

	public Object getQuery();

	public default ObjectValuePair<Class<?>, String>
		getResourceMethodObjectValuePair(String methodName, boolean mutation) {

		return null;
	}

	public default boolean isJaxRsResourceInvocation() {
		return true;
	}

}