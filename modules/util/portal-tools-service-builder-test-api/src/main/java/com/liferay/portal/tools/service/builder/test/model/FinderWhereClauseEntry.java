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
 * The extended model interface for the FinderWhereClauseEntry service. Represents a row in the &quot;FinderWhereClauseEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see FinderWhereClauseEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.model.impl.FinderWhereClauseEntryImpl"
)
@ProviderType
public interface FinderWhereClauseEntry
	extends FinderWhereClauseEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.model.impl.FinderWhereClauseEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<FinderWhereClauseEntry, Long>
		FINDER_WHERE_CLAUSE_ENTRY_ID_ACCESSOR =
			new Accessor<FinderWhereClauseEntry, Long>() {

				@Override
				public Long get(FinderWhereClauseEntry finderWhereClauseEntry) {
					return finderWhereClauseEntry.getFinderWhereClauseEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<FinderWhereClauseEntry> getTypeClass() {
					return FinderWhereClauseEntry.class;
				}

			};

}