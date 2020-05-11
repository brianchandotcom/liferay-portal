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
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.ImageTable;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.model.UserTable;
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
	public void defineTableReferences(
		TableReferenceInfoBuilder<LayoutTable> tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.groupedModel(
			LayoutTable.INSTANCE
		).nonreferenceColumn(
			LayoutTable.INSTANCE.uuid
		).parentColumnReference(
			LayoutTable.INSTANCE.plid, LayoutTable.INSTANCE.parentPlid
		).nonreferenceColumn(
			LayoutTable.INSTANCE.privateLayout
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
		).nonreferenceColumns(
			LayoutTable.INSTANCE.name, LayoutTable.INSTANCE.title,
			LayoutTable.INSTANCE.description, LayoutTable.INSTANCE.keywords,
			LayoutTable.INSTANCE.robots, LayoutTable.INSTANCE.type,
			LayoutTable.INSTANCE.typeSettings, LayoutTable.INSTANCE.hidden,
			LayoutTable.INSTANCE.system, LayoutTable.INSTANCE.friendlyURL
		).singleColumnReference(
			LayoutTable.INSTANCE.iconImageId, ImageTable.INSTANCE.imageId
		).nonreferenceColumns(
			LayoutTable.INSTANCE.themeId, LayoutTable.INSTANCE.colorSchemeId,
			LayoutTable.INSTANCE.css, LayoutTable.INSTANCE.priority
		).parentColumnReference(
			LayoutTable.INSTANCE.plid, LayoutTable.INSTANCE.masterLayoutPlid
		).nonreferenceColumns(
			LayoutTable.INSTANCE.layoutPrototypeUuid,
			LayoutTable.INSTANCE.layoutPrototypeLinkEnabled,
			LayoutTable.INSTANCE.sourcePrototypeLayoutUuid,
			LayoutTable.INSTANCE.publishDate,
			LayoutTable.INSTANCE.lastPublishDate, LayoutTable.INSTANCE.status
		).singleColumnReference(
			LayoutTable.INSTANCE.statusByUserId, UserTable.INSTANCE.userId
		).nonreferenceColumns(
			LayoutTable.INSTANCE.statusByUserName,
			LayoutTable.INSTANCE.statusDate
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