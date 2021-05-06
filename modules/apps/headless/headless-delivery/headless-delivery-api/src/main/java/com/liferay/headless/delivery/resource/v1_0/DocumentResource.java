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

import com.liferay.headless.delivery.dto.v1_0.Document;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;
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
public interface DocumentResource {

	public static Builder builder() {
		return FactoryHolder.factory.create();
	}

	public Page<Document> getAssetLibraryDocumentsPage(
			Long assetLibraryId, Boolean flatten, String search,
			com.liferay.portal.vulcan.aggregation.Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public Document postAssetLibraryDocument(
			Long assetLibraryId, MultipartBody multipartBody)
		throws Exception;

	public Response postAssetLibraryDocumentBatch(
			Long assetLibraryId, MultipartBody multipartBody,
			String callbackURL, Object object)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getAssetLibraryDocumentPermissionsPage(
				Long assetLibraryId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putAssetLibraryDocumentPermission(
				Long assetLibraryId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception;

	public Page<Document> getDocumentFolderDocumentsPage(
			Long documentFolderId, Boolean flatten, String search,
			com.liferay.portal.vulcan.aggregation.Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public Document postDocumentFolderDocument(
			Long documentFolderId, MultipartBody multipartBody)
		throws Exception;

	public Response postDocumentFolderDocumentBatch(
			Long documentFolderId, MultipartBody multipartBody,
			String callbackURL, Object object)
		throws Exception;

	public void deleteDocument(Long documentId) throws Exception;

	public Response deleteDocumentBatch(String callbackURL, Object object)
		throws Exception;

	public Document getDocument(Long documentId) throws Exception;

	public Document patchDocument(Long documentId, MultipartBody multipartBody)
		throws Exception;

	public Document putDocument(Long documentId, MultipartBody multipartBody)
		throws Exception;

	public Response putDocumentBatch(
			MultipartBody multipartBody, String callbackURL, Object object)
		throws Exception;

	public void deleteDocumentMyRating(Long documentId) throws Exception;

	public Rating getDocumentMyRating(Long documentId) throws Exception;

	public Rating postDocumentMyRating(Long documentId, Rating rating)
		throws Exception;

	public Rating putDocumentMyRating(Long documentId, Rating rating)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getDocumentPermissionsPage(Long documentId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putDocumentPermission(
				Long documentId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception;

	public String getDocumentRenderedContentByDisplayPageDisplayPageKey(
			Long documentId, String displayPageKey)
		throws Exception;

	public Page<Document> getSiteDocumentsPage(
			Long siteId, Boolean flatten, String search,
			com.liferay.portal.vulcan.aggregation.Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public Document postSiteDocument(Long siteId, MultipartBody multipartBody)
		throws Exception;

	public Response postSiteDocumentBatch(
			Long siteId, MultipartBody multipartBody, String callbackURL,
			Object object)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getSiteDocumentPermissionsPage(Long siteId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putSiteDocumentPermission(
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

		public DocumentResource build();

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