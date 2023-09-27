/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.mapper;

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

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemMapper.class)
public class DropZoneLayoutStructureItemMapper
	implements LayoutStructureItemMapper {

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
							dropZoneLayoutStructureItem);
					}
				};
				type = Type.DROP_ZONE;
			}
		};
	}

	private Fragment[] _toFragments(List<String> fragmentEntryKeys) {
		List<Fragment> fragments = new ArrayList<>();

		for (String fragmentEntryKey : fragmentEntryKeys) {
			fragments.add(
				new Fragment() {
					{
						key = fragmentEntryKey;
					}
				});
		}

		return fragments.toArray(new Fragment[0]);
	}

	private Map<String, Fragment[]> _toFragmentSettingsMap(
		DropZoneLayoutStructureItem dropZoneLayoutStructureItem) {

		if (dropZoneLayoutStructureItem.isAllowNewFragmentEntries()) {
			return HashMapBuilder.put(
				"unallowedFragments",
				_toFragments(dropZoneLayoutStructureItem.getFragmentEntryKeys())
			).build();
		}

		return HashMapBuilder.put(
			"allowedFragments",
			_toFragments(dropZoneLayoutStructureItem.getFragmentEntryKeys())
		).build();
	}

}