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

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Samuel Trong Tran
 */
public class ChangeTrackingIndicatorDisplayContext {

	public ChangeTrackingIndicatorDisplayContext(
		HttpServletRequest httpServletRequest,
		CTCollectionLocalService ctCollectionLocalService,
		CTEntryLocalService ctEntryLocalService,
		CTPreferencesLocalService ctPreferencesLocalService, Html html,
		Language language, Portal portal) {

		_httpServletRequest = httpServletRequest;
		_ctCollectionLocalService = ctCollectionLocalService;
		_ctEntryLocalService = ctEntryLocalService;
		_ctPreferencesLocalService = ctPreferencesLocalService;
		_html = html;
		_language = language;
		_portal = portal;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_resourceBundle = ResourceBundleUtil.getBundle(
			_themeDisplay.getLocale(),
			ChangeTrackingIndicatorDisplayContext.class);

		_ctPreferences = _ctPreferencesLocalService.fetchCTPreferences(
			_themeDisplay.getCompanyId(), _themeDisplay.getUserId());

		long ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;

		if (_ctPreferences != null) {
			ctCollectionId = _ctPreferences.getCtCollectionId();
		}

		_ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);
	}

	public Map<String, Object> getReactData() {
		PortletURL checkoutURL = _portal.getControlPanelPortletURL(
			_httpServletRequest, _themeDisplay.getScopeGroup(),
			CTPortletKeys.CHANGE_LISTS, 0, 0, PortletRequest.ACTION_PHASE);

		checkoutURL.setParameter(
			ActionRequest.ACTION_NAME, "/change_lists/checkout_ct_collection");
		checkoutURL.setParameter(
			"redirect", _portal.getCurrentURL(_httpServletRequest));

		PortletURL selectURL = _portal.getControlPanelPortletURL(
			_httpServletRequest, _themeDisplay.getScopeGroup(),
			CTPortletKeys.CHANGE_LISTS, 0, 0, PortletRequest.RENDER_PHASE);

		selectURL.setParameter(
			"mvcPath", "/change_lists/select_change_list.jsp");

		try {
			selectURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			return ReflectionUtil.throwException(windowStateException);
		}

		Map<String, Object> data = HashMapBuilder.<String, Object>put(
			"checkoutURL", checkoutURL.toString()
		).put(
			"namespace", _portal.getPortletNamespace(CTPortletKeys.CHANGE_LISTS)
		).put(
			"selectURL", selectURL.toString()
		).build();

		if (_ctCollection != null) {
			data.put("iconClass", "change-tracking-indicator-icon-change-list");
			data.put("iconName", "radio-button");
			data.put("title", _ctCollection.getName());
		}
		else {
			data.put("iconClass", "change-tracking-indicator-icon-production");
			data.put("iconName", "simple-circle");

			data.put("title", _language.get(_resourceBundle, "production"));
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		if (_ctPreferences != null) {
			long ctCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;

			if (_ctCollection != null) {
				ctCollectionId = _ctCollection.getCtCollectionId();
			}

			long previousCtCollectionId =
				_ctPreferences.getPreviousCtCollectionId();

			if (previousCtCollectionId != ctCollectionId) {
				if (previousCtCollectionId ==
						CTConstants.CT_COLLECTION_ID_PRODUCTION) {

					checkoutURL.setParameter(
						"ctCollectionId", String.valueOf(0L));

					jsonArray.put(
						JSONUtil.put(
							"href", checkoutURL.toString()
						).put(
							"label",
							_language.get(_resourceBundle, "work-on-production")
						).put(
							"symbolLeft", "simple-circle"
						));
				}
				else {
					CTCollection previousCtCollection =
						_ctCollectionLocalService.fetchCTCollection(
							previousCtCollectionId);

					if (previousCtCollection != null) {
						checkoutURL.setParameter(
							"ctCollectionId",
							String.valueOf(previousCtCollectionId));

						jsonArray.put(
							JSONUtil.put(
								"href", checkoutURL.toString()
							).put(
								"label",
								_language.format(
									_resourceBundle, "work-on-x",
									previousCtCollection.getName(), false)
							).put(
								"symbolLeft", "radio-button"
							));
					}
				}
			}
		}

		jsonArray.put(
			JSONUtil.put(
				"href",
				StringBundler.concat(
					"javascript:Liferay.fire('",
					_portal.getPortletNamespace(CTPortletKeys.CHANGE_LISTS),
					"openDialog', {});")
			).put(
				"label", _language.get(_resourceBundle, "select-a-publication")
			).put(
				"symbolLeft", "cards2"
			));

		PortletURL addURL = _portal.getControlPanelPortletURL(
			_httpServletRequest, _themeDisplay.getScopeGroup(),
			CTPortletKeys.CHANGE_LISTS, 0, 0, PortletRequest.RENDER_PHASE);

		PortletURL overviewURL = _portal.getControlPanelPortletURL(
			_httpServletRequest, _themeDisplay.getScopeGroup(),
			CTPortletKeys.CHANGE_LISTS, 0, 0, PortletRequest.RENDER_PHASE);

		addURL.setParameter("backURL", overviewURL.toString());

		addURL.setParameter(
			"mvcRenderCommandName", "/change_lists/add_ct_collection");

		jsonArray.put(
			JSONUtil.put(
				"href", addURL.toString()
			).put(
				"label",
				_language.get(_resourceBundle, "create-new-publication")
			).put(
				"symbolLeft", "plus"
			));

		if (_ctCollection != null) {
			jsonArray.put(
				JSONUtil.put("type", "divider")
			).put(
				JSONUtil.put(
					"href", overviewURL.toString()
				).put(
					"label", _language.get(_resourceBundle, "review-changes")
				).put(
					"symbolLeft", "list-ul"
				)
			);

			int count = _ctEntryLocalService.getCTCollectionCTEntriesCount(
				_ctCollection.getCtCollectionId());

			if (count > 0) {
				jsonArray.put(JSONUtil.put("type", "divider"));

				PortletURL publishURL = _portal.getControlPanelPortletURL(
					_httpServletRequest, _themeDisplay.getScopeGroup(),
					CTPortletKeys.CHANGE_LISTS, 0, 0,
					PortletRequest.ACTION_PHASE);

				publishURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/change_lists/publish_ct_collection");

				publishURL.setParameter(
					"ctCollectionId",
					String.valueOf(_ctCollection.getCtCollectionId()));
				publishURL.setParameter("name", _ctCollection.getName());

				String href = StringBundler.concat(
					"javascript:confirm('",
					_html.escapeJS(
						_language.format(
							_resourceBundle,
							"are-you-sure-you-want-to-publish-x-change-list",
							_ctCollection.getName(), false)),
					"') && Liferay.Util.navigate('",
					_html.escapeJS(publishURL.toString()), "')");

				jsonArray.put(
					JSONUtil.put(
						"href", href
					).put(
						"label", _language.get(_resourceBundle, "publish")
					).put(
						"symbolLeft", "upload"
					));
			}
		}

		data.put("items", jsonArray);

		return data;
	}

	private final CTCollection _ctCollection;
	private final CTCollectionLocalService _ctCollectionLocalService;
	private final CTEntryLocalService _ctEntryLocalService;
	private final CTPreferences _ctPreferences;
	private final CTPreferencesLocalService _ctPreferencesLocalService;
	private final Html _html;
	private final HttpServletRequest _httpServletRequest;
	private final Language _language;
	private final Portal _portal;
	private final ResourceBundle _resourceBundle;
	private final ThemeDisplay _themeDisplay;

}