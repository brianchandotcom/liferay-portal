/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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