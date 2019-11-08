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

package com.liferay.data.engine.rest.resource.v1_0.test.util;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Marcela Cunha
 */
public class DataRecordCollectionTestUtil {

	public static DDLRecordSet addRecordSet(
			DDMStructure ddmStructure, Group group,
			ResourceLocalService resourceLocalService)
		throws Exception {

		return addRecordSet(
			ddmStructure, group, resourceLocalService,
			DDLRecordSetConstants.SCOPE_DATA_ENGINE);
	}

	public static DDLRecordSet addRecordSet(
			DDMStructure ddmStructure, Group group,
			ResourceLocalService resourceLocalService, int scope)
		throws Exception {

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		DDLRecordSet ddlRecordSet = DDLRecordSetLocalServiceUtil.addRecordSet(
			TestPropsValues.getUserId(), group.getGroupId(),
			ddmStructure.getStructureId(), ddmStructure.getStructureKey(),
			nameMap, null, DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT,
			scope, serviceContext);

		resourceLocalService.addModelResources(
			TestPropsValues.getCompanyId(), ddmStructure.getGroupId(),
			TestPropsValues.getUserId(), _RESOURCE_NAME,
			ddlRecordSet.getRecordSetId(),
			serviceContext.getModelPermissions());

		return ddlRecordSet;
	}

	private static final String _RESOURCE_NAME =
		"com.liferay.data.engine.rest.internal.model." +
			"InternalDataRecordCollection";

}