/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class Field implements Serializable {

	public Field() {
	}

	public Field(long ddmStructureId, String name, List<Serializable> values) {
		_ddmStructureId = ddmStructureId;
		_name = name;
		_values = values;
	}

	public Field(long ddmStructureId, String name, Serializable value) {
		_ddmStructureId = ddmStructureId;
		_name = name;

		setValue(value);
	}

	public Field(String name, Serializable value) {
		this(0, name, value);
	}

	public void addValue(Serializable value) {
		_values.add(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Field)) {
			return false;
		}

		Field field = (Field)obj;

		if ((_ddmStructureId == field._ddmStructureId) &&
			Validator.equals(_name, field._name) &&
			Validator.equals(_values, field._values)) {

			return true;
		}

		return false;
	}

	public String getDataType() throws PortalException, SystemException {
		DDMStructure ddmStructure = getDDMStructure();

		return ddmStructure.getFieldDataType(_name);
	}

	public DDMStructure getDDMStructure() throws SystemException {
		return DDMStructureLocalServiceUtil.fetchStructure(_ddmStructureId);
	}

	public long getDDMStructureId() {
		return _ddmStructureId;
	}

	public String getName() {
		return _name;
	}

	public String getRenderedValue(Locale locale)
		throws PortalException, SystemException {

		FieldRenderer fieldRenderer = getFieldRenderer();

		return fieldRenderer.render(this, locale);
	}

	public String getRenderedValue(Locale locale, int valueIndex)
		throws PortalException, SystemException {

		FieldRenderer fieldRenderer = getFieldRenderer();

		return fieldRenderer.render(this, locale, valueIndex);
	}

	public String getType() throws PortalException, SystemException {
		DDMStructure ddmStructure = getDDMStructure();

		return ddmStructure.getFieldType(_name);
	}

	public Serializable getValue() {
		if (_values.isEmpty()) {
			return null;
		}

		try {
			DDMStructure ddmStructure = getDDMStructure();

			if (ddmStructure == null) {
				return _values.get(0);
			}

			boolean repeatable = isRepeatable();

			if (repeatable) {
				return FieldConstants.getSerializable(getType(), _values);
			}

			return _values.get(0);
		}
		catch (Exception e) {
			_log.error(e);
		}

		return null;
	}

	public Serializable getValue(int index) {
		return _values.get(index);
	}

	public List<Serializable> getValues() {
		return _values;
	}

	public boolean isRepeatable() throws PortalException, SystemException {
		DDMStructure ddmStructure = getDDMStructure();

		return ddmStructure.isFieldRepeatable(_name);
	}

	public void setDDMStructureId(long ddmStructureId) {
		_ddmStructureId = ddmStructureId;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setValue(Serializable value) {
		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			_values = ListUtil.fromArray((Serializable[])value);
		}
		else {
			_values.add(value);
		}
	}

	public void setValues(List<Serializable> values) {
		_values = values;
	}

	protected FieldRenderer getFieldRenderer()
		throws PortalException, SystemException {

		DDMStructure ddmStructure = getDDMStructure();

		String dataType = null;

		if (ddmStructure != null) {
			dataType = getDataType();
		}

		return FieldRendererFactory.getFieldRenderer(dataType);
	}

	private static Log _log = LogFactoryUtil.getLog(Field.class);

	private long _ddmStructureId;
	private String _name;
	private List<Serializable> _values = new ArrayList<Serializable>();

}