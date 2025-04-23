/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.LayoutStructureItemImporter;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Eudaldo Alonso
 */
public class LayoutStructureUtil {

	public static LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement)
		throws Exception {

		LayoutStructureItemImporter layoutStructureItemImporter =
			LayoutStructureItemImporterUtil.getLayoutStructureItemImporter(
				pageElement.getDefinition());

		LayoutStructureItem layoutStructureItem =
			layoutStructureItemImporter.addLayoutStructureItem(
				layoutStructure, layoutStructureItemImporterContext,
				pageElement);

		if (ArrayUtil.isEmpty(pageElement.getPageElements())) {
			return layoutStructureItem;
		}

		for (PageElement childPageElement : pageElement.getPageElements()) {
			addLayoutStructureItem(
				layoutStructure, layoutStructureItemImporterContext,
				childPageElement);
		}

		return layoutStructureItem;
	}

	public static String getParentExternalReferenceCode(
		PageElement pageElement, LayoutStructure layoutStructure) {

		String parentExternalReferenceCode =
			pageElement.getParentExternalReferenceCode();

		if (Validator.isNotNull(parentExternalReferenceCode)) {
			return parentExternalReferenceCode;
		}

		return layoutStructure.getMainItemId();
	}

}