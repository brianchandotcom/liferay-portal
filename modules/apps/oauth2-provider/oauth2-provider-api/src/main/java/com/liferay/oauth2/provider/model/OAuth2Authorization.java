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
 * The extended model interface for the OAuth2Authorization service. Represents a row in the &quot;OAuth2Authorization&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AuthorizationModel
 * @see com.liferay.oauth2.provider.model.impl.OAuth2AuthorizationImpl
 * @see com.liferay.oauth2.provider.model.impl.OAuth2AuthorizationModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.oauth2.provider.model.impl.OAuth2AuthorizationImpl")
@ProviderType
public interface OAuth2Authorization extends OAuth2AuthorizationModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.oauth2.provider.model.impl.OAuth2AuthorizationImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<OAuth2Authorization, Long> O_AUTH2_AUTHORIZATION_ID_ACCESSOR =
		new Accessor<OAuth2Authorization, Long>() {
			@Override
			public Long get(OAuth2Authorization oAuth2Authorization) {
				return oAuth2Authorization.getOAuth2AuthorizationId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<OAuth2Authorization> getTypeClass() {
				return OAuth2Authorization.class;
			}
		};
}