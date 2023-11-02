/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v5_4_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Michael Bowerman
 */
public class LayoutPageTemplateStructureRelUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			StringBundler.concat(
				"update LayoutPageTemplateStructureRel set data_ = ",
				"REPLACE(data_, '", _OLD_CLASS_NAME, "' , '", _NEW_CLASS_NAME,
				"') where data_ is not null and data_ != ''"));
	}

	private static final String _NEW_CLASS_NAME =
		"com.liferay.object.web.internal.info.collection.provider." +
			"ObjectEntrySingleFormVariationInfoCollectionProvider";

	private static final String _OLD_CLASS_NAME =
		"com.liferay.object.internal.info.collection.provider." +
			"ObjectEntrySingleFormVariationInfoCollectionProvider";

}