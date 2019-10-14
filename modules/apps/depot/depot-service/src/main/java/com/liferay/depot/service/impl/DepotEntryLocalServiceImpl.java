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

import com.liferay.depot.constants.DepotEntryConstants;
import com.liferay.depot.exception.DepotEntryNameException;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.base.DepotEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the depot entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.depot.service.DepotEntryLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.depot.model.DepotEntry",
	service = AopService.class
)
public class DepotEntryLocalServiceImpl extends DepotEntryLocalServiceBaseImpl {

	@Override
	public DepotEntry addDepotEntry(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			ServiceContext serviceContext)
		throws PortalException {

		_validateNameMap(nameMap);

		long entryId = counterLocalService.increment();

		DepotEntry entry = depotEntryPersistence.create(entryId);

		entry.setUuid(serviceContext.getUuid());
		entry.setCompanyId(serviceContext.getCompanyId());
		entry.setUserId(serviceContext.getUserId());

		entry = depotEntryPersistence.update(entry);

		Group group = _groupLocalService.addGroup(
			entry.getUserId(), GroupConstants.DEFAULT_PARENT_GROUP_ID,
			DepotEntry.class.getName(), entry.getDepotEntryId(),
			GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, descriptionMap,
			DepotEntryConstants.GROUP_TYPE_DEPOT, false,
			GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false, false,
			true, serviceContext);

		entry.setGroupId(group.getGroupId());

		return depotEntryPersistence.update(entry);
	}

	@Override
	public DepotEntry updateDepotEntry(
			long depotEntryId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		_validateNameMap(nameMap);

		DepotEntry entry = getDepotEntry(depotEntryId);

		Group group = _groupLocalService.getGroup(entry.getGroupId());

		_groupLocalService.updateGroup(
			entry.getGroupId(), group.getParentGroupId(), nameMap,
			descriptionMap, group.getType(), group.isManualMembership(),
			group.getMembershipRestriction(), group.getFriendlyURL(),
			group.isInheritContent(), group.isActive(), serviceContext);

		return depotEntryPersistence.update(entry);
	}

	private void _validateNameMap(Map<Locale, String> nameMap)
		throws DepotEntryNameException {

		if (MapUtil.isEmpty(nameMap) ||
			Validator.isNull(nameMap.get(LocaleUtil.getDefault()))) {

			throw new DepotEntryNameException();
		}
	}

	@Reference
	private GroupLocalService _groupLocalService;

}