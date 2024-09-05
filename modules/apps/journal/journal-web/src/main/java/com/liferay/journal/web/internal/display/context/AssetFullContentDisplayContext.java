/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class AssetFullContentDisplayContext {

	public AssetFullContentDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public JournalArticleDisplay getJournalArticleDisplay() {
		if (_journalArticleDisplay != null) {
			return _journalArticleDisplay;
		}

		_journalArticleDisplay =
			(JournalArticleDisplay)_httpServletRequest.getAttribute(
				WebKeys.JOURNAL_ARTICLE_DISPLAY);

		return _journalArticleDisplay;
	}

	public PortletURL getPaginationURL(String currentURL) {
		JournalArticleDisplay journalArticleDisplay =
			getJournalArticleDisplay();

		String pageRedirect = ParamUtil.getString(
			_httpServletRequest, "redirect");

		if (Validator.isNull(pageRedirect)) {
			pageRedirect = currentURL;
		}

		return PortletURLBuilder.create(
			PortalUtil.getControlPanelPortletURL(
				_httpServletRequest, JournalPortletKeys.JOURNAL,
				PortletRequest.RENDER_PHASE)
		).setMVCPath(
			"/view_content.jsp"
		).setRedirect(
			pageRedirect
		).setParameter(
			"cur", ParamUtil.getInteger(_httpServletRequest, "cur")
		).setParameter(
			"groupId", journalArticleDisplay.getGroupId()
		).setParameter(
			"type",
			() -> {
				AssetRendererFactory<?> assetRendererFactory =
					_getAssetRendererFactory();

				return assetRendererFactory.getType();
			}
		).setParameter(
			"urlTitle", journalArticleDisplay.getUrlTitle()
		).buildPortletURL();
	}

	private AssetRendererFactory<?> _getAssetRendererFactory() {
		if (_assetRendererFactory != null) {
			return _assetRendererFactory;
		}

		_assetRendererFactory =
			(AssetRendererFactory<?>)_httpServletRequest.getAttribute(
				WebKeys.ASSET_RENDERER_FACTORY);

		return _assetRendererFactory;
	}

	private AssetRendererFactory<?> _assetRendererFactory;
	private final HttpServletRequest _httpServletRequest;
	private JournalArticleDisplay _journalArticleDisplay;

}