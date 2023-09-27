/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the DEDataListView service. Represents a row in the &quot;DEDataListView&quot; database table, with each column mapped to a property of this class.
 *
 * @author Brian Wing Shun Chan
 * @see DEDataListViewModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.data.engine.model.impl.DEDataListViewImpl"
)
@ProviderType
public interface DEDataListView extends DEDataListViewModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.data.engine.model.impl.DEDataListViewImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<DEDataListView, Long>
		DE_DATA_LIST_VIEW_ID_ACCESSOR = new Accessor<DEDataListView, Long>() {

			@Override
			public Long get(DEDataListView deDataListView) {
				return deDataListView.getDeDataListViewId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<DEDataListView> getTypeClass() {
				return DEDataListView.class;
			}

		};

}