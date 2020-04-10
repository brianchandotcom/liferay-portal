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
import com.liferay.change.tracking.reference.helper.TableReferenceDefinitionHelper;
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
		TableReferenceDefinitionHelper<LayoutSetTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				GroupTable.INSTANCE
			).innerJoinON(
				LayoutSetTable.INSTANCE,
				LayoutSetTable.INSTANCE.groupId.eq(GroupTable.INSTANCE.groupId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				LayoutSetTable.INSTANCE,
				LayoutSetTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutSetTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutSetTable.INSTANCE.modifiedDate);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
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
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				ImageTable.INSTANCE
			).innerJoinON(
				LayoutSetTable.INSTANCE,
				LayoutSetTable.INSTANCE.logoId.eq(ImageTable.INSTANCE.imageId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutSetTable.INSTANCE.themeId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutSetTable.INSTANCE.colorSchemeId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutSetTable.INSTANCE.css);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutSetTable.INSTANCE.settings);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutSetTable.INSTANCE.layoutSetPrototypeUuid);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutSetTable.INSTANCE.layoutSetPrototypeLinkEnabled);
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