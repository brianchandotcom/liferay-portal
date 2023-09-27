/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.jabsorb.serializer;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;

import org.jabsorb.JSONSerializer;
import org.jabsorb.serializer.Serializer;
import org.jabsorb.serializer.UnmarshallException;

import org.json.JSONObject;

/**
 * @author Tomas Polesovsky
 */
public class LiferayJSONSerializer extends JSONSerializer {

	public LiferayJSONSerializer(
		LiferayJSONDeserializationWhitelist
			liferayJSONDeserializationWhitelist) {

		_liferayJSONDeserializationWhitelist =
			liferayJSONDeserializationWhitelist;
	}

	@Override
	public void registerSerializer(Serializer serializer) {
		if (serializer != null) {
			_liferayJSONDeserializationWhitelist.register(
				_toClassNames(serializer.getSerializableClasses()));
		}

		super.registerSerializer(serializer);
	}

	@Override
	protected Class getClassFromHint(Object object) throws UnmarshallException {
		if (object == null) {
			return null;
		}

		if (object instanceof JSONObject) {
			String className = StringPool.BLANK;

			try {
				JSONObject jsonObject = (JSONObject)object;

				className = jsonObject.getString("javaClass");

				if (!_liferayJSONDeserializationWhitelist.isWhitelisted(
						className)) {

					if (jsonObject.has("serializable")) {
						jsonObject.put(
							"map", jsonObject.remove("serializable"));
					}
					else {
						jsonObject.put("map", new JSONObject());
					}

					jsonObject.put("javaClass", "java.util.HashMap");

					return HashMap.class;
				}

				if (jsonObject.has("contextName")) {
					String contextName = jsonObject.getString("contextName");

					ClassLoader classLoader = ClassLoaderPool.getClassLoader(
						contextName);

					if (classLoader != null) {
						try {
							return Class.forName(className, true, classLoader);
						}
						catch (ClassNotFoundException classNotFoundException) {
							if (_log.isWarnEnabled()) {
								_log.warn(
									StringBundler.concat(
										"Unable to load class ", className,
										" in context ", contextName),
									classNotFoundException);
							}
						}
					}
					else if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to get class loader for class ",
								className, " in context ", contextName));
					}
				}
			}
			catch (Exception exception) {
				throw new UnmarshallException(
					"Unable to get class " + className, exception);
			}
		}

		return super.getClassFromHint(object);
	}

	private String[] _toClassNames(Class<?>[] classes) {
		String[] classNames = new String[classes.length];

		for (int i = 0; i < classes.length; i++) {
			classNames[i] = classes[i].getName();
		}

		return classNames;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayJSONSerializer.class);

	private final LiferayJSONDeserializationWhitelist
		_liferayJSONDeserializationWhitelist;

}