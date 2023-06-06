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

package com.liferay.layout.set.prototype.internal.helper;

import com.liferay.layout.set.prototype.helper.LayoutSetPrototypeHelper;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.LayoutSetPrototypeTable;
import com.liferay.portal.kernel.model.LayoutSetTable;
import com.liferay.portal.kernel.model.LayoutTable;
import com.liferay.portal.kernel.service.LayoutLocalService;

import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = LayoutSetPrototypeHelper.class)
public class LayoutSetPrototypeHelperImpl implements LayoutSetPrototypeHelper {

	@Override
	public Set<Long> getConflictingPlidsOfLayoutSetGroup(long groupId) {
		LayoutTable tempLayoutTable = LayoutTable.INSTANCE.as(
			"tempLayoutTable");

		Set<Long> plids = new HashSet<>();

		plids.addAll(
			_layoutLocalService.dslQuery(
				DSLQueryFactoryUtil.selectDistinct(
					LayoutTable.INSTANCE.plid
				).from(
					LayoutTable.INSTANCE
				).innerJoinON(
					LayoutSetTable.INSTANCE,
					LayoutSetTable.INSTANCE.companyId.eq(
						LayoutTable.INSTANCE.companyId
					).and(
						LayoutSetTable.INSTANCE.groupId.eq(
							LayoutTable.INSTANCE.groupId)
					).and(
						LayoutSetTable.INSTANCE.privateLayout.eq(
							LayoutTable.INSTANCE.privateLayout)
					)
				).innerJoinON(
					LayoutSetPrototypeTable.INSTANCE,
					LayoutSetPrototypeTable.INSTANCE.companyId.eq(
						LayoutSetTable.INSTANCE.companyId
					).and(
						LayoutSetPrototypeTable.INSTANCE.uuid.eq(
							LayoutSetTable.INSTANCE.layoutSetPrototypeUuid)
					)
				).innerJoinON(
					GroupTable.INSTANCE,
					GroupTable.INSTANCE.companyId.eq(
						LayoutSetPrototypeTable.INSTANCE.companyId
					).and(
						GroupTable.INSTANCE.classPK.eq(
							LayoutSetPrototypeTable.INSTANCE.
								layoutSetPrototypeId)
					)
				).innerJoinON(
					tempLayoutTable,
					tempLayoutTable.companyId.eq(
						GroupTable.INSTANCE.companyId
					).and(
						tempLayoutTable.groupId.eq(GroupTable.INSTANCE.groupId)
					).and(
						tempLayoutTable.friendlyURL.eq(
							LayoutTable.INSTANCE.friendlyURL)
					)
				).where(
					LayoutTable.INSTANCE.groupId.eq(
						groupId
					).and(
						LayoutTable.INSTANCE.system.eq(false)
					).and(
						LayoutTable.INSTANCE.sourcePrototypeLayoutUuid.isNull()
					)
				)));

		return plids;
	}

	@Override
	public Set<Long> getConflictingPlidsOfLayoutSetPrototypeGroup(
		long groupId) {

		LayoutTable tempLayoutTable = LayoutTable.INSTANCE.as(
			"tempLayoutTable");

		Set<Long> plids = new HashSet<>();

		plids.addAll(
			_layoutLocalService.dslQuery(
				DSLQueryFactoryUtil.selectDistinct(
					LayoutTable.INSTANCE.plid
				).from(
					LayoutTable.INSTANCE
				).innerJoinON(
					GroupTable.INSTANCE,
					GroupTable.INSTANCE.companyId.eq(
						LayoutTable.INSTANCE.companyId
					).and(
						GroupTable.INSTANCE.groupId.eq(
							LayoutTable.INSTANCE.groupId)
					)
				).innerJoinON(
					LayoutSetPrototypeTable.INSTANCE,
					LayoutSetPrototypeTable.INSTANCE.companyId.eq(
						GroupTable.INSTANCE.companyId
					).and(
						LayoutSetPrototypeTable.INSTANCE.layoutSetPrototypeId.
							eq(GroupTable.INSTANCE.classPK)
					)
				).innerJoinON(
					LayoutSetTable.INSTANCE,
					LayoutSetTable.INSTANCE.companyId.eq(
						LayoutSetPrototypeTable.INSTANCE.companyId
					).and(
						LayoutSetTable.INSTANCE.layoutSetPrototypeUuid.eq(
							LayoutSetPrototypeTable.INSTANCE.uuid)
					)
				).innerJoinON(
					tempLayoutTable,
					tempLayoutTable.companyId.eq(
						LayoutSetTable.INSTANCE.companyId
					).and(
						tempLayoutTable.groupId.eq(
							LayoutSetTable.INSTANCE.groupId)
					).and(
						tempLayoutTable.privateLayout.eq(
							LayoutSetTable.INSTANCE.privateLayout)
					).and(
						tempLayoutTable.friendlyURL.eq(
							LayoutTable.INSTANCE.friendlyURL)
					).and(
						tempLayoutTable.sourcePrototypeLayoutUuid.isNull()
					)
				).where(
					LayoutTable.INSTANCE.groupId.eq(
						groupId
					).and(
						LayoutTable.INSTANCE.system.eq(false)
					)
				)));

		return plids;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

}