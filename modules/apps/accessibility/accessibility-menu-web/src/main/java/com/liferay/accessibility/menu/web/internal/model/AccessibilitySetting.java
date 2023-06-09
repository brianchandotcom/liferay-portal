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

		this.cssClass = cssClass;
		this.defaultValue = defaultValue;
		this.key = key;
		this.label = label;
		this.sessionClicksValue = sessionClicksValue;
	}

	public String getCssClass() {
		return cssClass;
	}

	public boolean getDefaultValue() {
		return defaultValue;
	}

	public String getKey() {
		return key;
	}

	public String getLabel() {
		return label;
	}

	public Boolean getSessionClicksValue() {
		return sessionClicksValue;
	}

	public boolean isEnabled() {
		if (sessionClicksValue != null) {
			return sessionClicksValue;
		}

		return defaultValue;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setSessionClicksValue(Boolean sessionClicksValue) {
		this.sessionClicksValue = sessionClicksValue;
	}

	protected String cssClass;
	protected boolean defaultValue;
	protected String key;
	protected String label;
	protected Boolean sessionClicksValue;

}