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

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.model.AssetDisplayPageEntryTable;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.display.page.util.AssetDisplayPageUtil;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.diff.DiffHtml;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.exception.StructureDefinitionException;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.expando.kernel.util.ExpandoBridgeUtil;
import com.liferay.exportimport.kernel.exception.ExportImportContentValidationException;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryLocalizationException;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.info.item.InfoItemReference;
import com.liferay.journal.configuration.JournalGroupServiceConfiguration;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.constants.JournalActivityKeys;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.content.compatibility.converter.JournalContentCompatibilityConverter;
import com.liferay.journal.exception.ArticleExpirationDateException;
import com.liferay.journal.exception.ArticleFriendlyURLException;
import com.liferay.journal.exception.ArticleReviewDateException;
import com.liferay.journal.exception.ArticleVersionException;
import com.liferay.journal.exception.DuplicateArticleIdException;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.exception.RequiredArticleLocalizationException;
import com.liferay.journal.internal.util.JournalTreePathUtil;
import com.liferay.journal.internal.util.JournalUtil;
import com.liferay.journal.internal.validation.JournalArticleModelValidator;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.model.JournalArticleLocalization;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.model.JournalArticleTable;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.impl.JournalArticleDisplayImpl;
import com.liferay.journal.model.impl.JournalArticleImpl;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.journal.service.JournalContentSearchLocalService;
import com.liferay.journal.service.base.JournalArticleLocalServiceBaseImpl;
import com.liferay.journal.service.persistence.JournalArticleLocalizationPersistence;
import com.liferay.journal.service.persistence.JournalArticleResourcePersistence;
import com.liferay.journal.service.persistence.JournalFolderPersistence;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.JournalDefaultTemplateProvider;
import com.liferay.journal.util.JournalHelper;
import com.liferay.journal.util.comparator.ArticleIDComparator;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.xml.XMLUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.portlet.constants.FriendlyURLResolverConstants;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.sanitizer.SanitizerUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.SystemEventLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.social.SocialActivityManagerUtil;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.systemevent.SystemEventHierarchyEntryThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupSubscriptionCheckSubscriptionSender;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.MathUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.validation.ModelValidator;
import com.liferay.portal.validation.ModelValidatorRegistryUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.trash.TrashHelper;
import com.liferay.trash.exception.RestoreEntryException;
import com.liferay.trash.exception.TrashEntryException;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.model.TrashVersion;
import com.liferay.trash.service.TrashEntryLocalService;
import com.liferay.trash.service.TrashVersionLocalService;
import com.liferay.upload.AttachmentContentUpdater;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the local service for accessing, adding, deleting, and updating web
 * content articles.
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Bruno Farache
 * @author Juan Fernández
 * @author Sergio González
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = AopService.class
)
public class JournalArticleLocalServiceImpl
	extends JournalArticleLocalServiceBaseImpl {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addArticle(String, long, long, long, long, long, String,
	 *             boolean, double, Map, Map, Map, String, String, String,
	 *             String, int, int, int, int, int, int, int, int, int, int,
	 *             boolean, int, int, int, int, int, boolean, boolean, boolean,
	 *             String, File, Map, String, ServiceContext)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
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

		return addArticle(
			null, userId, groupId, folderId, classNameId, classPK, articleId,
			autoArticleId, version, titleMap, descriptionMap, friendlyURLMap,
			content, ddmStructureKey, ddmTemplateKey, layoutUuid,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallImageFile, images, articleURL,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addArticle(String, long, long, long, long, long, String,
	 *             boolean, double, Map, Map, Map, String, String, String,
	 *             String, int, int, int, int, int, int, int, int, int, int,
	 *             boolean, int, int, int, int, int, boolean, boolean, boolean,
	 *             String, File, Map, String, ServiceContext)}
	 */
	@Deprecated
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

		return journalArticleLocalService.addArticle(
			null, userId, groupId, folderId, classNameId, classPK, articleId,
			autoArticleId, version, titleMap, descriptionMap, titleMap, content,
			ddmStructureKey, ddmTemplateKey, layoutUuid, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, indexable, smallImage, smallImageURL,
			smallImageFile, images, articleURL, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addArticle(String, long, long, long, Map, Map, String,
	 *             String, String, ServiceContext)}
	 */
	@Deprecated
	public JournalArticle addArticle(
			long userId, long groupId, long folderId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String content, String ddmStructureKey, String ddmTemplateKey,
			ServiceContext serviceContext)
		throws PortalException {

		return addArticle(
			null, userId, groupId, folderId, titleMap, descriptionMap, content,
			ddmStructureKey, ddmTemplateKey, serviceContext);
	}

	/**
	 * Adds a web content article with additional parameters. All scheduling
	 * parameters (display date, expiration date, and review date) use the
	 * current user's timezone.
	 *
	 * <p>
	 * The web content articles hold HTML content wrapped in XML. The XML lets
	 * you specify the article's default locale and available locales. Here is a
	 * content example:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * &lt;?xml version='1.0' encoding='UTF-8'?&gt;
	 * &lt;root default-locale="en_US" available-locales="en_US"&gt;
	 * 	&lt;static-content language-id="en_US"&gt;
	 * 		&lt;![CDATA[&lt;p&gt;&lt;b&gt;&lt;i&gt;test&lt;i&gt; content&lt;b&gt;&lt;/p&gt;]]&gt;
	 * 	&lt;/static-content&gt;
	 * &lt;/root&gt;
	 * </code>
	 * </pre></p>
	 *
	 * @param  externalReferenceCode the external reference code of the web
	 *         content article
	 * @param  userId the primary key of the web content article's creator/owner
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or
	 *         JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 *         module otherwise
	 * @param  classPK the primary key of the DDM structure, if the primary key
	 *         of the DDMStructure class is given as the
	 *         <code>classNameId</code> parameter, the primary key of the class
	 *         associated with the web content article, or <code>0</code>
	 *         otherwise
	 * @param  articleId the primary key of the web content article
	 * @param  autoArticleId whether to auto generate the web content article ID
	 * @param  version the web content article's version
	 * @param  titleMap the web content article's locales and localized titles
	 * @param  descriptionMap the web content article's locales and localized
	 *         descriptions
	 * @param  friendlyURLMap the web content article's locales and localized
	 *         friendly URLs
	 * @param  content the HTML content wrapped in XML
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  layoutUuid the unique string identifying the web content
	 *         article's display page
	 * @param  displayDateMonth the month the web content article is set to
	 *         display
	 * @param  displayDateDay the calendar day the web content article is set to
	 *         display
	 * @param  displayDateYear the year the web content article is set to
	 *         display
	 * @param  displayDateHour the hour the web content article is set to
	 *         display
	 * @param  displayDateMinute the minute the web content article is set to
	 *         display
	 * @param  expirationDateMonth the month the web content article is set to
	 *         expire
	 * @param  expirationDateDay the calendar day the web content article is set
	 *         to expire
	 * @param  expirationDateYear the year the web content article is set to
	 *         expire
	 * @param  expirationDateHour the hour the web content article is set to
	 *         expire
	 * @param  expirationDateMinute the minute the web content article is set to
	 *         expire
	 * @param  neverExpire whether the web content article is not set to auto
	 *         expire
	 * @param  reviewDateMonth the month the web content article is set for
	 *         review
	 * @param  reviewDateDay the calendar day the web content article is set for
	 *         review
	 * @param  reviewDateYear the year the web content article is set for review
	 * @param  reviewDateHour the hour the web content article is set for review
	 * @param  reviewDateMinute the minute the web content article is set for
	 *         review
	 * @param  neverReview whether the web content article is not set for review
	 * @param  indexable whether the web content article is searchable
	 * @param  smallImage whether the web content article has a small image
	 * @param  smallImageURL the web content article's small image URL
	 * @param  smallImageFile the web content article's small image file
	 * @param  images the web content's images
	 * @param  articleURL the web content article's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, expando bridge
	 *         attributes, guest permissions, group permissions, asset category
	 *         IDs, asset tag names, asset link entry IDs, URL title, and
	 *         workflow actions for the web content article. Can also set
	 *         whether to add the default guest and group permissions.
	 * @return the web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public JournalArticle addArticle(
			String externalReferenceCode, long userId, long groupId,
			long folderId, long classNameId, long classPK, String articleId,
			boolean autoArticleId, double version, Map<Locale, String> titleMap,
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

		// Article

		User user = _userLocalService.getUser(userId);

		articleId = StringUtil.toUpperCase(StringUtil.trim(articleId));

		Date displayDate = _portal.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(), null);

		Date expirationDate = null;
		Date reviewDate = null;

		if (!neverExpire) {
			expirationDate = _portal.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				ArticleExpirationDateException.class);
		}

		if (!neverReview) {
			reviewDate = _portal.getDate(
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, user.getTimeZone(),
				ArticleReviewDateException.class);
		}

		byte[] smallImageBytes = null;

		try {
			smallImageBytes = FileUtil.getBytes(smallImageFile);
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}
		}

		boolean validate = !ExportImportThreadLocal.isImportInProcess();

		if (validate) {
			validateDDMStructureId(groupId, folderId, ddmStructureKey);
		}

		if (autoArticleId) {
			articleId = String.valueOf(counterLocalService.increment());
		}

		sanitize(user.getCompanyId(), groupId, userId, classPK, descriptionMap);

		if (validate) {
			validate(
				externalReferenceCode, user.getCompanyId(), groupId,
				classNameId, articleId, autoArticleId, version, titleMap,
				content, ddmStructureKey, ddmTemplateKey, displayDate,
				expirationDate, smallImage, smallImageURL, smallImageFile,
				smallImageBytes, serviceContext);

			try {
				validateReferences(
					groupId, ddmStructureKey, ddmTemplateKey, layoutUuid,
					smallImage, smallImageURL, smallImageBytes, 0, content);
			}
			catch (ExportImportContentValidationException
						exportImportContentValidationException) {

				exportImportContentValidationException.setStagedModelClassName(
					JournalArticle.class.getName());
				exportImportContentValidationException.
					setStagedModelPrimaryKeyObj(articleId);

				throw exportImportContentValidationException;
			}
		}

		serviceContext.setAttribute("articleId", articleId);

		long id = counterLocalService.increment();

		String articleResourceUuid = GetterUtil.getString(
			serviceContext.getAttribute("articleResourceUuid"));

		long resourcePrimKey =
			_journalArticleResourceLocalService.getArticleResourcePrimKey(
				articleResourceUuid, groupId, articleId);

		JournalArticle article = journalArticlePersistence.create(id);

		Locale locale = getArticleDefaultLocale(content);

		friendlyURLMap = _checkFriendlyURLMap(locale, friendlyURLMap, titleMap);

		Map<String, String> urlTitleMap = _getURLTitleMap(
			groupId, resourcePrimKey, friendlyURLMap, titleMap);

		article.setUuid(serviceContext.getUuid());
		article.setResourcePrimKey(resourcePrimKey);
		article.setGroupId(groupId);
		article.setCompanyId(user.getCompanyId());
		article.setUserId(user.getUserId());
		article.setUserName(user.getFullName());
		article.setExternalReferenceCode(externalReferenceCode);
		article.setFolderId(folderId);
		article.setClassNameId(classNameId);
		article.setClassPK(classPK);
		article.setTreePath(article.buildTreePath());
		article.setArticleId(articleId);
		article.setVersion(version);
		article.setUrlTitle(urlTitleMap.get(LocaleUtil.toLanguageId(locale)));
		article.setDDMStructureKey(ddmStructureKey);
		article.setDDMTemplateKey(ddmTemplateKey);
		article.setDefaultLanguageId(LocaleUtil.toLanguageId(locale));
		article.setLayoutUuid(layoutUuid);
		article.setDisplayDate(displayDate);
		article.setExpirationDate(expirationDate);
		article.setReviewDate(reviewDate);
		article.setIndexable(indexable);
		article.setSmallImage(smallImage);

		if (smallImage) {
			article.setSmallImageId(counterLocalService.increment());
		}
		else {
			article.setSmallImageId(0);
		}

		article.setSmallImageURL(smallImageURL);

		Date date = new Date();

		if ((expirationDate == null) || expirationDate.after(date)) {
			article.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			article.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		article.setStatusByUserId(userId);
		article.setStatusByUserName(user.getFullName());
		article.setStatusDate(serviceContext.getModifiedDate(date));
		article.setExpandoBridgeAttributes(serviceContext);

		article = journalArticlePersistence.update(article);

		// Friendly URLs

		updateFriendlyURLs(article, urlTitleMap, serviceContext);

		// Article localization

		_addArticleLocalizedFields(
			user.getCompanyId(), article.getId(), titleMap, descriptionMap);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addArticleResources(
				article, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addArticleResources(article, serviceContext.getModelPermissions());
		}

		// Small image

		saveImages(
			article.getCompanyId(), smallImage, article.getSmallImageId(),
			smallImageFile, smallImageBytes);

		// Asset

		updateAsset(
			userId, article, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		// Dynamic data mapping

		updateDDMFields(
			article, _formatContent(article, content, groupId, user));

		if (_classNameLocalService.getClassNameId(DDMStructure.class) !=
				classNameId) {

			updateDDMLinks(id, groupId, ddmStructureKey, ddmTemplateKey, true);
		}

		// Email

		articleURL = buildArticleURL(articleURL, groupId, folderId, articleId);

		serviceContext.setAttribute("articleURL", articleURL);

		sendEmail(article, articleURL, "requested", serviceContext);

		// Workflow

		startWorkflowInstance(userId, article, serviceContext);

		return article;
	}

	/**
	 * Adds a web content article.
	 *
	 * @param  externalReferenceCode the external reference code of the web
	 *         content article.
	 * @param  userId the primary key of the web content article's creator/owner
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @param  titleMap the web content article's locales and localized titles
	 * @param  descriptionMap the web content article's locales and localized
	 *         descriptions
	 * @param  content the HTML content wrapped in XML. For more information,
	 *         see the content example in the {@link #addArticle(String, long,
	 *         long, long, long, long, String, boolean, double, Map, Map, Map,
	 *         String, String, String, String, int, int, int, int, int, int,
	 *         int, int, int, int, boolean, int, int, int, int, int, boolean,
	 *         boolean, boolean, String, File, Map, String, ServiceContext)}
	 *         description.
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  serviceContext the service context to be applied. Can set the
	 *         UUID, creation date, modification date, expando bridge
	 *         attributes, guest permissions, group permissions, asset category
	 *         IDs, asset tag names, asset link entry IDs, asset priority, URL
	 *         title, and workflow actions for the web content article. Can also
	 *         set whether to add the default guest and group permissions.
	 * @return the web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle addArticle(
			String externalReferenceCode, long userId, long groupId,
			long folderId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String content,
			String ddmStructureKey, String ddmTemplateKey,
			ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		Calendar calendar = CalendarFactoryUtil.getCalendar(user.getTimeZone());

		int displayDateMonth = calendar.get(Calendar.MONTH);
		int displayDateDay = calendar.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = calendar.get(Calendar.YEAR);
		int displayDateHour = calendar.get(Calendar.HOUR_OF_DAY);
		int displayDateMinute = calendar.get(Calendar.MINUTE);

		return journalArticleLocalService.addArticle(
			externalReferenceCode, userId, groupId, folderId,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, 1, titleMap, descriptionMap, titleMap, content,
			ddmStructureKey, ddmTemplateKey, null, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true, true, false, null, null,
			null, null, serviceContext);
	}

	@Override
	public JournalArticle addArticleDefaultValues(
			long userId, long groupId, long classNameId, long classPK,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String content, String ddmStructureKey, String ddmTemplateKey,
			String layoutUuid, boolean indexable, boolean smallImage,
			String smallImageURL, File smallImageFile,
			ServiceContext serviceContext)
		throws PortalException {

		// Article

		User user = _userLocalService.getUser(userId);

		byte[] smallImageBytes = null;

		try {
			smallImageBytes = FileUtil.getBytes(smallImageFile);
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}
		}

		String articleId = String.valueOf(counterLocalService.increment());

		String externalReferenceCode = articleId;

		sanitize(user.getCompanyId(), groupId, userId, classPK, descriptionMap);

		validate(
			externalReferenceCode, user.getCompanyId(), groupId, classNameId,
			articleId, true, 0, titleMap, content, ddmStructureKey,
			ddmTemplateKey, null, null, smallImage, smallImageURL,
			smallImageFile, smallImageBytes, serviceContext);

		serviceContext.setAttribute("articleId", articleId);

		long id = counterLocalService.increment();

		JournalArticle article = journalArticlePersistence.create(id);

		article.setUuid(serviceContext.getUuid());

		String articleResourceUuid = GetterUtil.getString(
			serviceContext.getAttribute("articleResourceUuid"));

		article.setResourcePrimKey(
			_journalArticleResourceLocalService.getArticleResourcePrimKey(
				articleResourceUuid, groupId, articleId));

		article.setGroupId(groupId);
		article.setCompanyId(user.getCompanyId());
		article.setUserId(user.getUserId());
		article.setUserName(user.getFullName());
		article.setExternalReferenceCode(externalReferenceCode);
		article.setClassNameId(classNameId);
		article.setClassPK(classPK);
		article.setArticleId(articleId);
		article.setDDMStructureKey(ddmStructureKey);
		article.setDDMTemplateKey(ddmTemplateKey);

		Locale locale = getArticleDefaultLocale(content);

		article.setDefaultLanguageId(LocaleUtil.toLanguageId(locale));

		article.setLayoutUuid(layoutUuid);
		article.setIndexable(indexable);
		article.setSmallImage(smallImage);
		article.setSmallImageId(counterLocalService.increment());
		article.setSmallImageURL(smallImageURL);
		article.setStatus(WorkflowConstants.STATUS_APPROVED);
		article.setStatusByUserId(userId);
		article.setStatusDate(serviceContext.getModifiedDate(new Date()));
		article.setExpandoBridgeAttributes(serviceContext);

		article = journalArticlePersistence.update(article);

		// Article localization

		_addArticleLocalizedFields(
			user.getCompanyId(), article.getId(), titleMap, descriptionMap);

		// Resources

		addArticleResources(article, serviceContext.getModelPermissions());

		// Small image

		saveImages(
			article.getCompanyId(), smallImage, article.getSmallImageId(),
			smallImageFile, smallImageBytes);

		// Asset

		updateAsset(
			userId, article, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		// Dynamic data mapping

		content = _formatContent(article, content, groupId, user);

		updateDDMFields(article, content);

		return journalArticlePersistence.findByPrimaryKey(article.getId());
	}

	/**
	 * Adds the resources to the web content article.
	 *
	 * @param  article the web content article
	 * @param  addGroupPermissions whether to add group permissions
	 * @param  addGuestPermissions whether to add guest permissions
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void addArticleResources(
			JournalArticle article, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		_resourceLocalService.addResources(
			article.getCompanyId(), article.getGroupId(), article.getUserId(),
			JournalArticle.class.getName(), article.getResourcePrimKey(), false,
			addGroupPermissions, addGuestPermissions);
	}

	/**
	 * Adds the model resources with the permissions to the web content article.
	 *
	 * @param  article the web content article to add resources to
	 * @param  modelPermissions the model permissions
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void addArticleResources(
			JournalArticle article, ModelPermissions modelPermissions)
		throws PortalException {

		_resourceLocalService.addModelResources(
			article.getCompanyId(), article.getGroupId(), article.getUserId(),
			JournalArticle.class.getName(), article.getResourcePrimKey(),
			modelPermissions);
	}

	/**
	 * Adds the resources to the most recently created web content article.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  addGroupPermissions whether to add group permissions
	 * @param  addGuestPermissions whether to add guest permissions
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void addArticleResources(
			long groupId, String articleId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		addArticleResources(article, addGroupPermissions, addGuestPermissions);
	}

	/**
	 * Returns the web content article with the group, article ID, and version.
	 * This method checks for the article's resource primary key and, if not
	 * found, creates a new one.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @return the matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle checkArticleResourcePrimKey(
			long groupId, String articleId, double version)
		throws PortalException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		if (article.getResourcePrimKey() > 0) {
			return article;
		}

		article.setResourcePrimKey(
			_journalArticleResourceLocalService.getArticleResourcePrimKey(
				groupId, articleId));

		return journalArticlePersistence.update(article);
	}

	/**
	 * Checks all web content articles by handling their expirations and sending
	 * review notifications based on their current workflow.
	 */
	@Override
	public void checkArticles() throws PortalException {
		Date date = new Date();

		checkArticlesByExpirationDate(date);

		checkArticlesByReviewDate(date);

		checkArticlesByDisplayDate(date);

		_previousCheckDate = date;
	}

	/**
	 * Checks the web content article matching the group, article ID, and
	 * version for an associated structure. If no structure is associated,
	 * return; otherwise check that the article and structure match.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void checkStructure(long groupId, String articleId, double version)
		throws PortalException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		checkStructure(article);
	}

	/**
	 * Copies the web content article matching the group, article ID, and
	 * version. This method creates a new article, extracting all the values
	 * from the old one and updating its article ID.
	 *
	 * @param  userId the primary key of the web content article's creator/owner
	 * @param  groupId the primary key of the web content article's group
	 * @param  oldArticleId the primary key of the old web content article
	 * @param  newArticleId the primary key of the new web content article
	 * @param  autoArticleId whether to auto-generate the web content article ID
	 * @param  version the web content article's version
	 * @return the new web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public JournalArticle copyArticle(
			long userId, long groupId, String oldArticleId, String newArticleId,
			boolean autoArticleId, double version)
		throws PortalException {

		// Article

		oldArticleId = StringUtil.toUpperCase(StringUtil.trim(oldArticleId));
		newArticleId = StringUtil.toUpperCase(StringUtil.trim(newArticleId));

		JournalArticle oldArticle = journalArticlePersistence.findByG_A_V(
			groupId, oldArticleId, version);

		if (autoArticleId) {
			newArticleId = String.valueOf(counterLocalService.increment());
		}
		else {
			validate(newArticleId);

			if (journalArticlePersistence.countByG_A(groupId, newArticleId) >
					0) {

				throw new DuplicateArticleIdException(
					StringBundler.concat(
						"{groupId=", groupId, ", articleId=", newArticleId,
						"}"));
			}
		}

		User user = _userLocalService.getUser(userId);

		long id = counterLocalService.increment();

		long resourcePrimKey =
			_journalArticleResourceLocalService.getArticleResourcePrimKey(
				groupId, newArticleId);

		JournalArticle newArticle = journalArticlePersistence.create(id);

		newArticle.setResourcePrimKey(resourcePrimKey);
		newArticle.setGroupId(groupId);
		newArticle.setCompanyId(user.getCompanyId());
		newArticle.setUserId(user.getUserId());
		newArticle.setUserName(user.getFullName());

		Date modifiedDate = new Date();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (serviceContext == null) {
			serviceContext = new ServiceContext();
		}

		modifiedDate = serviceContext.getModifiedDate(modifiedDate);

		newArticle.setModifiedDate(modifiedDate);

		newArticle.setExternalReferenceCode(newArticleId);
		newArticle.setFolderId(oldArticle.getFolderId());
		newArticle.setTreePath(oldArticle.getTreePath());
		newArticle.setArticleId(newArticleId);
		newArticle.setVersion(JournalArticleConstants.VERSION_DEFAULT);
		newArticle.setDDMStructureKey(oldArticle.getDDMStructureKey());
		newArticle.setDDMTemplateKey(oldArticle.getDDMTemplateKey());
		newArticle.setDefaultLanguageId(oldArticle.getDefaultLanguageId());
		newArticle.setLayoutUuid(oldArticle.getLayoutUuid());
		newArticle.setDisplayDate(oldArticle.getDisplayDate());
		newArticle.setExpirationDate(oldArticle.getExpirationDate());
		newArticle.setReviewDate(oldArticle.getReviewDate());
		newArticle.setIndexable(oldArticle.isIndexable());
		newArticle.setSmallImage(oldArticle.isSmallImage());
		newArticle.setSmallImageId(counterLocalService.increment());
		newArticle.setSmallImageURL(oldArticle.getSmallImageURL());

		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(
				JournalArticle.class.getName());

		WorkflowDefinitionLink workflowDefinitionLink =
			workflowHandler.getWorkflowDefinitionLink(
				oldArticle.getCompanyId(), oldArticle.getGroupId(),
				oldArticle.getId());

		if (oldArticle.isPending() || (workflowDefinitionLink != null)) {
			newArticle.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			newArticle.setStatus(oldArticle.getStatus());
		}

		newArticle.setStatusByUserId(user.getUserId());
		newArticle.setStatusByUserName(user.getFullName());
		newArticle.setStatusDate(modifiedDate);

		int uniqueUrlTitleCount = _getUniqueUrlTitleCount(
			groupId, newArticleId,
			JournalUtil.getUrlTitle(id, oldArticle.getUrlTitle()));

		Map<Locale, String> newTitleMap = oldArticle.getTitleMap();
		Map<Locale, String> newUniqueURLTitleMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : newTitleMap.entrySet()) {
			Locale locale = entry.getKey();

			String urlTitle = StringBundler.concat(
				entry.getValue(), StringPool.SPACE,
				LanguageUtil.get(locale, "duplicate"), StringPool.SPACE,
				uniqueUrlTitleCount);

			newTitleMap.put(locale, urlTitle);
			newUniqueURLTitleMap.put(
				locale, getUniqueUrlTitle(id, groupId, newArticleId, urlTitle));
		}

		Locale locale = getArticleDefaultLocale(oldArticle.getContent());

		String newURLTitle = newUniqueURLTitleMap.get(locale);

		while (fetchArticleByUrlTitle(groupId, newURLTitle) != null) {
			newURLTitle = getUniqueUrlTitle(
				id, groupId, newArticleId, newURLTitle);
		}

		newArticle.setUrlTitle(newURLTitle);

		ExpandoBridgeUtil.copyExpandoBridgeAttributes(
			oldArticle.getExpandoBridge(), newArticle.getExpandoBridge());

		newArticle = journalArticlePersistence.update(newArticle);

		// Article localization

		Map<Locale, String> friendlyURLMap = _checkFriendlyURLMap(
			locale, new HashMap(), newTitleMap);

		Map<String, String> newUrlTitleMap = _getURLTitleMap(
			groupId, resourcePrimKey, friendlyURLMap, newUniqueURLTitleMap);

		updateFriendlyURLs(newArticle, newUrlTitleMap, serviceContext);

		_addArticleLocalizedFields(
			newArticle.getCompanyId(), newArticle.getId(), newTitleMap,
			oldArticle.getDescriptionMap());

		// Resources

		_resourceLocalService.copyModelResources(
			oldArticle.getCompanyId(), JournalArticle.class.getName(),
			oldArticle.getResourcePrimKey(), resourcePrimKey);

		// Small image

		if (oldArticle.isSmallImage()) {
			Image image = _imageLocalService.fetchImage(
				oldArticle.getSmallImageId());

			if (image != null) {
				byte[] smallImageBytes = image.getTextObj();

				_imageLocalService.updateImage(
					newArticle.getCompanyId(), newArticle.getSmallImageId(),
					smallImageBytes);
			}
		}

		// Asset

		long[] assetCategoryIds = _assetCategoryLocalService.getCategoryIds(
			JournalArticle.class.getName(), oldArticle.getResourcePrimKey());
		String[] assetTagNames = _assetTagLocalService.getTagNames(
			JournalArticle.class.getName(), oldArticle.getResourcePrimKey());

		AssetEntry oldAssetEntry = _assetEntryLocalService.getEntry(
			JournalArticle.class.getName(), oldArticle.getResourcePrimKey());

		List<AssetLink> assetLinks = _assetLinkLocalService.getDirectLinks(
			oldAssetEntry.getEntryId(), false);

		long[] assetLinkEntryIds = ListUtil.toLongArray(
			assetLinks, AssetLink.ENTRY_ID2_ACCESSOR);

		updateAsset(
			userId, newArticle, assetCategoryIds, assetTagNames,
			assetLinkEntryIds, oldAssetEntry.getPriority());

		AssetDisplayPageEntry assetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				groupId, _portal.getClassNameId(JournalArticle.class.getName()),
				oldArticle.getResourcePrimKey());

		if (assetDisplayPageEntry != null) {
			_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
				userId, groupId,
				_portal.getClassNameId(JournalArticle.class.getName()),
				newArticle.getResourcePrimKey(),
				assetDisplayPageEntry.getLayoutPageTemplateEntryId(),
				assetDisplayPageEntry.getType(), serviceContext);
		}

		// Dynamic data mapping

		updateDDMFields(newArticle, copyArticleImages(oldArticle, newArticle));

		updateDDMLinks(
			id, groupId, oldArticle.getDDMStructureKey(),
			oldArticle.getDDMTemplateKey(), true);

		return newArticle;
	}

	/**
	 * Deletes the web content article and its resources.
	 *
	 * @param  article the web content article
	 * @return the deleted web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP, send = false,
		type = SystemEventConstants.TYPE_DELETE
	)
	public JournalArticle deleteArticle(JournalArticle article)
		throws PortalException {

		return journalArticleLocalService.deleteArticle(
			article, StringPool.BLANK, null);
	}

	/**
	 * Deletes the web content article and its resources, optionally sending
	 * email notifying denial of the article if it had not yet been approved.
	 *
	 * @param  article the web content article
	 * @param  articleURL the web content article's accessible URL to include in
	 *         email notifications (optionally <code>null</code>)
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set the portlet preferences that include
	 *         email information to notify recipients of the unapproved web
	 *         content's denial.
	 * @return the deleted web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP, send = false,
		type = SystemEventConstants.TYPE_DELETE
	)
	public JournalArticle deleteArticle(
			JournalArticle article, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticleResource articleResource =
			_journalArticleResourceLocalService.fetchArticleResource(
				article.getGroupId(), article.getArticleId());

		if (article.isApproved() &&
			isLatestVersion(
				article.getGroupId(), article.getArticleId(),
				article.getVersion(), WorkflowConstants.STATUS_APPROVED)) {

			updatePreviousApprovedArticle(article);
		}

		// Article localization

		_journalArticleLocalizationPersistence.removeByArticlePK(
			article.getId());

		// Asset

		if (article.isDraft() || article.isInTrash()) {
			_assetEntryLocalService.deleteEntry(
				JournalArticle.class.getName(), article.getPrimaryKey());
		}

		// Email

		if ((serviceContext != null) && Validator.isNotNull(articleURL) &&
			!article.isApproved() &&
			isLatestVersion(
				article.getGroupId(), article.getArticleId(),
				article.getVersion())) {

			articleURL = buildArticleURL(
				articleURL, article.getGroupId(), article.getFolderId(),
				article.getArticleId());

			sendEmail(article, articleURL, "denied", serviceContext);
		}

		// Dynamic data mapping

		_ddmFieldLocalService.deleteDDMFormValues(article.getId());

		if (article.getClassNameId() != _classNameLocalService.getClassNameId(
				DDMStructure.class)) {

			ddmStorageLinkLocalService.deleteClassStorageLink(article.getId());

			ddmStructureLinkLocalService.deleteStructureLinks(
				_classNameLocalService.getClassNameId(JournalArticle.class),
				article.getId());

			ddmTemplateLinkLocalService.deleteTemplateLink(
				_classNameLocalService.getClassNameId(JournalArticle.class),
				article.getId());
		}

		// Expando

		_expandoRowLocalService.deleteRows(
			article.getCompanyId(),
			_classNameLocalService.getClassNameId(JournalArticle.class),
			article.getId());

		// Trash

		if (article.isInTrash() && (article.getTrashEntry() != null)) {
			_trashVersionLocalService.deleteTrashVersion(
				JournalArticle.class.getName(), article.getId());
		}

		// Workflow

		if (!article.isDraft()) {
			_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
				article.getCompanyId(), article.getGroupId(),
				JournalArticle.class.getName(), article.getId());
		}

		int articlesCount = journalArticlePersistence.countByG_A(
			article.getGroupId(), article.getArticleId());

		if (articlesCount == 1) {

			// Asset

			_assetEntryLocalService.deleteEntry(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Comment

			_commentManager.deleteDiscussion(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Content searches

			_journalContentSearchLocalService.deleteArticleContentSearches(
				article.getGroupId(), article.getArticleId());

			// Friendly URL

			List<FriendlyURLEntry> friendlyURLEntries =
				friendlyURLEntryLocalService.getFriendlyURLEntries(
					article.getGroupId(),
					_classNameLocalService.getClassNameId(JournalArticle.class),
					article.getResourcePrimKey());

			if (!friendlyURLEntries.isEmpty()) {
				friendlyURLEntryLocalService.deleteFriendlyURLEntry(
					article.getGroupId(), JournalArticle.class,
					article.getResourcePrimKey());
			}

			// Images

			long folderId = article.getImagesFolderId();

			if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				_portletFileRepository.deletePortletFolder(folderId);
			}

			// Ratings

			_ratingsStatsLocalService.deleteStats(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Small image

			_imageLocalService.deleteImage(article.getSmallImageId());

			// Trash

			_trashEntryLocalService.deleteEntry(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Resources

			_resourceLocalService.deleteResource(
				article.getCompanyId(), JournalArticle.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				article.getResourcePrimKey());

			// Resource

			if (articleResource != null) {
				_journalArticleResourceLocalService.
					deleteJournalArticleResource(articleResource);
			}
		}

		// Article

		journalArticlePersistence.remove(article);

		// System event

		if (articleResource != null) {
			_systemEventLocalService.addSystemEvent(
				0, article.getGroupId(), article.getModelClassName(),
				article.getPrimaryKey(), articleResource.getUuid(), null,
				SystemEventConstants.TYPE_DELETE,
				JSONUtil.put(
					"uuid", article.getUuid()
				).put(
					"version", article.getVersion()
				).toString());
		}

		return article;
	}

	/**
	 * Deletes the web content article and its resources matching the group,
	 * article ID, and version, optionally sending email notifying denial of the
	 * web content article if it had not yet been approved.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  articleURL the web content article's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         portlet preferences that include email information to notify
	 *         recipients of the unapproved web content article's denial.
	 * @return the deleted web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle deleteArticle(
			long groupId, String articleId, double version, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		return journalArticleLocalService.deleteArticle(
			article, articleURL, serviceContext);
	}

	/**
	 * Deletes all web content articles and their resources matching the group
	 * and article ID, optionally sending email notifying denial of article if
	 * it had not yet been approved.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  serviceContext the service context to be applied. Can set the
	 *         portlet preferences that include email information to notify
	 *         recipients of the unapproved web content article's denial.
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteArticle(
			long groupId, String articleId, ServiceContext serviceContext)
		throws PortalException {

		SystemEventHierarchyEntryThreadLocal.push(JournalArticle.class);

		JournalArticleResource articleResource =
			_journalArticleResourceLocalService.fetchArticleResource(
				groupId, articleId);

		try {
			List<JournalArticle> articles = journalArticlePersistence.findByG_A(
				groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new ArticleVersionComparator(true));

			for (JournalArticle article : articles) {
				journalArticleLocalService.deleteArticle(
					article, null, serviceContext);
			}
		}
		finally {
			SystemEventHierarchyEntryThreadLocal.pop(JournalArticle.class);
		}

		if (articleResource != null) {
			_systemEventLocalService.addSystemEvent(
				0, groupId, JournalArticle.class.getName(),
				articleResource.getResourcePrimKey(), articleResource.getUuid(),
				null, SystemEventConstants.TYPE_DELETE, StringPool.BLANK);
		}
	}

	@Override
	public void deleteArticleDefaultValues(
			long groupId, String articleId, String ddmStructureKey)
		throws PortalException {

		// Dynamic data mapping

		_deleteDDMStructurePredefinedValues(groupId, ddmStructureKey);

		journalArticleLocalService.deleteArticle(groupId, articleId, null);
	}

	/**
	 * Deletes all the group's web content articles and resources.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteArticles(long groupId) throws PortalException {
		SystemEventHierarchyEntryThreadLocal.push(JournalArticle.class);

		List<JournalArticleResource> articleResources = new ArrayList<>();

		try {
			JournalArticleResource articleResource = null;

			for (JournalArticle article :
					journalArticlePersistence.findByGroupId(groupId)) {

				if ((articleResource == null) ||
					(articleResource.getPrimaryKey() !=
						article.getResourcePrimKey())) {

					articleResources.add(
						_journalArticleResourceLocalService.getArticleResource(
							article.getResourcePrimKey()));
				}

				journalArticleLocalService.deleteArticle(article, null, null);
			}
		}
		finally {
			SystemEventHierarchyEntryThreadLocal.pop(JournalArticle.class);
		}

		for (JournalArticleResource articleResource : articleResources) {
			_systemEventLocalService.addSystemEvent(
				0, groupId, JournalArticle.class.getName(),
				articleResource.getResourcePrimKey(), articleResource.getUuid(),
				null, SystemEventConstants.TYPE_DELETE, StringPool.BLANK);
		}
	}

	/**
	 * Deletes all the group's web content articles and resources in the folder,
	 * including recycled articles.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteArticles(long groupId, long folderId)
		throws PortalException {

		deleteArticles(groupId, folderId, true);
	}

	/**
	 * Deletes all the group's web content articles and resources in the folder,
	 * optionally including recycled articles.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @param  includeTrashedEntries whether to include recycled web content
	 *         articles
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteArticles(
			long groupId, long folderId, boolean includeTrashedEntries)
		throws PortalException {

		SystemEventHierarchyEntryThreadLocal.push(JournalArticle.class);

		List<JournalArticleResource> articleResources = new ArrayList<>();

		try {
			JournalArticleResource articleResource = null;

			for (JournalArticle article :
					journalArticlePersistence.findByG_F(groupId, folderId)) {

				if ((articleResource == null) ||
					(articleResource.getPrimaryKey() !=
						article.getResourcePrimKey())) {

					articleResource =
						_journalArticleResourceLocalService.getArticleResource(
							article.getResourcePrimKey());

					articleResources.add(articleResource);
				}

				if (includeTrashedEntries || !article.isInTrashExplicitly()) {
					journalArticleLocalService.deleteArticle(
						article, null, null);
				}
				else {
					articleResources.remove(articleResource);
				}
			}
		}
		finally {
			SystemEventHierarchyEntryThreadLocal.pop(JournalArticle.class);
		}

		for (JournalArticleResource articleResource : articleResources) {
			_systemEventLocalService.addSystemEvent(
				0, groupId, JournalArticle.class.getName(),
				articleResource.getResourcePrimKey(), articleResource.getUuid(),
				null, SystemEventConstants.TYPE_DELETE, StringPool.BLANK);
		}
	}

	/**
	 * Deletes all the group's web content articles and resources matching the
	 * class name and class primary key.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  className the DDMStructure class name if the web content article
	 *         is related to a DDM structure, the primary key of the class name
	 *         associated with the article, or
	 *         JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 *         module otherwise
	 * @param  classPK the primary key of the DDM structure, if the DDMStructure
	 *         class name is given as the <code>className</code> parameter, the
	 *         primary key of the class associated with the web content article,
	 *         or <code>0</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void deleteArticles(long groupId, String className, long classPK)
		throws PortalException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_C_C(
			groupId, _classNameLocalService.getClassNameId(className), classPK);

		for (JournalArticle article : articles) {
			journalArticleLocalService.deleteArticle(article, null, null);
		}
	}

	/**
	 * Deletes the layout's association with the web content articles for the
	 * group.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param layoutUuid the unique string identifying the web content article's
	 *        display page
	 */
	@Override
	public void deleteLayoutArticleReferences(long groupId, String layoutUuid) {
		List<JournalArticle> articles = journalArticlePersistence.findByG_L(
			groupId, layoutUuid);

		for (JournalArticle article : articles) {
			article.setLayoutUuid(StringPool.BLANK);

			journalArticlePersistence.update(article);
		}
	}

	/**
	 * Expires the web content article matching the group, article ID, and
	 * version.
	 *
	 * @param  userId the primary key of the user updating the web content
	 *         article
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  articleURL the web content article's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, status date, portlet preferences, and can set
	 *         whether to add the default command update for the web content
	 *         article. With respect to social activities, by setting the
	 *         service context's command to {@link Constants#UPDATE}, the
	 *         invocation is considered a web content update activity; otherwise
	 *         it is considered a web content add activity.
	 * @return the web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public JournalArticle expireArticle(
			long userId, long groupId, String articleId, double version,
			String articleURL, ServiceContext serviceContext)
		throws PortalException {

		return updateStatus(
			userId, groupId, articleId, version,
			WorkflowConstants.STATUS_EXPIRED, articleURL, new HashMap<>(),
			serviceContext);
	}

	/**
	 * Expires the web content article matching the group and article ID,
	 * expiring all of its versions if the
	 * <code>journal.article.expire.all.versions</code> portal property is
	 * <code>true</code>, otherwise expiring only its latest approved version.
	 *
	 * @param  userId the primary key of the user updating the web content
	 *         article
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  articleURL the web content article's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, status date, portlet preferences, and can set
	 *         whether to add the default command update for the web content
	 *         article. With respect to social activities, by setting the
	 *         service context's command to {@link Constants#UPDATE}, the
	 *         invocation is considered a web content update activity; otherwise
	 *         it is considered a web content add activity.
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void expireArticle(
			long userId, long groupId, String articleId, String articleURL,
			ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		if (isExpireAllArticleVersions(user.getCompanyId())) {
			List<JournalArticle> articles = journalArticlePersistence.findByG_A(
				groupId, articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new ArticleVersionComparator(true));

			for (JournalArticle article : articles) {
				if (!article.isExpired()) {
					journalArticleLocalService.expireArticle(
						userId, groupId, article.getArticleId(),
						article.getVersion(), articleURL, serviceContext);
				}
			}
		}
		else {
			JournalArticle article = getLatestArticle(
				groupId, articleId, WorkflowConstants.STATUS_APPROVED);

			journalArticleLocalService.expireArticle(
				userId, groupId, article.getArticleId(), article.getVersion(),
				articleURL, serviceContext);
		}
	}

	/**
	 * Returns the web content article with the ID.
	 *
	 * @param  id the primary key of the web content article
	 * @return the web content article with the ID
	 */
	@Override
	public JournalArticle fetchArticle(long id) {
		return journalArticlePersistence.fetchByPrimaryKey(id);
	}

	@Override
	public JournalArticle fetchArticle(long groupId, String articleId) {

		// Get the latest article that is approved, if none are approved, get
		// the latest unapproved article

		JournalArticle article = fetchLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_APPROVED);

		if (article != null) {
			return article;
		}

		return fetchLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_ANY);
	}

	/**
	 * Returns the web content article matching the group, article ID, and
	 * version.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @return the web content article matching the group, article ID, and
	 *         version, or <code>null</code> if no web content article could be
	 *         found
	 */
	@Override
	public JournalArticle fetchArticle(
		long groupId, String articleId, double version) {

		return journalArticlePersistence.fetchByG_A_V(
			groupId, articleId, version);
	}

	@Override
	public JournalArticle fetchArticleByUrlTitle(
		long groupId, String urlTitle) {

		JournalArticle article = fetchLatestArticleByUrlTitle(
			groupId, urlTitle, WorkflowConstants.STATUS_APPROVED);

		if (article != null) {
			return article;
		}

		return fetchLatestArticleByUrlTitle(
			groupId, urlTitle, WorkflowConstants.STATUS_ANY);
	}

	@Override
	public JournalArticle fetchArticleByUrlTitle(
		long groupId, String urlTitle, double version) {

		FriendlyURLEntry friendlyURLEntry =
			friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				groupId, JournalArticle.class, urlTitle);

		if (friendlyURLEntry != null) {
			JournalArticle article = fetchLatestArticle(
				friendlyURLEntry.getClassPK(), WorkflowConstants.STATUS_ANY,
				true);

			if (article.getVersion() == version) {
				return article;
			}
		}

		JournalArticle article = fetchLatestArticleByUrlTitle(
			groupId, urlTitle, WorkflowConstants.STATUS_APPROVED);

		if ((article != null) && (article.getVersion() == version)) {
			return article;
		}

		article = fetchLatestArticleByUrlTitle(
			groupId, urlTitle, WorkflowConstants.STATUS_ANY);

		if ((article != null) && (article.getVersion() == version)) {
			return article;
		}
		else if (article != null) {
			return fetchArticle(
				article.getGroupId(), article.getArticleId(), version);
		}

		return null;
	}

	@Override
	public JournalArticle fetchDisplayArticle(long groupId, String articleId) {
		List<JournalArticle> articles = journalArticlePersistence.findByG_A_ST(
			groupId, articleId, WorkflowConstants.STATUS_APPROVED);

		if (articles.isEmpty()) {
			return null;
		}

		Date date = new Date();

		for (JournalArticle article : articles) {
			Date displayDate = article.getDisplayDate();
			Date expirationDate = article.getExpirationDate();

			if (((displayDate == null) || displayDate.before(date)) &&
				((expirationDate == null) || expirationDate.after(date))) {

				return article;
			}
		}

		return articles.get(0);
	}

	@Override
	public JournalArticle fetchLatestArticle(long resourcePrimKey) {
		return fetchLatestArticle(
			resourcePrimKey, WorkflowConstants.STATUS_ANY);
	}

	@Override
	public JournalArticle fetchLatestArticle(long resourcePrimKey, int status) {
		return fetchLatestArticle(resourcePrimKey, status, true);
	}

	/**
	 * Returns the latest web content article matching the resource primary key
	 * and workflow status, optionally preferring articles with approved
	 * workflow status.
	 *
	 * @param  resourcePrimKey the primary key of the resource instance
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  preferApproved whether to prefer returning the latest matching
	 *         article that has workflow status {@link
	 *         WorkflowConstants#STATUS_APPROVED} over returning one that has a
	 *         different status
	 * @return the latest web content article matching the resource primary key
	 *         and workflow status, optionally preferring articles with an
	 *         approved workflow status, or <code>null</code> if no matching web
	 *         content article could be found
	 */
	@Override
	public JournalArticle fetchLatestArticle(
		long resourcePrimKey, int status, boolean preferApproved) {

		JournalArticle article = null;

		OrderByComparator<JournalArticle> orderByComparator =
			new ArticleVersionComparator();

		if (status == WorkflowConstants.STATUS_ANY) {
			if (preferApproved) {
				article = journalArticlePersistence.fetchByR_ST_First(
					resourcePrimKey, WorkflowConstants.STATUS_APPROVED,
					orderByComparator);
			}

			if (article == null) {
				article =
					journalArticlePersistence.fetchByResourcePrimKey_First(
						resourcePrimKey, orderByComparator);
			}
		}
		else {
			article = journalArticlePersistence.fetchByR_ST_First(
				resourcePrimKey, status, orderByComparator);
		}

		return article;
	}

	@Override
	public JournalArticle fetchLatestArticle(
		long resourcePrimKey, int[] statuses) {

		OrderByComparator<JournalArticle> orderByComparator =
			new ArticleVersionComparator();

		List<JournalArticle> articles = journalArticlePersistence.findByR_ST(
			resourcePrimKey, statuses, 0, 1, orderByComparator);

		if (!articles.isEmpty()) {
			return articles.get(0);
		}

		return null;
	}

	/**
	 * Returns the latest web content article matching the group, article ID,
	 * and workflow status.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @return the latest matching web content article, or <code>null</code> if
	 *         no matching web content article could be found
	 */
	@Override
	public JournalArticle fetchLatestArticle(
		long groupId, String articleId, int status) {

		OrderByComparator<JournalArticle> orderByComparator =
			new ArticleVersionComparator();

		if (status == WorkflowConstants.STATUS_ANY) {
			return journalArticlePersistence.fetchByG_A_NotST_First(
				groupId, articleId, WorkflowConstants.STATUS_IN_TRASH,
				orderByComparator);
		}

		return journalArticlePersistence.fetchByG_A_ST_First(
			groupId, articleId, status, orderByComparator);
	}

	/**
	 * Returns the latest web content article matching the group and the
	 * external reference code.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  externalReferenceCode the web content article's external
	 *         reference code
	 * @return the latest matching web content article, or <code>null</code> if
	 *         no matching web content article could be found
	 */
	@Override
	public JournalArticle fetchLatestArticleByExternalReferenceCode(
		long groupId, String externalReferenceCode) {

		OrderByComparator<JournalArticle> orderByComparator =
			new ArticleVersionComparator();

		return journalArticlePersistence.fetchByG_ERC_First(
			groupId, externalReferenceCode, orderByComparator);
	}

	@Override
	public JournalArticle fetchLatestArticleByUrlTitle(
		long groupId, String urlTitle, int status) {

		FriendlyURLEntry friendlyURLEntry =
			friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				groupId, JournalArticle.class, urlTitle);

		if (friendlyURLEntry != null) {
			JournalArticle article = fetchLatestArticle(
				friendlyURLEntry.getClassPK(), status);

			if ((article != null) && (article.getGroupId() != groupId)) {
				article = fetchLatestArticle(
					groupId, article.getArticleId(), status);
			}

			return article;
		}

		List<JournalArticle> articles = null;

		OrderByComparator<JournalArticle> orderByComparator =
			new ArticleVersionComparator();

		if (status == WorkflowConstants.STATUS_ANY) {
			articles = journalArticlePersistence.findByG_UT(
				groupId, _friendlyURLNormalizer.normalizeWithEncoding(urlTitle),
				0, 1, orderByComparator);
		}
		else {
			articles = journalArticlePersistence.findByG_UT_ST(
				groupId, _friendlyURLNormalizer.normalizeWithEncoding(urlTitle),
				status, 0, 1, orderByComparator);
		}

		if (articles.isEmpty()) {
			return null;
		}

		return articles.get(0);
	}

	/**
	 * Returns the latest indexable web content article matching the resource
	 * primary key.
	 *
	 * @param  resourcePrimKey the primary key of the resource instance
	 * @return the latest indexable web content article matching the resource
	 *         primary key, or <code>null</code> if no matching web content
	 *         article could be found
	 */
	@Override
	public JournalArticle fetchLatestIndexableArticle(long resourcePrimKey) {
		OrderByComparator<JournalArticle> orderByComparator =
			new ArticleVersionComparator();

		List<JournalArticle> articles = journalArticlePersistence.findByR_I_S(
			resourcePrimKey, true,
			new int[] {
				WorkflowConstants.STATUS_APPROVED,
				WorkflowConstants.STATUS_IN_TRASH
			},
			0, 1, orderByComparator);

		if (articles.isEmpty()) {
			return null;
		}

		return articles.get(0);
	}

	/**
	 * Returns the web content article with the ID.
	 *
	 * @param  id the primary key of the web content article
	 * @return the web content article with the ID
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getArticle(long id) throws PortalException {
		return journalArticlePersistence.findByPrimaryKey(id);
	}

	/**
	 * Returns the latest approved web content article, or the latest unapproved
	 * article if none are approved. Both approved and unapproved articles must
	 * match the group and article ID.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @return the matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getArticle(long groupId, String articleId)
		throws PortalException {

		// Get the latest article that is approved, if none are approved, get
		// the latest unapproved article

		JournalArticle article = fetchLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_APPROVED);

		if (article != null) {
			return article;
		}

		return getLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_ANY);
	}

	/**
	 * Returns the web content article matching the group, article ID, and
	 * version.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @return the matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getArticle(
			long groupId, String articleId, double version)
		throws PortalException {

		return journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);
	}

	/**
	 * Returns the web content article matching the group, class name, and class
	 * PK.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  className the DDMStructure class name if the web content article
	 *         is related to a DDM structure, the primary key of the class name
	 *         associated with the article, or
	 *         JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 *         module otherwise
	 * @param  classPK the primary key of the DDM structure, if the DDMStructure
	 *         class name is given as the <code>className</code> parameter, the
	 *         primary key of the class associated with the web content article,
	 *         or <code>0</code> otherwise
	 * @return the matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getArticle(
			long groupId, String className, long classPK)
		throws PortalException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_C_C(
			groupId, _classNameLocalService.getClassNameId(className), classPK);

		if (articles.isEmpty()) {
			throw new NoSuchArticleException(
				StringBundler.concat(
					"No approved JournalArticle exists with the key {groupId=",
					groupId, ", className=", className, ", classPK=", classPK,
					"}"));
		}

		return articles.get(0);
	}

	/**
	 * Returns the latest web content article that is approved, or the latest
	 * unapproved article if none are approved. Both approved and unapproved
	 * articles must match the group and URL title.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  urlTitle the web content article's accessible URL title
	 * @return the matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getArticleByUrlTitle(long groupId, String urlTitle)
		throws PortalException {

		FriendlyURLEntry friendlyURLEntry =
			friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				groupId, JournalArticle.class, urlTitle);

		if (friendlyURLEntry != null) {
			return getLatestArticle(
				friendlyURLEntry.getClassPK(), WorkflowConstants.STATUS_ANY,
				true);
		}

		// Get the latest article that is approved, if none are approved, get
		// the latest unapproved article

		JournalArticle article = fetchLatestArticleByUrlTitle(
			groupId, urlTitle, WorkflowConstants.STATUS_APPROVED);

		if (article != null) {
			return article;
		}

		return getLatestArticleByUrlTitle(
			groupId, urlTitle, WorkflowConstants.STATUS_ANY);
	}

	/**
	 * Returns the web content from the web content article associated with the
	 * portlet request model and the DDM template.
	 *
	 * @param  article the web content article
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the language translation to get
	 * @param  portletRequestModel the portlet request model
	 * @param  themeDisplay the theme display
	 * @return the web content from the web content article associated with the
	 *         portlet request model and the DDM template
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public String getArticleContent(
			JournalArticle article, String ddmTemplateKey, String viewMode,
			String languageId, PortletRequestModel portletRequestModel,
			ThemeDisplay themeDisplay)
		throws PortalException {

		JournalArticleDisplay articleDisplay = getArticleDisplay(
			article, ddmTemplateKey, viewMode, languageId, 1,
			portletRequestModel, themeDisplay);

		if (articleDisplay == null) {
			return StringPool.BLANK;
		}

		return articleDisplay.getContent();
	}

	/**
	 * Returns the web content from the web content article matching the group,
	 * article ID, and version, and associated with the portlet request model
	 * and the DDM template.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  languageId the primary key of the language translation to get
	 * @param  portletRequestModel the portlet request model
	 * @param  themeDisplay the theme display
	 * @return the web content from the matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public String getArticleContent(
			long groupId, String articleId, double version, String viewMode,
			String ddmTemplateKey, String languageId,
			PortletRequestModel portletRequestModel, ThemeDisplay themeDisplay)
		throws PortalException {

		JournalArticleDisplay articleDisplay = getArticleDisplay(
			groupId, articleId, version, ddmTemplateKey, viewMode, languageId,
			1, portletRequestModel, themeDisplay);

		if (articleDisplay == null) {
			return StringPool.BLANK;
		}

		return articleDisplay.getContent();
	}

	/**
	 * Returns the latest web content from the web content article matching the
	 * group and article ID, and associated with the portlet request model and
	 * the DDM template.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  languageId the primary key of the language translation to get
	 * @param  portletRequestModel the portlet request model
	 * @param  themeDisplay the theme display
	 * @return the latest web content from the matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public String getArticleContent(
			long groupId, String articleId, String viewMode,
			String ddmTemplateKey, String languageId,
			PortletRequestModel portletRequestModel, ThemeDisplay themeDisplay)
		throws PortalException {

		JournalArticleDisplay articleDisplay = getArticleDisplay(
			groupId, articleId, ddmTemplateKey, viewMode, languageId, 1,
			portletRequestModel, themeDisplay);

		return articleDisplay.getContent();
	}

	@Override
	public String getArticleDescription(long articlePK, Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getArticleDescription(articlePK, languageId);
	}

	@Override
	public String getArticleDescription(long articlePK, String languageId) {
		JournalArticleLocalization journalArticleLocalization =
			_journalArticleLocalizationPersistence.fetchByA_L(
				articlePK, languageId);

		if (journalArticleLocalization == null) {
			return null;
		}

		return journalArticleLocalization.getDescription();
	}

	@Override
	public Map<Locale, String> getArticleDescriptionMap(long articlePK) {
		Map<Locale, String> journalArticleLocalizationDescriptionMap =
			new HashMap<>();

		List<JournalArticleLocalization> journalArticleLocalizationList =
			_journalArticleLocalizationPersistence.findByArticlePK(articlePK);

		for (JournalArticleLocalization journalArticleLocalization :
				journalArticleLocalizationList) {

			journalArticleLocalizationDescriptionMap.put(
				LocaleUtil.fromLanguageId(
					journalArticleLocalization.getLanguageId()),
				journalArticleLocalization.getDescription());
		}

		return journalArticleLocalizationDescriptionMap;
	}

	/**
	 * Returns a web content article display for the specified page of the
	 * latest version of the web content article, based on the DDM template. Web
	 * content transformation tokens are added using the portlet request model
	 * and theme display.
	 *
	 * @param  article the primary key of the web content article
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the language translation to get
	 * @param  page the web content article page to display
	 * @param  portletRequestModel the portlet request model
	 * @param  themeDisplay the theme display
	 * @return the web content article display, or <code>null</code> if the
	 *         article has expired or if article's display date/time is after
	 *         the current date/time
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticleDisplay getArticleDisplay(
			JournalArticle article, String ddmTemplateKey, String viewMode,
			String languageId, int page,
			PortletRequestModel portletRequestModel, ThemeDisplay themeDisplay)
		throws PortalException {

		return getArticleDisplay(
			article, ddmTemplateKey, viewMode, languageId, page,
			portletRequestModel, themeDisplay, false);
	}

	/**
	 * Returns a web content article display for the specified page of the
	 * specified version of the web content article matching the group, article
	 * ID, and DDM template. Web content transformation tokens are added using
	 * the portlet request model and theme display.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the language translation to get
	 * @param  page the web content article page to display
	 * @param  portletRequestModel the portlet request model
	 * @param  themeDisplay the theme display
	 * @return the web content article display, or <code>null</code> if the
	 *         article has expired or if article's display date/time is after
	 *         the current date/time
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, double version,
			String ddmTemplateKey, String viewMode, String languageId, int page,
			PortletRequestModel portletRequestModel, ThemeDisplay themeDisplay)
		throws PortalException {

		Date date = new Date();

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		if (article.isExpired()) {
			Date expirationDate = article.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(date)) {
				return null;
			}
		}

		Date displayDate = article.getDisplayDate();

		if ((displayDate != null) && displayDate.after(date) &&
			!Objects.equals(viewMode, Constants.PREVIEW)) {

			return null;
		}

		return getArticleDisplay(
			article, ddmTemplateKey, viewMode, languageId, page,
			portletRequestModel, themeDisplay);
	}

	/**
	 * Returns a web content article display for the first page of the specified
	 * version of the web content article matching the group, article ID, and
	 * DDM template. Web content transformation tokens are added from the theme
	 * display (if not <code>null</code>).
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the language translation to get
	 * @param  themeDisplay the theme display
	 * @return the web content article display, or <code>null</code> if the
	 *         article has expired or if article's display date/time is after
	 *         the current date/time
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, double version,
			String ddmTemplateKey, String viewMode, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException {

		return getArticleDisplay(
			groupId, articleId, version, ddmTemplateKey, viewMode, languageId,
			1, null, themeDisplay);
	}

	/**
	 * Returns a web content article display for the specified page of the
	 * latest version of the web content article matching the group and article
	 * ID. Web content transformation tokens are added from the theme display
	 * (if not <code>null</code>).
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the language translation to get
	 * @param  page the web content article page to display
	 * @param  portletRequestModel the portlet request model
	 * @param  themeDisplay the theme display
	 * @return the web content article display, or <code>null</code> if the
	 *         article has expired or if article's display date/time is after
	 *         the current date/time
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String viewMode, String languageId,
			int page, PortletRequestModel portletRequestModel,
			ThemeDisplay themeDisplay)
		throws PortalException {

		JournalArticle article = getDisplayArticle(groupId, articleId);

		return getArticleDisplay(
			groupId, articleId, article.getDDMTemplateKey(), viewMode,
			languageId, page, portletRequestModel, themeDisplay);
	}

	/**
	 * Returns a web content article display for the specified page of the
	 * latest version of the web content article matching the group, article ID,
	 * and DDM template. Web content transformation tokens are added using the
	 * portlet request model and theme display.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the language translation to get
	 * @param  page the web content article page to display
	 * @param  portletRequestModel the portlet request model
	 * @param  themeDisplay the theme display
	 * @return the web content article display, or <code>null</code> if the
	 *         article has expired or if article's display date/time is after
	 *         the current date/time
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String ddmTemplateKey,
			String viewMode, String languageId, int page,
			PortletRequestModel portletRequestModel, ThemeDisplay themeDisplay)
		throws PortalException {

		JournalArticle article = getDisplayArticle(groupId, articleId);

		return getArticleDisplay(
			groupId, articleId, article.getVersion(), ddmTemplateKey, viewMode,
			languageId, page, portletRequestModel, themeDisplay);
	}

	/**
	 * Returns a web content article display for the first page of the latest
	 * version of the web content article matching the group, article ID, and
	 * DDM template. Web content transformation tokens are added from the theme
	 * display (if not <code>null</code>).
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the language translation to get
	 * @param  themeDisplay the theme display
	 * @return the web content article display, or <code>null</code> if the
	 *         article has expired or if article's display date/time is after
	 *         the current date/time
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String ddmTemplateKey,
			String viewMode, String languageId, ThemeDisplay themeDisplay)
		throws PortalException {

		JournalArticle article = getDisplayArticle(groupId, articleId);

		return getArticleDisplay(
			groupId, articleId, article.getVersion(), ddmTemplateKey, viewMode,
			languageId, themeDisplay);
	}

	/**
	 * Returns a web content article display for the first page of the latest
	 * version of the web content article matching the group and article ID. Web
	 * content transformation tokens are added from the theme display (if not
	 * <code>null</code>).
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  viewMode the mode in which the web content is being viewed
	 * @param  languageId the primary key of the language translation to get
	 * @param  themeDisplay the theme display
	 * @return the web content article display, or <code>null</code> if the
	 *         article has expired or if article's display date/time is after
	 *         the current date/time
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String viewMode, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException {

		JournalArticle article = getDisplayArticle(groupId, articleId);

		return getArticleDisplay(
			groupId, articleId, article.getDDMTemplateKey(), viewMode,
			languageId, themeDisplay);
	}

	@Override
	public List<String> getArticleLocalizationLanguageIds(long articlePK) {
		List<JournalArticleLocalization> journalArticleLocalizationList =
			_journalArticleLocalizationPersistence.findByArticlePK(articlePK);

		List<String> availableLanguageIds = new ArrayList<>();

		for (JournalArticleLocalization journalArticleLocalization :
				journalArticleLocalizationList) {

			availableLanguageIds.add(
				journalArticleLocalization.getLanguageId());
		}

		return availableLanguageIds;
	}

	/**
	 * Returns all the web content articles present in the system.
	 *
	 * @return the web content articles present in the system
	 */
	@Override
	public List<JournalArticle> getArticles() {
		return journalArticlePersistence.findAll();
	}

	/**
	 * Returns all the web content articles belonging to the group.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @return the web content articles belonging to the group
	 */
	@Override
	public List<JournalArticle> getArticles(long groupId) {
		return journalArticlePersistence.findByGroupId(groupId);
	}

	/**
	 * Returns a range of all the web content articles belonging to the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @return the range of matching web content articles
	 */
	@Override
	public List<JournalArticle> getArticles(long groupId, int start, int end) {
		return journalArticlePersistence.findByGroupId(groupId, start, end);
	}

	/**
	 * Returns an ordered range of all the web content articles belonging to the
	 * group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  orderByComparator the comparator to order the web content
	 *         articles
	 * @return the range of matching web content articles ordered by the
	 *         comparator
	 */
	@Override
	public List<JournalArticle> getArticles(
		long groupId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return journalArticlePersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	/**
	 * Returns all the web content articles matching the group and folder.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @return the matching web content articles
	 */
	@Override
	public List<JournalArticle> getArticles(long groupId, long folderId) {
		return journalArticlePersistence.findByG_F(groupId, folderId);
	}

	/**
	 * Returns a range of all the web content articles matching the group and
	 * folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article's folder
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @return the range of matching web content articles
	 */
	@Override
	public List<JournalArticle> getArticles(
		long groupId, long folderId, int start, int end) {

		return journalArticlePersistence.findByG_F(
			groupId, folderId, start, end);
	}

	/**
	 * Returns a range of all the web content articles matching the group,
	 * folder, and status.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article's folder
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @return the range of matching web content articles
	 */
	@Override
	public List<JournalArticle> getArticles(
		long groupId, long folderId, int status, int start, int end) {

		return journalArticlePersistence.findByG_F_ST(
			groupId, folderId, status, start, end);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group and folder.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article's folder
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  orderByComparator the comparator to order the web content
	 *         articles
	 * @return the range of matching web content articles ordered by the
	 *         comparator
	 */
	@Override
	public List<JournalArticle> getArticles(
		long groupId, long folderId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return journalArticlePersistence.findByG_F(
			groupId, folderId, start, end, orderByComparator);
	}

	/**
	 * Returns all the web content articles matching the group and article ID.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @return the matching web content articles
	 */
	@Override
	public List<JournalArticle> getArticles(long groupId, String articleId) {
		return journalArticlePersistence.findByG_A(groupId, articleId);
	}

	@Override
	public List<JournalArticle> getArticles(
		long groupId, String articleId, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return journalArticlePersistence.findByG_A(
			groupId, articleId, start, end, orderByComparator);
	}

	/**
	 * Returns all the web content articles matching the resource primary key.
	 *
	 * @param  resourcePrimKey the primary key of the resource instance
	 * @return the web content articles matching the resource primary key
	 */
	@Override
	public List<JournalArticle> getArticlesByResourcePrimKey(
		long resourcePrimKey) {

		return journalArticlePersistence.findByResourcePrimKey(resourcePrimKey);
	}

	/**
	 * Returns all the web content articles matching the small image ID.
	 *
	 * @param  smallImageId the primary key of the web content article's small
	 *         image
	 * @return the web content articles matching the small image ID
	 */
	@Override
	public List<JournalArticle> getArticlesBySmallImageId(long smallImageId) {
		return journalArticlePersistence.findBySmallImageId(smallImageId);
	}

	@Override
	public List<JournalArticle> getArticlesByStructureId(
		long groupId, long classNameId, String ddmStructureKey, int status,
		int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		QueryDefinition<JournalArticle> queryDefinition = new QueryDefinition<>(
			status, start, end, orderByComparator);

		return journalArticleFinder.findByG_C_S_L(
			groupId, classNameId, ddmStructureKey,
			LocaleUtil.getMostRelevantLocale(), queryDefinition);
	}

	@Override
	public List<JournalArticle> getArticlesByStructureId(
		long groupId, long classNameId, String ddmStructureKey, Locale locale,
		int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		QueryDefinition<JournalArticle> queryDefinition = new QueryDefinition<>(
			status, start, end, orderByComparator);

		return journalArticleFinder.findByG_C_S_L(
			groupId, classNameId, ddmStructureKey, locale, queryDefinition);
	}

	@Override
	public List<JournalArticle> getArticlesByStructureId(
		long groupId, String ddmStructureKey, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		QueryDefinition<JournalArticle> queryDefinition = new QueryDefinition<>(
			status, start, end, orderByComparator);

		return journalArticleFinder.findByG_C_S_L(
			groupId, JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
			ddmStructureKey, LocaleUtil.getMostRelevantLocale(),
			queryDefinition);
	}

	@Override
	public List<JournalArticle> getArticlesByStructureId(
		long groupId, String ddmStructureKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return getArticlesByStructureId(
			groupId, ddmStructureKey, WorkflowConstants.STATUS_ANY, start, end,
			orderByComparator);
	}

	@Override
	public List<JournalArticle> getArticlesByStructureId(
		long groupId, String ddmStructureKey, Locale locale, int status,
		int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		QueryDefinition<JournalArticle> queryDefinition = new QueryDefinition<>(
			status, start, end, orderByComparator);

		return journalArticleFinder.findByG_C_S_L(
			groupId, JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
			ddmStructureKey, locale, queryDefinition);
	}

	@Override
	public List<Long> getArticlesClassPKsWithDefaultDisplayPage(
		long groupId, long classTypeId) {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchDefaultLayoutPageTemplateEntry(
					groupId,
					_portal.getClassNameId(JournalArticle.class.getName()),
					classTypeId);

		if (layoutPageTemplateEntry == null) {
			return Collections.emptyList();
		}

		JournalArticleTable tempJournalArticleTable =
			JournalArticleTable.INSTANCE.as("tempJournalArticleTable");

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchDDMStructure(
			layoutPageTemplateEntry.getClassTypeId());

		return journalArticlePersistence.dslQuery(
			DSLQueryFactoryUtil.selectDistinct(
				JournalArticleTable.INSTANCE.resourcePrimKey
			).from(
				JournalArticleTable.INSTANCE
			).where(
				JournalArticleTable.INSTANCE.groupId.eq(
					groupId
				).and(
					JournalArticleTable.INSTANCE.DDMStructureKey.eq(
						ddmStructure.getStructureKey())
				).and(
					JournalArticleTable.INSTANCE.layoutUuid.isNull()
				).and(
					JournalArticleTable.INSTANCE.status.eq(
						WorkflowConstants.STATUS_APPROVED)
				).and(
					JournalArticleTable.INSTANCE.version.in(
						DSLQueryFactoryUtil.select(
							DSLFunctionFactoryUtil.max(
								tempJournalArticleTable.version)
						).from(
							tempJournalArticleTable
						).where(
							tempJournalArticleTable.resourcePrimKey.eq(
								JournalArticleTable.INSTANCE.resourcePrimKey
							).and(
								tempJournalArticleTable.status.eq(
									WorkflowConstants.STATUS_APPROVED)
							)
						))
				).and(
					JournalArticleTable.INSTANCE.resourcePrimKey.notIn(
						DSLQueryFactoryUtil.select(
							AssetDisplayPageEntryTable.INSTANCE.classPK
						).from(
							AssetDisplayPageEntryTable.INSTANCE
						).where(
							AssetDisplayPageEntryTable.INSTANCE.groupId.eq(
								groupId
							).and(
								AssetDisplayPageEntryTable.INSTANCE.classNameId.
									eq(
										_portal.getClassNameId(
											JournalArticle.class.getName()))
							)
						))
				)
			));
	}

	/**
	 * Returns the number of web content articles belonging to the group.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @return the number of web content articles belonging to the group
	 */
	@Override
	public int getArticlesCount(long groupId) {
		return journalArticlePersistence.countByGroupId(groupId);
	}

	/**
	 * Returns the number of web content articles matching the group and folder.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article's folder
	 * @return the number of matching web content articles
	 */
	@Override
	public int getArticlesCount(long groupId, long folderId) {
		return journalArticlePersistence.countByG_F(groupId, folderId);
	}

	/**
	 * Returns the number of web content articles matching the group, folder,
	 * and status.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article's folder
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @return the number of matching web content articles
	 */
	@Override
	public int getArticlesCount(long groupId, long folderId, int status) {
		return journalArticlePersistence.countByG_F_ST(
			groupId, folderId, status);
	}

	@Override
	public int getArticlesCount(long groupId, String articleId) {
		return journalArticlePersistence.countByG_A(groupId, articleId);
	}

	@Override
	public String getArticleTitle(long articlePK, Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getArticleTitle(articlePK, languageId);
	}

	@Override
	public String getArticleTitle(long articlePK, String languageId) {
		JournalArticleLocalization journalArticleLocalization =
			_journalArticleLocalizationPersistence.fetchByA_L(
				articlePK, languageId);

		if (journalArticleLocalization == null) {
			return null;
		}

		return journalArticleLocalization.getTitle();
	}

	@Override
	public Map<Locale, String> getArticleTitleMap(long articlePK) {
		Map<Locale, String> journalArticleLocalizationTitleMap =
			new HashMap<>();

		List<JournalArticleLocalization> journalArticleLocalizationList =
			_journalArticleLocalizationPersistence.findByArticlePK(articlePK);

		for (JournalArticleLocalization journalArticleLocalization :
				journalArticleLocalizationList) {

			journalArticleLocalizationTitleMap.put(
				LocaleUtil.fromLanguageId(
					journalArticleLocalization.getLanguageId()),
				journalArticleLocalization.getTitle());
		}

		return journalArticleLocalizationTitleMap;
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * company, version, and workflow status.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  version the web content article's version
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @return the range of matching web content articles ordered by article ID
	 */
	@Override
	public List<JournalArticle> getCompanyArticles(
		long companyId, double version, int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return journalArticlePersistence.findByC_V(
				companyId, version, start, end, new ArticleIDComparator(true));
		}

		return journalArticlePersistence.findByC_V_ST(
			companyId, version, status, start, end,
			new ArticleIDComparator(true));
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * company and workflow status.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @return the range of matching web content articles ordered by article ID
	 */
	@Override
	public List<JournalArticle> getCompanyArticles(
		long companyId, int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return journalArticlePersistence.findByCompanyId(
				companyId, start, end, new ArticleIDComparator(true));
		}

		return journalArticlePersistence.findByC_ST(
			companyId, status, start, end, new ArticleIDComparator(true));
	}

	/**
	 * Returns the number of web content articles matching the company, version,
	 * and workflow status.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  version the web content article's version
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @return the number of matching web content articles
	 */
	@Override
	public int getCompanyArticlesCount(
		long companyId, double version, int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return journalArticlePersistence.countByC_V(companyId, version);
		}

		return journalArticlePersistence.countByC_V_ST(
			companyId, version, status);
	}

	/**
	 * Returns the number of web content articles matching the company and
	 * workflow status.
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @return the number of matching web content articles
	 */
	@Override
	public int getCompanyArticlesCount(long companyId, int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return journalArticlePersistence.countByCompanyId(companyId);
		}

		return journalArticlePersistence.countByC_ST(companyId, status);
	}

	/**
	 * Returns the matching web content article currently displayed or next to
	 * be displayed if no article is currently displayed.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @return the matching web content article currently displayed, or the next
	 *         one to be displayed if no version of the article is currently
	 *         displayed
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getDisplayArticle(long groupId, String articleId)
		throws PortalException {

		JournalArticle article = fetchDisplayArticle(groupId, articleId);

		if (article == null) {
			throw new NoSuchArticleException(
				StringBundler.concat(
					"No approved JournalArticle exists with the key {groupId=",
					groupId, ", articleId=", articleId, "}"));
		}

		return article;
	}

	/**
	 * Returns the web content article matching the URL title that is currently
	 * displayed or next to be displayed if no article is currently displayed.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  urlTitle the web content article's accessible URL title
	 * @return the web content article matching the URL title that is currently
	 *         displayed, or next one to be displayed if no version of the
	 *         article is currently displayed
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getDisplayArticleByUrlTitle(
			long groupId, String urlTitle)
		throws PortalException {

		List<JournalArticle> articles = null;

		FriendlyURLEntry friendlyURLEntry =
			friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				groupId, JournalArticle.class, urlTitle);

		if (friendlyURLEntry != null) {
			articles = journalArticlePersistence.findByR_ST(
				friendlyURLEntry.getClassPK(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new ArticleVersionComparator());
		}
		else {
			articles = journalArticlePersistence.findByG_UT_ST(
				groupId, _friendlyURLNormalizer.normalizeWithEncoding(urlTitle),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new ArticleVersionComparator());
		}

		if (articles.isEmpty()) {
			throw new NoSuchArticleException(
				StringBundler.concat(
					"No JournalArticle exists with the key {groupId=", groupId,
					", urlTitle=", urlTitle, "}"));
		}

		Date date = new Date();

		for (JournalArticle article : articles) {
			Date displayDate = article.getDisplayDate();
			Date expirationDate = article.getExpirationDate();

			if ((displayDate != null) && displayDate.before(date) &&
				((expirationDate == null) || expirationDate.after(date))) {

				return article;
			}
		}

		return articles.get(0);
	}

	@Override
	public List<JournalArticle> getIndexableArticlesByDDMStructureKey(
		String[] ddmStructureKeys) {

		if (isReindexAllArticleVersions()) {
			return getStructureArticles(ddmStructureKeys);
		}

		List<JournalArticle> articles = new ArrayList<>();

		QueryDefinition<JournalArticle> approvedQueryDefinition =
			new QueryDefinition<>(
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new ArticleVersionComparator());

		articles.addAll(
			journalArticleFinder.findByG_C_S_L(
				0, JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
				ddmStructureKeys, LocaleUtil.getMostRelevantLocale(),
				approvedQueryDefinition));

		QueryDefinition<JournalArticle> trashQueryDefinition =
			new QueryDefinition<>(
				WorkflowConstants.STATUS_IN_TRASH, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new ArticleVersionComparator());

		articles.addAll(
			journalArticleFinder.findByG_C_S_L(
				0, JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
				ddmStructureKeys, LocaleUtil.getMostRelevantLocale(),
				trashQueryDefinition));

		return articles;
	}

	@Override
	public List<JournalArticle> getIndexableArticlesByDDMStructureKey(
		String[] ddmStructureKeys, Locale locale) {

		if (isReindexAllArticleVersions()) {
			return getStructureArticles(ddmStructureKeys);
		}

		QueryDefinition<JournalArticle> approvedQueryDefinition =
			new QueryDefinition<>(
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new ArticleVersionComparator());

		List<JournalArticle> articles = new ArrayList<>();

		articles.addAll(
			journalArticleFinder.findByG_C_S_L(
				0, JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
				ddmStructureKeys, locale, approvedQueryDefinition));

		QueryDefinition<JournalArticle> trashQueryDefinition =
			new QueryDefinition<>(
				WorkflowConstants.STATUS_IN_TRASH, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new ArticleVersionComparator());

		articles.addAll(
			journalArticleFinder.findByG_C_S_L(
				0, JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
				ddmStructureKeys, locale, trashQueryDefinition));

		return articles;
	}

	/**
	 * Returns the indexable web content articles matching the resource primary
	 * key.
	 *
	 * @param  resourcePrimKey the primary key of the resource instance
	 * @return the indexable web content articles matching the resource primary
	 *         key
	 */
	@Override
	public List<JournalArticle> getIndexableArticlesByResourcePrimKey(
		long resourcePrimKey) {

		return journalArticlePersistence.findByR_I(resourcePrimKey, true);
	}

	/**
	 * Returns the latest web content article matching the resource primary key,
	 * preferring articles with approved workflow status.
	 *
	 * @param  resourcePrimKey the primary key of the resource instance
	 * @return the latest web content article matching the resource primary key,
	 *         preferring articles with approved workflow status
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getLatestArticle(long resourcePrimKey)
		throws PortalException {

		return getLatestArticle(resourcePrimKey, WorkflowConstants.STATUS_ANY);
	}

	/**
	 * Returns the latest web content article matching the resource primary key
	 * and workflow status, preferring articles with approved workflow status.
	 *
	 * @param  resourcePrimKey the primary key of the resource instance
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @return the latest web content article matching the resource primary key
	 *         and workflow status, preferring articles with approved workflow
	 *         status
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getLatestArticle(long resourcePrimKey, int status)
		throws PortalException {

		return getLatestArticle(resourcePrimKey, status, true);
	}

	/**
	 * Returns the latest web content article matching the resource primary key
	 * and workflow status, optionally preferring articles with approved
	 * workflow status.
	 *
	 * @param  resourcePrimKey the primary key of the resource instance
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  preferApproved whether to prefer returning the latest matching
	 *         article that has workflow status {@link
	 *         WorkflowConstants#STATUS_APPROVED} over returning one that has a
	 *         different status
	 * @return the latest web content article matching the resource primary key
	 *         and workflow status, optionally preferring articles with approved
	 *         workflow status
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getLatestArticle(
			long resourcePrimKey, int status, boolean preferApproved)
		throws PortalException {

		List<JournalArticle> articles = null;

		OrderByComparator<JournalArticle> orderByComparator =
			new ArticleVersionComparator();

		if (status == WorkflowConstants.STATUS_ANY) {
			if (preferApproved) {
				articles = journalArticlePersistence.findByR_ST(
					resourcePrimKey, WorkflowConstants.STATUS_APPROVED, 0, 1,
					orderByComparator);
			}

			if (ListUtil.isEmpty(articles)) {
				articles = journalArticlePersistence.findByResourcePrimKey(
					resourcePrimKey, 0, 1, orderByComparator);
			}
		}
		else {
			articles = journalArticlePersistence.findByR_ST(
				resourcePrimKey, status, 0, 1, orderByComparator);
		}

		if (articles.isEmpty()) {
			throw new NoSuchArticleException(
				"No JournalArticle exists with the key {resourcePrimKey=" +
					resourcePrimKey + "}");
		}

		return articles.get(0);
	}

	/**
	 * Returns the latest web content article with the group and article ID.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @return the latest matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getLatestArticle(long groupId, String articleId)
		throws PortalException {

		return getLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_ANY);
	}

	/**
	 * Returns the latest web content article matching the group, article ID,
	 * and workflow status.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @return the latest matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getLatestArticle(
			long groupId, String articleId, int status)
		throws PortalException {

		return getFirstArticle(
			groupId, articleId, status, new ArticleVersionComparator());
	}

	/**
	 * Returns the latest web content article matching the group, class name ID,
	 * and class PK.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  className the DDMStructure class name if the web content article
	 *         is related to a DDM structure, the class name associated with the
	 *         article, or JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the
	 *         journal-api module otherwise
	 * @param  classPK the primary key of the DDM structure, if the DDMStructure
	 *         class name is given as the <code>className</code> parameter, the
	 *         primary key of the class associated with the web content article,
	 *         or <code>0</code> otherwise
	 * @return the latest matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getLatestArticle(
			long groupId, String className, long classPK)
		throws PortalException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_C_C(
			groupId, _classNameLocalService.getClassNameId(className), classPK,
			0, 1, new ArticleVersionComparator());

		if (articles.isEmpty()) {
			throw new NoSuchArticleException(
				StringBundler.concat(
					"No JournalArticle exists with the key {groupId=", groupId,
					", className=", className, ", classPK =", classPK, "}"));
		}

		return articles.get(0);
	}

	/**
	 * Returns the latest web content article matching the group and the
	 * external reference code.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  externalReferenceCode the web content article's external
	 *         reference code
	 * @return the latest matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getLatestArticleByExternalReferenceCode(
			long groupId, String externalReferenceCode)
		throws PortalException {

		OrderByComparator<JournalArticle> orderByComparator =
			new ArticleVersionComparator();

		return journalArticlePersistence.findByG_ERC_First(
			groupId, externalReferenceCode, orderByComparator);
	}

	/**
	 * Returns the latest web content article matching the group, URL title, and
	 * workflow status.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  urlTitle the web content article's accessible URL title
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @return the latest matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getLatestArticleByUrlTitle(
			long groupId, String urlTitle, int status)
		throws PortalException {

		JournalArticle article = null;

		FriendlyURLEntry friendlyURLEntry =
			friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				groupId, JournalArticle.class, urlTitle);

		if (friendlyURLEntry != null) {
			article = fetchLatestArticle(friendlyURLEntry.getClassPK(), status);
		}
		else {
			article = fetchLatestArticleByUrlTitle(groupId, urlTitle, status);
		}

		if (article == null) {
			throw new NoSuchArticleException(
				StringBundler.concat(
					"No JournalArticle exists with the key {groupId=", groupId,
					", urlTitle=", urlTitle, ", status=", status, "}"));
		}

		return article;
	}

	/**
	 * Returns the latest version number of the web content with the group and
	 * article ID.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @return the latest version number of the matching web content
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public double getLatestVersion(long groupId, String articleId)
		throws PortalException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		return article.getVersion();
	}

	/**
	 * Returns the latest version number of the web content with the group,
	 * article ID, and workflow status.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @return the latest version number of the matching web content
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public double getLatestVersion(long groupId, String articleId, int status)
		throws PortalException {

		JournalArticle article = getLatestArticle(groupId, articleId, status);

		return article.getVersion();
	}

	@Override
	public List<JournalArticle> getNoAssetArticles() {
		return journalArticleFinder.findByNoAssets();
	}

	@Override
	public List<JournalArticle> getNoPermissionArticles() {
		return journalArticleFinder.findByNoPermissions();
	}

	/**
	 * Returns the number of web content articles that are not recycled.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @return the number of web content articles that are not recycled
	 */
	@Override
	public int getNotInTrashArticlesCount(long groupId, long folderId) {
		QueryDefinition<JournalArticle> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_ANY);

		return journalArticleFinder.countByG_F(
			groupId, ListUtil.fromArray(folderId), queryDefinition);
	}

	/**
	 * Returns the oldest web content article with the group and article ID.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @return the oldest matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getOldestArticle(long groupId, String articleId)
		throws PortalException {

		return getOldestArticle(
			groupId, articleId, WorkflowConstants.STATUS_ANY);
	}

	/**
	 * Returns the oldest web content article matching the group, article ID,
	 * and workflow status.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @return the oldest matching web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle getOldestArticle(
			long groupId, String articleId, int status)
		throws PortalException {

		return getFirstArticle(
			groupId, articleId, status, new ArticleVersionComparator(true));
	}

	/**
	 * Returns the previously approved version of the web content article. For
	 * more information on the approved workflow status, see {@link
	 * WorkflowConstants#STATUS_APPROVED}.
	 *
	 * @param  article the web content article
	 * @return the previously approved version of the web content article, or
	 *         the current web content article if there are no previously
	 *         approved web content articles
	 */
	@Override
	public JournalArticle getPreviousApprovedArticle(JournalArticle article) {
		List<JournalArticle> approvedArticles =
			journalArticlePersistence.findByG_A_ST(
				article.getGroupId(), article.getArticleId(),
				WorkflowConstants.STATUS_APPROVED, 0, 2);

		if (approvedArticles.isEmpty() ||
			((approvedArticles.size() == 1) &&
			 (article.getStatus() == WorkflowConstants.STATUS_APPROVED))) {

			return article;
		}

		JournalArticle previousApprovedArticle = approvedArticles.get(0);

		if ((approvedArticles.size() > 1) &&
			(previousApprovedArticle.getVersion() == article.getVersion())) {

			previousApprovedArticle = approvedArticles.get(1);
		}

		return previousApprovedArticle;
	}

	/**
	 * Returns the web content articles matching the group and DDM structure
	 * key.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure
	 * @return the matching web content articles
	 */
	@Override
	public List<JournalArticle> getStructureArticles(
		long groupId, String ddmStructureKey) {

		return journalArticlePersistence.findByG_DDMSK(
			groupId, ddmStructureKey);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group and DDM structure key.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  orderByComparator the comparator to order the web content
	 *         articles
	 * @return the range of matching web content articles ordered by the
	 *         comparator
	 */
	@Override
	public List<JournalArticle> getStructureArticles(
		long groupId, String ddmStructureKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return journalArticlePersistence.findByG_DDMSK(
			groupId, ddmStructureKey, start, end, orderByComparator);
	}

	/**
	 * Returns the web content articles matching the DDM structure keys.
	 *
	 * @param  ddmStructureKeys the primary keys of the web content article's
	 *         DDM structures
	 * @return the web content articles matching the DDM structure keys
	 */
	@Override
	public List<JournalArticle> getStructureArticles(
		String[] ddmStructureKeys) {

		return journalArticlePersistence.findByDDMStructureKey(
			ddmStructureKeys);
	}

	/**
	 * Returns the number of web content articles matching the group and DDM
	 * structure key.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure
	 * @return the number of matching web content articles
	 */
	@Override
	public int getStructureArticlesCount(long groupId, String ddmStructureKey) {
		return journalArticlePersistence.countByG_DDMSK(
			groupId, ddmStructureKey);
	}

	/**
	 * Returns the web content articles matching the group and DDM template key.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @return the matching web content articles
	 */
	@Override
	public List<JournalArticle> getTemplateArticles(
		long groupId, String ddmTemplateKey) {

		return journalArticlePersistence.findByG_DDMTK(groupId, ddmTemplateKey);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * group and DDM template key.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  orderByComparator the comparator to order the web content
	 *         articles
	 * @return the range of matching web content articles ordered by the
	 *         comparator
	 */
	@Override
	public List<JournalArticle> getTemplateArticles(
		long groupId, String ddmTemplateKey, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return journalArticlePersistence.findByG_DDMTK(
			groupId, ddmTemplateKey, start, end, orderByComparator);
	}

	/**
	 * Returns the number of web content articles matching the group and DDM
	 * template key.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @return the number of matching web content articles
	 */
	@Override
	public int getTemplateArticlesCount(long groupId, String ddmTemplateKey) {
		return journalArticlePersistence.countByG_DDMTK(
			groupId, ddmTemplateKey);
	}

	/**
	 * Returns the web content article's unique URL title.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  urlTitle the web content article's accessible URL title
	 * @return the web content article's unique URL title
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public String getUniqueUrlTitle(
			long groupId, String articleId, String urlTitle)
		throws PortalException {

		for (int i = 1;; i++) {
			JournalArticle article = fetchArticleByUrlTitle(groupId, urlTitle);

			if ((article == null) || articleId.equals(article.getArticleId())) {
				break;
			}

			String suffix = StringPool.DASH + i;

			String prefix = urlTitle;

			if (urlTitle.length() > suffix.length()) {
				prefix = urlTitle.substring(
					0, urlTitle.length() - suffix.length());
			}

			urlTitle = prefix + suffix;
		}

		return urlTitle;
	}

	/**
	 * Returns <code>true</code> if the specified web content article exists.
	 *
	 * @param  groupId the primary key of the group
	 * @param  articleId the primary key of the web content article
	 * @return <code>true</code> if the specified web content article exists;
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean hasArticle(long groupId, String articleId) {
		JournalArticle article = fetchArticle(groupId, articleId);

		if (article != null) {
			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the web content article, specified by group
	 * and article ID, is the latest version.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @return <code>true</code> if the specified web content article is the
	 *         latest version; <code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public boolean isLatestVersion(
			long groupId, String articleId, double version)
		throws PortalException {

		if (getLatestVersion(groupId, articleId) == version) {
			return true;
		}

		return false;
	}

	/**
	 * Returns <code>true</code> if the web content article, specified by group,
	 * article ID, and workflow status, is the latest version.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @return <code>true</code> if the specified web content article is the
	 *         latest version; <code>false</code> otherwise
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public boolean isLatestVersion(
			long groupId, String articleId, double version, int status)
		throws PortalException {

		if (getLatestVersion(groupId, articleId, status) == version) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isListable(JournalArticle article) {
		if ((article != null) && article.isIndexable()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isRenderable(
		JournalArticle article, PortletRequestModel portletRequestModel,
		ThemeDisplay themeDisplay) {

		try {
			getArticleDisplay(
				article, article.getDDMTemplateKey(), Constants.VIEW,
				article.getDefaultLanguageId(), 0, portletRequestModel,
				themeDisplay, true);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return false;
		}

		return true;
	}

	/**
	 * Moves the web content article matching the group and article ID to a new
	 * folder.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  newFolderId the primary key of the web content article's new
	 *         folder
	 * @param  serviceContext the service context to be applied. Can set the
	 *         user ID, language ID, portlet preferences, portlet request,
	 *         portlet response, theme display, and can set whether to add the
	 *         default command update for the web content article. With respect
	 *         to social activities, by setting the service context's command to
	 *         {@link Constants#UPDATE}, the invocation is considered a web
	 *         content update activity; otherwise it is considered a web content
	 *         add activity.
	 * @return the updated web content article, which was moved to a new folder
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public JournalArticle moveArticle(
			long groupId, String articleId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle latestArticle = getLatestArticle(groupId, articleId);

		validateDDMStructureId(
			groupId, newFolderId, latestArticle.getDDMStructureKey());

		List<JournalArticle> articles = journalArticlePersistence.findByG_A(
			groupId, articleId);

		String treePath = null;

		for (JournalArticle article : articles) {
			article.setFolderId(newFolderId);

			if (treePath == null) {
				treePath = article.buildTreePath();
			}

			article.setTreePath(treePath);

			journalArticlePersistence.update(article);
		}

		if (serviceContext != null) {
			notifySubscribers(
				serviceContext.getUserId(), latestArticle, "move_from",
				serviceContext);

			latestArticle.setFolderId(newFolderId);
			latestArticle.setTreePath(latestArticle.buildTreePath());

			notifySubscribers(
				serviceContext.getUserId(), latestArticle, "move_to",
				serviceContext);
		}

		return getArticle(groupId, articleId);
	}

	/**
	 * Moves the web content article from the Recycle Bin to a new folder.
	 *
	 * @param  userId the primary key of the user updating the web content
	 *         article
	 * @param  groupId the primary key of the web content article's group
	 * @param  article the web content article
	 * @param  newFolderId the primary key of the web content article's new
	 *         folder
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, portlet preferences, and can set whether to
	 *         add the default command update for the web content article. With
	 *         respect to social activities, by setting the service context's
	 *         command to {@link Constants#UPDATE}, the invocation is considered
	 *         a web content update activity; otherwise it is considered a web
	 *         content add activity.
	 * @return the updated web content article, which was moved from the Recycle
	 *         Bin to a new folder
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public JournalArticle moveArticleFromTrash(
			long userId, long groupId, JournalArticle article, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		if (!article.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

		if (article.isInTrashExplicitly()) {
			article = restoreArticleFromTrash(userId, article);
		}
		else {

			// Article

			TrashVersion trashVersion = _trashVersionLocalService.fetchVersion(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			int status = WorkflowConstants.STATUS_APPROVED;

			if (trashVersion != null) {
				status = trashVersion.getStatus();
			}

			article = updateStatus(
				userId, article, status, null, serviceContext, new HashMap<>());

			// Attachments

			for (FileEntry fileEntry : article.getImagesFileEntries()) {
				_portletFileRepository.restorePortletFileEntryFromTrash(
					userId, fileEntry.getFileEntryId());
			}

			// Trash

			if (trashVersion != null) {
				_trashVersionLocalService.deleteTrashVersion(trashVersion);
			}
		}

		return moveArticle(
			groupId, article.getArticleId(), newFolderId, serviceContext);
	}

	/**
	 * Moves the latest version of the web content article matching the group
	 * and article ID to the recycle bin.
	 *
	 * @param  userId the primary key of the user updating the web content
	 *         article
	 * @param  article the web content article
	 * @return the updated web content article, which was moved to the Recycle
	 *         Bin
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public JournalArticle moveArticleToTrash(
			long userId, JournalArticle article)
		throws PortalException {

		// Article

		if (article.isInTrash()) {
			throw new TrashEntryException();
		}

		int oldStatus = article.getStatus();

		if (oldStatus == WorkflowConstants.STATUS_PENDING) {
			article.setStatus(WorkflowConstants.STATUS_DRAFT);

			article = journalArticlePersistence.update(article);
		}

		article = updateStatus(
			userId, article.getId(), WorkflowConstants.STATUS_IN_TRASH,
			new HashMap<>(), new ServiceContext());

		List<JournalArticle> articleVersions =
			journalArticlePersistence.findByG_A(
				article.getGroupId(), article.getArticleId());

		articleVersions = ListUtil.sort(
			articleVersions, new ArticleVersionComparator());

		List<ObjectValuePair<Long, Integer>> articleVersionStatusOVPs =
			new ArrayList<>();

		if ((articleVersions != null) && !articleVersions.isEmpty()) {
			articleVersionStatusOVPs = getArticleVersionStatuses(
				articleVersions);
		}

		// Trash

		JournalArticleResource articleResource =
			_journalArticleResourceLocalService.getArticleResource(
				article.getResourcePrimKey());

		TrashEntry trashEntry = _trashEntryLocalService.addTrashEntry(
			userId, article.getGroupId(), JournalArticle.class.getName(),
			article.getResourcePrimKey(), articleResource.getUuid(), null,
			oldStatus, articleVersionStatusOVPs,
			UnicodePropertiesBuilder.put(
				"title", article.getArticleId()
			).build());

		String trashArticleId = _trashHelper.getTrashTitle(
			trashEntry.getEntryId());

		for (JournalArticle articleVersion : articleVersions) {
			articleVersion.setExternalReferenceCode(trashArticleId);
			articleVersion.setArticleId(trashArticleId);
			articleVersion.setStatus(WorkflowConstants.STATUS_IN_TRASH);

			journalArticlePersistence.update(articleVersion);
		}

		articleResource.setArticleId(trashArticleId);

		_journalArticleResourcePersistence.update(articleResource);

		article.setExternalReferenceCode(trashArticleId);
		article.setArticleId(trashArticleId);

		article = journalArticlePersistence.update(article);

		// Asset

		_assetEntryLocalService.updateVisible(
			JournalArticle.class.getName(), article.getResourcePrimKey(),
			false);

		// Attachments

		for (FileEntry fileEntry : article.getImagesFileEntries()) {
			_portletFileRepository.movePortletFileEntryToTrash(
				userId, fileEntry.getFileEntryId());
		}

		// Comment

		if (isArticleCommentsEnabled(article.getCompanyId())) {
			_commentManager.moveDiscussionToTrash(
				JournalArticle.class.getName(), article.getResourcePrimKey());
		}

		// Social

		JSONObject extraDataJSONObject = JSONUtil.put(
			"title", article.getTitleMapAsXML());

		SocialActivityManagerUtil.addActivity(
			userId, article, SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			extraDataJSONObject.toString(), 0);

		if (oldStatus == WorkflowConstants.STATUS_PENDING) {
			_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
				article.getCompanyId(), article.getGroupId(),
				JournalArticle.class.getName(), article.getId());
		}

		return article;
	}

	/**
	 * Moves the latest version of the web content article matching the group
	 * and article ID to the recycle bin.
	 *
	 * @param  userId the primary key of the user updating the web content
	 *         article
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @return the moved web content article or <code>null</code> if no matching
	 *         article was found
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle moveArticleToTrash(
			long userId, long groupId, String articleId)
		throws PortalException {

		List<JournalArticle> articles = journalArticlePersistence.findByG_A(
			groupId, articleId, 0, 1, new ArticleVersionComparator());

		if (!articles.isEmpty()) {
			return journalArticleLocalService.moveArticleToTrash(
				userId, articles.get(0));
		}

		return null;
	}

	/**
	 * Rebuilds the web content article's tree path using tree traversal.
	 *
	 * <p>
	 * For example, here is a conceptualization of a web content article tree
	 * path:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * <code>
	 * /(Folder's folderId)/(Subfolder's folderId)/(article's articleId)
	 * </code>
	 * </pre></p>
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void rebuildTree(long companyId) throws PortalException {
		JournalTreePathUtil.rebuildTree(
			companyId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringPool.SLASH, _journalFolderPersistence, this);
	}

	/**
	 * Removes the web content of the web content article matching the group,
	 * article ID, and version, and language.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  languageId the primary key of the language locale to remove
	 * @return the updated web content article with the locale removed
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public JournalArticle removeArticleLocale(
			long groupId, String articleId, double version, String languageId)
		throws PortalException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		if (Objects.equals(languageId, article.getDefaultLanguageId())) {
			throw new RequiredArticleLocalizationException(
				"Default article localization is required");
		}

		JournalArticleLocalization journalArticleLocalization =
			_journalArticleLocalizationPersistence.fetchByA_L(
				article.getId(), languageId);

		if (journalArticleLocalization != null) {
			_journalArticleLocalizationPersistence.removeByA_L(
				article.getId(), languageId);
		}

		String content = JournalUtil.removeArticleLocale(article, languageId);

		if (content != null) {
			updateDDMFields(article, content);
		}

		article = journalArticlePersistence.update(article);

		FriendlyURLEntry friendlyURLEntry =
			friendlyURLEntryLocalService.fetchFriendlyURLEntry(
				article.getGroupId(), JournalArticle.class,
				article.getUrlTitle());

		if (friendlyURLEntry == null) {
			return article;
		}

		FriendlyURLEntryLocalization friendlyURLEntryLocalization =
			friendlyURLEntryLocalService.fetchFriendlyURLEntryLocalization(
				friendlyURLEntry.getFriendlyURLEntryId(), languageId);

		if (friendlyURLEntryLocalization != null) {
			friendlyURLEntryLocalService.deleteFriendlyURLLocalizationEntry(
				friendlyURLEntry.getFriendlyURLEntryId(), languageId);
		}

		return article;
	}

	/**
	 * Restores the web content article from the Recycle Bin.
	 *
	 * @param  userId the primary key of the user restoring the web content
	 *         article
	 * @param  article the web content article
	 * @return the restored web content article from the Recycle Bin
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public JournalArticle restoreArticleFromTrash(
			long userId, JournalArticle article)
		throws PortalException {

		// Article

		if (!article.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

		String trashArticleId = _trashHelper.getOriginalTitle(
			article.getArticleId());

		List<JournalArticle> articleVersions =
			journalArticlePersistence.findByG_A(
				article.getGroupId(), article.getArticleId());

		for (JournalArticle articleVersion : articleVersions) {
			articleVersion.setExternalReferenceCode(trashArticleId);
			articleVersion.setArticleId(trashArticleId);

			articleVersion = journalArticlePersistence.update(articleVersion);

			if (article.equals(articleVersion)) {
				article = articleVersion;
			}
		}

		article.setExternalReferenceCode(trashArticleId);
		article.setArticleId(trashArticleId);

		article = journalArticlePersistence.update(article);

		JournalArticleResource articleResource =
			_journalArticleResourcePersistence.fetchByPrimaryKey(
				article.getResourcePrimKey());

		articleResource.setArticleId(trashArticleId);

		_journalArticleResourcePersistence.update(articleResource);

		TrashEntry trashEntry = _trashEntryLocalService.getEntry(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(article.getGroupId());

		article = updateStatus(
			userId, article, trashEntry.getStatus(), null, serviceContext,
			new HashMap<>());

		// Trash

		List<TrashVersion> trashVersions =
			_trashVersionLocalService.getVersions(trashEntry.getEntryId());

		boolean visible = false;

		for (TrashVersion trashVersion : trashVersions) {
			JournalArticle trashArticleVersion =
				journalArticlePersistence.findByPrimaryKey(
					trashVersion.getClassPK());

			if (!ArrayUtil.contains(
					new int[] {
						WorkflowConstants.STATUS_APPROVED,
						WorkflowConstants.STATUS_IN_TRASH
					},
					trashVersion.getStatus())) {

				trashArticleVersion.setStatus(trashVersion.getStatus());
			}
			else {
				trashArticleVersion.setStatus(trashEntry.getStatus());
			}

			if (trashEntry.getStatus() == WorkflowConstants.STATUS_APPROVED) {
				visible = true;
			}

			journalArticlePersistence.update(trashArticleVersion);
		}

		_trashEntryLocalService.deleteEntry(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		if (visible) {
			_assetEntryLocalService.updateVisible(
				JournalArticle.class.getName(), article.getResourcePrimKey(),
				true);
		}

		// Comment

		if (isArticleCommentsEnabled(article.getCompanyId())) {
			_commentManager.restoreDiscussionFromTrash(
				JournalArticle.class.getName(), article.getResourcePrimKey());
		}

		// Social

		JSONObject extraDataJSONObject = JSONUtil.put(
			"title", article.getTitleMapAsXML());

		SocialActivityManagerUtil.addActivity(
			userId, article, SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);

		return article;
	}

	@Override
	public List<JournalArticle> search(
		long groupId, List<Long> folderIds, Locale locale, int status,
		int start, int end) {

		QueryDefinition<JournalArticle> queryDefinition = new QueryDefinition<>(
			status, start, end, null);

		return journalArticleFinder.findByG_F_L(
			groupId, folderIds, locale, queryDefinition);
	}

	/**
	 * Returns a range of all the web content articles in a single folder
	 * matching the parameters without using the indexer. It is preferable to
	 * use the indexed version {@link #search(long, long, long, int, int, int)}
	 * instead of this method wherever possible for performance reasons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderId the primary key of the web content article folder
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @return the matching web content articles
	 */
	@Override
	public List<JournalArticle> search(
		long groupId, long folderId, int status, int start, int end) {

		return search(
			groupId, ListUtil.fromArray(folderId),
			LocaleUtil.getMostRelevantLocale(), status, start, end);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * parameters without using the indexer, including a keywords parameter for
	 * matching with the article's ID, title, description, and content, a DDM
	 * structure key parameter, and a DDM template key parameter. It is
	 * preferable to use the indexed version {@link #search(long, long, List,
	 * long, String, String, String, LinkedHashMap, int, int, Sort)} instead of
	 * this method wherever possible for performance reasons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or
	 *         JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 *         module otherwise
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         web content article ID, title, description, or content
	 *         (optionally <code>null</code>). If the keywords value is not
	 *         <code>null</code>, the search uses the OR operator in connecting
	 *         query criteria; otherwise it uses the AND operator.
	 * @param  version the web content article's version (optionally
	 *         <code>null</code>)
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  displayDateGT the date after which a matching web content
	 *         article's display date must be after (optionally
	 *         <code>null</code>)
	 * @param  displayDateLT the date before which a matching web content
	 *         article's display date must be before (optionally
	 *         <code>null</code>)
	 * @param  reviewDate the web content article's scheduled review date
	 *         (optionally <code>null</code>)
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  orderByComparator the comparator to order the web content
	 *         articles
	 * @return the range of matching web content articles ordered by the
	 *         comparator
	 */
	@Override
	public List<JournalArticle> search(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String keywords, Double version, String ddmStructureKey,
		String ddmTemplateKey, Date displayDateGT, Date displayDateLT,
		Date reviewDate, int status, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		return journalArticleFinder.findByKeywords(
			companyId, groupId, folderIds, classNameId, keywords, version,
			ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
			reviewDate, status, start, end, orderByComparator);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * parameters without using the indexer, including keyword parameters for
	 * article ID, title, description, and content, a DDM structure key
	 * parameter, a DDM template key parameter, and an AND operator switch. It
	 * is preferable to use the indexed version {@link #search(long, long, List,
	 * long, String, String, String, String, int, String, String, LinkedHashMap,
	 * boolean, int, int, Sort)} instead of this method wherever possible for
	 * performance reasons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or
	 *         JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 *         module otherwise
	 * @param  articleId the article ID keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  version the web content article's version (optionally
	 *         <code>null</code>)
	 * @param  title the title keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  description the description keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  content the content keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  displayDateGT the date after which a matching web content
	 *         article's display date must be after (optionally
	 *         <code>null</code>)
	 * @param  displayDateLT the date before which a matching web content
	 *         article's display date must be before (optionally
	 *         <code>null</code>)
	 * @param  reviewDate the web content article's scheduled review date
	 *         (optionally <code>null</code>)
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  andOperator whether every field must match its value or keywords,
	 *         or just one field must match. Company, group, folder IDs, class
	 *         name ID, and status must all match their values.
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  orderByComparator the comparator to order the web content
	 *         articles
	 * @return the range of matching web content articles ordered by the
	 *         comparator
	 */
	@Override
	public List<JournalArticle> search(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String articleId, Double version, String title, String description,
		String content, String ddmStructureKey, String ddmTemplateKey,
		Date displayDateGT, Date displayDateLT, Date reviewDate, int status,
		boolean andOperator, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		QueryDefinition<JournalArticle> queryDefinition = new QueryDefinition<>(
			status, start, end, orderByComparator);

		return journalArticleFinder.findByC_G_F_C_A_V_T_D_C_S_T_D_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, ddmStructureKey, ddmTemplateKey,
			displayDateGT, displayDateLT, reviewDate, andOperator,
			queryDefinition);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * parameters without using the indexer, including keyword parameters for
	 * article ID, title, description, and content, a DDM structure keys
	 * (plural) parameter, a DDM template keys (plural) parameter, and an AND
	 * operator switch.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or
	 *         JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 *         module otherwise
	 * @param  articleId the article ID keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  version the web content article's version (optionally
	 *         <code>null</code>)
	 * @param  title the title keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  description the description keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  content the content keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  ddmStructureKeys the primary keys of the web content article's
	 *         DDM structures, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKeys the primary keys of the web content article's DDM
	 *         templates (originally <code>null</code>). If the articles are
	 *         related to a DDM structure, the template's structure must match
	 *         it.
	 * @param  displayDateGT the date after which a matching web content
	 *         article's display date must be after (optionally
	 *         <code>null</code>)
	 * @param  displayDateLT the date before which a matching web content
	 *         article's display date must be before (optionally
	 *         <code>null</code>)
	 * @param  reviewDate the web content article's scheduled review date
	 *         (optionally <code>null</code>)
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  andOperator whether every field must match its value or keywords,
	 *         or just one field must match.  Company, group, folder IDs, class
	 *         name ID, and status must all match their values.
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  orderByComparator the comparator to order the web content
	 *         articles
	 * @return the range of matching web content articles ordered by the
	 *         comparator
	 */
	@Override
	public List<JournalArticle> search(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String articleId, Double version, String title, String description,
		String content, String[] ddmStructureKeys, String[] ddmTemplateKeys,
		Date displayDateGT, Date displayDateLT, Date reviewDate, int status,
		boolean andOperator, int start, int end,
		OrderByComparator<JournalArticle> orderByComparator) {

		QueryDefinition<JournalArticle> queryDefinition = new QueryDefinition<>(
			status, start, end, orderByComparator);

		return journalArticleFinder.findByC_G_F_C_A_V_T_D_C_S_T_D_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, ddmStructureKeys, ddmTemplateKeys,
			displayDateGT, displayDateLT, reviewDate, andOperator,
			queryDefinition);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * parameters using the indexer, including a keywords parameter for matching
	 * an article's ID, title, description, or content, a DDM structure key
	 * parameter, a DDM template key parameter, and a finder hash map parameter.
	 * It is preferable to use this method instead of the non-indexed version
	 * whenever possible for performance reasons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or
	 *         JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 *         module otherwise
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         web content article ID, title, description, or content
	 *         (optionally <code>null</code>). If the keywords value is not
	 *         <code>null</code>, the search uses the OR operator in connecting
	 *         query criteria; otherwise it uses the AND operator.
	 * @param  params the finder parameters (optionally <code>null</code>)
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  sort the field, type, and direction by which to sort (optionally
	 *         <code>null</code>)
	 * @return the matching web content articles ordered by <code>sort</code>
	 */
	@Override
	public Hits search(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String ddmStructureKey, String ddmTemplateKey, String keywords,
		LinkedHashMap<String, Object> params, int start, int end, Sort sort) {

		String articleId = null;
		String title = null;
		String description = null;
		String content = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			articleId = keywords;
			title = keywords;
			description = keywords;
			content = keywords;
		}
		else {
			andOperator = true;
		}

		if (params != null) {
			params.put("keywords", keywords);
		}

		return search(
			companyId, groupId, folderIds, classNameId, articleId, title,
			description, content, WorkflowConstants.STATUS_ANY, ddmStructureKey,
			ddmTemplateKey, params, andOperator, start, end, sort);
	}

	/**
	 * Returns an ordered range of all the web content articles matching the
	 * parameters using the indexer, including a keywords parameter for matching
	 * an article's ID, title, description, or content, a DDM structure key
	 * parameter, a DDM template key parameter, an AND operator switch, and
	 * parameters for type, status, a finder hash map. It is preferable to use
	 * this method instead of the non-indexed version whenever possible for
	 * performance reasons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or
	 *         JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 *         module otherwise
	 * @param  articleId the article ID keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  title the title keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  description the description keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  content the content keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  params the finder parameters (optionally <code>null</code>). The
	 *         <code>includeDiscussions</code> parameter can be set to
	 *         <code>true</code> to search for the keywords in the web content
	 *         article discussions.
	 * @param  andSearch whether every field must match its value or keywords,
	 *         or just one field must match
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  sort the field, type, and direction by which to sort (optionally
	 *         <code>null</code>)
	 * @return the matching web content articles ordered by <code>sort</code>
	 */
	@Override
	public Hits search(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String articleId, String title, String description, String content,
		int status, String ddmStructureKey, String ddmTemplateKey,
		LinkedHashMap<String, Object> params, boolean andSearch, int start,
		int end, Sort sort) {

		try {
			Indexer<JournalArticle> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(JournalArticle.class);

			SearchContext searchContext = buildSearchContext(
				companyId, groupId, folderIds, classNameId, articleId, title,
				description, content, status, ddmStructureKey, ddmTemplateKey,
				params, andSearch, start, end, sort);

			return indexer.search(searchContext);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	/**
	 * Returns a range of all the web content articles matching the group,
	 * creator, and status using the indexer. It is preferable to use this
	 * method instead of the non-indexed version whenever possible for
	 * performance reasons.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  userId the primary key of the user searching for web content
	 *         articles
	 * @param  creatorUserId the primary key of the web content article's
	 *         creator
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @return the matching web content articles
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public Hits search(
			long groupId, long userId, long creatorUserId, int status,
			int start, int end)
		throws PortalException {

		Indexer<JournalArticle> indexer = IndexerRegistryUtil.getIndexer(
			JournalArticle.class.getName());

		SearchContext searchContext = buildSearchContext(
			groupId, userId, creatorUserId, status, start, end);

		return indexer.search(searchContext);
	}

	/**
	 * Returns the number of web content articles matching the group, folders,
	 * and status.
	 *
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link Collections#EMPTY_LIST})
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @return the number of matching web content articles
	 */
	@Override
	public int searchCount(long groupId, List<Long> folderIds, int status) {
		QueryDefinition<JournalArticle> queryDefinition = new QueryDefinition<>(
			status);

		return journalArticleFinder.countByG_F(
			groupId, folderIds, queryDefinition);
	}

	/**
	 * Returns the number of web content articles matching the group, folder,
	 * and status.
	 *
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderId the primary key of the web content article folder
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @return the number of matching web content articles
	 */
	@Override
	public int searchCount(long groupId, long folderId, int status) {
		return searchCount(groupId, ListUtil.fromArray(folderId), status);
	}

	/**
	 * Returns the number of web content articles matching the parameters,
	 * including a keywords parameter for matching with the article's ID, title,
	 * description, and content, a DDM structure key parameter, and a DDM
	 * template key parameter.
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or
	 *         JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 *         module otherwise
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         web content article ID, title, description, or content
	 *         (optionally <code>null</code>). If the keywords value is not
	 *         <code>null</code>, the search uses the OR operator in connecting
	 *         query criteria; otherwise it uses the AND operator.
	 * @param  version the web content article's version (optionally
	 *         <code>null</code>)
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  displayDateGT the date after which a matching web content
	 *         article's display date must be after (optionally
	 *         <code>null</code>)
	 * @param  displayDateLT the date before which a matching web content
	 *         article's display date must be before (optionally
	 *         <code>null</code>)
	 * @param  reviewDate the web content article's scheduled review date
	 *         (optionally <code>null</code>)
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @return the number of matching web content articles
	 */
	@Override
	public int searchCount(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String keywords, Double version, String ddmStructureKey,
		String ddmTemplateKey, Date displayDateGT, Date displayDateLT,
		Date reviewDate, int status) {

		return journalArticleFinder.countByKeywords(
			companyId, groupId, folderIds, classNameId, keywords, version,
			ddmStructureKey, ddmTemplateKey, displayDateGT, displayDateLT,
			reviewDate, status);
	}

	/**
	 * Returns the number of web content articles matching the parameters,
	 * including keyword parameters for article ID, title, description, and
	 * content, a DDM structure key parameter, a DDM template key parameter, and
	 * an AND operator switch.
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or
	 *         JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 *         module otherwise
	 * @param  articleId the article ID keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  version the web content article's version (optionally
	 *         <code>null</code>)
	 * @param  title the title keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  description the description keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  content the content keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  displayDateGT the date after which a matching web content
	 *         article's display date must be after (optionally
	 *         <code>null</code>)
	 * @param  displayDateLT the date before which a matching web content
	 *         article's display date must be before (optionally
	 *         <code>null</code>)
	 * @param  reviewDate the web content article's scheduled review date
	 *         (optionally <code>null</code>)
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  andOperator whether every field must match its value or keywords,
	 *         or just one field must match. Group, folder IDs, class name ID,
	 *         and status must all match their values.
	 * @return the number of matching web content articles
	 */
	@Override
	public int searchCount(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String articleId, Double version, String title, String description,
		String content, String ddmStructureKey, String ddmTemplateKey,
		Date displayDateGT, Date displayDateLT, Date reviewDate, int status,
		boolean andOperator) {

		return journalArticleFinder.countByC_G_F_C_A_V_T_D_C_S_T_D_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, ddmStructureKey, ddmTemplateKey,
			displayDateGT, displayDateLT, reviewDate, andOperator,
			new QueryDefinition<JournalArticle>(status));
	}

	/**
	 * Returns the number of web content articles matching the parameters,
	 * including keyword parameters for article ID, title, description, and
	 * content, a DDM structure keys (plural) parameter, a DDM template keys
	 * (plural) parameter, and an AND operator switch.
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class if the web
	 *         content article is related to a DDM structure, the primary key of
	 *         the class name associated with the article, or
	 *         JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 *         module otherwise
	 * @param  articleId the article ID keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  version the web content article's version (optionally
	 *         <code>null</code>)
	 * @param  title the title keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  description the description keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  content the content keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  ddmStructureKeys the primary keys of the web content article's
	 *         DDM structures, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKeys the primary keys of the web content article's DDM
	 *         templates (originally <code>null</code>). If the articles are
	 *         related to a DDM structure, the template's structure must match
	 *         it.
	 * @param  displayDateGT the date after which a matching web content
	 *         article's display date must be after (optionally
	 *         <code>null</code>)
	 * @param  displayDateLT the date before which a matching web content
	 *         article's display date must be before (optionally
	 *         <code>null</code>)
	 * @param  reviewDate the web content article's scheduled review date
	 *         (optionally <code>null</code>)
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  andOperator whether every field must match its value or keywords,
	 *         or just one field must match.  Group, folder IDs, class name ID,
	 *         and status must all match their values.
	 * @return the number of matching web content articles
	 */
	@Override
	public int searchCount(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String articleId, Double version, String title, String description,
		String content, String[] ddmStructureKeys, String[] ddmTemplateKeys,
		Date displayDateGT, Date displayDateLT, Date reviewDate, int status,
		boolean andOperator) {

		return journalArticleFinder.countByC_G_F_C_A_V_T_D_C_S_T_D_R(
			companyId, groupId, folderIds, classNameId, articleId, version,
			title, description, content, ddmStructureKeys, ddmTemplateKeys,
			displayDateGT, displayDateLT, reviewDate, andOperator,
			new QueryDefinition<JournalArticle>(status));
	}

	/**
	 * Returns a {@link BaseModelSearchResult} containing the total number of
	 * hits and an ordered range of all the web content articles matching the
	 * parameters using the indexer, including a keywords parameter for matching
	 * an article's ID, title, description, or content, a DDM structure key
	 * parameter, a DDM template key parameter, and a finder hash map parameter.
	 * It is preferable to use this method instead of the non-indexed version
	 * whenever possible for performance reasons.
	 *
	 * <p>
	 * The <code>start</code> and <code>end</code> parameters only affect the
	 * amount of web content articles returned as results, not the total number
	 * of hits.
	 * </p>
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class, the
	 *         primary key of the class name associated with the article, or
	 *         JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 *         module otherwise
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  keywords the keywords (space separated), which may occur in the
	 *         web content article ID, title, description, or content
	 *         (optionally <code>null</code>). If the keywords value is not
	 *         <code>null</code>, the search uses the OR operator in connecting
	 *         query criteria; otherwise it uses the AND operator.
	 * @param  params the finder parameters (optionally <code>null</code>)
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  sort the field, type, and direction by which to sort (optionally
	 *         <code>null</code>)
	 * @return a {@link BaseModelSearchResult} containing the total number of
	 *         hits and an ordered range of all the matching web content
	 *         articles ordered by <code>sort</code>
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public BaseModelSearchResult<JournalArticle> searchJournalArticles(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String ddmStructureKey, String ddmTemplateKey,
			String keywords, LinkedHashMap<String, Object> params, int start,
			int end, Sort sort)
		throws PortalException {

		String articleId = null;
		String title = null;
		String description = null;
		String content = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			articleId = keywords;
			title = keywords;
			description = keywords;
			content = keywords;
		}
		else {
			andOperator = true;
		}

		if (params != null) {
			params.put("keywords", keywords);
		}

		return searchJournalArticles(
			companyId, groupId, folderIds, classNameId, articleId, title,
			description, content, WorkflowConstants.STATUS_ANY, ddmStructureKey,
			ddmTemplateKey, params, andOperator, start, end, sort);
	}

	/**
	 * Returns a {@link BaseModelSearchResult} containing the total number of
	 * hits and an ordered range of all the web content articles matching the
	 * parameters using the indexer, including keyword parameters for article
	 * ID, title, description, or content, a DDM structure key parameter, a DDM
	 * template key parameter, an AND operator switch, and parameters for type,
	 * status, and a finder hash map. It is preferable to use this method
	 * instead of the non-indexed version whenever possible for performance
	 * reasons.
	 *
	 * <p>
	 * The <code>start</code> and <code>end</code> parameters only affect the
	 * amount of web content articles returned as results, not the total number
	 * of hits.
	 * </p>
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  companyId the primary key of the web content article's company
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  folderIds the primary keys of the web content article folders
	 *         (optionally {@link Collections#EMPTY_LIST})
	 * @param  classNameId the primary key of the DDMStructure class, the
	 *         primary key of the class name associated with the article, or
	 *         JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 *         module otherwise
	 * @param  articleId the article ID keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  title the title keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  description the description keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  content the content keywords (space separated, optionally
	 *         <code>null</code>)
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  params the finder parameters (optionally <code>null</code>). The
	 *         <code>includeDiscussions</code> parameter can be set to
	 *         <code>true</code> to search for the keywords in the web content
	 *         article discussions.
	 * @param  andSearch whether every field must match its value or keywords,
	 *         or just one field must match
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @param  sort the field, type, and direction by which to sort (optionally
	 *         <code>null</code>)
	 * @return a {@link BaseModelSearchResult} containing the total number of
	 *         hits and an ordered range of all the matching web content
	 *         articles ordered by <code>sort</code>
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public BaseModelSearchResult<JournalArticle> searchJournalArticles(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String articleId, String title,
			String description, String content, int status,
			String ddmStructureKey, String ddmTemplateKey,
			LinkedHashMap<String, Object> params, boolean andSearch, int start,
			int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, folderIds, classNameId, articleId, title,
			description, content, status, ddmStructureKey, ddmTemplateKey,
			params, andSearch, start, end, sort);

		return searchJournalArticles(searchContext);
	}

	/**
	 * Returns a {@link BaseModelSearchResult} containing the total number of
	 * hits and an ordered range of all the web content articles matching the
	 * parameters using the indexer, including the web content article's creator
	 * ID and status. It is preferable to use this method instead of the
	 * non-indexed version whenever possible for performance reasons.
	 *
	 * <p>
	 * The <code>start</code> and <code>end</code> parameters only affect the
	 * amount of web content articles returned as results, not the total number
	 * of hits.
	 * </p>
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full
	 * result set.
	 * </p>
	 *
	 * @param  groupId the primary key of the group (optionally <code>0</code>)
	 * @param  userId the primary key of the user searching for web content
	 *         articles
	 * @param  creatorUserId the primary key of the web content article's
	 *         creator
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  start the lower bound of the range of web content articles to
	 *         return
	 * @param  end the upper bound of the range of web content articles to
	 *         return (not inclusive)
	 * @return a {@link BaseModelSearchResult} containing the total number of
	 *         hits and an ordered range of all the matching web content
	 *         articles ordered by <code>sort</code>
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public BaseModelSearchResult<JournalArticle> searchJournalArticles(
			long groupId, long userId, long creatorUserId, int status,
			int start, int end)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			groupId, userId, creatorUserId, status, start, end);

		return searchJournalArticles(searchContext);
	}

	@Override
	public void setTreePaths(long folderId, String treePath, boolean reindex)
		throws PortalException {

		if (treePath == null) {
			throw new IllegalArgumentException("Tree path is null");
		}

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property folderIdProperty = PropertyFactoryUtil.forName(
					"folderId");

				dynamicQuery.add(folderIdProperty.eq(folderId));

				Property treePathProperty = PropertyFactoryUtil.forName(
					"treePath");

				dynamicQuery.add(
					RestrictionsFactoryUtil.or(
						treePathProperty.isNull(),
						treePathProperty.ne(treePath)));
			});

		Indexer<JournalArticle> indexer = IndexerRegistryUtil.getIndexer(
			JournalArticle.class.getName());

		indexableActionableDynamicQuery.setPerformActionMethod(
			(JournalArticle article) -> {
				article.setTreePath(treePath);

				updateJournalArticle(article);

				if (!reindex) {
					return;
				}

				indexableActionableDynamicQuery.setCompanyId(
					article.getCompanyId());

				indexableActionableDynamicQuery.addDocuments(
					indexer.getDocument(article));
			});

		indexableActionableDynamicQuery.performActions();
	}

	/**
	 * Subscribes the user to changes in elements that belong to the web content
	 * article.
	 *
	 * @param  userId the primary key of the user to be subscribed
	 * @param  groupId the primary key of the folder's group
	 * @param  articleId the primary key of the article to subscribe to
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void subscribe(long userId, long groupId, long articleId)
		throws PortalException {

		_subscriptionLocalService.addSubscription(
			userId, groupId, JournalArticle.class.getName(), articleId);
	}

	/**
	 * Subscribes the user to changes in elements that belong to the web content
	 * article's DDM structure.
	 *
	 * @param  groupId the primary key of the folder's group
	 * @param  userId the primary key of the user to be subscribed
	 * @param  ddmStructureId the primary key of the structure to subscribe to
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void subscribeStructure(
			long groupId, long userId, long ddmStructureId)
		throws PortalException {

		_subscriptionLocalService.addSubscription(
			userId, groupId, DDMStructure.class.getName(), ddmStructureId);
	}

	/**
	 * Unsubscribes the user from changes in elements that belong to the web
	 * content article.
	 *
	 * @param  userId the primary key of the user to be subscribed
	 * @param  groupId the primary key of the folder's group
	 * @param  articleId the primary key of the article to unsubscribe from
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void unsubscribe(long userId, long groupId, long articleId)
		throws PortalException {

		_subscriptionLocalService.deleteSubscription(
			userId, JournalArticle.class.getName(), articleId);
	}

	/**
	 * Unsubscribes the user from changes in elements that belong to the web
	 * content article's DDM structure.
	 *
	 * @param  groupId the primary key of the folder's group
	 * @param  userId the primary key of the user to be subscribed
	 * @param  ddmStructureId the primary key of the structure to subscribe to
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void unsubscribeStructure(
			long groupId, long userId, long ddmStructureId)
		throws PortalException {

		_subscriptionLocalService.deleteSubscription(
			userId, DDMStructure.class.getName(), ddmStructureId);
	}

	/**
	 * Updates the web content article with additional parameters. All
	 * scheduling parameters (display date, expiration date, and review date)
	 * use the current user's timezone.
	 *
	 * @param  userId the primary key of the user updating the web content
	 *         article
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  titleMap the web content article's locales and localized titles
	 * @param  descriptionMap the web content article's locales and localized
	 *         descriptions
	 * @param  friendlyURLMap the web content article's locales and localized
	 *         friendly URLs
	 * @param  content the HTML content wrapped in XML. For more information,
	 *         see the content example in the {@link #addArticle(String, long,
	 *         long, long, long, long, String, boolean, double, Map, Map, Map,
	 *         String, String, String, String, int, int, int, int, int, int,
	 *         int, int, int, int, boolean, int, int, int, int, int, boolean,
	 *         boolean, boolean, String, File, Map, String, ServiceContext)}
	 *         description.
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  layoutUuid the unique string identifying the web content
	 *         article's display page
	 * @param  displayDateMonth the month the web content article is set to
	 *         display
	 * @param  displayDateDay the calendar day the web content article is set to
	 *         display
	 * @param  displayDateYear the year the web content article is set to
	 *         display
	 * @param  displayDateHour the hour the web content article is set to
	 *         display
	 * @param  displayDateMinute the minute the web content article is set to
	 *         display
	 * @param  expirationDateMonth the month the web content article is set to
	 *         expire
	 * @param  expirationDateDay the calendar day the web content article is set
	 *         to expire
	 * @param  expirationDateYear the year the web content article is set to
	 *         expire
	 * @param  expirationDateHour the hour the web content article is set to
	 *         expire
	 * @param  expirationDateMinute the minute the web content article is set to
	 *         expire
	 * @param  neverExpire whether the web content article is not set to auto
	 *         expire
	 * @param  reviewDateMonth the month the web content article is set for
	 *         review
	 * @param  reviewDateDay the calendar day the web content article is set for
	 *         review
	 * @param  reviewDateYear the year the web content article is set for review
	 * @param  reviewDateHour the hour the web content article is set for review
	 * @param  reviewDateMinute the minute the web content article is set for
	 *         review
	 * @param  neverReview whether the web content article is not set for review
	 * @param  indexable whether the web content is searchable
	 * @param  smallImage whether to update web content article's a small image.
	 *         A file must be passed in as <code>smallImageFile</code> value,
	 *         otherwise the current small image is deleted.
	 * @param  smallImageURL the web content article's small image URL
	 *         (optionally <code>null</code>)
	 * @param  smallImageFile the web content article's new small image file
	 *         (optionally <code>null</code>). Must pass in
	 *         <code>smallImage</code> value of <code>true</code> to replace the
	 *         article's small image file.
	 * @param  images the web content's images (optionally <code>null</code>)
	 * @param  articleURL the web content article's accessible URL (optionally
	 *         <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, expando bridge attributes, asset category IDs,
	 *         asset tag names, asset link entry IDs, asset priority, workflow
	 *         actions, URL title , and can set whether to add the default
	 *         command update for the web content article. With respect to
	 *         social activities, by setting the service context's command to
	 *         {@link Constants#UPDATE}, the invocation is considered a web
	 *         content update activity; otherwise it is considered a web content
	 *         add activity.
	 * @return the updated web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
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

		// Article

		articleId = StringUtil.toUpperCase(StringUtil.trim(articleId));

		byte[] smallImageBytes = null;

		try {
			smallImageBytes = FileUtil.getBytes(smallImageFile);
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}
		}

		JournalArticle latestArticle = getLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_ANY);

		JournalArticle article = latestArticle;

		boolean imported = ExportImportThreadLocal.isImportInProcess();

		boolean addNewVersion = false;

		if (imported) {
			article = getArticle(groupId, articleId, version);
		}
		else {
			double latestArticleVersion = latestArticle.getVersion();

			if ((version > 0) && (version != latestArticleVersion)) {
				throw new ArticleVersionException(
					StringBundler.concat(
						"Version ", version, " is not the same as ",
						latestArticleVersion));
			}

			serviceContext.validateModifiedDate(
				latestArticle, ArticleVersionException.class);

			if (latestArticle.isApproved() || latestArticle.isExpired() ||
				latestArticle.isScheduled()) {

				addNewVersion = true;

				version = getNextVersion(article);
			}
		}

		User user = _userLocalService.getUser(userId);

		Date displayDate = _portal.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(), null);

		Date expirationDate = null;
		Date reviewDate = null;

		if (!neverExpire) {
			expirationDate = _portal.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				ArticleExpirationDateException.class);
		}

		if (!neverReview) {
			reviewDate = _portal.getDate(
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, user.getTimeZone(),
				ArticleReviewDateException.class);
		}

		Date date = new Date();

		boolean expired = false;

		if ((expirationDate != null) && expirationDate.before(date)) {
			expired = true;
		}

		sanitize(
			user.getCompanyId(), groupId, userId, article.getClassPK(),
			descriptionMap);

		boolean validate = !ExportImportThreadLocal.isImportInProcess();

		if (validate) {
			validate(
				user.getCompanyId(), groupId, latestArticle.getClassNameId(),
				titleMap, content, ddmStructureKey, ddmTemplateKey, displayDate,
				expirationDate, smallImage, smallImageURL, smallImageFile,
				smallImageBytes, serviceContext);

			try {
				validateReferences(
					groupId, ddmStructureKey, ddmTemplateKey, layoutUuid,
					smallImage, smallImageURL, smallImageBytes,
					latestArticle.getSmallImageId(), content);
			}
			catch (ExportImportContentValidationException
						exportImportContentValidationException) {

				exportImportContentValidationException.setStagedModelClassName(
					JournalArticle.class.getName());
				exportImportContentValidationException.
					setStagedModelPrimaryKeyObj(articleId);

				throw exportImportContentValidationException;
			}
		}

		if (addNewVersion) {
			long id = counterLocalService.increment();

			article = journalArticlePersistence.create(id);

			article.setResourcePrimKey(latestArticle.getResourcePrimKey());
			article.setGroupId(latestArticle.getGroupId());
			article.setCompanyId(latestArticle.getCompanyId());
			article.setUserId(latestArticle.getUserId());
			article.setUserName(latestArticle.getUserName());
			article.setCreateDate(latestArticle.getCreateDate());
			article.setModifiedDate(serviceContext.getModifiedDate(date));
			article.setExternalReferenceCode(
				latestArticle.getExternalReferenceCode());
			article.setClassNameId(latestArticle.getClassNameId());
			article.setClassPK(latestArticle.getClassPK());
			article.setArticleId(articleId);
			article.setVersion(version);
			article.setSmallImageId(latestArticle.getSmallImageId());
			article.setStatusByUserId(user.getUserId());
			article.setStatusByUserName(user.getFullName());
			article.setStatusDate(serviceContext.getModifiedDate(date));

			serviceContext.setAttribute("version", version);

			_addArticleLocalizedFields(
				article.getCompanyId(), article.getId(), titleMap,
				descriptionMap);
		}
		else {
			_updateArticleLocalizedFields(
				article.getCompanyId(), article.getId(), titleMap,
				descriptionMap);
		}

		Locale locale = getArticleDefaultLocale(content);

		Map<String, String> urlTitleMap = _getURLTitleMap(
			groupId, article.getResourcePrimKey(), friendlyURLMap, titleMap);

		String urlTitle = urlTitleMap.get(LocaleUtil.toLanguageId(locale));

		if (Validator.isNull(urlTitle) &&
			(_classNameLocalService.getClassNameId(DDMStructure.class) !=
				article.getClassNameId())) {

			Group companyGroup = _groupLocalService.getCompanyGroup(
				article.getCompanyId());

			if (article.getGroupId() != companyGroup.getGroupId()) {
				throw new ArticleFriendlyURLException();
			}
		}

		article.setFolderId(folderId);
		article.setTreePath(article.buildTreePath());
		article.setUrlTitle(urlTitle);
		article.setDDMStructureKey(ddmStructureKey);
		article.setDDMTemplateKey(ddmTemplateKey);
		article.setDefaultLanguageId(LocaleUtil.toLanguageId(locale));
		article.setLayoutUuid(layoutUuid);
		article.setDisplayDate(displayDate);
		article.setExpirationDate(expirationDate);
		article.setReviewDate(reviewDate);
		article.setIndexable(indexable);
		article.setSmallImage(smallImage);

		if (smallImage) {
			if ((smallImageFile != null) && (smallImageBytes != null) &&
				(article.getSmallImageId() <= 0)) {

				article.setSmallImageId(counterLocalService.increment());
			}
		}
		else {
			article.setSmallImageId(0);
		}

		article.setSmallImageURL(smallImageURL);

		if (latestArticle.isPending()) {
			article.setStatus(latestArticle.getStatus());
		}
		else if (!expired) {
			article.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			article.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		ExpandoBridgeUtil.setExpandoBridgeAttributes(
			latestArticle.getExpandoBridge(), article.getExpandoBridge(),
			serviceContext);

		article = journalArticlePersistence.update(article);

		// Friendly URLs

		updateFriendlyURLs(article, urlTitleMap, serviceContext);

		// Asset

		if (hasModifiedLatestApprovedVersion(groupId, articleId, version)) {
			updateAsset(
				userId, article, serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(),
				serviceContext.getAssetLinkEntryIds(),
				serviceContext.getAssetPriority());
		}

		// Dynamic data mapping

		updateDDMFields(
			article, _formatContent(article, content, groupId, user));

		if (_classNameLocalService.getClassNameId(DDMStructure.class) !=
				article.getClassNameId()) {

			updateDDMLinks(
				article.getId(), groupId, ddmStructureKey, ddmTemplateKey,
				addNewVersion);
		}

		// Small image

		saveImages(
			article.getCompanyId(), smallImage, article.getSmallImageId(),
			smallImageFile, smallImageBytes);

		// Email and workflow

		if (expired && imported) {
			article = updateStatus(
				userId, article, article.getStatus(), articleURL,
				serviceContext, new HashMap<>());
		}

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_PUBLISH) {

			articleURL = buildArticleURL(
				articleURL, groupId, folderId, articleId);

			serviceContext.setAttribute("articleURL", articleURL);

			sendEmail(article, articleURL, "requested", serviceContext);

			startWorkflowInstance(userId, article, serviceContext);
		}

		return article;
	}

	/**
	 * Updates the web content article matching the version, replacing its
	 * folder, title, description, content, and layout UUID.
	 *
	 * @param  userId the primary key of the user updating the web content
	 *         article
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  titleMap the web content article's locales and localized titles
	 * @param  descriptionMap the web content article's locales and localized
	 *         descriptions
	 * @param  content the HTML content wrapped in XML. For more information,
	 *         see the content example in the {@link #addArticle(String, long,
	 *         long, long, long, long, String, boolean, double, Map, Map, Map,
	 *         String, String, String, String, int, int, int, int, int, int,
	 *         int, int, int, int, boolean, int, int, int, int, int, boolean,
	 *         boolean, boolean, String, File, Map, String, ServiceContext)}
	 *         description.
	 * @param  layoutUuid the unique string identifying the web content
	 *         article's display page
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, expando bridge attributes, asset category IDs,
	 *         asset tag names, asset link entry IDs, asset priority, workflow
	 *         actions, URL title, and can set whether to add the default
	 *         command update for the web content article. With respect to
	 *         social activities, by setting the service context's command to
	 *         {@link Constants#UPDATE}, the invocation is considered a web
	 *         content update activity; otherwise it is considered a web content
	 *         add activity.
	 * @return the updated web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String content,
			String layoutUuid, ServiceContext serviceContext)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

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

		return journalArticleLocalService.updateArticle(
			userId, groupId, folderId, articleId, version, titleMap,
			descriptionMap, content, article.getDDMStructureKey(),
			article.getDDMTemplateKey(), layoutUuid, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, article.isIndexable(),
			article.isSmallImage(), article.getSmallImageURL(), null, null,
			null, serviceContext);
	}

	/**
	 * Updates the web content article with additional parameters. All
	 * scheduling parameters (display date, expiration date, and review date)
	 * use the current user's timezone.
	 *
	 * @param  userId the primary key of the user updating the web content
	 *         article
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  titleMap the web content article's locales and localized titles
	 * @param  descriptionMap the web content article's locales and localized
	 *         descriptions
	 * @param  content the HTML content wrapped in XML. For more information,
	 *         see the content example in the {@link #addArticle(String, long,
	 *         long, long, long, long, String, boolean, double, Map, Map, Map,
	 *         String, String, String, String, int, int, int, int, int, int,
	 *         int, int, int, int, boolean, int, int, int, int, int, boolean,
	 *         boolean, boolean, String, File, Map, String, ServiceContext)}
	 *         description.
	 * @param  ddmStructureKey the primary key of the web content article's DDM
	 *         structure, if the article is related to a DDM structure, or
	 *         <code>null</code> otherwise
	 * @param  ddmTemplateKey the primary key of the web content article's DDM
	 *         template
	 * @param  layoutUuid the unique string identifying the web content
	 *         article's display page
	 * @param  displayDateMonth the month the web content article is set to
	 *         display
	 * @param  displayDateDay the calendar day the web content article is set to
	 *         display
	 * @param  displayDateYear the year the web content article is set to
	 *         display
	 * @param  displayDateHour the hour the web content article is set to
	 *         display
	 * @param  displayDateMinute the minute the web content article is set to
	 *         display
	 * @param  expirationDateMonth the month the web content article is set to
	 *         expire
	 * @param  expirationDateDay the calendar day the web content article is set
	 *         to expire
	 * @param  expirationDateYear the year the web content article is set to
	 *         expire
	 * @param  expirationDateHour the hour the web content article is set to
	 *         expire
	 * @param  expirationDateMinute the minute the web content article is set to
	 *         expire
	 * @param  neverExpire whether the web content article is not set to auto
	 *         expire
	 * @param  reviewDateMonth the month the web content article is set for
	 *         review
	 * @param  reviewDateDay the calendar day the web content article is set for
	 *         review
	 * @param  reviewDateYear the year the web content article is set for review
	 * @param  reviewDateHour the hour the web content article is set for review
	 * @param  reviewDateMinute the minute the web content article is set for
	 *         review
	 * @param  neverReview whether the web content article is not set for review
	 * @param  indexable whether the web content is searchable
	 * @param  smallImage whether to update web content article's a small image.
	 *         A file must be passed in as <code>smallImageFile</code> value,
	 *         otherwise the current small image is deleted.
	 * @param  smallImageURL the web content article's small image URL
	 *         (optionally <code>null</code>)
	 * @param  smallImageFile the web content article's new small image file
	 *         (optionally <code>null</code>). Must pass in
	 *         <code>smallImage</code> value of <code>true</code> to replace the
	 *         article's small image file.
	 * @param  images the web content's images (optionally <code>null</code>)
	 * @param  articleURL the web content article's accessible URL (optionally
	 *         <code>null</code>)
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, expando bridge attributes, asset category IDs,
	 *         asset tag names, asset link entry IDs, asset priority, workflow
	 *         actions, URL title , and can set whether to add the default
	 *         command update for the web content article. With respect to
	 *         social activities, by setting the service context's command to
	 *         {@link Constants#UPDATE}, the invocation is considered a web
	 *         content update activity; otherwise it is considered a web content
	 *         add activity.
	 * @return the updated web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
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

		JournalArticle article = getLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_ANY);

		Map<Locale, String> friendlyURLMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : titleMap.entrySet()) {
			String title = entry.getValue();

			if (Validator.isNull(title)) {
				continue;
			}

			String urlTitle = friendlyURLEntryLocalService.getUniqueUrlTitle(
				groupId,
				_classNameLocalService.getClassNameId(JournalArticle.class),
				article.getResourcePrimKey(), title,
				LanguageUtil.getLanguageId(entry.getKey()));

			friendlyURLMap.put(entry.getKey(), urlTitle);
		}

		return journalArticleLocalService.updateArticle(
			userId, groupId, folderId, articleId, version, titleMap,
			descriptionMap, friendlyURLMap, content, ddmStructureKey,
			ddmTemplateKey, layoutUuid, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
			reviewDateMinute, neverReview, indexable, smallImage, smallImageURL,
			null, null, null, serviceContext);
	}

	/**
	 * Updates the web content article matching the version, replacing its
	 * folder and content.
	 *
	 * @param  userId the primary key of the user updating the web content
	 *         article
	 * @param  groupId the primary key of the web content article's group
	 * @param  folderId the primary key of the web content article folder
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  content the HTML content wrapped in XML. For more information,
	 *         see the content example in the {@link #addArticle(String, long,
	 *         long, long, long, long, String, boolean, double, Map, Map, Map,
	 *         String, String, String, String, int, int, int, int, int, int,
	 *         int, int, int, int, boolean, int, int, int, int, int, boolean,
	 *         boolean, boolean, String, File, Map, String, ServiceContext)}
	 *         description.
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, expando bridge attributes, asset category IDs,
	 *         asset tag names, asset link entry IDs, asset priority, workflow
	 *         actions, URL title, and can set whether to add the default
	 *         command update for the web content article. With respect to
	 *         social activities, by setting the service context's command to
	 *         {@link Constants#UPDATE}, the invocation is considered a web
	 *         content update activity; otherwise it is considered a web content
	 *         add activity.
	 * @return the updated web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle updateArticle(
			long userId, long groupId, long folderId, String articleId,
			double version, String content, ServiceContext serviceContext)
		throws PortalException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		return journalArticleLocalService.updateArticle(
			userId, groupId, folderId, articleId, version,
			article.getTitleMap(), article.getDescriptionMap(), content,
			article.getLayoutUuid(), serviceContext);
	}

	/**
	 * Updates the URL title of the web content article.
	 *
	 * @param  id the primary key of the web content article
	 * @param  urlTitle the web content article's URL title
	 * @return the updated web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public JournalArticle updateArticle(long id, String urlTitle)
		throws PortalException {

		JournalArticle article = journalArticlePersistence.findByPrimaryKey(id);

		article.setUrlTitle(urlTitle);

		return journalArticlePersistence.update(article);
	}

	@Override
	public JournalArticle updateArticleDefaultValues(
			long userId, long groupId, String articleId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String content, String ddmStructureKey, String ddmTemplateKey,
			String layoutUuid, boolean indexable, boolean smallImage,
			String smallImageURL, File smallImageFile,
			ServiceContext serviceContext)
		throws PortalException {

		// Article

		User user = _userLocalService.getUser(userId);
		articleId = StringUtil.toUpperCase(StringUtil.trim(articleId));

		byte[] smallImageBytes = null;

		try {
			smallImageBytes = FileUtil.getBytes(smallImageFile);
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}
		}

		JournalArticle article = getArticle(groupId, articleId);

		serviceContext.validateModifiedDate(
			article, ArticleVersionException.class);

		sanitize(
			user.getCompanyId(), groupId, userId, article.getClassPK(),
			descriptionMap);

		validate(
			user.getCompanyId(), groupId, article.getClassNameId(), titleMap,
			content, ddmStructureKey, ddmTemplateKey, null, null, smallImage,
			smallImageURL, smallImageFile, smallImageBytes, serviceContext);

		_updateArticleLocalizedFields(
			article.getCompanyId(), article.getId(), titleMap, descriptionMap);

		article.setDDMStructureKey(ddmStructureKey);
		article.setDDMTemplateKey(ddmTemplateKey);

		Locale locale = getArticleDefaultLocale(content);

		article.setDefaultLanguageId(LocaleUtil.toLanguageId(locale));

		article.setLayoutUuid(layoutUuid);
		article.setIndexable(indexable);
		article.setSmallImage(smallImage);

		if (smallImage) {
			if ((smallImageFile != null) && (smallImageBytes != null) &&
				(article.getSmallImageId() <= 0)) {

				article.setSmallImageId(counterLocalService.increment());
			}
		}
		else {
			article.setSmallImageId(0);
		}

		article.setSmallImageURL(smallImageURL);
		article.setStatus(WorkflowConstants.STATUS_APPROVED);

		ExpandoBridgeUtil.setExpandoBridgeAttributes(
			article.getExpandoBridge(), article.getExpandoBridge(),
			serviceContext);

		article = journalArticlePersistence.update(article);

		// Asset

		updateAsset(
			userId, article, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		// Dynamic data mapping

		content = _formatContent(article, content, groupId, user);

		updateDDMFields(article, content);

		// Small image

		saveImages(
			article.getCompanyId(), smallImage, article.getSmallImageId(),
			smallImageFile, smallImageBytes);

		return journalArticlePersistence.findByPrimaryKey(article.getId());
	}

	/**
	 * Updates the translation of the web content article.
	 *
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  locale the locale of the web content article's display template
	 * @param  title the translated web content article title
	 * @param  description the translated web content article description
	 * @param  content the HTML content wrapped in XML. For more information,
	 *         see the content example in the {@link #addArticle(String, long,
	 *         long, long, long, long, String, boolean, double, Map, Map, Map,
	 *         String, String, String, String, int, int, int, int, int, int,
	 *         int, int, int, int, boolean, int, int, int, int, int, boolean,
	 *         boolean, boolean, String, File, Map, String, ServiceContext)}
	 *         description.
	 * @param  images the web content's images
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date and URL title for the web content article.
	 * @return the updated web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public JournalArticle updateArticleTranslation(
			long groupId, String articleId, double version, Locale locale,
			String title, String description, String content,
			Map<String, byte[]> images, ServiceContext serviceContext)
		throws PortalException {

		validateContent(content);

		JournalArticle oldArticle = getLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_ANY);

		double oldVersion = oldArticle.getVersion();

		if ((version > 0) && (version != oldVersion)) {
			throw new ArticleVersionException(
				StringBundler.concat(
					"Version ", version, " is not the same as ", oldVersion));
		}

		boolean incrementVersion = false;

		if (oldArticle.isApproved() || oldArticle.isExpired()) {
			incrementVersion = true;
		}

		if (serviceContext != null) {
			serviceContext.validateModifiedDate(
				oldArticle, ArticleVersionException.class);
		}

		JournalArticle article = null;

		User user = _userLocalService.fetchUser(oldArticle.getUserId());

		if (user == null) {
			user = _userLocalService.getDefaultUser(oldArticle.getCompanyId());
		}

		if (incrementVersion) {
			double newVersion = getNextVersion(oldArticle);

			long id = counterLocalService.increment();

			article = journalArticlePersistence.create(id);

			article.setResourcePrimKey(oldArticle.getResourcePrimKey());
			article.setGroupId(oldArticle.getGroupId());
			article.setCompanyId(oldArticle.getCompanyId());
			article.setUserId(oldArticle.getUserId());
			article.setUserName(user.getFullName());
			article.setCreateDate(oldArticle.getCreateDate());
			article.setExternalReferenceCode(
				oldArticle.getExternalReferenceCode());
			article.setFolderId(oldArticle.getFolderId());
			article.setClassNameId(oldArticle.getClassNameId());
			article.setClassPK(oldArticle.getClassPK());
			article.setArticleId(articleId);
			article.setVersion(newVersion);
			article.setUrlTitle(
				getUniqueUrlTitle(
					id, groupId, articleId, title, oldArticle.getUrlTitle(),
					serviceContext));
			article.setDDMStructureKey(oldArticle.getDDMStructureKey());
			article.setDDMTemplateKey(oldArticle.getDDMTemplateKey());
			article.setDefaultLanguageId(
				LocaleUtil.toLanguageId(getArticleDefaultLocale(content)));
			article.setLayoutUuid(oldArticle.getLayoutUuid());
			article.setDisplayDate(oldArticle.getDisplayDate());
			article.setExpirationDate(oldArticle.getExpirationDate());
			article.setReviewDate(oldArticle.getReviewDate());
			article.setIndexable(oldArticle.isIndexable());
			article.setSmallImage(oldArticle.isSmallImage());
			article.setSmallImageId(oldArticle.getSmallImageId());

			if (article.getSmallImageId() == 0) {
				article.setSmallImageId(counterLocalService.increment());
			}

			article.setSmallImageURL(oldArticle.getSmallImageURL());

			article.setStatus(WorkflowConstants.STATUS_DRAFT);

			User statusUser = _userLocalService.fetchUser(
				serviceContext.getUserId());

			if (statusUser == null) {
				statusUser = _userLocalService.getDefaultUser(
					oldArticle.getCompanyId());
			}

			article.setStatusByUserId(statusUser.getUserId());
			article.setStatusByUserName(statusUser.getFullName());

			article.setStatusDate(new Date());

			ExpandoBridgeUtil.copyExpandoBridgeAttributes(
				oldArticle.getExpandoBridge(), article.getExpandoBridge());

			// Article localization

			_addArticleLocalizedFields(
				article.getCompanyId(), article.getId(),
				oldArticle.getTitleMap(), oldArticle.getDescriptionMap());
		}
		else {
			article = oldArticle;
		}

		_updateArticleLocalizedFields(
			article.getCompanyId(), article.getId(), title, description,
			LocaleUtil.toLanguageId(locale));

		article = journalArticlePersistence.update(article);

		// Dynamic data mapping

		updateDDMFields(
			article, _formatContent(article, content, groupId, user));

		if (incrementVersion) {
			updateDDMLinks(
				article.getId(), groupId, oldArticle.getDDMStructureKey(),
				oldArticle.getDDMTemplateKey(), true);
		}

		return article;
	}

	/**
	 * Updates the web content article's asset with the new asset categories,
	 * tag names, and link entries, removing and adding them as necessary.
	 *
	 * @param  userId the primary key of the user updating the web content
	 *         article's asset
	 * @param  article the web content article
	 * @param  assetCategoryIds the primary keys of the new asset categories
	 * @param  assetTagNames the new asset tag names
	 * @param  assetLinkEntryIds the primary keys of the new asset link entries
	 * @param  priority the priority of the asset
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public void updateAsset(
			long userId, JournalArticle article, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds, Double priority)
		throws PortalException {

		boolean visible = article.isApproved();

		if (article.getClassNameId() !=
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT) {

			visible = false;
		}

		boolean addDraftAssetEntry = _addDraftAssetEntry(article);

		AssetEntry assetEntry = null;

		String title = article.getTitleMapAsXML();
		String description = article.getDescriptionMapAsXML();

		if (addDraftAssetEntry) {
			assetEntry = _assetEntryLocalService.updateEntry(
				userId, article.getGroupId(), article.getCreateDate(),
				article.getModifiedDate(), JournalArticle.class.getName(),
				article.getPrimaryKey(), article.getUuid(),
				getClassTypeId(article), assetCategoryIds, assetTagNames,
				isListable(article), false, null, null, null,
				article.getExpirationDate(), ContentTypes.TEXT_HTML, title,
				description, description, null, article.getLayoutUuid(), 0, 0,
				priority);
		}
		else {
			JournalArticleResource journalArticleResource =
				_journalArticleResourceLocalService.getArticleResource(
					article.getResourcePrimKey());

			Date publishDate = null;

			if (article.isApproved()) {
				publishDate = article.getDisplayDate();
			}

			assetEntry = _assetEntryLocalService.updateEntry(
				userId, article.getGroupId(), article.getCreateDate(),
				article.getModifiedDate(), JournalArticle.class.getName(),
				journalArticleResource.getResourcePrimKey(),
				journalArticleResource.getUuid(), getClassTypeId(article),
				assetCategoryIds, assetTagNames, isListable(article), visible,
				null, null, publishDate, article.getExpirationDate(),
				ContentTypes.TEXT_HTML, title, description, description, null,
				article.getLayoutUuid(), 0, 0, priority);
		}

		_assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	/**
	 * Updates the web content articles matching the group, class name ID, and
	 * DDM template key, replacing the DDM template key with a new one.
	 *
	 * @param groupId the primary key of the web content article's group
	 * @param classNameId the primary key of the DDMStructure class if the web
	 *        content article is related to a DDM structure, the primary key of
	 *        the class name associated with the article, or
	 *        JournalArticleConstants.CLASS_NAME_ID_DEFAULT in the journal-api
	 *        module otherwise
	 * @param oldDDMTemplateKey the primary key of the web content article's old
	 *        DDM template
	 * @param newDDMTemplateKey the primary key of the web content article's new
	 *        DDM template
	 */
	@Override
	public void updateDDMTemplateKey(
		long groupId, long classNameId, String oldDDMTemplateKey,
		String newDDMTemplateKey) {

		List<JournalArticle> articles =
			journalArticlePersistence.findByG_C_DDMTK(
				groupId, classNameId, oldDDMTemplateKey);

		for (JournalArticle article : articles) {
			article.setDDMTemplateKey(newDDMTemplateKey);

			journalArticlePersistence.update(article);
		}
	}

	/**
	 * Updates the workflow status of the web content article.
	 *
	 * @param  userId the primary key of the user updating the web content
	 *         article's status
	 * @param  article the web content article
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  articleURL the web content article's accessible URL
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, status date, and portlet preferences. With
	 *         respect to social activities, by setting the service context's
	 *         command to {@link Constants#UPDATE}, the invocation is considered
	 *         a web content update activity; otherwise it is considered a web
	 *         content add activity.
	 * @param  workflowContext the web content article's configured workflow
	 *         context
	 * @return the updated web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public JournalArticle updateStatus(
			long userId, JournalArticle article, int status, String articleURL,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		// Article

		User user = _userLocalService.getUser(userId);

		Date date = new Date();

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(article.getDisplayDate() != null) &&
			date.before(article.getDisplayDate())) {

			status = WorkflowConstants.STATUS_SCHEDULED;
		}

		int oldStatus = article.getStatus();

		Date modifiedDate = serviceContext.getModifiedDate(date);

		article.setModifiedDate(modifiedDate);

		Date expirationDate = article.getExpirationDate();

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(expirationDate != null) && expirationDate.before(date)) {

			article.setExpirationDate(null);
		}

		if ((status == WorkflowConstants.STATUS_EXPIRED) &&
			(expirationDate == null)) {

			article.setExpirationDate(date);
		}

		article.setStatus(status);
		article.setStatusByUserId(user.getUserId());
		article.setStatusByUserName(user.getFullName());
		article.setStatusDate(modifiedDate);

		article = journalArticlePersistence.update(article);

		if (isExpireAllArticleVersions(article.getCompanyId()) &&
			(expirationDate != null) && expirationDate.before(date)) {

			article = setArticlesExpirationDate(article);
		}

		if (hasModifiedLatestApprovedVersion(
				article.getGroupId(), article.getArticleId(),
				article.getVersion())) {

			if (status == WorkflowConstants.STATUS_APPROVED) {
				updateUrlTitles(
					article.getGroupId(), article.getArticleId(),
					article.getUrlTitle());

				// Asset

				String title = article.getTitleMapAsXML();

				if ((oldStatus != WorkflowConstants.STATUS_APPROVED) &&
					(article.getVersion() !=
						JournalArticleConstants.VERSION_DEFAULT)) {

					AssetEntry draftAssetEntry =
						_assetEntryLocalService.fetchEntry(
							JournalArticle.class.getName(),
							article.getPrimaryKey());

					if (draftAssetEntry != null) {
						long[] assetCategoryIds =
							draftAssetEntry.getCategoryIds();
						String[] assetTagNames = draftAssetEntry.getTagNames();

						List<AssetLink> assetLinks =
							_assetLinkLocalService.getDirectLinks(
								draftAssetEntry.getEntryId(),
								AssetLinkConstants.TYPE_RELATED, false);

						long[] assetLinkEntryIds = ListUtil.toLongArray(
							assetLinks, AssetLink.ENTRY_ID2_ACCESSOR);

						String description = article.getDescriptionMapAsXML();

						AssetEntry assetEntry =
							_assetEntryLocalService.updateEntry(
								userId, article.getGroupId(),
								article.getCreateDate(),
								article.getModifiedDate(),
								JournalArticle.class.getName(),
								article.getResourcePrimKey(), article.getUuid(),
								getClassTypeId(article), assetCategoryIds,
								assetTagNames, isListable(article), false, null,
								null, null, null, ContentTypes.TEXT_HTML, title,
								description, description, null,
								article.getLayoutUuid(), 0, 0,
								draftAssetEntry.getPriority());

						_assetLinkLocalService.updateLinks(
							userId, assetEntry.getEntryId(), assetLinkEntryIds,
							AssetLinkConstants.TYPE_RELATED);

						_assetEntryLocalService.deleteEntry(
							draftAssetEntry.getEntryId());
					}
				}

				_assetEntryLocalService.updateEntry(
					JournalArticle.class.getName(),
					article.getResourcePrimKey(), article.getDisplayDate(),
					article.getExpirationDate(), isListable(article), true);

				expireMaxVersionArticles(
					article, user.getUserId(), serviceContext, articleURL);

				// Social

				JSONObject extraDataJSONObject = JSONUtil.put("title", title);

				if (serviceContext.isCommandUpdate()) {
					SocialActivityManagerUtil.addActivity(
						user.getUserId(), article,
						JournalActivityKeys.UPDATE_ARTICLE,
						extraDataJSONObject.toString(), 0);
				}
				else {
					SocialActivityManagerUtil.addUniqueActivity(
						user.getUserId(), article,
						JournalActivityKeys.ADD_ARTICLE,
						extraDataJSONObject.toString(), 0);
				}
			}
			else if ((oldStatus == WorkflowConstants.STATUS_APPROVED) &&
					 (status != WorkflowConstants.STATUS_IN_TRASH)) {

				updatePreviousApprovedArticle(article);
			}
		}

		String action = "update";

		if ((oldStatus != WorkflowConstants.STATUS_IN_TRASH) &&
			(status != WorkflowConstants.STATUS_IN_TRASH)) {

			// Email

			if ((oldStatus == WorkflowConstants.STATUS_PENDING) &&
				((status == WorkflowConstants.STATUS_APPROVED) ||
				 (status == WorkflowConstants.STATUS_DENIED))) {

				String msg = "granted";

				if (status == WorkflowConstants.STATUS_DENIED) {
					msg = "denied";
				}

				try {
					articleURL = buildArticleURL(
						articleURL, article.getGroupId(), article.getFolderId(),
						article.getArticleId());

					sendEmail(article, articleURL, msg, serviceContext);
				}
				catch (Exception exception) {
					_log.error(
						StringBundler.concat(
							"Unable to send email to notify the change of ",
							"status to ", msg, " for article ", article.getId(),
							": ", exception.getMessage()));
				}
			}

			// Subscriptions

			if (article.equals(
					getOldestArticle(
						article.getGroupId(), article.getArticleId()))) {

				action = "add";
			}
		}

		if ((oldStatus != WorkflowConstants.STATUS_IN_TRASH) &&
			(status == WorkflowConstants.STATUS_IN_TRASH)) {

			action = "move_to_trash";
		}
		else if ((oldStatus == WorkflowConstants.STATUS_IN_TRASH) &&
				 (status != WorkflowConstants.STATUS_IN_TRASH)) {

			action = "move_from_trash";
		}

		Group group = _groupLocalService.getGroup(article.getGroupId());

		if (!group.isStaged() || group.isStagingGroup()) {
			notifySubscribers(
				user.getUserId(), article, action, serviceContext);
		}

		return article;
	}

	/**
	 * Updates the workflow status of the web content article matching the class
	 * PK.
	 *
	 * @param  userId the primary key of the user updating the web content
	 *         article's status
	 * @param  classPK the primary key of the DDM structure, if the web content
	 *         article is related to a DDM structure, the primary key of the
	 *         class associated with the article, or <code>0</code> otherwise
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  workflowContext the web content article's configured workflow
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, portlet preferences, and can set whether to
	 *         add the default command update for the web content article.
	 * @return the updated web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle updateStatus(
			long userId, long classPK, int status,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		return journalArticleLocalService.updateStatus(
			userId, getArticle(classPK), status, null, serviceContext,
			workflowContext);
	}

	/**
	 * Updates the workflow status of the web content article matching the
	 * group, article ID, and version.
	 *
	 * @param  userId the primary key of the user updating the web content
	 *         article's status
	 * @param  groupId the primary key of the web content article's group
	 * @param  articleId the primary key of the web content article
	 * @param  version the web content article's version
	 * @param  status the web content article's workflow status. For more
	 *         information see {@link WorkflowConstants} for constants starting
	 *         with the "STATUS_" prefix.
	 * @param  articleURL the web content article's accessible URL
	 * @param  workflowContext the web content article's configured workflow
	 * @param  serviceContext the service context to be applied. Can set the
	 *         modification date, portlet preferences, and can set whether to
	 *         add the default command update for the web content article.
	 * @return the updated web content article
	 * @throws PortalException if a portal exception occurred
	 */
	@Override
	public JournalArticle updateStatus(
			long userId, long groupId, String articleId, double version,
			int status, String articleURL,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

		return journalArticleLocalService.updateStatus(
			userId, article, status, articleURL, serviceContext,
			workflowContext);
	}

	@Activate
	protected void activate() {
		JournalArticleImpl.setDDMFormValuesToFieldsConverter(
			_ddmFormValuesToFieldsConverter);
		JournalArticleImpl.setJournalConverter(_journalConverter);
	}

	protected void addImageFileEntries(
			JournalArticle article, Element dynamicElementElement)
		throws PortalException {

		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		Map<Long, FileEntry> tempFileEntries = new HashMap<>();

		try {
			for (Element dynamicContentElement :
					dynamicElementElement.elements("dynamic-content")) {

				String value = dynamicContentElement.getText();

				if (Validator.isNull(value)) {
					continue;
				}

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

				if (!jsonObject.has("groupId") || !jsonObject.has("uuid")) {
					continue;
				}

				String uuid = jsonObject.getString("uuid");
				long groupId = jsonObject.getLong("groupId");

				FileEntry fileEntry =
					_dlAppLocalService.getFileEntryByUuidAndGroupId(
						uuid, groupId);

				boolean tempFile = fileEntry.isRepositoryCapabilityProvided(
					TemporaryFileEntriesCapability.class);

				if (tempFile) {
					FileEntry tempFileEntry = fileEntry;

					fileEntry = tempFileEntries.get(
						tempFileEntry.getFileEntryId());

					if (fileEntry == null) {
						Folder folder = article.addImagesFolder();

						String fileEntryName = DLUtil.getUniqueFileName(
							folder.getGroupId(), folder.getFolderId(),
							tempFileEntry.getFileName(), false);

						fileEntry = _portletFileRepository.addPortletFileEntry(
							folder.getGroupId(), tempFileEntry.getUserId(),
							JournalArticle.class.getName(),
							article.getResourcePrimKey(),
							JournalConstants.SERVICE_NAME, folder.getFolderId(),
							tempFileEntry.getContentStream(), fileEntryName,
							tempFileEntry.getMimeType(), false);

						tempFileEntries.put(
							tempFileEntry.getFileEntryId(), fileEntry);
					}
				}

				JSONObject cdataJSONObject = JSONFactoryUtil.createJSONObject(
					dynamicContentElement.getText());

				cdataJSONObject.put(
					"fileEntryId", fileEntry.getFileEntryId()
				).put(
					"resourcePrimKey", article.getResourcePrimKey()
				).put(
					"uuid", fileEntry.getUuid()
				);

				dynamicContentElement.clearContent();

				dynamicContentElement.addCDATA(cdataJSONObject.toString());
			}
		}
		finally {
			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					for (Long tempFileEntryId : tempFileEntries.keySet()) {
						TempFileEntryUtil.deleteTempFileEntry(tempFileEntryId);
					}

					return null;
				});
		}
	}

	protected String buildArticleURL(
		String articleURL, long groupId, long folderId, String articleId) {

		String namespace = _portal.getPortletNamespace(
			PortletProviderUtil.getPortletId(
				JournalArticle.class.getName(), PortletProvider.Action.EDIT));

		articleURL = HttpComponentsUtil.addParameter(
			articleURL, namespace + "groupId", groupId);
		articleURL = HttpComponentsUtil.addParameter(
			articleURL, namespace + "folderId", folderId);
		articleURL = HttpComponentsUtil.addParameter(
			articleURL, namespace + "articleId", articleId);

		return articleURL;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String articleId, String title, String description, String content,
		int status, String ddmStructureKey, String ddmTemplateKey,
		LinkedHashMap<String, Object> params, boolean andSearch, int start,
		int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(andSearch);

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.ARTICLE_ID, articleId
			).put(
				Field.CLASS_NAME_ID, classNameId
			).put(
				Field.CONTENT, content
			).put(
				Field.DESCRIPTION, description
			).put(
				Field.STATUS, status
			).put(
				Field.TITLE, title
			).put(
				"ddmStructureKey", ddmStructureKey
			).put(
				"ddmTemplateKey", ddmTemplateKey
			).put(
				"params", params
			).build());

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setFolderIds(folderIds);
		searchContext.setGroupIds(new long[] {groupId});

		if (params != null) {
			searchContext.setIncludeDiscussions(
				GetterUtil.getBoolean(params.get("includeDiscussions")));

			String keywords = (String)params.remove("keywords");

			if (Validator.isNotNull(keywords)) {
				searchContext.setKeywords(keywords);
			}
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		return searchContext;
	}

	protected SearchContext buildSearchContext(
			long groupId, long userId, long creatorUserId, int status,
			int start, int end)
		throws PortalException {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(Field.STATUS, status);

		searchContext.setAttribute("paginationType", "none");

		if (creatorUserId > 0) {
			searchContext.setAttribute(
				Field.USER_ID, String.valueOf(creatorUserId));
		}

		Group group = _groupLocalService.getGroup(groupId);

		searchContext.setCompanyId(group.getCompanyId());

		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setSorts(new Sort(Field.MODIFIED_DATE, true));
		searchContext.setStart(start);
		searchContext.setUserId(userId);

		return searchContext;
	}

	protected void checkArticlesByCompanyIdAndExpirationDate(
			long companyId, Date expirationDate, Date nextExpirationDate)
		throws PortalException {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			getIndexableActionableDynamicQuery();

		Indexer<JournalArticle> indexer = IndexerRegistryUtil.getIndexer(
			JournalArticle.class);

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				dynamicQuery.add(
					classNameIdProperty.eq(
						JournalArticleConstants.CLASS_NAME_ID_DEFAULT));

				Property expirationDateProperty = PropertyFactoryUtil.forName(
					"expirationDate");

				dynamicQuery.add(expirationDateProperty.le(nextExpirationDate));

				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.eq(WorkflowConstants.STATUS_APPROVED));
			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(JournalArticle article) -> {
				if (_log.isDebugEnabled()) {
					_log.debug("Expiring article " + article.getId());
				}

				if (isExpireAllArticleVersions(companyId)) {
					List<JournalArticle> currentArticles =
						journalArticleLocalService.getArticles(
							article.getGroupId(), article.getArticleId(),
							QueryUtil.ALL_POS, QueryUtil.ALL_POS,
							new ArticleVersionComparator(true));

					for (JournalArticle currentArticle : currentArticles) {
						if (currentArticle.getVersion() >=
								article.getVersion()) {

							continue;
						}

						currentArticle.setExpirationDate(
							article.getExpirationDate());
						currentArticle.setStatus(
							WorkflowConstants.STATUS_EXPIRED);

						journalArticlePersistence.update(currentArticle);
					}
				}

				article.setStatus(WorkflowConstants.STATUS_EXPIRED);

				article = journalArticleLocalService.updateJournalArticle(
					article);

				updatePreviousApprovedArticle(article);

				if (indexer != null) {
					indexableActionableDynamicQuery.addDocuments(
						indexer.getDocument(article));
				}
			});

		if (indexer != null) {
			indexableActionableDynamicQuery.setSearchEngineId(
				indexer.getSearchEngineId());
		}

		indexableActionableDynamicQuery.setTransactionConfig(
			DefaultActionableDynamicQuery.REQUIRES_NEW_TRANSACTION_CONFIG);

		indexableActionableDynamicQuery.performActions();
	}

	protected void checkArticlesByDisplayDate(Date displayDate)
		throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Publishing articles with display date less than ",
					displayDate, " and status ",
					WorkflowConstants.STATUS_SCHEDULED));
		}

		ActionableDynamicQuery actionableDynamicQuery =
			getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property displayDateProperty = PropertyFactoryUtil.forName(
					"displayDate");

				dynamicQuery.add(displayDateProperty.lt(displayDate));

				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.eq(WorkflowConstants.STATUS_SCHEDULED));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(JournalArticle article) -> {
				if (_log.isDebugEnabled()) {
					_log.debug("Publishing article " + article.getId());
				}

				long userId = _portal.getValidUserId(
					article.getCompanyId(), article.getUserId());

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setCommand(Constants.UPDATE);
				serviceContext.setScopeGroupId(article.getGroupId());

				journalArticleLocalService.updateStatus(
					userId, article, WorkflowConstants.STATUS_APPROVED, null,
					serviceContext, new HashMap<>());
			});
		actionableDynamicQuery.setTransactionConfig(
			DefaultActionableDynamicQuery.REQUIRES_NEW_TRANSACTION_CONFIG);

		actionableDynamicQuery.performActions();
	}

	protected void checkArticlesByExpirationDate(Date expirationDate)
		throws PortalException {

		long checkInterval = getArticleCheckInterval();

		Date nextExpirationDate = new Date(
			expirationDate.getTime() + checkInterval);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Expiring articles with expiration date less than or ",
					"equal to ", nextExpirationDate, " and status ",
					WorkflowConstants.STATUS_APPROVED));
		}

		_companyLocalService.forEachCompanyId(
			companyId -> checkArticlesByCompanyIdAndExpirationDate(
				companyId, expirationDate, nextExpirationDate));

		if (_previousCheckDate == null) {
			_previousCheckDate = new Date(
				expirationDate.getTime() - getArticleCheckInterval());
		}
	}

	protected void checkArticlesByReviewDate(Date reviewDate)
		throws PortalException {

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Sending review notification for articles with reviewDate ",
					"between ", _previousCheckDate, " and ", reviewDate));
		}

		List<JournalArticle> articles = journalArticleFinder.findByReviewDate(
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, reviewDate,
			_previousCheckDate);

		for (JournalArticle article : articles) {
			if (article.isInTrash() ||
				!journalArticleLocalService.isLatestVersion(
					article.getGroupId(), article.getArticleId(),
					article.getVersion())) {

				continue;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Sending review notification for article " +
						article.getId());
			}

			String portletId = PortletProviderUtil.getPortletId(
				JournalArticle.class.getName(), PortletProvider.Action.EDIT);

			String articleURL = _portal.getControlPanelFullURL(
				article.getGroupId(), portletId, null);

			articleURL = HttpComponentsUtil.addParameter(
				articleURL, _portal.getPortletNamespace(portletId) + "mvcPath",
				"/edit_article.jsp");

			articleURL = buildArticleURL(
				articleURL, article.getGroupId(), article.getFolderId(),
				article.getArticleId());

			sendEmail(article, articleURL, "review", new ServiceContext());
		}
	}

	protected void checkStructure(Document contentDocument, DDMForm ddmForm)
		throws PortalException {

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			checkStructureField(ddmFormField, contentDocument.getRootElement());
		}
	}

	protected void checkStructure(JournalArticle article)
		throws PortalException {

		checkStructure(article, article.getDDMStructure());
	}

	protected void checkStructure(
			JournalArticle article, DDMStructure ddmStructure)
		throws PortalException {

		checkStructure(article.getDocument(), ddmStructure.getDDMForm());
	}

	protected void checkStructureField(
			DDMFormField ddmFormField, Element contentElement)
		throws PortalException {

		String fieldName = ddmFormField.getName();

		boolean hasField = false;

		for (Element childElement : contentElement.elements()) {
			if (fieldName.equals(
					childElement.attributeValue("name", StringPool.BLANK))) {

				hasField = true;

				for (DDMFormField childDDMFormField :
						ddmFormField.getNestedDDMFormFields()) {

					checkStructureField(childDDMFormField, childElement);
				}

				break;
			}
		}

		if (!hasField) {
			String contentElementType = contentElement.attributeValue(
				"type", StringPool.BLANK);

			if (!contentElementType.equals("list") &&
				!contentElementType.equals("multi-list")) {

				throw new StructureDefinitionException(fieldName);
			}
		}
	}

	protected String copyArticleImages(
		JournalArticle oldArticle, JournalArticle newArticle) {

		try {
			Folder folder = newArticle.addImagesFolder();

			for (FileEntry fileEntry : oldArticle.getImagesFileEntries()) {
				_portletFileRepository.addPortletFileEntry(
					oldArticle.getGroupId(), newArticle.getUserId(),
					JournalArticle.class.getName(),
					newArticle.getResourcePrimKey(),
					JournalConstants.SERVICE_NAME, folder.getFolderId(),
					fileEntry.getContentStream(), fileEntry.getFileName(),
					fileEntry.getMimeType(), false);
			}

			Document contentDocument = oldArticle.getDocument();

			contentDocument = contentDocument.clone();

			XPath xPathSelector = SAXReaderUtil.createXPath(
				"//dynamic-element[@type='image']");

			List<Node> imageNodes = xPathSelector.selectNodes(contentDocument);

			for (Node imageNode : imageNodes) {
				Element imageEl = (Element)imageNode;

				List<Element> dynamicContentEls = imageEl.elements(
					"dynamic-content");

				for (Element dynamicContentEl : dynamicContentEls) {
					String fileName = dynamicContentEl.attributeValue("name");

					FileEntry fileEntry =
						_portletFileRepository.getPortletFileEntry(
							newArticle.getGroupId(), folder.getFolderId(),
							fileName);

					String previewURL = _dlURLHelper.getPreviewURL(
						fileEntry, fileEntry.getFileVersion(), null,
						StringPool.BLANK, false, true);

					dynamicContentEl.addAttribute(
						"resourcePrimKey",
						String.valueOf(newArticle.getResourcePrimKey()));

					dynamicContentEl.clearContent();

					dynamicContentEl.addCDATA(previewURL);
				}
			}

			return contentDocument.formattedString();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return oldArticle.getContent();
	}

	protected Map<String, LocalizedValue> createFieldsValuesMap(
		Element parentElement, Locale defaultLocale) {

		Map<String, LocalizedValue> fieldsValuesMap = new HashMap<>();

		List<Element> dynamicElementElements = parentElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			String fieldName = dynamicElementElement.attributeValue(
				"name", StringPool.BLANK);
			LocalizedValue fieldLocalizedValue = new LocalizedValue(
				defaultLocale);

			List<Element> dynamicContentElements =
				dynamicElementElement.elements("dynamic-content");

			for (Element dynamicContentElement : dynamicContentElements) {
				String languageId = dynamicContentElement.attributeValue(
					"language-id");
				String value = dynamicContentElement.getText();

				fieldLocalizedValue.addString(
					LocaleUtil.fromLanguageId(languageId), value);
			}

			fieldsValuesMap.put(fieldName, fieldLocalizedValue);

			fieldsValuesMap.putAll(
				createFieldsValuesMap(dynamicElementElement, defaultLocale));
		}

		return fieldsValuesMap;
	}

	protected Map<String, LocalizedValue> createFieldsValuesMap(
		String content) {

		try {
			Document document = SAXReaderUtil.read(content);

			Element rootElement = document.getRootElement();

			Locale defaultLocale = LocaleUtil.fromLanguageId(
				rootElement.attributeValue("default-locale"));

			return createFieldsValuesMap(rootElement, defaultLocale);
		}
		catch (DocumentException documentException) {
			throw new SystemException(documentException);
		}
	}

	@Deactivate
	@Override
	protected void deactivate() {
		super.deactivate();

		JournalArticleImpl.setDDMFormValuesToFieldsConverter(null);
		JournalArticleImpl.setJournalConverter(null);
	}

	protected void expireMaxVersionArticles(
			JournalArticle article, long userId, ServiceContext serviceContext,
			String articleURL)
		throws PortalException {

		int journalArticleMaxVersionCount = getJournalArticleMaxVersionCount();

		if (journalArticleMaxVersionCount <= 0) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Expiring oldest articles past the maximum version limit");
		}

		List<JournalArticle> articles = journalArticlePersistence.findByG_A_ST(
			article.getGroupId(), article.getArticleId(),
			WorkflowConstants.STATUS_APPROVED);

		for (int i = journalArticleMaxVersionCount; i < articles.size(); i++) {
			JournalArticle curArticle = articles.get(i);

			expireArticle(
				userId, curArticle.getGroupId(), curArticle.getArticleId(),
				curArticle.getVersion(), articleURL, serviceContext);
		}
	}

	protected JournalArticle fetchLatestLiveArticle(JournalArticle article)
		throws PortalException {

		Group group = _groupLocalService.getGroup(article.getGroupId());

		long liveGroupId = group.getLiveGroupId();

		if (liveGroupId == 0) {
			return null;
		}

		JournalArticleResource articleResource =
			_journalArticleResourceLocalService.
				fetchJournalArticleResourceByUuidAndGroupId(
					article.getArticleResourceUuid(), liveGroupId);

		if (articleResource == null) {
			return null;
		}

		return journalArticleLocalService.fetchLatestArticle(
			articleResource.getResourcePrimKey(), WorkflowConstants.STATUS_ANY,
			false);
	}

	protected void format(
			User user, long groupId, JournalArticle article, Element root)
		throws PortalException {

		for (Element element : root.elements()) {
			String elType = element.attributeValue("type", StringPool.BLANK);

			if (elType.equals(DDMFormFieldTypeConstants.IMAGE)) {
				addImageFileEntries(article, element);
			}
			else if (elType.equals(DDMFormFieldTypeConstants.RICH_TEXT) ||
					 elType.equals(DDMFormFieldTypeConstants.TEXT)) {

				List<Element> dynamicContentElements = element.elements(
					"dynamic-content");

				for (Element dynamicContentElement : dynamicContentElements) {
					String dynamicContent = dynamicContentElement.getText();

					if (Validator.isNotNull(dynamicContent)) {
						String contentType = ContentTypes.TEXT_PLAIN;

						if (elType.equals(
								DDMFormFieldTypeConstants.RICH_TEXT)) {

							contentType = ContentTypes.TEXT_HTML;
						}

						dynamicContent = SanitizerUtil.sanitize(
							user.getCompanyId(), groupId, user.getUserId(),
							JournalArticle.class.getName(), 0, contentType,
							dynamicContent);

						dynamicContentElement.clearContent();

						dynamicContentElement.addCDATA(dynamicContent);
					}
				}
			}

			format(user, groupId, article, element);
		}
	}

	protected String format(
			User user, long groupId, JournalArticle article, String content)
		throws PortalException {

		Document document = null;

		try {
			document = SAXReaderUtil.read(content);

			format(user, groupId, article, document.getRootElement());

			content = XMLUtil.formatXML(document);
		}
		catch (DocumentException documentException) {
			_log.error(documentException);
		}

		return content;
	}

	protected long getArticleCheckInterval() {
		try {
			JournalServiceConfiguration journalServiceConfiguration =
				configurationProvider.getCompanyConfiguration(
					JournalServiceConfiguration.class,
					CompanyThreadLocal.getCompanyId());

			return journalServiceConfiguration.checkInterval() * Time.MINUTE;
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	protected Locale getArticleDefaultLocale(String content) {
		String defaultLanguageId = LocalizationUtil.getDefaultLanguageId(
			content);

		if (Validator.isNotNull(defaultLanguageId)) {
			return LocaleUtil.fromLanguageId(defaultLanguageId);
		}

		return LocaleUtil.getSiteDefault();
	}

	protected JournalArticleDisplay getArticleDisplay(
			JournalArticle article, String ddmTemplateKey, String viewMode,
			String languageId, int page,
			PortletRequestModel portletRequestModel, ThemeDisplay themeDisplay,
			boolean propagateException)
		throws PortalException {

		String content = null;

		if (page < 1) {
			page = 1;
		}

		int numberOfPages = 1;
		boolean paginate = false;
		boolean pageFlow = false;

		boolean cacheable = true;

		Document document = article.getDocument();

		document = document.clone();

		Element rootElement = document.getRootElement();

		List<Element> pages = rootElement.elements("page");

		if (!pages.isEmpty()) {
			pageFlow = true;

			String targetPage = null;

			Map<String, String[]> parameters =
				portletRequestModel.getParameters();

			if (parameters != null) {
				String[] values = parameters.get("targetPage");

				if ((values != null) && (values.length > 0)) {
					targetPage = values[0];
				}
			}

			Element pageElement = null;

			if (Validator.isNotNull(targetPage)) {
				targetPage = HtmlUtil.escapeXPathAttribute(targetPage);

				XPath xPathSelector = SAXReaderUtil.createXPath(
					"/root/page[@id = " + targetPage + "]");

				pageElement = (Element)xPathSelector.selectSingleNode(document);
			}

			if (pageElement != null) {
				document = SAXReaderUtil.createDocument(pageElement);

				rootElement = document.getRootElement();

				numberOfPages = pages.size();
			}
			else {
				if (page > pages.size()) {
					page = 1;
				}

				pageElement = pages.get(page - 1);

				document = SAXReaderUtil.createDocument(pageElement);

				rootElement = document.getRootElement();

				numberOfPages = pages.size();
				paginate = true;
			}
		}

		try {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Transforming ", article.getArticleId(),
						StringPool.SPACE, article.getVersion(),
						StringPool.SPACE, languageId));
			}

			// Try with specified template first (in the current group and the
			// global group). If a template is not specified, use the default
			// one. If the specified template does not exist, use the default
			// one. If the default one does not exist, throw an exception.

			DDMTemplate ddmTemplate = ddmTemplateLocalService.fetchTemplate(
				_portal.getSiteGroupId(article.getGroupId()),
				_classNameLocalService.getClassNameId(DDMStructure.class),
				ddmTemplateKey, true);

			if ((ddmTemplate == null) &&
				!Objects.equals(article.getDDMTemplateKey(), ddmTemplateKey)) {

				ddmTemplate = ddmTemplateLocalService.fetchTemplate(
					_portal.getSiteGroupId(article.getGroupId()),
					_classNameLocalService.getClassNameId(DDMStructure.class),
					article.getDDMTemplateKey(), true);
			}

			Map<String, String> tokens = JournalUtil.getTokens(
				article, ddmTemplate, portletRequestModel, themeDisplay);

			JournalUtil.addAllReservedEls(
				rootElement, tokens, article, languageId, themeDisplay);

			String script = StringPool.BLANK;

			if (ddmTemplate != null) {
				script = ddmTemplate.getScript();
				cacheable = ddmTemplate.isCacheable();
			}
			else {
				DDMStructure ddmStructure = article.getDDMStructure();

				script = _journalDefaultTemplateProvider.getScript(
					ddmStructure.getStructureId());

				cacheable = _journalDefaultTemplateProvider.isCacheable();
			}

			content = JournalUtil.transform(
				themeDisplay, tokens, viewMode, languageId, document,
				portletRequestModel, script, propagateException,
				HashMapBuilder.<String, Object>put(
					"friendlyURLs", _getFriendlyURLMap(article, themeDisplay)
				).build());

			if (!pageFlow) {
				JournalServiceConfiguration journalServiceConfiguration =
					configurationProvider.getCompanyConfiguration(
						JournalServiceConfiguration.class,
						article.getCompanyId());

				String[] pieces = StringUtil.split(
					content,
					journalServiceConfiguration.journalArticlePageBreakToken());

				if (pieces.length > 1) {
					if (page > pieces.length) {
						page = 1;
					}

					content = pieces[page - 1];
					numberOfPages = pieces.length;
					paginate = true;
				}
			}
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}

		return new JournalArticleDisplayImpl(
			article.getCompanyId(), article.getId(),
			article.getResourcePrimKey(), article.getGroupId(),
			article.getUserId(), article.getArticleId(), article.getVersion(),
			article.getTitle(languageId), article.getUrlTitle(),
			article.getDescription(languageId),
			article.getAvailableLanguageIds(), content,
			article.getDDMStructureKey(), ddmTemplateKey,
			article.isSmallImage(), article.getSmallImageId(),
			article.getSmallImageURL(), numberOfPages, page, paginate,
			cacheable);
	}

	protected List<ObjectValuePair<Long, Integer>> getArticleVersionStatuses(
		List<JournalArticle> articles) {

		List<ObjectValuePair<Long, Integer>> articleVersionStatusOVPs =
			new ArrayList<>(articles.size());

		for (JournalArticle article : articles) {
			int status = article.getStatus();

			if (status == WorkflowConstants.STATUS_PENDING) {
				status = WorkflowConstants.STATUS_DRAFT;
			}

			ObjectValuePair<Long, Integer> articleVersionStatusOVP =
				new ObjectValuePair<>(article.getId(), status);

			articleVersionStatusOVPs.add(articleVersionStatusOVP);
		}

		return articleVersionStatusOVPs;
	}

	protected long getClassTypeId(JournalArticle article) {
		DDMStructure ddmStructure = ddmStructureLocalService.fetchStructure(
			_portal.getSiteGroupId(article.getGroupId()),
			_classNameLocalService.getClassNameId(JournalArticle.class),
			article.getDDMStructureKey(), true);

		return ddmStructure.getStructureId();
	}

	protected JournalArticle getFirstArticle(
			long groupId, String articleId, int status,
			OrderByComparator<JournalArticle> orderByComparator)
		throws PortalException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return journalArticlePersistence.findByG_A_NotST_First(
				groupId, articleId, WorkflowConstants.STATUS_IN_TRASH,
				orderByComparator);
		}

		return journalArticlePersistence.findByG_A_ST_First(
			groupId, articleId, status, orderByComparator);
	}

	protected int getJournalArticleMaxVersionCount() throws PortalException {
		JournalServiceConfiguration journalServiceConfiguration =
			configurationProvider.getCompanyConfiguration(
				JournalServiceConfiguration.class,
				CompanyThreadLocal.getCompanyId());

		return journalServiceConfiguration.journalArticleMaxVersionCount();
	}

	protected JournalGroupServiceConfiguration
			getJournalGroupServiceConfiguration(long groupId)
		throws ConfigurationException {

		return configurationProvider.getConfiguration(
			JournalGroupServiceConfiguration.class,
			new GroupServiceSettingsLocator(
				groupId, JournalConstants.SERVICE_NAME));
	}

	protected double getNextVersion(JournalArticle article)
		throws PortalException {

		double nextVersion = article.getVersion();

		// The next version must be greater than the version of the latest live
		// article

		JournalArticle latestLiveArticle = fetchLatestLiveArticle(article);

		if ((latestLiveArticle != null) &&
			(latestLiveArticle.getVersion() > nextVersion)) {

			nextVersion = latestLiveArticle.getVersion();
		}

		return MathUtil.format(nextVersion + 0.1, 1, 1);
	}

	protected String getUniqueUrlTitle(
			long id, long groupId, String articleId, String title)
		throws PortalException {

		return getUniqueUrlTitle(
			groupId, articleId, JournalUtil.getUrlTitle(id, title));
	}

	protected String getUniqueUrlTitle(
			long id, long groupId, String articleId, String title,
			String oldUrlTitle, ServiceContext serviceContext)
		throws PortalException {

		String serviceContextUrlTitle = ParamUtil.getString(
			serviceContext, "urlTitle");

		String urlTitle = null;

		if (Validator.isNotNull(serviceContextUrlTitle)) {
			urlTitle = JournalUtil.getUrlTitle(id, serviceContextUrlTitle);
		}
		else if (Validator.isNotNull(oldUrlTitle)) {
			return oldUrlTitle;
		}
		else {
			urlTitle = getUniqueUrlTitle(id, groupId, articleId, title);
		}

		JournalArticle urlTitleArticle = fetchArticleByUrlTitle(
			groupId, urlTitle);

		if ((urlTitleArticle != null) &&
			!Objects.equals(urlTitleArticle.getArticleId(), articleId)) {

			urlTitle = getUniqueUrlTitle(id, groupId, articleId, urlTitle);
		}

		return urlTitle;
	}

	protected String getURLViewInContext(
		JournalArticle article, String portletId,
		ServiceContext serviceContext) {

		String defaultArticleURL = StringPool.BLANK;

		try {
			defaultArticleURL = _portal.getControlPanelFullURL(
				article.getGroupId(), portletId, null);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		LiferayPortletRequest liferayPortletRequest =
			serviceContext.getLiferayPortletRequest();

		if (liferayPortletRequest == null) {
			return defaultArticleURL;
		}

		try {
			AssetRendererFactory<JournalArticle> assetRendererFactory =
				AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
					JournalArticle.class);

			AssetRenderer<JournalArticle> assetRenderer =
				assetRendererFactory.getAssetRenderer(
					article, AssetRendererFactory.TYPE_LATEST_APPROVED);

			return assetRenderer.getURLViewInContext(
				liferayPortletRequest, null, defaultArticleURL);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return StringPool.BLANK;
	}

	protected boolean hasModifiedLatestApprovedVersion(
		long groupId, String articleId, double version) {

		JournalArticle article = fetchLatestArticle(
			groupId, articleId, WorkflowConstants.STATUS_APPROVED);

		if ((article == null) || (article.getVersion() <= version)) {
			return true;
		}

		return false;
	}

	protected boolean isArticleCommentsEnabled(long companyId)
		throws PortalException {

		JournalServiceConfiguration journalServiceConfiguration =
			configurationProvider.getCompanyConfiguration(
				JournalServiceConfiguration.class, companyId);

		return journalServiceConfiguration.articleCommentsEnabled();
	}

	protected boolean isExpireAllArticleVersions(long companyId)
		throws PortalException {

		JournalServiceConfiguration journalServiceConfiguration =
			configurationProvider.getCompanyConfiguration(
				JournalServiceConfiguration.class, companyId);

		return journalServiceConfiguration.expireAllArticleVersionsEnabled();
	}

	protected boolean isReindexAllArticleVersions() {
		try {
			JournalServiceConfiguration journalServiceConfiguration =
				configurationProvider.getCompanyConfiguration(
					JournalServiceConfiguration.class,
					CompanyThreadLocal.getCompanyId());

			return journalServiceConfiguration.indexAllArticleVersionsEnabled();
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		return false;
	}

	protected void notifySubscribers(
			long userId, JournalArticle article, String action,
			ServiceContext serviceContext)
		throws PortalException {

		JournalGroupServiceConfiguration journalGroupServiceConfiguration =
			getJournalGroupServiceConfiguration(article.getGroupId());

		if (action.equals("add") &&
			journalGroupServiceConfiguration.emailArticleAddedEnabled()) {
		}
		else if (action.equals("move_to") &&
				 journalGroupServiceConfiguration.
					 emailArticleMovedToFolderEnabled()) {
		}
		else if (action.equals("move_to_trash") &&
				 journalGroupServiceConfiguration.
					 emailArticleMovedToTrashEnabled()) {
		}
		else if (action.equals("move_from") &&
				 journalGroupServiceConfiguration.
					 emailArticleMovedFromFolderEnabled()) {
		}
		else if (action.equals("move_from_trash") &&
				 journalGroupServiceConfiguration.
					 emailArticleMovedToTrashEnabled()) {
		}
		else if (action.equals("update") &&
				 journalGroupServiceConfiguration.
					 emailArticleUpdatedEnabled()) {
		}
		else {
			return;
		}

		SubscriptionSender subscriptionSender =
			new GroupSubscriptionCheckSubscriptionSender(
				JournalConstants.RESOURCE_NAME);

		subscriptionSender.setClassName(article.getModelClassName());
		subscriptionSender.setClassPK(article.getId());
		subscriptionSender.setCompanyId(article.getCompanyId());

		JournalFolder folder = _journalFolderPersistence.fetchByPrimaryKey(
			article.getFolderId());

		subscriptionSender.addPersistedSubscribers(
			JournalFolder.class.getName(), article.getGroupId());

		Group group = _groupLocalService.getGroup(article.getGroupId());

		long liveGroupId = group.getLiveGroupId();

		subscriptionSender.addAssetEntryPersistedSubscribers(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		if (liveGroupId > 0) {
			subscriptionSender.addPersistedSubscribers(
				JournalFolder.class.getName(), liveGroupId, false);
		}

		if (folder != null) {
			subscriptionSender.addPersistedSubscribers(
				JournalFolder.class.getName(), folder.getFolderId());

			for (Long ancestorFolderId : folder.getAncestorFolderIds()) {
				subscriptionSender.addPersistedSubscribers(
					JournalFolder.class.getName(), ancestorFolderId);
			}

			if (liveGroupId > 0) {
				folder = _journalFolderPersistence.fetchByUUID_G(
					folder.getUuid(), liveGroupId);

				if (folder != null) {
					subscriptionSender.addPersistedSubscribers(
						JournalFolder.class.getName(), folder.getFolderId(),
						false);

					for (Long ancestorFolderId :
							folder.getAncestorFolderIds()) {

						subscriptionSender.addPersistedSubscribers(
							JournalFolder.class.getName(), ancestorFolderId,
							false);
					}
				}
			}
		}

		DDMStructure ddmStructure = ddmStructureLocalService.fetchStructure(
			_portal.getSiteGroupId(article.getGroupId()),
			_classNameLocalService.getClassNameId(JournalArticle.class),
			article.getDDMStructureKey(), true);

		if (ddmStructure != null) {
			subscriptionSender.addPersistedSubscribers(
				DDMStructure.class.getName(), ddmStructure.getStructureId());
		}

		subscriptionSender.addPersistedSubscribers(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		if (liveGroupId > 0) {
			JournalArticle liveGroupArticle =
				journalArticlePersistence.fetchByUUID_G(
					article.getUuid(), liveGroupId);

			if (liveGroupArticle != null) {
				subscriptionSender.addPersistedSubscribers(
					JournalArticle.class.getName(),
					liveGroupArticle.getResourcePrimKey(), false);
			}
		}

		if (!subscriptionSender.hasSubscribers()) {
			return;
		}

		String fromAddress =
			journalGroupServiceConfiguration.emailFromAddress();

		_populateSubscriptionSender(
			article,
			JournalUtil.getJournalControlPanelLink(
				article.getFolderId(), article.getGroupId(),
				serviceContext.getLiferayPortletResponse()),
			action, fromAddress,
			journalGroupServiceConfiguration.emailFromName(),
			journalGroupServiceConfiguration, serviceContext,
			subscriptionSender);

		String articleContent = StringPool.BLANK;

		try {
			PortletRequestModel portletRequestModel = null;

			if (!ExportImportThreadLocal.isImportInProcess()) {
				portletRequestModel = new PortletRequestModel(
					serviceContext.getLiferayPortletRequest(),
					serviceContext.getLiferayPortletResponse());
			}

			JournalArticleDisplay articleDisplay = getArticleDisplay(
				article, article.getDDMTemplateKey(), Constants.VIEW,
				LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()), 1,
				portletRequestModel, serviceContext.getThemeDisplay());

			articleContent = articleDisplay.getContent();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		subscriptionSender.setContextAttribute(
			"[$ARTICLE_CONTENT$]", articleContent, false);

		String folderName = StringPool.BLANK;

		if (folder != null) {
			folderName = folder.getName();
		}
		else if (article.getFolderId() ==
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			folderName = LanguageUtil.get(LocaleUtil.getSiteDefault(), "home");
		}

		subscriptionSender.setContextAttributes(
			"[$FOLDER_NAME$]", folderName, "[$ARTICLE_STATUS$]",
			LanguageUtil.get(
				LocaleUtil.getSiteDefault(),
				WorkflowConstants.getStatusLabel(article.getStatus())));
		subscriptionSender.setCurrentUserId(userId);
		subscriptionSender.setEntryTitle(
			article.getTitle(serviceContext.getLanguageId()));
		subscriptionSender.setNotificationType(_getNotificationType(action));
		subscriptionSender.setReplyToAddress(fromAddress);

		subscriptionSender.flushNotificationsAsync();
	}

	protected void sanitize(
			long companyId, long groupId, long userId, long classPK,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		if (descriptionMap == null) {
			return;
		}

		for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
			String description = SanitizerUtil.sanitize(
				companyId, groupId, userId, JournalArticle.class.getName(),
				classPK, ContentTypes.TEXT_HTML, entry.getValue());

			descriptionMap.put(entry.getKey(), description);
		}
	}

	protected void saveImages(
			long companyId, boolean smallImage, long smallImageId,
			File smallImageFile, byte[] smallImageBytes)
		throws PortalException {

		if (smallImage) {
			if ((smallImageFile != null) && (smallImageBytes != null)) {
				_imageLocalService.updateImage(
					companyId, smallImageId, smallImageBytes);
			}
		}
		else {
			_imageLocalService.deleteImage(smallImageId);
		}
	}

	protected BaseModelSearchResult<JournalArticle> searchJournalArticles(
			SearchContext searchContext)
		throws PortalException {

		Indexer<JournalArticle> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(JournalArticle.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(
				searchContext, JournalUtil.SELECTED_FIELD_NAMES);

			List<JournalArticle> articles = _journalHelper.getArticles(hits);

			if (articles != null) {
				return new BaseModelSearchResult<>(articles, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected void sendEmail(
			JournalArticle article, String articleURL, String emailType,
			ServiceContext serviceContext)
		throws PortalException {

		JournalGroupServiceConfiguration journalGroupServiceConfiguration =
			getJournalGroupServiceConfiguration(article.getGroupId());

		if (emailType.equals("denied") &&
			journalGroupServiceConfiguration.
				emailArticleApprovalDeniedEnabled()) {
		}
		else if (emailType.equals("granted") &&
				 journalGroupServiceConfiguration.
					 emailArticleApprovalGrantedEnabled()) {
		}
		else if (emailType.equals("requested") &&
				 journalGroupServiceConfiguration.
					 emailArticleApprovalRequestedEnabled()) {
		}
		else if (emailType.equals("review") &&
				 journalGroupServiceConfiguration.emailArticleReviewEnabled()) {
		}
		else {
			return;
		}

		User user = _userLocalService.fetchUser(article.getUserId());

		if (user == null) {
			return;
		}

		String fromName = journalGroupServiceConfiguration.emailFromName();
		String fromAddress =
			journalGroupServiceConfiguration.emailFromAddress();

		String toName = user.getFullName();
		String toAddress = user.getEmailAddress();

		if (emailType.equals("requested")) {
			String tempToName = fromName;
			String tempToAddress = fromAddress;

			fromName = toName;
			fromAddress = toAddress;

			toName = tempToName;
			toAddress = tempToAddress;
		}

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		_populateSubscriptionSender(
			article, articleURL, emailType, fromAddress, fromName,
			journalGroupServiceConfiguration, serviceContext,
			subscriptionSender);

		subscriptionSender.setClassPK(article.getPrimaryKey());
		subscriptionSender.setContextAttribute(
			"[$ARTICLE_USER_NAME$]", article.getUserName());
		subscriptionSender.setEntryTitle(article.getTitle(user.getLocale()));

		subscriptionSender.addRuntimeSubscribers(toAddress, toName);

		subscriptionSender.flushNotificationsAsync();
	}

	protected JournalArticle setArticlesExpirationDate(JournalArticle article) {
		if (ExportImportThreadLocal.isImportInProcess() ||
			!article.isApproved() || (article.getExpirationDate() == null)) {

			return article;
		}

		List<JournalArticle> articles = journalArticlePersistence.findByG_A(
			article.getGroupId(), article.getArticleId());

		Date expirationDate = article.getExpirationDate();

		for (JournalArticle curArticle : articles) {
			curArticle.setExpirationDate(expirationDate);

			curArticle = journalArticleLocalService.updateJournalArticle(
				curArticle);

			if (article.equals(curArticle)) {
				article = curArticle;
			}
		}

		return article;
	}

	protected void startWorkflowInstance(
			long userId, JournalArticle article, ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			article.getCompanyId(), article.getGroupId(), userId,
			JournalArticle.class.getName(), article.getId(), article,
			serviceContext, workflowContext);
	}

	protected void updateDDMFields(JournalArticle article, String content)
		throws PortalException {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			_portal.getSiteGroupId(article.getGroupId()),
			_portal.getClassNameId(JournalArticle.class),
			article.getDDMStructureKey(), true);

		DDMFormValues ddmFormValues = _fieldsToDDMFormValuesConverter.convert(
			ddmStructure,
			_journalConverter.getDDMFields(ddmStructure, content));

		_ddmFieldLocalService.updateDDMFormValues(
			ddmStructure.getStructureId(), article.getId(), ddmFormValues);

		// Document Cache

		try {
			article.setDocument(SAXReaderUtil.read(content));

			journalArticlePersistence.cacheResult(article);
		}
		catch (DocumentException documentException) {
			if (_log.isWarnEnabled()) {
				_log.warn(documentException);
			}
		}
	}

	protected void updateDDMFormFieldPredefinedValue(
		DDMFormField ddmFormField, LocalizedValue ddmFormFieldValue) {

		DDMForm ddmForm = ddmFormField.getDDMForm();

		Set<Locale> ddmFormAvailableLocales = ddmForm.getAvailableLocales();

		ddmFormField.setPredefinedValue(ddmFormFieldValue);

		ddmFormAvailableLocales.addAll(ddmFormFieldValue.getAvailableLocales());

		for (Locale locale : ddmFormAvailableLocales) {
			LocalizedValue label = ddmFormField.getLabel();

			Map<Locale, String> labelValues = label.getValues();

			if (!labelValues.containsKey(locale)) {
				label.addString(
					locale, label.getString(label.getDefaultLocale()));
			}

			LocalizedValue style = ddmFormField.getStyle();

			Map<Locale, String> styleValues = style.getValues();

			if (!styleValues.containsKey(locale)) {
				style.addString(
					locale, style.getString(style.getDefaultLocale()));
			}

			LocalizedValue tip = ddmFormField.getTip();

			Map<Locale, String> tipValues = tip.getValues();

			if (!tipValues.containsKey(locale)) {
				tip.addString(locale, tip.getString(tip.getDefaultLocale()));
			}
		}
	}

	protected void updateDDMLinks(
			long id, long groupId, String ddmStructureKey,
			String ddmTemplateKey, boolean incrementVersion)
		throws PortalException {

		DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
			_portal.getSiteGroupId(groupId),
			_classNameLocalService.getClassNameId(JournalArticle.class),
			ddmStructureKey, true);

		DDMTemplate ddmTemplate = ddmTemplateLocalService.fetchTemplate(
			_portal.getSiteGroupId(groupId),
			_classNameLocalService.getClassNameId(DDMStructure.class),
			ddmTemplateKey, true);

		if (incrementVersion) {
			DDMStructureVersion ddmStructureVersion =
				_ddmStructureVersionLocalService.getStructureVersion(
					ddmStructure.getStructureId(), ddmStructure.getVersion());

			ddmStorageLinkLocalService.addStorageLink(
				ddmStructure.getClassNameId(), id,
				ddmStructureVersion.getStructureVersionId(),
				new ServiceContext());

			ddmStructureLinkLocalService.addStructureLink(
				_classNameLocalService.getClassNameId(JournalArticle.class), id,
				ddmStructure.getStructureId());

			if (ddmTemplate != null) {
				ddmTemplateLinkLocalService.addTemplateLink(
					_classNameLocalService.getClassNameId(JournalArticle.class),
					id, ddmTemplate.getTemplateId());
			}
		}
		else {
			DDMStorageLink ddmStorageLink =
				ddmStorageLinkLocalService.getClassStorageLink(id);

			ddmStorageLink.setStructureId(ddmStructure.getStructureId());

			ddmStorageLinkLocalService.updateDDMStorageLink(ddmStorageLink);

			int count = ddmStructureLinkLocalService.getStructureLinksCount(
				_classNameLocalService.getClassNameId(JournalArticle.class),
				id);

			if (count == 0) {
				ddmStructureLinkLocalService.addStructureLink(
					_classNameLocalService.getClassNameId(JournalArticle.class),
					id, ddmStructure.getStructureId());
			}
			else {
				DDMStructureLink ddmStructureLink =
					ddmStructureLinkLocalService.getUniqueStructureLink(
						_classNameLocalService.getClassNameId(
							JournalArticle.class),
						id);

				ddmStructureLink.setStructureId(ddmStructure.getStructureId());

				ddmStructureLinkLocalService.updateDDMStructureLink(
					ddmStructureLink);
			}

			if (ddmTemplate != null) {
				ddmTemplateLinkLocalService.updateTemplateLink(
					_classNameLocalService.getClassNameId(JournalArticle.class),
					id, ddmTemplate.getTemplateId());
			}
		}
	}

	protected void updateFriendlyURLs(
			JournalArticle article, Map<String, String> urlTitleMap,
			ServiceContext serviceContext)
		throws PortalException {

		if (ExportImportThreadLocal.isImportInProcess() ||
			ExportImportThreadLocal.isStagingInProcess()) {

			return;
		}

		List<FriendlyURLEntry> friendlyURLEntries =
			friendlyURLEntryLocalService.getFriendlyURLEntries(
				article.getGroupId(),
				_classNameLocalService.getClassNameId(JournalArticle.class),
				article.getResourcePrimKey());

		FriendlyURLEntry newFriendlyURLEntry =
			friendlyURLEntryLocalService.addFriendlyURLEntry(
				article.getGroupId(),
				_classNameLocalService.getClassNameId(JournalArticle.class),
				article.getResourcePrimKey(), urlTitleMap, serviceContext);

		for (Map.Entry<String, String> entry : urlTitleMap.entrySet()) {
			if (Validator.isNull(entry.getValue())) {
				for (FriendlyURLEntry friendlyURLEntry : friendlyURLEntries) {
					try {
						friendlyURLEntryLocalService.
							deleteFriendlyURLLocalizationEntry(
								friendlyURLEntry.getFriendlyURLEntryId(),
								entry.getKey());
					}
					catch (NoSuchFriendlyURLEntryLocalizationException
								noSuchFriendlyURLEntryLocalizationException) {

						if (_log.isDebugEnabled()) {
							_log.debug(
								noSuchFriendlyURLEntryLocalizationException);
						}
					}
				}
			}
		}

		for (FriendlyURLEntry friendlyURLEntry : friendlyURLEntries) {
			if (newFriendlyURLEntry.getFriendlyURLEntryId() ==
					friendlyURLEntry.getFriendlyURLEntryId()) {

				continue;
			}

			friendlyURLEntryLocalService.deleteFriendlyURLEntry(
				friendlyURLEntry);
		}
	}

	protected void updatePreviousApprovedArticle(JournalArticle article)
		throws PortalException {

		JournalArticle previousApprovedArticle = getPreviousApprovedArticle(
			article);

		if (previousApprovedArticle.getVersion() == article.getVersion()) {
			AssetEntry assetEntry = _assetEntryLocalService.updateVisible(
				JournalArticle.class.getName(), article.getResourcePrimKey(),
				false);

			if (article.getStatus() == WorkflowConstants.STATUS_EXPIRED) {
				assetEntry.setExpirationDate(article.getExpirationDate());

				_assetEntryLocalService.updateAssetEntry(assetEntry);
			}
		}
		else {
			AssetEntry assetEntry = _assetEntryLocalService.updateEntry(
				JournalArticle.class.getName(), article.getResourcePrimKey(),
				previousApprovedArticle.getDisplayDate(),
				previousApprovedArticle.getExpirationDate(),
				isListable(article), true);

			assetEntry.setModifiedDate(
				previousApprovedArticle.getModifiedDate());
			assetEntry.setTitle(previousApprovedArticle.getTitleMapAsXML());

			_assetEntryLocalService.updateAssetEntry(assetEntry);
		}
	}

	protected void updateUrlTitles(
			long groupId, String articleId, String urlTitle)
		throws PortalException {

		JournalArticle firstArticle = journalArticlePersistence.findByG_A_First(
			groupId, articleId, new ArticleVersionComparator(false));

		String firstArticleUrlTitle = firstArticle.getUrlTitle();

		if (firstArticleUrlTitle.equals(urlTitle)) {
			return;
		}

		List<JournalArticle> articles = journalArticlePersistence.findByG_A(
			groupId, articleId);

		for (JournalArticle article : articles) {
			String curArticleUrlTitle = article.getUrlTitle();

			if (!curArticleUrlTitle.equals(urlTitle)) {
				article.setUrlTitle(urlTitle);

				journalArticlePersistence.update(article);
			}
		}
	}

	protected void validate(
			long companyId, long groupId, long classNameId,
			Map<Locale, String> titleMap, String content,
			String ddmStructureKey, String ddmTemplateKey, Date displayDate,
			Date expirationDate, boolean smallImage, String smallImageURL,
			File smallImageFile, byte[] smallImageBytes,
			ServiceContext serviceContext)
		throws PortalException {

		_getModelValidator().validate(
			companyId, groupId, classNameId, titleMap, content, ddmStructureKey,
			ddmTemplateKey, displayDate, expirationDate, smallImage,
			smallImageURL, smallImageFile, smallImageBytes, serviceContext);
	}

	protected void validate(String articleId) throws PortalException {
		_getModelValidator().validate(articleId);
	}

	protected void validate(
			String externalReferenceCode, long companyId, long groupId,
			long classNameId, String articleId, boolean autoArticleId,
			double version, Map<Locale, String> titleMap, String content,
			String ddmStructureKey, String ddmTemplateKey, Date displayDate,
			Date expirationDate, boolean smallImage, String smallImageURL,
			File smallImageFile, byte[] smallImageBytes,
			ServiceContext serviceContext)
		throws PortalException {

		_getModelValidator().validate(
			externalReferenceCode, companyId, groupId, classNameId, articleId,
			autoArticleId, version, titleMap, content, ddmStructureKey,
			ddmTemplateKey, displayDate, expirationDate, smallImage,
			smallImageURL, smallImageFile, smallImageBytes, serviceContext);
	}

	protected void validateContent(String content) throws PortalException {
		_getModelValidator().validateContent(content);
	}

	protected void validateDDMStructureId(
			long groupId, long folderId, String ddmStructureKey)
		throws PortalException {

		_getModelValidator().validateDDMStructureId(
			groupId, folderId, ddmStructureKey);
	}

	protected void validateReferences(
			long groupId, String ddmStructureKey, String ddmTemplateKey,
			String layoutUuid, boolean smallImage, String smallImageURL,
			byte[] smallImageBytes, long smallImageId, String content)
		throws PortalException {

		_getModelValidator().validateReferences(
			groupId, ddmStructureKey, ddmTemplateKey, layoutUuid, smallImage,
			smallImageURL, smallImageBytes, smallImageId, content);
	}

	@Reference
	protected ConfigurationProvider configurationProvider;

	@Reference
	protected DDMStorageLinkLocalService ddmStorageLinkLocalService;

	@Reference
	protected DDMStructureLinkLocalService ddmStructureLinkLocalService;

	@Reference
	protected DDMStructureLocalService ddmStructureLocalService;

	@Reference
	protected DDMTemplateLinkLocalService ddmTemplateLinkLocalService;

	@Reference
	protected DDMTemplateLocalService ddmTemplateLocalService;

	@Reference
	protected FriendlyURLEntryLocalService friendlyURLEntryLocalService;

	private List<JournalArticleLocalization> _addArticleLocalizedFields(
		long companyId, long articlePK, Map<Locale, String> titleMap,
		Map<Locale, String> descriptionMap) {

		Set<Locale> localeSet = new HashSet<>();

		localeSet.addAll(titleMap.keySet());

		if (descriptionMap != null) {
			localeSet.addAll(descriptionMap.keySet());
		}

		List<JournalArticleLocalization> journalArticleLocalizations =
			new ArrayList<>();

		for (Locale locale : localeSet) {
			String title = titleMap.get(locale);

			String description = null;

			if (descriptionMap != null) {
				description = descriptionMap.get(locale);
			}

			if (Validator.isNull(title) && Validator.isNull(description)) {
				continue;
			}

			JournalArticleLocalization journalArticleLocalization =
				_addArticleLocalizedFields(
					companyId, articlePK, title, description,
					LocaleUtil.toLanguageId(locale));

			journalArticleLocalizations.add(journalArticleLocalization);
		}

		return journalArticleLocalizations;
	}

	private JournalArticleLocalization _addArticleLocalizedFields(
		long companyId, long articlePK, String title, String description,
		String languageId) {

		JournalArticleLocalization journalArticleLocalization =
			_journalArticleLocalizationPersistence.fetchByA_L(
				articlePK, languageId);

		if (journalArticleLocalization == null) {
			long journalArticleLocalizationId = counterLocalService.increment();

			journalArticleLocalization =
				_journalArticleLocalizationPersistence.create(
					journalArticleLocalizationId);

			journalArticleLocalization.setCompanyId(companyId);
			journalArticleLocalization.setArticlePK(articlePK);
			journalArticleLocalization.setTitle(title);
			journalArticleLocalization.setDescription(description);
			journalArticleLocalization.setLanguageId(languageId);
		}
		else {
			journalArticleLocalization.setTitle(title);
			journalArticleLocalization.setDescription(description);
		}

		return _journalArticleLocalizationPersistence.update(
			journalArticleLocalization);
	}

	private boolean _addDraftAssetEntry(JournalArticle journalArticle) {
		if (journalArticle.isApproved() ||
			(journalArticle.getVersion() ==
				JournalArticleConstants.VERSION_DEFAULT)) {

			return false;
		}

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());

		if (assetEntry == null) {
			return false;
		}

		int approvedArticlesCount = journalArticlePersistence.countByG_A_ST(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			JournalArticleConstants.ASSET_ENTRY_CREATION_STATUSES);

		if (approvedArticlesCount == 0) {
			return false;
		}

		return true;
	}

	private Map<Locale, String> _checkFriendlyURLMap(
		Locale defaultLocale, Map<Locale, String> friendlyURLMap,
		Map<Locale, String> titleMap) {

		for (Map.Entry<Locale, String> friendlyURL :
				friendlyURLMap.entrySet()) {

			if (Validator.isNotNull(friendlyURL.getValue())) {
				return friendlyURLMap;
			}
		}

		return HashMapBuilder.put(
			defaultLocale, titleMap.get(defaultLocale)
		).build();
	}

	private void _deleteDDMStructurePredefinedValues(
			long groupId, String ddmStructureKey)
		throws PortalException {

		DDMStructure ddmStructure = ddmStructureLocalService.fetchStructure(
			groupId,
			_classNameLocalService.getClassNameId(JournalArticle.class),
			ddmStructureKey, true);

		if (ddmStructure == null) {
			return;
		}

		DDMForm ddmForm = ddmStructure.getDDMForm();

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			ddmFormField.setPredefinedValue(new LocalizedValue());
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		boolean indexingEnabled = serviceContext.isIndexingEnabled();

		try {
			serviceContext.setIndexingEnabled(false);

			ddmStructureLocalService.updateStructure(
				serviceContext.getUserId(), ddmStructure.getStructureId(),
				ddmForm, ddmStructure.getDDMFormLayout(), serviceContext);
		}
		finally {
			serviceContext.setIndexingEnabled(indexingEnabled);
		}
	}

	private String _formatContent(
			JournalArticle article, String content, long groupId, User user)
		throws PortalException {

		content = _journalContentCompatibilityConverter.convert(content);

		content = format(user, groupId, article, content);

		return _replaceTempImages(article, content);
	}

	private String _getArticleDiffs(
		JournalArticle article, ServiceContext serviceContext) {

		JournalArticle previousApprovedArticle = getPreviousApprovedArticle(
			article);

		try {
			PortletRequestModel portletRequestModel = null;

			if (!ExportImportThreadLocal.isImportInProcess()) {
				portletRequestModel = new PortletRequestModel(
					serviceContext.getLiferayPortletRequest(),
					serviceContext.getLiferayPortletResponse());
			}

			String articleDiffs = _journalHelper.diffHtml(
				article.getGroupId(), article.getArticleId(),
				previousApprovedArticle.getVersion(), article.getVersion(),
				LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()),
				portletRequestModel, serviceContext.getThemeDisplay());

			return _diffHtml.replaceStyles(articleDiffs);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return StringPool.BLANK;
	}

	private Map<String, String> _getFriendlyURLMap(
			JournalArticle article, ThemeDisplay themeDisplay)
		throws PortalException {

		Map<String, String> friendlyURLMap = new HashMap<>();

		LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
			_layoutDisplayPageProviderTracker.
				getLayoutDisplayPageProviderByClassName(
					JournalArticle.class.getName());

		if (layoutDisplayPageProvider == null) {
			return friendlyURLMap;
		}

		LayoutDisplayPageObjectProvider<?> layoutDisplayPageObjectProvider =
			layoutDisplayPageProvider.getLayoutDisplayPageObjectProvider(
				new InfoItemReference(
					JournalArticle.class.getName(),
					article.getResourcePrimKey()));

		if ((themeDisplay == null) ||
			(layoutDisplayPageObjectProvider == null) ||
			(themeDisplay.getSiteGroup() == null) ||
			!AssetDisplayPageUtil.hasAssetDisplayPage(
				themeDisplay.getScopeGroupId(),
				layoutDisplayPageObjectProvider.getClassNameId(),
				layoutDisplayPageObjectProvider.getClassPK(),
				layoutDisplayPageObjectProvider.getClassTypeId())) {

			return friendlyURLMap;
		}

		Map<Locale, String> friendlyURLs = article.getFriendlyURLMap();

		for (Locale locale : friendlyURLs.keySet()) {
			friendlyURLMap.put(
				LocaleUtil.toLanguageId(locale),
				_journalHelper.createURLPattern(
					article, locale, false,
					FriendlyURLResolverConstants.URL_SEPARATOR_JOURNAL_ARTICLE,
					themeDisplay));
		}

		return friendlyURLMap;
	}

	private Map<Locale, String> _getLocalizedBodyMap(
		String emailType,
		JournalGroupServiceConfiguration journalGroupServiceConfiguration) {

		if (emailType.equals("add")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.emailArticleAddedBody());
		}

		if (emailType.equals("denied")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.
					emailArticleApprovalDeniedBody());
		}

		if (emailType.equals("granted")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.
					emailArticleApprovalGrantedBody());
		}

		if (emailType.equals("move_to")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.
					emailArticleMovedToFolderBody());
		}

		if (emailType.equals("move_to_trash")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.
					emailArticleMovedToTrashBody());
		}
		else if (emailType.equals("move_from")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.
					emailArticleMovedFromFolderBody());
		}

		if (emailType.equals("move_from_trash")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.
					emailArticleMovedFromTrashBody());
		}

		if (emailType.equals("requested")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.
					emailArticleApprovalRequestedBody());
		}

		if (emailType.equals("review")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.emailArticleReviewBody());
		}

		if (emailType.equals("update")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.emailArticleUpdatedBody());
		}

		return null;
	}

	private Map<Locale, String> _getLocalizedSubjectMap(
		String emailType,
		JournalGroupServiceConfiguration journalGroupServiceConfiguration) {

		if (emailType.equals("add")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.emailArticleAddedSubject());
		}

		if (emailType.equals("denied")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.
					emailArticleApprovalDeniedSubject());
		}

		if (emailType.equals("granted")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.
					emailArticleApprovalGrantedSubject());
		}

		if (emailType.equals("move_to")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.
					emailArticleMovedToFolderSubject());
		}

		if (emailType.equals("move_to_trash")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.
					emailArticleMovedToTrashSubject());
		}

		if (emailType.equals("move_from")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.
					emailArticleMovedFromFolderSubject());
		}

		if (emailType.equals("move_from_trash")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.
					emailArticleMovedFromTrashSubject());
		}

		if (emailType.equals("requested")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.
					emailArticleApprovalRequestedSubject());
		}

		if (emailType.equals("review")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.emailArticleReviewSubject());
		}

		if (emailType.equals("update")) {
			return LocalizationUtil.getMap(
				journalGroupServiceConfiguration.emailArticleUpdatedSubject());
		}

		return null;
	}

	private JournalArticleModelValidator _getModelValidator() {
		ModelValidator<JournalArticle> modelValidator =
			ModelValidatorRegistryUtil.getModelValidator(JournalArticle.class);

		return (JournalArticleModelValidator)modelValidator;
	}

	private int _getNotificationType(String emailType) {
		if (emailType.equals("move_from")) {
			return JournalArticleConstants.
				NOTIFICATION_TYPE_MOVE_ENTRY_FROM_FOLDER;
		}

		if (emailType.equals("move_from_trash")) {
			return JournalArticleConstants.
				NOTIFICATION_TYPE_MOVE_ENTRY_FROM_TRASH;
		}

		if (emailType.equals("move_to")) {
			return JournalArticleConstants.
				NOTIFICATION_TYPE_MOVE_ENTRY_TO_FOLDER;
		}

		if (emailType.equals("move_to_trash")) {
			return JournalArticleConstants.
				NOTIFICATION_TYPE_MOVE_ENTRY_TO_TRASH;
		}

		if (emailType.equals("update")) {
			return UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		return UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;
	}

	private int _getUniqueUrlTitleCount(
		long groupId, String articleId, String urlTitle) {

		for (int i = 1;; i++) {
			JournalArticle article = fetchArticleByUrlTitle(groupId, urlTitle);

			if ((article == null) ||
				Objects.equals(articleId, article.getArticleId())) {

				return i - 1;
			}

			String suffix = StringPool.DASH + i;

			String prefix = urlTitle;

			if (urlTitle.length() > suffix.length()) {
				prefix = urlTitle.substring(
					0, urlTitle.length() - suffix.length());
			}

			urlTitle = prefix + suffix;
		}
	}

	private Map<String, String> _getURLTitleMap(
		long groupId, long resourcePrimKey, Map<Locale, String> friendlyURLMap,
		Map<Locale, String> titleMap) {

		Map<String, String> urlTitleMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : titleMap.entrySet()) {
			String friendlyURL = friendlyURLMap.get(entry.getKey());

			if (friendlyURL == null) {
				continue;
			}

			if (Validator.isNull(friendlyURL)) {
				urlTitleMap.put(
					LanguageUtil.getLanguageId(entry.getKey()),
					StringPool.BLANK);

				continue;
			}

			String languageId = LanguageUtil.getLanguageId(entry.getKey());

			String urlTitle = friendlyURLEntryLocalService.getUniqueUrlTitle(
				groupId,
				_classNameLocalService.getClassNameId(JournalArticle.class),
				resourcePrimKey, friendlyURL, languageId);

			urlTitleMap.put(languageId, urlTitle);
		}

		for (Map.Entry<Locale, String> entry : friendlyURLMap.entrySet()) {
			Locale key = entry.getKey();
			String value = entry.getValue();

			if (!urlTitleMap.containsKey(key) && Validator.isNotNull(value)) {
				String languageId = LocaleUtil.toLanguageId(key);

				String urlTitle =
					friendlyURLEntryLocalService.getUniqueUrlTitle(
						groupId,
						_classNameLocalService.getClassNameId(
							JournalArticle.class),
						resourcePrimKey, value, languageId);

				urlTitleMap.put(languageId, urlTitle);
			}
		}

		return urlTitleMap;
	}

	private void _populateSubscriptionSender(
		JournalArticle article, String articleURL, String emailType,
		String fromAddress, String fromName,
		JournalGroupServiceConfiguration journalGroupServiceConfiguration,
		ServiceContext serviceContext, SubscriptionSender subscriptionSender) {

		subscriptionSender.setClassName(article.getModelClassName());
		subscriptionSender.setCompanyId(article.getCompanyId());
		subscriptionSender.setContextAttribute(
			"[$ARTICLE_DIFFS$]", _getArticleDiffs(article, serviceContext),
			false);
		subscriptionSender.setContextAttributes(
			"[$ARTICLE_ID$]", article.getArticleId(), "[$ARTICLE_TITLE$]",
			article.getTitle(serviceContext.getLanguageId()), "[$ARTICLE_URL$]",
			articleURL, "[$ARTICLE_VERSION$]", article.getVersion());
		subscriptionSender.setContextCreatorUserPrefix("ARTICLE");
		subscriptionSender.setCreatorUserId(article.getUserId());
		subscriptionSender.setEntryURL(articleURL);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setLocalizedBodyMap(
			_getLocalizedBodyMap(emailType, journalGroupServiceConfiguration));
		subscriptionSender.setLocalizedSubjectMap(
			_getLocalizedSubjectMap(
				emailType, journalGroupServiceConfiguration));
		subscriptionSender.setMailId("journal_article", article.getId());
		subscriptionSender.setPortletId(
			PortletProviderUtil.getPortletId(
				JournalArticle.class.getName(), PortletProvider.Action.EDIT));
		subscriptionSender.setScopeGroupId(article.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);
	}

	private String _replaceTempImages(JournalArticle article, String content)
		throws PortalException {

		return _attachmentContentUpdater.updateContent(
			content, ContentTypes.TEXT_HTML,
			fileEntry -> {
				String fileEntryName = DLUtil.getUniqueFileName(
					fileEntry.getGroupId(), fileEntry.getFolderId(),
					fileEntry.getFileName(), false);

				Folder folder = article.addImagesFolder();

				_portletFileRepository.addPortletFileEntry(
					article.getGroupId(), fileEntry.getUserId(),
					JournalArticle.class.getName(),
					article.getResourcePrimKey(), JournalConstants.SERVICE_NAME,
					folder.getFolderId(), fileEntry.getContentStream(),
					fileEntryName, fileEntry.getMimeType(), false);

				return null;
			});
	}

	private List<JournalArticleLocalization> _updateArticleLocalizedFields(
		long companyId, long articleId, Map<Locale, String> titleMap,
		Map<Locale, String> descriptionMap) {

		List<JournalArticleLocalization> oldJournalArticleLocalizations =
			new ArrayList<>(
				_journalArticleLocalizationPersistence.findByArticlePK(
					articleId));

		List<JournalArticleLocalization> newJournalArticleLocalizations =
			_addArticleLocalizedFields(
				companyId, articleId, titleMap, descriptionMap);

		oldJournalArticleLocalizations.removeAll(
			newJournalArticleLocalizations);

		for (JournalArticleLocalization oldJournalArticleLocalization :
				oldJournalArticleLocalizations) {

			_journalArticleLocalizationPersistence.remove(
				oldJournalArticleLocalization);
		}

		return newJournalArticleLocalizations;
	}

	private JournalArticleLocalization _updateArticleLocalizedFields(
		long companyId, long articleId, String title, String description,
		String languageId) {

		JournalArticleLocalization journalArticleLocalization =
			_journalArticleLocalizationPersistence.fetchByA_L(
				articleId, languageId);

		if (journalArticleLocalization == null) {
			return _addArticleLocalizedFields(
				companyId, articleId, title, description, languageId);
		}

		journalArticleLocalization.setTitle(title);
		journalArticleLocalization.setDescription(description);

		return _journalArticleLocalizationPersistence.update(
			journalArticleLocalization);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleLocalServiceImpl.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetLinkLocalService _assetLinkLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private AttachmentContentUpdater _attachmentContentUpdater;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DDMFieldLocalService _ddmFieldLocalService;

	@Reference
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private DiffHtml _diffHtml;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference
	private ExpandoRowLocalService _expandoRowLocalService;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private FriendlyURLNormalizer _friendlyURLNormalizer;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private JournalArticleLocalizationPersistence
		_journalArticleLocalizationPersistence;

	@Reference
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

	@Reference
	private JournalArticleResourcePersistence
		_journalArticleResourcePersistence;

	@Reference
	private JournalContentCompatibilityConverter
		_journalContentCompatibilityConverter;

	@Reference
	private JournalContentSearchLocalService _journalContentSearchLocalService;

	@Reference
	private JournalConverter _journalConverter;

	@Reference
	private JournalDefaultTemplateProvider _journalDefaultTemplateProvider;

	@Reference
	private JournalFolderPersistence _journalFolderPersistence;

	@Reference
	private JournalHelper _journalHelper;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

	private Date _previousCheckDate;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private SystemEventLocalService _systemEventLocalService;

	@Reference
	private TrashEntryLocalService _trashEntryLocalService;

	@Reference
	private TrashHelper _trashHelper;

	@Reference
	private TrashVersionLocalService _trashVersionLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}