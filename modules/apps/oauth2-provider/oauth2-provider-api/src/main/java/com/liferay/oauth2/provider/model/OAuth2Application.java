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
 * The extended model interface for the OAuth2Application service. Represents a row in the &quot;OAuth2Application&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.oauth2.provider.model.impl.OAuth2ApplicationImpl"
)
@ProviderType
public interface OAuth2Application
	extends OAuth2ApplicationModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.oauth2.provider.model.impl.OAuth2ApplicationImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<OAuth2Application, Long>
		O_AUTH2_APPLICATION_ID_ACCESSOR =
			new Accessor<OAuth2Application, Long>() {

				@Override
				public Long get(OAuth2Application oAuth2Application) {
					return oAuth2Application.getOAuth2ApplicationId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<OAuth2Application> getTypeClass() {
					return OAuth2Application.class;
				}

			};

	public java.util.List<com.liferay.oauth2.provider.constants.GrantType>
		getAllowedGrantTypesList();

	public java.util.List<String> getFeaturesList();

	public java.util.List<String> getRedirectURIsList();

	public void setAllowedGrantTypesList(
		java.util.List<com.liferay.oauth2.provider.constants.GrantType>
			allowedGrantTypesList);

	public void setFeaturesList(java.util.List<String> featuresList);

	public void setRedirectURIsList(java.util.List<String> redirectURIsList);

}