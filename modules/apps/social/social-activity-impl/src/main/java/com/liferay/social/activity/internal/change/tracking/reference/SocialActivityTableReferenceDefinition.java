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

package com.liferay.social.activity.internal.change.tracking.reference;

import com.liferay.change.tracking.reference.TableReferenceDefinition;
import com.liferay.change.tracking.reference.builder.TableReferenceInfoBuilder;
import com.liferay.portal.kernel.model.CompanyTable;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.social.kernel.model.SocialActivitySetTable;
import com.liferay.social.kernel.model.SocialActivityTable;
import com.liferay.social.kernel.service.persistence.SocialActivityPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class SocialActivityTableReferenceDefinition
	implements TableReferenceDefinition<SocialActivityTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<SocialActivityTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.singleColumnReference(
			SocialActivityTable.INSTANCE.groupId, GroupTable.INSTANCE.groupId
		).singleColumnReference(
			SocialActivityTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			SocialActivityTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).nonreferenceColumn(
			SocialActivityTable.INSTANCE.createDate
		).singleColumnReference(
			SocialActivityTable.INSTANCE.activitySetId,
			SocialActivitySetTable.INSTANCE.activitySetId
		).parentColumnReference(
			SocialActivityTable.INSTANCE.activityId,
			SocialActivityTable.INSTANCE.mirrorActivityId
		).nonreferenceColumns(
			SocialActivityTable.INSTANCE.classNameId,
			SocialActivityTable.INSTANCE.classPK,
			SocialActivityTable.INSTANCE.parentClassNameId,
			SocialActivityTable.INSTANCE.parentClassPK,
			SocialActivityTable.INSTANCE.type,
			SocialActivityTable.INSTANCE.extraData
		).singleColumnReference(
			SocialActivityTable.INSTANCE.receiverUserId,
			UserTable.INSTANCE.userId
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _socialActivityPersistence;
	}

	@Override
	public SocialActivityTable getTable() {
		return SocialActivityTable.INSTANCE;
	}

	@Reference
	private SocialActivityPersistence _socialActivityPersistence;

}