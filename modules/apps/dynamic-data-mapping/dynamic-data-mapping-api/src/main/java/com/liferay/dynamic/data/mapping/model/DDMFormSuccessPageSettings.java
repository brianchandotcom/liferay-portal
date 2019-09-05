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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.petra.lang.HashUtil;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class DDMFormSuccessPageSettings implements Serializable {

	public DDMFormSuccessPageSettings() {
		_body = new LocalizedValue();
		_title = new LocalizedValue();
	}

	public DDMFormSuccessPageSettings(
		DDMFormSuccessPageSettings ddmFormSuccessPageSettings) {

		_body = new LocalizedValue(ddmFormSuccessPageSettings._body);
		_enabled = ddmFormSuccessPageSettings._enabled;
		_title = new LocalizedValue(ddmFormSuccessPageSettings._title);
	}

	public DDMFormSuccessPageSettings(
		LocalizedValue body, LocalizedValue title, boolean enabled) {

		_body = body;
		_title = title;
		_enabled = enabled;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormSuccessPageSettings)) {
			return false;
		}

		DDMFormSuccessPageSettings ddmFormSuccessPageSettings =
			(DDMFormSuccessPageSettings)obj;

		if (Objects.equals(_body, ddmFormSuccessPageSettings._body) &&
			Objects.equals(_enabled, ddmFormSuccessPageSettings._enabled) &&
			Objects.equals(_title, ddmFormSuccessPageSettings._title)) {

			return true;
		}

		return false;
	}

	public LocalizedValue getBody() {
		return _body;
	}

	public LocalizedValue getTitle() {
		return _title;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _body);

		hash = HashUtil.hash(hash, _enabled);

		return HashUtil.hash(hash, _title);
	}

	public boolean isEnabled() {
		return _enabled;
	}

	public void setBody(LocalizedValue body) {
		_body = body;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	public void setTitle(LocalizedValue title) {
		_title = title;
	}

	private LocalizedValue _body;
	private boolean _enabled;
	private LocalizedValue _title;

}