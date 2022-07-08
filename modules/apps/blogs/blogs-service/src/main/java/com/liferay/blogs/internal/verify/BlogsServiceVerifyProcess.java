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

package com.liferay.blogs.internal.verify;

import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.verify.VerifyProcess;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = "verify.process.name=com.liferay.blogs.service",
	service = VerifyProcess.class
)
public class BlogsServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		updateStagedPortletNames();
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	protected void updateStagedPortletNames() throws PortalException {
		ActionableDynamicQuery groupActionableDynamicQuery =
			_groupLocalService.getActionableDynamicQuery();

		groupActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property siteProperty = PropertyFactoryUtil.forName("site");

				dynamicQuery.add(siteProperty.eq(Boolean.TRUE));
			});
		groupActionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<Group>)group -> {
				UnicodeProperties typeSettingsProperties =
					group.getTypeSettingsProperties();

				if (typeSettingsProperties == null) {
					return;
				}

				String propertyKey = _staging.getStagedPortletId(
					BlogsPortletKeys.BLOGS);

				String propertyValue = typeSettingsProperties.getProperty(
					propertyKey);

				if (Validator.isNull(propertyValue)) {
					return;
				}

				typeSettingsProperties.remove(propertyKey);

				propertyKey = _staging.getStagedPortletId(
					BlogsPortletKeys.BLOGS_ADMIN);

				typeSettingsProperties.put(propertyKey, propertyValue);

				group.setTypeSettingsProperties(typeSettingsProperties);

				_groupLocalService.updateGroup(group);
			});

		groupActionableDynamicQuery.performActions();
	}

	private GroupLocalService _groupLocalService;

	@Reference
	private Staging _staging;

}