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

package com.liferay.search.experiences.internal.model.listener;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.search.experiences.internal.util.SXPElementDataUtil;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;
import com.liferay.search.experiences.service.SXPElementLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(enabled = true, immediate = true, service = ModelListener.class)
public class CompanyModelListener extends BaseModelListener<Company> {

	@Override
	public void onAfterCreate(Company company) {
		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				try {
					SXPElementDataUtil.addSXPElements(
						_sxpElementLocalService, company);
				}
				catch (PortalException portalException) {
					_log.error(portalException, portalException);
				}

				return null;
			});
	}

	@Override
	public void onAfterRemove(Company company) {
		try {
			_sxpBlueprintLocalService.deleteCompanySXPBlueprints(
				company.getCompanyId());

			_sxpElementLocalService.deleteCompanySXPElements(
				company.getCompanyId());
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyModelListener.class);

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

}