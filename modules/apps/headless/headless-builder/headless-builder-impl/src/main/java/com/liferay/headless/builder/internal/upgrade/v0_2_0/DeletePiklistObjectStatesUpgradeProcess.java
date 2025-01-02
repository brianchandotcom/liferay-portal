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

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			"APPLICATION_STATUS", objectDefinition.getObjectDefinitionId());

		_objectStateFlowLocalService.deleteObjectFieldObjectStateFlow(
			objectField.getObjectFieldId());

		objectField.setState(false);

		_objectFieldLocalService.updateObjectField(objectField);

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"APPLICATION_STATUS_PICKLIST", companyId);

		listTypeDefinition.setName("Application Status");

		listTypeDefinition =
			_listTypeDefinitionLocalService.updateListTypeDefinition(
				listTypeDefinition);

		ListTypeEntry publishedListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), "published");

		publishedListTypeEntry.setExternalReferenceCode("PUBLISHED");

		_listTypeEntryLocalService.updateListTypeEntry(publishedListTypeEntry);

		ListTypeEntry unpublishedListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), "unpublished");

		unpublishedListTypeEntry.setExternalReferenceCode("UNPUBLISHED");

		_listTypeEntryLocalService.updateListTypeEntry(
			unpublishedListTypeEntry);
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

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			"HTTP_METHOD", objectDefinition.getObjectDefinitionId());

		_objectStateFlowLocalService.deleteObjectFieldObjectStateFlow(
			objectField.getObjectFieldId());

		objectField.setState(false);

		_objectFieldLocalService.updateObjectField(objectField);

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"HTTP_METHOD_PICKLIST", companyId);

		listTypeDefinition.setName("HTTP Method");

		listTypeDefinition =
			_listTypeDefinitionLocalService.updateListTypeDefinition(
				listTypeDefinition);

		ListTypeEntry getListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), "get");

		getListTypeEntry.setExternalReferenceCode("GET");

		_listTypeEntryLocalService.updateListTypeEntry(getListTypeEntry);

		ListTypeEntry postListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), "post");

		postListTypeEntry.setExternalReferenceCode("POST");

		_listTypeEntryLocalService.updateListTypeEntry(postListTypeEntry);
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

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			"RETRIEVE_TYPE", objectDefinition.getObjectDefinitionId());

		_objectStateFlowLocalService.deleteObjectFieldObjectStateFlow(
			objectField.getObjectFieldId());

		objectField.setState(false);

		_objectFieldLocalService.updateObjectField(objectField);

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"RETRIEVE_TYPE_PICKLIST", companyId);

		listTypeDefinition.setName("Retrieve Type");

		listTypeDefinition =
			_listTypeDefinitionLocalService.updateListTypeDefinition(
				listTypeDefinition);

		ListTypeEntry collectionListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), "collection");

		collectionListTypeEntry.setExternalReferenceCode("COLLECTION");

		_listTypeEntryLocalService.updateListTypeEntry(collectionListTypeEntry);

		ListTypeEntry singleElementListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), "singleElement");

		singleElementListTypeEntry.setExternalReferenceCode("SINGLE_ELEMENT");

		_listTypeEntryLocalService.updateListTypeEntry(
			singleElementListTypeEntry);
	}

	private void _modifyScopePicklist(Long companyId) throws PortalException {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				fetchObjectDefinitionByExternalReferenceCode(
					"L_API_ENDPOINT", companyId);

		if (objectDefinition == null) {
			return;
		}

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			"SCOPE", objectDefinition.getObjectDefinitionId());

		_objectStateFlowLocalService.deleteObjectFieldObjectStateFlow(
			objectField.getObjectFieldId());

		objectField.setState(false);

		_objectFieldLocalService.updateObjectField(objectField);

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.
				fetchListTypeDefinitionByExternalReferenceCode(
					"SCOPE_PICKLIST", companyId);

		listTypeDefinition.setName("Scope");

		listTypeDefinition =
			_listTypeDefinitionLocalService.updateListTypeDefinition(
				listTypeDefinition);

		ListTypeEntry companyListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), "company");

		companyListTypeEntry.setExternalReferenceCode("COMPANY");

		_listTypeEntryLocalService.updateListTypeEntry(companyListTypeEntry);

		ListTypeEntry siteListTypeEntry =
			_listTypeEntryLocalService.getListTypeEntry(
				listTypeDefinition.getListTypeDefinitionId(), "site");

		siteListTypeEntry.setExternalReferenceCode("SITE");

		_listTypeEntryLocalService.updateListTypeEntry(siteListTypeEntry);
	}

	private final CompanyLocalService _companyLocalService;
	private final ListTypeDefinitionLocalService
		_listTypeDefinitionLocalService;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectStateFlowLocalService _objectStateFlowLocalService;

}