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

import com.liferay.headless.delivery.dto.v1_0.MessageBoardSuspiciousActivity;
import com.liferay.headless.delivery.internal.dto.v1_0.converter.MessageBoardSuspiciousActivityDTOConverter;
import com.liferay.headless.delivery.resource.v1_0.MessageBoardSuspiciousActivityResource;
import com.liferay.message.boards.model.MBSuspiciousActivity;
import com.liferay.message.boards.service.MBSuspiciousActivityService;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.aggregation.Aggregation;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Felipe VelosoS
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/message-board-suspicious-activity.properties",
	scope = ServiceScope.PROTOTYPE,
	service = MessageBoardSuspiciousActivityResource.class
)
public class MessageBoardSuspiciousActivityResourceImpl
	extends BaseMessageBoardSuspiciousActivityResourceImpl {

	@Override
	public void
			deleteMessageBoardSuspiciousActivityMessageBoardSuspiciousActivity(
				Long messageBoardSuspiciousActivityId)
		throws Exception {

		_mbSuspiciousActivityService.deleteSuspiciousActivity(
			messageBoardSuspiciousActivityId);
	}

	@Override
	public Page<MessageBoardSuspiciousActivity>
			getMessageBoardMessageMessageBoardSuspiciousActivityPage(
				Long messageBoardMessageId, String search,
				Aggregation aggregation, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		List<MBSuspiciousActivity> mbSuspiciousActivities =
			_mbSuspiciousActivityService.getMessageSuspiciousActivities(
				messageBoardMessageId);

		return Page.of(
			TransformUtil.transform(
				mbSuspiciousActivities, this::_toMessageSuspiciousActivity));
	}

	@Override
	public Page<MessageBoardSuspiciousActivity>
			getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityPage(
				Long messageBoardSuspiciousActivityId, String search,
				Aggregation aggregation, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		List<MBSuspiciousActivity> mbSuspiciousActivities =
			ListUtil.fromArray();

		mbSuspiciousActivities.add(
			_mbSuspiciousActivityService.getSuspiciousActivity(
				messageBoardSuspiciousActivityId));

		return Page.of(
			TransformUtil.transform(
				mbSuspiciousActivities, this::_toMessageSuspiciousActivity));
	}

	@Override
	public Page<MessageBoardSuspiciousActivity>
			getMessageBoardSuspiciousActivitiesMessageBoardSuspiciousActivityUpdateValidatedPage(
				Long messageBoardSuspiciousActivityId)
		throws Exception {

		List<MBSuspiciousActivity> mbSuspiciousActivities =
			ListUtil.fromArray();

		mbSuspiciousActivities.add(
			_mbSuspiciousActivityService.updateValidated(
				messageBoardSuspiciousActivityId));

		return Page.of(
			TransformUtil.transform(
				mbSuspiciousActivities, this::_toMessageSuspiciousActivity));
	}

	@Override
	public Page<MessageBoardSuspiciousActivity>
			getMessageBoardThreadMessageBoardSuspiciousActivityPage(
				Long messageBoardThreadId, String search,
				Aggregation aggregation, Filter filter, Pagination pagination,
				Sort[] sorts)
		throws Exception {

		List<MBSuspiciousActivity> mbSuspiciousActivities =
			_mbSuspiciousActivityService.getThreadSuspiciousActivities(
				messageBoardThreadId);

		return Page.of(
			TransformUtil.transform(
				mbSuspiciousActivities, this::_toMessageSuspiciousActivity));
	}

	@Override
	public Page<MessageBoardSuspiciousActivity>
			postMessageBoardMessageMessageBoardSuspiciousActivityPage(
				Long messageBoardMessageId,
				MessageBoardSuspiciousActivity messageBoardSuspiciousActivity)
		throws Exception {

		List<MBSuspiciousActivity> mbSuspiciousActivity = ListUtil.fromArray();

		mbSuspiciousActivity.add(
			_mbSuspiciousActivityService.addOrUpdateSuspiciousActivityByMessage(
				messageBoardMessageId,
				messageBoardSuspiciousActivity.getDescription(),
				messageBoardSuspiciousActivity.getType()));

		return Page.of(
			TransformUtil.transform(
				mbSuspiciousActivity, this::_toMessageSuspiciousActivity));
	}

	@Override
	public MessageBoardSuspiciousActivity
			postMessageBoardThreadMessageBoardSuspiciousActivity(
				Long messageBoardThreadId,
				MessageBoardSuspiciousActivity messageBoardSuspiciousActivity)
		throws Exception {

		List<MBSuspiciousActivity> mbSuspiciousActivity = ListUtil.fromArray();

		mbSuspiciousActivity.add(
			_mbSuspiciousActivityService.addOrUpdateSuspiciousActivityByThread(
				messageBoardThreadId,
				messageBoardSuspiciousActivity.getDescription(),
				messageBoardSuspiciousActivity.getType()));

		List<MessageBoardSuspiciousActivity> messageBoardSuspiciousActivities =
			TransformUtil.transform(
				mbSuspiciousActivity, this::_toMessageSuspiciousActivity);

		return messageBoardSuspiciousActivities.get(0);
	}

	private MessageBoardSuspiciousActivity _toMessageSuspiciousActivity(
			MBSuspiciousActivity mbSuspiciousActivity)
		throws Exception {

		return _messageBoardSuspiciousActivityDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				false, null, _dtoConverterRegistry,
				mbSuspiciousActivity.getPrimaryKey(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private MBSuspiciousActivityService _mbSuspiciousActivityService;

	@Reference
	private MessageBoardSuspiciousActivityDTOConverter
		_messageBoardSuspiciousActivityDTOConverter;

}