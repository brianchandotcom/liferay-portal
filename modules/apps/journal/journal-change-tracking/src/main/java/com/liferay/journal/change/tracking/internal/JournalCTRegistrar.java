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

package com.liferay.journal.change.tracking.internal;

import com.liferay.change.tracking.definition.CTDefinitionRegistrar;
import com.liferay.change.tracking.definition.builder.CTDefinitionBuilder;
import com.liferay.change.tracking.function.CTFunctions;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = {})
public class JournalCTRegistrar {

	@Activate
	public void activate() {
		_ctDefinitionRegistrar.register(
			_builder.setContentType(
				"Web Content"
			).setContentTypeLanguageKey(
				"journal-article"
			).setEntityClasses(
				JournalArticleResource.class, JournalArticle.class
			).setResourceEntitiesByCompanyIdFunction(
				this::_fetchJournalArticleResources
			).setResourceEntityByResourceEntityIdFunction(
				_journalArticleResourceLocalService::fetchJournalArticleResource
			).setEntityIdsFromResourceEntityFunctions(
				JournalArticleResource::getResourcePrimKey,
				JournalArticleResource::getLatestArticlePK
			).setVersionEntitiesFromResourceEntityFunction(
				journalArticleResource ->
					_journalArticleLocalService.getArticlesByResourcePrimKey(
						journalArticleResource.getResourcePrimKey())
			).setVersionEntityByVersionEntityIdFunction(
				_journalArticleLocalService::fetchJournalArticle
			).setVersionEntityDetails(
				Arrays.asList(
					_getDDMStructuresFunction(), _getDDMTemplatesFunction()),
				CTFunctions.getFetchSiteNameFunction(),
				JournalArticle::getTitle, JournalArticle::getVersion
			).setEntityIdsFromVersionEntityFunctions(
				JournalArticle::getResourcePrimKey, JournalArticle::getId
			).setVersionEntityStatusInfo(
				new Integer[] {
					WorkflowConstants.STATUS_APPROVED,
					WorkflowConstants.STATUS_DRAFT
				},
				JournalArticle::getStatus
			).build());
	}

	private List<JournalArticleResource> _fetchJournalArticleResources(
		long companyId) {

		DynamicQuery dynamicQuery =
			_journalArticleResourceLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		return _journalArticleResourceLocalService.dynamicQuery(dynamicQuery);
	}

	private Function<JournalArticle, List<? extends BaseModel>>
		_getDDMStructuresFunction() {

		return journalArticle -> Collections.singletonList(
			journalArticle.getDDMStructure());
	}

	private Function<JournalArticle, List<? extends BaseModel>>
		_getDDMTemplatesFunction() {

		return journalArticle -> Collections.singletonList(
			journalArticle.getDDMTemplate());
	}

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private CTDefinitionBuilder<JournalArticleResource, JournalArticle>
		_builder;

	@Reference
	private CTDefinitionRegistrar _ctDefinitionRegistrar;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

}