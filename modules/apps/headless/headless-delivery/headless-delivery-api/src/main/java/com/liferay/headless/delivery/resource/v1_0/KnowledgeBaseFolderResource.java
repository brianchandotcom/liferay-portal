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

import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder;
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
public interface KnowledgeBaseFolderResource {

	public static Builder builder() {
		return FactoryHolder.factory.create();
	}

	public void deleteKnowledgeBaseFolder(Long knowledgeBaseFolderId)
		throws Exception;

	public Response deleteKnowledgeBaseFolderBatch(
			String callbackURL, Object object)
		throws Exception;

	public KnowledgeBaseFolder getKnowledgeBaseFolder(
			Long knowledgeBaseFolderId)
		throws Exception;

	public KnowledgeBaseFolder patchKnowledgeBaseFolder(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception;

	public KnowledgeBaseFolder putKnowledgeBaseFolder(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception;

	public Response putKnowledgeBaseFolderBatch(
			String callbackURL, Object object)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getKnowledgeBaseFolderPermissionsPage(
				Long knowledgeBaseFolderId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putKnowledgeBaseFolderPermission(
				Long knowledgeBaseFolderId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception;

	public Page<KnowledgeBaseFolder>
			getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				Long parentKnowledgeBaseFolderId, Pagination pagination)
		throws Exception;

	public KnowledgeBaseFolder postKnowledgeBaseFolderKnowledgeBaseFolder(
			Long parentKnowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception;

	public Page<KnowledgeBaseFolder> getSiteKnowledgeBaseFoldersPage(
			Long siteId, Pagination pagination)
		throws Exception;

	public KnowledgeBaseFolder postSiteKnowledgeBaseFolder(
			Long siteId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception;

	public Response postSiteKnowledgeBaseFolderBatch(
			Long siteId, String callbackURL, Object object)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getSiteKnowledgeBaseFolderPermissionsPage(
				Long siteId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putSiteKnowledgeBaseFolderPermission(
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

		public KnowledgeBaseFolderResource build();

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