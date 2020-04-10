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
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.portal.kernel.model.ClassNameTable;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.ImageTable;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
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
		TableReferenceDefinitionHelper<LayoutTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.uuid);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				GroupTable.INSTANCE
			).innerJoinON(
				LayoutTable.INSTANCE,
				LayoutTable.INSTANCE.groupId.eq(GroupTable.INSTANCE.groupId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				LayoutTable.INSTANCE,
				LayoutTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				LayoutTable.INSTANCE,
				LayoutTable.INSTANCE.userId.eq(UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.userName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.modifiedDate);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> {
				LayoutTable aliasLayoutTable = LayoutTable.INSTANCE.as(
					"aliasLayoutTable");

				return fromStep.from(
					aliasLayoutTable
				).innerJoinON(
					LayoutTable.INSTANCE,
					LayoutTable.INSTANCE.parentPlid.eq(aliasLayoutTable.plid)
				);
			});

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.privateLayout);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
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
			});

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
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
			});

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.name);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.title);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.description);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.keywords);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.robots);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.type);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.typeSettings);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.hidden);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.system);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.friendlyURL);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				ImageTable.INSTANCE
			).innerJoinON(
				LayoutTable.INSTANCE,
				LayoutTable.INSTANCE.iconImageId.eq(ImageTable.INSTANCE.imageId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.themeId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.colorSchemeId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.css);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.priority);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> {
				LayoutTable aliasLayoutTable = LayoutTable.INSTANCE.as(
					"aliasLayoutTable");

				return fromStep.from(
					aliasLayoutTable
				).innerJoinON(
					LayoutTable.INSTANCE,
					LayoutTable.INSTANCE.masterLayoutPlid.eq(
						aliasLayoutTable.plid)
				);
			});

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.layoutPrototypeUuid);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.layoutPrototypeLinkEnabled);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.sourcePrototypeLayoutUuid);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.publishDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.lastPublishDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.status);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				LayoutTable.INSTANCE,
				LayoutTable.INSTANCE.statusByUserId.eq(
					UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.statusByUserName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutTable.INSTANCE.statusDate);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
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
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
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
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				ResourcePermissionTable.INSTANCE
			).innerJoinON(
				LayoutTable.INSTANCE,
				LayoutTable.INSTANCE.companyId.eq(
					ResourcePermissionTable.INSTANCE.companyId
				).and(
					ResourcePermissionTable.INSTANCE.name.eq(
						Layout.class.getName())
				).and(
					ResourcePermissionTable.INSTANCE.scope.eq(
						ResourceConstants.SCOPE_INDIVIDUAL)
				).and(
					ResourcePermissionTable.INSTANCE.primKeyId.eq(
						LayoutTable.INSTANCE.plid)
				)
			));
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