/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.persistence.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the OpenIdConnectSession service. Represents a row in the &quot;OpenIdConnectSession&quot; database table, with each column mapped to a property of this class.
 *
 * @author Arthur Chan
 * @see OpenIdConnectSessionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.security.sso.openid.connect.persistence.model.impl.OpenIdConnectSessionImpl"
)
@ProviderType
public interface OpenIdConnectSession
	extends OpenIdConnectSessionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.security.sso.openid.connect.persistence.model.impl.OpenIdConnectSessionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<OpenIdConnectSession, Long>
		OPEN_ID_CONNECT_SESSION_ID_ACCESSOR =
			new Accessor<OpenIdConnectSession, Long>() {

				@Override
				public Long get(OpenIdConnectSession openIdConnectSession) {
					return openIdConnectSession.getOpenIdConnectSessionId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<OpenIdConnectSession> getTypeClass() {
					return OpenIdConnectSession.class;
				}

			};

}