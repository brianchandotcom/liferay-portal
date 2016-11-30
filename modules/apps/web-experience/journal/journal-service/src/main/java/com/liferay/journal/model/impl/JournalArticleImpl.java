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

package com.liferay.journal.model.impl;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.transformer.LocaleTransformerListener;
import com.liferay.journal.util.impl.JournalUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.cache.CacheField;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ImageLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class JournalArticleImpl extends JournalArticleBaseImpl {

	public static String getContentByLocale(
		Document document, String languageId) {

		return getContentByLocale(document, languageId, null);
	}

	public static String getContentByLocale(
		Document document, String languageId, Map<String, String> tokens) {

		TransformerListener transformerListener =
			new LocaleTransformerListener();

		document = transformerListener.onXml(
			document.clone(), languageId, tokens);

		return document.asXML();
	}

	/**
	 * @deprecated As of 4.0.0, replaced by {@link #getContentByLocale(Document,
	 *             String)}
	 */
	@Deprecated
	public static String getContentByLocale(
		String content, boolean templateDriven, String languageId) {

		try {
			return getContentByLocale(SAXReaderUtil.read(content), languageId);
		}
		catch (DocumentException de) {
			if (_log.isWarnEnabled()) {
				_log.warn(de, de);
			}

			return content;
		}
	}

	@Override
	public Folder addImagesFolder() throws PortalException {
		if (_imagesFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return PortletFileRepositoryUtil.getPortletFolder(_imagesFolderId);
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Repository repository = PortletFileRepositoryUtil.addPortletRepository(
			getGroupId(), JournalConstants.SERVICE_NAME, serviceContext);

		Folder folder = PortletFileRepositoryUtil.addPortletFolder(
			getUserId(), repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			String.valueOf(getResourcePrimKey()), serviceContext);

		_imagesFolderId = folder.getFolderId();

		return folder;
	}

	@Override
	public String buildTreePath() throws PortalException {
		if (getFolderId() == JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return StringPool.SLASH;
		}

		JournalFolder folder = getFolder();

		return folder.buildTreePath();
	}

	@Override
	public Object clone() {
		JournalArticleImpl journalArticle = (JournalArticleImpl)super.clone();

		journalArticle.setDescriptionMap(getDescriptionMap());
		journalArticle.setTitleMap(getTitleMap());

		return journalArticle;
	}

	@Override
	public String getArticleImageURL(ThemeDisplay themeDisplay) {
		if (!isSmallImage()) {
			return null;
		}

		if (Validator.isNotNull(getSmallImageURL())) {
			return getSmallImageURL();
		}

		return themeDisplay.getPathImage() + "/journal/article?img_id=" +
			getSmallImageId() + "&t=" +
				WebServerServletTokenUtil.getToken(getSmallImageId());
	}

	@Override
	public JournalArticleResource getArticleResource() throws PortalException {
		return JournalArticleResourceLocalServiceUtil.getArticleResource(
			getResourcePrimKey());
	}

	@Override
	public String getArticleResourceUuid() throws PortalException {
		JournalArticleResource articleResource = getArticleResource();

		return articleResource.getUuid();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		Set<String> availableLanguageIds = new TreeSet<>();

		availableLanguageIds.addAll(
			JournalArticleLocalServiceUtil.getArticleLocalizationLanguageIds(
				getId()));

		Document document = getDocument();

		if (document != null) {
			for (String availableLanguageId :
					LocalizationUtil.getAvailableLanguageIds(document)) {

				availableLanguageIds.add(availableLanguageId);
			}
		}

		return availableLanguageIds.toArray(
			new String[availableLanguageIds.size()]);
	}

	@Override
	public String getContentByLocale(String languageId) {
		Map<String, String> tokens = new HashMap<>();

		DDMStructure ddmStructure = getDDMStructure();

		if (ddmStructure != null) {
			tokens.put(
				"ddm_structure_id",
				String.valueOf(ddmStructure.getStructureId()));
		}

		return getContentByLocale(getDocument(), languageId, tokens);
	}

	@Override
	public DDMStructure getDDMStructure() {
		DDMStructure ddmStructure = null;

		try {
			ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
				PortalUtil.getSiteGroupId(getGroupId()),
				ClassNameLocalServiceUtil.getClassNameId(JournalArticle.class),
				getDDMStructureKey(), true);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to get DDM structure with DDM structure key " +
					getDDMStructureKey(),
				pe);
		}

		return ddmStructure;
	}

	@Override
	public DDMTemplate getDDMTemplate() {
		DDMTemplate ddmTemplate = null;

		try {
			ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
				PortalUtil.getSiteGroupId(getGroupId()),
				ClassNameLocalServiceUtil.getClassNameId(JournalArticle.class),
				getDDMTemplateKey(), true);
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to get DDM template for DDM structure with" +
					"DDM structure key " + getDDMStructureKey(),
				pe);
		}

		return ddmTemplate;
	}

	@JSON
	@Override
	public String getDescription() {
		String description =
			JournalArticleLocalServiceUtil.getArticleDescription(
				getId(), getDefaultLanguageId());

		if (description == null) {
			return StringPool.BLANK;
		}
		else {
			return description;
		}
	}

	@Override
	public String getDescription(Locale locale) {
		String description =
			JournalArticleLocalServiceUtil.getArticleDescription(
				getId(), locale);

		if (description == null) {
			return getDescription();
		}
		else {
			return description;
		}
	}

	@Override
	public String getDescription(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getDescription(languageId, useDefault);
	}

	@Override
	public String getDescription(String languageId) {
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return getDescription(locale);
	}

	@Override
	public String getDescription(String languageId, boolean useDefault) {
		String description =
			JournalArticleLocalServiceUtil.getArticleDescription(
				getId(), languageId);

		if (description != null) {
			return description;
		}
		else if (useDefault) {
			return getDescription();
		}

		return StringPool.BLANK;
	}

	@Override
	public Map<Locale, String> getDescriptionMap() {
		if (_descriptionMap != null) {
			return _descriptionMap;
		}

		_descriptionMap =
			JournalArticleLocalServiceUtil.getArticleDescriptionMap(getId());

		return _descriptionMap;
	}

	@Override
	public String getDescriptionMapAsXML() {
		return LocalizationUtil.updateLocalization(
			getDescriptionMap(), StringPool.BLANK, "Description",
			getDefaultLanguageId());
	}

	@Override
	public Document getDocument() {
		if (_document == null) {
			try {
				_document = SAXReaderUtil.read(getContent());
			}
			catch (DocumentException de) {
				if (_log.isWarnEnabled()) {
					_log.warn(de, de);
				}
			}
		}

		return _document;
	}

	@Override
	public JournalFolder getFolder() throws PortalException {
		if (getFolderId() <= 0) {
			return new JournalFolderImpl();
		}

		return JournalFolderLocalServiceUtil.getFolder(getFolderId());
	}

	@Override
	public List<FileEntry> getImagesFileEntries() throws PortalException {
		return getImagesFileEntries(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<FileEntry> getImagesFileEntries(int start, int end)
		throws PortalException {

		return getImagesFileEntries(start, end, null);
	}

	@Override
	public List<FileEntry> getImagesFileEntries(
			int start, int end, OrderByComparator obc)
		throws PortalException {

		long imagesFolderId = getImagesFolderId();

		if (imagesFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return new ArrayList<>();
		}

		return PortletFileRepositoryUtil.getPortletFileEntries(
			getGroupId(), imagesFolderId, WorkflowConstants.STATUS_APPROVED,
			start, end, obc);
	}

	@Override
	public int getImagesFileEntriesCount() throws PortalException {
		long imagesFolderId = getImagesFolderId();

		if (imagesFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return 0;
		}

		return PortletFileRepositoryUtil.getPortletFileEntriesCount(
			getGroupId(), imagesFolderId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public long getImagesFolderId() {
		if (_imagesFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return _imagesFolderId;
		}

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				getGroupId(), JournalConstants.SERVICE_NAME);

		if (repository == null) {
			return DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		try {
			Folder folder = PortletFileRepositoryUtil.getPortletFolder(
				repository.getRepositoryId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				String.valueOf(getResourcePrimKey()));

			_imagesFolderId = folder.getFolderId();
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to get folder for " + getResourcePrimKey());
			}
		}

		return _imagesFolderId;
	}

	@Override
	public Layout getLayout() {
		String layoutUuid = getLayoutUuid();

		if (Validator.isNull(layoutUuid)) {
			return null;
		}

		return JournalUtil.getArticleLayout(layoutUuid, getGroupId());
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	public String getLegacyDescription() {
		return _description;
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	public String getLegacyTitle() {
		return _title;
	}

	@Override
	public String getSmallImageType() throws PortalException {
		if ((_smallImageType == null) && isSmallImage()) {
			Image smallImage = ImageLocalServiceUtil.getImage(
				getSmallImageId());

			_smallImageType = smallImage.getType();
		}

		return _smallImageType;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(JournalArticle.class);
	}

	/**
	 * @deprecated As of 4.0.0, replaced by {@link #getDDMStructureKey()}
	 */
	@Deprecated
	@Override
	public String getStructureId() {
		return getDDMStructureKey();
	}

	/**
	 * @deprecated As of 4.0.0, replaced by {@link #getDDMTemplateKey()}
	 */
	@Deprecated
	@Override
	public String getTemplateId() {
		return getDDMTemplateKey();
	}

	@JSON
	@Override
	public String getTitle() {
		String title = JournalArticleLocalServiceUtil.getArticleTitle(
			getId(), getDefaultLanguageId());

		if (title == null) {
			return StringPool.BLANK;
		}
		else {
			return title;
		}
	}

	@Override
	public String getTitle(Locale locale) {
		String title = JournalArticleLocalServiceUtil.getArticleTitle(
			getId(), locale);

		if (title == null) {
			return getTitle();
		}
		else {
			return title;
		}
	}

	@Override
	public String getTitle(Locale locale, boolean useDefault) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return getTitle(languageId, useDefault);
	}

	@Override
	public String getTitle(String languageId) {
		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return getTitle(locale);
	}

	@Override
	public String getTitle(String languageId, boolean useDefault) {
		String title = JournalArticleLocalServiceUtil.getArticleTitle(
			getId(), languageId);

		if (title != null) {
			return title;
		}
		else if (useDefault) {
			return getTitle();
		}

		return StringPool.BLANK;
	}

	@JSON
	@Override
	public String getTitleCurrentValue() {
		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		return getTitle(locale, true);
	}

	@Override
	public Map<Locale, String> getTitleMap() {
		if (_titleMap != null) {
			return _titleMap;
		}

		_titleMap = JournalArticleLocalServiceUtil.getArticleTitleMap(getId());

		return _titleMap;
	}

	@Override
	public String getTitleMapAsXML() {
		return LocalizationUtil.updateLocalization(
			getTitleMap(), StringPool.BLANK, "Title", getDefaultLanguageId());
	}

	@Override
	public long getTrashEntryClassPK() {
		return getResourcePrimKey();
	}

	@Override
	public boolean hasApprovedVersion() {
		JournalArticle article =
			JournalArticleLocalServiceUtil.fetchLatestArticle(
				getGroupId(), getArticleId(),
				WorkflowConstants.STATUS_APPROVED);

		if (article == null) {
			return false;
		}

		return true;
	}

	/**
	 * @deprecated As of 4.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isTemplateDriven() {
		return true;
	}

	@Override
	public void setContent(String content) {
		super.setContent(content);

		_document = null;
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	public void setDescription(String description) {
		_description = description;
	}

	public void setDescriptionMap(Map<Locale, String> descriptionMap) {
		_descriptionMap = descriptionMap;
	}

	@Override
	public void setDocument(Document document) {
		_document = document;
	}

	@Override
	public void setImagesFolderId(long imagesFolderId) {
		_imagesFolderId = imagesFolderId;
	}

	@Override
	public void setSmallImageType(String smallImageType) {
		_smallImageType = smallImageType;
	}

	/**
	 * @deprecated As of 4.0.0, replaced by {@link #setDDMStructureKey(String)}
	 */
	@Deprecated
	@Override
	public void setStructureId(String ddmStructureKey) {
		setDDMStructureKey(ddmStructureKey);
	}

	/**
	 * @deprecated As of 4.0.0, replaced by {@link #setDDMTemplateKey(String)}
	 */
	@Deprecated
	@Override
	public void setTemplateId(String ddmTemplateKey) {
		setDDMTemplateKey(ddmTemplateKey);
	}

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	public void setTitle(String title) {
		_title = title;
	}

	public void setTitleMap(Map<Locale, String> titleMap) {
		_titleMap = titleMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleImpl.class);

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	private String _description;

	private Map<Locale, String> _descriptionMap;

	@CacheField(propagateToInterface = true)
	private Document _document;

	private long _imagesFolderId;
	private String _smallImageType;

	/**
	 * @deprecated As of 4.0.0
	 */
	@Deprecated
	private String _title;

	private Map<Locale, String> _titleMap;

}