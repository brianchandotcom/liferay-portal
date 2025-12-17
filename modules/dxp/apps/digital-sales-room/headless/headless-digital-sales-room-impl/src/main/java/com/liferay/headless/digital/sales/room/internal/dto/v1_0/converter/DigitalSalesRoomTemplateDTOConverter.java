/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.headless.digital.sales.room.dto.v1_0.DigitalSalesRoomTemplate;
import com.liferay.headless.digital.sales.room.dto.v1_0.FileEntry;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedFieldsSupplier;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = {
		"application.name=Liferay.Headless.Digital.Sales.Room",
		"dto.class.name=com.liferay.headless.digital.sales.room.dto.v1_0.DigitalSalesRoomTemplate",
		"version=v1.0"
	},
	service = DTOConverter.class
)
public class DigitalSalesRoomTemplateDTOConverter
	implements DTOConverter<Group, DigitalSalesRoomTemplate> {

	@Override
	public String getContentType() {
		return Group.class.getSimpleName();
	}

	@Override
	public DigitalSalesRoomTemplate toDTO(
			DTOConverterContext dtoConverterContext, Group group)
		throws Exception {

		ObjectEntry objectEntry;

		if (dtoConverterContext instanceof
				DigitalSalesRoomTemplateDTOConverterContext) {

			DigitalSalesRoomTemplateDTOConverterContext
				digitalSalesRoomTemplateDTOConverterContext =
					(DigitalSalesRoomTemplateDTOConverterContext)
						dtoConverterContext;

			objectEntry =
				digitalSalesRoomTemplateDTOConverterContext.getObjectEntry();
		}
		else {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.
					getObjectDefinitionByExternalReferenceCode(
						"L_DSR_TEMPLATE", group.getCompanyId());

			objectEntry = _objectEntryLocalService.fetchObjectEntry(
				group.getExternalReferenceCode(), group.getGroupId(),
				objectDefinition.getObjectDefinitionId());
		}

		if (objectEntry == null) {
			return null;
		}

		Map<String, Serializable> values = objectEntry.getValues();

		return new DigitalSalesRoomTemplate() {
			{
				setActions(dtoConverterContext::getActions);
				setBanner(() -> _getFileEntry("banner", values));
				setClientLogo(() -> _getFileEntry("clientLogo", values));
				setClientName(
					() -> GetterUtil.getString(values.get("clientName")));
				setCreateDate(objectEntry::getCreateDate);
				setDescription(
					() -> group.getDescription(
						dtoConverterContext.getLocale()));
				setExternalReferenceCode(group::getExternalReferenceCode);
				setFriendlyUrlPath(group::getFriendlyURL);
				setId(group::getGroupId);
				setModifiedDate(group::getModifiedDate);
				setName(() -> group.getName(dtoConverterContext.getLocale()));
				setPrimaryColor(
					() -> GetterUtil.getString(values.get("primaryColor")));
				setSecondaryColor(
					() -> GetterUtil.getString(values.get("secondaryColor")));
			}
		};
	}

	private FileEntry _getFileEntry(
		String name, Map<String, Serializable> values) {

		long fileEntryId = GetterUtil.getLong(values.get(name));

		if (fileEntryId == 0) {
			return null;
		}

		try {
			com.liferay.portal.kernel.repository.model.FileEntry
				serviceBuilderFileEntry = _dlAppLocalService.fetchFileEntry(
					fileEntryId);

			if (serviceBuilderFileEntry == null) {
				return null;
			}

			return new FileEntry() {
				{
					setFileBase64(
						() -> (String)NestedFieldsSupplier.supply(
							"fileBase64",
							fieldName -> Base64.encode(
								_file.getBytes(
									serviceBuilderFileEntry.
										getContentStream()))));
					setFileName(serviceBuilderFileEntry::getFileName);
					setFileURL(
						() -> DLURLHelperUtil.getDownloadURL(
							serviceBuilderFileEntry,
							serviceBuilderFileEntry.getLatestFileVersion(),
							null, StringPool.BLANK, true, true));
					setId(serviceBuilderFileEntry::getFileEntryId);
				}
			};
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DigitalSalesRoomTemplateDTOConverter.class);

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private File _file;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}