/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.k8s.agent.internal.model.listener;

import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.k8s.agent.PortalK8sConfigMapModifier;
import com.liferay.portal.k8s.agent.internal.thread.local.AgentPortalK8sThreadLocal;
import com.liferay.portal.k8s.agent.internal.util.CompanyConfigMapUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(service = ModelListener.class)
public class VirtualHostModelListener extends BaseModelListener<VirtualHost> {

	@Override
	public void onAfterCreate(VirtualHost virtualHost) {
		Company company = _companyLocalService.fetchCompanyById(
			virtualHost.getCompanyId());

		if (company == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Created virtual host ", virtualHost.getHostname(),
					" for company ", company.getWebId()));
		}

		PortalK8sConfigMapModifier portalK8sConfigMapModifier =
			_portalK8sConfigMapModifierSnapshot.get();

		try (SafeCloseable safeCloseable =
				AgentPortalK8sThreadLocal.
					executeOnCurrentNodeWithSafeCloseable()) {

			CompanyConfigMapUtil.modifyConfigMap(
				company, portalK8sConfigMapModifier, _virtualHostLocalService);
		}
	}

	@Override
	public void onAfterRemove(VirtualHost virtualHost)
		throws ModelListenerException {

		Company company = _companyLocalService.fetchCompanyById(
			virtualHost.getCompanyId());

		if (company == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Removed virtual host ", virtualHost.getHostname(),
					" for company ", company.getWebId()));
		}

		PortalK8sConfigMapModifier portalK8sConfigMapModifier =
			_portalK8sConfigMapModifierSnapshot.get();

		try (SafeCloseable safeCloseable =
				AgentPortalK8sThreadLocal.
					executeOnCurrentNodeWithSafeCloseable()) {

			CompanyConfigMapUtil.modifyConfigMap(
				company, portalK8sConfigMapModifier, _virtualHostLocalService);
		}
	}

	@Override
	public void onAfterUpdate(
			VirtualHost originalVirtualHost, VirtualHost virtualHost)
		throws ModelListenerException {

		Company company = _companyLocalService.fetchCompanyById(
			virtualHost.getCompanyId());

		if (company == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Updated virtual host ", virtualHost.getHostname(),
					" for company ", company.getWebId()));
		}

		PortalK8sConfigMapModifier portalK8sConfigMapModifier =
			_portalK8sConfigMapModifierSnapshot.get();

		try (SafeCloseable safeCloseable =
				AgentPortalK8sThreadLocal.
					executeOnCurrentNodeWithSafeCloseable()) {

			CompanyConfigMapUtil.modifyConfigMap(
				company, portalK8sConfigMapModifier, _virtualHostLocalService);
		}
	}

	@Activate
	protected void activate() {
		PortalK8sConfigMapModifier portalK8sConfigMapModifier =
			_portalK8sConfigMapModifierSnapshot.get();

		_companyLocalService.forEachCompany(
			company -> CompanyConfigMapUtil.modifyConfigMap(
				company, portalK8sConfigMapModifier, _virtualHostLocalService));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VirtualHostModelListener.class);

	private static final Snapshot<PortalK8sConfigMapModifier>
		_portalK8sConfigMapModifierSnapshot = new Snapshot<>(
			VirtualHostModelListener.class, PortalK8sConfigMapModifier.class,
			null, true);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private VirtualHostLocalService _virtualHostLocalService;

}