/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the PermissionPropagationEntry service. Represents a row in the &quot;PermissionPropagationEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see PermissionPropagationEntryModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portal.model.impl.PermissionPropagationEntryImpl"
)
@ProviderType
public interface PermissionPropagationEntry
	extends PermissionPropagationEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portal.model.impl.PermissionPropagationEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<PermissionPropagationEntry, Long>
		PERMISSION_PROPAGATION_ENTRY_ID_ACCESSOR =
			new Accessor<PermissionPropagationEntry, Long>() {

				@Override
				public Long get(
					PermissionPropagationEntry permissionPropagationEntry) {

					return permissionPropagationEntry.
						getPermissionPropagationEntryId();
				}

				@Override
				public Class<Long> getAttributeClass() {
					return Long.class;
				}

				@Override
				public Class<PermissionPropagationEntry> getTypeClass() {
					return PermissionPropagationEntry.class;
				}

			};

}