/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.drop.zone;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.layout.util.structure.FragmentDropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;

/**
 * @author Lourdes Fernández Besada
 */
public class FragmentEntryProcessorDropZoneTestUtil {

	public static FragmentDropZoneLayoutStructureItem
		addFragmentDropZoneLayoutStructureItem(
			FragmentEntryLink fragmentEntryLink,
			LayoutStructure layoutStructure, String fragmentDropZoneId) {

		FragmentDropZoneLayoutStructureItem[]
			fragmentDropZoneLayoutStructureItems =
				addFragmentDropZoneLayoutStructureItems(
					fragmentEntryLink, layoutStructure, fragmentDropZoneId);

		return fragmentDropZoneLayoutStructureItems[0];
	}

	public static FragmentDropZoneLayoutStructureItem[]
		addFragmentDropZoneLayoutStructureItems(
			FragmentEntryLink fragmentEntryLink,
			LayoutStructure layoutStructure, String... fragmentDropZoneIds) {

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		LayoutStructureItem containerStyledLayoutStructureItem =
			layoutStructure.addContainerStyledLayoutStructureItem(
				rootLayoutStructureItem.getItemId(), 0);

		layoutStructure.addLayoutStructureItem(
			containerStyledLayoutStructureItem);

		LayoutStructureItem fragmentStyledLayoutStructureItem =
			layoutStructure.addFragmentStyledLayoutStructureItem(
				fragmentEntryLink.getFragmentEntryLinkId(),
				containerStyledLayoutStructureItem.getItemId(), 0);

		layoutStructure.addLayoutStructureItem(
			fragmentStyledLayoutStructureItem);

		List<FragmentDropZoneLayoutStructureItem>
			fragmentDropZoneLayoutStructureItems = new ArrayList<>();

		for (int i = 0; i < fragmentDropZoneIds.length; i++) {
			String fragmentDropZoneId = fragmentDropZoneIds[i];

			FragmentDropZoneLayoutStructureItem
				fragmentDropZoneLayoutStructureItem =
					(FragmentDropZoneLayoutStructureItem)
						layoutStructure.addFragmentDropZoneLayoutStructureItem(
							fragmentStyledLayoutStructureItem.getItemId(), i);

			if (!Validator.isBlank(fragmentDropZoneId)) {
				fragmentDropZoneLayoutStructureItem.setFragmentDropZoneId(
					fragmentDropZoneId);
			}

			layoutStructure.addLayoutStructureItem(
				fragmentDropZoneLayoutStructureItem);

			fragmentDropZoneLayoutStructureItems.add(
				fragmentDropZoneLayoutStructureItem);
		}

		return fragmentDropZoneLayoutStructureItems.toArray(
			new FragmentDropZoneLayoutStructureItem[0]);
	}

	public static String getHTML(String... dropZoneIds) {
		StringBundler sb = new StringBundler("<div class=\"fragment_1\">");

		for (String dropZoneId : dropZoneIds) {
			sb.append("<lfr-drop-zone");

			if (!Validator.isBlank(dropZoneId)) {
				sb.append(" data-lfr-drop-zone-id=\"");
				sb.append(dropZoneId);
				sb.append(StringPool.QUOTE);
			}

			sb.append("></lfr-drop-zone>");
		}

		sb.append("</div>");

		return sb.toString();
	}

	public static FragmentEntryLink getMockFragmentEntryLink() {
		FragmentEntryLink fragmentEntryLink = Mockito.mock(
			FragmentEntryLink.class);

		Mockito.when(
			fragmentEntryLink.getFragmentEntryLinkId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		return fragmentEntryLink;
	}

}