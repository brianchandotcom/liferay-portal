/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.object.internal.dto.v1_0.converter;

import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.object.dto.v1_0.Collaborator;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.security.permission.SharingEntryAction;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(
	property = "dto.class.name=com.liferay.headless.object.dto.v1_0.Collaborator",
	service = DTOConverter.class
)
public class CollaboratorDTOConverter
	implements DTOConverter<SharingEntry, Collaborator> {

	@Override
	public String getContentType() {
		return Collaborator.class.getSimpleName();
	}

	@Override
	public Collaborator toDTO(
			DTOConverterContext dtoConverterContext, SharingEntry sharingEntry)
		throws Exception {

		User user = _getUser(sharingEntry);
		UserGroup userGroup = _getUserGroup(sharingEntry);

		return new Collaborator() {
			{
				setActionIds(
					() -> TransformUtil.transformToArray(
						SharingEntryAction.getSharingEntryActions(
							sharingEntry.getActionIds()),
						SharingEntryAction::getActionId, String.class));
				setCreator(
					() -> CreatorUtil.toCreator(
						dtoConverterContext, _portal,
						_userLocalService.getUser(sharingEntry.getUserId())));
				setDateExpired(sharingEntry::getExpirationDate);
				setExternalReferenceCode(
					() -> {
						if (user != null) {
							return user.getExternalReferenceCode();
						}

						return userGroup.getExternalReferenceCode();
					});
				setId(
					() -> {
						if (user != null) {
							return user.getUserId();
						}

						return userGroup.getUserGroupId();
					});
				setName(
					() -> {
						if (user != null) {
							return user.getFullName();
						}

						return userGroup.getName();
					});
				setPortrait(
					() -> {
						if (user != null) {
							if (user.getPortraitId() == 0) {
								return null;
							}

							ThemeDisplay themeDisplay = new ThemeDisplay() {
								{
									setPathImage(_portal.getPathImage());
								}
							};

							return user.getPortraitURL(themeDisplay);
						}

						return null;
					});
				setShare(sharingEntry::isShareable);
				setType(
					() -> {
						if (user != null) {
							return "User";
						}

						return "UserGroup";
					});
			}
		};
	}

	private User _getUser(SharingEntry sharingEntry) throws Exception {
		if (sharingEntry.getToUserId() > 0) {
			return _userLocalService.getUser(sharingEntry.getToUserId());
		}

		return null;
	}

	private UserGroup _getUserGroup(SharingEntry sharingEntry)
		throws Exception {

		if (sharingEntry.getToUserGroupId() > 0) {
			return _userGroupLocalService.getUserGroup(
				sharingEntry.getToUserGroupId());
		}

		return null;
	}

	@Reference
	private Portal _portal;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}