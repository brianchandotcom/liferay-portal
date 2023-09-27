/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.StringUtil_IW;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.Validator_IW;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.FreeMarkerTool;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.JavaMethodSignature;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.util.OpenAPIParserUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.ConfigUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.FreeMarkerUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.OpenAPIUtil;
import com.liferay.portal.tools.rest.builder.internal.util.FileUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.YAMLUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.config.Application;
import com.liferay.portal.tools.rest.builder.internal.yaml.config.ConfigYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.exception.OpenAPIValidatorException;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Components;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Content;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Info;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Items;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.License;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Operation;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Parameter;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.PathItem;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.RequestBody;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Response;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.ResponseCode;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Schema;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
			_printHelp(jCommander);

			throw new RuntimeException(
				parameterException.getMessage(), parameterException);
		}
		catch (Exception exception) {
			throw new RuntimeException(
				"Error generating REST API\n" + exception.getMessage(),
				exception);
		}
	}

	public RESTBuilder(
			File copyrightFile, File configDir,
			Boolean forceClientVersionDescription,
			Boolean forcePredictableOperationId)
		throws Exception {

		_copyrightFile = copyrightFile;

		_configDir = configDir;

		File configFile = new File(_configDir, "rest-config.yaml");

		try (InputStream inputStream = new FileInputStream(configFile)) {
			_configYAML = YAMLUtil.loadConfigYAML(StringUtil.read(inputStream));

			if (forceClientVersionDescription != null) {
				_configYAML.setForceClientVersionDescription(
					forceClientVersionDescription);
			}

			if (forcePredictableOperationId != null) {
				_configYAML.setForcePredictableOperationId(
					forcePredictableOperationId);
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(
				"Error in file \"rest-config.yaml\": " +
					exception.getMessage());
		}
	}

	public RESTBuilder(RESTBuilderArgs restBuilderArgs) throws Exception {
		this(
			restBuilderArgs.getCopyrightFile(),
			restBuilderArgs.getRESTConfigDir(),
			restBuilderArgs.isForceClientVersionDescription(),
			restBuilderArgs.isForcePredictableOperationId());
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

		if (_configYAML.isGenerateREST() &&
			(_configYAML.getApplication() != null)) {

			_createApplicationFile(context);
		}

		if (Validator.isNotNull(_configYAML.getClientDir())) {
			_createClientAggregationFile(context);
			_createClientBaseJSONParserFile(context);
			_createClientFacetFile(context);
			_createClientHttpInvokerFile(context);
			_createClientPageFile(context);
			_createClientPaginationFile(context);
			_createClientPermissionFile(context);
			_createClientProblemFile(context);
			_createClientUnsafeSupplierFile(context);
		}

		List<String> validationErrorMessages = new ArrayList<>();

		File[] files = FileUtil.getFiles(_configDir, "rest-openapi", ".yaml");

		for (File file : files) {
			try {
				_checkOpenAPIYAMLFile(freeMarkerTool, file);
			}
			catch (Exception exception) {
				_log.error(exception);

				throw new RuntimeException(
					StringBundler.concat(
						"Error in file \"", file.getName(), "\": ",
						exception.getMessage()));
			}

			String yamlString = FileUtil.read(file);

			if (!_validateOpenAPIYAML(
					file.getName(), yamlString, validationErrorMessages)) {

				continue;
			}

			OpenAPIYAML openAPIYAML = _loadOpenAPIYAML(yamlString);

			Map<String, Schema> allSchemas = OpenAPIUtil.getAllSchemas(
				openAPIYAML);

			Map<String, Schema> allExternalSchemas =
				OpenAPIUtil.getAllExternalSchemas(openAPIYAML);

			context.put("allExternalSchemas", allExternalSchemas);

			context.put("allSchemas", allSchemas);

			String escapedVersion = OpenAPIUtil.escapeVersion(openAPIYAML);

			context.put("escapedVersion", escapedVersion);

			Map<String, Schema> globalEnumSchemas =
				OpenAPIUtil.getGlobalEnumSchemas(openAPIYAML);

			context.put("globalEnumSchemas", globalEnumSchemas);

			Map<String, String> javaDataTypeMap =
				OpenAPIParserUtil.getJavaDataTypeMap(_configYAML, openAPIYAML);

			context.put("javaDataTypeMap", javaDataTypeMap);

			context.put("openAPIYAML", openAPIYAML);

			if (_configYAML.isGenerateGraphQL() &&
				(_configYAML.getApplication() != null)) {

				_createGraphQLMutationFile(context, escapedVersion);
				_createGraphQLQueryFile(context, escapedVersion);
				_createGraphQLServletDataFile(context, escapedVersion);
			}

			context.put("schemaName", "openapi");

			if (_configYAML.isGenerateOpenAPI() &&
				(_configYAML.getResourceApplicationSelect() == null)) {

				_createOpenAPIResourceFile(context, escapedVersion);
				_createPropertiesFile(context, escapedVersion, "openapi");
			}

			Map<String, Schema> schemas = freeMarkerTool.getSchemas(
				openAPIYAML);

			_createExternalSchemaFiles(
				allExternalSchemas, context, escapedVersion);

			Set<Map.Entry<String, Schema>> set = new HashSet<>(
				allSchemas.entrySet());

			for (Map.Entry<String, Schema> entry : set) {
				Schema schema = entry.getValue();
				String schemaName = entry.getKey();

				_putSchema(
					context, escapedVersion, javaDataTypeMap, schema,
					schemaName, new HashSet<>());

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
					context, escapedVersion, javaDataTypeMap, entry.getValue(),
					entry.getKey(), new HashSet<>());

				_createEnumFile(context, escapedVersion, entry.getKey());

				if (Validator.isNotNull(_configYAML.getClientDir())) {
					_createClientEnumFile(
						context, escapedVersion, entry.getKey());
				}
			}

			schemas = freeMarkerTool.getAllSchemas(
				allExternalSchemas, openAPIYAML, schemas);

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
					context, escapedVersion, javaDataTypeMap, schema,
					schemaName,
					_getRelatedSchemaNames(allSchemas, javaMethodSignatures));

				_createBaseResourceImplFile(
					context, escapedVersion, schemaName);
				_createLiberalPermissionCheckerFile(context);
				_createPropertiesFile(
					context, escapedVersion,
					String.valueOf(context.get("schemaPath")));

				if (_configYAML.getApplication() != null) {
					_createResourceFactoryImplFile(
						context, escapedVersion, schemaName);
				}

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

				if (ConfigUtil.isVersionCompatible(_configYAML, 3)) {
					_createBaseDTOActionMetadataProviderFile(
						context, escapedVersion, schemaName);
					_createDTOActionMetadataProviderFile(
						context, escapedVersion, schemaName);
					_createDTOActionProviderFile(
						context, escapedVersion, schemaName);
				}
			}
		}

		if (!validationErrorMessages.isEmpty()) {
			String validationErrorMessagesString = StringUtil.merge(
				validationErrorMessages, StringPool.NEW_LINE);

			throw new RuntimeException(
				"OpenAPI validation errors:\n" + validationErrorMessagesString);
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

	private String _addClientVersionDescription(String yamlString) {
		String clientMavenGroupId = _getClientMavenGroupId(
			_configYAML.getApiPackagePath());
		String clientVersion = _getClientVersion();

		int licenseIndex = yamlString.indexOf("    license:");

		if ((clientMavenGroupId == null) || (clientVersion == null) ||
			(licenseIndex == -1)) {

			return yamlString;
		}

		OpenAPIYAML openAPIYAML = _loadOpenAPIYAML(yamlString);

		Info info = openAPIYAML.getInfo();

		String description = info.getDescription();

		if (description == null) {
			return yamlString;
		}

		String clientMessage = StringBundler.concat(
			"A Java client JAR is available for use with the group ID '",
			clientMavenGroupId, "', artifact ID '",
			_configYAML.getApiPackagePath(), ".client', and version '");

		if (description.contains(clientMessage)) {
			description = StringUtil.removeSubstring(
				description,
				description.substring(description.indexOf(clientMessage)));
		}

		if (!description.isEmpty() && !description.endsWith(". ")) {
			description = StringBundler.concat(
				description, ". ", clientMessage, clientVersion, "'.");
		}
		else {
			description = StringBundler.concat(
				description, clientMessage, clientVersion, "'.");
		}

		String formattedDescription = _formatDescrition(
			StringPool.FOUR_SPACES + StringPool.FOUR_SPACES,
			"\"" + description + "\"");

		String descriptionBlock =
			"    description:\n" + formattedDescription + "\n";

		return StringUtil.replace(
			yamlString,
			yamlString.substring(
				yamlString.indexOf(
					"    description:", yamlString.indexOf("info:")),
				licenseIndex),
			descriptionBlock);
	}

	private void _checkOpenAPIYAMLFile(FreeMarkerTool freeMarkerTool, File file)
		throws Exception {

		String yamlString = _fixOpenAPILicense(FileUtil.read(file));

		yamlString = _fixOpenAPIPaths(yamlString);

		yamlString = _fixOpenAPIPathParameters(yamlString);

		if (_configYAML.isForcePredictableSchemaPropertyName()) {
			yamlString = _fixOpenAPISchemaPropertyNames(
				freeMarkerTool, yamlString);
		}

		if (_configYAML.isForcePredictableOperationId()) {
			yamlString = _fixOpenAPIOperationIds(freeMarkerTool, yamlString);
		}

		if (_configYAML.isForcePredictableContentApplicationXML()) {
			yamlString = _fixOpenAPIContentApplicationXML(yamlString);
		}

		if (_configYAML.isForceClientVersionDescription()) {
			yamlString = _addClientVersionDescription(yamlString);
		}

		if (_configYAML.isWarningsEnabled()) {
			_validate(yamlString);
		}

		FileUtil.write(file, yamlString);
	}

	private void _createApplicationFile(Map<String, Object> context)
		throws Exception {

		StringBundler sb = new StringBundler(6);

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
				_copyrightFile, _getCopyrightYear(file), "application",
				context));
	}

	private void _createBaseDTOActionMetadataProviderFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getImplDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/internal/dto/", escapedVersion, "/action/metadata/Base",
				schemaName, "DTOActionMetadataProvider.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file),
				"base_dto_action_metadata_provider", context));
	}

	private void _createBaseResourceImplFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getImplDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/internal/resource/", escapedVersion, "/Base", schemaName,
				"ResourceImpl.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "base_resource_impl",
				context));
	}

	private void _createBaseResourceTestCaseFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getTestDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/resource/", escapedVersion, "/test/Base", schemaName,
				"ResourceTestCase.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file),
				"base_resource_test_case", context));
	}

	private void _createClientAggregationFile(Map<String, Object> context)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getClientDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/client/aggregation/Aggregation.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "client_aggregation",
				context));
	}

	private void _createClientBaseJSONParserFile(Map<String, Object> context)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getClientDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/client/json/BaseJSONParser.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file),
				"client_base_json_parser", context));
	}

	private void _createClientDTOFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getClientDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/client/dto/", escapedVersion, "/", schemaName, ".java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "client_dto",
				context));
	}

	private void _createClientEnumFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getClientDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/client/constant/", escapedVersion, "/", schemaName, ".java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "client_enum",
				context));
	}

	private void _createClientFacetFile(Map<String, Object> context)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getClientDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/client/aggregation/Facet.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "client_facet",
				context));
	}

	private void _createClientHttpInvokerFile(Map<String, Object> context)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getClientDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/client/http/HttpInvoker.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "client_http_invoker",
				context));
	}

	private void _createClientPageFile(Map<String, Object> context)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getClientDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/client/pagination/Page.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "client_page",
				context));
	}

	private void _createClientPaginationFile(Map<String, Object> context)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getClientDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/client/pagination/Pagination.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "client_pagination",
				context));
	}

	private void _createClientPermissionFile(Map<String, Object> context)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getClientDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/client/permission/Permission.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "client_permission",
				context));
	}

	private void _createClientProblemFile(Map<String, Object> context)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getClientDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/client/problem/Problem.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "client_problem",
				context));
	}

	private void _createClientResourceFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getClientDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/client/resource/", escapedVersion, "/", schemaName,
				"Resource.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "client_resource",
				context));
	}

	private void _createClientSerDesFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getClientDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/client/serdes/", escapedVersion, "/", schemaName,
				"SerDes.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "client_serdes",
				context));
	}

	private void _createClientUnsafeSupplierFile(Map<String, Object> context)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getClientDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/client/function/UnsafeSupplier.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file),
				"client_unsafe_supplier", context));
	}

	private void _createDTOActionMetadataProviderFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getImplDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/internal/dto/", escapedVersion, "/action/metadata/",
				schemaName, "DTOActionMetadataProvider.java"));

		_files.add(file);

		if (file.exists()) {
			return;
		}

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file),
				"dto_action_metadata_provider", context));
	}

	private void _createDTOActionProviderFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getImplDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/internal/dto/", escapedVersion, "/action/", schemaName,
				"DTOActionProvider.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "dto_action_provider",
				context));
	}

	private void _createDTOFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getApiDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/dto/", escapedVersion, "/", schemaName, ".java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "dto", context));
	}

	private void _createEnumFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getApiDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/constant/", escapedVersion, "/", schemaName, ".java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "enum", context));
	}

	private void _createExternalSchemaFiles(
			Map<String, Schema> allExternalSchemas, Map<String, Object> context,
			String escapedVersion)
		throws Exception {

		for (Map.Entry<String, Schema> entry : allExternalSchemas.entrySet()) {
			String schemaName = entry.getKey();

			_putSchema(
				context, escapedVersion,
				Collections.singletonMap(schemaName, schemaName),
				entry.getValue(), schemaName, new HashSet<>());

			if (Validator.isNotNull(_configYAML.getClientDir())) {
				_createClientDTOFile(context, escapedVersion, schemaName);
				_createClientSerDesFile(context, escapedVersion, schemaName);
			}
		}
	}

	private void _createGraphQLMutationFile(
			Map<String, Object> context, String escapedVersion)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getImplDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/internal/graphql/mutation/", escapedVersion,
				"/Mutation.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "graphql_mutation",
				context));
	}

	private void _createGraphQLQueryFile(
			Map<String, Object> context, String escapedVersion)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getImplDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/internal/graphql/query/", escapedVersion, "/Query.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "graphql_query",
				context));
	}

	private void _createGraphQLServletDataFile(
			Map<String, Object> context, String escapedVersion)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getImplDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/internal/graphql/servlet/", escapedVersion,
				"/ServletDataImpl.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "graphql_servlet_data",
				context));
	}

	private void _createLiberalPermissionCheckerFile(
			Map<String, Object> context)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getImplDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/internal/security/permission/LiberalPermissionChecker.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file),
				"liberal_permission_checker", context));
	}

	private void _createOpenAPIResourceFile(
			Map<String, Object> context, String escapedVersion)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getImplDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/internal/resource/", escapedVersion,
				"/OpenAPIResourceImpl.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file),
				"openapi_resource_impl", context));
	}

	private void _createPropertiesFile(
			Map<String, Object> context, String escapedVersion,
			String schemaPath)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getImplDir(),
				"/../resources/OSGI-INF/liferay/rest/", escapedVersion, "/",
				StringUtil.toLowerCase(schemaPath), ".properties"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(null, null, "properties", context));
	}

	private void _createResourceFactoryImplFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getImplDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/internal/resource/", escapedVersion, "/factory/", schemaName,
				"ResourceFactoryImpl.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file),
				"resource_factory_impl", context));
	}

	private void _createResourceFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getApiDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/resource/", escapedVersion, "/", schemaName,
				"Resource.java"));

		_files.add(file);

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "resource", context));
	}

	private void _createResourceImplFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getImplDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/internal/resource/", escapedVersion, "/", schemaName,
				"ResourceImpl.java"));

		_files.add(file);

		if (file.exists()) {
			return;
		}

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "resource_impl",
				context));
	}

	private void _createResourceTestFile(
			Map<String, Object> context, String escapedVersion,
			String schemaName)
		throws Exception {

		File file = new File(
			StringBundler.concat(
				_configYAML.getTestDir(), "/",
				StringUtil.replace(_configYAML.getApiPackagePath(), '.', '/'),
				"/resource/", escapedVersion, "/test/", schemaName,
				"ResourceTest.java"));

		_files.add(file);

		if (file.exists()) {
			return;
		}

		FileUtil.write(
			file,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, _getCopyrightYear(file), "resource_test",
				context));
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

		int endIndex = _getLineEndIndex(s, startIndex);

		String line = s.substring(startIndex, endIndex);

		String leadingWhitespace = line.replaceAll("^(\\s+).+", "$1");

		while (line.startsWith(leadingWhitespace)) {
			sb.append(line);
			sb.append("\n");

			startIndex = endIndex + 1;

			endIndex = _getLineEndIndex(s, startIndex);

			line = s.substring(Math.min(startIndex, endIndex), endIndex);
		}

		sb.setLength(sb.length() - 1);

		String oldSub = sb.toString();

		String replacement = "\n";

		replacement += StringUtil.replace(
			oldSub, "application/json", "application/xml");

		return StringUtil.replaceFirst(s, oldSub, oldSub + replacement, index);
	}

	private String _fixOpenAPIContentApplicationXML(String yamlString) {
		OpenAPIYAML openAPIYAML = _loadOpenAPIYAML(yamlString);

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		if (pathItems == null) {
			return yamlString;
		}

		for (Map.Entry<String, PathItem> entry1 : pathItems.entrySet()) {
			String path = entry1.getKey();

			int x = yamlString.indexOf(StringUtil.quote(path, '"') + ":");

			if (x == -1) {
				x = yamlString.indexOf(path + ":");
			}

			for (Operation operation :
					OpenAPIParserUtil.getOperations(entry1.getValue())) {

				RequestBody requestBody = operation.getRequestBody();

				String httpMethod = OpenAPIParserUtil.getHTTPMethod(operation);

				int y = yamlString.indexOf(httpMethod + ":", x);

				if (requestBody != null) {
					Map<String, Content> contents = requestBody.getContent();
					int index = yamlString.indexOf("requestBody:", y);

					yamlString = _fixOpenAPIContentApplicationXML(
						contents, index, yamlString);
				}

				Map<ResponseCode, Response> responses =
					operation.getResponses();

				for (Map.Entry<ResponseCode, Response> entry2 :
						responses.entrySet()) {

					Response response = entry2.getValue();

					if (response == null) {
						continue;
					}

					Map<String, Content> contents = response.getContent();

					int index = yamlString.indexOf(entry2.getKey() + ":", y);

					yamlString = _fixOpenAPIContentApplicationXML(
						contents, index, yamlString);
				}
			}
		}

		return yamlString;
	}

	private String _fixOpenAPILicense(String yamlString) {
		OpenAPIYAML openAPIYAML = _loadOpenAPIYAML(yamlString);

		String licenseName = _configYAML.getLicenseName();
		String licenseURL = _configYAML.getLicenseURL();

		StringBundler licenseSB = new StringBundler(6);

		licenseSB.append("        name: \"");
		licenseSB.append(licenseName);
		licenseSB.append("\"\n");
		licenseSB.append("        url: \"");
		licenseSB.append(licenseURL);
		licenseSB.append("\"");

		Info info = openAPIYAML.getInfo();

		if (info == null) {
			return StringBundler.concat("info:\n", licenseSB, '\n', yamlString);
		}

		License license = info.getLicense();

		if ((license != null) && licenseName.equals(license.getName()) &&
			licenseURL.equals(license.getUrl())) {

			return yamlString;
		}

		int x = yamlString.indexOf("\ninfo:");

		int y = yamlString.indexOf('\n', x + 1);

		String line = yamlString.substring(
			y + 1, yamlString.indexOf("\n", y + 1));

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

			if (yamlString.indexOf('\n', y + 1) == -1) {
				y = yamlString.length();

				break;
			}

			line = yamlString.substring(y + 1, yamlString.indexOf('\n', y + 1));

			y = yamlString.indexOf('\n', y + 1);
		}

		if (Validator.isNull(fieldName)) {
			return yamlString;
		}

		fieldMap.put(fieldName, fieldValue);

		fieldMap.put("license", licenseSB.toString());

		StringBundler sb = new StringBundler();

		sb.append(yamlString.substring(0, yamlString.indexOf('\n', x + 1) + 1));

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

		sb.append(
			yamlString.substring(yamlString.lastIndexOf('\n', y - 1) + 1));

		return sb.toString();
	}

	private String _fixOpenAPIOperationIds(
			FreeMarkerTool freeMarkerTool, String yamlString)
		throws Exception {

		OpenAPIYAML openAPIYAML = _loadOpenAPIYAML(yamlString);

		yamlString = yamlString.replaceAll("\n\\s+operationId:.+", "");

		Map<String, Schema> allExternalSchemas =
			OpenAPIUtil.getAllExternalSchemas(openAPIYAML);
		Map<String, Schema> schemas = freeMarkerTool.getSchemas(openAPIYAML);

		MapUtil.merge(allExternalSchemas, schemas);

		for (String schemaName : schemas.keySet()) {
			Set<String> methodNames = new HashSet<>();

			List<JavaMethodSignature> javaMethodSignatures =
				freeMarkerTool.getResourceJavaMethodSignatures(
					_configYAML, openAPIYAML, schemaName);

			for (JavaMethodSignature javaMethodSignature :
					javaMethodSignatures) {

				String methodName = javaMethodSignature.getMethodName();

				if (methodNames.contains(methodName) ||
					methodName.endsWith("Batch")) {

					continue;
				}

				methodNames.add(methodName);

				int x = yamlString.indexOf(
					StringUtil.quote(javaMethodSignature.getPath(), '"') + ":");

				if (x == -1) {
					x = yamlString.indexOf(javaMethodSignature.getPath() + ":");
				}

				String pathLine = yamlString.substring(
					yamlString.lastIndexOf("\n", x) + 1,
					yamlString.indexOf("\n", x));

				String httpMethod = OpenAPIParserUtil.getHTTPMethod(
					javaMethodSignature.getOperation());

				int y = yamlString.indexOf(httpMethod + ":", x);

				String httpMethodLine = yamlString.substring(
					yamlString.lastIndexOf("\n", y) + 1,
					yamlString.indexOf("\n", y));

				String leadingWhiteSpace =
					pathLine.replaceAll("^(\\s+).+", "$1") +
						httpMethodLine.replaceAll("^(\\s+).+", "$1");

				int z = yamlString.indexOf('\n', y);

				String line = yamlString.substring(
					z + 1, yamlString.indexOf("\n", z + 1));

				while (line.startsWith(leadingWhiteSpace)) {
					if (line.matches(leadingWhiteSpace + "\\w.*")) {
						String text = line.trim();

						if ((text.compareTo("operationId:") > 0) ||
							(yamlString.indexOf('\n', z + 1) == -1)) {

							break;
						}
					}

					z = yamlString.indexOf('\n', z + 1);

					line = yamlString.substring(
						z + 1, yamlString.indexOf("\n", z + 1));
				}

				yamlString = StringBundler.concat(
					yamlString.substring(0, z + 1), leadingWhiteSpace,
					"operationId: ", methodName, "\n",
					yamlString.substring(z + 1));
			}
		}

		return yamlString;
	}

	private String _fixOpenAPIPathParameters(String yamlString) {
		OpenAPIYAML openAPIYAML = _loadOpenAPIYAML(yamlString);

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		if (pathItems == null) {
			return yamlString;
		}

		for (Map.Entry<String, PathItem> entry : pathItems.entrySet()) {
			String path = entry.getKey();

			int x = yamlString.indexOf(StringUtil.quote(path, '"') + ":");

			if (x == -1) {
				x = yamlString.indexOf(path + ":");
			}

			String pathLine = yamlString.substring(
				yamlString.lastIndexOf("\n", x) + 1,
				yamlString.indexOf("\n", x));

			// /blogs/{blog-id}/blogs --> /blogs/{blogId}/blogs

			for (Operation operation :
					OpenAPIParserUtil.getOperations(entry.getValue())) {

				int y = yamlString.indexOf(
					OpenAPIParserUtil.getHTTPMethod(operation) + ":", x);

				for (Parameter parameter : operation.getParameters()) {
					String in = parameter.getIn();
					String parameterName = parameter.getName();

					if (in.equals("path") && parameterName.contains("-")) {
						String newParameterName = CamelCaseUtil.toCamelCase(
							parameterName);

						int z = yamlString.indexOf(
							" " + parameterName + "\n", y);

						yamlString = StringBundler.concat(
							yamlString.substring(0, z + 1), newParameterName,
							"\n",
							yamlString.substring(
								z + parameterName.length() + 2));

						String newPathLine = StringUtil.replace(
							pathLine, "{" + parameterName + "}",
							"{" + newParameterName + "}");

						yamlString = StringUtil.replace(
							yamlString, pathLine, newPathLine);
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

			for (Operation operation :
					OpenAPIParserUtil.getOperations(entry.getValue())) {

				int y = yamlString.indexOf(
					OpenAPIParserUtil.getHTTPMethod(operation) + ":", x);

				for (Parameter parameter : operation.getParameters()) {
					String in = parameter.getIn();
					String parameterName = parameter.getName();

					if (in.equals("path") &&
						parameterName.equals(selParameterName)) {

						int z = yamlString.indexOf(
							" " + parameterName + "\n", y);

						sb.setLength(0);

						sb.append(yamlString.substring(0, z + 1));
						sb.append(newParameterName);
						sb.append("\n");
						sb.append(
							yamlString.substring(
								z + parameterName.length() + 2));

						yamlString = sb.toString();

						String newPathLine = StringUtil.replace(
							pathLine, "{" + parameterName + "}",
							"{" + newParameterName + "}");

						yamlString = StringUtil.replace(
							yamlString, pathLine, newPathLine);
					}
				}
			}

			String newPathLine = StringUtil.replace(
				pathLine, "{" + selParameterName + "}",
				"{" + newParameterName + "}");

			yamlString = StringUtil.replace(yamlString, pathLine, newPathLine);
		}

		return yamlString;
	}

	private String _fixOpenAPIPaths(String yamlString) {
		OpenAPIYAML openAPIYAML = _loadOpenAPIYAML(yamlString);

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		if (pathItems == null) {
			return yamlString;
		}

		for (Map.Entry<String, PathItem> entry : pathItems.entrySet()) {
			String path = entry.getKey();

			if (!path.endsWith("/")) {
				continue;
			}

			String newPath = path.substring(0, path.length() - 1);

			int x = yamlString.indexOf(StringUtil.quote(path, '"') + ":");

			if (x != -1) {
				String newSub = StringUtil.quote(newPath, '"');
				String oldSub = StringUtil.quote(path, '"');

				yamlString = StringUtil.replaceFirst(
					yamlString, oldSub, newSub, x);

				continue;
			}

			x = yamlString.indexOf(path + ":");

			if (x != -1) {
				yamlString = StringUtil.replaceFirst(
					yamlString, path, newPath, x);
			}
		}

		return yamlString;
	}

	private String _fixOpenAPISchemaPropertyNames(
		FreeMarkerTool freeMarkerTool, String yamlString) {

		OpenAPIYAML openAPIYAML = _loadOpenAPIYAML(yamlString);

		Map<String, Schema> schemas = freeMarkerTool.getSchemas(openAPIYAML);

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

				String propertyName = entry2.getKey();
				String schemaVarName = _getSchemaVarName(
					freeMarkerTool, reference);

				int x = yamlString.indexOf(' ' + entry1.getKey() + ':');

				int y = yamlString.indexOf(' ' + entry2.getKey() + ':', x);

				int z = yamlString.indexOf(':', y);

				if (Objects.equals(propertySchema.getType(), "array")) {
					String plural = TextFormatter.formatPlural(schemaVarName);

					if (propertyName.endsWith(
							StringUtil.upperCaseFirstLetter(plural)) &&
						propertyName.matches("[a-zA-Z]+")) {

						continue;
					}

					yamlString =
						yamlString.substring(0, y + 1) + plural +
							yamlString.substring(z);
				}
				else {
					if (propertyName.endsWith(
							StringUtil.upperCaseFirstLetter(schemaVarName)) &&
						propertyName.matches("[a-zA-Z]+")) {

						continue;
					}

					yamlString =
						yamlString.substring(0, y + 1) + schemaVarName +
							yamlString.substring(z);
				}
			}
		}

		return yamlString;
	}

	private String _formatDescrition(String indent, String descriton) {
		if (Validator.isNull(descriton)) {
			return StringPool.BLANK;
		}

		if ((indent.length() + descriton.length()) <=
				_DESCRIPTION_MAX_LINE_LENGTH) {

			return indent + descriton;
		}

		descriton = indent + descriton;

		int x = descriton.indexOf(CharPool.SPACE, indent.length());

		if (x == -1) {
			return descriton;
		}

		if (x > _DESCRIPTION_MAX_LINE_LENGTH) {
			String s = descriton.substring(x + 1);

			return descriton.substring(0, x) + "\n" +
				_formatDescrition(indent, s);
		}

		x = descriton.lastIndexOf(CharPool.SPACE, _DESCRIPTION_MAX_LINE_LENGTH);

		String s = descriton.substring(x + 1);

		return descriton.substring(0, x) + "\n" + _formatDescrition(indent, s);
	}

	private String _getClientMavenGroupId(String apiPackagePath) {
		if (apiPackagePath.startsWith("com.liferay.commerce")) {
			return "com.liferay.commerce";
		}
		else if (apiPackagePath.startsWith("com.liferay")) {
			return "com.liferay";
		}

		return _configYAML.getClientMavenGroupId();
	}

	private String _getClientVersion() {
		try {
			String directory = StringUtil.removeSubstring(
				_configYAML.getClientDir(), "src/main/java");

			for (String line :
					Files.readAllLines(
						Paths.get(directory + "/bnd.bnd"),
						StandardCharsets.UTF_8)) {

				if (!line.startsWith("Bundle-Version: ")) {
					continue;
				}

				return StringUtil.removeSubstring(line, "Bundle-Version: ");
			}

			return null;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return null;
		}
	}

	private String _getCopyrightYear(File file) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");

		String year = simpleDateFormat.format(new Date());

		if (file.exists()) {
			String content = FileUtil.read(file);

			int x = content.indexOf("/**\n * SPDX-FileCopyrightText: (c) ");

			if (x != -1) {
				year = content.substring(x + 35, x + 39);
			}
		}

		return year;
	}

	private int _getLineEndIndex(String s, int startIndex) {
		int endIndex = s.indexOf("\n", startIndex);

		if (endIndex < 0) {
			endIndex = s.length();
		}

		return endIndex;
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

	private String _getSchemaVarName(
		FreeMarkerTool freeMarkerTool, String reference) {

		int index = Math.max(
			reference.lastIndexOf('#'), reference.lastIndexOf('/'));

		return freeMarkerTool.getSchemaVarName(reference.substring(index + 1));
	}

	private OpenAPIYAML _loadOpenAPIYAML(String yamlString) {
		OpenAPIYAML openAPIYAML = YAMLUtil.loadOpenAPIYAML(yamlString);

		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();

		if (pathItems == null) {
			return openAPIYAML;
		}

		Components components = openAPIYAML.getComponents();

		if (components == null) {
			return openAPIYAML;
		}

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
						String key = OpenAPIParserUtil.getReferenceName(
							parameter.getReference());

						if (parameterMap.containsKey(key)) {
							parameters.set(i, parameterMap.get(key));
						}
					}
				}
			}
		}

		return openAPIYAML;
	}

	private void _putSchema(
		Map<String, Object> context, String escapedVersion,
		Map<String, String> javaDataTypeMap, Schema schema, String schemaName,
		Set<String> relatedSchemaNames) {

		context.put("schema", schema);

		String javaType = javaDataTypeMap.get(schemaName);

		if (javaType == null) {
			context.put("schemaClientJavaType", "Object");
			context.put("schemaJavaType", "Object");
		}
		else {
			context.put(
				"schemaClientJavaType",
				StringBundler.concat(
					_configYAML.getApiPackagePath(), ".client.dto.",
					escapedVersion, ".", schemaName));
			context.put("schemaJavaType", javaType);
		}

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

	private void _validate(String yamlString) {
		OpenAPIYAML openAPIYAML = _loadOpenAPIYAML(yamlString);

		Components components = openAPIYAML.getComponents();

		if (components == null) {
			return;
		}

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

					System.out.println(
						StringBundler.concat(
							"The property \"", entry1.getKey(), '.',
							entry2.getKey(),
							"\" should use \"type: integer\" instead of ",
							"\"type: number\""));
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
					System.out.println(
						StringBundler.concat(
							"The required property \"",
							requiredPropertySchemaName, "\" is not defined in ",
							entry1.getKey()));
				}
			}
		}
	}

	private boolean _validateOpenAPIYAML(
		String fileName, String yamlString, List<String> validationErrors) {

		try {
			YAMLUtil.validateOpenAPIYAML(fileName, yamlString);

			return true;
		}
		catch (OpenAPIValidatorException openAPIValidatorException) {
			validationErrors.add(openAPIValidatorException.getMessage());

			return false;
		}
	}

	private static final int _DESCRIPTION_MAX_LINE_LENGTH = 120;

	private static final Log _log = LogFactoryUtil.getLog(RESTBuilder.class);

	private final File _configDir;
	private final ConfigYAML _configYAML;
	private final File _copyrightFile;
	private final List<File> _files = new ArrayList<>();

}