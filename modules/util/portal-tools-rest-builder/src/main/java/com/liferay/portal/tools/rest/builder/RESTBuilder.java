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

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.StringUtil_IW;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.Validator_IW;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.FreeMarkerTool;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.util.OpenAPIParserUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.FreeMarkerUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.OpenAPIUtil;
import com.liferay.portal.tools.rest.builder.internal.util.FileUtil;
import com.liferay.portal.vulcan.yaml.YAMLUtil;
import com.liferay.portal.vulcan.yaml.config.Application;
import com.liferay.portal.vulcan.yaml.config.ConfigYAML;
import com.liferay.portal.vulcan.yaml.openapi.Components;
import com.liferay.portal.vulcan.yaml.openapi.Content;
import com.liferay.portal.vulcan.yaml.openapi.Info;
import com.liferay.portal.vulcan.yaml.openapi.Items;
import com.liferay.portal.vulcan.yaml.openapi.License;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Operation;
import com.liferay.portal.vulcan.yaml.openapi.Parameter;
import com.liferay.portal.vulcan.yaml.openapi.PathItem;
import com.liferay.portal.vulcan.yaml.openapi.RequestBody;
import com.liferay.portal.vulcan.yaml.openapi.Response;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Peter Shin
 */
public class RESTBuilder {

	public static void main(String[] args) throws Exception {
		RESTBuilderArgs restBuilderArgs = new RESTBuilderArgs();

		JCommander jCommander = new JCommander(restBuilderArgs);

		try {
			ProtectionDomain protectionDomain =
				RESTBuilder.class.getProtectionDomain();

			CodeSource codeSource = protectionDomain.getCodeSource();

			URL url = codeSource.getLocation();

			File jarFile = new File(url.toURI());

			if (jarFile.isFile()) {
				jCommander.setProgramName("java -jar " + jarFile.getName());
			}
			else {
				jCommander.setProgramName(RESTBuilder.class.getName());
			}

			jCommander.parse(args);

			if (restBuilderArgs.isHelp()) {
				_printHelp(jCommander);
			}
			else {
				RESTBuilder restBuilder = new RESTBuilder(restBuilderArgs);

				restBuilder.build();
			}
		}
		catch (ParameterException parameterException) {
			System.err.println(parameterException.getMessage());

			_printHelp(jCommander);
		}
	}

	public RESTBuilder(File copyrightFile, File restConfigDir)
		throws Exception {

		_copyrightFile = copyrightFile;

		_configDir = restConfigDir;

		File configFile = new File(_configDir, "rest-config.yaml");

		try (InputStream is = new FileInputStream(configFile)) {
			_configYAML = YAMLUtil.loadConfigYAML(StringUtil.read(is));
		}
	}

	public RESTBuilder(RESTBuilderArgs restBuilderArgs) throws Exception {
		this(
			restBuilderArgs.getCopyrightFile(),
			restBuilderArgs.getRESTConfigDir());
	}

	public void build() throws Exception {
		FreeMarkerTool freeMarkerTool = FreeMarkerTool.getInstance();

		Map<String, Object> context = HashMapBuilder.<String, Object>put(
			"configYAML", _configYAML
		).put(
			"freeMarkerTool", freeMarkerTool
		).put(
			"stringUtil", StringUtil_IW.getInstance()
		).put(
			"validator", Validator_IW.getInstance()
		).build();

		if (_configYAML.isGenerateREST()) {
			_createApplicationFile(context);
		}

		if (Validator.isNotNull(_configYAML.getClientDir())) {
			_createClientBaseJSONParserFile(context);
			_createClientHttpInvokerFile(context);
			_createClientPageFile(context);
			_createClientPaginationFile(context);
			_createClientPermissionFile(context);
			_createClientProblemFile(context);
			_createClientUnsafeSupplierFile(context);
		}

		File[] files = FileUtil.getFiles(_configDir, "rest-openapi", ".yaml");

		for (File file : files) {
			_checkOpenAPIYAMLFile(freeMarkerTool, file);

			String content = FileUtil.read(file);

			OpenAPIYAML openAPIYAML = YAMLUtil.loadOpenAPIYAML(content);

			Info info = openAPIYAML.getInfo();

			if (Validator.isNull(info.getVersion())) {
				continue;
			}

			Components components = openAPIYAML.getComponents();

			Map<String, Schema> allSchemas = OpenAPIUtil.getAllSchemas(
				openAPIYAML);

			context.put("allSchemas", allSchemas);

			String escapedVersion = OpenAPIUtil.escapeVersion(openAPIYAML);

			context.put("escapedVersion", escapedVersion);

			Map<String, Schema> globalEnumSchemas =
				OpenAPIUtil.getGlobalEnumSchemas(openAPIYAML);

			context.put("globalEnumSchemas", globalEnumSchemas);

			context.put("openAPIYAML", openAPIYAML);

			if (_configYAML.isGenerateGraphQL()) {
				_createGraphQLMutationFile(context, escapedVersion);
				_createGraphQLQueryFile(context, escapedVersion);
				_createGraphQLServletDataFile(context, escapedVersion);
			}

			_createOpenAPIResourceFile(context, escapedVersion);
			_createPropertiesFile(context, escapedVersion, "openapi");

			Set<Map.Entry<String, Schema>> set = new HashSet<>(
				allSchemas.entrySet());

			for (Map.Entry<String, Schema> entry : set) {
				Schema schema = entry.getValue();
				String schemaName = entry.getKey();

				_putSchema(context, schema, schemaName, new HashSet<>());

				_createDTOFile(context, escapedVersion, schemaName);

				if (Validator.isNotNull(_configYAML.getClientDir())) {
					_createClientDTOFile(context, escapedVersion, schemaName);
					_createClientSerDesFile(
						context, escapedVersion, schemaName);
				}
			}

			for (Map.Entry<String, Schema> entry :
					globalEnumSchemas.entrySet()) {

				_putSchema(
					context, entry.getValue(), entry.getKey(), new HashSet<>());

				_createEnumFile(context, escapedVersion, entry.getKey());

				if (Validator.isNotNull(_configYAML.getClientDir())) {
					_createClientEnumFile(
						context, escapedVersion, entry.getKey());
				}
			}

			Map<String, Schema> schemas = components.getSchemas();

			for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
				String schemaName = entry.getKey();

				List<JavaMethodSignature> javaMethodSignatures =
					freeMarkerTool.getResourceJavaMethodSignatures(
						_configYAML, openAPIYAML, schemaName);

				if (javaMethodSignatures.isEmpty()) {
					continue;
				}

				Schema schema = entry.getValue();

				_putSchema(
					context, schema, schemaName,
					_getRelatedSchemaNames(allSchemas, javaMethodSignatures));

				_createBaseResourceImplFile(
					context, escapedVersion, schemaName);
				_createPropertiesFile(
					context, escapedVersion,
					String.valueOf(context.get("schemaPath")));
				_createResourceFile(context, escapedVersion, schemaName);
				_createResourceImplFile(context, escapedVersion, schemaName);

				if (Validator.isNotNull(_configYAML.getClientDir())) {
					_createClientResourceFile(
						context, escapedVersion, schemaName);
				}

				if (Validator.isNotNull(_configYAML.getTestDir())) {
					_createBaseResourceTestCaseFile(
						context, escapedVersion, schemaName);
					_createResourceTestFile(
						context, escapedVersion, schemaName);
				}
			}
		}

		FileUtil.deleteFiles(_configYAML.getApiDir(), _files);

		if (Validator.isNotNull(_configYAML.getClientDir())) {
			FileUtil.deleteFiles(_configYAML.getClientDir(), _files);
		}

		FileUtil.deleteFiles(_configYAML.getImplDir(), _files);
		FileUtil.deleteFiles(
			_configYAML.getImplDir() + "/../resources/OSGI-INF/", _files);

		if (Validator.isNotNull(_configYAML.getTestDir())) {
			FileUtil.deleteFiles(_configYAML.getTestDir(), _files);
		}
	}

	private static void _printHelp(JCommander jCommander) {
		jCommander.usage();
	}

	private void _checkOpenAPIYAMLFile(FreeMarkerTool freeMarkerTool, File file)
		throws Exception {

		String s = _fixOpenAPILicense(FileUtil.read(file));

		s = _fixOpenAPIPaths(s);

		s = _fixOpenAPIPathParameters(s);

		if (_configYAML.isForcePredictableSchemaPropertyName()) {
			s = _fixOpenAPISchemaPropertyNames(freeMarkerTool, s);
		}

		if (_configYAML.isForcePredictableOperationId()) {
			s = _fixOpenAPIOperationIds(freeMarkerTool, s);
		}

		if (_configYAML.isForcePredictableContentApplicationXML()) {
			s = _fixOpenAPIContentApplicationXML(s);
		}

		if (_configYAML.isWarningsEnabled()) {
			_validate(s);
		}

		FileUtil.write(file, s);
	}

	private void _createApplicationFile(Map<String, Object> context)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/internal/jaxrs/application/");

		Application application = _configYAML.getApplication();

		sb.append(application.getClassName());

		sb.append(".java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "application", context));
	}

	private void _createBaseResourceImplFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/internal/resource/");
		sb.append(escapedVersion);
		sb.append("/Base");
		sb.append(schemaName);
		sb.append("ResourceImpl.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "base_resource_impl", context));
	}

	private void _createBaseResourceTestCaseFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getTestDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/resource/");
		sb.append(escapedVersion);
		sb.append("/test/Base");
		sb.append(schemaName);
		sb.append("ResourceTestCase.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "base_resource_test_case", context));
	}

	private void _createClientBaseJSONParserFile(Map<String, Object> context)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/client/json/BaseJSONParser.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "client_base_json_parser", context));
	}

	private void _createClientDTOFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/client/dto/");
		sb.append(escapedVersion);
		sb.append("/");
		sb.append(schemaName);
		sb.append(".java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "client_dto", context));
	}

	private void _createClientEnumFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/client/constant/");
		sb.append(escapedVersion);
		sb.append("/");
		sb.append(schemaName);
		sb.append(".java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "client_enum", context));
	}

	private void _createClientHttpInvokerFile(Map<String, Object> context)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/client/http/HttpInvoker.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "client_http_invoker", context));
	}

	private void _createClientPageFile(Map<String, Object> context)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/client/pagination/Page.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "client_page", context));
	}

	private void _createClientPaginationFile(Map<String, Object> context)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/client/pagination/Pagination.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "client_pagination", context));
	}

	private void _createClientPermissionFile(Map<String, Object> context)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/client/permission/Permission.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "client_permission", context));
	}

	private void _createClientProblemFile(Map<String, Object> context)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/client/problem/Problem.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "client_problem", context));
	}

	private void _createClientResourceFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/client/resource/");
		sb.append(escapedVersion);
		sb.append("/");
		sb.append(schemaName);
		sb.append("Resource.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "client_resource", context));
	}

	private void _createClientSerDesFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/client/serdes/");
		sb.append(escapedVersion);
		sb.append("/");
		sb.append(schemaName);
		sb.append("SerDes.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "client_serdes", context));
	}

	private void _createClientUnsafeSupplierFile(Map<String, Object> context)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/client/function/UnsafeSupplier.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "client_unsafe_supplier", context));
	}

	private void _createDTOFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getApiDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/dto/");
		sb.append(escapedVersion);
		sb.append("/");
		sb.append(schemaName);
		sb.append(".java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(_copyrightFile, "dto", context));
	}

	private void _createEnumFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getApiDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/constant/");
		sb.append(escapedVersion);
		sb.append("/");
		sb.append(schemaName);
		sb.append(".java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(_copyrightFile, "enum", context));
	}

	private void _createGraphQLMutationFile(
			Map<String, Object> context, String escapedVersion)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/internal/graphql/mutation/");
		sb.append(escapedVersion);
		sb.append("/Mutation.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "graphql_mutation", context));
	}

	private void _createGraphQLQueryFile(
			Map<String, Object> context, String escapedVersion)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/internal/graphql/query/");
		sb.append(escapedVersion);
		sb.append("/Query.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "graphql_query", context));
	}

	private void _createGraphQLServletDataFile(
			Map<String, Object> context, String escapedVersion)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/internal/graphql/servlet/");
		sb.append(escapedVersion);
		sb.append("/ServletDataImpl.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "graphql_servlet_data", context));
	}

	private void _createOpenAPIResourceFile(
			Map<String, Object> context, String escapedVersion)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/internal/resource/");
		sb.append(escapedVersion);
		sb.append("/OpenAPIResourceImpl.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "openapi_resource_impl", context));
	}

	private void _createPropertiesFile(
			Map<String, Object> context, String escapedVersion,
			String schemaPath)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/../resources/OSGI-INF/liferay/rest/");
		sb.append(escapedVersion);
		sb.append("/");
		sb.append(StringUtil.toLowerCase(schemaPath));
		sb.append(".properties");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file, FreeMarkerUtil.processTemplate(null, "properties", context));
	}

	private void _createResourceFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getApiDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/resource/");
		sb.append(escapedVersion);
		sb.append("/");
		sb.append(schemaName);
		sb.append("Resource.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, "resource", context));
	}

	private void _createResourceImplFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/internal/resource/");
		sb.append(escapedVersion);
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
				_copyrightFile, "resource_impl", context));
	}

	private void _createResourceTestFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getTestDir());
		sb.append("/");
		sb.append(
			StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'));
		sb.append("/resource/");
		sb.append(escapedVersion);
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
				_copyrightFile, "resource_test", context));
	}

	private String _fixOpenAPIContentApplicationXML(
		Map<String, Content> contents, int index, String s) {

		if (contents == null) {
			return s;
		}

		Set<String> mediaTypes = contents.keySet();

		if (!mediaTypes.contains("application/json") ||
			mediaTypes.contains("application/xml")) {

			return s;
		}

		StringBuilder sb = new StringBuilder();

		int startIndex =
			s.lastIndexOf("\n", s.indexOf("application/json", index)) + 1;

		int endIndex = s.indexOf("\n", startIndex);

		if (endIndex < 0) {
			endIndex = s.length();
		}

		String line = s.substring(startIndex, endIndex);

		String leadingWhitespace = line.replaceAll("^(\\s+).+", "$1");

		while (line.startsWith(leadingWhitespace)) {
			sb.append(line);
			sb.append("\n");

			startIndex = endIndex + 1;

			line = s.substring(Math.min(startIndex, endIndex), endIndex);
		}

		String oldSub = sb.toString();

		String replacement = StringUtil.replace(
			oldSub, "application/json", "application/xml");

		return StringUtil.replaceFirst(s, oldSub, oldSub + replacement, index);
	}

	private String _fixOpenAPIContentApplicationXML(String s) {
		OpenAPIYAML openAPIYAML = YAMLUtil.loadOpenAPIYAML(s);

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		if (pathItems == null) {
			return s;
		}

		for (Map.Entry<String, PathItem> entry1 : pathItems.entrySet()) {
			String path = entry1.getKey();

			int x = s.indexOf(StringUtil.quote(path, '"') + ":");

			if (x == -1) {
				x = s.indexOf(path + ":");
			}

			for (Operation operation : _getOperations(entry1.getValue())) {
				RequestBody requestBody = operation.getRequestBody();

				String httpMethod = OpenAPIParserUtil.getHTTPMethod(operation);

				int y = s.indexOf(httpMethod + ":", x);

				if (requestBody != null) {
					Map<String, Content> contents = requestBody.getContent();
					int index = s.indexOf("requestBody:", y);

					s = _fixOpenAPIContentApplicationXML(contents, index, s);
				}

				Map<Integer, Response> responses = operation.getResponses();

				for (Map.Entry<Integer, Response> entry2 :
						responses.entrySet()) {

					Response response = entry2.getValue();

					Map<String, Content> contents = response.getContent();

					int index = s.indexOf(entry2.getKey() + ":", y);

					s = _fixOpenAPIContentApplicationXML(contents, index, s);
				}
			}
		}

		return s;
	}

	private String _fixOpenAPILicense(String s) {
		String licenseName = _configYAML.getLicenseName();
		String licenseURL = _configYAML.getLicenseURL();

		StringBuilder licenseSB = new StringBuilder();

		licenseSB.append("        name: \"");
		licenseSB.append(licenseName);
		licenseSB.append("\"\n");
		licenseSB.append("        url: \"");
		licenseSB.append(licenseURL);
		licenseSB.append("\"");

		OpenAPIYAML openAPIYAML = YAMLUtil.loadOpenAPIYAML(s);

		Info info = openAPIYAML.getInfo();

		if (info == null) {
			StringBuilder sb = new StringBuilder();

			sb.append("info:\n");
			sb.append(licenseSB.toString());
			sb.append('\n');
			sb.append(s);

			return sb.toString();
		}

		License license = info.getLicense();

		if ((license != null) && licenseName.equals(license.getName()) &&
			licenseURL.equals(license.getUrl())) {

			return s;
		}

		int x = s.indexOf("\ninfo:");

		int y = s.indexOf('\n', x + 1);

		String line = s.substring(y + 1, s.indexOf("\n", y + 1));

		String leadingWhiteSpace = line.replaceAll("^(\\s+).+", "$1");

		Map<String, String> fieldMap = new TreeMap<>();

		String fieldName = "";
		String fieldValue = "";

		while (line.matches("^" + leadingWhiteSpace + ".*")) {
			if (line.matches("^" + leadingWhiteSpace + "\\w.*")) {
				if (Validator.isNotNull(fieldName)) {
					fieldMap.put(fieldName, fieldValue);

					fieldValue = "";
				}

				fieldName = line.replaceAll("^\\s+(\\w+):.*", "$1");
				fieldValue = line.replaceAll("^\\s+\\w+:\\s*(.*)\\s*", "$1");
			}
			else if (Validator.isNull(fieldValue)) {
				fieldValue = line;
			}
			else {
				fieldValue = fieldValue + '\n' + line;
			}

			if (s.indexOf('\n', y + 1) == -1) {
				y = s.length();

				break;
			}

			line = s.substring(y + 1, s.indexOf('\n', y + 1));

			y = s.indexOf('\n', y + 1);
		}

		if (Validator.isNull(fieldName)) {
			return s;
		}

		fieldMap.put(fieldName, fieldValue);

		fieldMap.put("license", licenseSB.toString());

		StringBuilder sb = new StringBuilder();

		sb.append(s.substring(0, s.indexOf('\n', x + 1) + 1));

		for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
			sb.append(leadingWhiteSpace);
			sb.append(entry.getKey());

			String value = entry.getValue();

			if (value.matches("(?s)^\\s*\\w+:.*")) {
				sb.append(":\n");
			}
			else {
				sb.append(": ");
			}

			sb.append(value);
			sb.append('\n');
		}

		sb.append(s.substring(s.lastIndexOf('\n', y - 1) + 1));

		return sb.toString();
	}

	private String _fixOpenAPIOperationIds(
		FreeMarkerTool freeMarkerTool, String s) {

		s = s.replaceAll("\n\\s+operationId:.+", "");

		OpenAPIYAML openAPIYAML = YAMLUtil.loadOpenAPIYAML(s);

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		for (String schemaName : schemas.keySet()) {
			Set<String> methodNames = new HashSet<>();

			List<JavaMethodSignature> javaMethodSignatures =
				freeMarkerTool.getResourceJavaMethodSignatures(
					_configYAML, openAPIYAML, schemaName);

			for (JavaMethodSignature javaMethodSignature :
					javaMethodSignatures) {

				String methodName = javaMethodSignature.getMethodName();

				if (methodNames.contains(methodName)) {
					continue;
				}

				methodNames.add(methodName);

				int x = s.indexOf(
					StringUtil.quote(javaMethodSignature.getPath(), '"') + ":");

				if (x == -1) {
					x = s.indexOf(javaMethodSignature.getPath() + ":");
				}

				String pathLine = s.substring(
					s.lastIndexOf("\n", x) + 1, s.indexOf("\n", x));

				String httpMethod = OpenAPIParserUtil.getHTTPMethod(
					javaMethodSignature.getOperation());

				int y = s.indexOf(httpMethod + ":", x);

				String httpMethodLine = s.substring(
					s.lastIndexOf("\n", y) + 1, s.indexOf("\n", y));

				String leadingWhiteSpace =
					pathLine.replaceAll("^(\\s+).+", "$1") +
						httpMethodLine.replaceAll("^(\\s+).+", "$1");

				int z = s.indexOf('\n', y);

				String line = s.substring(z + 1, s.indexOf("\n", z + 1));

				while (line.startsWith(leadingWhiteSpace)) {
					if (line.matches(leadingWhiteSpace + "\\w.*")) {
						String text = line.trim();

						if ((text.compareTo("operationId:") > 0) ||
							(s.indexOf('\n', z + 1) == -1)) {

							break;
						}
					}

					z = s.indexOf('\n', z + 1);

					line = s.substring(z + 1, s.indexOf("\n", z + 1));
				}

				StringBuilder sb = new StringBuilder();

				sb.append(s.substring(0, z + 1));
				sb.append(leadingWhiteSpace);
				sb.append("operationId: ");
				sb.append(methodName);
				sb.append("\n");
				sb.append(s.substring(z + 1));

				s = sb.toString();
			}
		}

		return s;
	}

	private String _fixOpenAPIPathParameters(String s) {
		OpenAPIYAML openAPIYAML = YAMLUtil.loadOpenAPIYAML(s);

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		if (pathItems == null) {
			return s;
		}

		for (Map.Entry<String, PathItem> entry : pathItems.entrySet()) {
			String path = entry.getKey();

			int x = s.indexOf(StringUtil.quote(path, '"') + ":");

			if (x == -1) {
				x = s.indexOf(path + ":");
			}

			String pathLine = s.substring(
				s.lastIndexOf("\n", x) + 1, s.indexOf("\n", x));

			// /blogs/{blog-id}/blogs --> /blogs/{blogId}/blogs

			for (Operation operation : _getOperations(entry.getValue())) {
				int y = s.indexOf(
					OpenAPIParserUtil.getHTTPMethod(operation) + ":", x);

				for (Parameter parameter : operation.getParameters()) {
					if (Validator.isNotNull(parameter.getReference())) {
						continue;
					}

					String in = parameter.getIn();
					String parameterName = parameter.getName();

					if (in.equals("path") && parameterName.contains("-")) {
						String newParameterName = CamelCaseUtil.toCamelCase(
							parameterName);

						int z = s.indexOf(" " + parameterName + "\n", y);

						StringBuilder sb = new StringBuilder();

						sb.append(s.substring(0, z + 1));
						sb.append(newParameterName);
						sb.append("\n");
						sb.append(s.substring(z + parameterName.length() + 2));

						s = sb.toString();

						String newPathLine = StringUtil.replace(
							pathLine, "{" + parameterName + "}",
							"{" + newParameterName + "}");

						s = StringUtil.replace(s, pathLine, newPathLine);
					}
				}
			}

			// /blogs/{blogId}/blogs --> /blogs/{parentBlogId}/blogs

			List<String> pathSegments = new ArrayList<>();

			for (String pathSegment : path.split("/")) {
				if (Validator.isNotNull(pathSegment)) {
					pathSegments.add(pathSegment);
				}
			}

			if ((pathSegments.size() != 3) ||
				Objects.equals(pathSegments.get(1), "{id}") ||
				!StringUtil.startsWith(pathSegments.get(1), "{") ||
				!StringUtil.endsWith(pathSegments.get(1), "Id}")) {

				continue;
			}

			String selParameterName = pathSegments.get(1);

			selParameterName = selParameterName.substring(
				1, selParameterName.length() - 1);

			String text = CamelCaseUtil.fromCamelCase(selParameterName);

			text = TextFormatter.formatPlural(
				text.substring(0, text.length() - 3));

			StringBuilder sb = new StringBuilder();

			sb.append('/');
			sb.append(text);
			sb.append('/');
			sb.append(pathSegments.get(1));
			sb.append('/');
			sb.append(text);

			if (!path.equals(sb.toString()) &&
				!path.equals(sb.toString() + "/")) {

				continue;
			}

			String newParameterName =
				"parent" + StringUtil.upperCaseFirstLetter(selParameterName);

			for (Operation operation : _getOperations(entry.getValue())) {
				int y = s.indexOf(
					OpenAPIParserUtil.getHTTPMethod(operation) + ":", x);

				for (Parameter parameter : operation.getParameters()) {
					if (Validator.isNotNull(parameter.getReference())) {
						continue;
					}

					String in = parameter.getIn();
					String parameterName = parameter.getName();

					if (in.equals("path") &&
						parameterName.equals(selParameterName)) {

						int z = s.indexOf(" " + parameterName + "\n", y);

						sb.setLength(0);

						sb.append(s.substring(0, z + 1));
						sb.append(newParameterName);
						sb.append("\n");
						sb.append(s.substring(z + parameterName.length() + 2));

						s = sb.toString();

						String newPathLine = StringUtil.replace(
							pathLine, "{" + parameterName + "}",
							"{" + newParameterName + "}");

						s = StringUtil.replace(s, pathLine, newPathLine);
					}
				}
			}

			String newPathLine = StringUtil.replace(
				pathLine, "{" + selParameterName + "}",
				"{" + newParameterName + "}");

			s = StringUtil.replace(s, pathLine, newPathLine);
		}

		return s;
	}

	private String _fixOpenAPIPaths(String s) {
		OpenAPIYAML openAPIYAML = YAMLUtil.loadOpenAPIYAML(s);

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		if (pathItems == null) {
			return s;
		}

		for (Map.Entry<String, PathItem> entry : pathItems.entrySet()) {
			String path = entry.getKey();

			if (!path.endsWith("/")) {
				continue;
			}

			String newPath = path.substring(0, path.length() - 1);

			int x = s.indexOf(StringUtil.quote(path, '"') + ":");

			if (x != -1) {
				String newSub = StringUtil.quote(newPath, '"');
				String oldSub = StringUtil.quote(path, '"');

				s = StringUtil.replaceFirst(s, oldSub, newSub, x);

				continue;
			}

			x = s.indexOf(path + ":");

			if (x != -1) {
				s = StringUtil.replaceFirst(s, path, newPath, x);
			}
		}

		return s;
	}

	private String _fixOpenAPISchemaPropertyNames(
		FreeMarkerTool freeMarkerTool, String s) {

		OpenAPIYAML openAPIYAML = YAMLUtil.loadOpenAPIYAML(s);

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		for (Map.Entry<String, Schema> entry1 : schemas.entrySet()) {
			Schema schema = entry1.getValue();

			Map<String, Schema> propertySchemas = schema.getPropertySchemas();

			if (propertySchemas == null) {
				continue;
			}

			for (Map.Entry<String, Schema> entry2 :
					propertySchemas.entrySet()) {

				Schema propertySchema = entry2.getValue();

				String description = propertySchema.getDescription();

				String reference = null;

				if (StringUtil.startsWith(
						description, "https://www.schema.org/")) {

					reference = description;
				}
				else if (propertySchema.getItems() != null) {
					Items items = propertySchema.getItems();

					reference = items.getReference();
				}

				if (reference == null) {
					continue;
				}

				int x = s.indexOf(' ' + entry1.getKey() + ':');

				int y = s.indexOf(' ' + entry2.getKey() + ':', x);

				int z = s.indexOf(':', y);

				String propertyName = entry2.getKey();
				String schemaVarName = freeMarkerTool.getSchemaVarName(
					reference.substring(reference.lastIndexOf('/') + 1));

				if (Objects.equals(propertySchema.getType(), "array")) {
					String plural = TextFormatter.formatPlural(schemaVarName);

					if (propertyName.endsWith(
							StringUtil.upperCaseFirstLetter(plural)) &&
						propertyName.matches("[a-zA-Z]+")) {

						continue;
					}

					s = s.substring(0, y + 1) + plural + s.substring(z);
				}
				else {
					if (propertyName.endsWith(
							StringUtil.upperCaseFirstLetter(schemaVarName)) &&
						propertyName.matches("[a-zA-Z]+")) {

						continue;
					}

					s = s.substring(0, y + 1) + schemaVarName + s.substring(z);
				}
			}
		}

		return s;
	}

	private List<Operation> _getOperations(PathItem pathItem) {
		List<Operation> operations = new ArrayList<>();

		if (pathItem.getDelete() != null) {
			operations.add(pathItem.getDelete());
		}

		if (pathItem.getGet() != null) {
			operations.add(pathItem.getGet());
		}

		if (pathItem.getHead() != null) {
			operations.add(pathItem.getHead());
		}

		if (pathItem.getOptions() != null) {
			operations.add(pathItem.getOptions());
		}

		if (pathItem.getPatch() != null) {
			operations.add(pathItem.getPatch());
		}

		if (pathItem.getPost() != null) {
			operations.add(pathItem.getPost());
		}

		if (pathItem.getPut() != null) {
			operations.add(pathItem.getPut());
		}

		return operations;
	}

	private Set<String> _getRelatedSchemaNames(
		Map<String, Schema> schemas,
		List<JavaMethodSignature> javaMethodSignatures) {

		Set<String> relatedSchemaNames = new HashSet<>();

		for (JavaMethodSignature javaMethodSignature : javaMethodSignatures) {
			String returnType = javaMethodSignature.getReturnType();

			String[] returnTypeParts = returnType.split("\\.");

			if (returnTypeParts.length > 0) {
				String string = returnTypeParts[returnTypeParts.length - 1];

				if (!string.equals(javaMethodSignature.getSchemaName()) &&
					schemas.containsKey(string)) {

					relatedSchemaNames.add(string);
				}
			}
		}

		return relatedSchemaNames;
	}

	private OpenAPIYAML _loadOpenAPIYAML(File file) throws IOException {
		OpenAPIYAML openAPIYAML = YAMLUtil.loadOpenAPIYAML(FileUtil.read(file));

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		if (pathItems == null) {
			return openAPIYAML;
		}

		Components components = openAPIYAML.getComponents();

		Map<String, Parameter> parameterMap = components.getParameters();

		for (Map.Entry<String, PathItem> entry : pathItems.entrySet()) {
			PathItem pathItem = entry.getValue();

			List<Operation> operations = new ArrayList<>();

			if (pathItem.getDelete() != null) {
				operations.add(pathItem.getDelete());
			}

			if (pathItem.getGet() != null) {
				operations.add(pathItem.getGet());
			}

			if (pathItem.getHead() != null) {
				operations.add(pathItem.getHead());
			}

			if (pathItem.getOptions() != null) {
				operations.add(pathItem.getOptions());
			}

			if (pathItem.getPatch() != null) {
				operations.add(pathItem.getPatch());
			}

			if (pathItem.getPost() != null) {
				operations.add(pathItem.getPost());
			}

			if (pathItem.getPut() != null) {
				operations.add(pathItem.getPut());
			}

			for (Operation operation : operations) {
				List<Parameter> parameters = operation.getParameters();

				for (int i = 0; i < parameters.size(); i++) {
					Parameter parameter = parameters.get(i);

					if (Validator.isNotNull(parameter.getReference())) {
						parameters.set(
							i,
							parameterMap.get(
								OpenAPIParserUtil.getReferenceName(
									parameter.getReference())));
					}
				}
			}
		}

		return openAPIYAML;
	}

	private void _putSchema(
		Map<String, Object> context, Schema schema, String schemaName,
		Set<String> relatedSchemaNames) {

		context.put("schema", schema);
		context.put("schemaName", schemaName);
		context.put("schemaNames", TextFormatter.formatPlural(schemaName));
		context.put(
			"schemaPath", TextFormatter.format(schemaName, TextFormatter.K));

		String schemaVarName = OpenAPIParserUtil.getSchemaVarName(schemaName);

		context.put("schemaVarName", schemaVarName);
		context.put(
			"schemaVarNames", TextFormatter.formatPlural(schemaVarName));

		context.put("relatedSchemaNames", relatedSchemaNames);
	}

	private void _validate(String string) {
		OpenAPIYAML openAPIYAML = YAMLUtil.loadOpenAPIYAML(string);

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		for (Map.Entry<String, Schema> entry1 : schemas.entrySet()) {
			Schema schema = entry1.getValue();

			Map<String, Schema> propertySchemas = schema.getPropertySchemas();

			if (propertySchemas == null) {
				continue;
			}

			for (Map.Entry<String, Schema> entry2 :
					propertySchemas.entrySet()) {

				Schema propertySchema = entry2.getValue();

				if (Objects.equals(propertySchema.getType(), "number") &&
					!Objects.equals(propertySchema.getFormat(), "bigdecimal") &&
					!Objects.equals(propertySchema.getFormat(), "double") &&
					!Objects.equals(propertySchema.getFormat(), "float")) {

					StringBuilder sb = new StringBuilder();

					sb.append("The property \"");
					sb.append(entry1.getKey());
					sb.append('.');
					sb.append(entry2.getKey());
					sb.append(
						"\" should use \"type: integer\" instead of \"type: " +
							"number\"");

					System.out.println(sb.toString());
				}
			}

			if (schema.getRequiredPropertySchemaNames() == null) {
				continue;
			}

			List<String> requiredPropertySchemaNames =
				schema.getRequiredPropertySchemaNames();

			Set<String> propertySchemaNames = propertySchemas.keySet();

			for (String requiredPropertySchemaName :
					requiredPropertySchemaNames) {

				if (!propertySchemaNames.contains(requiredPropertySchemaName)) {
					StringBuilder sb = new StringBuilder();

					sb.append("The required property \"");
					sb.append(requiredPropertySchemaName);
					sb.append("\" is not defined in ");
					sb.append(entry1.getKey());

					System.out.println(sb.toString());
				}
			}
		}
	}

	private final File _configDir;
	private final ConfigYAML _configYAML;
	private final File _copyrightFile;
	private final List<File> _files = new ArrayList<>();

}