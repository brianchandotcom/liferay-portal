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

import com.liferay.fragment.exception.NoSuchEntryException;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.page.template.exception.NoSuchPageTemplateEntryException;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateFragment;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateFragmentLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateFragmentLocalServiceImpl
	extends LayoutPageTemplateFragmentLocalServiceBaseImpl {

	@Override
	public LayoutPageTemplateFragment addLayoutPageTemplateFragment(
			long userId, long groupId, long layoutPageTemplateEntryId,
			long fragmentEntryId, int position, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validateLayoutPageTemplateEntry(layoutPageTemplateEntryId);
		validateFragmentEntry(fragmentEntryId);

		long layoutPageTemplateFragmentId = counterLocalService.increment();

		LayoutPageTemplateFragment layoutPageTemplateFragment =
			layoutPageTemplateFragmentPersistence.create(
				layoutPageTemplateFragmentId);

		layoutPageTemplateFragment.setGroupId(groupId);
		layoutPageTemplateFragment.setCompanyId(user.getCompanyId());
		layoutPageTemplateFragment.setUserId(user.getUserId());
		layoutPageTemplateFragment.setUserName(user.getFullName());
		layoutPageTemplateFragment.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		layoutPageTemplateFragment.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		layoutPageTemplateFragment.setLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);
		layoutPageTemplateFragment.setFragmentEntryId(fragmentEntryId);
		layoutPageTemplateFragment.setPosition(position);

		layoutPageTemplateFragmentPersistence.update(
			layoutPageTemplateFragment);

		return layoutPageTemplateFragment;
	}

	@Override
	public List<LayoutPageTemplateFragment> deleteByLayoutPageTemplateEntry(
			long groupId, long layoutPageTemplateEntryId)
		throws PortalException {

		List<LayoutPageTemplateFragment> deletedLayoutPageTemplateFragments =
			new ArrayList<>();

		List<LayoutPageTemplateFragment> layoutPageTemplateFragments =
			getLayoutPageTemplateFragmentsByPageTemplate(
				groupId, layoutPageTemplateEntryId);

		if (ListUtil.isEmpty(layoutPageTemplateFragments)) {
			return Collections.emptyList();
		}

		for (LayoutPageTemplateFragment layoutPageTemplateFragment :
				layoutPageTemplateFragments) {

			layoutPageTemplateFragmentPersistence.remove(
				layoutPageTemplateFragment);

			deletedLayoutPageTemplateFragments.add(layoutPageTemplateFragment);
		}

		return deletedLayoutPageTemplateFragments;
	}

	@Override
	public LayoutPageTemplateFragment deleteLayoutPageTemplateFragment(
			LayoutPageTemplateFragment layoutPageTemplateFragment)
		throws PortalException {

		layoutPageTemplateEntryPersistence.remove(layoutPageTemplateFragment);

		return layoutPageTemplateFragment;
	}

	@Override
	public List<LayoutPageTemplateFragment>
		getLayoutPageTemplateFragmentsByPageTemplate(
			long groupId, long layoutPageTemplateEntryId) {

		return layoutPageTemplateFragmentPersistence.findByG_L(
			groupId, layoutPageTemplateEntryId);
	}

	protected void validateFragmentEntry(long fragmentEntryId)
		throws PortalException {

		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(fragmentEntryId);

		if (fragmentEntry == null) {
			throw new NoSuchEntryException();
		}
	}

	protected void validateLayoutPageTemplateEntry(
			long layoutPageTemplateEntryId)
		throws PortalException {

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				layoutPageTemplateEntryId);

		if (layoutPageTemplateEntry == null) {
			throw new NoSuchPageTemplateEntryException();
		}
	}

	@ServiceReference(type = FragmentEntryLocalService.class)
	private FragmentEntryLocalService _fragmentEntryLocalService;

}