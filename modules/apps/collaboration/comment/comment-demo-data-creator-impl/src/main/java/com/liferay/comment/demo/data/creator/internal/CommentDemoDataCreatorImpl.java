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

package com.liferay.comment.demo.data.creator.internal;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.comment.demo.data.creator.CommentDemoDataCreator;
import com.liferay.message.boards.exception.NoSuchMessageException;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.IdentityServiceContextFunction;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Hernández
 */
@Component(service = CommentDemoDataCreator.class)
public class CommentDemoDataCreatorImpl implements CommentDemoDataCreator {

	@Override
	public Comment create(long userId, ClassedModel classedModel)
		throws PortalException {

		User user = _userLocalService.fetchUser(userId);

		String className = classedModel.getModelClassName();
		Long classPK = (long)classedModel.getPrimaryKeyObj();

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			className, classPK);

		Group group = _groupLocalService.getGroup(assetEntry.getGroupId());

		IdentityServiceContextFunction identityServiceContextFunction =
			new IdentityServiceContextFunction(new ServiceContext());

		long commentId = _commentManager.addComment(
			user.getUserId(), group.getGroupId(), className, classPK,
			user.getFullName(), StringPool.BLANK, _getRandomBody(),
			identityServiceContextFunction);

		return _getComment(commentId);
	}

	@Override
	public Comment create(long userId, long parentCommentId)
		throws PortalException {

		User user = _userLocalService.fetchUser(userId);
		Comment parentComment = _commentManager.fetchComment(parentCommentId);

		IdentityServiceContextFunction identityServiceContextFunction =
			new IdentityServiceContextFunction(new ServiceContext());

		long commentId = _commentManager.addComment(
			userId, parentComment.getClassName(), parentComment.getClassPK(),
			user.getFullName(), parentCommentId, StringPool.BLANK,
			_getRandomBody(), identityServiceContextFunction);

		return _getComment(commentId);
	}

	@Override
	public void delete() throws PortalException {
		for (long commentId : _commentIds) {
			try {
				_commentManager.deleteComment(commentId);
			}
			catch (NoSuchMessageException nsme) {
				if (_log.isWarnEnabled()) {
					_log.warn(nsme, nsme);
				}
			}

			_commentIds.remove(commentId);
		}
	}

	private static List<String> _read(String fileName) {
		String content = StringUtil.read(
			CommentDemoDataCreatorImpl.class,
			"dependencies/" + fileName + ".txt");

		return Arrays.asList(StringUtil.split(content, CharPool.NEW_LINE));
	}

	private Comment _getComment(long commentId) {
		_commentIds.add(commentId);

		return _commentManager.fetchComment(commentId);
	}

	private String _getRandomBody() {
		return _bodies.get(RandomUtil.nextInt(_bodies.size()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommentDemoDataCreatorImpl.class);

	private static final List<String> _bodies = _read("bodies");

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	private final List<Long> _commentIds = new CopyOnWriteArrayList<>();

	@Reference
	private CommentManager _commentManager;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}