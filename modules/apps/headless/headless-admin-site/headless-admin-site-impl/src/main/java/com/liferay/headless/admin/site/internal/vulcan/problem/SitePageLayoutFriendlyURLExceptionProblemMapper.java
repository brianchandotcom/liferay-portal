/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.vulcan.problem;

import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier Moral
 */
@Component(service = ProblemMapper.class)
public class SitePageLayoutFriendlyURLExceptionProblemMapper
	implements ProblemMapper<LayoutFriendlyURLException> {

	@Override
	public Problem getProblem(
		LayoutFriendlyURLException layoutFriendlyURLException) {

		int type = layoutFriendlyURLException.getType();

		if (type == LayoutFriendlyURLException.DUPLICATE) {
			return ProblemUtil.getProblem(
				"The friendly URL is already in use", Problem.Status.CONFLICT,
				layoutFriendlyURLException);
		}

		if (type == LayoutFriendlyURLException.KEYWORD_CONFLICT) {
			return ProblemUtil.getProblem(
				"The friendly URL conflicts with the reserved keyword \"" +
					layoutFriendlyURLException.getKeywordConflict() + "\"",
				Problem.Status.CONFLICT, layoutFriendlyURLException);
		}

		if (type == LayoutFriendlyURLException.POSSIBLE_DUPLICATE) {
			return ProblemUtil.getProblem(
				"The friendly URL conflicts with the page ID \"" +
					layoutFriendlyURLException.getKeywordConflict() + "\"",
				Problem.Status.CONFLICT, layoutFriendlyURLException);
		}

		return ProblemUtil.getProblem(
			_getMessage(type), Problem.Status.BAD_REQUEST,
			layoutFriendlyURLException);
	}

	private String _getMessage(int type) {
		if (type == LayoutFriendlyURLException.ADJACENT_SLASHES) {
			return "The friendly URL must not contain adjacent slashes";
		}

		if (type == LayoutFriendlyURLException.DOES_NOT_START_WITH_SLASH) {
			return "The friendly URL must begin with a slash";
		}

		if (type == LayoutFriendlyURLException.ENDS_WITH_DASH) {
			return "The friendly URL must not end with a dash";
		}

		if (type == LayoutFriendlyURLException.ENDS_WITH_SLASH) {
			return "The friendly URL must not end with a slash";
		}

		if (type == LayoutFriendlyURLException.INVALID_CHARACTERS) {
			return "The friendly URL contains an invalid character";
		}

		if (type == LayoutFriendlyURLException.TOO_DEEP) {
			return "The friendly URL has too many path segments";
		}

		if (type == LayoutFriendlyURLException.TOO_LONG) {
			return "The friendly URL is too long";
		}

		if (type == LayoutFriendlyURLException.TOO_SHORT) {
			return "The friendly URL is too short";
		}

		return "The friendly URL is invalid";
	}

}