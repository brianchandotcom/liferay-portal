/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.util.layout.structure;

import com.liferay.fragment.contributor.util.FragmentCollectionContributorRegistryUtil;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.util.FragmentRendererRegistryUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalServiceUtil;
import com.liferay.fragment.service.FragmentEntryLocalServiceUtil;
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
import com.liferay.portal.kernel.util.ScopeUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Víctor Galán
 */
public class LayoutStructureUtil {

	public static int countInvalidFragments(
		String itemId, LayoutStructure layoutStructure) {

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(itemId);

		if (layoutStructureItem == null) {
			return 0;
		}

		List<FragmentEntryLink> fragmentEntryLinks =
			_getMissingFragmentEntryFragmentEntryLinks(
				layoutStructureItem.getChildrenItemIds(), layoutStructure);

		return fragmentEntryLinks.size();
	}

	public static void deleteMarkedForDeletionItems(
			long groupId, long plid, long userId)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(groupId, plid);

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
					userId, groupId, plid,
					layoutPageTemplateStructureRel.getSegmentsExperienceId(),
					layoutStructure.toString());
		}
	}

	public static LayoutStructure getLayoutStructure(
			long groupId, long plid, long segmentsExperienceId)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(groupId, plid);

		return LayoutStructure.of(
			layoutPageTemplateStructure.getData(segmentsExperienceId));
	}

	public static LayoutStructure getLayoutStructure(
			long groupId, long plid, String segmentsExperienceKey)
		throws PortalException {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(groupId, plid);

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

	private static FragmentEntry _getFragmentEntry(
		FragmentEntryLink fragmentEntryLink) {

		if (Validator.isNull(fragmentEntryLink.getFragmentEntryERC())) {
			return FragmentCollectionContributorRegistryUtil.getFragmentEntry(
				fragmentEntryLink.getRendererKey());
		}

		Long fragmentEntryGroupId = ScopeUtil.getItemGroupId(
			fragmentEntryLink.getCompanyId(),
			fragmentEntryLink.getFragmentEntryScopeERC(),
			fragmentEntryLink.getGroupId());

		if (fragmentEntryGroupId == null) {
			return null;
		}

		return FragmentEntryLocalServiceUtil.
			fetchFragmentEntryByExternalReferenceCode(
				fragmentEntryLink.getFragmentEntryERC(), fragmentEntryGroupId);
	}

	private static List<FragmentEntryLink>
		_getMissingFragmentEntryFragmentEntryLinks(
			List<String> itemIds, LayoutStructure layoutStructure) {

		List<FragmentEntryLink> fragmentEntryLinks = new ArrayList<>();

		for (String itemId : itemIds) {
			LayoutStructureItem layoutStructureItem =
				layoutStructure.getLayoutStructureItem(itemId);

			fragmentEntryLinks.addAll(
				_getMissingFragmentEntryFragmentEntryLinks(
					layoutStructureItem.getChildrenItemIds(), layoutStructure));

			if (!(layoutStructureItem instanceof
					FragmentStyledLayoutStructureItem)) {

				continue;
			}

			FragmentStyledLayoutStructureItem
				fragmentStyledLayoutStructureItem =
					(FragmentStyledLayoutStructureItem)layoutStructureItem;

			FragmentEntryLink fragmentEntryLink =
				FragmentEntryLinkLocalServiceUtil.fetchFragmentEntryLink(
					fragmentStyledLayoutStructureItem.getFragmentEntryLinkId());

			if (Validator.isNull(fragmentEntryLink.getFragmentEntryERC()) &&
				Validator.isNull(fragmentEntryLink.getRendererKey())) {

				continue;
			}

			FragmentEntry fragmentEntry = _getFragmentEntry(fragmentEntryLink);

			if (fragmentEntry != null) {
				continue;
			}

			if (Validator.isNull(fragmentEntryLink.getRendererKey())) {
				fragmentEntryLinks.add(fragmentEntryLink);

				continue;
			}

			FragmentRenderer fragmentRenderer =
				FragmentRendererRegistryUtil.getFragmentRenderer(
					fragmentEntryLink.getRendererKey());

			if (fragmentRenderer == null) {
				fragmentEntryLinks.add(fragmentEntryLink);
			}
		}

		return fragmentEntryLinks;
	}

}