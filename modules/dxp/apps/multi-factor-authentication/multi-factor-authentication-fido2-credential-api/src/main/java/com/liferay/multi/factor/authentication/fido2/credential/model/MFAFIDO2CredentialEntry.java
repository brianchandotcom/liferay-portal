/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.fido2.credential.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the MFAFIDO2CredentialEntry service. Represents a row in the &quot;MFAFIDO2CredentialEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Arthur Chan
 * @see MFAFIDO2CredentialEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.multi.factor.authentication.fido2.credential.model.impl.MFAFIDO2CredentialEntryImpl"
)
@ProviderType
public interface MFAFIDO2CredentialEntry
	extends MFAFIDO2CredentialEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.multi.factor.authentication.fido2.credential.model.impl.MFAFIDO2CredentialEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MFAFIDO2CredentialEntry, Long>
		MFA_FIDO2_CREDENTIAL_ENTRY_ID_ACCESSOR =
			new Accessor<MFAFIDO2CredentialEntry, Long>() {

				@Override
				public Long get(
					MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

					return mfaFIDO2CredentialEntry.
						getMfaFIDO2CredentialEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<MFAFIDO2CredentialEntry> getTypeClass() {
					return MFAFIDO2CredentialEntry.class;
				}

			};

}