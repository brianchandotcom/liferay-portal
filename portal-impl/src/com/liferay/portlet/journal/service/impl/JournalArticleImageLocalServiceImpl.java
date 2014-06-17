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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.journal.DuplicateArticleImageIdException;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.service.base.JournalArticleImageLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class JournalArticleImageLocalServiceImpl
	extends JournalArticleImageLocalServiceBaseImpl {

	@Override
	public void addArticleImageId(
			long articleImageId, long groupId, String articleId, double version,
			String elInstanceId, String elName, String languageId)
		throws PortalException {

		if (articleImageId <= 0) {
			return;
		}

		JournalArticleImage articleImage =
			journalArticleImagePersistence.fetchByG_A_V_E_E_L(
				groupId, articleId, version, elInstanceId, elName, languageId);

		if (articleImage == null) {
			articleImage = journalArticleImagePersistence.create(
				articleImageId);

			articleImage.setGroupId(groupId);
			articleImage.setArticleId(articleId);
			articleImage.setVersion(version);
			articleImage.setElInstanceId(elInstanceId);
			articleImage.setElName(elName);
			articleImage.setLanguageId(languageId);
			articleImage.setTempImage(false);

			journalArticleImagePersistence.update(articleImage);
		}
		else if (articleImage.getArticleImageId() != articleImageId) {
			throw new DuplicateArticleImageIdException(
				"{articleImageId=" + articleImageId + "}");
		}
	}

	@Override
	public void deleteArticleImage(JournalArticleImage articleImage) {
		try {
			imageLocalService.deleteImage(articleImage.getArticleImageId());
		}
		catch (PortalException pe) {
		}

		journalArticleImagePersistence.remove(articleImage);
	}

	@Override
	public void deleteArticleImage(long articleImageId) {
		JournalArticleImage articleImage =
			journalArticleImagePersistence.fetchByPrimaryKey(articleImageId);

		if (articleImage != null) {
			deleteArticleImage(articleImage);
		}
	}

	@Override
	public void deleteArticleImage(
		long groupId, String articleId, double version, String elInstanceId,
		String elName, String languageId) {

		JournalArticleImage articleImage =
			journalArticleImagePersistence.fetchByG_A_V_E_E_L(
				groupId, articleId, version, elInstanceId, elName, languageId);

		if (articleImage != null) {
			deleteArticleImage(articleImage);
		}
	}

	@Override
	public void deleteImages(long groupId, String articleId, double version) {
		for (JournalArticleImage articleImage :
				journalArticleImagePersistence.findByG_A_V(
					groupId, articleId, version)) {

			deleteArticleImage(articleImage);
		}
	}

	@Override
	public JournalArticleImage getArticleImage(long articleImageId)
		throws PortalException {

		return journalArticleImagePersistence.findByPrimaryKey(articleImageId);
	}

	@Override
	public long getArticleImageId(
		long groupId, String articleId, double version, String elInstanceId,
		String elName, String languageId) {

		return getArticleImageId(
			groupId, articleId, version, elInstanceId, elName, languageId,
			false);
	}

	@Override
	public long getArticleImageId(
		long groupId, String articleId, double version, String elInstanceId,
		String elName, String languageId, boolean tempImage) {

		JournalArticleImage articleImage =
			journalArticleImagePersistence.fetchByG_A_V_E_E_L(
				groupId, articleId, version, elInstanceId, elName, languageId);

		if (articleImage == null) {
			long articleImageId = counterLocalService.increment();

			articleImage = journalArticleImagePersistence.create(
				articleImageId);

			articleImage.setGroupId(groupId);
			articleImage.setArticleId(articleId);
			articleImage.setVersion(version);
			articleImage.setElInstanceId(elInstanceId);
			articleImage.setElName(elName);
			articleImage.setLanguageId(languageId);
			articleImage.setTempImage(tempImage);

			journalArticleImagePersistence.update(articleImage);
		}

		return articleImage.getArticleImageId();
	}

	@Override
	public List<JournalArticleImage> getArticleImages(long groupId) {
		return journalArticleImagePersistence.findByGroupId(groupId);
	}

	@Override
	public List<JournalArticleImage> getArticleImages(
		long groupId, String articleId, double version) {

		return journalArticleImagePersistence.findByG_A_V(
			groupId, articleId, version);
	}

	@Override
	public int getArticleImagesCount(long groupId) {
		return journalArticleImagePersistence.countByGroupId(groupId);
	}

}