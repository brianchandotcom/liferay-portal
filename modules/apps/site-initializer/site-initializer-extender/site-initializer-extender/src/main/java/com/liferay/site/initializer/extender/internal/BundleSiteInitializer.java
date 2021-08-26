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

import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.external.reference.service.ERAssetCategoryLocalService;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.headless.admin.taxonomy.dto.v1_0.TaxonomyVocabulary;
import com.liferay.headless.admin.taxonomy.resource.v1_0.TaxonomyVocabularyResource;
import com.liferay.headless.delivery.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.resource.v1_0.DocumentFolderResource;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.journal.model.JournalArticle;
import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.resource.v1_0.ObjectDefinitionResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.style.book.zip.processor.StyleBookEntryZipProcessor;

import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
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
		AssetCategoryLocalService assetCategoryLocalService, Bundle bundle,
		DDMStructureLocalService ddmStructureLocalService,
		DDMTemplateLocalService ddmTemplateLocalService,
		DefaultDDMStructureHelper defaultDDMStructureHelper,
		DocumentFolderResource.Factory documentFolderResourceFactory,
		DocumentResource.Factory documentResourceFactory,
		ERAssetCategoryLocalService erAssetCategoryLocalService,
		FragmentsImporter fragmentsImporter,
		GroupLocalService groupLocalService, JSONFactory jsonFactory,
		ObjectDefinitionResource.Factory objectDefinitionResourceFactory,
		Portal portal,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService, ServletContext servletContext,
		StyleBookEntryZipProcessor styleBookEntryZipProcessor,
		TaxonomyVocabularyResource.Factory taxonomyVocabularyResourceFactory,
		UserLocalService userLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
		_bundle = bundle;
		_ddmStructureLocalService = ddmStructureLocalService;
		_ddmTemplateLocalService = ddmTemplateLocalService;
		_defaultDDMStructureHelper = defaultDDMStructureHelper;
		_documentFolderResourceFactory = documentFolderResourceFactory;
		_documentResourceFactory = documentResourceFactory;
		_erAssetCategoryLocalService = erAssetCategoryLocalService;
		_fragmentsImporter = fragmentsImporter;
		_groupLocalService = groupLocalService;
		_jsonFactory = jsonFactory;
		_objectDefinitionResourceFactory = objectDefinitionResourceFactory;
		_portal = portal;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
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
			_addDocuments(serviceContext);
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

	private void _addAssetCategories(
			long groupId, long vocabularyId, String parentResourcePath,
			ServiceContext serviceContext)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			_read(parentResourcePath + "asset-categories.json"));

		for (int i = 0; i < jsonArray.length(); i++) {
			String titleCategory = null;
			String externalReferenceCodeCategory = null;
			JSONArray subcategoriesJSONArray = null;

			JSONObject categoryJSONObject = jsonArray.getJSONObject(i);

			if (categoryJSONObject != null) {
				titleCategory = categoryJSONObject.getString("title");

				externalReferenceCodeCategory = categoryJSONObject.getString(
					"externalReferenceCode");

				subcategoriesJSONArray = categoryJSONObject.getJSONArray(
					"subcategories");
			}
			else {
				titleCategory = jsonArray.getString(i);
			}

			AssetCategory assetCategory = _addAssetCategory(
				vocabularyId, new String[0], null,
				externalReferenceCodeCategory, groupId,
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
				serviceContext, titleCategory);

			// Permissions

			if (categoryJSONObject == null) {
				continue;
			}

			JSONArray permissionsJSONArray = categoryJSONObject.getJSONArray(
				"permissions");

			if ((permissionsJSONArray != null) &&
				(permissionsJSONArray.length() > 0)) {

				_updatePermissions(
					assetCategory.getCompanyId(),
					assetCategory.getModelClassName(),
					String.valueOf(assetCategory.getCategoryId()),
					permissionsJSONArray);
			}

			if (subcategoriesJSONArray != null) {
				for (int y = 0; y < subcategoriesJSONArray.length(); y++) {
					JSONObject subcategoryJSONObject =
						subcategoriesJSONArray.getJSONObject(y);

					String descriptionSubcategory =
						subcategoryJSONObject.getString("description");

					String titleSubcategory = subcategoryJSONObject.getString(
						"title");

					String externalReferenceCodeSubcategory =
						subcategoryJSONObject.getString(
							"externalReferenceCode");

					JSONArray propertiesJSONArray =
						subcategoryJSONObject.getJSONArray("properties");

					String[] properties =
						new String[propertiesJSONArray.length()];

					for (int x = 0; x < propertiesJSONArray.length(); x++) {
						JSONObject propertyJSONObject =
							propertiesJSONArray.getJSONObject(x);

						String key = propertyJSONObject.getString("key");
						String value = propertyJSONObject.getString("value");

						properties[x] = StringBundler.concat(
							key,
							AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR,
							value);
					}

					AssetCategory subassetcategory = _addAssetCategory(
						vocabularyId, properties, descriptionSubcategory,
						externalReferenceCodeSubcategory, groupId,
						assetCategory.getCategoryId(), serviceContext,
						titleSubcategory);

					// Permissions

					JSONArray subcategorypermissionsJSONArray =
						subcategoryJSONObject.getJSONArray("permissions");

					if ((subcategorypermissionsJSONArray != null) &&
						(subcategorypermissionsJSONArray.length() > 0)) {

						_updatePermissions(
							subassetcategory.getCompanyId(),
							subassetcategory.getModelClassName(),
							String.valueOf(subassetcategory.getCategoryId()),
							subcategorypermissionsJSONArray);
					}
				}
			}
		}
	}

	private AssetCategory _addAssetCategory(
			long assetVocabularyId, String[] categoryProperties,
			String description, String externalReferenceCode, long groupId,
			long parentCategoryId, ServiceContext serviceContext, String title)
		throws Exception {

		AssetCategory assetCategory = _assetCategoryLocalService.fetchCategory(
			groupId, parentCategoryId, title, assetVocabularyId);

		if (assetCategory == null) {
			Map<Locale, String> titleMap = Collections.singletonMap(
				LocaleUtil.getSiteDefault(), title);

			Map<Locale, String> descriptionMap = null;

			if (Validator.isNotNull(description)) {
				descriptionMap = Collections.singletonMap(
					LocaleUtil.getSiteDefault(), description);
			}

			assetCategory = _erAssetCategoryLocalService.addOrUpdateCategory(
				externalReferenceCode, serviceContext.getUserId(), groupId,
				parentCategoryId, titleMap, descriptionMap, assetVocabularyId,
				categoryProperties, serviceContext);
		}

		return assetCategory;
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

	private Long _addDocumentFolder(
			Long documentFolderId, String resourcePath,
			ServiceContext serviceContext)
		throws Exception {

		DocumentFolderResource.Builder documentFolderResourceBuilder =
			_documentFolderResourceFactory.create();

		DocumentFolderResource documentFolderResource =
			documentFolderResourceBuilder.user(
				serviceContext.fetchUser()
			).build();

		DocumentFolder documentFolder = null;

		resourcePath = resourcePath.substring(0, resourcePath.length() - 1);

		String json = _read(resourcePath + "._si.json");

		if (json != null) {
			documentFolder = DocumentFolder.toDTO(json);
		}
		else {
			documentFolder = DocumentFolder.toDTO(
				JSONUtil.put(
					"name", FileUtil.getShortFileName(resourcePath)
				).toString());
		}

		if (documentFolderId != null) {
			documentFolder =
				documentFolderResource.postDocumentFolderDocumentFolder(
					documentFolderId, documentFolder);
		}
		else {
			documentFolder = documentFolderResource.postSiteDocumentFolder(
				serviceContext.getScopeGroupId(), documentFolder);
		}

		return documentFolder.getId();
	}

	private void _addDocuments(
			Long documentFolderId, String parentResourcePath,
			ServiceContext serviceContext)
		throws Exception {

		Set<String> resourcePaths = _servletContext.getResourcePaths(
			parentResourcePath);

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
				_addDocuments(
					_addDocumentFolder(
						documentFolderId, resourcePath, serviceContext),
					resourcePath, serviceContext);

				continue;
			}

			if (resourcePath.endsWith("._si.json")) {
				continue;
			}

			String fileName = FileUtil.getShortFileName(resourcePath);

			URL url = _servletContext.getResource(resourcePath);

			URLConnection urlConnection = url.openConnection();

			Map<String, String> values = new HashMap<>();

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
								MimeTypesUtil.getContentType(fileName),
								fileName, urlConnection.getInputStream(),
								urlConnection.getContentLength())),
						__ -> _objectMapper, values));
			}
			else {
				documentResource.postSiteDocument(
					serviceContext.getScopeGroupId(),
					MultipartBody.of(
						Collections.singletonMap(
							"file",
							new BinaryFile(
								MimeTypesUtil.getContentType(fileName),
								fileName, urlConnection.getInputStream(),
								urlConnection.getContentLength())),
						__ -> _objectMapper, values));
			}
		}
	}

	private void _addDocuments(ServiceContext serviceContext) throws Exception {
		_addDocuments(null, "/site-initializer/documents", serviceContext);
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
			String jsonVocabulary = _read(
				resourcePath + "taxonomy-vocabulary.json");

			TaxonomyVocabulary taxonomyVocabulary = TaxonomyVocabulary.toDTO(
				jsonVocabulary);

			if (taxonomyVocabulary == null) {
				_log.error(
					"Unable to transform taxonomy vocabulary from JSON: " +
						jsonVocabulary);

				continue;
			}

			//TODO filter by name
			Page<TaxonomyVocabulary> page =
				taxonomyVocabularyResource.getSiteTaxonomyVocabulariesPage(
					groupId, taxonomyVocabulary.getName(), null, null, null);

			if (page.getTotalCount() > 0) {
				TaxonomyVocabulary existingTaxonomyVocabulary = page.getItems(
				).iterator(
				).next();

				taxonomyVocabularyResource.patchTaxonomyVocabulary(
					existingTaxonomyVocabulary.getId(), taxonomyVocabulary);
			}
			else {
				taxonomyVocabulary =
					taxonomyVocabularyResource.postSiteTaxonomyVocabulary(
						groupId, taxonomyVocabulary);
			}

			_addAssetCategories(
				groupId, taxonomyVocabulary.getId(), resourcePath,
				serviceContext);
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

	private void _updatePermissions(
			long companyId, String name, String primKey, JSONArray jsonArray)
		throws Exception {

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			int scope = jsonObject.getInt("scope");

			String roleName = jsonObject.getString("roleName");

			Role role = _roleLocalService.getRole(companyId, roleName);

			String[] actionIds = new String[0];

			JSONArray actionIdsJSONArray = jsonObject.getJSONArray("actionIds");

			if (actionIdsJSONArray != null) {
				for (int j = 0; j < actionIdsJSONArray.length(); j++) {
					actionIds = ArrayUtil.append(
						actionIds, actionIdsJSONArray.getString(j));
				}
			}

			_resourcePermissionLocalService.setResourcePermissions(
				companyId, name, scope, primKey, role.getRoleId(), actionIds);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BundleSiteInitializer.class);

	private static final ObjectMapper _objectMapper = new ObjectMapper();

	private final AssetCategoryLocalService _assetCategoryLocalService;
	private final Bundle _bundle;
	private final ClassLoader _classLoader;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDMTemplateLocalService _ddmTemplateLocalService;
	private final DefaultDDMStructureHelper _defaultDDMStructureHelper;
	private final DocumentFolderResource.Factory _documentFolderResourceFactory;
	private final DocumentResource.Factory _documentResourceFactory;
	private final ERAssetCategoryLocalService _erAssetCategoryLocalService;
	private final FragmentsImporter _fragmentsImporter;
	private final GroupLocalService _groupLocalService;
	private final JSONFactory _jsonFactory;
	private final ObjectDefinitionResource.Factory
		_objectDefinitionResourceFactory;
	private final Portal _portal;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;
	private final ServletContext _servletContext;
	private final StyleBookEntryZipProcessor _styleBookEntryZipProcessor;
	private final TaxonomyVocabularyResource.Factory
		_taxonomyVocabularyResourceFactory;
	private final UserLocalService _userLocalService;

}