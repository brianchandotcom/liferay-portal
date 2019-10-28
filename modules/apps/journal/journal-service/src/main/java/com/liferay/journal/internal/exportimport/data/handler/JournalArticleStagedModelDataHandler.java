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

package com.liferay.journal.internal.exportimport.data.handler;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.changeset.model.ChangesetCollection;
import com.liferay.changeset.service.ChangesetCollectionLocalService;
import com.liferay.changeset.service.ChangesetEntryLocalService;
import com.liferay.document.library.kernel.exception.NoSuchFileException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.exception.ExportImportRuntimeException;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.kernel.staging.StagingConstants;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.journal.configuration.JournalGroupServiceConfiguration;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.internal.exportimport.creation.strategy.JournalCreationStrategy;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;

import java.io.File;
import java.io.InputStream;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Daniel Kocsis
 * @author Máté Thurzó
 */
@Component(
	immediate = true,
	service = {
		JournalArticleStagedModelDataHandler.class, StagedModelDataHandler.class
	}
)
public class JournalArticleStagedModelDataHandler
	extends BaseStagedModelDataHandler<JournalArticle> {

	public static final String[] CLASS_NAMES = {JournalArticle.class.getName()};

	@Override
	public void deleteStagedModel(JournalArticle article)
		throws PortalException {

		_journalArticleLocalService.deleteArticle(article);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		JournalArticleResource articleResource =
			_journalArticleResourceLocalService.
				fetchJournalArticleResourceByUuidAndGroupId(uuid, groupId);

		if (articleResource == null) {
			return;
		}

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject(
			extraData);

		if (Validator.isNotNull(extraData) && extraDataJSONObject.has("uuid")) {
			String articleUuid = extraDataJSONObject.getString("uuid");

			JournalArticle article = fetchStagedModelByUuidAndGroupId(
				articleUuid, groupId);

			deleteStagedModel(article);
		}
		else {
			_journalArticleLocalService.deleteArticle(
				groupId, articleResource.getArticleId(), null);
		}
	}

	@Override
	public JournalArticle fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _journalArticleLocalService.fetchJournalArticleByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<JournalArticle> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _journalArticleLocalService.getJournalArticlesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<JournalArticle>());
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
			WorkflowConstants.STATUS_APPROVED, WorkflowConstants.STATUS_EXPIRED,
			WorkflowConstants.STATUS_SCHEDULED
		};
	}

	@Override
	public Map<String, String> getReferenceAttributes(
		PortletDataContext portletDataContext, JournalArticle article) {

		String articleResourceUuid = null;

		try {
			articleResourceUuid = article.getArticleResourceUuid();
		}
		catch (Exception e) {
			ExportImportRuntimeException eire =
				new ExportImportRuntimeException(StringPool.BLANK, e);

			eire.setMessageKey(
				"unable-to-find-article-resource-x-while-gathering-reference-" +
					"attributes");
			eire.setData(String.valueOf(article.getArticleId()));

			eire.setClassName(
				JournalArticleStagedModelDataHandler.class.getName());

			throw eire;
		}

		Map<String, String> referenceAttributes = HashMapBuilder.put(
			"article-resource-uuid", articleResourceUuid
		).put(
			"article-id", article.getArticleId()
		).build();

		long defaultUserId = 0;

		try {
			defaultUserId = _userLocalService.getDefaultUserId(
				article.getCompanyId());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			return referenceAttributes;
		}

		boolean preloaded = isPreloadedArticle(defaultUserId, article);

		referenceAttributes.put("preloaded", String.valueOf(preloaded));

		referenceAttributes.put(
			"resource-prim-key", String.valueOf(article.getResourcePrimKey()));

		return referenceAttributes;
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		validateMissingGroupReference(portletDataContext, referenceElement);

		String uuid = referenceElement.attributeValue("uuid");
		String articleResourceUuid = referenceElement.attributeValue(
			"article-resource-uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		groupId = MapUtil.getLong(groupIds, groupId);

		String articleArticleId = referenceElement.attributeValue("article-id");

		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		if (!preloaded) {
			return super.validateMissingReference(uuid, groupId);
		}

		JournalArticle existingArticle = fetchExistingArticleWithParentGroups(
			uuid, articleResourceUuid, groupId, articleArticleId, null, 0.0,
			preloaded);

		if (existingArticle == null) {
			return false;
		}

		return true;
	}

	@Override
	protected boolean countStagedModel(
		PortletDataContext portletDataContext, JournalArticle article) {

		if (article.getClassNameId() == _portal.getClassNameId(
				DDMStructure.class)) {

			return false;
		}

		return !portletDataContext.isModelCounted(
			JournalArticleResource.class.getName(),
			article.getResourcePrimKey());
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, JournalArticle article)
		throws Exception {

		if (ExportImportThreadLocal.isStagingInProcess()) {
			ChangesetCollection changesetCollection =
				_changesetCollectionLocalService.fetchChangesetCollection(
					portletDataContext.getGroupId(),
					StagingConstants.
						RANGE_FROM_LAST_PUBLISH_DATE_CHANGESET_NAME);

			if (changesetCollection != null) {
				long classNameId = _classNameLocalService.getClassNameId(
					JournalArticleResource.class);

				_changesetEntryLocalService.deleteEntry(
					changesetCollection.getChangesetCollectionId(), classNameId,
					article.getResourcePrimKey());
			}
		}

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		String versionHistoryControlName =
			PortletDataHandlerControl.getNamespacedControlName(
				"journal", "version-history");

		if ((parameterMap.get(versionHistoryControlName) != null) &&
			!portletDataContext.getBooleanParameter(
				"journal", "version-history")) {

			JournalArticle latestArticle =
				_journalArticleLocalService.fetchLatestArticle(
					article.getResourcePrimKey(), getExportableStatuses());

			if ((latestArticle != null) &&
				(latestArticle.getId() != article.getId())) {

				return;
			}
		}

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

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			article.getGroupId(), _portal.getClassNameId(JournalArticle.class),
			article.getDDMStructureKey(), true);

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, article, ddmStructure,
			PortletDataContext.REFERENCE_TYPE_STRONG);

		if ((article.getClassNameId() != _portal.getClassNameId(
				DDMStructure.class)) &&
			Validator.isNotNull(article.getDDMTemplateKey())) {

			DDMTemplate ddmTemplate = _ddmTemplateLocalService.getTemplate(
				article.getGroupId(),
				_portal.getClassNameId(DDMStructure.class),
				article.getDDMTemplateKey(), true);

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, article, ddmTemplate,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}

		Layout layout = article.getLayout();

		if (layout != null) {
			portletDataContext.addReferenceElement(
				article, articleElement, layout,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY_DISPOSABLE, true);
		}

		if (article.isSmallImage()) {
			if (Validator.isNotNull(article.getSmallImageURL())) {
				String smallImageURL =
					_journalArticleExportImportContentProcessor.
						replaceExportContentReferences(
							portletDataContext, article,
							article.getSmallImageURL() + StringPool.SPACE, true,
							false);

				article.setSmallImageURL(smallImageURL);
			}
			else {
				Image smallImage = _imageLocalService.fetchImage(
					article.getSmallImageId());

				if ((smallImage != null) && (smallImage.getTextObj() != null)) {
					String smallImagePath = ExportImportPathUtil.getModelPath(
						article,
						smallImage.getImageId() + StringPool.PERIOD +
							smallImage.getType());

					articleElement.addAttribute(
						"small-image-path", smallImagePath);

					article.setSmallImageType(smallImage.getType());

					portletDataContext.addZipEntry(
						smallImagePath, smallImage.getTextObj());
				}
				else {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler(4);

						sb.append("Unable to export small image ");
						sb.append(article.getSmallImageId());
						sb.append(" to article ");
						sb.append(article.getArticleId());

						_log.warn(sb.toString());
					}

					article.setSmallImage(false);
					article.setSmallImageId(0);
				}
			}
		}

		JournalArticle latestArticle =
			_journalArticleLocalService.fetchLatestArticle(
				article.getResourcePrimKey());

		if ((latestArticle != null) &&
			(latestArticle.getId() == article.getId())) {

			for (FileEntry fileEntry : article.getImagesFileEntries()) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, article, fileEntry,
					PortletDataContext.REFERENCE_TYPE_WEAK);
			}
		}

		article.setStatusByUserUuid(article.getStatusByUserUuid());

		String content =
			_journalArticleExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, article, article.getContent(),
					portletDataContext.getBooleanParameter(
						"journal", "referenced-content"),
					false);

		article.setContent(content);

		long defaultUserId = _userLocalService.getDefaultUserId(
			article.getCompanyId());

		if (isPreloadedArticle(defaultUserId, article)) {
			articleElement.addAttribute("preloaded", "true");
		}

		_exportAssetDisplayPage(portletDataContext, article);

		_exportFriendlyURLEntries(portletDataContext, article);

		portletDataContext.addClassedModel(
			articleElement, ExportImportPathUtil.getModelPath(article),
			article);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, Element referenceElement)
		throws PortletDataException {

		importMissingGroupReference(portletDataContext, referenceElement);

		String uuid = referenceElement.attributeValue("uuid");
		String articleResourceUuid = referenceElement.attributeValue(
			"article-resource-uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		groupId = MapUtil.getLong(groupIds, groupId);

		String articleArticleId = referenceElement.attributeValue("article-id");
		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		JournalArticle existingArticle;

		if (!preloaded) {
			existingArticle = fetchMissingReference(uuid, groupId);
		}
		else {
			existingArticle = fetchExistingArticleWithParentGroups(
				uuid, articleResourceUuid, groupId, articleArticleId, null, 0.0,
				preloaded);
		}

		if (existingArticle == null) {
			return;
		}

		Map<String, String> articleArticleIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class + ".articleId");

		articleArticleIds.put(articleArticleId, existingArticle.getArticleId());

		Map<String, Long> articleGroupIds =
			(Map<String, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class + ".groupId");

		articleGroupIds.put(articleArticleId, existingArticle.getGroupId());

		Map<Long, Long> articlePrimaryKeys =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class + ".primaryKey");

		long classPK = GetterUtil.getLong(
			referenceElement.attributeValue("class-pk"));

		articlePrimaryKeys.put(classPK, existingArticle.getPrimaryKey());

		long articleResourcePrimKey = GetterUtil.getLong(
			referenceElement.attributeValue("resource-prim-key"));

		if (articleResourcePrimKey != 0) {
			Map<Long, Long> articleRecourcePrimKeys =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					JournalArticle.class);

			articleRecourcePrimKeys.put(
				articleResourcePrimKey, existingArticle.getResourcePrimKey());
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, JournalArticle article)
		throws Exception {

		long userId = portletDataContext.getUserId(article.getUserUuid());

		long authorId = _journalCreationStrategy.getAuthorUserId(
			portletDataContext, article);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		User user = _userLocalService.getUser(userId);

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalFolder.class);

		long folderId = MapUtil.getLong(
			folderIds, article.getFolderId(), article.getFolderId());

		String articleId = article.getArticleId();

		boolean autoArticleId = false;

		List<JournalArticle> articles = _journalArticleLocalService.getArticles(
			portletDataContext.getScopeGroupId(), articleId);

		if (!articles.isEmpty()) {
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

		content =
			_journalArticleExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, article, content);

		article.setContent(content);

		String newContent = _journalCreationStrategy.getTransformedContent(
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

		Map<String, String> ddmStructureKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class + ".ddmStructureKey");

		String parentDDMStructureKey = MapUtil.getString(
			ddmStructureKeys, article.getDDMStructureKey(),
			article.getDDMStructureKey());

		long ddmStructureId = 0;

		if (article.getClassNameId() != 0) {
			Map<String, Long> ddmStructureIds =
				(Map<String, Long>)portletDataContext.getNewPrimaryKeysMap(
					DDMStructure.class);

			ddmStructureId = ddmStructureIds.get(article.getClassPK());
		}

		Map<String, String> ddmTemplateKeys =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				DDMTemplate.class + ".ddmTemplateKey");

		String parentDDMTemplateKey = MapUtil.getString(
			ddmTemplateKeys, article.getDDMTemplateKey(),
			article.getDDMTemplateKey());

		File smallFile = null;

		try {
			Element articleElement =
				portletDataContext.getImportDataStagedModelElement(article);

			if (article.isSmallImage()) {
				String smallImagePath = articleElement.attributeValue(
					"small-image-path");

				if (Validator.isNotNull(article.getSmallImageURL())) {
					String smallImageURL =
						_journalArticleExportImportContentProcessor.
							replaceImportContentReferences(
								portletDataContext, article,
								article.getSmallImageURL());

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

			JournalArticle latestArticle =
				_journalArticleLocalService.fetchLatestArticle(
					article.getResourcePrimKey());

			if ((latestArticle != null) &&
				(latestArticle.getId() == article.getId())) {

				List<Element> attachmentElements =
					portletDataContext.getReferenceDataElements(
						article, DLFileEntry.class,
						PortletDataContext.REFERENCE_TYPE_WEAK);

				for (Element attachmentElement : attachmentElements) {
					String path = attachmentElement.attributeValue("path");

					FileEntry fileEntry =
						(FileEntry)portletDataContext.getZipEntryAsObject(path);

					InputStream inputStream = null;

					try {
						String binPath = attachmentElement.attributeValue(
							"bin-path");

						if (Validator.isNull(binPath) &&
							portletDataContext.isPerformDirectBinaryImport()) {

							try {
								inputStream = FileEntryUtil.getContentStream(
									fileEntry);
							}
							catch (NoSuchFileException nsfe) {
								if (_log.isDebugEnabled()) {
									_log.debug(
										"Unable to import attachment for " +
											"file entry " +
												fileEntry.getFileEntryId(),
										nsfe);
								}
							}
						}
						else {
							inputStream =
								portletDataContext.getZipEntryAsInputStream(
									binPath);
						}

						if (inputStream == null) {
							if (_log.isWarnEnabled()) {
								_log.warn(
									"Unable to import attachment for file " +
										"entry " + fileEntry.getFileEntryId());
							}
						}
					}
					finally {
						StreamUtil.cleanUp(inputStream);
					}
				}
			}

			Map<Locale, String> friendlyURLMap = article.getFriendlyURLMap();

			friendlyURLMap.forEach(
				(locale, url) -> friendlyURLMap.replace(
					locale, _http.decodeURL(url)));

			String articleURL = null;

			boolean addGroupPermissions =
				_journalCreationStrategy.addGroupPermissions(
					portletDataContext, article);
			boolean addGuestPermissions =
				_journalCreationStrategy.addGuestPermissions(
					portletDataContext, article);

			ServiceContext serviceContext =
				portletDataContext.createServiceContext(article);

			serviceContext.setAddGroupPermissions(addGroupPermissions);
			serviceContext.setAddGuestPermissions(addGuestPermissions);

			if ((expirationDate != null) && expirationDate.before(new Date())) {
				article.setStatus(WorkflowConstants.STATUS_EXPIRED);
			}

			if ((article.getStatus() != WorkflowConstants.STATUS_APPROVED) &&
				(article.getStatus() != WorkflowConstants.STATUS_SCHEDULED)) {

				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_SAVE_DRAFT);
			}

			JournalArticle importedArticle = null;

			// Used when importing LARs with journal schemas under 1.1.0

			_setLegacyValues(article);

			if (portletDataContext.isDataStrategyMirror()) {
				serviceContext.setUuid(article.getUuid());

				String articleResourceUuid = articleElement.attributeValue(
					"article-resource-uuid");

				serviceContext.setAttribute(
					"articleResourceUuid", articleResourceUuid);

				serviceContext.setAttribute("urlTitle", article.getUrlTitle());

				boolean preloaded = GetterUtil.getBoolean(
					articleElement.attributeValue("preloaded"));

				JournalArticle existingArticle = fetchExistingArticle(
					articleResourceUuid, portletDataContext.getScopeGroupId(),
					articleId, newArticleId, preloaded);

				JournalArticle existingArticleVersion = null;

				if (existingArticle != null) {
					existingArticleVersion = fetchExistingArticleVersion(
						article.getUuid(), portletDataContext.getScopeGroupId(),
						existingArticle.getArticleId(), article.getVersion());
				}

				if ((existingArticle != null) &&
					(existingArticleVersion == null)) {

					autoArticleId = false;
					articleId = existingArticle.getArticleId();
				}

				if (existingArticleVersion == null) {
					importedArticle = _journalArticleLocalService.addArticle(
						userId, portletDataContext.getScopeGroupId(), folderId,
						article.getClassNameId(), ddmStructureId, articleId,
						autoArticleId, article.getVersion(),
						article.getTitleMap(), article.getDescriptionMap(),
						friendlyURLMap, article.getContent(),
						parentDDMStructureKey, parentDDMTemplateKey,
						article.getLayoutUuid(), displayDateMonth,
						displayDateDay, displayDateYear, displayDateHour,
						displayDateMinute, expirationDateMonth,
						expirationDateDay, expirationDateYear,
						expirationDateHour, expirationDateMinute, neverExpire,
						reviewDateMonth, reviewDateDay, reviewDateYear,
						reviewDateHour, reviewDateMinute, neverReview,
						article.isIndexable(), article.isSmallImage(),
						article.getSmallImageURL(), smallFile, null, articleURL,
						serviceContext);
				}
				else {
					importedArticle = _journalArticleLocalService.updateArticle(
						userId, existingArticle.getGroupId(), folderId,
						existingArticle.getArticleId(), article.getVersion(),
						article.getTitleMap(), article.getDescriptionMap(),
						friendlyURLMap, article.getContent(),
						parentDDMStructureKey, parentDDMTemplateKey,
						article.getLayoutUuid(), displayDateMonth,
						displayDateDay, displayDateYear, displayDateHour,
						displayDateMinute, expirationDateMonth,
						expirationDateDay, expirationDateYear,
						expirationDateHour, expirationDateMinute, neverExpire,
						reviewDateMonth, reviewDateDay, reviewDateYear,
						reviewDateHour, reviewDateMinute, neverReview,
						article.isIndexable(), article.isSmallImage(),
						article.getSmallImageURL(), smallFile, null, articleURL,
						serviceContext);

					String articleUuid = article.getUuid();
					String importedArticleUuid = importedArticle.getUuid();

					if (!articleUuid.equals(importedArticleUuid)) {
						importedArticle.setUuid(articleUuid);

						_journalArticleLocalService.updateJournalArticle(
							importedArticle);
					}
				}
			}
			else {
				importedArticle = _journalArticleLocalService.addArticle(
					userId, portletDataContext.getScopeGroupId(), folderId,
					article.getClassNameId(), ddmStructureId, articleId,
					autoArticleId, article.getVersion(), article.getTitleMap(),
					article.getDescriptionMap(), friendlyURLMap,
					article.getContent(), parentDDMStructureKey,
					parentDDMTemplateKey, article.getLayoutUuid(),
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, reviewDateMonth,
					reviewDateDay, reviewDateYear, reviewDateHour,
					reviewDateMinute, neverReview, article.isIndexable(),
					article.isSmallImage(), article.getSmallImageURL(),
					smallFile, null, articleURL, serviceContext);
			}

			_journalArticleLocalService.updateAsset(
				userId, importedArticle, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(),
				serviceContext.getAssetLinkEntryIds(),
				serviceContext.getAssetPriority());

			serviceContext.setModifiedDate(importedArticle.getModifiedDate());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			try {

				// Clean up initial publication

				if (ExportImportThreadLocal.isInitialLayoutStagingInProcess() &&
					(article.getStatus() == WorkflowConstants.STATUS_DRAFT)) {

					_journalArticleLocalService.deleteArticle(article);
				}

				boolean exportVersionHistory =
					portletDataContext.getBooleanParameter(
						"journal", "version-history");

				if (!ExportImportThreadLocal.isStagingInProcess() ||
					!exportVersionHistory) {

					updateArticleVersions(importedArticle);
				}

				if (Validator.isNull(newArticleId)) {
					articleIds.put(
						article.getArticleId(), importedArticle.getArticleId());
				}

				Map<Long, Long> articlePrimaryKeys =
					(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
						JournalArticle.class + ".primaryKey");

				articlePrimaryKeys.put(
					article.getPrimaryKey(), importedArticle.getPrimaryKey());

				_importAssetDisplayPage(
					portletDataContext, article, importedArticle);

				_importFriendlyURLEntries(
					portletDataContext, article, importedArticle);

				_sendUndeliveredUserNotificationEvents(article);
			}
			finally {
				ServiceContextThreadLocal.popServiceContext();
			}

			portletDataContext.importClassedModel(article, importedArticle);
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

		boolean preloaded = GetterUtil.getBoolean(
			articleElement.attributeValue("preloaded"));

		JournalArticle existingArticle = fetchExistingArticle(
			article.getUuid(), articleResourceUuid,
			portletDataContext.getScopeGroupId(), article.getArticleId(),
			article.getArticleId(), article.getVersion(), preloaded);

		if ((existingArticle == null) || !existingArticle.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = existingArticle.getTrashHandler();

		if (trashHandler.isRestorable(existingArticle.getResourcePrimKey())) {
			trashHandler.restoreTrashEntry(
				userId, existingArticle.getResourcePrimKey());
		}
	}

	protected JournalArticle fetchExistingArticle(
		String articleResourceUuid, long groupId, String articleId,
		String newArticleId, boolean preloaded) {

		JournalArticleResource journalArticleResource =
			_journalArticleResourceLocalService.
				fetchJournalArticleResourceByUuidAndGroupId(
					articleResourceUuid, groupId);

		if (journalArticleResource != null) {
			return _journalArticleLocalService.fetchLatestArticle(
				journalArticleResource.getResourcePrimKey());
		}

		if (!preloaded) {
			return null;
		}

		JournalArticle existingArticle = null;

		if (Validator.isNotNull(newArticleId)) {
			existingArticle = _journalArticleLocalService.fetchArticle(
				groupId, newArticleId);
		}

		if ((existingArticle == null) && Validator.isNull(newArticleId)) {
			existingArticle = _journalArticleLocalService.fetchArticle(
				groupId, articleId);
		}

		return existingArticle;
	}

	protected JournalArticle fetchExistingArticle(
		String articleUuid, String articleResourceUuid, long groupId,
		String articleId, String newArticleId, double version,
		boolean preloaded) {

		JournalArticle article = fetchExistingArticle(
			articleResourceUuid, groupId, articleId, newArticleId, preloaded);

		if (article != null) {
			article = fetchExistingArticleVersion(
				article.getUuid(), groupId, article.getArticleId(), version);
		}

		return article;
	}

	protected JournalArticle fetchExistingArticleVersion(
		String articleUuid, long groupId, String articleId, double version) {

		JournalArticle existingArticle = fetchStagedModelByUuidAndGroupId(
			articleUuid, groupId);

		if (existingArticle != null) {
			return existingArticle;
		}

		if (version == 0.0) {
			return _journalArticleLocalService.fetchArticle(groupId, articleId);
		}

		return _journalArticleLocalService.fetchArticle(
			groupId, articleId, version);
	}

	protected JournalArticle fetchExistingArticleWithParentGroups(
		String articleUuid, String articleResourceUuid, long groupId,
		String articleId, String newArticleId, double version,
		boolean preloaded) {

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return null;
		}

		long companyId = group.getCompanyId();

		while (group != null) {
			JournalArticle article = fetchExistingArticle(
				articleUuid, articleResourceUuid, group.getGroupId(), articleId,
				newArticleId, version, preloaded);

			if (article != null) {
				return article;
			}

			group = group.getParentGroup();
		}

		Group companyGroup = _groupLocalService.fetchCompanyGroup(companyId);

		if (companyGroup == null) {
			return null;
		}

		return fetchExistingArticle(
			articleUuid, articleResourceUuid, companyGroup.getGroupId(),
			articleId, newArticleId, version, preloaded);
	}

	@Override
	protected String[] getSkipImportReferenceStagedModelNames() {
		return new String[] {
			AssetDisplayPageEntry.class.getName(),
			FriendlyURLEntry.class.getName()
		};
	}

	protected boolean isExpireAllArticleVersions(long companyId)
		throws PortalException {

		JournalServiceConfiguration journalServiceConfiguration =
			_configurationProvider.getCompanyConfiguration(
				JournalServiceConfiguration.class, companyId);

		return journalServiceConfiguration.expireAllArticleVersionsEnabled();
	}

	protected boolean isPreloadedArticle(
		long defaultUserId, JournalArticle article) {

		if (defaultUserId == article.getUserId()) {
			return true;
		}

		JournalArticle firstArticle = _journalArticleLocalService.fetchArticle(
			article.getGroupId(), article.getArticleId(),
			JournalArticleConstants.VERSION_DEFAULT);

		if ((firstArticle != null) &&
			(defaultUserId == firstArticle.getUserId())) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setConfigurationProvider(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	@Reference(unbind = "-")
	protected void setImageLocalService(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleResourceLocalService(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {

		_journalArticleResourceLocalService =
			journalArticleResourceLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalCreationStrategy(
		JournalCreationStrategy journalCreationStrategy) {

		_journalCreationStrategy = journalCreationStrategy;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected void updateArticleVersions(JournalArticle article)
		throws PortalException {

		boolean expireAllArticleVersions = isExpireAllArticleVersions(
			article.getCompanyId());

		List<JournalArticle> articles = _journalArticleLocalService.getArticles(
			article.getGroupId(), article.getArticleId());

		for (JournalArticle curArticle : articles) {
			boolean update = false;

			if ((article.isApproved() || article.isExpired()) &&
				(article.getExpirationDate() != null) &&
				expireAllArticleVersions &&
				!Objects.equals(
					curArticle.getExpirationDate(),
					article.getExpirationDate())) {

				Date expirationDate = article.getExpirationDate();

				curArticle.setExpirationDate(expirationDate);

				if (expirationDate.before(new Date())) {
					curArticle.setStatus(WorkflowConstants.STATUS_EXPIRED);
				}

				update = true;
			}

			if (curArticle.getFolderId() != article.getFolderId()) {
				curArticle.setFolderId(article.getFolderId());
				curArticle.setTreePath(article.getTreePath());

				update = true;
			}

			if (update) {
				_journalArticleLocalService.updateJournalArticle(curArticle);
			}
		}
	}

	private void _exportAssetDisplayPage(
			PortletDataContext portletDataContext, JournalArticle article)
		throws PortletDataException {

		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				article.getGroupId(),
				_portal.getClassNameId(JournalArticle.class),
				article.getResourcePrimKey());

		if (assetDisplayPageEntry != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, article, assetDisplayPageEntry,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
	}

	private void _exportFriendlyURLEntries(
			PortletDataContext portletDataContext, JournalArticle article)
		throws PortletDataException {

		long classNameId = _portal.getClassNameId(JournalArticle.class);

		List<FriendlyURLEntry> friendlyURLEntries =
			_friendlyURLEntryLocalService.getFriendlyURLEntries(
				article.getGroupId(), classNameId,
				article.getResourcePrimKey());

		for (FriendlyURLEntry friendlyURLEntry : friendlyURLEntries) {
			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, friendlyURLEntry);

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, article, friendlyURLEntry,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
	}

	private void _importAssetDisplayPage(
			PortletDataContext portletDataContext, JournalArticle article,
			JournalArticle importedArticle)
		throws PortalException {

		List<Element> assetDisplayPageEntryElements =
			portletDataContext.getReferenceDataElements(
				article, AssetDisplayPageEntry.class);

		Map<Long, Long> articleNewPrimaryKeys =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class);

		articleNewPrimaryKeys.put(
			article.getResourcePrimKey(), importedArticle.getResourcePrimKey());

		for (Element assetDisplayPageEntryElement :
				assetDisplayPageEntryElements) {

			String path = assetDisplayPageEntryElement.attributeValue("path");

			AssetDisplayPageEntry assetDisplayPageEntry =
				(AssetDisplayPageEntry)portletDataContext.getZipEntryAsObject(
					path);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, assetDisplayPageEntryElement);

			Map<Long, Long> assetDisplayPageEntries =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					AssetDisplayPageEntry.class);

			long assetDisplayPageEntryId = MapUtil.getLong(
				assetDisplayPageEntries,
				assetDisplayPageEntry.getAssetDisplayPageEntryId(),
				assetDisplayPageEntry.getAssetDisplayPageEntryId());

			AssetDisplayPageEntry existingAssetDisplayPageEntry =
				_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
					assetDisplayPageEntryId);

			if (existingAssetDisplayPageEntry != null) {
				existingAssetDisplayPageEntry.setClassPK(
					importedArticle.getResourcePrimKey());

				_assetDisplayPageEntryLocalService.updateAssetDisplayPageEntry(
					existingAssetDisplayPageEntry);
			}
		}
	}

	private void _importFriendlyURLEntries(
			PortletDataContext portletDataContext, JournalArticle article,
			JournalArticle importedArticle)
		throws PortalException {

		List<Element> friendlyURLEntryElements =
			portletDataContext.getReferenceDataElements(
				article, FriendlyURLEntry.class);

		Map<Long, Long> articleNewPrimaryKeys =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class);

		articleNewPrimaryKeys.put(
			article.getResourcePrimKey(), importedArticle.getResourcePrimKey());

		for (Element friendlyURLEntryElement : friendlyURLEntryElements) {
			String path = friendlyURLEntryElement.attributeValue("path");

			FriendlyURLEntry friendlyURLEntry =
				(FriendlyURLEntry)portletDataContext.getZipEntryAsObject(path);

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, friendlyURLEntryElement);

			Map<Long, Long> friendlyURLEntries =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					FriendlyURLEntry.class);

			long friendlyURLEntryId = MapUtil.getLong(
				friendlyURLEntries, friendlyURLEntry.getFriendlyURLEntryId(),
				friendlyURLEntry.getFriendlyURLEntryId());

			FriendlyURLEntry existingFriendlyURLEntry =
				_friendlyURLEntryLocalService.fetchFriendlyURLEntry(
					friendlyURLEntryId);

			if (existingFriendlyURLEntry != null) {
				existingFriendlyURLEntry.setClassPK(
					importedArticle.getResourcePrimKey());

				_friendlyURLEntryLocalService.updateFriendlyURLEntry(
					existingFriendlyURLEntry);
			}
		}
	}

	private void _sendUndeliveredUserNotificationEvents(
		JournalArticle article) {

		ActionableDynamicQuery actionableDynamicQuery =
			_userNotificationEventLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property delivered = PropertyFactoryUtil.forName("delivered");

				dynamicQuery.add(delivered.eq(false));

				Property payload = PropertyFactoryUtil.forName("payload");

				dynamicQuery.add(
					payload.like(
						StringPool.PERCENT + article.getId() +
							StringPool.PERCENT));
			});
		actionableDynamicQuery.setCompanyId(article.getCompanyId());
		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<UserNotificationEvent>)
				userNotificationEvent -> {
					userNotificationEvent.setDelivered(true);

					_userNotificationEventLocalService.
						updateUserNotificationEvent(userNotificationEvent);

					JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
						userNotificationEvent.getPayload());

					SubscriptionSender subscriptionSender =
						new SubscriptionSender();

					subscriptionSender.setClassName(
						jsonObject.getString("className"));
					subscriptionSender.setClassPK(
						jsonObject.getLong("classPK"));
					subscriptionSender.setCompanyId(
						userNotificationEvent.getCompanyId());

					Map<String, HashMap<String, Object>> contextMap =
						(Map)JSONFactoryUtil.looseDeserialize(
							String.valueOf(jsonObject.get("context")));

					contextMap.forEach(
						(key, value) -> subscriptionSender.setContextAttribute(
							key, value.get("originalValue")));

					JournalGroupServiceConfiguration
						journalGroupServiceConfiguration =
							_configurationProvider.getConfiguration(
								JournalGroupServiceConfiguration.class,
								new GroupServiceSettingsLocator(
									article.getGroupId(),
									JournalConstants.SERVICE_NAME));

					String fromAddress =
						journalGroupServiceConfiguration.emailFromAddress();

					String fromName =
						journalGroupServiceConfiguration.emailFromName();

					subscriptionSender.setFrom(fromAddress, fromName);

					subscriptionSender.setHtmlFormat(true);

					Map<String, String> localizedJsonBodyMap =
						(Map)JSONFactoryUtil.looseDeserialize(
							String.valueOf(jsonObject.get("localizedBodyMap")));

					Map<Locale, String> localizedBodyMap = new HashMap<>();

					for (Map.Entry<String, String> entry :
							localizedJsonBodyMap.entrySet()) {

						localizedBodyMap.put(
							LocaleUtil.fromLanguageId(entry.getKey()),
							entry.getValue());
					}

					subscriptionSender.setLocalizedBodyMap(localizedBodyMap);

					Map<String, String> localizedJsonSubjectMap =
						(Map)JSONFactoryUtil.looseDeserialize(
							String.valueOf(
								jsonObject.get("localizedSubjectMap")));

					Map<Locale, String> localizedSubjectMap = new HashMap<>();

					for (Map.Entry<String, String> entry :
							localizedJsonSubjectMap.entrySet()) {

						localizedSubjectMap.put(
							LocaleUtil.fromLanguageId(entry.getKey()),
							entry.getValue());
					}

					subscriptionSender.setLocalizedSubjectMap(
						localizedSubjectMap);

					subscriptionSender.setMailId(
						"journal_article", article.getId());
					subscriptionSender.setNotificationType(
						jsonObject.getInt("notificationType"));
					subscriptionSender.setPortletId(
						jsonObject.getString("portletId"));
					subscriptionSender.setReplyToAddress(fromAddress);

					try {
						subscriptionSender.sendEmailNotification(
							userNotificationEvent.getUserId());
					}
					catch (Exception e) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to send email notification for " +
									"article " + article.getArticleId());
						}
					}
				});

		try {
			actionableDynamicQuery.performActions();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to send email notification for article " +
						article.getArticleId());
			}
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), only used for backwards compatibility
	 *             with LARs that use journal schema under 1.1.0
	 */
	@Deprecated
	private void _setLegacyValues(JournalArticle article) {
		if (MapUtil.isEmpty(article.getTitleMap()) &&
			Validator.isNotNull(article.getLegacyTitle())) {

			article.setTitleMap(
				LocalizationUtil.getLocalizationMap(article.getLegacyTitle()));
		}

		if (MapUtil.isEmpty(article.getDescriptionMap()) &&
			Validator.isNotNull(article.getLegacyDescription())) {

			article.setDescriptionMap(
				LocalizationUtil.getLocalizationMap(
					article.getLegacyDescription()));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleStagedModelDataHandler.class);

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private ChangesetCollectionLocalService _changesetCollectionLocalService;

	@Reference
	private ChangesetEntryLocalService _changesetEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private ConfigurationProvider _configurationProvider;
	private DDMStructureLocalService _ddmStructureLocalService;
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Http _http;

	private ImageLocalService _imageLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.journal.model.JournalArticle)"
	)
	private volatile ExportImportContentProcessor<String>
		_journalArticleExportImportContentProcessor;

	private JournalArticleLocalService _journalArticleLocalService;
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;
	private JournalCreationStrategy _journalCreationStrategy;

	@Reference
	private Portal _portal;

	private UserLocalService _userLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}