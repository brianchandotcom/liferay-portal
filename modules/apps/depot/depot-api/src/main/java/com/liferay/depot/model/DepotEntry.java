/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DepotEntry service. Represents a row in the &quot;DepotEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.depot.model.impl.DepotEntryImpl")
@ProviderType
public interface DepotEntry extends DepotEntryModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.depot.model.impl.DepotEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DepotEntry, Long> DEPOT_ENTRY_ID_ACCESSOR =
		new Accessor<DepotEntry, Long>() {

			@Override
			public Long get(DepotEntry depotEntry) {
				return depotEntry.getDepotEntryId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DepotEntry> getTypeClass() {
				return DepotEntry.class;
			}

		};

	public com.liferay.portal.kernel.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException;

}