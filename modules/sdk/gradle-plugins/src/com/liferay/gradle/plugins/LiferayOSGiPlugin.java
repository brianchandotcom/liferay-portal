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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import org.dm.gradle.plugins.bundle.BundleExtension;
import org.dm.gradle.plugins.bundle.BundlePlugin;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.bundling.Jar;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayOSGiPlugin extends LiferayJavaPlugin {

	@Override
	public void apply(Project project) {
		super.apply(project);

		configureBundleExtension(project);
	}

	@Override
	protected void applyConfigScripts(Project project) {
		super.applyConfigScripts(project);

		GradleUtil.applyScript(project, "config-bundle.gradle", project);
	}

	@Override
	protected void applyPlugins(Project project) {
		super.applyPlugins(project);

		GradleUtil.applyPlugin(project, BundlePlugin.class);
	}

	protected void configureBundleExtension(Project project) {
		Map<String, String> bundleInstructions = getBundleInstructions(project);

		Properties bundleProperties;

		try {
			bundleProperties = FileUtil.readProperties(project, "bnd.bnd");
		}
		catch (Exception e) {
			throw new GradleException("Unable to read bundle properties", e);
		}

		Enumeration<Object> keys = bundleProperties.keys();

		while (keys.hasMoreElements()) {
			String key = (String)keys.nextElement();

			String value = bundleProperties.getProperty(key);

			bundleInstructions.put(key, value);
		}
	}

	protected void configureTaskJar(Project project) {
		Jar jar = (Jar)GradleUtil.getTask(project, JavaPlugin.JAR_TASK_NAME);

		configureTaskJarArchiveName(jar);
	}

	protected void configureTaskJarArchiveName(Jar jar) {
		String bundleSymbolicName = getBundleInstruction(
			jar.getProject(), "Bundle-SymbolicName");

		if (Validator.isNull(bundleSymbolicName)) {
			return;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(bundleSymbolicName);
		sb.append("-");
		sb.append(jar.getVersion());
		sb.append(".");
		sb.append(jar.getExtension());

		jar.setArchiveName(sb.toString());
	}

	@Override
	protected void configureTasks(
		Project project, LiferayExtension liferayExtension) {

		super.configureTasks(project, liferayExtension);

		configureTaskJar(project);
	}

	@Override
	protected void configureVersion(
		Project project, LiferayExtension liferayExtension) {

		String bundleVersion = getBundleInstruction(project, "Bundle-Version");

		if (Validator.isNotNull(bundleVersion)) {
			project.setVersion(bundleVersion);

			return;
		}

		super.configureVersion(project, liferayExtension);
	}

	protected String getBundleInstruction(Project project, String key) {
		Map<String, String> bundleInstructions = getBundleInstructions(project);

		return bundleInstructions.get(key);
	}

	protected Map<String, String> getBundleInstructions(Project project) {
		BundleExtension bundleExtension = GradleUtil.getExtension(
			project, BundleExtension.class);

		return (Map<String, String>)bundleExtension.getInstructions();
	}

}