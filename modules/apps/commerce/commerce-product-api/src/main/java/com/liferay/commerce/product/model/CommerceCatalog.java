/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CommerceCatalog service. Represents a row in the &quot;CommerceCatalog&quot; database table, with each column mapped to a property of this class.
 *
 * @author Marco Leo
 * @see CommerceCatalogModel
 * @generated
 */
@ImplementationClassName(
	"com.liferay.commerce.product.model.impl.CommerceCatalogImpl"
)
@ProviderType
public interface CommerceCatalog extends CommerceCatalogModel, PersistedModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.liferay.commerce.product.model.impl.CommerceCatalogImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CommerceCatalog, Long>
		COMMERCE_CATALOG_ID_ACCESSOR = new Accessor<CommerceCatalog, Long>() {

			@Override
			public Long get(CommerceCatalog commerceCatalog) {
				return commerceCatalog.getCommerceCatalogId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CommerceCatalog> getTypeClass() {
				return CommerceCatalog.class;
			}

		};

	public com.liferay.portal.kernel.model.Group getGroup();

	public long getGroupId();

}