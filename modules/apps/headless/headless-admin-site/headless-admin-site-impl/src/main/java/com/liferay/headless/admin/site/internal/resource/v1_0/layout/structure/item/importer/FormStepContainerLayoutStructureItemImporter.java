/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer;

import com.liferay.headless.admin.site.dto.v1_0.FormStepContainerPageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.FragmentViewportUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutStructureUtil;
import com.liferay.layout.util.structure.FormStepContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * @author Eudaldo Alonso
 */
public class FormStepContainerLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement)
		throws Exception {

		FormStepContainerStyledLayoutStructureItem
			formStepContainerStyledLayoutStructureItem =
				(FormStepContainerStyledLayoutStructureItem)
					layoutStructure.
						addFormStepContainerStyledLayoutStructureItem(
							pageElement.getExternalReferenceCode(),
							LayoutStructureUtil.getParentExternalReferenceCode(
								pageElement, layoutStructure),
							pageElement.getPosition());

		FormStepContainerPageElementDefinition
			formStepContainerPageElementDefinition =
				(FormStepContainerPageElementDefinition)
					pageElement.getPageElementDefinition();

		if (formStepContainerPageElementDefinition == null) {
			return formStepContainerStyledLayoutStructureItem;
		}

		formStepContainerStyledLayoutStructureItem.setCssClasses(
			_getCssClasses(
				formStepContainerPageElementDefinition.getCssClasses()));
		formStepContainerStyledLayoutStructureItem.setCustomCSS(
			formStepContainerPageElementDefinition.getCustomCSS());

		JSONObject fragmentViewportsJSONObject =
			FragmentViewportUtil.toFragmentViewportsJSONObject(
				formStepContainerPageElementDefinition.getFragmentViewports());

		if (fragmentViewportsJSONObject != null) {
			formStepContainerStyledLayoutStructureItem.updateItemConfig(
				fragmentViewportsJSONObject);
		}

		return formStepContainerStyledLayoutStructureItem;
	}

	private LinkedHashSet<String> _getCssClasses(String[] cssClasses) {
		if (cssClasses == null) {
			return null;
		}

		return new LinkedHashSet<>(Arrays.asList(cssClasses));
	}

}