/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.configuration;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.URLCodec;
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
 * </pre></p>
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
 * {@link AMImageConfigurationEntry} may require a particular set of
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
@Component(service = AMImageConfigurationEntryParser.class)
public class AMImageConfigurationEntryParser {

	public String getConfigurationString(
		AMImageConfigurationEntry amImageConfigurationEntry) {

		StringBundler sb = new StringBundler(14);

		sb.append(URLCodec.encodeURL(amImageConfigurationEntry.getName()));
		sb.append(StringPool.COLON);
		sb.append(
			URLCodec.encodeURL(amImageConfigurationEntry.getDescription()));
		sb.append(StringPool.COLON);
		sb.append(amImageConfigurationEntry.getUUID());
		sb.append(StringPool.COLON);

		Map<String, String> properties =
			amImageConfigurationEntry.getProperties();

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
		sb.append(String.valueOf(amImageConfigurationEntry.isEnabled()));

		return sb.toString();
	}

	/**
	 * Returns a configuration entry parsed from the configuration line's data.
	 *
	 * @param  s the configuration line to parse
	 * @return a {@link AMImageConfigurationEntry} with the line data
	 */
	public AMImageConfigurationEntry parse(String s) {
		if (Validator.isNull(s)) {
			throw new IllegalArgumentException(
				"Invalid image adaptive media configuration: " + s);
		}

		String[] fields = _fieldSeparatorPattern.split(s);

		if ((fields.length != 4) && (fields.length != 5)) {
			throw new IllegalArgumentException(
				"Invalid image adaptive media configuration: " + s);
		}

		String name = fields[0];

		name = HttpComponentsUtil.decodeURL(name);

		String description = fields[1];

		description = HttpComponentsUtil.decodeURL(description);

		String uuid = fields[2];

		if (Validator.isNull(name) || Validator.isNull(uuid)) {
			throw new IllegalArgumentException(
				"Invalid image adaptive media configuration: " + s);
		}

		String[] attributes = _attributeSeparatorPattern.split(fields[3]);

		Map<String, String> properties = new HashMap<>();

		for (String attribute : attributes) {
			String[] keyValuePair = _keyValueSeparatorPattern.split(attribute);

			properties.put(keyValuePair[0], keyValuePair[1]);
		}

		boolean enabled = true;

		if (fields.length == 5) {
			String disabledAttribute = fields[4];

			Matcher matcher = _disabledSeparatorPattern.matcher(
				disabledAttribute);

			if (!matcher.matches()) {
				throw new IllegalArgumentException(
					"Invalid image adaptive media configuration: " + s);
			}

			enabled = GetterUtil.getBoolean(matcher.group(1));
		}

		return new AMImageConfigurationEntryImpl(
			name, description, uuid, properties, enabled);
	}

	private static final Pattern _attributeSeparatorPattern = Pattern.compile(
		"\\s*;\\s*");
	private static final Pattern _disabledSeparatorPattern = Pattern.compile(
		"enabled=(true|false)");
	private static final Pattern _fieldSeparatorPattern = Pattern.compile(
		"\\s*:\\s*");
	private static final Pattern _keyValueSeparatorPattern = Pattern.compile(
		"\\s*=\\s*");

}