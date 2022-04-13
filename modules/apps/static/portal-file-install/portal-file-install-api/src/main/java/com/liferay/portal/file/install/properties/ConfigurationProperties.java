/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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