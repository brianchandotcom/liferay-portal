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

package com.liferay.headless.form.dto;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "FormRecord")
public class FormRecord {

	public String getCreator() {
		return _creator;
	}

	public String getDateCreated() {
		return _dateCreated;
	}

	public String getDateModified() {
		return _dateModified;
	}

	public String getDatePublished() {
		return _datePublished;
	}

	public Boolean getDraft() {
		return _draft;
	}

	public Object getFieldValues() {
		return _fieldValues;
	}

	public Form getForm() {
		return _form;
	}

	public Integer getId() {
		return _id;
	}

	public String getSelf() {
		return _self;
	}

	public void setCreator(String creator) {
		_creator = creator;
	}

	public void setDateCreated(String dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateModified(String dateModified) {
		_dateModified = dateModified;
	}

	public void setDatePublished(String datePublished) {
		_datePublished = datePublished;
	}

	public void setDraft(Boolean draft) {
		_draft = draft;
	}

	public void setFieldValues(Object fieldValues) {
		_fieldValues = fieldValues;
	}

	public void setForm(Form form) {
		_form = form;
	}

	public void setId(Integer id) {
		_id = id;
	}

	public void setSelf(String self) {
		_self = self;
	}

	private String _creator;
	private String _dateCreated;
	private String _dateModified;
	private String _datePublished;
	private Boolean _draft;
	private Object _fieldValues;
	private Form _form;
	private Integer _id;
	private String _self;

}