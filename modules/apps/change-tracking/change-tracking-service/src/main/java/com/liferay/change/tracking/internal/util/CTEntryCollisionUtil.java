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
import com.liferay.change.tracking.service.CTEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

/**
 * @author Daniel Kocsis
 */
public class CTEntryCollisionUtil {

	public static void checkCollidingCTEntries(CTEntry ctEntry) {
		List<CTEntry> collidingCTEntries = _getCollidingCTEntries(ctEntry);

		if (ListUtil.isEmpty(collidingCTEntries)) {
			return;
		}

		collidingCTEntries.forEach(
			collidingCTEntry -> CTEntryLocalServiceUtil.updateCollision(
				collidingCTEntry.getCtEntryId(), true));
	}

	private static List<CTEntry> _getCollidingCTEntries(CTEntry ctEntry) {
		DynamicQuery dynamicQuery = CTEntryLocalServiceUtil.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(ctEntry.getCompanyId()));

		Property modelClassPKProperty = PropertyFactoryUtil.forName(
			"modelClassPK");

		dynamicQuery.add(modelClassPKProperty.lt(ctEntry.getModelClassPK()));

		Property modelResourcePrimKeyProperty = PropertyFactoryUtil.forName(
			"modelResourcePrimKey");

		dynamicQuery.add(
			modelResourcePrimKeyProperty.eq(ctEntry.getModelResourcePrimKey()));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(statusProperty.eq(WorkflowConstants.STATUS_DRAFT));

		return CTEntryLocalServiceUtil.dynamicQuery(dynamicQuery);
	}

}