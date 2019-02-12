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

package com.liferay.headless.web.experience.dto.v1_0;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "Fields")
public class Fields {

	public String getDataType() {
		return _dataType;
	}

	public String getInputControl() {
		return _inputControl;
	}

	public String getLabel() {
		return _label;
	}

	public Boolean getLocalizable() {
		return _localizable;
	}

	public Boolean getMultiple() {
		return _multiple;
	}

	public String getName() {
		return _name;
	}

	public Options[] getOptions() {
		return _options;
	}

	public String getPredefinedValue() {
		return _predefinedValue;
	}

	public Boolean getRepeatable() {
		return _repeatable;
	}

	public Boolean getRequired() {
		return _required;
	}

	public Boolean getShowLabel() {
		return _showLabel;
	}

	public void setDataType(String dataType) {
		_dataType = dataType;
	}

	public void setInputControl(String inputControl) {
		_inputControl = inputControl;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setLocalizable(Boolean localizable) {
		_localizable = localizable;
	}

	public void setMultiple(Boolean multiple) {
		_multiple = multiple;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOptions(Options[] options) {
		_options = options;
	}

	public void setPredefinedValue(String predefinedValue) {
		_predefinedValue = predefinedValue;
	}

	public void setRepeatable(Boolean repeatable) {
		_repeatable = repeatable;
	}

	public void setRequired(Boolean required) {
		_required = required;
	}

	public void setShowLabel(Boolean showLabel) {
		_showLabel = showLabel;
	}

	private String _dataType;
	private String _inputControl;
	private String _label;
	private Boolean _localizable;
	private Boolean _multiple;
	private String _name;
	private Options[] _options;
	private String _predefinedValue;
	private Boolean _repeatable;
	private Boolean _required;
	private Boolean _showLabel;

}