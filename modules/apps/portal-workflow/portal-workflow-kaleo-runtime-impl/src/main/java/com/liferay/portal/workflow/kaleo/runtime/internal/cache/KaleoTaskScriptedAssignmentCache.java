/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.cache;

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;

import java.util.ArrayList;
import java.util.Collection;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ricardo Couso
 */
@Component(service = KaleoTaskScriptedAssignmentCache.class)
public class KaleoTaskScriptedAssignmentCache {

	public Collection<KaleoTaskAssignment> getKaleoTaskAssignments(
		long kaleoTaskInstanceTokenId) {

		return _portalCache.get(kaleoTaskInstanceTokenId);
	}

	public void putKaleoTaskAssignments(
		long kaleoTaskInstanceTokenId,
		Collection<KaleoTaskAssignment> kaleoTaskAssignments, int timeToLive) {

		_portalCache.put(
			kaleoTaskInstanceTokenId, new ArrayList<>(kaleoTaskAssignments),
			timeToLive);
	}

	@Activate
	protected void activate() {
		_portalCache =
			(PortalCache<Long, ArrayList<KaleoTaskAssignment>>)
				_multiVMPool.getPortalCache(
					KaleoTaskScriptedAssignmentCache.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_multiVMPool.removePortalCache(
			KaleoTaskScriptedAssignmentCache.class.getName());
	}

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<Long, ArrayList<KaleoTaskAssignment>> _portalCache;

}