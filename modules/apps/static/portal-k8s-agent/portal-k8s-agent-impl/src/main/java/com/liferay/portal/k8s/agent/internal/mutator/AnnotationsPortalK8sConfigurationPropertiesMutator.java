/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.k8s.agent.internal.mutator;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.k8s.agent.mutator.PortalK8sConfigurationPropertiesMutator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Dictionary;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;

/**
 * @author Raymond Augé
 */
@Component(service = PortalK8sConfigurationPropertiesMutator.class)
@ServiceRanking(1900)
public class AnnotationsPortalK8sConfigurationPropertiesMutator
	implements PortalK8sConfigurationPropertiesMutator {

	@Override
	public void mutateConfigurationProperties(
		Map<String, String> annotations, Map<String, String> labels,
		Dictionary<String, Object> properties) {

		for (Map.Entry<String, String> entry : annotations.entrySet()) {
			String key = entry.getKey();

			if (key.contains(StringPool.SLASH)) {
				key = StringUtil.replace(key, CharPool.SLASH, CharPool.PERIOD);
			}

			if (Objects.equals(key, "ext.lxc.liferay.com.domains")) {
				properties.put(key, StringUtil.splitLines(entry.getValue()));
			}
			else {
				properties.put(key, entry.getValue());
			}
		}
	}

}