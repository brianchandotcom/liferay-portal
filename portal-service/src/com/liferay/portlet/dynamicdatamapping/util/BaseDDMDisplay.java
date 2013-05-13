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
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;

import java.util.Locale;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Eduardo Garcia
 */
public abstract class BaseDDMDisplay implements DDMDisplay {

	@Override
	public String getAddStructureActionId() {
		return ActionKeys.ADD_STRUCTURE;
	}

	@Override
	public String getAddTemplateActionId() {
		return ActionKeys.ADD_TEMPLATE;
	}

	@Override
	public String getEditStructureDefaultValuesURL(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			DDMStructure structure, String redirectURL, String backURL)
		throws Exception {

		return null;
	}

	@Override
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

	@Override
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

	@Override
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

	@Override
	public String getResourceName(long classNameId) {
		if (classNameId > 0) {
			TemplateHandler templateHandler =
				TemplateHandlerRegistryUtil.getTemplateHandler(classNameId);

			if (templateHandler != null) {
				return templateHandler.getResourceName();
			}
		}

		return getResourceName();
	}

	@Override
	public String getStorageType() {
		return StringPool.BLANK;
	}

	@Override
	public String getStructureName(Locale locale) {
		return LanguageUtil.get(locale, "structure");
	}

	@Override
	public String getStructureType() {
		return StringPool.BLANK;
	}

	@Override
	public long[] getTemplateClassNameIds(long classNameId) {
		if (classNameId > 0) {
			return new long[] {classNameId};
		}

		return TemplateHandlerRegistryUtil.getClassNameIds();
	}

	@Override
	public long getTemplateHandlerClassNameId(DDMTemplate template) {
		if (template != null) {
			return template.getClassNameId();
		}

		return 0;
	}

	@Override
	public Set<String> getTemplateLanguageTypes() {
		return _templateLanguageTypes;
	}

	@Override
    public String getTemplateType() {
		return DDMTemplateConstants.TEMPLATE_TYPE_FORM;
	}

	@Override
	public String getTemplateType(DDMTemplate template, Locale locale) {
		return LanguageUtil.get(locale, template.getType());
	}

	@Override
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

	@Override
	public Set<String> getViewTemplatesExcludedColumnNames() {
		return _viewTemplateExcludedColumnNames;
	}

	@Override
	public String getViewTemplatesTitle(
		DDMStructure structure, boolean controlPanel, Locale locale) {

		if (structure != null) {
			return LanguageUtil.format(
				locale, "templates-for-structure-x", structure.getName(locale),
				false);
		}

		return getDefaultViewTemplateTitle(locale);
	}

	@Override
	public String getViewTemplatesTitle(DDMStructure structure, Locale locale) {
		return getViewTemplatesTitle(structure, false, locale);
	}

	@Override
	public boolean isShowAddStructureButton(
		PermissionChecker permissionChecker, long groupId) {

		String portletId = getPortletId();

		if (portletId.equals(PortletKeys.DYNAMIC_DATA_MAPPING)) {
			return false;
		}

		return permissionChecker.hasPermission(
			groupId, getResourceName(), groupId, getAddStructureActionId());
	}

	@Override
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