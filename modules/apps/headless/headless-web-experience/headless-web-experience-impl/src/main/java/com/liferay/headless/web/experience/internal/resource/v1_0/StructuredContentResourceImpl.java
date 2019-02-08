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

package com.liferay.headless.web.experience.internal.resource.v1_0;

import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.internal.search.SearchHelper;
import com.liferay.headless.web.experience.resource.v1_0.StructuredContentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.vulcan.context.Pagination;
import com.liferay.portal.vulcan.dto.Page;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/structured-content.properties",
	scope = ServiceScope.PROTOTYPE, service = StructuredContentResource.class
)
public class StructuredContentResourceImpl
	extends BaseStructuredContentResourceImpl {

	@Override
	public Page<StructuredContent> getContentSpaceStructuredContentsPage(
			Long contentSpaceId, Filter filter, String sort,
			Pagination pagination)
		throws Exception {

		List<JournalArticle> journalArticles = _searchHelper.getJournalArticles(
			_groupService.getGroup(contentSpaceId), pagination, filter);

		return Page.of(
			transform(journalArticles, this::_toStructuredContent), pagination,
			journalArticles.size());
	}

	private StructuredContent _toStructuredContent(
		JournalArticle journalArticle) {

		return new StructuredContent() {
			{
				setDateCreated(journalArticle.getCreateDate());
				setDateModified(journalArticle.getModifiedDate());
				setDatePublished(journalArticle.getDisplayDate());
				setDescription(
					journalArticle.getDescription(
						acceptLanguage.getPreferredLocale()));
				setId(journalArticle.getResourcePrimKey());
				setTitle(
					journalArticle.getTitle(
						acceptLanguage.getPreferredLocale()));
			}
		};
	}

	@Reference
	private GroupService _groupService;

	@Reference
	private SearchHelper _searchHelper;

}