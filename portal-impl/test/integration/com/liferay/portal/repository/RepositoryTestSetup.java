/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.repository;

import com.liferay.portal.service.ServiceTestUtil;

import junit.extensions.TestSetup;

import junit.framework.Test;

/**
 * @author Alexander Chow
 */
public class RepositoryTestSetup extends TestSetup {

	public RepositoryTestSetup(Test test) {
		super(test);
	}

	@Override
	public void setUp() {
		ServiceTestUtil.initServices();
		ServiceTestUtil.initPermissions();
	}

	@Override
	public void tearDown() {
		ServiceTestUtil.destroyServices();
	}

}