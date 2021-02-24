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
import com.liferay.site.navigation.model.SiteNavigationMenuItemTable;
import com.liferay.site.navigation.model.SiteNavigationMenuTable;
import com.liferay.site.navigation.service.persistence.SiteNavigationMenuItemPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = TableReferenceDefinition.class)
public class SiteNavigationMenuItemTableReferenceDefinition
	implements TableReferenceDefinition<SiteNavigationMenuItemTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<SiteNavigationMenuItemTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<SiteNavigationMenuItemTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			SiteNavigationMenuItemTable.INSTANCE
		).parentColumnReference(
			SiteNavigationMenuItemTable.INSTANCE.siteNavigationMenuItemId,
			SiteNavigationMenuItemTable.INSTANCE.parentSiteNavigationMenuItemId
		).singleColumnReference(
			SiteNavigationMenuItemTable.INSTANCE.siteNavigationMenuId,
			SiteNavigationMenuTable.INSTANCE.siteNavigationMenuId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _siteNavigationMenuItemPersistence;
	}

	@Override
	public SiteNavigationMenuItemTable getTable() {
		return SiteNavigationMenuItemTable.INSTANCE;
	}

	@Reference
	private SiteNavigationMenuItemPersistence
		_siteNavigationMenuItemPersistence;

}