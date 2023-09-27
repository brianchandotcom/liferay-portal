/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the JournalArticleLocalization service. Represents a row in the &quot;JournalArticleLocalization&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleLocalizationModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.journal.model.impl.JournalArticleLocalizationImpl"
)
@ProviderType
public interface JournalArticleLocalization
	extends JournalArticleLocalizationModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.journal.model.impl.JournalArticleLocalizationImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<JournalArticleLocalization, Long>
		ARTICLE_LOCALIZATION_ID_ACCESSOR =
			new Accessor<JournalArticleLocalization, Long>() {

				@Override
				public Long get(
					JournalArticleLocalization journalArticleLocalization) {

					return journalArticleLocalization.
						getArticleLocalizationId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<JournalArticleLocalization> getTypeClass() {
					return JournalArticleLocalization.class;
				}

			};

}