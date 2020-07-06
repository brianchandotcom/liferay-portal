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

package com.liferay.asset.list.util;

import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.exportimport.kernel.staging.constants.StagingConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.Map;

/**
 * @author Kyle Miho
 */
public class AssetListStagingTestUtil {

	public static Group enableLocalStaging(
			Group group, boolean enableAssetListStaging)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		addStagingAttribute(
			serviceContext,
			StagingUtil.getStagedPortletId(AssetListPortletKeys.ASSET_LIST),
			enableAssetListStaging);
		addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.DATA_STRATEGY_MIRROR, true);
		addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			false);
		addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_DATA_ALL, false);
		addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_SETUP_ALL, false);

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), group, false, false, serviceContext);

		return group.getStagingGroup();
	}

	public static void publishLayouts(Group stagingGroup, Group liveGroup)
		throws PortalException {

		Map<String, String[]> parameters =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildFullPublishParameterMap();

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			liveGroup.getGroupId(), false, parameters);
	}

	protected static void addStagingAttribute(
		ServiceContext serviceContext, String key, Object value) {

		serviceContext.setAttribute(
			StagingConstants.STAGED_PREFIX + key + StringPool.DOUBLE_DASH,
			String.valueOf(value));
	}

}