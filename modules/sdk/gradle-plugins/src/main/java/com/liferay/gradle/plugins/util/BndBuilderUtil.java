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

package com.liferay.gradle.plugins.util;

import com.liferay.gradle.plugins.extensions.BundleExtension;
import com.liferay.gradle.plugins.internal.util.GradleUtil;

import java.util.Map;

import org.gradle.api.Project;
import org.gradle.api.UnknownDomainObjectException;
import org.gradle.api.plugins.ExtensionContainer;

/**
 * @author Andrea Di Giorgi
 * @author Raymond Augé
 */
public class BndBuilderUtil {

	public static String getInstruction(Project project, String key) {
		Map<String, Object> bundleInstructions = getInstructions(project);

		return GradleUtil.toString(bundleInstructions.get(key));
	}

	public static BundleExtension getInstructions(Project project) {
		try {
			return GradleUtil.getExtension(project, BundleExtension.class);
		}
		catch (UnknownDomainObjectException udoe) {
			BundleExtension bundleExtension = new BundleExtension();

			ExtensionContainer extensionContainer = project.getExtensions();

			extensionContainer.add(
				BundleExtension.class, "bundle", bundleExtension);

			return bundleExtension;
		}
	}

}