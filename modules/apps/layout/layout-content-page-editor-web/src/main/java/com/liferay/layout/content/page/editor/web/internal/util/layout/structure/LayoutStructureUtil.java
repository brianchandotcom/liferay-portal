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

import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.layout.content.page.editor.listener.ContentPageEditorListenerTracker;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureServiceUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.DeletedLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Víctor Galán
 */
public class LayoutStructureUtil {

	public static void deleteMarkedForDeletionItems(
			long companyId,
			ContentPageEditorListenerTracker contentPageEditorListenerTracker,
			long groupId, long plid, PortletRegistry portletRegistry)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(groupId, plid, true);

		if (layoutPageTemplateStructure == null) {
			return;
		}

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

				List<LayoutStructureItem> deletedLayoutStructureItems =
					layoutStructure.deleteLayoutStructureItem(
						deletedLayoutStructureItem.getItemId());

				for (long fragmentEntryLinkId :
						getFragmentEntryLinkIds(deletedLayoutStructureItems)) {

					FragmentEntryLinkUtil.deleteFragmentEntryLink(
						companyId, contentPageEditorListenerTracker,
						fragmentEntryLinkId, plid, portletRegistry);
				}
			}

			LayoutPageTemplateStructureLocalServiceUtil.
				updateLayoutPageTemplateStructureData(
					groupId, plid,
					layoutPageTemplateStructureRel.getSegmentsExperienceId(),
					layoutStructure.toString());
		}
	}

	public static List<String> getCollectionStyledLayoutStructureItemIds(
		long fragmentEntryLinkId, LayoutStructure layoutStructure) {

		if (layoutStructure == null) {
			return Collections.emptyList();
		}

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
				fragmentEntryLinkId);

		if (layoutStructureItem == null) {
			return Collections.emptyList();
		}

		LayoutStructureItem currentLayoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				layoutStructureItem.getParentItemId());

		List<String> collectionStyledLayoutStructureItemIds = new ArrayList<>();

		while (!Objects.equals(
					currentLayoutStructureItem.getItemId(),
					layoutStructure.getMainItemId())) {

			if (Objects.equals(
					LayoutDataItemTypeConstants.TYPE_COLLECTION,
					currentLayoutStructureItem.getItemType())) {

				collectionStyledLayoutStructureItemIds.add(
					currentLayoutStructureItem.getItemId());
			}

			currentLayoutStructureItem = layoutStructure.getLayoutStructureItem(
				currentLayoutStructureItem.getParentItemId());
		}

		Collections.reverse(collectionStyledLayoutStructureItemIds);

		return collectionStyledLayoutStructureItemIds;
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