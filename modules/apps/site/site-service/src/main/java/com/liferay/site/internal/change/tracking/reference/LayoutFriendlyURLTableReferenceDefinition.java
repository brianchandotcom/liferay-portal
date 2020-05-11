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
import com.liferay.portal.kernel.model.LayoutFriendlyURLTable;
import com.liferay.portal.kernel.model.LayoutSetTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.LayoutFriendlyURLPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class LayoutFriendlyURLTableReferenceDefinition
	implements TableReferenceDefinition<LayoutFriendlyURLTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<LayoutFriendlyURLTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			LayoutFriendlyURLTable.INSTANCE
		).nonreferenceColumns(
			LayoutFriendlyURLTable.INSTANCE.uuid
		).singleColumnReference(
			LayoutFriendlyURLTable.INSTANCE.plid, LayoutTable.INSTANCE.plid
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				LayoutSetTable.INSTANCE
			).innerJoinON(
				LayoutFriendlyURLTable.INSTANCE,
				LayoutFriendlyURLTable.INSTANCE.groupId.eq(
					LayoutSetTable.INSTANCE.groupId
				).and(
					LayoutFriendlyURLTable.INSTANCE.privateLayout.eq(
						LayoutSetTable.INSTANCE.privateLayout)
				)
			)
		).nonreferenceColumns(
			LayoutFriendlyURLTable.INSTANCE.friendlyURL,
			LayoutFriendlyURLTable.INSTANCE.languageId,
			LayoutFriendlyURLTable.INSTANCE.lastPublishDate
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _layoutFriendlyURLPersistence;
	}

	@Override
	public LayoutFriendlyURLTable getTable() {
		return LayoutFriendlyURLTable.INSTANCE;
	}

	@Reference
	private LayoutFriendlyURLPersistence _layoutFriendlyURLPersistence;

}