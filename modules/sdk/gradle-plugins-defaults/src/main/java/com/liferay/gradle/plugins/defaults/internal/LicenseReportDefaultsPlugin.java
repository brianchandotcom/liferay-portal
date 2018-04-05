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

package com.liferay.gradle.plugins.defaults.internal;

import aQute.bnd.osgi.Constants;

import com.github.jk1.license.LicenseReportPlugin;
import com.github.jk1.license.LicenseReportPlugin.LicenseReportExtension;
import com.github.jk1.license.ModuleData;

import com.liferay.gradle.plugins.LiferayAntPlugin;
import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.defaults.internal.util.VersionsXmlReportRenderer;
import com.liferay.gradle.plugins.defaults.internal.util.XMLUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;

import nebula.plugin.extraconfigurations.ProvidedBasePlugin;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.ExternalModuleDependency;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.bundling.War;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * @author Andrea Di Giorgi
 */
public class LicenseReportDefaultsPlugin implements Plugin<Project> {

	public static final Plugin<Project> INSTANCE =
		new LicenseReportDefaultsPlugin();

	@Override
	public void apply(Project project) {
		GradleUtil.withPlugin(
			project, LiferayAntPlugin.class,
			new AntLicenseReportConfigurator(project));
		GradleUtil.withPlugin(
			project, LiferayOSGiPlugin.class,
			new OSGiLicenseReportConfigurator(project));
	}

	private LicenseReportDefaultsPlugin() {
	}

	private static class AntLicenseReportConfigurator
		extends BaseLicenseReportConfigurator<LiferayAntPlugin> {

		public AntLicenseReportConfigurator(Project project) {
			super(project);
		}

		@Override
		public void execute(LiferayAntPlugin liferayAntPlugin) {
			GradlePluginsDefaultsUtil.configureRepositories(project, null);

			super.execute(liferayAntPlugin);
		}

		@Override
		protected String[] addConfigurations() throws Exception {
			String[] configurationNames = super.addConfigurations();

			File ivyXmlFile = project.file("ivy.xml");

			if (!ivyXmlFile.exists()) {
				return configurationNames;
			}

			DocumentBuilder documentBuilder = XMLUtil.getDocumentBuilder();

			Document document = documentBuilder.parse(ivyXmlFile);

			Element ivyModuleElement = document.getDocumentElement();

			NodeList dependencyNodeList = ivyModuleElement.getElementsByTagName(
				"dependency");

			for (int i = 0; i < dependencyNodeList.getLength(); i++) {
				Element dependencyElement = (Element)dependencyNodeList.item(i);

				String group = dependencyElement.getAttribute("org");
				String name = dependencyElement.getAttribute("name");
				String version = dependencyElement.getAttribute("rev");

				GradleUtil.addDependency(
					project, LICENSE_REPORT_CONFIGURATION_NAME, group, name,
					version);
			}

			return configurationNames;
		}

		@Override
		protected String getArchiveExtension() {
			return War.WAR_EXTENSION;
		}

	}

	private abstract static class
		BaseLicenseReportConfigurator<P extends Plugin<? extends Project>>
			implements Action<P> {

		public static final String LICENSE_REPORT_CONFIGURATION_NAME =
			"licenseReport";

		public BaseLicenseReportConfigurator(Project project) {
			this.project = project;
		}

		@Override
		public void execute(P plugin) {
			GradleUtil.applyPlugin(project, LicenseReportPlugin.class);

			LicenseReportExtension licenseReportExtension =
				GradleUtil.getExtension(project, LicenseReportExtension.class);

			try {
				licenseReportExtension.setConfigurations(addConfigurations());
			}
			catch (IOException ioe) {
				throw new UncheckedIOException(ioe);
			}
			catch (Exception e) {
				throw new GradleException(
					"Unable to configure license report for " + project, e);
			}

			// We need to pass a non-existent group to avoid excluding
			// "com.liferay" dependencies.

			licenseReportExtension.setExcludeGroups(
				String.valueOf(System.currentTimeMillis()));

			String fileName = "versions.xml";

			String outputDir = System.getProperty("license.report.output.dir");

			if (Validator.isNotNull(outputDir)) {
				fileName = project.getName() + ".xml";

				licenseReportExtension.setOutputDir(outputDir);
			}

			licenseReportExtension.setRenderer(
				new ThirdPartyVersionsXmlReportRenderer(
					fileName, licenseReportExtension,
					new Callable<String>() {

						@Override
						public String call() throws Exception {
							return
								GradleUtil.getArchivesBaseName(project) + "." +
									getArchiveExtension();
						}

					}));
		}

		protected String[] addConfigurations() throws Exception {
			Configuration configuration = GradleUtil.addConfiguration(
				project, LICENSE_REPORT_CONFIGURATION_NAME);

			configuration.setDescription(
				"Configures additional dependencies to add to the license " +
					"report.");
			configuration.setTransitive(false);
			configuration.setVisible(false);

			return new String[] {configuration.getName()};
		}

		protected abstract String getArchiveExtension();

		protected final Project project;

	}

	private static class OSGiLicenseReportConfigurator
		extends BaseLicenseReportConfigurator<LiferayOSGiPlugin> {

		public OSGiLicenseReportConfigurator(Project project) {
			super(project);
		}

		@Override
		protected String[] addConfigurations() throws Exception {
			super.addConfigurations();

			final Set<String> dependencyNames = new HashSet<>();

			Map<String, String> bundleInstructions =
				GradlePluginsDefaultsUtil.getBundleInstructions(project);

			_addBundleDependencyNames(
				dependencyNames, bundleInstructions, Constants.INCLUDERESOURCE);
			_addBundleDependencyNames(
				dependencyNames, bundleInstructions,
				Constants.INCLUDE_RESOURCE);

			_addDependenciesLicenseReport(
				JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME, dependencyNames);
			_addDependenciesLicenseReport(
				ProvidedBasePlugin.getPROVIDED_CONFIGURATION_NAME(),
				dependencyNames);

			return new String[] {
				LiferayOSGiPlugin.COMPILE_INCLUDE_CONFIGURATION_NAME,
				LICENSE_REPORT_CONFIGURATION_NAME
			};
		}

		@Override
		protected String getArchiveExtension() {
			return Jar.DEFAULT_EXTENSION;
		}

		private void _addBundleDependencyNames(
			Set<String> dependencyNames, Map<String, String> bundleInstructions,
			String key) {

			String value = bundleInstructions.get(key);

			if (Validator.isNull(value)) {
				return;
			}

			Matcher matcher = _bundleDependencyNamePattern.matcher(value);

			while (matcher.find()) {
				String dependencyName = matcher.group(1);

				dependencyNames.add(dependencyName);
			}
		}

		private void _addDependenciesLicenseReport(
			String configurationName, final Set<String> dependencyNames) {

			Configuration configuration = GradleUtil.getConfiguration(
				project, configurationName);

			DependencySet dependencySet = configuration.getDependencies();

			dependencySet.withType(
				ExternalModuleDependency.class,
				new Action<ExternalModuleDependency>() {

					@Override
					public void execute(
						ExternalModuleDependency externalModuleDependency) {

						if (dependencyNames.contains(
								externalModuleDependency.getName())) {

							GradleUtil.addDependency(
								project, LICENSE_REPORT_CONFIGURATION_NAME,
								externalModuleDependency.getGroup(),
								externalModuleDependency.getName(),
								externalModuleDependency.getVersion());
						}
					}

				});
		}

		private static final Pattern _bundleDependencyNamePattern =
			Pattern.compile("[@=]{1,2}(.+)-\\[0-9\\]\\*\\.jar");

	}

	private static class ThirdPartyVersionsXmlReportRenderer
		extends VersionsXmlReportRenderer {

		public ThirdPartyVersionsXmlReportRenderer(
			String fileName, LicenseReportExtension licenseReportExtension,
			Callable<String> moduleFileNamePrefixCallable) {

			super(
				fileName, licenseReportExtension, moduleFileNamePrefixCallable);
		}

		@Override
		protected boolean isExcluded(ModuleData moduleData) {
			String group = moduleData.getGroup();
			String name = moduleData.getName();

			if ((group.equals("com.liferay") ||
				 group.startsWith("com.liferay.")) &&
				name.startsWith("com.liferay.")) {

				return true;
			}

			return false;
		}

	}

}