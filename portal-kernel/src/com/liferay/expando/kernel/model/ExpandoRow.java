/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ExpandoRow service. Represents a row in the &quot;ExpandoRow&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoRowModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.expando.model.impl.ExpandoRowImpl"
)
@ProviderType
public interface ExpandoRow extends ExpandoRowModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.expando.model.impl.ExpandoRowImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ExpandoRow, Long> ROW_ID_ACCESSOR =
		new Accessor<ExpandoRow, Long>() {

			@Override
			public Long get(ExpandoRow expandoRow) {
				return expandoRow.getRowId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ExpandoRow> getTypeClass() {
				return ExpandoRow.class;
			}

		};

}