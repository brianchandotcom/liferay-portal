/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.layout.page.template.internal.importer.helper;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.model.Layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Rubén Pulido
 */
public class DropZoneLayoutStructureItemHelper
	extends BaseLayoutStructureItemHelper implements LayoutStructureItemHelper {

	@Override
	public LayoutStructureItem addLayoutStructureItem(
			FragmentCollectionContributorTracker
				fragmentCollectionContributorTracker,
			FragmentEntryProcessorRegistry fragmentEntryProcessorRegistry,
			FragmentEntryValidator fragmentEntryValidator, Layout layout,
			LayoutStructure layoutStructure, PageElement pageElement,
			String parentItemId, int position)
		throws Exception {

		DropZoneLayoutStructureItem dropZoneLayoutStructureItem =
			(DropZoneLayoutStructureItem)
				layoutStructure.addDropZoneLayoutStructureItem(
					parentItemId, position);

		Map<String, Object> definitionMap = getDefinitionMap(
			pageElement.getDefinition());

		if (definitionMap == null) {
			return dropZoneLayoutStructureItem;
		}

		Object fragmentSettings = definitionMap.get("fragmentSettings");

		Map<String, Object> fragmentSettingsMap =
			(Map<String, Object>)fragmentSettings;

		if (fragmentSettingsMap == null) {
			return dropZoneLayoutStructureItem;
		}

		if ((!fragmentSettingsMap.containsKey(_KEY_ALLOWED_FRAGMENTS) &&
			 !fragmentSettingsMap.containsKey(_KEY_UNALLOWED_FRAGMENTS)) ||
			(fragmentSettingsMap.containsKey(_KEY_ALLOWED_FRAGMENTS) &&
			 fragmentSettingsMap.containsKey(_KEY_UNALLOWED_FRAGMENTS))) {

			return dropZoneLayoutStructureItem;
		}

		List<String> fragmentEntryKeys = new ArrayList<>();

		if (fragmentSettingsMap.containsKey(_KEY_ALLOWED_FRAGMENTS)) {
			dropZoneLayoutStructureItem.setAllowNewFragmentEntries(false);

			List<Map<String, String>> allowedFragments =
				(List<Map<String, String>>)fragmentSettingsMap.get(
					_KEY_ALLOWED_FRAGMENTS);

			for (Map<String, String> allowedFragmentMap : allowedFragments) {
				fragmentEntryKeys.add(allowedFragmentMap.get(_KEY_KEY));
			}
		}

		if (fragmentSettingsMap.containsKey(_KEY_UNALLOWED_FRAGMENTS)) {
			dropZoneLayoutStructureItem.setAllowNewFragmentEntries(true);

			List<Map<String, String>> allowedFragments =
				(List<Map<String, String>>)fragmentSettingsMap.get(
					_KEY_UNALLOWED_FRAGMENTS);

			for (Map<String, String> allowedFragmentMap : allowedFragments) {
				fragmentEntryKeys.add(allowedFragmentMap.get(_KEY_KEY));
			}
		}

		dropZoneLayoutStructureItem.setFragmentEntryKeys(fragmentEntryKeys);

		return dropZoneLayoutStructureItem;
	}

	private static final String _KEY_ALLOWED_FRAGMENTS = "allowedFragments";

	private static final String _KEY_KEY = "key";

	private static final String _KEY_UNALLOWED_FRAGMENTS = "unallowedFragments";

}