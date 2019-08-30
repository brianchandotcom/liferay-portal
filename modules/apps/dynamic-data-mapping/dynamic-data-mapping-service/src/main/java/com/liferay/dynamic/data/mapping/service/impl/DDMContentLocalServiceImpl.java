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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.exception.ContentException;
import com.liferay.dynamic.data.mapping.exception.ContentNameException;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.service.base.DDMContentLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMContent",
	service = AopService.class
)
public class DDMContentLocalServiceImpl extends DDMContentLocalServiceBaseImpl {

	@Override
	public DDMContent addContent(
			long userId, long groupId, String name, String description,
			String data, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(0, name, data);

		long contentId = counterLocalService.increment();

		DDMContent content = ddmContentPersistence.create(contentId);

		content.setUuid(serviceContext.getUuid());
		content.setGroupId(serviceContext.getScopeGroupId());
		content.setCompanyId(user.getCompanyId());
		content.setUserId(user.getUserId());
		content.setUserName(user.getFullName());
		content.setName(name);
		content.setDescription(description);
		content.setData(data);

		ddmContentPersistence.update(content);

		return content;
	}

	@Override
	public void deleteContent(DDMContent content) {
		ddmContentPersistence.remove(content);
	}

	@Override
	public void deleteContents(long groupId) {
		List<DDMContent> contents = ddmContentPersistence.findByGroupId(
			groupId);

		for (DDMContent content : contents) {
			deleteContent(content);
		}
	}

	@Override
	public DDMContent getContent(long contentId) throws PortalException {
		return ddmContentPersistence.findByPrimaryKey(contentId);
	}

	@Override
	public List<DDMContent> getContents() {
		return ddmContentPersistence.findAll();
	}

	@Override
	public List<DDMContent> getContents(long groupId) {
		return ddmContentPersistence.findByGroupId(groupId);
	}

	@Override
	public List<DDMContent> getContents(long groupId, int start, int end) {
		return ddmContentPersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public int getContentsCount(long groupId) {
		return ddmContentPersistence.countByGroupId(groupId);
	}

	@Override
	public DDMContent updateContent(
			long contentId, String name, String description, String data,
			ServiceContext serviceContext)
		throws PortalException {

		validate(contentId, name, data);

		DDMContent content = ddmContentPersistence.findByPrimaryKey(contentId);

		content.setName(name);
		content.setDescription(description);
		content.setData(data);

		ddmContentPersistence.update(content);

		return content;
	}

	protected void validate(long contentId, String name, String data)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new ContentNameException(
				"Content " + contentId + " has a null name");
		}

		if (Validator.isNull(data)) {
			throw new ContentException(
				"Content " + contentId + " has null data");
		}
	}

}