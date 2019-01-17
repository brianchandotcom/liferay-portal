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

package com.liferay.journal.change.tracking.internal.configuration;

import com.liferay.change.tracking.configuration.CTConfigurationRegistrar;
import com.liferay.change.tracking.configuration.builder.CTConfigurationBuilder;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.persistence.JournalArticleResourceUtil;
import com.liferay.journal.service.persistence.JournalArticleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceScope;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = {})
public class JournalCTConfigurationRegistrar {

	@Activate
	public void activate() {
		_ctConfigurationRegistrar.register(
			_builder.setContentType(
				"Web Content"
			).setContentTypeLanguageKey(
				"journal"
			).setEntityClasses(
				JournalArticleResource.class, JournalArticle.class
			).setResourceEntityByResourceEntityIdFunction(
				JournalArticleResourceUtil::fetchByPrimaryKey
			).setEntityIdsFromResourceEntityFunctions(
				JournalArticleResource::getResourcePrimKey,
				JournalArticleResource::getLatestArticlePK
			).setVersionEntityByVersionEntityIdFunction(
				JournalArticleUtil::fetchByPrimaryKey
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

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private CTConfigurationBuilder<JournalArticleResource, JournalArticle>
		_builder;

	@Reference
	private CTConfigurationRegistrar _ctConfigurationRegistrar;

}