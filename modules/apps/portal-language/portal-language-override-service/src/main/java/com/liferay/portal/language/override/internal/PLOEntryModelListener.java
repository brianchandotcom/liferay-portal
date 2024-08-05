/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.override.internal;

import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.language.override.model.PLOEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = ModelListener.class)
public class PLOEntryModelListener extends BaseModelListener<PLOEntry> {

	@Override
	public void onAfterCreate(PLOEntry ploEntry) {
		_clearCache();
	}

	@Override
	public void onAfterRemove(PLOEntry ploEntry) {
		_clearCache();
	}

	@Override
	public void onAfterUpdate(PLOEntry originalPLOEntry, PLOEntry ploEntry) {
		_clearCache();
	}

	private void _clearCache() {
		PLOOverrideResourceBundleManager.clearCache();

		if (!_clusterExecutor.isEnabled()) {
			return;
		}

		MethodHandler methodHandler = new MethodHandler(_clearCacheMethodKey);

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			methodHandler, true);

		clusterRequest.setFireAndForget(true);

		_clusterExecutor.execute(clusterRequest);
	}

	private static final MethodKey _clearCacheMethodKey = new MethodKey(
		PLOOverrideResourceBundleManager.class, "clearCache");

	@Reference
	private ClusterExecutor _clusterExecutor;

}