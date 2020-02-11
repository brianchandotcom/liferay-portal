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

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.renderer.DefaultFragmentRendererContext;
import com.liferay.fragment.renderer.FragmentRendererController;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.PortletIdException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.util.SegmentsExperiencePortletUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET,
		"mvc.command.name=/content_layout/add_portlet"
	},
	service = MVCActionCommand.class
)
public class AddPortletMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _processAddPortlet(
			actionRequest, actionResponse);

		hideDefaultSuccessMessage(actionRequest);

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);
	}

	private String _getPortletInstanceId(
			Layout layout, String portletId, long segmentsExperienceId)
		throws PortletIdException {

		Portlet portlet = _portletLocalService.getPortletById(portletId);

		if (portlet.isInstanceable()) {
			return SegmentsExperiencePortletUtil.setSegmentsExperienceId(
				PortletIdCodec.generateInstanceId(), segmentsExperienceId);
		}

		String instanceId =
			SegmentsExperiencePortletUtil.setSegmentsExperienceId(
				String.valueOf(CharPool.NUMBER_0), segmentsExperienceId);

		String checkPortletId =
			SegmentsExperiencePortletUtil.setSegmentsExperienceId(
				PortletIdCodec.encode(portletId, instanceId),
				segmentsExperienceId);

		long count = _portletPreferencesLocalService.getPortletPreferencesCount(
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
			checkPortletId);

		if (count > 0) {
			throw new PortletIdException(
				"Unable to add uninstanceable portlet more than once");
		}

		return instanceId;
	}

	private JSONObject _processAddPortlet(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		Layout layout = _layoutLocalService.getLayout(themeDisplay.getPlid());

		String portletId = PortletIdCodec.decodePortletName(
			ParamUtil.getString(actionRequest, "portletId"));

		PortletPermissionUtil.check(
			themeDisplay.getPermissionChecker(), layout.getGroupId(), layout,
			portletId, ActionKeys.ADD_TO_PAGE);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		try {
			String instanceId = _getPortletInstanceId(
				layout, portletId,
				ParamUtil.getLong(actionRequest, "segmentsExperienceId"));

			JSONObject editableValueJSONObject =
				_fragmentEntryProcessorRegistry.
					getDefaultEditableValuesJSONObject(
						StringPool.BLANK, StringPool.BLANK);

			editableValueJSONObject.put(
				"instanceId", instanceId
			).put(
				"portletId", portletId
			);

			FragmentEntryLink fragmentEntryLink =
				_fragmentEntryLinkLocalService.addFragmentEntryLink(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), 0, 0,
					_portal.getClassNameId(Layout.class),
					themeDisplay.getPlid(), StringPool.BLANK, StringPool.BLANK,
					StringPool.BLANK, StringPool.BLANK,
					editableValueJSONObject.toString(), StringPool.BLANK, 0,
					null, serviceContext);

			DefaultFragmentRendererContext defaultFragmentRendererContext =
				new DefaultFragmentRendererContext(fragmentEntryLink);

			defaultFragmentRendererContext.setMode(
				FragmentEntryLinkConstants.EDIT);

			jsonObject.put(
				"content",
				_fragmentRendererController.render(
					defaultFragmentRendererContext,
					_portal.getHttpServletRequest(actionRequest),
					_portal.getHttpServletResponse(actionResponse))
			).put(
				"editableValues", fragmentEntryLink.getEditableValues()
			);

			if (SessionErrors.contains(
					actionRequest, "fragmentEntryContentInvalid")) {

				jsonObject.put("error", true);
			}

			jsonObject.put(
				"fragmentEntryLinkId",
				fragmentEntryLink.getFragmentEntryLinkId()
			).put(
				"name",
				_portal.getPortletTitle(portletId, themeDisplay.getLocale())
			);

			SessionMessages.add(actionRequest, "fragmentEntryLinkAdded");
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			String errorMessage = "an-unexpected-error-occurred";

			jsonObject.put(
				"error",
				LanguageUtil.get(themeDisplay.getRequest(), errorMessage));
		}

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddPortletMVCActionCommand.class);

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private FragmentRendererController _fragmentRendererController;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}