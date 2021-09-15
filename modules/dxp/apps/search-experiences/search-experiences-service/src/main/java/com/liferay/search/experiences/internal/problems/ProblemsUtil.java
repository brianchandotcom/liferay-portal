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

package com.liferay.search.experiences.internal.problems;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.search.experiences.problems.Problem;
import com.liferay.search.experiences.problems.Severity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Petteri Karttunen
 * @author Brian Wing Shun Chan
 */
public class ProblemsUtil {

	public static void addProblem(Problem problem) {
	}

	public static List<Problem> getProblems() {
		return _threadLocal.get();
	}

	public static List<Problem> getProblems(Severity severity) {
		return _threadLocal.get();
	}

	public static boolean hasErrors() {
		return false;
	}

	private static final ThreadLocal<List<Problem>> _threadLocal =
		new CentralizedThreadLocal<>(
			ProblemsUtil.class.getName() + "._threadLocal", ArrayList::new);

}