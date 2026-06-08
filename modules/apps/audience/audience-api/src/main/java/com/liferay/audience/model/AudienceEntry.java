/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the AudienceEntry service. Represents a row in the &quot;AudienceEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see AudienceEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.audience.model.impl.AudienceEntryImpl")
@ProviderType
public interface AudienceEntry extends AudienceEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.audience.model.impl.AudienceEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<AudienceEntry, Long>
		AUDIENCE_ENTRY_ID_ACCESSOR = new Accessor<AudienceEntry, Long>() {

			@Override
			public Long get(AudienceEntry audienceEntry) {
				return audienceEntry.getAudienceEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<AudienceEntry> getTypeClass() {
				return AudienceEntry.class;
			}

		};

}
// LIFERAY-SERVICE-BUILDER-HASH:-1390815400