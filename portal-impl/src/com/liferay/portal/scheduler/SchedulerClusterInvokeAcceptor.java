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

package com.liferay.portal.scheduler;

import com.liferay.portal.kernel.cluster.ClusterInvokeAcceptor;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.StorageType;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Michael C. Han
 */
public class SchedulerClusterInvokeAcceptor implements ClusterInvokeAcceptor {

	@Override
	public boolean accept(Map<String, Serializable> context) {
		if (ClusterInvokeThreadLocal.isEnabled()) {
			return false;
		}

		StorageType storageType = (StorageType)context.get(
			SchedulerEngine.STORAGE_TYPE);

		boolean portalReady = (Boolean)context.get(
			ClusterSchedulerEngine.PORTAL_READY);

		boolean pluginReady = (Boolean)context.get(
			ClusterSchedulerEngine.PLUGIN_READY);

		if ((storageType == StorageType.PERSISTED) || !portalReady ||
			!pluginReady) {

			return false;
		}

		return true;
	}

}