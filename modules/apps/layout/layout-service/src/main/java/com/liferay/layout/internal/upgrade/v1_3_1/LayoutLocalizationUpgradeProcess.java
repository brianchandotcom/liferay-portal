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

package com.liferay.layout.internal.upgrade.v1_3_1;

import com.liferay.layout.model.LayoutLocalization;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author David Truong
 */
public class LayoutLocalizationUpgradeProcess extends UpgradeProcess {

	public LayoutLocalizationUpgradeProcess(Portal portal) {
		_portal = portal;
	}

	@Override
	protected void doUpgrade() throws Exception {
		runSQL(
			"delete from LayoutLocalization where plid not in (select plid " +
				"from Layout)");

		if (hasTable("CTEntry")) {
			runSQL(
				StringBundler.concat(
					"delete CTEntry from CTEntry where modelClassNameId = ",
					_portal.getClassNameId(LayoutLocalization.class.getName()),
					" and modelClassPk not in (select layoutLocalizationId ",
					"from LayoutLocalization)"));
		}
	}

	private final Portal _portal;

}