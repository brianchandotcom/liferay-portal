/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.renderer.react.internal.model.listener;

import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.react.internal.helper.FragmentEntryLinkJSModuleInitializerHelper;
import com.liferay.fragment.renderer.react.internal.util.FragmentEntryFragmentRendererReactUtil;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistryUpdate;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiServiceUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = {IdentifiableOSGiService.class, ModelListener.class})
public class FragmentEntryLinkModelListener
	extends BaseModelListener<FragmentEntryLink>
	implements IdentifiableOSGiService {

	@Override
	public String getOSGiServiceIdentifier() {
		return FragmentEntryLinkModelListener.class.getName();
	}

	@Override
	public void onAfterCreate(FragmentEntryLink fragmentEntryLink) {
		if (!fragmentEntryLink.isTypeReact()) {
			return;
		}

		_updateNPMRegistry(MethodType.ADD, null, fragmentEntryLink);

		_notifyCluster(MethodType.ADD, null, fragmentEntryLink);
	}

	@Override
	public void onAfterRemove(FragmentEntryLink fragmentEntryLink) {
		if (!fragmentEntryLink.isTypeReact()) {
			return;
		}

		_fragmentEntryLinkJSModuleInitializerHelper.ensureInitialized();

		_updateNPMRegistry(MethodType.REMOVE, fragmentEntryLink, null);

		_notifyCluster(MethodType.REMOVE, fragmentEntryLink, null);
	}

	@Override
	public void onAfterUpdate(
		FragmentEntryLink originalFragmentEntryLink,
		FragmentEntryLink fragmentEntryLink) {

		if (!fragmentEntryLink.isTypeReact()) {
			return;
		}

		_fragmentEntryLinkJSModuleInitializerHelper.ensureInitialized();

		_updateNPMRegistry(
			MethodType.UPDATE, originalFragmentEntryLink, fragmentEntryLink);

		_notifyCluster(
			MethodType.UPDATE, originalFragmentEntryLink, fragmentEntryLink);
	}

	@Override
	public void onBeforeCreate(FragmentEntryLink fragmentEntryLink)
		throws ModelListenerException {

		_fragmentEntryLinkJSModuleInitializerHelper.ensureInitialized();
	}

	@Deactivate
	protected void deactivate() {
		JSPackage jsPackage = _npmResolver.getJSPackage();

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				FragmentConstants.TYPE_REACT, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		NPMRegistryUpdate npmRegistryUpdate = _npmRegistry.update();

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			npmRegistryUpdate.unregisterJSModule(
				jsPackage.getJSModule(
					FragmentEntryFragmentRendererReactUtil.getModuleName(
						fragmentEntryLink)));
		}

		npmRegistryUpdate.finish();
	}

	private static void _onNotify(
		MethodType methodType, String osgiServiceIdentifier,
		FragmentEntryLink oldFragmentEntryLink,
		FragmentEntryLink newFragmentEntryLink) {

		FragmentEntryLinkModelListener fragmentEntryLinkModelListener =
			(FragmentEntryLinkModelListener)
				IdentifiableOSGiServiceUtil.getIdentifiableOSGiService(
					osgiServiceIdentifier);

		fragmentEntryLinkModelListener._updateNPMRegistry(
			methodType, oldFragmentEntryLink, newFragmentEntryLink);
	}

	private void _notifyCluster(
		MethodType methodType, FragmentEntryLink oldFragmentEntryLink,
		FragmentEntryLink newFragmentEntryLink) {

		if (!_clusterExecutor.isEnabled()) {
			return;
		}

		try {
			MethodHandler methodHandler = new MethodHandler(
				_onNotifyMethodKey, methodType, getOSGiServiceIdentifier(),
				oldFragmentEntryLink, newFragmentEntryLink);

			ClusterRequest clusterRequest =
				ClusterRequest.createMulticastRequest(methodHandler, true);

			clusterRequest.setFireAndForget(true);

			_clusterExecutor.execute(clusterRequest);
		}
		catch (Throwable throwable) {
			_log.error(throwable);
		}
	}

	private void _updateNPMRegistry(
		MethodType methodType, FragmentEntryLink oldFragmentEntryLink,
		FragmentEntryLink newFragmentEntryLink) {

		NPMRegistryUpdate npmRegistryUpdate = _npmRegistry.update();

		JSPackage jsPackage = _npmResolver.getJSPackage();

		if ((methodType == MethodType.REMOVE) ||
			(methodType == MethodType.UPDATE)) {

			npmRegistryUpdate.unregisterJSModule(
				jsPackage.getJSModule(
					FragmentEntryFragmentRendererReactUtil.getModuleName(
						oldFragmentEntryLink)));
		}

		if ((methodType == MethodType.ADD) ||
			(methodType == MethodType.UPDATE)) {

			npmRegistryUpdate.registerJSModule(
				jsPackage,
				FragmentEntryFragmentRendererReactUtil.getModuleName(
					newFragmentEntryLink),
				FragmentEntryFragmentRendererReactUtil.getDependencies(),
				FragmentEntryFragmentRendererReactUtil.getJs(
					newFragmentEntryLink, jsPackage),
				null);
		}

		npmRegistryUpdate.finish();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkModelListener.class);

	private static final MethodKey _onNotifyMethodKey = new MethodKey(
		FragmentEntryLinkModelListener.class, "_onNotify", MethodType.class,
		String.class, FragmentEntryLink.class, FragmentEntryLink.class);

	@Reference
	private ClusterExecutor _clusterExecutor;

	@Reference
	private FragmentEntryLinkJSModuleInitializerHelper
		_fragmentEntryLinkJSModuleInitializerHelper;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private NPMRegistry _npmRegistry;

	@Reference
	private NPMResolver _npmResolver;

	private enum MethodType {

		ADD, REMOVE, UPDATE

	}

}