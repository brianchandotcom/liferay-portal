/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.file.install.properties;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.util.Set;

/**
 * @author Matthew Tambara
 */
public interface ConfigurationProperties {

	public Object get(String key) throws IOException;

	public Set<String> keySet();

	public void load(Reader reader) throws IOException;

	public void put(String key, Object value) throws IOException;

	public void remove(String key);

	public void save(Writer writer) throws IOException;

}