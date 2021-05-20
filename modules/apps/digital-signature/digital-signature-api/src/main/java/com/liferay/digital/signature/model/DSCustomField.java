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

package com.liferay.digital.signature.model;

/**
 * @author Victor Trajano
 */
public class DSCustomField {

	public long getDSCustomFieldId() {
		return dsCustomFieldId;
	}

	public String getName() {
		return name;
	}

	public boolean getShow() {
		return show;
	}

	public String getValue() {
		return value;
	}

	public boolean isRequired() {
		return required;
	}

	public void setDSCustomFieldId(long dsCustomFieldId) {
		this.dsCustomFieldId = dsCustomFieldId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public void setValue(String value) {
		this.value = value;
	}

	protected long dsCustomFieldId;
	protected String name;
	protected boolean required;
	protected boolean show;
	protected String value;

}