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

import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.exception.NoSuchEntryLinkException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.excecption.NoninstanceablePortletException;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkUtil;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.util.structure.FragmentLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/duplicate_item"
	},
	service = MVCActionCommand.class
)
public class DuplicateItemMVCActionCommand
	extends BaseContentPageEditorTransactionalMVCActionCommand {

	@Override
	protected JSONObject executeTransactionalMVCActionCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject =
			_addDuplicateFragmentEntryLinkToLayoutDataJSONObject(
				actionRequest, actionResponse);

		SessionMessages.add(actionRequest, "fragmentEntryLinkDuplicated");

		return jsonObject;
	}

	@Override
	protected JSONObject processException(
		ActionRequest actionRequest, Exception exception) {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String errorMessage = StringPool.BLANK;

		if (exception instanceof NoSuchEntryLinkException) {
			errorMessage = LanguageUtil.get(
				themeDisplay.getRequest(),
				"the-section-could-not-be-duplicated-because-it-has-been-" +
					"deleted");
		}
		else if (exception instanceof NoninstanceablePortletException) {
			NoninstanceablePortletException noninstanceablePortletException =
				(NoninstanceablePortletException)exception;

			Portlet portlet = _portletLocalService.getPortletById(
				themeDisplay.getCompanyId(),
				noninstanceablePortletException.getPortletId());

			HttpServletRequest httpServletRequest =
				_portal.getHttpServletRequest(actionRequest);

			HttpSession httpSession = httpServletRequest.getSession();

			errorMessage = LanguageUtil.format(
				themeDisplay.getRequest(),
				"the-layout-could-not-be-duplicated-because-it-contains-a-" +
					"widget-x-that-can-only-appear-once-in-the-page",
				_portal.getPortletTitle(
					portlet, httpSession.getServletContext(),
					themeDisplay.getLocale()));
		}
		else {
			errorMessage = LanguageUtil.get(
				themeDisplay.getRequest(), "an-unexpected-error-occurred");
		}

		return JSONUtil.put("error", errorMessage);
	}

	private JSONObject _addDuplicateFragmentEntryLinkToLayoutDataJSONObject(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long segmentsExperienceId = ParamUtil.getLong(
			actionRequest, "segmentsExperienceId",
			SegmentsExperienceConstants.ID_DEFAULT);
		String itemId = ParamUtil.getString(actionRequest, "itemId");

		JSONArray duplicatedFragmentEntryLinksJSONArray =
			JSONFactoryUtil.createJSONArray();
		List<String> duplicatedLayoutStructureItemIds = new ArrayList<>();

		JSONObject layoutDataJSONObject =
			LayoutStructureUtil.updateLayoutPageTemplateData(
				themeDisplay.getScopeGroupId(), segmentsExperienceId,
				themeDisplay.getPlid(),
				layoutStructure -> {
					List<LayoutStructureItem> duplicatedLayoutStructureItems =
						layoutStructure.duplicateLayoutStructureItem(itemId);

					for (LayoutStructureItem duplicatedLayoutStructureItem :
							duplicatedLayoutStructureItems) {

						duplicatedLayoutStructureItemIds.add(
							duplicatedLayoutStructureItem.getItemId());

						if (!(duplicatedLayoutStructureItem instanceof
								FragmentLayoutStructureItem)) {

							continue;
						}

						FragmentLayoutStructureItem
							fragmentLayoutStructureItem =
								(FragmentLayoutStructureItem)
									duplicatedLayoutStructureItem;

						JSONObject fragmentEntryLinkJSONObject =
							_duplicateFragmentEntryLink(
								actionRequest, actionResponse,
								fragmentLayoutStructureItem.
									getFragmentEntryLinkId());

						layoutStructure.updateItemConfig(
							JSONUtil.put(
								"fragmentEntryLinkId",
								fragmentEntryLinkJSONObject.get(
									"fragmentEntryLinkId")),
							duplicatedLayoutStructureItem.getItemId());

						duplicatedFragmentEntryLinksJSONArray.put(
							fragmentEntryLinkJSONObject);
					}
				});

		JSONObject jsonObject = JSONUtil.put(
			"duplicatedFragmentEntryLinks",
			duplicatedFragmentEntryLinksJSONArray);

		if (!duplicatedLayoutStructureItemIds.isEmpty()) {
			jsonObject.put(
				"duplicatedItemId", duplicatedLayoutStructureItemIds.get(0));
		}

		return jsonObject.put("layoutData", layoutDataJSONObject);
	}

	private void _copyPortletPreferences(
			HttpServletRequest httpServletRequest, String portletId,
			String oldInstanceId, String newInstanceId)
		throws PortalException {

		PortletPreferences portletPreferences =
			_portletPreferencesFactory.getPortletPreferences(
				httpServletRequest,
				PortletIdCodec.encode(portletId, oldInstanceId));

		PortletPreferencesIds portletPreferencesIds =
			_portletPreferencesFactory.getPortletPreferencesIds(
				httpServletRequest,
				PortletIdCodec.encode(portletId, oldInstanceId));

		_portletPreferencesLocalService.addPortletPreferences(
			portletPreferencesIds.getCompanyId(),
			portletPreferencesIds.getOwnerId(),
			portletPreferencesIds.getOwnerType(),
			portletPreferencesIds.getPlid(),
			PortletIdCodec.encode(portletId, newInstanceId), null,
			PortletPreferencesFactoryUtil.toXML(portletPreferences));
	}

	private JSONObject _duplicateFragmentEntryLink(
			ActionRequest actionRequest, ActionResponse actionResponse,
			long fragmentEntryLinkId)
		throws PortalException {

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				fragmentEntryLinkId);

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		String portletId = editableValuesJSONObject.getString("portletId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (Validator.isNotNull(portletId)) {
			Portlet portlet = _portletLocalService.getPortletById(portletId);

			if (!portlet.isInstanceable()) {
				throw new NoninstanceablePortletException(portletId);
			}

			String oldInstanceId = editableValuesJSONObject.getString(
				"instanceId");

			String newInstanceId = PortletIdCodec.generateInstanceId();

			editableValuesJSONObject.put("instanceId", newInstanceId);

			_copyPortletPreferences(
				serviceContext.getRequest(), portletId, oldInstanceId,
				newInstanceId);
		}

		FragmentEntryLink duplicateFragmentEntryLink =
			_fragmentEntryLinkService.addFragmentEntryLink(
				fragmentEntryLink.getGroupId(),
				fragmentEntryLink.getOriginalFragmentEntryLinkId(),
				fragmentEntryLink.getFragmentEntryId(),
				fragmentEntryLink.getClassNameId(),
				fragmentEntryLink.getClassPK(), fragmentEntryLink.getCss(),
				fragmentEntryLink.getHtml(), fragmentEntryLink.getJs(),
				fragmentEntryLink.getConfiguration(),
				editableValuesJSONObject.toString(), StringUtil.randomId(), 0,
				fragmentEntryLink.getRendererKey(), serviceContext);

		return FragmentEntryLinkUtil.getFragmentEntryLinkJSONObject(
			actionRequest, actionResponse, _fragmentEntryConfigurationParser,
			duplicateFragmentEntryLink, _fragmentCollectionContributorTracker,
			_fragmentRendererController, _fragmentRendererTracker, portletId);
	}

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesFactory _portletPreferencesFactory;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}