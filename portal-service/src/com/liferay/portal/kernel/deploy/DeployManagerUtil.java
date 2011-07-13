/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.deploy;

import java.io.File;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class DeployManagerUtil {

	public static void deploy(File source) throws Exception {
		getDeployManager().deploy(source);
	}

	public static void deploy(File source, String context) throws Exception {
		getDeployManager().deploy(source, context);
	}

	public static String getDeployDir() throws Exception {
		return getDeployManager().getDeployDir();
	}

	public static DeployManager getDeployManager() {
		return _deployManager;
	}

	public static void redeploy(String context) throws Exception {
		getDeployManager().redeploy(context);
	}

	public static void undeploy(String context) throws Exception {
		getDeployManager().undeploy(context);
	}

	public void setDeployManager(DeployManager deployManager) {
		_deployManager = deployManager;
	}

	private static DeployManager _deployManager;

}