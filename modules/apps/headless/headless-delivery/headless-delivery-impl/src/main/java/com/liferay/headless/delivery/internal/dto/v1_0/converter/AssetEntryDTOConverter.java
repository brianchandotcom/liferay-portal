/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.headless.delivery.dto.v1_0.AssetEntry;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedFieldsSupplier;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Ortiz
 */
@Component(
	property = "dto.class.name=com.liferay.asset.kernel.model.AssetEntry",
	service = DTOConverter.class
)
public class AssetEntryDTOConverter
	implements DTOConverter
		<com.liferay.asset.kernel.model.AssetEntry, AssetEntry> {

	@Override
	public String getContentType() {
		return AssetEntry.class.getSimpleName();
	}

	@Override
	public AssetEntry toDTO(
		DTOConverterContext dtoConverterContext,
		com.liferay.asset.kernel.model.AssetEntry serviceBuilderAssetEntry) {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				serviceBuilderAssetEntry.getClassName());
		Group group = _groupLocalService.fetchGroup(
			serviceBuilderAssetEntry.getGroupId());
		Locale locale = dtoConverterContext.getLocale();

		return new AssetEntry() {
			{
				setAssetEntryId(serviceBuilderAssetEntry::getEntryId);
				setAssetType(
					() -> {
						if (assetRendererFactory == null) {
							return null;
						}

						if (!assetRendererFactory.isSupportsClassTypes()) {
							return assetRendererFactory.getTypeName(
								locale,
								serviceBuilderAssetEntry.getClassTypeId());
						}

						ClassTypeReader classTypeReader =
							assetRendererFactory.getClassTypeReader();

						ClassType classType = classTypeReader.getClassType(
							serviceBuilderAssetEntry.getClassTypeId(), locale);

						return classType.getName();
					});
				setClassName(serviceBuilderAssetEntry::getClassName);
				setClassNameId(serviceBuilderAssetEntry::getClassNameId);
				setClassPK(serviceBuilderAssetEntry::getClassPK);
				setCreator(
					() -> CreatorUtil.toCreator(
						dtoConverterContext, _portal,
						_userLocalService.fetchUser(
							serviceBuilderAssetEntry.getUserId())));
				setDateModified(serviceBuilderAssetEntry::getModifiedDate);
				setDescription(
					() -> serviceBuilderAssetEntry.getDescription(locale));
				setGroupDescriptiveName(
					() -> {
						if (group == null) {
							return null;
						}

						return group.getDescriptiveName(locale);
					});
				setStatus(
					() -> {
						AssetRenderer<?> assetRenderer =
							serviceBuilderAssetEntry.getAssetRenderer();

						if (assetRenderer == null) {
							return null;
						}

						return assetRenderer.getStatus();
					});
				setTitle(() -> serviceBuilderAssetEntry.getTitle(locale));
				setViewableByGuest(
					() -> NestedFieldsSupplier.supply(
						"viewableByGuest",
						nestedField -> _hasGuestViewPermission(
							serviceBuilderAssetEntry)));
			}
		};
	}

	private boolean _hasGuestViewPermission(
			com.liferay.asset.kernel.model.AssetEntry assetEntry)
		throws Exception {

		Role role = _roleLocalService.getRole(
			assetEntry.getCompanyId(), RoleConstants.GUEST);

		return _resourcePermissionLocalService.hasResourcePermission(
			assetEntry.getCompanyId(), assetEntry.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(assetEntry.getClassPK()), role.getRoleId(),
			ActionKeys.VIEW);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}