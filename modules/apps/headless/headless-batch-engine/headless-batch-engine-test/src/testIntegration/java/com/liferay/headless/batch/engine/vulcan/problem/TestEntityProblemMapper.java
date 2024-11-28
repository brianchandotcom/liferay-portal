/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.batch.engine.vulcan.problem;

import com.liferay.headless.batch.engine.exception.TestEntityException;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemMapper;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alberto Javier Moreno Lage
 */
@Component(service = ProblemMapper.class)
public class TestEntityProblemMapper
	implements ProblemMapper<TestEntityException> {

	@Override
	public Problem getProblem(TestEntityException testEntityException) {
		return new Problem() {

			@Override
			public String getDetail(Locale locale) {
				return "Modified " + testEntityException.getMessage();
			}

			@Override
			public Status getStatus() {
				return Status.BAD_REQUEST;
			}

			@Override
			public String getTitle(Locale locale) {
				return null;
			}

			@Override
			public String getType() {
				return null;
			}

		};
	}

}