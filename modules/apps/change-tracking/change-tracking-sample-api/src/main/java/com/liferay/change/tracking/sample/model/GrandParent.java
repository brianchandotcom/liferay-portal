/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the GrandParent service. Represents a row in the &quot;GrandParent&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see GrandParentModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.change.tracking.sample.model.impl.GrandParentImpl"
)
@ProviderType
public interface GrandParent extends GrandParentModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.change.tracking.sample.model.impl.GrandParentImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<GrandParent, Long> GRAND_PARENT_ID_ACCESSOR =
		new Accessor<GrandParent, Long>() {

			@Override
			public Long get(GrandParent grandParent) {
				return grandParent.getGrandParentId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<GrandParent> getTypeClass() {
				return GrandParent.class;
			}

		};

}