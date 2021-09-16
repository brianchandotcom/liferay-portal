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

package com.liferay.search.experiences.internal.validator;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.search.experiences.problem.Problem;
import com.liferay.search.experiences.problem.Severity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;

/**
 * @author Petteri Karttunen
 */
public abstract class BaseJSONValidator {

	protected void addJSONValidationProblem(
		List<Problem> problems, ValidationException validationException) {

		String keyword = validationException.getKeyword();

		if (!isLoggedKeyword(keyword)) {
			return;
		}

		String rootProperty = validationException.getPointerToViolation();

		if (StringUtil.startsWith(rootProperty, "#") &&
			(rootProperty.length() > 1)) {

			rootProperty = rootProperty.substring(1);
		}

		problems.add(
			new Problem.Builder().className(
				getClass().getName()
			).languageKey(
				_getProblemLanguageKey(keyword)
			).message(
				_getMessage(validationException, keyword)
			).rootConfiguration(
				_getRootConfiguration(validationException)
			).rootJSONObjectPropertyKey(
				rootProperty
			).severity(
				Severity.ERROR
			).throwable(
				validationException
			).build());
	}

	protected void addJSONValidationProblems(
		List<Problem> problems, ValidationException validationException1) {

		List<ValidationException> causingExceptions1 =
			validationException1.getCausingExceptions();

		if (causingExceptions1.isEmpty()) {
			addJSONValidationProblem(problems, validationException1);
		}
		else {
			for (ValidationException validationException2 :
					causingExceptions1) {

				List<ValidationException> causingExceptions2 =
					validationException2.getCausingExceptions();

				if (causingExceptions2.isEmpty()) {
					addJSONValidationProblem(problems, validationException2);
				}
				else {
					addJSONValidationProblems(problems, validationException2);
				}
			}
		}
	}

	protected boolean isLoggedKeyword(String keyword) {
		return !_excludedKeywords.contains(keyword);
	}

	private String _getExpectedValue(
		ValidationException validationException, String keyword) {

		try {
			Schema schema = validationException.getViolatedSchema();

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				schema.toString());

			return ". Expected value is " +
				jsonObject.get(
					keyword
				).toString();
		}
		catch (JSONException jsonException) {
			_log.error(jsonException.getMessage(), jsonException);
		}

		return "";
	}

	private String _getMessage(
		ValidationException validationException, String keyword) {

		StringBundler sb = new StringBundler(2);

		sb.append(validationException.getErrorMessage());

		if (keyword.equals("const") || keyword.equals("enum")) {
			sb.append(_getExpectedValue(validationException, keyword));
		}

		return sb.toString();
	}

	private String _getProblemLanguageKey(String keyword) {
		return "json.validator.invalid." + keyword;
	}

	private String _getRootConfiguration(
		ValidationException validationException) {

		String pointer = validationException.getPointerToViolation();

		int end = pointer.indexOf("/", 2);

		if (StringUtil.startsWith(pointer, "#") && (end > 0)) {
			return pointer.substring(2, end);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseJSONValidator.class);

	private static final List<String> _excludedKeywords = new ArrayList<>(
		Arrays.asList("allOf", "anyOf", "oneOf", "none"));

}