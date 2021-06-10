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

package com.liferay.object.dynamic.data.mapping.storage.internal.model.listener;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(immediate = true, service = ModelListener.class)
public class DDMStructureModelListener extends BaseModelListener<DDMStructure> {

	@Override
	public void onAfterCreate(DDMStructure ddmStructure)
		throws ModelListenerException {

		if (!StringUtil.equals(ddmStructure.getStorageType(), "object")) {
			return;
		}

		DDMForm ddmForm = ddmStructure.getDDMForm();
		List<ObjectField> objectFields = new ArrayList<>();

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			objectFields.add(
				_createObjectField(
					StringUtil.toLowerCase(ddmFormField.getName()),
					StringUtil.upperCaseFirstLetter(
						ddmFormField.getDataType())));
		}

		objectFields.add(_createObjectField("ddmStorageId", "Long"));

		try {
			_objectDefinitionLocalService.addObjectDefinition(
				ddmStructure.getUserId(),
				"Structure" + ddmStructure.getStructureId(), objectFields);
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private ObjectField _createObjectField(String name, String type) {
		ObjectField objectField = _objectFieldLocalService.createObjectField(0);

		objectField.setIndexed(true);
		objectField.setIndexedAsKeyword(false);
		objectField.setName(name);
		objectField.setType(type);

		return objectField;
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}