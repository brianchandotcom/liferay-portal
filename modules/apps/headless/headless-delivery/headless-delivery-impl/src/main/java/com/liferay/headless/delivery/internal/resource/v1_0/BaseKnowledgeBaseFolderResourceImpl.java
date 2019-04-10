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

package com.liferay.headless.delivery.internal.resource.v1_0;

import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.TransformUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;

import javax.annotation.Generated;

import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseKnowledgeBaseFolderResourceImpl
	implements AopService, KnowledgeBaseFolderResource {

	@Override
	public Dictionary<String, Object> getProperties() {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("api.version", "v1.0");
		properties.put(
			"osgi.jaxrs.application.select",
			"(osgi.jaxrs.name=Liferay.Headless.Delivery)");
		properties.put("osgi.jaxrs.resource", true);

		return properties;
	}

	@Override
	public void deleteKnowledgeBaseFolder(Long knowledgeBaseFolderId)
		throws Exception {
	}

	@Override
	public KnowledgeBaseFolder getKnowledgeBaseFolder(
			Long knowledgeBaseFolderId)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	@Override
	public KnowledgeBaseFolder patchKnowledgeBaseFolder(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		KnowledgeBaseFolder existingKnowledgeBaseFolder =
			getKnowledgeBaseFolder(knowledgeBaseFolderId);

		if (knowledgeBaseFolder.getDateCreated() != null) {
			existingKnowledgeBaseFolder.setDateCreated(
				knowledgeBaseFolder.getDateCreated());
		}

		if (knowledgeBaseFolder.getDateModified() != null) {
			existingKnowledgeBaseFolder.setDateModified(
				knowledgeBaseFolder.getDateModified());
		}

		if (knowledgeBaseFolder.getDescription() != null) {
			existingKnowledgeBaseFolder.setDescription(
				knowledgeBaseFolder.getDescription());
		}

		if (knowledgeBaseFolder.getName() != null) {
			existingKnowledgeBaseFolder.setName(knowledgeBaseFolder.getName());
		}

		if (knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles() != null) {
			existingKnowledgeBaseFolder.setNumberOfKnowledgeBaseArticles(
				knowledgeBaseFolder.getNumberOfKnowledgeBaseArticles());
		}

		if (knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders() != null) {
			existingKnowledgeBaseFolder.setNumberOfKnowledgeBaseFolders(
				knowledgeBaseFolder.getNumberOfKnowledgeBaseFolders());
		}

		if (knowledgeBaseFolder.getParentKnowledgeBaseFolderId() != null) {
			existingKnowledgeBaseFolder.setParentKnowledgeBaseFolderId(
				knowledgeBaseFolder.getParentKnowledgeBaseFolderId());
		}

		if (knowledgeBaseFolder.getSiteId() != null) {
			existingKnowledgeBaseFolder.setSiteId(
				knowledgeBaseFolder.getSiteId());
		}

		if (knowledgeBaseFolder.getViewableBy() != null) {
			existingKnowledgeBaseFolder.setViewableBy(
				knowledgeBaseFolder.getViewableBy());
		}

		preparePatch(knowledgeBaseFolder, existingKnowledgeBaseFolder);

		return putKnowledgeBaseFolder(
			knowledgeBaseFolderId, existingKnowledgeBaseFolder);
	}

	@Override
	public KnowledgeBaseFolder putKnowledgeBaseFolder(
			Long knowledgeBaseFolderId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	@Override
	public Page<KnowledgeBaseFolder>
			getKnowledgeBaseFolderKnowledgeBaseFoldersPage(
				Long parentKnowledgeBaseFolderId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public KnowledgeBaseFolder postKnowledgeBaseFolderKnowledgeBaseFolder(
			Long parentKnowledgeBaseFolderId,
			KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	@Override
	public Page<KnowledgeBaseFolder> getSiteKnowledgeBaseFoldersPage(
			Long siteId, Pagination pagination)
		throws Exception {

		return Page.of(Collections.emptyList());
	}

	@Override
	public KnowledgeBaseFolder postSiteKnowledgeBaseFolder(
			Long siteId, KnowledgeBaseFolder knowledgeBaseFolder)
		throws Exception {

		return new KnowledgeBaseFolder();
	}

	@Override
	public void setContextAcceptLanguage(AcceptLanguage contextAcceptLanguage) {
		this.contextAcceptLanguage = contextAcceptLanguage;
	}

	@Override
	public void setContextCompany(Company contextCompany) {
		this.contextCompany = contextCompany;
	}

	@Override
	public void setContextUriInfo(UriInfo contextUriInfo) {
		this.contextUriInfo = contextUriInfo;
	}

	protected void preparePatch(
		KnowledgeBaseFolder knowledgeBaseFolder,
		KnowledgeBaseFolder existingKnowledgeBaseFolder) {
	}

	protected <T, R> List<R> transform(
		Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transform(collection, unsafeFunction);
	}

	protected <T, R> R[] transform(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction,
		Class<?> clazz) {

		return TransformUtil.transform(array, unsafeFunction, clazz);
	}

	protected <T, R> R[] transformToArray(
		Collection<T> collection,
		UnsafeFunction<T, R, Exception> unsafeFunction, Class<?> clazz) {

		return TransformUtil.transformToArray(
			collection, unsafeFunction, clazz);
	}

	protected <T, R> List<R> transformToList(
		T[] array, UnsafeFunction<T, R, Exception> unsafeFunction) {

		return TransformUtil.transformToList(array, unsafeFunction);
	}

	protected AcceptLanguage contextAcceptLanguage;
	protected Company contextCompany;
	protected UriInfo contextUriInfo;

}