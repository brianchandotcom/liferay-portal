/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.converter;

import com.liferay.headless.admin.user.dto.v1_0.Role;
import com.liferay.headless.admin.user.dto.v1_0.RolePermission;
import com.liferay.headless.admin.user.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = {
		"application.name=Liferay.Headless.Admin.User",
		"dto.class.name=com.liferay.portal.kernel.model.Role", "version=v1.0"
	},
	service = DTOConverter.class
)
public class RoleDTOConverter
	implements DTOConverter<com.liferay.portal.kernel.model.Role, Role> {

	@Override
	public String getContentType() {
		return Role.class.getSimpleName();
	}

	@Override
	public Role toDTO(
			DTOConverterContext dtoConverterContext,
			com.liferay.portal.kernel.model.Role role)
		throws Exception {

		return new Role() {
			{
				actions = dtoConverterContext.getActions();
				availableLanguages = LocaleUtil.toW3cLanguageIds(
					role.getAvailableLanguageIds());
				creator = CreatorUtil.toCreator(
					_portal, _userLocalService.fetchUser(role.getUserId()));
				dateCreated = role.getCreateDate();
				dateModified = role.getModifiedDate();
				description = role.getDescription(
					dtoConverterContext.getLocale());
				description_i18n = LocalizedMapUtil.getI18nMap(
					role.getDescriptionMap());
				externalReferenceCode = role.getName();
				id = role.getRoleId();
				name = role.getTitle(dtoConverterContext.getLocale());
				name_i18n = LocalizedMapUtil.getI18nMap(role.getTitleMap());

				rolePermissions = TransformUtil.transformToArray(
					_resourcePermissionLocalService.getRoleResourcePermissions(
						id),
					resourcePermission -> new RolePermission() {
						{
							id = resourcePermission.getRoleId();
							primaryKey = resourcePermission.getPrimKey();
							resourceName = resourcePermission.getName();
							roleId = resourcePermission.getRoleId();
							scope = (long)resourcePermission.getScope();

							setActionIds(
								() -> {
									List<ResourceAction> resourceActions =
										_resourceActionLocalService.
											getResourceActions(
												resourcePermission.getName());

									Set<String> actionIdsSet = new HashSet<>();

									long actionIds =
										resourcePermission.getActionIds();

									for (ResourceAction resourceAction :
											resourceActions) {

										long bitwiseValue =
											resourceAction.getBitwiseValue();

										if ((actionIds & bitwiseValue) ==
												bitwiseValue) {

											actionIdsSet.add(
												resourceAction.getActionId());
										}
									}

									return actionIdsSet.toArray(new String[0]);
								});

							setLabel(
								() -> {
									if (Validator.isBlank(resourceName)) {
										return null;
									}

									if (resourceName.contains("model")) {
										return _language.get(
											dtoConverterContext.getLocale(),
											"model.resource." + resourceName);
									}

									if (resourceName.contains("portlet")) {
										return _language.get(
											dtoConverterContext.getLocale(),
											"javax.portlet.title." +
												resourceName);
									}

									return resourceName;
								});
						}
					},
					RolePermission.class);

				roleType = role.getTypeLabel();
			}
		};
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}