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

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.security.permission.resource.JournalArticlePermission;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalHistoryManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public JournalHistoryManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, JournalArticle article,
		JournalHistoryDisplayContext journalHistoryDisplayContext) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			journalHistoryDisplayContext.getArticleSearchContainer());

		_article = article;
		_journalHistoryDisplayContext = journalHistoryDisplayContext;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> JournalArticlePermission.contains(
							themeDisplay.getPermissionChecker(), _article,
							ActionKeys.EXPIRE),
						dropdownItem -> {
							dropdownItem.putData("action", "expireArticles");
							dropdownItem.putData(
								"expireArticlesURL",
								PortletURLBuilder.createActionURL(
									liferayPortletResponse
								).setActionName(
									"/journal/expire_articles"
								).setRedirect(
									themeDisplay.getURLCurrent()
								).buildString());
							dropdownItem.setIcon("time");
							dropdownItem.setLabel(
								LanguageUtil.get(httpServletRequest, "expire"));
							dropdownItem.setQuickAction(true);
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						() -> JournalArticlePermission.contains(
							themeDisplay.getPermissionChecker(), _article,
							ActionKeys.DELETE),
						dropdownItem -> {
							dropdownItem.putData("action", "deleteArticles");
							dropdownItem.putData(
								"deleteArticlesURL",
								PortletURLBuilder.createActionURL(
									liferayPortletResponse
								).setActionName(
									"/journal/delete_articles"
								).setRedirect(
									themeDisplay.getURLCurrent()
								).buildString());
							dropdownItem.setIcon("trash");
							dropdownItem.setLabel(
								LanguageUtil.get(httpServletRequest, "delete"));
							dropdownItem.setQuickAction(true);
						}
					).build());
				dropdownGroupItem.setSeparator(true);
			}
		).build();
	}

	public String getAvailableActions(JournalArticle article)
		throws PortalException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		List<String> availableActions = new ArrayList<>();

		if (JournalArticlePermission.contains(
				themeDisplay.getPermissionChecker(), article,
				ActionKeys.DELETE)) {

			availableActions.add("deleteArticles");
		}

		if (JournalArticlePermission.contains(
				themeDisplay.getPermissionChecker(), article,
				ActionKeys.EXPIRE) &&
			(article.getStatus() == WorkflowConstants.STATUS_APPROVED)) {

			availableActions.add("expireArticles");
		}

		return StringUtil.merge(availableActions, StringPool.COMMA);
	}

	@Override
	public String getComponentId() {
		return "journalHistoryManagementToolbar";
	}

	@Override
	public String getSearchContainerId() {
		return "articleVersions";
	}

	@Override
	protected String getDisplayStyle() {
		return _journalHistoryDisplayContext.getDisplayStyle();
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list", "descriptive", "icon"};
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"version", "display-date", "modified-date"};
	}

	private final JournalArticle _article;
	private final JournalHistoryDisplayContext _journalHistoryDisplayContext;

}