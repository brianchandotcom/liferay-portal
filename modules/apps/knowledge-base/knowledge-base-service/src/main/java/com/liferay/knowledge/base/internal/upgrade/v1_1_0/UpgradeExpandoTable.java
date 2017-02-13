/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
public class UpgradeExpandoTable extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateExpandoTable(PortalUtil.getDefaultCompanyId());
	}

	protected void updateExpandoTable(long companyId) throws Exception {
		ExpandoTable expandoTable = null;

		try {
			expandoTable = ExpandoTableLocalServiceUtil.getTable(
				companyId, Subscription.class.getName(), "KB");
		}
		catch (NoSuchTableException nste) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nste, nste);
			}

			return;
		}

		ExpandoTableLocalServiceUtil.deleteExpandoTable(expandoTable);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeExpandoTable.class);

}