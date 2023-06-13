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

package com.liferay.accessibility.menu.web.internal.model;

/**
 * @author Evan Thibodeau
 */
public class AccessibilitySetting {

	public AccessibilitySetting(
		String cssClass, boolean defaultValue, String key, String label,
		Boolean sessionClicksValue) {

		_cssClass = cssClass;
		_defaultValue = defaultValue;
		_key = key;
		_label = label;
		_sessionClicksValue = sessionClicksValue;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public boolean getDefaultValue() {
		return _defaultValue;
	}

	public String getKey() {
		return _key;
	}

	public String getLabel() {
		return _label;
	}

	public Boolean getSessionClicksValue() {
		return _sessionClicksValue;
	}

	public boolean isEnabled() {
		if (_sessionClicksValue != null) {
			return _sessionClicksValue;
		}

		return _defaultValue;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDefaultValue(boolean defaultValue) {
		_defaultValue = defaultValue;
	}

	public void setKey(String key) {
		_key = key;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setSessionClicksValue(Boolean sessionClicksValue) {
		_sessionClicksValue = sessionClicksValue;
	}

	private String _cssClass;
	private boolean _defaultValue;
	private String _key;
	private String _label;
	private Boolean _sessionClicksValue;

}