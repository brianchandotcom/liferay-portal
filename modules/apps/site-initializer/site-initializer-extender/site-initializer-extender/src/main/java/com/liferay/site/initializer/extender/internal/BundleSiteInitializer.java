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

package com.liferay.site.initializer.extender.internal;

import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.util.Dictionary;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;

/**
 * @author Brian Wing Shun Chan
 */
public class BundleSiteInitializer implements SiteInitializer {

	public BundleSiteInitializer(
		Bundle bundle, ServletContext servletContext,
		ObjectDefinitionResource.Factory objectDefinitionResourceFactory,
		TaxonomyVocabularyResource.Factory taxonomyVocabularyResourceFactory,
		UserLocalService userLocalService) {

		_bundle = bundle;
		_servletContext = servletContext;
		_objectDefinitionResourceFactory = objectDefinitionResourceFactory;
		_taxonomyVocabularyResourceFactory = taxonomyVocabularyResourceFactory;
		_userLocalService = userLocalService;
	}

	@Override
	public String getDescription(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getKey() {
		return _bundle.getSymbolicName();
	}

	@Override
	public String getName(Locale locale) {
		Dictionary<String, String> headers = _bundle.getHeaders(
			StringPool.BLANK);

		return GetterUtil.getString(headers.get("Bundle-Name"));
	}

	@Override
	public String getThumbnailSrc() {
		return _servletContext.getContextPath() + "/images/thumbnail.png";
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		try {
			User user = _userLocalService.getUser(
				PrincipalThreadLocal.getUserId());

			_initialize(groupId, user);
		}
		catch (Exception exception) {
			throw new InitializationException(exception);
		}
	}

	@Override
	public boolean isActive(long companyId) {
		return true;
	}

	private void _addObjectDefinition(User user) throws Exception {
		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/rest/object-definition");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		ObjectDefinitionResource.Builder objectDefinitionResourceBuilder =
			_objectDefinitionResourceFactory.create();

		ObjectDefinitionResource objectDefinitionResource =
			objectDefinitionResourceBuilder.user(
				user
			).build();

		for (String resourcePath : resourcePaths) {
			ObjectDefinition objectDefinition = ObjectDefinition.toDTO(
				_read(resourcePath));

			if (objectDefinition != null) {
				objectDefinition =
					objectDefinitionResource.postObjectDefinition(
						objectDefinition);

				objectDefinitionResource.postObjectDefinitionPublish(
					objectDefinition.getId());
			}
		}
	}

	private void _addTaxonomyVocabularies(long groupId, User user)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/rest/taxonomy-vocabularies");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		TaxonomyVocabularyResource.Builder taxonomyVocabularyResourceBuilder =
			_taxonomyVocabularyResourceFactory.create();

		TaxonomyVocabularyResource taxonomyVocabularyResource =
			taxonomyVocabularyResourceBuilder.user(
				user
			).build();

		for (String resourcePath : resourcePaths) {
			TaxonomyVocabulary taxonomyVocabulary = TaxonomyVocabulary.toDTO(
				_read(resourcePath));

			// TODO

			if (false) {
				taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
					groupId, taxonomyVocabulary);
			}
		}
	}

	private void _initialize(long groupId, User user) throws Exception {
		_addObjectDefinition(user);
		_addTaxonomyVocabularies(groupId, user);
	}

	private String _read(String resourcePath) throws Exception {
		return StringUtil.read(
			_servletContext.getResourceAsStream(resourcePath));
	}

	private final Bundle _bundle;
	private final ObjectDefinitionResource.Factory
		_objectDefinitionResourceFactory;
	private final ServletContext _servletContext;
	private final TaxonomyVocabularyResource.Factory
		_taxonomyVocabularyResourceFactory;
	private final UserLocalService _userLocalService;

}