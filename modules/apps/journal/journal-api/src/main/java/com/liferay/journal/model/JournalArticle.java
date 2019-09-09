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

package com.liferay.journal.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.model.TreeModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the JournalArticle service. Represents a row in the &quot;JournalArticle&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleModel
 * @generated
 */
@ImplementationClassName("com.liferay.journal.model.impl.JournalArticleImpl")
@ProviderType
public interface JournalArticle
	extends JournalArticleModel, PersistedModel, TreeModel {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.journal.model.impl.JournalArticleImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<JournalArticle, Long> ID_ACCESSOR =
		new Accessor<JournalArticle, Long>() {

			@Override
			public Long get(JournalArticle journalArticle) {
				return journalArticle.getId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<JournalArticle> getTypeClass() {
				return JournalArticle.class;
			}

		};
	public static final Accessor<JournalArticle, String> ARTICLE_ID_ACCESSOR =
		new Accessor<JournalArticle, String>() {

			@Override
			public String get(JournalArticle journalArticle) {
				return journalArticle.getArticleId();
			}

			@Override
			public Class<String> getAttributeClass() {
				return String.class;
			}

			@Override
			public Class<JournalArticle> getTypeClass() {
				return JournalArticle.class;
			}

		};

	public com.liferay.portal.kernel.repository.model.Folder addImagesFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	@Override
	public String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException;

	public Object clone();

	public String getArticleImageURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay);

	public JournalArticleResource getArticleResource()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getArticleResourceUuid()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String[] getAvailableLanguageIds();

	public String getContentByLocale(String languageId);

	public com.liferay.dynamic.data.mapping.model.DDMStructure
		getDDMStructure();

	public com.liferay.dynamic.data.mapping.model.DDMTemplate getDDMTemplate();

	@com.liferay.portal.kernel.json.JSON
	public String getDescription();

	public String getDescription(java.util.Locale locale);

	public String getDescription(java.util.Locale locale, boolean useDefault);

	public String getDescription(String languageId);

	public String getDescription(String languageId, boolean useDefault);

	public java.util.Map<java.util.Locale, String> getDescriptionMap();

	public String getDescriptionMapAsXML();

	public com.liferay.portal.kernel.xml.Document getDocument();

	public JournalFolder getFolder()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.Map<java.util.Locale, String> getFriendlyURLMap()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getFriendlyURLsXML()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getImagesFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getImagesFileEntries(int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getImagesFileEntries(
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.PortalException;

	public int getImagesFileEntriesCount()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getImagesFolderId();

	public com.liferay.portal.kernel.model.Layout getLayout();

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	public String getLegacyDescription();

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	public String getLegacyTitle();

	public String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException;

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #getDDMStructureKey()}
	 */
	@Deprecated
	public String getStructureId();

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #getDDMTemplateKey()}
	 */
	@Deprecated
	public String getTemplateId();

	@com.liferay.portal.kernel.json.JSON
	public String getTitle();

	public String getTitle(java.util.Locale locale);

	public String getTitle(java.util.Locale locale, boolean useDefault);

	public String getTitle(String languageId);

	public String getTitle(String languageId, boolean useDefault);

	@com.liferay.portal.kernel.json.JSON
	public String getTitleCurrentValue();

	public java.util.Map<java.util.Locale, String> getTitleMap();

	public String getTitleMapAsXML();

	public String getUrlTitle(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean hasApprovedVersion();

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public boolean isTemplateDriven();

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	public void setDescription(String description);

	public void setDescriptionMap(
		java.util.Map<java.util.Locale, String> descriptionMap);

	public void setDocument(com.liferay.portal.kernel.xml.Document document);

	public void setImagesFolderId(long imagesFolderId);

	public void setSmallImageType(String smallImageType);

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #setDDMStructureKey(String)}
	 */
	@Deprecated
	public void setStructureId(String ddmStructureKey);

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 #setDDMTemplateKey(String)}
	 */
	@Deprecated
	public void setTemplateId(String ddmTemplateKey);

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	public void setTitle(String title);

	public void setTitleMap(java.util.Map<java.util.Locale, String> titleMap);

}