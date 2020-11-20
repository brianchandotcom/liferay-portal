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

package com.liferay.headless.admin.content.internal.jaxrs.extension;

import com.liferay.headless.admin.content.internal.dto.v1_0.util.VersionInformationUtil;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.jaxrs.context.EntityExtensionContext;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luis Miguel Barcos
 */
@Component(immediate = true, service = EntityExtensionContext.class)
public class StructuredContentExtensionContext
	extends EntityExtensionContext<StructuredContent> {

	public StructuredContentExtensionContext() {
	}

	public StructuredContentExtensionContext(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Override
	public Map<String, Object> getEntityExtendedProperties(
		StructuredContent entity) {

		try {
			JournalArticle journalArticle =
				_journalArticleLocalService.getLatestArticle(
					entity.getId(), WorkflowConstants.STATUS_ANY, false);

			return Collections.singletonMap(
				"versionInformation",
				VersionInformationUtil.toVersionInformation(
					journalArticle.getGroupId(), journalArticle.getStatus(),
					journalArticle.getVersion()));
		}
		catch (PortalException portalException) {
			throw new NotFoundException(portalException.getMessage());
		}
	}

	@Override
	public Set<String> getEntityFilteredPropertyKeys(StructuredContent entity) {
		return null;
	}

	private JournalArticleLocalService _journalArticleLocalService;

}