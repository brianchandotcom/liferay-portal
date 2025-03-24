/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.internal.dto.v1_0.converter;

import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.object.dto.v1_0.ObjectEntryFolder;
import com.liferay.headless.object.dto.v1_0.ParentObjectEntryFolderBrief;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedFieldsSupplier;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = "dto.class.name=com.liferay.object.model.ObjectEntryFolder",
	service = DTOConverter.class
)
public class ObjectEntryFolderDTOConverter
	implements DTOConverter
		<com.liferay.object.model.ObjectEntryFolder, ObjectEntryFolder> {

	@Override
	public String getContentType() {
		return ObjectEntryFolder.class.getSimpleName();
	}

	@Override
	public ObjectEntryFolder toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		com.liferay.object.model.ObjectEntryFolder objectEntryFolder =
			_objectEntryFolderLocalService.getObjectEntryFolder(
				(Long)dtoConverterContext.getId());

		com.liferay.object.model.ObjectEntryFolder parentObjectEntryFolder =
			_getParentObjectEntryFolder(objectEntryFolder);

		return new ObjectEntryFolder() {
			{
				setActions(dtoConverterContext::getActions);
				setCreator(
					() -> CreatorUtil.toCreator(
						dtoConverterContext, _portal,
						_userLocalService.fetchUser(
							objectEntryFolder.getUserId())));
				setDateCreated(objectEntryFolder::getCreateDate);
				setDateModified(objectEntryFolder::getModifiedDate);
				setExternalReferenceCode(
					objectEntryFolder::getExternalReferenceCode);
				setId(objectEntryFolder::getObjectEntryFolderId);
				setLabel(
					() -> objectEntryFolder.getLabel(
						dtoConverterContext.getLocale()));
				setLabel_i18n(
					() -> LocalizedMapUtil.getLanguageIdMap(
						objectEntryFolder.getLabelMap()));
				setName(objectEntryFolder::getName);
				setNumberOfObjectEntries(
					() -> NestedFieldsSupplier.supply(
						"numberOfObjectEntries",
						nestedField ->
							_objectEntryLocalService.
								getObjectEntryFolderObjectEntriesCount(
									objectEntryFolder.getGroupId(),
									objectEntryFolder.
										getObjectEntryFolderId())));
				setNumberOfObjectEntryFolders(
					() -> NestedFieldsSupplier.supply(
						"numberOfObjectEntryFolders",
						nestedField ->
							_objectEntryFolderLocalService.
								getObjectEntryFoldersCount(
									objectEntryFolder.getGroupId(),
									objectEntryFolder.getCompanyId(),
									objectEntryFolder.
										getObjectEntryFolderId())));
				setParentObjectEntryFolderBrief(
					() -> NestedFieldsSupplier.supply(
						"parentObjectEntryFolderBrief",
						nestedField -> _getParentObjectEntryFolderBrief(
							dtoConverterContext, parentObjectEntryFolder)));
				setParentObjectEntryFolderExternalReferenceCode(
					() -> {
						if (parentObjectEntryFolder != null) {
							return parentObjectEntryFolder.
								getExternalReferenceCode();
						}

						return null;
					});
				setParentObjectEntryFolderId(
					() -> {
						if (parentObjectEntryFolder != null) {
							return parentObjectEntryFolder.
								getObjectEntryFolderId();
						}

						return null;
					});

				setScopeKey(
					() -> String.valueOf(objectEntryFolder.getGroupId()));
			}
		};
	}

	private com.liferay.object.model.ObjectEntryFolder
			_getParentObjectEntryFolder(
				com.liferay.object.model.ObjectEntryFolder objectEntryFolder)
		throws Exception {

		if (objectEntryFolder.getParentObjectEntryFolderId() > 0L) {
			return _objectEntryFolderLocalService.getObjectEntryFolder(
				objectEntryFolder.getParentObjectEntryFolderId());
		}

		return null;
	}

	private ParentObjectEntryFolderBrief _getParentObjectEntryFolderBrief(
		DTOConverterContext dtoConverterContext,
		com.liferay.object.model.ObjectEntryFolder parentObjectEntryFolder) {

		if (parentObjectEntryFolder == null) {
			return null;
		}

		return new ParentObjectEntryFolderBrief() {
			{
				setExternalReferenceCode(
					parentObjectEntryFolder::getExternalReferenceCode);
				setId(parentObjectEntryFolder::getObjectEntryFolderId);
				setLabel(
					() -> parentObjectEntryFolder.getLabel(
						dtoConverterContext.getLocale()));
				setLabel_i18n(
					() -> LocalizedMapUtil.getLanguageIdMap(
						parentObjectEntryFolder.getLabelMap()));
				setName(parentObjectEntryFolder::getName);
			}
		};
	}

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}