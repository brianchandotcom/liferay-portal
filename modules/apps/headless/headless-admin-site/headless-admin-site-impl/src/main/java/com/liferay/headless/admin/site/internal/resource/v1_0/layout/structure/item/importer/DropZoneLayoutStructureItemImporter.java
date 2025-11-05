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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
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
			dropZonePageElementDefinition.getAddNewFragmentEntries());

		List<FragmentEntryReferenceUtil.FragmentEntryReference>
			fragmentEntryReferences = _getFragmentEntryReference(
				dropZonePageElementDefinition,
				layoutStructureItemImporterContext);

		if (fragmentEntryReferences != null) {
			dropZoneLayoutStructureItem.setFragmentEntriesJSONArray(
				_toFragmentEntriesJSONArray(fragmentEntryReferences));
			dropZoneLayoutStructureItem.setFragmentEntryKeys(
				_toFragmentEntryKeys(fragmentEntryReferences));
		}
		else {
			dropZoneLayoutStructureItem.setFragmentEntriesJSONArray(null);
			dropZoneLayoutStructureItem.setFragmentEntryKeys(null);
		}

		return dropZoneLayoutStructureItem;
	}

	private List<FragmentEntryReferenceUtil.FragmentEntryReference>
			_getFragmentEntryReference(
				DropZonePageElementDefinition dropZonePageElementDefinition,
				LayoutStructureItemImporterContext
					layoutStructureItemImporterContext)
		throws Exception {

		if (dropZonePageElementDefinition.getAllowedFragments() == null) {
			return null;
		}

		List<FragmentEntryReferenceUtil.FragmentEntryReference>
			fragmentEntryReferences = new ArrayList<>();

		for (FragmentReference fragmentReference :
				dropZonePageElementDefinition.getAllowedFragments()) {

			fragmentEntryReferences.add(
				FragmentEntryReferenceUtil.getFragmentEntryReference(
					layoutStructureItemImporterContext.getCompanyId(),
					fragmentReference,
					layoutStructureItemImporterContext.getGroupId()));
		}

		return fragmentEntryReferences;
	}

	private JSONArray _toFragmentEntriesJSONArray(
		List<FragmentEntryReferenceUtil.FragmentEntryReference>
			fragmentEntryReferences) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (FragmentEntryReferenceUtil.FragmentEntryReference
				fragmentEntryReference : fragmentEntryReferences) {

			if (Validator.isNotNull(fragmentEntryReference.getRendererKey())) {
				jsonArray.put(
					JSONUtil.put(
						"fragmentEntryRendererKey",
						fragmentEntryReference.getRendererKey()));
			}
			else if (Validator.isNotNull(
						fragmentEntryReference.getFragmentEntryKey())) {

				jsonArray.put(
					JSONUtil.put(
						"fragmentEntryERC",
						fragmentEntryReference.getFragmentEntryERC()
					).put(
						"fragmentEntryKey",
						fragmentEntryReference.getFragmentEntryKey()
					).put(
						"fragmentEntryScopeERC",
						fragmentEntryReference.getFragmentEntryScopeERC()
					));
			}
		}

		return jsonArray;
	}

	private List<String> _toFragmentEntryKeys(
		List<FragmentEntryReferenceUtil.FragmentEntryReference>
			fragmentEntryReferences) {

		List<String> fragmentKeys = new ArrayList<>();

		for (FragmentEntryReferenceUtil.FragmentEntryReference
				fragmentEntryReference : fragmentEntryReferences) {

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