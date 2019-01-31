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
@XmlRootElement(name = "WebUrl")
public class WebUrl {

	public Integer getId() {
		return _id;
	}

	public String getSelf() {
		return _self;
	}

	public String getUrl() {
		return _url;
	}

	public String getUrlType() {
		return _urlType;
	}

	public void setId(Integer id) {
		_id = id;
	}

	public void setSelf(String self) {
		_self = self;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public void setUrlType(String urlType) {
		_urlType = urlType;
	}

	private Integer _id;
	private String _self;
	private String _url;
	private String _urlType;

}