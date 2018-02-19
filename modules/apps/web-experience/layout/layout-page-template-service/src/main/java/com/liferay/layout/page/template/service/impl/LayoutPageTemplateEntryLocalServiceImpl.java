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
import com.liferay.html.preview.model.HtmlPreviewEntry;
import com.liferay.html.preview.service.HtmlPreviewEntryLocalService;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateEntryException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateEntryNameException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateEntryLocalServiceBaseImpl;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
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
			String name, long[] fragmentEntryIds, ServiceContext serviceContext)
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

		// HTML preview

		HtmlPreviewEntry htmlPreviewEntry = _updateHtmlPreviewEntry(
			layoutPageTemplateEntry, serviceContext);

		layoutPageTemplateEntry.setHtmlPreviewEntryId(
			htmlPreviewEntry.getHtmlPreviewEntryId());

		layoutPageTemplateEntryPersistence.update(layoutPageTemplateEntry);

		// Fragment entry instance links

		_fragmentEntryLinkLocalService.updateFragmentEntryLinks(
			layoutPageTemplateEntry.getGroupId(),
			classNameLocalService.getClassNameId(
				LayoutPageTemplateEntry.class.getName()),
			layoutPageTemplateEntryId, fragmentEntryIds, StringPool.BLANK);

		// Resources

		resourceLocalService.addModelResources(
			layoutPageTemplateEntry, serviceContext);

		return layoutPageTemplateEntry;
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

		// HTML preview

		_htmlPreviewEntryLocalService.deleteHtmlPreviewEntry(
			layoutPageTemplateEntry.getHtmlPreviewEntryId());

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

		return layoutPageTemplateEntryPersistence.findByG_L(
			groupId, layoutPageTemplateCollectionId);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
			long groupId, long layoutPageTemplateCollectionId, int start,
			int end)
		throws PortalException {

		return layoutPageTemplateEntryPersistence.findByG_L(
			groupId, layoutPageTemplateCollectionId, start, end);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
			long groupId, long layoutPageTemplateCollectionId, int start,
			int end,
			OrderByComparator<LayoutPageTemplateEntry> orderByComparator)
		throws PortalException {

		return layoutPageTemplateEntryPersistence.findByG_L(
			groupId, layoutPageTemplateCollectionId, start, end,
			orderByComparator);
	}

	@Override
	public List<LayoutPageTemplateEntry> getLayoutPageTemplateEntries(
		long groupId, long layoutPageTemplateCollectionId, String name,
		int start, int end,
		OrderByComparator<LayoutPageTemplateEntry> orderByComparator) {

		if (Validator.isNull(name)) {
			return layoutPageTemplateEntryPersistence.findByG_L(
				groupId, layoutPageTemplateCollectionId, start, end,
				orderByComparator);
		}

		return layoutPageTemplateEntryPersistence.findByG_L_LikeN(
			groupId, layoutPageTemplateCollectionId, name, start, end,
			orderByComparator);
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
			layoutPageTemplateEntry.getGroupId(),
			classNameLocalService.getClassNameId(
				LayoutPageTemplateEntry.class.getName()),
			layoutPageTemplateEntryId, fragmentEntryIds, editableValues);

		// HTML preview

		_updateHtmlPreviewEntry(layoutPageTemplateEntry, serviceContext);

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

	private HtmlPreviewEntry _updateHtmlPreviewEntry(
			LayoutPageTemplateEntry layoutPageTemplateEntry,
			ServiceContext serviceContext)
		throws PortalException {

		HtmlPreviewEntry htmlPreviewEntry =
			_htmlPreviewEntryLocalService.fetchHtmlPreviewEntry(
				layoutPageTemplateEntry.getHtmlPreviewEntryId());

		if (htmlPreviewEntry == null) {
			return _htmlPreviewEntryLocalService.addHtmlPreviewEntry(
				layoutPageTemplateEntry.getUserId(),
				layoutPageTemplateEntry.getGroupId(),
				classNameLocalService.getClassNameId(
					LayoutPageTemplateEntry.class),
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				layoutPageTemplateEntry.getContent(), ContentTypes.IMAGE_PNG,
				serviceContext);
		}

		return _htmlPreviewEntryLocalService.updateHtmlPreviewEntry(
			layoutPageTemplateEntry.getHtmlPreviewEntryId(),
			layoutPageTemplateEntry.getContent(), ContentTypes.IMAGE_PNG,
			serviceContext);
	}

	@ServiceReference(type = FragmentEntryLinkLocalService.class)
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@ServiceReference(type = HtmlPreviewEntryLocalService.class)
	private HtmlPreviewEntryLocalService _htmlPreviewEntryLocalService;

}