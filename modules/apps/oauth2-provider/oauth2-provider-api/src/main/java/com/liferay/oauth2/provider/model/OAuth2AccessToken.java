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
 * The extended model interface for the OAuth2AccessToken service. Represents a row in the &quot;OAuth2AccessToken&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AccessTokenModel
 * @see com.liferay.oauth2.provider.model.impl.OAuth2AccessTokenImpl
 * @see com.liferay.oauth2.provider.model.impl.OAuth2AccessTokenModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.oauth2.provider.model.impl.OAuth2AccessTokenImpl")
@ProviderType
public interface OAuth2AccessToken extends OAuth2AccessTokenModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.oauth2.provider.model.impl.OAuth2AccessTokenImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<OAuth2AccessToken, Long> O_AUTH2_ACCESS_TOKEN_ID_ACCESSOR =
		new Accessor<OAuth2AccessToken, Long>() {
			@Override
			public Long get(OAuth2AccessToken oAuth2AccessToken) {
				return oAuth2AccessToken.getOAuth2AccessTokenId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<OAuth2AccessToken> getTypeClass() {
				return OAuth2AccessToken.class;
			}
		};
}