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

package com.liferay.portal.search.elasticsearch.cross.cluster.replication.internal.configuration.persistence.listener;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.ccr.CrossClusterReplicationHelper;
import com.liferay.portal.search.elasticsearch.cross.cluster.replication.internal.configuration.CrossClusterReplicationConfiguration;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexResponse;

import java.util.Dictionary;
import java.util.List;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Bryan Engler
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.search.elasticsearch.cross.cluster.replication.internal.configuration.CrossClusterReplicationConfiguration",
	service = ConfigurationModelListener.class
)
public class CrossClusterReplicationConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		String remoteClusterAlias = (String)properties.get(
			"remoteClusterAlias");

		if ((Boolean)properties.get("ccrEnabled")) {
			addRemoteAndFollowIndexes(remoteClusterAlias, properties);
		}
	}

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		String filterString = String.format("(service.pid=%s)", pid);

		Configuration[] configurations = null;

		try {
			configurations = configurationAdmin.listConfigurations(
				filterString);
		}
		catch (Exception exception) {
			throw new ConfigurationModelListenerException(
				exception.getMessage(),
				CrossClusterReplicationConfiguration.class, getClass(),
				properties);
		}

		if (configurations == null) {
			return;
		}

		Configuration configuration = configurations[0];

		Dictionary<String, Object> previousProperties =
			configuration.getProperties();

		String[] excludedIndexes = GetterUtil.getStringValues(
			properties.get("excludedIndexes"));
		boolean ccrEnabled = (Boolean)properties.get("ccrEnabled");
		String[] ccrLocalClusterConnectionConfigurations =
			GetterUtil.getStringValues(
				properties.get("ccrLocalClusterConnectionConfigurations"));
		String remoteClusterAlias = (String)properties.get(
			"remoteClusterAlias");
		String[] previousExcludedIndexes = GetterUtil.getStringValues(
			previousProperties.get("excludedIndexes"));
		boolean previousCcrEnabled = (Boolean)previousProperties.get(
			"ccrEnabled");
		String[] previousCcrLocalClusterConnectionConfigurations =
			GetterUtil.getStringValues(
				previousProperties.get(
					"ccrLocalClusterConnectionConfigurations"));
		String previousRemoteClusterAlias = (String)previousProperties.get(
			"remoteClusterAlias");

		if (previousCcrEnabled &&
			(!ccrEnabled ||
			 (ccrEnabled &&
			  (!equals(
				  previousCcrLocalClusterConnectionConfigurations,
				  ccrLocalClusterConnectionConfigurations) ||
			   !previousRemoteClusterAlias.equals(remoteClusterAlias) ||
			   !equals(previousExcludedIndexes, excludedIndexes))))) {

			unfollowIndexesAndDeleteRemoteCluster(
				previousCcrLocalClusterConnectionConfigurations,
				previousRemoteClusterAlias, previousExcludedIndexes);
		}
	}

	protected void addRemoteAndFollowIndexes(
		String remoteClusterAlias, Dictionary<String, Object> properties) {

		String[] excludedIndexes = GetterUtil.getStringValues(
			properties.get("excludedIndexes"));

		String remoteClusterSeedNodeTransportAddress = (String)properties.get(
			"remoteClusterSeedNodeTransportAddress");

		String[] ccrLocalClusterConnectionConfigurations =
			GetterUtil.getStringValues(
				properties.get("ccrLocalClusterConnectionConfigurations"));

		String[] indexNames = getIndexNames(null);

		for (String ccrLocalClusterConnectionConfiguration :
				ccrLocalClusterConnectionConfigurations) {

			List<String> localClusterConnectionConfigurationParts =
				StringUtil.split(ccrLocalClusterConnectionConfiguration);

			String localClusterConnectionId =
				localClusterConnectionConfigurationParts.get(1);

			crossClusterReplicationHelper.addRemoteCluster(
				remoteClusterAlias, remoteClusterSeedNodeTransportAddress,
				localClusterConnectionId);

			for (String indexName : indexNames) {
				if (indexName.startsWith(StringPool.PERIOD) ||
					isExcludedIndex(indexName, excludedIndexes)) {

					continue;
				}

				crossClusterReplicationHelper.follow(
					remoteClusterAlias, indexName, localClusterConnectionId);
			}
		}
	}

	protected boolean equals(String[] array1, String[] array2) {
		if (ArrayUtil.isEmpty(array1) && ArrayUtil.isEmpty(array2)) {
			return true;
		}

		if (!ArrayUtil.containsAll(array1, array2) ||
			!ArrayUtil.containsAll(array2, array1)) {

			return false;
		}

		return true;
	}

	protected String[] getIndexNames(String connectionId) {
		GetIndexIndexRequest getIndexIndexRequest = new GetIndexIndexRequest(
			StringPool.STAR);

		getIndexIndexRequest.setConnectionId(connectionId);
		getIndexIndexRequest.setPreferLocalCluster(false);

		GetIndexIndexResponse getIndexIndexResponse =
			searchEngineAdapter.execute(getIndexIndexRequest);

		return getIndexIndexResponse.getIndexNames();
	}

	protected boolean isExcludedIndex(
		String indexName, String[] excludedIndexes) {

		return ArrayUtil.contains(excludedIndexes, indexName);
	}

	protected void unfollowIndexesAndDeleteRemoteCluster(
		String[] ccrLocalClusterConnectionConfigurations,
		String remoteClusterAlias, String[] excludedIndexes) {

		for (String ccrLocalClusterConnectionConfiguration :
				ccrLocalClusterConnectionConfigurations) {

			List<String> localClusterConnectionConfigurationParts =
				StringUtil.split(ccrLocalClusterConnectionConfiguration);

			String localClusterConnectionId =
				localClusterConnectionConfigurationParts.get(1);

			try {
				for (String indexName :
						getIndexNames(localClusterConnectionId)) {

					if (indexName.startsWith(StringPool.PERIOD) ||
						isExcludedIndex(indexName, excludedIndexes)) {

						continue;
					}

					crossClusterReplicationHelper.unfollow(
						indexName, localClusterConnectionId);
				}

				crossClusterReplicationHelper.deleteRemoteCluster(
					remoteClusterAlias, localClusterConnectionId);
			}
			catch (RuntimeException runtimeException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to unfollow indexes and/or delete remote " +
							"cluster for connection " +
								localClusterConnectionId,
						runtimeException);
				}
			}
		}
	}

	@Reference
	protected ConfigurationAdmin configurationAdmin;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected CrossClusterReplicationHelper crossClusterReplicationHelper;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	private static final Log _log = LogFactoryUtil.getLog(
		CrossClusterReplicationConfigurationModelListener.class);

}