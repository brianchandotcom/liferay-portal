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

package com.liferay.portlet.tck.bridge.deploy.auto;

import com.liferay.portal.deploy.auto.PortletAutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployListener;

import java.io.File;

import org.osgi.service.component.annotations.Component;

/**
 * @author Miguel Pastor
 */
@Component(immediate = true, service = AutoDeployListener.class)
public class TCKPortletAutoDeployListener extends PortletAutoDeployListener {

	@Override
	protected String getSuccessMessage(File file) {
		return "TCK portlets for " + file.getPath() + " copied successfully";
	}

	@Override
	protected boolean isDeployable(File file) throws AutoDeployException {
		String fileName = file.getName();

		if (super.isDeployable(file) && fileName.startsWith("portlet_jp_")) {
			return true;
		}

		return false;
	}

}