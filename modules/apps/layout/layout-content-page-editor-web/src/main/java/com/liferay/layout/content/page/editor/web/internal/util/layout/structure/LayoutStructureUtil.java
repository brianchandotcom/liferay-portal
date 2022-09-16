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

import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureServiceUtil;
import com.liferay.layout.util.structure.DeletedLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Víctor Galán
 */
public class LayoutStructureUtil {

	public static void deleteMarkedForDeletionItems(long groupId, long plid)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(groupId, plid, true);

		if (layoutPageTemplateStructure == null) {
			return;
		}

		FragmentEntryLinkLocalServiceUtil.deleteFragmentEntryLinks(
			groupId, plid, true);

		List<LayoutPageTemplateStructureRel> layoutPageTemplateStructureRels =
			LayoutPageTemplateStructureRelLocalServiceUtil.
				getLayoutPageTemplateStructureRels(
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId());

		for (LayoutPageTemplateStructureRel layoutPageTemplateStructureRel :
				layoutPageTemplateStructureRels) {

			LayoutStructure layoutStructure = LayoutStructure.of(
				layoutPageTemplateStructureRel.getData());

			for (DeletedLayoutStructureItem deletedLayoutStructureItem :
					layoutStructure.getDeletedLayoutStructureItems()) {

				layoutStructure.deleteLayoutStructureItem(
					deletedLayoutStructureItem.getItemId());
			}

			LayoutPageTemplateStructureLocalServiceUtil.
				updateLayoutPageTemplateStructureData(
					groupId, plid,
					layoutPageTemplateStructureRel.getSegmentsExperienceId(),
					layoutStructure.toString());
		}
	}

	public static long[] getFragmentEntryLinkIds(
		List<LayoutStructureItem> layoutStructureItems) {

		List<Long> fragmentEntryLinkIds = new ArrayList<>();

		for (LayoutStructureItem layoutStructureItem : layoutStructureItems) {
			if (!(layoutStructureItem instanceof
					FragmentStyledLayoutStructureItem)) {

				continue;
			}

			FragmentStyledLayoutStructureItem
				fragmentStyledLayoutStructureItem =
					(FragmentStyledLayoutStructureItem)layoutStructureItem;

			if (fragmentStyledLayoutStructureItem.getFragmentEntryLinkId() <=
					0) {

				continue;
			}

			fragmentEntryLinkIds.add(
				fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());
		}

		return ArrayUtil.toLongArray(fragmentEntryLinkIds);
	}

	public static LayoutStructure getLayoutStructure(
			long groupId, long plid, long segmentsExperienceId)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(groupId, plid, true);

		return LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));
	}

	public static LayoutStructure getLayoutStructure(
			long groupId, long plid, String segmentsExperienceKey)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(groupId, plid, true);

		return LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceKey));
	}

	public static JSONObject updateLayoutPageTemplateData(
			long groupId, long segmentsExperienceId, long plid,
			UnsafeConsumer<LayoutStructure, PortalException> unsafeConsumer)
		throws PortalException {

		LayoutStructure layoutStructure = getLayoutStructure(
			groupId, plid, segmentsExperienceId);

		unsafeConsumer.accept(layoutStructure);

		JSONObject dataJSONObject = layoutStructure.toJSONObject();

		LayoutPageTemplateStructureServiceUtil.
			updateLayoutPageTemplateStructureData(
				groupId, plid, segmentsExperienceId, dataJSONObject.toString());

		return dataJSONObject;
	}

}