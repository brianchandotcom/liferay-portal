/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AccountRole service. Represents a row in the &quot;AccountRole&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AccountRoleModel
 * @generated
 */
@ImplementationClassName("com.liferay.account.model.impl.AccountRoleImpl")
@ProviderType
public interface AccountRole extends AccountRoleModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.account.model.impl.AccountRoleImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AccountRole, Long> ACCOUNT_ROLE_ID_ACCESSOR =
		new Accessor<AccountRole, Long>() {

			@Override
			public Long get(AccountRole accountRole) {
				return accountRole.getAccountRoleId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AccountRole> getTypeClass() {
				return AccountRole.class;
			}

		};

	public com.liferay.portal.kernel.model.Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException;

	public String getRoleName()
		throws com.liferay.portal.kernel.exception.PortalException;

}