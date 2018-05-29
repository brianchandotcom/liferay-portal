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
 * The extended model interface for the SamlSpIdpConnection service. Represents a row in the &quot;SamlSpIdpConnection&quot; database table, with each column mapped to a property of this class.
 *
 * @author Mika Koivisto
 * @see SamlSpIdpConnectionModel
 * @see com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionImpl
 * @see com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionImpl")
@ProviderType
public interface SamlSpIdpConnection extends SamlSpIdpConnectionModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SamlSpIdpConnection, Long> SAML_SP_IDP_CONNECTION_ID_ACCESSOR =
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
}