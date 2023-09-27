/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.internal.upgrade.v1_1_0;

import com.liferay.expando.kernel.exception.NoSuchTableException;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoTableLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.subscription.model.Subscription;

/**
 * @author Peter Shin
 */
public class ExpandoTableUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_updateExpandoTable(PortalUtil.getDefaultCompanyId());
	}

	private void _updateExpandoTable(long companyId) throws Exception {
		ExpandoTable expandoTable = null;

		try {
			expandoTable = ExpandoTableLocalServiceUtil.getTable(
				companyId, Subscription.class.getName(), "KB");
		}
		catch (NoSuchTableException noSuchTableException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(noSuchTableException);
			}

			return;
		}

		ExpandoTableLocalServiceUtil.deleteExpandoTable(expandoTable);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExpandoTableUpgradeProcess.class);

}