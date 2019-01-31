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
@XmlRootElement(name = "Phone")
public class Phone {

	public String getExtension() {
		return _extension;
	}

	public Integer getId() {
		return _id;
	}

	public String getPhoneNumber() {
		return _phoneNumber;
	}

	public String getPhoneType() {
		return _phoneType;
	}

	public String getSelf() {
		return _self;
	}

	public void setExtension(String extension) {
		_extension = extension;
	}

	public void setId(Integer id) {
		_id = id;
	}

	public void setPhoneNumber(String phoneNumber) {
		_phoneNumber = phoneNumber;
	}

	public void setPhoneType(String phoneType) {
		_phoneType = phoneType;
	}

	public void setSelf(String self) {
		_self = self;
	}

	private String _extension;
	private Integer _id;
	private String _phoneNumber;
	private String _phoneType;
	private String _self;

}