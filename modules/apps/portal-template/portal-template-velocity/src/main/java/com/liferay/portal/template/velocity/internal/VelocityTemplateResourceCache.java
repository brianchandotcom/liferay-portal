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

package com.liferay.portal.template.velocity.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.template.BaseTemplateResourceCache;
import com.liferay.portal.template.velocity.configuration.VelocityEngineConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.template.velocity.configuration.VelocityEngineConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = VelocityTemplateResourceCache.class
)
public class VelocityTemplateResourceCache extends BaseTemplateResourceCache {

	@Activate
	protected void activate(Map<String, Object> properties) {
		VelocityEngineConfiguration velocityEngineConfiguration =
			ConfigurableUtil.createConfigurable(
				VelocityEngineConfiguration.class, properties);

		init(
			velocityEngineConfiguration.resourceModificationCheckInterval(),
			_multiVMPool, _singleVMPool, _PORTAL_CACHE_NAME);
	}

	@Deactivate
	protected void deactivate() {
		destroy();
	}

	private static final String _PORTAL_CACHE_NAME =
		VelocityTemplateResourceCache.class.getName();

	@Reference
	private MultiVMPool _multiVMPool;

	@Reference
	private SingleVMPool _singleVMPool;

}