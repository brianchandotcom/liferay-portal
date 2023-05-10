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
import com.liferay.change.tracking.spi.display.CTDisplayRendererRegistry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
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

		_ctCollectionId = ParamUtil.getLong(renderRequest, "ctCollectionId");
		_modelClassNameId = ParamUtil.getLong(
			renderRequest, "modelClassNameId");
		_modelClassPK = ParamUtil.getLong(renderRequest, "modelClassPK");
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
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
			"spritemap", _themeDisplay.getPathThemeSpritemap()
		).put(
			"typeNames",
			DisplayContextUtil.getTypeNamesJSONObject(
				classNameIds, _ctDisplayRendererRegistry, _themeDisplay)
		).put(
			"userInfo",
			DisplayContextUtil.getUserInfoJSONObject(
				CTEntryTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId),
				CTEntryTable.INSTANCE, _themeDisplay, _userLocalService,
				CTEntryTable.INSTANCE.ctEntryId.in(
					ctEntryIds.toArray(new Long[0])))
		).build();
	}

	public String getRedirectURL() {
		String redirect = ParamUtil.getString(_renderRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			return redirect;
		}

		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/change_tracking/view_changes"
		).setParameter(
			"ctCollectionId", _ctCollectionId
		).buildString();
	}

	public String getSubmitURL() {
		return PortletURLBuilder.createActionURL(
			_renderResponse
		).setActionName(
			"/change_tracking/discard_changes"
		).setRedirect(
			getRedirectURL()
		).setParameter(
			"ctCollectionId", _ctCollectionId
		).setParameter(
			"modelClassNameId", _modelClassNameId
		).setParameter(
			"modelClassPK", _modelClassPK
		).buildString();
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