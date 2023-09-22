/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio Jiménez del Coso
 */
@Component(service = RelevantObjectEntryModelListener.class)
public class APISortRelevantObjectEntryModelListener
	extends BaseModelListener<ObjectEntry>
	implements RelevantObjectEntryModelListener {

	@Override
	public String getObjectDefinitionExternalReferenceCode() {
		return "L_API_SORT";
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

			long apiEndpointId = (long)values.get(
				"r_apiEndpointToAPISorts_c_apiEndpointId");

			if (!_objectEntryHelper.isValidObjectEntry(
					apiEndpointId, "L_API_ENDPOINT")) {

				throw new ObjectEntryValuesException.InvalidObjectField(
					null, "An API sort must be related to an API endpoint",
					"an-api-sort-must-be-related-to-an-api-endpoint");
			}

			if (Validator.isNotNull(
					_objectEntryHelper.getObjectEntry(
						objectEntry.getCompanyId(),
						StringBundler.concat(
							"id ne '", objectEntry.getObjectEntryId(),
							"' and r_apiEndpointToAPISorts_c_apiEndpointId eq ",
							"'", apiEndpointId, "'"),
						getObjectDefinitionExternalReferenceCode()))) {

				ObjectDefinition objectDefinition =
					_objectDefinitionLocalService.
						getObjectDefinitionByExternalReferenceCode(
							getObjectDefinitionExternalReferenceCode(),
							objectEntry.getCompanyId());

				User user = _userLocalService.getUser(objectEntry.getUserId());

				String label = objectDefinition.getLabel(user.getLocale());

				throw new ObjectEntryValuesException.InvalidObjectField(
					Collections.singletonList(label),
					String.format(
						"The API Endpoint already has an associated %s", label),
					"the-api-endpoint-already-has-an-associated-x");
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
	private UserLocalService _userLocalService;

}