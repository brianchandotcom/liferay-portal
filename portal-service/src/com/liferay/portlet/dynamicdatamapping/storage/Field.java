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
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.io.Serializable;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class Field implements Serializable {

	public Field() {
	}

	public Field(long ddmStructureId, String name, Serializable value) {
		_ddmStructureId = ddmStructureId;
		_name = name;
		_value = value;
	}

	public Field(String name, Serializable value) {
		this(0, name, value);
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

		DDMStructure ddmStructure = getDDMStructure();

		String dataType = null;

		if (ddmStructure != null) {
			dataType = getDataType();
		}

		FieldRenderer fieldrenderer = FieldRendererFactory.getFieldRenderer(
			dataType);

		return fieldrenderer.render(this, locale);
	}

	public String getType() throws PortalException, SystemException {
		DDMStructure ddmStructure = getDDMStructure();

		return ddmStructure.getFieldType(_name);
	}

	public Serializable getValue() {
		return _value;
	}

	public void setDDMStructureId(long ddmStructureId) {
		_ddmStructureId = ddmStructureId;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setValue(Serializable value) {
		_value = value;
	}

	private long _ddmStructureId;
	private String _name;
	private Serializable _value;

}