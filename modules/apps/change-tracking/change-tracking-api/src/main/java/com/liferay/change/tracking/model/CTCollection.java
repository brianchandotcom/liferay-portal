/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CTCollection service. Represents a row in the &quot;CTCollection&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see CTCollectionModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.change.tracking.model.impl.CTCollectionImpl"
)
@ProviderType
public interface CTCollection extends CTCollectionModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.change.tracking.model.impl.CTCollectionImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CTCollection, Long> CT_COLLECTION_ID_ACCESSOR =
		new Accessor<CTCollection, Long>() {

			@Override
			public Long get(CTCollection ctCollection) {
				return ctCollection.getCtCollectionId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CTCollection> getTypeClass() {
				return CTCollection.class;
			}

		};

	public String getUserName();

	public boolean isProduction();

}