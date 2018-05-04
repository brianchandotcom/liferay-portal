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

package com.liferay.message.boards.demo.internal;

import com.liferay.message.boards.demo.data.creator.MBCategoryDemoDataCreator;
import com.liferay.message.boards.demo.data.creator.MBThreadDemoDataCreator;
import com.liferay.message.boards.demo.data.creator.RootMBCategoryDemoDataCreator;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.users.admin.demo.data.creator.OmniAdminUserDemoDataCreator;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class MBDemo extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		List<Long> userIds = new ArrayList<>();

		for (int i = 0; i < 30; i++) {
			User user = _omniAdminUserDemoDataCreator.create(
				company.getCompanyId());

			userIds.add(user.getUserId());
		}

		Group guestGroup = _groupLocalService.getGroup(
			company.getCompanyId(), "Guest");

		int rootCategoriesCount = 10;

		List<MBCategory> rootCategories = new ArrayList<>();

		for (int i = 0; i < rootCategoriesCount; i++) {
			long userId = _getRandomElement(userIds);

			rootCategories.add(
				_rootMBCategoryDemoDataCreator.create(
					userId, guestGroup.getGroupId()));
		}

		List<MBCategory> firstLevelCategories = new ArrayList<>();

		for (MBCategory rootCategory : rootCategories) {
			int firstLevelCategoriesCount = 5;

			for (int i = 0; i < firstLevelCategoriesCount; i++) {
				long userId = _getRandomElement(userIds);

				firstLevelCategories.add(
					_mbCategoryDemoDataCreator.create(
						userId, rootCategory.getCategoryId()));
			}
		}

		List<MBCategory> secondLevelCategories = new ArrayList<>();

		for (MBCategory firstLevelCategory : firstLevelCategories) {
			int secondLevelCategoriesCount = 3;

			for (int i = 0; i < secondLevelCategoriesCount; i++) {
				long userId = _getRandomElement(userIds);

				secondLevelCategories.add(
					_mbCategoryDemoDataCreator.create(
						userId, firstLevelCategory.getCategoryId()));
			}
		}

		for (MBCategory firstLevelCategory : firstLevelCategories) {
			int firstLevelThreadsCount = RandomUtil.nextInt(2);

			for (int i = 0; i < firstLevelThreadsCount; i++) {
				_mbThreadDemoDataCreator.create(
					userIds, firstLevelCategory.getCategoryId());
			}
		}

		for (MBCategory secondLevelCategory : secondLevelCategories) {
			int secondLevelThreadsCount = RandomUtil.nextInt(40);

			for (int i = 0; i < secondLevelThreadsCount; i++) {
				_mbThreadDemoDataCreator.create(
					userIds, secondLevelCategory.getCategoryId());
			}
		}
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		_mbCategoryDemoDataCreator.delete();
		_omniAdminUserDemoDataCreator.delete();
		_rootMBCategoryDemoDataCreator.delete();
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(target = "(source=liferay)", unbind = "-")
	protected void setLiferayMBCategoryDemoDataCreator(
		MBCategoryDemoDataCreator mbCategoryDemoDataCreator) {

		_mbCategoryDemoDataCreator = mbCategoryDemoDataCreator;
	}

	@Reference(target = "(source=liferay)", unbind = "-")
	protected void setLiferayRootMBCategoryDemoDataCreator(
		RootMBCategoryDemoDataCreator rootMBCategoryDemoDataCreator) {

		_rootMBCategoryDemoDataCreator = rootMBCategoryDemoDataCreator;
	}

	@Reference(target = "(source=lorem-ipsum)", unbind = "-")
	protected void setLoremIpsumMBThreadDemoDataCreator(
		MBThreadDemoDataCreator mbThreadDemoDataCreator) {

		_mbThreadDemoDataCreator = mbThreadDemoDataCreator;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setOmniAdminUserDemoDataCreator(
		OmniAdminUserDemoDataCreator omniAdminUserDemoDataCreator) {

		_omniAdminUserDemoDataCreator = omniAdminUserDemoDataCreator;
	}

	private <T> T _getRandomElement(List<T> list) {
		return list.get(RandomUtil.nextInt(list.size()));
	}

	private GroupLocalService _groupLocalService;
	private MBCategoryDemoDataCreator _mbCategoryDemoDataCreator;
	private MBThreadDemoDataCreator _mbThreadDemoDataCreator;
	private OmniAdminUserDemoDataCreator _omniAdminUserDemoDataCreator;
	private RootMBCategoryDemoDataCreator _rootMBCategoryDemoDataCreator;

}