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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.ContentException;
import com.liferay.portlet.dynamicdatamapping.ContentNameException;
import com.liferay.portlet.dynamicdatamapping.ContentXmlException;
import com.liferay.portlet.dynamicdatamapping.model.DDMContent;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMContentLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class DDMContentLocalServiceImpl extends DDMContentLocalServiceBaseImpl {

	@Override
	public DDMContent addContent(
			long userId, long groupId, String name, String description,
			String xml, ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		try {
			xml = DDMXMLUtil.formatXML(xml);
		}
		catch (Exception e) {
			throw new ContentXmlException(e);
		}

		Date now = new Date();

		validate(name, xml);

		long contentId = counterLocalService.increment();

		DDMContent content = ddmContentPersistence.create(contentId);

		content.setUuid(serviceContext.getUuid());
		content.setGroupId(serviceContext.getScopeGroupId());
		content.setCompanyId(user.getCompanyId());
		content.setUserId(user.getUserId());
		content.setUserName(user.getFullName());
		content.setCreateDate(serviceContext.getCreateDate(now));
		content.setModifiedDate(serviceContext.getModifiedDate(now));
		content.setName(name);
		content.setDescription(description);
		content.setXml(xml);

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
			long contentId, String name, String description, String xml,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			xml = DDMXMLUtil.formatXML(xml);
		}
		catch (Exception e) {
			throw new ContentXmlException();
		}

		validate(name, xml);

		DDMContent content = ddmContentPersistence.findByPrimaryKey(contentId);

		content.setModifiedDate(serviceContext.getModifiedDate(null));
		content.setName(name);
		content.setDescription(description);
		content.setXml(xml);

		ddmContentPersistence.update(content);

		return content;
	}

	protected void validate(String name, String xml) throws PortalException {
		if (Validator.isNull(name)) {
			throw new ContentNameException();
		}

		if (Validator.isNull(xml)) {
			throw new ContentException();
		}

		try {
			SAXReaderUtil.read(xml);
		}
		catch (DocumentException de) {
			throw new ContentException();
		}
	}

}