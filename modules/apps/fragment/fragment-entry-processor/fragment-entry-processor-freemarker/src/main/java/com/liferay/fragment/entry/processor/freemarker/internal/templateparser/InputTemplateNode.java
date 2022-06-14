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

package com.liferay.fragment.entry.processor.freemarker.internal.templateparser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class InputTemplateNode extends LinkedHashMap<String, Object> {

	public InputTemplateNode(
		String dataType, String errorMessage, String helpText, String label,
		String name, boolean required, boolean showHelpText, boolean showLabel,
		String type, String value) {

		_dataType = dataType;
		_errorMessage = errorMessage;
		_helpText = helpText;
		_label = label;
		_name = name;
		_required = required;
		_showHelpText = showHelpText;
		_showLabel = showLabel;
		_type = type;
		_value = value;

		put("dataType", dataType);
		put("errorMessage", errorMessage);
		put("helpText", helpText);
		put("label", label);
		put("name", name);
		put("required", required);
		put("showHelpText", showHelpText);
		put("showLabel", showLabel);
		put("type", type);
		put("value", value);

		put("options", _options);
	}

	public void addOption(String label, String value) {
		_options.add(new Option(label, value));
	}

	public String getDataType() {
		return _dataType;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public String getHelpText() {
		return _helpText;
	}

	public String getInputLabel() {
		return _label;
	}

	public String getInputName() {
		return _name;
	}

	public String getInputValue() {
		return _value;
	}

	public List<InputTemplateNode.Option> getOptions() {
		return _options;
	}

	public String getType() {
		return _type;
	}

	public boolean isRequired() {
		return _required;
	}

	public boolean isShowHelpText() {
		return _showHelpText;
	}

	public boolean isShowLabel() {
		return _showLabel;
	}

	public static class Option {

		public Option(String label, String value) {
			_label = label;
			_value = value;
		}

		public String getLabel() {
			return _label;
		}

		public String getValue() {
			return _value;
		}

		private final String _label;
		private final String _value;

	}

	private final String _dataType;
	private final String _errorMessage;
	private final String _helpText;
	private final String _label;
	private final String _name;
	private final List<InputTemplateNode.Option> _options = new ArrayList<>();
	private final boolean _required;
	private final boolean _showHelpText;
	private final boolean _showLabel;
	private final String _type;
	private final String _value;

}