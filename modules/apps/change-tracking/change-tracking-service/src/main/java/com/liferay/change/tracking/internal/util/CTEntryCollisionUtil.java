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

package com.liferay.change.tracking.internal.util;

import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * @author Daniel Kocsis
 */
public class CTEntryCollisionUtil {

	public static void checkCollidingCTEntries(
		CTEntryLocalService ctEntryLocalService, long companyId,
		long modelClassPK, long modelResourcePrimKey) {

		DynamicQuery dynamicQuery = ctEntryLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		Property modelClassPKProperty = PropertyFactoryUtil.forName(
			"modelClassPK");

		dynamicQuery.add(modelClassPKProperty.lt(modelClassPK));

		Property modelResourcePrimKeyProperty = PropertyFactoryUtil.forName(
			"modelResourcePrimKey");

		dynamicQuery.add(modelResourcePrimKeyProperty.eq(modelResourcePrimKey));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(statusProperty.eq(WorkflowConstants.STATUS_DRAFT));

		List<CTEntry> collidingCTEntries = ctEntryLocalService.dynamicQuery(
			dynamicQuery);

		for (CTEntry ctEntry : collidingCTEntries) {
			ctEntryLocalService.updateCollision(ctEntry.getCtEntryId(), true);
		}
	}

}