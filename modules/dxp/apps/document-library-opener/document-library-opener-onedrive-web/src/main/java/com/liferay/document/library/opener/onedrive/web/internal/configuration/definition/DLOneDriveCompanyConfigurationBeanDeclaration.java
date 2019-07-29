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

package com.liferay.document.library.opener.onedrive.web.internal.configuration.definition;

import com.liferay.document.library.opener.onedrive.web.internal.configuration.DLOneDriveCompanyConfiguration;
import com.liferay.portal.kernel.settings.definition.ConfigurationBeanDeclaration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Cristina González
 */
@Component(service = ConfigurationBeanDeclaration.class)
public class DLOneDriveCompanyConfigurationBeanDeclaration
	implements ConfigurationBeanDeclaration {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return DLOneDriveCompanyConfiguration.class;
	}

}