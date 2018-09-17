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

package com.liferay.frontend.theme.westeros.bank.site.initializer.internal;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.model.FragmentEntryModel;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.portlet.PortletPreferences;

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
	property = "site.initializer.key=" + WesterosBankSiteInitializer.KEY,
	service = SiteInitializer.class
)
public class WesterosBankSiteInitializer implements SiteInitializer {

	public static final String KEY = "westeros-bank-site-initializer";

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

			List<FragmentEntry> fragmentEntries = _addFragmentEntries(
				fileEntries, serviceContext);

			Map<String, FragmentEntry> fragmentEntriesMap =
				_getFragmentEntriesMap(fragmentEntries);

			LayoutPageTemplateCollection layoutPageTemplateCollection =
				_addLayoutPageTemplateCollection(serviceContext);

			List<FragmentEntry> personalFragmentEntries = new ArrayList<>();

			personalFragmentEntries.add(fragmentEntriesMap.get("carousel"));
			personalFragmentEntries.add(fragmentEntriesMap.get("features"));
			personalFragmentEntries.add(fragmentEntriesMap.get("news"));
			personalFragmentEntries.add(fragmentEntriesMap.get("offerings"));
			personalFragmentEntries.add(fragmentEntriesMap.get("links"));

			LayoutPageTemplateEntry personalLayoutPageTemplate =
				_addLayoutPageTemplateEntry(
					layoutPageTemplateCollection, "For You",
					personalFragmentEntries, _PATH + "/page_templates",
					"personal.jpg", serviceContext);

			Layout personalLayout = _addParentLayout(
				"For You",
				personalLayoutPageTemplate.getLayoutPageTemplateEntryId(),
				serviceContext);

			FragmentEntry carouselFragmentEntry = fragmentEntriesMap.get(
				"carousel");

			JournalArticle carouselJournalArticle = _addCarouselJournalArticle(
				fileEntries, serviceContext);

			_configureFragmentEntryLink(
				serviceContext.getCompanyId(), serviceContext.getScopeGroupId(),
				personalLayout.getPlid(),
				carouselFragmentEntry.getFragmentEntryId(),
				carouselJournalArticle.getArticleId());

			List<Layout> personalLayoutChildren = _addLayouts(
				personalLayout, _LAYOUT_NAMES_CHILDREN_PERSONAL,
				fragmentEntriesMap, serviceContext);

			_addLayouts(
				personalLayoutChildren.get(0), _LAYOUT_NAMES_CHILDREN_CHECKING,
				fragmentEntriesMap, serviceContext);

			_addLayouts(
				personalLayoutChildren.get(1), _LAYOUT_NAMES_CHILDREN_SAVINGS,
				fragmentEntriesMap, serviceContext);

			_addLayouts(
				personalLayoutChildren.get(2), _LAYOUT_NAMES_CHILDREN_LOANS,
				fragmentEntriesMap, serviceContext);

			_addLayouts(
				personalLayoutChildren.get(3), _LAYOUT_NAMES_CHILDREN_ASSURANCE,
				fragmentEntriesMap, serviceContext);

			List<FragmentEntry> businessFragmentEntries = new ArrayList<>();

			businessFragmentEntries.add(fragmentEntriesMap.get("video"));
			businessFragmentEntries.add(fragmentEntriesMap.get("links"));

			LayoutPageTemplateEntry businessLayoutPageTemplate =
				_addLayoutPageTemplateEntry(
					layoutPageTemplateCollection, "For Your Business",
					businessFragmentEntries, _PATH + "/page_templates",
					"business.jpg", serviceContext);

			Layout businessLayout = _addParentLayout(
				"For Your Business",
				businessLayoutPageTemplate.getLayoutPageTemplateEntryId(),
				serviceContext);

			_addLayouts(
				businessLayout, _LAYOUT_NAMES_CHILDREN_BUSINESS,
				fragmentEntriesMap, serviceContext);
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

	private JournalArticle _addCarouselJournalArticle(
			List<FileEntry> fileEntries, ServiceContext serviceContext)
		throws Exception {

		Class<?> clazz = getClass();

		_defaultDDMStructureHelper.addDDMStructures(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			_portal.getClassNameId(JournalArticle.class),
			clazz.getClassLoader(), _PATH + "/ddm/carousel.xml",
			serviceContext);

		URL carouselContentURL = _bundle.getEntry(
			_PATH + "/ddm/content/carousel.xml");

		Map<String, String> fileEntriesMap = new HashMap<>();

		for (FileEntry fileEntry : fileEntries) {
			fileEntriesMap.put(
				fileEntry.getFileName(),
				JSONFactoryUtil.looseSerialize(fileEntry));
		}

		String content = StringUtil.replace(
			StringUtil.read(carouselContentURL.openStream()), StringPool.DOLLAR,
			StringPool.DOLLAR, fileEntriesMap);

		return _journalArticleLocalService.addArticle(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), 0,
			Collections.singletonMap(LocaleUtil.US, "Carousel"), null, content,
			"CAROUSEL", "CAROUSEL", serviceContext);
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

			String fragmentEntryName = StringUtil.upperCaseFirstLetter(
				fragmentEntryId);

			FragmentEntry fragmentEntry =
				_fragmentEntryLocalService.addFragmentEntry(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(),
					fragmentCollection.getFragmentCollectionId(),
					fragmentEntryName, StringPool.BLANK, html, StringPool.BLANK,
					_getPreviewFileEntryId(
						filePath, fragmentEntryId + ".jpg", serviceContext),
					WorkflowConstants.STATUS_APPROVED, serviceContext);

			fragmentEntries.add(fragmentEntry);
		}

		return fragmentEntries;
	}

	private LayoutPageTemplateCollection _addLayoutPageTemplateCollection(
			ServiceContext serviceContext)
		throws PortalException {

		return _layoutPageTemplateCollectionLocalService.
			addLayoutPageTemplateCollection(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_THEME_NAME, _THEME_NAME, serviceContext);
	}

	private LayoutPageTemplateEntry _addLayoutPageTemplateEntry(
			LayoutPageTemplateCollection layoutPageTemplateCollection,
			String name, List<FragmentEntry> fragmentEntries,
			String thumbnailPath, String thumbnailFileName,
			ServiceContext serviceContext)
		throws Exception {

		long layoutPageTemplateCollectionId =
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId();
		long previewFileEntryId = _getPreviewFileEntryId(
			thumbnailPath, thumbnailFileName, serviceContext);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				layoutPageTemplateCollectionId, name,
				LayoutPageTemplateEntryTypeConstants.TYPE_BASIC, 0,
				previewFileEntryId, WorkflowConstants.STATUS_APPROVED,
				serviceContext);

		long[] fragmentEntryIds = ListUtil.toLongArray(
			fragmentEntries, FragmentEntryModel::getFragmentEntryId);

		return _layoutPageTemplateEntryLocalService.
			updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(), name,
				fragmentEntryIds, StringPool.BLANK, serviceContext);
	}

	private List<Layout> _addLayouts(
			Layout parentLayout, String[] layoutNames,
			Map<String, FragmentEntry> fragmentEntriesMap,
			ServiceContext serviceContext)
		throws Exception {

		List<Layout> layouts = new ArrayList<>();

		Random random = new Random();

		for (String layoutName : layoutNames) {
			Map<Locale, String> nameMap = new HashMap<>();

			nameMap.put(LocaleUtil.getSiteDefault(), layoutName);

			Layout layout = _layoutLocalService.addLayout(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				false, parentLayout.getLayoutId(), nameMap, new HashMap<>(),
				new HashMap<>(), new HashMap<>(), new HashMap<>(), "content",
				StringPool.BLANK, false, new HashMap<>(), serviceContext);

			int fragmentKeyId = random.nextInt(
				_LAYOUT_NAMES_FRAGMENT_KEYS.length);

			FragmentEntry fragmentEntry = fragmentEntriesMap.get(
				_LAYOUT_NAMES_FRAGMENT_KEYS[fragmentKeyId]);

			_fragmentEntryLinkLocalService.updateFragmentEntryLinks(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_portal.getClassNameId(Layout.class), layout.getPlid(),
				new long[] {fragmentEntry.getFragmentEntryId()},
				StringPool.BLANK, serviceContext);

			layouts.add(layout);
		}

		return layouts;
	}

	private Layout _addParentLayout(
			String name, long layoutPageTemplateEntryId,
			ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), name);

		UnicodeProperties typeSettingsProperties = new UnicodeProperties();

		typeSettingsProperties.put(
			"layoutPageTemplateEntryId",
			String.valueOf(layoutPageTemplateEntryId));

		return _layoutLocalService.addLayout(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, nameMap, new HashMap<>(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), "content",
			typeSettingsProperties.toString(), false, new HashMap<>(),
			serviceContext);
	}

	private void _configureFragmentEntryLink(
			long companyId, long groupId, long plid, long fragmentEntryId,
			String articleId)
		throws Exception {

		List<FragmentEntryLink> fragmentEntryLinks =
			_fragmentEntryLinkLocalService.getFragmentEntryLinks(
				groupId, _portal.getClassNameId(Layout.class), plid);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			if (fragmentEntryLink.getFragmentEntryId() != fragmentEntryId) {
				continue;
			}

			PortletPreferences portletPreferences =
				new PortletPreferencesImpl();

			portletPreferences.setValue("articleId", articleId);

			portletPreferences.setValue(
				"portletSetupPortletDecoratorId", "barebone");

			String portletId = PortletIdCodec.encode(
				JournalContentPortletKeys.JOURNAL_CONTENT,
				fragmentEntryLink.getNamespace());

			_portletPreferencesLocalService.addPortletPreferences(
				companyId, 0, PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				_portal.getControlPanelPlid(companyId), portletId, null,
				PortletPreferencesFactoryUtil.toXML(portletPreferences));
		}
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

		Map<String, String> fileEntriesPathMap = new HashMap<>();

		for (FileEntry fileEntry : fileEntries) {
			String fileEntryURL = DLUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
				false, false);

			fileEntriesPathMap.put(fileEntry.getFileName(), fileEntryURL);
		}

		return fileEntriesPathMap;
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
			String path, String fileName, ServiceContext serviceContext)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append(path);
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

	private static final String[] _LAYOUT_NAMES_CHILDREN_ASSURANCE =
		{"Travel Insurance", "Home insurance", "Life insurance"};

	private static final String[] _LAYOUT_NAMES_CHILDREN_BUSINESS =
		{"Credit Cards for Business", "Assurance for Business"};

	private static final String[] _LAYOUT_NAMES_CHILDREN_CHECKING = {
		"All credit cards", "Check your eligibility",
		"Balance-transfer credit cards", "Purchase credit card"
	};

	private static final String[] _LAYOUT_NAMES_CHILDREN_LOANS =
		{"Mortgages", "All mortgage products", "Mortgate rates and charges"};

	private static final String[] _LAYOUT_NAMES_CHILDREN_PERSONAL = {
		"Checking and Credit Cards", "Savings and Investments",
		"Loans and Mortgages", "Assurance"
	};

	private static final String[] _LAYOUT_NAMES_CHILDREN_SAVINGS = {
		"Compare Savings accounts", "Everyday Saver",
		"Children's Instant Saver", "All interest rates"
	};

	private static final String[] _LAYOUT_NAMES_FRAGMENT_KEYS =
		{"features", "links", "news", "offerings", "video"};

	private static final String _PATH =
		"com/liferay/frontend/theme/westeros/bank/site/initializer/internal" +
			"/dependencies";

	private static final String _THEME_ID =
		"westerosbank_WAR_westerosbanktheme";

	private static final String _THEME_NAME = "Westeros Bank";

	private static final Log _log = LogFactoryUtil.getLog(
		WesterosBankSiteInitializer.class);

	private Bundle _bundle;

	@Reference
	private DefaultDDMStructureHelper _defaultDDMStructureHelper;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

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
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.frontend.theme.westeros.bank.site.initializer)"
	)
	private ServletContext _servletContext;

	@Reference
	private ThemeLocalService _themeLocalService;

	@Reference
	private UserLocalService _userLocalService;

}