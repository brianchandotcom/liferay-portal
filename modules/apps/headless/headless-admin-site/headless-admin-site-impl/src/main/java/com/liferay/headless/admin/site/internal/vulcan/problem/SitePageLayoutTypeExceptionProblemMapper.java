/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.vulcan.problem;

import com.liferay.headless.admin.site.dto.v1_0.SitePage;
import com.liferay.headless.admin.site.internal.dto.v1_0.util.SitePageTypeUtil;
import com.liferay.portal.kernel.exception.LayoutTypeException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemMapper;

import org.osgi.service.component.annotations.Component;

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
			return "The first site page cannot be of type \"" +
				_getSitePageType(layoutTypeException) + "\"";
		}

		if (type == LayoutTypeException.FIRST_LAYOUT_PERMISSION) {
			return "The first site page must be visible to guest users";
		}

		if (type == LayoutTypeException.NOT_PARENTABLE) {
			return "A site page cannot become a child of a site page that is " +
				"not parentable";
		}

		if (type == LayoutTypeException.TYPE_NOT_ALLOWED) {
			return "An empty site page cannot be converted to type \"" +
				_getSitePageType(layoutTypeException) + "\"";
		}

		return layoutTypeException.getMessage();
	}

	private String _getSitePageType(LayoutTypeException layoutTypeException) {
		String layoutType = GetterUtil.getString(
			layoutTypeException.getLayoutType());

		SitePage.Type sitePageType = SitePageTypeUtil.fetchExternalType(
			layoutType);

		if (sitePageType == null) {
			return layoutType;
		}

		return sitePageType.getValue();
	}

}