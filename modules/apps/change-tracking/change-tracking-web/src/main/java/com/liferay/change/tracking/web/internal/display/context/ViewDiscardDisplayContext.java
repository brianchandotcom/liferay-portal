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

package com.liferay.change.tracking.web.internal.display.context;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryTable;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class ViewDiscardDisplayContext {

	public ViewDiscardDisplayContext(
		CTCollectionLocalService ctCollectionLocalService,
		CTDisplayRendererRegistry ctDisplayRendererRegistry,
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse, UserLocalService userLocalService) {

		_ctCollectionLocalService = ctCollectionLocalService;
		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_userLocalService = userLocalService;

		_ctCollectionId = ParamUtil.getLong(_renderRequest, "ctCollectionId");
		_modelClassNameId = ParamUtil.getLong(
			_renderRequest, "modelClassNameId");
		_modelClassPK = ParamUtil.getLong(_renderRequest, "modelClassPK");
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getReactData() {
		Set<Long> ctEntryIds = new HashSet<>();
		Set<Long> classNameIds = new HashSet<>();

		List<CTEntry> ctEntries = _ctCollectionLocalService.getDiscardCTEntries(
			_ctCollectionId, _modelClassNameId, _modelClassPK);

		for (CTEntry ctEntry : ctEntries) {
			ctEntryIds.add(ctEntry.getCtEntryId());

			classNameIds.add(ctEntry.getModelClassNameId());
		}

		return HashMapBuilder.<String, Object>put(
			"ctEntriesJSONArray",
			() -> {
				JSONArray ctEntriesJSONArray =
					JSONFactoryUtil.createJSONArray();

				for (CTEntry ctEntry : ctEntries) {
					ResourceURL dataURL = _renderResponse.createResourceURL();

					dataURL.setResourceID(
						"/change_tracking/get_entry_render_data");
					dataURL.setParameter(
						"ctEntryId", String.valueOf(ctEntry.getCtEntryId()));

					ctEntriesJSONArray.put(
						JSONUtil.put(
							"ctEntryId", ctEntry.getCtEntryId()
						).put(
							"dataURL", dataURL.toString()
						).put(
							"description",
							_ctDisplayRendererRegistry.getEntryDescription(
								_httpServletRequest, ctEntry)
						).put(
							"modelClassNameId", ctEntry.getModelClassNameId()
						).put(
							"modelClassPK", ctEntry.getModelClassPK()
						).put(
							"title",
							_ctDisplayRendererRegistry.getTitle(
								ctEntry.getCtCollectionId(), ctEntry,
								_themeDisplay.getLocale())
						).put(
							"userId", ctEntry.getUserId()
						));
				}

				return ctEntriesJSONArray;
			}
		).put(
			"spritemap", _themeDisplay.getPathThemeImages() + "/clay/icons.svg"
		).put(
			"typeNames",
			DisplayContextUtil.getTypeNamesJSONObject(
				classNameIds, _ctDisplayRendererRegistry, _themeDisplay)
		).put(
			"userInfo",
			DisplayContextUtil.getUserInfoJSONObject(
				CTEntryTable.INSTANCE.ctEntryId.in(
					ctEntryIds.toArray(new Long[0])),
				_themeDisplay, _userLocalService)
		).build();
	}

	public String getRedirectURL() {
		String redirect = ParamUtil.getString(_renderRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			return redirect;
		}

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/change_tracking/view_changes");
		portletURL.setParameter(
			"ctCollectionId", String.valueOf(_ctCollectionId));

		return portletURL.toString();
	}

	public String getSubmitURL() {
		ActionURL submitURL = _renderResponse.createActionURL();

		submitURL.setParameter(
			ActionRequest.ACTION_NAME, "/change_tracking/discard_changes");
		submitURL.setParameter("redirect", getRedirectURL());
		submitURL.setParameter(
			"ctCollectionId", String.valueOf(_ctCollectionId));
		submitURL.setParameter(
			"modelClassNameId", String.valueOf(_modelClassNameId));
		submitURL.setParameter("modelClassPK", String.valueOf(_modelClassPK));

		return submitURL.toString();
	}

	private final long _ctCollectionId;
	private final CTCollectionLocalService _ctCollectionLocalService;
	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;
	private final HttpServletRequest _httpServletRequest;
	private final long _modelClassNameId;
	private final long _modelClassPK;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;
	private final UserLocalService _userLocalService;

}