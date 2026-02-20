/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.util;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

/**
 * @author Lourdes Fernández Besada
 */
public class GroupUtil {

	public static long getGroupId(
			boolean allowCompanyGroup, boolean allowLiveGroup, long companyId,
			String siteExternalReferenceCode)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroupByExternalReferenceCode(
			siteExternalReferenceCode, companyId);

		if (ExportImportThreadLocal.isExportInProcess() ||
			ExportImportThreadLocal.isImportInProcess()) {

			return group.getGroupId();
		}

		if ((!allowCompanyGroup && group.isCompany()) || group.isDepot() ||
			(!allowLiveGroup && group.hasLocalOrRemoteStagingGroup())) {

			throw new UnsupportedOperationException();
		}

		return group.getGroupId();
	}

	public static long getGroupId(
			boolean allowLiveGroup, long companyId,
			String siteExternalReferenceCode)
		throws Exception {

		return getGroupId(
			false, allowLiveGroup, companyId, siteExternalReferenceCode);
	}

	public static long getStagingAwareGroupId(
			boolean allowCompanyGroup, long companyId,
			String siteExternalReferenceCode)
		throws Exception {

		return getGroupId(
			allowCompanyGroup,
			ExportImportThreadLocal.isInitialLayoutStagingInProcess() ||
			ExportImportThreadLocal.isStagingInProcess(),
			companyId, siteExternalReferenceCode);
	}

	public static long getStagingAwareGroupId(
			long companyId, String siteExternalReferenceCode)
		throws Exception {

		return getGroupId(
			false,
			ExportImportThreadLocal.isInitialLayoutStagingInProcess() ||
			ExportImportThreadLocal.isStagingInProcess(),
			companyId, siteExternalReferenceCode);
	}

}