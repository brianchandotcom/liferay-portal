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
import com.liferay.learn.LearnMessage;
import com.liferay.learn.LearnMessageUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.time.Instant;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class ViewConflictsDisplayContext {

	public ViewConflictsDisplayContext(
		long activeCtCollectionId,
		Map<Long, List<ConflictInfo>> conflictInfoMap,
		CTCollection ctCollection,
		CTDisplayRendererRegistry ctDisplayRendererRegistry,
		CTEntryLocalService ctEntryLocalService, Language language,
		Portal portal, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_activeCtCollectionId = activeCtCollectionId;
		_conflictInfoMap = conflictInfoMap;
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
	}

	public CTCollection getCtCollection() {
		return _ctCollection;
	}

	public Map<String, Object> getReactData() {
		JSONArray resolvedConflictsJSONArray =
			JSONFactoryUtil.createJSONArray();
		JSONArray unresolvedConflictsJSONArray =
			JSONFactoryUtil.createJSONArray();

		for (Map.Entry<Long, List<ConflictInfo>> entry :
				_conflictInfoMap.entrySet()) {

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

		return HashMapBuilder.<String, Object>put(
			"learnLink",
			() -> {
				LearnMessage learnMessage = LearnMessageUtil.getLearnMessage(
					"manually-resolving-conflicts",
					_themeDisplay.getLanguageId(), "change-tracking-web");

				return JSONUtil.put(
					"message", learnMessage.getMessage()
				).put(
					"url", learnMessage.getURL()
				);
			}
		).put(
			"publishURL",
			() -> PortletURLBuilder.createActionURL(
				_renderResponse
			).setActionName(
				"/change_tracking/publish_ct_collection"
			).setParameter(
				"ctCollectionId", _ctCollection.getCtCollectionId()
			).setParameter(
				"name", _ctCollection.getName()
			).buildString()
		).put(
			"redirect", getRedirect()
		).put(
			"resolvedConflicts", resolvedConflictsJSONArray
		).put(
			"schedule", ParamUtil.getBoolean(_renderRequest, "schedule")
		).put(
			"scheduleURL",
			() -> PortletURLBuilder.createActionURL(
				_renderResponse
			).setActionName(
				"/change_tracking/schedule_publication"
			).setRedirect(
				getRedirect()
			).setParameter(
				"ctCollectionId", _ctCollection.getCtCollectionId()
			).buildString()
		).put(
			"spritemap", _themeDisplay.getPathThemeImages() + "/clay/icons.svg"
		).put(
			"timeZone",
			() -> {
				TimeZone timeZone = _themeDisplay.getTimeZone();

				if (Objects.equals(timeZone.getID(), StringPool.UTC)) {
					return "GMT";
				}

				Instant instant = Instant.now();

				return "GMT" +
					String.format("%tz", instant.atZone(timeZone.toZoneId()));
			}
		).put(
			"unresolvedConflicts", unresolvedConflictsJSONArray
		).build();
	}

	public String getRedirect() {
		String redirect = ParamUtil.getString(_renderRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			return redirect;
		}

		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/change_tracking/view_changes"
		).setParameter(
			"ctCollectionId", _ctCollection.getCtCollectionId()
		).buildString();
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
		).put(
			"dismissURL",
			() -> {
				if (!conflictInfo.isResolved()) {
					return null;
				}

				return PortletURLBuilder.createActionURL(
					_renderResponse
				).setActionName(
					"/change_tracking/delete_ct_auto_resolution_info"
				).setRedirect(
					_portal.getCurrentURL(_renderRequest)
				).setParameter(
					"ctAutoResolutionInfoId",
					conflictInfo.getCTAutoResolutionInfoId()
				).buildString();
			}
		);

		ResourceURL dataURL = _renderResponse.createResourceURL();

		dataURL.setResourceID("/change_tracking/get_entry_render_data");

		CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
			_ctCollection.getCtCollectionId(), modelClassNameId,
			conflictInfo.getSourcePrimaryKey());

		if (ctEntry != null) {
			dataURL.setParameter(
				"ctEntryId", String.valueOf(ctEntry.getCtEntryId()));

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

				actionsJSONArray.put(
					JSONUtil.put(
						"href",
						PortletURLBuilder.createRenderURL(
							_renderResponse
						).setMVCRenderCommandName(
							"/change_tracking/view_discard"
						).setRedirect(
							_portal.getCurrentURL(_renderRequest)
						).setParameter(
							"ctCollectionId", ctEntry.getCtCollectionId()
						).setParameter(
							"modelClassNameId", ctEntry.getModelClassNameId()
						).setParameter(
							"modelClassPK", ctEntry.getModelClassPK()
						).buildString()
					).put(
						"label",
						_language.get(_httpServletRequest, "discard-change")
					).put(
						"symbol", "times-circle"
					));

				jsonObject.put("actions", actionsJSONArray);
			}
		}
		else {
			dataURL.setParameter(
				"modelClassNameId", String.valueOf(modelClassNameId));
			dataURL.setParameter(
				"modelClassPK",
				String.valueOf(conflictInfo.getTargetPrimaryKey()));

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
			);
		}

		jsonObject.put("dataURL", dataURL.toString());

		return jsonObject;
	}

	private final long _activeCtCollectionId;
	private final Map<Long, List<ConflictInfo>> _conflictInfoMap;
	private final CTCollection _ctCollection;
	private final CTDisplayRendererRegistry _ctDisplayRendererRegistry;
	private final CTEntryLocalService _ctEntryLocalService;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}