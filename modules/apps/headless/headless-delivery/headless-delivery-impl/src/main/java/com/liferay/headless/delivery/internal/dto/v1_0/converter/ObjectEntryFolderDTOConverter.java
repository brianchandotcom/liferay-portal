/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.headless.delivery.dto.v1_0.ObjectEntryFolder;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.GroupUtil;
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

		Group group = _groupLocalService.getGroup(
			objectEntryFolder.getGroupId());

		return new ObjectEntryFolder() {
			{
				setActions(dtoConverterContext::getActions);
				setAssetLibraryKey(() -> GroupUtil.getAssetLibraryKey(group));
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
					() ->
						_objectEntryLocalService.
							getObjectEntryFolderObjectEntriesCount(
								objectEntryFolder.getGroupId(),
								objectEntryFolder.getObjectEntryFolderId()));
				setNumberOfObjectEntryFolders(
					() ->
						_objectEntryFolderLocalService.
							getObjectEntryFoldersCount(
								objectEntryFolder.getGroupId(),
								objectEntryFolder.getCompanyId(),
								objectEntryFolder.getObjectEntryFolderId()));
				setParentObjectEntryFolderId(
					() -> {
						if (objectEntryFolder.getParentObjectEntryFolderId() >
								0L) {

							return objectEntryFolder.
								getParentObjectEntryFolderId();
						}

						return null;
					});
			}
		};
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}