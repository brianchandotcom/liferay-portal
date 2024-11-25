/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemProvider;

import java.util.Locale;

/**
 * @author Alejandro Tardín
 */
public class ErrorMessageUtil {

	public static String getErrorMessage(Throwable throwable, long userId) {
		if (throwable == null) {
			return null;
		}

		ProblemProvider problemProvider =
			_problemProviderRegistrySnapshot.get();

		Problem problem = problemProvider.getProblem(throwable);

		if (problem != null) {
			return problem.getDetail(_getLocale(userId));
		}

		return throwable.toString();
	}

	private static Locale _getLocale(long userId) {
		try {
			User user = UserLocalServiceUtil.getUser(userId);

			return user.getLocale();
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return LocaleUtil.getDefault();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ErrorMessageUtil.class);

	private static final Snapshot<ProblemProvider>
		_problemProviderRegistrySnapshot = new Snapshot<>(
			ErrorMessageUtil.class, ProblemProvider.class);

}