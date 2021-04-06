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

package com.liferay.portal.workflow.kaleo.designer.web.internal.configuration;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Feliphe Marinho
 */
@Component(
	configurationPid = "com.liferay.portal.workflow.kaleo.designer.web.internal.configuration.FFKaleoDesignerConfiguration",
	immediate = true, service = {}
)
public class FFKaleoDesignerConfigurationUtil {

	public static boolean newKaleoDesignerEnabled() {
		return _ffKaleoDesignerConfiguration.newKaleoDesignerEnabled();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffKaleoDesignerConfiguration = ConfigurableUtil.createConfigurable(
			FFKaleoDesignerConfiguration.class, properties);
	}

	private static volatile FFKaleoDesignerConfiguration
		_ffKaleoDesignerConfiguration;

}