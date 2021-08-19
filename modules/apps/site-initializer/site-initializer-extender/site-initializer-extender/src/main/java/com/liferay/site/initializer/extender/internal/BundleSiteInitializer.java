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

import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.delivery.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.resource.v1_0.DocumentFolderResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;

/**
 * @author Brian Wing Shun Chan
 */
public class BundleSiteInitializer implements SiteInitializer {

	public BundleSiteInitializer(
		Bundle bundle, DDMStructureLocalService ddmStructureLocalService,
		DDMTemplateLocalService ddmTemplateLocalService,
		DefaultDDMStructureHelper defaultDDMStructureHelper,
		DocumentFolderResource.Factory documentFolderResourceFactory,
		DocumentResource.Factory documentResourceFactory,
		FragmentsImporter fragmentsImporter,
		GroupLocalService groupLocalService, JSONFactory jsonFactory,
		ObjectDefinitionResource.Factory objectDefinitionResourceFactory,
		Portal portal, ServletContext servletContext,
		StyleBookEntryZipProcessor styleBookEntryZipProcessor,
		TaxonomyVocabularyResource.Factory taxonomyVocabularyResourceFactory,
		UserLocalService userLocalService) {

		_bundle = bundle;
		_ddmStructureLocalService = ddmStructureLocalService;
		_ddmTemplateLocalService = ddmTemplateLocalService;
		_defaultDDMStructureHelper = defaultDDMStructureHelper;
		_documentFolderResourceFactory = documentFolderResourceFactory;
		_documentResourceFactory = documentResourceFactory;
		_fragmentsImporter = fragmentsImporter;
		_groupLocalService = groupLocalService;
		_jsonFactory = jsonFactory;
		_objectDefinitionResourceFactory = objectDefinitionResourceFactory;
		_portal = portal;
		_servletContext = servletContext;
		_styleBookEntryZipProcessor = styleBookEntryZipProcessor;
		_taxonomyVocabularyResourceFactory = taxonomyVocabularyResourceFactory;
		_userLocalService = userLocalService;

		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		_classLoader = bundleWiring.getClassLoader();
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

			ServiceContext serviceContext = new ServiceContext() {
				{
					setAddGroupPermissions(true);
					setAddGuestPermissions(true);
					setCompanyId(user.getCompanyId());
					setScopeGroupId(groupId);
					setTimeZone(user.getTimeZone());
					setUserId(user.getUserId());
				}
			};

			_addDDMStructures(serviceContext);
			_addDDMTemplates(serviceContext);
			_addDocuments(null, "/site-initializer/documents", serviceContext);
			_addFragmentEntries(serviceContext);
			_addObjectDefinitions(serviceContext);
			_addStyleBookEntries(serviceContext);
			_addTaxonomyVocabularies(serviceContext);
		}
		catch (Exception exception) {
			throw new InitializationException(exception);
		}
	}

	@Override
	public boolean isActive(long companyId) {
		return true;
	}

	private void _addDDMStructures(ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/ddm-structures");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		for (String resourcePath : resourcePaths) {
			_defaultDDMStructureHelper.addDDMStructures(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_portal.getClassNameId(JournalArticle.class), _classLoader,
				resourcePath, serviceContext);
		}
	}

	private void _addDDMTemplates(ServiceContext serviceContext)
		throws Exception {

		Enumeration<URL> enumeration = _bundle.findEntries(
			"/site-initializer/ddm-templates", "ddm-template.json", true);

		if (enumeration == null) {
			return;
		}

		long resourceClassNameId = _portal.getClassNameId(JournalArticle.class);

		while (enumeration.hasMoreElements()) {
			URL url = enumeration.nextElement();

			JSONObject ddmTemplateJSONObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(url.openStream()));

			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchStructure(
					serviceContext.getScopeGroupId(), resourceClassNameId,
					ddmTemplateJSONObject.getString("ddmStructureKey"));

			_ddmTemplateLocalService.addTemplate(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_portal.getClassNameId(DDMStructure.class),
				ddmStructure.getStructureId(), resourceClassNameId,
				ddmTemplateJSONObject.getString("ddmTemplateKey"),
				HashMapBuilder.put(
					LocaleUtil.getSiteDefault(),
					ddmTemplateJSONObject.getString("name")
				).build(),
				null, DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null,
				TemplateConstants.LANG_TYPE_FTL, _read("ddm-template.ftl", url),
				false, false, null, null, serviceContext);
		}
	}

	private void _addDocuments(Long documentFolderId, String path, ServiceContext serviceContext) throws Exception {
		Set<String> resourcePaths = _servletContext.getResourcePaths(path);

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		DocumentResource.Builder documentResourceBuilder =
			_documentResourceFactory.create();

		DocumentResource documentResource = documentResourceBuilder.user(
			serviceContext.fetchUser()
		).build();

		for (String resourcePath : resourcePaths) {
			if (resourcePath.endsWith("/")) {
				long documentInternalFolderId = _addDocumentFolder(documentFolderId, resourcePath, serviceContext);

				_addDocuments(documentInternalFolderId, resourcePath, serviceContext);

				continue;
			}

			if (resourcePath.endsWith("._si.json")) {
				continue;
			}

			String fileName = FileUtil.getShortFileName(resourcePath);

			URL url = _servletContext.getResource(resourcePath);

			URLConnection urlConnection = url.openConnection();

			Map<String, String> values = new HashMap<>();

			ObjectMapper objectMapper = new ObjectMapper();

			String json = _read(resourcePath + "._si.json");

			if (json != null) {
				values = Collections.singletonMap("document", json);
			}

			if (documentFolderId != null) {
				documentResource.postDocumentFolderDocument(
					documentFolderId,
					MultipartBody.of(
						Collections.singletonMap(
							"file",
							new BinaryFile(
								MimeTypesUtil.getContentType(fileName), fileName,
								urlConnection.getInputStream(),
								urlConnection.getContentLength())),
						__ -> objectMapper, values));
			} else {
				documentResource.postSiteDocument(
					serviceContext.getScopeGroupId(),
					MultipartBody.of(
						Collections.singletonMap(
							"file",
							new BinaryFile(
								MimeTypesUtil.getContentType(fileName), fileName,
								urlConnection.getInputStream(),
								urlConnection.getContentLength())),
						__ -> objectMapper, values));
			}
		}
	}

	private Long _addDocumentFolder(Long documentFolderId, String resourcePath, ServiceContext serviceContext) throws Exception {
		DocumentFolderResource.Builder documentFolderResourceBuilder =
			_documentFolderResourceFactory.create();

		DocumentFolderResource documentFolderResource = documentFolderResourceBuilder.user(
			serviceContext.fetchUser()
		).build();

		resourcePath = StringUtil.replaceLast(resourcePath, StringPool.FORWARD_SLASH, StringPool.BLANK);

		String json = _read(resourcePath + "._si.json");

		DocumentFolder documentFolder = null;

		if (json != null) {
			documentFolder = DocumentFolder.toDTO(json);
		} else {
			JSONObject jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put("name", FileUtil.getShortFileName(resourcePath));

			documentFolder = DocumentFolder.toDTO(jsonObject.toString());
		}

		if (documentFolderId != null) {
			documentFolder = documentFolderResource.postDocumentFolderDocumentFolder(documentFolderId, documentFolder);
		} else {
			documentFolder = documentFolderResource.postSiteDocumentFolder(serviceContext.getScopeGroupId(), documentFolder);
		}

		return documentFolder.getId();
	}

	private void _addFragmentEntries(ServiceContext serviceContext)
		throws Exception {

		URL url = _bundle.getEntry("/fragments.zip");

		if (url == null) {
			return;
		}

		_fragmentsImporter.importFragmentEntries(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), 0,
			FileUtil.createTempFile(url.openStream()), false);
	}

	private void _addObjectDefinitions(ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			"/site-initializer/object-definitions");

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		ObjectDefinitionResource.Builder objectDefinitionResourceBuilder =
			_objectDefinitionResourceFactory.create();

		ObjectDefinitionResource objectDefinitionResource =
			objectDefinitionResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		for (String resourcePath : resourcePaths) {
			String json = _read(resourcePath);

			ObjectDefinition objectDefinition = ObjectDefinition.toDTO(json);

			if (objectDefinition == null) {
				_log.error(
					"Unable to transform object definition from JSON: " + json);

				continue;
			}

			try {
				objectDefinition =
					objectDefinitionResource.postObjectDefinition(
						objectDefinition);

				objectDefinitionResource.postObjectDefinitionPublish(
					objectDefinition.getId());
			}
			catch (Exception exception) {

				// TODO PUT

			}
		}
	}

	private void _addStyleBookEntries(ServiceContext serviceContext)
		throws Exception {

		URL url = _bundle.getEntry("/style-books.zip");

		if (url == null) {
			return;
		}

		_styleBookEntryZipProcessor.importStyleBookEntries(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			FileUtil.createTempFile(url.openStream()), false);
	}

	private void _addTaxonomyVocabularies(
			long groupId, String parentResourcePath,
			ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			parentResourcePath);

		if (SetUtil.isEmpty(resourcePaths)) {
			return;
		}

		TaxonomyVocabularyResource.Builder taxonomyVocabularyResourceBuilder =
			_taxonomyVocabularyResourceFactory.create();

		TaxonomyVocabularyResource taxonomyVocabularyResource =
			taxonomyVocabularyResourceBuilder.user(
				serviceContext.fetchUser()
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

			taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
				groupId, taxonomyVocabulary);
		}
	}

	private void _addTaxonomyVocabularies(ServiceContext serviceContext)
		throws Exception {

		Group group = _groupLocalService.getCompanyGroup(
			serviceContext.getCompanyId());

		_addTaxonomyVocabularies(
			group.getGroupId(),
			"/site-initializer/taxonomy-vocabularies/company", serviceContext);

		_addTaxonomyVocabularies(
			serviceContext.getScopeGroupId(),
			"/site-initializer/taxonomy-vocabularies/group", serviceContext);
	}

	private String _read(String resourcePath) throws Exception {
		InputStream inputStream = _servletContext.getResourceAsStream(
			resourcePath);

		if (inputStream == null) {
			return null;
		}

		return StringUtil.read(inputStream);
	}

	private String _read(String fileName, URL url) throws Exception {
		String path = url.getPath();

		URL entryURL = _bundle.getEntry(
			path.substring(0, path.lastIndexOf("/") + 1) + fileName);

		return StringUtil.read(entryURL.openStream());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BundleSiteInitializer.class);

	private final Bundle _bundle;
	private final ClassLoader _classLoader;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDMTemplateLocalService _ddmTemplateLocalService;
	private final DefaultDDMStructureHelper _defaultDDMStructureHelper;
	private final DocumentFolderResource.Factory _documentFolderResourceFactory;
	private final DocumentResource.Factory _documentResourceFactory;
	private final FragmentsImporter _fragmentsImporter;
	private final GroupLocalService _groupLocalService;
	private final JSONFactory _jsonFactory;
	private final ObjectDefinitionResource.Factory
		_objectDefinitionResourceFactory;
	private final Portal _portal;
	private final ServletContext _servletContext;
	private final StyleBookEntryZipProcessor _styleBookEntryZipProcessor;
	private final TaxonomyVocabularyResource.Factory
		_taxonomyVocabularyResourceFactory;
	private final UserLocalService _userLocalService;

}