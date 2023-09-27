/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.configurator;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.cache.PortalCacheReplicator;
import com.liferay.portal.cache.configuration.PortalCacheConfiguration;
import com.liferay.portal.cache.configuration.PortalCacheManagerConfiguration;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dante Wang
 */
@Component(service = MultiVMEhcachePortalCacheManagerConfigurator.class)
public class MultiVMEhcachePortalCacheManagerConfigurator
	extends BaseEhcachePortalCacheManagerConfigurator {

	@Activate
	protected void activate() {
		clusterEnabled = GetterUtil.getBoolean(
			_props.get(PropsKeys.CLUSTER_LINK_ENABLED));
		_defaultReplicatorPropertiesString = _getPortalPropertiesString(
			PropsKeys.EHCACHE_REPLICATOR_PROPERTIES_DEFAULT);
		_replicatorProperties = _props.getProperties(
			PropsKeys.EHCACHE_REPLICATOR_PROPERTIES + StringPool.PERIOD, true);
	}

	@Override
	protected boolean isRequireSerialization(
		CacheConfiguration cacheConfiguration) {

		if (clusterEnabled) {
			return true;
		}

		return super.isRequireSerialization(cacheConfiguration);
	}

	@Override
	protected void manageConfiguration(
		Configuration configuration,
		PortalCacheManagerConfiguration portalCacheManagerConfiguration) {

		if (!clusterEnabled) {
			return;
		}

		Map<String, ObjectValuePair<Properties, Properties>>
			mergedPropertiesMap = _getMergedPropertiesMap();

		for (Map.Entry<String, ObjectValuePair<Properties, Properties>> entry :
				mergedPropertiesMap.entrySet()) {

			ObjectValuePair<Properties, Properties> propertiesPair =
				entry.getValue();

			if (propertiesPair.getValue() != null) {
				PortalCacheConfiguration portalCacheConfiguration =
					portalCacheManagerConfiguration.getPortalCacheConfiguration(
						entry.getKey());

				Set<Properties> portalCacheListenerPropertiesSet =
					portalCacheConfiguration.
						getPortalCacheListenerPropertiesSet();

				Iterator<Properties> iterator =
					portalCacheListenerPropertiesSet.iterator();

				while (iterator.hasNext()) {
					Properties properties = iterator.next();

					if ((Boolean)properties.get(
							PortalCacheReplicator.REPLICATOR)) {

						iterator.remove();
					}
				}

				portalCacheListenerPropertiesSet.add(propertiesPair.getValue());
			}
		}
	}

	@Override
	protected PortalCacheConfiguration parseCacheListenerConfigurations(
		CacheConfiguration cacheConfiguration, ClassLoader classLoader,
		boolean usingDefault) {

		PortalCacheConfiguration portalCacheConfiguration =
			super.parseCacheListenerConfigurations(
				cacheConfiguration, classLoader, usingDefault);

		if (!clusterEnabled) {
			return portalCacheConfiguration;
		}

		String cacheName = cacheConfiguration.getName();

		String replicatorPropertiesString =
			(String)_replicatorProperties.remove(cacheName);

		if (Validator.isNull(replicatorPropertiesString)) {
			replicatorPropertiesString = _defaultReplicatorPropertiesString;
		}

		Properties replicatorProperties = parseProperties(
			replicatorPropertiesString, StringPool.COMMA);

		replicatorProperties.put(PortalCacheReplicator.REPLICATOR, true);

		Set<Properties> portalCacheListenerPropertiesSet =
			portalCacheConfiguration.getPortalCacheListenerPropertiesSet();

		portalCacheListenerPropertiesSet.add(replicatorProperties);

		return portalCacheConfiguration;
	}

	protected boolean clusterEnabled;

	private Map<String, ObjectValuePair<Properties, Properties>>
		_getMergedPropertiesMap() {

		Map<String, ObjectValuePair<Properties, Properties>>
			mergedPropertiesMap = new HashMap<>();

		for (String portalCacheName :
				_replicatorProperties.stringPropertyNames()) {

			Properties replicatorProperties = parseProperties(
				_replicatorProperties.getProperty(portalCacheName),
				StringPool.COMMA);

			replicatorProperties.put(PortalCacheReplicator.REPLICATOR, true);

			ObjectValuePair<Properties, Properties> objectValuePair =
				mergedPropertiesMap.get(portalCacheName);

			if (objectValuePair == null) {
				mergedPropertiesMap.put(
					portalCacheName,
					new ObjectValuePair(null, replicatorProperties));
			}
			else {
				objectValuePair.setValue(replicatorProperties);
			}
		}

		return mergedPropertiesMap;
	}

	private String _getPortalPropertiesString(String portalPropertyKey) {
		String[] array = _props.getArray(portalPropertyKey);

		if (array.length == 0) {
			return null;
		}

		if (array.length == 1) {
			return array[0];
		}

		StringBundler sb = new StringBundler(array.length * 2);

		for (String value : array) {
			sb.append(value);
			sb.append(StringPool.COMMA);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private String _defaultReplicatorPropertiesString;

	@Reference
	private Props _props;

	private Properties _replicatorProperties;

}