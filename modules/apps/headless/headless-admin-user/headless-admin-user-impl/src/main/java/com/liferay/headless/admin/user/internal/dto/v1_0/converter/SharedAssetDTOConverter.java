/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.converter;

import com.liferay.headless.admin.user.dto.v1_0.SharedAsset;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.interpreter.SharingEntryInterpreterProvider;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(
	property = "dto.class.name=com.liferay.sharing.model.SharingEntry",
	service = DTOConverter.class
)
public class SharedAssetDTOConverter
	implements DTOConverter<SharingEntry, SharedAsset> {

	@Override
	public String getContentType() {
		return SharingEntry.class.getSimpleName();
	}

	@Override
	public SharedAsset toDTO(
		DTOConverterContext dtoConverterContext, SharingEntry sharingEntry) {

		SharingEntryInterpreter sharingEntryInterpreter =
			_sharingEntryInterpreterProvider.getSharingEntryInterpreter(
				sharingEntry);

		return new SharedAsset() {
			{
				setActionIds(
					() -> TransformUtil.transformToArray(
						SharingEntryAction.getSharingEntryActions(
							sharingEntry.getActionIds()),
						SharingEntryAction::getActionId, String.class));
				setAssetType(
					() -> {
						if (sharingEntryInterpreter == null) {
							return null;
						}

						return sharingEntryInterpreter.getAssetTypeTitle(
							sharingEntry, dtoConverterContext.getLocale());
					});
				setClassName(sharingEntry::getClassName);
				setClassPK(sharingEntry::getClassPK);
				setCreator(
					() -> CreatorUtil.toCreator(
						_portal,
						_userLocalService.getUser(sharingEntry.getUserId())));
				setDateCreated(sharingEntry::getCreateDate);
				setDateModified(sharingEntry::getModifiedDate);
				setExternalReferenceCode(
					sharingEntry::getExternalReferenceCode);
				setId(sharingEntry::getSharingEntryId);
				setShareable(sharingEntry::isShareable);
				setSiteName(
					() -> {
						Group group = _groupLocalService.getGroup(
							sharingEntry.getGroupId());

						return group.getName(dtoConverterContext.getLocale());
					});
				setTitle(
					() -> {
						if (sharingEntryInterpreter == null) {
							return null;
						}

						return sharingEntryInterpreter.getTitle(
							sharingEntry, dtoConverterContext.getLocale());
					});
			}
		};
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SharingEntryInterpreterProvider _sharingEntryInterpreterProvider;

	@Reference
	private UserLocalService _userLocalService;

}