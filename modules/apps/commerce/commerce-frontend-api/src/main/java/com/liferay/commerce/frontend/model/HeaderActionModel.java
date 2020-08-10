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

package com.liferay.commerce.frontend.model;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class HeaderActionModel {

	public HeaderActionModel() {
	}

	public HeaderActionModel(
		String additionalClasses, String href, String id, String label) {

		_additionalClasses = additionalClasses;
		_href = href;
		_id = id;
		_label = label;
	}

	public HeaderActionModel(
		String additionalClasses, String formId, String href, String id,
		String label) {

		_additionalClasses = additionalClasses;
		_formId = formId;
		_href = href;
		_id = id;
		_label = label;
	}

	public String getAdditionalClasses() {
		return _additionalClasses;
	}

	public String getFormId() {
		return _formId;
	}

	public String getHref() {
		return _href;
	}

	public String getId() {
		return _id;
	}

	public String getLabel() {
		return _label;
	}

	public void setAdditionalClasses(String additionalClasses) {
		_additionalClasses = additionalClasses;
	}

	public void setFormId(String formId) {
		_formId = formId;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	private String _additionalClasses;
	private String _formId;
	private String _href;
	private String _id;
	private String _label;

}