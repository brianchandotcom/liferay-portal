/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.model.listener;

import com.liferay.headless.builder.internal.helper.ObjectEntryHelper;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.listener.RelevantObjectEntryModelListener;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
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

	private void _validate(ObjectEntry objectEntry) {
		try {
			Map<String, Serializable> values = objectEntry.getValues();

			if (!_objectEntryHelper.isValidObjectEntry(
					(long)values.get(
						"r_apiApplicationToAPISchemas_c_apiApplicationId"),
					"L_API_APPLICATION")) {

				throw new ObjectEntryValuesException.InvalidObjectField(
					null, "An API schema must be related to an API application",
					"an-api-schema-must-be-related-to-an-api-application");
			}

			String mainObjectDefinitionERC = (String)values.get(
				"mainObjectDefinitionERC");

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.
					fetchObjectDefinitionByExternalReferenceCode(
						mainObjectDefinitionERC, objectEntry.getCompanyId());

			if (objectDefinition == null) {
				throw new ObjectEntryValuesException.InvalidObjectField(
					null, "An API schema must be an existing object definition",
					"an-api-schema-must-be-an-existing-object-definition");
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
					null,
					"There is an API schema with the same name in the API " +
						"application",
					"there-is-an-api-schema-with-the-same-name-in-the-api-" +
						"application");
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

}