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

package com.liferay.headless.batch.engine.resource.v1_0;

import com.liferay.headless.batch.engine.dto.v1_0.ImportTask;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import java.util.Locale;

import javax.annotation.Generated;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.core.UriInfo;

import org.osgi.annotation.versioning.ProviderType;

/**
 * To access this resource, run:
 *
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-batch-engine/v1.0
 *
 * @author Ivica Cardic
 * @generated
 */
@Generated("")
@ProviderType
public interface ImportTaskResource {

	public static Builder builder() {
		return FactoryHolder.factory.create();
	}

	public ImportTask deleteImportTask(
			String className, String callbackURL, String taskItemDelegateName,
			Object object)
		throws Exception;

	public ImportTask deleteImportTask(
			String className, String callbackURL, String taskItemDelegateName,
			MultipartBody multipartBody)
		throws Exception;

	public ImportTask postImportTask(
			String className, String callbackURL, String fieldNameMapping,
			String taskItemDelegateName, Object object)
		throws Exception;

	public ImportTask postImportTask(
			String className, String callbackURL, String fieldNameMapping,
			String taskItemDelegateName, MultipartBody multipartBody)
		throws Exception;

	public ImportTask putImportTask(
			String className, String callbackURL, String taskItemDelegateName,
			Object object)
		throws Exception;

	public ImportTask putImportTask(
			String className, String callbackURL, String taskItemDelegateName,
			MultipartBody multipartBody)
		throws Exception;

	public ImportTask getImportTask(Long importTaskId) throws Exception;

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

		public ImportTaskResource build();

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