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

package com.liferay.layout.internal.service;

import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yang Cao
 */
@Component(service = ServiceWrapper.class)
public class LayoutServiceWrapper
	extends com.liferay.portal.kernel.service.LayoutServiceWrapper {

	@Override
	public Layout publishLayout(long plid) throws Exception {
		Layout layout = _layoutLocalService.getLayout(plid);

		if (!layout.isTypeContent()) {
			throw new UnsupportedOperationException(
				"Only layouts of type content can be published");
		}

		LayoutPermissionUtil.check(
			GuestOrUserUtil.getPermissionChecker(), layout, ActionKeys.UPDATE);

		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class), layout.getPlid());

		LayoutPermissionUtil.check(
			GuestOrUserUtil.getPermissionChecker(), draftLayout,
			ActionKeys.UPDATE);

		layout = _layoutCopyHelper.copyLayout(draftLayout, layout);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		_layoutLocalService.updateStatus(
			draftLayout.getUserId(), draftLayout.getPlid(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);

		return _layoutLocalService.updateStatus(
			layout.getUserId(), layout.getPlid(),
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private Portal _portal;

}