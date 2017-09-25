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
import com.liferay.layout.page.template.exception.NoSuchPageTemplateException;
import com.liferay.layout.page.template.model.LayoutPageTemplate;
import com.liferay.layout.page.template.model.LayoutPageTemplateFragment;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateFragmentLocalServiceBaseImpl;
import com.liferay.layout.page.template.service.persistence.LayoutPageTemplateFragmentPK;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Date;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateFragmentLocalServiceImpl
	extends LayoutPageTemplateFragmentLocalServiceBaseImpl {

	@Override
	public LayoutPageTemplateFragment addLayoutPageTemplateFragment(
			long userId, long groupId, long layoutPageTemplateId,
			long fragmentId, int position, ServiceContext serviceContext)
		throws PortalException {

		// Layout Page Template Fragment

		User user = userLocalService.getUser(userId);

		_validateFragment(fragmentId);
		_validateLayoutPageTemplate(layoutPageTemplateId);

		LayoutPageTemplateFragmentPK layoutPageTemplateFragmentPK =
			new LayoutPageTemplateFragmentPK(
				groupId, layoutPageTemplateId, fragmentId);

		LayoutPageTemplateFragment layoutPageTemplateFragment =
			layoutPageTemplateFragmentPersistence.create(
				layoutPageTemplateFragmentPK);

		layoutPageTemplateFragment.setCompanyId(user.getCompanyId());
		layoutPageTemplateFragment.setUserId(user.getUserId());
		layoutPageTemplateFragment.setUserName(user.getFullName());
		layoutPageTemplateFragment.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		layoutPageTemplateFragment.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		layoutPageTemplateFragment.setPosition(position);

		layoutPageTemplateFragmentPersistence.update(
			layoutPageTemplateFragment);

		return layoutPageTemplateFragment;
	}

	@Override
	public LayoutPageTemplateFragment deleteLayoutPageTemplateFragment(
			LayoutPageTemplateFragment layoutPageTemplateFragment)
		throws PortalException {

		// Layout Page Template Fragment

		layoutPageTemplatePersistence.remove(layoutPageTemplateFragment);

		return layoutPageTemplateFragment;
	}

	@Override
	public LayoutPageTemplateFragment deleteLayoutPageTemplateFragment(
			long groupId, long layoutPageTemplateId, long fragmentId)
		throws PortalException {

		LayoutPageTemplateFragmentPK layoutPageTemplateFragmentPK =
			new LayoutPageTemplateFragmentPK(
				groupId, layoutPageTemplateId, fragmentId);

		LayoutPageTemplateFragment layoutPageTemplateFragment =
			layoutPageTemplateFragmentPersistence.fetchByPrimaryKey(
				layoutPageTemplateFragmentPK);

		layoutPageTemplateFragmentLocalService.deleteLayoutPageTemplateFragment(
			layoutPageTemplateFragment);

		return layoutPageTemplateFragment;
	}

	private void _validateFragment(long fragmentId) throws PortalException {
		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(fragmentId);

		if (fragmentEntry == null) {
			throw new NoSuchEntryException();
		}
	}

	private void _validateLayoutPageTemplate(long layoutPageTemplateId)
		throws PortalException {

		LayoutPageTemplate layoutPageTemplate =
			layoutPageTemplateLocalService.fetchLayoutPageTemplate(
				layoutPageTemplateId);

		if (layoutPageTemplate == null) {
			throw new NoSuchPageTemplateException();
		}
	}

	@ServiceReference(type = FragmentEntryLocalService.class)
	private FragmentEntryLocalService _fragmentEntryLocalService;

}