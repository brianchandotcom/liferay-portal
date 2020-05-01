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

import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.helper.TableReferenceInfoDefiner;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.CountryTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.ImageTable;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.ListTypeTable;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationTable;
import com.liferay.portal.kernel.model.RegionTable;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.model.UserTable;
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
	public void defineTableReferences(
		TableReferenceInfoDefiner<OrganizationTable>
			tableReferenceInfoDefiner) {

		tableReferenceInfoDefiner.defineNonreferenceColumn(
			OrganizationTable.INSTANCE.uuid);

		tableReferenceInfoDefiner.defineNonreferenceColumn(
			OrganizationTable.INSTANCE.externalReferenceCode);

		tableReferenceInfoDefiner.defineReferenceInnerJoin(
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
			));

		tableReferenceInfoDefiner.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				OrganizationTable.INSTANCE,
				OrganizationTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceInfoDefiner.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				OrganizationTable.INSTANCE,
				OrganizationTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			));

		tableReferenceInfoDefiner.defineNonreferenceColumn(
			OrganizationTable.INSTANCE.userName);

		tableReferenceInfoDefiner.defineNonreferenceColumn(
			OrganizationTable.INSTANCE.createDate);

		tableReferenceInfoDefiner.defineNonreferenceColumn(
			OrganizationTable.INSTANCE.modifiedDate);

		tableReferenceInfoDefiner.defineReferenceInnerJoin(
			fromStep -> {
				OrganizationTable aliasOrganizationTable =
					OrganizationTable.INSTANCE.as("aliasOrganizationTable");

				return fromStep.from(
					aliasOrganizationTable
				).innerJoinON(
					OrganizationTable.INSTANCE,
					OrganizationTable.INSTANCE.parentOrganizationId.eq(
						aliasOrganizationTable.organizationId)
				);
			});

		tableReferenceInfoDefiner.defineNonreferenceColumn(
			OrganizationTable.INSTANCE.treePath);

		tableReferenceInfoDefiner.defineNonreferenceColumn(
			OrganizationTable.INSTANCE.name);

		tableReferenceInfoDefiner.defineNonreferenceColumn(
			OrganizationTable.INSTANCE.type);

		tableReferenceInfoDefiner.defineNonreferenceColumn(
			OrganizationTable.INSTANCE.recursable);

		tableReferenceInfoDefiner.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				RegionTable.INSTANCE
			).innerJoinON(
				OrganizationTable.INSTANCE,
				OrganizationTable.INSTANCE.regionId.eq(
					RegionTable.INSTANCE.regionId)
			));

		tableReferenceInfoDefiner.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CountryTable.INSTANCE
			).innerJoinON(
				OrganizationTable.INSTANCE,
				OrganizationTable.INSTANCE.countryId.eq(
					CountryTable.INSTANCE.countryId)
			));

		tableReferenceInfoDefiner.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				ListTypeTable.INSTANCE
			).innerJoinON(
				OrganizationTable.INSTANCE,
				OrganizationTable.INSTANCE.statusId.eq(
					ListTypeTable.INSTANCE.listTypeId
				).and(
					ListTypeTable.INSTANCE.type.eq(
						ListTypeConstants.ORGANIZATION_STATUS)
				)
			));

		tableReferenceInfoDefiner.defineNonreferenceColumn(
			OrganizationTable.INSTANCE.comments);

		tableReferenceInfoDefiner.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				ImageTable.INSTANCE
			).innerJoinON(
				OrganizationTable.INSTANCE,
				OrganizationTable.INSTANCE.logoId.eq(
					ImageTable.INSTANCE.imageId)
			));

		tableReferenceInfoDefiner.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				ResourcePermissionTable.INSTANCE
			).innerJoinON(
				OrganizationTable.INSTANCE,
				OrganizationTable.INSTANCE.companyId.eq(
					ResourcePermissionTable.INSTANCE.companyId
				).and(
					ResourcePermissionTable.INSTANCE.name.eq(
						Organization.class.getName())
				).and(
					ResourcePermissionTable.INSTANCE.primKeyId.eq(
						OrganizationTable.INSTANCE.organizationId)
				)
			));

		tableReferenceInfoDefiner.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				AssetEntryTable.INSTANCE
			).innerJoinON(
				OrganizationTable.INSTANCE,
				OrganizationTable.INSTANCE.organizationId.eq(
					AssetEntryTable.INSTANCE.classPK)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.classNameId.eq(
					AssetEntryTable.INSTANCE.classNameId
				).and(
					ClassNameTable.INSTANCE.value.eq(
						Organization.class.getName())
				)
			));
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