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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.deploy.hot.HotDeployListener;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

/**
 * @author Adolfo Pérez
 */
public class OSGiHotDeployListener extends BaseHotDeployListener {

	@Override
	public void invokeDeploy(HotDeployEvent event) throws HotDeployException {
		for (HotDeployListener hotDeployListener : _serviceTrackerList) {
			hotDeployListener.invokeDeploy(event);
		}
	}

	@Override
	public void invokeUndeploy(HotDeployEvent event) throws HotDeployException {
		for (HotDeployListener hotDeployListener : _serviceTrackerList) {
			hotDeployListener.invokeUndeploy(event);
		}
	}

	private final ServiceTrackerList<HotDeployListener> _serviceTrackerList =
		ServiceTrackerCollections.list(HotDeployListener.class);

}