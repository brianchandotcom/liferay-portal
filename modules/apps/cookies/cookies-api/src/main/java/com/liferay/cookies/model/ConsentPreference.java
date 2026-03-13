/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ConsentPreference service. Represents a row in the &quot;ConsentPreference&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ConsentPreferenceModel
 * @generated
 */
@ImplementationClassName("com.liferay.cookies.model.impl.ConsentPreferenceImpl")
@ProviderType
public interface ConsentPreference
	extends ConsentPreferenceModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.cookies.model.impl.ConsentPreferenceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ConsentPreference, Long>
		CONSENT_PREFERENCE_ID_ACCESSOR =
			new Accessor<ConsentPreference, Long>() {

				@Override
				public Long get(ConsentPreference consentPreference) {
					return consentPreference.getConsentPreferenceId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<ConsentPreference> getTypeClass() {
					return ConsentPreference.class;
				}

			};

}