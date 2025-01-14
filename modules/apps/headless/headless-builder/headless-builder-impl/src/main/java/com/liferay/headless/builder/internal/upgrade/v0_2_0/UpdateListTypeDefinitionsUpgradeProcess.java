/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.upgrade.v0_2_0;

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectStateFlowLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Alberto Javier Moreno Lage
 */
public class UpdateListTypeDefinitionsUpgradeProcess extends UpgradeProcess {

	public UpdateListTypeDefinitionsUpgradeProcess(
		CompanyLocalService companyLocalService,
		ListTypeDefinitionLocalService listTypeDefinitionLocalService,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectStateFlowLocalService objectStateFlowLocalService) {

		_companyLocalService = companyLocalService;
		_listTypeDefinitionLocalService = listTypeDefinitionLocalService;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectStateFlowLocalService = objectStateFlowLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				_updateListTypeDefinition(
					companyId, "APPLICATION_STATUS_PICKLIST",
					"Application Status", "PUBLISHED", "UNPUBLISHED",
					"published", "unpublished", "L_API_APPLICATION_STATUSES",
					"L_API_APPLICATION", "APPLICATION_STATUS");
				_updateListTypeDefinition(
					companyId, "HTTP_METHOD_PICKLIST", "HTTP Method", "GET",
					"POST", "get", "post", "L_API_ENDPOINT_HTTP_METHODS",
					"L_API_ENDPOINT", "HTTP_METHOD");
				_updateListTypeDefinition(
					companyId, "RETRIEVE_TYPE_PICKLIST", "Retrieve Type",
					"COLLECTION", "SINGLE_ELEMENT", "collection",
					"singleElement", "L_API_ENDPOINT_RETRIEVE_TYPES",
					"L_API_ENDPOINT", "RETRIEVE_TYPE");
				_updateListTypeDefinition(
					companyId, "SCOPE_PICKLIST", "Scope", "COMPANY", "SITE",
					"company", "site", "L_API_ENDPOINT_SCOPES",
					"L_API_ENDPOINT", "SCOPE");
			});
	}

	private void _updateListTypeDefinition(
			long companyId, String listTypeDefinitionExternalReferenceCode,
			String listTypeDefinitionName,
			String listTypeEntryExternalReferenceCode1,
			String listTypeEntryExternalReferenceCode2,
			String listTypeEntryKey1, String listTypeEntryKey2,
			String newListTypeDefinitionExternalReferenceCode,
			String objectDefinitionExternalReferenceCode,
			String objectFieldExternalReferenceCode)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					objectDefinitionExternalReferenceCode, companyId);

		if (objectDefinition == null) {
			return;
		}

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectFieldExternalReferenceCode,
			objectDefinition.getObjectDefinitionId());

		_objectStateFlowLocalService.deleteObjectFieldObjectStateFlow(
			objectField.getObjectFieldId());

		objectField.setState(false);

		_objectFieldLocalService.updateObjectField(objectField);

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.
				getListTypeDefinitionByExternalReferenceCode(
					listTypeDefinitionExternalReferenceCode, companyId);

		listTypeDefinition.setName(listTypeDefinitionName);

		listTypeDefinition.setExternalReferenceCode(
			newListTypeDefinitionExternalReferenceCode);

		listTypeDefinition =
			_listTypeDefinitionLocalService.updateListTypeDefinition(
				listTypeDefinition);

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(),
				listTypeEntryKey1);

		listTypeEntry.setExternalReferenceCode(
			listTypeEntryExternalReferenceCode1);

		_listTypeEntryLocalService.updateListTypeEntry(listTypeEntry);

		listTypeEntry = _listTypeEntryLocalService.getListTypeEntry(
			listTypeDefinition.getListTypeDefinitionId(), listTypeEntryKey2);

		listTypeEntry.setExternalReferenceCode(
			listTypeEntryExternalReferenceCode2);

		_listTypeEntryLocalService.updateListTypeEntry(listTypeEntry);
	}

	private final CompanyLocalService _companyLocalService;
	private final ListTypeDefinitionLocalService
		_listTypeDefinitionLocalService;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectStateFlowLocalService _objectStateFlowLocalService;

}