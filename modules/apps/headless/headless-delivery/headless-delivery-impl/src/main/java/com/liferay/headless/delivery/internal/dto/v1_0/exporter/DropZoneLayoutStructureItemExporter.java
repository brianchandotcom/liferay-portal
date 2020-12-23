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

package com.liferay.headless.delivery.internal.dto.v1_0.exporter;

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.headless.delivery.dto.v1_0.Fragment;
import com.liferay.headless.delivery.dto.v1_0.PageDropZoneDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.util.structure.DropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemExporter.class)
public class DropZoneLayoutStructureItemExporter
	implements LayoutStructureItemExporter {

	@Override
	public String getClassName() {
		return DropZoneLayoutStructureItem.class.getName();
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		DropZoneLayoutStructureItem dropZoneLayoutStructureItem =
			(DropZoneLayoutStructureItem)layoutStructureItem;

		return new PageElement() {
			{
				definition = new PageDropZoneDefinition() {
					{
						fragmentSettings = _toFragmentSettingsMap(
							dropZoneLayoutStructureItem, groupId);
					}
				};
				type = Type.DROP_ZONE;
			}
		};
	}

	private boolean _isFragmentEntryKey(String fragmentEntryKey, long groupId) {
		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				groupId, fragmentEntryKey);

		if (fragmentEntry != null) {
			return true;
		}

		fragmentEntry = _fragmentCollectionContributorTracker.getFragmentEntry(
			fragmentEntryKey);

		if (fragmentEntry != null) {
			return true;
		}

		FragmentRenderer fragmentRenderer =
			_fragmentRendererTracker.getFragmentRenderer(fragmentEntryKey);

		if (fragmentRenderer != null) {
			return true;
		}

		return false;
	}

	private Fragment[] _toFragments(
		List<String> fragmentEntryKeys, long groupId) {

		List<Fragment> fragments = new ArrayList<>();

		for (String fragmentEntryKey : fragmentEntryKeys) {
			if (_isFragmentEntryKey(fragmentEntryKey, groupId)) {
				fragments.add(
					new Fragment() {
						{
							key = fragmentEntryKey;
						}
					});
			}
		}

		return fragments.toArray(new Fragment[0]);
	}

	private Map<String, Fragment[]> _toFragmentSettingsMap(
		DropZoneLayoutStructureItem dropZoneLayoutStructureItem, long groupId) {

		if (dropZoneLayoutStructureItem.isAllowNewFragmentEntries()) {
			return HashMapBuilder.put(
				"unallowedFragments",
				_toFragments(
					dropZoneLayoutStructureItem.getFragmentEntryKeys(), groupId)
			).build();
		}

		return HashMapBuilder.put(
			"allowedFragments",
			_toFragments(
				dropZoneLayoutStructureItem.getFragmentEntryKeys(), groupId)
		).build();
	}

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

}