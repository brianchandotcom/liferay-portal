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

package com.liferay.journal.service.impl;

import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.base.JournalArticleResourceLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalArticleResource",
	service = AopService.class
)
public class JournalArticleResourceLocalServiceImpl
	extends JournalArticleResourceLocalServiceBaseImpl {

	@Override
	public void deleteArticleResource(long groupId, String articleId)
		throws PortalException {

		journalArticleResourcePersistence.removeByG_A(groupId, articleId);
	}

	@Override
	public JournalArticleResource fetchArticleResource(
		long groupId, String articleId) {

		return journalArticleResourcePersistence.fetchByG_A(groupId, articleId);
	}

	@Override
	public JournalArticleResource fetchArticleResource(
		String uuid, long groupId) {

		return journalArticleResourcePersistence.fetchByUUID_G(uuid, groupId);
	}

	@Override
	public JournalArticleResource getArticleResource(
			long articleResourcePrimKey)
		throws PortalException {

		return journalArticleResourcePersistence.findByPrimaryKey(
			articleResourcePrimKey);
	}

	@Override
	public long getArticleResourcePrimKey(long groupId, String articleId) {
		return getArticleResourcePrimKey(null, groupId, articleId);
	}

	@Override
	public long getArticleResourcePrimKey(
		String uuid, long groupId, String articleId) {

		JournalArticleResource articleResource = null;

		if (Validator.isNotNull(uuid)) {
			articleResource = journalArticleResourcePersistence.fetchByUUID_G(
				uuid, groupId);
		}

		if (articleResource == null) {
			articleResource = journalArticleResourcePersistence.fetchByG_A(
				groupId, articleId);
		}

		if (articleResource == null) {
			long articleResourcePrimKey = counterLocalService.increment();

			articleResource = journalArticleResourcePersistence.create(
				articleResourcePrimKey);

			if (Validator.isNotNull(uuid)) {
				articleResource.setUuid(uuid);
			}

			articleResource.setGroupId(groupId);
			articleResource.setArticleId(articleId);

			journalArticleResourcePersistence.update(articleResource);
		}

		return articleResource.getResourcePrimKey();
	}

	@Override
	public List<JournalArticleResource> getArticleResources(long groupId) {
		return journalArticleResourcePersistence.findByGroupId(groupId);
	}

}