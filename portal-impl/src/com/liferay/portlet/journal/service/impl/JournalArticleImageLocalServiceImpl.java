/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.journal.DuplicateArticleImageIdException;
import com.liferay.portlet.journal.NoSuchArticleImageException;
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
		throws PortalException, SystemException {

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
		else if (articleImage.getArticleImageId() == articleImageId) {
		}
		else {
			throw new DuplicateArticleImageIdException();
		}
	}

	@Override
	public void deleteArticleImage(JournalArticleImage articleImage)
		throws SystemException {

		try {
			imageLocalService.deleteImage(articleImage.getArticleImageId());
		}
		catch (PortalException pe) {
		}

		journalArticleImagePersistence.remove(articleImage);
	}

	@Override
	public void deleteArticleImage(long articleImageId) throws SystemException {
		try {
			JournalArticleImage articleImage =
				journalArticleImagePersistence.findByPrimaryKey(articleImageId);

			deleteArticleImage(articleImage);
		}
		catch (NoSuchArticleImageException nsaie) {
		}
	}

	@Override
	public void deleteArticleImage(
			long groupId, String articleId, double version, String elInstanceId,
			String elName, String languageId)
		throws SystemException {

		try {
			JournalArticleImage articleImage =
				journalArticleImagePersistence.findByG_A_V_E_E_L(
					groupId, articleId, version, elInstanceId, elName,
					languageId);

			deleteArticleImage(articleImage);
		}
		catch (NoSuchArticleImageException nsaie) {
		}
	}

	@Override
	public void deleteImages(long groupId, String articleId, double version)
		throws SystemException {

		for (JournalArticleImage articleImage :
				journalArticleImagePersistence.findByG_A_V(
					groupId, articleId, version)) {

			deleteArticleImage(articleImage);
		}
	}

	@Override
	public JournalArticleImage getArticleImage(long articleImageId)
		throws PortalException, SystemException {

		return journalArticleImagePersistence.findByPrimaryKey(articleImageId);
	}

	@Override
	public long getArticleImageId(
			long groupId, String articleId, double version, String elInstanceId,
			String elName, String languageId)
		throws SystemException {

		return getArticleImageId(
			groupId, articleId, version, elInstanceId, elName, languageId,
			false);
	}

	@Override
	public long getArticleImageId(
			long groupId, String articleId, double version, String elInstanceId,
			String elName, String languageId, boolean tempImage)
		throws SystemException {

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
	public List<JournalArticleImage> getArticleImages(long groupId)
		throws SystemException {

		return journalArticleImagePersistence.findByGroupId(groupId);
	}

}