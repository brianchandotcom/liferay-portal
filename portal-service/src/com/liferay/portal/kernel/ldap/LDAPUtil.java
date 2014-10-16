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

package com.liferay.portal.kernel.ldap;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.ldap.LDAPFilterValidator;

import java.text.DateFormat;

import java.util.Date;
import java.util.Properties;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

/**
 * @author Toma Bedolla
 * @author Michael Young
 * @author Brian Wing Shun Chan
 * @author James Lefeu
 * @author Vilmos Papp
 */
public class LDAPUtil {

	public static Object getAttributeObject(
			Attributes attributes, Properties properties, String key)
		throws NamingException {

		String id = properties.getProperty(key);

		return getAttributeObject(attributes, id);
	}

	public static Object getAttributeObject(
			Attributes attributes, Properties properties, String key,
			Object defaultValue)
		throws NamingException {

		String id = properties.getProperty(key);

		return getAttributeObject(attributes, id, defaultValue);
	}

	public static Object getAttributeObject(Attributes attributes, String id)
		throws NamingException {

		return getAttributeObject(attributes, id, null);
	}

	public static Object getAttributeObject(
			Attributes attributes, String id, Object defaultValue)
		throws NamingException {

		if (Validator.isNull(id)) {
			return defaultValue;
		}

		Attribute attribute = attributes.get(id);

		if (attribute == null) {
			return defaultValue;
		}

		Object object = attribute.get();

		if (object == null) {
			return defaultValue;
		}

		return object;
	}

	public static String getAttributeString(
			Attributes attributes, Properties properties, String key)
		throws NamingException {

		String id = properties.getProperty(key);

		return getAttributeString(attributes, id);
	}

	public static String getAttributeString(
			Attributes attributes, Properties properties, String key,
			String defaultValue)
		throws NamingException {

		String id = properties.getProperty(key);

		return getAttributeString(attributes, id, defaultValue);
	}

	public static String getAttributeString(Attributes attributes, String id)
		throws NamingException {

		return getAttributeString(attributes, id, StringPool.BLANK);
	}

	public static String getAttributeString(
			Attributes attributes, String id, String defaultValue)
		throws NamingException {

		if (Validator.isNull(id)) {
			return defaultValue;
		}

		Attribute attribute = attributes.get(id);

		if (attribute == null) {
			return defaultValue;
		}

		Object object = attribute.get();

		if (object == null) {
			return defaultValue;
		}

		return object.toString();
	}

	public static String[] getAttributeStringArray(
			Attributes attributes, Properties properties, String key)
		throws NamingException {

		String id = properties.getProperty(key);

		return getAttributeStringArray(attributes, id);
	}

	public static String[] getAttributeStringArray(
			Attributes attributes, String id)
		throws NamingException {

		if (Validator.isNull(id)) {
			return null;
		}

		Attribute attribute = attributes.get(id);

		if (attribute == null) {
			return new String[0];
		}

		int size = attribute.size();

		if (size == 0) {
			return null;
		}

		String[] array = new String[size];

		for (int i = 0; i < size; i++) {
			Object object = attribute.get(i);

			if (object == null) {
				array[i] = StringPool.BLANK;
			}
			else {
				array[i] = object.toString();
			}
		}

		return array;
	}

	public static String getFullProviderURL(String baseURL, String baseDN) {
		return baseURL + StringPool.SLASH + baseDN;
	}

	public static LDAPFilterValidator getLDAPFilterValidator() {
		PortalRuntimePermission.checkGetBeanProperty(LDAPUtil.class);

		return _ldapFilterValidator;
	}

	public static boolean isValidFilter(String filter) {
		return getLDAPFilterValidator().isValidFilter(filter);
	}

	public static Date parseDate(String date) throws Exception {
		String format = "yyyyMMddHHmmss";

		if (date.endsWith("Z")) {
			if (date.indexOf(CharPool.PERIOD) != -1) {
				format = "yyyyMMddHHmmss.S'Z'";
			}
			else {
				format = "yyyyMMddHHmmss'Z'";
			}
		}
		else if ((date.indexOf(CharPool.DASH) != -1) ||
				 (date.indexOf(CharPool.PLUS) != -1)) {

			if (date.indexOf(CharPool.PERIOD) != -1) {
				format = "yyyyMMddHHmmss.SZ";
			}
			else {
				format = "yyyyMMddHHmmssZ";
			}
		}
		else if (date.indexOf(CharPool.PERIOD) != -1) {
			format = "yyyyMMddHHmmss.S";
		}

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			format);

		return dateFormat.parse(date);
	}

	public static void validateFilter(String filter) throws PortalException {
		if (!isValidFilter(filter)) {
			throw new LDAPFilterException("Invalid filter " + filter);
		}
	}

	public static void validateFilter(String filter, String filterPropertyName)
		throws PortalException {

		if (!isValidFilter(filter)) {
			throw new LDAPFilterException(
				"Invalid filter " + filter + " defined by " +
					filterPropertyName);
		}
	}

	public void setLDAPFilterValidator(
		LDAPFilterValidator ldapFilterValidator) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_ldapFilterValidator = ldapFilterValidator;
	}

	private static LDAPFilterValidator _ldapFilterValidator;

}