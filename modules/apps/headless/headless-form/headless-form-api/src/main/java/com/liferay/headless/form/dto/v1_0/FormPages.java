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

package com.liferay.headless.form.dto.v1_0;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "FormPages")
public class FormPages {

	public Fields[] getFields() {
		return _fields;
	}

	public String getHeadline() {
		return _headline;
	}

	public Long getId() {
		return _id;
	}

	public String getText() {
		return _text;
	}

	public void setFields(Fields[] fields) {
		_fields = fields;
	}

	public void setHeadline(String headline) {
		_headline = headline;
	}

	public void setId(Long id) {
		_id = id;
	}

	public void setText(String text) {
		_text = text;
	}

	private Fields[] _fields;
	private String _headline;
	private Long _id;
	private String _text;

}