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

package com.liferay.mobile.device.rules.web.internal.upgrade;

import com.liferay.mobile.device.rules.web.internal.rule.group.action.LayoutTemplateModificationActionHandler;
import com.liferay.mobile.device.rules.web.internal.rule.group.action.SimpleRedirectActionHandler;
import com.liferay.mobile.device.rules.web.internal.rule.group.action.SiteRedirectActionHandler;
import com.liferay.mobile.device.rules.web.internal.rule.group.action.ThemeModificationActionHandler;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Tom Wang
 */
public class MDRActionUpgrade extends UpgradeProcess {

	public MDRActionUpgrade(String oldPackageName, String newPackageName) {
		_oldPackageName = oldPackageName;
		_newPackageName = newPackageName;
	}

	@Override
	public void doUpgrade() throws Exception {
		_updateMDRAction(
			LayoutTemplateModificationActionHandler.class.getSimpleName());
		_updateMDRAction(SimpleRedirectActionHandler.class.getSimpleName());
		_updateMDRAction(SiteRedirectActionHandler.class.getSimpleName());
		_updateMDRAction(ThemeModificationActionHandler.class.getSimpleName());
	}

	private void _updateMDRAction(String name) throws Exception {
		runSQL(
			StringBundler.concat(
				"update MDRAction set type_ = '", _newPackageName, ".", name,
				"' where type_ = '", _oldPackageName, ".", name, "'"));
	}

	private final String _newPackageName;
	private final String _oldPackageName;

}