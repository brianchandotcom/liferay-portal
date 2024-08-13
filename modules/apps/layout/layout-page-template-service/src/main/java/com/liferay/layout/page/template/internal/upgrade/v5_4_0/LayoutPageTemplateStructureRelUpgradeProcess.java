/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v5_4_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutPageTemplateStructureRelUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"update LayoutPageTemplateStructureRel set data_ = ",
				"REPLACE(data_, 'com.liferay.object.internal.info.collection.",
				"provider.",
				"ObjectEntrySingleFormVariationInfoCollectionProvider', '",
				"com.liferay.object.web.internal.info.collection.provider.",
				"ObjectEntrySingleFormVariationInfoCollectionProvider') where ",
				"data_ is not null and data_ != ''"));
	}

}