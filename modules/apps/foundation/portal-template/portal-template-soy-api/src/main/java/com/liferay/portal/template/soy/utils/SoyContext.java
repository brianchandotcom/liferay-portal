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

package com.liferay.portal.template.soy.utils;

import com.liferay.osgi.util.service.OSGiServiceUtil;
import com.liferay.portal.template.soy.constants.SoyTemplateConstants;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Bruno Basto
 */
public class SoyContext extends HashMap<String, Object> {

	public SoyContext() {
		_injectedData = new HashMap<>();

		put(SoyTemplateConstants.INJECTED_DATA, _injectedData);
	}

	public void clearInjectedData() {
		_injectedData.clear();
	}

	public void putHTML(String key, String value) {
		Bundle bundle = FrameworkUtil.getBundle(SoyContext.class);

		SoyRawData soyRawData = new SoyRawData() {

			@Override
			public Object getValue() {
				return OSGiServiceUtil.callService(
					bundle.getBundleContext(), SoyHTMLSanitizer.class,
					soyHTMLSanitizer -> soyHTMLSanitizer.sanitize(value));
			}

		};

		put(key, soyRawData);
	}

	public void putInjectedData(String key, Object value) {
		_injectedData.put(key, value);
	}

	public void removeInjectedData(String key) {
		_injectedData.remove(key);
	}

	private final Map<String, Object> _injectedData;

}