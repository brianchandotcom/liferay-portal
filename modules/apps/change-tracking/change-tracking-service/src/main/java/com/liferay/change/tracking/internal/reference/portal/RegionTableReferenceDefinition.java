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
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CountryTable;
import com.liferay.portal.kernel.model.RegionTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.RegionPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class RegionTableReferenceDefinition
	implements TableReferenceDefinition<RegionTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<RegionTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.singleColumnReference(
			RegionTable.INSTANCE.countryId, CountryTable.INSTANCE.countryId
		).nonreferenceColumns(
			RegionTable.INSTANCE.regionCode, RegionTable.INSTANCE.name,
			RegionTable.INSTANCE.active
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _regionPersistence;
	}

	@Override
	public RegionTable getTable() {
		return RegionTable.INSTANCE;
	}

	@Reference
	private RegionPersistence _regionPersistence;

}