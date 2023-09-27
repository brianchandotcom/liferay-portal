/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.demo.internal;

import com.liferay.blogs.demo.data.creator.BlogsEntryDemoDataCreator;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.comment.demo.data.creator.MultipleCommentDemoDataCreator;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
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

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class BlogsDemo extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		BlogsEntryDemoDataCreator randomBlogsEntryDemoDataCreator =
			_getRandomElement(_serviceTrackerList.toList());

		User user1 = _basicUserDemoDataCreator.create(
			company.getCompanyId(), "nikki.prudencio@liferay.com");
		Group group = _groupLocalService.getGroup(
			company.getCompanyId(), "Guest");

		BlogsEntry blogsEntry1 = randomBlogsEntryDemoDataCreator.create(
			user1.getUserId(), group.getGroupId());

		_multipleCommentDemoDataCreator.create(blogsEntry1);

		User user2 = _omniAdminUserDemoDataCreator.create(
			company.getCompanyId(), "sergio.gonzalez@liferay.com");

		BlogsEntry blogsEntry2 = randomBlogsEntryDemoDataCreator.create(
			user2.getUserId(), group.getGroupId());

		_multipleCommentDemoDataCreator.create(blogsEntry2);

		User user3 = _siteAdminUserDemoDataCreator.create(
			group.getGroupId(), "sharon.choi@liferay.com");

		BlogsEntry blogsEntry3 = randomBlogsEntryDemoDataCreator.create(
			user3.getUserId(), group.getGroupId());

		_multipleCommentDemoDataCreator.create(blogsEntry3);

		List<User> users = new ArrayList<>();

		for (int i = 0; i < 30; i++) {
			users.add(_basicUserDemoDataCreator.create(company.getCompanyId()));
		}

		for (int i = 0; i < 10; i++) {
			BlogsEntryDemoDataCreator blogsEntryDemoDataCreator =
				_getRandomElement(_serviceTrackerList.toList());

			User user = _getRandomElement(users);

			BlogsEntry blogsEntry = blogsEntryDemoDataCreator.create(
				user.getUserId(), group.getGroupId());

			_multipleCommentDemoDataCreator.create(blogsEntry);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, BlogsEntryDemoDataCreator.class);
	}

	@Deactivate
	protected void deactivate() throws PortalException {
		for (BlogsEntryDemoDataCreator blogsEntryDemoDataCreator :
				_serviceTrackerList) {

			blogsEntryDemoDataCreator.delete();
		}

		_serviceTrackerList.close();

		_basicUserDemoDataCreator.delete();
		_multipleCommentDemoDataCreator.delete();
		_omniAdminUserDemoDataCreator.delete();
		_siteAdminUserDemoDataCreator.delete();
	}

	private <T> T _getRandomElement(List<T> list) {
		return list.get(RandomUtil.nextInt(list.size()));
	}

	@Reference
	private BasicUserDemoDataCreator _basicUserDemoDataCreator;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private MultipleCommentDemoDataCreator _multipleCommentDemoDataCreator;

	@Reference
	private OmniAdminUserDemoDataCreator _omniAdminUserDemoDataCreator;

	private ServiceTrackerList<BlogsEntryDemoDataCreator> _serviceTrackerList;

	@Reference
	private SiteAdminUserDemoDataCreator _siteAdminUserDemoDataCreator;

}