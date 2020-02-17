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

package com.liferay.journal.web.internal.item.selector;

import com.liferay.info.item.selector.InfoItemSelectorView;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.PortletItemSelectorView;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.JournalArticleItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.configuration.JournalWebConfiguration;
import com.liferay.journal.web.internal.constants.JournalWebConstants;
import com.liferay.journal.web.internal.display.context.JournalArticleItemSelectorViewDisplayContext;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	configurationPid = "com.liferay.journal.web.internal.configuration.JournalWebConfiguration",
	property = "item.selector.view.order:Integer=100",
	service = ItemSelectorView.class
)
public class JournalArticleItemSelectorView
	implements InfoItemSelectorView,
			   PortletItemSelectorView<InfoItemItemSelectorCriterion> {

	@Override
	public String getClassName() {
		return JournalArticle.class.getName();
	}

	@Override
	public Class<InfoItemItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return InfoItemItemSelectorCriterion.class;
	}

	@Override
	public List<String> getPortletIds() {
		return Collections.singletonList(JournalPortletKeys.JOURNAL);
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "web-content");
	}

	@Override
	public boolean isVisible(ThemeDisplay themeDisplay) {
		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			InfoItemItemSelectorCriterion infoItemItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		JournalArticleItemSelectorViewDisplayContext
			journalItemSelectorViewDisplayContext =
				new JournalArticleItemSelectorViewDisplayContext(
					(HttpServletRequest)servletRequest,
					infoItemItemSelectorCriterion, itemSelectedEventName, this,
					_journalWebConfiguration, portletURL, search);

		servletRequest.setAttribute(
			JournalWebConstants.
				JOURNAL_ARTICLE_ITEM_SELECTOR_VIEW_DISPLAY_CONTEXT,
			journalItemSelectorViewDisplayContext);

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(
				"/item/selector/select_articles.jsp");

		requestDispatcher.include(servletRequest, servletResponse);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_journalWebConfiguration = ConfigurableUtil.createConfigurable(
			JournalWebConfiguration.class, properties);
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Arrays.asList(
			new InfoItemItemSelectorReturnType(),
			new JournalArticleItemSelectorReturnType());

	private volatile JournalWebConfiguration _journalWebConfiguration;

	@Reference
	private Language _language;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.journal.web)")
	private ServletContext _servletContext;

}