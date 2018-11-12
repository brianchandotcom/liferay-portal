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

package com.liferay.portal.kernel.template;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringPool;

import java.util.Objects;

/**
 * @author Jorge Ferrer
 */
public class TemplateVariableDefinition {

	public TemplateVariableDefinition(
		String label, Class<?> clazz, String name, String accessor) {

		this(
			label, clazz, StringPool.BLANK, name, accessor,
			label.concat("-help"), false, null);
	}

	public TemplateVariableDefinition(
		String label, Class<?> clazz, String dataType, String name,
		String accessor, String help, boolean repeatable,
		TemplateVariableCodeHandler templateVariableCodeHandler) {

		this(
			label, clazz, dataType, name, accessor, help, repeatable,
			templateVariableCodeHandler, null);
	}

	public TemplateVariableDefinition(
		String label, Class<?> clazz, String name,
		TemplateVariableDefinition itemTemplateVariableDefinition) {

		this(
			label, clazz, StringPool.BLANK, name, StringPool.BLANK,
			label.concat("-help"), false, null, itemTemplateVariableDefinition);
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

		if (Objects.equals(_name, templateVariableDefinition._name) &&
			Objects.equals(_accessor, templateVariableDefinition._accessor)) {

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

	public String getAccessor() {
		return _accessor;
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

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _name);

		return HashUtil.hash(hash, _accessor);
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

	protected TemplateVariableDefinition(
		String label, Class<?> clazz, String dataType, String name,
		String accessor, String help, boolean repeatable,
		TemplateVariableCodeHandler templateVariableCodeHandler,
		TemplateVariableDefinition itemTemplateVariableDefinition) {

		_label = label;
		_clazz = clazz;
		_dataType = dataType;
		_name = name;
		_accessor = accessor;
		_help = help;
		_repeatable = repeatable;
		_templateVariableCodeHandler = templateVariableCodeHandler;
		_itemTemplateVariableDefinition = itemTemplateVariableDefinition;
	}

	private final String _accessor;
	private final Class<?> _clazz;
	private final String _dataType;
	private final String _help;
	private final TemplateVariableDefinition _itemTemplateVariableDefinition;
	private final String _label;
	private final String _name;
	private final boolean _repeatable;
	private TemplateVariableCodeHandler _templateVariableCodeHandler;

}