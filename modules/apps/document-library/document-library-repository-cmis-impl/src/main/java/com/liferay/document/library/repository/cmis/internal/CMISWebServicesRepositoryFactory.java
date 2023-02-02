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

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.document.library.repository.cmis.configuration.CMISRepositoryConfiguration;
import com.liferay.document.library.repository.cmis.internal.constants.CMISRepositoryConstants;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.repository.RepositoryFactory;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	configurationPid = "com.liferay.document.library.repository.cmis.configuration.CMISRepositoryConfiguration",
	property = "repository.target.class.name=" + CMISRepositoryConstants.CMIS_WEB_SERVICES_REPOSITORY_CLASS_NAME,
	service = RepositoryFactory.class
)
public class CMISWebServicesRepositoryFactory
	extends BaseCMISRepositoryFactory<CMISWebServicesRepository> {

	@Activate
	protected void activate(Map<String, Object> properties) {
		super.setCMISRepositoryConfiguration(
			ConfigurableUtil.createConfigurable(
				CMISRepositoryConfiguration.class, properties));
	}

	@Override
	protected CMISWebServicesRepository createBaseRepository() {
		return new CMISWebServicesRepository();
	}

}