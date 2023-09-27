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
 * The extended model interface for the AccountEntryUserRel service. Represents a row in the &quot;AccountEntryUserRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryUserRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.account.model.impl.AccountEntryUserRelImpl"
)
@ProviderType
public interface AccountEntryUserRel
	extends AccountEntryUserRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.account.model.impl.AccountEntryUserRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AccountEntryUserRel, Long>
		ACCOUNT_ENTRY_USER_REL_ID_ACCESSOR =
			new Accessor<AccountEntryUserRel, Long>() {

				@Override
				public Long get(AccountEntryUserRel accountEntryUserRel) {
					return accountEntryUserRel.getAccountEntryUserRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<AccountEntryUserRel> getTypeClass() {
					return AccountEntryUserRel.class;
				}

			};

	public AccountEntry fetchAccountEntry();

	public com.liferay.portal.kernel.model.User fetchUser();

	public AccountEntry getAccountEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.model.User getUser()
		throws com.liferay.portal.kernel.exception.PortalException;

	public java.util.List<com.liferay.portal.kernel.model.UserGroupRole>
			getUserGroupRoles()
		throws com.liferay.portal.kernel.exception.PortalException;

}