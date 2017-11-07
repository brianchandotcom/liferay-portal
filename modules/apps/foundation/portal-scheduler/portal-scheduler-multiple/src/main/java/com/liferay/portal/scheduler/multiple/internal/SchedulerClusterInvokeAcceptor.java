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

package com.liferay.portal.scheduler.multiple.internal;

import com.liferay.portal.kernel.cluster.ClusterInvokeAcceptor;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.StorageType;

import java.io.Serializable;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = ClusterInvokeAcceptor.class)
public class SchedulerClusterInvokeAcceptor implements ClusterInvokeAcceptor {

	@Override
	public boolean accept(Map<String, Serializable> context) {
		if (ClusterInvokeThreadLocal.isEnabled()) {
			return false;
		}

		boolean pluginReady = (Boolean)context.get(
			ClusterSchedulerEngine.PLUGIN_READY);

		if (!pluginReady) {
			return false;
		}

		boolean portalReady = (Boolean)context.get(
			ClusterSchedulerEngine.PORTAL_READY);

		if (!portalReady) {
			return false;
		}

		Boolean schedulerClusterInvoking = (Boolean)context.get(
			SchedulerEngine.SCHEDULER_CLUSTER_INVOKING);

		if ((schedulerClusterInvoking != null) && !schedulerClusterInvoking) {
			return false;
		}

		StorageType storageType = (StorageType)context.get(
			SchedulerEngine.STORAGE_TYPE);

		if (storageType == StorageType.PERSISTED) {
			return false;
		}

		return true;
	}

}