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

package com.liferay.adaptive.media.image.internal.configuration;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.petra.string.StringPool;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Adolfo Pérez
 */
public class AMImageConfigurationEntryImpl
	implements AMImageConfigurationEntry {

	public AMImageConfigurationEntryImpl(
		String name, String uuid, Map<String, String> properties) {

		this(name, StringPool.BLANK, uuid, properties, true);
	}

	public AMImageConfigurationEntryImpl(
		String name, String description, String uuid,
		Map<String, String> properties, boolean enabled) {

		_name = name;
		_description = description;
		_uuid = uuid;
		_properties = properties;
		_enabled = enabled;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AMImageConfigurationEntryImpl)) {
			return false;
		}

		AMImageConfigurationEntryImpl amImageConfigurationEntryImpl =
			(AMImageConfigurationEntryImpl)obj;

		if (Objects.equals(_enabled, amImageConfigurationEntryImpl._enabled) &&
			Objects.equals(_name, amImageConfigurationEntryImpl._name) &&
			Objects.equals(
				_properties, amImageConfigurationEntryImpl._properties) &&
			Objects.equals(_uuid, amImageConfigurationEntryImpl._uuid)) {

			return true;
		}

		return false;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public Map<String, String> getProperties() {
		return new HashMap<>(_properties);
	}

	@Override
	public String getUUID() {
		return _uuid;
	}

	@Override
	public int hashCode() {
		int hash =
			_name.hashCode() ^ _uuid.hashCode() ^ Boolean.hashCode(_enabled);

		for (Map.Entry<String, String> entry : _properties.entrySet()) {
			hash ^= entry.hashCode();
		}

		return hash;
	}

	@Override
	public boolean isEnabled() {
		return _enabled;
	}

	private final String _description;
	private final boolean _enabled;
	private final String _name;
	private final Map<String, String> _properties;
	private final String _uuid;

}