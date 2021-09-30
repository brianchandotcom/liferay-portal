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

package com.liferay.portal.service.impl;

import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.LayoutBranchNameException;
import com.liferay.portal.kernel.exception.NoSuchLayoutBranchException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutBranch;
import com.liferay.portal.kernel.model.LayoutBranchConstants;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.model.LayoutRevisionConstants;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.LayoutRevisionLocalService;
import com.liferay.portal.kernel.service.RecentLayoutBranchLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.persistence.LayoutRevisionPersistence;
import com.liferay.portal.kernel.service.persistence.LayoutSetBranchPersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.base.LayoutBranchLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Julio Camarero
 */
public class LayoutBranchLocalServiceImpl
	extends LayoutBranchLocalServiceBaseImpl {

	@Override
	public LayoutBranch addLayoutBranch(
			long layoutSetBranchId, long plid, String name, String description,
			boolean master, ServiceContext serviceContext)
		throws PortalException {

		// Layout branch

		User user = _userPersistence.findByPrimaryKey(
			serviceContext.getUserId());
		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchPersistence.findByPrimaryKey(layoutSetBranchId);

		validate(0, layoutSetBranchId, plid, name);

		long layoutBranchId = counterLocalService.increment();

		LayoutBranch layoutBranch = layoutBranchPersistence.create(
			layoutBranchId);

		layoutBranch.setGroupId(layoutSetBranch.getGroupId());
		layoutBranch.setCompanyId(user.getCompanyId());
		layoutBranch.setUserId(user.getUserId());
		layoutBranch.setUserName(user.getFullName());
		layoutBranch.setLayoutSetBranchId(layoutSetBranchId);
		layoutBranch.setPlid(plid);
		layoutBranch.setName(name);
		layoutBranch.setDescription(description);
		layoutBranch.setMaster(master);

		layoutBranch = layoutBranchPersistence.update(layoutBranch);

		// Resources

		_resourceLocalService.addResources(
			layoutBranch.getCompanyId(), layoutBranch.getGroupId(),
			layoutBranch.getUserId(), LayoutBranch.class.getName(),
			layoutBranch.getLayoutBranchId(), false, true, false);

		StagingUtil.setRecentLayoutBranchId(
			user, layoutBranch.getLayoutSetBranchId(), layoutBranch.getPlid(),
			layoutBranch.getLayoutBranchId());

		return layoutBranch;
	}

	@Override
	public LayoutBranch addLayoutBranch(
			long layoutRevisionId, String name, String description,
			boolean master, ServiceContext serviceContext)
		throws PortalException {

		LayoutRevision layoutRevision =
			_layoutRevisionPersistence.findByPrimaryKey(layoutRevisionId);

		LayoutBranch layoutBranch = addLayoutBranch(
			layoutRevision.getLayoutSetBranchId(), layoutRevision.getPlid(),
			name, description, master, serviceContext);

		serviceContext.setAttribute("major", Boolean.TRUE.toString());

		_layoutRevisionLocalService.addLayoutRevision(
			layoutBranch.getUserId(), layoutRevision.getLayoutSetBranchId(),
			layoutBranch.getLayoutBranchId(),
			LayoutRevisionConstants.DEFAULT_PARENT_LAYOUT_REVISION_ID, false,
			layoutRevision.getPlid(), layoutRevision.getLayoutRevisionId(),
			layoutRevision.isPrivateLayout(), layoutRevision.getName(),
			layoutRevision.getTitle(), layoutRevision.getDescription(),
			layoutRevision.getKeywords(), layoutRevision.getRobots(),
			layoutRevision.getTypeSettings(), layoutRevision.getIconImage(),
			layoutRevision.getIconImageId(), layoutRevision.getThemeId(),
			layoutRevision.getColorSchemeId(), layoutRevision.getCss(),
			serviceContext);

		return layoutBranch;
	}

	@Override
	public LayoutBranch deleteLayoutBranch(long layoutBranchId)
		throws PortalException {

		LayoutBranch layoutBranch = layoutBranchPersistence.findByPrimaryKey(
			layoutBranchId);

		_layoutRevisionLocalService.deleteLayoutRevisions(
			layoutBranch.getLayoutSetBranchId(), layoutBranchId,
			layoutBranch.getPlid());

		_recentLayoutBranchLocalService.deleteRecentLayoutBranches(
			layoutBranch.getLayoutBranchId());

		return deleteLayoutBranch(layoutBranch);
	}

	@Override
	public void deleteLayoutSetBranchLayoutBranches(long layoutSetBranchId)
		throws PortalException {

		List<LayoutBranch> layoutBranches =
			layoutBranchPersistence.findByLayoutSetBranchId(layoutSetBranchId);

		for (LayoutBranch layoutBranch : layoutBranches) {
			deleteLayoutBranch(layoutBranch.getLayoutBranchId());
		}
	}

	@Override
	public List<LayoutBranch> getLayoutBranches(
		long layoutSetBranchId, long plid, int start, int end,
		OrderByComparator<LayoutBranch> orderByComparator) {

		return layoutBranchPersistence.findByL_P(
			layoutSetBranchId, plid, start, end, orderByComparator);
	}

	@Override
	public List<LayoutBranch> getLayoutSetBranchLayoutBranches(
		long layoutSetBranchId) {

		return layoutBranchPersistence.findByLayoutSetBranchId(
			layoutSetBranchId);
	}

	@Override
	public LayoutBranch getMasterLayoutBranch(long layoutSetBranchId, long plid)
		throws PortalException {

		return layoutBranchPersistence.findByL_P_M_First(
			layoutSetBranchId, plid, true, null);
	}

	@Override
	public LayoutBranch getMasterLayoutBranch(
			long layoutSetBranchId, long plid, ServiceContext serviceContext)
		throws PortalException {

		LayoutBranch layoutBranch = layoutBranchPersistence.fetchByL_P_M_First(
			layoutSetBranchId, plid, true, null);

		if (layoutBranch != null) {
			return layoutBranch;
		}

		return layoutBranchLocalService.addLayoutBranch(
			layoutSetBranchId, plid, LayoutBranchConstants.MASTER_BRANCH_NAME,
			LayoutBranchConstants.MASTER_BRANCH_DESCRIPTION, true,
			serviceContext);
	}

	@Override
	public LayoutBranch updateLayoutBranch(
			long layoutBranchId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutBranch layoutBranch = layoutBranchPersistence.findByPrimaryKey(
			layoutBranchId);

		validate(
			layoutBranch.getLayoutBranchId(),
			layoutBranch.getLayoutSetBranchId(), layoutBranch.getPlid(), name);

		layoutBranch.setName(name);
		layoutBranch.setDescription(description);

		return layoutBranchPersistence.update(layoutBranch);
	}

	protected void validate(
			long layoutBranchId, long layoutSetBranchId, long plid, String name)
		throws PortalException {

		if (Validator.isNull(name) || (name.length() < 4)) {
			throw new LayoutBranchNameException(
				LayoutBranchNameException.TOO_SHORT);
		}

		if (name.length() > 100) {
			throw new LayoutBranchNameException(
				LayoutBranchNameException.TOO_LONG);
		}

		try {
			LayoutBranch layoutBranch = layoutBranchPersistence.findByL_P_N(
				layoutSetBranchId, plid, name);

			if (layoutBranch.getLayoutBranchId() != layoutBranchId) {
				throw new LayoutBranchNameException(
					LayoutBranchNameException.DUPLICATE);
			}
		}
		catch (NoSuchLayoutBranchException noSuchLayoutBranchException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(
					noSuchLayoutBranchException, noSuchLayoutBranchException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutBranchLocalServiceImpl.class);

	@BeanReference(type = LayoutRevisionLocalService.class)
	private LayoutRevisionLocalService _layoutRevisionLocalService;

	@BeanReference(type = LayoutRevisionPersistence.class)
	private LayoutRevisionPersistence _layoutRevisionPersistence;

	@BeanReference(type = LayoutSetBranchPersistence.class)
	private LayoutSetBranchPersistence _layoutSetBranchPersistence;

	@BeanReference(type = RecentLayoutBranchLocalService.class)
	private RecentLayoutBranchLocalService _recentLayoutBranchLocalService;

	@BeanReference(type = ResourceLocalService.class)
	private ResourceLocalService _resourceLocalService;

	@BeanReference(type = UserPersistence.class)
	private UserPersistence _userPersistence;

}