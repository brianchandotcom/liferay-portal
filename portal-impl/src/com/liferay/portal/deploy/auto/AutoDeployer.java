/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public interface AutoDeployer {

	public static final int CODE_DEFAULT = 1;

	public static final int CODE_SKIP_NEWER_VERSION = 2;

	public int autoDeploy() throws AutoDeployException;

	public int autoDeployWithCopyAndContext(
			AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException;

	public AutoDeployer copy();

	public void setAutoDeploymentContext(
		AutoDeploymentContext autoDeploymentContext);

}