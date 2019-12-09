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

package com.liferay.headless.discovery.internal.dto;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 */
@XmlRootElement
public class Resource {

	public Hint getHint() {
		return _hint;
	}

	public String getHref() {
		return _href;
	}

	public String getHrefTemplate() {
		return _hrefTemplate;
	}

	public Map<String, String> getHrefVars() {
		return _hrefVars;
	}

	public void setHint(Hint hint) {
		_hint = hint;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setHrefTemplate(String hrefTemplate) {
		_hrefTemplate = hrefTemplate;
	}

	public void setHrefVars(Map<String, String> hrefVars) {
		_hrefVars = hrefVars;
	}

	private Hint _hint;
	private String _href;
	private String _hrefTemplate;
	private Map<String, String> _hrefVars = new HashMap<>();

}