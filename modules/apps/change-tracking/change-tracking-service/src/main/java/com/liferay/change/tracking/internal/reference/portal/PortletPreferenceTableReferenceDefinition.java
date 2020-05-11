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
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.model.PortletPreferencesTable;
import com.liferay.portal.kernel.model.PortletTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.PortletPreferencesPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class PortletPreferenceTableReferenceDefinition
	implements TableReferenceDefinition<PortletPreferencesTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<PortletPreferencesTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.singleColumnReference(
			PortletPreferencesTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).nonreferenceColumns(
			PortletPreferencesTable.INSTANCE.ownerId,
			PortletPreferencesTable.INSTANCE.ownerType
		).singleColumnReference(
			PortletPreferencesTable.INSTANCE.plid, LayoutTable.INSTANCE.plid
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				PortletTable.INSTANCE
			).innerJoinON(
				PortletPreferencesTable.INSTANCE,
				PortletPreferencesTable.INSTANCE.companyId.eq(
					PortletTable.INSTANCE.companyId
				).and(
					PortletPreferencesTable.INSTANCE.portletId.eq(
						PortletTable.INSTANCE.portletId)
				)
			)
		).nonreferenceColumn(
			PortletPreferencesTable.INSTANCE.preferences
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _portletPreferencesPersistence;
	}

	@Override
	public PortletPreferencesTable getTable() {
		return PortletPreferencesTable.INSTANCE;
	}

	@Reference
	private PortletPreferencesPersistence _portletPreferencesPersistence;

}