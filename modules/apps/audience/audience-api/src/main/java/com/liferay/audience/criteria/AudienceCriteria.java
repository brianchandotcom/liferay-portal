/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.criteria;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class AudienceCriteria {

	public AudienceCriteria(
		String icon, String key, String label, List<Option> options,
		Type type) {

		_icon = icon;
		_key = key;
		_label = label;
		_options = options;
		_type = type;
	}

	public AudienceCriteria(String icon, String key, String label, Type type) {
		this(icon, key, label, null, type);
	}

	public String getIcon() {
		return _icon;
	}

	public String getKey() {
		return _key;
	}

	public String getLabel() {
		return _label;
	}

	public List<Operator> getOperators() {
		return _type.getOperators();
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

	public enum Operator {

		EQ("eq"), GT("gt"), GTE("gte"), INCLUDES("includes"), LT("lt"),
		LTE("lte"), NOT_EQ("not_eq"), NOT_INCLUDES("not_includes");

		public String getValue() {
			return _value;
		}

		private Operator(String value) {
			_value = value;
		}

		private final String _value;

	}

	public enum Type {

		COLLECTION(Operator.INCLUDES, Operator.NOT_INCLUDES),
		DATE(
			Operator.EQ, Operator.GT, Operator.GTE, Operator.LT, Operator.LTE,
			Operator.NOT_EQ),
		NUMBER(
			Operator.EQ, Operator.GT, Operator.GTE, Operator.LT, Operator.LTE,
			Operator.NOT_EQ),
		OPTIONS(Operator.EQ, Operator.NOT_EQ),
		STRING(
			Operator.EQ, Operator.INCLUDES, Operator.NOT_EQ,
			Operator.NOT_INCLUDES);

		public List<Operator> getOperators() {
			return _operators;
		}

		private Type(Operator... operators) {
			_operators = Collections.unmodifiableList(Arrays.asList(operators));
		}

		private final List<Operator> _operators;

	}

	private final String _icon;
	private final String _key;
	private final String _label;
	private final List<Option> _options;
	private final Type _type;

}