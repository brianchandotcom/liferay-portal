/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.internal.typescript;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.util.OpenAPIParserUtil;
import com.liferay.portal.tools.rest.builder.internal.freemarker.util.FreeMarkerUtil;
import com.liferay.portal.tools.rest.builder.internal.util.FileUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.YAMLUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Content;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Operation;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Parameter;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.PathItem;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.RequestBody;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Response;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.ResponseCode;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Schema;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Daniel Raposo
 */
public class TypeScriptClientGenerator {

	public TypeScriptClientGenerator(
			File baseDir, File openAPIYAMLFile, File copyrightFile)
		throws IOException {

		_baseDir = baseDir;
		_copyrightFile = copyrightFile;

		_openAPIYAML = YAMLUtil.loadOpenAPIYAML(FileUtil.read(openAPIYAMLFile));
	}

	public void generate() throws Exception {
		Map<String, PathItem> pathItems = _openAPIYAML.getPathItems();

		if (pathItems == null) {
			return;
		}

		Files.createDirectories(
			Paths.get(_baseDir.getPath(), "src", "node", "api"));
		Files.createDirectories(
			Paths.get(_baseDir.getPath(), "src", "node", "model"));

		// Generate API files

		Set<Map.Entry<String, Map<String, Object>>> apiContexts =
			_buildApiContexts(
				pathItems
			).entrySet();

		for (Map.Entry<String, Map<String, Object>> apiContext : apiContexts) {
			File apiFile = new File(
				StringBundler.concat(
					_baseDir.getPath(), "/src/node/api/",
					StringUtil.lowerCaseFirstLetter(apiContext.getKey()),
					"Api.ts"));

			FileUtil.write(
				apiFile,
				FreeMarkerUtil.processTemplate(
					_copyrightFile, FileUtil.getCopyrightYear(apiFile),
					"typescript/api", apiContext.getValue()));
		}

		//Generate node/api/apis.ts file
		File apisFile = new File(_baseDir.getPath() + "/src/node/api/apis.ts");

		FileUtil.write(
			apisFile,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, FileUtil.getCopyrightYear(apisFile), "typescript/apis",
				Collections.singletonMap("apiContexts", apiContexts)));

		// Generate Model files

		Map<String, Schema> schemas = _openAPIYAML.getComponents(
		).getSchemas();

		if (schemas != null) {
			for (Map.Entry<String, Schema> schema : schemas.entrySet()) {
				Map<String, Object> modelContext = _buildModelContext(
					schema.getKey(), schema.getValue());
				File modelFile = new File(
					StringBundler.concat(
						_baseDir.getPath(), "/src/node/model/",
						StringUtil.lowerCaseFirstLetter(schema.getKey()),
						".ts"));

				FileUtil.write(
					modelFile,
					FreeMarkerUtil.processTemplate(
						_copyrightFile, FileUtil.getCopyrightYear(modelFile),
						"typescript/model", modelContext));
			}
		}

		// Generate node/model/models.ts file

		File modelsFile = new File(
			_baseDir.getPath() + "/src/node/model/models.ts");

		FileUtil.write(
			modelsFile,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, FileUtil.getCopyrightYear(modelsFile),
				"typescript/models",
				Collections.singletonMap("modelContexts", schemas)));

		// Generate node/api.ts file

		File apiGlobalFile = new File(_baseDir.getPath() + "/src/node/api.ts");

		FileUtil.write(
			apiGlobalFile,
			FreeMarkerUtil.processTemplate(
				_copyrightFile, FileUtil.getCopyrightYear(apiGlobalFile),
				"typescript/api_global", null));
	}

	private Map<String, Map<String, Object>> _buildApiContexts(
		Map<String, PathItem> pathItems) {

		Map<String, List<Map<String, Object>>> operationsByTag =
			new HashMap<>();

		for (Map.Entry<String, PathItem> entry : pathItems.entrySet()) {
			for (Operation operation :
					OpenAPIParserUtil.getOperations(entry.getValue())) {

				String operationTag = operation.getTags(
				).get(
					0
				);

				List<Map<String, Object>> operations =
					operationsByTag.computeIfAbsent(
						operationTag, k -> new ArrayList<>());

				operations.add(_buildOperationMap(operation, entry.getKey()));
			}
		}

		Map<String, Map<String, Object>> apiContexts = new HashMap<>();

		for (Map.Entry<String, List<Map<String, Object>>> tagOperations :
				operationsByTag.entrySet()) {

			Map<String, Object> apiContext = HashMapBuilder.<String, Object>put(
				"classname", tagOperations.getKey() + "Api"
			).put(
				"operations", tagOperations.getValue()
			).build();

			// Collect all imports

			Set<Map<String, String>> allImports = new LinkedHashSet<>();

			for (Map<String, Object> tagOperation : tagOperations.getValue()) {
				if (tagOperation.containsKey("imports")) {
					Collection<Map<String, String>> imports =
						(Collection<Map<String, String>>)tagOperation.remove(
							"imports");

					allImports.addAll(imports);
				}
			}

			if (!allImports.isEmpty()) {
				apiContext.put("imports", new ArrayList<>(allImports));
			}

			apiContexts.put(tagOperations.getKey(), apiContext);
		}

		return apiContexts;
	}

	private Map<String, Object> _buildModelContext(
		String modelName, Schema schema) {

		Map<String, Object> modelContext = HashMapBuilder.<String, Object>put(
			"description", schema.getDescription()
		).put(
			"modelName", modelName
		).build();

		Set<Map<String, String>> modelImports = new HashSet<>();

		if (schema.getDiscriminator() != null) {
			String discriminatorPropName = schema.getDiscriminator(
			).getPropertyName();

			if (Validator.isNotNull(discriminatorPropName)) {
				modelContext.put("discriminator", discriminatorPropName);
			}
		}

		// Handle inheritance and properties

		List<Map<String, Object>> schemaProperties = new ArrayList<>();

		if (schema.getAllOfSchemas() != null) {
			Schema parentSchema = schema.getAllOfSchemas(
			).get(
				0
			);

			if (parentSchema.getReference() != null) {
				String parentSchemaReference = parentSchema.getReference();

				String parentClass = parentSchemaReference.substring(
					parentSchemaReference.lastIndexOf('/') + 1);

				modelContext.put("parent", parentClass);
				modelImports.add(
					Collections.singletonMap("classname", parentClass));

				// Add properties from additional schemas in allOf

				for (Schema additionalSchema : schema.getAllOfSchemas()) {
					if (additionalSchema.getPropertySchemas() != null) {
						additionalSchema.getPropertySchemas(
						).forEach(
							(propName, propSchema) -> schemaProperties.add(
								HashMapBuilder.<String, Object>put(
									"name",
									StringUtil.replace(
										propName, '-', StringPool.UNDERLINE)
								).put(
									"type",
									_getTypeScriptType(propSchema, modelImports)
								).build())
						);
					}
				}
			}
		}

		if (schema.getPropertySchemas() != null) {
			schema.getPropertySchemas(
			).forEach(
				(propName, propSchema) -> schemaProperties.add(
					HashMapBuilder.<String, Object>put(
						"name",
						StringUtil.replace(propName, '-', StringPool.UNDERLINE)
					).put(
						"type", _getTypeScriptType(propSchema, modelImports)
					).build())
			);
		}

		modelContext.put("properties", schemaProperties);

		if (!modelImports.isEmpty()) {
			modelContext.put("imports", new ArrayList<>(modelImports));
		}

		return modelContext;
	}

	private Map<String, Object> _buildOperationMap(
		Operation operation, String path) {

		Set<Map<String, String>> imports = new HashSet<>();

		Map<String, Object> operationMap = HashMapBuilder.<String, Object>put(
			"httpMethod",
			OpenAPIParserUtil.getHTTPMethod(
				operation
			).toUpperCase()
		).put(
			"nickname", operation.getOperationId()
		).put(
			"notes", operation.getDescription()
		).put(
			"path", path
		).build();

		// Extract produces (media types) from responses.

		if (operation.getResponses() != null) {
			Set<String> produces = new LinkedHashSet<>();

			for (Response response :
					operation.getResponses(
					).values()) {

				if (response.getContent() != null) {
					produces.addAll(
						response.getContent(
						).keySet());
				}
			}

			if (!produces.isEmpty()) {
				operationMap.put("produces", new ArrayList<>(produces));
			}
		}

		Collection<Map<String, Object>> operationParams =
			_getAllOperationParams(operation, operationMap, imports);

		if (!operationParams.isEmpty()) {
			operationMap.put("allParams", operationParams);
		}

		String returnType = null;

		if (operation.getResponses() != null) {
			for (Map.Entry<ResponseCode, Response> response :
					operation.getResponses(
					).entrySet()) {

				Integer httpCode = response.getKey(
				).getHttpCode();

				if (httpCode.equals(200)) {
					Map<String, Content> responseContent = response.getValue(
					).getContent();

					if ((responseContent != null) &&
						!responseContent.isEmpty()) {

						Content firstContent = responseContent.values(
						).iterator(
						).next();

						if ((firstContent != null) &&
							(firstContent.getSchema() != null)) {

							returnType = _getTypeScriptType(
								firstContent.getSchema(), imports);
						}
					}

					break;
				}
			}
		}

		operationMap.put("returnType", returnType);

		if (!imports.isEmpty()) {
			operationMap.put("imports", new ArrayList<>(imports));
		}

		return operationMap;
	}

	private Collection<Map<String, Object>> _getAllOperationParams(
		Operation operation, Map<String, Object> operationMap,
		Set<Map<String, String>> imports) {

		List<Map<String, Object>> allParams = new ArrayList<>();
		Map<String, List<Map<String, Object>>> paramsByType = new HashMap<>();

		// Process regular parameters

		if (operation.getParameters() != null) {
			for (Parameter parameter : operation.getParameters()) {
				Map<String, Object> paramMap =
					HashMapBuilder.<String, Object>put(
						"dataType",
						_getTypeScriptType(parameter.getSchema(), imports)
					).put(
						"name",
						StringUtil.replace(
							parameter.getName(), '-', StringPool.UNDERLINE)
					).put(
						"required",
						Validator.isNotNull(parameter.isRequired()) &&
						parameter.isRequired()
					).build();

				allParams.add(paramMap);

				paramsByType.computeIfAbsent(
					parameter.getIn() + "Params", k -> new ArrayList<>()
				).add(
					paramMap
				);
			}
		}

		// Process request body

		RequestBody requestBody = operation.getRequestBody();

		if ((requestBody != null) && (requestBody.getContent() != null) &&
			!requestBody.getContent(
			).isEmpty()) {

			Content firstBodyContent = requestBody.getContent(
			).values(
			).iterator(
			).next();

			if ((firstBodyContent != null) &&
				(firstBodyContent.getSchema() != null)) {

				String bodyParamName = _getTypeScriptType(
					firstBodyContent.getSchema(), imports);

				if (Validator.isNull(
						firstBodyContent.getSchema(
						).getReference())) {

					bodyParamName = "body";
				}

				Map<String, Object> bodyParam =
					HashMapBuilder.<String, Object>put(
						"dataType",
						_getTypeScriptType(firstBodyContent.getSchema(), imports)
					).put(
						"name", StringUtil.replace(
							bodyParamName, '-', StringPool.UNDERLINE)
					).put(
						"required",
						false // TODO: Retrieve 'required' property inside requestBody
					).build();

				allParams.add(bodyParam);
				operationMap.put("bodyParam", bodyParam);
			}
		}

		// Add parameter lists to operation map

		paramsByType.forEach(
			(key, value) -> {
				if (!value.isEmpty()) {
					operationMap.put(key, value);
				}
			});

		return allParams;
	}

	private String _getTypeScriptType(
		Schema schema, Set<Map<String, String>> imports) {

		if (schema == null) {
			return "any";
		}

		if (schema.getReference() != null) {
			String schemaReference = schema.getReference();

			String typeName = schemaReference.substring(
				schemaReference.lastIndexOf('/') + 1);

			if (imports != null) {
				imports.add(Collections.singletonMap("classname", typeName));
			}

			return typeName;
		}

		String schemaType = schema.getType();

		if (schemaType.equals("array")) {
			String nestedType = _getTypeScriptType(
				schema.getItems(
				).toSchema(),
				imports);

			return "Array<" + nestedType + ">";
		}
		else if (schemaType.equals("boolean")) {
			return "boolean";
		}
		else if (schemaType.equals("integer") || schemaType.equals("number")) {
			return "number";
		}
		else if (schemaType.equals("object")) {
			if (schema.getAdditionalPropertySchema() != null) {
				Schema valueSchema = schema.getAdditionalPropertySchema();

				if (valueSchema.getAdditionalPropertySchema() != null) {
					Schema nestedValueSchema =
						valueSchema.getAdditionalPropertySchema();

					return "{ [key: string]: { [key: string]: " +
						_getTypeScriptType(nestedValueSchema, imports) +
							"; }; }";
				}

				return "{ [key: string]: " +
					_getTypeScriptType(valueSchema, imports) + "; }";
			}

			return "object";
		}
		else if (schemaType.equals("permission")) {
			if (imports != null) {
				imports.add(
					Collections.singletonMap("classname", "Permission"));
			}

			return "Permission";
		}
		else if (schemaType.equals("string")) {
			if ((schema.getEnumValues() != null) &&
				!schema.getEnumValues(
				).isEmpty()) {

				StringBuilder enumValues = new StringBuilder();

				for (String value : schema.getEnumValues()) {
					if (enumValues.length() > 0) {
						enumValues.append(" | ");
					}

					enumValues.append(
						"'"
					).append(
						value
					).append(
						"'"
					);
				}

				return enumValues.toString();
			}

			String schemaFormat = schema.getFormat();

			if (Validator.isNotNull(schemaFormat) &&
				(schemaFormat.equals("date") ||
				 schemaFormat.equals("date-time"))) {

				return "Date";
			}

			return "string";
		}

		return "any";
	}

	private final File _baseDir;
	private final File _copyrightFile;
	private final OpenAPIYAML _openAPIYAML;

}