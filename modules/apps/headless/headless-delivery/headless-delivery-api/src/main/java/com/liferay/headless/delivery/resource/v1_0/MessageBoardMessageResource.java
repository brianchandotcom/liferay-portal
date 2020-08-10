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

package com.liferay.headless.delivery.resource.v1_0;

import com.liferay.headless.delivery.dto.v1_0.MessageBoardMessage;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Locale;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.annotation.versioning.ProviderType;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-delivery/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@ProviderType
public interface MessageBoardMessageResource {

	public static Builder builder() {
		return FactoryHolder.factory.create();
	}

	public void deleteMessageBoardMessage(Long messageBoardMessageId)
		throws Exception;

	public Response deleteMessageBoardMessageBatch(
			String callbackURL, Object object)
		throws Exception;

	public MessageBoardMessage getMessageBoardMessage(
			Long messageBoardMessageId)
		throws Exception;

	public MessageBoardMessage patchMessageBoardMessage(
			Long messageBoardMessageId, MessageBoardMessage messageBoardMessage)
		throws Exception;

	public MessageBoardMessage putMessageBoardMessage(
			Long messageBoardMessageId, MessageBoardMessage messageBoardMessage)
		throws Exception;

	public Response putMessageBoardMessageBatch(
			String callbackURL, Object object)
		throws Exception;

	public void deleteMessageBoardMessageMyRating(Long messageBoardMessageId)
		throws Exception;

	public Rating getMessageBoardMessageMyRating(Long messageBoardMessageId)
		throws Exception;

	public Rating postMessageBoardMessageMyRating(
			Long messageBoardMessageId, Rating rating)
		throws Exception;

	public Rating putMessageBoardMessageMyRating(
			Long messageBoardMessageId, Rating rating)
		throws Exception;

	public void putMessageBoardMessageSubscribe(Long messageBoardMessageId)
		throws Exception;

	public void putMessageBoardMessageUnsubscribe(Long messageBoardMessageId)
		throws Exception;

	public Page<MessageBoardMessage>
			getMessageBoardMessageMessageBoardMessagesPage(
				Long parentMessageBoardMessageId, Boolean flatten,
				String search,
				com.liferay.portal.vulcan.aggregation.Aggregation aggregation,
				Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public MessageBoardMessage postMessageBoardMessageMessageBoardMessage(
			Long parentMessageBoardMessageId,
			MessageBoardMessage messageBoardMessage)
		throws Exception;

	public Page<MessageBoardMessage>
			getMessageBoardThreadMessageBoardMessagesPage(
				Long messageBoardThreadId, String search,
				com.liferay.portal.vulcan.aggregation.Aggregation aggregation,
				Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public MessageBoardMessage postMessageBoardThreadMessageBoardMessage(
			Long messageBoardThreadId, MessageBoardMessage messageBoardMessage)
		throws Exception;

	public Response postMessageBoardThreadMessageBoardMessageBatch(
			Long messageBoardThreadId, String callbackURL, Object object)
		throws Exception;

	public Page<MessageBoardMessage> getSiteMessageBoardMessagesPage(
			Long siteId, Boolean flatten, String search,
			com.liferay.portal.vulcan.aggregation.Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public MessageBoardMessage getSiteMessageBoardMessageByFriendlyUrlPath(
			Long siteId, String friendlyUrlPath)
		throws Exception;

	public default void setContextAcceptLanguage(
		AcceptLanguage contextAcceptLanguage) {
	}

	public void setContextCompany(
		com.liferay.portal.kernel.model.Company contextCompany);

	public default void setContextHttpServletRequest(
		HttpServletRequest contextHttpServletRequest) {
	}

	public default void setContextHttpServletResponse(
		HttpServletResponse contextHttpServletResponse) {
	}

	public default void setContextUriInfo(UriInfo contextUriInfo) {
	}

	public void setContextUser(
		com.liferay.portal.kernel.model.User contextUser);

	public void setGroupLocalService(GroupLocalService groupLocalService);

	public void setRoleLocalService(RoleLocalService roleLocalService);

	public static class FactoryHolder {

		public static volatile Factory factory;

	}

	@ProviderType
	public interface Builder {

		public MessageBoardMessageResource build();

		public Builder checkPermissions(boolean checkPermissions);

		public Builder httpServletRequest(
			HttpServletRequest httpServletRequest);

		public Builder preferredLocale(Locale preferredLocale);

		public Builder user(com.liferay.portal.kernel.model.User user);

	}

	@ProviderType
	public interface Factory {

		public Builder create();

	}

}