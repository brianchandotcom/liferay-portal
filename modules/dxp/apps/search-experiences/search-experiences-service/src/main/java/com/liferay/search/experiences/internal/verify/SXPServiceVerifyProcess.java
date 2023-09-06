/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.verify;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.search.experiences.internal.util.SXPElementUtil;
import com.liferay.search.experiences.service.SXPElementLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	enabled = false, property = "initial.deployment=true",
	service = VerifyProcess.class
)
public class SXPServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		_companyLocalService.forEachCompany(
			company -> SXPElementUtil.addSXPElements(
				company, _sxpElementLocalService));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

}