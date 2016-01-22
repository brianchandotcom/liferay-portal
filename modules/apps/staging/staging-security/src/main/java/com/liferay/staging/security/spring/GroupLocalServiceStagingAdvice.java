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

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portlet.exportimport.staging.Staging;

import java.lang.reflect.Method;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(immediate = true)
public class GroupLocalServiceStagingAdvice extends LiveGroupStagingAdvice {

	public GroupLocalServiceStagingAdvice() throws NoSuchMethodException {
		super(GroupLocalService.class);

		initRelatedServiceBuilderMethods("Organization");
		initRelatedServiceBuilderMethods("Role");
		initRelatedServiceBuilderMethods("User");
		initRelatedServiceBuilderMethods("UserGroup");

		initCustomMethod("getUserGroupPrimaryKeys", 0, long.class);
	}

	protected void initRelatedServiceBuilderMethods(String relatedEntityName)
		throws NoSuchMethodException {

		Method[] relatedEntityMethods = GroupLocalService.class.getMethods();

		for (String template : _SERVICE_BUILDER_GENERATED_TEMPLATES) {
			String serviceBuilderMethodName = StringUtil.replace(
				template, "$1", relatedEntityName);

			for (Method method : relatedEntityMethods) {
				if (method.getName().equals(serviceBuilderMethodName)) {
					initCustomMethod(
						serviceBuilderMethodName, 1,
						method.getParameterTypes());
				}
			}
		}
	}

	@Reference
	protected void setService(GroupLocalService service) {
		registerAdvice(service);
	}

	@Reference(unbind = "-")
	protected void setStaging(Staging staging) {
	}

	protected void unsetService(GroupLocalService service) {
		unregisterAdvice(service);
	}

	private static final String[] _SERVICE_BUILDER_GENERATED_TEMPLATES =
		new String[] {
			"add$1Group", "add$1Groups", "delete$1Group", "delete$1Groups",
			"has$1Group", "set$1Groups", "unset$1Groups"
		};

}