/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.upgrade.v8_1_1;

import com.liferay.account.model.AccountEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Brian I. Kim
 */
public class CommerceAddressUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		long commerceAccountEntryClassNameId = PortalUtil.getClassNameId(
			"com.liferay.commerce.account.model.CommerceAccount");
		long accountEntryClassNameId = PortalUtil.getClassNameId(
			AccountEntry.class.getName());

		runSQL(
			StringBundler.concat(
				"update Address set classNameId = ", accountEntryClassNameId,
				" where classNameId = ", commerceAccountEntryClassNameId));
	}

}