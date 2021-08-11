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

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.net.URL;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;

/**
 * @author Brian Wing Shun Chan
 */
public class BundleSiteInitializer implements SiteInitializer {

	public BundleSiteInitializer(
		Bundle bundle, DLAppLocalService dlAppLocalService,
		ObjectDefinitionResource.Factory objectDefinitionResourceFactory,
		ServletContext servletContext,
		TaxonomyVocabularyResource.Factory taxonomyVocabularyResourceFactory,
		UserLocalService userLocalService) {

		_bundle = bundle;
		_dlAppLocalService = dlAppLocalService;
		_objectDefinitionResourceFactory = objectDefinitionResourceFactory;
		_servletContext = servletContext;
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

	private void _addFileEntries(long groupId, User user) throws Exception {
		Enumeration<URL> enumeration = _bundle.findEntries(
			"/site-initializer/file-entries", "*", true);

		if (enumeration == null) {
			return;
		}

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			String fileName = FileUtil.getShortFileName(url.getFile());

			_dlAppLocalService.addFileEntry(
				null, user.getUserId(), groupId,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
				MimeTypesUtil.getContentType(fileName),
				FileUtil.getBytes(url.openStream()), null, null,
				ServiceContextThreadLocal.getServiceContext());
		}
	}

	private void _addObjectDefinitions(User user) throws Exception {
		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/object-definitions");

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
			String json = _read(resourcePath);

			ObjectDefinition objectDefinition = ObjectDefinition.toDTO(json);

			if (objectDefinition == null) {
				_log.error(
					"Unable to transform object definition from JSON: " + json);

				continue;
			}

			objectDefinition = objectDefinitionResource.postObjectDefinition(
				objectDefinition);

			objectDefinitionResource.postObjectDefinitionPublish(
				objectDefinition.getId());
		}
	}

	private void _addTaxonomyVocabularies(long groupId, User user)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/taxonomy-vocabularies");

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
			String json = _read(resourcePath);

			TaxonomyVocabulary taxonomyVocabulary = TaxonomyVocabulary.toDTO(
				json);

			if (taxonomyVocabulary == null) {
				_log.error(
					"Unable to transform taxonomy vocabulary from JSON: " +
						json);

				continue;
			}

			// TODO

			if (false) {
				taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
					groupId, taxonomyVocabulary);
			}
		}
	}

	private void _initialize(long groupId, User user) throws Exception {
		_addFileEntries(groupId, user);
		_addObjectDefinitions(user);
		_addTaxonomyVocabularies(groupId, user);
	}

	private String _read(String resourcePath) throws Exception {
		return StringUtil.read(
			_servletContext.getResourceAsStream(resourcePath));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BundleSiteInitializer.class);

	private final Bundle _bundle;
	private final DLAppLocalService _dlAppLocalService;
	private final ObjectDefinitionResource.Factory
		_objectDefinitionResourceFactory;
	private final ServletContext _servletContext;
	private final TaxonomyVocabularyResource.Factory
		_taxonomyVocabularyResourceFactory;
	private final UserLocalService _userLocalService;

}