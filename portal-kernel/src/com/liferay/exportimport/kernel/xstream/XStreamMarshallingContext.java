/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.xstream;

import java.util.Iterator;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Daniel Kocsis
 */
@ProviderType
public interface XStreamMarshallingContext {

	public void convertAnother(Object object);

	public void convertAnother(
		Object object, XStreamConverter xStreamConverter);

	public Object get(Object key);

	public Iterator<String> keys();

	public void put(Object key, Object value);

}