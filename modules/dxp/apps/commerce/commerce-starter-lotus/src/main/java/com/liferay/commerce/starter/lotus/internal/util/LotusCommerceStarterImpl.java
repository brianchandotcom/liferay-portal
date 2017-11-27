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

package com.liferay.commerce.starter.lotus.internal.util;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.commerce.product.demo.data.creator.CPDemoDataCreator;
import com.liferay.commerce.product.service.CPGroupLocalService;
import com.liferay.commerce.product.util.CommerceStarter;
import com.liferay.commerce.product.util.CommerceStarterRegistry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.InputStream;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.starter.key=" + LotusCommerceStarterImpl.KEY,
		"commerce.starter.order:Integer=10"
	},
	service = CommerceStarter.class
)
public class LotusCommerceStarterImpl implements CommerceStarter {

	public static final String JOURNAL_CONTENT_PORTLET_NAME =
		"com_liferay_journal_content_web_portlet_JournalContentPortlet";

	public static final String KEY = "lotus";

	public static final String KEY_END_INTERCEPTOR = "%]";

	public static final String KEY_START_INTERCEPTOR = "\\[%";

	@Activate
	public void activate() {
		_fileEntries = new HashMap<>();
	}

	@Override
	public void create(HttpServletRequest httpServletRequest) throws Exception {
		ServiceContext serviceContext =
			_commerceStarterLotusHelper.getServiceContext(httpServletRequest);

		init(serviceContext);

		JSONArray journalArticleJSONArray = getJournalArticleJSONArray();

		long classNameId = _portal.getClassNameId(JournalArticle.class);

		createJournalArticles(
			journalArticleJSONArray, classNameId, httpServletRequest,
			serviceContext);

		JSONObject sampleDataJSONObject = getSampleDataJSONObject();

		updateLookAndFeel(
			sampleDataJSONObject, httpServletRequest, serviceContext);

		JSONArray layoutsJSONArray = sampleDataJSONObject.getJSONArray(
			"layouts");

		createLayouts(layoutsJSONArray, serviceContext);

		_cpGroupLocalService.addCPGroup(serviceContext);

		if (hasSampleData(httpServletRequest)) {
			_cpDemoDataCreator.init();
			_cpDemoDataCreator.create(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				true);
		}
	}

	@Override
	public String getDescription(HttpServletRequest httpServletRequest) {
		return StringPool.BLANK;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(HttpServletRequest httpServletRequest) {
		Theme theme = _commerceStarterLotusHelper.fetchTheme(
			httpServletRequest);

		if (theme != null) {
			return theme.getName();
		}

		return StringPool.BLANK;
	}

	public JSONObject getSampleDataJSONObject() throws Exception {
		Class<?> clazz = getClass();

		String sampleDataPath =
			"com/liferay/commerce/starter/lotus/internal/dependencies" +
				"/sample-data.json";

		String sampleDataJSON = StringUtil.read(
			clazz.getClassLoader(), sampleDataPath, false);

		return JSONFactoryUtil.createJSONObject(sampleDataJSON);
	}

	@Override
	public boolean hasSampleData(HttpServletRequest httpServletRequest) {
		return ParamUtil.getBoolean(httpServletRequest, "sampleData");
	}

	@Override
	public boolean isActive(HttpServletRequest httpServletRequest) {
		Theme theme = _commerceStarterLotusHelper.fetchTheme(
			httpServletRequest);

		if (theme == null) {
			return false;
		}

		return true;
	}

	@Override
	public void renderPreview(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		CommerceStarter commerceStarter =
			_commerceStarterRegistry.getCommerceStarter(
				LotusCommerceStarterImpl.KEY);

		httpServletRequest.setAttribute(
			"render.jsp-commerceStarter", commerceStarter);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/render.jsp");
	}

	protected void addDDMStructure(
			long classNameId, String key, ServiceContext serviceContext)
		throws Exception {

		Class<?> clazz = getClass();

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			serviceContext.getScopeGroupId(), classNameId, key, true);

		if (ddmStructure == null) {
			String ddmStructureFileName = getDDMStructureFileName(key);

			_defaultDDMStructureHelper.addDDMStructures(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				classNameId, clazz.getClassLoader(), ddmStructureFileName,
				serviceContext);
		}
	}

	protected void addLayoutPortlets(
			JSONArray portletsJSONArray, LayoutTypePortlet layoutTypePortlet,
			Layout layout, ServiceContext serviceContext)
		throws Exception {

		for (int i = 0; i < portletsJSONArray.length(); i++) {
			JSONObject portletJSONObject = portletsJSONArray.getJSONObject(i);

			String layoutColumnId = portletJSONObject.getString(
				"layoutColumnId");
			int layoutColumnPos = portletJSONObject.getInt("layoutColumnPos");
			String portletName = portletJSONObject.getString("portletName");
			String articleId = portletJSONObject.getString("articleId");

			String portletId = layoutTypePortlet.addPortletId(
				serviceContext.getUserId(), portletName, layoutColumnId,
				layoutColumnPos, false);

			JSONObject portletPreferencesJSONObject =
				portletJSONObject.getJSONObject("portletPreferences");

			PortletPreferencesIds portletPreferencesIds =
				PortletPreferencesFactoryUtil.getPortletPreferencesIds(
					layout.getGroupId(), 0, layout, portletId, false);

			PortletPreferences portletSetup =
				_portletPreferencesLocalService.getPreferences(
					portletPreferencesIds);

			if (portletPreferencesJSONObject != null) {
				Iterator<String> iterator = portletPreferencesJSONObject.keys();

				while (iterator.hasNext()) {
					String key = iterator.next();
				}
			}

			JournalArticle journalArticle =
				_journalArticleLocalService.fetchArticle(
					serviceContext.getScopeGroupId(), articleId);

			AssetEntry assetEntry = null;

			if (journalArticle != null) {
				assetEntry = _assetEntryLocalService.getEntry(
					JournalArticle.class.getName(),
					journalArticle.getResourcePrimKey());
			}

			if (portletName.equals(JOURNAL_CONTENT_PORTLET_NAME)) {
				portletSetup.setValue(
					"ddmTemplateKey", journalArticle.getDDMTemplateKey());

				if (assetEntry != null) {
					portletSetup.setValue(
						"assetEntryId",
						String.valueOf(assetEntry.getEntryId()));
				}

				portletSetup.setValue(
					"groupId",
					String.valueOf(serviceContext.getScopeGroupId()));
				portletSetup.setValue(
					"articleId", journalArticle.getArticleId());
			}

			portletSetup.store();
		}
	}

	protected void createJournalArticles(
			JSONArray journalArticleJSONArray, long classNameId,
			HttpServletRequest httpServletRequest,
			ServiceContext serviceContext)
		throws Exception {

		for (int i = 0; i < journalArticleJSONArray.length(); i++) {
			JSONObject journalArticleJSONObject =
				journalArticleJSONArray.getJSONObject(i);

			String ddmStructureKey = journalArticleJSONObject.getString(
				"ddmStructureKey");
			String ddmTemplateKey = journalArticleJSONObject.getString(
				"ddmTemplateKey");
			String articleId = journalArticleJSONObject.getString("articleId");
			String title = journalArticleJSONObject.getString("title");
			String description = journalArticleJSONObject.getString(
				"description");
			String content = journalArticleJSONObject.getString("content");

			Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
				serviceContext.getTimeZone());

			displayCalendar.add(Calendar.YEAR, -1);

			int displayDateMonth = displayCalendar.get(Calendar.MONTH);
			int displayDateDay = displayCalendar.get(Calendar.DAY_OF_MONTH);
			int displayDateYear = displayCalendar.get(Calendar.YEAR);
			int displayDateHour = displayCalendar.get(Calendar.HOUR);
			int displayDateMinute = displayCalendar.get(Calendar.MINUTE);
			int displayDateAmPm = displayCalendar.get(Calendar.AM_PM);

			if (displayDateAmPm == Calendar.PM) {
				displayDateHour += 12;
			}

			addDDMStructure(classNameId, ddmStructureKey, serviceContext);

			Map<Locale, String> titleMap = new HashMap<>();
			Map<Locale, String> descriptionMap = new HashMap<>();

			titleMap.put(Locale.US, title);
			descriptionMap.put(Locale.US, description);

			content = getNormalizedContent(
				content, KEY_START_INTERCEPTOR, KEY_END_INTERCEPTOR,
				httpServletRequest, serviceContext);

			JournalArticle journalArticle =
				_journalArticleLocalService.fetchArticle(
					serviceContext.getScopeGroupId(), articleId);

			if (journalArticle == null) {
				_journalArticleLocalService.addArticle(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), 0L,
					JournalArticleConstants.CLASSNAME_ID_DEFAULT, 0L, articleId,
					false, 1, titleMap, descriptionMap, content,
					ddmStructureKey, ddmTemplateKey, StringPool.BLANK,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, 0, 0, 0, 0, 0, true, 0,
					0, 0, 0, 0, true, true, false, StringPool.BLANK, null, null,
					StringPool.BLANK, serviceContext);
			}
		}
	}

	protected void createLayouts(
			JSONArray layoutsJSONArray, ServiceContext serviceContext)
		throws Exception {

		for (int i = 0; i < layoutsJSONArray.length(); i++) {
			JSONObject layoutJSONObject = layoutsJSONArray.getJSONObject(i);

			String layoutTemplateId = layoutJSONObject.getString(
				"layoutTemplateId");
			String name = layoutJSONObject.getString("name");

			String friendlyURL = StringUtil.toLowerCase(name.trim());

			friendlyURL =
				CharPool.SLASH +
					FriendlyURLNormalizerUtil.normalize(friendlyURL);

			Layout layout = _layoutLocalService.addLayout(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				false, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, name,
				StringPool.BLANK, LayoutConstants.TYPE_PORTLET, true,
				friendlyURL, serviceContext);

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			layoutTypePortlet.setLayoutTemplateId(
				UserConstants.USER_ID_DEFAULT, layoutTemplateId, false);

			JSONArray portletsJSONArray = layoutJSONObject.getJSONArray(
				"portlets");

			addLayoutPortlets(
				portletsJSONArray, layoutTypePortlet, layout, serviceContext);

			_layoutLocalService.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), layout.getTypeSettings());
		}
	}

	protected String getDDMStructureFileName(String ddmStructureKey) {
		return "com/liferay/commerce/starter/lotus/internal/dependencies/" +
			ddmStructureKey + ".xml";
	}

	protected String getImagePath(String fileName) {
		return "com/liferay/commerce/starter/lotus/internal/dependencies" +
			"/images/" + fileName;
	}

	protected JSONArray getJournalArticleJSONArray() throws Exception {
		Class<?> clazz = getClass();

		String journalArticlePath =
			"com/liferay/commerce/starter/lotus/internal/dependencies" +
				"/journal-articles.json";

		String journalArticleJSON = StringUtil.read(
			clazz.getClassLoader(), journalArticlePath, false);

		return JSONFactoryUtil.createJSONArray(journalArticleJSON);
	}

	protected String getNormalizedContent(
			String content, String startRegex, String endRegex,
			HttpServletRequest httpServletRequest,
			ServiceContext serviceContext)
		throws Exception {

		StringBuilder sb = new StringBuilder();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String[] contentParts = content.split(startRegex);

		for (int i = 0; i < contentParts.length; i++) {
			String contentPart = contentParts[i];

			if (contentPart.contains(endRegex)) {
				String[] contentPartSplit = contentPart.split(endRegex);

				String fileName = contentPartSplit[0];

				FileEntry fileEntry = _fileEntries.get(fileName);

				if (fileEntry == null) {
					Class<?> clazz = getClass();

					ClassLoader classLoader = clazz.getClassLoader();

					InputStream is = classLoader.getResourceAsStream(
						getImagePath(fileName));

					String mimeType = MimeTypesUtil.getContentType(fileName);

					fileEntry = _dlAppLocalService.addFileEntry(
						serviceContext.getUserId(),
						serviceContext.getScopeGroupId(),
						DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
						mimeType, fileName, StringPool.BLANK, StringPool.BLANK,
						FileUtil.getBytes(is), serviceContext);

					_fileEntries.put(fileName, fileEntry);
				}

				String previewURL = DLUtil.getDownloadURL(
					fileEntry, fileEntry.getLatestFileVersion(), themeDisplay,
					StringPool.BLANK, false, false);

				String imgHtmlTag =
					"<img src=\"" + previewURL + "\" data-fileentryid=\"" +
						fileEntry.getFileEntryId() + "\" />";

				contentPart = imgHtmlTag + contentPartSplit[1];
			}
			else if (contentPart.contains(startRegex)) {
				contentPart = _commerceStarterLotusHelper.replaceInterceptors(
					contentPart, startRegex);
			}

			sb.append(contentPart);
		}

		return sb.toString();
	}

	protected void init(ServiceContext serviceContext) throws PortalException {
		_layoutLocalService.deleteLayouts(
			serviceContext.getScopeGroupId(), true, serviceContext);

		_layoutLocalService.deleteLayouts(
			serviceContext.getScopeGroupId(), false, serviceContext);

		resetFileEntryMap();
	}

	protected void resetFileEntryMap() throws PortalException {
		if (!_fileEntries.isEmpty()) {
			Set<Map.Entry<String, FileEntry>> entrySet =
				_fileEntries.entrySet();

			Iterator<Map.Entry<String, FileEntry>> iterator =
				entrySet.iterator();

			while (iterator.hasNext()) {
				Map.Entry<String, FileEntry> entry = iterator.next();

				FileEntry fileEntry = entry.getValue();

				_dlAppLocalService.deleteFileEntry(fileEntry.getFileEntryId());

				iterator.remove();
			}
		}
	}

	protected void updateLookAndFeel(
			JSONObject sampleDataJSONObject,
			HttpServletRequest httpServletRequest,
			ServiceContext serviceContext)
		throws PortalException {

		String themeId = sampleDataJSONObject.getString("themeId");

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			serviceContext.getScopeGroupId(), false);

		UnicodeProperties typeSettingProperties =
			layoutSet.getSettingsProperties();

		_commerceStarterLotusHelper.setThemeSettingProperties(
			httpServletRequest, typeSettingProperties);

		_layoutSetLocalService.updateLookAndFeel(
			serviceContext.getScopeGroupId(), false, themeId, StringPool.BLANK,
			StringPool.BLANK);
	}

	private static Map<String, FileEntry> _fileEntries;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private CommerceStarterLotusHelper _commerceStarterLotusHelper;

	@Reference
	private CommerceStarterRegistry _commerceStarterRegistry;

	@Reference
	private CPDemoDataCreator _cpDemoDataCreator;

	@Reference
	private CPGroupLocalService _cpGroupLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DefaultDDMStructureHelper _defaultDDMStructureHelper;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.starter.lotus)"
	)
	private ServletContext _servletContext;

}