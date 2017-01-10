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

package com.liferay.product.navigation.simulation.device.internal.application.list;

import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Lancha
 */
@Component(immediate = true, service = DynamicInclude.class)
public class SimulationDeviceDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response,
			String key)
		throws IOException {

		PrintWriter printWriter = response.getWriter();

		printWriter.print(_simulationDeviceDynamicIncludeTemplate);
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register("/html/common/themes/bottom.jsp#post");
	}

	private static final String _simulationDeviceDynamicIncludeTemplate;

	static {
		try (InputStream inputStream =
				SimulationDeviceDynamicInclude.class.getResourceAsStream(
					"/META-INF/resources/simulation_device_dynamic_include." +
						"tmpl")) {

			_simulationDeviceDynamicIncludeTemplate = StringUtil.read(
				inputStream);
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

}