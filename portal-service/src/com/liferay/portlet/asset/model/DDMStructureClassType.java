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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.NoSuchClassTypeFieldException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class DDMStructureClassType implements ClassType {

	public DDMStructureClassType(
		long classTypeId, String classTypeName, String languageId) {

		_classTypeId = classTypeId;
		_classTypeName = classTypeName;
		_languageId = languageId;
	}

	@Override
	public ClassTypeField getClassTypeField(String fieldName)
		throws PortalException, SystemException {

		for (ClassTypeField classTypeField : getClassTypeFields()) {
			if (fieldName.equals(classTypeField.getName())) {
				return classTypeField;
			}
		}

		throw new NoSuchClassTypeFieldException();
	}

	@Override
	public List<ClassTypeField> getClassTypeFields()
		throws PortalException, SystemException {

		DDMStructure ddmStructure =
			DDMStructureLocalServiceUtil.getDDMStructure(getClassTypeId());

		List<ClassTypeField> classTypeFields = getClassTypeFields(ddmStructure);

		return classTypeFields;
	}

	@Override
	public List<ClassTypeField> getClassTypeFields(int start, int end)
		throws PortalException, SystemException {

		return ListUtil.subList(getClassTypeFields(), start, end);
	}

	@Override
	public int getClassTypeFieldsCount()
		throws PortalException, SystemException {

		return getClassTypeFields().size();
	}

	@Override
	public long getClassTypeId() {
		return _classTypeId;
	}

	@Override
	public String getName() {
		return _classTypeName;
	}

	protected List<ClassTypeField> getClassTypeFields(DDMStructure ddmStructure)
		throws PortalException, SystemException {

		List<ClassTypeField> fields = new ArrayList<ClassTypeField>();

		Map<String, Map<String, String>> fieldsMap = ddmStructure.getFieldsMap(
			_languageId);

		for (Map<String, String> fieldMap : fieldsMap.values()) {
			String indexType = fieldMap.get("indexType");
			boolean privateField = GetterUtil.getBoolean(
				fieldMap.get("private"));

			String type = fieldMap.get("type");

			if (Validator.isNull(indexType) || privateField ||
				!ArrayUtil.contains(_SELECTABLE_DDM_STRUCTURE_FIELDS, type)) {

				continue;
			}

			String label = fieldMap.get("label");
			String name = fieldMap.get("name");

			fields.add(
				new ClassTypeField(
					label, name, type, ddmStructure.getStructureId()));
		}

		return fields;
	}

	private static final String[] _SELECTABLE_DDM_STRUCTURE_FIELDS = {
		"checkbox", "ddm-date", "ddm-decimal", "ddm-integer", "ddm-number",
		"radio", "select", "text"
	};

	private final long _classTypeId;
	private final String _classTypeName;
	private final String _languageId;

}