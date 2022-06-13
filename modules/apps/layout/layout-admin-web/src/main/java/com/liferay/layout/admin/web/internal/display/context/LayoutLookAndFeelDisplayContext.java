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

import com.liferay.client.extension.constants.ClientExtensionEntryConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalServiceUtil;
import com.liferay.style.book.util.DefaultStyleBookEntryUtil;

import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletURL;

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

	public Map<String, Object> getChangeFaviconButtonAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"url", _layoutsAdminDisplayContext.getFileEntryItemSelectorURL()
		).build();
	}

	public Map<String, Object> getClearFaviconButtonAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"faviconTitleValue", _getClearFaviconButtonTitle()
		).build();
	}

	public String getFaviconTitle() {
		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

		if (selLayout.getFaviconFileEntryId() > 0) {
			try {
				FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
					selLayout.getFaviconFileEntryId());

				return fileEntry.getTitle();
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException);
				}
			}
		}

		if (hasEditableMasterLayout() &&
			(selLayout.getMasterLayoutPlid() > 0)) {

			Layout masterLayout = LayoutLocalServiceUtil.fetchLayout(
				selLayout.getMasterLayoutPlid());

			if ((masterLayout != null) &&
				(masterLayout.getFaviconFileEntryId() > 0)) {

				return LanguageUtil.get(
					_httpServletRequest, "favicon-from-master");
			}
		}

		LayoutSet layoutSet = selLayout.getLayoutSet();

		if (layoutSet.getFaviconFileEntryId() > 0) {
			return LanguageUtil.format(
				_themeDisplay.getLocale(), "favicon-from-x",
				_layoutsAdminDisplayContext.getRootNodeName());
		}

		return LanguageUtil.get(_httpServletRequest, "favicon-from-theme");
	}

	public String getFaviconURL() {
		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

		String faviconImage = selLayout.getFaviconURL();

		if (faviconImage != null) {
			return faviconImage;
		}

		Theme theme = null;

		try {
			theme = selLayout.getTheme();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return StringPool.BLANK;
		}

		return _layoutsAdminDisplayContext.getThemeFavicon(theme);
	}

	public Map<String, Object> getGlobalCSSCETsConfigurationProps() {
		return HashMapBuilder.<String, Object>put(
			"globalCSSCETs", JSONFactoryUtil.createJSONArray()
		).put(
			"globalCSSCETSelectorURL",
			() -> {
				PortletURL cetItemSelectorURL =
					_layoutsAdminDisplayContext.getCETItemSelectorURL(
						"selectGlobalCSSCETs",
						ClientExtensionEntryConstants.TYPE_GLOBAL_CSS);

				return PortletURLBuilder.create(
					cetItemSelectorURL
				).setParameter(
					"multipleSelection", true
				).buildString();
			}
		).put(
			"selectGlobalCSSCETsEventName", "selectGlobalCSSCETs"
		).build();
	}

	public Map<String, Object> getGlobalJSCETsConfigurationProps() {
		return HashMapBuilder.<String, Object>put(
			"globalJSCETs", JSONFactoryUtil.createJSONArray()
		).put(
			"globalJSCETSelectorURL",
			() -> {
				PortletURL cetItemSelectorURL =
					_layoutsAdminDisplayContext.getCETItemSelectorURL(
						"selectGlobalJSCETs",
						ClientExtensionEntryConstants.TYPE_GLOBAL_JS);

				return PortletURLBuilder.create(
					cetItemSelectorURL
				).setParameter(
					"multipleSelection", true
				).buildString();
			}
		).put(
			"selectGlobalJSCETsEventName", "selectGlobalJSCETs"
		).build();
	}

	public Map<String, Object> getMasterLayoutConfigurationProps() {
		return HashMapBuilder.<String, Object>put(
			"changeMasterLayoutURL",
			PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setMVCPath(
				"/select_master_layout.jsp"
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString()
		).put(
			"editMasterLayoutURL",
			() -> {
				if (!hasMasterLayout()) {
					return StringPool.BLANK;
				}

				Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

				Layout masterLayout = LayoutLocalServiceUtil.getLayout(
					selLayout.getMasterLayoutPlid());

				String editLayoutURL = HttpComponentsUtil.addParameter(
					HttpComponentsUtil.addParameter(
						PortalUtil.getLayoutFullURL(selLayout, _themeDisplay),
						"p_l_mode", Constants.EDIT),
					"p_l_back_url",
					ParamUtil.getString(_httpServletRequest, "redirect"));

				return HttpComponentsUtil.addParameter(
					HttpComponentsUtil.addParameter(
						PortalUtil.getLayoutFullURL(
							masterLayout.fetchDraftLayout(), _themeDisplay),
						"p_l_mode", Constants.EDIT),
					"p_l_back_url", editLayoutURL);
			}
		).put(
			"masterLayoutName", getMasterLayoutName()
		).put(
			"masterLayoutPlid",
			() -> {
				if (hasMasterLayout()) {
					Layout selLayout =
						_layoutsAdminDisplayContext.getSelLayout();

					return String.valueOf(selLayout.getMasterLayoutPlid());
				}

				return StringPool.BLANK;
			}
		).build();
	}

	public String getMasterLayoutName() {
		if (_masterLayoutName != null) {
			return _masterLayoutName;
		}

		String masterLayoutName = LanguageUtil.get(
			_httpServletRequest, "blank");

		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

		if (selLayout.getMasterLayoutPlid() > 0) {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					fetchLayoutPageTemplateEntryByPlid(
						selLayout.getMasterLayoutPlid());

			if (layoutPageTemplateEntry != null) {
				masterLayoutName = layoutPageTemplateEntry.getName();
			}
		}

		_masterLayoutName = masterLayoutName;

		return _masterLayoutName;
	}

	public Map<String, Object> getStyleBookConfigurationProps() {
		return HashMapBuilder.<String, Object>put(
			"changeStyleBookURL",
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
		).put(
			"styleBookEntryId",
			() -> {
				Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

				return String.valueOf(selLayout.getStyleBookEntryId());
			}
		).put(
			"styleBookEntryName", getStyleBookEntryName()
		).build();
	}

	public String getStyleBookEntryName() {
		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

		StyleBookEntry defaultStyleBookEntry =
			DefaultStyleBookEntryUtil.getDefaultStyleBookEntry(selLayout);

		if (selLayout.getStyleBookEntryId() > 0) {
			return defaultStyleBookEntry.getName();
		}

		if (defaultStyleBookEntry == null) {
			return LanguageUtil.get(_httpServletRequest, "styles-from-theme");
		}

		if (hasEditableMasterLayout() &&
			(selLayout.getMasterLayoutPlid() > 0)) {

			return LanguageUtil.get(_httpServletRequest, "styles-from-master");
		}

		return LanguageUtil.get(_httpServletRequest, "styles-by-default");
	}

	public boolean hasEditableMasterLayout() {
		if (_hasEditableMasterLayout != null) {
			return _hasEditableMasterLayout;
		}

		boolean hasEditableMasterLayout = false;

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

			hasEditableMasterLayout = true;
		}

		_hasEditableMasterLayout = hasEditableMasterLayout;

		return _hasEditableMasterLayout;
	}

	public boolean hasMasterLayout() {
		if (_hasMasterLayout != null) {
			return _hasMasterLayout;
		}

		boolean hasMasterLayout = false;

		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

		if (selLayout.getMasterLayoutPlid() > 0) {
			LayoutPageTemplateEntry layoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					fetchLayoutPageTemplateEntryByPlid(
						selLayout.getMasterLayoutPlid());

			if (layoutPageTemplateEntry != null) {
				hasMasterLayout = true;
			}
		}

		_hasMasterLayout = hasMasterLayout;

		return _hasMasterLayout;
	}

	public boolean hasStyleBooks() {
		if (_hasStyleBooks != null) {
			return _hasStyleBooks;
		}

		boolean hasStyleBooks = false;

		Group liveGroup = StagingUtil.getLiveGroup(
			_layoutsAdminDisplayContext.getGroup());

		int styleBookEntriesCount =
			StyleBookEntryLocalServiceUtil.getStyleBookEntriesCount(
				liveGroup.getGroupId());

		if (styleBookEntriesCount > 0) {
			hasStyleBooks = true;
		}

		_hasStyleBooks = hasStyleBooks;

		return _hasStyleBooks;
	}

	public boolean isClearFaviconButtonEnabled() {
		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

		if (selLayout.getFaviconFileEntryId() > 0) {
			return true;
		}

		return false;
	}

	private String _getClearFaviconButtonTitle() {
		Layout selLayout = _layoutsAdminDisplayContext.getSelLayout();

		if (hasEditableMasterLayout() &&
			(selLayout.getMasterLayoutPlid() > 0)) {

			Layout masterLayout = LayoutLocalServiceUtil.fetchLayout(
				selLayout.getMasterLayoutPlid());

			if ((masterLayout != null) &&
				(masterLayout.getFaviconFileEntryId() > 0)) {

				return LanguageUtil.get(
					_httpServletRequest, "favicon-from-master");
			}
		}

		LayoutSet layoutSet = selLayout.getLayoutSet();

		if (layoutSet.getFaviconFileEntryId() > 0) {
			return LanguageUtil.format(
				_themeDisplay.getLocale(), "favicon-from-x",
				_layoutsAdminDisplayContext.getRootNodeName());
		}

		return LanguageUtil.get(_httpServletRequest, "favicon-from-theme");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutLookAndFeelDisplayContext.class);

	private Boolean _hasEditableMasterLayout;
	private Boolean _hasMasterLayout;
	private Boolean _hasStyleBooks;
	private final HttpServletRequest _httpServletRequest;
	private final LayoutsAdminDisplayContext _layoutsAdminDisplayContext;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _masterLayoutName;
	private final ThemeDisplay _themeDisplay;

}