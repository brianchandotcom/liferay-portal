/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.upgrade.v2_10_3;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Lourdes Fernández Besada
 */
public class FragmentEntryLinkUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"update FragmentEntryLink set originalFragmentEntryLinkId = 0 ",
				"where originalFragmentEntryLinkId > 0 and plid in (select ",
				"plid from Layout where classPK > 0)"));
	}

}