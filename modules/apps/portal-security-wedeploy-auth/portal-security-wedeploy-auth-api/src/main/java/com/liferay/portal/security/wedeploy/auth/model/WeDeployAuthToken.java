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

package com.liferay.portal.security.wedeploy.auth.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the WeDeployAuthToken service. Represents a row in the &quot;WeDeployAuth_WeDeployAuthToken&quot; database table, with each column mapped to a property of this class.
 *
 * @author Supritha Sundaram
 * @see WeDeployAuthTokenModel
 * @see com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthTokenImpl
 * @see com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthTokenModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthTokenImpl")
@ProviderType
public interface WeDeployAuthToken extends WeDeployAuthTokenModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.security.wedeploy.auth.model.impl.WeDeployAuthTokenImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<WeDeployAuthToken, Long> WE_DEPLOY_AUTH_TOKEN_ID_ACCESSOR =
		new Accessor<WeDeployAuthToken, Long>() {
			@Override
			public Long get(WeDeployAuthToken weDeployAuthToken) {
				return weDeployAuthToken.getWeDeployAuthTokenId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<WeDeployAuthToken> getTypeClass() {
				return WeDeployAuthToken.class;
			}
		};
}