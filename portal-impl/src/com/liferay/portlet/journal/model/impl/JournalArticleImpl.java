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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.webserver.WebServerServletTokenUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
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
		String content, boolean templateDriven, String languageId) {

		TransformerListener transformerListener =
			new LocaleTransformerListener();

		return transformerListener.onXml(content, languageId, null);
	}

	public JournalArticleImpl() {
	}

	@Override
	public String buildTreePath() throws PortalException, SystemException {
		StringBundler sb = new StringBundler();

		buildTreePath(sb, getFolder());

		return sb.toString();
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

		String[] contentAvailableLanguageIds =
			LocalizationUtil.getAvailableLanguageIds(getContent());

		for (String availableLanguageId : contentAvailableLanguageIds) {
			availableLanguageIds.add(availableLanguageId);
		}

		return availableLanguageIds.toArray(
			new String[availableLanguageIds.size()]);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getAvailableLanguageIds}
	 */
	@Override
	public String[] getAvailableLocales() {
		return getAvailableLanguageIds();
	}

	@Override
	public String getContentByLocale(String languageId) {
		return getContentByLocale(getContent(), isTemplateDriven(), languageId);
	}

	@Override
	public String getDefaultLanguageId() {
		String defaultLanguageId = super.getDefaultLanguageId();

		if (isTemplateDriven() && Validator.isNull(defaultLanguageId)) {
			defaultLanguageId = LocaleUtil.toLanguageId(
				LocaleUtil.getSiteDefault());
		}

		return defaultLanguageId;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #getDefaultLanguageId}
	 */
	@Override
	public String getDefaultLocale() {
		return getDefaultLanguageId();
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

	@Override
	public boolean isTemplateDriven() {
		if (Validator.isNull(getStructureId())) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * @param  defaultImportLocale the default imported locale
	 * @throws LocaleException if a locale exception occurred
	 */
	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException {
	}

	@Override
	public void setSmallImageType(String smallImageType) {
		_smallImageType = smallImageType;
	}

	protected void buildTreePath(StringBundler sb, JournalFolder folder)
		throws PortalException, SystemException {

		if (folder == null) {
			sb.append(StringPool.SLASH);
		}
		else {
			buildTreePath(sb, folder.getParentFolder());

			sb.append(folder.getFolderId());
			sb.append(StringPool.SLASH);
		}
	}

	private String _smallImageType;

}