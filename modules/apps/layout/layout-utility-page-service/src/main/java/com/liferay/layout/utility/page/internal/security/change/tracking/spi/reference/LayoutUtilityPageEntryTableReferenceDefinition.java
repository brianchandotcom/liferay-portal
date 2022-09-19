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

package com.liferay.layout.utility.page.internal.security.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.layout.utility.page.model.LayoutUtilityPageEntryTable;
import com.liferay.layout.utility.page.service.persistence.LayoutUtilityPageEntryPersistence;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = TableReferenceDefinition.class)
public class LayoutUtilityPageEntryTableReferenceDefinition
	implements TableReferenceDefinition<LayoutUtilityPageEntryTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<LayoutUtilityPageEntryTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<LayoutUtilityPageEntryTable>
			parentTableReferenceInfoBuilder) {
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _layoutUtilityPageEntryPersistence;
	}

	@Override
	public LayoutUtilityPageEntryTable getTable() {
		return LayoutUtilityPageEntryTable.INSTANCE;
	}

	@Reference
	private LayoutUtilityPageEntryPersistence
		_layoutUtilityPageEntryPersistence;

}