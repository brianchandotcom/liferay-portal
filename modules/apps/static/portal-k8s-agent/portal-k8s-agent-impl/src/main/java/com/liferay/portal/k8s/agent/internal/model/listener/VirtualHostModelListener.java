/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.k8s.agent.internal.model.listener;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.k8s.agent.PortalK8sConfigMapModifier;
import com.liferay.portal.k8s.agent.internal.util.CompanyConfigMapUtil;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.util.PropsValues;

import java.util.Objects;

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

		PortalK8sConfigMapModifier portalK8sConfigMapModifier =
			_portalK8sConfigMapModifierSnapshot.get();

		CompanyConfigMapUtil.modifyConfigMap(
			company, portalK8sConfigMapModifier, _virtualHostLocalService);
	}

	@Override
	public void onAfterRemove(VirtualHost virtualHost)
		throws ModelListenerException {

		Company company = _companyLocalService.fetchCompanyById(
			virtualHost.getCompanyId());

		if ((company == null) ||
			Objects.equals(
				company.getWebId(), PropsValues.COMPANY_DEFAULT_WEB_ID)) {

			return;
		}

		PortalK8sConfigMapModifier portalK8sConfigMapModifier =
			_portalK8sConfigMapModifierSnapshot.get();

		CompanyConfigMapUtil.modifyConfigMap(
			company, portalK8sConfigMapModifier, _virtualHostLocalService);
	}

	@Override
	public void onAfterUpdate(
			VirtualHost originalVirtualHost, VirtualHost virtualHost)
		throws ModelListenerException {

		Company company = _companyLocalService.fetchCompanyById(
			virtualHost.getCompanyId());

		PortalK8sConfigMapModifier portalK8sConfigMapModifier =
			_portalK8sConfigMapModifierSnapshot.get();

		CompanyConfigMapUtil.modifyConfigMap(
			company, portalK8sConfigMapModifier, _virtualHostLocalService);
	}

	@Activate
	protected void activate() {
		PortalK8sConfigMapModifier portalK8sConfigMapModifier =
			_portalK8sConfigMapModifierSnapshot.get();

		_companyLocalService.forEachCompany(
			company -> CompanyConfigMapUtil.modifyConfigMap(
				company, portalK8sConfigMapModifier, _virtualHostLocalService));
	}

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