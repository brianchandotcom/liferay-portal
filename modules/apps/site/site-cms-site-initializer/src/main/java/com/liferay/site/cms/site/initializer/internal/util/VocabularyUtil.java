/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Kiana Suetani
 */
public class VocabularyUtil {

	public static void buildObjectDefinitionSelectOptions(
		ObjectFolder objectFolder, List<Map<String, String>> selectOptions) {

		for (ObjectDefinition objectDefinition :
				ObjectDefinitionLocalServiceUtil.
					getObjectFolderObjectDefinitions(
						objectFolder.getObjectFolderId())) {

			selectOptions.add(
				HashMapBuilder.put(
					"label", objectDefinition.getLabelCurrentValue()
				).put(
					"restricted", Boolean.FALSE.toString()
				).put(
					"value",
					String.valueOf(
						PortalUtil.getClassNameId(
							objectDefinition.getClassName()))
				).build());
		}
	}

}