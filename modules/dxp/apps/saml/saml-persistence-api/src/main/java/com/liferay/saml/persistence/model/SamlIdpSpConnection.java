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
 * The extended model interface for the SamlIdpSpConnection service. Represents a row in the &quot;SamlIdpSpConnection&quot; database table, with each column mapped to a property of this class.
 *
 * @author Mika Koivisto
 * @see SamlIdpSpConnectionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionImpl"
)
@ProviderType
public interface SamlIdpSpConnection
	extends PersistedModel, SamlIdpSpConnectionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SamlIdpSpConnection, Long>
		SAML_IDP_SP_CONNECTION_ID_ACCESSOR =
			new Accessor<SamlIdpSpConnection, Long>() {

				@Override
				public Long get(SamlIdpSpConnection samlIdpSpConnection) {
					return samlIdpSpConnection.getSamlIdpSpConnectionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<SamlIdpSpConnection> getTypeClass() {
					return SamlIdpSpConnection.class;
				}

			};

}