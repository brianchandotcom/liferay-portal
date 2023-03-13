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

package com.liferay.osgi.util.configuration;

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import java.nio.ByteBuffer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Shuyang Zhou
 */
public class ConfigurationPersistenceUtil {

	public static long update(Object target, Map<String, Object> properties)
		throws Exception {

		Map<String, Object> scrubbedProperties = new HashMap<>();

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			String key = entry.getKey();

			if (key.equals("component.id") || key.equals("component.name")) {
				continue;
			}

			scrubbedProperties.put(key, entry.getValue());
		}

		Bundle bundle = FrameworkUtil.getBundle(target.getClass());

		File dataFile = bundle.getDataFile("configuration.data");

		if (!dataFile.exists()) {
			return _saveConfiguration(bundle, scrubbedProperties, dataFile);
		}

		Deserializer deserializer = new Deserializer(
			ByteBuffer.wrap(FileUtil.getBytes(dataFile)));

		if (deserializer.readLong() != bundle.getLastModified()) {
			return _saveConfiguration(bundle, scrubbedProperties, dataFile);
		}

		int size = deserializer.readInt();

		if (scrubbedProperties.size() != size) {
			return _saveConfiguration(bundle, scrubbedProperties, dataFile);
		}

		for (int i = 0; i < size; i++) {
			if (!Objects.equals(
					scrubbedProperties.get(deserializer.readString()),
					deserializer.readObject())) {

				return _saveConfiguration(bundle, scrubbedProperties, dataFile);
			}
		}

		return dataFile.lastModified();
	}

	private static long _saveConfiguration(
			Bundle bundle, Map<String, Object> scrubbedProperties,
			File dataFile)
		throws Exception {

		Serializer serializer = new Serializer();

		serializer.writeLong(bundle.getLastModified());

		serializer.writeInt(scrubbedProperties.size());

		for (Map.Entry<String, Object> entry : scrubbedProperties.entrySet()) {
			serializer.writeString(entry.getKey());
			serializer.writeObject((Serializable)entry.getValue());
		}

		try (OutputStream outputStream = new FileOutputStream(dataFile)) {
			serializer.writeTo(outputStream);
		}

		return dataFile.lastModified();
	}

}