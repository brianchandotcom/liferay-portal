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

package com.liferay.oauth.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the OAuthApplication service. Represents a row in the &quot;OAuth_OAuthApplication&quot; database table, with each column mapped to a property of this class.
 *
 * @author Ivica Cardic
 * @see OAuthApplicationModel
 * @see com.liferay.oauth.model.impl.OAuthApplicationImpl
 * @see com.liferay.oauth.model.impl.OAuthApplicationModelImpl
 * @generated
 */
@ImplementationClassName("com.liferay.oauth.model.impl.OAuthApplicationImpl")
@ProviderType
public interface OAuthApplication extends OAuthApplicationModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.oauth.model.impl.OAuthApplicationImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<OAuthApplication, Long> O_AUTH_APPLICATION_ID_ACCESSOR =
		new Accessor<OAuthApplication, Long>() {
			@Override
			public Long get(OAuthApplication oAuthApplication) {
				return oAuthApplication.getOAuthApplicationId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<OAuthApplication> getTypeClass() {
				return OAuthApplication.class;
			}
		};

	public String getAccessLevelLabel();
}