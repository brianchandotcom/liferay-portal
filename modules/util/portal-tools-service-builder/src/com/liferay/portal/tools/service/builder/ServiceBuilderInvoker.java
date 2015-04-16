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

package com.liferay.portal.tools.service.builder;

import com.liferay.portal.model.ModelHintsUtil;

import java.util.Set;

/**
 * @author Raymond Augé
 */
public class ServiceBuilderInvoker {

	public static ServiceBuilder invoke(ServiceBuilderBean serviceBuilderBean)
		throws Exception {

		Set<String> resourceActionModels =
			ServiceBuilder.readResourceActionModels(
				serviceBuilderBean.getApiDir(),
				serviceBuilderBean.getResourceActionsConfigs());

		ModelHintsImpl modelHintsImpl = new ModelHintsImpl();

		modelHintsImpl.setModelHintsConfigs(
			serviceBuilderBean.getModelHintsConfigs());
		modelHintsImpl.afterPropertiesSet();

		ModelHintsUtil modelHintsUtil = new ModelHintsUtil();

		modelHintsUtil.setModelHints(modelHintsImpl);

		return new ServiceBuilder(
			serviceBuilderBean.getApiDir(),
			serviceBuilderBean.isAutoImportDefaultReferences(),
			serviceBuilderBean.isAutoNamespaceTables(),
			serviceBuilderBean.getBeanLocatorUtil(),
			serviceBuilderBean.getBuildNumber(),
			serviceBuilderBean.isBuildNumberIncrement(),
			serviceBuilderBean.getHbmFileName(),
			serviceBuilderBean.getImplDir(),
			serviceBuilderBean.getInputFileName(),
			serviceBuilderBean.getModelHintsFileName(),
			serviceBuilderBean.isOsgiModule(),
			serviceBuilderBean.getPluginName(),
			serviceBuilderBean.getPropsUtil(),
			serviceBuilderBean.getReadOnlyPrefixes(),
			serviceBuilderBean.getRemotingFileName(), resourceActionModels,
			serviceBuilderBean.getResourcesDir(),
			serviceBuilderBean.getSpringFileName(),
			serviceBuilderBean.getSpringNamespaces(),
			serviceBuilderBean.getSqlDir(), serviceBuilderBean.getSqlFileName(),
			serviceBuilderBean.getSqlIndexesFileName(),
			serviceBuilderBean.getSqlSequencesFileName(),
			serviceBuilderBean.getTargetEntityName(),
			serviceBuilderBean.getTestDir(), true);
	}

}