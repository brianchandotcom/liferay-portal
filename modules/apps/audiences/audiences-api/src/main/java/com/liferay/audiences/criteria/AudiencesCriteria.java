/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.criteria;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class AudiencesCriteria {

	public AudiencesCriteria(
		String icon, InputType inputType, String key, String label,
		List<Option> options, Type type) {

		_icon = icon;
		_inputType = inputType;
		_key = key;
		_label = label;
		_options = options;
		_type = type;
	}

	public AudiencesCriteria(
		String icon, InputType inputType, String key, String label, Type type) {

		this(icon, inputType, key, label, null, type);
	}

	public String getIcon() {
		return _icon;
	}

	public InputType getInputType() {
		return _inputType;
	}

	public String getKey() {
		return _key;
	}

	public String getLabel() {
		return _label;
	}

	public List<Option> getOptions() {
		return _options;
	}

	public Type getType() {
		return _type;
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

	public enum InputType {

		BOOLEAN("boolean"), DATE("date"), SELECT("select"), TEXT("text");

		public String getValue() {
			return _value;
		}

		private InputType(String value) {
			_value = value;
		}

		private final String _value;

	}

	public enum Type {

		BOOLEAN("boolean"), NUMBER("number"), SET("set"), STRING("string");

		public String getValue() {
			return _value;
		}

		private Type(String value) {
			_value = value;
		}

		private final String _value;

	}

	private final String _icon;
	private final InputType _inputType;
	private final String _key;
	private final String _label;
	private final List<Option> _options;
	private final Type _type;

}