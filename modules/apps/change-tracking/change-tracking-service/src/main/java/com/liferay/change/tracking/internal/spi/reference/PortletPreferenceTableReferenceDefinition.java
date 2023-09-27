/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.model.PortletPreferenceValueTable;
import com.liferay.portal.kernel.model.PortletPreferencesTable;
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
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<PortletPreferencesTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			PortletPreferencesTable.INSTANCE.portletPreferencesId,
			PortletPreferenceValueTable.INSTANCE.portletPreferencesId);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<PortletPreferencesTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			PortletPreferencesTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			PortletPreferencesTable.INSTANCE.plid, LayoutTable.INSTANCE.plid
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