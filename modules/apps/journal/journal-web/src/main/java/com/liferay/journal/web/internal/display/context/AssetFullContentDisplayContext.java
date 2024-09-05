/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class AssetFullContentDisplayContext {

	public AssetFullContentDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_currentURLObj = PortletURLUtil.getCurrent(
			liferayPortletRequest, liferayPortletResponse);
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

	public PortletURL getPaginationURL() {
		JournalArticleDisplay journalArticleDisplay =
			getJournalArticleDisplay();

		String pageRedirect = ParamUtil.getString(
			_httpServletRequest, "redirect");

		if (Validator.isNull(pageRedirect)) {
			pageRedirect = _currentURLObj.toString();
		}

		return PortletURLBuilder.create(
			_getPortletURL()
		).setMVCPath(
			"/view_content.jsp"
		).setRedirect(
			pageRedirect
		).setParameter(
			"cur", ParamUtil.getInteger(_httpServletRequest, "cur")
		).setParameter(
			"groupId", journalArticleDisplay.getGroupId()
		).setParameter(
			"page", (String)null
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

	private PortletURL _getPortletURL() {
		try {
			return PortletURLUtil.clone(
				_currentURLObj, _liferayPortletResponse);
		}
		catch (PortletException portletException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portletException);
			}

			return PortletURLBuilder.createRenderURL(
				_liferayPortletResponse
			).setParameters(
				_currentURLObj.getParameterMap()
			).buildPortletURL();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetFullContentDisplayContext.class);

	private AssetRendererFactory<?> _assetRendererFactory;
	private final PortletURL _currentURLObj;
	private final HttpServletRequest _httpServletRequest;
	private JournalArticleDisplay _journalArticleDisplay;
	private final LiferayPortletResponse _liferayPortletResponse;

}