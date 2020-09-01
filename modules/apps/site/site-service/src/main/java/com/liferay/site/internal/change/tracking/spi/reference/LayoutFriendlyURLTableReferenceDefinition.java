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

package com.liferay.site.internal.change.tracking.spi.reference;

import com.liferay.change.tracking.spi.reference.TableReferenceDefinition;
import com.liferay.change.tracking.spi.reference.builder.ChildTableReferenceInfoBuilder;
import com.liferay.change.tracking.spi.reference.builder.ParentTableReferenceInfoBuilder;
import com.liferay.friendly.url.model.FriendlyURLEntryTable;
import com.liferay.layout.friendly.url.LayoutFriendlyURLEntryHelper;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.LayoutFriendlyURLTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.LayoutFriendlyURLPersistence;

import java.util.function.Function;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class LayoutFriendlyURLTableReferenceDefinition
	implements TableReferenceDefinition<LayoutFriendlyURLTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<LayoutFriendlyURLTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.referenceInnerJoin(
			_getFriendlyURLEntryJoinFunction(false)
		).referenceInnerJoin(
			_getFriendlyURLEntryJoinFunction(true)
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<LayoutFriendlyURLTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			LayoutFriendlyURLTable.INSTANCE);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _layoutFriendlyURLPersistence;
	}

	@Override
	public LayoutFriendlyURLTable getTable() {
		return LayoutFriendlyURLTable.INSTANCE;
	}

	private Function<FromStep, JoinStep> _getFriendlyURLEntryJoinFunction(
		boolean privateLayout) {

		return fromStep -> fromStep.from(
			FriendlyURLEntryTable.INSTANCE
		).innerJoinON(
			LayoutFriendlyURLTable.INSTANCE,
			LayoutFriendlyURLTable.INSTANCE.groupId.eq(
				FriendlyURLEntryTable.INSTANCE.groupId
			).and(
				LayoutFriendlyURLTable.INSTANCE.plid.eq(
					FriendlyURLEntryTable.INSTANCE.classPK)
			)
		).innerJoinON(
			ClassNameTable.INSTANCE,
			ClassNameTable.INSTANCE.classNameId.eq(
				FriendlyURLEntryTable.INSTANCE.classNameId
			).and(
				ClassNameTable.INSTANCE.value.eq(
					_layoutFriendlyURLEntryHelper.getClassName(privateLayout))
			).and(
				LayoutFriendlyURLTable.INSTANCE.privateLayout.eq(privateLayout)
			)
		);
	}

	@Reference
	private LayoutFriendlyURLEntryHelper _layoutFriendlyURLEntryHelper;

	@Reference
	private LayoutFriendlyURLPersistence _layoutFriendlyURLPersistence;

}