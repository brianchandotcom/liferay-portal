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

import com.liferay.headless.delivery.dto.v1_0.MessageBoardSection;
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
public interface MessageBoardSectionResource {

	public static Builder builder() {
		return FactoryHolder.factory.create();
	}

	public void deleteMessageBoardSection(Long messageBoardSectionId)
		throws Exception;

	public Response deleteMessageBoardSectionBatch(
			String callbackURL, Object object)
		throws Exception;

	public MessageBoardSection getMessageBoardSection(
			Long messageBoardSectionId)
		throws Exception;

	public MessageBoardSection patchMessageBoardSection(
			Long messageBoardSectionId, MessageBoardSection messageBoardSection)
		throws Exception;

	public MessageBoardSection putMessageBoardSection(
			Long messageBoardSectionId, MessageBoardSection messageBoardSection)
		throws Exception;

	public Response putMessageBoardSectionBatch(
			String callbackURL, Object object)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getMessageBoardSectionPermissionsPage(
				Long messageBoardSectionId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putMessageBoardSectionPermission(
				Long messageBoardSectionId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception;

	public void putMessageBoardSectionSubscribe(Long messageBoardSectionId)
		throws Exception;

	public void putMessageBoardSectionUnsubscribe(Long messageBoardSectionId)
		throws Exception;

	public Page<MessageBoardSection>
			getMessageBoardSectionMessageBoardSectionsPage(
				Long parentMessageBoardSectionId, String search,
				com.liferay.portal.vulcan.aggregation.Aggregation aggregation,
				Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public MessageBoardSection postMessageBoardSectionMessageBoardSection(
			Long parentMessageBoardSectionId,
			MessageBoardSection messageBoardSection)
		throws Exception;

	public Page<MessageBoardSection> getSiteMessageBoardSectionsPage(
			Long siteId, Boolean flatten, String search,
			com.liferay.portal.vulcan.aggregation.Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public MessageBoardSection postSiteMessageBoardSection(
			Long siteId, MessageBoardSection messageBoardSection)
		throws Exception;

	public Response postSiteMessageBoardSectionBatch(
			Long siteId, String callbackURL, Object object)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getSiteMessageBoardSectionPermissionsPage(
				Long siteId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putSiteMessageBoardSectionPermission(
				Long siteId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
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

		public MessageBoardSectionResource build();

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