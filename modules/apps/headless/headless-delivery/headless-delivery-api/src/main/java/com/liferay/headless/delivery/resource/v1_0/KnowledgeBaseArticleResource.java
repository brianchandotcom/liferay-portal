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

import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseArticle;
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
public interface KnowledgeBaseArticleResource {

	public static Builder builder() {
		return FactoryHolder.factory.create();
	}

	public void deleteKnowledgeBaseArticle(Long knowledgeBaseArticleId)
		throws Exception;

	public Response deleteKnowledgeBaseArticleBatch(
			String callbackURL, Object object)
		throws Exception;

	public KnowledgeBaseArticle getKnowledgeBaseArticle(
			Long knowledgeBaseArticleId)
		throws Exception;

	public KnowledgeBaseArticle patchKnowledgeBaseArticle(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception;

	public KnowledgeBaseArticle putKnowledgeBaseArticle(
			Long knowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception;

	public Response putKnowledgeBaseArticleBatch(
			String callbackURL, Object object)
		throws Exception;

	public void deleteKnowledgeBaseArticleMyRating(Long knowledgeBaseArticleId)
		throws Exception;

	public Rating getKnowledgeBaseArticleMyRating(Long knowledgeBaseArticleId)
		throws Exception;

	public Rating postKnowledgeBaseArticleMyRating(
			Long knowledgeBaseArticleId, Rating rating)
		throws Exception;

	public Rating putKnowledgeBaseArticleMyRating(
			Long knowledgeBaseArticleId, Rating rating)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getKnowledgeBaseArticlePermissionsPage(
				Long knowledgeBaseArticleId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putKnowledgeBaseArticlePermission(
				Long knowledgeBaseArticleId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception;

	public void putKnowledgeBaseArticleSubscribe(Long knowledgeBaseArticleId)
		throws Exception;

	public void putKnowledgeBaseArticleUnsubscribe(Long knowledgeBaseArticleId)
		throws Exception;

	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
				Long parentKnowledgeBaseArticleId, Boolean flatten,
				String search,
				com.liferay.portal.vulcan.aggregation.Aggregation aggregation,
				Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public KnowledgeBaseArticle postKnowledgeBaseArticleKnowledgeBaseArticle(
			Long parentKnowledgeBaseArticleId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception;

	public Page<KnowledgeBaseArticle>
			getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
				Long knowledgeBaseFolderId, Boolean flatten, String search,
				com.liferay.portal.vulcan.aggregation.Aggregation aggregation,
				Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public KnowledgeBaseArticle postKnowledgeBaseFolderKnowledgeBaseArticle(
			Long knowledgeBaseFolderId,
			KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception;

	public Response postKnowledgeBaseFolderKnowledgeBaseArticleBatch(
			Long knowledgeBaseFolderId, String callbackURL, Object object)
		throws Exception;

	public Page<KnowledgeBaseArticle> getSiteKnowledgeBaseArticlesPage(
			Long siteId, Boolean flatten, String search,
			com.liferay.portal.vulcan.aggregation.Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public KnowledgeBaseArticle postSiteKnowledgeBaseArticle(
			Long siteId, KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception;

	public Response postSiteKnowledgeBaseArticleBatch(
			Long siteId, String callbackURL, Object object)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getSiteKnowledgeBaseArticlePermissionsPage(
				Long siteId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putSiteKnowledgeBaseArticlePermission(
				Long siteId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception;

	public void putSiteKnowledgeBaseArticleSubscribe(Long siteId)
		throws Exception;

	public void putSiteKnowledgeBaseArticleUnsubscribe(Long siteId)
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

		public KnowledgeBaseArticleResource build();

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