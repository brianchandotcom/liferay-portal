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

package com.liferay.oauth2.provider.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the OAuth2Application service. Represents a row in the &quot;OAuth2Application&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ApplicationModel
 * @see com.liferay.oauth2.provider.model.impl.OAuth2ApplicationImpl
 * @see com.liferay.oauth2.provider.model.impl.OAuth2ApplicationModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.oauth2.provider.model.impl.OAuth2ApplicationImpl")
@ProviderType
public interface OAuth2Application extends OAuth2ApplicationModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.oauth2.provider.model.impl.OAuth2ApplicationImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<OAuth2Application, Long> O_AUTH2_APPLICATION_ID_ACCESSOR =
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

	public java.util.List<com.liferay.oauth2.provider.constants.GrantType> getAllowedGrantTypesList();

	public java.util.List<java.lang.String> getFeaturesList();

	public java.util.List<java.lang.String> getRedirectURIsList();

	public void setAllowedGrantTypesList(
		java.util.List<com.liferay.oauth2.provider.constants.GrantType> allowedGrantTypesList);

	public void setFeaturesList(java.util.List<java.lang.String> featuresList);

	public void setRedirectURIsList(
		java.util.List<java.lang.String> redirectURIsList);
}