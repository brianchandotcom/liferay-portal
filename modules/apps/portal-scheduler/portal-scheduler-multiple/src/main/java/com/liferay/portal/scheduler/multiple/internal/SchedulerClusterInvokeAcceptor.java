/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = ClusterInvokeAcceptor.class)
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