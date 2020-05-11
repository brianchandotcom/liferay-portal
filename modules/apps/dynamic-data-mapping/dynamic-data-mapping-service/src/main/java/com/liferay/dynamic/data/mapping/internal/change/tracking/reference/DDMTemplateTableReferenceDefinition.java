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

package com.liferay.dynamic.data.mapping.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateTable;
import com.liferay.dynamic.data.mapping.service.persistence.DDMTemplatePersistence;
import com.liferay.portal.kernel.model.ImageTable;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.model.UserTable;
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
	public void defineTableReferences(
		TableReferenceInfoBuilder<DDMTemplateTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			DDMTemplateTable.INSTANCE
		).nonreferenceColumn(
			DDMTemplateTable.INSTANCE.uuid
		).singleColumnReference(
			DDMTemplateTable.INSTANCE.versionUserId, UserTable.INSTANCE.userId
		).nonreferenceColumns(
			DDMTemplateTable.INSTANCE.versionUserName,
			DDMTemplateTable.INSTANCE.classNameId,
			DDMTemplateTable.INSTANCE.classPK,
			DDMTemplateTable.INSTANCE.resourceClassNameId,
			DDMTemplateTable.INSTANCE.templateKey,
			DDMTemplateTable.INSTANCE.version, DDMTemplateTable.INSTANCE.name,
			DDMTemplateTable.INSTANCE.description,
			DDMTemplateTable.INSTANCE.type, DDMTemplateTable.INSTANCE.mode,
			DDMTemplateTable.INSTANCE.language,
			DDMTemplateTable.INSTANCE.script,
			DDMTemplateTable.INSTANCE.cacheable
		).referenceInnerJoin(
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
		).nonreferenceColumns(
			DDMTemplateTable.INSTANCE.smallImageURL,
			DDMTemplateTable.INSTANCE.lastPublishDate
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