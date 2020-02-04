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

package com.liferay.layout.content.page.editor.web.internal.util.layout.structure;

import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Víctor Galán
 */
public class LayoutStructureUtil {

	public static long[] getFragmentEntryLinkIds(
		List<LayoutStructureItem> layoutStructureItems) {

		List<Long> fragmentEntryLinkIds = new ArrayList<>();

		for (LayoutStructureItem layoutStructureItem : layoutStructureItems) {
			if (!(layoutStructureItem instanceof FragmentLayoutStructureItem)) {
				continue;
			}

			FragmentLayoutStructureItem fragmentLayoutStructureItem =
				(FragmentLayoutStructureItem)layoutStructureItem;

			if (fragmentLayoutStructureItem.getFragmentEntryLinkId() <= 0) {
				continue;
			}

			fragmentEntryLinkIds.add(
				fragmentLayoutStructureItem.getFragmentEntryLinkId());
		}

		return ArrayUtil.toLongArray(fragmentEntryLinkIds);
	}

	public static JSONObject updateLayoutPageTemplateData(
			long groupId, long segmentsExperienceId, long plid,
			UnsafeConsumer<LayoutStructure, PortalException> unsafeConsumer)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					groupId, PortalUtil.getClassNameId(Layout.class.getName()),
					plid, true);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		unsafeConsumer.accept(layoutStructure);

		JSONObject dataJSONObject = layoutStructure.toJSONObject();

		LayoutPageTemplateStructureLocalServiceUtil.
			updateLayoutPageTemplateStructure(
				groupId, PortalUtil.getClassNameId(Layout.class.getName()),
				plid, segmentsExperienceId, dataJSONObject.toString());

		return dataJSONObject;
	}

}