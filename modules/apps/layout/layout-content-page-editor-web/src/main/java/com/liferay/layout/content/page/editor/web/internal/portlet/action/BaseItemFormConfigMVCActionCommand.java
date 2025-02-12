/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.content.page.editor.web.internal.manager.FormItemManager;
import com.liferay.layout.content.page.editor.web.internal.manager.FragmentEntryLinkManager;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseItemFormConfigMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	protected JSONObject getLayoutStructureItemChangesJSONObject(
			List<FragmentEntryLink> addedFragmentEntryLinks,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, JSONObject jsonObject,
			LayoutStructure layoutStructure,
			FormItemManager.LayoutStructureItemChanges
				layoutStructureItemChanges,
			FragmentEntryLink stepperFragmentEntryLink)
		throws PortalException {

		JSONObject addedFragmentEntryLinksJSONObject =
			jsonFactory.createJSONObject();

		for (FragmentEntryLink addedFragmentEntryLink :
				addedFragmentEntryLinks) {

			addedFragmentEntryLinksJSONObject.put(
				String.valueOf(addedFragmentEntryLink.getFragmentEntryLinkId()),
				fragmentEntryLinkManager.getFragmentEntryLinkJSONObject(
					addedFragmentEntryLink, httpServletRequest,
					httpServletResponse, layoutStructure));

			layoutStructureItemChanges.addAddedLayoutStructureItems(
				layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
					addedFragmentEntryLink.getFragmentEntryLinkId()));
		}

		return jsonObject.put(
			"addedFragmentEntryLinks", addedFragmentEntryLinksJSONObject
		).put(
			"addedItemIds",
			jsonFactory.createJSONArray(
				TransformUtil.transform(
					layoutStructureItemChanges.getAddedLayoutStructureItems(),
					LayoutStructureItem::getItemId))
		).put(
			"fragmentEntryLinks",
			() -> {
				if (stepperFragmentEntryLink == null) {
					return null;
				}

				return JSONUtil.put(
					String.valueOf(
						stepperFragmentEntryLink.getFragmentEntryLinkId()),
					fragmentEntryLinkManager.getFragmentEntryLinkJSONObject(
						stepperFragmentEntryLink, httpServletRequest,
						httpServletResponse, layoutStructure));
			}
		).put(
			"layoutData", layoutStructure.toJSONObject()
		).put(
			"movedItemIds",
			() -> {
				JSONArray jsonArray = jsonFactory.createJSONArray();

				for (LayoutStructureItem movedLayoutStructureItem :
						layoutStructureItemChanges.
							getMovedLayoutStructureItems()) {

					jsonArray.put(
						JSONUtil.put(
							"itemId", movedLayoutStructureItem.getItemId()
						).put(
							"parentId",
							movedLayoutStructureItem.getParentItemId()
						));
				}

				return jsonArray;
			}
		).put(
			"removedItemIds",
			jsonFactory.createJSONArray(
				TransformUtil.transform(
					layoutStructureItemChanges.getRemovedLayoutStructureItems(),
					LayoutStructureItem::getItemId))
		);
	}

	@Reference
	protected FormItemManager formItemManager;

	@Reference
	protected FragmentEntryLinkLocalService fragmentEntryLinkLocalService;

	@Reference
	protected FragmentEntryLinkManager fragmentEntryLinkManager;

	@Reference
	protected JSONFactory jsonFactory;

}