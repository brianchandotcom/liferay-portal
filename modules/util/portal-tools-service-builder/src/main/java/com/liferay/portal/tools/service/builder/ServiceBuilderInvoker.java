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
			File baseDir, ServiceBuilderArgs serviceBuilderArgs)
		throws Exception {

		Set<String> resourceActionModels =
			ServiceBuilder.readResourceActionModels(
				serviceBuilderArgs.getApiDirName(),
				serviceBuilderArgs.getResourceActionsConfigs());

		ModelHintsImpl modelHintsImpl = new ModelHintsImpl();

		modelHintsImpl.setModelHintsConfigs(
			serviceBuilderArgs.getModelHintsConfigs());

		modelHintsImpl.afterPropertiesSet();

		ModelHintsUtil modelHintsUtil = new ModelHintsUtil();

		modelHintsUtil.setModelHints(modelHintsImpl);

		return new ServiceBuilder(
			_getAbsolutePath(baseDir, serviceBuilderArgs.getApiDirName()),
			serviceBuilderArgs.isAutoImportDefaultReferences(),
			serviceBuilderArgs.isAutoNamespaceTables(),
			serviceBuilderArgs.getBeanLocatorUtil(),
			serviceBuilderArgs.getBuildNumber(),
			serviceBuilderArgs.isBuildNumberIncrement(),
			_getAbsolutePath(baseDir, serviceBuilderArgs.getHbmFileName()),
			_getAbsolutePath(baseDir, serviceBuilderArgs.getImplDirName()),
			_getAbsolutePath(baseDir, serviceBuilderArgs.getInputFileName()),
			_getAbsolutePath(
				baseDir, serviceBuilderArgs.getModelHintsFileName()),
			serviceBuilderArgs.isOsgiModule(),
			serviceBuilderArgs.getPluginName(),
			serviceBuilderArgs.getPropsUtil(),
			serviceBuilderArgs.getReadOnlyPrefixes(), resourceActionModels,
			_getAbsolutePath(baseDir, serviceBuilderArgs.getResourcesDirName()),
			_getAbsolutePath(baseDir, serviceBuilderArgs.getSpringFileName()),
			serviceBuilderArgs.getSpringNamespaces(),
			_getAbsolutePath(baseDir, serviceBuilderArgs.getSqlDirName()),
			serviceBuilderArgs.getSqlFileName(),
			serviceBuilderArgs.getSqlIndexesFileName(),
			serviceBuilderArgs.getSqlSequencesFileName(),
			serviceBuilderArgs.getTargetEntityName(),
			_getAbsolutePath(baseDir, serviceBuilderArgs.getTestDirName()),
			true);
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