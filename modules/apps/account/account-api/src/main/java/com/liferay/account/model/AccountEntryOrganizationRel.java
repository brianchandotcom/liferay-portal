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

	public AccountEntry fetchAccountEntry();

	public com.liferay.portal.kernel.model.Organization fetchOrganization();

	public AccountEntry getAccountEntry()
		throws com.liferay.portal.kernel.exception.PortalException;

	public com.liferay.portal.kernel.model.Organization getOrganization()
		throws com.liferay.portal.kernel.exception.PortalException;

}