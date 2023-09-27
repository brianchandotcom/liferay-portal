/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the TranslationEntry service. Represents a row in the &quot;TranslationEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see TranslationEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.translation.model.impl.TranslationEntryImpl"
)
@ProviderType
public interface TranslationEntry
	extends PersistedModel, TranslationEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.translation.model.impl.TranslationEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<TranslationEntry, Long>
		TRANSLATION_ENTRY_ID_ACCESSOR = new Accessor<TranslationEntry, Long>() {

			@Override
			public Long get(TranslationEntry translationEntry) {
				return translationEntry.getTranslationEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<TranslationEntry> getTypeClass() {
				return TranslationEntry.class;
			}

		};

	public com.liferay.info.item.InfoItemFieldValues getInfoItemFieldValues(
			long groupId, String className, long classPK, String content)
		throws com.liferay.portal.kernel.exception.PortalException;

}