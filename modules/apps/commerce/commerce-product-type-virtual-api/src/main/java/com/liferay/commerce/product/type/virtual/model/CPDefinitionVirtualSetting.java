/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CPDefinitionVirtualSetting service. Represents a row in the &quot;CPDefinitionVirtualSetting&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSettingModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingImpl"
)
@ProviderType
public interface CPDefinitionVirtualSetting
	extends CPDefinitionVirtualSettingModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDefinitionVirtualSetting, Long>
		CP_DEFINITION_VIRTUAL_SETTING_ID_ACCESSOR =
			new Accessor<CPDefinitionVirtualSetting, Long>() {

				@Override
				public Long get(
					CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {

					return cpDefinitionVirtualSetting.
						getCPDefinitionVirtualSettingId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPDefinitionVirtualSetting> getTypeClass() {
					return CPDefinitionVirtualSetting.class;
				}

			};

	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.repository.model.FileEntry
			getSampleFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.journal.model.JournalArticle
			getTermsOfUseJournalArticle()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isUseSampleURL();

	public boolean isUseTermsOfUseJournal();

	public boolean isUseUrl();

}