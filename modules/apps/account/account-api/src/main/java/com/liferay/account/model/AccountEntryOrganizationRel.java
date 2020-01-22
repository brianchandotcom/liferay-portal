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
 * The extended model interface for the AccountEntryOrganizationRel service. Represents a row in the &quot;AccountEntryOrganizationRel&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryOrganizationRelModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.account.model.impl.AccountEntryOrganizationRelImpl"
)
@ProviderType
public interface AccountEntryOrganizationRel
	extends AccountEntryOrganizationRelModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.account.model.impl.AccountEntryOrganizationRelImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AccountEntryOrganizationRel, Long>
		ACCOUNT_ENTRY_ORGANIZATION_REL_ID_ACCESSOR =
			new Accessor<AccountEntryOrganizationRel, Long>() {

				@Override
				public Long get(
					AccountEntryOrganizationRel accountEntryOrganizationRel) {

					return accountEntryOrganizationRel.
						getAccountEntryOrganizationRelId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<AccountEntryOrganizationRel> getTypeClass() {
					return AccountEntryOrganizationRel.class;
				}

			};

}