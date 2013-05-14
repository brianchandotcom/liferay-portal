/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_0_0.util;

import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.util.FileUtil;

/**
 * @author Alexander Chow
 */
public class DLFileEntryNameUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public static String getNewName(String name) throws Exception {
		if (name.startsWith("DLFE-")) {
			name = name.substring(5);
		}

		name = FileUtil.stripExtension(name);

		return name;
	}

	public DLFileEntryNameUpgradeColumnImpl(String name) {
		super(name);
	}

	@Override
	public Object getNewValue(Object oldValue) throws Exception {
		String name = (String)oldValue;

		return getNewName(name);
	}

}