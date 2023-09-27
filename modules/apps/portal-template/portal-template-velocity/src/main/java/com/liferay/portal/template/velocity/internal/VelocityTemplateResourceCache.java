/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.template.velocity.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.template.BaseTemplateResourceCache;
import com.liferay.portal.template.velocity.configuration.VelocityEngineConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.portal.template.velocity.configuration.VelocityEngineConfiguration",
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
			_PORTAL_CACHE_NAME,
			StringBundler.concat(
				TemplateResource.class.getName(), StringPool.POUND,
				TemplateConstants.LANG_TYPE_VM));
	}

	@Deactivate
	protected void deactivate() {
		destroy();
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		VelocityEngineConfiguration velocityEngineConfiguration =
			ConfigurableUtil.createConfigurable(
				VelocityEngineConfiguration.class, properties);

		setModificationCheckInterval(
			velocityEngineConfiguration.resourceModificationCheckInterval());
	}

	private static final String _PORTAL_CACHE_NAME =
		VelocityTemplateResourceCache.class.getName();

}