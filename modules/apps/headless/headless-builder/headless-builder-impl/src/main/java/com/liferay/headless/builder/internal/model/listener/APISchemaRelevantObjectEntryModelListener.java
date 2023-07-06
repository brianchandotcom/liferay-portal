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

package com.liferay.headless.builder.internal.model.listener;

import com.liferay.headless.builder.internal.helper.ObjectEntryHelper;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.listener.RelevantObjectEntryModelListener;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio Jiménez del Coso
 */
@Component(service = RelevantObjectEntryModelListener.class)
public class APISchemaRelevantObjectEntryModelListener
	extends BaseModelListener<ObjectEntry>
	implements RelevantObjectEntryModelListener {

	@Override
	public String getObjectDefinitionExternalReferenceCode() {
		return "L_API_SCHEMA";
	}

	@Override
	public void onBeforeCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		_validate(objectEntry);
	}

	@Override
	public void onBeforeUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		_validate(objectEntry);
	}

	private boolean _isValidAPIApplication(long apiApplicationId)
		throws Exception {

		if (apiApplicationId == 0) {
			return false;
		}

		ObjectEntry apiApplicationObjectEntry =
			_objectEntryLocalService.fetchObjectEntry(apiApplicationId);

		if (apiApplicationObjectEntry == null) {
			return false;
		}

		ObjectDefinition apiApplicationObjectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				apiApplicationObjectEntry.getObjectDefinitionId());

		if (!StringUtil.equals(
				apiApplicationObjectDefinition.getExternalReferenceCode(),
				"L_API_APPLICATION")) {

			return false;
		}

		return true;
	}

	private void _validate(ObjectEntry objectEntry) {
		Map<String, Serializable> values = objectEntry.getValues();

		try {
			if (!_isValidAPIApplication(
					(long)values.get(
						"r_apiApplicationToAPISchemas_c_apiApplicationId"))) {

				throw new ObjectEntryValuesException.InvalidObjectField(
					"An API schema must be related to a valid API application",
					"an-api-schema-must-be-related-to-a-valid-api-application",
					null);
			}

			if (Validator.isNotNull(
					_objectEntryHelper.getObjectEntry(
						objectEntry.getCompanyId(),
						StringBundler.concat(
							"id ne '", objectEntry.getObjectEntryId(),
							"' and name eq '", values.get("name"),
							"' and r_apiApplicationToAPISchemas_c_",
							"apiApplicationId eq '",
							values.get(
								"r_apiApplicationToAPISchemas_c_" +
									"apiApplicationId"),
							"'"),
						"L_API_SCHEMA"))) {

				throw new ObjectEntryValuesException.InvalidObjectField(
					"There is an API schema with the same name in the API " +
						"application",
					"there-is-an-api-schema-with-the-same-name-in-the-api-" +
						"application",
					null);
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryHelper _objectEntryHelper;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}