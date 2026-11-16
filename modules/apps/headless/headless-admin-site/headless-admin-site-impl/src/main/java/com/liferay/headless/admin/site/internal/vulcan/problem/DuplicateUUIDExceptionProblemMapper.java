/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.vulcan.problem;

import com.liferay.headless.admin.site.internal.exception.DuplicateUUIDException;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier Moral
 */
@Component(service = ProblemMapper.class)
public class DuplicateUUIDExceptionProblemMapper
	implements ProblemMapper<DuplicateUUIDException> {

	@Override
	public Problem getProblem(DuplicateUUIDException duplicateUUIDException) {
		return ProblemUtil.getProblem(
			StringBundler.concat(
				"A ", duplicateUUIDException.entityName, " with UUID \"",
				duplicateUUIDException.uuid, "\" already exists"),
			Problem.Status.CONFLICT, duplicateUUIDException);
	}

}