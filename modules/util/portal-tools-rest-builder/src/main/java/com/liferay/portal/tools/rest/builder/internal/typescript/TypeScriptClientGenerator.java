package com.liferay.portal.tools.rest.builder.internal.typescript;

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
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Schema;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.LinkedHashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TypeScriptClientGenerator {
	private final File baseDir;
	private final File copyrightFile;
	private final OpenAPIYAML openAPIYAML;

	public TypeScriptClientGenerator(File baseDir, File openAPIYAMLFile, File copyrightFile) throws IOException {
		this.baseDir = baseDir;
		this.copyrightFile = copyrightFile;
		this.openAPIYAML = YAMLUtil.loadOpenAPIYAML(FileUtil.read(openAPIYAMLFile));
	}

	public void generate() throws Exception {
		Map<String, PathItem> pathItems = openAPIYAML.getPathItems();
		if (pathItems == null) return;

		Files.createDirectories(Paths.get(baseDir.getPath(), "src", "node", "api"));
		Files.createDirectories(Paths.get(baseDir.getPath(), "src", "node", "model"));

		//Generate API files
		Set<Map.Entry<String, Map<String, Object>>> apiContexts = _buildApiContexts(pathItems).entrySet();
		for (Map.Entry<String, Map<String, Object>> apiContext : apiContexts) {
			File apiFile = new File(baseDir.getPath() + "/src/node/api/" + StringUtil.lowerCaseFirstLetter(apiContext.getKey()) + "Api.ts");
			FileUtil.write(
				apiFile,
				FreeMarkerUtil.processTemplate(copyrightFile, FileUtil.getCopyrightYear(apiFile), "ts/api", apiContext.getValue()));
		}

		//Generate node/api/apis.ts file
		File apisFile = new File(baseDir.getPath() + "/src/node/api/apis.ts");
		FileUtil.write(
			apisFile,
			FreeMarkerUtil.processTemplate(copyrightFile, FileUtil.getCopyrightYear(apisFile), "ts/apis", Collections.singletonMap("apiContexts", apiContexts)));

		// Generate Model files
		Map<String, Schema> schemas = openAPIYAML.getComponents().getSchemas();
		if (schemas != null) {
			for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
				Map<String, Object> modelContext = _buildModelContext(entry.getKey(), entry.getValue());
				File modelFile = new File(baseDir.getPath() + "/src/node/model/" + StringUtil.lowerCaseFirstLetter(entry.getKey()) + ".ts");
				FileUtil.write(
					modelFile,
					FreeMarkerUtil.processTemplate(copyrightFile, FileUtil.getCopyrightYear(modelFile), "ts/model", modelContext));
			}
		}

		// Generate node/model/models.ts file
		File modelsFile = new File(baseDir.getPath() + "/src/node/model/models.ts");
		FileUtil.write(
			modelsFile,
			FreeMarkerUtil.processTemplate(copyrightFile, FileUtil.getCopyrightYear(modelsFile), "ts/models", Collections.singletonMap("modelContexts", schemas)));

		// Generate node/api.ts file
		File apiFile = new File(baseDir.getPath() + "/src/node/api.ts");
		FileUtil.write(
			apiFile,
			FreeMarkerUtil.processTemplate(copyrightFile, FileUtil.getCopyrightYear(apiFile), "ts/api_global", null));
	}

	private Map<String, Map<String, Object>> _buildApiContexts(Map<String, PathItem> pathItems) {
		Map<String, List<Map<String, Object>>> operationsByTag = new HashMap<>();

		for (Map.Entry<String, PathItem> entry : pathItems.entrySet()) {
			for (Operation operation : OpenAPIParserUtil.getOperations(entry.getValue())) {
				String tag = operation.getTags().get(0);
				List<Map<String, Object>> operations = operationsByTag.computeIfAbsent(tag, k -> new ArrayList<>());
				operations.add(_buildOperationMap(operation, entry.getKey()));
			}
		}
		Map<String, Map<String, Object>> apiContexts = new HashMap<>();
		for (Map.Entry<String, List<Map<String, Object>>> entry : operationsByTag.entrySet()) {
			Map<String, Object> apiContext = new HashMap<>();
			apiContext.put("operations", entry.getValue());
			apiContext.put("classname", entry.getKey() + "Api");

			// Collect all imports
			Set<Map<String, String>> allImports = entry.getValue().stream()
				.filter(op -> op.containsKey("imports"))
				.flatMap(op -> ((Collection<Map<String, String>>) op.remove("imports")).stream())
				.collect(Collectors.toCollection(LinkedHashSet::new));

			if (!allImports.isEmpty()) {
				apiContext.put("imports", new ArrayList<>(allImports));
			}

			apiContexts.put(entry.getKey(), apiContext);

		}

		return apiContexts;
	}

	private Map<String, Object> _buildModelContext(String modelName, Schema schema) {
		Map<String, Object> context = new HashMap<>();
		Set<Map<String, String>> modelImports = new HashSet<>();

		context.put("modelName", modelName);
		context.put("description", schema.getDescription());

		if (schema.getDiscriminator() != null && schema.getDiscriminator().getPropertyName() != null) {
			context.put("discriminator", schema.getDiscriminator().getPropertyName());
		}

		// Handle inheritance and properties
		List<Map<String, Object>> properties = new ArrayList<>();
		if (schema.getAllOfSchemas() != null) {
			Schema parentSchema = schema.getAllOfSchemas().get(0);
			if (parentSchema.getReference() != null) {
				String parentClass = parentSchema.getReference().substring(
					parentSchema.getReference().lastIndexOf('/') + 1);
				context.put("parent", parentClass);
				modelImports.add(Collections.singletonMap("classname", parentClass));

				// Add properties from additional schemas in allOf
				for (Schema additionalSchema: schema.getAllOfSchemas()){
					if (additionalSchema.getPropertySchemas() != null) {
						additionalSchema.getPropertySchemas().forEach((propName, propSchema) -> {
							Map<String, Object> prop = new HashMap<>();
							prop.put("name", propName);
							String type = _getTypeScriptType(propSchema, modelImports);
							prop.put("type", type);
							prop.put("required", additionalSchema.getRequiredPropertySchemaNames() != null &&
												 additionalSchema.getRequiredPropertySchemaNames().contains(propName));
							properties.add(prop);
						});
					}
				}
			}
		}

		if (schema.getPropertySchemas() != null) {
			schema.getPropertySchemas().forEach((propName, propSchema) -> {
				Map<String, Object> prop = new HashMap<>();
				prop.put("name", propName);
				String type = _getTypeScriptType(propSchema, modelImports);
				prop.put("type", type);
				prop.put("required", schema.getRequiredPropertySchemaNames() != null &&
									 schema.getRequiredPropertySchemaNames().contains(propName));
				properties.add(prop);
			});
		}
		context.put("properties", properties);

		if (!modelImports.isEmpty()) {
			context.put("imports", new ArrayList<>(modelImports));
		}

		return context;
	}

	private Map<String, Object> _buildOperationMap(Operation operation, String path) {
		Set<Map<String, String>> imports = new HashSet<>();
		Map<String, Object> operationMap = new HashMap<>();

		operationMap.put("nickname", operation.getOperationId());
		operationMap.put("notes", operation.getDescription());
		operationMap.put("path", path);
		operationMap.put("httpMethod", OpenAPIParserUtil.getHTTPMethod(operation).toUpperCase());

		// Extract produces (media types) from responses.
		if (operation.getResponses() != null) {
			Set<String> produces = operation.getResponses().values().stream()
				.flatMap(response -> response.getContent() != null
					? response.getContent().keySet().stream()
					: Stream.empty())
				.collect(Collectors.toCollection(LinkedHashSet::new));

			if (!produces.isEmpty()) {
				operationMap.put("produces", new ArrayList<>(produces));
			}
		}

		Collection<Map<String, Object>> params = _getAllOperationParams(operation, operationMap, imports);
		if (!params.isEmpty()) {
			operationMap.put("allParams", params);
		}

		if (operation.getResponses() == null) return null;

		String returnType = operation.getResponses().entrySet().stream()
			.filter(e -> e.getKey().getHttpCode() == 200)
			.findFirst()
			.map(e -> e.getValue().getContent())
			.filter(content -> content != null && !content.isEmpty())
			.map(content -> content.values().iterator().next())
			.filter(c -> c != null && c.getSchema() != null)
			.map(c -> _getTypeScriptType(c.getSchema(), imports))
			.orElse(null);
		operationMap.put("returnType", returnType);

		if (!imports.isEmpty()) {
			operationMap.put("imports", new ArrayList<>(imports));
		}

		return operationMap;
	}

	private Collection<Map<String, Object>> _getAllOperationParams(Operation operation, Map<String, Object> operationMap, Set<Map<String, String>> imports) {
		List<Map<String, Object>> allParams = new ArrayList<>();
		Map<String, List<Map<String, Object>>> paramsByType = new HashMap<>();

		// Process regular parameters
		if (operation.getParameters() != null) {
			for (Parameter parameter : operation.getParameters()) {
				Map<String, Object> paramMap = new HashMap<>();

				paramMap.put("name", parameter.getName());
				paramMap.put("required", Validator.isNotNull(parameter.isRequired()) && parameter.isRequired());
				paramMap.put("dataType", _getTypeScriptType(parameter.getSchema(), imports));

				allParams.add(paramMap);

				String paramType = parameter.getIn() + "Params";
				paramsByType.computeIfAbsent(paramType, k -> new ArrayList<>()).add(paramMap);
			}
		}

		// Process request body
		RequestBody requestBody = operation.getRequestBody();
		if (requestBody != null && requestBody.getContent() != null && !requestBody.getContent().isEmpty()) {
			Content content = requestBody.getContent().values().iterator().next();
			if (content != null && content.getSchema() != null) {
				String dataType = _getTypeScriptType(content.getSchema(), imports);
				Map<String, Object> bodyParam = new HashMap<>();
				bodyParam.put("name", (Validator.isNull(content.getSchema().getReference())) ? "body" : dataType);
				bodyParam.put("dataType", dataType);
				bodyParam.put("required", false);	// I can't retrieve the 'required' property inside requestBody
				allParams.add(bodyParam);
				operationMap.put("bodyParam", bodyParam);
			}
		}

		// Add parameter lists to operation map
		paramsByType.forEach((key, value) -> {
			if (!value.isEmpty()) {
				operationMap.put(key, value);
			}
		});

		return allParams;
	}

	private String _getTypeScriptType(Schema schema, Set<Map<String, String>> imports) {
		if (schema == null) return "any";

		if (schema.getReference() != null) {
			String typeName = schema.getReference().substring(schema.getReference().lastIndexOf('/') + 1);
			if (imports != null) {
				imports.add(Collections.singletonMap("classname", typeName));
			}
			return typeName;
		}

		String type = schema.getType();
		if (type == null) return "any";

		switch (type) {
			case "array":
				return "Array<" + _getTypeScriptType(schema.getItems().toSchema(), imports) + ">";
			case "object":
				if (schema.getAdditionalPropertySchema() != null) {
					Schema valueSchema = schema.getAdditionalPropertySchema();
					if (valueSchema.getAdditionalPropertySchema() != null) {
						Schema nestedValueSchema = valueSchema.getAdditionalPropertySchema();
						return "{ [key: string]: { [key: string]: " + _getTypeScriptType(nestedValueSchema, imports) + "; }; }";
					}
					return "{ [key: string]: " + _getTypeScriptType(valueSchema, imports) + "; }";
				}
				return "object";
			case "integer":
			case "number":
				return "number";
			case "string":
				if (schema.getEnumValues() != null && !schema.getEnumValues().isEmpty()) {
					return schema.getEnumValues().stream()
						.map(value -> "'" + value + "'")
						.collect(Collectors.joining(" | "));
				}
				return "date".equals(schema.getFormat()) || "date-time".equals(schema.getFormat())
					? "Date" : "string";
			case "boolean":
				return "boolean";
			case "permission":
				if (imports != null) {
					imports.add(Collections.singletonMap("classname", "Permission"));
				}
				return "Permission";
			default:
				return "any";
		}
	}
}