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

package com.liferay.layout.page.template.util;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;

/**
 * @author Jürgen
 */
public class LayoutPageTemplateStructureHelperUtil {

	public static JSONObject generateContentLayoutStructure(
		List<FragmentEntryLink> fragmentEntryLinks) {

		return generateContentLayoutStructure(
			fragmentEntryLinks,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC);
	}

	public static JSONObject generateContentLayoutStructure(
		List<FragmentEntryLink> fragmentEntryLinks, int type) {

		if (fragmentEntryLinks.isEmpty() &&
			(type == LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT)) {

			LayoutStructure layoutStructure = new LayoutStructure();

			LayoutStructureItem rootLayoutStructureItem =
				layoutStructure.addRootLayoutStructureItem();

			layoutStructure.addDropZoneLayoutStructureItem(
				rootLayoutStructureItem.getItemId(), 0);

			return layoutStructure.toJSONObject();
		}

		if (fragmentEntryLinks.isEmpty()) {
			LayoutStructure layoutStructure = new LayoutStructure();

			layoutStructure.addRootLayoutStructureItem();

			return layoutStructure.toJSONObject();
		}

		LayoutStructure layoutStructure = new LayoutStructure();

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.addRootLayoutStructureItem();

		LayoutStructureItem containerLayoutStructureItem =
			layoutStructure.addContainerLayoutStructureItem(
				rootLayoutStructureItem.getItemId(), 0);

		LayoutStructureItem rowLayoutStructureItem =
			layoutStructure.addRowLayoutStructureItem(
				containerLayoutStructureItem.getItemId(), 0, 0);

		for (int i = 0; i < fragmentEntryLinks.size(); i++) {
			FragmentEntryLink fragmentEntryLink = fragmentEntryLinks.get(i);

			layoutStructure.addFragmentLayoutStructureItem(
				fragmentEntryLink.getFragmentEntryLinkId(),
				rowLayoutStructureItem.getItemId(), i);
		}

		return layoutStructure.toJSONObject();
	}

}