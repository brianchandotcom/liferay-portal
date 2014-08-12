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

package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;

/**
 * @author Brian Wing Shun Chan
 */
public class ThreadSafeAutoDeployer implements AutoDeployer {

	public ThreadSafeAutoDeployer(AutoDeployer autoDeployer) {
		_autoDeployer = autoDeployer;
	}

	@Override
	public int autoDeploy(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		AutoDeployer cloneAutoDeployer = _autoDeployer.cloneAutoDeployer();

		return cloneAutoDeployer.autoDeploy(autoDeploymentContext);
	}

	@Override
	public AutoDeployer cloneAutoDeployer() {
		throw new UnsupportedOperationException();
	}

	private final AutoDeployer _autoDeployer;

}