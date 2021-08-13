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

import com.github.jk1.license.LicenseReportExtension;
import com.github.jk1.license.LicenseReportPlugin;
import com.github.jk1.license.ModuleData;
import com.github.jk1.license.ReportTask;
import com.github.jk1.license.render.ReportRenderer;

import com.liferay.gradle.plugins.LiferayAntPlugin;
import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.GradlePluginsDefaultsUtil;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.defaults.internal.util.VersionsXmlReportRenderer;
import com.liferay.gradle.plugins.defaults.internal.util.XMLUtil;
import com.liferay.gradle.plugins.extensions.BundleExtension;
import com.liferay.gradle.plugins.util.BndUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;

import java.util.HashSet;
import java.util.Properties;
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
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.bundling.War;
import org.gradle.util.GUtil;

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

	private abstract static class BaseLicenseReportConfigurator
		<P extends Plugin<? extends Project>>
			implements Action<P> {

		public static final String LICENSE_REPORT_CONFIGURATION_NAME =
			"licenseReport";

		public BaseLicenseReportConfigurator(Project project) {
			this.project = project;

			_overridePropertiesFile = _getOverridePropertiesFile();
		}

		@Override
		public void execute(P plugin) {
			GradleUtil.applyPlugin(project, LicenseReportPlugin.class);

			LicenseReportExtension licenseReportExtension =
				GradleUtil.getExtension(project, LicenseReportExtension.class);

			try {
				licenseReportExtension.configurations = addConfigurations();
			}
			catch (IOException ioException) {
				throw new UncheckedIOException(ioException);
			}
			catch (Exception exception) {
				throw new GradleException(
					"Unable to configure license report for " + project,
					exception);
			}

			licenseReportExtension.excludeOwnGroup = false;

			String fileName = "versions.xml";

			String outputDir = System.getProperty("license.report.output.dir");

			if (Validator.isNotNull(outputDir)) {
				fileName = project.getName() + ".xml";

				licenseReportExtension.outputDir = outputDir;
			}

			Properties overrideProperties = new Properties();

			if (_overridePropertiesFile != null) {
				overrideProperties = GUtil.loadProperties(
					_overridePropertiesFile);
			}

			licenseReportExtension.renderers = new ReportRenderer[] {
				new ThirdPartyVersionsXmlReportRenderer(
					fileName, overrideProperties, licenseReportExtension,
					new Callable<String>() {

						@Override
						public String call() throws Exception {
							return GradleUtil.getArchivesBaseName(project) +
								"." + getArchiveExtension();
						}

					})
			};

			if (_overridePropertiesFile != null) {
				ReportTask reportTask = (ReportTask)GradleUtil.getTask(
					project, "generateLicenseReport");

				TaskInputs taskInputs = reportTask.getInputs();

				taskInputs.file(_overridePropertiesFile);
			}
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

		private File _getOverridePropertiesFile() {
			String overridePropertiesFileName = System.getProperty(
				"license.report.override.properties.file");

			if (Validator.isNull(overridePropertiesFileName)) {
				return null;
			}

			File file = new File(overridePropertiesFileName);

			if (!file.exists()) {
				return null;
			}

			return file;
		}

		private final File _overridePropertiesFile;

	}

	private static class OSGiLicenseReportConfigurator
		extends BaseLicenseReportConfigurator<LiferayOSGiPlugin> {

		public OSGiLicenseReportConfigurator(Project project) {
			super(project);
		}

		@Override
		protected String[] addConfigurations() throws Exception {
			super.addConfigurations();

			BundleExtension bundleExtension = BndUtil.getBundleExtension(
				project.getExtensions());

			Set<String> dependencyNames = new HashSet<>();

			_addBundleDependencyNames(
				bundleExtension, dependencyNames, Constants.INCLUDERESOURCE);
			_addBundleDependencyNames(
				bundleExtension, dependencyNames, Constants.INCLUDE_RESOURCE);

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
			BundleExtension bundleExtension, Set<String> dependencyNames,
			String key) {

			String value = bundleExtension.getInstruction(key);

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
			String fileName, Properties overrideProperties,
			LicenseReportExtension licenseReportExtension,
			Callable<String> moduleFileNamePrefixCallable) {

			super(
				fileName, licenseReportExtension, moduleFileNamePrefixCallable);

			_overrideProperties = overrideProperties;
		}

		@Override
		protected String getLicenseName(
			String moduleFileName, ModuleData moduleData) {

			String key = "license.name[" + moduleFileName + "]";

			if (_overrideProperties.containsKey(key)) {
				return _overrideProperties.getProperty(key);
			}

			return super.getLicenseName(moduleFileName, moduleData);
		}

		@Override
		protected String getLicenseUrl(
			String moduleFileName, ModuleData moduleData) {

			String key = "license.url[" + moduleFileName + "]";

			if (_overrideProperties.containsKey(key)) {
				return _overrideProperties.getProperty(key);
			}

			return super.getLicenseUrl(moduleFileName, moduleData);
		}

		@Override
		protected boolean isExcluded(
			String moduleFileName, ModuleData moduleData) {

			String group = moduleData.getGroup();
			String name = moduleData.getName();

			if ((group.equals("com.liferay") ||
				 group.startsWith("com.liferay.")) &&
				name.startsWith("com.liferay.")) {

				return true;
			}

			if (Validator.isNull(getLicenseName(moduleFileName, moduleData))) {
				return true;
			}

			return false;
		}

		private final Properties _overrideProperties;

	}

}