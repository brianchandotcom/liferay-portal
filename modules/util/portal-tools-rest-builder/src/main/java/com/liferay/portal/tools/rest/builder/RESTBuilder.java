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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.StringUtil_IW;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.Validator_IW;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.JavaTool;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.FreeMarkerUtil;
import com.liferay.portal.tools.rest.builder.internal.util.CamelCaseUtil;
import com.liferay.portal.tools.rest.builder.internal.util.FileUtil;
import com.liferay.portal.vulcan.yaml.YAMLUtil;
import com.liferay.portal.vulcan.yaml.config.Application;
import com.liferay.portal.vulcan.yaml.config.ConfigYAML;
import com.liferay.portal.vulcan.yaml.openapi.Components;
import com.liferay.portal.vulcan.yaml.openapi.Info;
import com.liferay.portal.vulcan.yaml.openapi.Items;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class RESTBuilder {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		String copyrightFileName = GetterUtil.getString(
			arguments.get("copyright.file"));
		String restConfigDirName = GetterUtil.getString(
			arguments.get("rest.config.dir"),
			RESTBuilderArgs.REST_CONFIG_DIR_NAME);

		try {
			new RESTBuilder(copyrightFileName, restConfigDirName);
		}
		catch (Exception e) {
			ArgumentsUtil.processMainException(arguments, e);
		}
	}

	public RESTBuilder(String copyrightFileName, String restConfigDirName)
		throws Exception {

		_copyrightFileName = copyrightFileName;

		_configDir = new File(restConfigDirName);

		File configFile = new File(_configDir, "rest-config.yaml");

		try (InputStream is = new FileInputStream(configFile)) {
			_configYAML = YAMLUtil.loadConfigYAML(StringUtil.read(is));
		}

		Map<String, Object> context = new HashMap<>();

		context.put("configYAML", _configYAML);

		JavaTool javaTool = JavaTool.getInstance();

		context.put("javaTool", javaTool);

		context.put("stringUtil", StringUtil_IW.getInstance());
		context.put("validator", Validator_IW.getInstance());

		_createApplicationFile(context);

		File[] files = FileUtil.getFiles(_configDir, "rest-openapi", ".yaml");

		for (File file : files) {
			OpenAPIYAML openAPIYAML = null;

			try (InputStream is = new FileInputStream(file)) {
				openAPIYAML = YAMLUtil.loadOpenAPIYAML(StringUtil.read(is));
			}

			Info info = openAPIYAML.getInfo();

			String version = info.getVersion();

			if (Validator.isNull(version)) {
				continue;
			}

			Components components = openAPIYAML.getComponents();

			Map<String, Schema> schemas = components.getSchemas();

			Map<String, Schema> allSchemas = _getAllSchemas(schemas);

			context.put("allSchemas", allSchemas);

			context.put("openAPIYAML", openAPIYAML);

			String versionDirName = _getVersionDirName(version);

			context.put("versionDirName", versionDirName);

			_createJSONMessageBodyReaderFile(context, versionDirName);
			_createGraphQLMutationFile(context, versionDirName);
			_createGraphQLQueryFile(context, versionDirName);
			_createGraphQLServletDataFile(context, versionDirName);

			for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
				String schemaName = entry.getKey();

				List<JavaMethodSignature> javaMethodSignatures =
					javaTool.getJavaMethodSignatures(openAPIYAML, schemaName);

				if (javaMethodSignatures.isEmpty()) {
					continue;
				}

				Schema schema = entry.getValue();

				context.put("schema", schema);

				context.put("schemaName", schemaName);
				context.put(
					"schemaPath", CamelCaseUtil.fromCamelCase(schemaName));
				context.put(
					"schemaVarName",
					StringUtil.lowerCaseFirstLetter(schemaName));

				_createBaseResourceImplFile(
					context, schemaName, versionDirName);
				_createPropertiesFile(context, schemaName, versionDirName);
				_createResourceFile(context, schemaName, versionDirName);
				_createResourceImplFile(context, schemaName, versionDirName);

				if (Validator.isNotNull(_configYAML.getTestDir())) {
					_createBaseResourceTestCaseFile(
						context, schemaName, versionDirName);
					_createResourceTestFile(
						context, schemaName, versionDirName);
				}
			}

			for (Map.Entry<String, Schema> entry : allSchemas.entrySet()) {
				Schema schema = entry.getValue();

				context.put("schema", schema);

				String schemaName = entry.getKey();

				context.put("schemaName", schemaName);
				context.put(
					"schemaPath", CamelCaseUtil.fromCamelCase(schemaName));
				context.put(
					"schemaVarName",
					StringUtil.lowerCaseFirstLetter(schemaName));

				_createDTOFile(context, schemaName, versionDirName);
				_createDTOImplFile(context, schemaName, versionDirName);
			}
		}

		FileUtil.deleteFiles(_configYAML.getApiDir(), _files);
		FileUtil.deleteFiles(_configYAML.getImplDir(), _files);
		FileUtil.deleteFiles(
			_configYAML.getImplDir() + "/../resources/OSGI-INF/", _files);

		if (Validator.isNotNull(_configYAML.getTestDir())) {
			FileUtil.deleteFiles(_configYAML.getTestDir(), _files);
		}
	}

	private void _createApplicationFile(Map<String, Object> context)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/jaxrs/application/");

		Application application = _configYAML.getApplication();

		sb.append(application.getClassName());

		sb.append(".java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "application", context));
	}

	private void _createBaseResourceImplFile(
			Map<String, Object> context, String schemaName,
			String versionDirName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/resource/");
		sb.append(versionDirName);
		sb.append("/Base");
		sb.append(schemaName);
		sb.append("ResourceImpl.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "base_resource_impl", context));
	}

	private void _createBaseResourceTestCaseFile(
			Map<String, Object> context, String schemaName,
			String versionDirName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getTestDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/resource/");
		sb.append(versionDirName);
		sb.append("/test/Base");
		sb.append(schemaName);
		sb.append("ResourceTestCase.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "base_resource_test_case", context));
	}

	private void _createDTOFile(
			Map<String, Object> context, String schemaName,
			String versionDirName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getApiDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/dto/");
		sb.append(versionDirName);
		sb.append("/");
		sb.append(schemaName);
		sb.append(".java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(_copyrightFileName, "dto", context));
	}

	private void _createDTOImplFile(
			Map<String, Object> context, String schemaName,
			String versionDirName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/dto/");
		sb.append(versionDirName);
		sb.append("/");
		sb.append(schemaName);
		sb.append("Impl.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "dto_impl", context));
	}

	private void _createGraphQLMutationFile(
			Map<String, Object> context, String versionDirName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/graphql/mutation/");
		sb.append(versionDirName);
		sb.append("/Mutation.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "graphql_mutation", context));
	}

	private void _createGraphQLQueryFile(
			Map<String, Object> context, String versionDirName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/graphql/query/");
		sb.append(versionDirName);
		sb.append("/Query.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "graphql_query", context));
	}

	private void _createGraphQLServletDataFile(
			Map<String, Object> context, String versionDirName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/graphql/servlet/");
		sb.append(versionDirName);
		sb.append("/ServletDataImpl.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "graphql_servlet_data", context));
	}

	private void _createJSONMessageBodyReaderFile(
			Map<String, Object> context, String versionDirName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/jaxrs/message/body/");
		sb.append(versionDirName);
		sb.append("/JSONMessageBodyReader.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "json_message_body_reader", context));
	}

	private void _createPropertiesFile(
			Map<String, Object> context, String schemaName,
			String versionDirName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/../resources/OSGI-INF/liferay/rest/");
		sb.append(versionDirName);
		sb.append("/");
		sb.append(CamelCaseUtil.fromCamelCase(schemaName));
		sb.append(".properties");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file, FreeMarkerUtil.processTemplate(null, "properties", context));
	}

	private void _createResourceFile(
			Map<String, Object> context, String schemaName,
			String versionDirName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getApiDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/resource/");
		sb.append(versionDirName);
		sb.append("/");
		sb.append(schemaName);
		sb.append("Resource.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "resource", context));
	}

	private void _createResourceImplFile(
			Map<String, Object> context, String schemaName,
			String versionDirName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/resource/");
		sb.append(versionDirName);
		sb.append("/");
		sb.append(schemaName);
		sb.append("ResourceImpl.java");

		File file = new File(sb.toString());

		_files.add(file);

		if (file.exists()) {
			return;
		}

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "resource_impl", context));
	}

	private void _createResourceTestFile(
			Map<String, Object> context, String schemaName,
			String versionDirName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getTestDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/resource/");
		sb.append(versionDirName);
		sb.append("/test/");
		sb.append(schemaName);
		sb.append("ResourceTest.java");

		File file = new File(sb.toString());

		_files.add(file);

		if (file.exists()) {
			return;
		}

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "resource_test", context));
	}

	private Map<String, Schema> _getAllSchemas(Map<String, Schema> schemas) {
		Map<String, Schema> allSchemas = new TreeMap<>();

		Queue<Map<String, Schema>> queue = new LinkedList<>();

		queue.add(schemas);

		Map<String, Schema> map = null;

		while ((map = queue.poll()) != null) {
			for (Map.Entry<String, Schema> entry : map.entrySet()) {
				Schema schema = entry.getValue();

				Map<String, Schema> propertySchemas = null;

				Items items = schema.getItems();

				if (items != null) {
					propertySchemas = items.getPropertySchemas();
				}
				else {
					propertySchemas = schema.getPropertySchemas();
				}

				if (propertySchemas == null) {
					continue;
				}

				String schemaName = StringUtil.upperCaseFirstLetter(
					entry.getKey());

				allSchemas.put(schemaName, schema);

				queue.add(propertySchemas);
			}
		}

		return allSchemas;
	}

	private String _getVersionDirName(String version) {
		Matcher matcher = _nondigitPattern.matcher(version);

		String versionDirName = matcher.replaceAll("_");

		matcher = _leadingUnderscorePattern.matcher(versionDirName);

		return "v" + matcher.replaceFirst("");
	}

	private static final Pattern _leadingUnderscorePattern = Pattern.compile(
		"^_+");
	private static final Pattern _nondigitPattern = Pattern.compile("\\D");

	private final File _configDir;
	private final ConfigYAML _configYAML;
	private final String _copyrightFileName;
	private final List<File> _files = new ArrayList<>();

}