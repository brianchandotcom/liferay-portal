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

package com.liferay.dynamic.data.mapping.test.util;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.service.StagingLocalServiceUtil;
import com.liferay.exportimport.kernel.staging.StagingConstants;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.persistence.GroupUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.util.PropsValues;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author Victor Ware
 */
public class DDMFormStagingTestUtil {

	public static void enableLocalStaging(
			Group group, boolean enableFormsStaging)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		addStagingAttribute(
			serviceContext,
			StagingUtil.getStagedPortletId(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM),
			enableFormsStaging);
		addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_CONFIGURATION_ALL,
			false);
		addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_DATA_ALL, false);
		addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_SETUP_ALL, false);

		StagingLocalServiceUtil.enableLocalStaging(
			TestPropsValues.getUserId(), group, false, false, serviceContext);
	}

	public static void enableRemoteStaging(
			Group remoteLiveGroup, Group remoteStagingGroup)
		throws Exception {

		setPortalProperty(
			"TUNNELING_SERVLET_SHARED_SECRET",
			"F0E1D2C3B4A5968778695A4B3C2D1E0F");

		setPortalProperty("TUNNELING_SERVLET_SHARED_SECRET_HEX", true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(remoteStagingGroup.getGroupId());

		addStagingAttribute(
			serviceContext,
			StagingUtil.getStagedPortletId(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM),
			true);
		addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_DATA_ALL, false);
		addStagingAttribute(
			serviceContext, PortletDataHandlerKeys.PORTLET_SETUP_ALL, false);

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		StagingLocalServiceUtil.enableRemoteStaging(
			TestPropsValues.getUserId(), remoteStagingGroup, false, false,
			"localhost", PortalUtil.getPortalServerPort(false),
			PortalUtil.getPathContext(), false, remoteLiveGroup.getGroupId(),
			serviceContext);

		GroupUtil.clearCache();
	}

	protected static void addStagingAttribute(
		ServiceContext serviceContext, String key, Object value) {

		String affixedKey =
			StagingConstants.STAGED_PREFIX + key + StringPool.DOUBLE_DASH;

		serviceContext.setAttribute(affixedKey, String.valueOf(value));
	}

	protected static void setPortalProperty(String propertyName, Object value)
		throws Exception {

		Field field = ReflectionUtil.getDeclaredField(
			PropsValues.class, propertyName);

		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");

		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, value);
	}

}