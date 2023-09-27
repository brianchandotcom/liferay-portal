/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.bean;

import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @author Miguel Pastor
 */
@ProviderType
public interface BeanLocator {

	public void destroy();

	public ClassLoader getClassLoader();

	public String[] getNames();

	public Class<?> getType(String name) throws BeanLocatorException;

	public <T> Map<String, T> locate(Class<T> clazz)
		throws BeanLocatorException;

	public Object locate(String name) throws BeanLocatorException;

}