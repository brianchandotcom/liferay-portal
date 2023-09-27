/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.OrganizationTable;
import com.liferay.portal.kernel.model.PortalPreferencesTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.PortalPreferencesPersistence;
import com.liferay.portal.kernel.util.PortletKeys;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class PortalPreferencesTableReferenceDefinition
	implements TableReferenceDefinition<PortalPreferencesTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<PortalPreferencesTable>
			childTableReferenceInfoBuilder) {
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<PortalPreferencesTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				PortalPreferencesTable.INSTANCE,
				PortalPreferencesTable.INSTANCE.ownerId.eq(
					CompanyTable.INSTANCE.companyId
				).and(
					PortalPreferencesTable.INSTANCE.ownerType.eq(
						PortletKeys.PREFS_OWNER_TYPE_COMPANY)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				OrganizationTable.INSTANCE
			).innerJoinON(
				PortalPreferencesTable.INSTANCE,
				PortalPreferencesTable.INSTANCE.ownerId.eq(
					OrganizationTable.INSTANCE.organizationId
				).and(
					PortalPreferencesTable.INSTANCE.ownerType.eq(
						PortletKeys.PREFS_OWNER_TYPE_ORGANIZATION)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				PortalPreferencesTable.INSTANCE,
				PortalPreferencesTable.INSTANCE.ownerId.eq(
					UserTable.INSTANCE.userId
				).and(
					PortalPreferencesTable.INSTANCE.ownerType.eq(
						PortletKeys.PREFS_OWNER_TYPE_USER)
				)
			)
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _portalPreferencesPersistence;
	}

	@Override
	public PortalPreferencesTable getTable() {
		return PortalPreferencesTable.INSTANCE;
	}

	@Reference
	private PortalPreferencesPersistence _portalPreferencesPersistence;

}