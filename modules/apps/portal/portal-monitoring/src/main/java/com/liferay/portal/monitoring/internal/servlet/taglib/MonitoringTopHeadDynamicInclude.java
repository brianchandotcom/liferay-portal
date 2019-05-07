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

package com.liferay.portal.monitoring.internal.servlet.taglib;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.monitoring.DataSample;
import com.liferay.portal.kernel.monitoring.DataSampleFactory;
import com.liferay.portal.kernel.monitoring.DataSampleThreadLocal;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.monitoring.internal.configuration.MonitoringConfiguration;
import com.liferay.portal.monitoring.internal.constants.MonitoringWebKeys;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.monitoring.internal.configuration.MonitoringConfiguration",
	enabled = false, immediate = true, service = DynamicInclude.class
)
public class MonitoringTopHeadDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, String key) {

		if (!_monitoringConfiguration.monitorPortalRequest()) {
			return;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		DataSample dataSample =
			_dataSampleFactory.createPortalRequestDataSample(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				httpServletRequest.getHeader(HttpHeaders.REFERER),
				httpServletRequest.getRemoteAddr(),
				httpServletRequest.getRemoteUser(),
				httpServletRequest.getRequestURI(),
				String.valueOf(httpServletRequest.getRequestURL()) +
					".jsp_display",
				httpServletRequest.getHeader(HttpHeaders.USER_AGENT));

		dataSample.setDescription("Portal Request");

		dataSample.prepare();

		DataSampleThreadLocal.initialize();

		httpServletRequest.setAttribute(
			MonitoringWebKeys.PORTAL_REQUEST_DATA_SAMPLE, dataSample);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/top_head.jsp#pre");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_monitoringConfiguration = ConfigurableUtil.createConfigurable(
			MonitoringConfiguration.class, properties);
	}

	@Reference(unbind = "-")
	protected void setDataSampleFactory(DataSampleFactory dataSampleFactory) {
		_dataSampleFactory = dataSampleFactory;
	}

	private DataSampleFactory _dataSampleFactory;
	private volatile MonitoringConfiguration _monitoringConfiguration;

}