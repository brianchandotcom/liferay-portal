/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.validator.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.problem.Problem;
import com.liferay.search.experiences.problem.Severity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaClient;
import org.everit.json.schema.loader.SchemaLoader;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * @author Petteri Karttunen
 */
public class JSONSchemaValidatorUtil {

	public static List<Problem> validate(
			Class<?> clazz, String json, String resourcePath)
		throws ValidationException {

		if (Validator.isNull(json)) {
			return Collections.<Problem>emptyList();
		}

		try {
			SchemaLoader schemaLoader = SchemaLoader.builder(
			).schemaClient(
				SchemaClient.classPathAwareClient()
			).schemaJson(
				new JSONObject(
					new JSONTokener(clazz.getResourceAsStream(resourcePath)))
			).resolutionScope(
				"classpath://com/liferay/search/experiences/internal" +
					"/validator/dependencies/"
			).build();

			Schema.Builder<?> builder = schemaLoader.load();

			Schema schema = builder.build();

			schema.validate(_filterJSONObject(new JSONObject(json)));

			return Collections.<Problem>emptyList();
		}
		catch (ValidationException validationException) {
			List<Problem> problems = new ArrayList<>();

			_addProblems(clazz.getName(), problems, validationException);

			return problems;
		}
	}

	private static void _addProblem(
		String className, List<Problem> problems,
		ValidationException validationException) {

		String keyword = validationException.getKeyword();

		if (_excludedKeywords.contains(keyword)) {
			return;
		}

		String rootJSONObjectPropertyKey =
			validationException.getPointerToViolation();

		if (StringUtil.startsWith(rootJSONObjectPropertyKey, "#") &&
			(rootJSONObjectPropertyKey.length() > 1)) {

			rootJSONObjectPropertyKey = rootJSONObjectPropertyKey.substring(1);
		}

		problems.add(
			new Problem.Builder().className(
				className
			).message(
				_getMessage(keyword, validationException)
			).rootConfiguration(
				_getRootConfiguration(validationException)
			).rootJSONObjectPropertyKey(
				rootJSONObjectPropertyKey
			).severity(
				Severity.ERROR
			).throwable(
				validationException
			).build());
	}

	private static void _addProblems(
		String className, List<Problem> problems,
		ValidationException validationException1) {

		if (ListUtil.isEmpty(validationException1.getCausingExceptions())) {
			_addProblem(className, problems, validationException1);

			return;
		}

		for (ValidationException validationException2 :
				validationException1.getCausingExceptions()) {

			if (ListUtil.isEmpty(validationException2.getCausingExceptions())) {
				_addProblem(className, problems, validationException2);
			}
			else {
				_addProblems(className, problems, validationException2);
			}
		}
	}

	private static JSONObject _filterJSONObject(JSONObject jsonObject) {
		JSONObject filteredJSONObject = new JSONObject();

		Set<String> keySet = jsonObject.keySet();

		Stream<String> stream = keySet.stream();

		stream.filter(
			key -> {
				Object object = jsonObject.get(key);

				if (object instanceof JSONObject) {
					JSONObject currentJSONObject = (JSONObject)object;

					if (currentJSONObject.length() == 0) {
						return false;
					}
				}

				return true;
			}
		).forEach(
			key -> filteredJSONObject.put(key, jsonObject.get(key))
		);

		return filteredJSONObject;
	}

	private static String _getMessage(
		String keyword, ValidationException validationException) {

		if (keyword.equals("const") || keyword.equals("enum")) {
			Schema schema = validationException.getViolatedSchema();

			JSONObject jsonObject = new JSONObject(schema.toString());

			return StringBundler.concat(
				validationException.getErrorMessage(), ". Expected value is ",
				jsonObject.get(keyword), ".");
		}

		return validationException.getErrorMessage();
	}

	private static String _getRootConfiguration(
		ValidationException validationException) {

		String rootConfiguration = validationException.getPointerToViolation();

		int end = rootConfiguration.indexOf("/", 2);

		if (StringUtil.startsWith(rootConfiguration, "#") && (end > 0)) {
			return rootConfiguration.substring(2, end);
		}

		return null;
	}

	private static final Set<String> _excludedKeywords = SetUtil.fromArray(
		"allOf", "anyOf", "oneOf", "none");

}