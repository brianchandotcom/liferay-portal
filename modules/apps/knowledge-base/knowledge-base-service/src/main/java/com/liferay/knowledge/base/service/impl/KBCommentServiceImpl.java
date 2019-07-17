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

package com.liferay.knowledge.base.service.impl;

import com.liferay.knowledge.base.constants.KBActionKeys;
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBComment;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.service.base.KBCommentServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=kb",
		"json.web.service.context.path=KBComment"
	},
	service = AopService.class
)
public class KBCommentServiceImpl extends KBCommentServiceBaseImpl {

	@Override
	public KBComment deleteKBComment(KBComment kbComment)
		throws PortalException {

		_kbCommentModelResourcePermission.check(
			getPermissionChecker(), kbComment, KBActionKeys.DELETE);

		return kbCommentLocalService.deleteKBComment(kbComment);
	}

	@Override
	public KBComment deleteKBComment(long kbCommentId) throws PortalException {
		KBComment kbComment = kbCommentPersistence.findByPrimaryKey(
			kbCommentId);

		return deleteKBComment(kbComment);
	}

	@Override
	public KBComment getKBComment(long kbCommentId) throws PortalException {
		_kbCommentModelResourcePermission.check(
			getPermissionChecker(), kbCommentId, KBActionKeys.VIEW);

		return kbCommentLocalService.getKBComment(kbCommentId);
	}

	@Override
	public List<KBComment> getKBComments(
			long groupId, int status, int start, int end)
		throws PortalException {

		if (_portletResourcePermission.contains(
				getPermissionChecker(), groupId,
				KBActionKeys.VIEW_SUGGESTIONS)) {

			return kbCommentLocalService.getKBComments(
				groupId, status, start, end);
		}

		return Collections.emptyList();
	}

	@Override
	public List<KBComment> getKBComments(
			long groupId, int status, int start, int end,
			OrderByComparator<KBComment> obc)
		throws PortalException {

		if (_portletResourcePermission.contains(
				getPermissionChecker(), groupId,
				KBActionKeys.VIEW_SUGGESTIONS)) {

			return kbCommentLocalService.getKBComments(
				groupId, status, start, end, obc);
		}

		return Collections.emptyList();
	}

	@Override
	public List<KBComment> getKBComments(
			long groupId, int start, int end, OrderByComparator<KBComment> obc)
		throws PortalException {

		if (_portletResourcePermission.contains(
				getPermissionChecker(), groupId,
				KBActionKeys.VIEW_SUGGESTIONS)) {

			return kbCommentLocalService.getKBComments(
				groupId, start, end, obc);
		}

		return Collections.emptyList();
	}

	@Override
	public List<KBComment> getKBComments(
			long groupId, String className, long classPK, int status, int start,
			int end)
		throws PortalException {

		if (_containsViewSuggestionPermission(
				getPermissionChecker(), groupId, className, classPK)) {

			return kbCommentLocalService.getKBComments(
				className, classPK, status, start, end);
		}

		return Collections.emptyList();
	}

	@Override
	public List<KBComment> getKBComments(
			long groupId, String className, long classPK, int status, int start,
			int end, OrderByComparator<KBComment> obc)
		throws PortalException {

		if (_containsViewSuggestionPermission(
				getPermissionChecker(), groupId, className, classPK)) {

			return kbCommentLocalService.getKBComments(
				className, classPK, status, start, end, obc);
		}

		return Collections.emptyList();
	}

	@Override
	public List<KBComment> getKBComments(
			long groupId, String className, long classPK, int start, int end,
			OrderByComparator<KBComment> obc)
		throws PortalException {

		if (_containsViewSuggestionPermission(
				getPermissionChecker(), groupId, className, classPK)) {

			return kbCommentLocalService.getKBComments(
				className, classPK, start, end, obc);
		}

		return Collections.emptyList();
	}

	@Override
	public int getKBCommentsCount(long groupId) throws PortalException {
		if (_portletResourcePermission.contains(
				getPermissionChecker(), groupId,
				KBActionKeys.VIEW_SUGGESTIONS)) {

			return kbCommentPersistence.countByGroupId(groupId);
		}

		return 0;
	}

	@Override
	public int getKBCommentsCount(long groupId, int status)
		throws PortalException {

		if (_portletResourcePermission.contains(
				getPermissionChecker(), groupId,
				KBActionKeys.VIEW_SUGGESTIONS)) {

			return kbCommentLocalService.getKBCommentsCount(groupId, status);
		}

		return 0;
	}

	@Override
	public int getKBCommentsCount(long groupId, String className, long classPK)
		throws PortalException {

		if (_containsViewSuggestionPermission(
				getPermissionChecker(), groupId, className, classPK)) {

			return kbCommentLocalService.getKBCommentsCount(className, classPK);
		}

		return 0;
	}

	@Override
	public int getKBCommentsCount(
			long groupId, String className, long classPK, int status)
		throws PortalException {

		if (_containsViewSuggestionPermission(
				getPermissionChecker(), groupId, className, classPK)) {

			return kbCommentLocalService.getKBCommentsCount(
				className, classPK, status);
		}

		return 0;
	}

	@Override
	public KBComment updateKBComment(
			long kbCommentId, long classNameId, long classPK, String content,
			int status, ServiceContext serviceContext)
		throws PortalException {

		_kbCommentModelResourcePermission.check(
			getPermissionChecker(), kbCommentId, KBActionKeys.UPDATE);

		return kbCommentLocalService.updateKBComment(
			kbCommentId, classNameId, classPK, content, status, serviceContext);
	}

	@Override
	public KBComment updateKBComment(
			long kbCommentId, long classNameId, long classPK, String content,
			ServiceContext serviceContext)
		throws PortalException {

		KBComment kbComment = kbCommentPersistence.findByPrimaryKey(
			kbCommentId);

		return updateKBComment(
			kbCommentId, classNameId, classPK, content, kbComment.getStatus(),
			serviceContext);
	}

	@Override
	public KBComment updateStatus(
			long kbCommentId, int status, ServiceContext serviceContext)
		throws PortalException {

		_kbCommentModelResourcePermission.check(
			getPermissionChecker(), kbCommentId, KBActionKeys.UPDATE);

		return kbCommentLocalService.updateStatus(
			getUserId(), kbCommentId, status, serviceContext);
	}

	private boolean _containsViewSuggestionPermission(
			PermissionChecker permissionChecker, long groupId, String className,
			long classPK)
		throws PortalException {

		if (!className.equals(KBArticleConstants.getClassName())) {
			throw new IllegalArgumentException(
				"Only KB articles support suggestions");
		}

		KBArticle kbArticle = _kbArticleLocalService.fetchKBArticle(classPK);

		if (kbArticle != null) {
			kbArticle = _kbArticleLocalService.getLatestKBArticle(
				kbArticle.getResourcePrimKey(), WorkflowConstants.STATUS_ANY);
		}
		else {
			kbArticle = _kbArticleLocalService.getLatestKBArticle(
				classPK, WorkflowConstants.STATUS_ANY);
		}

		if (_portletResourcePermission.contains(
				permissionChecker, groupId, KBActionKeys.VIEW_SUGGESTIONS) ||
			_kbArticleModelResourcePermission.contains(
				permissionChecker, kbArticle, KBActionKeys.UPDATE)) {

			return true;
		}

		return false;
	}

	@Reference
	private KBArticleLocalService _kbArticleLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.knowledge.base.model.KBArticle)"
	)
	private ModelResourcePermission<KBArticle>
		_kbArticleModelResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.knowledge.base.model.KBComment)"
	)
	private ModelResourcePermission<KBComment>
		_kbCommentModelResourcePermission;

	@Reference(
		target = "(resource.name=" + KBConstants.RESOURCE_NAME_ADMIN + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}