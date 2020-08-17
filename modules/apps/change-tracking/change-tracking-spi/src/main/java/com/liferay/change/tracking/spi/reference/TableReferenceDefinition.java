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

package com.liferay.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * Describes parent and child relationships for a given table using joins.
 * Implementations are required for complete CT integration of a service builder
 * service that has a base persistence.
 *
 * @author Preston Crary
 */
public interface TableReferenceDefinition<T extends Table<T>> {

	/**
	 * Define child rows using inner joins on the table. A child row is defined
	 * as a row required by the parent table to function correctly. Typically,
	 * children have their parent's primary keys contained in one of child's
	 * columns. AssetEntry and ResourcePermission are common children of many
	 * tables.
	 *
	 * @param childTableReferenceInfoBuilder used to define child relationships
	 *        for this TableReferenceDefinition
	 */
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<T> childTableReferenceInfoBuilder);

	/**
	 * Define parent rows using inner joins on the table. A parent row is
	 * defined as a row that when deleted triggers it's children to be deleted.
	 * Typically, children have their parent's primary keys contained in one of
	 * child's columns. Company and Group are common parents of many tables.
	 *
	 * @param parentTableReferenceInfoBuilder used to define parent
	 *        relationships for this TableReferenceDefinition
	 */
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<T> parentTableReferenceInfoBuilder);

	/**
	 * Returns the base persistence for the table described by this table
	 * reference definition.
	 *
	 * @return the base persistence for this table reference definition
	 */
	public BasePersistence<?> getBasePersistence();

	/**
	 * Returns the table being described by this table reference definition.
	 *
	 * @return the table
	 */
	public T getTable();

}