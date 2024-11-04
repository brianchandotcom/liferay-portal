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
 * The extended model interface for the CPConfigurationList service. Represents a row in the &quot;CPConfigurationList&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPConfigurationListModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPConfigurationListImpl"
)
@ProviderType
public interface CPConfigurationList
	extends CPConfigurationListModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPConfigurationListImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPConfigurationList, Long>
		CP_CONFIGURATION_LIST_ID_ACCESSOR =
			new Accessor<CPConfigurationList, Long>() {

				@Override
				public Long get(CPConfigurationList cpConfigurationList) {
					return cpConfigurationList.getCPConfigurationListId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPConfigurationList> getTypeClass() {
					return CPConfigurationList.class;
				}

			};

}