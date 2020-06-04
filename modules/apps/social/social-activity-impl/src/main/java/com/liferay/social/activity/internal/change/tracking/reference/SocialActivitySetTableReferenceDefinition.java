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
import com.liferay.social.kernel.service.persistence.SocialActivitySetPersistence;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = TableReferenceDefinition.class)
public class SocialActivitySetTableReferenceDefinition
	implements TableReferenceDefinition<SocialActivitySetTable> {

	@Override
	public void defineTableReferences(
		TableReferenceInfoBuilder<SocialActivitySetTable>
			tableReferenceInfoBuilder) {

		tableReferenceInfoBuilder.singleColumnReference(
			SocialActivitySetTable.INSTANCE.groupId, GroupTable.INSTANCE.groupId
		).singleColumnReference(
			SocialActivitySetTable.INSTANCE.companyId,
			CompanyTable.INSTANCE.companyId
		).singleColumnReference(
			SocialActivitySetTable.INSTANCE.userId, UserTable.INSTANCE.userId
		).nonreferenceColumns(
			SocialActivitySetTable.INSTANCE.createDate,
			SocialActivitySetTable.INSTANCE.modifiedDate,
			SocialActivitySetTable.INSTANCE.classNameId,
			SocialActivitySetTable.INSTANCE.classPK,
			SocialActivitySetTable.INSTANCE.type,
			SocialActivitySetTable.INSTANCE.extraData,
			SocialActivitySetTable.INSTANCE.activityCount
		);
	}

	@Override
	public BasePersistence<?> getBasePersistence() {
		return _socialActivitySetPersistence;
	}

	@Override
	public SocialActivitySetTable getTable() {
		return SocialActivitySetTable.INSTANCE;
	}

	@Reference
	private SocialActivitySetPersistence _socialActivitySetPersistence;

}