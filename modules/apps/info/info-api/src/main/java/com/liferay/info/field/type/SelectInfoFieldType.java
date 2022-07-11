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

package com.liferay.info.field.type;

import com.liferay.info.localized.InfoLocalizedValue;

import java.util.Collection;
import java.util.Locale;

/**
 * @author Alicia Garcia
 */
public class SelectInfoFieldType implements InfoFieldType {

	public static final SelectInfoFieldType INSTANCE =
		new SelectInfoFieldType();

	public static final Attribute<SelectInfoFieldType, Boolean> MULTIPLE =
		new Attribute<>();

	public static final Attribute<SelectInfoFieldType, Collection<Option>>
		OPTIONS = new Attribute<>();

	@Override
	public String getName() {
		return "select";
	}

	public static class Option {

		public Option(InfoLocalizedValue<String> label, String value) {
			_label = label;
			_value = value;
		}

		public String getLabel(Locale locale) {
			return _label.getValue(locale);
		}

		public String getValue() {
			return _value;
		}

		private final InfoLocalizedValue<String> _label;
		private final String _value;

	}

	private SelectInfoFieldType() {
	}

}