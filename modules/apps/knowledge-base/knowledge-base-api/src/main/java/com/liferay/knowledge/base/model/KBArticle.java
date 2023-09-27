/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the KBArticle service. Represents a row in the &quot;KBArticle&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see KBArticleModel
 * @generated
 */
@ImplementationClassName("com.liferay.knowledge.base.model.impl.KBArticleImpl")
@ProviderType
public interface KBArticle extends KBArticleModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.knowledge.base.model.impl.KBArticleImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<KBArticle, Long> KB_ARTICLE_ID_ACCESSOR =
		new Accessor<KBArticle, Long>() {

			@Override
			public Long get(KBArticle kbArticle) {
				return kbArticle.getKbArticleId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<KBArticle> getTypeClass() {
				return KBArticle.class;
			}

		};

	/**
	 * @see com.liferay.portal.model.impl.OrganizationBaseImpl#buildTreePath()
	 */
	public String buildTreePath()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<Long> getAncestorResourcePrimaryKeys()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.repository.model.FileEntry>
			getAttachmentsFileEntries()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.repository.model.FileEntry
			getAttachmentsFileEntryByExternalReferenceCode(
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getAttachmentsFolderId()
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getClassNameId();

	public long getClassPK();

	public KBArticle getParentKBArticle()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getParentTitle(java.util.Locale locale, int status)
		throws com.liferay.portal.kernel.exception.PortalException;

	public long getViewCount();

	public boolean isFirstVersion();

	public boolean isRoot();

}