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

import com.liferay.change.tracking.CTManager;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.exception.CTEntryException;
import com.liferay.change.tracking.exception.CTException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.Portal;

import java.io.File;
import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class CTJournalArticleLocalServiceWrapper
	extends JournalArticleLocalServiceWrapper {

	public CTJournalArticleLocalServiceWrapper() {
		super(null);
	}

	public CTJournalArticleLocalServiceWrapper(
		JournalArticleLocalService journalArticleLocalService) {

		super(journalArticleLocalService);
	}

	@Override
	public JournalArticle addArticle(
			long userId, long groupId, long folderId, long classNameId,
			long classPK, String articleId, boolean autoArticleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap,
			Map<Locale, String> friendlyURLMap, String content,
			String ddmStructureKey, String ddmTemplateKey, String layoutUuid,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallImageFile,
			Map<String, byte[]> images, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = _ctManager.executeModelUpdate(
			() -> super.addArticle(
				userId, groupId, folderId, classNameId, classPK, articleId,
				autoArticleId, version, titleMap, descriptionMap,
				friendlyURLMap, content, ddmStructureKey, ddmTemplateKey,
				layoutUuid, displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, reviewDateMonth,
				reviewDateDay, reviewDateYear, reviewDateHour, reviewDateMinute,
				neverReview, indexable, smallImage, smallImageURL,
				smallImageFile, images, articleURL, serviceContext));

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_ADDITION, true);

		return journalArticle;
	}

	@Override
	public JournalArticle addArticle(
			long userId, long groupId, long folderId, long classNameId,
			long classPK, String articleId, boolean autoArticleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String content,
			String ddmStructureKey, String ddmTemplateKey, String layoutUuid,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallImageFile,
			Map<String, byte[]> images, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = _ctManager.executeModelUpdate(
			() -> super.addArticle(
				userId, groupId, folderId, classNameId, classPK, articleId,
				autoArticleId, version, titleMap, descriptionMap, content,
				ddmStructureKey, ddmTemplateKey, layoutUuid, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
				reviewDateHour, reviewDateMinute, neverReview, indexable,
				smallImage, smallImageURL, smallImageFile, images, articleURL,
				serviceContext));

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_ADDITION, true);

		return journalArticle;
	}

	@Override
	public JournalArticle addArticle(
			long userId, long groupId, long folderId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String content, String ddmStructureKey, String ddmTemplateKey,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = _ctManager.executeModelUpdate(
			() -> super.addArticle(
				userId, groupId, folderId, titleMap, descriptionMap, content,
				ddmStructureKey, ddmTemplateKey, serviceContext));

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_ADDITION, true);

		return journalArticle;
	}

	@Override
	public JournalArticle checkArticleResourcePrimKey(
			long groupId, String articleId, double version)
		throws PortalException {

		JournalArticle journalArticle = super.checkArticleResourcePrimKey(
			groupId, articleId, version);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public void checkNewLine(long groupId, String articleId, double version)
		throws PortalException {

		super.checkNewLine(groupId, articleId, version);

		JournalArticle journalArticle = super.fetchArticle(
			groupId, articleId, version);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);
	}

	@Override
	public JournalArticle copyArticle(
			long userId, long groupId, String oldArticleId, String newArticleId,
			boolean autoArticleId, double version)
		throws PortalException {

		JournalArticle journalArticle = super.copyArticle(
			userId, groupId, oldArticleId, newArticleId, autoArticleId,
			version);

		_registerChange(journalArticle, CTConstants.CT_CHANGE_TYPE_ADDITION);

		return journalArticle;
	}

	@Override
	public JournalArticle deleteArticle(JournalArticle article)
		throws PortalException {

		JournalArticle journalArticle = super.deleteArticle(article);

		_unregisterChange(journalArticle);

		return journalArticle;
	}

	@Override
	public JournalArticle deleteArticle(
			JournalArticle article, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.deleteArticle(
			article, articleURL, serviceContext);

		_unregisterChange(journalArticle);

		return journalArticle;
	}

	@Override
	public JournalArticle deleteArticle(
			long groupId, String articleId, double version, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.deleteArticle(
			groupId, articleId, version, articleURL, serviceContext);

		_unregisterChange(journalArticle);

		return journalArticle;
	}

	@Override
	public JournalArticle moveArticle(
			long groupId, String articleId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.moveArticle(
			groupId, articleId, newFolderId, serviceContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle moveArticleFromTrash(
			long userId, long groupId, JournalArticle article, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.moveArticleFromTrash(
			userId, groupId, article, newFolderId, serviceContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle moveArticleToTrash(
			long userId, JournalArticle article)
		throws PortalException {

		JournalArticle journalArticle = super.moveArticleToTrash(
			userId, article);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_DELETION, true);

		return journalArticle;
	}

	@Override
	public JournalArticle moveArticleToTrash(
			long userId, long groupId, String articleId)
		throws PortalException {

		JournalArticle journalArticle = super.moveArticleToTrash(
			userId, groupId, articleId);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_DELETION, true);

		return journalArticle;
	}

	@Override
	public JournalArticle removeArticleLocale(
			long groupId, String articleId, double version, String languageId)
		throws PortalException {

		JournalArticle journalArticle = super.removeArticleLocale(
			groupId, articleId, version, languageId);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle restoreArticleFromTrash(
			long userId, JournalArticle article)
		throws PortalException {

		JournalArticle journalArticle = super.restoreArticleFromTrash(
			userId, article);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap,
			Map<Locale, String> friendlyURLMap, String content,
			String ddmStructureKey, String ddmTemplateKey, String layoutUuid,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallImageFile,
			Map<String, byte[]> images, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.updateArticle(
			userId, groupId, folderId, articleId, version, titleMap,
			descriptionMap, friendlyURLMap, content, ddmStructureKey,
			ddmTemplateKey, layoutUuid, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, indexable, smallImage, smallImageURL,
			smallImageFile, images, articleURL, serviceContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String content,
			String layoutUuid, ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.updateArticle(
			userId, groupId, folderId, articleId, version, titleMap,
			descriptionMap, content, layoutUuid, serviceContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String content,
			String ddmStructureKey, String ddmTemplateKey, String layoutUuid,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallImageFile,
			Map<String, byte[]> images, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.updateArticle(
			userId, groupId, folderId, articleId, version, titleMap,
			descriptionMap, content, ddmStructureKey, ddmTemplateKey,
			layoutUuid, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, reviewDateMonth, reviewDateDay,
			reviewDateYear, reviewDateHour, reviewDateMinute, neverReview,
			indexable, smallImage, smallImageURL, smallImageFile, images,
			articleURL, serviceContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, String content, ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.updateArticle(
			userId, groupId, folderId, articleId, version, content,
			serviceContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateArticle(long id, String urlTitle)
		throws PortalException {

		JournalArticle journalArticle = super.updateArticle(id, urlTitle);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateArticleTranslation(
			long groupId, String articleId, double version, Locale locale,
			String title, String description, String content,
			Map<String, byte[]> images, ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.updateArticleTranslation(
			groupId, articleId, version, locale, title, description, content,
			images, serviceContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateContent(
			long groupId, String articleId, double version, String content)
		throws PortalException {

		JournalArticle journalArticle = super.updateContent(
			groupId, articleId, version, content);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateStatus(
			long userId, JournalArticle article, int status, String articleURL,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		JournalArticle journalArticle = super.updateStatus(
			userId, article, status, articleURL, serviceContext,
			workflowContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateStatus(
			long userId, long classPK, int status,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.updateStatus(
			userId, classPK, status, workflowContext, serviceContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Override
	public JournalArticle updateStatus(
			long userId, long groupId, String articleId, double version,
			int status, String articleURL,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle journalArticle = super.updateStatus(
			userId, groupId, articleId, version, status, articleURL,
			workflowContext, serviceContext);

		_registerChange(
			journalArticle, CTConstants.CT_CHANGE_TYPE_MODIFICATION);

		return journalArticle;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		// this is needed because of synchronisation

	}

	private void _registerChange(JournalArticle journalArticle, int changeType)
		throws CTException {

		_registerChange(journalArticle, changeType, false);
	}

	private void _registerChange(
			JournalArticle journalArticle, int changeType, boolean force)
		throws CTException {

		try {
			_ctManager.registerModelChange(
				PrincipalThreadLocal.getUserId(),
				_portal.getClassNameId(JournalArticle.class.getName()),
				journalArticle.getId(), journalArticle.getResourcePrimKey(),
				changeType, force);
		}
		catch (CTException cte) {
			if (cte instanceof CTEntryException) {
				if (_log.isWarnEnabled()) {
					_log.warn(cte.getMessage());
				}
			}
			else {
				throw cte;
			}
		}
	}

	private void _unregisterChange(JournalArticle journalArticle) {
		_ctManager.unregisterModelChange(
			PrincipalThreadLocal.getUserId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			journalArticle.getId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTJournalArticleLocalServiceWrapper.class);

	@Reference
	private CTManager _ctManager;

	@Reference
	private Portal _portal;

}