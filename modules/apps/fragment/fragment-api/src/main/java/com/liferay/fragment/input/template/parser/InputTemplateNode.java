/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.input.template.parser;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class InputTemplateNode extends LinkedHashMap<String, Object> {

	public InputTemplateNode(
		String errorMessage, String helpText, String label, String name,
		boolean required, boolean showHelpText, boolean showLabel, String type,
		String value) {

		_errorMessage = errorMessage;
		_helpText = helpText;
		_label = label;
		_name = name;
		_required = required;
		_showHelpText = showHelpText;
		_showLabel = showLabel;
		_type = type;
		_value = value;

		put("errorMessage", errorMessage);
		put("helpText", helpText);
		put("label", label);
		put("name", name);
		put("required", required);
		put("showHelpText", showHelpText);
		put("showLabel", showLabel);
		put("type", type);
		put("value", value);
	}

	public void addAttribute(String name, Object object) {
		_attributes.put(name, object);
	}

	public Map<String, Object> getAttributes() {
		return _attributes;
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

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"attributes",
			() -> {
				JSONObject attributesJSONObject =
					JSONFactoryUtil.createJSONObject();

				for (Map.Entry<String, Object> entry : _attributes.entrySet()) {
					attributesJSONObject.put(entry.getKey(), entry.getValue());
				}

				return attributesJSONObject;
			}
		).put(
			"errorMessage", _errorMessage
		).put(
			"helpText", HtmlUtil.escapeJS(_helpText)
		).put(
			"label", HtmlUtil.escapeJS(_label)
		).put(
			"name", _name
		).put(
			"required", _required
		).put(
			"showHelpText", _showHelpText
		).put(
			"showLabel", _showLabel
		).put(
			"type", _type
		).put(
			"value", _value
		);
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

		@Override
		public String toString() {
			return JSONUtil.put(
				"label", _label
			).put(
				"value", _value
			).toString();
		}

		private final String _label;
		private final String _value;

	}

	private final Map<String, Object> _attributes = new HashMap<>();
	private final String _errorMessage;
	private final String _helpText;
	private final String _label;
	private final String _name;
	private final boolean _required;
	private final boolean _showHelpText;
	private final boolean _showLabel;
	private final String _type;
	private final String _value;

}