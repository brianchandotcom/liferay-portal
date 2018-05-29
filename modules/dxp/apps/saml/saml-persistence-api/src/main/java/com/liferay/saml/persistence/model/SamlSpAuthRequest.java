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
 * The extended model interface for the SamlSpAuthRequest service. Represents a row in the &quot;SamlSpAuthRequest&quot; database table, with each column mapped to a property of this class.
 *
 * @author Mika Koivisto
 * @see SamlSpAuthRequestModel
 * @see com.liferay.saml.persistence.model.impl.SamlSpAuthRequestImpl
 * @see com.liferay.saml.persistence.model.impl.SamlSpAuthRequestModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.saml.persistence.model.impl.SamlSpAuthRequestImpl")
@ProviderType
public interface SamlSpAuthRequest extends SamlSpAuthRequestModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.saml.persistence.model.impl.SamlSpAuthRequestImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SamlSpAuthRequest, Long> SAML_SP_AUTHN_REQUEST_ID_ACCESSOR =
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