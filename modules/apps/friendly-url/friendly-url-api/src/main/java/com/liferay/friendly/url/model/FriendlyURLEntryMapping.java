/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the FriendlyURLEntryMapping service. Represents a row in the &quot;FriendlyURLEntryMapping&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryMappingModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.friendly.url.model.impl.FriendlyURLEntryMappingImpl"
)
@ProviderType
public interface FriendlyURLEntryMapping extends FriendlyURLEntryMappingModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.friendly.url.model.impl.FriendlyURLEntryMappingImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<FriendlyURLEntryMapping, Long>
		FRIENDLY_URL_ENTRY_MAPPING_ID_ACCESSOR =
			new Accessor<FriendlyURLEntryMapping, Long>() {

				@Override
				public Long get(
					FriendlyURLEntryMapping friendlyURLEntryMapping) {

					return friendlyURLEntryMapping.
						getFriendlyURLEntryMappingId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<FriendlyURLEntryMapping> getTypeClass() {
					return FriendlyURLEntryMapping.class;
				}

			};

}