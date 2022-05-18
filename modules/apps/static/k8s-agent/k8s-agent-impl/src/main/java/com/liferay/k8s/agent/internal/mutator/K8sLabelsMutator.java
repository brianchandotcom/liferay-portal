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

package com.liferay.k8s.agent.internal.mutator;

import com.liferay.k8s.agent.constants.K8sAgentConstants;
import com.liferay.k8s.agent.mutator.K8sConfigurationPropertiesMutator;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.util.Dictionary;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceRanking;

/**
 * @author Raymond Augé
 */
@Component(immediate = true, service = K8sConfigurationPropertiesMutator.class)
@ServiceRanking(2000)
public class K8sLabelsMutator implements K8sConfigurationPropertiesMutator {

	@Override
	public void mutateConfigurationProperties(
		Map<String, String> annotations, Map<String, String> labels,
		Dictionary<String, Object> properties) {

		for (Map.Entry<String, String> entry : labels.entrySet()) {
			String key = entry.getKey();

			String modifiedKey = K8sAgentConstants.K8S_PROPERTY_KEY.concat(key);

			if (key.contains(StringPool.SLASH)) {
				modifiedKey = StringUtil.replace(
					key, CharPool.SLASH, CharPool.PERIOD);
			}

			properties.put(modifiedKey, entry.getValue());
		}
	}

}