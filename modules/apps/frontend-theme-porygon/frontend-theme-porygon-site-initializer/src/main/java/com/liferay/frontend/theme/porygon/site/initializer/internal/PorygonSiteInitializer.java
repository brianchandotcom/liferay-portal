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

package com.liferay.frontend.theme.porygon.site.initializer.internal;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryModel;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
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
 */
@Component(
	immediate = true,
	property = "site.initializer.key=" + PorygonSiteInitializer.KEY
)
public class PorygonSiteInitializer implements SiteInitializer {

	public static final String KEY = "porygon-site-initializer";

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
		return _THEME_NAME;
	}

	@Override
	public String getThumbnailSrc() {
		return _servletContext.getContextPath() + "/images/thumbnail.png";
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
		try {
			ServiceContext serviceContext = _createServiceContext(groupId);

			_updateLogo(serviceContext);
			_updateLookAndFeel(serviceContext);

			List<FileEntry> fileEntries = _addFileEntries(serviceContext);

			Map<String, String> fileEntriesMap = _getFileEntriesMap(
				fileEntries);

			List<FragmentEntry> fragmentEntries = _addFragmentEntries(
				fileEntries, serviceContext);

			Map<String, FragmentEntry> fragmentEntriesMap =
				_getFragmentEntriesMap(fragmentEntries);

			List<FragmentEntry> entryFragmentEntries = new ArrayList<>();

			entryFragmentEntries.add(fragmentEntriesMap.get("entry"));

			_addDisplayPageEntry(
				"Entry", entryFragmentEntries, _PATH + "/page_templates",
				"entry.jpg", serviceContext);

			_addApplicationDisplayTemplates(serviceContext);

			_addLayouts(_LAYOUT_NAMES, serviceContext);
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

	private void _addApplicationDisplayTemplates(ServiceContext serviceContext)
		throws Exception {

		Enumeration<URL> urls = _bundle.findEntries(
			_PATH + "/adt", "*.ftl", false);

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();

			String script = StringUtil.read(url.openStream());

			String fileName = FileUtil.getShortFileName(url.getPath());

			Map<Locale, String> nameMap = new HashMap<>();

			nameMap.put(LocaleUtil.getSiteDefault(), fileName);

			_ddmTemplateLocalService.addTemplate(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_portal.getClassNameId(AssetEntry.class.getName()), 0,
				_portal.getClassNameId(_PORTLET_DISPLAY_TEMPLATE_CLASS_NAME),
				nameMap, new HashMap<>(),
				DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY,
				DDMTemplateConstants.TEMPLATE_MODE_EDIT,
				TemplateConstants.LANG_TYPE_FTL, script, serviceContext);
		}
	}

	private LayoutPageTemplateEntry _addDisplayPageEntry(
			String name, List<FragmentEntry> fragmentEntries,
			String thumbnailPath, String thumbnailFileName,
			ServiceContext serviceContext)
		throws Exception {

		long previewFileEntryId = _getPreviewFileEntryId(
			thumbnailPath, thumbnailFileName, serviceContext);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(), 0,
				name, LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0,
				previewFileEntryId, WorkflowConstants.STATUS_APPROVED,
				serviceContext);

		long[] fragmentEntryIds = ListUtil.toLongArray(
			fragmentEntries, FragmentEntryModel::getFragmentEntryId);

		return _layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(), name,
				fragmentEntryIds, StringPool.BLANK, serviceContext);
	}

	private List<FileEntry> _addFileEntries(ServiceContext serviceContext)
		throws Exception {

		List<FileEntry> fileEntries = new ArrayList<>();

		Folder folder = _dlAppLocalService.addFolder(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _THEME_NAME,
			StringPool.BLANK, serviceContext);

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
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				folder.getFolderId(), fileName, null, fileName,
				StringPool.BLANK, StringPool.BLANK, bytes, serviceContext);

			fileEntries.add(fileEntry);
		}

		return fileEntries;
	}

	private List<FragmentEntry> _addFragmentEntries(
			List<FileEntry> fileEntries, ServiceContext serviceContext)
		throws Exception {

		List<FragmentEntry> fragmentEntries = new ArrayList<>();

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_THEME_NAME, null, serviceContext);

		Map<String, String> fileEntriesMap = _getFileEntriesMap(fileEntries);

		Enumeration<URL> urls = _bundle.findEntries(
			_PATH + "/fragments", "*.html", false);

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();

			String fileName = FileUtil.getShortFileName(url.getPath());
			String filePath = FileUtil.getPath(url.getPath());

			url = _bundle.getEntry(filePath + "/" + fileName);

			String html = StringUtil.replace(
				StringUtil.read(url.openStream()), StringPool.DOLLAR,
				StringPool.DOLLAR, fileEntriesMap);

			String shortFileName = FileUtil.getShortFileName(url.getPath());

			String fragmentEntryId = FileUtil.stripExtension(shortFileName);

			StringBundler sb = new StringBundler(4);

			sb.append(filePath);
			sb.append(StringPool.SLASH);
			sb.append(fragmentEntryId);
			sb.append(".css");

			URL cssURL = _bundle.getEntry(sb.toString());

			String css = StringUtil.replace(
				StringUtil.read(cssURL.openStream()), StringPool.DOLLAR,
				StringPool.DOLLAR, fileEntriesMap);

			String fragmentEntryName = StringUtil.upperCaseFirstLetter(
				fragmentEntryId);

			FragmentEntry fragmentEntry =
				_fragmentEntryLocalService.addFragmentEntry(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(),
					fragmentCollection.getFragmentCollectionId(),
					fragmentEntryName, css, html, StringPool.BLANK,
					_getPreviewFileEntryId(
						filePath, fragmentEntryId + ".jpg", serviceContext),
					WorkflowConstants.STATUS_APPROVED, serviceContext);

			fragmentEntries.add(fragmentEntry);
		}

		return fragmentEntries;
	}

	private List<Layout> _addLayouts(
			String[] layoutNames, ServiceContext serviceContext)
		throws Exception {

		List<Layout> layouts = new ArrayList<>();

		for (String layoutName : layoutNames) {
			Map<Locale, String> nameMap = new HashMap<>();

			nameMap.put(LocaleUtil.getSiteDefault(), layoutName);

			Layout layout = _layoutLocalService.addLayout(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, nameMap,
				new HashMap<>(), new HashMap<>(), new HashMap<>(),
				new HashMap<>(), "portlet", StringPool.BLANK, false,
				new HashMap<>(), serviceContext);

			layouts.add(layout);
		}

		return layouts;
	}

	private ServiceContext _createServiceContext(long groupId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Group group = _groupLocalService.getGroup(groupId);

		serviceContext.setCompanyId(group.getCompanyId());

		User user = _userLocalService.getUser(PrincipalThreadLocal.getUserId());

		Locale locale = LocaleUtil.getSiteDefault();

		serviceContext.setLanguageId(LanguageUtil.getLanguageId(locale));

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(user.getUserId());
		serviceContext.setTimeZone(user.getTimeZone());

		return serviceContext;
	}

	private Map<String, String> _getFileEntriesMap(List<FileEntry> fileEntries)
		throws PortalException {

		Map<String, String> fileEntriesMap = new HashMap<>();

		for (FileEntry fileEntry : fileEntries) {
			String fileEntryURL = DLUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
				false, false);

			fileEntriesMap.put(fileEntry.getFileName(), fileEntryURL);
		}

		return fileEntriesMap;
	}

	private Map<String, FragmentEntry> _getFragmentEntriesMap(
		List<FragmentEntry> fragmentEntries) {

		Map<String, FragmentEntry> fragmentEntriesMap = new HashMap<>();

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			fragmentEntriesMap.put(
				StringUtil.toLowerCase(fragmentEntry.getName()), fragmentEntry);
		}

		return fragmentEntriesMap;
	}

	private long _getPreviewFileEntryId(
			String filePath, String fileName, ServiceContext serviceContext)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append(filePath);
		sb.append(StringPool.SLASH);
		sb.append(fileName);

		URL url = _bundle.getEntry(sb.toString());

		if (url == null) {
			return 0;
		}

		Folder folder = _dlAppLocalService.getFolder(
			serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _THEME_NAME);

		byte[] bytes = null;

		try (InputStream is = url.openStream()) {
			bytes = FileUtil.getBytes(is);
		}

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			folder.getFolderId(), fileName, null, fileName, StringPool.BLANK,
			StringPool.BLANK, bytes, serviceContext);

		return fileEntry.getFileEntryId();
	}

	private void _updateLogo(ServiceContext serviceContext) throws Exception {
		URL url = _bundle.getEntry(_PATH + "/images/logo.png");

		byte[] bytes = null;

		try (InputStream is = url.openStream()) {
			bytes = FileUtil.getBytes(is);
		}

		_layoutSetLocalService.updateLogo(
			serviceContext.getScopeGroupId(), false, true, bytes);
	}

	private void _updateLookAndFeel(ServiceContext serviceContext)
		throws PortalException {

		Theme theme = _themeLocalService.fetchTheme(
			serviceContext.getCompanyId(), _THEME_ID);

		if (theme == null) {
			if (_log.isInfoEnabled()) {
				_log.info("No theme found for " + _THEME_ID);
			}

			return;
		}

		_layoutSetLocalService.updateLookAndFeel(
			serviceContext.getScopeGroupId(), false, _THEME_ID,
			StringPool.BLANK, StringPool.BLANK);
	}

	private static final String[] _LAYOUT_NAMES =
		{"Home", "Photography", "Science", "Reviews"};

	private static final String _PATH =
		"com/liferay/frontend/theme/porygon/site/initializer/internal" +
			"/dependencies";

	private static final String _PORTLET_DISPLAY_TEMPLATE_CLASS_NAME =
		"com.liferay.portlet.display.template.PortletDisplayTemplate";

	private static final String _THEME_ID = "porygon_WAR_porygontheme";

	private static final String _THEME_NAME = "Porygon";

	private static final Log _log = LogFactoryUtil.getLog(
		PorygonSiteInitializer.class);

	private Bundle _bundle;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.theme.porygon.site.initializer)"
	)
	private ServletContext _servletContext;

	@Reference
	private ThemeLocalService _themeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}