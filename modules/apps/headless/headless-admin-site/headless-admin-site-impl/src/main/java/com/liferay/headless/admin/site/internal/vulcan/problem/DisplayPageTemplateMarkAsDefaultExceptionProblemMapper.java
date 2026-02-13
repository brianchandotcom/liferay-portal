/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.vulcan.problem;

import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.exception.LayoutPageTemplateEntryDefaultTemplateException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemMapper;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bárbara Cabrera
 */
@Component(service = ProblemMapper.class)
public class DisplayPageTemplateMarkAsDefaultExceptionProblemMapper
	implements ProblemMapper<LayoutPageTemplateEntryDefaultTemplateException> {

	@Override
	public Problem getProblem(
		LayoutPageTemplateEntryDefaultTemplateException
			layoutPageTemplateEntryDefaultTemplateException) {

		String name = "display page template";

		if (Objects.equals(
				layoutPageTemplateEntryDefaultTemplateException.getType(),
				LayoutPageTemplateEntryTypeConstants.MASTER_LAYOUT)) {

			name = "master page";
		}

		return ProblemUtil.getProblem(
			StringUtil.replace(
				layoutPageTemplateEntryDefaultTemplateException.getMessage(),
				"layout page template entry", name),
			Problem.Status.CONFLICT,
			layoutPageTemplateEntryDefaultTemplateException);
	}

}