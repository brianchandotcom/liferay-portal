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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelHintsUtil;

import java.io.File;

import java.util.Set;

/**
 * @author Raymond Augé
 */
public class ServiceBuilderInvoker {

	public static ServiceBuilder invoke(
			File baseDir, ServiceBuilderBean serviceBuilderBean)
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
			_getAbsolutePath(baseDir, serviceBuilderBean.getApiDir()),
			serviceBuilderBean.isAutoImportDefaultReferences(),
			serviceBuilderBean.isAutoNamespaceTables(),
			serviceBuilderBean.getBeanLocatorUtil(),
			serviceBuilderBean.getBuildNumber(),
			serviceBuilderBean.isBuildNumberIncrement(),
			_getAbsolutePath(baseDir, serviceBuilderBean.getHbmFileName()),
			_getAbsolutePath(baseDir, serviceBuilderBean.getImplDir()),
			_getAbsolutePath(baseDir, serviceBuilderBean.getInputFileName()),
			_getAbsolutePath(
				baseDir, serviceBuilderBean.getModelHintsFileName()),
			serviceBuilderBean.isOsgiModule(),
			serviceBuilderBean.getPluginName(),
			serviceBuilderBean.getPropsUtil(),
			serviceBuilderBean.getReadOnlyPrefixes(),
			_getAbsolutePath(baseDir, serviceBuilderBean.getRemotingFileName()),
			resourceActionModels,
			_getAbsolutePath(baseDir, serviceBuilderBean.getResourcesDir()),
			_getAbsolutePath(baseDir, serviceBuilderBean.getSpringFileName()),
			serviceBuilderBean.getSpringNamespaces(),
			_getAbsolutePath(baseDir, serviceBuilderBean.getSqlDir()),
			serviceBuilderBean.getSqlFileName(),
			serviceBuilderBean.getSqlIndexesFileName(),
			serviceBuilderBean.getSqlSequencesFileName(),
			serviceBuilderBean.getTargetEntityName(),
			_getAbsolutePath(baseDir, serviceBuilderBean.getTestDir()), true);
	}

	private static String _getAbsolutePath(File baseDir, String fileName) {
		if (Validator.isNull(fileName)) {
			return fileName;
		}

		File file = new File(baseDir, fileName);

		return StringUtil.replace(
			file.getAbsolutePath(), CharPool.BACK_SLASH, CharPool.SLASH);
	}

}