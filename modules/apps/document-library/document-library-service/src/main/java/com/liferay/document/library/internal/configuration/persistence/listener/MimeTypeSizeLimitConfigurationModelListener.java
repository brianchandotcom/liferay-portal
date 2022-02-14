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

package com.liferay.document.library.internal.configuration.persistence.listener;

import com.liferay.document.library.internal.configuration.MimeTypeSizeLimitConfiguration;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Dictionary;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.document.library.internal.configuration.MimeTypeSizeLimitConfiguration",
	service = ConfigurationModelListener.class
)
public class MimeTypeSizeLimitConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		String[] mimeTypeSizeLimit = (String[])properties.get(
			"mimeTypeSizeLimit");

		if (ArrayUtil.isEmpty(mimeTypeSizeLimit)) {
			return;
		}

		for (String mimeTypeSizeString : mimeTypeSizeLimit) {
			String[] parts = StringUtil.split(
				mimeTypeSizeString, CharPool.COLON);

			if (!_isValidMimeTypeName(StringUtil.trim(parts[0]))) {
				throw new ConfigurationModelListenerException(
					parts[0] + " is not a valid mime type name.",
					MimeTypeSizeLimitConfiguration.class,
					MimeTypeSizeLimitConfigurationModelListener.class,
					properties);
			}

			if (!_isValidSizeValue(StringUtil.trim(parts[1]))) {
				throw new ConfigurationModelListenerException(
					parts[0] + " is not a valid mime type value.",
					MimeTypeSizeLimitConfiguration.class,
					MimeTypeSizeLimitConfigurationModelListener.class,
					properties);
			}
		}
	}

	private boolean _isValidMimeTypeName(String contentType) {
		String[] parts = StringUtil.split(contentType, CharPool.SLASH);

		if (parts.length != 2) {
			return false;
		}

		Matcher typeMatcher = _pattern.matcher(parts[0]);

		if (!typeMatcher.matches()) {
			return false;
		}

		Matcher subtypeMatcher = _pattern.matcher(parts[1]);

		if (!subtypeMatcher.matches()) {
			return false;
		}

		return true;
	}

	private boolean _isValidSizeValue(String sizeValue) {
		try {
			int value = Integer.parseInt(sizeValue);

			if (value < 0) {
				return false;
			}

			return true;
		}
		catch (NumberFormatException numberFormatException) {
			return false;
		}
	}

	private static final Pattern _pattern = Pattern.compile(
		"[a-zA-Z0-9][a-zA-Z0-9$!#&^_-]*");

}