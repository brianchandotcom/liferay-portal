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

package com.liferay.commerce.product.type.virtual.internal.upgrade.v1_1_0;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.type.virtual.model.impl.CPDefinitionVirtualSettingImpl;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionVirtualSettingUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasColumn(
				CPDefinitionVirtualSettingImpl.TABLE_NAME, "classNameId")) {

			String template = StringUtil.read(
				CPDefinitionVirtualSettingUpgradeProcess.class.
					getResourceAsStream(
						"dependencies/CPDefinitionVirtualSetting.sql"));

			long classNameId = ClassNameLocalServiceUtil.getClassNameId(
				CPDefinition.class.getName());

			template = StringUtil.replace(
				template, "(?)", "\'" + classNameId + "\'");

			runSQLTemplateString(template, false);
		}
	}

	@Override
	protected UpgradeStep[] getPreUpgradeSteps() {
		return new UpgradeStep[] {
			UpgradeProcessFactory.addColumns(
				"CPDefinitionVirtualSetting", "classNameId LONG",
				"override BOOLEAN"),
			UpgradeProcessFactory.alterColumnName(
				"CPDefinitionVirtualSetting", "CPDefinitionId", "classPK LONG")
		};
	}

}