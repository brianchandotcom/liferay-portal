/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.indexer;

import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.spi.model.registrar.ModelSearchConfigurator;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;
import com.liferay.portal.search.spi.model.registrar.contributor.ModelSearchDefinitionContributor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;

/**
 * @author André de Oliveira
 */
@Component(service = ModelSearchRegistrarHelper.class)
public class ModelSearchRegistrarHelperImpl
	implements ModelSearchRegistrarHelper {

	@Override
	public ServiceRegistration<?> register(
		Class<? extends BaseModel<?>> clazz, BundleContext bundleContext,
		ModelSearchDefinitionContributor modelSearchDefinitionContributor) {

		return register(
			clazz.getName(), bundleContext, modelSearchDefinitionContributor);
	}

	@Override
	public ServiceRegistration<?> register(
		String className, BundleContext bundleContext,
		ModelSearchDefinitionContributor modelSearchDefinitionContributor) {

		ModelSearchConfigurator<?> modelSearchConfigurator =
			new ModelSearchConfiguratorImpl<>(className);

		modelSearchDefinitionContributor.contribute(modelSearchConfigurator);

		return bundleContext.registerService(
			ModelSearchConfigurator.class, modelSearchConfigurator,
			MapUtil.singletonDictionary("indexer.class.name", className));
	}

}