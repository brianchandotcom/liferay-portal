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

import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.TableItemView;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.journal.web.internal.util.JournalArticleTranslation;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Barbara Cabrera
 */
public class JournalArticleTranslationsItemSelectorViewDescriptor
	implements ItemSelectorViewDescriptor<JournalArticleTranslation> {

	public JournalArticleTranslationsItemSelectorViewDescriptor(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	@Override
	public String getDefaultDisplayStyle() {
		return "list";
	}

	public String[] getDisplayViews() {
		return new String[] {"list"};
	}

	@Override
	public ItemDescriptor getItemDescriptor(
		JournalArticleTranslation journalArticleTranslation) {

		return new JournalArticleTranslationsItemDescriptor(
			journalArticleTranslation);
	}

	@Override
	public ItemSelectorReturnType getItemSelectorReturnType() {
		return new UUIDItemSelectorReturnType();
	}

	@Override
	public SearchContainer<JournalArticleTranslation> getSearchContainer()
		throws PortalException {

		return null;
	}

	public TableItemView getTableItemView(
		JournalArticleTranslation journalArticleTranslation) {

		return new JournalArticleTranslationsItemView(
			journalArticleTranslation);
	}

	@Override
	public boolean isMultipleSelection() {
		return true;
	}

	@Override
	public boolean isShowBreadcrumb() {
		return false;
	}

	@Override
	public boolean isShowSearch() {
		return true;
	}

	private final HttpServletRequest _httpServletRequest;

}