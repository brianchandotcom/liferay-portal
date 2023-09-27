/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CacheFieldEntry service. Represents a row in the &quot;CacheFieldEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see CacheFieldEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.model.impl.CacheFieldEntryImpl"
)
@ProviderType
public interface CacheFieldEntry extends CacheFieldEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.model.impl.CacheFieldEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CacheFieldEntry, Long>
		CACHE_FIELD_ENTRY_ID_ACCESSOR = new Accessor<CacheFieldEntry, Long>() {

			@Override
			public Long get(CacheFieldEntry cacheFieldEntry) {
				return cacheFieldEntry.getCacheFieldEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CacheFieldEntry> getTypeClass() {
				return CacheFieldEntry.class;
			}

		};

	public String getNickname();

	public void setNickname(String nickname);

}