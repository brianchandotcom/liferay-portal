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

package com.liferay.site.initializer.liferay.help.internal;

import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.delivery.dto.v1_0.KnowledgeBaseFolder;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseFolderResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.io.InputStream;

import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "site.initializer.key=" + LiferayHelpSiteInitializer.KEY,
	service = SiteInitializer.class
)
public class LiferayHelpSiteInitializer implements SiteInitializer {

	public static final String KEY = "liferay-help-site-initializer";

	@Override
	public String getDescription(Locale locale) {
		return null;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		return KEY;
	}

	@Override
	public String getThumbnailSrc() {
		return null;
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		try {
			_addKBEntries(
				KnowledbeBaseParentType.FOLDER, 0,
				"/site-initializer/knowledge-base-articles",
				_createServiceContext(groupId));
		}
		catch (Exception exception) {
			throw new InitializationException(exception);
		}
	}

	@Override
	public boolean isActive(long companyId) {
		return true;
	}

	private void _addKBArticle(
			KnowledbeBaseParentType knowledbeBaseParentType,
			long parentResourcePrimKey, String resourcePath,
			JSONObject jsonObject, ServiceContext serviceContext)
		throws Exception {

		KnowledgeBaseArticle knowledgeBaseArticle = _addKnowledgeBaseArticle(
			knowledbeBaseParentType, parentResourcePrimKey, jsonObject,
			serviceContext);

		_addKBEntries(
			KnowledbeBaseParentType.ARTICLE, knowledgeBaseArticle.getId(),
			resourcePath, serviceContext);
	}

	private void _addKBEntries(
			KnowledbeBaseParentType knowledbeBaseParentType,
			long parentResourcePrimKey, String parentResourcePath,
			ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			parentResourcePath);

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		for (String resourcePath : resourcePaths) {
			if (!resourcePath.endsWith(".metadata.json")) {
				continue;
			}

			String json = _read(resourcePath);

			if (json == null) {
				continue;
			}

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

			if (jsonObject.has("articleBody")) {
				_addKBArticle(
					knowledbeBaseParentType, parentResourcePrimKey,
					resourcePath.substring(
						0, resourcePath.indexOf(".metadata.json")),
					jsonObject, serviceContext);
			}
			else {
				_addKBFolder(
					parentResourcePrimKey,
					resourcePath.substring(
						0, resourcePath.indexOf(".metadata.json")),
					jsonObject, serviceContext);
			}
		}
	}

	private void _addKBFolder(
			long parentResourcePrimKey, String resourcePath,
			JSONObject jsonObject, ServiceContext serviceContext)
		throws Exception {

		KnowledgeBaseFolder knowledgeBaseFolder = _addKnowledgeBaseFolder(
			parentResourcePrimKey, jsonObject, serviceContext);

		_addKBEntries(
			KnowledbeBaseParentType.FOLDER, knowledgeBaseFolder.getId(),
			resourcePath, serviceContext);
	}

	private KnowledgeBaseArticle _addKnowledgeBaseArticle(
			KnowledbeBaseParentType knowledbeBaseParentType,
			long parentResourcePrimKey, JSONObject jsonObject,
			ServiceContext serviceContext)
		throws Exception {

		KnowledgeBaseArticleResource.Builder
			knowledgeBaseArticleResourceBuilder =
				_knowledgeBaseArticleResourceFactory.create();

		KnowledgeBaseArticleResource knowledgeBaseArticleResource =
			knowledgeBaseArticleResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		if (knowledbeBaseParentType == KnowledbeBaseParentType.ARTICLE) {
			return knowledgeBaseArticleResource.
				postKnowledgeBaseArticleKnowledgeBaseArticle(
					parentResourcePrimKey,
					KnowledgeBaseArticle.toDTO(jsonObject.toString()));
		}

		if (parentResourcePrimKey == 0) {
			return knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
				serviceContext.getScopeGroupId(),
				KnowledgeBaseArticle.toDTO(jsonObject.toString()));
		}

		return knowledgeBaseArticleResource.
			postKnowledgeBaseFolderKnowledgeBaseArticle(
				parentResourcePrimKey,
				KnowledgeBaseArticle.toDTO(jsonObject.toJSONString()));
	}

	private KnowledgeBaseFolder _addKnowledgeBaseFolder(
			long parentResourcePrimKey, JSONObject jsonObject,
			ServiceContext serviceContext)
		throws Exception {

		KnowledgeBaseFolderResource.Builder knowledgeBaseFolderResourceBuilder =
			_knowledgeBaseFolderResourceFactory.create();

		KnowledgeBaseFolderResource knowledgeBaseFolderResource =
			knowledgeBaseFolderResourceBuilder.httpServletRequest(
				serviceContext.getRequest()
			).user(
				serviceContext.fetchUser()
			).build();

		if (parentResourcePrimKey == 0) {
			return knowledgeBaseFolderResource.postSiteKnowledgeBaseFolder(
				serviceContext.getScopeGroupId(),
				KnowledgeBaseFolder.toDTO(jsonObject.toString()));
		}

		return knowledgeBaseFolderResource.
			postKnowledgeBaseFolderKnowledgeBaseFolder(
				parentResourcePrimKey,
				KnowledgeBaseFolder.toDTO(jsonObject.toString()));
	}

	private ServiceContext _createServiceContext(long groupId)
		throws PortalException {

		User user = _userLocalService.getUser(PrincipalThreadLocal.getUserId());

		ServiceContext serviceContextThreadLocal =
			ServiceContextThreadLocal.getServiceContext();

		ServiceContext serviceContext =
			(ServiceContext)serviceContextThreadLocal.clone();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private String _read(String resourcePath) throws Exception {
		try (InputStream inputStream = _servletContext.getResourceAsStream(
				resourcePath)) {

			if (inputStream == null) {
				return null;
			}

			return StringUtil.read(inputStream);
		}
	}

	@Reference
	private KnowledgeBaseArticleResource.Factory
		_knowledgeBaseArticleResourceFactory;

	@Reference
	private KnowledgeBaseFolderResource.Factory
		_knowledgeBaseFolderResourceFactory;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.initializer.liferay.help)"
	)
	private ServletContext _servletContext;

	@Reference
	private UserLocalService _userLocalService;

	private static enum KnowledbeBaseParentType {

		ARTICLE, FOLDER

	}

}