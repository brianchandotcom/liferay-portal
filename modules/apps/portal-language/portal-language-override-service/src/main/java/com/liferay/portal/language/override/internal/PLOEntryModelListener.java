/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.language.override.internal;

import com.liferay.portal.kernel.cluster.ClusterExecutor;
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
		MethodType methodType, String osgiServiceIdentifier,
		PLOEntry ploEntry) {

		PLOEntryModelListener ploEntryModelListener =
			(PLOEntryModelListener)
				IdentifiableOSGiServiceUtil.getIdentifiableOSGiService(
					osgiServiceIdentifier);

		ploEntryModelListener._updatePLOLanguageOverrideProvider(
			methodType, ploEntry);
	}

	private void _notifyCluster(MethodType methodType, PLOEntry ploEntry) {
		if (!_clusterExecutor.isEnabled()) {
			return;
		}

		try {
			MethodHandler methodHandler = new MethodHandler(
				_onNotifyMethodKey, methodType, getOSGiServiceIdentifier(),
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
			_ploOverrideResourceBundleManager.add(ploEntry);
		}
		else if (methodType == MethodType.REMOVE) {
			_ploOverrideResourceBundleManager.remove(ploEntry);
		}
		else if (methodType == MethodType.UPDATE) {
			_ploOverrideResourceBundleManager.update(ploEntry);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PLOEntryModelListener.class.getName());

	private static final MethodKey _onNotifyMethodKey = new MethodKey(
		PLOEntryModelListener.class, "_onNotify", MethodType.class,
		String.class, PLOEntry.class);

	@Reference
	private ClusterExecutor _clusterExecutor;

	@Reference
	private PLOOverrideResourceBundleManager _ploOverrideResourceBundleManager;

	private enum MethodType {

		ADD, REMOVE, UPDATE

	}

}