/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.vulcan.problem;

import com.liferay.layout.page.template.exception.LayoutPageTemplateEntryNameException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.vulcan.problem.Problem;
import com.liferay.portal.vulcan.problem.ProblemMapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Moral
 */
@Component(service = ProblemMapper.class)
public class DuplicateLayoutPageTemplateEntryNameExceptionProblemMapper
	implements ProblemMapper
		<LayoutPageTemplateEntryNameException.MustNotBeDuplicate> {

	@Override
	public Problem getProblem(
		LayoutPageTemplateEntryNameException.MustNotBeDuplicate
			mustNotBeDuplicate) {

		return ProblemUtil.getProblem(
			_language.get(
				LocaleUtil.getMostRelevantLocale(),
				"a-page-template-entry-with-that-name-already-exists"),
			Problem.Status.CONFLICT, mustNotBeDuplicate);
	}

	@Reference
	private Language _language;

}