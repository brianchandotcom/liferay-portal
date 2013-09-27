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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleImageLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;

import java.io.File;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Daniel Kocsis
 * @author Mate Thurzo
 */
public class JournalArticleStagedModelDataHandler
	extends BaseStagedModelDataHandler<JournalArticle> {

	public static final String[] CLASS_NAMES = {JournalArticle.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException {

		JournalArticleResource articleResource =
			JournalArticleResourceLocalServiceUtil.fetchArticleResource(
				uuid, groupId);

		if (articleResource == null) {
			return;
		}

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(
			extraData);

		if (Validator.isNull(extraData) ||
			extraDataJSONObject.getBoolean("inTrash")) {

			JournalArticleLocalServiceUtil.deleteArticle(
				groupId, articleResource.getArticleId(), null);
		}
		else {
			double version = extraDataJSONObject.getDouble("version");

			JournalArticle article = JournalArticleLocalServiceUtil.getArticle(
				groupId, articleResource.getArticleId(), version);

			JournalArticleLocalServiceUtil.deleteArticle(article);
		}
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(JournalArticle article) {
		return article.getTitleCurrentValue();
	}

	@Override
	public int[] getExportableStatuses() {
		return new int[] {
			WorkflowConstants.STATUS_APPROVED, WorkflowConstants.STATUS_EXPIRED
		};
	}

	@Override
	protected boolean countStagedModel(
		PortletDataContext portletDataContext, JournalArticle article) {

		return !portletDataContext.isModelCounted(
			JournalArticleResource.class.getName(),
			article.getResourcePrimKey());
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, JournalArticle article)
		throws Exception {

		Element articleElement = portletDataContext.getExportDataElement(
			article);

		articleElement.addAttribute(
			"article-resource-uuid", article.getArticleResourceUuid());

		if (article.getFolderId() !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, article, article.getFolder(),
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		if (Validator.isNotNull(article.getStructureId())) {
			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.getStructure(
					article.getGroupId(),
					PortalUtil.getClassNameId(JournalArticle.class),
					article.getStructureId(), true);

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, article, ddmStructure,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}

		if (Validator.isNotNull(article.getTemplateId())) {
			DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
				article.getGroupId(),
				PortalUtil.getClassNameId(DDMStructure.class),
				article.getTemplateId(), true);

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, article, ddmTemplate,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}

		if (article.isSmallImage()) {
			Image smallImage = ImageLocalServiceUtil.fetchImage(
				article.getSmallImageId());

			if (Validator.isNotNull(article.getSmallImageURL())) {
				String smallImageURL =
					ExportImportHelperUtil.replaceExportContentReferences(
						portletDataContext, article, articleElement,
						article.getSmallImageURL().concat(StringPool.SPACE),
						true);

				article.setSmallImageURL(smallImageURL);
			}
			else if (smallImage != null) {
				String smallImagePath = ExportImportPathUtil.getModelPath(
					article,
					smallImage.getImageId() + StringPool.PERIOD +
						smallImage.getType());

				articleElement.addAttribute("small-image-path", smallImagePath);

				article.setSmallImageType(smallImage.getType());

				portletDataContext.addZipEntry(
					smallImagePath, smallImage.getTextObj());
			}
		}

		List<JournalArticleImage> articleImages =
			JournalArticleImageLocalServiceUtil.getArticleImages(
				article.getGroupId(), article.getArticleId(),
				article.getVersion());

		for (JournalArticleImage articleImage : articleImages) {
			exportArticleImage(
				portletDataContext, articleImage, article, articleElement);
		}

		article.setStatusByUserUuid(article.getStatusByUserUuid());

		String content = ExportImportHelperUtil.replaceExportContentReferences(
			portletDataContext, article, articleElement, article.getContent(),
			portletDataContext.getBooleanParameter(
				JournalPortletDataHandler.NAMESPACE, "referenced-content"));

		article.setContent(content);

		portletDataContext.addClassedModel(
			articleElement, ExportImportPathUtil.getModelPath(article),
			article);
	}

	@Override
	protected void doImportCompanyStagedModel(
			PortletDataContext portletDataContext, JournalArticle article)
		throws Exception {

		JournalArticleResource existingArticleResource =
			JournalArticleResourceLocalServiceUtil.
				fetchJournalArticleResourceByUuidAndGroupId(
					article.getArticleResourceUuid(),
					portletDataContext.getCompanyGroupId());

		JournalArticle existingArticle =
			JournalArticleLocalServiceUtil.getLatestArticle(
				existingArticleResource.getResourcePrimKey(),
				WorkflowConstants.STATUS_ANY, false);

		Map<String, String> articleArticleIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class + ".articleId");

		articleArticleIds.put(
			article.getArticleId(), existingArticle.getArticleId());

		Map<Long, Long> articleIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class);

		articleIds.put(article.getId(), existingArticle.getId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, JournalArticle article)
		throws Exception {

		prepareLanguagesForImport(article);

		long userId = portletDataContext.getUserId(article.getUserUuid());

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(
			portletDataContext, article);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		User user = UserLocalServiceUtil.getUser(userId);

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalFolder.class);

		if (article.getFolderId() !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			Element folderElement = portletDataContext.getReferenceDataElement(
				article, JournalFolder.class, article.getFolderId());

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, folderElement);
		}

		long folderId = MapUtil.getLong(
			folderIds, article.getFolderId(), article.getFolderId());

		String articleId = article.getArticleId();

		boolean autoArticleId = false;

		if (Validator.isNumber(articleId) ||
			(JournalArticleLocalServiceUtil.fetchArticle(
				portletDataContext.getScopeGroupId(), articleId,
				JournalArticleConstants.VERSION_DEFAULT) != null)) {

			autoArticleId = true;
		}

		Map<String, String> articleIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class + ".articleId");

		String newArticleId = articleIds.get(articleId);

		if (Validator.isNotNull(newArticleId)) {

			// A sibling of a different version was already assigned a new
			// article id

			articleId = newArticleId;
			autoArticleId = false;
		}

		String content = article.getContent();

		Element articleElement =
			portletDataContext.getImportDataStagedModelElement(article);

		content = ExportImportHelperUtil.replaceImportContentReferences(
			portletDataContext, articleElement, content, true);

		article.setContent(content);

		String newContent = creationStrategy.getTransformedContent(
			portletDataContext, article);

		if (newContent != JournalCreationStrategy.ARTICLE_CONTENT_UNCHANGED) {
			article.setContent(newContent);
		}

		Date displayDate = article.getDisplayDate();

		int displayDateMonth = 0;
		int displayDateDay = 0;
		int displayDateYear = 0;
		int displayDateHour = 0;
		int displayDateMinute = 0;

		if (displayDate != null) {
			Calendar displayCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			displayCal.setTime(displayDate);

			displayDateMonth = displayCal.get(Calendar.MONTH);
			displayDateDay = displayCal.get(Calendar.DATE);
			displayDateYear = displayCal.get(Calendar.YEAR);
			displayDateHour = displayCal.get(Calendar.HOUR);
			displayDateMinute = displayCal.get(Calendar.MINUTE);

			if (displayCal.get(Calendar.AM_PM) == Calendar.PM) {
				displayDateHour += 12;
			}
		}

		Date expirationDate = article.getExpirationDate();

		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;
		boolean neverExpire = true;

		if (expirationDate != null) {
			Calendar expirationCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			expirationCal.setTime(expirationDate);

			expirationDateMonth = expirationCal.get(Calendar.MONTH);
			expirationDateDay = expirationCal.get(Calendar.DATE);
			expirationDateYear = expirationCal.get(Calendar.YEAR);
			expirationDateHour = expirationCal.get(Calendar.HOUR);
			expirationDateMinute = expirationCal.get(Calendar.MINUTE);
			neverExpire = false;

			if (expirationCal.get(Calendar.AM_PM) == Calendar.PM) {
				expirationDateHour += 12;
			}
		}

		Date reviewDate = article.getReviewDate();

		int reviewDateMonth = 0;
		int reviewDateDay = 0;
		int reviewDateYear = 0;
		int reviewDateHour = 0;
		int reviewDateMinute = 0;
		boolean neverReview = true;

		if (reviewDate != null) {
			Calendar reviewCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			reviewCal.setTime(reviewDate);

			reviewDateMonth = reviewCal.get(Calendar.MONTH);
			reviewDateDay = reviewCal.get(Calendar.DATE);
			reviewDateYear = reviewCal.get(Calendar.YEAR);
			reviewDateHour = reviewCal.get(Calendar.HOUR);
			reviewDateMinute = reviewCal.get(Calendar.MINUTE);
			neverReview = false;

			if (reviewCal.get(Calendar.AM_PM) == Calendar.PM) {
				reviewDateHour += 12;
			}
		}

		String parentDDMStructureKey = StringPool.BLANK;

		long ddmStructureId = 0;

		List<Element> structureElements =
			portletDataContext.getReferenceDataElements(
				article, DDMStructure.class);

		if (!structureElements.isEmpty()) {
			Element structureElement = structureElements.get(0);

			String structurePath = structureElement.attributeValue("path");

			DDMStructure ddmStructure =
				(DDMStructure)portletDataContext.getZipEntryAsObject(
					structurePath);

			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, ddmStructure);

			Map<String, String> ddmStructureKeys =
				(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
					DDMStructure.class + ".ddmStructureKey");

			parentDDMStructureKey = MapUtil.getString(
				ddmStructureKeys, ddmStructure.getStructureKey(),
				ddmStructure.getStructureKey());
		}

		String parentDDMTemplateKey = StringPool.BLANK;

		List<Element> ddmTemplateElements =
			portletDataContext.getReferenceDataElements(
				article, DDMTemplate.class);

		if (!ddmTemplateElements.isEmpty()) {
			Element templateElement = ddmTemplateElements.get(0);

			String ddmTemplatePath = templateElement.attributeValue("path");

			DDMTemplate ddmTemplate =
				(DDMTemplate)portletDataContext.getZipEntryAsObject(
					ddmTemplatePath);

			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, ddmTemplate);

			Map<String, String> ddmTemplateKeys =
				(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
					DDMTemplate.class + ".ddmTemplateKey");

			parentDDMTemplateKey = MapUtil.getString(
				ddmTemplateKeys, ddmTemplate.getTemplateKey(),
				ddmTemplate.getTemplateKey());
		}

		File smallFile = null;

		try {
			if (article.isSmallImage()) {
				String smallImagePath = articleElement.attributeValue(
					"small-image-path");

				if (Validator.isNotNull(article.getSmallImageURL())) {
					String smallImageURL =
						ExportImportHelperUtil.replaceImportContentReferences(
							portletDataContext, articleElement,
							article.getSmallImageURL(), true);

					article.setSmallImageURL(smallImageURL);
				}
				else if (Validator.isNotNull(smallImagePath)) {
					byte[] bytes = portletDataContext.getZipEntryAsByteArray(
						smallImagePath);

					if (bytes != null) {
						smallFile = FileUtil.createTempFile(
							article.getSmallImageType());

						FileUtil.write(smallFile, bytes);
					}
				}
			}

			Map<String, byte[]> images = new HashMap<String, byte[]>();

			List<Element> imagesElements =
				portletDataContext.getReferenceDataElements(
					article, Image.class);

			for (Element imageElement : imagesElements) {
				String imagePath = imageElement.attributeValue("path");

				String fileName = imageElement.attributeValue("file-name");

				images.put(
					fileName,
					portletDataContext.getZipEntryAsByteArray(imagePath));
			}

			String articleURL = null;

			boolean addGroupPermissions = creationStrategy.addGroupPermissions(
				portletDataContext, article);
			boolean addGuestPermissions = creationStrategy.addGuestPermissions(
				portletDataContext, article);

			ServiceContext serviceContext =
				portletDataContext.createServiceContext(article);

			serviceContext.setAddGroupPermissions(addGroupPermissions);
			serviceContext.setAddGuestPermissions(addGuestPermissions);

			if (article.getStatus() != WorkflowConstants.STATUS_APPROVED) {
				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_SAVE_DRAFT);
			}

			JournalArticle importedArticle = null;

			String articleResourceUuid = articleElement.attributeValue(
				"article-resource-uuid");

			if (portletDataContext.isDataStrategyMirror()) {
				JournalArticleResource articleResource =
					JournalArticleResourceLocalServiceUtil.
						fetchJournalArticleResourceByUuidAndGroupId(
							articleResourceUuid,
							portletDataContext.getScopeGroupId());

				serviceContext.setUuid(articleResourceUuid);
				serviceContext.setAttribute("urlTitle", article.getUrlTitle());

				JournalArticle existingArticle = null;

				if (articleResource != null) {
					existingArticle =
						JournalArticleLocalServiceUtil.fetchLatestArticle(
							articleResource.getResourcePrimKey(),
							WorkflowConstants.STATUS_ANY, false);
				}

				if (existingArticle == null) {
					existingArticle =
						JournalArticleLocalServiceUtil.fetchArticle(
							portletDataContext.getScopeGroupId(), newArticleId,
							article.getVersion());
				}

				if (existingArticle == null) {
					importedArticle =
						JournalArticleLocalServiceUtil.addArticle(
							userId, portletDataContext.getScopeGroupId(),
							folderId, article.getClassNameId(), ddmStructureId,
							articleId, autoArticleId, article.getVersion(),
							article.getTitleMap(), article.getDescriptionMap(),
							article.getContent(), article.getType(),
							parentDDMStructureKey, parentDDMTemplateKey,
							article.getLayoutUuid(), displayDateMonth,
							displayDateDay, displayDateYear, displayDateHour,
							displayDateMinute, expirationDateMonth,
							expirationDateDay, expirationDateYear,
							expirationDateHour, expirationDateMinute,
							neverExpire, reviewDateMonth, reviewDateDay,
							reviewDateYear, reviewDateHour, reviewDateMinute,
							neverReview, article.isIndexable(),
							article.isSmallImage(), article.getSmallImageURL(),
							smallFile, images, articleURL, serviceContext);
				}
				else {
					importedArticle =
						JournalArticleLocalServiceUtil.updateArticle(
							userId, existingArticle.getGroupId(), folderId,
							existingArticle.getArticleId(),
							article.getVersion(), article.getTitleMap(),
							article.getDescriptionMap(), article.getContent(),
							article.getType(), parentDDMStructureKey,
							parentDDMTemplateKey, article.getLayoutUuid(),
							displayDateMonth, displayDateDay, displayDateYear,
							displayDateHour, displayDateMinute,
							expirationDateMonth, expirationDateDay,
							expirationDateYear, expirationDateHour,
							expirationDateMinute, neverExpire, reviewDateMonth,
							reviewDateDay, reviewDateYear, reviewDateHour,
							reviewDateMinute, neverReview,
							article.isIndexable(), article.isSmallImage(),
							article.getSmallImageURL(), smallFile, images,
							articleURL, serviceContext);
				}
			}
			else {
				importedArticle = JournalArticleLocalServiceUtil.addArticle(
					userId, portletDataContext.getScopeGroupId(), folderId,
					article.getClassNameId(), ddmStructureId, articleId,
					autoArticleId, article.getVersion(), article.getTitleMap(),
					article.getDescriptionMap(), article.getContent(),
					article.getType(), parentDDMStructureKey,
					parentDDMTemplateKey, article.getLayoutUuid(),
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, reviewDateMonth,
					reviewDateDay, reviewDateYear, reviewDateHour,
					reviewDateMinute, neverReview, article.isIndexable(),
					article.isSmallImage(), article.getSmallImageURL(),
					smallFile, images, articleURL, serviceContext);
			}

			portletDataContext.importClassedModel(article, importedArticle);

			if (Validator.isNull(newArticleId)) {
				articleIds.put(
					article.getArticleId(), importedArticle.getArticleId());
			}
		}
		finally {
			if (smallFile != null) {
				smallFile.delete();
			}
		}
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, JournalArticle article)
		throws Exception {

		long userId = portletDataContext.getUserId(article.getUserUuid());

		Element articleElement =
			portletDataContext.getImportDataStagedModelElement(article);

		String articleResourceUuid = articleElement.attributeValue(
			"article-resource-uuid");

		JournalArticleResource existingArticleResource =
			JournalArticleResourceLocalServiceUtil.
				fetchJournalArticleResourceByUuidAndGroupId(
					articleResourceUuid, portletDataContext.getScopeGroupId());

		if (existingArticleResource == null) {
			return;
		}

		JournalArticle existingArticle =
			JournalArticleLocalServiceUtil.fetchLatestArticle(
				existingArticleResource.getResourcePrimKey(),
				WorkflowConstants.STATUS_ANY, false);

		if ((existingArticle == null) || !existingArticle.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = existingArticle.getTrashHandler();

		if (trashHandler.isRestorable(existingArticle.getResourcePrimKey())) {
			trashHandler.restoreTrashEntry(
				userId, existingArticle.getResourcePrimKey());
		}
	}

	protected void exportArticleImage(
			PortletDataContext portletDataContext,
			JournalArticleImage articleImage, JournalArticle article,
			Element articleElement)
		throws SystemException {

		Image image = ImageLocalServiceUtil.fetchImage(
			articleImage.getArticleImageId());

		if ((image == null) || (image.getTextObj() == null)) {
			return;
		}

		String fileName =
			image.getImageId() + StringPool.PERIOD + image.getType();

		String articleImagePath = ExportImportPathUtil.getModelPath(
			article, fileName);

		if (!portletDataContext.isPathNotProcessed(articleImagePath)) {
			return;
		}

		Element imageElement = portletDataContext.getExportDataElement(image);

		imageElement.addAttribute("path", articleImagePath);

		if (Validator.isNotNull(fileName)) {
			imageElement.addAttribute("file-name", fileName);
		}

		portletDataContext.addZipEntry(articleImagePath, image.getTextObj());

		portletDataContext.addReferenceElement(
			article, articleElement, image, articleImagePath, false);
	}

	protected void prepareLanguagesForImport(JournalArticle article)
		throws PortalException {

		Locale articleDefaultLocale = LocaleUtil.fromLanguageId(
			article.getDefaultLanguageId());

		Locale[] articleAvailableLocales = LocaleUtil.fromLanguageIds(
			article.getAvailableLanguageIds());

		Locale defaultImportLocale = LocalizationUtil.getDefaultImportLocale(
			JournalArticle.class.getName(), article.getPrimaryKey(),
			articleDefaultLocale, articleAvailableLocales);

		article.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	protected boolean validateMissingReference(
			String uuid, long companyId, long groupId)
		throws Exception {

		JournalArticle article =
			JournalArticleLocalServiceUtil.fetchJournalArticleByUuidAndGroupId(
				uuid, groupId);

		if (article == null) {
			return false;
		}

		return true;
	}

}