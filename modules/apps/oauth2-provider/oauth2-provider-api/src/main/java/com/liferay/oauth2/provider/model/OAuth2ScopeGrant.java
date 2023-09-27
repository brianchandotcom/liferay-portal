/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the OAuth2ScopeGrant service. Represents a row in the &quot;OAuth2ScopeGrant&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ScopeGrantModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.oauth2.provider.model.impl.OAuth2ScopeGrantImpl"
)
@ProviderType
public interface OAuth2ScopeGrant
	extends OAuth2ScopeGrantModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.oauth2.provider.model.impl.OAuth2ScopeGrantImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<OAuth2ScopeGrant, Long>
		O_AUTH2_SCOPE_GRANT_ID_ACCESSOR =
			new Accessor<OAuth2ScopeGrant, Long>() {

				@Override
				public Long get(OAuth2ScopeGrant oAuth2ScopeGrant) {
					return oAuth2ScopeGrant.getOAuth2ScopeGrantId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<OAuth2ScopeGrant> getTypeClass() {
					return OAuth2ScopeGrant.class;
				}

			};

	public java.util.List<String> getScopeAliasesList();

	public void setScopeAliasesList(java.util.List<String> scopeAliasesList);

}