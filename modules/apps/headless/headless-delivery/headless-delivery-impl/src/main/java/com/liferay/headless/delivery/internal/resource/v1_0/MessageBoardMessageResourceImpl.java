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

import com.liferay.headless.common.spi.service.context.ServiceContextUtil;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardMessage;
import com.liferay.headless.delivery.dto.v1_0.converter.DefaultDTOConverterContext;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.MessageBoardMessageDTOConverter;
import com.liferay.headless.delivery.internal.odata.entity.v1_0.MessageBoardMessageEntityModel;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardMessageResource;
import com.liferay.message.boards.constants.MBMessageConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.vulcan.util.SearchUtil;

import java.util.Collections;

import javax.ws.rs.core.Context;
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

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
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
				Long messageBoardMessageId, String search, Filter filter,
				Pagination pagination, Sort[] sorts)
		throws Exception {

		return _getMessageBoardMessagesPage(
			messageBoardMessageId, search, filter, pagination, sorts);
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
			mbThread.getRootMessageId(), search, filter, pagination, sorts);
	}

	@Override
	public MessageBoardMessage postMessageBoardMessageMessageBoardMessage(
			Long messageBoardMessageId, MessageBoardMessage messageBoardMessage)
		throws Exception {

		return _addMessageBoardThread(
			messageBoardMessageId, messageBoardMessage);
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
				messageBoardMessage.getKeywords(),
				messageBoardMessage.getTaxonomyCategoryIds(),
				mbMessage.getGroupId(),
				messageBoardMessage.getViewableByAsString()));

		_updateAnswer(mbMessage, messageBoardMessage);

		return _toMessageBoardMessage(mbMessage);
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
				messageBoardMessage.getKeywords(),
				messageBoardMessage.getTaxonomyCategoryIds(),
				parentMBMessage.getGroupId(),
				messageBoardMessage.getViewableByAsString()));

		_updateAnswer(mbMessage, messageBoardMessage);

		return _toMessageBoardMessage(mbMessage);
	}

	private Page<MessageBoardMessage> _getMessageBoardMessagesPage(
			Long messageBoardMessageId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.add(
					new TermFilter(
						Field.ENTRY_CLASS_PK,
						String.valueOf(messageBoardMessageId)),
					BooleanClauseOccur.MUST_NOT);
				booleanFilter.add(
					new TermFilter(
						"parentMessageId",
						String.valueOf(messageBoardMessageId)),
					BooleanClauseOccur.MUST);
			},
			filter, MBMessage.class, search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				searchContext.setCompanyId(contextCompany.getCompanyId());
			},
			document -> _toMessageBoardMessage(
				_mbMessageService.getMessage(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))),
			sorts);
	}

	private MessageBoardMessage _toMessageBoardMessage(MBMessage mbMessage)
		throws Exception {

		return _messageBoardMessageDTOConverter.toDTO(
			new DefaultDTOConverterContext(null, mbMessage.getPrimaryKey()));
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

	private static final EntityModel _entityModel =
		new MessageBoardMessageEntityModel();

	@Reference
	private MBMessageService _mbMessageService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

	@Reference
	private MessageBoardMessageDTOConverter _messageBoardMessageDTOConverter;

	@Context
	private User _user;

}