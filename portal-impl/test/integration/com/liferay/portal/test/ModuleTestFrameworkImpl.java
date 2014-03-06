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

package com.liferay.portal.test;

import com.liferay.osgi.bootstrap.ModuleFrameworkImpl;
import com.liferay.portal.kernel.util.ServiceLoaderCondition;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Manuel de la Peña
 */
public class ModuleTestFrameworkImpl extends ModuleFrameworkImpl {

	public ModuleTestFrameworkImpl(
		ServiceLoaderCondition serviceLoaderCondition) {

		super(serviceLoaderCondition);
	}

	@Override
	protected String getSystemPackagesExtra() {
		return StringPool.BLANK;
	}

}