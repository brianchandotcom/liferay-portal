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

package com.liferay.journal.change.tracking.internal.service;

import com.liferay.change.tracking.engine.CTManager;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.service.JournalArticleServiceWrapper;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class CTJournalArticleServiceWrapper
	extends JournalArticleServiceWrapper {

	public CTJournalArticleServiceWrapper() {
		super(null);
	}

	public CTJournalArticleServiceWrapper(
		JournalArticleServiceWrapper journalFolderService) {

		super(journalFolderService);
	}

	@Override
	public List<JournalArticle> getArticlesByArticleId(
		long groupId, String articleId, int start, int end,
		OrderByComparator<JournalArticle> obc) {

		if (!_hasPermission(groupId, articleId)) {
			return Collections.emptyList();
		}

		return _journalArticleLocalService.dynamicQuery(
			_getArticlesByArticleIdDynamicQuery(groupId, articleId), start, end,
			obc);
	}

	@Override
	public int getArticlesCountByArticleId(long groupId, String articleId) {
		if (!_hasPermission(groupId, articleId)) {
			return 0;
		}

		return (int)_journalArticleLocalService.dynamicQueryCount(
			_getArticlesByArticleIdDynamicQuery(groupId, articleId));
	}

	private DynamicQuery _getArticlesByArticleIdDynamicQuery(
		long groupId, String articleId) {

		long userId = PrincipalThreadLocal.getUserId();

		DynamicQuery query = _journalArticleLocalService.dynamicQuery();

		query.add(RestrictionsFactoryUtil.eq("groupId", groupId));
		query.add(RestrictionsFactoryUtil.eq("articleId", articleId));

		JournalArticleResource journalArticleResource =
			_journalArticleResourceLocalService.fetchArticleResource(
				groupId, articleId);

		if (journalArticleResource != null) {
			List<CTEntry> ctEntries = _ctManager.getModelChangeCTEntries(
				journalArticleResource.getCompanyId(), userId,
				journalArticleResource.getResourcePrimKey());

			Stream<CTEntry> ctEntryStream = ctEntries.stream();

			List<Long> classPKs = ctEntryStream.map(
				CTEntry::getModelClassPK
			).collect(
				Collectors.toList()
			);

			if (!classPKs.isEmpty()) {
				query.add(RestrictionsFactoryUtil.in("id", classPKs));
			}
		}

		return query;
	}

	private boolean _hasPermission(long groupId, String articleId) {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			return false;
		}

		JournalArticle journalArticle =
			_journalArticleLocalService.fetchArticle(groupId, articleId);

		if (journalArticle == null) {
			return false;
		}

		try {
			_journalArticleModelResourcePermission.check(
				permissionChecker, journalArticle.getPrimaryKey(),
				ActionKeys.VIEW);

			return true;
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe.getMessage());
			}

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTJournalArticleServiceWrapper.class);

	private static volatile ModelResourcePermission<JournalArticle>
		_journalArticleModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CTJournalArticleServiceWrapper.class,
				"_journalArticleModelResourcePermission", JournalArticle.class);

	@Reference
	private CTManager _ctManager;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private UserLocalService _userLocalService;

}