/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
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
public class DeletePiklistObjectStatesUpgradeProcess extends UpgradeProcess {

	public DeletePiklistObjectStatesUpgradeProcess(
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
				_modifyApplicationStatusPicklist(companyId);
				_modifyHTTPMethodPicklist(companyId);
				_modifyRetrieveTypePicklist(companyId);
				_modifyScopePicklist(companyId);
			});
	}

	private void _modifyApplicationStatusPicklist(Long companyId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_API_APPLICATION", companyId);

		if (objectDefinition == null) {
			return;
		}

		ObjectField applicationStatusObjectField =
			_objectFieldLocalService.fetchObjectField(
				"APPLICATION_STATUS", objectDefinition.getObjectDefinitionId());

		_objectStateFlowLocalService.deleteObjectFieldObjectStateFlow(
			applicationStatusObjectField.getObjectFieldId());

		ListTypeDefinition applicationStatusListTypeDefinition =
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"APPLICATION_STATUS_PICKLIST", companyId);

		applicationStatusListTypeDefinition.setName("Application Status");

		ListTypeEntry publishedListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				applicationStatusListTypeDefinition.getListTypeDefinitionId(),
				"published");

		publishedListTypeEntry.setExternalReferenceCode("PUBLISHED");

		ListTypeEntry unpublishedListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				applicationStatusListTypeDefinition.getListTypeDefinitionId(),
				"unpublished");

		unpublishedListTypeEntry.setExternalReferenceCode("UNPUBLISHED");
	}

	private void _modifyHTTPMethodPicklist(Long companyId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_API_ENDPOINT", companyId);

		if (objectDefinition == null) {
			return;
		}

		ObjectField applicationStatusObjectField =
			_objectFieldLocalService.fetchObjectField(
				"HTTP_METHOD",
				objectDefinition.getObjectDefinitionId());

		_objectStateFlowLocalService.deleteObjectFieldObjectStateFlow(
			applicationStatusObjectField.getObjectFieldId());

		ListTypeDefinition applicationStatusListTypeDefinition =
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"HTTP_METHOD_PICKLIST", companyId);

		applicationStatusListTypeDefinition.setName("HTTP Method");

		ListTypeEntry publishedListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				applicationStatusListTypeDefinition.getListTypeDefinitionId(),
				"get");

		publishedListTypeEntry.setExternalReferenceCode("GET");

		ListTypeEntry unpublishedListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				applicationStatusListTypeDefinition.getListTypeDefinitionId(),
				"post");

		unpublishedListTypeEntry.setExternalReferenceCode("POST");
	}

	private void _modifyRetrieveTypePicklist(Long companyId)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_API_ENDPOINT", companyId);

		if (objectDefinition == null) {
			return;
		}

		ObjectField applicationStatusObjectField =
			_objectFieldLocalService.fetchObjectField(
				"RETRIEVE_TYPE", objectDefinition.getObjectDefinitionId());

		_objectStateFlowLocalService.deleteObjectFieldObjectStateFlow(
			applicationStatusObjectField.getObjectFieldId());

		ListTypeDefinition applicationStatusListTypeDefinition =
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"RETRIEVE_TYPE_PICKLIST", companyId);

		applicationStatusListTypeDefinition.setName("Retrieve Type");

		ListTypeEntry publishedListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				applicationStatusListTypeDefinition.getListTypeDefinitionId(),
				"collection");

		publishedListTypeEntry.setExternalReferenceCode("COLLECTION");

		ListTypeEntry unpublishedListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				applicationStatusListTypeDefinition.getListTypeDefinitionId(),
				"singleElement");

		unpublishedListTypeEntry.setExternalReferenceCode("SINGLE_ELEMENT");
	}

	private void _modifyScopePicklist(Long companyId) throws PortalException {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_API_ENDPOINT", companyId);

		if (objectDefinition == null) {
			return;
		}

		ObjectField applicationStatusObjectField =
			_objectFieldLocalService.fetchObjectField(
				"SCOPE", objectDefinition.getObjectDefinitionId());

		_objectStateFlowLocalService.deleteObjectFieldObjectStateFlow(
			applicationStatusObjectField.getObjectFieldId());

		ListTypeDefinition applicationStatusListTypeDefinition =
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"SCOPE_PICKLIST", companyId);

		applicationStatusListTypeDefinition.setName("Scope");

		ListTypeEntry publishedListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				applicationStatusListTypeDefinition.getListTypeDefinitionId(),
				"company");

		publishedListTypeEntry.setExternalReferenceCode("COMPANY");

		ListTypeEntry unpublishedListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				applicationStatusListTypeDefinition.getListTypeDefinitionId(),
				"site");

		unpublishedListTypeEntry.setExternalReferenceCode("SITE");
	}

	private final CompanyLocalService _companyLocalService;
	private final ListTypeDefinitionLocalService
		_listTypeDefinitionLocalService;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectStateFlowLocalService _objectStateFlowLocalService;

}