/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.propertiesdoc;

import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
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

	private List<String> _comments = Collections.emptyList();
	private String _defaultProperties;
	private String _exampleProperties;
	private List<PropertyComment> _propertyComments = Collections.emptyList();
	private final String _text;
	private String _title;

}