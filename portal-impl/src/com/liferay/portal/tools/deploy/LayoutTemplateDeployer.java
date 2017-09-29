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

package com.liferay.portal.tools.deploy;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Plugin;
import com.liferay.portal.tools.ToolDependencies;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutTemplateDeployer extends BaseDeployer {

	public static void main(String[] args) {
		ToolDependencies.wireDeployers();

		List<String> wars = new ArrayList<>();
		List<String> jars = new ArrayList<>();

		for (String arg : args) {
			if (arg.endsWith(".war")) {
				wars.add(arg);
			}
			else if (arg.endsWith(".jar")) {
				jars.add(arg);
			}
		}

		try (LayoutTemplateDeployer layoutTemplateDeployer =
				new LayoutTemplateDeployer(wars, jars)) {
		}
		catch (IOException ioe) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioe, ioe);
			}
		}
	}

	public LayoutTemplateDeployer() {
	}

	public LayoutTemplateDeployer(List<String> wars, List<String> jars) {
		super(wars, jars);
	}

	@Override
	public String getPluginType() {
		return Plugin.TYPE_LAYOUT_TEMPLATE;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutTemplateDeployer.class);

}