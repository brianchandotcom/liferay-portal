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

package com.liferay.change.tracking.internal.reference.portal;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
import com.liferay.portal.kernel.model.CountryTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.CountryPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class CountryTableReferenceDefinition
	implements TableReferenceDefinition<CountryTable> {

	@Override
	public void defineTableReferences(
		TableReferenceDefinitionHelper<CountryTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			CountryTable.INSTANCE.name);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			CountryTable.INSTANCE.a2);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			CountryTable.INSTANCE.a3);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			CountryTable.INSTANCE.number);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			CountryTable.INSTANCE.idd);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			CountryTable.INSTANCE.zipRequired);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			CountryTable.INSTANCE.active);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _countryPersistence;
	}

	@Override
	public CountryTable getTable() {
		return CountryTable.INSTANCE;
	}

	@Reference
	private CountryPersistence _countryPersistence;

}