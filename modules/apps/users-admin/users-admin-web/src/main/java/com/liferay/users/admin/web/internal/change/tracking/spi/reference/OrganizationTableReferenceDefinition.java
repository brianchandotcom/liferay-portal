/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.web.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.AddressTable;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.CountryTable;
import com.liferay.portal.kernel.model.EmailAddressTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.ImageTable;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.ListTypeTable;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationTable;
import com.liferay.portal.kernel.model.PhoneTable;
import com.liferay.portal.kernel.model.RegionTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.OrganizationPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class OrganizationTableReferenceDefinition
	implements TableReferenceDefinition<OrganizationTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<OrganizationTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				AddressTable.INSTANCE
			).innerJoinON(
				OrganizationTable.INSTANCE,
				OrganizationTable.INSTANCE.organizationId.eq(
					AddressTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					AddressTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(
						Organization.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				EmailAddressTable.INSTANCE
			).innerJoinON(
				OrganizationTable.INSTANCE,
				OrganizationTable.INSTANCE.organizationId.eq(
					EmailAddressTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					EmailAddressTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(
						Organization.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				GroupTable.INSTANCE
			).innerJoinON(
				OrganizationTable.INSTANCE,
				OrganizationTable.INSTANCE.organizationId.eq(
					GroupTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					GroupTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(
						Organization.class.getName())
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				ListTypeTable.INSTANCE
			).innerJoinON(
				OrganizationTable.INSTANCE,
				OrganizationTable.INSTANCE.statusListTypeId.eq(
					ListTypeTable.INSTANCE.listTypeId
				).and(
					ListTypeTable.INSTANCE.type.eq(
						ListTypeConstants.ORGANIZATION_STATUS)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				PhoneTable.INSTANCE
			).innerJoinON(
				OrganizationTable.INSTANCE,
				OrganizationTable.INSTANCE.organizationId.eq(
					PhoneTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					PhoneTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(
						Organization.class.getName())
				)
			)
		).singleColumnReference(
			OrganizationTable.INSTANCE.logoId, ImageTable.INSTANCE.imageId
		).assetEntryReference(
			OrganizationTable.INSTANCE.organizationId, Organization.class
		).resourcePermissionReference(
			OrganizationTable.INSTANCE.organizationId, Organization.class
		).systemEventReference(
			OrganizationTable.INSTANCE.organizationId, Organization.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<OrganizationTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.singleColumnReference(
			OrganizationTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).parentColumnReference(
			OrganizationTable.INSTANCE.organizationId,
			OrganizationTable.INSTANCE.parentOrganizationId
		).singleColumnReference(
			OrganizationTable.INSTANCE.regionId, RegionTable.INSTANCE.regionId
		).singleColumnReference(
			OrganizationTable.INSTANCE.countryId,
			CountryTable.INSTANCE.countryId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _organizationPersistence;
	}

	@Override
	public OrganizationTable getTable() {
		return OrganizationTable.INSTANCE;
	}

	@Reference
	private OrganizationPersistence _organizationPersistence;

}