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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Víctor Galán
 */
public class LayoutLookAndFeelDisplayContext {

	public LayoutLookAndFeelDisplayContext(
		HttpServletRequest httpServletRequest,
		LayoutsAdminDisplayContext layoutsAdminDisplayContext,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_layoutsAdminDisplayContext = layoutsAdminDisplayContext;
		_liferayPortletResponse = liferayPortletResponse;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getChangeMasterLayoutButtonAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"url",
			() -> PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCPath(
				"/select_master_layout.jsp"
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString()
		).build();
	}

	public Map<String, Object> getChangeStyleBookButtonAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"url",
			() -> PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCPath(
				"/select_style_book.jsp"
			).setParameter(
				"editableMasterLayout", hasEditableMasterLayout()
			).setParameter(
				"selPlid",
				() -> {
					Layout selLayout =
						_layoutsAdminDisplayContext.getSelLayout();

					return selLayout.getPlid();
				}
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString()
		).build();
	}

	public Map<String, Object> getEditMasterLayoutButtonAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"editableMasterLayout", hasEditableMasterLayout()
		).put(
			"editMasterLayoutURL",
			() -> {
				if (!hasMasterLayout()) {
					return StringPool.BLANK;
				}

				Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

				Layout masterLayout = LayoutLocalServiceUtil.getLayout(
					selLayout.getMasterLayoutPlid());

				String editLayoutURL = HttpUtil.addParameter(
					HttpUtil.addParameter(
						PortalUtil.getLayoutFullURL(selLayout, _themeDisplay),
						"p_l_mode", Constants.EDIT),
					"p_l_back_url",
					ParamUtil.getString(_httpServletRequest, "redirect"));

				return HttpUtil.addParameter(
					HttpUtil.addParameter(
						PortalUtil.getLayoutFullURL(
							masterLayout.fetchDraftLayout(), _themeDisplay),
						"p_l_mode", Constants.EDIT),
					"p_l_back_url", editLayoutURL);
			}
		).build();
	}

	public String getMasterLayoutName() {
		if (_masterLayoutName != null) {
			return _masterLayoutName;
		}

		_masterLayoutName = LanguageUtil.get(_httpServletRequest, "blank");
		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

		if (selLayout.getMasterLayoutPlid() > 0) {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					fetchLayoutPageTemplateEntryByPlid(
						selLayout.getMasterLayoutPlid());

			_masterLayoutName = layoutPageTemplateEntry.getName();
		}

		return _masterLayoutName;
	}

	public String getStyleBookEntryName() {
		if (_styleBookEntryName != null) {
			return _styleBookEntryName;
		}

		_styleBookEntryName = LanguageUtil.get(
			_httpServletRequest, "inherited");

		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

		if (hasStyleBooks() && (selLayout.getStyleBookEntryId() > 0)) {
			StyleBookEntry styleBookEntry =
				StyleBookEntryLocalServiceUtil.fetchStyleBookEntry(
					selLayout.getStyleBookEntryId());

			_styleBookEntryName = styleBookEntry.getName();
		}

		return _styleBookEntryName;
	}

	public boolean hasEditableMasterLayout() {
		if (_hasEditableMasterLayout != null) {
			return _hasEditableMasterLayout;
		}

		_hasEditableMasterLayout = false;

		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				fetchLayoutPageTemplateEntryByPlid(selLayout.getPlid());

		if (layoutPageTemplateEntry == null) {
			layoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					fetchLayoutPageTemplateEntryByPlid(selLayout.getClassPK());
		}

		if ((layoutPageTemplateEntry == null) ||
			!Objects.equals(
				layoutPageTemplateEntry.getType(),
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT)) {

			_hasEditableMasterLayout = true;
		}

		return _hasEditableMasterLayout;
	}

	public boolean hasMasterLayout() {
		if (_hasMasterLayout != null) {
			return _hasMasterLayout;
		}

		_hasMasterLayout = false;

		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

		if (selLayout.getMasterLayoutPlid() > 0) {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					fetchLayoutPageTemplateEntryByPlid(
						selLayout.getMasterLayoutPlid());

			if (layoutPageTemplateEntry != null) {
				_hasMasterLayout = true;
			}
		}

		return _hasMasterLayout;
	}

	public boolean hasStyleBooks() {
		if (_hasStyleBooks != null) {
			return _hasStyleBooks;
		}

		_hasStyleBooks = false;

		Group liveGroup = StagingUtil.getLiveGroup(
			_layoutsAdminDisplayContext.getGroup());

		int styleBookEntriesCount =
			StyleBookEntryLocalServiceUtil.getStyleBookEntriesCount(
				liveGroup.getGroupId());

		if (styleBookEntriesCount > 0) {
			_hasStyleBooks = true;
		}

		return _hasStyleBooks;
	}

	private Boolean _hasEditableMasterLayout;
	private Boolean _hasMasterLayout;
	private Boolean _hasStyleBooks;
	private final HttpServletRequest _httpServletRequest;
	private final LayoutsAdminDisplayContext _layoutsAdminDisplayContext;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _masterLayoutName;
	private String _styleBookEntryName;
	private final ThemeDisplay _themeDisplay;

}