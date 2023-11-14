/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.internal.upgrade.v1_3_1;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Christopher Kian
 */
public class ListTypeUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"update Contact_ set prefixListTypeId = 0 where ",
				"prefixListTypeId in (select listTypeId from ListType where ",
				"(name IS NULL or name = '') and (type_ = '",
				ListTypeConstants.CONTACT_PREFIX, "'))"));

		runSQL(
			StringBundler.concat(
				"update Contact_ set suffixListTypeId = 0 where ",
				"suffixListTypeId in (select listTypeId from ListType where ",
				"(name IS NULL or name = '') and (type_ = '",
				ListTypeConstants.CONTACT_SUFFIX, "'))"));

		runSQL(
			StringBundler.concat(
				"delete from ListType where (name IS NULL or name = '') and ",
				"(type_ = '", ListTypeConstants.CONTACT_PREFIX, "' or type_ = ",
				"'", ListTypeConstants.CONTACT_SUFFIX, "')"));
	}

}