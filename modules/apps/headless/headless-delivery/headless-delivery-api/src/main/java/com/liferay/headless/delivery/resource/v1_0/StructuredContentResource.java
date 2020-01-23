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

import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public interface StructuredContentResource {

	public Page<StructuredContent> getContentStructureStructuredContentsPage(
			Long contentStructureId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception;

	public Page<StructuredContent> getSiteStructuredContentsPage(
			Long siteId, Boolean flatten, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception;

	public StructuredContent postSiteStructuredContent(
			Long siteId, StructuredContent structuredContent)
		throws Exception;

	public StructuredContent getSiteStructuredContentByKey(
			Long siteId, String key)
		throws Exception;

	public StructuredContent getSiteStructuredContentByUuid(
			Long siteId, String uuid)
		throws Exception;

	public Page<StructuredContent>
			getStructuredContentFolderStructuredContentsPage(
				Long structuredContentFolderId, Boolean flatten, String search,
				Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public StructuredContent postStructuredContentFolderStructuredContent(
			Long structuredContentFolderId, StructuredContent structuredContent)
		throws Exception;

	public void deleteStructuredContent(Long structuredContentId)
		throws Exception;

	public StructuredContent getStructuredContent(Long structuredContentId)
		throws Exception;

	public StructuredContent patchStructuredContent(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception;

	public StructuredContent putStructuredContent(
			Long structuredContentId, StructuredContent structuredContent)
		throws Exception;

	public void deleteStructuredContentMyRating(Long structuredContentId)
		throws Exception;

	public Rating getStructuredContentMyRating(Long structuredContentId)
		throws Exception;

	public Rating postStructuredContentMyRating(
			Long structuredContentId, Rating rating)
		throws Exception;

	public Rating putStructuredContentMyRating(
			Long structuredContentId, Rating rating)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getStructuredContentPermissionsPage(
				Long structuredContentId, String roleNames)
		throws Exception;

	public void putStructuredContentPermission(
			Long structuredContentId,
			com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception;

	public String getStructuredContentRenderedContentTemplate(
			Long structuredContentId, Long templateId)
		throws Exception;

	public void putStructuredContentSubscribe(Long structuredContentId)
		throws Exception;

	public void putStructuredContentUnsubscribe(Long structuredContentId)
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

}