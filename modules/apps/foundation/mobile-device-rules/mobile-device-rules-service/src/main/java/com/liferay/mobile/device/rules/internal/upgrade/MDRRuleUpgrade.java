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

package com.liferay.mobile.device.rules.internal.upgrade;

import com.liferay.mobile.device.rules.rule.group.action.LayoutTemplateModificationActionHandler;
import com.liferay.mobile.device.rules.rule.group.action.SimpleRedirectActionHandler;
import com.liferay.mobile.device.rules.rule.group.action.SiteRedirectActionHandler;
import com.liferay.mobile.device.rules.rule.group.action.ThemeModificationActionHandler;
import com.liferay.mobile.device.rules.rule.group.rule.SimpleRuleHandler;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Tom Wang
 */
public class MDRRuleUpgrade extends UpgradeProcess {

	@Override
	public void doUpgrade() throws Exception {
		runSQL(
			"update MDRRule set type_ = '" + SimpleRuleHandler.class.getName() +
				"' where type_ = 'com.liferay.portal.mobile.device.rulegroup." +
					"rule.impl.SimpleRuleHandler'");

		_updateMDRAction(
			LayoutTemplateModificationActionHandler.class.getSimpleName(),
			LayoutTemplateModificationActionHandler.class.getName());
		_updateMDRAction(
			SimpleRedirectActionHandler.class.getSimpleName(),
			SimpleRedirectActionHandler.class.getName());
		_updateMDRAction(
			SiteRedirectActionHandler.class.getSimpleName(),
			SiteRedirectActionHandler.class.getName());
		_updateMDRAction(
			ThemeModificationActionHandler.class.getSimpleName(),
			ThemeModificationActionHandler.class.getName());
	}

	private void _updateMDRAction(String originalSimpleName, String newName)
		throws Exception {

		runSQL(
			"update MDRAction set type_ = '" + newName +
				"' where type_ = 'com.liferay.portal.mobile.device.rulegroup." +
					"action.impl." + originalSimpleName + "'");
	}

}