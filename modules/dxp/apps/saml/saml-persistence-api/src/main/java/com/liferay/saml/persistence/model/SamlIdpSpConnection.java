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
 * The extended model interface for the SamlIdpSpConnection service. Represents a row in the &quot;SamlIdpSpConnection&quot; database table, with each column mapped to a property of this class.
 *
 * @author Mika Koivisto
 * @see SamlIdpSpConnectionModel
 * @see com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionImpl
 * @see com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionImpl")
@ProviderType
public interface SamlIdpSpConnection extends SamlIdpSpConnectionModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SamlIdpSpConnection, Long> SAML_IDP_SP_CONNECTION_ID_ACCESSOR =
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