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
 * The extended model interface for the SamlSpAuthRequest service. Represents a row in the &quot;SamlSpAuthRequest&quot; database table, with each column mapped to a property of this class.
 *
 * @author Mika Koivisto
 * @see SamlSpAuthRequestModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.saml.persistence.model.impl.SamlSpAuthRequestImpl"
)
@ProviderType
public interface SamlSpAuthRequest
	extends PersistedModel, SamlSpAuthRequestModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.saml.persistence.model.impl.SamlSpAuthRequestImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SamlSpAuthRequest, Long>
		SAML_SP_AUTHN_REQUEST_ID_ACCESSOR =
			new Accessor<SamlSpAuthRequest, Long>() {

				@Override
				public Long get(SamlSpAuthRequest samlSpAuthRequest) {
					return samlSpAuthRequest.getSamlSpAuthnRequestId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<SamlSpAuthRequest> getTypeClass() {
					return SamlSpAuthRequest.class;
				}

			};

}