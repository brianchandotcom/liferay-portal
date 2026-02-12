/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.vulcan.problem;

import com.liferay.portal.kernel.exception.LayoutTypeException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemMapper;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = ProblemMapper.class)
public class SitePageLayoutTypeExceptionProblemMapper
	implements ProblemMapper<LayoutTypeException> {

	@Override
	public Problem getProblem(LayoutTypeException layoutTypeException) {
		String message = _getLayoutTypeExceptionMessage(
			layoutTypeException.getMessage(), layoutTypeException.getType(),
			GetterUtil.getString(layoutTypeException.getLayoutType()),
			LocaleUtil.getMostRelevantLocale());

		return new Problem() {

			@Override
			public String getDetail(Locale locale) {
				return message;
			}

			@Override
			public Status getStatus() {
				return Status.CONFLICT;
			}

			@Override
			public String getTitle(Locale locale) {
				return message;
			}

			@Override
			public String getType() {
				return LayoutTypeException.class.getName();
			}

		};
	}

	private String _getLayoutTypeExceptionMessage(
		String defaultMessage, int exceptionType, String layoutType,
		Locale locale) {

		if (exceptionType == LayoutTypeException.FIRST_LAYOUT) {
			return _language.format(
				locale, "the-first-page-cannot-be-of-type-x",
				_language.get(locale, "layout.types." + layoutType));
		}

		if (exceptionType == LayoutTypeException.FIRST_LAYOUT_PERMISSION) {
			return _language.get(
				locale, "the-first-page-should-be-visible-for-guest-users");
		}

		if (exceptionType == LayoutTypeException.NOT_PARENTABLE) {
			return _language.get(
				locale,
				"a-page-cannot-become-a-child-of-a-page-that-is-not-" +
					"parentable");
		}

		if (exceptionType == LayoutTypeException.TYPE_NOT_ALLOWED) {
			return _language.format(
				locale, "an-empty-page-cannot-be-converted-to-x",
				_language.get(locale, "layout.types." + layoutType));
		}

		return defaultMessage;
	}

	@Reference
	private Language _language;

}