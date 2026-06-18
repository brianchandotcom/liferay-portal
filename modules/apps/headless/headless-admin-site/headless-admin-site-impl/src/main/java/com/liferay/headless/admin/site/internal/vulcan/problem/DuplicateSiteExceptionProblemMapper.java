/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.vulcan.problem;

import com.liferay.portal.kernel.exception.DuplicateGroupException;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jonathan McCann
 */
@Component(service = ProblemMapper.class)
public class DuplicateSiteExceptionProblemMapper
	implements ProblemMapper<DuplicateGroupException> {

	@Override
	public Problem getProblem(DuplicateGroupException duplicateGroupException) {
		return ProblemUtil.getProblem(
			"A site with the same key already exists", Problem.Status.CONFLICT,
			duplicateGroupException);
	}

}