/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.vulcan.problem;

import com.liferay.portal.vulcan.problem.Problem;

import java.util.Locale;

/**
 * @author Lourdes Fernández Besada
 */
public class ProblemUtil {

	public static <T extends Throwable> Problem getProblem(
		Problem.Status status, T throwable) {

		return getProblem(throwable.getMessage(), status, throwable);
	}

	public static <T extends Throwable> Problem getProblem(
		String message, Problem.Status status, T throwable) {

		return new Problem() {

			@Override
			public String getDetail(Locale locale) {
				return message;
			}

			@Override
			public Status getStatus() {
				return status;
			}

			@Override
			public String getTitle(Locale locale) {
				return message;
			}

			@Override
			public String getType() {
				Class<? extends Throwable> throwableClass =
					throwable.getClass();

				return throwableClass.getName();
			}

		};
	}

	public static <T extends Throwable> Problem getProblem(T throwable) {
		return getProblem(Problem.Status.BAD_REQUEST, throwable);
	}

}