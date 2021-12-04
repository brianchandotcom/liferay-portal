/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.upgrade.v1_0_0;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.search.experiences.internal.util.SXPElementDataUtil;
import com.liferay.search.experiences.service.SXPElementLocalService;

/**
 * @author Shuyang Zhou
 */
public class SXPElementUpgradeProcess extends UpgradeProcess {

	public SXPElementUpgradeProcess(
		CompanyLocalService companyLocalService,
		SXPElementLocalService sxpElementLocalService) {

		_companyLocalService = companyLocalService;
		_sxpElementLocalService = sxpElementLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> SXPElementDataUtil.addSXPElements(
				_sxpElementLocalService, company));
	}

	private final CompanyLocalService _companyLocalService;
	private final SXPElementLocalService _sxpElementLocalService;

}