/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mcp.server.rest.internal.search.index;

import com.liferay.mcp.server.rest.internal.search.index.constants.MCPToolConstants;
import com.liferay.mcp.server.rest.internal.search.index.util.IntentUtil;
import com.liferay.mcp.server.rest.internal.search.index.util.WordUtil;
import com.liferay.mcp.server.rest.internal.util.OpenAPIUtil;
import com.liferay.mcp.server.rest.internal.util.ToolSetUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.application.HeadlessApplicationProvider;

import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Petteri Karttunen
 */
public class MCPToolFactoryUtil {

	public static List<MCPTool> getMCPTools(
		HttpServletRequest httpServletRequest,
		HeadlessApplicationProvider.OpenAPIDocument openAPIDocument,
		String toolSetName) {

		List<MCPTool> mcpTools = new ArrayList<>();

		JSONObject openAPIJSONObject = ToolSetUtil.getOpenAPIJSONObject(
			httpServletRequest, toolSetName);

		JSONObject pathsJSONObject = openAPIJSONObject.getJSONObject("paths");

		if (pathsJSONObject == null) {
			return mcpTools;
		}

		HeadlessApplicationProvider.Application application =
			openAPIDocument.getApplication();

		for (String path : pathsJSONObject.keySet()) {
			JSONObject pathItemJSONObject = pathsJSONObject.getJSONObject(path);

			mcpTools.addAll(
				TransformUtil.transformToList(
					OpenAPIUtil.METHODS,
					method -> _getMCPTool(
						application, method, openAPIJSONObject, path,
						pathItemJSONObject, toolSetName)));
		}

		return mcpTools;
	}

	private static String _getDescription(JSONObject operationJSONObject) {
		String description = StringUtil.trim(
			operationJSONObject.getString("description"));
		String summary = StringUtil.trim(
			operationJSONObject.getString("summary"));

		if (Validator.isNull(summary)) {
			return description;
		}

		if (Validator.isNull(description)) {
			return summary;
		}

		if (!StringUtil.endsWith(summary, CharPool.PERIOD)) {
			summary += StringPool.PERIOD;
		}

		return summary + StringPool.SPACE + description;
	}

	private static String _getEntityName(JSONObject operationJSONObject) {
		JSONArray tagsJSONArray = operationJSONObject.getJSONArray("tags");

		if (JSONUtil.isEmpty(tagsJSONArray)) {
			return StringPool.BLANK;
		}

		return tagsJSONArray.getString(0);
	}

	private static String[] _getExpansions(
		boolean batch, boolean collection, String entityWords, String intent,
		String operationActionSuffix, String path, String toolName) {

		entityWords = StringUtil.trim(entityWords);

		if (Validator.isNull(entityWords) || Validator.isNull(intent)) {
			return new String[0];
		}

		String toolNameRemainder = _removeMethodPrefix(toolName);

		boolean plural = false;

		if (batch || collection) {
			plural = true;
		}

		if (collection) {
			toolNameRemainder = _removeSuffix(toolNameRemainder, "Page");
		}

		if (operationActionSuffix != null) {
			toolNameRemainder = _removeSuffix(
				toolNameRemainder, operationActionSuffix);

			plural = _isCollectionAction(path);
		}

		String parentEntityName = _getParentEntityName(
			entityWords, path, toolNameRemainder);

		if (parentEntityName != null) {
			toolNameRemainder = toolNameRemainder.substring(
				parentEntityName.length());
		}

		if (batch) {
			toolNameRemainder = _removeSuffix(toolNameRemainder, "Batch");
		}

		String pathParameterWords = _getLastPathParameterWords(path);

		NounPhrase nounPhrase = _getNounPhrase(
			entityWords, pathParameterWords, plural, toolNameRemainder);

		String parentPhrase = _getParentPhrase(parentEntityName);

		String parameterPhrase = _getLastParameterPhrase(
			nounPhrase, parentPhrase, pathParameterWords);

		return TransformUtil.transform(
			IntentUtil.getIntentVerbs(intent),
			verb -> _toExpansion(
				batch, nounPhrase, parameterPhrase, parentPhrase, verb),
			String.class);
	}

	private static String _getLastParameterPhrase(
		NounPhrase nounPhrase, String parentPhrase, String pathParameterWords) {

		if (Validator.isNull(pathParameterWords) ||
			StringUtil.contains(
				nounPhrase.toString(), pathParameterWords, StringPool.SPACE)) {

			return StringPool.BLANK;
		}

		for (String word :
				StringUtil.split(pathParameterWords, CharPool.SPACE)) {

			if (StringUtil.contains(parentPhrase, word, StringPool.SPACE)) {
				return StringPool.BLANK;
			}
		}

		return "by " + pathParameterWords;
	}

	private static String _getLastPathParameterWords(String path) {
		String pathParameter = OpenAPIUtil.getLastPathParameter(path);

		if (pathParameter == null) {
			return StringPool.BLANK;
		}

		return WordUtil.toWords(pathParameter);
	}

	private static MCPTool _getMCPTool(
		HeadlessApplicationProvider.Application application, String method,
		JSONObject openAPIJSONObject, String path,
		JSONObject pathItemJSONObject, String toolSetName) {

		JSONObject operationJSONObject = pathItemJSONObject.getJSONObject(
			method);

		if (operationJSONObject == null) {
			return null;
		}

		String toolName = operationJSONObject.getString("operationId");

		if (Validator.isBlank(toolName)) {
			return null;
		}

		String operationActionSuffix = _getOperationActionSuffix(
			method, path, toolName);

		String entityName = _getEntityName(operationJSONObject);

		String operationVariant = _getOperationVariant(entityName, path);

		if (Objects.equals(
				operationVariant,
				MCPToolConstants.OPERATION_VARIANT_TRAVERSAL)) {

			entityName = _getTraversalEntityName(entityName, path, toolName);
		}

		boolean batch = Objects.equals(
			operationVariant, MCPToolConstants.OPERATION_VARIANT_BATCH);

		JSONObject responseSchemaJSONObject =
			OpenAPIUtil.getResponseSchemaJSONObject(operationJSONObject);

		boolean collection = OpenAPIUtil.isCollectionSchema(
			openAPIJSONObject, responseSchemaJSONObject);

		String intent = IntentUtil.getOperationIntent(
			collection, method, operationActionSuffix);

		JSONObject requestBodySchemaJSONObject =
			OpenAPIUtil.getRequestBodySchemaJSONObject(
				openAPIJSONObject, operationJSONObject);

		return new MCPTool(
			collection, operationJSONObject.getBoolean("deprecated"),
			_getDescription(operationJSONObject), entityName,
			_getExpansions(
				batch, collection, WordUtil.toWords(entityName), intent,
				operationActionSuffix, path, toolName),
			intent, method, operationVariant,
			_getParameterNames(operationJSONObject, pathItemJSONObject),
			Portal.PATH_MODULE + application.getBasePath() + path,
			_getRequiredReferences(
				openAPIJSONObject, path, requestBodySchemaJSONObject),
			_getSchemaPropertyNames(
				openAPIJSONObject, requestBodySchemaJSONObject,
				responseSchemaJSONObject),
			toolName, toolSetName);
	}

	private static NounPhrase _getNounPhrase(
		String entityWords, String pathParameterWords, boolean plural,
		String toolNameRemainder) {

		NounPhrase nounPhrase = _getNounPhrase(
			entityWords, pathParameterWords, toolNameRemainder);

		if (!plural) {
			return nounPhrase;
		}

		return new NounPhrase(
			WordUtil.toPlural(nounPhrase._noun), nounPhrase._wordsBefore,
			nounPhrase._wordsAfter);
	}

	private static NounPhrase _getNounPhrase(
		String entityWords, String pathParameterWords,
		String toolNameRemainder) {

		String toolNameWords = WordUtil.toWords(toolNameRemainder);

		if (Validator.isNotNull(pathParameterWords) &&
			StringUtil.startsWith(
				toolNameWords, pathParameterWords + StringPool.SPACE)) {

			toolNameWords = toolNameWords.substring(
				pathParameterWords.length() + 1);
		}

		String[] words = StringUtil.split(toolNameWords, CharPool.SPACE);

		Set<String> singularEntityWords = new HashSet<>();

		for (String word : StringUtil.split(entityWords, CharPool.SPACE)) {
			singularEntityWords.add(WordUtil.toSingular(word));
		}

		int firstEntityWordIndex = words.length;
		int lastEntityWordIndex = words.length;

		for (int i = 0; i < words.length; i++) {
			if (!singularEntityWords.contains(WordUtil.toSingular(words[i]))) {
				continue;
			}

			if (firstEntityWordIndex == words.length) {
				firstEntityWordIndex = i;
			}

			lastEntityWordIndex = i;
		}

		List<String> wordsAfterEntity = new ArrayList<>();
		List<String> wordsBeforeEntity = new ArrayList<>();
		List<String> wordsBetweenEntityWords = new ArrayList<>();

		for (int i = 0; i < words.length; i++) {
			if (i < firstEntityWordIndex) {
				wordsBeforeEntity.add(words[i]);
			}
			else if (i > lastEntityWordIndex) {
				wordsAfterEntity.add(words[i]);
			}
			else if (!singularEntityWords.contains(
						WordUtil.toSingular(words[i]))) {

				wordsBetweenEntityWords.add(words[i]);
			}
		}

		String wordsBefore = StringUtil.merge(
			wordsBeforeEntity, StringPool.SPACE);

		if (firstEntityWordIndex == words.length) {
			if (StringUtil.startsWith(wordsBefore, "by ")) {
				return new NounPhrase(
					entityWords, StringPool.BLANK, wordsBefore);
			}

			return _getToolNameNounPhrase(wordsBeforeEntity);
		}

		if (StringUtil.startsWith(wordsBefore, "by ")) {
			wordsBefore = StringPool.BLANK;
		}

		String wordsAfter = StringUtil.merge(
			wordsAfterEntity, StringPool.SPACE);

		String wordsBetween = StringUtil.merge(
			wordsBetweenEntityWords, StringPool.SPACE);

		if (Validator.isNull(wordsAfter) &&
			StringUtil.startsWith(wordsBetween, "by ")) {

			wordsAfter = wordsBetween;
		}

		if (Validator.isNull(wordsAfter)) {
			return new NounPhrase(entityWords, wordsBefore, StringPool.BLANK);
		}

		if (StringUtil.startsWith(wordsAfter, "by ") ||
			!WordUtil.isPlural(wordsAfter)) {

			return new NounPhrase(entityWords, wordsBefore, wordsAfter);
		}

		return new NounPhrase(
			wordsAfter,
			StringUtil.trim(wordsBefore + StringPool.SPACE + entityWords),
			StringPool.BLANK);
	}

	private static String _getOperationActionSuffix(
		String method, String path, String toolName) {

		if (Objects.equals(method, "get") || Objects.equals(method, "head") ||
			Objects.equals(method, "options")) {

			return null;
		}

		for (String operationActionSuffix :
				IntentUtil.getOperationActionSuffixes()) {

			if (toolName.endsWith(operationActionSuffix) &&
				_hasOperationActionSuffixSegment(operationActionSuffix, path)) {

				return operationActionSuffix;
			}
		}

		return null;
	}

	private static String _getOperationVariant(String entityName, String path) {
		String operationVariant = _getPathSegmentOperationVariant(path);

		if (Validator.isNotNull(operationVariant)) {
			return operationVariant;
		}

		if (_isTraversal(entityName, path)) {
			return MCPToolConstants.OPERATION_VARIANT_TRAVERSAL;
		}

		return StringPool.BLANK;
	}

	private static String[] _getParameterNames(
		JSONObject operationJSONObject, JSONObject pathItemJSONObject) {

		Set<String> parameterNames = new LinkedHashSet<>();

		parameterNames.addAll(
			JSONUtil.toStringList(
				operationJSONObject.getJSONArray("parameters"), "name"));
		parameterNames.addAll(
			JSONUtil.toStringList(
				pathItemJSONObject.getJSONArray("parameters"), "name"));

		return parameterNames.toArray(new String[0]);
	}

	private static String _getParentCollectionSegment(String path) {
		String[] segments = StringUtil.split(path, CharPool.SLASH);

		for (int i = 1; i < (segments.length - 1); i++) {
			if (!OpenAPIUtil.isPathParameter(segments[i])) {
				continue;
			}

			String segment = segments[i - 1];

			if (StringUtil.startsWith(segment, "by-")) {
				segment = segments[i - 2];
			}

			if (Validator.isNull(segment) ||
				StringUtil.startsWith(segment, "by-")) {

				return null;
			}

			return segment;
		}

		return null;
	}

	private static String _getParentEntityName(
		String entityWords, String path, String toolNameRemainder) {

		String parentCollectionSegment = _getParentCollectionSegment(path);

		if (parentCollectionSegment == null) {
			return null;
		}

		String parentEntityName = _toEntityName(parentCollectionSegment);

		if (!toolNameRemainder.startsWith(parentEntityName)) {
			return null;
		}

		int length = parentEntityName.length();

		if ((toolNameRemainder.length() <= length) ||
			!Character.isUpperCase(toolNameRemainder.charAt(length)) ||
			StringUtil.contains(
				entityWords, WordUtil.toWords(parentEntityName),
				StringPool.SPACE)) {

			return null;
		}

		return parentEntityName;
	}

	private static String _getParentPhrase(String parentEntityName) {
		if (parentEntityName == null) {
			return StringPool.BLANK;
		}

		String parentEntityWords = WordUtil.toWords(parentEntityName);

		if ("aeio".indexOf(parentEntityWords.charAt(0)) >= 0) {
			return "in an " + parentEntityWords;
		}

		return "in a " + parentEntityWords;
	}

	private static String _getPathSegmentOperationVariant(String path) {
		String[] segments = StringUtil.split(path, CharPool.SLASH);

		for (int i = segments.length - 1; i >= 0; i--) {
			String segment = StringUtil.toLowerCase(segments[i]);

			if (Validator.isNull(segment) ||
				OpenAPIUtil.isPathParameter(segment)) {

				continue;
			}

			int index = segment.indexOf(CharPool.PERIOD);

			if (index > 0) {
				segment = segment.substring(0, index);
			}

			String operationVariant =
				MCPToolConstants.pathSegmentOperationVariants.get(
					StringUtil.removeLast(segment, "-replace"));

			if (operationVariant != null) {
				return operationVariant;
			}
		}

		int byExternalReferenceCodeSegmentCount = 0;

		for (String segment : segments) {
			if (!StringUtil.startsWith(
					StringUtil.toLowerCase(segment), "by-external")) {

				continue;
			}

			byExternalReferenceCodeSegmentCount++;
		}

		if (byExternalReferenceCodeSegmentCount > 1) {
			return MCPToolConstants.OPERATION_VARIANT_NESTED;
		}

		return StringPool.BLANK;
	}

	private static Set<String> _getPropertyNames(
		JSONObject openAPIJSONObject, JSONObject schemaJSONObject) {

		Set<String> propertyNames = new LinkedHashSet<>();

		Map<String, JSONObject> propertiesJSONObjects =
			OpenAPIUtil.getPropertiesJSONObjects(
				openAPIJSONObject, schemaJSONObject);

		for (Map.Entry<String, JSONObject> entry :
				propertiesJSONObjects.entrySet()) {

			String propertyName = entry.getKey();

			if (propertyName.startsWith("x-") ||
				OpenAPIUtil.isNestedSchema(entry.getValue())) {

				continue;
			}

			propertyNames.add(propertyName);
		}

		return propertyNames;
	}

	private static String _getReferenceCollectionSegment(String propertyName) {
		String referencedEntityName = null;

		for (String suffix : _REFERENCE_PROPERTY_NAME_SUFFIXES) {
			if (propertyName.endsWith(suffix) &&
				(propertyName.length() > suffix.length())) {

				referencedEntityName = propertyName.substring(
					0, propertyName.length() - suffix.length());

				break;
			}
		}

		if (referencedEntityName == null) {
			return null;
		}

		return StringUtil.replace(
			WordUtil.toPlural(
				StringUtil.trim(WordUtil.toWords(referencedEntityName))),
			CharPool.SPACE, CharPool.DASH);
	}

	private static String[] _getRequiredReferences(
		JSONObject openAPIJSONObject, String path,
		JSONObject requestBodySchemaJSONObject) {

		Set<String> propertyNames = _getPropertyNames(
			openAPIJSONObject, requestBodySchemaJSONObject);

		return TransformUtil.transformToArray(
			OpenAPIUtil.getRequiredPropertyNames(
				openAPIJSONObject, requestBodySchemaJSONObject),
			propertyName -> {
				if (path.contains("{" + propertyName + "}") ||
					!propertyNames.contains(propertyName)) {

					return null;
				}

				String collectionSegment = _getReferenceCollectionSegment(
					propertyName);

				if (collectionSegment == null) {
					return null;
				}

				return propertyName + StringPool.POUND + collectionSegment;
			},
			String.class);
	}

	private static String[] _getSchemaPropertyNames(
		JSONObject openAPIJSONObject, JSONObject requestBodySchemaJSONObject,
		JSONObject responseSchemaJSONObject) {

		Set<String> propertyNames = _getPropertyNames(
			openAPIJSONObject, requestBodySchemaJSONObject);

		propertyNames.addAll(
			_getPropertyNames(openAPIJSONObject, responseSchemaJSONObject));

		return propertyNames.toArray(new String[0]);
	}

	private static NounPhrase _getToolNameNounPhrase(List<String> words) {
		int index = words.indexOf("by");

		if (index < 0) {
			return new NounPhrase(
				StringUtil.merge(words, StringPool.SPACE), StringPool.BLANK,
				StringPool.BLANK);
		}

		return new NounPhrase(
			StringUtil.merge(words.subList(0, index), StringPool.SPACE),
			StringPool.BLANK,
			StringUtil.merge(
				words.subList(index, words.size()), StringPool.SPACE));
	}

	private static String _getTraversalEntityName(
		String entityName, String path, String toolName) {

		if (!StringUtil.contains(
				_toSingularWords(WordUtil.toWords(toolName)),
				_toSingularWords(WordUtil.toWords(entityName)),
				StringPool.SPACE)) {

			return entityName;
		}

		String[] segments = StringUtil.split(path, CharPool.SLASH);

		for (int i = segments.length - 1; i >= 0; i--) {
			String segment = segments[i];

			if (OpenAPIUtil.isPathParameter(segment) ||
				StringUtil.startsWith(segment, "by-") ||
				!_isCollectionSegment(segment)) {

				continue;
			}

			return _toEntityName(segment);
		}

		return entityName;
	}

	private static boolean _hasOperationActionSuffixSegment(
		String operationActionSuffix, String path) {

		String normalizedOperationActionSuffix = WordUtil.normalize(
			operationActionSuffix);

		for (String segment : StringUtil.split(path, CharPool.SLASH)) {
			if (Validator.isNull(segment) ||
				OpenAPIUtil.isPathParameter(segment)) {

				continue;
			}

			String normalizedSegment = WordUtil.normalize(segment);

			if (normalizedSegment.endsWith(normalizedOperationActionSuffix) ||
				normalizedOperationActionSuffix.startsWith(
					WordUtil.toSingular(normalizedSegment))) {

				return true;
			}
		}

		return false;
	}

	private static boolean _isCollectionAction(String path) {
		String[] segments = StringUtil.split(path, CharPool.SLASH);

		if (segments.length < 2) {
			return false;
		}

		return _isCollectionSegment(segments[segments.length - 2]);
	}

	private static boolean _isCollectionSegment(String segment) {
		if (StringUtil.startsWith(segment, "has-") ||
			StringUtil.startsWith(segment, "is-")) {

			return false;
		}

		return WordUtil.isPlural(segment);
	}

	private static boolean _isEntitySegment(String entityName, String segment) {
		String entityWords = _toSingularWords(WordUtil.toWords(entityName));
		String segmentWords = _toSingularWords(
			WordUtil.toWords(_toEntityName(segment)));

		return StringUtil.endsWith(
			StringPool.SPACE + entityWords, StringPool.SPACE + segmentWords);
	}

	private static boolean _isTraversal(String entityName, String path) {
		if (Validator.isNull(entityName)) {
			return false;
		}

		List<String> segmentsAfterParameter = new ArrayList<>();

		boolean parameterSeen = false;

		for (String segment : StringUtil.split(path, CharPool.SLASH)) {
			if (Validator.isNull(segment)) {
				continue;
			}

			if (OpenAPIUtil.isPathParameter(segment)) {
				parameterSeen = true;

				continue;
			}

			if (!parameterSeen) {
				continue;
			}

			segmentsAfterParameter.add(segment);
		}

		if (segmentsAfterParameter.isEmpty()) {
			return false;
		}

		String segment = segmentsAfterParameter.get(
			segmentsAfterParameter.size() - 1);

		if (_isEntitySegment(entityName, segment)) {
			return false;
		}

		if (_isCollectionSegment(segment)) {
			return true;
		}

		if (segmentsAfterParameter.size() < 2) {
			return false;
		}

		segment = segmentsAfterParameter.get(segmentsAfterParameter.size() - 2);

		if (_isCollectionSegment(segment) &&
			!_isEntitySegment(entityName, segment)) {

			return true;
		}

		return false;
	}

	private static String _removeMethodPrefix(String toolName) {
		Matcher matcher = _methodPrefixPattern.matcher(toolName);

		return matcher.replaceFirst(StringPool.BLANK);
	}

	private static String _removeSuffix(String s, String suffix) {
		if (s.endsWith(suffix)) {
			return s.substring(0, s.length() - suffix.length());
		}

		return s;
	}

	private static String _toEntityName(String segment) {
		StringBundler sb = new StringBundler();

		for (String word :
				StringUtil.split(WordUtil.toSingular(segment), CharPool.DASH)) {

			sb.append(StringUtil.upperCaseFirstLetter(word));
		}

		return sb.toString();
	}

	private static String _toExpansion(
		boolean batch, NounPhrase nounPhrase, String parameterPhrase,
		String parentPhrase, String verb) {

		StringBundler sb = new StringBundler(12);

		if (batch) {
			sb.append("batch ");
		}

		sb.append(verb);
		sb.append(StringPool.SPACE);

		if (Validator.isNotNull(nounPhrase._wordsBefore)) {
			sb.append(nounPhrase._wordsBefore);
			sb.append(StringPool.SPACE);
		}

		sb.append(nounPhrase._noun);

		if (Validator.isNotNull(parentPhrase)) {
			sb.append(StringPool.SPACE);
			sb.append(parentPhrase);
		}

		if (Validator.isNotNull(nounPhrase._wordsAfter)) {
			sb.append(StringPool.SPACE);
			sb.append(nounPhrase._wordsAfter);
		}

		if (Validator.isNotNull(parameterPhrase)) {
			sb.append(StringPool.SPACE);
			sb.append(parameterPhrase);
		}

		return sb.toString();
	}

	private static String _toSingularWords(String words) {
		StringBundler sb = new StringBundler();

		for (String word : StringUtil.split(words, CharPool.SPACE)) {
			sb.append(WordUtil.toSingular(word));
			sb.append(StringPool.SPACE);
		}

		return StringUtil.trim(sb.toString());
	}

	private static final String[] _REFERENCE_PROPERTY_NAME_SUFFIXES = {
		"ExternalReferenceCode", "ExternalReferenceCodes", "Id", "Ids", "Key",
		"Keys"
	};

	private static final Pattern _methodPrefixPattern = Pattern.compile(
		StringBundler.concat(
			"^(", StringUtil.merge(OpenAPIUtil.METHODS, StringPool.PIPE), ")"));

	private static class NounPhrase {

		@Override
		public String toString() {
			return StringUtil.trim(
				StringBundler.concat(
					_wordsBefore, StringPool.SPACE, _noun, StringPool.SPACE,
					_wordsAfter));
		}

		private NounPhrase(String noun, String wordsBefore, String wordsAfter) {
			_noun = noun;
			_wordsBefore = wordsBefore;
			_wordsAfter = wordsAfter;
		}

		private final String _noun;
		private final String _wordsAfter;
		private final String _wordsBefore;

	}

}