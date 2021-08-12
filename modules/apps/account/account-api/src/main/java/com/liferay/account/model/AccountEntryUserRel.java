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

}