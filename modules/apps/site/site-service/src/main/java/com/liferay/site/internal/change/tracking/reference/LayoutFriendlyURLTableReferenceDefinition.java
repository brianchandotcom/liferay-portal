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
import com.liferay.portal.kernel.model.LayoutFriendlyURLTable;
import com.liferay.portal.kernel.model.LayoutSetTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.model.UserTable;
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
		TableReferenceDefinitionHelper<LayoutFriendlyURLTable>
			tableReferenceDefinitionHelper) {

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutFriendlyURLTable.INSTANCE.uuid);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				GroupTable.INSTANCE
			).innerJoinON(
				LayoutFriendlyURLTable.INSTANCE,
				LayoutFriendlyURLTable.INSTANCE.groupId.eq(
					GroupTable.INSTANCE.groupId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				CompanyTable.INSTANCE
			).innerJoinON(
				LayoutFriendlyURLTable.INSTANCE,
				LayoutFriendlyURLTable.INSTANCE.companyId.eq(
					CompanyTable.INSTANCE.companyId)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				UserTable.INSTANCE
			).innerJoinON(
				LayoutFriendlyURLTable.INSTANCE,
				LayoutFriendlyURLTable.INSTANCE.userId.eq(
					UserTable.INSTANCE.userId)
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutFriendlyURLTable.INSTANCE.userName);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutFriendlyURLTable.INSTANCE.createDate);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutFriendlyURLTable.INSTANCE.modifiedDate);

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
			fromStep -> fromStep.from(
				LayoutTable.INSTANCE
			).innerJoinON(
				LayoutFriendlyURLTable.INSTANCE,
				LayoutFriendlyURLTable.INSTANCE.plid.eq(
					LayoutTable.INSTANCE.plid)
			));

		tableReferenceDefinitionHelper.defineReferenceInnerJoin(
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
			));

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutFriendlyURLTable.INSTANCE.friendlyURL);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutFriendlyURLTable.INSTANCE.languageId);

		tableReferenceDefinitionHelper.defineNonreferenceColumn(
			LayoutFriendlyURLTable.INSTANCE.lastPublishDate);
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