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

package com.liferay.portal.comment;

import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.service.ServiceContext;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

/**
 * @author André de Oliveira
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class CommentManagerImpl implements CommentManager {

	public CommentManagerImpl() {
		Registry registry = RegistryUtil.getRegistry();

		Class<?> clazz = getClass();

		Filter filter = registry.getFilter(
			"(&(objectClass=" + CommentManager.class.getName() +
				")(!(objectClass=" + clazz.getName() + ")))");

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();
	}

	@Override
	public void addComment(
			long userId, long groupId, String className, long classPK,
			String body, ServiceContext serviceContext)
		throws PortalException {

		CommentManager commentManager = getCommentManager();

		commentManager.addComment(
			userId, groupId, className, classPK, body, serviceContext);
	}

	@Override
	public long addComment(
			long userId, long groupId, String className, long classPK,
			String userName, String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		CommentManager commentManager = getCommentManager();

		return commentManager.addComment(
			userId, groupId, className, classPK, userName, subject, body,
			serviceContextFunction);
	}

	@Override
	public void addDiscussion(
			long userId, long groupId, String className, long classPK,
			String userName)
		throws PortalException {

		CommentManager commentManager = getCommentManager();

		commentManager.addDiscussion(
			userId, groupId, className, classPK, userName);
	}

	@Override
	public void deleteComment(long commentId) throws PortalException {
		CommentManager commentManager = getCommentManager();

		commentManager.deleteComment(commentId);
	}

	@Override
	public void deleteDiscussion(String className, long classPK)
		throws PortalException {

		CommentManager commentManager = getCommentManager();

		commentManager.deleteDiscussion(className, classPK);
	}

	@Override
	public int getCommentsCount(String className, long classPK) {
		CommentManager commentManager = getCommentManager();

		return commentManager.getCommentsCount(className, classPK);
	}

	protected CommentManager getCommentManager() {
		if (_serviceTracker.isEmpty()) {
			return _defaultCommentManager;
		}

		return _serviceTracker.getService();
	}

	protected void setDefaultCommentManager(
		CommentManager defaultCommentManager) {

		_defaultCommentManager = defaultCommentManager;
	}

	private CommentManager _defaultCommentManager =
		new DummyCommentManagerImpl();
	private final ServiceTracker<CommentManager, CommentManager>
		_serviceTracker;

}