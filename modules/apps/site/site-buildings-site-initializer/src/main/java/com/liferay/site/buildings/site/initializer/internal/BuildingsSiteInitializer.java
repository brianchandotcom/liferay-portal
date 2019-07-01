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

package com.liferay.site.buildings.site.initializer.internal;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.io.File;
import java.io.InputStream;

import java.net.URL;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Chema Balsas
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = "site.initializer.key=" + BuildingsSiteInitializer.KEY,
	service = SiteInitializer.class
)
public class BuildingsSiteInitializer implements SiteInitializer {

	public static final String KEY = "site-buildings-site-initializer";

	@Override
	public String getDescription(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		return _NAME;
	}

	@Override
	public String getThumbnailSrc() {
		return _servletContext.getContextPath() + "/images/thumbnail.png";
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		try {
			_createServiceContext(groupId);

			_addImages();

			_addFragments();

			_addLayouts();

			_addLayoutPageTemplateEntry();

			_addDDMStructures();

			_addWebContent();
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new InitializationException(e);
		}
	}

	@Override
	public boolean isActive(long companyId) {
		return true;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundle = bundleContext.getBundle();
	}

	private void _addDDMStructures() throws Exception {
		Enumeration<URL> urls = _bundle.findEntries(
			_PATH + "/ddm", StringPool.STAR, false);

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();

			Class<?> clazz = getClass();

			_defaultDDMStructureHelper.addDDMStructures(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				_portal.getClassNameId(JournalArticle.class),
				clazz.getClassLoader(), url.getPath(), _serviceContext);
		}
	}

	private void _addFragments() throws Exception {
		URL url = _bundle.getEntry("/fragments.zip");

		File file = FileUtil.createTempFile(url.openStream());

		_fragmentsImporter.importFile(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(), 0,
			file, false);
	}

	private void _addImages() throws Exception {
		Enumeration<URL> urls = _bundle.findEntries(
			_PATH + "/images", StringPool.STAR, false);

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();

			byte[] bytes = null;

			try (InputStream is = url.openStream()) {
				bytes = FileUtil.getBytes(is);
			}

			String fileName = FileUtil.getShortFileName(url.getPath());

			FileEntry fileEntry = _dlAppLocalService.addFileEntry(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
				MimeTypesUtil.getContentType(fileName), bytes, _serviceContext);

			_fileEntriesObjectMap.put(
				fileEntry.getFileName(),
				JSONFactoryUtil.looseSerialize(fileEntry));

			_fileEntriesLinkMap.put(
				fileEntry.getFileName(),
				_dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false));
		}
	}

	private Layout _addLayout(long parentLayoutId, String name)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), name);

		return _layoutLocalService.addLayout(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			false, parentLayoutId, nameMap, new HashMap<>(), new HashMap<>(),
			new HashMap<>(), new HashMap<>(), LayoutConstants.TYPE_CONTENT,
			null, false, false, new HashMap<>(), _serviceContext);
	}

	private void _addLayoutPageTemplateEntry() throws PortalException {
		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					_serviceContext.getUserId(),
					_serviceContext.getScopeGroupId(), "Liferay",
					StringPool.BLANK, _serviceContext);

		_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			"Main Template", LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, 0,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);
	}

	private void _addLayouts() throws Exception {
		_addLayout(LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Home");

		Layout productsLayout = _addLayout(
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Products");

		Layout digitalExperiencePlatformLayout = _addLayout(
			productsLayout.getLayoutId(), "Digital Experience Platform");

		_addLayout(digitalExperiencePlatformLayout.getLayoutId(), "Overview");

		_addLayout(digitalExperiencePlatformLayout.getLayoutId(), "Features");

		_addLayout(
			digitalExperiencePlatformLayout.getLayoutId(), "Key Benefits");

		_addLayout(
			digitalExperiencePlatformLayout.getLayoutId(), "What is New");

		Layout commerceLayout = _addLayout(
			productsLayout.getLayoutId(), "Commerce");

		_addLayout(commerceLayout.getLayoutId(), "Commerce Demo");

		_addLayout(commerceLayout.getLayoutId(), "Features");

		_addLayout(commerceLayout.getLayoutId(), "News");

		_addLayout(productsLayout.getLayoutId(), "Analytics Cloud");

		_addLayout(LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Solutions");

		_addLayout(LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "News");
	}

	private void _addWebContent() throws Exception {
		Enumeration<URL> urls = _bundle.findEntries(
			_PATH + "/web-content", "*.json", false);

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(url.openStream()));

			String name = jsonObject.getString("name");

			String contentPath = jsonObject.getString("contentPath");

			String path = url.getPath();

			int index = path.lastIndexOf(CharPool.SLASH);

			String filePath = path.substring(0, index);

			String content = StringUtil.replace(
				StringUtil.read(getClass(), filePath + "/" + contentPath),
				StringPool.DOLLAR, StringPool.DOLLAR, _fileEntriesObjectMap);

			String ddmStructureKey = jsonObject.getString("ddmStructureKey");

			_journalArticleLocalService.addArticle(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				Collections.singletonMap(LocaleUtil.US, name), null, content,
				ddmStructureKey, null, _serviceContext);
		}
	}

	private void _createServiceContext(long groupId) throws PortalException {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		User user = _userLocalService.getUser(PrincipalThreadLocal.getUserId());

		Locale locale = LocaleUtil.getSiteDefault();

		serviceContext.setLanguageId(LanguageUtil.getLanguageId(locale));

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(user.getUserId());
		serviceContext.setTimeZone(user.getTimeZone());

		_serviceContext = serviceContext;
	}

	private static final String _NAME = "Buildings";

	private static final String _PATH =
		"com/liferay/site/buildings/site/initializer/internal/dependencies";

	private static final Log _log = LogFactoryUtil.getLog(
		BuildingsSiteInitializer.class);

	private Bundle _bundle;

	@Reference
	private DefaultDDMStructureHelper _defaultDDMStructureHelper;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	private final Map<String, String> _fileEntriesLinkMap = new HashMap<>();
	private final Map<String, String> _fileEntriesObjectMap = new HashMap<>();

	@Reference
	private FragmentsImporter _fragmentsImporter;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private Portal _portal;

	private ServiceContext _serviceContext;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.buildings.site.initializer)"
	)
	private ServletContext _servletContext;

	@Reference
	private UserLocalService _userLocalService;

}