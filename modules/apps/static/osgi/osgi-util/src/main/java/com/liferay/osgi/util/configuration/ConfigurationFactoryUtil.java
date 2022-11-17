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

package com.liferay.osgi.util.configuration;

import com.liferay.osgi.util.StringPlus;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.framework.Constants;

/**
 * @author Raymond Augé
 */
public class ConfigurationFactoryUtil {

	public static long getCompanyId(
			CompanyLocalService companyLocalService,
			Map<String, Object> properties)
		throws IllegalStateException {

		long companyId = GetterUtil.getLong(properties.get("companyId"));

		if (companyId > 0) {
			return companyId;
		}

		String webId = (String)properties.get(
			"dxp.lxc.liferay.com.virtualInstanceId");

		if (Objects.equals(webId, "default")) {
			webId = PropsValues.COMPANY_DEFAULT_WEB_ID;
		}

		if (Validator.isNull(webId)) {
			throw new IllegalStateException(
				"The property \"companyId\" or " +
					"\"dxp.lxc.liferay.com.virtualInstanceId\" must be set");
		}

		try {
			Company company = companyLocalService.getCompanyByWebId(webId);

			return company.getCompanyId();
		}
		catch (PortalException portalException) {
			throw new IllegalStateException(portalException);
		}
	}

	public static String getExternalReferenceCode(
			Map<String, Object> properties)
		throws IllegalStateException {

		String serviceFactoryPid = GetterUtil.getString(
			properties.get("service.factoryPid"));

		if (Validator.isNull(serviceFactoryPid)) {
			throw new IllegalStateException("Service factory PID is null");
		}

		List<String> servicePids = StringPlus.asList(
			properties.get(Constants.SERVICE_PID));

		if (servicePids.isEmpty()) {
			throw new IllegalStateException("Service PID is null");
		}

		String servicePid = servicePids.get(0);

		if (!servicePid.startsWith(serviceFactoryPid)) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Service PID (", servicePid, ") does not start with ",
					"service factory PID (", serviceFactoryPid, ")"));
		}

		return servicePid.substring(serviceFactoryPid.length() + 1);
	}

}