/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.model;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the JournalArticle service. Represents a row in the &quot;JournalArticle&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleModel
 * @see com.liferay.portlet.journal.model.impl.JournalArticleImpl
 * @see com.liferay.portlet.journal.model.impl.JournalArticleModelImpl
 * @generated
 */
public interface JournalArticle extends JournalArticleModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portlet.journal.model.impl.JournalArticleImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<JournalArticle, String> ARTICLE_ID_ACCESSOR = new Accessor<JournalArticle, String>() {
			public String get(JournalArticle journalArticle) {
				return journalArticle.getArticleId();
			}
		};

	public com.liferay.portlet.journal.model.JournalArticleResource getArticleResource()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getArticleResourceUuid()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String[] getAvailableLocales();

	public java.lang.String getContentByLocale(java.lang.String languageId);

	public java.lang.String getDefaultLocale();

	public com.liferay.portlet.journal.model.JournalFolder getFolder();

	public java.lang.String getSmallImageType()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.Map<java.util.Locale, java.lang.String> getTitleMap();

	public boolean isTemplateDriven();

	public void prepareLocalizedFieldsForImport(
		java.util.Locale defaultImportLocale)
		throws com.liferay.portal.LocaleException;

	public void setSmallImageType(java.lang.String smallImageType);
}