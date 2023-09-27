/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.k8s.agent.internal.mutator;

import com.liferay.portal.k8s.agent.mutator.PortalK8sConfigurationPropertiesMutator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;

/**
 * @author Raymond Augé
 */
@Component(service = PortalK8sConfigurationPropertiesMutator.class)
@ServiceRanking(1800)
public class BaseURLPortalK8sConfigurationPropertiesMutator
	implements PortalK8sConfigurationPropertiesMutator {

	@Override
	public void mutateConfigurationProperties(
		Map<String, String> annotations, Map<String, String> labels,
		Dictionary<String, Object> properties) {

		String mainDomain = GetterUtil.getString(
			annotations.get("ext.lxc.liferay.com/mainDomain"));

		if (Validator.isNotNull(mainDomain)) {
			properties.put(
				"baseURL", "$[conf:.serviceScheme]://$[conf:.serviceAddress]");
			properties.put(".serviceAddress", mainDomain);
			properties.put(".serviceScheme", Http.HTTPS);
		}
	}

}