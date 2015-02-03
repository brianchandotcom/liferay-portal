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

package com.liferay.portal.upgrade.util.classname.dependency;

import com.liferay.portal.upgrade.util.Table;
import com.liferay.portal.upgrade.util.classname.ClassNameDependency;

/**
 * @author Miguel Pastor
 */
public class ResourceBlockDependency implements ClassNameDependency {

	@Override
	public void update(String oldValue, String newValue) {
		updateResourceAction(oldValue, newValue);
		updateResourcePermission(oldValue, newValue);
	}

	protected void updateResourceAction(String oldName, String newName) {
		Table table = new Table("ResourceAction");

		table.updateColumnValue("name", oldName, newName);
	}

	protected void updateResourcePermission(String oldName, String newName) {
		Table table = new Table("ResourceBlock");

		table.updateColumnValue("name", oldName, newName);
	}

}