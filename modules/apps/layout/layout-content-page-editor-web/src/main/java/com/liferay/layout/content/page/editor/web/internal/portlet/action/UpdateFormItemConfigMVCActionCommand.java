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

import com.liferay.fragment.contributor.FragmentCollectionContributor;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkManager;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureService;
import com.liferay.layout.util.structure.FormStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/layout_content_page_editor/update_form_item_config"
	},
	service = MVCActionCommand.class
)
public class UpdateFormItemConfigMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse,
			_updateFormItemConfig(actionRequest, actionResponse));
	}

	private List<FragmentEntryLink> _addFormChildrenItems(
			String formItemId, HttpServletRequest httpServletRequest,
			JSONObject jsonObject, LayoutStructure layoutStructure,
			long segmentsExperienceId, ThemeDisplay themeDisplay)
		throws PortalException {

		FragmentCollectionContributor fragmentCollectionContributor =
			_fragmentCollectionContributorTracker.
				getFragmentCollectionContributor("INPUTS");

		FragmentEntry fragmentEntry =
			_fragmentCollectionContributorTracker.getFragmentEntry(
				"INPUTS-submit-button");

		if (fragmentCollectionContributor == null) {
			jsonObject.put(
				"errorMessage",
				LanguageUtil.get(
					themeDisplay.getLocale(),
					"your-form-could-not-be-loaded-because-fragments-are-not-" +
						"available"));
		}
		else if (fragmentEntry == null) {
			jsonObject.put(
				"errorMessage",
				LanguageUtil.format(
					themeDisplay.getLocale(),
					"some-fragments-are-missing.-x-could-not-be-added-to-" +
						"your-form-because-they-are-not-available",
					"submit-button"));
		}
		else {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				httpServletRequest);

			FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink(
				formItemId, fragmentEntry, layoutStructure,
				segmentsExperienceId, serviceContext, themeDisplay);

			return ListUtil.fromArray(fragmentEntryLink);
		}

		return Collections.emptyList();
	}

	private FragmentEntryLink _addFragmentEntryLink(
			String formItemId, FragmentEntry fragmentEntry,
			LayoutStructure layoutStructure, long segmentsExperienceId,
			ServiceContext serviceContext, ThemeDisplay themeDisplay)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkService.addFragmentEntryLink(
				themeDisplay.getScopeGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), segmentsExperienceId,
				themeDisplay.getPlid(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				fragmentEntry.getConfiguration(), null, StringPool.BLANK, 0,
				fragmentEntry.getFragmentEntryKey(), fragmentEntry.getType(),
				serviceContext);

		layoutStructure.addFragmentStyledLayoutStructureItem(
			fragmentEntryLink.getFragmentEntryLinkId(), formItemId, 0);

		return fragmentEntryLink;
	}

	private JSONArray _removeFormChildrenItems(
		LayoutStructure layoutStructure, List<String> childrenItemIdsToRemove) {

		JSONArray fragmentEntryLinkIdsJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (String childItemId : childrenItemIdsToRemove) {
			layoutStructure.markLayoutStructureItemForDeletion(
				childItemId, Collections.emptyList());

			LayoutStructureItem removedLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(childItemId);

			if (removedLayoutStructureItem instanceof
					FragmentStyledLayoutStructureItem) {

				FragmentStyledLayoutStructureItem
					fragmentStyledLayoutStructureItem =
						(FragmentStyledLayoutStructureItem)
							removedLayoutStructureItem;

				fragmentEntryLinkIdsJSONArray.put(
					String.valueOf(
						fragmentStyledLayoutStructureItem.
							getFragmentEntryLinkId()));
			}
		}

		return fragmentEntryLinkIdsJSONArray;
	}

	private JSONObject _updateFormItemConfig(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId");
		String itemConfig = ParamUtil.getString(actionRequest, "itemConfig");
		String formItemId = ParamUtil.getString(actionRequest, "itemId");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			LayoutPageTemplateStructure layoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						themeDisplay.getScopeGroupId(), themeDisplay.getPlid());

			LayoutStructure layoutStructure = LayoutStructure.of(
				layoutPageTemplateStructure.getData(segmentsExperienceId));

			FormStyledLayoutStructureItem
				previousFormStyledLayoutStructureItem =
					(FormStyledLayoutStructureItem)
						layoutStructure.getLayoutStructureItem(formItemId);

			FormStyledLayoutStructureItem formStyledLayoutStructureItem =
				(FormStyledLayoutStructureItem)layoutStructure.updateItemConfig(
					JSONFactoryUtil.createJSONObject(itemConfig), formItemId);

			JSONArray removedLayoutStructureItemsJSONArray =
				JSONFactoryUtil.createJSONArray();

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(actionRequest);

			List<FragmentEntryLink> addedFragmentEntryLinks = new ArrayList<>();

			if (!Objects.equals(
					previousFormStyledLayoutStructureItem.getClassNameId(),
					formStyledLayoutStructureItem.getClassNameId()) ||
				!Objects.equals(
					previousFormStyledLayoutStructureItem.getClassNameId(),
					formStyledLayoutStructureItem.getClassTypeId())) {

				removedLayoutStructureItemsJSONArray = _removeFormChildrenItems(
					layoutStructure,
					ListUtil.copy(
						formStyledLayoutStructureItem.getChildrenItemIds()));

				addedFragmentEntryLinks.addAll(
					_addFormChildrenItems(
						formItemId, httpServletRequest, jsonObject,
						layoutStructure, segmentsExperienceId, themeDisplay));
			}

			layoutPageTemplateStructure =
				_layoutPageTemplateStructureService.
					updateLayoutPageTemplateStructureData(
						themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
						segmentsExperienceId, layoutStructure.toString());

			HttpServletResponse httpServletResponse =
				_portal.getHttpServletResponse(actionResponse);

			LayoutStructure updatedLayoutStructure = LayoutStructure.of(
				layoutPageTemplateStructure.getData(segmentsExperienceId));

			jsonObject.put(
				"addedFragmentEntryLinks",
				() -> {
					JSONObject addedFragmentEntryLinksJSONObject =
						JSONFactoryUtil.createJSONObject();

					for (FragmentEntryLink addedFragmentEntryLink :
							addedFragmentEntryLinks) {

						addedFragmentEntryLinksJSONObject.put(
							String.valueOf(
								addedFragmentEntryLink.
									getFragmentEntryLinkId()),
							_fragmentEntryLinkManager.
								getFragmentEntryLinkJSONObject(
									addedFragmentEntryLink, httpServletRequest,
									httpServletResponse,
									updatedLayoutStructure));
					}

					return addedFragmentEntryLinksJSONObject;
				}
			).put(
				"layoutData", updatedLayoutStructure.toJSONObject()
			).put(
				"removedFragmentEntryLinkIds",
				removedLayoutStructureItemsJSONArray
			);
		}
		catch (Exception exception) {
			_log.error(exception);

			jsonObject.put(
				"error",
				LanguageUtil.get(
					themeDisplay.getRequest(), "an-unexpected-error-occurred"));
		}

		hideDefaultSuccessMessage(actionRequest);

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpdateFormItemConfigMVCActionCommand.class);

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryLinkManager _fragmentEntryLinkManager;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutPageTemplateStructureService
		_layoutPageTemplateStructureService;

	@Reference
	private Portal _portal;

}