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

package com.liferay.data.engine.rest.resource.v2_0;

import com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection;
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
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/data-engine/v2.0
 *
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
@ProviderType
public interface DataRecordCollectionResource {

	public static Builder builder() {
		return FactoryHolder.factory.create();
	}

	public DataRecordCollection getDataDefinitionDataRecordCollection(
			Long dataDefinitionId)
		throws Exception;

	public Page<DataRecordCollection>
			getDataDefinitionDataRecordCollectionsPage(
				Long dataDefinitionId, String keywords, Pagination pagination)
		throws Exception;

	public DataRecordCollection postDataDefinitionDataRecordCollection(
			Long dataDefinitionId, DataRecordCollection dataRecordCollection)
		throws Exception;

	public Response postDataDefinitionDataRecordCollectionBatch(
			Long dataDefinitionId, String callbackURL, Object object)
		throws Exception;

	public void deleteDataRecordCollection(Long dataRecordCollectionId)
		throws Exception;

	public Response deleteDataRecordCollectionBatch(
			String callbackURL, Object object)
		throws Exception;

	public DataRecordCollection getDataRecordCollection(
			Long dataRecordCollectionId)
		throws Exception;

	public DataRecordCollection putDataRecordCollection(
			Long dataRecordCollectionId,
			DataRecordCollection dataRecordCollection)
		throws Exception;

	public Response putDataRecordCollectionBatch(
			String callbackURL, Object object)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getDataRecordCollectionPermissionsPage(
				Long dataRecordCollectionId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putDataRecordCollectionPermission(
				Long dataRecordCollectionId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception;

	public String getDataRecordCollectionPermissionByCurrentUser(
			Long dataRecordCollectionId)
		throws Exception;

	public DataRecordCollection
			getSiteDataRecordCollectionByDataRecordCollectionKey(
				Long siteId, String dataRecordCollectionKey)
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

		public DataRecordCollectionResource build();

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