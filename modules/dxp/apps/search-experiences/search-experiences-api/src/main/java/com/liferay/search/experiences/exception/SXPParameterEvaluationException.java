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

package com.liferay.search.experiences.exception;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.search.experiences.problem.Problem;

/**
 * @author Petteri Karttunen
 */
public class SXPParameterEvaluationException extends PortalException {

	public SXPParameterEvaluationException() {
	}

	public SXPParameterEvaluationException(Problem problem) {
		_problem = problem;
	}

	public SXPParameterEvaluationException(String msg) {
		super(msg);
	}

	public SXPParameterEvaluationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public SXPParameterEvaluationException(Throwable throwable) {
		super(throwable);
	}

	public Problem getProblem() {
		return _problem;
	}

	private static final long serialVersionUID = 1L;

	private Problem _problem;

}