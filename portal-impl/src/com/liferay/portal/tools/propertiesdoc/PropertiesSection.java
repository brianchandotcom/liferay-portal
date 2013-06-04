/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.propertiesdoc;

import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jesse Rao
 * @author James Hinkey
 */
public class PropertiesSection {

	public PropertiesSection(String text) {
		_text = text;
	}

	public List<String> getComments() {
		return _comments;
	}

	public String getDefaultProperties() {
		return _defaultProperties;
	}

	public String getExampleProperties() {
		return _exampleProperties;
	}

	public List<PropertyComment> getPropertyComments() {
		return _propertyComments;
	}

	public String getText() {
		return _text;
	}

	public String getTitle() {
		return _title;
	}

	public boolean hasComments() {
		return !_comments.isEmpty();
	}

	public boolean hasDefaultProperties() {
		return Validator.isNotNull(_defaultProperties);
	}

	public boolean hasExampleProperties() {
		return Validator.isNotNull(_exampleProperties);
	}

	public boolean hasPropertyComments() {
		return !_propertyComments.isEmpty();
	}

	public boolean hasTitle() {
		return Validator.isNotNull(_title);
	}

	public void setComments(List<String> comments) {
		_comments = comments;
	}

	public void setDefaultProperties(String defaultProperties) {
		_defaultProperties = defaultProperties;
	}

	public void setExampleProperties(String exampleProperties) {
		_exampleProperties = exampleProperties;
	}

	public void setPropertyComments(List<PropertyComment> propertyComments) {
		_propertyComments = propertyComments;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private List<String> _comments = new ArrayList<String>();
	private String _defaultProperties;
	private String _exampleProperties;
	private List<PropertyComment> _propertyComments =
		new ArrayList<PropertyComment>();
	private final String _text;
	private String _title;

}