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

import com.liferay.document.library.util.DLURLHelper;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.fragment.contributor.FragmentCollectionContributorTracker;
import com.liferay.fragment.importer.FragmentsImporter;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererTracker;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
import com.liferay.layout.page.template.service.LayoutPageTemplateCollectionLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.site.buildings.site.initializer.internal.util.ImagesImporterUtil;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.navigation.menu.item.layout.constants.SiteNavigationMenuItemTypeConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalService;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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

			_addFragments();
			_addImages();

			_addResourceClassNames();

			_addJournalArticleDDMStructures();
			_addJournalArticles();

			_addLayoutPageTemplateEntry();
			_addLayouts();

			_updateLookAndFeel();
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

	private void _addComments(FragmentEntryLink fragmentEntryLink)
		throws PortalException {

		long parentCommentId1 = _commentManager.addComment(
			fragmentEntryLink.getUserId(), fragmentEntryLink.getGroupId(),
			FragmentEntryLink.class.getName(),
			fragmentEntryLink.getFragmentEntryLinkId(),
			"Can we add some borders to this? It is ugly.",
			className -> new ServiceContext());

		_commentManager.addComment(
			fragmentEntryLink.getUserId(), FragmentEntryLink.class.getName(),
			fragmentEntryLink.getFragmentEntryLinkId(),
			fragmentEntryLink.getUserName(), parentCommentId1,
			String.valueOf(Math.random()), "Borders are overrated.",
			className -> new ServiceContext());

		_commentManager.addComment(
			fragmentEntryLink.getUserId(), FragmentEntryLink.class.getName(),
			fragmentEntryLink.getFragmentEntryLinkId(),
			fragmentEntryLink.getUserName(), parentCommentId1,
			String.valueOf(Math.random()), "Sure, we can add 2px border.",
			className -> new ServiceContext());

		long parentCommentId2 = _commentManager.addComment(
			fragmentEntryLink.getUserId(), fragmentEntryLink.getGroupId(),
			FragmentEntryLink.class.getName(),
			fragmentEntryLink.getFragmentEntryLinkId(),
			"Hmmm, this looks too small. Make it bigger.",
			className -> new ServiceContext());

		_commentManager.addComment(
			fragmentEntryLink.getUserId(), FragmentEntryLink.class.getName(),
			fragmentEntryLink.getFragmentEntryLinkId(),
			fragmentEntryLink.getUserName(), parentCommentId2,
			String.valueOf(Math.random()),
			"The size is ok, do not worry about it.",
			className -> new ServiceContext());

		long parentCommentId3 = _commentManager.addComment(
			fragmentEntryLink.getUserId(), fragmentEntryLink.getGroupId(),
			FragmentEntryLink.class.getName(),
			fragmentEntryLink.getFragmentEntryLinkId(),
			"This does not fit our guidelines. Please fix it.",
			className -> new ServiceContext());

		_commentManager.addComment(
			fragmentEntryLink.getUserId(), FragmentEntryLink.class.getName(),
			fragmentEntryLink.getFragmentEntryLinkId(),
			fragmentEntryLink.getUserName(), parentCommentId3,
			String.valueOf(Math.random()), "You are right. Fixed!",
			className -> new ServiceContext());

		_commentManager.updateComment(
			fragmentEntryLink.getUserId(), FragmentEntryLink.class.getName(),
			fragmentEntryLink.getFragmentEntryLinkId(), parentCommentId3,
			String.valueOf(Math.random()),
			"This does not fit our guidelines. Please fix it.",
			className -> {
				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setWorkflowAction(
					WorkflowConstants.ACTION_SAVE_DRAFT);

				return serviceContext;
			});
	}

	private FragmentEntryLink _addFragmentEntryLink(
		long plid, String fragmentEntryKey, String editableValues) {

		FragmentEntry fragmentEntry = _getFragmentEntry(fragmentEntryKey);

		FragmentRenderer fragmentRenderer =
			_fragmentRendererTracker.getFragmentRenderer(fragmentEntryKey);

		if ((fragmentEntry == null) && (fragmentRenderer == null)) {
			return null;
		}

		try {
			if (fragmentEntry != null) {
				String contributedRendererKey = null;

				if (fragmentEntry.getFragmentEntryId() == 0) {
					contributedRendererKey = fragmentEntryKey;
				}

				return _fragmentEntryLinkLocalService.addFragmentEntryLink(
					_serviceContext.getUserId(),
					_serviceContext.getScopeGroupId(), 0,
					fragmentEntry.getFragmentEntryId(),
					_portal.getClassNameId(Layout.class), plid,
					fragmentEntry.getCss(), fragmentEntry.getHtml(),
					fragmentEntry.getJs(), fragmentEntry.getConfiguration(),
					editableValues, StringPool.BLANK, 0, contributedRendererKey,
					_serviceContext);
			}

			return _fragmentEntryLinkLocalService.addFragmentEntryLink(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				0, 0, _portal.getClassNameId(Layout.class), plid,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, editableValues, StringPool.BLANK, 0,
				fragmentEntryKey, _serviceContext);
		}
		catch (Exception e) {
		}

		return null;
	}

	private void _addFragments() throws Exception {
		URL url = _bundle.getEntry("/fragments.zip");

		File file = FileUtil.createTempFile(url.openStream());

		_fragmentsImporter.importFile(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(), 0,
			file, false);
	}

	private void _addImages() throws Exception {
		URL url = _bundle.getEntry("/images.zip");

		File file = FileUtil.createTempFile(url.openStream());

		_fileEntries = ImagesImporterUtil.importFile(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			file);

		for (FileEntry fileEntry : _fileEntries) {
			_resourcesMap.put(
				fileEntry.getFileName(),
				_dlURLHelper.getPreviewURL(
					fileEntry, fileEntry.getFileVersion(), null,
					StringPool.BLANK, false, false));
		}
	}

	private void _addJournalArticleDDMStructures() throws Exception {
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

	private void _addJournalArticles() throws Exception {
		JSONArray journalArticlesJSONArray = JSONFactoryUtil.createJSONArray(
			_readFile("/journal/journal_articles.json"));

		Map<String, String> fileEntriesMap = _getFileEntriesMap();

		for (int i = 0; i < journalArticlesJSONArray.length(); i++) {
			JSONObject jsonObject = journalArticlesJSONArray.getJSONObject(i);

			String content = StringUtil.replace(
				_readFile(jsonObject.getString("contentPath")),
				StringPool.DOLLAR, StringPool.DOLLAR, fileEntriesMap);

			Calendar calendar = CalendarFactoryUtil.getCalendar(
				_serviceContext.getTimeZone());

			int displayDateMonth = calendar.get(Calendar.MONTH);
			int displayDateDay = calendar.get(Calendar.DAY_OF_MONTH);
			int displayDateYear = calendar.get(Calendar.YEAR);
			int displayDateHour = calendar.get(Calendar.HOUR_OF_DAY);
			int displayDateMinute = calendar.get(Calendar.MINUTE);

			JournalArticle journalArticle =
				_journalArticleLocalService.addArticle(
					_serviceContext.getUserId(),
					_serviceContext.getScopeGroupId(),
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					JournalArticleConstants.CLASSNAME_ID_DEFAULT, 0,
					jsonObject.getString("articleId"), false, 1,
					Collections.singletonMap(
						LocaleUtil.US, jsonObject.getString("name")),
					null, content, jsonObject.getString("ddmStructureKey"),
					null, null, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute, 0, 0,
					0, 0, 0, true, 0, 0, 0, 0, 0, true, true, false, null, null,
					null, null, _serviceContext);

			_resourcesMap.put(
				journalArticle.getArticleId(),
				String.valueOf(journalArticle.getResourcePrimKey()));
		}
	}

	private Layout _addLayout(
			long parentLayoutId, String name, String type, String dataPath)
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), name);

		Layout layout = _layoutLocalService.addLayout(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			false, parentLayoutId, nameMap, new HashMap<>(), new HashMap<>(),
			new HashMap<>(), new HashMap<>(), type, null, false, false,
			new HashMap<>(), _serviceContext);

		if (Validator.isNotNull(dataPath)) {
			Layout draftLayout = _layoutLocalService.fetchLayout(
				_portal.getClassNameId(Layout.class), layout.getPlid());

			_layoutPageTemplateStructureLocalService.
				addLayoutPageTemplateStructure(
					_serviceContext.getUserId(),
					_serviceContext.getScopeGroupId(),
					_portal.getClassNameId(Layout.class), draftLayout.getPlid(),
					_parseLayoutContent(
						draftLayout.getPlid(),
						_readFile("/layout-content/" + dataPath)),
					_serviceContext);
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				_copyLayout(layout);

				return null;
			});

		return layout;
	}

	private void _addLayoutPageTemplateEntry() throws Exception {
		LayoutPageTemplateCollection layoutPageTemplateCollection =
			_layoutPageTemplateCollectionLocalService.
				addLayoutPageTemplateCollection(
					_serviceContext.getUserId(),
					_serviceContext.getScopeGroupId(), "Liferay",
					StringPool.BLANK, _serviceContext);

		_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
			_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
			layoutPageTemplateCollection.getLayoutPageTemplateCollectionId(),
			"Main Template", LayoutPageTemplateEntryTypeConstants.TYPE_BASIC,
			WorkflowConstants.STATUS_APPROVED, _serviceContext);
	}

	private void _addLayouts() throws Exception {
		_addLayout(
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Home",
			LayoutConstants.TYPE_CONTENT, "home.json");

		Layout productsLayout = _addLayout(
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Products", "node",
			StringPool.BLANK);

		Layout digitalExperiencePlatformLayout = _addLayout(
			productsLayout.getLayoutId(), "Digital Experience Platform",
			LayoutConstants.TYPE_CONTENT, "digital-experience-platform.json");

		Layout overviewLayout = _addLayout(
			digitalExperiencePlatformLayout.getLayoutId(), "Overview",
			LayoutConstants.TYPE_CONTENT, StringPool.BLANK);

		Layout featuresLayout = _addLayout(
			digitalExperiencePlatformLayout.getLayoutId(), "Features",
			LayoutConstants.TYPE_CONTENT, StringPool.BLANK);

		Layout keyBenefitsLayout = _addLayout(
			digitalExperiencePlatformLayout.getLayoutId(), "Key Benefits",
			LayoutConstants.TYPE_CONTENT, StringPool.BLANK);

		Layout whatIsNewLayout = _addLayout(
			digitalExperiencePlatformLayout.getLayoutId(), "What is New",
			LayoutConstants.TYPE_CONTENT, StringPool.BLANK);

		_addSiteNavigationMenus(
			"DXP Secondary", overviewLayout, featuresLayout, keyBenefitsLayout,
			whatIsNewLayout);

		Layout commerceLayout = _addLayout(
			productsLayout.getLayoutId(), "Commerce",
			LayoutConstants.TYPE_CONTENT, "commerce.json");

		Layout commerceDemoLayout = _addLayout(
			commerceLayout.getLayoutId(), "Commerce Demo",
			LayoutConstants.TYPE_CONTENT, StringPool.BLANK);

		featuresLayout = _addLayout(
			commerceLayout.getLayoutId(), "Features",
			LayoutConstants.TYPE_CONTENT, StringPool.BLANK);

		Layout newsLayout = _addLayout(
			commerceLayout.getLayoutId(), "News", LayoutConstants.TYPE_CONTENT,
			StringPool.BLANK);

		Layout analyticsCloudLayout = _addLayout(
			productsLayout.getLayoutId(), "Analytics Cloud",
			LayoutConstants.TYPE_CONTENT, "analytics-cloud.json");

		_addSiteNavigationMenus(
			"Commerce Secondary", commerceDemoLayout, featuresLayout,
			newsLayout, analyticsCloudLayout);

		_addLayout(
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Solutions",
			LayoutConstants.TYPE_CONTENT, "solutions.json");

		_addLayout(
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "News",
			LayoutConstants.TYPE_CONTENT, "news.json");
	}

	private void _addResourceClassNames() {
		_resourcesMap.put(
			JournalArticle.class.getName(),
			String.valueOf(
				_portal.getClassNameId(JournalArticle.class.getName())));
	}

	private void _addSiteNavigationMenus(String name, Layout... layouts)
		throws Exception {

		SiteNavigationMenu siteNavigationMenu =
			_siteNavigationMenuLocalService.addSiteNavigationMenu(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				name, _serviceContext);

		SiteNavigationMenuItemType siteNavigationMenuItemType =
			_siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
				SiteNavigationMenuItemTypeConstants.LAYOUT);

		for (Layout layout : layouts) {
			_siteSiteNavigationMenuItemLocalService.addSiteNavigationMenuItem(
				_serviceContext.getUserId(), _serviceContext.getScopeGroupId(),
				siteNavigationMenu.getSiteNavigationMenuId(), 0,
				SiteNavigationMenuItemTypeConstants.LAYOUT,
				siteNavigationMenuItemType.getTypeSettingsFromLayout(layout),
				_serviceContext);
		}
	}

	private void _copyLayout(Layout layout) throws Exception {
		Layout draftLayout = _layoutLocalService.fetchLayout(
			_portal.getClassNameId(Layout.class), layout.getPlid());

		if (draftLayout != null) {
			_layoutCopyHelper.copyLayout(draftLayout, layout);
		}

		_layoutLocalService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			new Date());
	}

	private void _createServiceContext(long groupId) throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		User user = _userLocalService.getUser(PrincipalThreadLocal.getUserId());

		Locale locale = LocaleUtil.getSiteDefault();

		serviceContext.setLanguageId(LanguageUtil.getLanguageId(locale));

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		_serviceContext = serviceContext;
	}

	private Map<String, String> _getFileEntriesMap() {
		Map<String, String> fileEntriesMap = new HashMap<>();

		for (FileEntry fileEntry : _fileEntries) {
			fileEntriesMap.put(
				fileEntry.getFileName(),
				JSONFactoryUtil.looseSerialize(fileEntry));
		}

		return fileEntriesMap;
	}

	private FragmentEntry _getFragmentEntry(String fragmentEntryKey) {
		FragmentEntry fragmentEntry =
			_fragmentEntryLocalService.fetchFragmentEntry(
				_serviceContext.getScopeGroupId(), fragmentEntryKey);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		Map<String, FragmentEntry> fragmentEntries =
			_fragmentCollectionContributorTracker.getFragmentEntries();

		return fragmentEntries.get(fragmentEntryKey);
	}

	private String _parseLayoutContent(long plid, String data)
		throws Exception {

		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject(data);

		JSONArray structureJSONArray = dataJSONObject.getJSONArray("structure");

		for (int i = 0; i < structureJSONArray.length(); i++) {
			JSONObject rowJSONObject = structureJSONArray.getJSONObject(i);

			JSONArray columnsJSONArray = rowJSONObject.getJSONArray("columns");

			for (int j = 0; j < columnsJSONArray.length(); j++) {
				JSONObject columnJSONObject = columnsJSONArray.getJSONObject(j);

				JSONArray fragmentEntriesJSONArray =
					columnJSONObject.getJSONArray("fragmentEntries");

				JSONArray fragmentEntryLinkIdsJSONArray =
					JSONFactoryUtil.createJSONArray();

				for (int k = 0; k < fragmentEntriesJSONArray.length(); k++) {
					JSONObject fragmentEntryJSONObject =
						fragmentEntriesJSONArray.getJSONObject(k);

					String fragmentEntryKey = fragmentEntryJSONObject.getString(
						"fragmentEntryKey");

					String editableValues = fragmentEntryJSONObject.getString(
						"editableValues");

					editableValues = StringUtil.replace(
						editableValues, StringPool.DOLLAR, StringPool.DOLLAR,
						_resourcesMap);

					FragmentEntryLink fragmentEntryLink = _addFragmentEntryLink(
						plid, fragmentEntryKey, editableValues);

					if (fragmentEntryLink != null) {
						_addComments(fragmentEntryLink);

						fragmentEntryLinkIdsJSONArray.put(
							fragmentEntryLink.getFragmentEntryLinkId());
					}
				}

				columnJSONObject.remove("fragmentEntries");

				columnJSONObject.put(
					"fragmentEntryLinkIds", fragmentEntryLinkIdsJSONArray);
			}
		}

		return StringUtil.replace(
			dataJSONObject.toString(), StringPool.DOLLAR, StringPool.DOLLAR,
			_resourcesMap);
	}

	private String _readFile(String fileName) throws IOException {
		Class<?> clazz = getClass();

		return StringUtil.read(clazz.getClassLoader(), _PATH + fileName);
	}

	private void _updateLookAndFeel() throws Exception {
		LayoutSet layoutSet = _layoutSetLocalService.fetchLayoutSet(
			_serviceContext.getScopeGroupId(), false);

		UnicodeProperties settingsProperties =
			layoutSet.getSettingsProperties();

		settingsProperties.setProperty(
			"lfr-theme:regular:show-footer", Boolean.FALSE.toString());
		settingsProperties.setProperty(
			"lfr-theme:regular:show-header", Boolean.FALSE.toString());
		settingsProperties.setProperty(
			"lfr-theme:regular:show-header-search", Boolean.FALSE.toString());

		_layoutSetLocalService.updateSettings(
			_serviceContext.getScopeGroupId(), false,
			settingsProperties.toString());

		_layoutSetLocalService.updateLookAndFeel(
			_serviceContext.getScopeGroupId(), false, layoutSet.getThemeId(),
			layoutSet.getColorSchemeId(), _readFile("/layout-set/custom.css"));
	}

	private static final String _NAME = "Buildings";

	private static final String _PATH =
		"com/liferay/site/buildings/site/initializer/internal/dependencies";

	private static final Log _log = LogFactoryUtil.getLog(
		BuildingsSiteInitializer.class);

	private Bundle _bundle;

	@Reference
	private CommentManager _commentManager;

	@Reference
	private DefaultDDMStructureHelper _defaultDDMStructureHelper;

	@Reference
	private DLURLHelper _dlURLHelper;

	private List<FileEntry> _fileEntries;

	@Reference
	private FragmentCollectionContributorTracker
		_fragmentCollectionContributorTracker;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentRendererTracker _fragmentRendererTracker;

	@Reference
	private FragmentsImporter _fragmentsImporter;

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

	private final Map<String, String> _resourcesMap = new HashMap<>();
	private ServiceContext _serviceContext;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.buildings.site.initializer)"
	)
	private ServletContext _servletContext;

	@Reference
	private SiteNavigationMenuItemTypeRegistry
		_siteNavigationMenuItemTypeRegistry;

	@Reference
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

	@Reference
	private SiteNavigationMenuItemLocalService
		_siteSiteNavigationMenuItemLocalService;

	@Reference
	private UserLocalService _userLocalService;

}