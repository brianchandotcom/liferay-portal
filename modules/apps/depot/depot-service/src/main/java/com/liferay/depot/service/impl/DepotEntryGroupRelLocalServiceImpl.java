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

package com.liferay.depot.service.impl;

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.base.DepotEntryGroupRelLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * The implementation of the depot entry group rel local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.depot.service.DepotEntryGroupRelLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryGroupRelLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.depot.model.DepotEntryGroupRel",
	service = AopService.class
)
public class DepotEntryGroupRelLocalServiceImpl
	extends DepotEntryGroupRelLocalServiceBaseImpl {

	public DepotEntryGroupRel addDepotEntryGroupRel(
		long depotEntryId, long toGroupId) {

		DepotEntryGroupRel depotEntryGroupRel =
			depotEntryGroupRelPersistence.fetchByD_G(depotEntryId, toGroupId);

		if (depotEntryGroupRel != null) {
			return depotEntryGroupRel;
		}

		depotEntryGroupRel = depotEntryGroupRelPersistence.create(
			counterLocalService.increment());

		depotEntryGroupRel.setDepotEntryId(depotEntryId);
		depotEntryGroupRel.setToGroupId(toGroupId);

		return depotEntryGroupRelPersistence.update(depotEntryGroupRel);
	}

	public List<DepotEntryGroupRel> getDepotEntryGroupRels(
		DepotEntry depotEntry) {

		return depotEntryGroupRelPersistence.findByDepotEntryId(
			depotEntry.getDepotEntryId());
	}

}