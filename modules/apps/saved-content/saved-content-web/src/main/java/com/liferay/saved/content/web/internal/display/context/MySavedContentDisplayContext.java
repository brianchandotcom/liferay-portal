/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.web.internal.display.context;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.saved.content.constants.MySavedContentPortletKeys;
import com.liferay.saved.content.model.SavedContentEntry;
import com.liferay.saved.content.service.SavedContentEntryServiceUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alicia García
 */
public class MySavedContentDisplayContext {

	public MySavedContentDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);

		_renderResponse = (RenderResponse)_httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getAssetTitle(String className, long classPK) {
		try {
			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(className);

			if (assetRendererFactory == null) {
				return null;
			}

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(classPK);

			if (assetRenderer == null) {
				return null;
			}

			return HtmlUtil.escape(
				assetRenderer.getTitle(_themeDisplay.getLocale()));
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get asset renderer with class primary key " +
						classPK,
					portalException);
			}

			return null;
		}
	}

	public String getRemoveSavedContentURL(String className, long classPK) {
		return PortletURLBuilder.create(
			PortletURLFactoryUtil.create(
				_httpServletRequest, MySavedContentPortletKeys.MY_SAVED_CONTENT,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/saved_content/delete_saved_content_entry"
		).setRedirect(
			PortalUtil.getCurrentURL(_httpServletRequest)
		).setParameter(
			"className", className
		).setParameter(
			"classPK", classPK
		).buildString();
	}

	public SearchContainer<SavedContentEntry> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		SearchContainer<SavedContentEntry> searchContainer =
			new SearchContainer(
				_liferayPortletRequest, null, null, "curEntry",
				SearchContainer.DEFAULT_DELTA, _getPortletURL(), null,
				"no-saved-content-were-found");

		searchContainer.setResultsAndTotal(
			() -> SavedContentEntryServiceUtil.getGroupUserSavedContentEntries(
				_themeDisplay.getScopeGroupId(),
				searchContainer.getStart(), searchContainer.getEnd()),
			SavedContentEntryServiceUtil.getGroupUserSavedContentEntriesCount(
				_themeDisplay.getScopeGroupId()));

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	public String getURL(String className, long classPK) {
		String url = null;

		try {
			AssetRendererFactory<?> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(className);

			if (assetRendererFactory == null) {
				return null;
			}

			AssetRenderer<?> assetRenderer =
				assetRendererFactory.getAssetRenderer(classPK);

			if (assetRenderer == null) {
				return null;
			}

			url = assetRenderer.getURLViewInContext(
				_liferayPortletRequest, _liferayPortletResponse,
				_themeDisplay.getURLCurrent());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to get asset renderer with class primary key " +
						classPK,
					exception);
			}

			return url;
		}

		return url;
	}

	private PortletURL _getPortletURL() {
		if (_portletURL != null) {
			return _portletURL;
		}

		_portletURL = PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCRenderCommandName(
			"/saved_content/view_my_saved_content"
		).setRedirect(
			ParamUtil.getString(_httpServletRequest, "redirect")
		).buildPortletURL();

		return _portletURL;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MySavedContentDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private PortletURL _portletURL;
	private final RenderResponse _renderResponse;
	private SearchContainer<SavedContentEntry> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}