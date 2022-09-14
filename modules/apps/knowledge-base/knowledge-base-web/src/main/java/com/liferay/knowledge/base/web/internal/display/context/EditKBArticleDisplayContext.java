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

package com.liferay.knowledge.base.web.internal.display.context;

import com.liferay.knowledge.base.configuration.KBGroupServiceConfiguration;
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.model.KBTemplate;
import com.liferay.knowledge.base.service.KBFolderLocalServiceUtil;
import com.liferay.knowledge.base.util.KnowledgeBaseUtil;
import com.liferay.knowledge.base.web.internal.configuration.KBSectionPortletInstanceConfiguration;
import com.liferay.knowledge.base.web.internal.constants.KBWebKeys;
import com.liferay.knowledge.base.web.internal.util.AdminUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.PortletConfig;

/**
 * @author Adolfo Pérez
 */
public class EditKBArticleDisplayContext {

	public EditKBArticleDisplayContext(
		KBGroupServiceConfiguration kbGroupServiceConfiguration,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		PortletConfig portletConfig) {

		_kbGroupServiceConfiguration = kbGroupServiceConfiguration;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_portletConfig = portletConfig;

		_redirect = PortalUtil.escapeRedirect(
			ParamUtil.getString(
				_liferayPortletRequest.getHttpServletRequest(), "redirect",
				String.valueOf(
					PortletURLUtil.getCurrent(
						_liferayPortletRequest, _liferayPortletResponse))));

		_themeDisplay = (ThemeDisplay)_liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, String> getAvailableKBArticleSections()
		throws ConfigurationException {

		Map<String, String> sections = new LinkedHashMap<>();

		KBSectionPortletInstanceConfiguration
			kbSectionPortletInstanceConfiguration =
				_getKBSectionPortletInstanceConfiguration();

		for (String section :
				kbSectionPortletInstanceConfiguration.
					adminKBArticleSections()) {

			sections.put(
				LanguageUtil.get(
					_liferayPortletRequest.getHttpServletRequest(), section),
				section);
		}

		return sections;
	}

	public String getContent() {
		return BeanParamUtil.getString(
			getKBArticle(), _liferayPortletRequest, "content",
			BeanPropertiesUtil.getString(_getKBTemplate(), "content"));
	}

	public String getFormCssClass() {
		if (!isPortletTitleBasedNavigation()) {
			return StringPool.BLANK;
		}

		return "class=\"container-fluid container-fluid-max-xl " +
			"container-form-lg\"";
	}

	public String getHeaderTitle() {
		KBArticle kbArticle = getKBArticle();

		if (kbArticle == null) {
			return LanguageUtil.get(
				_liferayPortletRequest.getHttpServletRequest(), "new-article");
		}

		return getKBArticleTitle();
	}

	public KBArticle getKBArticle() {
		if (_kbArticle != null) {
			return _kbArticle;
		}

		_kbArticle = (KBArticle)_liferayPortletRequest.getAttribute(
			KBWebKeys.KNOWLEDGE_BASE_KB_ARTICLE);

		return _kbArticle;
	}

	public long getKBArticleClassPK() {
		KBArticle kbArticle = getKBArticle();

		if (kbArticle == null) {
			return 0;
		}

		return kbArticle.getClassPK();
	}

	public long getKBArticleId() {
		KBArticle kbArticle = getKBArticle();

		if (kbArticle == null) {
			return KBArticleConstants.DEFAULT_PARENT_RESOURCE_PRIM_KEY;
		}

		return kbArticle.getKbArticleId();
	}

	public int getKBArticleStatus() {
		KBArticle kbArticle = getKBArticle();

		return kbArticle.getStatus();
	}

	public String getKBArticleTitle() {
		return BeanParamUtil.getString(
			getKBArticle(), _liferayPortletRequest, "title",
			BeanPropertiesUtil.getString(_getKBTemplate(), "title"));
	}

	public String getKBArticleURLTitle() {
		return BeanParamUtil.getString(
			getKBArticle(), _liferayPortletRequest, "urlTitle");
	}

	public String getKBArticleVersion() {
		KBArticle kbArticle = getKBArticle();

		return String.valueOf(kbArticle.getVersion());
	}

	public long getParentResourceClassNameId() {
		if (_parentResourceClassNameId != null) {
			return _parentResourceClassNameId;
		}

		_parentResourceClassNameId = BeanParamUtil.getLong(
			getKBArticle(), _liferayPortletRequest, "parentResourceClassNameId",
			PortalUtil.getClassNameId(KBFolderConstants.getClassName()));

		return _parentResourceClassNameId;
	}

	public long getParentResourcePrimKey() {
		if (_parentResourcePrimKey != null) {
			return _parentResourcePrimKey;
		}

		_parentResourcePrimKey = BeanParamUtil.getLong(
			getKBArticle(), _liferayPortletRequest, "parentResourcePrimKey",
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _parentResourcePrimKey;
	}

	public String getPublishButtonLabel() {
		if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(
				_themeDisplay.getCompanyId(), _themeDisplay.getScopeGroupId(),
				KBArticle.class.getName())) {

			return "submit-for-publication";
		}

		return "publish";
	}

	public String getRedirect() {
		return _redirect;
	}

	public long getResourcePrimKey() {
		if (_resourcePrimKey != null) {
			return _resourcePrimKey;
		}

		_resourcePrimKey = BeanParamUtil.getLong(
			getKBArticle(), _liferayPortletRequest, "resourcePrimKey");

		return _resourcePrimKey;
	}

	public String getSaveButtonLabel() {
		if ((getKBArticle() == null) || isDraft() || isApproved()) {
			return "save-as-draft";
		}

		return "save";
	}

	public String getUpdateKBArticleURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/knowledge_base/update_kb_article"
		).setMVCPath(
			"/admin/common/edit_kb_article.jsp"
		).setCMD(
			() -> {
				if (getKBArticle() == null) {
					return Constants.ADD;
				}

				return Constants.UPDATE;
			}
		).setParameter(
			"parentResourceClassNameId", getParentResourceClassNameId()
		).setParameter(
			"parentResourcePrimKey", getParentResourcePrimKey()
		).setParameter(
			"resourcePrimKey", getResourcePrimKey()
		).buildString();
	}

	public String getURLTitlePrefix() throws PortalException {
		StringBundler sb = new StringBundler(4);

		sb.append("/-/");

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			KBPortletKeys.KNOWLEDGE_BASE_DISPLAY);

		sb.append(portlet.getFriendlyURLMapping());

		long kbFolderId = KnowledgeBaseUtil.getKBFolderId(
			getParentResourceClassNameId(), getParentResourcePrimKey());

		if (kbFolderId != KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			KBFolder kbFolder = KBFolderLocalServiceUtil.getKBFolder(
				kbFolderId);

			sb.append(StringPool.SLASH);
			sb.append(kbFolder.getUrlTitle());
		}

		return StringUtil.shorten(sb.toString(), 40) + StringPool.SLASH;
	}

	public boolean hasKBArticleSections() throws ConfigurationException {
		KBSectionPortletInstanceConfiguration
			kbSectionPortletInstanceConfiguration =
				_getKBSectionPortletInstanceConfiguration();

		long kbFolderClassNameId = PortalUtil.getClassNameId(
			KBFolderConstants.getClassName());

		if (ArrayUtil.isNotEmpty(
				kbSectionPortletInstanceConfiguration.
					adminKBArticleSections()) &&
			(getParentResourceClassNameId() == kbFolderClassNameId)) {

			return true;
		}

		return false;
	}

	public boolean isApproved() {
		KBArticle kbArticle = getKBArticle();

		if ((kbArticle != null) && kbArticle.isApproved()) {
			return true;
		}

		return false;
	}

	public boolean isDraft() {
		KBArticle kbArticle = getKBArticle();

		if ((kbArticle != null) && kbArticle.isDraft()) {
			return true;
		}

		return false;
	}

	public boolean isHeaderVisible() {
		if (isPortletTitleBasedNavigation()) {
			return true;
		}

		return false;
	}

	public boolean isKBArticleDescriptionEnabled() {
		return GetterUtil.getBoolean(
			_liferayPortletRequest.getAttribute(
				"init.jsp-enableKBArticleDescription"));
	}

	public boolean isKBArticleSectionSelected(String section)
		throws ConfigurationException {

		if (ArrayUtil.contains(_getKBArticleSections(), section)) {
			return true;
		}

		return false;
	}

	public boolean isNavigationBarVisible() {
		if ((getKBArticle() != null) && isPortletTitleBasedNavigation()) {
			return true;
		}

		return false;
	}

	public boolean isPending() {
		KBArticle kbArticle = getKBArticle();

		if ((kbArticle != null) && kbArticle.isPending()) {
			return true;
		}

		return false;
	}

	public boolean isPortletTitleBasedNavigation() {
		return GetterUtil.getBoolean(
			_portletConfig.getInitParameter("portlet-title-based-navigation"));
	}

	public boolean isSourceURLEnabled() {
		return _kbGroupServiceConfiguration.sourceURLEnabled();
	}

	public boolean isURLTitleDisabled() {
		KBArticle kbArticle = getKBArticle();

		if (kbArticle == null) {
			return false;
		}

		return true;
	}

	public boolean isWorkflowStatusVisible() {
		if ((getKBArticle() != null) && !isPortletTitleBasedNavigation()) {
			return true;
		}

		return false;
	}

	private String[] _getKBArticleSections() throws ConfigurationException {
		if (_sections != null) {
			return _sections;
		}

		KBSectionPortletInstanceConfiguration
			kbSectionPortletInstanceConfiguration =
				_getKBSectionPortletInstanceConfiguration();

		_sections = AdminUtil.unescapeSections(
			BeanPropertiesUtil.getString(
				getKBArticle(), "sections",
				StringUtil.merge(
					kbSectionPortletInstanceConfiguration.
						adminKBArticleSectionsDefault())));

		return _sections;
	}

	private KBSectionPortletInstanceConfiguration
			_getKBSectionPortletInstanceConfiguration()
		throws ConfigurationException {

		if (_kbSectionPortletInstanceConfiguration != null) {
			return _kbSectionPortletInstanceConfiguration;
		}

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_kbSectionPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				KBSectionPortletInstanceConfiguration.class);

		return _kbSectionPortletInstanceConfiguration;
	}

	private KBTemplate _getKBTemplate() {
		if (_kbTemplate != null) {
			return _kbTemplate;
		}

		_kbTemplate = (KBTemplate)_liferayPortletRequest.getAttribute(
			KBWebKeys.KNOWLEDGE_BASE_KB_TEMPLATE);

		return _kbTemplate;
	}

	private KBArticle _kbArticle;
	private final KBGroupServiceConfiguration _kbGroupServiceConfiguration;
	private KBSectionPortletInstanceConfiguration
		_kbSectionPortletInstanceConfiguration;
	private KBTemplate _kbTemplate;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private Long _parentResourceClassNameId;
	private Long _parentResourcePrimKey;
	private final PortletConfig _portletConfig;
	private final String _redirect;
	private Long _resourcePrimKey;
	private String[] _sections;
	private final ThemeDisplay _themeDisplay;

}