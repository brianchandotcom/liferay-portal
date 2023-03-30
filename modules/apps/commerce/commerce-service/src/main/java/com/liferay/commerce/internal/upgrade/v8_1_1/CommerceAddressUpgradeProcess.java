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