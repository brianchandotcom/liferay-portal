/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the FriendlyURLEntryLocalization service. Represents a row in the &quot;FriendlyURLEntryLocalization&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryLocalizationModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.friendly.url.model.impl.FriendlyURLEntryLocalizationImpl"
)
@ProviderType
public interface FriendlyURLEntryLocalization
	extends FriendlyURLEntryLocalizationModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.friendly.url.model.impl.FriendlyURLEntryLocalizationImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<FriendlyURLEntryLocalization, Long>
		FRIENDLY_URL_ENTRY_LOCALIZATION_ID_ACCESSOR =
			new Accessor<FriendlyURLEntryLocalization, Long>() {

				@Override
				public Long get(
					FriendlyURLEntryLocalization friendlyURLEntryLocalization) {

					return friendlyURLEntryLocalization.
						getFriendlyURLEntryLocalizationId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<FriendlyURLEntryLocalization> getTypeClass() {
					return FriendlyURLEntryLocalization.class;
				}

			};

}