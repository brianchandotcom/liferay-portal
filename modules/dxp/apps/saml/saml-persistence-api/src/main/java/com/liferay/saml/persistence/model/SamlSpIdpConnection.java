/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SamlSpIdpConnection service. Represents a row in the &quot;SamlSpIdpConnection&quot; database table, with each column mapped to a property of this class.
 *
 * @author Mika Koivisto
 * @see SamlSpIdpConnectionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionImpl"
)
@ProviderType
public interface SamlSpIdpConnection
	extends PersistedModel, SamlSpIdpConnectionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SamlSpIdpConnection, Long>
		SAML_SP_IDP_CONNECTION_ID_ACCESSOR =
			new Accessor<SamlSpIdpConnection, Long>() {

				@Override
				public Long get(SamlSpIdpConnection samlSpIdpConnection) {
					return samlSpIdpConnection.getSamlSpIdpConnectionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<SamlSpIdpConnection> getTypeClass() {
					return SamlSpIdpConnection.class;
				}

			};

	public java.util.Properties getNormalizedUserAttributeMappings()
		throws java.io.IOException;

}