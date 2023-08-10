/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.k8s.agent.internal.model.listener;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.k8s.agent.PortalK8sConfigMapModifier;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.portal.k8s.agent.configuration.PortalK8sAgentConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE,
	service = ModelListener.class
)
public class VirtualHostModelListener extends BaseModelListener<VirtualHost> {

	@Override
	public void onAfterCreate(VirtualHost virtualHost) {
		Company company = _companyLocalService.fetchCompanyById(
			virtualHost.getCompanyId());

		_modifyConfigMap(company);
	}

	@Override
	public void onAfterRemove(VirtualHost virtualHost)
		throws ModelListenerException {

		Company company = _companyLocalService.fetchCompanyById(
			virtualHost.getCompanyId());

		if (Objects.equals(
				company.getWebId(), PropsValues.COMPANY_DEFAULT_WEB_ID)) {

			return;
		}

		_portalK8sConfigMapModifier.modifyConfigMap(
			configMapModel -> {
				Map<String, String> data = configMapModel.data();

				data.clear();

				Map<String, String> labels = configMapModel.labels();

				labels.clear();
			},
			_getConfigMapName(company));
	}

	@Override
	public void onAfterUpdate(
			VirtualHost originalVirtualHost, VirtualHost virtualHost)
		throws ModelListenerException {

		Company company = _companyLocalService.fetchCompanyById(
			virtualHost.getCompanyId());

		_modifyConfigMap(company);
	}

	@Activate
	protected void activate() {
		_companyLocalService.forEachCompany(this::_modifyConfigMap);
	}

	private String _getConfigMapName(Company company) {
		return company.getWebId() + "-lxc-dxp-metadata";
	}

	private String _getWebServerProtocol() {
		String webServerProtocol = PropsValues.WEB_SERVER_PROTOCOL;

		if (Validator.isNull(webServerProtocol)) {
			return Http.HTTP;
		}

		return webServerProtocol;
	}

	private void _modifyConfigMap(Company company) {
		List<String> virtualHostNames = new ArrayList<>();

		for (VirtualHost virtualHost :
				_virtualHostLocalService.getVirtualHosts(
					company.getCompanyId())) {

			virtualHostNames.add(virtualHost.getHostname());
		}

		_portalK8sConfigMapModifier.modifyConfigMap(
			configMapModel -> {
				Map<String, String> data = configMapModel.data();

				data.put(
					"com.liferay.lxc.dxp.domains",
					StringUtil.merge(virtualHostNames, StringPool.NEW_LINE));
				data.put(
					"com.liferay.lxc.dxp.mainDomain",
					company.getVirtualHostname());
				data.put(
					"com.liferay.lxc.dxp.server.protocol",
					_getWebServerProtocol());

				Map<String, String> labels = configMapModel.labels();

				labels.put(
					"dxp.lxc.liferay.com/virtualInstanceId",
					company.getWebId());
				labels.put("lxc.liferay.com/metadataType", "dxp");
			},
			_getConfigMapName(company));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private PortalK8sConfigMapModifier _portalK8sConfigMapModifier;

	@Reference
	private VirtualHostLocalService _virtualHostLocalService;

}