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
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.style.book.exception.StyleBookEntryNameException;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.base.StyleBookEntryLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
	public StyleBookEntry deleteStyleBookEntry(long styleBookEntryId)
		throws PortalException {

		return deleteStyleBookEntry(getStyleBookEntry(styleBookEntryId));
	}

	@Override
	public StyleBookEntry deleteStyleBookEntry(StyleBookEntry styleBookEntry)
		throws PortalException {

		// Style book entry

		styleBookEntryPersistence.remove(styleBookEntry);

		return styleBookEntry;
	}

	@Override
	public List<StyleBookEntry> getStyleBookEntries(
		long groupId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return styleBookEntryPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<StyleBookEntry> getStyleBookEntries(
		long groupId, String name, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return styleBookEntryPersistence.findByG_LikeN(
			groupId, _customSQL.keywords(name, false, WildcardMode.SURROUND)[0],
			start, end, orderByComparator);
	}

	@Override
	public int getStyleBookEntriesCount(long groupId) {
		return styleBookEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public int getStyleBookEntriesCount(long groupId, String name) {
		return styleBookEntryPersistence.countByG_LikeN(
			groupId,
			_customSQL.keywords(name, false, WildcardMode.SURROUND)[0]);
	}

	@Override
	public StyleBookEntry updateStyleBookEntry(
			long styleBookEntryId, long previewFileEntryId)
		throws PortalException {

		StyleBookEntry styleBookEntry =
			styleBookEntryPersistence.findByPrimaryKey(styleBookEntryId);

		styleBookEntry.setPreviewFileEntryId(previewFileEntryId);

		return styleBookEntryPersistence.update(styleBookEntry);
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

	@Reference
	private CustomSQL _customSQL;

}