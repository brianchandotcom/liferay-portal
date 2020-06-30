/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

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