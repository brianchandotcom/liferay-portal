/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.email.otp.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the MFAEmailOTPEntry service. Represents a row in the &quot;MFAEmailOTPEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Arthur Chan
 * @see MFAEmailOTPEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.multi.factor.authentication.email.otp.model.impl.MFAEmailOTPEntryImpl"
)
@ProviderType
public interface MFAEmailOTPEntry
	extends MFAEmailOTPEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.multi.factor.authentication.email.otp.model.impl.MFAEmailOTPEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MFAEmailOTPEntry, Long>
		MFA_EMAIL_OTP_ENTRY_ID_ACCESSOR =
			new Accessor<MFAEmailOTPEntry, Long>() {

				@Override
				public Long get(MFAEmailOTPEntry mfaEmailOTPEntry) {
					return mfaEmailOTPEntry.getMfaEmailOTPEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<MFAEmailOTPEntry> getTypeClass() {
					return MFAEmailOTPEntry.class;
				}

			};

}