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

package com.liferay.portal.k8s.agent.internal.util;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.k8s.agent.PortalK8sConfigMapModifier;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.VirtualHost;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Raymond Augé
 */
public class CompanyConfigMapUtil {

	public static void clearConfigMap(
		Company company,
		PortalK8sConfigMapModifier portalK8sConfigMapModifier) {

		portalK8sConfigMapModifier.modifyConfigMap(
			configMapModel -> {
				Map<String, String> data = configMapModel.data();

				data.clear();

				Map<String, String> labels = configMapModel.labels();

				labels.put(
					"dxp.lxc.liferay.com/virtualInstanceId",
					company.getWebId());
				labels.put("lxc.liferay.com/metadataType", "dxp");
			},
			getConfigMapName(company));
	}

	public static String getConfigMapName(Company company) {
		return company.getWebId() + "-lxc-dxp-metadata";
	}

	public static void modifyConfigMap(
		Company company, PortalK8sConfigMapModifier portalK8sConfigMapModifier,
		VirtualHostLocalService virtualHostLocalService) {

		List<String> virtualHostNames = new ArrayList<>();

		for (VirtualHost virtualHost :
				virtualHostLocalService.getVirtualHosts(
					company.getCompanyId())) {

			virtualHostNames.add(virtualHost.getHostname());
		}

		portalK8sConfigMapModifier.modifyConfigMap(
			configMapModel -> {
				Map<String, String> data = configMapModel.data();

				data.put(
					"com.liferay.lxc.dxp.domains",
					StringUtil.merge(virtualHostNames, StringPool.NEW_LINE));
				data.put(
					"com.liferay.lxc.dxp.main.domain",
					company.getVirtualHostname());
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
			getConfigMapName(company));
	}

	private static String _getWebServerProtocol() {
		String webServerProtocol = PropsValues.WEB_SERVER_PROTOCOL;

		if (Validator.isNull(webServerProtocol)) {
			return Http.HTTP;
		}

		return webServerProtocol;
	}

}