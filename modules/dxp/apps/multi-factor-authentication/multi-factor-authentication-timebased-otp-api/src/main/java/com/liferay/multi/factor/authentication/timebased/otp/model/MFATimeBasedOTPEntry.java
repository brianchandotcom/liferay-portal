/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.timebased.otp.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the MFATimeBasedOTPEntry service. Represents a row in the &quot;MFATimeBasedOTPEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Arthur Chan
 * @see MFATimeBasedOTPEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.multi.factor.authentication.timebased.otp.model.impl.MFATimeBasedOTPEntryImpl"
)
@ProviderType
public interface MFATimeBasedOTPEntry
	extends MFATimeBasedOTPEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.multi.factor.authentication.timebased.otp.model.impl.MFATimeBasedOTPEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<MFATimeBasedOTPEntry, Long>
		MFA_TIME_BASED_OTP_ENTRY_ID_ACCESSOR =
			new Accessor<MFATimeBasedOTPEntry, Long>() {

				@Override
				public Long get(MFATimeBasedOTPEntry mfaTimeBasedOTPEntry) {
					return mfaTimeBasedOTPEntry.getMfaTimeBasedOTPEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<MFATimeBasedOTPEntry> getTypeClass() {
					return MFATimeBasedOTPEntry.class;
				}

			};

}