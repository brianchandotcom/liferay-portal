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

package com.liferay.staging.security.spring;

import com.liferay.portal.service.UserGroupLocalService;
import com.liferay.portlet.exportimport.staging.Staging;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true)
public class UserGroupLocalServiceStagingAdvice extends LiveGroupStagingAdvice {

	public UserGroupLocalServiceStagingAdvice() throws NoSuchMethodException {
		super(UserGroupLocalService.class);

		initGroupServiceBuilderMethods();

		initCustomMethod("getGroupUserUserGroups", 0, long.class, long.class);

		checkCoverage(_GROUP_METHODS_WHITELIST);
	}

	@Reference
	protected void setService(UserGroupLocalService service) {
		registerAdvice(service);
	}

	@Reference(unbind = "-")
	protected void setStaging(Staging staging) {
	}

	protected void unsetService(UserGroupLocalService service) {
		unregisterAdvice(service);
	}

	private static final List<String> _GROUP_METHODS_WHITELIST = Arrays.asList(
		new String[] {"getGroupPrimaryKeys"});

}