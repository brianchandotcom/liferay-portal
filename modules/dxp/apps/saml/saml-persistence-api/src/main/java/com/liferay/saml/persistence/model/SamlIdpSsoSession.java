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
 * The extended model interface for the SamlIdpSsoSession service. Represents a row in the &quot;SamlIdpSsoSession&quot; database table, with each column mapped to a property of this class.
 *
 * @author Mika Koivisto
 * @see SamlIdpSsoSessionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.saml.persistence.model.impl.SamlIdpSsoSessionImpl"
)
@ProviderType
public interface SamlIdpSsoSession
	extends PersistedModel, SamlIdpSsoSessionModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.saml.persistence.model.impl.SamlIdpSsoSessionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SamlIdpSsoSession, Long>
		SAML_IDP_SSO_SESSION_ID_ACCESSOR =
			new Accessor<SamlIdpSsoSession, Long>() {

				@Override
				public Long get(SamlIdpSsoSession samlIdpSsoSession) {
					return samlIdpSsoSession.getSamlIdpSsoSessionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<SamlIdpSsoSession> getTypeClass() {
					return SamlIdpSsoSession.class;
				}

			};

	public boolean isExpired();

}