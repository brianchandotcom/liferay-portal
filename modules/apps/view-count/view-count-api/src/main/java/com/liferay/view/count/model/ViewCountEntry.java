/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.view.count.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the ViewCountEntry service. Represents a row in the &quot;ViewCountEntry&quot; database table, with each column mapped to a property of this class.
 *
 * @author Preston Crary
 * @see ViewCountEntryModel
 * @generated
 */
@ImplementationClassName("com.liferay.view.count.model.impl.ViewCountEntryImpl")
@ProviderType
public interface ViewCountEntry extends PersistedModel, ViewCountEntryModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.view.count.model.impl.ViewCountEntryImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ViewCountEntry, Long> COMPANY_ID_ACCESSOR =
		new Accessor<ViewCountEntry, Long>() {

			@Override
			public Long get(ViewCountEntry viewCountEntry) {
				return viewCountEntry.getCompanyId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ViewCountEntry> getTypeClass() {
				return ViewCountEntry.class;
			}

		};
	public static final Accessor<ViewCountEntry, Long> CLASS_NAME_ID_ACCESSOR =
		new Accessor<ViewCountEntry, Long>() {

			@Override
			public Long get(ViewCountEntry viewCountEntry) {
				return viewCountEntry.getClassNameId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ViewCountEntry> getTypeClass() {
				return ViewCountEntry.class;
			}

		};
	public static final Accessor<ViewCountEntry, Long> CLASS_PK_ACCESSOR =
		new Accessor<ViewCountEntry, Long>() {

			@Override
			public Long get(ViewCountEntry viewCountEntry) {
				return viewCountEntry.getClassPK();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ViewCountEntry> getTypeClass() {
				return ViewCountEntry.class;
			}

		};

}