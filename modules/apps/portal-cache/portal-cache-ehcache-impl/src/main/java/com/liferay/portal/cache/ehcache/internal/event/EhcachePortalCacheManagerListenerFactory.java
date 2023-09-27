/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.cache.ehcache.internal.event;

import com.liferay.portal.cache.PortalCacheManagerListenerFactory;
import com.liferay.portal.cache.ehcache.internal.BaseEhcachePortalCacheManager;
import com.liferay.portal.cache.ehcache.internal.constants.EhcacheConstants;
import com.liferay.portal.kernel.cache.PortalCacheManagerListener;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.Validator;

import java.util.Properties;

import net.sf.ehcache.event.CacheManagerEventListenerFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Tina Tian
 */
@Component(service = PortalCacheManagerListenerFactory.class)
public class EhcachePortalCacheManagerListenerFactory
	implements PortalCacheManagerListenerFactory
		<BaseEhcachePortalCacheManager<?, ?>> {

	@Override
	public PortalCacheManagerListener create(
		BaseEhcachePortalCacheManager<?, ?> baseEhcachePortalCacheManager,
		Properties properties) {

		String className = (String)properties.remove(
			EhcacheConstants.
				CACHE_MANAGER_LISTENER_PROPERTIES_KEY_FACTORY_CLASS_NAME);

		if (Validator.isNull(className)) {
			return null;
		}

		ClassLoader classLoader = (ClassLoader)properties.remove(
			EhcacheConstants.
				CACHE_MANAGER_LISTENER_PROPERTIES_KEY_FACTORY_CLASS_LOADER);

		if (classLoader == null) {
			return null;
		}

		try {
			CacheManagerEventListenerFactory cacheManagerEventListenerFactory =
				(CacheManagerEventListenerFactory)InstanceFactory.newInstance(
					classLoader, className);

			return new EhcachePortalCacheManagerListenerAdapter(
				cacheManagerEventListenerFactory.
					createCacheManagerEventListener(
						baseEhcachePortalCacheManager.getEhcacheManager(),
						properties));
		}
		catch (Exception exception) {
			throw new SystemException(
				"Unable to instantiate cache manager event listener " +
					className,
				exception);
		}
	}

}