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

package com.liferay.structured.content.apio.architect.controller;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.structured.content.apio.architect.form.StructuredContentCreatorForm;
import com.liferay.structured.content.apio.architect.form.StructuredContentUpdaterForm;
import com.liferay.structured.content.apio.architect.model.JournalArticleWrapper;

/**
 * Public API for the Structured Content Controller.
 *
 * @author Cristina González
 */
public interface StructuredContentController {

	public JournalArticleWrapper addJournalArticle(
			long contentSpaceId,
			StructuredContentCreatorForm structuredContentCreatorForm,
			ThemeDisplay themeDisplay)
		throws PortalException;

	public void deleteJournalArticle(long journalArticleId)
		throws PortalException;

	public PageItems<JournalArticleWrapper> getPageItems(
			Pagination pagination, long contentSpaceId,
			ThemeDisplay themeDisplay)
		throws PortalException;

	public JournalArticleWrapper updateJournalArticle(
			long journalArticleId,
			StructuredContentUpdaterForm structuredContentUpdaterForm,
			ThemeDisplay themeDisplay)
		throws PortalException;

}