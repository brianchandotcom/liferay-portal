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
public class ModifyAPIBuilderListTypeDefinitionsUpgradeProcess
	extends UpgradeProcess {

	public ModifyAPIBuilderListTypeDefinitionsUpgradeProcess(
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
				_modifyPicklist(
					companyId, "APPLICATION_STATUS_PICKLIST",
					"Application Status", "PUBLISHED", "published",
					"UNPUBLISHED", "unpublished", "L_API_APPLICATION",
					"APPLICATION_STATUS");
				_modifyPicklist(
					companyId, "HTTP_METHOD_PICKLIST", "HTTP Method", "GET",
					"get", "POST", "post", "L_API_ENDPOINT", "HTTP_METHOD");
				_modifyPicklist(
					companyId, "RETRIEVE_TYPE_PICKLIST", "Retrieve Type",
					"COLLECTION", "collection", "SINGLE_ELEMENT",
					"singleElement", "L_API_ENDPOINT", "RETRIEVE_TYPE");
				_modifyPicklist(
					companyId, "SCOPE_PICKLIST", "Scope", "COMPANY", "company",
					"SITE", "site", "L_API_ENDPOINT", "SCOPE");
			});
	}

	private void _modifyPicklist(
			Long companyId, String listTypeDefinitionExternalReferenceCode,
			String listTypeDefinitionName,
			String listTypeEntry1ExternalReferenceCode,
			String listTypeEntry1Key,
			String listTypeEntry2ExternalReferenceCode,
			String listTypeEntry2Key,
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

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectFieldExternalReferenceCode,
			objectDefinition.getObjectDefinitionId());

		_objectStateFlowLocalService.deleteObjectFieldObjectStateFlow(
			objectField.getObjectFieldId());

		objectField.setState(false);

		_objectFieldLocalService.updateObjectField(objectField);

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					listTypeDefinitionExternalReferenceCode, companyId);

		listTypeDefinition.setName(listTypeDefinitionName);

		listTypeDefinition =
			_listTypeDefinitionLocalService.updateListTypeDefinition(
				listTypeDefinition);

		ListTypeEntry listTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(),
				listTypeEntry1Key);

		listTypeEntry.setExternalReferenceCode(
			listTypeEntry1ExternalReferenceCode);

		_listTypeEntryLocalService.updateListTypeEntry(listTypeEntry);

		listTypeEntry = _listTypeEntryLocalService.getListTypeEntry(
			listTypeDefinition.getListTypeDefinitionId(), listTypeEntry2Key);

		listTypeEntry.setExternalReferenceCode(
			listTypeEntry2ExternalReferenceCode);

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