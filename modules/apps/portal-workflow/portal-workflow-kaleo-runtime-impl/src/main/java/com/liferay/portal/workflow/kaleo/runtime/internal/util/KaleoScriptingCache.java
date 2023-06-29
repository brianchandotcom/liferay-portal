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

package com.liferay.portal.workflow.kaleo.runtime.internal.util;

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
@Component(service = KaleoScriptingCache.class)
public class KaleoScriptingCache {

	public Collection<KaleoTaskAssignment> getKaleoTaskAssignments(
		long kaleoTaskAssignmentId) {

		return _portalCache.get(kaleoTaskAssignmentId);
	}

	public void putKaleoTaskAssignments(
		long kaleoTaskAssignmentId,
		Collection<KaleoTaskAssignment> kaleoTaskAssignments, int timeToLive) {

		_portalCache.put(
			kaleoTaskAssignmentId, new ArrayList<>(kaleoTaskAssignments),
			timeToLive);
	}

	@Activate
	protected void activate() {
		_portalCache =
			(PortalCache<Long, ArrayList<KaleoTaskAssignment>>)
				_multiVMPool.getPortalCache(
					KaleoScriptingCache.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_multiVMPool.removePortalCache(KaleoScriptingCache.class.getName());
	}

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<Long, ArrayList<KaleoTaskAssignment>> _portalCache;

}