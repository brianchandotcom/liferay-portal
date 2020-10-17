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

import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class ViewConflictsDisplayContext {

	public ViewConflictsDisplayContext(
		long activeCtCollectionId, CTCollection ctCollection,
		Map<Long, List<ConflictInfo>> conflictInfoMap,
		CTDisplayRendererRegistry ctDisplayRendererRegistry,
		CTEntryLocalService ctEntryLocalService, Language language,
		Portal portal, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_activeCtCollectionId = activeCtCollectionId;
		_ctCollection = ctCollection;
		_ctDisplayRendererRegistry = ctDisplayRendererRegistry;
		_ctEntryLocalService = ctEntryLocalService;
		_language = language;
		_portal = portal;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_httpServletRequest = _portal.getHttpServletRequest(_renderRequest);
		_themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray resolvedConflictsJSONArray =
			JSONFactoryUtil.createJSONArray();

		JSONArray unresolvedConflictsJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (Map.Entry<Long, List<ConflictInfo>> entry :
				conflictInfoMap.entrySet()) {

			for (ConflictInfo conflictInfo : entry.getValue()) {
				JSONObject jsonObject = _getConflictJSONObject(
					conflictInfo, entry.getKey());

				if (conflictInfo.isResolved()) {
					resolvedConflictsJSONArray.put(jsonObject);
				}
				else {
					unresolvedConflictsJSONArray.put(jsonObject);
				}
			}
		}

		_resolvedConflictsReactData = HashMapBuilder.<String, Object>put(
			"conflicts", resolvedConflictsJSONArray
		).put(
			"spritemap", _themeDisplay.getPathThemeImages() + "/clay/icons.svg"
		).build();

		_resolvedConflicts = resolvedConflictsJSONArray.length();

		_unresolvedConflictsReactData = HashMapBuilder.<String, Object>put(
			"conflicts", unresolvedConflictsJSONArray
		).put(
			"spritemap", _themeDisplay.getPathThemeImages() + "/clay/icons.svg"
		).build();

		_unresolvedConflicts = unresolvedConflictsJSONArray.length();
	}

	public CTCollection getCtCollection() {
		return _ctCollection;
	}

	public String getRedirect() {
		String redirect = ParamUtil.getString(_renderRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			return redirect;
		}

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/publications/view_conflicts");
		portletURL.setParameter(
			"ctCollectionId",
			String.valueOf(_ctCollection.getCtCollectionId()));

		return portletURL.toString();
	}

	public Map<String, Object> getResolvedConflictsReactData() {
		return _resolvedConflictsReactData;
	}

	public String getResolvedConflictsTitle() {
		return StringBundler.concat(
			_language.get(_httpServletRequest, "automatically-resolved"), " (",
			_resolvedConflicts, ")");
	}

	public Map<String, Object> getUnresolvedConflictsReactData() {
		return _unresolvedConflictsReactData;
	}

	public String getUnresolvedConflictsTitle() {
		return StringBundler.concat(
			_language.get(_httpServletRequest, "needs-manual-resolution"), " (",
			_unresolvedConflicts, ")");
	}

	public boolean hasResolvedConflicts() {
		if (_resolvedConflicts > 0) {
			return true;
		}

		return false;
	}

	public boolean hasUnresolvedConflicts() {
		if (_unresolvedConflicts > 0) {
			return true;
		}

		return false;
	}

	private <T extends BaseModel<T>> JSONObject _getConflictJSONObject(
		ConflictInfo conflictInfo, long modelClassNameId) {

		ResourceBundle resourceBundle = conflictInfo.getResourceBundle(
			_themeDisplay.getLocale());

		JSONObject jsonObject = JSONUtil.put(
			"alertType", conflictInfo.isResolved() ? "success" : "warning"
		).put(
			"conflictDescription",
			conflictInfo.getConflictDescription(resourceBundle)
		).put(
			"conflictResolution",
			conflictInfo.getResolutionDescription(resourceBundle)
		);

		if (conflictInfo.isResolved()) {
			ActionURL dismissURL = _renderResponse.createActionURL();

			dismissURL.setParameter(
				ActionRequest.ACTION_NAME,
				"/change_tracking/delete_ct_auto_resolution_info");
			dismissURL.setParameter(
				"redirect", _portal.getCurrentURL(_renderRequest));
			dismissURL.setParameter(
				"ctAutoResolutionInfoId",
				String.valueOf(conflictInfo.getCTAutoResolutionInfoId()));

			jsonObject.put("dismissURL", dismissURL.toString());
		}

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
			_ctCollection.getCtCollectionId(), modelClassNameId,
			conflictInfo.getSourcePrimaryKey());

		if (ctEntry != null) {
			jsonObject.put(
				"description",
				_ctDisplayRendererRegistry.getEntryDescription(
					_httpServletRequest, ctEntry)
			).put(
				"title",
				_ctDisplayRendererRegistry.getTitle(
					_ctCollection.getCtCollectionId(), ctEntry,
					_themeDisplay.getLocale())
			);

			if (!conflictInfo.isResolved()) {
				JSONArray actionsJSONArray = JSONFactoryUtil.createJSONArray();

				if (_ctCollection.getCtCollectionId() ==
						_activeCtCollectionId) {

					String editURL = _ctDisplayRendererRegistry.getEditURL(
						_httpServletRequest, ctEntry);

					if (Validator.isNotNull(editURL)) {
						actionsJSONArray.put(
							JSONUtil.put(
								"href", editURL
							).put(
								"label",
								_language.get(_httpServletRequest, "edit-item")
							).put(
								"symbol", "pencil"
							));
					}
				}

				RenderURL discardURL = _renderResponse.createRenderURL();

				discardURL.setParameter(
					"mvcRenderCommandName", "/change_tracking/view_discard");
				discardURL.setParameter(
					"redirect", _portal.getCurrentURL(_renderRequest));
				discardURL.setParameter(
					"ctCollectionId",
					String.valueOf(ctEntry.getCtCollectionId()));
				discardURL.setParameter(
					"modelClassNameId",
					String.valueOf(ctEntry.getModelClassNameId()));
				discardURL.setParameter(
					"modelClassPK", String.valueOf(ctEntry.getModelClassPK()));

				actionsJSONArray.put(
					JSONUtil.put(
						"href", discardURL.toString()
					).put(
						"label",
						_language.get(_httpServletRequest, "discard-change")
					).put(
						"symbol", "times-circle"
					));

				jsonObject.put("actions", actionsJSONArray);
			}

			String viewURL = _getViewURL(
				_renderResponse, ctEntry,
				conflictInfo.getSourcePrimaryKey() ==
					conflictInfo.getTargetPrimaryKey());

			jsonObject.put("viewURL", viewURL);
		}
		else {
			T model = _ctDisplayRendererRegistry.fetchCTModel(
				modelClassNameId, conflictInfo.getTargetPrimaryKey());

			String title = null;

			if (model != null) {
				title = _ctDisplayRendererRegistry.getTitle(
					CTConstants.CT_COLLECTION_ID_PRODUCTION,
					CTSQLModeThreadLocal.CTSQLMode.DEFAULT,
					_themeDisplay.getLocale(), model, modelClassNameId);
			}
			else {
				title = _ctDisplayRendererRegistry.getTypeName(
					_themeDisplay.getLocale(), modelClassNameId);
			}

			jsonObject.put(
				"description",
				_ctDisplayRendererRegistry.getTypeName(
					_themeDisplay.getLocale(), modelClassNameId)
			).put(
				"title", title
			).put(
				"viewURL",
				_getViewURL(
					_renderResponse, modelClassNameId,
					conflictInfo.getTargetPrimaryKey())
			);
		}

		return jsonObject;
	}

	private String _getViewURL(
		RenderResponse renderResponse, CTEntry ctEntry, boolean viewDiff) {

		RenderURL viewURL = renderResponse.createRenderURL();

		if (viewDiff) {
			viewURL.setParameter(
				"mvcRenderCommandName", "/change_tracking/view_diff");
		}
		else {
			viewURL.setParameter(
				"mvcRenderCommandName", "/change_tracking/view_entry");
		}

		viewURL.setParameter(
			"ctEntryId", String.valueOf(ctEntry.getCtEntryId()));

		try {
			viewURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			ReflectionUtil.throwException(windowStateException);
		}

		return viewURL.toString();
	}

	private <T extends BaseModel<T>> String _getViewURL(
		RenderResponse renderResponse, long modelClassNameId,
		long modelClassPK) {

		RenderURL viewURL = renderResponse.createRenderURL();

		viewURL.setParameter(
			"mvcRenderCommandName", "/change_tracking/view_entry");
		viewURL.setParameter(
			"modelClassNameId", String.valueOf(modelClassNameId));
		viewURL.setParameter("modelClassPK", String.valueOf(modelClassPK));

		try {
			viewURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			ReflectionUtil.throwException(windowStateException);
		}

		return viewURL.toString();
	}

	private final long _activeCtCollectionId;
	private final CTCollection _ctCollection;
	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;
	private final CTEntryLocalService _ctEntryLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final int _resolvedConflicts;
	private final Map<String, Object> _resolvedConflictsReactData;
	private final ThemeDisplay _themeDisplay;
	private final int _unresolvedConflicts;
	private final Map<String, Object> _unresolvedConflictsReactData;

}