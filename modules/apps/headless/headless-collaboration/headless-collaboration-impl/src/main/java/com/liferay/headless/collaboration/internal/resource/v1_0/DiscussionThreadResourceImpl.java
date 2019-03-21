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

package com.liferay.headless.collaboration.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.headless.collaboration.dto.v1_0.DiscussionThread;
import com.liferay.headless.collaboration.dto.v1_0.TaxonomyCategory;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.TaxonomyCategoryUtil;
import com.liferay.headless.collaboration.resource.v1_0.DiscussionThreadResource;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.message.boards.service.MBThreadService;
import com.liferay.message.boards.settings.MBGroupServiceSettings;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/discussion-thread.properties",
	scope = ServiceScope.PROTOTYPE, service = DiscussionThreadResource.class
)
public class DiscussionThreadResourceImpl
	extends BaseDiscussionThreadResourceImpl {

	@Override
	public boolean deleteDiscussionThread(Long discussionThreadId)
		throws Exception {

		_mbThreadService.deleteThread(discussionThreadId);

		return true;
	}

	@Override
	public Page<DiscussionThread> getContentSpaceDiscussionThreadsPage(
			Long contentSpaceId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return Page.of(
			transform(
				_mbThreadService.getThreads(
					contentSpaceId, 0, WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toDiscussionThread),
			pagination,
			_mbMessageLocalService.getGroupMessagesCount(
				contentSpaceId, WorkflowConstants.STATUS_APPROVED));
	}

	@Override
	public Page<DiscussionThread> getDiscussionSectionDiscussionThreadsPage(
			Long discussionSectionId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		MBCategory mbCategory = _mbCategoryService.getCategory(
			discussionSectionId);

		return Page.of(
			transform(
				_mbThreadService.getThreads(
					mbCategory.getGroupId(), discussionSectionId,
					WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition()),
				this::_toDiscussionThread),
			pagination,
			_mbThreadService.getThreadsCount(
				mbCategory.getGroupId(), discussionSectionId,
				WorkflowConstants.STATUS_APPROVED));
	}

	@Override
	public DiscussionThread getDiscussionThread(Long discussionThreadId)
		throws Exception {

		return _toDiscussionThread(
			_mbThreadLocalService.getMBThread(discussionThreadId));
	}

	@Override
	public DiscussionThread postContentSpaceDiscussionThread(
			Long contentSpaceId, DiscussionThread discussionThread)
		throws Exception {

		return _addDiscussionThread(contentSpaceId, 0L, discussionThread);
	}

	@Override
	public DiscussionThread postDiscussionSectionDiscussionThread(
			Long discussionSectionId, DiscussionThread discussionThread)
		throws Exception {

		MBCategory mbCategory = _mbCategoryService.getCategory(
			discussionSectionId);

		return _addDiscussionThread(
			mbCategory.getGroupId(), discussionSectionId, discussionThread);
	}

	@Override
	public DiscussionThread putDiscussionThread(
			Long discussionThreadId, DiscussionThread discussionThread)
		throws Exception {

		MBThread mbThread = _mbThreadLocalService.getMBThread(
			discussionThreadId);

		MBMessage mbMessage = _mbMessageService.getMessage(
			mbThread.getRootMessageId());

		MBMessage updatedMBMessage = _mbMessageService.updateDiscussionMessage(
			mbMessage.getClassName(), mbMessage.getClassPK(),
			mbMessage.getMessageId(), discussionThread.getHeadline(),
			discussionThread.getArticleBody(),
			ServiceContextUtil.createServiceContext(
				discussionThread.getKeywords(),
				discussionThread.getTaxonomyCategoryIds(),
				mbThread.getGroupId(),
				discussionThread.getViewableByAsString()));

		return _toDiscussionThread(updatedMBMessage.getThread());
	}

	private DiscussionThread _addDiscussionThread(
			Long contentSpaceId, Long discussionSectionId,
			DiscussionThread discussionThread)
		throws Exception {

		MBMessage mbMessage = _mbMessageService.addMessage(
			contentSpaceId, discussionSectionId, discussionThread.getHeadline(),
			discussionThread.getArticleBody(),
			MBMessageConstants.DEFAULT_FORMAT, Collections.emptyList(), false,
			0.0, false,
			ServiceContextUtil.createServiceContext(
				discussionThread.getKeywords(),
				discussionThread.getTaxonomyCategoryIds(), contentSpaceId,
				discussionThread.getViewableByAsString()));

		return _toDiscussionThread(mbMessage.getThread());
	}

	private DiscussionThread _toDiscussionThread(MBThread mbThread)
		throws Exception {

		MBMessage mbMessage = _mbMessageService.getMessage(
			mbThread.getRootMessageId());

		return new DiscussionThread() {
			{
				articleBody = mbMessage.getBody();
				contentSpaceId = mbThread.getGroupId();
				creator = CreatorUtil.toCreator(
					_portal, _userService.getUserById(mbThread.getUserId()));
				dateCreated = mbThread.getCreateDate();
				dateModified = mbThread.getModifiedDate();
				headline = mbMessage.getSubject();
				id = mbThread.getThreadId();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						KBArticle.class.getName(), mbMessage.getClassPK()),
					AssetTag.NAME_ACCESSOR);
				numberOfDiscussionAttachments =
					mbMessage.getAttachmentsFileEntriesCount();
				numberOfDiscussionForumPostings =
					_mbMessageLocalService.getChildMessagesCount(
						mbMessage.getMessageId(),
						WorkflowConstants.STATUS_APPROVED);
				showAsQuestion = mbThread.isQuestion();
				taxonomyCategories = transformToArray(
					_assetCategoryLocalService.getCategories(
						MBMessage.class.getName(), mbMessage.getClassPK()),
					TaxonomyCategoryUtil::toTaxonomyCategory,
					TaxonomyCategory.class);
				threadType = _toThreadType(
					mbThread.getGroupId(), mbThread.getPriority());
			}
		};
	}

	private String _toThreadType(Long contentSpaceId, double priority)
		throws Exception {

		MBGroupServiceSettings mbGroupServiceSettings =
			MBGroupServiceSettings.getInstance(contentSpaceId);

		String[] priorities = mbGroupServiceSettings.getPriorities(
			contextAcceptLanguage.getPreferredLanguageId());

		for (String priorityString : priorities) {
			String[] parts = StringUtil.split(priorityString, StringPool.PIPE);

			if (priority == GetterUtil.getDouble(parts[2])) {
				return parts[0];
			}
		}

		return null;
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private MBCategoryService _mbCategoryService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBMessageService _mbMessageService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private MBThreadService _mbThreadService;

	@Reference
	private Portal _portal;

	@Reference
	private UserService _userService;

}