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

package com.liferay.lpkg.deployer.internal;

import com.liferay.lpkg.deployer.LPKGDeployer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true)
public class LPKGDeployerActivator {

	@Activate
	public void activate(final BundleContext bundleContext) throws IOException {
		String deploymentDir = GetterUtil.getString(
			bundleContext.getProperty("lpkg.deployment.dir"),
			PropsValues.MODULE_FRAMEWORK_BASE_DIR + "marketplace");

		Path deploymentDirPath = Paths.get(deploymentDir);

		if (Files.notExists(deploymentDirPath)) {
			return;
		}

		Files.walkFileTree(
			deploymentDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path fileNamePath = filePath.getFileName();

					String fileName = StringUtil.toLowerCase(
						fileNamePath.toString());

					if (fileName.endsWith(".lpkg")) {
						_lpkgDeployer.deploy(bundleContext, filePath.toFile());
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	@Reference
	private LPKGDeployer _lpkgDeployer;

}