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

package com.liferay.journal.content.web.portlet.configuration.icon;

import com.liferay.journal.content.web.configuration.JournalContentPortletInstanceConfiguration;
import com.liferay.journal.content.web.constants.JournalContentPortletKeys;
import com.liferay.journal.content.web.display.context.JournalContentDisplayContext;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseFactory;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Pavel Savinov
 */
public class EditJournalArticlePortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public EditJournalArticlePortletConfigurationIcon(
		PortletRequest portletRequest) {

		super(portletRequest);

		createJournalContentDisplayContext(portletRequest);
	}

	@Override
	public String getMessage() {
		return "edit-web-content";
	}

	@Override
	public String getOnClick() {
		StringBundler sb = new StringBundler(14);

		JournalArticle article = null;

		try {
			article = _journalContentDisplayContext.getArticle();
		}
		catch (Exception e) {
			_log.error("Unable to get current article", e);
		}

		if (article == null) {
			return "";
		}

		sb.append("Liferay.Util.openWindow({bodyCssClass: ");
		sb.append("'dialog-with-footer', destroyOnHide: true, id: '");
		sb.append(HtmlUtil.escape(portletDisplay.getNamespace()));
		sb.append("editAsset', namespace: '");
		sb.append(portletDisplay.getNamespace());
		sb.append("', portlet: '#p_p_id_");
		sb.append(portletDisplay.getId());
		sb.append("_', portletId: '");
		sb.append(portletDisplay.getId());
		sb.append("', title: '");
		sb.append(article.getTitle(themeDisplay.getLocale()));
		sb.append("', uri: '");
		sb.append(HtmlUtil.escapeJS(getURL()));
		sb.append("'}); return false;");

		return sb.toString();
	}

	@Override
	public String getURL() {
		AssetRendererFactory<JournalArticle> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				JournalArticle.class);

		PortletURL redirectURL = PortletURLFactoryUtil.create(
			portletRequest, JournalContentPortletKeys.JOURNAL_CONTENT,
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		redirectURL.setParameter(
			"mvcPath", "/update_journal_article_redirect.jsp");
		redirectURL.setParameter(
			"referringPortletResource", portletDisplay.getId());

		PortletURL portletURL = null;

		try {
			JournalArticle article = _journalContentDisplayContext.getArticle();

			AssetRenderer<JournalArticle> latestArticleAssetRenderer =
				assetRendererFactory.getAssetRenderer(
					article.getResourcePrimKey());

			portletURL = latestArticleAssetRenderer.getURLEdit(
				(LiferayPortletRequest)portletRequest, null,
				LiferayWindowState.POP_UP, redirectURL);

			return portletURL.toString();
		}
		catch (Exception e) {
			_log.error("Unable to create portlet URL", e);
		}

		return "";
	}

	@Override
	public boolean isShow() {
		try {
			if (!_journalContentDisplayContext.isShowEditArticleIcon()) {
				return false;
			}

			JournalArticle article = _journalContentDisplayContext.getArticle();

			if ((article != null) && !article.isNew()) {
				return true;
			}
		}
		catch (Exception e) {
		}

		return false;
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

	protected void createJournalContentDisplayContext(
		PortletRequest portletRequest) {

		try {
			PortletResponse portletResponse = RenderResponseFactory.create(
				(RenderRequestImpl)portletRequest, themeDisplay.getResponse(),
				portletDisplay.getPortletName(), themeDisplay.getCompanyId());

			JournalContentPortletInstanceConfiguration
				journalContentPortletInstanceConfiguration =
					portletDisplay.getPortletInstanceConfiguration(
						JournalContentPortletInstanceConfiguration.class);

			_journalContentDisplayContext = new JournalContentDisplayContext(
				portletRequest, portletResponse,
				journalContentPortletInstanceConfiguration);
		}
		catch (Exception e) {
			_log.error("Unable to create display context", e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditJournalArticlePortletConfigurationIcon.class);

	private JournalContentDisplayContext _journalContentDisplayContext;

}