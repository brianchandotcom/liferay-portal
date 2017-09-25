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

import com.liferay.layout.page.template.model.LayoutPageTemplateFragment;
import com.liferay.layout.page.template.service.base.LayoutPageTemplateFragmentServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Jürgen Kappler
 */
public class LayoutPageTemplateFragmentServiceImpl
	extends LayoutPageTemplateFragmentServiceBaseImpl {

	@Override
	public LayoutPageTemplateFragment addLayoutPageTemplateFragment(
			LayoutPageTemplateFragment layoutPageTemplateFragment,
			ServiceContext serviceContext)
		throws PortalException {

		return layoutPageTemplateFragmentService.addLayoutPageTemplateFragment(
			layoutPageTemplateFragment.getGroupId(),
			layoutPageTemplateFragment.getLayoutPageTemplateId(),
			layoutPageTemplateFragment.getFragmentEntryId(),
			layoutPageTemplateFragment.getPosition(), serviceContext);
	}

	@Override
	public LayoutPageTemplateFragment addLayoutPageTemplateFragment(
			long groupId, long layoutPageTemplateId, long fragmentId,
			int position, ServiceContext serviceContext)
		throws PortalException {

		return layoutPageTemplateFragmentLocalService.
			addLayoutPageTemplateFragment(
				groupId, getUserId(), layoutPageTemplateId, fragmentId,
				position, serviceContext);
	}

	@Override
	public LayoutPageTemplateFragment deleteLayoutPageTemplateFragment(
			long groupId, long layoutPageTemplateId, long fragmentId,
			ServiceContext serviceContext)
		throws PortalException {

		return layoutPageTemplateFragmentLocalService.
			deleteLayoutPageTemplateFragment(
				groupId, layoutPageTemplateId, fragmentId);
	}

}