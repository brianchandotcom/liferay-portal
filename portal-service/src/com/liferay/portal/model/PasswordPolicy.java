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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the PasswordPolicy service. Represents a row in the &quot;PasswordPolicy&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyModel
 * @see com.liferay.portal.model.impl.PasswordPolicyImpl
 * @see com.liferay.portal.model.impl.PasswordPolicyModelImpl
 * @generated
 */
@ProviderType
public interface PasswordPolicy extends PasswordPolicyModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link com.liferay.portal.model.impl.PasswordPolicyImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<PasswordPolicy, Long> PASSWORD_POLICY_ID_ACCESSOR =
		new Accessor<PasswordPolicy, Long>() {
			@Override
			public Long get(PasswordPolicy passwordPolicy) {
				return passwordPolicy.getPasswordPolicyId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<PasswordPolicy> getTypeClass() {
				return PasswordPolicy.class;
			}
		};
}