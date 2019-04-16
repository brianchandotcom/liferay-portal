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

import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.StringUtil_IW;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.Validator_IW;
import com.liferay.portal.tools.ArgumentsUtil;
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
import com.liferay.portal.vulcan.yaml.openapi.Info;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Operation;
import com.liferay.portal.vulcan.yaml.openapi.Parameter;
import com.liferay.portal.vulcan.yaml.openapi.PathItem;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

		FreeMarkerTool freeMarkerTool = FreeMarkerTool.getInstance();

		context.put("freeMarkerTool", freeMarkerTool);

		context.put("stringUtil", StringUtil_IW.getInstance());
		context.put("validator", Validator_IW.getInstance());

		_createApplicationFile(context);

		if (Validator.isNotNull(_configYAML.getClientDir())) {
			_createClientBaseJSONParserFile(context);
			_createClientPageFile(context);
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

			Map<String, Schema> schemas = components.getSchemas();

			Map<String, Schema> allSchemas = OpenAPIUtil.getAllSchemas(
				openAPIYAML);

			context.put("allSchemas", allSchemas);

			context.put("openAPIYAML", openAPIYAML);

			String escapedVersion = OpenAPIUtil.escapeVersion(openAPIYAML);

			context.put("escapedVersion", escapedVersion);

			_createGraphQLMutationFile(context, escapedVersion);
			_createGraphQLQueryFile(context, escapedVersion);
			_createGraphQLServletDataFile(context, escapedVersion);
			_createOpenAPIResourceFile(context, escapedVersion);
			_createPropertiesFile(context, escapedVersion, "openapi");

			for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
				String schemaName = entry.getKey();

				List<JavaMethodSignature> javaMethodSignatures =
					freeMarkerTool.getResourceJavaMethodSignatures(
						_configYAML, openAPIYAML, schemaName);

				if (javaMethodSignatures.isEmpty()) {
					continue;
				}

				Schema schema = entry.getValue();

				_putSchema(context, schema, schemaName);

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

			Set<Map.Entry<String, Schema>> set = new HashSet<>(
				allSchemas.entrySet());

			for (Map.Entry<String, Schema> entry : set) {
				Schema schema = entry.getValue();
				String schemaName = entry.getKey();

				_putSchema(context, schema, schemaName);

				_createDTOFile(context, escapedVersion, schemaName);

				if (Validator.isNotNull(_configYAML.getClientDir())) {
					_createClientDTOFile(context, escapedVersion, schemaName);
					_createClientSerDesFile(
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

	private void _checkOpenAPIYAMLFile(FreeMarkerTool freeMarkerTool, File file)
		throws Exception {

		String content = _fixOpenAPIPathParameters(FileUtil.read(file));

		if (_configYAML.isForcePredictableOperationId()) {
			content = _fixOpenAPIOperationIds(freeMarkerTool, content);
		}

		_validate(content);

		FileUtil.write(file, content);
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
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

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
				_copyrightFileName, "base_resource_impl", context));
	}

	private void _createBaseResourceTestCaseFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getTestDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

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
				_copyrightFileName, "base_resource_test_case", context));
	}

	private void _createClientBaseJSONParserFile(Map<String, Object> context)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/client/json/BaseJSONParser.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "client_base_json_parser", context));
	}

	private void _createClientDTOFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

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
				_copyrightFileName, "client_dto", context));
	}

	private void _createClientPageFile(Map<String, Object> context)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/client/pagination/Page.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "client_page", context));
	}

	private void _createClientResourceFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

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
				_copyrightFileName, "client_resource", context));
	}

	private void _createClientSerDesFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

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
				_copyrightFileName, "client_serdes", context));
	}

	private void _createClientUnsafeSupplierFile(Map<String, Object> context)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getClientDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/client/function/UnsafeSupplier.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "client_unsafe_supplier", context));
	}

	private void _createDTOFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getApiDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/dto/");
		sb.append(escapedVersion);
		sb.append("/");
		sb.append(schemaName);
		sb.append(".java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(_copyrightFileName, "dto", context));
	}

	private void _createGraphQLMutationFile(
			Map<String, Object> context, String escapedVersion)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/graphql/mutation/");
		sb.append(escapedVersion);
		sb.append("/Mutation.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "graphql_mutation", context));
	}

	private void _createGraphQLQueryFile(
			Map<String, Object> context, String escapedVersion)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/graphql/query/");
		sb.append(escapedVersion);
		sb.append("/Query.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "graphql_query", context));
	}

	private void _createGraphQLServletDataFile(
			Map<String, Object> context, String escapedVersion)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/graphql/servlet/");
		sb.append(escapedVersion);
		sb.append("/ServletDataImpl.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "graphql_servlet_data", context));
	}

	private void _createOpenAPIResourceFile(
			Map<String, Object> context, String escapedVersion)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

		sb.append("/internal/resource/");
		sb.append(escapedVersion);
		sb.append("/OpenAPIResourceImpl.java");

		File file = new File(sb.toString());

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFileName, "openapi_resource_impl", context));
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

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

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
				_copyrightFileName, "resource", context));
	}

	private void _createResourceImplFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getImplDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

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
				_copyrightFileName, "resource_impl", context));
	}

	private void _createResourceTestFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append(_configYAML.getTestDir());
		sb.append("/");

		String apiPackagePath = _configYAML.getApiPackagePath();

		sb.append(apiPackagePath.replace('.', '/'));

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
				_copyrightFileName, "resource_test", context));
	}

	private String _fixOpenAPIOperationIds(
		FreeMarkerTool freeMarkerTool, String content) {

		content = content.replaceAll("\n\\s+operationId:.+", "");

		OpenAPIYAML openAPIYAML = YAMLUtil.loadOpenAPIYAML(content);

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		for (String schemaName : schemas.keySet()) {
			List<JavaMethodSignature> javaMethodSignatures =
				freeMarkerTool.getResourceJavaMethodSignatures(
					_configYAML, openAPIYAML, schemaName);

			for (JavaMethodSignature javaMethodSignature :
					javaMethodSignatures) {

				int x = content.indexOf(
					StringUtil.quote(javaMethodSignature.getPath(), '"') + ":");

				if (x == -1) {
					x = content.indexOf(javaMethodSignature.getPath() + ":");
				}

				String pathLine = content.substring(
					content.lastIndexOf("\n", x) + 1, content.indexOf("\n", x));

				String httpMethod = OpenAPIParserUtil.getHTTPMethod(
					javaMethodSignature.getOperation());

				int y = content.indexOf(httpMethod + ":", x);

				String httpMethodLine = content.substring(
					content.lastIndexOf("\n", y) + 1, content.indexOf("\n", y));

				int z = content.indexOf('\n', y);

				String line = content.substring(
					z + 1, content.indexOf("\n", z + 1));

				if (line.contains("operationId:")) {
					continue;
				}

				StringBuilder sb = new StringBuilder();

				sb.append(content.substring(0, z + 1));
				sb.append(pathLine.replaceAll("^(\\s+).+", "$1"));
				sb.append(httpMethodLine.replaceAll("^(\\s+).+", "$1"));
				sb.append("operationId: ");
				sb.append(javaMethodSignature.getMethodName());
				sb.append("\n");
				sb.append(content.substring(z + 1));

				content = sb.toString();
			}
		}

		return content;
	}

	private String _fixOpenAPIPathParameters(String content) {
		OpenAPIYAML openAPIYAML = YAMLUtil.loadOpenAPIYAML(content);

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		for (Map.Entry<String, PathItem> entry : pathItems.entrySet()) {
			String path = entry.getKey();

			int x = content.indexOf(StringUtil.quote(path, '"') + ":");

			if (x == -1) {
				x = content.indexOf(path + ":");
			}

			String pathLine = content.substring(
				content.lastIndexOf("\n", x) + 1, content.indexOf("\n", x));

			// /blogs/{blog-id}/blogs --> /blogs/{blogId}/blogs

			for (Operation operation : _getOperations(entry.getValue())) {
				int y = content.indexOf(
					OpenAPIParserUtil.getHTTPMethod(operation) + ":", x);

				for (Parameter parameter : operation.getParameters()) {
					String in = parameter.getIn();
					String parameterName = parameter.getName();

					if (in.equals("path") && parameterName.contains("-")) {
						String newParameterName = CamelCaseUtil.toCamelCase(
							parameterName);

						int z = content.indexOf(" " + parameterName + "\n", y);

						StringBuilder sb = new StringBuilder();

						sb.append(content.substring(0, z + 1));
						sb.append(newParameterName);
						sb.append("\n");
						sb.append(
							content.substring(z + parameterName.length() + 2));

						content = sb.toString();

						String newPathLine = pathLine.replace(
							"{" + parameterName + "}",
							"{" + newParameterName + "}");

						content = content.replace(pathLine, newPathLine);
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

			String s = CamelCaseUtil.fromCamelCase(selParameterName);

			s = TextFormatter.formatPlural(s.substring(0, s.length() - 3));

			StringBuilder sb = new StringBuilder();

			sb.append('/');
			sb.append(s);
			sb.append('/');
			sb.append(pathSegments.get(1));
			sb.append('/');
			sb.append(s);

			if (!path.equals(sb.toString()) &&
				!path.equals(sb.toString() + "/")) {

				continue;
			}

			String newParameterName =
				"parent" + StringUtil.upperCaseFirstLetter(selParameterName);

			for (Operation operation : _getOperations(entry.getValue())) {
				int y = content.indexOf(
					OpenAPIParserUtil.getHTTPMethod(operation) + ":", x);

				for (Parameter parameter : operation.getParameters()) {
					String in = parameter.getIn();
					String parameterName = parameter.getName();

					if (in.equals("path") &&
						parameterName.equals(selParameterName)) {

						int z = content.indexOf(" " + parameterName + "\n", y);

						sb.setLength(0);

						sb.append(content.substring(0, z + 1));
						sb.append(newParameterName);
						sb.append("\n");
						sb.append(
							content.substring(z + parameterName.length() + 2));

						content = sb.toString();

						String newPathLine = pathLine.replace(
							"{" + parameterName + "}",
							"{" + newParameterName + "}");

						content = content.replace(pathLine, newPathLine);
					}
				}
			}

			String newPathLine = pathLine.replace(
				"{" + selParameterName + "}", "{" + newParameterName + "}");

			content = content.replace(pathLine, newPathLine);
		}

		return content;
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

	private void _putSchema(
		Map<String, Object> context, Schema schema, String schemaName) {

		context.put("schema", schema);
		context.put("schemaName", schemaName);
		context.put("schemaNames", TextFormatter.formatPlural(schemaName));
		context.put(
			"schemaPath", TextFormatter.format(schemaName, TextFormatter.K));

		String schemaVarName = OpenAPIParserUtil.getSchemaVarName(schemaName);

		context.put("schemaVarName", schemaVarName);
		context.put(
			"schemaVarNames", TextFormatter.formatPlural(schemaVarName));
	}

	private void _validate(String content) {
		OpenAPIYAML openAPIYAML = YAMLUtil.loadOpenAPIYAML(content);

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		for (Map.Entry<String, Schema> entry1 : schemas.entrySet()) {
			Schema schema = entry1.getValue();

			Map<String, Schema> propertySchemas = schema.getPropertySchemas();

			for (Map.Entry<String, Schema> entry2 :
					propertySchemas.entrySet()) {

				Schema propertySchema = entry2.getValue();

				if (Objects.equals(propertySchema.getType(), "number") &&
					(propertySchema.getFormat() == null)) {

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
		}
	}

	private final File _configDir;
	private final ConfigYAML _configYAML;
	private final String _copyrightFileName;
	private final List<File> _files = new ArrayList<>();

}