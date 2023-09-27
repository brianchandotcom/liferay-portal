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
 * The extended model interface for the OAuth2ApplicationScopeAliases service. Represents a row in the &quot;OAuth2ApplicationScopeAliases&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationScopeAliasesModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.oauth2.provider.model.impl.OAuth2ApplicationScopeAliasesImpl"
)
@ProviderType
public interface OAuth2ApplicationScopeAliases
	extends OAuth2ApplicationScopeAliasesModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.oauth2.provider.model.impl.OAuth2ApplicationScopeAliasesImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<OAuth2ApplicationScopeAliases, Long>
		O_AUTH2_APPLICATION_SCOPE_ALIASES_ID_ACCESSOR =
			new Accessor<OAuth2ApplicationScopeAliases, Long>() {

				@Override
				public Long get(
					OAuth2ApplicationScopeAliases
						oAuth2ApplicationScopeAliases) {

					return oAuth2ApplicationScopeAliases.
						getOAuth2ApplicationScopeAliasesId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<OAuth2ApplicationScopeAliases> getTypeClass() {
					return OAuth2ApplicationScopeAliases.class;
				}

			};

}