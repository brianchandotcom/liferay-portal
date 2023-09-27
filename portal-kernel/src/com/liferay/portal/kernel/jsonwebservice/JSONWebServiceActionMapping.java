/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.jsonwebservice;

import com.liferay.portal.kernel.util.MethodParameter;

import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public interface JSONWebServiceActionMapping {

	public Class<?> getActionClass();

	public Method getActionMethod();

	public Object getActionObject();

	public String getContextName();

	public String getContextPath();

	public String getMethod();

	public MethodParameter[] getMethodParameters();

	public String getPath();

	public Method getRealActionMethod();

	public String getSignature();

	public boolean isDeprecated();

}