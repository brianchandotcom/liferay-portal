/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DepotEntryPin service. Represents a row in the &quot;DepotEntryPin&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryPinModel
 * @generated
 */
@ImplementationClassName("com.liferay.depot.model.impl.DepotEntryPinImpl")
@ProviderType
public interface DepotEntryPin extends DepotEntryPinModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.depot.model.impl.DepotEntryPinImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DepotEntryPin, Long>
		DEPOT_ENTRY_PIN_ID_ACCESSOR = new Accessor<DepotEntryPin, Long>() {

			@Override
			public Long get(DepotEntryPin depotEntryPin) {
				return depotEntryPin.getDepotEntryPinId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DepotEntryPin> getTypeClass() {
				return DepotEntryPin.class;
			}

		};

}
// LIFERAY-SERVICE-BUILDER-HASH:1566134702