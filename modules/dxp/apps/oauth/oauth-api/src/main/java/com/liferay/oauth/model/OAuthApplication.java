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