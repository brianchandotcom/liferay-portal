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

package com.liferay.site.navigation.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.model.SiteNavigationMenuTable;
import com.liferay.site.navigation.service.persistence.SiteNavigationMenuPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class SiteNavigationMenuTableReferenceDefinition
	implements TableReferenceDefinition<SiteNavigationMenuTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<SiteNavigationMenuTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.resourcePermissionReference(
			SiteNavigationMenuTable.INSTANCE.siteNavigationMenuId,
			SiteNavigationMenu.class);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<SiteNavigationMenuTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			SiteNavigationMenuTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _siteNavigationMenuPersistence;
	}

	@Override
	public SiteNavigationMenuTable getTable() {
		return SiteNavigationMenuTable.INSTANCE;
	}

	@Reference
	private SiteNavigationMenuPersistence _siteNavigationMenuPersistence;

}