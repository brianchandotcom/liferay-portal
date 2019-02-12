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
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
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
				_journalArticleResourceLocalService::fetchJournalArticleResource
			).setEntityIdsFromResourceEntityFunctions(
				JournalArticleResource::getResourcePrimKey,
				JournalArticleResource::getLatestArticlePK
			).setVersionEntityByVersionEntityIdFunction(
				_journalArticleLocalService::fetchJournalArticle
			).setVersionEntityDetails(
				this::_fetchGroupName, JournalArticle::getTitle,
				JournalArticle::getVersion
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

	private String _fetchGroupName(JournalArticle journalArticle) {
		Group group = _groupLocalService.fetchGroup(
			journalArticle.getGroupId());

		try {
			return group.getDescriptiveName();
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return StringPool.BLANK;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalCTConfigurationRegistrar.class);

	@Reference(scope = ReferenceScope.PROTOTYPE_REQUIRED)
	private CTConfigurationBuilder<JournalArticleResource, JournalArticle>
		_builder;

	@Reference
	private CTConfigurationRegistrar _ctConfigurationRegistrar;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

}