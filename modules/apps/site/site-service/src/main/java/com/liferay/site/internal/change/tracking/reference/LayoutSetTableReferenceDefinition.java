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

package com.liferay.site.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.ImageTable;
import com.liferay.portal.kernel.model.LayoutSetTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.LayoutSetPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class LayoutSetTableReferenceDefinition
	implements TableReferenceDefinition<LayoutSetTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<LayoutSetTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.singleColumnReference(
			LayoutSetTable.INSTANCE.groupId, GroupTable.INSTANCE.groupId
		).singleColumnReference(
			LayoutSetTable.INSTANCE.companyId, CompanyTable.INSTANCE.companyId
		).nonreferenceColumns(
			LayoutSetTable.INSTANCE.createDate,
			LayoutSetTable.INSTANCE.modifiedDate
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				LayoutTable.INSTANCE
			).innerJoinON(
				LayoutSetTable.INSTANCE,
				LayoutSetTable.INSTANCE.groupId.eq(
					LayoutTable.INSTANCE.groupId
				).and(
					LayoutSetTable.INSTANCE.privateLayout.eq(
						LayoutTable.INSTANCE.privateLayout)
				)
			)
		).singleColumnReference(
			LayoutSetTable.INSTANCE.logoId, ImageTable.INSTANCE.imageId
		).nonreferenceColumns(
			LayoutSetTable.INSTANCE.themeId,
			LayoutSetTable.INSTANCE.colorSchemeId, LayoutSetTable.INSTANCE.css,
			LayoutSetTable.INSTANCE.settings,
			LayoutSetTable.INSTANCE.layoutSetPrototypeUuid,
			LayoutSetTable.INSTANCE.layoutSetPrototypeLinkEnabled
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _layoutSetPersistence;
	}

	@Override
	public LayoutSetTable getTable() {
		return LayoutSetTable.INSTANCE;
	}

	@Reference
	private LayoutSetPersistence _layoutSetPersistence;

}