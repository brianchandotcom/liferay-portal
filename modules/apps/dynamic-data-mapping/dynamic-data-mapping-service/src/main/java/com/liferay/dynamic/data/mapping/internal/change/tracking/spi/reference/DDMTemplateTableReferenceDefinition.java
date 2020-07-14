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

package com.liferay.dynamic.data.mapping.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.dynamic.data.mapping.model.DDMStructureTable;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMTemplatePersistence;
import com.liferay.portal.kernel.model.ImageTable;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class DDMTemplateTableReferenceDefinition
	implements TableReferenceDefinition<DDMTemplateTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<DDMTemplateTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			fromStep -> fromStep.from(
				ImageTable.INSTANCE
			).innerJoinON(
				DDMTemplateTable.INSTANCE,
				DDMTemplateTable.INSTANCE.smallImageId.eq(
					ImageTable.INSTANCE.imageId
				).and(
					DDMTemplateTable.INSTANCE.smallImage.eq(Boolean.TRUE)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				ResourcePermissionTable.INSTANCE
			).innerJoinON(
				DDMTemplateTable.INSTANCE,
				DDMTemplateTable.INSTANCE.companyId.eq(
					ResourcePermissionTable.INSTANCE.companyId
				).and(
					ResourcePermissionTable.INSTANCE.name.like(
						"%" + DDMTemplate.class.getName())
				).and(
					ResourcePermissionTable.INSTANCE.scope.eq(
						ResourceConstants.SCOPE_INDIVIDUAL)
				).and(
					DDMTemplateTable.INSTANCE.templateId.eq(
						ResourcePermissionTable.INSTANCE.primKeyId)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				ResourcePermissionTable.INSTANCE
			).innerJoinON(
				DDMTemplateTable.INSTANCE,
				DDMTemplateTable.INSTANCE.companyId.eq(
					ResourcePermissionTable.INSTANCE.companyId
				).and(
					ResourcePermissionTable.INSTANCE.name.like(
						DDMTemplate.class.getName() + "%")
				).and(
					ResourcePermissionTable.INSTANCE.scope.eq(
						ResourceConstants.SCOPE_INDIVIDUAL)
				).and(
					DDMTemplateTable.INSTANCE.templateId.eq(
						ResourcePermissionTable.INSTANCE.primKeyId)
				)
			)
		).systemEventReference(
			DDMTemplateTable.INSTANCE.templateId, DDMTemplate.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<DDMTemplateTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			DDMTemplateTable.INSTANCE
		).singleColumnReference(
			DDMTemplateTable.INSTANCE.classPK,
			DDMStructureTable.INSTANCE.structureId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _ddmTemplatePersistence;
	}

	@Override
	public DDMTemplateTable getTable() {
		return DDMTemplateTable.INSTANCE;
	}

	@Reference
	private DDMTemplatePersistence _ddmTemplatePersistence;

}