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
import com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseArticleResource;
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
				0, "/site-initializer/knowledge-base-articles",
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
			long parentResourcePrimKey, String resourcePath,
			JSONObject jsonObject, ServiceContext serviceContext)
		throws Exception {

		KnowledgeBaseArticle knowledgeBaseArticle = _addKnowledgeBaseArticle(
			parentResourcePrimKey, jsonObject, serviceContext);

		_addKBEntries(
			knowledgeBaseArticle.getId(), resourcePath, serviceContext);
	}

	private void _addKBEntries(
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
					parentResourcePrimKey,
					resourcePath.substring(
						0, resourcePath.indexOf(".metadata.json")),
					jsonObject, serviceContext);
			}
			else {
				throw new UnsupportedOperationException();
			}
		}
	}

	private KnowledgeBaseArticle _addKnowledgeBaseArticle(
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

		if (parentResourcePrimKey != 0) {
			return knowledgeBaseArticleResource.
				postKnowledgeBaseArticleKnowledgeBaseArticle(
					parentResourcePrimKey,
					KnowledgeBaseArticle.toDTO(jsonObject.toString()));
		}

		return knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
			serviceContext.getScopeGroupId(),
			KnowledgeBaseArticle.toDTO(jsonObject.toString()));
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

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.initializer.liferay.help)"
	)
	private ServletContext _servletContext;

	@Reference
	private UserLocalService _userLocalService;

}