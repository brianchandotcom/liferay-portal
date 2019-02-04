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

package com.liferay.portal.tools.rest.builder;

import com.liferay.portal.kernel.util.StringUtil_IW;
import com.liferay.portal.kernel.util.Validator_IW;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.FreeMarkerUtil;
import com.liferay.portal.tools.rest.builder.internal.util.CamelCaseUtil;
import com.liferay.portal.tools.rest.builder.internal.util.FileUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.config.Application;
import com.liferay.portal.tools.rest.builder.internal.yaml.config.ConfigYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Components;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Schema;
import com.liferay.portal.tools.rest.builder.internal.yaml.util.YAMLUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Peter Shin
 */
public class RESTBuilder {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String copyrightFileName = arguments.get("copyright.file");
		String restConfigFileName = arguments.get("rest.config.file");
		String restOpenAPIFileName = arguments.get("rest.openapi.file");

		try {
			new RESTBuilder(
				copyrightFileName, restConfigFileName, restOpenAPIFileName);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public RESTBuilder(
			String copyrightFileName, String restConfigFileName,
			String restOpenAPIFileName)
		throws Exception {

		_copyrightFileName = copyrightFileName;

		_configYAML = YAMLUtil.loadConfigYAML(restConfigFileName);
		_openAPIYAML = YAMLUtil.loadOpenAPIYAML(restOpenAPIFileName);

		Map<String, Object> context = new HashMap<>();

		context.put("configYAML", _configYAML);
		context.put("openAPIYAML", _openAPIYAML);
		context.put("stringUtil", StringUtil_IW.getInstance());
		context.put("validator", Validator_IW.getInstance());

		_createApplicationFile(context);

		_createSourceFormatterPropertiesFile();

		Components components = _openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
			context.put("schema", entry.getValue());

			String schemaName = entry.getKey();

			context.put("schemaName", schemaName);
			context.put("schemaPath", CamelCaseUtil.fromCamelCase(schemaName));

			_createBaseResourceImplFile(context, schemaName);
			_createDTOFile(context, schemaName);
			_createPropertiesFile(context, schemaName);
			_createResourceFile(context, schemaName);
			_createResourceImplFile(context, schemaName);
		}

		// FileUtil.format(new File(restConfigFileName));
		// FileUtil.format(new File(restOpenAPIFileName));

	}

	private void _createApplicationFile(Map<String, Object> context)
		throws Exception {

		String content = FreeMarkerUtil.processTemplate(
			_copyrightFileName, "application", context);

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/jaxrs/application/");

		Application application = _configYAML.getApplication();

		sb.append(application.getClassName());

		sb.append(".java");

		FileUtil.write(content, new File(sb.toString()));
	}

	private void _createBaseResourceImplFile(
			Map<String, Object> context, String schemaName)
		throws Exception {

		String content = FreeMarkerUtil.processTemplate(
			_copyrightFileName, "base_resource_impl", context);

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/resource/Base");
		sb.append(schemaName);
		sb.append("ResourceImpl.java");

		FileUtil.write(content, new File(sb.toString()));
	}

	private void _createDTOFile(Map<String, Object> context, String schemaName)
		throws Exception {

		String content = FreeMarkerUtil.processTemplate(
			_copyrightFileName, "dto", context);

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getApiDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/dto/");
		sb.append(schemaName);
		sb.append(".java");

		FileUtil.write(content, new File(sb.toString()));
	}

	private void _createPropertiesFile(
			Map<String, Object> context, String schemaName)
		throws Exception {

		String content = FreeMarkerUtil.processTemplate(
			null, "properties", context);

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/../resources/OSGI-INF/");
		sb.append(CamelCaseUtil.fromCamelCase(schemaName));
		sb.append(".properties");

		FileUtil.write(content, new File(sb.toString()));
	}

	private void _createResourceFile(
			Map<String, Object> context, String schemaName)
		throws Exception {

		String content = FreeMarkerUtil.processTemplate(
			_copyrightFileName, "resource", context);

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getApiDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/resource/");
		sb.append(schemaName);
		sb.append("Resource.java");

		FileUtil.write(content, new File(sb.toString()));
	}

	private void _createResourceImplFile(
			Map<String, Object> context, String schemaName)
		throws Exception {

		String content = FreeMarkerUtil.processTemplate(
			_copyrightFileName, "resource_impl", context);

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/resource/");
		sb.append(schemaName);
		sb.append("ResourceImpl.java");

		FileUtil.write(content, new File(sb.toString()));
	}

	private void _createSourceFormatterPropertiesFile() throws Exception {
		String content = FreeMarkerUtil.processTemplate(
			null, "source-formatter-properties", null);

		String[] dirNames = {_configYAML.getApiDir(), _configYAML.getImplDir()};

		for (String dirName : dirNames) {
			if (dirName.endsWith("src/main/java")) {
				dirName = dirName.substring(0, dirName.length() - 13);
			}

			File file = new File(dirName + "/source-formatter.properties");

			if (dirName.isEmpty()) {
				file = new File("source-formatter.properties");
			}

			FileUtil.write(content, file);
		}
	}

	private final ConfigYAML _configYAML;
	private final String _copyrightFileName;
	private final OpenAPIYAML _openAPIYAML;

}