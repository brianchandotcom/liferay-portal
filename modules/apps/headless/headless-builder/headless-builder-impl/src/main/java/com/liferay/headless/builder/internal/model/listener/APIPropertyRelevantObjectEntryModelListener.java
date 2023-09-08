/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.model.listener;

import com.liferay.headless.builder.internal.helper.ObjectEntryHelper;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.listener.RelevantObjectEntryModelListener;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio Jiménez del Coso
 */
@Component(service = RelevantObjectEntryModelListener.class)
public class APIPropertyRelevantObjectEntryModelListener
	extends BaseModelListener<ObjectEntry>
	implements RelevantObjectEntryModelListener {

	@Override
	public String getObjectDefinitionExternalReferenceCode() {
		return "L_API_PROPERTY";
	}

	@Override
	public void onBeforeCreate(ObjectEntry objectEntry)
		throws ModelListenerException {

		Map<String, Serializable> values = objectEntry.getValues();

		_validate(
			(long)values.get("r_apiSchemaToAPIProperties_c_apiSchemaId"),
			values);
	}

	@Override
	public void onBeforeUpdate(
			ObjectEntry originalObjectEntry, ObjectEntry objectEntry)
		throws ModelListenerException {

		Map<String, Serializable> values = objectEntry.getValues();

		long apiSchemaId = MapUtil.getLong(
			values, "r_apiSchemaToAPIProperties_c_apiSchemaId");

		if (apiSchemaId != 0) {
			_validate(apiSchemaId, values);
		}
		else {
			_scheduleOrphanAPIPropertyDeletion(objectEntry.getObjectEntryId());
		}
	}

	private boolean _isValidAPIPropertyFields(
			long apiSchemaId, String objectFieldExternalReferenceCode,
			String objectRelationshipName)
		throws Exception {

		ObjectEntry apiSchemaObjectEntry =
			_objectEntryLocalService.getObjectEntry(apiSchemaId);

		Map<String, Serializable> apiSchemaValues =
			apiSchemaObjectEntry.getValues();

		String mainObjectDefinitionERC = (String)apiSchemaValues.get(
			"mainObjectDefinitionERC");

		if (mainObjectDefinitionERC == null) {
			return false;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					mainObjectDefinitionERC,
					apiSchemaObjectEntry.getCompanyId());

		if ((objectRelationshipName != null) &&
			!StringUtil.equals(objectRelationshipName, "")) {

			objectDefinition = _objectEntryHelper.getPropertyObjectDefinition(
				objectDefinition,
				ListUtil.fromArray(objectRelationshipName.split(",")));
		}

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectFieldExternalReferenceCode,
			objectDefinition.getObjectDefinitionId());

		if (objectField == null) {
			return false;
		}

		return true;
	}

	private void _scheduleOrphanAPIPropertyDeletion(long apiPropertyId) {
		_pendingAPIProperties.add(apiPropertyId);

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				if (_pendingAPIProperties.remove(apiPropertyId)) {
					ObjectEntry apiPropertyObjectEntry =
						_objectEntryLocalService.fetchObjectEntry(
							apiPropertyId);

					if (apiPropertyObjectEntry == null) {
						return null;
					}

					long apiSchemaId = MapUtil.getLong(
						apiPropertyObjectEntry.getValues(),
						"r_apiSchemaToAPIProperties_c_apiSchemaId");

					if (apiSchemaId == 0) {
						_objectEntryLocalService.deleteObjectEntry(
							apiPropertyId);
					}
				}

				return null;
			});
	}

	private void _validate(long apiSchemaId, Map<String, Serializable> values) {
		try {
			if (!_objectEntryHelper.isValidObjectEntry(
					apiSchemaId, "L_API_SCHEMA")) {

				throw new ObjectEntryValuesException.InvalidObjectField(
					null, "An API property must be related to an API schema",
					"an-api-property-must-be-related-to-an-api-schema");
			}

			if (!_isValidAPIPropertyFields(
					apiSchemaId, (String)values.get("objectFieldERC"),
					(String)values.get("objectRelationshipNames"))) {

				throw new ObjectEntryValuesException.InvalidObjectField(
					null,
					"An API property must be related to an existing object " +
						"field",
					"an-api-property-must-be-related-to-an-existing-object-" +
						"field");
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

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	private final Set<Long> _pendingAPIProperties = new CopyOnWriteArraySet<>();

}