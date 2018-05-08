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

package com.liferay.portal.template.soy.internal;

import com.liferay.portal.template.soy.constants.SoyTemplateConstants;
import com.liferay.portal.template.soy.internal.util.SoyHTMLSanitizerUtil;
import com.liferay.portal.template.soy.utils.SoyContext;
import com.liferay.portal.template.soy.utils.SoyRawData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Tambara
 */
public class SoyContextImpl
	extends HashMap<String, Object> implements SoyContext {

	public SoyContextImpl() {
		_injectedData = new HashMap<>();

		put(SoyTemplateConstants.INJECTED_DATA, _injectedData);
	}

	@Override
	public void clearInjectedData() {
		_injectedData.clear();
	}

	@Override
	public void putHTML(String key, String value) {
		SoyRawData soyRawData = new SoyRawData() {

			@Override
			public Object getValue() {
				return SoyHTMLSanitizerUtil.sanitize(value);
			}

		};

		put(key, soyRawData);
	}

	@Override
	public void putInjectedData(String key, Object value) {
		_injectedData.put(key, value);
	}

	@Override
	public void removeInjectedData(String key) {
		_injectedData.remove(key);
	}

	private final Map<String, Object> _injectedData;

}