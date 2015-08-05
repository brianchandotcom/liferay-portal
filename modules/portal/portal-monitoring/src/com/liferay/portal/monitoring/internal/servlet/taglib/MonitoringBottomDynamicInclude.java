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

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.monitoring.DataSample;
import com.liferay.portal.kernel.monitoring.DataSampleThreadLocal;
import com.liferay.portal.kernel.monitoring.RequestStatus;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.monitoring.configuration.MonitoringConfiguration;
import com.liferay.portal.monitoring.constants.MonitoringWebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.monitoring.configuration.MonitoringConfiguration",
	immediate = true, service = DynamicInclude.class
)
public class MonitoringBottomDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		if (!_monitoringConfiguration.monitorPortalRequest()) {
			return;
		}

		DataSample dataSample = (DataSample)request.getAttribute(
			MonitoringWebKeys.PORTAL_REQUEST_DATA_SAMPLE);

		if (dataSample != null) {
			dataSample.capture(RequestStatus.SUCCESS);

			DataSampleThreadLocal.addDataSample(dataSample);
		}

		List<DataSample> dataSamples = DataSampleThreadLocal.getDataSamples();

		if (!_monitoringConfiguration.showPerRequestDataSample() ||
			ListUtil.isEmpty(dataSamples)) {

			return;
		}

		StringBundler sb = new StringBundler(dataSamples.size() * 2 + 2);

		sb.append("<!--\n");

		for (DataSample curDataSample : dataSamples) {
			sb.append(curDataSample.toString());
			sb.append("\n");
		}

		sb.append("-->");

		PrintWriter printWriter = response.getWriter();

		printWriter.println(sb);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/bottom.jsp#post");
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_monitoringConfiguration = Configurable.createConfigurable(
			MonitoringConfiguration.class, properties);
	}

	private volatile MonitoringConfiguration _monitoringConfiguration;

}