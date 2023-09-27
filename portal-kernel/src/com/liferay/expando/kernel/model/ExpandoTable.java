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
 * The extended model interface for the ExpandoTable service. Represents a row in the &quot;ExpandoTable&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoTableModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.portlet.expando.model.impl.ExpandoTableImpl"
)
@ProviderType
public interface ExpandoTable extends ExpandoTableModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.portlet.expando.model.impl.ExpandoTableImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ExpandoTable, Long> TABLE_ID_ACCESSOR =
		new Accessor<ExpandoTable, Long>() {

			@Override
			public Long get(ExpandoTable expandoTable) {
				return expandoTable.getTableId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ExpandoTable> getTypeClass() {
				return ExpandoTable.class;
			}

		};

	public boolean isDefaultTable();

}