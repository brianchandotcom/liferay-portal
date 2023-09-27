/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;

/**
 * @author Jiaxu Wei
 */
public class EnvPropertiesUtil {

	public static void loadEnvOverrides(
		String envPrefix, long companyId,
		BiConsumer<String, String> biConsumer) {

		String companyEnvPrefix = envPrefix.concat(_PROPS_BY_COMPANY);

		Map<String, String> env = System.getenv();

		for (Map.Entry<String, String> entry : env.entrySet()) {
			String key = entry.getKey();

			if (!key.startsWith(envPrefix)) {
				continue;
			}

			if (key.startsWith(companyEnvPrefix)) {
				if (companyId == GetterUtil.getLong(
						key.substring(companyEnvPrefix.length()), -1)) {

					try {
						Properties properties = PropertiesUtil.load(
							entry.getValue());

						properties.forEach(
							(propertyKey, propertyValue) -> biConsumer.accept(
								String.valueOf(propertyKey),
								String.valueOf(propertyValue)));
					}
					catch (IOException ioException) {
						ReflectionUtil.throwException(ioException);
					}
				}

				continue;
			}

			String newKey = _decode(
				StringUtil.toLowerCase(key.substring(envPrefix.length())));

			if (newKey.equals("include-and-override")) {
				continue;
			}

			biConsumer.accept(newKey, entry.getValue());

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Overrode property ", newKey,
						" with the value from the environment variable ", key));
			}
		}
	}

	private static String _decode(String s) {
		int index = -1;
		int openUnderLine = -1;
		int position = 0;
		StringBundler sb = new StringBundler();

		while ((index = s.indexOf(CharPool.UNDERLINE, index + 1)) != -1) {
			if (openUnderLine == -1) {
				sb.append(s.substring(position, index));

				openUnderLine = index;
				position = index;

				continue;
			}

			String encoded = s.substring(openUnderLine + 1, index);

			Character character = _charPoolChars.get(
				StringUtil.toUpperCase(encoded));

			if (character == null) {
				int value = GetterUtil.get(encoded, -1);

				if (Character.isDefined(value)) {
					sb.append(new String(Character.toChars(value)));
				}
				else {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to decode part \"", encoded,
								"\" from \"", s, "\", preserve it literally"));
					}

					sb.append(s.substring(openUnderLine, index + 1));
				}
			}
			else {
				sb.append(character);
			}

			openUnderLine = -1;
			position = index + 1;
		}

		sb.append(s.substring(position));

		return sb.toString();
	}

	private static Map<String, Character> _getCharPoolChars() {
		try {
			Map<String, Character> charPoolChars = new HashMap<>();

			for (Field field : CharPool.class.getFields()) {
				if (Modifier.isStatic(field.getModifiers()) &&
					(field.getType() == char.class)) {

					charPoolChars.put(
						StringUtil.removeChar(
							field.getName(), CharPool.UNDERLINE),
						field.getChar(null));
				}
			}

			return charPoolChars;
		}
		catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	private static final String _PROPS_BY_COMPANY = "PROPS_BY_COMPANY_";

	private static final Log _log = LogFactoryUtil.getLog(
		EnvPropertiesUtil.class);

	private static final Map<String, Character> _charPoolChars =
		_getCharPoolChars();

}