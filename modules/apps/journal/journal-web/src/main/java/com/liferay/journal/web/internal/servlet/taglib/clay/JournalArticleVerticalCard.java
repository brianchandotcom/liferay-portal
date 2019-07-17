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

package com.liferay.journal.web.internal.servlet.taglib.clay;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseVerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.constants.JournalWebConstants;
import com.liferay.journal.web.internal.security.permission.resource.JournalArticlePermission;
import com.liferay.journal.web.internal.servlet.taglib.util.JournalArticleActionDropdownItemsProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.TrashHelper;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleVerticalCard extends BaseVerticalCard {

	public JournalArticleVerticalCard(
		BaseModel<?> baseModel, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker,
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		TrashHelper trashHelper) {

		super(baseModel, renderRequest, rowChecker);

		_renderResponse = renderResponse;
		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_trashHelper = trashHelper;

		_article = (JournalArticle)baseModel;
		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		JournalArticleActionDropdownItemsProvider
			articleActionDropdownItemsProvider =
				new JournalArticleActionDropdownItemsProvider(
					_article,
					PortalUtil.getLiferayPortletRequest(renderRequest),
					PortalUtil.getLiferayPortletResponse(_renderResponse),
					_assetDisplayPageFriendlyURLProvider, _trashHelper);

		try {
			return articleActionDropdownItemsProvider.getActionDropdownItems();
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getDefaultEventHandler() {
		return JournalWebConstants.JOURNAL_ELEMENTS_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getHref() {
		try {
			if (!JournalArticlePermission.contains(
					themeDisplay.getPermissionChecker(), _article,
					ActionKeys.UPDATE)) {

				return StringPool.BLANK;
			}

			String referringPortletResource = ParamUtil.getString(
				_httpServletRequest, "referringPortletResource");

			PortletURL editArticleURL = _renderResponse.createRenderURL();

			editArticleURL.setParameter("mvcPath", "/edit_article.jsp");
			editArticleURL.setParameter(
				"redirect", themeDisplay.getURLCurrent());
			editArticleURL.setParameter(
				"referringPortletResource", referringPortletResource);
			editArticleURL.setParameter(
				"groupId", String.valueOf(_article.getGroupId()));
			editArticleURL.setParameter(
				"folderId", String.valueOf(_article.getFolderId()));
			editArticleURL.setParameter("articleId", _article.getArticleId());
			editArticleURL.setParameter(
				"version", String.valueOf(_article.getVersion()));

			return editArticleURL.toString();
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getIcon() {
		return "web-content";
	}

	@Override
	public String getImageSrc() {
		return HtmlUtil.escape(_article.getArticleImageURL(themeDisplay));
	}

	@Override
	public String getInputName() {
		return rowChecker.getRowIds() + JournalArticle.class.getSimpleName();
	}

	@Override
	public String getInputValue() {
		return HtmlUtil.escape(_article.getArticleId());
	}

	@Override
	public List<LabelItem> getLabels() {
		return new LabelItemList() {
			{
				if (!_article.isApproved() && _article.hasApprovedVersion()) {
					add(
						labelItem -> labelItem.setStatus(
							WorkflowConstants.STATUS_APPROVED));
				}

				add(labelItem -> labelItem.setStatus(_article.getStatus()));
			}
		};
	}

	@Override
	public String getSubtitle() {
		Date createDate = _article.getModifiedDate();

		String modifiedDateDescription = LanguageUtil.getTimeDescription(
			_httpServletRequest,
			System.currentTimeMillis() - createDate.getTime(), true);

		return LanguageUtil.format(
			_httpServletRequest, "modified-x-ago", modifiedDateDescription);
	}

	@Override
	public String getTitle() {
		String title = _article.getTitle(themeDisplay.getLocale());

		if (Validator.isNotNull(title)) {
			return HtmlUtil.escape(title);
		}

		Locale defaultLanguage = LocaleUtil.fromLanguageId(
			_article.getDefaultLanguageId());

		return HtmlUtil.escape(_article.getTitle(defaultLanguage));
	}

	private final JournalArticle _article;
	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private final HttpServletRequest _httpServletRequest;
	private final RenderResponse _renderResponse;
	private final TrashHelper _trashHelper;

}