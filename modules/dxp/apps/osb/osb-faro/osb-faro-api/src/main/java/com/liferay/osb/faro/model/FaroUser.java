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

package com.liferay.osb.faro.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the FaroUser service. Represents a row in the &quot;OSBFaro_FaroUser&quot; database table, with each column mapped to a property of this class.
 *
 * @author Matthew Kong
 * @see FaroUserModel
 * @generated
 */
@ImplementationClassName("com.liferay.osb.faro.model.impl.FaroUserImpl")
@ProviderType
public interface FaroUser extends FaroUserModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.osb.faro.model.impl.FaroUserImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<FaroUser, Long> FARO_USER_ID_ACCESSOR =
		new Accessor<FaroUser, Long>() {

			@Override
			public Long get(FaroUser faroUser) {
				return faroUser.getFaroUserId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<FaroUser> getTypeClass() {
				return FaroUser.class;
			}

		};

}