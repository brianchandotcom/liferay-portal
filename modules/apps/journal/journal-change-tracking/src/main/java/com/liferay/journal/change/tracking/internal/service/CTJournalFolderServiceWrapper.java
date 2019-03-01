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

import com.liferay.change.tracking.CTEngineManager;
import com.liferay.change.tracking.CTManager;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalFolderService;
import com.liferay.journal.service.JournalFolderServiceWrapper;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class CTJournalFolderServiceWrapper extends JournalFolderServiceWrapper {

	public CTJournalFolderServiceWrapper() {
		super(null);
	}

	public CTJournalFolderServiceWrapper(
		JournalFolderService journalFolderService) {

		super(journalFolderService);
	}

	@Override
	public List<Object> getFoldersAndArticles(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<?> obc) {

		List<Object> objects = super.getFoldersAndArticles(
			groupId, folderId, status, start, end, obc);

		objects.removeIf(object -> !_isRetrievable(object));

		return objects;
	}

	@Override
	public List<Object> getFoldersAndArticles(
		long groupId, long folderId, int start, int end,
		OrderByComparator<?> obc) {

		List<Object> objects = super.getFoldersAndArticles(
			groupId, folderId, start, end, obc);

		objects.removeIf(object -> !_isRetrievable(object));

		return objects;
	}

	@Override
	public List<Object> getFoldersAndArticles(
		long groupId, long userId, long folderId, int status, int start,
		int end, OrderByComparator<?> obc) {

		List<Object> objects = super.getFoldersAndArticles(
			groupId, userId, folderId, status, start, end, obc);

		objects.removeIf(object -> !_isRetrievable(object));

		return objects;
	}

	@Override
	public List<Object> getFoldersAndArticles(
		long groupId, long userId, long folderId, int status, Locale locale,
		int start, int end, OrderByComparator<?> obc) {

		List<Object> objects = super.getFoldersAndArticles(
			groupId, userId, folderId, status, locale, start, end, obc);

		objects.removeIf(object -> !_isRetrievable(object));

		return objects;
	}

	@Override
	public int getFoldersAndArticlesCount(
		long groupId, List<Long> folderIds, int status) {

		return super.getFoldersAndArticlesCount(groupId, folderIds, status);
	}

	@Override
	public int getFoldersAndArticlesCount(long groupId, long folderId) {
		super.getFoldersAndArticlesCount(groupId, folderId);

		return getFoldersAndArticlesCount(
			groupId, folderId, WorkflowConstants.STATUS_ANY);
	}

	@Override
	public int getFoldersAndArticlesCount(
		long groupId, long folderId, int status) {

		super.getFoldersAndArticlesCount(groupId, folderId, status);

		return getFoldersAndArticlesCount(groupId, 0, folderId, status);
	}

	@Override
	public int getFoldersAndArticlesCount(
		long groupId, long userId, long folderId, int status) {

		super.getFoldersAndArticlesCount(groupId, userId, folderId, status);

		List<Object> objects = getFoldersAndArticles(
			groupId, userId, folderId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		return objects.size();
	}

	@Reference(unbind = "-")
	protected void setJournalFolderService(
		JournalFolderService journalFolderService) {

		// This is needed because of synchronisation

	}

	private boolean _isRetrievable(Object object) {
		if (object == null) {
			return true;
		}

		if (!(object instanceof JournalArticle)) {
			return true;
		}

		JournalArticle journalArticle = (JournalArticle)object;

		if (!_ctEngineManager.isChangeTrackingEnabled(
				journalArticle.getCompanyId()) ||
			!_ctEngineManager.isChangeTrackingSupported(
				journalArticle.getCompanyId(), JournalArticle.class)) {

			return true;
		}

		if (_ctManager.isModelUpdateInProgress()) {
			return true;
		}

		Optional<CTEntry> ctEntryOptional =
			_ctManager.getLatestModelChangeCTEntryOptional(
				PrincipalThreadLocal.getUserId(),
				journalArticle.getResourcePrimKey());

		return ctEntryOptional.isPresent();
	}

	@Reference
	private CTEngineManager _ctEngineManager;

	@Reference
	private CTManager _ctManager;

}