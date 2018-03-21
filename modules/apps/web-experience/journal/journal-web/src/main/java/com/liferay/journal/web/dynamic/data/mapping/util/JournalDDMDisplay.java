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

package com.liferay.journal.web.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.DDMTemplateConstants;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.BaseDDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplay;
import com.liferay.dynamic.data.mapping.util.DDMDisplayTabItem;
import com.liferay.dynamic.data.mapping.util.DDMNavigationHelper;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.configuration.JournalWebConfiguration;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webdav.WebDAVUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(
	configurationPid = "com.liferay.journal.web.configuration.JournalWebConfiguration",
	property = {"javax.portlet.name=" + JournalPortletKeys.JOURNAL},
	service = DDMDisplay.class
)
public class JournalDDMDisplay extends BaseDDMDisplay {

	@Override
	public String getAvailableFields() {
		return "Liferay.FormBuilder.AVAILABLE_FIELDS.WCM_STRUCTURE";
	}

	public String getConfirmSelectStructureMessage(Locale locale) {
		return LanguageUtil.get(
			getResourceBundle(locale),
			"selecting-a-new-structure-deletes-all-unsaved-content");
	}

	public String getConfirmSelectTemplateMessage(Locale locale) {
		return LanguageUtil.get(
			getResourceBundle(locale),
			"selecting-a-new-template-deletes-all-unsaved-content");
	}

	@Override
	public String getEditStructureDefaultValuesURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			DDMStructure structure, String redirectURL)
		throws Exception {

		PortletURL portletURL = portal.getControlPanelPortletURL(
			liferayPortletRequest, JournalPortletKeys.JOURNAL,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/edit_article.jsp");
		portletURL.setParameter("redirect", redirectURL);
		portletURL.setParameter(
			"groupId", String.valueOf(structure.getGroupId()));
		portletURL.setParameter(
			"classNameId",
			String.valueOf(portal.getClassNameId(DDMStructure.class)));
		portletURL.setParameter(
			"classPK", String.valueOf(structure.getStructureId()));
		portletURL.setParameter("ddmStructureKey", structure.getStructureKey());

		return portletURL.toString();
	}

	@Override
	public String getEditTemplateBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classNameId,
			long classPK, long resourceClassNameId, String portletResource)
		throws Exception {

		DDMNavigationHelper ddmNavigationHelper = getDDMNavigationHelper();

		if (ddmNavigationHelper.isNavigationStartsOnEditTemplate(
				liferayPortletRequest)) {

			return StringPool.BLANK;
		}

		if (ddmNavigationHelper.isNavigationStartsOnSelectTemplate(
				liferayPortletRequest)) {

			return ParamUtil.getString(liferayPortletRequest, "redirect");
		}

		if (ddmNavigationHelper.isNavigationStartsOnViewTemplates(
				liferayPortletRequest)) {

			return getViewTemplatesURL(
				liferayPortletRequest, liferayPortletResponse, classNameId, 0,
				resourceClassNameId);
		}

		return getViewTemplatesURL(
			liferayPortletRequest, liferayPortletResponse, classNameId, classPK,
			resourceClassNameId);
	}

	@Override
	public String getPortletId() {
		return JournalPortletKeys.JOURNAL;
	}

	@Override
	public String getStorageType() {
		String storageType = StorageType.JSON.getValue();

		try {
			long companyId = CompanyThreadLocal.getCompanyId();

			JournalServiceConfiguration journalServiceConfiguration =
				ConfigurationProviderUtil.getCompanyConfiguration(
					JournalServiceConfiguration.class, companyId);

			storageType =
				journalServiceConfiguration.journalArticleStorageType();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return storageType;
	}

	@Override
	public String getStructureType() {
		return JournalArticle.class.getName();
	}

	@Override
	public List<DDMDisplayTabItem> getTabItems() {
		List<DDMDisplayTabItem> tabItems = new ArrayList<>();

		tabItems.add(_getWebContentTabItem());
		tabItems.add(_getStructuresTabItem());
		tabItems.add(_getTemplatesTabItem());

		if (portal.isRSSFeedsEnabled()) {
			tabItems.add(_getFeedsTabItem());
		}

		return tabItems;
	}

	@Override
	public long getTemplateHandlerClassNameId(
		DDMTemplate template, long classNameId) {

		return portal.getClassNameId(JournalArticle.class);
	}

	@Override
	public Set<String> getTemplateLanguageTypes() {
		return _templateLanguageTypes;
	}

	@Override
	public String getTemplateType() {
		return DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY;
	}

	@Override
	public String getViewTemplatesBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classPK)
		throws Exception {

		DDMNavigationHelper ddmNavigationHelper = getDDMNavigationHelper();

		if (ddmNavigationHelper.isNavigationStartsOnEditStructure(
				liferayPortletRequest)) {

			return StringPool.BLANK;
		}

		if (ddmNavigationHelper.isNavigationStartsOnViewTemplates(
				liferayPortletRequest)) {

			return ParamUtil.getString(liferayPortletRequest, "backURL");
		}

		return super.getViewTemplatesBackURL(
			liferayPortletRequest, liferayPortletResponse, classPK);
	}

	@Override
	public Set<String> getViewTemplatesExcludedColumnNames() {
		return _viewTemplateExcludedColumnNames;
	}

	@Override
	public boolean isShowBackURLInTitleBar() {
		return true;
	}

	@Override
	public boolean isShowConfirmSelectStructure() {
		return true;
	}

	@Override
	public boolean isShowConfirmSelectTemplate() {
		return true;
	}

	@Override
	public boolean isShowStructureSelector() {
		return true;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_journalWebConfiguration = ConfigurableUtil.createConfigurable(
			JournalWebConfiguration.class, properties);
	}

	@Reference
	protected Portal portal;

	@Reference
	protected PortletLocalService portletLocalService;

	private DDMDisplayTabItem _getFeedsTabItem() {
		return new DDMDisplayTabItem() {

			@Override
			public String getTitle(
				LiferayPortletRequest liferayPortletRequest,
				LiferayPortletResponse liferayPortletResponse) {

				ResourceBundle resourceBundle = getResourceBundle(
					liferayPortletRequest.getLocale());

				return LanguageUtil.get(resourceBundle, "feeds");
			}

			@Override
			public String getURL(
					LiferayPortletRequest liferayPortletRequest,
					LiferayPortletResponse liferayPortletResponse)
				throws Exception {

				PortletURL portletURL = portal.getControlPanelPortletURL(
					liferayPortletRequest, JournalPortletKeys.JOURNAL,
					PortletRequest.RENDER_PHASE);

				portletURL.setParameter("mvcPath", "/view_feeds.jsp");

				ThemeDisplay themeDisplay =
					(ThemeDisplay)liferayPortletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				portletURL.setParameter(
					"redirect", themeDisplay.getURLCurrent());

				return portletURL.toString();
			}

		};
	}

	private DDMDisplayTabItem _getStructuresTabItem() {
		return new DDMDisplayTabItem() {

			@Override
			public String getTitle(
				LiferayPortletRequest liferayPortletRequest,
				LiferayPortletResponse liferayPortletResponse) {

				ResourceBundle resourceBundle = getResourceBundle(
					liferayPortletRequest.getLocale());

				return LanguageUtil.get(resourceBundle, "structures");
			}

			@Override
			public String getURL(
					LiferayPortletRequest liferayPortletRequest,
					LiferayPortletResponse liferayPortletResponse)
				throws Exception {

				ThemeDisplay themeDisplay =
					(ThemeDisplay)liferayPortletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				Portlet portlet = portletLocalService.getPortletById(
					portletDisplay.getId());

				PortletURL portletURL = PortletURLFactoryUtil.create(
					liferayPortletRequest,
					PortletProviderUtil.getPortletId(
						DDMStructure.class.getName(),
						PortletProvider.Action.VIEW),
					PortletRequest.RENDER_PHASE);

				portletURL.setParameter("mvcPath", "/view.jsp");
				portletURL.setParameter(
					"backURL", themeDisplay.getURLCurrent());
				portletURL.setParameter(
					"groupId", String.valueOf(themeDisplay.getScopeGroupId()));
				portletURL.setParameter(
					"refererPortletName", JournalPortletKeys.JOURNAL);
				portletURL.setParameter(
					"refererWebDAVToken", WebDAVUtil.getStorageToken(portlet));
				portletURL.setParameter(
					"scopeTitle",
					getTitle(liferayPortletRequest, liferayPortletResponse));
				portletURL.setParameter(
					"showAncestorScopes",
					String.valueOf(_journalWebConfiguration.
						showAncestorScopesByDefault()));
				portletURL.setParameter(
					"showCacheableInput", Boolean.TRUE.toString());
				portletURL.setParameter(
					"showManageTemplates", Boolean.TRUE.toString());

				return portletURL.toString();
			}

		};
	}

	private DDMDisplayTabItem _getTemplatesTabItem() {
		return new DDMDisplayTabItem() {

			@Override
			public String getTitle(
				LiferayPortletRequest liferayPortletRequest,
				LiferayPortletResponse liferayPortletResponse) {

				ResourceBundle resourceBundle = getResourceBundle(
					liferayPortletRequest.getLocale());

				return LanguageUtil.get(resourceBundle, "templates");
			}

			@Override
			public String getURL(
					LiferayPortletRequest liferayPortletRequest,
					LiferayPortletResponse liferayPortletResponse)
				throws Exception {

				ThemeDisplay themeDisplay =
					(ThemeDisplay)liferayPortletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				Portlet portlet = portletLocalService.getPortletById(
					portletDisplay.getId());

				PortletURL portletURL = PortletURLFactoryUtil.create(
					liferayPortletRequest,
					PortletProviderUtil.getPortletId(
						DDMTemplate.class.getName(),
						PortletProvider.Action.VIEW),
					PortletRequest.RENDER_PHASE);

				ResourceBundle resourceBundle = getResourceBundle(
					liferayPortletRequest.getLocale());

				portletURL.setParameter("mvcPath", "/view_template.jsp");
				portletURL.setParameter(
					"navigationStartsOn", DDMNavigationHelper.VIEW_TEMPLATES);
				portletURL.setParameter(
					"backURL", themeDisplay.getURLCurrent());
				portletURL.setParameter(
					"groupId", String.valueOf(themeDisplay.getScopeGroupId()));
				portletURL.setParameter(
					"classNameId",
					String.valueOf(portal.getClassNameId(DDMStructure.class)));
				portletURL.setParameter(
					"resourceClassNameId",
					String.valueOf(
						portal.getClassNameId(JournalArticle.class)));
				portletURL.setParameter(
					"refererPortletName", JournalPortletKeys.JOURNAL);
				portletURL.setParameter(
					"refererWebDAVToken", WebDAVUtil.getStorageToken(portlet));
				portletURL.setParameter(
					"scopeTitle",
					LanguageUtil.get(resourceBundle, "templates"));
				portletURL.setParameter(
					"showAncestorScopes",
					String.valueOf(_journalWebConfiguration.
						showAncestorScopesByDefault()));
				portletURL.setParameter(
					"showCacheableInput", Boolean.TRUE.toString());
				portletURL.setParameter("showHeader", Boolean.TRUE.toString());

				return portletURL.toString();
			}

		};
	}

	private DDMDisplayTabItem _getWebContentTabItem() {
		return new DDMDisplayTabItem() {

			@Override
			public String getTitle(
				LiferayPortletRequest liferayPortletRequest,
				LiferayPortletResponse liferayPortletResponse) {

				ResourceBundle resourceBundle = getResourceBundle(
					liferayPortletRequest.getLocale());

				return LanguageUtil.get(resourceBundle, "web-content");
			}

			@Override
			public String getURL(
					LiferayPortletRequest liferayPortletRequest,
					LiferayPortletResponse liferayPortletResponse)
				throws Exception {

				PortletURL portletURL = portal.getControlPanelPortletURL(
					liferayPortletRequest, JournalPortletKeys.JOURNAL,
					PortletRequest.RENDER_PHASE);

				portletURL.setParameter("mvcPath", "/view.jsp");

				return portletURL.toString();
			}

		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalDDMDisplay.class);

	private static final Set<String> _templateLanguageTypes = SetUtil.fromArray(
		new String[] {
			TemplateConstants.LANG_TYPE_FTL, TemplateConstants.LANG_TYPE_VM,
			TemplateConstants.LANG_TYPE_XSL
		});
	private static final Set<String> _viewTemplateExcludedColumnNames =
		SetUtil.fromArray(new String[] {"mode"});

	private volatile JournalWebConfiguration _journalWebConfiguration;

}