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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.importer;

import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentCollectionLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
import com.liferay.fragment.validator.FragmentEntryValidator;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
			FragmentEntryValidator fragmentEntryValidator,
			FragmentRendererTracker fragmentRendererTracker, Layout layout,
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

		Set<String> fragmentEntryKeys = new HashSet<>();

		Set<String> fragmentCollectionKeys = new HashSet<>();

		List<Map<String, String>> allowedFragments = new ArrayList<>();

		if (fragmentSettingsMap.containsKey(_KEY_ALLOWED_FRAGMENTS)) {
			dropZoneLayoutStructureItem.setAllowNewFragmentEntries(false);

			allowedFragments.addAll(
				(List<Map<String, String>>)fragmentSettingsMap.get(
					_KEY_ALLOWED_FRAGMENTS));
		}

		if (fragmentSettingsMap.containsKey(_KEY_UNALLOWED_FRAGMENTS)) {
			dropZoneLayoutStructureItem.setAllowNewFragmentEntries(true);

			allowedFragments.addAll(
				(List<Map<String, String>>)fragmentSettingsMap.get(
					_KEY_UNALLOWED_FRAGMENTS));
		}

		for (Map<String, String> allowedFragmentMap : allowedFragments) {
			fragmentEntryKeys.add(allowedFragmentMap.get(_KEY_KEY));

			String fragmentCollectionKey = _getFragmentCollectionKey(
				fragmentCollectionContributorTracker,
				allowedFragmentMap.get(_KEY_KEY), fragmentRendererTracker,
				layout.getGroupId());

			if (Validator.isNotNull(fragmentCollectionKey)) {
				fragmentCollectionKeys.add(fragmentCollectionKey);
			}
		}

		for (String fragmentCollectionKey : fragmentCollectionKeys) {
			fragmentEntryKeys.add(fragmentCollectionKey);
		}

		dropZoneLayoutStructureItem.setFragmentEntryKeys(
			new ArrayList<>(fragmentEntryKeys));

		return dropZoneLayoutStructureItem;
	}

	private String _getFragmentCollectionKey(
			FragmentCollectionContributorTracker
				fragmentCollectionContributorTracker,
			String fragmentKey, FragmentRendererTracker fragmentRendererTracker,
			long groupId)
		throws PortalException {

		FragmentEntry fragmentEntry =
			FragmentEntryLocalServiceUtil.fetchFragmentEntry(
				groupId, fragmentKey);

		if (fragmentEntry != null) {
			FragmentCollection fragmentCollection =
				FragmentCollectionLocalServiceUtil.getFragmentCollection(
					fragmentEntry.getFragmentCollectionId());

			return fragmentCollection.getFragmentCollectionKey();
		}

		List<FragmentCollectionContributor> fragmentCollectionContributors =
			fragmentCollectionContributorTracker.
				getFragmentCollectionContributors();

		for (FragmentCollectionContributor fragmentCollectionContributor :
				fragmentCollectionContributors) {

			String fragmentCollectionKey =
				fragmentCollectionContributor.getFragmentCollectionKey();

			if (fragmentKey.startsWith(
					fragmentCollectionKey + StringPool.DASH)) {

				return fragmentCollectionKey;
			}
		}

		FragmentRenderer fragmentRenderer =
			fragmentRendererTracker.getFragmentRenderer(fragmentKey);

		if (fragmentRenderer != null) {
			return fragmentRenderer.getCollectionKey();
		}

		return null;
	}

	private static final String _KEY_ALLOWED_FRAGMENTS = "allowedFragments";

	private static final String _KEY_KEY = "key";

	private static final String _KEY_UNALLOWED_FRAGMENTS = "unallowedFragments";

}