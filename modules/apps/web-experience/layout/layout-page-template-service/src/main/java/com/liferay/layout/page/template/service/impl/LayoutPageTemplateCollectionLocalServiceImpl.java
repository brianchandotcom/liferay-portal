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

package com.liferay.layout.page.template.service.impl;

import com.liferay.layout.page.template.constants.LayoutPageTemplateCollectionTypeConstants;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateCollectionException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateCollectionNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateCollectionLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateCollectionLocalServiceImpl
	extends LayoutPageTemplateCollectionLocalServiceBaseImpl {

	@Override
	public LayoutPageTemplateCollection addLayoutPageTemplateCollection(
			long userId, long groupId, String name, String description,
			int type, ServiceContext serviceContext)
		throws PortalException {

		// Layout page template collection

		User user = userLocalService.getUser(userId);

		validate(groupId, name);

		long layoutPageTemplateId = counterLocalService.increment();

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			layoutPageTemplateCollectionPersistence.create(
				layoutPageTemplateId);

		layoutPageTemplateCollection.setGroupId(groupId);
		layoutPageTemplateCollection.setCompanyId(user.getCompanyId());
		layoutPageTemplateCollection.setUserId(user.getUserId());
		layoutPageTemplateCollection.setUserName(user.getFullName());
		layoutPageTemplateCollection.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		layoutPageTemplateCollection.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		layoutPageTemplateCollection.setName(name);
		layoutPageTemplateCollection.setDescription(description);
		layoutPageTemplateCollection.setType(type);

		layoutPageTemplateCollectionPersistence.update(
			layoutPageTemplateCollection);

		// Resources

		resourceLocalService.addModelResources(
			layoutPageTemplateCollection, serviceContext);

		return layoutPageTemplateCollection;
	}

	@Override
	public LayoutPageTemplateCollection addLayoutPageTemplateCollection(
			long userId, long groupId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		return addLayoutPageTemplateCollection(
			userId, groupId, name, description,
			LayoutPageTemplateCollectionTypeConstants.TYPE_BASIC,
			serviceContext);
	}

	@Override
	public LayoutPageTemplateCollection deleteLayoutPageTemplateCollection(
			LayoutPageTemplateCollection layoutPageTemplateCollection)
		throws PortalException {

		/// Layout page template collection

		layoutPageTemplateCollectionPersistence.remove(
			layoutPageTemplateCollection);

		// Resources

		resourceLocalService.deleteResource(
			layoutPageTemplateCollection.getCompanyId(),
			LayoutPageTemplateCollection.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId());

		// Layout page template entries

		List<LayoutPageTemplateEntry> layoutPageTemplateEntries =
			layoutPageTemplateEntryLocalService.getLayoutPageTemplateEntries(
				layoutPageTemplateCollection.getGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId());

		for (LayoutPageTemplateEntry layoutPageTemplateEntry :
				layoutPageTemplateEntries) {

			layoutPageTemplateEntryLocalService.deleteLayoutPageTemplateEntry(
				layoutPageTemplateEntry);
		}

		return layoutPageTemplateCollection;
	}

	@Override
	public LayoutPageTemplateCollection deleteLayoutPageTemplateCollection(
			long layoutPageTemplateCollectionId)
		throws PortalException {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			getLayoutPageTemplateCollection(layoutPageTemplateCollectionId);

		return deleteLayoutPageTemplateCollection(layoutPageTemplateCollection);
	}

	@Override
	public LayoutPageTemplateCollection fetchLayoutPageTemplateCollection(
		long layoutPageTemplateCollectionId) {

		return layoutPageTemplateCollectionPersistence.fetchByPrimaryKey(
			layoutPageTemplateCollectionId);
	}

	@Override
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
			long groupId, int start, int end)
		throws PortalException {

		return layoutPageTemplateCollectionPersistence.findByGroupId(
			groupId, start, end);
	}

	@Override
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
			long groupId, int start, int end,
			OrderByComparator<LayoutPageTemplateCollection> orderByComparator)
		throws PortalException {

		return layoutPageTemplateCollectionPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateCollection> getLayoutPageTemplateCollections(
		long groupId, String name, int start, int end,
		OrderByComparator<LayoutPageTemplateCollection> orderByComparator) {

		if (Validator.isNull(name)) {
			return layoutPageTemplateCollectionPersistence.findByGroupId(
				groupId, start, end, orderByComparator);
		}

		return layoutPageTemplateCollectionPersistence.findByG_LikeN(
			groupId, name, start, end, orderByComparator);
	}

	@Override
	public LayoutPageTemplateCollection updateLayoutPageTemplateCollection(
			long layoutPageTemplateCollectionId, String name,
			String description)
		throws PortalException {

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			layoutPageTemplateCollectionPersistence.findByPrimaryKey(
				layoutPageTemplateCollectionId);

		if (!Objects.equals(layoutPageTemplateCollection.getName(), name)) {
			validate(layoutPageTemplateCollection.getGroupId(), name);
		}

		layoutPageTemplateCollection.setModifiedDate(new Date());
		layoutPageTemplateCollection.setName(name);
		layoutPageTemplateCollection.setDescription(description);

		layoutPageTemplateCollectionPersistence.update(
			layoutPageTemplateCollection);

		return layoutPageTemplateCollection;
	}

	protected void validate(long groupId, String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new LayoutPageTemplateCollectionNameException(
				"Name must not be null for group " + groupId);
		}

		LayoutPageTemplateCollection layoutPageTemplateCollection =
			layoutPageTemplateCollectionPersistence.fetchByG_N(groupId, name);

		if (layoutPageTemplateCollection != null) {
			throw new DuplicateLayoutPageTemplateCollectionException(name);
		}
	}

}