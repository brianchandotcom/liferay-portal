/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.vulcan.problem;

import com.liferay.portal.kernel.exception.RequiredGroupException;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jonathan McCann
 */
@Component(service = ProblemMapper.class)
public class RequiredSiteExceptionProblemMapper
	implements ProblemMapper
		<RequiredGroupException.MustNotDeactivateSystemGroup> {

	@Override
	public Problem getProblem(
		RequiredGroupException.MustNotDeactivateSystemGroup
			mustNotDeactivateSystemGroup) {

		return ProblemUtil.getProblem(
			Problem.Status.METHOD_NOT_ALLOWED, mustNotDeactivateSystemGroup);
	}

}