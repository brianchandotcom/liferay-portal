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

package com.liferay.site.internal.change.tracking.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.LayoutFriendlyURLTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.LayoutFriendlyURLPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class LayoutFriendlyURLTableReferenceDefinition
	implements TableReferenceDefinition<LayoutFriendlyURLTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<LayoutFriendlyURLTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<LayoutFriendlyURLTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			LayoutFriendlyURLTable.INSTANCE
		).singleColumnReference(
			LayoutFriendlyURLTable.INSTANCE.plid, LayoutTable.INSTANCE.plid
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _layoutFriendlyURLPersistence;
	}

	@Override
	public LayoutFriendlyURLTable getTable() {
		return LayoutFriendlyURLTable.INSTANCE;
	}

	@Reference
	private LayoutFriendlyURLPersistence _layoutFriendlyURLPersistence;

}