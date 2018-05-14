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

import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateEntryException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateEntryNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateEntryLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;

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
			long userId, long groupId, long layoutPageTemplateCollectionId,
			String name, int type, long[] fragmentEntryIds, int status,
			long layoutPrototypeId, ServiceContext serviceContext)
		throws PortalException {

		// Layout page template entry

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
		layoutPageTemplateEntry.setLayoutPageTemplateCollectionId(
			layoutPageTemplateCollectionId);
		layoutPageTemplateEntry.setName(name);
		layoutPageTemplateEntry.setType(type);
		layoutPageTemplateEntry.setStatus(status);
		layoutPageTemplateEntry.setStatusByUserId(userId);
		layoutPageTemplateEntry.setStatusByUserName(user.getFullName());
		layoutPageTemplateEntry.setStatusDate(new Date());
		layoutPageTemplateEntry.setLayoutPrototypeId(layoutPrototypeId);

		layoutPageTemplateEntryPersistence.update(layoutPageTemplateEntry);

		// Fragment entry instance links

		_fragmentEntryLinkLocalService.updateFragmentEntryLinks(
			userId, layoutPageTemplateEntry.getGroupId(),
			classNameLocalService.getClassNameId(
				LayoutPageTemplateEntry.class.getName()),
			layoutPageTemplateEntryId, fragmentEntryIds, StringPool.BLANK,
			serviceContext);

		// Resources

		resourceLocalService.addModelResources(
			layoutPageTemplateEntry, serviceContext);

		return layoutPageTemplateEntry;
	}

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			String name, int type, long[] fragmentEntryIds, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, name, type,
			fragmentEntryIds, status, 0, serviceContext);
	}

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			String name, int type, long[] fragmentEntryIds,
			ServiceContext serviceContext)
		throws PortalException {

		return addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, name, type,
			fragmentEntryIds, WorkflowConstants.STATUS_DRAFT, serviceContext);
	}

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			String name, long[] fragmentEntryIds, int status,
			ServiceContext serviceContext)
		throws PortalException {

		return addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, name,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, fragmentEntryIds,
			status, serviceContext);
	}

	@Override
	public LayoutPageTemplateEntry addLayoutPageTemplateEntry(
			long userId, long groupId, long layoutPageTemplateCollectionId,
			String name, long[] fragmentEntryIds, ServiceContext serviceContext)
		throws PortalException {

		return addLayoutPageTemplateEntry(
			userId, groupId, layoutPageTemplateCollectionId, name,
			LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, fragmentEntryIds,
			WorkflowConstants.STATUS_DRAFT, serviceContext);
	}

	@Override
	public LayoutPageTemplateEntry deleteLayoutPageTemplateEntry(
			LayoutPageTemplateEntry layoutPageTemplateEntry)
		throws PortalException {

		// Layout page template entry

		layoutPageTemplateEntryPersistence.remove(layoutPageTemplateEntry);

		// Fragment entry instance links

		_fragmentEntryLinkLocalService.
			deleteLayoutPageTemplateEntryFragmentEntryLinks(
				layoutPageTemplateEntry.getGroupId(),
				classNameLocalService.getClassNameId(
					LayoutPageTemplateEntry.class.getName()),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId());

		// Resources

		resourceLocalService.deleteResource(
			layoutPageTemplateEntry.getCompanyId(),
			LayoutPageTemplateEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
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
		long groupId, long layoutPageTemplateCollectionId) {

		return getLayoutPageTemplateEntries(
			groupId, layoutPageTemplateCollectionId,
			WorkflowConstants.STATUS_ANY);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int status) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return layoutPageTemplateEntryPersistence.findByG_L(
				groupId, layoutPageTemplateCollectionId);
		}

		return layoutPageTemplateEntryPersistence.findByG_L_S(
			groupId, layoutPageTemplateCollectionId, status);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int start, int end) {

		return getLayoutPageTemplateEntries(
			groupId, layoutPageTemplateCollectionId,
			WorkflowConstants.STATUS_ANY, start, end);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int status,
		int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return layoutPageTemplateEntryPersistence.findByG_L(
				groupId, layoutPageTemplateCollectionId, start, end);
		}

		return layoutPageTemplateEntryPersistence.findByG_L_S(
			groupId, layoutPageTemplateCollectionId, status, start, end);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int status,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return layoutPageTemplateEntryPersistence.findByG_L(
				groupId, layoutPageTemplateCollectionId, start, end,
				orderByComparator);
		}

		return layoutPageTemplateEntryPersistence.findByG_L_S(
			groupId, layoutPageTemplateCollectionId, status, start, end,
			orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return getLayoutPageTemplateEntries(
			groupId, layoutPageTemplateCollectionId,
			WorkflowConstants.STATUS_ANY, start, end, orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int status, int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			if (Validator.isNull(name)) {
				return layoutPageTemplateEntryPersistence.findByG_L(
					groupId, layoutPageTemplateCollectionId, start, end,
					orderByComparator);
			}

			return layoutPageTemplateEntryPersistence.findByG_L_LikeN(
				groupId, layoutPageTemplateCollectionId, name, start, end,
				orderByComparator);
		}

		if (Validator.isNull(name)) {
			return layoutPageTemplateEntryPersistence.findByG_L_S(
				groupId, layoutPageTemplateCollectionId, status, start, end,
				orderByComparator);
		}

		return layoutPageTemplateEntryPersistence.findByG_L_LikeN_S(
			groupId, layoutPageTemplateCollectionId, name, status, start, end,
			orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		return getLayoutPageTemplateEntries(
			groupId, layoutPageTemplateCollectionId, name,
			WorkflowConstants.STATUS_ANY, start, end, orderByComparator);
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
		long layoutPageTemplateEntryId, boolean defaultTemplate) {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			fetchLayoutPageTemplateEntry(layoutPageTemplateEntryId);

		if (layoutPageTemplateEntry == null) {
			return null;
		}

		LayoutPageTemplateEntry defaultLayoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.fetchByG_C_C_D_First(
				layoutPageTemplateEntry.getGroupId(),
				layoutPageTemplateEntry.getClassNameId(),
				layoutPageTemplateEntry.getClassTypeId(), true, null);

		if (defaultTemplate && (defaultLayoutPageTemplateEntry != null) &&
			(defaultLayoutPageTemplateEntry.getLayoutPageTemplateEntryId() !=
				layoutPageTemplateEntryId)) {

			defaultLayoutPageTemplateEntry.setDefaultTemplate(false);

			layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
				defaultLayoutPageTemplateEntry);
		}

		layoutPageTemplateEntry.setDefaultTemplate(defaultTemplate);

		layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
			layoutPageTemplateEntry);

		return layoutPageTemplateEntry;
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long userId, long layoutPageTemplateEntryId, int status)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.findByPrimaryKey(
				layoutPageTemplateEntryId);

		layoutPageTemplateEntry.setStatus(status);
		layoutPageTemplateEntry.setStatusByUserId(userId);
		layoutPageTemplateEntry.setStatusByUserName(user.getScreenName());
		layoutPageTemplateEntry.setStatusDate(new Date());

		return layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(layoutPageTemplateEntry);
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, long classNameId, long classTypeId)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.findByPrimaryKey(
				layoutPageTemplateEntryId);

		layoutPageTemplateEntry.setClassNameId(classNameId);
		layoutPageTemplateEntry.setClassTypeId(classTypeId);

		return layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(layoutPageTemplateEntry);
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, String name)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.findByPrimaryKey(
				layoutPageTemplateEntryId);

		if (Objects.equals(layoutPageTemplateEntry.getName(), name)) {
			return layoutPageTemplateEntry;
		}

		validate(layoutPageTemplateEntry.getGroupId(), name);

		layoutPageTemplateEntry.setName(name);

		return layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(layoutPageTemplateEntry);
	}

	@Override
	public LayoutPageTemplateEntry updateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId, String name,
			long[] fragmentEntryIds, String editableValues,
			ServiceContext serviceContext)
		throws PortalException {

		// Layout page template entry

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.findByPrimaryKey(
				layoutPageTemplateEntryId);

		if (!Objects.equals(layoutPageTemplateEntry.getName(), name)) {
			validate(layoutPageTemplateEntry.getGroupId(), name);
		}

		layoutPageTemplateEntry.setModifiedDate(new Date());
		layoutPageTemplateEntry.setName(name);

		layoutPageTemplateEntryPersistence.update(layoutPageTemplateEntry);

		// Fragment entry instance links

		_fragmentEntryLinkLocalService.updateFragmentEntryLinks(
			serviceContext.getUserId(), layoutPageTemplateEntry.getGroupId(),
			classNameLocalService.getClassNameId(
				LayoutPageTemplateEntry.class.getName()),
			layoutPageTemplateEntryId, fragmentEntryIds, editableValues,
			serviceContext);

		return layoutPageTemplateEntry;
	}

	protected void validate(long groupId, String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new LayoutPageTemplateEntryNameException(
				"Name must not be null for group " + groupId);
		}

		int nameMaxLength = ModelHintsUtil.getMaxLength(
			LayoutPageTemplateEntry.class.getName(), "name");

		if (name.length() > nameMaxLength) {
			throw new LayoutPageTemplateEntryNameException(
				"Maximum length of name exceeded");
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryPersistence.fetchByG_N(groupId, name);

		if (layoutPageTemplateEntry != null) {
			throw new DuplicateLayoutPageTemplateEntryException(name);
		}
	}

	@ServiceReference(type = FragmentEntryLinkLocalService.class)
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

}