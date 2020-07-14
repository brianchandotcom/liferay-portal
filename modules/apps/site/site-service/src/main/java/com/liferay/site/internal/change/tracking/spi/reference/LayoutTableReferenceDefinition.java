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
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.ImageTable;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutFriendlyURLTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.LayoutPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class LayoutTableReferenceDefinition
	implements TableReferenceDefinition<LayoutTable> {

	@Override
	public void defineChildTableReferences(
		ChildTableReferenceInfoBuilder<LayoutTable>
			childTableReferenceInfoBuilder) {

		childTableReferenceInfoBuilder.singleColumnReference(
			LayoutTable.INSTANCE.iconImageId, ImageTable.INSTANCE.imageId
		).singleColumnReference(
			LayoutTable.INSTANCE.plid, LayoutFriendlyURLTable.INSTANCE.plid
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				GroupTable.INSTANCE
			).innerJoinON(
				LayoutTable.INSTANCE,
				LayoutTable.INSTANCE.companyId.eq(
					GroupTable.INSTANCE.companyId
				).and(
					LayoutTable.INSTANCE.plid.eq(GroupTable.INSTANCE.classPK)
				)
			).innerJoinON(
				ClassNameTable.INSTANCE,
				ClassNameTable.INSTANCE.value.eq(
					Layout.class.getName()
				).and(
					ClassNameTable.INSTANCE.classNameId.eq(
						GroupTable.INSTANCE.classNameId)
				)
			)
		).referenceInnerJoin(
			fromStep -> fromStep.from(
				ResourcePermissionTable.INSTANCE
			).innerJoinON(
				LayoutTable.INSTANCE,
				LayoutTable.INSTANCE.companyId.eq(
					ResourcePermissionTable.INSTANCE.companyId
				).and(
					ResourcePermissionTable.INSTANCE.primKey.like(
						DSLFunctionFactoryUtil.concat(
							DSLFunctionFactoryUtil.castText(
								LayoutTable.INSTANCE.plid),
							new Scalar<>(
								PortletConstants.LAYOUT_SEPARATOR + "%")))
				)
			)
		).assetEntryReference(
			LayoutTable.INSTANCE.plid, Layout.class
		).resourcePermissionReference(
			LayoutTable.INSTANCE.plid, Layout.class
		).systemEventReference(
			LayoutTable.INSTANCE.plid, Layout.class
		);
	}

	@Override
	public void defineParentTableReferences(
		ParentTableReferenceInfoBuilder<LayoutTable>
			parentTableReferenceInfoBuilder) {

		parentTableReferenceInfoBuilder.groupedModel(
			LayoutTable.INSTANCE
		).parentColumnReference(
			LayoutTable.INSTANCE.plid, LayoutTable.INSTANCE.parentPlid
		).parentColumnReference(
			LayoutTable.INSTANCE.plid, LayoutTable.INSTANCE.masterLayoutPlid
		).referenceInnerJoin(
			fromStep -> {
				LayoutTable aliasLayoutTable = LayoutTable.INSTANCE.as(
					"aliasLayoutTable");

				return fromStep.from(
					aliasLayoutTable
				).innerJoinON(
					LayoutTable.INSTANCE,
					LayoutTable.INSTANCE.parentLayoutId.eq(
						aliasLayoutTable.layoutId)
				);
			}
		).referenceInnerJoin(
			fromStep -> {
				LayoutTable aliasLayoutTable = LayoutTable.INSTANCE.as(
					"aliasLayoutTable");

				return fromStep.from(
					aliasLayoutTable
				).innerJoinON(
					LayoutTable.INSTANCE,
					LayoutTable.INSTANCE.classPK.eq(aliasLayoutTable.plid)
				).innerJoinON(
					ClassNameTable.INSTANCE,
					ClassNameTable.INSTANCE.value.eq(
						Layout.class.getName()
					).and(
						LayoutTable.INSTANCE.classNameId.eq(
							ClassNameTable.INSTANCE.classNameId)
					)
				);
			}
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _layoutPersistence;
	}

	@Override
	public LayoutTable getTable() {
		return LayoutTable.INSTANCE;
	}

	@Reference
	private LayoutPersistence _layoutPersistence;

}