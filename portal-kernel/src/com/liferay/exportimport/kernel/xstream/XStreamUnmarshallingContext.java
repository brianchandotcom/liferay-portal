/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.xstream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Daniel Kocsis
 */
@ProviderType
public interface XStreamUnmarshallingContext {

	public void addCompletionCallback(Runnable runnable, int i);

	public Object convertAnother(Object object, Class<?> clazz);

	public Object convertAnother(
		Object object, Class<?> clazz, XStreamConverter xStreamConverter);

	public Object currentObject();

	public Object getRequiredType();

}