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

package com.liferay.portlet.mobiledevicerules;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.plugin.PluginPackageUtil;
import com.liferay.portlet.StrutsPortlet;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Pei-Jung Lan
 */
public class MDRPortlet extends StrutsPortlet {

	@Override
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (!PluginPackageUtil.isInstalled("wurfl-web")) {
			SessionErrors.add(
				renderRequest, "warning",
				"there-is-no-device-recognition-provider-installed");
		}

		super.doView(renderRequest, renderResponse);
	}

}