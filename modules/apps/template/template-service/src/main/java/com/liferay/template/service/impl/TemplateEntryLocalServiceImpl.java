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

package com.liferay.template.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.base.TemplateEntryLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "model.class.name=com.liferay.template.model.TemplateEntry",
	service = AopService.class
)
public class TemplateEntryLocalServiceImpl
	extends TemplateEntryLocalServiceBaseImpl {

	@Override
	public TemplateEntry addTemplateEntry(
			long userId, long groupId, String infoItemClassName,
			String infoItemFormVariationKey, long ddmTemplateId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long templateEntryId = counterLocalService.increment();

		TemplateEntry templateEntry = templateEntryPersistence.create(
			templateEntryId);

		templateEntry.setUuid(PortalUUIDUtil.generate());
		templateEntry.setCompanyId(user.getCompanyId());
		templateEntry.setUserId(user.getUserId());
		templateEntry.setUserName(user.getFullName());
		templateEntry.setCreateDate(serviceContext.getCreateDate(new Date()));
		templateEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		templateEntry.setGroupId(groupId);
		templateEntry.setInfoItemClassName(infoItemClassName);
		templateEntry.setInfoItemFormVariationKey(infoItemFormVariationKey);
		templateEntry.setDDMTemplateId(ddmTemplateId);

		return templateEntryPersistence.update(templateEntry);
	}

	@Override
	public TemplateEntry deleteTemplateEntry(long templateEntryId)
		throws PortalException {

		return templateEntryPersistence.remove(templateEntryId);
	}

	@Override
	public TemplateEntry fetchTemplateEntryByDDMTemplateId(long ddmTemplateId) {
		return templateEntryPersistence.fetchByDDMTemplateId(ddmTemplateId);
	}

	@Override
	public List<TemplateEntry> getTemplateEntries(
		long groupId, int start, int end,
		OrderByComparator<TemplateEntry> orderByComparator) {

		return templateEntryPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getTemplateEntriesCount(long groupId) {
		return templateEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public TemplateEntry updateTemplateEntry(
			long templateEntryId, ServiceContext serviceContext)
		throws PortalException {

		TemplateEntry templateEntry = templateEntryPersistence.findByPrimaryKey(
			templateEntryId);

		templateEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));

		return templateEntryPersistence.update(templateEntry);
	}

}