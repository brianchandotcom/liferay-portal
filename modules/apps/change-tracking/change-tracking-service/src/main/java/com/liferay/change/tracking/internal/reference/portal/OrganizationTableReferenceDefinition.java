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
import com.liferay.change.tracking.reference.helper.TableReferenceInfoBuilder;
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
		TableReferenceInfoBuilder<OrganizationTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.defineNonreferenceColumns(
			OrganizationTable.INSTANCE.uuid,
			OrganizationTable.INSTANCE.externalReferenceCode
		).defineReferenceInnerJoin(
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
		).defineSingleColumnReference(
			OrganizationTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).defineSingleColumnReference(
			OrganizationTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).defineNonreferenceColumns(
			OrganizationTable.INSTANCE.userName,
			OrganizationTable.INSTANCE.createDate,
			OrganizationTable.INSTANCE.modifiedDate
		).defineParentColumnReference(
			OrganizationTable.INSTANCE.organizationId,
			OrganizationTable.INSTANCE.parentOrganizationId
		).defineNonreferenceColumns(
			OrganizationTable.INSTANCE.treePath,
			OrganizationTable.INSTANCE.name, OrganizationTable.INSTANCE.type,
			OrganizationTable.INSTANCE.recursable
		).defineSingleColumnReference(
			OrganizationTable.INSTANCE.regionId, RegionTable.INSTANCE.regionId
		).defineSingleColumnReference(
			OrganizationTable.INSTANCE.countryId,
			CountryTable.INSTANCE.countryId
		).defineReferenceInnerJoin(
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
			)
		).defineNonreferenceColumn(
			OrganizationTable.INSTANCE.comments
		).defineSingleColumnReference(
			OrganizationTable.INSTANCE.logoId, ImageTable.INSTANCE.imageId
		).defineReferenceInnerJoin(
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
			)
		).defineReferenceInnerJoin(
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
			)
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