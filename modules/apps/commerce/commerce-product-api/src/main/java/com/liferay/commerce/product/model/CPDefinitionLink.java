/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CPDefinitionLink service. Represents a row in the &quot;CPDefinitionLink&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CPDefinitionLinkModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CPDefinitionLinkImpl"
)
@ProviderType
public interface CPDefinitionLink
	extends CPDefinitionLinkModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CPDefinitionLinkImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CPDefinitionLink, Long>
		CP_DEFINITION_LINK_ID_ACCESSOR =
			new Accessor<CPDefinitionLink, Long>() {

				@Override
				public Long get(CPDefinitionLink cpDefinitionLink) {
					return cpDefinitionLink.getCPDefinitionLinkId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CPDefinitionLink> getTypeClass() {
					return CPDefinitionLink.class;
				}

			};

	public CPDefinition getCPDefinition();

	public CProduct getCProduct();

	public String getCProductName();

}