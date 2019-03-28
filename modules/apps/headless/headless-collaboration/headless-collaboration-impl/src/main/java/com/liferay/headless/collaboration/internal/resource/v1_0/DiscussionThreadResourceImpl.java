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
import com.liferay.headless.collaboration.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.collaboration.internal.dto.v1_0.util.TaxonomyCategoryUtil;
import com.liferay.headless.collaboration.internal.odata.entity.v1_0.DiscussionForumPostingEntityModel;
import com.liferay.headless.collaboration.resource.v1_0.DiscussionThreadResource;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import java.util.Collections;

import javax.ws.rs.core.MultivaluedMap;

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
	extends BaseDiscussionThreadResourceImpl implements EntityModelResource {

	@Override
	public void deleteDiscussionThread(Long discussionThreadId)
		throws Exception {

		_mbThreadService.deleteThread(discussionThreadId);
	}

	@Override
	public Page<DiscussionThread> getContentSpaceDiscussionThreadsPage(
			Long contentSpaceId, Boolean tree, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter("parentMessageId", "0"),
					BooleanClauseOccur.MUST);

				if (GetterUtil.getBoolean(tree)) {
					booleanFilter.add(
						new TermFilter(Field.CATEGORY_ID, "0"),
						BooleanClauseOccur.MUST);
				}
			},
			filter, MBMessage.class, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			document -> _toDiscussionThread(
				_mbMessageService.getMessage(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public Page<DiscussionThread> getDiscussionSectionDiscussionThreadsPage(
			Long discussionSectionId, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		MBCategory mbCategory = _mbCategoryService.getCategory(
			discussionSectionId);

		return SearchUtil.search(
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						"categoryId",
						String.valueOf(mbCategory.getCategoryId())),
					BooleanClauseOccur.MUST);

				booleanFilter.add(
					new TermFilter("parentMessageId", "0"),
					BooleanClauseOccur.MUST);
			},
			filter, MBMessage.class, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			document -> _toDiscussionThread(
				_mbMessageService.getMessage(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	@Override
	public DiscussionThread getDiscussionThread(Long discussionThreadId)
		throws Exception {

		return _toDiscussionThread(
			_mbThreadLocalService.getMBThread(discussionThreadId));
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
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

		return _toDiscussionThread(
			_mbMessageService.updateDiscussionMessage(
				mbMessage.getClassName(), mbMessage.getClassPK(),
				mbMessage.getMessageId(), discussionThread.getHeadline(),
				discussionThread.getArticleBody(),
				ServiceContextUtil.createServiceContext(
					discussionThread.getKeywords(),
					discussionThread.getTaxonomyCategoryIds(),
					mbThread.getGroupId(),
					discussionThread.getViewableByAsString())));
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

		_updateQuestion(discussionThread, mbMessage);

		return _toDiscussionThread(mbMessage);
	}

	private DiscussionThread _toDiscussionThread(MBMessage mbMessage)
		throws Exception {

		return _toDiscussionThread(mbMessage.getThread());
	}

	private DiscussionThread _toDiscussionThread(MBThread mbThread)
		throws Exception {

		MBMessage mbMessage = _mbMessageService.getMessage(
			mbThread.getRootMessageId());

		return new DiscussionThread() {
			{
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						MBMessage.class.getName(), mbMessage.getMessageId()));
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
						MBMessage.class.getName(), mbMessage.getMessageId()),
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
						MBMessage.class.getName(), mbMessage.getMessageId()),
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

	private void _updateQuestion(
			DiscussionThread discussionThread, MBMessage mbMessage)
		throws PortalException {

		Boolean showAsQuestion = discussionThread.getShowAsQuestion();

		if (showAsQuestion != null) {
			_mbThreadLocalService.updateQuestion(
				mbMessage.getThreadId(), showAsQuestion);

			MBThread mbThread = mbMessage.getThread();

			mbThread.setQuestion(showAsQuestion);
		}
	}

	private static final EntityModel _entityModel =
		new DiscussionForumPostingEntityModel();

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
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Reference
	private UserService _userService;

}