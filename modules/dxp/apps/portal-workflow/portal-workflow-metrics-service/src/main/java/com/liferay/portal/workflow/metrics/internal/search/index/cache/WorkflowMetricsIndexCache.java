/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index.cache;

import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;

import java.util.ArrayList;
import java.util.Set;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = WorkflowMetricsIndexCache.class)
public class WorkflowMetricsIndexCache {

	public boolean hasIndex(long companyId, String indexName) {
		ArrayList<String> indexNames = _portalCache.get(companyId);

		if (indexNames == null) {
			return false;
		}

		return indexNames.contains(indexName);
	}

	public void putIndex(long companyId, String indexName) {
		ArrayList<String> indexNames = _portalCache.get(companyId);

		if (indexNames == null) {
			indexNames = new ArrayList<>();
		}

		indexNames.add(indexName);

		_portalCache.put(companyId, indexNames);
	}

	public void removeIndex(long companyId, String indexName) {
		ArrayList<String> indexNames = _portalCache.get(companyId);

		if (indexNames == null) {
			return;
		}

		indexNames.remove(indexName);

		_portalCache.put(companyId, indexNames);
	}

	@Activate
	protected void activate() {
		_portalCache =
			(PortalCache<Long, ArrayList<String>>)_multiVMPool.getPortalCache(
				WorkflowMetricsIndexCache.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_multiVMPool.removePortalCache(
			WorkflowMetricsIndexCache.class.getName());
	}

	@Reference
	private MultiVMPool _multiVMPool;

	private PortalCache<Long, ArrayList<String>> _portalCache;

}