/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		).referenceInnerJoin(
			fromStep -> {
				LayoutTable aliasLayoutTable = LayoutTable.INSTANCE.as(
					"aliasLayoutTable");

				return fromStep.from(
					aliasLayoutTable
				).innerJoinON(
					LayoutTable.INSTANCE,
					LayoutTable.INSTANCE.plid.eq(aliasLayoutTable.classPK)
				).innerJoinON(
					ClassNameTable.INSTANCE,
					ClassNameTable.INSTANCE.value.eq(
						Layout.class.getName()
					).and(
						aliasLayoutTable.classNameId.eq(
							ClassNameTable.INSTANCE.classNameId)
					)
				);
			}
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
						aliasLayoutTable.layoutId
					).and(
						LayoutTable.INSTANCE.groupId.eq(
							aliasLayoutTable.groupId)
					).and(
						LayoutTable.INSTANCE.privateLayout.eq(
							aliasLayoutTable.privateLayout)
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