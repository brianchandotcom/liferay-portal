/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.util;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemProvider;

/**
 * @author Alejandro Tardín
 */
public class ErrorMessageUtil {

	public static String getErrorMessage(Throwable throwable) {
		if (throwable == null) {
			return null;
		}

		ProblemProvider problemProvider =
			_problemProviderRegistrySnapshot.get();

		Problem problem = problemProvider.getProblem(throwable);

		if (problem != null) {
			return problem.getDetail(LocaleUtil.getDefault());
		}

		return throwable.toString();
	}

	private static final Snapshot<ProblemProvider>
		_problemProviderRegistrySnapshot = new Snapshot<>(
			ErrorMessageUtil.class, ProblemProvider.class);

}