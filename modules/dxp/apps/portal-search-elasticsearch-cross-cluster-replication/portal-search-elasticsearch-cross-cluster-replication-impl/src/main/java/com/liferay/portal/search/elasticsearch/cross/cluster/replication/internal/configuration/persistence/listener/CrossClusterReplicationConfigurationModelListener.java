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
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.search.ccr.CrossClusterReplicationHelper;
import com.liferay.portal.search.elasticsearch.cross.cluster.replication.internal.configuration.CrossClusterReplicationConfiguration;
import com.liferay.portal.search.elasticsearch.cross.cluster.replication.internal.helper.CrossClusterReplicationHelperImpl;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexResponse;

import java.util.Dictionary;
import java.util.List;
import java.util.ResourceBundle;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	enabled = false, immediate = true,
	property = "model.class.name=com.liferay.portal.search.elasticsearch.cross.cluster.replication.internal.configuration.CrossClusterReplicationConfiguration",
	service = ConfigurationModelListener.class
)
public class CrossClusterReplicationConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		if (GetterUtil.getBoolean(properties.get("ccrEnabled"))) {
			if (GetterUtil.getBoolean(
					properties.get("automaticReplicationEnabled"))) {

				String remoteClusterAlias = (String)properties.get(
					"remoteClusterAlias");

				_addRemoteAndFollowIndexes(remoteClusterAlias, properties);
			}

			if (_log.isInfoEnabled()) {
				_log.info("Read operations from local clusters are enabled");
			}
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("Read operations from local clusters are disabled");
			}
		}
	}

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		boolean ccrEnabled = GetterUtil.getBoolean(
			properties.get("ccrEnabled"));
		String[] ccrLocalClusterConnectionConfigurations =
			GetterUtil.getStringValues(
				properties.get("ccrLocalClusterConnectionConfigurations"));

		if (ccrEnabled) {
			_validateCCRLocalClusterConnectionConfigurations(
				ccrLocalClusterConnectionConfigurations, properties);
		}

		Configuration[] configurations = null;

		try {
			String filterString = String.format("(service.pid=%s)", pid);

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

		boolean automaticReplicationEnabled = GetterUtil.getBoolean(
			properties.get("automaticReplicationEnabled"));
		String[] excludedIndexes = GetterUtil.getStringValues(
			properties.get("excludedIndexes"));
		boolean previousAutomaticReplicationEnabled = GetterUtil.getBoolean(
			previousProperties.get("automaticReplicationEnabled"), true);
		String[] previousExcludedIndexes = GetterUtil.getStringValues(
			previousProperties.get("excludedIndexes"));
		boolean previousCcrEnabled = GetterUtil.getBoolean(
			previousProperties.get("ccrEnabled"));
		String[] previousCcrLocalClusterConnectionConfigurations =
			GetterUtil.getStringValues(
				previousProperties.get(
					"ccrLocalClusterConnectionConfigurations"));
		String previousRemoteClusterAlias = (String)previousProperties.get(
			"remoteClusterAlias");
		String previousRemoteClusterSeedNodeTransportAddress =
			(String)previousProperties.get(
				"remoteClusterSeedNodeTransportAddress");
		String remoteClusterAlias = (String)properties.get(
			"remoteClusterAlias");
		String remoteClusterSeedNodeTransportAddress = (String)properties.get(
			"remoteClusterSeedNodeTransportAddress");

		if (previousCcrEnabled && previousAutomaticReplicationEnabled &&
			(!ccrEnabled || !automaticReplicationEnabled ||
			 !_equals(
				 previousCcrLocalClusterConnectionConfigurations,
				 ccrLocalClusterConnectionConfigurations) ||
			 !previousRemoteClusterAlias.equals(remoteClusterAlias) ||
			 !previousRemoteClusterSeedNodeTransportAddress.equals(
				 remoteClusterSeedNodeTransportAddress) ||
			 !_equals(previousExcludedIndexes, excludedIndexes))) {

			_unfollowIndexesAndDeleteRemoteCluster(
				previousCcrLocalClusterConnectionConfigurations,
				previousRemoteClusterAlias, previousExcludedIndexes);
		}
	}

	@Reference
	protected ConfigurationAdmin configurationAdmin;

	@Reference
	protected CrossClusterReplicationHelper crossClusterReplicationHelper;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	private void _addRemoteAndFollowIndexes(
		String remoteClusterAlias, Dictionary<String, Object> properties) {

		if (_log.isInfoEnabled()) {
			_log.info("Creating follower indexes");
		}

		Log log = LogFactoryUtil.getLog(
			CrossClusterReplicationHelperImpl.class);

		if (!log.isInfoEnabled()) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"For more information, enable INFO logs on " +
						CrossClusterReplicationHelperImpl.class);
			}
		}

		String[] excludedIndexes = GetterUtil.getStringValues(
			properties.get("excludedIndexes"));

		String remoteClusterSeedNodeTransportAddress = (String)properties.get(
			"remoteClusterSeedNodeTransportAddress");

		String[] ccrLocalClusterConnectionConfigurations =
			GetterUtil.getStringValues(
				properties.get("ccrLocalClusterConnectionConfigurations"));

		String[] indexNames = _getIndexNames(null);

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
					_isExcludedIndex(indexName, excludedIndexes)) {

					continue;
				}

				crossClusterReplicationHelper.follow(
					remoteClusterAlias, indexName, localClusterConnectionId);
			}
		}
	}

	private boolean _equals(String[] array1, String[] array2) {
		if (ArrayUtil.isEmpty(array1) && ArrayUtil.isEmpty(array2)) {
			return true;
		}

		if (!ArrayUtil.containsAll(array1, array2) ||
			!ArrayUtil.containsAll(array2, array1)) {

			return false;
		}

		return true;
	}

	private String[] _getIndexNames(String connectionId) {
		GetIndexIndexRequest getIndexIndexRequest = new GetIndexIndexRequest(
			StringPool.STAR);

		getIndexIndexRequest.setConnectionId(connectionId);
		getIndexIndexRequest.setPreferLocalCluster(false);

		GetIndexIndexResponse getIndexIndexResponse =
			searchEngineAdapter.execute(getIndexIndexRequest);

		return getIndexIndexResponse.getIndexNames();
	}

	private String _getMessage(String key, Object... arguments) {
		try {
			return ResourceBundleUtil.getString(
				_getResourceBundle(), key, arguments);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return null;
		}
	}

	private ResourceBundle _getResourceBundle() {
		return ResourceBundleUtil.getBundle(
			"content.Language", LocaleThreadLocal.getThemeDisplayLocale(),
			getClass());
	}

	private boolean _isExcludedIndex(
		String indexName, String[] excludedIndexes) {

		return ArrayUtil.contains(excludedIndexes, indexName);
	}

	private void _unfollowIndexesAndDeleteRemoteCluster(
		String[] ccrLocalClusterConnectionConfigurations,
		String remoteClusterAlias, String[] excludedIndexes) {

		if (_log.isInfoEnabled()) {
			_log.info("Deleting follower indexes");
		}

		for (String ccrLocalClusterConnectionConfiguration :
				ccrLocalClusterConnectionConfigurations) {

			List<String> localClusterConnectionConfigurationParts =
				StringUtil.split(ccrLocalClusterConnectionConfiguration);

			String localClusterConnectionId =
				localClusterConnectionConfigurationParts.get(1);

			try {
				for (String indexName :
						_getIndexNames(localClusterConnectionId)) {

					if (indexName.startsWith(StringPool.PERIOD) ||
						_isExcludedIndex(indexName, excludedIndexes)) {

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

	private void _validateCCRLocalClusterConnectionConfigurations(
			String[] ccrLocalClusterConnectionConfigurations,
			Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		for (String ccrLocalClusterConnectionConfiguration :
				ccrLocalClusterConnectionConfigurations) {

			List<String> localClusterConnectionConfigurationParts =
				StringUtil.split(ccrLocalClusterConnectionConfiguration);

			if (localClusterConnectionConfigurationParts.size() != 2) {
				throw new ConfigurationModelListenerException(
					_getMessage("please-set-a-hostname-and-connection-id"),
					CrossClusterReplicationConfiguration.class, getClass(),
					properties);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CrossClusterReplicationConfigurationModelListener.class);

}