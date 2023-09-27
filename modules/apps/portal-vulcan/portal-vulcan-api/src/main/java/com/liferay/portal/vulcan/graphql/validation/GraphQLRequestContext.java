/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.graphql.validation;

import java.lang.reflect.Method;

/**
 * @author Carlos Correa
 */
public interface GraphQLRequestContext {

	public String getApplicationName();

	public long getCompanyId();

	public String getNamespace();

	public Class<?> getResourceClass();

	public Method getResourceMethod();

	public boolean isJaxRsResourceInvocation();

}