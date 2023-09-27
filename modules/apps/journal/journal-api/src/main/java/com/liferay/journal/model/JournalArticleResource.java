/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the JournalArticleResource service. Represents a row in the &quot;JournalArticleResource&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleResourceModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.journal.model.impl.JournalArticleResourceImpl"
)
@ProviderType
public interface JournalArticleResource
	extends JournalArticleResourceModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.journal.model.impl.JournalArticleResourceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<JournalArticleResource, Long>
		RESOURCE_PRIM_KEY_ACCESSOR =
			new Accessor<JournalArticleResource, Long>() {

				@Override
				public Long get(JournalArticleResource journalArticleResource) {
					return journalArticleResource.getResourcePrimKey();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<JournalArticleResource> getTypeClass() {
					return JournalArticleResource.class;
				}

			};

	public long getLatestArticlePK();

}