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

package com.liferay.portal.language.override.internal;

import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiServiceUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.language.override.model.PLOEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(service = {ModelListener.class, IdentifiableOSGiService.class})
public class PLOEntryModelListener
	extends BaseModelListener<PLOEntry> implements IdentifiableOSGiService {

	@Override
	public String getOSGiServiceIdentifier() {
		return PLOEntryModelListener.class.getName();
	}

	@Override
	public void onAfterCreate(PLOEntry ploEntry) {
		_updatePLOLanguageOverrideProvider(MethodType.ADD, ploEntry);

		_notifyCluster(MethodType.ADD, ploEntry);
	}

	@Override
	public void onAfterRemove(PLOEntry ploEntry) {
		_updatePLOLanguageOverrideProvider(MethodType.REMOVE, ploEntry);

		_notifyCluster(MethodType.REMOVE, ploEntry);
	}

	@Override
	public void onAfterUpdate(PLOEntry originalPLOEntry, PLOEntry ploEntry) {
		_updatePLOLanguageOverrideProvider(MethodType.UPDATE, ploEntry);

		_notifyCluster(MethodType.UPDATE, ploEntry);
	}

	private static void _onNotify(
		String osgiServiceIdentifier, MethodType methodType,
		PLOEntry ploEntry) {

		PLOEntryModelListener ploEntryModelListener =
			(PLOEntryModelListener)
				IdentifiableOSGiServiceUtil.getIdentifiableOSGiService(
					osgiServiceIdentifier);

		ploEntryModelListener._updatePLOLanguageOverrideProvider(
			methodType, ploEntry);
	}

	private void _notifyCluster(MethodType methodType, PLOEntry ploEntry) {
		if (!_clusterLink.isEnabled()) {
			return;
		}

		try {
			MethodHandler methodHandler = new MethodHandler(
				_onNotifyMethodKey, getOSGiServiceIdentifier(), methodType,
				ploEntry);

			ClusterRequest clusterRequest =
				ClusterRequest.createMulticastRequest(methodHandler, true);

			clusterRequest.setFireAndForget(true);

			_clusterExecutor.execute(clusterRequest);
		}
		catch (Throwable throwable) {
			_log.error(throwable);
		}
	}

	private void _updatePLOLanguageOverrideProvider(
		MethodType methodType, PLOEntry ploEntry) {

		if (methodType == MethodType.ADD) {
			_ploLanguageOverrideProvider.add(ploEntry);
		}
		else if (methodType == MethodType.REMOVE) {
			_ploLanguageOverrideProvider.remove(ploEntry);
		}
		else if (methodType == MethodType.UPDATE) {
			_ploLanguageOverrideProvider.update(ploEntry);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PLOEntryModelListener.class.getName());

	private static final MethodKey _onNotifyMethodKey = new MethodKey(
		PLOEntryModelListener.class, "_onNotify", String.class,
		MethodType.class, PLOEntry.class);

	@Reference
	private ClusterExecutor _clusterExecutor;

	@Reference
	private ClusterLink _clusterLink;

	@Reference
	private PLOLanguageOverrideProvider _ploLanguageOverrideProvider;

	private enum MethodType {

		ADD, REMOVE, UPDATE

	}

}