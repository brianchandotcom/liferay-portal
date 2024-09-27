/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.helper;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alberto Javier Moreno Lage
 */
@Component(service = ValidationHelper.class)
public class ValidationHelper {

	public static boolean isSupported(ObjectDefinition objectDefinition) {
		if (!objectDefinition.isUnmodifiableSystemObject() ||
			(FeatureFlagManagerUtil.isEnabled("LPD-21414") &&
			 _allowedUnmodifiableSystemObjectDefinitionNames.contains(
				 objectDefinition.getName()))) {

			return true;
		}

		return false;
	}

	public boolean isValidObjectEntry(
			String externalReferenceCode, long objectEntryId)
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
		String objectDefinitionName, ObjectEntry objectEntry,
		String relationshipName) {

		try {
			Map<String, Serializable> values = objectEntry.getValues();

			long apiEndpointId = (long)values.get(
				"r_" + relationshipName + "_l_apiEndpointId");

			if (!isValidObjectEntry("L_API_ENDPOINT", apiEndpointId)) {
				throw new ObjectEntryValuesException.InvalidObjectField(
					Collections.singletonList(objectDefinitionName),
					String.format(
						"The %s must be related to an API endpoint",
						objectDefinitionName),
					"the-x-must-be-related-to-an-api-endpoint");
			}

			com.liferay.object.rest.dto.v1_0.ObjectEntry
				apiEndpointObjectEntry = _objectEntryHelper.getObjectEntry(
					objectEntry.getCompanyId(), Collections.emptyList(),
					apiEndpointId, "L_API_ENDPOINT");

			ListEntry listEntry =
				(ListEntry)apiEndpointObjectEntry.getPropertyValue(
					"retrieveType");

			APIApplication.Endpoint.RetrieveType retrieveType =
				APIApplication.Endpoint.RetrieveType.parse(listEntry.getKey());

			if (!Objects.equals(
					retrieveType,
					APIApplication.Endpoint.RetrieveType.COLLECTION)) {

				throw new ObjectEntryValuesException.InvalidObjectField(
					Collections.singletonList(objectDefinitionName),
					String.format(
						"The %s can only be associated to API endpoints with " +
							"a retrieve type of \"collection\"",
						objectDefinitionName),
					"the-x-can-only-be-associated-to-api-endpoints-with-a-" +
						"retrieve-type-of-collection");
			}

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectEntry.getObjectDefinitionId());

			if (Validator.isNotNull(
					_objectEntryHelper.getObjectEntry(
						objectEntry.getCompanyId(),
						StringBundler.concat(
							"id ne '", objectEntry.getObjectEntryId(),
							"' and r_", relationshipName,
							"_l_apiEndpointId eq '", apiEndpointId, "'"),
						objectDefinition.getExternalReferenceCode()))) {

				throw new ObjectEntryValuesException.InvalidObjectField(
					Collections.singletonList(objectDefinitionName),
					String.format(
						"The API endpoint already has an associated %s",
						objectDefinitionName),
					"the-api-endpoint-already-has-an-associated-x");
			}
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	private static final Set<String>
		_allowedUnmodifiableSystemObjectDefinitionNames = SetUtil.fromArray(
			"AccountEntry", "User");

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryHelper _objectEntryHelper;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}