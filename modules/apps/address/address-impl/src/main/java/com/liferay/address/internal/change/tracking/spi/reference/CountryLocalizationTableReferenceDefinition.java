/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CountryLocalizationTable;
import com.liferay.portal.kernel.model.CountryTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.CountryLocalizationPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brooke Dalton
 */
@Component(service = TableReferenceDefinition.class)
public class CountryLocalizationTableReferenceDefinition
	implements TableReferenceDefinition<CountryLocalizationTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<CountryLocalizationTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<CountryLocalizationTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			CountryLocalizationTable.INSTANCE.countryId,
			CountryTable.INSTANCE.countryId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _countryLocalizationPersistence;
	}

	@Override
	public CountryLocalizationTable getTable() {
		return CountryLocalizationTable.INSTANCE;
	}

	@Reference
	private CountryLocalizationPersistence _countryLocalizationPersistence;

}