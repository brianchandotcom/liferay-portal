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

package com.liferay.asset.service.test;

import com.liferay.portlet.usersadmin.util.OrganizationIndexer;

import org.junit.Assert;

/**
 * @author Michael C. Han
 */
public class TestAssetIndexer extends OrganizationIndexer {

	@Override
	public void reindex(String className, long classPK) {
		Assert.assertEquals(_className, className);
		Assert.assertEquals(_classPK, classPK);
	}

	public void setExpectedValues(String className, long classPK) {
		_className = className;
		_classPK = classPK;
	}

	private String _className;
	private long _classPK;

}