/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael C. Han
 */
public class BackgroundTaskStatus implements Serializable {

	public void clearAttributes() {
		_attributes.clear();
	}

	public Serializable getAttribute(String key) {
		return _attributes.get(key);
	}

	public Map<String, Serializable> getAttributes() {
		return Collections.unmodifiableMap(_attributes);
	}

	public String getAttributesJSON() {
		return JSONFactoryUtil.serialize(_attributes);
	}

	public void setAttribute(String key, Serializable value) {
		_attributes.put(key, value);
	}

	private final Map<String, Serializable> _attributes =
		new ConcurrentHashMap<>();

}