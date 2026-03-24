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
 * The extended model interface for the CookiesConsentPreference service. Represents a row in the &quot;CookiesConsentPreference&quot; database table, with each column mapped to a property of this class.
 *
 * @author Christopher Kian
 * @see CookiesConsentPreferenceModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.cookies.model.impl.CookiesConsentPreferenceImpl"
)
@ProviderType
public interface CookiesConsentPreference
	extends CookiesConsentPreferenceModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.cookies.model.impl.CookiesConsentPreferenceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CookiesConsentPreference, Long>
		COOKIES_CONSENT_PREFERENCE_ID_ACCESSOR =
			new Accessor<CookiesConsentPreference, Long>() {

				@Override
				public Long get(
					CookiesConsentPreference cookiesConsentPreference) {

					return cookiesConsentPreference.
						getCookiesConsentPreferenceId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CookiesConsentPreference> getTypeClass() {
					return CookiesConsentPreference.class;
				}

			};

}
// LIFERAY-SERVICE-BUILDER-HASH:-518655669