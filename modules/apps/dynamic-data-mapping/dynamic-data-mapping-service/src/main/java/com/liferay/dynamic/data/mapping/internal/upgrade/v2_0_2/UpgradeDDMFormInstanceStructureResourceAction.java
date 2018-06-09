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

package com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_2;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;

/**
 * @author Lino Alves
 */
public class UpgradeDDMFormInstanceStructureResourceAction
	extends UpgradeProcess {

	public UpgradeDDMFormInstanceStructureResourceAction(
		ResourceActions resourceActions) {

		_resourceActions = resourceActions;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"delete from ResourcePermission where name = ?");
			PreparedStatement ps1 = connection.prepareStatement(
				"delete from ResourceAction where name = ?")) {

			String compositeModelName = _resourceActions.getCompositeModelName(
				DDMFormInstance.class.getName(), DDMStructure.class.getName());

			ps.setString(1, compositeModelName);

			ps.executeUpdate();

			ps1.setString(1, compositeModelName);

			ps1.executeUpdate();
		}
	}

	private final ResourceActions _resourceActions;

}