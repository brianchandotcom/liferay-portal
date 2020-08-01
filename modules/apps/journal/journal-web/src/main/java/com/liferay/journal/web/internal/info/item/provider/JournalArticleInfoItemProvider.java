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

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.GroupKeyInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.exception.NoSuchArticleResourceException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Objects;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = InfoItemObjectProvider.class)
public class JournalArticleInfoItemProvider
	implements InfoItemObjectProvider<JournalArticle> {

	@Override
	public JournalArticle getInfoItem(InfoItemIdentifier infoItemIdentifier)
		throws NoSuchInfoItemException {

		if (!(infoItemIdentifier instanceof ClassPKInfoItemIdentifier) &&
			!(infoItemIdentifier instanceof GroupKeyInfoItemIdentifier)) {

			throw new NoSuchInfoItemException(
				"Unsupported info item identifier type " + infoItemIdentifier);
		}

		JournalArticle article = null;

		String version = null;

		Optional<String> versionOptional =
			infoItemIdentifier.getVersionOptional();

		if (versionOptional.isPresent()) {
			version = versionOptional.get();
		}

		try {
			if (infoItemIdentifier instanceof ClassPKInfoItemIdentifier) {
				ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
					(ClassPKInfoItemIdentifier)infoItemIdentifier;

				article = _getArticle(
					classPKInfoItemIdentifier.getClassPK(), version);
			}
			else if (infoItemIdentifier instanceof GroupKeyInfoItemIdentifier) {
				GroupKeyInfoItemIdentifier groupKeyInfoItemIdentifier =
					(GroupKeyInfoItemIdentifier)infoItemIdentifier;

				article = _getArticle(
					groupKeyInfoItemIdentifier.getGroupId(),
					groupKeyInfoItemIdentifier.getKey(), version);
			}
		}
		catch (NoSuchArticleException | NoSuchArticleResourceException
					exception) {

			throw new NoSuchInfoItemException(
				"Unable to get journal article with identifier " +
					infoItemIdentifier,
				exception);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		if ((article == null) || article.isInTrash()) {
			throw new NoSuchInfoItemException(
				"Unable to get journal article " + infoItemIdentifier);
		}

		return article;
	}

	@Override
	public JournalArticle getInfoItem(long classPK)
		throws NoSuchInfoItemException {

		InfoItemIdentifier infoItemIdentifier = new ClassPKInfoItemIdentifier(
			classPK);

		return getInfoItem(infoItemIdentifier);
	}

	private JournalArticle _getArticle(long classPK, String version)
		throws PortalException {

		if (Validator.isNull(version) ||
			Objects.equals(
				version, InfoItemIdentifier.VERSION_LATEST_APPROVED)) {

			return _journalArticleLocalService.fetchLatestArticle(
				classPK, WorkflowConstants.STATUS_APPROVED);
		}
		else if (Objects.equals(version, InfoItemIdentifier.VERSION_LATEST)) {
			return _journalArticleLocalService.fetchLatestArticle(
				classPK, WorkflowConstants.STATUS_ANY);
		}
		else {
			JournalArticleResource articleResource =
				_journalArticleResourceLocalService.getArticleResource(classPK);

			return _journalArticleLocalService.getArticle(
				articleResource.getGroupId(), articleResource.getArticleId(),
				GetterUtil.getDouble(version));
		}
	}

	private JournalArticle _getArticle(
			long groupId, String articleId, String version)
		throws PortalException {

		if (Validator.isNull(version) ||
			Objects.equals(
				version, InfoItemIdentifier.VERSION_LATEST_APPROVED)) {

			return _journalArticleLocalService.fetchLatestArticle(
				groupId, articleId, WorkflowConstants.STATUS_APPROVED);
		}
		else if (Objects.equals(version, InfoItemIdentifier.VERSION_LATEST)) {
			return _journalArticleLocalService.fetchLatestArticle(
				groupId, articleId, WorkflowConstants.STATUS_ANY);
		}
		else {
			return _journalArticleLocalService.getArticle(
				groupId, articleId, GetterUtil.getDouble(version));
		}
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

}