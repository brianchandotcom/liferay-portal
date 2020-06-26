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

package com.liferay.style.book.service.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.style.book.exception.StyleBookEntryNameException;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.base.StyleBookEntryLocalServiceBaseImpl;

import java.util.Date;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 * @see StyleBookEntryLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.style.book.model.StyleBookEntry",
	service = AopService.class
)
public class StyleBookEntryLocalServiceImpl
	extends StyleBookEntryLocalServiceBaseImpl {

	@Override
	public StyleBookEntry addStyleBookEntry(
			long userId, long groupId, String name,
			ServiceContext serviceContext)
		throws PortalException {

		// Style book entry

		User user = userLocalService.getUser(userId);

		long companyId = user.getCompanyId();

		if (serviceContext != null) {
			companyId = serviceContext.getCompanyId();
		}
		else {
			serviceContext = new ServiceContext();
		}

		validate(name);

		long styleBookEntryId = counterLocalService.increment();

		StyleBookEntry styleBookEntry = styleBookEntryPersistence.create(
			styleBookEntryId);

		styleBookEntry.setGroupId(groupId);
		styleBookEntry.setCompanyId(companyId);
		styleBookEntry.setUserId(user.getUserId());
		styleBookEntry.setUserName(user.getFullName());
		styleBookEntry.setCreateDate(serviceContext.getCreateDate(new Date()));
		styleBookEntry.setName(name);

		return styleBookEntryPersistence.update(styleBookEntry);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public StyleBookEntry deleteStyleBookEntry(StyleBookEntry styleBookEntry)
		throws PortalException {

		// Style book entry

		styleBookEntryPersistence.remove(styleBookEntry);

		return styleBookEntry;
	}

	@Override
	public StyleBookEntry updateStyleBookEntry(
			long styleBookEntryId, String name)
		throws PortalException {

		StyleBookEntry styleBookEntry =
			styleBookEntryPersistence.findByPrimaryKey(styleBookEntryId);

		validate(name);

		styleBookEntry.setName(name);

		return styleBookEntryPersistence.update(styleBookEntry);
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new StyleBookEntryNameException("Name must not be null");
		}

		if (name.contains(StringPool.PERIOD) ||
			name.contains(StringPool.SLASH)) {

			throw new StyleBookEntryNameException(
				"Name contains invalid characters");
		}

		int nameMaxLength = ModelHintsUtil.getMaxLength(
			StyleBookEntry.class.getName(), "name");

		if (name.length() > nameMaxLength) {
			throw new StyleBookEntryNameException(
				"Maximum length of name exceeded");
		}
	}

}