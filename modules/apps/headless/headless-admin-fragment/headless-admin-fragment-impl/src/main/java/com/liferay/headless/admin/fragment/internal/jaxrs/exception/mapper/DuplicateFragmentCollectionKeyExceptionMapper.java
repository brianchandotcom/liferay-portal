/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.fragment.internal.jaxrs.exception.mapper;

import com.liferay.fragment.exception.DuplicateFragmentCollectionKeyException;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemMapper;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rubén Pulido
 */
@Component(service = ProblemMapper.class)
public class DuplicateFragmentCollectionKeyExceptionMapper
	implements ProblemMapper<DuplicateFragmentCollectionKeyException> {

	@Override
	public Problem getProblem(
		DuplicateFragmentCollectionKeyException
			duplicateFragmentCollectionKeyException) {

		String message = "A fragment set with the same key already exists";

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
				return DuplicateFragmentCollectionKeyException.class.getName();
			}

		};
	}

}