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
 * The extended model interface for the CPConfigurationListRel service. Represents a row in the &quot;CPConfigurationListRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPConfigurationListRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPConfigurationListRelImpl"
)
@ProviderType
public interface CPConfigurationListRel
	extends CPConfigurationListRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPConfigurationListRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPConfigurationListRel, Long>
		CP_CONFIGURATION_LIST_REL_ID_ACCESSOR =
			new Accessor<CPConfigurationListRel, Long>() {

				@Override
				public Long get(CPConfigurationListRel cpConfigurationListRel) {
					return cpConfigurationListRel.getCPConfigurationListRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPConfigurationListRel> getTypeClass() {
					return CPConfigurationListRel.class;
				}

			};

}
// LIFERAY-SERVICE-BUILDER-HASH:-43123511