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

package com.liferay.headless.foundation.dto;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "Email")
public class Email {

	public String getEmail() {
		return _email;
	}

	public Integer getId() {
		return _id;
	}

	public String getSelf() {
		return _self;
	}

	public String getType() {
		return _type;
	}

	public void setEmail(String email) {
		_email = email;
	}

	public void setId(Integer id) {
		_id = id;
	}

	public void setSelf(String self) {
		_self = self;
	}

	public void setType(String type) {
		_type = type;
	}

	private String _email;
	private Integer _id;
	private String _self;
	private String _type;

}