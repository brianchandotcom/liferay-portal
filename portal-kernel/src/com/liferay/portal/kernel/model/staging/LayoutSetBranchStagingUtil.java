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

package com.liferay.portal.kernel.model.staging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;

/**
 * @author Preston Crary
 */
public class LayoutSetBranchStagingUtil {

	public static LayoutSetBranch getLayoutSetBranch(LayoutSet layoutSet) {
		try {
			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (serviceContext == null) {
				return null;
			}

			long layoutSetBranchId = ParamUtil.getLong(
				serviceContext, "layoutSetBranchId");

			if (layoutSetBranchId > 0) {
				return LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(
					layoutSetBranchId);
			}

			if (serviceContext.isSignedIn()) {
				return LayoutSetBranchLocalServiceUtil.getUserLayoutSetBranch(
					serviceContext.getUserId(), layoutSet.getGroupId(),
					layoutSet.isPrivateLayout(), layoutSet.getLayoutSetId(),
					layoutSetBranchId);
			}

			return LayoutSetBranchLocalServiceUtil.getMasterLayoutSetBranch(
				layoutSet.getGroupId(), layoutSet.isPrivateLayout());
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new IllegalStateException(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetBranchStagingUtil.class);

}