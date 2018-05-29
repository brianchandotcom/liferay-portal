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

package com.liferay.saml.persistence.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the SamlIdpSsoSession service. Represents a row in the &quot;SamlIdpSsoSession&quot; database table, with each column mapped to a property of this class.
 *
 * @author Mika Koivisto
 * @see SamlIdpSsoSessionModel
 * @see com.liferay.saml.persistence.model.impl.SamlIdpSsoSessionImpl
 * @see com.liferay.saml.persistence.model.impl.SamlIdpSsoSessionModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.saml.persistence.model.impl.SamlIdpSsoSessionImpl")
@ProviderType
public interface SamlIdpSsoSession extends SamlIdpSsoSessionModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.saml.persistence.model.impl.SamlIdpSsoSessionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SamlIdpSsoSession, Long> SAML_IDP_SSO_SESSION_ID_ACCESSOR =
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