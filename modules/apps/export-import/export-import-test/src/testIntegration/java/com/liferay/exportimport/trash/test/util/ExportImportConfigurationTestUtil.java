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

package com.liferay.exportimport.trash.test.util;

import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationSettingsMapFactoryUtil;
import com.liferay.exportimport.kernel.model.ExportImportConfiguration;
import com.liferay.exportimport.kernel.service.ExportImportConfigurationLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Daniel Kocsis
 */
public class ExportImportConfigurationTestUtil {

	public static ExportImportConfiguration addExportImportConfiguration(
			long groupId, int type)
		throws Exception {

		Map<String, Serializable> settingsMap = getDefaultSettingsMap(
			TestPropsValues.getUserId(), groupId);

		return addExportImportConfiguration(groupId, type, settingsMap);
	}

	public static ExportImportConfiguration addExportImportConfiguration(
			long groupId, int type, Map<String, Serializable> settingsMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return ExportImportConfigurationLocalServiceUtil.
			addExportImportConfiguration(
				TestPropsValues.getUserId(), groupId,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				type, settingsMap, serviceContext);
	}

	public static Map<String, Serializable> getDefaultSettingsMap(
			long userId, long groupId)
		throws Exception {

		return ExportImportConfigurationSettingsMapFactoryUtil.
			buildPublishLayoutLocalSettingsMap(
				UserLocalServiceUtil.getUser(userId), groupId, groupId, false,
				null, null);
	}

}