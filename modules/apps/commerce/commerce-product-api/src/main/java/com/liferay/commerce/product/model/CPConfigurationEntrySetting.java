/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CPConfigurationEntrySetting service. Represents a row in the &quot;CPConfigurationEntrySetting&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPConfigurationEntrySettingModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPConfigurationEntrySettingImpl"
)
@ProviderType
public interface CPConfigurationEntrySetting
	extends CPConfigurationEntrySettingModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPConfigurationEntrySettingImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPConfigurationEntrySetting, Long>
		CP_CONFIGURATION_ENTRY_SETTING_ID_ACCESSOR =
			new Accessor<CPConfigurationEntrySetting, Long>() {

				@Override
				public Long get(
					CPConfigurationEntrySetting cpConfigurationEntrySetting) {

					return cpConfigurationEntrySetting.
						getCPConfigurationEntrySettingId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPConfigurationEntrySetting> getTypeClass() {
					return CPConfigurationEntrySetting.class;
				}

			};

}
// LIFERAY-SERVICE-BUILDER-HASH:1028692345