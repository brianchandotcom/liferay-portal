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

package com.liferay.frontend.js.loader.modules.extender.internal;

import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSModuleAlias;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.felix.utils.log.Logger;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.frontend.js.loader.modules.extender.internal.Details",
	immediate = true,
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.frontend.js.loader.modules.extender.internal.JSLoaderModulesServlet",
		"osgi.http.whiteboard.servlet.pattern=/js_loader_modules",
		"service.ranking:Integer=" + Details.MAX_VALUE_LESS_1K
	},
	service = {JSLoaderModulesServlet.class, Servlet.class}
)
public class JSLoaderModulesServlet extends HttpServlet {

	public JSLoaderModulesServlet() {
		_dependencyAliases.put("exports", "E");
		_dependencyAliases.put("module", "M");
		_dependencyAliases.put("require", "R");
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		_componentContext.enableComponent(
			JSLoaderModulesPortalWebResources.class.getName());
	}

	@Activate
	@Modified
	protected void activate(
			ComponentContext componentContext, Map<String, Object> properties)
		throws Exception {

		_details = ConfigurableUtil.createConfigurable(
			Details.class, properties);

		_logger = new Logger(componentContext.getBundleContext());

		_componentContext = componentContext;
	}

	protected JSLoaderModulesTracker getJSLoaderModulesTracker() {
		return _jsLoaderModulesTracker;
	}

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		StringWriter stringWriter = new StringWriter();

		PrintWriter printWriter = new PrintWriter(stringWriter);

		printWriter.println("(function() {");

		_writePaths(printWriter);

		_writeModules(printWriter);

		_writeMaps(printWriter);

		printWriter.println(
			"Liferay.EXPLAIN_RESOLUTIONS = " + _details.explainResolutions() +
				";\n");

		printWriter.println(
			"Liferay.EXPOSE_GLOBAL = " + _details.exposeGlobal() + ";\n");

		printWriter.println(
			"Liferay.WAIT_TIMEOUT = " + (_details.waitTimeout() * 1000) +
				";\n");

		printWriter.println("}());");

		printWriter.close();

		_writeResponse(response, stringWriter.toString());
	}

	protected void setDetails(Details details) {
		_details = details;
	}

	@Reference(unbind = "-")
	protected void setJSLoaderModulesTracker(
		JSLoaderModulesTracker jsLoaderModulesTracker) {

		_jsLoaderModulesTracker = jsLoaderModulesTracker;
	}

	@Reference(unbind = "-")
	protected void setNPMRegistry(NPMRegistry npmRegistry) {
		_npmRegistry = npmRegistry;
	}

	private String _applyDependencyAliases(String dependency) {
		String dependencyAlias = _dependencyAliases.get(dependency);

		if (dependencyAlias == null) {
			return "\"" + dependency + "\"";
		}

		return dependencyAlias;
	}

	private void _writeMaps(PrintWriter printWriter) {
		printWriter.println("Liferay.MAPS = {");

		String delimiter = "";
		Set<String> processedNames = new HashSet<>();

		for (JSLoaderModule jsLoaderModule :
				_jsLoaderModulesTracker.getJSLoaderModules()) {

			if (processedNames.contains(jsLoaderModule.getName())) {
				continue;
			}

			processedNames.add(jsLoaderModule.getName());

			printWriter.write(delimiter);
			printWriter.write("\"");
			printWriter.write(jsLoaderModule.getName());
			printWriter.write("\": \"");
			printWriter.write(jsLoaderModule.getName());
			printWriter.write("@");
			printWriter.write(jsLoaderModule.getVersion());
			printWriter.write("\"");

			delimiter = ",\n";

			String unversionedMapsConfiguration =
				jsLoaderModule.getUnversionedMapsConfiguration();

			if (!unversionedMapsConfiguration.equals("")) {
				printWriter.write(delimiter);
				printWriter.write(unversionedMapsConfiguration);
			}
		}

		for (JSPackage jsPackage : _npmRegistry.getResolvedJSPackages()) {
			printWriter.write(delimiter);
			printWriter.write("\"");
			printWriter.write(jsPackage.getResolvedId());
			printWriter.write("\": {exactMatch: true, value: \"");
			printWriter.write(jsPackage.getResolvedId());
			printWriter.write(StringPool.SLASH);
			printWriter.write(jsPackage.getMainModuleName());
			printWriter.write("\"}");

			delimiter = ",\n";

			for (JSModuleAlias jsModuleAlias : jsPackage.getJSModuleAliases()) {
				printWriter.write(delimiter);
				printWriter.write("\"");
				printWriter.write(jsPackage.getResolvedId());
				printWriter.write(StringPool.SLASH);
				printWriter.write(jsModuleAlias.getAlias());
				printWriter.write("\": {exactMatch: true, value: \"");
				printWriter.write(jsPackage.getResolvedId());
				printWriter.write(StringPool.SLASH);
				printWriter.write(jsModuleAlias.getModuleName());
				printWriter.write("\"}");
			}
		}

		Map<String, String> globalAliases = _npmRegistry.getGlobalAliases();

		for (Map.Entry<String, String> alias : globalAliases.entrySet()) {
			printWriter.write(delimiter);
			printWriter.write(StringPool.QUOTE);
			printWriter.write(alias.getKey());
			printWriter.write(StringPool.QUOTE);
			printWriter.write(StringPool.COLON);
			printWriter.write(StringPool.QUOTE);
			printWriter.write(alias.getValue());
			printWriter.write(StringPool.QUOTE);

			delimiter = ",\n";
		}

		printWriter.println("\n};");
	}

	private void _writeModules(PrintWriter printWriter) {
		String delimiter = "";

		printWriter.write("var ");

		for (Map.Entry<String, String> entry : _dependencyAliases.entrySet()) {
			printWriter.write(delimiter);
			printWriter.write(entry.getValue());
			printWriter.write("=\"");
			printWriter.write(entry.getKey());
			printWriter.write("\"");

			delimiter = ",";
		}

		printWriter.write(";\n");

		printWriter.println("Liferay.MODULES = {");

		Set<String> processedNames = new HashSet<>();

		delimiter = "";

		for (JSLoaderModule jsLoaderModule :
				_jsLoaderModulesTracker.getJSLoaderModules()) {

			String unversionedConfiguration =
				jsLoaderModule.getUnversionedConfiguration();

			if (unversionedConfiguration.length() == 0) {
				continue;
			}

			if (!processedNames.contains(jsLoaderModule.getName())) {
				processedNames.add(jsLoaderModule.getName());

				printWriter.write(delimiter);
				printWriter.write(unversionedConfiguration);

				delimiter = ",\n";
			}

			String versionedConfiguration =
				jsLoaderModule.getVersionedConfiguration();

			if (versionedConfiguration.length() > 0) {
				printWriter.write(delimiter);
				printWriter.write(versionedConfiguration);

				delimiter = ",\n";
			}
		}

		String delimiter2 = "";

		for (JSModule resolvedJSModule : _npmRegistry.getResolvedJSModules()) {
			printWriter.write(delimiter);
			printWriter.write("\"");
			printWriter.write(resolvedJSModule.getResolvedId());
			printWriter.write("\": {\n");

			delimiter2 = "";

			printWriter.write("  \"dependencies\": [");

			for (String dependency : resolvedJSModule.getDependencies()) {
				printWriter.write(delimiter2);
				printWriter.write(_applyDependencyAliases(dependency));

				delimiter2 = ", ";
			}

			printWriter.write("],\n");

			JSPackage jsPackage = resolvedJSModule.getJSPackage();

			delimiter2 = "";

			printWriter.write("  \"map\": {");

			for (String dependencyPackageName :
					resolvedJSModule.getDependencyPackageNames()) {

				if (dependencyPackageName == null) {
					continue;
				}

				printWriter.write(delimiter2);

				StringBundler aliasSB = new StringBundler(1);
				StringBundler aliasValueSB = new StringBundler();

				if (dependencyPackageName.equals(jsPackage.getName())) {
					aliasSB.append(dependencyPackageName);

					aliasValueSB.append(jsPackage.getResolvedId());
				}
				else {
					JSPackageDependency jsPackageDependency =
						jsPackage.getJSPackageDependency(dependencyPackageName);

					if (jsPackageDependency == null) {
						aliasSB.append(dependencyPackageName);

						aliasValueSB.append(
							":ERROR:Missing version constraints for ");
						aliasValueSB.append(dependencyPackageName);
						aliasValueSB.append(" in package.json of ");
						aliasValueSB.append(jsPackage.getResolvedId());
					}
					else {
						JSPackage jsDependencyPackage =
							_npmRegistry.resolveJSPackageDependency(
								jsPackageDependency);

						if (jsDependencyPackage == null) {
							aliasSB.append(dependencyPackageName);

							aliasValueSB.append(":ERROR:Package ");
							aliasValueSB.append(dependencyPackageName);
							aliasValueSB.append(" which is a dependency of ");
							aliasValueSB.append(jsPackage.getResolvedId());
							aliasValueSB.append(
								" is not deployed in the server");
						}
						else {
							aliasSB.append(jsDependencyPackage.getName());

							aliasValueSB.append(
								jsDependencyPackage.getResolvedId());
						}
					}
				}

				printWriter.write("\"");
				printWriter.write(aliasSB.toString());
				printWriter.write("\": \"");
				printWriter.write(aliasValueSB.toString());
				printWriter.write("\"");

				delimiter2 = ", ";
			}

			printWriter.write("}\n");

			printWriter.write("}");

			delimiter = ",\n";
		}

		printWriter.println("\n};");
	}

	private void _writePaths(PrintWriter printWriter) {
		printWriter.write("var O=\"");
		printWriter.write(_portal.getPathProxy());
		printWriter.write(_portal.getPathModule());
		printWriter.write("/js/resolved-module/");
		printWriter.write("\";\n");

		printWriter.println("Liferay.PATHS = {");

		String delimiter = "";
		Set<String> processedNames = new HashSet<>();

		for (JSLoaderModule jsLoaderModule :
				_jsLoaderModulesTracker.getJSLoaderModules()) {

			printWriter.write(delimiter);
			printWriter.write("\"");
			printWriter.write(jsLoaderModule.getName());
			printWriter.write("@");
			printWriter.write(jsLoaderModule.getVersion());
			printWriter.write("\": \"");
			printWriter.write(_portal.getPathProxy());
			printWriter.write(jsLoaderModule.getContextPath());
			printWriter.write("\"");

			if (!processedNames.contains(jsLoaderModule.getName())) {
				processedNames.add(jsLoaderModule.getName());

				printWriter.println(",");
				printWriter.write("\"");
				printWriter.write(jsLoaderModule.getName());
				printWriter.write("\": \"");
				printWriter.write(_portal.getPathProxy());
				printWriter.write(jsLoaderModule.getContextPath());
				printWriter.write("\"");
			}

			delimiter = ",\n";
		}

		for (JSPackage resolvedJSPackage :
				_npmRegistry.getResolvedJSPackages()) {

			printWriter.write(delimiter);
			printWriter.write("\"");
			printWriter.write(resolvedJSPackage.getResolvedId());
			printWriter.write("\":O+\"");
			printWriter.write(resolvedJSPackage.getResolvedId());
			printWriter.write("\"");

			delimiter = ",\n";
		}

		printWriter.println("\n};");
	}

	private void _writeResponse(HttpServletResponse response, String content)
		throws IOException {

		response.setContentType(Details.CONTENT_TYPE);

		ServletOutputStream servletOutputStream = response.getOutputStream();

		PrintWriter printWriter = new PrintWriter(servletOutputStream, true);

		printWriter.write(_minifier.minify("/o/js_loader_modules", content));

		printWriter.close();
	}

	private ComponentContext _componentContext;
	private final Map<String, String> _dependencyAliases = new HashMap<>();
	private volatile Details _details;
	private JSLoaderModulesTracker _jsLoaderModulesTracker;
	private Logger _logger;

	@Reference
	private Minifier _minifier;

	private NPMRegistry _npmRegistry;

	@Reference
	private Portal _portal;

}