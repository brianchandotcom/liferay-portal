/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.feature.flag;

import com.liferay.change.tracking.internal.helper.CTConflictCheckerDispatchTriggerHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "featureFlagKey=LPD-11018", service = FeatureFlagListener.class
)
public class CTFeatureFlagListener implements FeatureFlagListener {

	@Override
	public void onValue(
		long companyId, String featureFlagKey, boolean enabled) {

		try {
			if (enabled) {
				_ctConflictCheckerDispatchTriggerHelper.addDispatchTrigger(
					_companyLocalService.getCompany(companyId));
			}
			else {
				_ctConflictCheckerDispatchTriggerHelper.deleteDispatchTrigger(
					companyId);
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTFeatureFlagListener.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CTConflictCheckerDispatchTriggerHelper
		_ctConflictCheckerDispatchTriggerHelper;

}