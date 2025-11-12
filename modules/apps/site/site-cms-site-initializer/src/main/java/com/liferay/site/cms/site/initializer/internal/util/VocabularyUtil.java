/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.util;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFolderLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Kiana Suetani
 */
public class VocabularyUtil {

	public static List<Map<String, String>> getAssetTypesSelectOptions(
		ThemeDisplay themeDisplay)
		throws PortalException {

		List<Map<String, String>> selectOptions = new ArrayList<>();

		_buildSelectOptions(
			ObjectFolderLocalServiceUtil.getObjectFolderByExternalReferenceCode(
				"L_CMS_CONTENT_STRUCTURES", themeDisplay.getCompanyId()),
			selectOptions);
		_buildSelectOptions(
			ObjectFolderLocalServiceUtil.getObjectFolderByExternalReferenceCode(
				"L_CMS_FILE_TYPES", themeDisplay.getCompanyId()),
			selectOptions);

		return selectOptions;
	}

	private static void _buildSelectOptions(
		ObjectFolder objectFolder, List<Map<String, String>> selectOptions) {

		for (ObjectDefinition objectDefinition :
			ObjectDefinitionLocalServiceUtil.
				getObjectFolderObjectDefinitions(
					objectFolder.getObjectFolderId())) {

			String className = objectDefinition.getClassName();

			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(className);

			selectOptions.add(
				HashMapBuilder.put(
					"icon", assetRendererFactory.getIconCssClass()
				).put(
					"restricted", Boolean.FALSE.toString()
				).put(
					"type", objectDefinition.getLabelCurrentValue()
				).put(
					"typeId",
					String.valueOf(PortalUtil.getClassNameId(className))
				).build());
		}
	}

}