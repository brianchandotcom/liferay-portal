/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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
				serviceBuilderArgs.getResourcesDirName(),
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
			serviceBuilderArgs.getDatabaseNameMaxLength(),
			_getAbsolutePath(baseDir, serviceBuilderArgs.getHbmFileName()),
			_getAbsolutePath(baseDir, serviceBuilderArgs.getImplDirName()),
			serviceBuilderArgs.getIncubationFeatures(),
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
			_getAbsolutePath(baseDir, serviceBuilderArgs.getUADDirName()),
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