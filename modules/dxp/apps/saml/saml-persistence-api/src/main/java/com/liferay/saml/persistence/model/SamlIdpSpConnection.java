/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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