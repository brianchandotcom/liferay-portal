/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.helper;

import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alberto Javier Moreno Lage
 */
@Component(service = ValidationHelper.class)
public class ValidationHelper {

	public boolean isValidObjectEntry(
			long objectEntryId, String externalReferenceCode)
		throws Exception {

		if (objectEntryId == 0) {
			return false;
		}

		ObjectEntry objectEntry = _objectEntryLocalService.fetchObjectEntry(
			objectEntryId);

		if (objectEntry == null) {
			return false;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectEntry.getObjectDefinitionId());

		if (!Objects.equals(
				objectDefinition.getExternalReferenceCode(),
				externalReferenceCode)) {

			return false;
		}

		return true;
	}

	public void validateAPIEndpointRelationship(
		ObjectEntry objectEntry, String relationshipName) {

		try {
			Map<String, Serializable> values = objectEntry.getValues();

			long apiEndpointId = (long)values.get(
				"r_" + relationshipName + "_c_apiEndpointId");

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntry.getObjectDefinitionId());

			User user = _userLocalService.getUser(objectEntry.getUserId());

			String label = objectDefinition.getLabel(user.getLocale());

			if (!isValidObjectEntry(apiEndpointId, "L_API_ENDPOINT")) {
				ObjectDefinition apiEndpointObjectDefinition =
					_objectDefinitionLocalService.
						getObjectDefinitionByExternalReferenceCode(
							"L_API_ENDPOINT", objectEntry.getCompanyId());

				String apiEndpointObjectDefinitionLabel =
					apiEndpointObjectDefinition.getLabel(user.getLocale());

				throw new ObjectEntryValuesException.InvalidObjectField(
					Arrays.asList(label, apiEndpointObjectDefinitionLabel),
					String.format(
						"An %s must be related to an %s", label,
						apiEndpointObjectDefinitionLabel),
					"an-x-must-be-related-to-an-x");
			}

			if (Validator.isNotNull(
					_objectEntryHelper.getObjectEntry(
						objectEntry.getCompanyId(),
						StringBundler.concat(
							"id ne '", objectEntry.getObjectEntryId(),
							"' and r_", relationshipName,
							"_c_apiEndpointId eq '", apiEndpointId, "'"),
						objectDefinition.getExternalReferenceCode()))) {

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
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}