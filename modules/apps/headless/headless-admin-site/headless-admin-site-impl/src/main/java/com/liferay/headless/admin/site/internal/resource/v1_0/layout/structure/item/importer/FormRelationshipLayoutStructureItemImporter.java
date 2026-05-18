/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer;

import com.liferay.headless.admin.site.dto.v1_0.FormRelationshipConfig;
import com.liferay.headless.admin.site.dto.v1_0.FormRelationshipPageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.FragmentInlineValue;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.FragmentViewportUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.ImageValueUtil;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutStructureUtil;
import com.liferay.layout.util.structure.FormRelationshipStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Arrays;
import java.util.LinkedHashSet;

/**
 * @author Javier Moral
 */
public class FormRelationshipLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement)
		throws Exception {

		FormRelationshipStyledLayoutStructureItem
			formRelationshipStyledLayoutStructureItem =
				(FormRelationshipStyledLayoutStructureItem)
					layoutStructure.
						addFormRelationshipStyledLayoutStructureItem(
							pageElement.getExternalReferenceCode(),
							LayoutStructureUtil.getParentExternalReferenceCode(
								pageElement, layoutStructure),
							pageElement.getPosition());

		FormRelationshipPageElementDefinition
			formRelationshipPageElementDefinition =
				(FormRelationshipPageElementDefinition)
					pageElement.getPageElementDefinition();

		if (formRelationshipPageElementDefinition == null) {
			return formRelationshipStyledLayoutStructureItem;
		}

		JSONObject backgroundImageValueJSONObject =
			ImageValueUtil.toBackgroundImageValueJSONObject(
				formRelationshipPageElementDefinition.getBackgroundImageValue(),
				layoutStructureItemImporterContext);

		if (backgroundImageValueJSONObject != null) {
			formRelationshipStyledLayoutStructureItem.updateItemConfig(
				JSONUtil.put(
					"backgroundImage", backgroundImageValueJSONObject));
		}

		formRelationshipStyledLayoutStructureItem.setCssClasses(
			_getCssClasses(
				formRelationshipPageElementDefinition.getCssClasses()));

		FormRelationshipConfig formRelationshipConfig =
			formRelationshipPageElementDefinition.getFormRelationshipConfig();

		if (formRelationshipConfig != null) {
			formRelationshipStyledLayoutStructureItem.setButtonLabelJSONObject(
				_toFragmentInlineValueJSONObject(
					formRelationshipConfig.
						getButtonLabelFragmentInlineValue()));
			formRelationshipStyledLayoutStructureItem.setContentType(
				formRelationshipConfig.getContentType());
		}

		JSONObject fragmentViewportsJSONObject =
			FragmentViewportUtil.toFragmentViewportsJSONObject(
				formRelationshipPageElementDefinition.getFragmentViewports());

		if (fragmentViewportsJSONObject != null) {
			formRelationshipStyledLayoutStructureItem.updateItemConfig(
				fragmentViewportsJSONObject);
		}

		if (formRelationshipPageElementDefinition.getIndexed() != null) {
			formRelationshipStyledLayoutStructureItem.setIndexed(
				formRelationshipPageElementDefinition.getIndexed());
		}

		formRelationshipStyledLayoutStructureItem.setName(
			formRelationshipPageElementDefinition.getName());

		if (formRelationshipPageElementDefinition.getRepeatable() != null) {
			formRelationshipStyledLayoutStructureItem.setRepeatable(
				formRelationshipPageElementDefinition.getRepeatable());
		}

		return formRelationshipStyledLayoutStructureItem;
	}

	private LinkedHashSet<String> _getCssClasses(String[] cssClasses) {
		if (cssClasses == null) {
			return null;
		}

		return new LinkedHashSet<>(Arrays.asList(cssClasses));
	}

	private JSONObject _toFragmentInlineValueJSONObject(
		FragmentInlineValue fragmentInlineValue) {

		if (fragmentInlineValue == null) {
			return null;
		}

		return LocalizedValueUtil.toJSONObject(
			fragmentInlineValue.getValue_i18n());
	}

}