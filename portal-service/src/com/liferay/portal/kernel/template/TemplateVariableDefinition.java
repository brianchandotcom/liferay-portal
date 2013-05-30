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

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Jorge Ferrer
 */
public class TemplateVariableDefinition {

	public TemplateVariableDefinition(
		String label, Class<?> clazz, String variableName) {

		this(
			label, clazz, StringPool.BLANK, variableName, label.concat("-help"),
			false, null);
	}

	public TemplateVariableDefinition(
		String label, Class<?> clazz, String dataType, String variableName,
		String help, boolean repeatable,
		TemplateVariableCodeHandler templateVariableCodeHandler) {

		_label = label;
		_clazz = clazz;
		_dataType = dataType;
		_name = variableName;
		_help = help;
		_repeatable = repeatable;
		_templateVariableCodeHandler = templateVariableCodeHandler;
	}

	public TemplateVariableDefinition(
		String label, Class<?> clazz, String variableName,
		TemplateVariableDefinition itemTemplateVariableDefinition) {

		this(label, clazz, variableName);

		_itemTemplateVariableDefinition = itemTemplateVariableDefinition;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TemplateVariableDefinition)) {
			return false;
		}

		TemplateVariableDefinition templateVariableDefinition =
			(TemplateVariableDefinition)obj;

		if (Validator.equals(_name, templateVariableDefinition._name)) {
			return true;
		}

		return false;
	}

	public String[] generateCode(String language) throws Exception {
		if (_templateVariableCodeHandler == null) {
			return null;
		}

		return _templateVariableCodeHandler.generate(this, language);
	}

	public Class<?> getClazz() {
		return _clazz;
	}

	public String getDataType() {
		return _dataType;
	}

	public String getHelp() {
		return _help;
	}

	public TemplateVariableDefinition getItemTemplateVariableDefinition() {
		return _itemTemplateVariableDefinition;
	}

	public String getLabel() {
		return _label;
	}

	public String getName() {
		return _name;
	}

	public TemplateVariableCodeHandler getTemplateVariableCodeHandler() {
		return _templateVariableCodeHandler;
	}

	public boolean isCollection() {
		if (_itemTemplateVariableDefinition != null) {
			return true;
		}

		return false;
	}

	public boolean isRepeatable() {
		return _repeatable;
	}

	private Class<?> _clazz;
	private String _dataType;
	private String _help;
	private TemplateVariableDefinition _itemTemplateVariableDefinition;
	private String _label;
	private String _name;
	private boolean _repeatable;
	private TemplateVariableCodeHandler _templateVariableCodeHandler;

}