/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.NestedSetsTreeNodeModel;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the TreeEntry service. Represents a row in the &quot;TreeEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see TreeEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.tools.service.builder.test.compat700.model.impl.TreeEntryImpl"
)
@ProviderType
public interface TreeEntry
	extends NestedSetsTreeNodeModel, PersistedModel, TreeEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.tools.service.builder.test.compat700.model.impl.TreeEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<TreeEntry, Long> TREE_ENTRY_ID_ACCESSOR =
		new Accessor<TreeEntry, Long>() {

			@Override
			public Long get(TreeEntry treeEntry) {
				return treeEntry.getTreeEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<TreeEntry> getTypeClass() {
				return TreeEntry.class;
			}

		};

}
// LIFERAY-SERVICE-BUILDER-HASH:1802157880