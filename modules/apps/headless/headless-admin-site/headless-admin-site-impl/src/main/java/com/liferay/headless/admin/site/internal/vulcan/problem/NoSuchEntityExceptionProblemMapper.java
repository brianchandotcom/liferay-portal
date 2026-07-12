/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.vulcan.problem;

import com.liferay.headless.admin.site.internal.exception.NoSuchEntityException;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemMapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier Moral
 */
@Component(service = ProblemMapper.class)
public class NoSuchEntityExceptionProblemMapper
	implements ProblemMapper<NoSuchEntityException> {

	@Override
	public Problem getProblem(NoSuchEntityException noSuchEntityException) {
		String entity = noSuchEntityException.getEntity();
		String externalReferenceCode =
			noSuchEntityException.getExternalReferenceCode();
		String parentEntity = noSuchEntityException.getParentEntity();

		String message = "The requested " + entity + " could not be found";

		if (Validator.isNotNull(externalReferenceCode)) {
			if (Validator.isNull(parentEntity)) {
				message = StringBundler.concat(
					"No ", entity,
					" exists with the external reference code \"",
					externalReferenceCode, "\"");
			}
			else {
				message = StringBundler.concat(
					"No ", entity, " with external reference code \"",
					externalReferenceCode, "\" exists in this ", parentEntity);
			}
		}

		return ProblemUtil.getProblem(
			message, Problem.Status.NOT_FOUND, noSuchEntityException);
	}

}