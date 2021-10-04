/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.elasticsearch.cross.cluster.replication.internal.helper;

import com.liferay.petra.string.StringUtil;
import com.liferay.portal.search.ccr.CrossClusterReplicationConfigurationHelper;
import com.liferay.portal.search.elasticsearch.cross.cluster.replication.internal.configuration.CrossClusterReplicationConfigurationWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	enabled = false, immediate = true,
	service = CrossClusterReplicationConfigurationHelper.class
)
public class CrossClusterReplicationConfigurationHelperImpl
	implements CrossClusterReplicationConfigurationHelper {

	@Override
	public List<String> getLocalClusterConnectionIds() {
		List<String> connectionIds = new ArrayList<>();

		String[] localClusterConnectionConfigurations =
			crossClusterReplicationConfigurationWrapper.
				getCCRLocalClusterConnectionConfigurations();

		for (String localClusterConnectionConfiguration :
				localClusterConnectionConfigurations) {

			List<String> localClusterConnectionConfigurationParts =
				StringUtil.split(localClusterConnectionConfiguration);

			connectionIds.add(localClusterConnectionConfigurationParts.get(1));
		}

		return connectionIds;
	}

	@Override
	public Map<String, String> getLocalClusterConnectionIdsMap() {
		Map<String, String> connectionIds = new HashMap<>();

		String[] localClusterConnectionConfigurations =
			crossClusterReplicationConfigurationWrapper.
				getCCRLocalClusterConnectionConfigurations();

		for (String localClusterConnectionConfiguration :
				localClusterConnectionConfigurations) {

			List<String> localClusterConnectionConfigurationParts =
				StringUtil.split(localClusterConnectionConfiguration);

			String hostName = localClusterConnectionConfigurationParts.get(0);
			String connectionId = localClusterConnectionConfigurationParts.get(
				1);

			connectionIds.put(hostName, connectionId);
		}

		return connectionIds;
	}

	@Override
	public boolean isCrossClusterReplicationEnabled() {
		return crossClusterReplicationConfigurationWrapper.isCCREnabled();
	}

	@Reference
	protected volatile CrossClusterReplicationConfigurationWrapper
		crossClusterReplicationConfigurationWrapper;

}