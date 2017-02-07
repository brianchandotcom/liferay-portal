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

package com.liferay.blogs.demo.internal;

import com.liferay.blogs.demo.data.creator.BlogsEntryDemoDataCreator;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.users.admin.demo.data.creator.BasicUserDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.OmniAdminUserDemoDataCreator;
import com.liferay.users.admin.demo.data.creator.SiteAdminUserDemoDataCreator;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class BlogsDemo extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		List<User> users = new ArrayList<>();

		Group guestGroup = _groupLocalService.getGroup(
			company.getCompanyId(), "Guest");

		_basicUserDemoDataCreator.create(
			company.getCompanyId(), "nikki.prudencio@liferay.com");

		_omniAdminUserDemoDataCreator.create(
			company.getCompanyId(), "sergio.gonzalez@liferay.com");

		_siteAdminUserDemoDataCreator.create(
			guestGroup.getGroupId(), "sharon.choi@liferay.com");

		long groupId = guestGroup.getGroupId();

		for (int i = 0; i < 30; i++) {
			users.add(_basicUserDemoDataCreator.create(company.getCompanyId()));
		}

		for (int i = 0; i < 10; i++) {
			long userId = _getRandomElement(users).getUserId();

			BlogsEntryDemoDataCreator blogsEntryDemoDataCreator =
				_getRandomElement(_blogsEntryDemoDataCreators);

			BlogsEntry blogsEntry = blogsEntryDemoDataCreator.create(
				userId, groupId);

			_multipleCommentDemoDataCreator.create(blogsEntry);
		}
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		_multipleCommentDemoDataCreator.delete();

		for (BlogsEntryDemoDataCreator blogsEntryDemoDataCreator :
				_blogsEntryDemoDataCreators) {

			blogsEntryDemoDataCreator.delete();
		}

		_basicUserDemoDataCreator.delete();
		_omniAdminUserDemoDataCreator.delete();
		_siteAdminUserDemoDataCreator.delete();
	}

	@Reference(unbind = "-")
	protected void setBasicUserDemoDataCreator(
		BasicUserDemoDataCreator basicUserDemoDataCreator) {

		_basicUserDemoDataCreator = basicUserDemoDataCreator;
	}

	@Reference(target = "(source=creative-commons)", unbind = "-")
	protected void setCreativeCommonsBlogsEntryDemoDataCreator(
		BlogsEntryDemoDataCreator blogsEntryDemoDataCreator) {

		_blogsEntryDemoDataCreators.add(blogsEntryDemoDataCreator);
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(target = "(source=lorem-ipsum)", unbind = "-")
	protected void setLoremIpsumBlogsEntryDemoDataCreator(
		BlogsEntryDemoDataCreator blogsEntryDemoDataCreator) {

		_blogsEntryDemoDataCreators.add(blogsEntryDemoDataCreator);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setMultipleCommentDemoDataCreator(
		MultipleCommentDemoDataCreator multipleCommentDemoDataCreator) {

		_multipleCommentDemoDataCreator = multipleCommentDemoDataCreator;
	}

	@Reference(unbind = "-")
	protected void setOmniAdminUserDemoDataCreator(
		OmniAdminUserDemoDataCreator omniAdminUserDemoDataCreator) {

		_omniAdminUserDemoDataCreator = omniAdminUserDemoDataCreator;
	}

	@Reference(unbind = "-")
	protected void setSiteAdminUserDemoDataCreator(
		SiteAdminUserDemoDataCreator siteAdminUserDemoDataCreator) {

		_siteAdminUserDemoDataCreator = siteAdminUserDemoDataCreator;
	}

	private <T> T _getRandomElement(List<T> list) {
		return list.get(RandomUtil.nextInt(list.size()));
	}

	private BasicUserDemoDataCreator _basicUserDemoDataCreator;
	private final List<BlogsEntryDemoDataCreator> _blogsEntryDemoDataCreators =
		new ArrayList<>();
	private GroupLocalService _groupLocalService;
	private MultipleCommentDemoDataCreator _multipleCommentDemoDataCreator;
	private OmniAdminUserDemoDataCreator _omniAdminUserDemoDataCreator;
	private SiteAdminUserDemoDataCreator _siteAdminUserDemoDataCreator;

}