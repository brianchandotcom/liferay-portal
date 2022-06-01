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
import com.liferay.fragment.listener.FragmentEntryLinkListener;
import com.liferay.fragment.listener.FragmentEntryLinkListenerTracker;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.DefaultFragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.FragmentEntryLinkService;
import com.liferay.layout.content.page.editor.constants.ContentPageEditorPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.util.FragmentEntryLinkManager;
import com.liferay.layout.content.page.editor.web.internal.util.layout.structure.LayoutStructureUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Iterator;
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
		"mvc.command.name=/layout_content_page_editor/update_configuration_values"
	},
	service = MVCActionCommand.class
)
public class UpdateConfigurationValuesMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse,
			_processUpdateConfigurationValues(actionRequest, actionResponse));
	}

	private JSONObject _mergeEditableValuesJSONObject(
			JSONObject defaultEditableValuesJSONObject, String editableValues)
		throws Exception {

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			editableValues);

		for (String fragmentEntryProcessorKey :
				_FRAGMENT_ENTRY_PROCESSOR_KEYS) {

			JSONObject editableFragmentEntryProcessorJSONObject =
				editableValuesJSONObject.getJSONObject(
					fragmentEntryProcessorKey);

			if (editableFragmentEntryProcessorJSONObject == null) {
				continue;
			}

			JSONObject defaultEditableFragmentEntryProcessorJSONObject =
				defaultEditableValuesJSONObject.getJSONObject(
					fragmentEntryProcessorKey);

			if (defaultEditableFragmentEntryProcessorJSONObject == null) {
				continue;
			}

			Iterator<String> iterator =
				defaultEditableFragmentEntryProcessorJSONObject.keys();

			while (iterator.hasNext()) {
				String key = iterator.next();

				if (editableFragmentEntryProcessorJSONObject.has(key)) {
					defaultEditableFragmentEntryProcessorJSONObject.put(
						key, editableFragmentEntryProcessorJSONObject.get(key));
				}
			}

			editableValuesJSONObject.put(
				fragmentEntryProcessorKey,
				defaultEditableFragmentEntryProcessorJSONObject);
		}

		return editableValuesJSONObject;
	}

	private JSONObject _processUpdateConfigurationValues(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fragmentEntryLinkId = ParamUtil.getLong(
			actionRequest, "fragmentEntryLinkId");

		String editableValues = ParamUtil.getString(
			actionRequest, "editableValues");

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkService.updateFragmentEntryLink(
				fragmentEntryLinkId, editableValues);

		FragmentEntryProcessorContext fragmentEntryProcessorContext =
			new DefaultFragmentEntryProcessorContext(
				_portal.getHttpServletRequest(actionRequest),
				_portal.getHttpServletResponse(actionResponse),
				FragmentEntryLinkConstants.EDIT,
				LocaleUtil.getMostRelevantLocale());

		String processedHTML =
			_fragmentEntryProcessorRegistry.processFragmentEntryLinkHTML(
				fragmentEntryLink, fragmentEntryProcessorContext);

		JSONObject newEditableValuesJSONObject = _mergeEditableValuesJSONObject(
			_fragmentEntryProcessorRegistry.getDefaultEditableValuesJSONObject(
				processedHTML, fragmentEntryLink.getConfiguration()),
			editableValues);

		fragmentEntryLink = _fragmentEntryLinkService.updateFragmentEntryLink(
			fragmentEntryLinkId, newEditableValuesJSONObject.toString());

		List<FragmentEntryLinkListener> fragmentEntryLinkListeners =
			_fragmentEntryLinkListenerTracker.getFragmentEntryLinkListeners();

		for (FragmentEntryLinkListener fragmentEntryLinkListener :
				fragmentEntryLinkListeners) {

			fragmentEntryLinkListener.
				onUpdateFragmentEntryLinkConfigurationValues(fragmentEntryLink);
		}

		hideDefaultSuccessMessage(actionRequest);

		LayoutStructure layoutStructure =
			LayoutStructureUtil.getLayoutStructure(
				themeDisplay.getScopeGroupId(), themeDisplay.getPlid(),
				fragmentEntryLink.getSegmentsExperienceId());

		JSONObject jsonObject = JSONUtil.put(
			"fragmentEntryLink",
			_fragmentEntryLinkManager.getFragmentEntryLinkJSONObject(
				fragmentEntryLink, _portal.getHttpServletRequest(actionRequest),
				_portal.getHttpServletResponse(actionResponse), layoutStructure,
				StringPool.BLANK));

		return jsonObject.put("layoutData", layoutStructure.toJSONObject());
	}

	private static final String[] _FRAGMENT_ENTRY_PROCESSOR_KEYS = {
		"com.liferay.fragment.entry.processor.editable." +
			"EditableFragmentEntryProcessor"
	};

	@Reference
	private FragmentEntryLinkListenerTracker _fragmentEntryLinkListenerTracker;

	@Reference
	private FragmentEntryLinkManager _fragmentEntryLinkManager;

	@Reference
	private FragmentEntryLinkService _fragmentEntryLinkService;

	@Reference
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}