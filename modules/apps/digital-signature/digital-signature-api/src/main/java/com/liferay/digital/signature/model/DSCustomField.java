/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.signature.model;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

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

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"fieldId", dsCustomFieldId
		).put(
			"name", name
		).put(
			"show", show
		).put(
			"value", value
		);
	}

	protected long dsCustomFieldId;
	protected String name;
	protected boolean required;
	protected boolean show;
	protected String value;

}