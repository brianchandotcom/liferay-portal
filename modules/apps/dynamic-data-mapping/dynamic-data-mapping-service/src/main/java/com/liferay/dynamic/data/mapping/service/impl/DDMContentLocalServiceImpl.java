/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
 * @author     Brian Wing Shun Chan
 * @author     Eduardo Lundgren
 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
 *             DDMFieldLocalServiceImpl}
 */
@Component(
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMContent",
	service = AopService.class
)
@Deprecated
public class DDMContentLocalServiceImpl extends DDMContentLocalServiceBaseImpl {

	@Override
	public DDMContent addContent(
			long userId, long groupId, String name, String description,
			String data, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		_validate(0, name, data);

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

		return ddmContentPersistence.update(content);
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

		_validate(contentId, name, data);

		DDMContent content = ddmContentPersistence.findByPrimaryKey(contentId);

		content.setName(name);
		content.setDescription(description);
		content.setData(data);

		return ddmContentPersistence.update(content);
	}

	private void _validate(long contentId, String name, String data)
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