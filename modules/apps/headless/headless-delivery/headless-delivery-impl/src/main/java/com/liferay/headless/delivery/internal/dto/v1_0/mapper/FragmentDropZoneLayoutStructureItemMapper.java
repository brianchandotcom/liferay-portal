/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.mapper;

import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.headless.delivery.dto.v1_0.PageFragmentDropZoneDefinition;
import com.liferay.layout.util.structure.FragmentDropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;

/**
 * @author Eudaldo Alonso
 */
public class FragmentDropZoneLayoutStructureItemMapper
	implements LayoutStructureItemMapper {

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		return new PageElement() {
			{
				setDefinition(
					() -> new PageFragmentDropZoneDefinition() {
						{
							setFragmentDropZoneId(
								() -> {
									FragmentDropZoneLayoutStructureItem
										fragmentDropZoneLayoutStructureItem =
											(FragmentDropZoneLayoutStructureItem)
												layoutStructureItem;

									return fragmentDropZoneLayoutStructureItem.
										getFragmentDropZoneId();
								});
						}
					});
				setId(layoutStructureItem::getItemId);
				setType(() -> Type.FRAGMENT_DROP_ZONE);
			}
		};
	}

}