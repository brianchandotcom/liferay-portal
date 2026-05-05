/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CacheReplicatorEntry service. Represents a row in the &quot;CacheReplicatorEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see CacheReplicatorEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.model.impl.CacheReplicatorEntryImpl"
)
@ProviderType
public interface CacheReplicatorEntry
	extends CacheReplicatorEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheReplicatorEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CacheReplicatorEntry, Long>
		CACHE_REPLICATOR_ENTRY_ID_ACCESSOR =
			new Accessor<CacheReplicatorEntry, Long>() {

				@Override
				public Long get(CacheReplicatorEntry cacheReplicatorEntry) {
					return cacheReplicatorEntry.getCacheReplicatorEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<CacheReplicatorEntry> getTypeClass() {
					return CacheReplicatorEntry.class;
				}

			};

}
// LIFERAY-SERVICE-BUILDER-HASH:-1763733800