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

package com.liferay.headless.admin.taxonomy.resource.v1_0;

import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary;
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
 *     curl -u your@email.com:yourpassword -D - http://localhost:8080/o/headless-admin-taxonomy/v1.0
 *
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@ProviderType
public interface TaxonomyVocabularyResource {

	public static Builder builder() {
		return FactoryHolder.factory.create();
	}

	public Page<TaxonomyVocabulary> getAssetLibraryTaxonomyVocabulariesPage(
			Long assetLibraryId, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception;

	public TaxonomyVocabulary postAssetLibraryTaxonomyVocabulary(
			Long assetLibraryId, TaxonomyVocabulary taxonomyVocabulary)
		throws Exception;

	public Response postAssetLibraryTaxonomyVocabularyBatch(
			Long assetLibraryId, String callbackURL, Object object)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getAssetLibraryTaxonomyVocabularyPermissionsPage(
				Long assetLibraryId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putAssetLibraryTaxonomyVocabularyPermission(
				Long assetLibraryId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception;

	public Page<TaxonomyVocabulary> getSiteTaxonomyVocabulariesPage(
			Long siteId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception;

	public TaxonomyVocabulary postSiteTaxonomyVocabulary(
			Long siteId, TaxonomyVocabulary taxonomyVocabulary)
		throws Exception;

	public Response postSiteTaxonomyVocabularyBatch(
			Long siteId, String callbackURL, Object object)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getSiteTaxonomyVocabularyPermissionsPage(
				Long siteId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putSiteTaxonomyVocabularyPermission(
				Long siteId,
				com.liferay.portal.vulcan.permission.Permission[] permissions)
		throws Exception;

	public void deleteTaxonomyVocabulary(Long taxonomyVocabularyId)
		throws Exception;

	public Response deleteTaxonomyVocabularyBatch(
			String callbackURL, Object object)
		throws Exception;

	public TaxonomyVocabulary getTaxonomyVocabulary(Long taxonomyVocabularyId)
		throws Exception;

	public TaxonomyVocabulary patchTaxonomyVocabulary(
			Long taxonomyVocabularyId, TaxonomyVocabulary taxonomyVocabulary)
		throws Exception;

	public TaxonomyVocabulary putTaxonomyVocabulary(
			Long taxonomyVocabularyId, TaxonomyVocabulary taxonomyVocabulary)
		throws Exception;

	public Response putTaxonomyVocabularyBatch(
			String callbackURL, Object object)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			getTaxonomyVocabularyPermissionsPage(
				Long taxonomyVocabularyId, String roleNames)
		throws Exception;

	public Page<com.liferay.portal.vulcan.permission.Permission>
			putTaxonomyVocabularyPermission(
				Long taxonomyVocabularyId,
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

		public TaxonomyVocabularyResource build();

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