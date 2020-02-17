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
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
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
import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
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
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
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
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

			List<FragmentEntry> fragmentEntries = _addFragmentEntries(
				serviceContext);

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
				serviceContext);

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					Layout draftLayout = _layoutLocalService.fetchLayout(
						_portal.getClassNameId(Layout.class),
						personalLayout.getPlid());

					_configureFragmentEntryLink(
						serviceContext.getCompanyId(),
						serviceContext.getScopeGroupId(), draftLayout.getPlid(),
						carouselFragmentEntry.getFragmentEntryId(),
						carouselJournalArticle.getArticleId());

					_copyLayout(personalLayout);

					_layoutLocalService.updateStatus(
						serviceContext.getUserId(), personalLayout.getPlid(),
						WorkflowConstants.STATUS_APPROVED, serviceContext);

					return null;
				});

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

			TransactionCommitCallbackUtil.registerCallback(
				() -> {
					_copyLayout(businessLayout);

					_layoutLocalService.updateStatus(
						serviceContext.getUserId(), businessLayout.getPlid(),
						WorkflowConstants.STATUS_APPROVED, serviceContext);

					return null;
				});

			_addLayouts(
				businessLayout, _LAYOUT_NAMES_CHILDREN_BUSINESS,
				fragmentEntriesMap, serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new InitializationException(exception);
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

	private Map<String, String> _addCarouselFileEntriesMap(
			ServiceContext serviceContext)
		throws Exception {

		Map<String, String> fileEntriesMap = new HashMap<>();

		Folder folder = _dlAppLocalService.addFolder(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _THEME_NAME,
			StringPool.BLANK, serviceContext);

		Enumeration<URL> urls = _bundle.findEntries(
			_PATH + "/carousel", StringPool.STAR, false);

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

			fileEntriesMap.put(
				fileEntry.getFileName(),
				JSONFactoryUtil.looseSerialize(fileEntry));
		}

		return fileEntriesMap;
	}

	private JournalArticle _addCarouselJournalArticle(
			ServiceContext serviceContext)
		throws Exception {

		Class<?> clazz = getClass();

		_defaultDDMStructureHelper.addDDMStructures(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			_portal.getClassNameId(JournalArticle.class),
			clazz.getClassLoader(), _PATH + "/ddm/carousel.xml",
			serviceContext);

		URL carouselContentURL = _bundle.getEntry(
			_PATH + "/ddm/content/carousel.xml");

		Map<String, String> fileEntriesMap = _addCarouselFileEntriesMap(
			serviceContext);

		String content = StringUtil.replace(
			StringUtil.read(carouselContentURL.openStream()), StringPool.DOLLAR,
			StringPool.DOLLAR, fileEntriesMap);

		return _journalArticleLocalService.addArticle(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), 0,
			Collections.singletonMap(LocaleUtil.US, "Carousel"), null, content,
			"CAROUSEL", "CAROUSEL", serviceContext);
	}

	private void _addFileEntries(
			long fragmentCollectionId, long folderId,
			ServiceContext serviceContext)
		throws Exception {

		Enumeration<URL> urls = _bundle.findEntries(
			_PATH + "/images", StringPool.STAR, false);

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();

			byte[] bytes = null;

			try (InputStream is = url.openStream()) {
				bytes = FileUtil.getBytes(is);
			}

			String fileName = FileUtil.getShortFileName(url.getPath());

			_portletFileRepository.addPortletFileEntry(
				serviceContext.getScopeGroupId(), serviceContext.getUserId(),
				FragmentCollection.class.getName(), fragmentCollectionId,
				FragmentPortletKeys.FRAGMENT, folderId, bytes, fileName,
				MimeTypesUtil.getContentType(fileName), false);
		}
	}

	private List<FragmentEntry> _addFragmentEntries(
			ServiceContext serviceContext)
		throws Exception {

		List<FragmentEntry> fragmentEntries = new ArrayList<>();

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_THEME_NAME, null, serviceContext);

		_addFileEntries(
			fragmentCollection.getFragmentCollectionId(),
			fragmentCollection.getResourcesFolderId(), serviceContext);

		Enumeration<URL> urls = _bundle.findEntries(
			_PATH + "/fragments", "*.html", false);

		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();

			String fileName = FileUtil.getShortFileName(url.getPath());
			String filePath = FileUtil.getPath(url.getPath());

			url = _bundle.getEntry(filePath + "/" + fileName);

			String shortFileName = FileUtil.getShortFileName(url.getPath());

			String fragmentEntryId = FileUtil.stripExtension(shortFileName);

			String fragmentEntryName = StringUtil.upperCaseFirstLetter(
				fragmentEntryId);

			StringBundler sb = new StringBundler(4);

			sb.append(filePath);
			sb.append(StringPool.SLASH);
			sb.append(fragmentEntryId);
			sb.append(".css");

			URL cssURL = _bundle.getEntry(sb.toString());

			FragmentEntry fragmentEntry =
				_fragmentEntryLocalService.addFragmentEntry(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(),
					fragmentCollection.getFragmentCollectionId(), null,
					fragmentEntryName, StringUtil.read(cssURL.openStream()),
					StringUtil.read(url.openStream()), StringPool.BLANK,
					StringPool.BLANK, 0, FragmentConstants.TYPE_SECTION,
					WorkflowConstants.STATUS_APPROVED, serviceContext);

			long fragmentEntryPreviewFileEntryId = _getPreviewFileEntryId(
				FragmentPortletKeys.FRAGMENT, FragmentEntry.class.getName(),
				fragmentEntry.getFragmentEntryId(), filePath,
				fragmentEntryId + ".jpg", serviceContext);

			fragmentEntry = _fragmentEntryLocalService.updateFragmentEntry(
				fragmentEntry.getFragmentEntryId(),
				fragmentEntryPreviewFileEntryId);

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

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				layoutPageTemplateCollection.
					getLayoutPageTemplateCollectionId(),
				name, LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
				WorkflowConstants.STATUS_APPROVED, 0, serviceContext);

		long previewFileEntryId = _getPreviewFileEntryId(
			LayoutAdminPortletKeys.GROUP_PAGES,
			LayoutPageTemplateEntry.class.getName(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			thumbnailPath, thumbnailFileName, serviceContext);

		layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.updateLayoutPageTemplateEntry(
				layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
				previewFileEntryId);

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
			Map<Locale, String> nameMap = HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), layoutName
			).build();

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

			_layoutLocalService.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), new Date());

			layouts.add(layout);
		}

		return layouts;
	}

	private Layout _addParentLayout(
			String name, long layoutPageTemplateEntryId,
			ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(), name
		).build();

		return _layoutLocalService.addLayout(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(), false,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
			_portal.getClassNameId(LayoutPageTemplateEntry.class),
			layoutPageTemplateEntryId, nameMap, new HashMap<>(),
			new HashMap<>(), new HashMap<>(), new HashMap<>(), "content", null,
			false, false, new HashMap<>(), 0, serviceContext);
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

			PortletPreferences jxPortletPreferences =
				new PortletPreferencesImpl();

			jxPortletPreferences.setValue("articleId", articleId);
			jxPortletPreferences.setValue(
				"portletSetupPortletDecoratorId", "barebone");

			String portletId = PortletIdCodec.encode(
				JournalContentPortletKeys.JOURNAL_CONTENT,
				fragmentEntryLink.getNamespace());

			com.liferay.portal.kernel.model.PortletPreferences
				portletPreferences =
					_portletPreferencesLocalService.fetchPortletPreferences(
						0, PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid,
						portletId);

			if (portletPreferences == null) {
				_portletPreferencesLocalService.addPortletPreferences(
					companyId, 0, PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid,
					portletId, null,
					PortletPreferencesFactoryUtil.toXML(jxPortletPreferences));
			}
			else {
				_portletPreferencesLocalService.updatePreferences(
					0, PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portletId,
					jxPortletPreferences);
			}
		}
	}

	private void _copyLayout(Layout layout) throws Exception {
		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class), layout.getPlid());

		if (draftLayout != null) {
			_layoutCopyHelper.copyLayout(draftLayout, layout);
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
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
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
			String portletId, String className, long classPK, String path,
			String fileName, ServiceContext serviceContext)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append(path);
		sb.append(StringPool.SLASH);
		sb.append(fileName);

		URL url = _bundle.getEntry(sb.toString());

		if (url == null) {
			return 0;
		}

		Repository repository = _portletFileRepository.fetchPortletRepository(
			serviceContext.getScopeGroupId(), portletId);

		if (repository == null) {
			repository = _portletFileRepository.addPortletRepository(
				serviceContext.getScopeGroupId(), portletId, serviceContext);
		}

		String imageFileName =
			classPK + "_preview." + FileUtil.getExtension(url.getPath());

		byte[] bytes = null;

		try (InputStream is = url.openStream()) {
			bytes = FileUtil.getBytes(is);
		}

		FileEntry fileEntry = _portletFileRepository.addPortletFileEntry(
			serviceContext.getScopeGroupId(), serviceContext.getUserId(),
			className, classPK, portletId, repository.getDlFolderId(), bytes,
			imageFileName, MimeTypesUtil.getContentType(imageFileName), false);

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
			serviceContext.getScopeGroupId(), _THEME_ID, StringPool.BLANK,
			StringPool.BLANK);
	}

	private static final String[] _LAYOUT_NAMES_CHILDREN_ASSURANCE = {
		"Travel Insurance", "Home insurance", "Life insurance"
	};

	private static final String[] _LAYOUT_NAMES_CHILDREN_BUSINESS = {
		"Credit Cards for Business", "Assurance for Business"
	};

	private static final String[] _LAYOUT_NAMES_CHILDREN_CHECKING = {
		"All credit cards", "Check your eligibility",
		"Balance-transfer credit cards", "Purchase credit card"
	};

	private static final String[] _LAYOUT_NAMES_CHILDREN_LOANS = {
		"Mortgages", "All mortgage products", "Mortgate rates and charges"
	};

	private static final String[] _LAYOUT_NAMES_CHILDREN_PERSONAL = {
		"Checking and Credit Cards", "Savings and Investments",
		"Loans and Mortgages", "Assurance"
	};

	private static final String[] _LAYOUT_NAMES_CHILDREN_SAVINGS = {
		"Compare Savings accounts", "Everyday Saver",
		"Children's Instant Saver", "All interest rates"
	};

	private static final String[] _LAYOUT_NAMES_FRAGMENT_KEYS = {
		"features", "links", "news", "offerings", "video"
	};

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
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateCollectionLocalService
		_layoutPageTemplateCollectionLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

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