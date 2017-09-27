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

import com.liferay.fragment.model.FragmentEntry;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateEntryException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateEntryNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateFragmentLocalService;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.bean.BeanReference;
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
public class LayoutPageTemplateEntryLocalServiceImpl
	extends LayoutPageTemplateEntryLocalServiceBaseImpl {

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long userId, long groupId, long layoutPageTemplateFolderId,
			String name, List<FragmentEntry> layoutPageTemplateFragments,
			ServiceContext serviceContext)
		throws PortalException {

		// Layout Page Template Entry

		User user = userLocalService.getUser(userId);

		validate(groupId, name);

		long layoutPageTemplateEntryId = counterLocalService.increment();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.create(
				layoutPageTemplateEntryId);

		layoutPageTemplateEntry.setGroupId(groupId);
		layoutPageTemplateEntry.setCompanyId(user.getCompanyId());
		layoutPageTemplateEntry.setUserId(user.getUserId());
		layoutPageTemplateEntry.setUserName(user.getFullName());
		layoutPageTemplateEntry.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		layoutPageTemplateEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		layoutPageTemplateEntry.setLayoutPageTemplateFolderId(
			layoutPageTemplateFolderId);
		layoutPageTemplateEntry.setName(name);

		layoutPageTemplateEntryPersistence.update(layoutPageTemplateEntry);

		// Resources

		resourceLocalService.addModelResources(
			layoutPageTemplateEntry, serviceContext);

		// Layout Page Template Fragments

		if (layoutPageTemplateFragments != null) {
			int position = 0;

			for (FragmentEntry fragmentEntry : layoutPageTemplateFragments) {
				_layoutPageTemplateFragmentLocalService.
					addLayoutPageTemplateFragment(
						userId, groupId, layoutPageTemplateEntryId,
						fragmentEntry.getFragmentEntryId(), position++,
						serviceContext);
			}
		}

		return layoutPageTemplateEntry;
	}

	@Override
	public LayoutPageTemplateEntry deleteLayoutPageTemplateEntry(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		// Layout Page Template Entry

		layoutPageTemplateEntryPersistence.remove(layoutPageTemplateEntry);

		// Resources

		resourceLocalService.deleteResource(
			layoutPageTemplateEntry.getCompanyId(),
			LayoutPageTemplateEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		// Layout Page Template Fragments

		_layoutPageTemplateFragmentLocalService.deleteByLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getGroupId(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		return layoutPageTemplateEntry;
	}

	@Override
	public LayoutPageTemplateEntry deleteLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			getLayoutPageTemplateEntry(layoutPageTemplateEntryId);

		return deleteLayoutPageTemplateEntry(layoutPageTemplateEntry);
	}

	@Override
	public LayoutPageTemplateEntry fetchLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId) {

		return layoutPageTemplateEntryPersistence.fetchByPrimaryKey(
			layoutPageTemplateEntryId);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateFolderId) {

		return layoutPageTemplateEntryPersistence.findByG_L(
			groupId, layoutPageTemplateFolderId);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
			long groupId, long layoutPageTemplateFolderId, int start, int end)
		throws PortalException {

		return layoutPageTemplateEntryPersistence.findByG_L(
			groupId, layoutPageTemplateFolderId, start, end);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
			long groupId, long layoutPageTemplateFolderId, int start, int end,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws PortalException {

		return layoutPageTemplateEntryPersistence.findByG_L(
			groupId, layoutPageTemplateFolderId, start, end, orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateFolderId, String name, int start,
		int end, OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (Validator.isNull(name)) {
			return layoutPageTemplateEntryPersistence.findByG_L(
				groupId, layoutPageTemplateFolderId, start, end,
				orderByComparator);
		}

		return layoutPageTemplateEntryPersistence.findByG_L_LikeN(
			groupId, layoutPageTemplateFolderId, name, start, end,
			orderByComparator);
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long userId, long layoutPageTemplateEntryId, String name,
			List<FragmentEntry> layoutPageTemplateFragments,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.findByPrimaryKey(
				layoutPageTemplateEntryId);

		if (!Objects.equals(layoutPageTemplateEntry.getName(), name)) {
			validate(layoutPageTemplateEntry.getGroupId(), name);
		}

		layoutPageTemplateEntry.setModifiedDate(new Date());
		layoutPageTemplateEntry.setName(name);

		layoutPageTemplateEntryPersistence.update(layoutPageTemplateEntry);

		// Layout Page Template Fragments

		_layoutPageTemplateFragmentLocalService.deleteByLayoutPageTemplateEntry(
			layoutPageTemplateEntry.getGroupId(), layoutPageTemplateEntryId);

		if (layoutPageTemplateFragments != null) {
			int position = 0;

			for (FragmentEntry fragmentEntry : layoutPageTemplateFragments) {
				_layoutPageTemplateFragmentLocalService.
					addLayoutPageTemplateFragment(
						userId, layoutPageTemplateEntry.getGroupId(),
						layoutPageTemplateEntryId,
						fragmentEntry.getFragmentEntryId(), position++,
						serviceContext);
			}
		}

		return layoutPageTemplateEntry;
	}

	protected void validate(long groupId, String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new LayoutPageTemplateEntryNameException(
				"Name must not be null for group " + groupId);
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.fetchByG_N(groupId, name);

		if (layoutPageTemplateEntry != null) {
			throw new DuplicateLayoutPageTemplateEntryException(name);
		}
	}

	@BeanReference(type = LayoutPageTemplateFragmentLocalService.class)
	private LayoutPageTemplateFragmentLocalService
		_layoutPageTemplateFragmentLocalService;

}