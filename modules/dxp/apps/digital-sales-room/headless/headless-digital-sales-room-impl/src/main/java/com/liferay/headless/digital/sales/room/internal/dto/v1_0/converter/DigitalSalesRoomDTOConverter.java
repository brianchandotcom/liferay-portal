/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.headless.digital.sales.room.dto.v1_0.DigitalSalesRoom;
import com.liferay.headless.digital.sales.room.dto.v1_0.FileEntry;
import com.liferay.headless.digital.sales.room.dto.v1_0.UserAccountBrief;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
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
		"dto.class.name=com.liferay.headless.digital.sales.room.dto.v1_0.DigitalSalesRoom",
		"version=v1.0"
	},
	service = DTOConverter.class
)
public class DigitalSalesRoomDTOConverter
	implements DTOConverter<Group, DigitalSalesRoom> {

	@Override
	public String getContentType() {
		return Group.class.getSimpleName();
	}

	@Override
	public DigitalSalesRoom toDTO(
			DTOConverterContext dtoConverterContext, Group group)
		throws Exception {

		ObjectEntry objectEntry;

		if (dtoConverterContext instanceof
				DigitalSalesRoomDTOConverterContext) {

			DigitalSalesRoomDTOConverterContext
				digitalSalesRoomDTOConverterContext =
					(DigitalSalesRoomDTOConverterContext)dtoConverterContext;

			objectEntry = digitalSalesRoomDTOConverterContext.getObjectEntry();
		}
		else {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.
					getObjectDefinitionByExternalReferenceCode(
						"L_DSR_ROOM", group.getCompanyId());

			objectEntry = _objectEntryLocalService.fetchObjectEntry(
				group.getExternalReferenceCode(), group.getGroupId(),
				objectDefinition.getObjectDefinitionId());
		}

		if (objectEntry == null) {
			return null;
		}

		Map<String, Serializable> values = objectEntry.getValues();

		return new DigitalSalesRoom() {
			{
				setAccountId(() -> GetterUtil.getLong(values.get("accountId")));
				setAccountName(
					() -> {
						long accountId = GetterUtil.getLong(
							values.get("accountId"));

						if (accountId == 0) {
							return null;
						}

						AccountEntry accountEntry =
							_accountEntryLocalService.fetchAccountEntry(
								accountId);

						if (accountEntry == null) {
							return null;
						}

						return accountEntry.getName();
					});
				setActions(dtoConverterContext::getActions);
				setBanner(() -> _getFileEntry("banner", values));
				setChannelId(() -> GetterUtil.getLong(values.get("channelId")));
				setChannelName(
					() -> GetterUtil.getString(values.get("channelName")));
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
				setOwnerId(objectEntry::getUserId);
				setOwnerName(objectEntry::getUserName);
				setPrimaryColor(
					() -> GetterUtil.getString(values.get("primaryColor")));
				setSecondaryColor(
					() -> GetterUtil.getString(values.get("secondaryColor")));
				setTemplateGroupId(
					() -> GetterUtil.getLong(values.get("templateGroupId")));
				setUserAccountBriefs(
					() -> TransformUtil.transformToArray(
						_userLocalService.getGroupUsers(group.getGroupId()),
						user -> _toUserAccountBrief(
							dtoConverterContext, group.getGroupId(), user),
						UserAccountBrief.class));
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

	private UserAccountBrief _toUserAccountBrief(
			DTOConverterContext dtoConverterContext, long groupId, User user)
		throws Exception {

		return _userAccountBriefDTOConverter.toDTO(
			new UserAccountBriefDTOConverterContext(
				dtoConverterContext.isAcceptAllLanguages(), null,
				dtoConverterContext.getDTOConverterRegistry(), groupId,
				user.getUserId(), dtoConverterContext.getLocale(),
				dtoConverterContext.getUriInfo(),
				dtoConverterContext.getUser()),
			user);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DigitalSalesRoomDTOConverter.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private File _file;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference(
		target = "(component.name=com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter.UserAccountBriefDTOConverter)"
	)
	private DTOConverter<User, UserAccountBrief> _userAccountBriefDTOConverter;

	@Reference
	private UserLocalService _userLocalService;

}