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

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkUtil;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/delete_item"
	},
	service = MVCActionCommand.class
)
public class DeleteItemMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	protected JSONObject deleteItemJSONObject(
			long companyId, long groupId, String itemId, long plid,
			long segmentsExperienceId)
		throws PortalException {

		List<Long> deletedFragmentEntryLinkIds = new ArrayList<>();

		JSONObject layoutDataJSONObject =
			LayoutStructureUtil.updateLayoutPageTemplateData(
				groupId, segmentsExperienceId, plid,
				layoutStructure -> {
					List<LayoutStructureItem> deletedLayoutStructureItems =
						layoutStructure.deleteLayoutStructureItem(itemId);

					for (long fragmentEntryLinkId :
							LayoutStructureUtil.getFragmentEntryLinkIds(
								deletedLayoutStructureItems)) {

						if (_isFragmentEntryLinkInAnotherSegmentsExperience(
								fragmentEntryLinkId, groupId, plid,
								segmentsExperienceId)) {

							continue;
						}

						FragmentEntryLinkUtil.deleteFragmentEntryLink(
							companyId, fragmentEntryLinkId, plid,
							_portletRegistry);

						deletedFragmentEntryLinkIds.add(fragmentEntryLinkId);
					}
				});

		return JSONUtil.put(
			"deletedFragmentEntryLinkIds", deletedFragmentEntryLinkIds.toArray()
		).put(
			"layoutData", layoutDataJSONObject
		);
	}

	@Override
	protected JSONObject doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String itemId = ParamUtil.getString(actionRequest, "itemId");
		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId",
			SegmentsExperienceConstants.ID_DEFAULT);

		return deleteItemJSONObject(
			themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(), itemId,
			themeDisplay.getPlid(), segmentsExperienceId);
	}

	private boolean _isFragmentEntryLinkInAnotherSegmentsExperience(
		long fragmentEntryLinkId, long groupId, long plid,
		long segmentsExperienceId) {

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					groupId, _portal.getClassNameId(Layout.class.getName()),
					plid);

		List<LayoutPageTemplateStructureRel> layoutPageTemplateStructureRels =
			_layoutPageTemplateStructureRelLocalService.
				getLayoutPageTemplateStructureRels(
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId());

		for (LayoutPageTemplateStructureRel layoutPageTemplateStructureRel :
				layoutPageTemplateStructureRels) {

			if (layoutPageTemplateStructureRel.getSegmentsExperienceId() ==
					segmentsExperienceId) {

				continue;
			}

			LayoutStructure layoutStructure = LayoutStructure.of(
				layoutPageTemplateStructureRel.getData());

			for (LayoutStructureItem layoutStructureItem :
					layoutStructure.getLayoutStructureItems()) {

				if (!(layoutStructureItem instanceof
						FragmentLayoutStructureItem)) {

					continue;
				}

				FragmentLayoutStructureItem fragmentLayoutStructureItem =
					(FragmentLayoutStructureItem)layoutStructureItem;

				if (fragmentLayoutStructureItem.getFragmentEntryLinkId() ==
						fragmentEntryLinkId) {

					return true;
				}
			}
		}

		return false;
	}

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletRegistry _portletRegistry;

}