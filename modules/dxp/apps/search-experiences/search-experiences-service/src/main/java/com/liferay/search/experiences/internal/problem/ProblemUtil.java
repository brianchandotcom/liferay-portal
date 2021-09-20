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

package com.liferay.search.experiences.internal.problem;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.search.experiences.problem.Problem;
import com.liferay.search.experiences.problem.Severity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Petteri Karttunen
 * @author Brian Wing Shun Chan
 */
public class ProblemUtil {

	public static void addError(
		String className, String languageKey, Object rootObject,
		String rootProperty, String rootValue, Throwable throwable) {

		_addProblem(
			className, languageKey, _getExceptionMessage(throwable, className),
			rootObject, rootProperty, rootValue, Severity.ERROR, throwable);
	}

	public static void addInvalidConfigurationValueError(
		String className, Object rootObject, String rootProperty,
		String rootValue, Throwable throwable) {

		_addProblem(
			className, "invalid-configuration-value",
			_getExceptionMessage(throwable, className), rootObject,
			rootProperty, rootValue, Severity.ERROR, throwable);
	}

	public static void addInvalidConfigurationValueTypeError(
		String className, String expectedType, Object rootObject,
		String rootProperty, String rootValue) {

		StringBundler sb = new StringBundler(5);

		sb.append("[ ");
		sb.append(rootProperty);
		sb.append(" ] has to be of type [ ");
		sb.append(expectedType);
		sb.append(" ] ");

		_addProblem(
			className, "invalid-configuration-value-type", sb.toString(),
			rootObject, rootProperty, rootValue, Severity.ERROR, null);
	}

	public static void addProblem(Problem problem) {
		List<Problem> problems = _threadLocal.get();

		problems.add(problem);
	}

	public static void addRequiredPropertyMissingError(
		String className, Object rootObject, String rootProperty) {

		_addProblem(
			className, "a-required-field-is-missing",
			"[ " + rootProperty + " ] must be defined", rootObject,
			rootProperty, null, Severity.ERROR, null);
	}

	public static void addUnknownError(
		String className, Object rootObject, String rootProperty,
		String rootValue, Throwable throwable) {

		_addProblem(
			className, "there-was-an-unknown-error",
			_getExceptionMessage(throwable, className), rootObject,
			rootProperty, rootValue, Severity.ERROR, throwable);
	}

	public static void addUnknownError(String className, Throwable throwable) {
		_addProblem(
			className, "there-was-an-unknown-error",
			_getExceptionMessage(throwable, className), null, null, null,
			Severity.ERROR, throwable);
	}

	public static void addWarning(
		String className, String languageKey, Object rootObject,
		String rootProperty, String rootValue, Throwable throwable) {

		_addProblem(
			className, languageKey, _getExceptionMessage(throwable, className),
			rootObject, rootProperty, rootValue, Severity.WARN, throwable);
	}

	public static void addWarning(
		String className, String languageKey, Object rootObject,
		String rootProperty, String rootValue) {

		_addProblem(
			className, languageKey, null, rootObject, rootProperty,
			rootValue, Severity.WARN, null);
	}

	public static List<Problem> getProblems() {
		return _threadLocal.get();
	}

	public static List<Problem> getProblems(Severity severity) {
		return _threadLocal.get();
	}

	public static boolean hasErrors() {
		List<Problem> problems = _threadLocal.get();

		Stream<Problem> stream = problems.stream();

		return stream.anyMatch(
			m -> m.getSeverity(
			).equals(
				Severity.ERROR
			));
	}

	private static void _addProblem(
		String className, String languageKey, String message, Object rootObject,
		String rootProperty, String rootValue, Severity severity,
		Throwable throwable) {

		addProblem(
			new Problem.Builder().className(
				className
			).languageKey(
				languageKey
			).message(
				message
			).rootObject(
				rootObject
			).rootProperty(
				rootProperty
			).rootValue(
				rootValue
			).throwable(
				throwable
			).severity(
				severity
			).build());
	}

	private static String _getExceptionMessage(
		Throwable throwable, String className) {

		if (throwable != null) {
			return throwable.getMessage();
		}

		return className + " reported an error";
	}

	private static final ThreadLocal<List<Problem>> _threadLocal =
		new CentralizedThreadLocal<>(
			ProblemUtil.class.getName() + "._threadLocal", ArrayList::new);

}