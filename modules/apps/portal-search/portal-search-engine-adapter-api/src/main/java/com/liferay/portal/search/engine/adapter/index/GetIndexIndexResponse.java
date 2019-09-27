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

package com.liferay.portal.search.engine.adapter.index;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public class GetIndexIndexResponse implements IndexResponse {

	public Map<String, List<String>> getAliases() {
		return _aliases;
	}

	public Map<String, String> getIndexMappings() {
		return _indexMappings;
	}

	public String[] getIndexNames() {
		return _indexNames;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getIndexMappings()}
	 */
	@Deprecated
	public Map<String, Map<String, String>> getMappings() {
		return _mappings;
	}

	public Map<String, String> getSettings() {
		return _settings;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getIndexMappings()}
	 */
	@Deprecated
	public Map<String, String> getUntypedMappings() {
		return _untypedMappings;
	}

	public void setAliases(Map<String, List<String>> aliases) {
		_aliases = aliases;
	}

	public void setIndexMappings(Map<String, String> indexMappings) {
		_indexMappings = indexMappings;
	}

	public void setIndexNames(String[] indexNames) {
		_indexNames = indexNames;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setIndexMappings(Map<String, String>)}
	 */
	@Deprecated
	public void setMappings(Map<String, Map<String, String>> mappings) {
		_mappings = mappings;
	}

	public void setSettings(Map<String, String> settings) {
		_settings = settings;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setIndexMappings(Map<String, String>)}
	 */
	@Deprecated
	public void setUntypedMappings(Map<String, String> untypedMappings) {
		_untypedMappings = untypedMappings;
	}

	private Map<String, List<String>> _aliases;
	private Map<String, String> _indexMappings;
	private String[] _indexNames;
	private Map<String, Map<String, String>> _mappings;
	private Map<String, String> _settings;
	private Map<String, String> _untypedMappings;

}