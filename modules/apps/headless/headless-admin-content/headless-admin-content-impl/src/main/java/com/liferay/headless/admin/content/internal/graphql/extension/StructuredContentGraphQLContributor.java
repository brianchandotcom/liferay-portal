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

package com.liferay.headless.admin.content.internal.graphql.extension;

import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.vulcan.graphql.contributor.GraphQLContributor;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(immediate = true, service = GraphQLContributor.class)
public class StructuredContentGraphQLContributor implements GraphQLContributor {

	@Override
	public String getPath() {
		return "/headless-admin-content-graphql/v1_0";
	}

	@Override
	public StructuredContentQueryContributor getQuery() {
		return new StructuredContentQueryContributor();
	}

	@Activate
	protected void activate() {
		StructuredContentQueryContributor.setJournalArticleLocalService(
			_journalArticleLocalService);
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}