/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.vulcan.problem;

import com.liferay.headless.admin.site.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.SitePageTypeUtil;
import com.liferay.portal.kernel.exception.LayoutTypeException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemMapper;

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
		return ProblemUtil.getProblem(
			_getMessage(layoutTypeException), Problem.Status.CONFLICT,
			layoutTypeException);
	}

	private String _getMessage(LayoutTypeException layoutTypeException) {
		int type = layoutTypeException.getType();

		if (type == LayoutTypeException.FIRST_LAYOUT) {
			String pageType = _getPageType(layoutTypeException);

			if (Validator.isNull(pageType)) {
				return "The first page cannot be of this type";
			}

			return _language.format(
				LocaleUtil.US, "the-first-page-cannot-be-of-type-x", pageType,
				false);
		}

		if (type == LayoutTypeException.FIRST_LAYOUT_PERMISSION) {
			return _language.get(
				LocaleUtil.US,
				"the-first-page-should-be-visible-for-guest-users");
		}

		if (type == LayoutTypeException.NOT_PARENTABLE) {
			return _language.get(
				LocaleUtil.US,
				"a-page-cannot-become-a-child-of-a-page-that-is-not-" +
					"parentable");
		}

		if (type == LayoutTypeException.TYPE_NOT_ALLOWED) {
			String pageType = _getPageType(layoutTypeException);

			if (Validator.isNull(pageType)) {
				return "An empty page cannot be converted to this type";
			}

			return _language.format(
				LocaleUtil.US, "an-empty-page-cannot-be-converted-to-x",
				pageType, false);
		}

		return layoutTypeException.getMessage();
	}

	private String _getPageType(LayoutTypeException layoutTypeException) {
		String layoutType = GetterUtil.getString(
			layoutTypeException.getLayoutType());

		SitePage.Type externalType = SitePageTypeUtil.fetchExternalType(
			layoutType);

		if (externalType == null) {
			return layoutType;
		}

		return externalType.getValue();
	}

	@Reference
	private Language _language;

}