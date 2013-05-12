/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Garcia
 */
public class BaseDDMDisplay implements DDMDisplay {

	public String getDDMResource() {
		return StringPool.BLANK;
	}

	public String getEditTemplateBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classNameId,
			long classPK, String portletResource)
		throws Exception {

		String backURL = ParamUtil.getString(liferayPortletRequest, "backURL");

		if (Validator.isNull(backURL) || Validator.isNull(portletResource)) {
			backURL = getViewTemplatesURL(
				liferayPortletRequest, liferayPortletResponse, classNameId,
				classPK);
		}

		return backURL;
	}

	public String getEditTemplateTitle(
		DDMStructure structure, DDMTemplate template, Locale locale) {

		if ((structure != null) && (template != null)) {
			StringBundler sb = new StringBundler(5);

			sb.append(template.getName(locale));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(structure.getName(locale));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			return sb.toString();
		}
		else if (structure != null) {
			return LanguageUtil.format(
				locale, "new-template-for-structure-x",
				structure.getName(locale), false);
		}
		else if (template != null) {
			return template.getName(locale);
		}

		return getDefaultEditTemplateTitle(locale);
	}

	public String getEditTemplateTitle(long classNameId, Locale locale) {
		if (classNameId > 0) {
			TemplateHandler templateHandler =
				TemplateHandlerRegistryUtil.getTemplateHandler(classNameId);

			if (templateHandler != null) {
				return LanguageUtil.get(locale, "new") + StringPool.SPACE +
					templateHandler.getName(locale);
			}
		}

		return getDefaultEditTemplateTitle(locale);
	}

	public String getPortletId() {
		return PortletKeys.DYNAMIC_DATA_MAPPING;
	}

	public String getTemplateDDMResource(
		HttpServletRequest request, DDMTemplate template) {

		return getDDMResource();
	}

	public String getTemplateDDMResourceActionId() {
		return ActionKeys.ADD_TEMPLATE;
	}

	public Set<String> getTemplateLanguageTypes() {
		return _templateLanguageTypes;
	}

	public String getTemplateType(DDMTemplate template, Locale locale) {
		return LanguageUtil.get(locale, template.getType());
	}

	public String getViewTemplatesBackURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classPK)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			getControlPanelPlid(liferayPortletRequest),
			PortletKeys.DYNAMIC_DATA_MAPPING, PortletRequest.RENDER_PHASE);

		portletURL.setParameter("struts_action", "/dynamic_data_mapping/view");

		return portletURL.toString();
	}

	public Set<String> getViewTemplatesExcludedColumnNames() {
		return _viewTemplateExcludedColumnNames;
	}

	public String getViewTemplatesTitle(
		DDMStructure structure, boolean controlPanel, Locale locale) {

		if (structure != null) {
			return LanguageUtil.format(
				locale, "templates-for-structure-x", structure.getName(locale),
				false);
		}

		return getDefaultViewTemplateTitle(locale);
	}

	public String getViewTemplatesTitle(DDMStructure structure, Locale locale) {
		return getViewTemplatesTitle(structure, false, locale);
	}

	public boolean isShowStructureSelector() {
		return false;
	}

	protected long getControlPanelPlid(
			LiferayPortletRequest liferayPortletRequest)
		throws PortalException, SystemException {

		return PortalUtil.getControlPanelPlid(liferayPortletRequest);
	}

	protected String getDefaultEditTemplateTitle(Locale locale) {
		return LanguageUtil.get(locale, "new-template");
	}

	protected String getDefaultViewTemplateTitle(Locale locale) {
		return LanguageUtil.get(locale, "templates");
	}

	protected String getViewTemplatesURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse, long classNameId,
			long classPK)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
			getControlPanelPlid(liferayPortletRequest),
			PortletKeys.DYNAMIC_DATA_MAPPING, PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"struts_action", "/dynamic_data_mapping/view_template");
		portletURL.setParameter("classNameId", String.valueOf(classNameId));
		portletURL.setParameter("classPK", String.valueOf(classPK));

		return portletURL.toString();
	}

	private static Set<String> _templateLanguageTypes =
		SetUtil.fromArray(
			new String[] {
				TemplateConstants.LANG_TYPE_FTL, TemplateConstants.LANG_TYPE_VM
			});
	private static Set<String> _viewTemplateExcludedColumnNames =
		SetUtil.fromArray(new String[] {"structure"});

}