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

import com.liferay.adaptive.media.image.configuration.AdaptiveMediaImageConfigurationEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

/**
 * Parses ConfigAdmin configuration entries.
 *
 * <p>
 * A configuration entry must be a string of the following form (items inside
 * square brackets are optional):
 * </p>
 *
 * <p>
 * <pre>
 * <code>
 * name:uuid:key0=val0;key1=val1;...;keyN=valN[:enable=flag]
 * </code>
 * </pre>
 * </p>
 *
 * <p>
 * Each part of the string is described below:
 * </p>
 *
 * <ul>
 * <li>
 * <code>name:</code> an arbitrary encoded {@link String}
 * </li>
 * <li>
 * <code>description:</code> an arbitrary encoded {@link String}
 * </li>
 * <li>
 * <code>uuid:</code> a unique identifier. No two configuration entries should
 * have the same UUID
 * </li>
 * <li>
 * The key and value pairs can be anything, but consumers of the resulting
 * {@link AdaptiveMediaImageConfigurationEntry} may require a particular set of
 * attributes.
 * </li>
 * <li>
 * <code>enabled:</code> a boolean value. If <code>false</code>, the
 * configuration is ignored when processing images. If unspecified, the default
 * value is <code>true</code>.
 * </li>
 * </ul>
 *
 * @author Adolfo Pérez
 */
@Component(
	immediate = true, service = AdaptiveMediaImageConfigurationEntryParser.class
)
public class AdaptiveMediaImageConfigurationEntryParser {

	public String getConfigurationString(
		AdaptiveMediaImageConfigurationEntry configurationEntry) {

		StringBundler sb = new StringBundler();

		sb.append(HttpUtil.encodeURL(configurationEntry.getName()));
		sb.append(StringPool.COLON);
		sb.append(HttpUtil.encodeURL(configurationEntry.getDescription()));
		sb.append(StringPool.COLON);
		sb.append(configurationEntry.getUUID());
		sb.append(StringPool.COLON);

		Map<String, String> properties = configurationEntry.getProperties();

		if (properties.get("max-height") != null) {
			int height = GetterUtil.getInteger(properties.get("max-height"));

			sb.append("max-height=");
			sb.append(height);

			if (properties.get("max-width") != null) {
				sb.append(StringPool.SEMICOLON);
			}
		}

		if (properties.get("max-width") != null) {
			int width = GetterUtil.getInteger(properties.get("max-width"));

			sb.append("max-width=");
			sb.append(width);
		}

		sb.append(StringPool.COLON);

		sb.append("enabled=");
		sb.append(String.valueOf(configurationEntry.isEnabled()));

		return sb.toString();
	}

	/**
	 * Returns a configuration entry parsed from the configuration line's data.
	 *
	 * @param  s the configuration line to parse
	 * @return a {@link AdaptiveMediaImageConfigurationEntry} with the line data
	 */
	public AdaptiveMediaImageConfigurationEntry parse(String s) {
		if (Validator.isNull(s)) {
			throw new IllegalArgumentException(
				"Invalid image adaptive media configuration: " + s);
		}

		String[] fields = _FIELD_SEPARATOR_PATTERN.split(s);

		if ((fields.length != 4) && (fields.length != 5)) {
			throw new IllegalArgumentException(
				"Invalid image adaptive media configuration: " + s);
		}

		String name = fields[0];

		name = HttpUtil.decodeURL(name);

		String description = fields[1];

		description = HttpUtil.decodeURL(description);

		String uuid = fields[2];

		if (Validator.isNull(name) || Validator.isNull(uuid)) {
			throw new IllegalArgumentException(
				"Invalid image adaptive media configuration: " + s);
		}

		String[] attributes = _ATTRIBUTE_SEPARATOR_PATTERN.split(fields[3]);

		Map<String, String> properties = new HashMap<>();

		for (String attribute : attributes) {
			String[] keyValuePair = _KEY_VALUE_SEPARATOR_PATTERN.split(
				attribute);

			properties.put(keyValuePair[0], keyValuePair[1]);
		}

		boolean enabled = true;

		if (fields.length == 5) {
			String disabledAttribute = fields[4];

			Matcher matcher = _DISABLED_SEPARATOR_PATTERN.matcher(
				disabledAttribute);

			if (!matcher.matches()) {
				throw new IllegalArgumentException(
					"Invalid image adaptive media configuration: " + s);
			}

			enabled = GetterUtil.getBoolean(matcher.group(1));
		}

		return new AdaptiveMediaImageConfigurationEntryImpl(
			name, description, uuid, properties, enabled);
	}

	private static final Pattern _ATTRIBUTE_SEPARATOR_PATTERN = Pattern.compile(
		"\\s*;\\s*");

	private static final Pattern _DISABLED_SEPARATOR_PATTERN = Pattern.compile(
		"enabled=(true|false)");

	private static final Pattern _FIELD_SEPARATOR_PATTERN = Pattern.compile(
		"\\s*:\\s*");

	private static final Pattern _KEY_VALUE_SEPARATOR_PATTERN = Pattern.compile(
		"\\s*=\\s*");

}