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

package com.liferay.journal.web.internal.display.context;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Eudaldo Alonso
 */
public class JournalPreviewArticleContentTemplateDisplayContext {

	public JournalPreviewArticleContentTemplateDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public JournalArticleDisplay getArticleDisplay() throws Exception {
		if (_articleDisplay != null) {
			return _articleDisplay;
		}

		JournalArticle article = JournalArticleLocalServiceUtil.fetchArticle(
			getGroupId(), getArticleId(), getVersion());

		if (article == null) {
			return _articleDisplay;
		}

		int page = ParamUtil.getInteger(_renderRequest, "page");

		_articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(
			article, getDDMTemplateKey(), null, _themeDisplay.getLanguageId(),
			page, new PortletRequestModel(_renderRequest, _renderResponse),
			_themeDisplay);

		return _articleDisplay;
	}

	public String getArticleId() {
		if (_articleId != null) {
			return _articleId;
		}

		_articleId = ParamUtil.getString(_renderRequest, "articleId");

		return _articleId;
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		_ddmTemplateKey = ParamUtil.getString(_renderRequest, "ddmTemplateKey");

		return _ddmTemplateKey;
	}

	public List<DDMTemplate> getDDMTemplates() throws PortalException {
		JournalArticle article = JournalArticleLocalServiceUtil.fetchArticle(
			getGroupId(), getArticleId(), getVersion());

		if (article == null) {
			return Collections.emptyList();
		}

		DDMStructure ddmStructure = article.getDDMStructure();

		if (ddmStructure == null) {
			return Collections.emptyList();
		}

		return DDMTemplateLocalServiceUtil.getTemplates(
			article.getGroupId(), PortalUtil.getClassNameId(DDMStructure.class),
			ddmStructure.getStructureId(), true);
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(_renderRequest, "groupId");

		return _groupId;
	}

	public PortletURL getPageIteratorPortletURL() throws Exception {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcPath", "/preview_article_content_template.jsp");
		portletURL.setParameter("groupId", String.valueOf(getGroupId()));
		portletURL.setParameter("articleId", getArticleId());
		portletURL.setParameter("version", String.valueOf(getVersion()));
		portletURL.setParameter("ddmTemplateKey", getDDMTemplateKey());
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL;
	}

	public PortletURL getPortletURL() throws Exception {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcPath", "/preview_article_content_template.jsp");
		portletURL.setParameter("groupId", String.valueOf(getGroupId()));
		portletURL.setParameter("articleId", getArticleId());
		portletURL.setParameter("version", String.valueOf(getVersion()));
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL;
	}

	public Double getVersion() {
		if (_version != null) {
			return _version;
		}

		_version = ParamUtil.getDouble(_renderRequest, "version");

		return _version;
	}

	private JournalArticleDisplay _articleDisplay;
	private String _articleId;
	private String _ddmTemplateKey;
	private Long _groupId;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;
	private Double _version;

}