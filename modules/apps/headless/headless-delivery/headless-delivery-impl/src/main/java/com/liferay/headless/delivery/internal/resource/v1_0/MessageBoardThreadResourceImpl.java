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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.headless.common.spi.resource.SPIRatingResource;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardThread;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.internal.dto.v1_0.util.AggregateRatingUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CreatorUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RatingUtil;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.MessageBoardMessageEntityModel;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardThreadResource;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.constants.MBThreadConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.message.boards.service.MBThreadService;
import com.liferay.message.boards.settings.MBGroupServiceSettings;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.UserLocalService;
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
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;
import com.liferay.ratings.kernel.service.RatingsStatsLocalService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/message-board-thread.properties",
	scope = ServiceScope.PROTOTYPE, service = MessageBoardThreadResource.class
)
public class MessageBoardThreadResourceImpl
	extends BaseMessageBoardThreadResourceImpl implements EntityModelResource {

	@Override
	public void deleteMessageBoardThread(Long messageBoardThreadId)
		throws Exception {

		_mbThreadService.deleteThread(messageBoardThreadId);
	}

	@Override
	public void deleteMessageBoardThreadMyRating(Long messageBoardThreadId)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		MBThread mbThread = _mbThreadLocalService.getMBThread(
			messageBoardThreadId);

		spiRatingResource.deleteRating(mbThread.getRootMessageId());
	}

	@Override
	public Page<MessageBoardThread> getContentSpaceMessageBoardThreadsPage(
			Long contentSpaceId, Boolean flatten, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getContentSpaceMessageBoardThreadsPage(
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				if (!GetterUtil.getBoolean(flatten)) {
					booleanFilter.add(
						new TermFilter(Field.CATEGORY_ID, "0"),
						BooleanClauseOccur.MUST);
				}

				booleanFilter.add(
					new TermFilter("parentMessageId", "0"),
					BooleanClauseOccur.MUST);
			},
			contentSpaceId, search, filter, pagination, sorts);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Page<MessageBoardThread>
			getMessageBoardSectionMessageBoardThreadsPage(
				Long messageBoardSectionId, String search, Filter filter,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		MBCategory mbCategory = _mbCategoryService.getCategory(
			messageBoardSectionId);

		return _getContentSpaceMessageBoardThreadsPage(
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
			mbCategory.getGroupId(), search, filter, pagination, sorts);
	}

	@Override
	public MessageBoardThread getMessageBoardThread(Long messageBoardThreadId)
		throws Exception {

		return _toMessageBoardThread(
			_mbThreadLocalService.getMBThread(messageBoardThreadId));
	}

	@Override
	public Rating getMessageBoardThreadMyRating(Long messageBoardThreadId)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		MBThread mbThread = _mbThreadLocalService.getMBThread(
			messageBoardThreadId);

		return spiRatingResource.getRating(mbThread.getRootMessageId());
	}

	@Override
	public MessageBoardThread postContentSpaceMessageBoardThread(
			Long contentSpaceId, MessageBoardThread messageBoardThread)
		throws Exception {

		return _addMessageBoardThread(contentSpaceId, 0L, messageBoardThread);
	}

	@Override
	public MessageBoardThread postMessageBoardSectionMessageBoardThread(
			Long messageBoardSectionId, MessageBoardThread messageBoardThread)
		throws Exception {

		MBCategory mbCategory = _mbCategoryService.getCategory(
			messageBoardSectionId);

		return _addMessageBoardThread(
			mbCategory.getGroupId(), messageBoardSectionId, messageBoardThread);
	}

	@Override
	public Rating postMessageBoardThreadMyRating(
			Long messageBoardThreadId, Rating rating)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		MBThread mbThread = _mbThreadLocalService.getThread(
			messageBoardThreadId);

		return spiRatingResource.addOrUpdateRating(
			rating.getRatingValue(), mbThread.getRootMessageId());
	}

	@Override
	public MessageBoardThread putMessageBoardThread(
			Long messageBoardThreadId, MessageBoardThread messageBoardThread)
		throws Exception {

		MBThread mbThread = _mbThreadLocalService.getMBThread(
			messageBoardThreadId);

		MBMessage mbMessage = _mbMessageService.getMessage(
			mbThread.getRootMessageId());

		_updateQuestion(mbMessage, messageBoardThread);

		return _toMessageBoardThread(
			_mbMessageService.updateMessage(
				mbThread.getRootMessageId(), messageBoardThread.getHeadline(),
				messageBoardThread.getArticleBody(), null,
				_toPriority(
					mbThread.getGroupId(), messageBoardThread.getThreadType()),
				false,
				ServiceContextUtil.createServiceContext(
					Optional.ofNullable(
						messageBoardThread.getKeywords()
					).orElse(
						new String[0]
					),
					null, mbThread.getGroupId(),
					messageBoardThread.getViewableByAsString())));
	}

	@Override
	public Rating putMessageBoardThreadMyRating(
			Long messageBoardThreadId, Rating rating)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		MBThread mbThread = _mbThreadLocalService.getThread(
			messageBoardThreadId);

		return spiRatingResource.addOrUpdateRating(
			rating.getRatingValue(), mbThread.getRootMessageId());
	}

	private MessageBoardThread _addMessageBoardThread(
			Long contentSpaceId, Long messageBoardSectionId,
			MessageBoardThread messageBoardThread)
		throws Exception {

		MBMessage mbMessage = _mbMessageService.addMessage(
			contentSpaceId, messageBoardSectionId,
			messageBoardThread.getHeadline(),
			messageBoardThread.getArticleBody(),
			MBMessageConstants.DEFAULT_FORMAT, Collections.emptyList(), false,
			_toPriority(contentSpaceId, messageBoardThread.getThreadType()),
			false,
			ServiceContextUtil.createServiceContext(
				messageBoardThread.getKeywords(), null, contentSpaceId,
				messageBoardThread.getViewableByAsString()));

		_updateQuestion(mbMessage, messageBoardThread);

		return _toMessageBoardThread(mbMessage);
	}

	private Page<MessageBoardThread> _getContentSpaceMessageBoardThreadsPage(
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			Long contentSpaceId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQueryUnsafeConsumer, filter, MBMessage.class, search,
			pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
				searchContext.setGroupIds(new long[] {contentSpaceId});
			},
			document -> _toMessageBoardThread(
				_mbMessageService.getMessage(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	private SPIRatingResource<Rating> _getSPIRatingResource() {
		return new SPIRatingResource<>(
			MBMessage.class.getName(), _ratingsEntryLocalService,
			ratingsEntry -> RatingUtil.toRating(
				_portal, ratingsEntry, _userLocalService),
			_user);
	}

	private MessageBoardThread _toMessageBoardThread(MBMessage mbMessage)
		throws Exception {

		return _toMessageBoardThread(mbMessage.getThread());
	}

	private MessageBoardThread _toMessageBoardThread(MBThread mbThread)
		throws Exception {

		MBMessage mbMessage = _mbMessageService.getMessage(
			mbThread.getRootMessageId());

		return new MessageBoardThread() {
			{
				aggregateRating = AggregateRatingUtil.toAggregateRating(
					_ratingsStatsLocalService.fetchStats(
						MBMessage.class.getName(), mbMessage.getMessageId()));
				articleBody = mbMessage.getBody();
				contentSpaceId = mbThread.getGroupId();
				creator = CreatorUtil.toCreator(
					_portal, _userService.getUserById(mbThread.getUserId()));
				dateCreated = mbMessage.getCreateDate();
				dateModified = mbMessage.getModifiedDate();
				encodingFormat = mbMessage.getFormat();
				headline = mbMessage.getSubject();
				id = mbThread.getThreadId();
				keywords = ListUtil.toArray(
					_assetTagLocalService.getTags(
						MBMessage.class.getName(), mbMessage.getMessageId()),
					AssetTag.NAME_ACCESSOR);
				numberOfMessageBoardAttachments =
					mbMessage.getAttachmentsFileEntriesCount();
				numberOfMessageBoardMessages =
					_mbMessageLocalService.getChildMessagesCount(
						mbMessage.getMessageId(),
						WorkflowConstants.STATUS_APPROVED);
				showAsQuestion = mbThread.isQuestion();
				threadType = _toThreadType(
					mbThread.getGroupId(), mbThread.getPriority());
			}
		};
	}

	private double _toPriority(Long contentSpaceId, String threadType)
		throws Exception {

		if (threadType == null) {
			return MBThreadConstants.PRIORITY_NOT_GIVEN;
		}

		MBGroupServiceSettings mbGroupServiceSettings =
			MBGroupServiceSettings.getInstance(contentSpaceId);

		String[] priorities = mbGroupServiceSettings.getPriorities(
			contextAcceptLanguage.getPreferredLanguageId());

		for (String priorityString : priorities) {
			String[] parts = StringUtil.split(priorityString, StringPool.PIPE);

			if (StringUtil.equalsIgnoreCase(parts[0], threadType)) {
				return GetterUtil.getDouble(parts[2]);
			}
		}

		throw new BadRequestException(
			StringBundler.concat(
				"Thread type \"", threadType, "\" is not available in ",
				Arrays.toString(
					transform(
						priorities,
						priority -> {
							String[] parts = StringUtil.split(
								priority, StringPool.PIPE);

							return parts[0];
						},
						String.class))));
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
			MBMessage mbMessage, MessageBoardThread messageBoardThread)
		throws Exception {

		Boolean showAsQuestion = messageBoardThread.getShowAsQuestion();

		if (showAsQuestion != null) {
			_mbThreadLocalService.updateQuestion(
				mbMessage.getThreadId(), showAsQuestion);

			MBThread mbThread = mbMessage.getThread();

			mbThread.setQuestion(showAsQuestion);
		}
	}

	private static final EntityModel _entityModel =
		new MessageBoardMessageEntityModel();

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
	private RatingsEntryLocalService _ratingsEntryLocalService;

	@Reference
	private RatingsStatsLocalService _ratingsStatsLocalService;

	@Context
	private User _user;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserService _userService;

}