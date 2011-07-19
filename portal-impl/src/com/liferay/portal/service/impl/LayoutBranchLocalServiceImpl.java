/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.LayoutRevision;
import com.liferay.portal.model.LayoutRevisionConstants;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.LayoutBranchLocalServiceBaseImpl;

/**
 * The implementation of the layout branch local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portal.service.LayoutBranchLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.base.LayoutBranchLocalServiceBaseImpl
 * @see com.liferay.portal.service.LayoutBranchLocalServiceUtil
 */
public class LayoutBranchLocalServiceImpl
	extends LayoutBranchLocalServiceBaseImpl {

		public LayoutBranch addLayoutBranch(
			long layoutSetBranchId, long plid, String name,
			String description, boolean master, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userLocalService.getUserById(serviceContext.getUserId());
		LayoutSetBranch layoutSetBranch =
			layoutSetBranchPersistence.findByPrimaryKey(layoutSetBranchId);

		long layoutBranchId = counterLocalService.increment();

		LayoutBranch layoutBranch = layoutBranchPersistence.create(
			layoutBranchId);

		layoutBranch.setGroupId(layoutSetBranch.getGroupId());
		layoutBranch.setCompanyId(user.getCompanyId());
		layoutBranch.setUserId(user.getUserId());
		layoutBranch.setLayoutSetBranchId(layoutSetBranchId);
		layoutBranch.setPlid(plid);
		layoutBranch.setName(name);
		layoutBranch.setDescription(description);
		layoutBranch.setMaster(master);

		layoutBranchPersistence.update(layoutBranch, false);

		return layoutBranch;
	}

	public LayoutBranch addLayoutBranch(
			String name, String description, boolean master,
			LayoutRevision layoutRevision, ServiceContext serviceContext)
		throws PortalException, SystemException {

		LayoutBranch layoutBranch = addLayoutBranch(
			layoutRevision.getLayoutSetBranchId(), layoutRevision.getPlid(),
			name, description, master, serviceContext);

		if (layoutRevision != null) {
			layoutRevisionService.addLayoutRevision(
				layoutBranch.getUserId(), layoutRevision.getLayoutSetBranchId(),
				layoutBranch.getLayoutBranchId(),
				LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID,
				false, layoutRevision.getPlid(),
				layoutRevision.isPrivateLayout(), layoutRevision.getName(),
				layoutRevision.getTitle(), layoutRevision.getDescription(),
				layoutRevision.getKeywords(), layoutRevision.getRobots(),
				layoutRevision.getTypeSettings(), layoutRevision.getIconImage(),
				layoutRevision.getIconImageId(), layoutRevision.getThemeId(),
				layoutRevision.getColorSchemeId(),
				layoutRevision.getWapThemeId(),
				layoutRevision.getWapColorSchemeId(), layoutRevision.getCss(),
				serviceContext);
		}

		return layoutBranch;
	}

	@Override
	public LayoutBranch getLayoutBranch(long layoutBranchId)
		throws PortalException, SystemException {

		return layoutBranchPersistence.findByPrimaryKey(layoutBranchId);
	}

	public LayoutBranch getMasterLayoutBranch(long layoutSetBranchId, long plid)
		throws PortalException, SystemException {

		return layoutBranchFinder.findByMaster(layoutSetBranchId, plid);
	}

}