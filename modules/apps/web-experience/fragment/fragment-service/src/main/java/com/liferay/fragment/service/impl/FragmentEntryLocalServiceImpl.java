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

package com.liferay.fragment.service.impl;

import com.liferay.fragment.exception.DuplicateFragmentEntryException;
import com.liferay.fragment.exception.FragmentEntryNameException;
import com.liferay.fragment.exception.RequiredFragmentEntryException;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.processor.FragmentEntryProcessorRegistry;
import com.liferay.fragment.service.base.FragmentEntryLocalServiceBaseImpl;
import com.liferay.html.preview.model.HtmlPreviewEntry;
import com.liferay.html.preview.service.HtmlPreviewEntryLocalService;
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
public class FragmentEntryLocalServiceImpl
	extends FragmentEntryLocalServiceBaseImpl {

	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			ServiceContext serviceContext)
		throws PortalException {

		// Fragment entry

		User user = userLocalService.getUser(userId);

		validate(groupId, fragmentCollectionId, 0, name);

		long fragmentEntryId = counterLocalService.increment();

		FragmentEntry fragmentEntry = fragmentEntryPersistence.create(
			fragmentEntryId);

		fragmentEntry.setGroupId(groupId);
		fragmentEntry.setCompanyId(user.getCompanyId());
		fragmentEntry.setUserId(user.getUserId());
		fragmentEntry.setUserName(user.getFullName());
		fragmentEntry.setCreateDate(serviceContext.getCreateDate(new Date()));
		fragmentEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		fragmentEntry.setFragmentCollectionId(fragmentCollectionId);
		fragmentEntry.setName(name);

		fragmentEntryPersistence.update(fragmentEntry);

		// Resources

		resourceLocalService.addModelResources(fragmentEntry, serviceContext);

		return fragmentEntry;
	}

	@Override
	public FragmentEntry addFragmentEntry(
			long userId, long groupId, long fragmentCollectionId, String name,
			String css, String html, String js, ServiceContext serviceContext)
		throws PortalException {

		// Fragment entry

		User user = userLocalService.getUser(userId);

		validate(groupId, fragmentCollectionId, 0, name);
		validateContent(html);

		long fragmentEntryId = counterLocalService.increment();

		FragmentEntry fragmentEntry = fragmentEntryPersistence.create(
			fragmentEntryId);

		fragmentEntry.setGroupId(groupId);
		fragmentEntry.setCompanyId(user.getCompanyId());
		fragmentEntry.setUserId(user.getUserId());
		fragmentEntry.setUserName(user.getFullName());
		fragmentEntry.setCreateDate(serviceContext.getCreateDate(new Date()));
		fragmentEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		fragmentEntry.setFragmentCollectionId(fragmentCollectionId);
		fragmentEntry.setName(name);
		fragmentEntry.setCss(css);
		fragmentEntry.setHtml(html);
		fragmentEntry.setJs(js);

		// HTML preview

		HtmlPreviewEntry htmlPreviewEntry = _updateHtmlPreviewEntry(
			fragmentEntry, serviceContext);

		fragmentEntry.setHtmlPreviewEntryId(
			htmlPreviewEntry.getHtmlPreviewEntryId());

		fragmentEntryPersistence.update(fragmentEntry);

		// Resources

		resourceLocalService.addModelResources(fragmentEntry, serviceContext);

		return fragmentEntry;
	}

	@Override
	public FragmentEntry deleteFragmentEntry(FragmentEntry fragmentEntry)
		throws PortalException {

		// Fragment entry

		long fragmentEntryInstanceLinkCount =
			fragmentEntryInstanceLinkPersistence.countByG_F(
				fragmentEntry.getGroupId(), fragmentEntry.getFragmentEntryId());

		if (fragmentEntryInstanceLinkCount > 0) {
			throw new RequiredFragmentEntryException();
		}

		fragmentEntryPersistence.remove(fragmentEntry);

		// Resources

		resourceLocalService.deleteResource(
			fragmentEntry.getCompanyId(), FragmentEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			fragmentEntry.getFragmentEntryId());

		// HTML preview

		if (fragmentEntry.getHtmlPreviewEntryId() > 0) {
			_htmlPreviewEntryLocalService.deleteHtmlPreviewEntry(
				fragmentEntry.getHtmlPreviewEntryId());
		}

		return fragmentEntry;
	}

	@Override
	public FragmentEntry deleteFragmentEntry(long fragmentEntryId)
		throws PortalException {

		FragmentEntry fragmentEntry = getFragmentEntry(fragmentEntryId);

		return deleteFragmentEntry(fragmentEntry);
	}

	@Override
	public List<FragmentEntry> fetchFragmentEntries(long fragmentCollectionId) {
		return fragmentEntryPersistence.findByFragmentCollectionId(
			fragmentCollectionId);
	}

	@Override
	public FragmentEntry fetchFragmentEntry(long fragmentEntryId) {
		return fragmentEntryPersistence.fetchByPrimaryKey(fragmentEntryId);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
			long fragmentCollectionId, int start, int end)
		throws PortalException {

		return fragmentEntryPersistence.findByFragmentCollectionId(
			fragmentCollectionId, start, end);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
			long groupId, long fragmentCollectionId, int start, int end,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws PortalException {

		return fragmentEntryPersistence.findByG_FCI(
			groupId, fragmentCollectionId, start, end, orderByComparator);
	}

	@Override
	public List<FragmentEntry> getFragmentEntries(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator) {

		if (Validator.isNull(name)) {
			return fragmentEntryPersistence.findByG_FCI(
				groupId, fragmentCollectionId, start, end, orderByComparator);
		}

		return fragmentEntryPersistence.findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, start, end, orderByComparator);
	}

	@Override
	public int getFragmentEntriesCount(long fragmentCollectionId) {
		return fragmentEntryPersistence.countByFragmentCollectionId(
			fragmentCollectionId);
	}

	@Override
	public FragmentEntry updateFragmentEntry(long fragmentEntryId, String name)
		throws PortalException {

		FragmentEntry fragmentEntry = fragmentEntryPersistence.findByPrimaryKey(
			fragmentEntryId);

		if (Objects.equals(fragmentEntry.getName(), name)) {
			return fragmentEntry;
		}

		validate(
			fragmentEntry.getGroupId(), fragmentEntry.getFragmentCollectionId(),
			fragmentEntryId, name);

		fragmentEntry.setName(name);

		return fragmentEntryPersistence.update(fragmentEntry);
	}

	@Override
	public FragmentEntry updateFragmentEntry(
			long fragmentEntryId, String name, String css, String html,
			String js, ServiceContext serviceContext)
		throws PortalException {

		FragmentEntry fragmentEntry = fragmentEntryPersistence.findByPrimaryKey(
			fragmentEntryId);

		validate(
			fragmentEntry.getGroupId(), fragmentEntry.getFragmentCollectionId(),
			fragmentEntryId, name);

		validateContent(html);

		fragmentEntry.setModifiedDate(new Date());
		fragmentEntry.setName(name);
		fragmentEntry.setCss(css);
		fragmentEntry.setHtml(html);
		fragmentEntry.setJs(js);

		_updateHtmlPreviewEntry(fragmentEntry, serviceContext);

		fragmentEntryPersistence.update(fragmentEntry);

		return fragmentEntry;
	}

	protected void validate(
			long groupId, long fragmentCollectionId, long fragmentEntryId,
			String name)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new FragmentEntryNameException(
				"Name must not be null for group " + groupId);
		}

		FragmentEntry fragmentEntry = fragmentEntryPersistence.fetchByG_FCI_N(
			groupId, fragmentCollectionId, name);

		if ((fragmentEntry != null) &&
			(fragmentEntry.getFragmentEntryId() != fragmentEntryId)) {

			throw new DuplicateFragmentEntryException(name);
		}
	}

	protected void validateContent(String html) throws PortalException {
		_fragmentEntryProcessorRegistry.validateFragmentEntryHTML(html);
	}

	private HtmlPreviewEntry _updateHtmlPreviewEntry(
			FragmentEntry fragmentEntry, ServiceContext serviceContext)
		throws PortalException {

		HtmlPreviewEntry htmlPreviewEntry =
			_htmlPreviewEntryLocalService.fetchHtmlPreviewEntry(
				fragmentEntry.getHtmlPreviewEntryId());

		if (htmlPreviewEntry != null) {
			return _htmlPreviewEntryLocalService.updateHtmlPreviewEntry(
				htmlPreviewEntry.getHtmlPreviewEntryId(),
				fragmentEntry.getContent(), ContentTypes.IMAGE_PNG,
				serviceContext);
		}

		return _htmlPreviewEntryLocalService.addHtmlPreviewEntry(
			fragmentEntry.getUserId(), fragmentEntry.getGroupId(),
			classNameLocalService.getClassNameId(FragmentEntry.class),
			fragmentEntry.getFragmentEntryId(), fragmentEntry.getContent(),
			ContentTypes.IMAGE_PNG, serviceContext);
	}

	@ServiceReference(type = FragmentEntryProcessorRegistry.class)
	private FragmentEntryProcessorRegistry _fragmentEntryProcessorRegistry;

	@ServiceReference(type = HtmlPreviewEntryLocalService.class)
	private HtmlPreviewEntryLocalService _htmlPreviewEntryLocalService;

}