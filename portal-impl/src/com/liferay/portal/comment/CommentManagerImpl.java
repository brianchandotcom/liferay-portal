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

import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.comment.Discussion;
import com.liferay.portal.kernel.comment.DiscussionPermission;
import com.liferay.portal.kernel.comment.DiscussionStagingHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.security.permission.PermissionChecker;
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
@OSGiBeanProperties(service = CommentManagerImpl.class)
public class CommentManagerImpl implements CommentManager {

	public CommentManagerImpl() {
		this(new DummyCommentManagerImpl());
	}

	public CommentManagerImpl(CommentManager defaultCommentManager) {
		Registry registry = RegistryUtil.getRegistry();

		Class<?> clazz = getClass();

		Filter filter = registry.getFilter(
			"(&(objectClass=" + CommentManager.class.getName() +
				")(!(objectClass=" + clazz.getName() + ")))");

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();

		_defaultCommentManager = defaultCommentManager;
	}

	@Override
	public long addComment(
			long userId, long groupId, String className, long classPK,
			String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		CommentManager commentManager = getCommentManager();

		return commentManager.addComment(
			userId, groupId, className, classPK, body, serviceContextFunction);
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
	public long addComment(
			long userId, String className, long classPK, String userName,
			long parentCommentId, String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		CommentManager commentManager = getCommentManager();

		return commentManager.addComment(
			userId, className, classPK, userName, parentCommentId, subject,
			body, serviceContextFunction);
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
	public Comment fetchComment(long commentId) {
		CommentManager commentManager = getCommentManager();

		return commentManager.fetchComment(commentId);
	}

	@Override
	public int getCommentsCount(String className, long classPK) {
		CommentManager commentManager = getCommentManager();

		return commentManager.getCommentsCount(className, classPK);
	}

	@Override
	public Discussion getDiscussion(
			long userId, long groupId, String className, long classPK,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		CommentManager commentManager = getCommentManager();

		return commentManager.getDiscussion(
			userId, groupId, className, classPK, serviceContextFunction);
	}

	@Override
	public DiscussionPermission getDiscussionPermission(
		PermissionChecker permissionChecker) {

		CommentManager commentManager = getCommentManager();

		return commentManager.getDiscussionPermission(permissionChecker);
	}

	@Override
	public DiscussionStagingHandler getDiscussionStagingHandler() {
		CommentManager commentManager = getCommentManager();

		return commentManager.getDiscussionStagingHandler();
	}

	@Override
	public boolean hasDiscussion(String className, long classPK)
		throws PortalException {

		CommentManager commentManager = getCommentManager();

		return commentManager.hasDiscussion(className, classPK);
	}

	@Override
	public void subscribeDiscussion(
			long userId, long groupId, String className, long classPK)
		throws PortalException {

		CommentManager commentManager = getCommentManager();

		commentManager.subscribeDiscussion(userId, groupId, className, classPK);
	}

	@Override
	public void unsubscribeDiscussion(
			long userId, String className, long classPK)
		throws PortalException {

		CommentManager commentManager = getCommentManager();

		commentManager.unsubscribeDiscussion(userId, className, classPK);
	}

	@Override
	public long updateComment(
			long userId, String className, long classPK, long commentId,
			String subject, String body,
			Function<String, ServiceContext> serviceContextFunction)
		throws PortalException {

		CommentManager commentManager = getCommentManager();

		return commentManager.updateComment(
			userId, className, classPK, commentId, subject, body,
			serviceContextFunction);
	}

	protected CommentManager getCommentManager() {
		if (_serviceTracker.isEmpty()) {
			return _defaultCommentManager;
		}

		return _serviceTracker.getService();
	}

	private final CommentManager _defaultCommentManager;
	private final ServiceTracker<CommentManager, CommentManager>
		_serviceTracker;

}