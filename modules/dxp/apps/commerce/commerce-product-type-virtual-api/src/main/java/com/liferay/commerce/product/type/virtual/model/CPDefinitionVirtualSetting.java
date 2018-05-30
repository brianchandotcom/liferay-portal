/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.type.virtual.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the CPDefinitionVirtualSetting service. Represents a row in the &quot;CPDefinitionVirtualSetting&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPDefinitionVirtualSettingModel
 * @see com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingImpl
 * @see com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingImpl")
@ProviderType
public interface CPDefinitionVirtualSetting
	extends CPDefinitionVirtualSettingModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDefinitionVirtualSetting, Long> CP_DEFINITION_VIRTUAL_SETTING_ID_ACCESSOR =
		new Accessor<CPDefinitionVirtualSetting, Long>() {
			@Override
			public Long get(
				CPDefinitionVirtualSetting cpDefinitionVirtualSetting) {
				return cpDefinitionVirtualSetting.getCPDefinitionVirtualSettingId();
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

	public com.liferay.commerce.product.model.CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.repository.model.FileEntry getFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.repository.model.FileEntry getSampleFileEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.journal.model.JournalArticle getTermsOfUseJournalArticle()
		throws com.liferay.portal.kernel.exception.PortalException;

	public boolean isUseSampleUrl();

	public boolean isUseTermsOfUseJournal();

	public boolean isUseUrl();
}