/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.accessibility.menu.web.internal.model;

/**
 * @author Evan Thibodeau
 */
public class AccessibilitySetting {

	public AccessibilitySetting(
		String cssClass, boolean defaultValue, String description, String key,
		String label, Boolean sessionClicksValue) {

		_cssClass = cssClass;
		_defaultValue = defaultValue;
		_description = description;
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

	public String getDescription() {
		return _description;
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

	public void setDescription(String description) {
		_description = description;
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
	private String _description;
	private String _key;
	private String _label;
	private Boolean _sessionClicksValue;

}