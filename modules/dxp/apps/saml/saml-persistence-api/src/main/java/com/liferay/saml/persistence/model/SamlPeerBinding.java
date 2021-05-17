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

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the SamlPeerBinding service. Represents a row in the &quot;SamlPeerBinding&quot; database table, with each column mapped to a property of this class.
 *
 * @author Mika Koivisto
 * @see SamlPeerBindingModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.saml.persistence.model.impl.SamlPeerBindingImpl"
)
@ProviderType
public interface SamlPeerBinding extends PersistedModel, SamlPeerBindingModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.saml.persistence.model.impl.SamlPeerBindingImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<SamlPeerBinding, Long>
		SAML_PEER_BINDING_ID_ACCESSOR = new Accessor<SamlPeerBinding, Long>() {

			@Override
			public Long get(SamlPeerBinding samlPeerBinding) {
				return samlPeerBinding.getSamlPeerBindingId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<SamlPeerBinding> getTypeClass() {
				return SamlPeerBinding.class;
			}

		};

}