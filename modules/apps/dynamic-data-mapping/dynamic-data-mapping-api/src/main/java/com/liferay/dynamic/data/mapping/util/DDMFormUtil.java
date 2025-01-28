/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.constants.DDMFormConstants;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Calendar;

/**
 * @author Carolina Barbosa
 */
public class DDMFormUtil {

	public static User getDDMFormDefaultUser(long companyId) {
		try {
			return UserLocalServiceUtil.getUserByExternalReferenceCode(
				DDMFormConstants.DDM_FORM_DEFAULT_USER_EXTERNAL_REFERENCE_CODE,
				companyId);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return _createDDMFormDefaultUser(companyId);
		}
	}

	private static User _createDDMFormDefaultUser(long companyId) {
		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			Company company = CompanyLocalServiceUtil.getCompany(companyId);

			User user = UserLocalServiceUtil.addOrUpdateUser(
				DDMFormConstants.DDM_FORM_DEFAULT_USER_EXTERNAL_REFERENCE_CODE,
				0, companyId, true, StringPool.BLANK, StringPool.BLANK, false,
				DDMFormConstants.DDM_FORM_DEFAULT_USER_SCREEN_NAME,
				StringBundler.concat(
					DDMFormConstants.DDM_FORM_DEFAULT_USER_SCREEN_NAME,
					StringPool.AT, company.getMx()),
				LocaleUtil.getDefault(),
				DDMFormConstants.DDM_FORM_DEFAULT_USER_FIRST_NAME,
				StringPool.BLANK,
				DDMFormConstants.DDM_FORM_DEFAULT_USER_LAST_NAME, 0, 0, true,
				Calendar.JANUARY, 1, 1970, StringPool.BLANK, false, null);

			return UserLocalServiceUtil.updateStatus(
				user, WorkflowConstants.STATUS_INACTIVE, new ServiceContext());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(DDMFormUtil.class);

}