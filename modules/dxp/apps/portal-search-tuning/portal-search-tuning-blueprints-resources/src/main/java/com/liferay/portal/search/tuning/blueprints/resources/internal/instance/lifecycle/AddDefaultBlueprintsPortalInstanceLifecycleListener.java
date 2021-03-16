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

package com.liferay.portal.search.tuning.blueprints.resources.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintTypes;
import com.liferay.portal.search.tuning.blueprints.resources.internal.util.ImportHelper;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	enabled = true, immediate = true,
	service = PortalInstanceLifecycleListener.class
)
public class AddDefaultBlueprintsPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (_hasBlueprints(company.getCompanyId())) {
			return;
		}

		try {
			User user = company.getDefaultUser();

			_importHelper.importDefaultBlueprints(
				company.getCompanyId(), company.getGroupId(), user.getUserId());
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);
		}
	}

	private boolean _hasBlueprints(long companyId) {
		int count = _blueprintLocalService.getCompanyBlueprintsCount(
			companyId, BlueprintTypes.BLUEPRINT);

		if (count > 0) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddDefaultBlueprintsPortalInstanceLifecycleListener.class);

	@Reference
	private BlueprintLocalService _blueprintLocalService;

	@Reference
	private ImportHelper _importHelper;

}