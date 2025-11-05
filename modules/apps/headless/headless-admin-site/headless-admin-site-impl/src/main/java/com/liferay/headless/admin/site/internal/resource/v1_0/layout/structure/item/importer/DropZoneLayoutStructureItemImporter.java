/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer;

import com.liferay.headless.admin.site.dto.v1_0.DropZonePageElementDefinition;
import com.liferay.headless.admin.site.dto.v1_0.FragmentReference;
import com.liferay.headless.admin.site.dto.v1_0.PageElement;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.FragmentEntryReferenceUtil;
import com.liferay.headless.admin.site.internal.resource.v1_0.layout.structure.item.importer.context.LayoutStructureItemImporterContext;
import com.liferay.headless.admin.site.internal.resource.v1_0.util.LayoutStructureUtil;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class DropZoneLayoutStructureItemImporter
	implements LayoutStructureItemImporter {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			LayoutStructure layoutStructure,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext,
			PageElement pageElement)
		throws Exception {

		PageElement[] pageElements = pageElement.getPageElements();

		if ((pageElements != null) && (pageElements.length > 1)) {
			throw new UnsupportedOperationException();
		}

		DropZoneLayoutStructureItem dropZoneLayoutStructureItem =
			(DropZoneLayoutStructureItem)layoutStructure.getLayoutStructureItem(
				pageElement.getExternalReferenceCode());

		if (dropZoneLayoutStructureItem == null) {
			dropZoneLayoutStructureItem =
				(DropZoneLayoutStructureItem)
					layoutStructure.addDropZoneLayoutStructureItem(
						pageElement.getExternalReferenceCode(),
						LayoutStructureUtil.getParentExternalReferenceCode(
							pageElement, layoutStructure),
						pageElement.getPosition());
		}

		DropZonePageElementDefinition dropZonePageElementDefinition =
			(DropZonePageElementDefinition)
				pageElement.getPageElementDefinition();

		if (dropZonePageElementDefinition == null) {
			return dropZoneLayoutStructureItem;
		}

		dropZoneLayoutStructureItem.setAllowNewFragmentEntries(
			dropZoneLayoutStructureItem.isAllowNewFragmentEntries());
		dropZoneLayoutStructureItem.setFragmentEntryKeys(
			_getFragmentEntryKeys(
				dropZonePageElementDefinition,
				layoutStructureItemImporterContext));

		return dropZoneLayoutStructureItem;
	}

	private List<String> _getFragmentEntryKeys(
			DropZonePageElementDefinition dropZonePageElementDefinition,
			LayoutStructureItemImporterContext
				layoutStructureItemImporterContext)
		throws Exception {

		if (ArrayUtil.isEmpty(
				dropZonePageElementDefinition.getAllowedFragments())) {

			return null;
		}

		List<String> fragmentKeys = new ArrayList<>();

		for (FragmentReference fragmentReference :
				dropZonePageElementDefinition.getAllowedFragments()) {

			FragmentEntryReferenceUtil.FragmentEntryReference
				fragmentEntryReference =
					FragmentEntryReferenceUtil.getFragmentEntryReference(
						layoutStructureItemImporterContext.getCompanyId(),
						fragmentReference,
						layoutStructureItemImporterContext.getGroupId());

			if (Validator.isNotNull(fragmentEntryReference.getRendererKey())) {
				fragmentKeys.add(fragmentEntryReference.getRendererKey());
			}
			else if (Validator.isNotNull(
						fragmentEntryReference.getFragmentEntryKey())) {

				fragmentKeys.add(fragmentEntryReference.getFragmentEntryKey());
			}
		}

		return fragmentKeys;
	}

}