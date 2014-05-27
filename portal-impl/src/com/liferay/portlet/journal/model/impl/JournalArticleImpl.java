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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.CacheField;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.webserver.WebServerServletTokenUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleImageLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.journal.util.LocaleTransformerListener;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class JournalArticleImpl extends JournalArticleBaseImpl {

	public static String getContentByLocale(
		Document document, String languageId) {

		TransformerListener transformerListener =
			new LocaleTransformerListener();

		document = transformerListener.onXml(
			document.clone(), languageId, null);

		return document.asXML();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getContentByLocale(String,
	 *             String}
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

	public JournalArticleImpl() {
	}

	@Override
	public String buildTreePath() throws PortalException, SystemException {
		JournalFolder folder = getFolder();

		return folder.buildTreePath();
	}

	@Override
	public long getArticleImageId(
			String elInstanceId, String elName, String languageId)
		throws SystemException {

		return JournalArticleImageLocalServiceUtil.getArticleImageId(
			getGroupId(), getArticleId(), getVersion(), elInstanceId, elName,
			languageId);
	}

	@Override
	public String getArticleImageURL(ThemeDisplay themeDisplay) {
		if (!isSmallImage()) {
			return null;
		}

		if (Validator.isNotNull(getSmallImageURL())) {
			return getSmallImageURL();
		}

		return
			themeDisplay.getPathImage() + "/journal/article?img_id=" +
				getSmallImageId() + "&t=" +
					WebServerServletTokenUtil.getToken(getSmallImageId());
	}

	@Override
	public JournalArticleResource getArticleResource()
		throws PortalException, SystemException {

		return JournalArticleResourceLocalServiceUtil.getArticleResource(
			getResourcePrimKey());
	}

	@Override
	public String getArticleResourceUuid()
		throws PortalException, SystemException {

		JournalArticleResource articleResource = getArticleResource();

		return articleResource.getUuid();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		Set<String> availableLanguageIds = SetUtil.fromArray(
			super.getAvailableLanguageIds());

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

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getAvailableLanguageIds}
	 */
	@Deprecated
	@Override
	public String[] getAvailableLocales() {
		return getAvailableLanguageIds();
	}

	@Override
	public String getContentByLocale(String languageId) {
		return getContentByLocale(getDocument(), languageId);
	}

	@Override
	public DDMStructure getDDMStructure()
		throws PortalException, SystemException {

		return DDMStructureLocalServiceUtil.fetchStructure(
			PortalUtil.getSiteGroupId(getGroupId()),
			ClassNameLocalServiceUtil.getClassNameId(JournalArticle.class),
			getStructureId(), true);
	}

	@Override
	public DDMTemplate getDDMTemplate()
		throws PortalException, SystemException {

		return DDMTemplateLocalServiceUtil.fetchTemplate(
			PortalUtil.getSiteGroupId(getGroupId()),
			ClassNameLocalServiceUtil.getClassNameId(JournalArticle.class),
			getStructureId(), true);
	}

	@Override
	public String getDefaultLanguageId() {
		if (_defaultLanguageId == null) {
			_defaultLanguageId = super.getDefaultLanguageId();

			if (Validator.isNull(_defaultLanguageId)) {
				_defaultLanguageId = LocaleUtil.toLanguageId(
					LocaleUtil.getSiteDefault());
			}
		}

		return _defaultLanguageId;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getDefaultLanguageId}
	 */
	@Deprecated
	@Override
	public String getDefaultLocale() {
		return getDefaultLanguageId();
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
	public JournalFolder getFolder() throws PortalException, SystemException {
		if (getFolderId() <= 0) {
			return new JournalFolderImpl();
		}

		return JournalFolderLocalServiceUtil.getFolder(getFolderId());
	}

	@Override
	public String getSmallImageType() throws PortalException, SystemException {
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

	@Override
	public Map<Locale, String> getTitleMap() {
		Locale defaultLocale = LocaleThreadLocal.getDefaultLocale();

		try {
			Locale articleDefaultLocale = LocaleUtil.fromLanguageId(
				getDefaultLanguageId());

			LocaleThreadLocal.setDefaultLocale(articleDefaultLocale);

			return super.getTitleMap();
		}
		finally {
			LocaleThreadLocal.setDefaultLocale(defaultLocale);
		}
	}

	@Override
	public long getTrashEntryClassPK() {
		return getResourcePrimKey();
	}

	@Override
	public boolean hasApprovedVersion() throws SystemException {
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
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isTemplateDriven() {
		return true;
	}

	/**
	 * @param  defaultImportLocale the default imported locale
	 * @throws LocaleException if a locale exception occurred
	 */
	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException {

		super.prepareLocalizedFieldsForImport(defaultImportLocale);

		String content = StringPool.BLANK;

		try {
			content = JournalUtil.prepareLocalizedContentForImport(
				getContent(), defaultImportLocale);
		}
		catch (Exception e) {
			throw new LocaleException(LocaleException.TYPE_DEFAULT, e);
		}

		setContent(content);
	}

	@Override
	public void setContent(String content) {
		super.setContent(content);

		_document = null;
	}

	@Override
	public void setDefaultLanguageId(String defaultLanguageId) {
		_defaultLanguageId = defaultLanguageId;
	}

	@Override
	public void setDocument(Document document) {
		_document = document;
	}

	@Override
	public void setSmallImageType(String smallImageType) {
		_smallImageType = smallImageType;
	}

	@Override
	public void setTitle(String title) {
		super.setTitle(title);

		_defaultLanguageId = null;
	}

	private static Log _log = LogFactoryUtil.getLog(JournalArticleImpl.class);

	@CacheField
	private String _defaultLanguageId;

	@CacheField
	private Document _document;

	private String _smallImageType;

}