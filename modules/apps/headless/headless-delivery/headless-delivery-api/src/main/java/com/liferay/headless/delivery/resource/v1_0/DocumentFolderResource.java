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

import com.liferay.headless.delivery.dto.v1_0.DocumentFolder;
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
public interface DocumentFolderResource {

	public static Builder builder() {
		return FactoryHolder.factory.create();
	}

	public Page<DocumentFolder> getAssetLibraryDocumentFoldersPage(
			Long assetLibraryId, Boolean flatten, String search,
			com.liferay.portal.vulcan.aggregation.Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public DocumentFolder postAssetLibraryDocumentFolder(
			Long assetLibraryId, DocumentFolder documentFolder)
		throws Exception;

	public Response postAssetLibraryDocumentFolderBatch(
			Long assetLibraryId, String callbackURL, Object object)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getAssetLibraryDocumentFolderPermissionsPage(
				Long assetLibraryId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putAssetLibraryDocumentFolderPermission(
				Long assetLibraryId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception;

	public void deleteDocumentFolder(Long documentFolderId) throws Exception;

	public Response deleteDocumentFolderBatch(String callbackURL, Object object)
		throws Exception;

	public DocumentFolder getDocumentFolder(Long documentFolderId)
		throws Exception;

	public DocumentFolder patchDocumentFolder(
			Long documentFolderId, DocumentFolder documentFolder)
		throws Exception;

	public DocumentFolder putDocumentFolder(
			Long documentFolderId, DocumentFolder documentFolder)
		throws Exception;

	public Response putDocumentFolderBatch(String callbackURL, Object object)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getDocumentFolderPermissionsPage(
				Long documentFolderId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putDocumentFolderPermission(
				Long documentFolderId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception;

	public void putDocumentFolderSubscribe(Long documentFolderId)
		throws Exception;

	public void putDocumentFolderUnsubscribe(Long documentFolderId)
		throws Exception;

	public Page<DocumentFolder> getDocumentFolderDocumentFoldersPage(
			Long parentDocumentFolderId, Boolean flatten, String search,
			com.liferay.portal.vulcan.aggregation.Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public DocumentFolder postDocumentFolderDocumentFolder(
			Long parentDocumentFolderId, DocumentFolder documentFolder)
		throws Exception;

	public Page<DocumentFolder> getSiteDocumentFoldersPage(
			Long siteId, Boolean flatten, String search,
			com.liferay.portal.vulcan.aggregation.Aggregation aggregation,
			Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception;

	public DocumentFolder postSiteDocumentFolder(
			Long siteId, DocumentFolder documentFolder)
		throws Exception;

	public Response postSiteDocumentFolderBatch(
			Long siteId, String callbackURL, Object object)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getSiteDocumentFolderPermissionsPage(Long siteId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putSiteDocumentFolderPermission(
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

		public DocumentFolderResource build();

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