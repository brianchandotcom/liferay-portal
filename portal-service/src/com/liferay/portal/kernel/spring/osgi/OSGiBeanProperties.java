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
package com.liferay.portal.kernel.spring.osgi;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * OSGi service properties used when publishing spring beans as services.
 *
 * @author Raymond Augé
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OSGiBeanProperties {

	/**
	 * Direct properties.
	 *
	 * <p>
	 * Each property string is specified as {@code "key=value"}. The type of the
	 * property value is string.
	 *
	 * <p>
	 * To specify a property with multiple values, use multiple key, value
	 * pairs. For example, {@code "foo=bar", "foo=baz"}.
	 */
	String[] property() default {};

	/**
	 * If not blank, this value will be used as the prefix for retrieving
	 * properties from portal.properties.
	 */
	String portalPropertyPrefix() default "";

	/**
	 * Determines if the prefix is removed from properties.
	 */
	boolean portalPropertiesRemovePrefix() default true;

	/**
	 * A conversion helper class for the {@link OSGiBeanProperties} annotation.
	 */
	public static class Convert {

		/**
		 * @param  object, possibly annotated with {@link OSGiBeanProperties}
		 * @return a map, after converting the annotation to properties, which
		 *         may be empty if there are no properties found, or null if the
		 *         object was not annotated with {@link OSGiBeanProperties}
		 */
		public static Map<String, Object> fromObject(Object object) {
			Class<? extends Object> clazz = object.getClass();

			OSGiBeanProperties osgiBeanProperties = clazz.getAnnotation(
				OSGiBeanProperties.class);

			if (osgiBeanProperties == null) {
				return null;
			}

			return toMap(osgiBeanProperties);
		}

		/**
		 * @param  osgiBeanProperties an instance of {@link OSGiBeanProperties}
		 *         which will be read for properties
		 * @return a map, after converting the annotation to properties, which
		 *         may be empty if there are no properties found
		 */
		@SuppressWarnings("unchecked")
		public static Map<String, Object> toMap(
			OSGiBeanProperties osgiBeanProperties) {

			Map<String, Object> properties = new HashMap<String, Object>();

			String[] propertiesArray = osgiBeanProperties.property();

			for (String propertyString : propertiesArray) {
				String[] parts = propertyString.split(StringPool.EQUAL, 1);

				if (parts.length <= 1) {
					continue;
				}

				Object object = properties.get(parts[0]);

				if (object == null) {
					properties.put(parts[0], parts[1]);
				}
				else if (object instanceof String) {
					properties.put(
						parts[0], new String[] {(String)object, parts[1]});
				}
				else if (object.getClass().isArray()) {
					properties.put(
						parts[0], ArrayUtil.append((String[])object, parts[1]));
				}
			}

			String portalPropertyPrefix =
				osgiBeanProperties.portalPropertyPrefix();

			if (Validator.isNotNull(portalPropertyPrefix)) {
				Properties portalProperties = PropsUtil.getProperties(
					portalPropertyPrefix,
					osgiBeanProperties.portalPropertiesRemovePrefix());

				properties.putAll(PropertiesUtil.toMap(portalProperties));
			}

			return properties;
		}

	}

}