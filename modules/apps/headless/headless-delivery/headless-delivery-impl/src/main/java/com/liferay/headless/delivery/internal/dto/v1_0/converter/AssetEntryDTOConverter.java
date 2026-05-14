/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.headless.delivery.dto.v1_0.AssetEntry;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

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
				setDescription(
					() -> serviceBuilderAssetEntry.getDescription(locale));
				setGroupDescriptiveName(() -> group.getDescriptiveName(locale));
				setTitle(() -> serviceBuilderAssetEntry.getTitle(locale));
			}
		};
	}

	@Reference
	private GroupLocalService _groupLocalService;

}