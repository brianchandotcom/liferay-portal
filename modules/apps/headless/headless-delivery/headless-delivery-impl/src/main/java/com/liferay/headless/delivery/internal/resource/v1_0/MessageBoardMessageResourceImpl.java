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

import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.headless.common.spi.resource.SPIRatingResource;
import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardMessage;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.MessageBoardMessageDTOConverter;
import com.liferay.headless.delivery.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.EntityFieldsUtil;
import com.liferay.headless.delivery.internal.dto.v1_0.util.RatingUtil;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.MessageBoardMessageEntityModel;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardMessageResource;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/message-board-message.properties",
	scope = ServiceScope.PROTOTYPE, service = MessageBoardMessageResource.class
)
public class MessageBoardMessageResourceImpl
	extends BaseMessageBoardMessageResourceImpl implements EntityModelResource {

	@Override
	public void deleteMessageBoardMessage(Long messageBoardMessageId)
		throws Exception {

		_mbMessageService.deleteMessage(messageBoardMessageId);
	}

	public void deleteMessageBoardMessageMyRating(Long messageBoardMessageId)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		spiRatingResource.deleteRating(messageBoardMessageId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return new MessageBoardMessageEntityModel(
			EntityFieldsUtil.getEntityFields(
				_portal.getClassNameId(MBMessage.class.getName()),
				contextCompany.getCompanyId(), _expandoColumnLocalService,
				_expandoTableLocalService));
	}

	@Override
	public MessageBoardMessage getMessageBoardMessage(
			Long messageBoardMessageId)
		throws Exception {

		return _toMessageBoardMessage(
			_mbMessageService.getMessage(messageBoardMessageId));
	}

	@Override
	public Page<MessageBoardMessage>
			getMessageBoardMessageMessageBoardMessagesPage(
				Long parentMessageBoardMessageId, Boolean flatten,
				String search, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		MBMessage mbMessage = _mbMessageService.getMessage(
			parentMessageBoardMessageId);

		return _getMessageBoardMessagesPage(
			_getMessageBoardListActions(mbMessage), parentMessageBoardMessageId,
			search, filter, pagination, sorts, flatten, null);
	}

	@Override
	public Rating getMessageBoardMessageMyRating(Long messageBoardMessageId)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.getRating(messageBoardMessageId);
	}

	@Override
	public Page<MessageBoardMessage>
			getMessageBoardThreadMessageBoardMessagesPage(
				Long messageBoardThreadId, String search, Filter filter,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		MBThread mbThread = _mbThreadLocalService.getMBThread(
			messageBoardThreadId);

		return _getMessageBoardMessagesPage(
			_getMessageBoardThreadListActions(mbThread),
			mbThread.getRootMessageId(), search, filter, pagination, sorts,
			false, null);
	}

	@Override
	public Page<MessageBoardMessage> getSiteMessageBoardMessagesPage(
			Long siteId, Boolean flatten, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getMessageBoardMessagesPage(
			_getSiteListActions(siteId), null, search, filter, pagination,
			sorts, flatten, siteId);
	}

	@Override
	public MessageBoardMessage postMessageBoardMessageMessageBoardMessage(
			Long parentMessageBoardMessageId,
			MessageBoardMessage messageBoardMessage)
		throws Exception {

		return _addMessageBoardThread(
			parentMessageBoardMessageId, messageBoardMessage);
	}

	@Override
	public Rating postMessageBoardMessageMyRating(
			Long messageBoardMessageId, Rating rating)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.addOrUpdateRating(
			rating.getRatingValue(), messageBoardMessageId);
	}

	@Override
	public MessageBoardMessage postMessageBoardThreadMessageBoardMessage(
			Long messageBoardThreadId, MessageBoardMessage messageBoardMessage)
		throws Exception {

		MBThread mbThread = _mbThreadLocalService.getMBThread(
			messageBoardThreadId);

		return _addMessageBoardThread(
			mbThread.getRootMessageId(), messageBoardMessage);
	}

	@Override
	public MessageBoardMessage putMessageBoardMessage(
			Long messageBoardMessageId, MessageBoardMessage messageBoardMessage)
		throws Exception {

		if ((messageBoardMessage.getArticleBody() == null) &&
			(messageBoardMessage.getHeadline() == null)) {

			throw new BadRequestException(
				"Headline and article body are both null");
		}

		MBMessage mbMessage = _mbMessageService.getMessage(
			messageBoardMessageId);

		String headline = messageBoardMessage.getHeadline();

		if (headline == null) {
			MBMessage parentMBMessage = _mbMessageService.getMessage(
				mbMessage.getParentMessageId());

			headline =
				MBMessageConstants.MESSAGE_SUBJECT_PREFIX_RE +
					parentMBMessage.getSubject();
		}

		mbMessage = _mbMessageService.updateDiscussionMessage(
			mbMessage.getClassName(), mbMessage.getClassPK(),
			messageBoardMessageId, headline,
			messageBoardMessage.getArticleBody(),
			ServiceContextUtil.createServiceContext(
				_getExpandoBridgeAttributes(messageBoardMessage),
				mbMessage.getGroupId(),
				messageBoardMessage.getViewableByAsString()));

		_updateAnswer(mbMessage, messageBoardMessage);

		return _toMessageBoardMessage(mbMessage);
	}

	@Override
	public Rating putMessageBoardMessageMyRating(
			Long messageBoardMessageId, Rating rating)
		throws Exception {

		SPIRatingResource<Rating> spiRatingResource = _getSPIRatingResource();

		return spiRatingResource.addOrUpdateRating(
			rating.getRatingValue(), messageBoardMessageId);
	}

	@Override
	public void putMessageBoardMessageSubscribe(Long messageBoardMessageId)
		throws Exception {

		_mbMessageService.subscribeMessage(messageBoardMessageId);
	}

	@Override
	public void putMessageBoardMessageUnsubscribe(Long messageBoardMessageId)
		throws Exception {

		_mbMessageService.unsubscribeMessage(messageBoardMessageId);
	}

	private MessageBoardMessage _addMessageBoardThread(
			Long messageBoardMessageId, MessageBoardMessage messageBoardMessage)
		throws Exception {

		MBMessage parentMBMessage = _mbMessageService.getMessage(
			messageBoardMessageId);

		String headline = messageBoardMessage.getHeadline();

		if (headline == null) {
			headline =
				MBMessageConstants.MESSAGE_SUBJECT_PREFIX_RE +
					parentMBMessage.getSubject();
		}

		MBMessage mbMessage = _mbMessageService.addMessage(
			messageBoardMessageId, headline,
			messageBoardMessage.getArticleBody(),
			MBMessageConstants.DEFAULT_FORMAT, Collections.emptyList(),
			GetterUtil.getBoolean(messageBoardMessage.getAnonymous()), 0.0,
			false,
			ServiceContextUtil.createServiceContext(
				_getExpandoBridgeAttributes(messageBoardMessage),
				parentMBMessage.getGroupId(),
				messageBoardMessage.getViewableByAsString()));

		_updateAnswer(mbMessage, messageBoardMessage);

		return _toMessageBoardMessage(mbMessage);
	}

	private Map<String, Map<String, String>> _getActions(
		GroupedModel groupedModel) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction("DELETE", groupedModel, "deleteMessageBoardMessage")
		).put(
			"get", addAction("VIEW", groupedModel, "getMessageBoardMessage")
		).put(
			"replace",
			addAction("UPDATE", groupedModel, "putMessageBoardMessage")
		).put(
			"reply-to-message",
			addAction(
				"REPLY_TO_MESSAGE",
				"postMessageBoardMessageMessageBoardMessage",
				"com.liferay.message.boards", groupedModel.getGroupId())
		).put(
			"subscribe",
			addAction(
				"SUBSCRIBE", groupedModel, "putMessageBoardMessageSubscribe")
		).put(
			"unsubscribe",
			addAction(
				"SUBSCRIBE", groupedModel, "putMessageBoardMessageSubscribe")
		).put(
			"update",
			addAction("UPDATE", groupedModel, "patchMessageBoardMessage")
		).build();
	}

	private Map<String, Serializable> _getExpandoBridgeAttributes(
		MessageBoardMessage messageBoardMessage) {

		return CustomFieldsUtil.toMap(
			MBMessage.class.getName(), contextCompany.getCompanyId(),
			messageBoardMessage.getCustomFields(),
			contextAcceptLanguage.getPreferredLocale());
	}

	private Map<String, Map<String, String>> _getMessageBoardListActions(
		MBMessage mbMessage) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"get-child-messages",
			addAction(
				"VIEW", mbMessage.getMessageId(),
				"getMessageBoardMessageMessageBoardMessagesPage",
				"com.liferay.message.boards", mbMessage.getGroupId())
		).put(
			"reply-to-message",
			addAction(
				"REPLY_TO_MESSAGE", mbMessage.getMessageId(),
				"postMessageBoardMessageMessageBoardMessage",
				"com.liferay.message.boards", mbMessage.getGroupId())
		).build();
	}

	private Page<MessageBoardMessage> _getMessageBoardMessagesPage(
			Map<String, Map<String, String>> actions,
			Long messageBoardMessageId, String search, Filter filter,
			Pagination pagination, Sort[] sorts, Boolean flatten, Long siteId)
		throws Exception {

		if (messageBoardMessageId != null) {
			MBMessage mbMessage = _mbMessageService.getMessage(
				messageBoardMessageId);

			siteId = mbMessage.getGroupId();
		}

		long messageBoardMessageSiteId = siteId;

		return SearchUtil.search(
			actions,
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				if (messageBoardMessageId != null) {
					booleanFilter.add(
						new TermFilter(
							Field.ENTRY_CLASS_PK,
							String.valueOf(messageBoardMessageId)),
						BooleanClauseOccur.MUST_NOT);

					String field = "parentMessageId";

					if (GetterUtil.getBoolean(flatten)) {
						field = "treePath";
					}

					booleanFilter.add(
						new TermFilter(
							field, String.valueOf(messageBoardMessageId)),
						BooleanClauseOccur.MUST);
				}
				else {
					if (!GetterUtil.getBoolean(flatten)) {
						booleanFilter.add(
							new TermFilter("categoryId", "0"),
							BooleanClauseOccur.MUST);
					}

					booleanFilter.add(
						new TermFilter(
							Field.GROUP_ID,
							String.valueOf(messageBoardMessageSiteId)),
						BooleanClauseOccur.MUST);
				}
			},
			filter, MBMessage.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> searchContext.setCompanyId(
				contextCompany.getCompanyId()),
			sorts,
			document -> _toMessageBoardMessage(
				_mbMessageService.getMessage(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	private Map<String, Map<String, String>> _getMessageBoardThreadListActions(
		MBThread mbThread) {

		return HashMapBuilder.<String, Map<String, String>>put(
			"create",
			addAction(
				"ADD_MESSAGE", mbThread.getThreadId(),
				"postMessageBoardThreadMessageBoardMessage",
				"com.liferay.message.boards", mbThread.getGroupId())
		).put(
			"get",
			addAction(
				"VIEW", mbThread.getThreadId(),
				"getMessageBoardThreadMessageBoardMessagesPage",
				"com.liferay.message.boards", mbThread.getGroupId())
		).build();
	}

	private Map<String, Map<String, String>> _getSiteListActions(long site) {
		return HashMapBuilder.<String, Map<String, String>>put(
			"get",
			addAction(
				"VIEW", "getSiteMessageBoardMessagesPage",
				"com.liferay.message.boards", site)
		).build();
	}

	private SPIRatingResource<Rating> _getSPIRatingResource() {
		return new SPIRatingResource<>(
			MBMessage.class.getName(), _ratingsEntryLocalService,
			ratingsEntry -> RatingUtil.toRating(
				_portal, ratingsEntry, _userLocalService),
			contextUser);
	}

	private MessageBoardMessage _toMessageBoardMessage(MBMessage mbMessage)
		throws Exception {

		return _messageBoardMessageDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				false, _getActions(mbMessage), _dtoConverterRegistry,
				mbMessage.getPrimaryKey(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private void _updateAnswer(
			MBMessage mbMessage, MessageBoardMessage messageBoardMessage)
		throws Exception {

		Boolean showAsAnswer = messageBoardMessage.getShowAsAnswer();

		if (showAsAnswer != null) {
			_mbMessageService.updateAnswer(
				mbMessage.getMessageId(), showAsAnswer, false);

			mbMessage.setAnswer(showAsAnswer);
		}
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ExpandoColumnLocalService _expandoColumnLocalService;

	@Reference
	private ExpandoTableLocalService _expandoTableLocalService;

	@Reference
	private MBMessageService _mbMessageService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private MessageBoardMessageDTOConverter _messageBoardMessageDTOConverter;

	@Reference
	private Portal _portal;

	@Reference
	private RatingsEntryLocalService _ratingsEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}