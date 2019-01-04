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

package com.liferay.portal.cache.ehcache.internal;

import com.liferay.portal.cache.PortalCacheListenerFactory;
import com.liferay.portal.cache.PortalCacheManagerListenerFactory;
import com.liferay.portal.cache.ehcache.internal.configurator.SingleVMEhcachePortalCacheManagerConfigurator;
import com.liferay.portal.kernel.cache.PortalCacheManager;
import com.liferay.portal.kernel.cache.PortalCacheManagerNames;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;

import java.io.Serializable;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(
	immediate = true,
	property = PortalCacheManager.PORTAL_CACHE_MANAGER_NAME + "=" + PortalCacheManagerNames.SINGLE_VM,
	service = PortalCacheManager.class
)
public class SingleVMEhcachePortalCacheManager<K extends Serializable, V>
	extends EhcachePortalCacheManager<K, V> {

	@Activate
	protected void activate(BundleContext bundleContext) {
		this.bundleContext = bundleContext;

		setConfigFile(props.get(PropsKeys.EHCACHE_SINGLE_VM_CONFIG_LOCATION));
		setDefaultConfigFile(_DEFAULT_CONFIG_FILE_NAME);
		setPortalCacheManagerName(PortalCacheManagerNames.SINGLE_VM);

		initialize();

		if (_log.isDebugEnabled()) {
			_log.debug("Activated " + PortalCacheManagerNames.SINGLE_VM);
		}
	}

	@Deactivate
	protected void deactivate() {
		destroy();
	}

	@Reference(unbind = "-")
	protected void setPortalCacheListenerFactory(
		PortalCacheListenerFactory portalCacheListenerFactory) {

		this.portalCacheListenerFactory = portalCacheListenerFactory;
	}

	@Reference(unbind = "-")
	protected void setPortalCacheManagerListenerFactory(
		PortalCacheManagerListenerFactory<PortalCacheManager<K, V>>
			portalCacheManagerListenerFactory) {

		this.portalCacheManagerListenerFactory =
			portalCacheManagerListenerFactory;
	}

	@Reference(unbind = "-")
	protected void setProps(Props props) {
		this.props = props;
	}

	@Reference(unbind = "-")
	protected void setSingleVMEhcachePortalCacheManagerConfigurator(
		SingleVMEhcachePortalCacheManagerConfigurator
			singleVMEhcachePortalCacheManagerConfigurator) {

		baseEhcachePortalCacheManagerConfigurator =
			singleVMEhcachePortalCacheManagerConfigurator;
	}

	private static final String _DEFAULT_CONFIG_FILE_NAME =
		"/ehcache/liferay-single-vm.xml";

	private static final Log _log = LogFactoryUtil.getLog(
		SingleVMEhcachePortalCacheManager.class);

}