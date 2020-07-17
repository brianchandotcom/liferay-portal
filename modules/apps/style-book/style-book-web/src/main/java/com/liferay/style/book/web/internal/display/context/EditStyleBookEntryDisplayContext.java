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

package com.liferay.style.book.web.internal.display.context;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.util.GroupURLProvider;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class EditStyleBookEntryDisplayContext {

	public EditStyleBookEntryDisplayContext(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_groupURLProvider = (GroupURLProvider)_renderRequest.getAttribute(
			GroupURLProvider.class.getName());
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_setViewAttributes();
	}

	public Map<String, Object> getStyleBookEditorData() {
		return HashMapBuilder.<String, Object>put(
			"namespace", _renderResponse.getNamespace()
		).put(
			"previewURL",
			() -> {
				String layoutURL = _groupURLProvider.getGroupLayoutsURL(
					_themeDisplay.getScopeGroup(), false, _renderRequest);

				return HttpUtil.addParameter(
					layoutURL, "p_l_mode", Constants.PREVIEW);
			}
		).put(
			"publishURL",
			() -> {
				PortletURL actionURL = _renderResponse.createActionURL();

				actionURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/style_book/publish_style_book_entry");
				actionURL.setParameter(
					"styleBookEntryId", String.valueOf(_getStyleBookEntryId()));

				return actionURL.toString();
			}
		).put(
			"redirectURL", _getRedirect()
		).put(
			"saveDraftURL",
			() -> {
				PortletURL actionURL = _renderResponse.createActionURL();

				actionURL.setParameter(
					ActionRequest.ACTION_NAME,
					"/style_book/save_draft_style_book_entry");

				return actionURL.toString();
			}
		).put(
			"styleBookEntryId", _getStyleBookEntryId()
		).put(
			"tokensValues",
			() -> {
				StyleBookEntry styleBookEntry = _getStyleBookEntry();

				String tokensValues = styleBookEntry.getTokensValues();

				if (styleBookEntry.isHead()) {
					StyleBookEntry draftStyleBookEntry =
						StyleBookEntryLocalServiceUtil.getDraft(styleBookEntry);

					tokensValues = draftStyleBookEntry.getTokensValues();
				}

				return JSONFactoryUtil.createJSONObject(tokensValues);
			}
		).build();
	}

	private String _getRedirect() {
		String redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			return redirect;
		}

		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/style_book/view");

		return portletURL.toString();
	}

	private StyleBookEntry _getStyleBookEntry() {
		if (_styleBookEntry != null) {
			return _styleBookEntry;
		}

		_styleBookEntry = StyleBookEntryLocalServiceUtil.fetchStyleBookEntry(
			_getStyleBookEntryId());

		return _styleBookEntry;
	}

	private long _getStyleBookEntryId() {
		if (_styleBookEntryId != null) {
			return _styleBookEntryId;
		}

		_styleBookEntryId = ParamUtil.getLong(
			_httpServletRequest, "styleBookEntryId");

		return _styleBookEntryId;
	}

	private String _getStyleBookEntryTitle() {
		StyleBookEntry styleBookEntry = _getStyleBookEntry();

		return styleBookEntry.getName();
	}

	private void _setViewAttributes() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(_getRedirect());

		_renderResponse.setTitle(_getStyleBookEntryTitle());
	}

	private final GroupURLProvider _groupURLProvider;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private StyleBookEntry _styleBookEntry;
	private Long _styleBookEntryId;
	private final ThemeDisplay _themeDisplay;

}