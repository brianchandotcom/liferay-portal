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

package com.liferay.portal.monitoring.statistics;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.monitoring.DataSample;
import com.liferay.portal.kernel.monitoring.DataSampleFactory;
import com.liferay.portal.kernel.monitoring.MethodSignature;
import com.liferay.portal.kernel.monitoring.PortletRequestType;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Michael C. Han
 */
@ProviderType
public class DataSampleFactoryUtil {

	public static DataSample createPortalRequestDataSample(
		long companyId, long groupId, String referer, String remoteAddr,
		String remoteUser, String requestURI, String requestURL,
		String userAgent) {

		return _getDataSampleFactory().createPortalRequestDataSample(
			companyId, groupId, referer, remoteAddr, remoteUser, requestURI,
			requestURL, userAgent);
	}

	public static DataSample createPortletRequestDataSample(
		PortletRequestType requestType, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		return _getDataSampleFactory().createPortletRequestDataSample(
			requestType, portletRequest, portletResponse);
	}

	public static DataSample createServiceRequestDataSample(
		MethodSignature methodSignature) {

		return _getDataSampleFactory().createServiceRequestDataSample(
			methodSignature);
	}

	private static DataSampleFactory _getDataSampleFactory() {
		return _instance._serviceTracker.getService();
	}

	private DataSampleFactoryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(DataSampleFactory.class);

		_serviceTracker.open();
	}

	private static final DataSampleFactoryUtil _instance =
		new DataSampleFactoryUtil();

	private final ServiceTracker<DataSampleFactory, DataSampleFactory>
		_serviceTracker;

}