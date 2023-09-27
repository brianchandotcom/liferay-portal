/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.PortletPreferencesTable;
import com.liferay.portal.kernel.model.PortletTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.PortletPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class PortletTableReferenceDefinition
	implements TableReferenceDefinition<PortletTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<PortletTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				PortletPreferencesTable.INSTANCE
			).innerJoinON(
				PortletTable.INSTANCE,
				PortletTable.INSTANCE.companyId.eq(
					PortletPreferencesTable.INSTANCE.companyId
				).and(
					PortletTable.INSTANCE.portletId.eq(
						PortletPreferencesTable.INSTANCE.portletId)
				)
			));
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<PortletTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			PortletTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _portletPersistence;
	}

	@Override
	public PortletTable getTable() {
		return PortletTable.INSTANCE;
	}

	@Reference
	private PortletPersistence _portletPersistence;

}