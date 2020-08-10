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

package com.liferay.commerce.product.definitions.web.internal.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.asset.util.AssetHelper;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionAssetRenderer
	extends BaseJSPAssetRenderer<CPDefinition> {

	public CPDefinitionAssetRenderer(
		CPDefinition cpDefinition, CPDefinitionHelper cpDefinitionHelper,
		ModelResourcePermission<CommerceCatalog> modelResourcePermission) {

		_cpDefinition = cpDefinition;
		_cpDefinitionHelper = cpDefinitionHelper;
		_modelResourcePermission = modelResourcePermission;
	}

	@Override
	public CPDefinition getAssetObject() {
		return _cpDefinition;
	}

	@Override
	public String getClassName() {
		return CPDefinition.class.getName();
	}

	@Override
	public long getClassPK() {
		return _cpDefinition.getCPDefinitionId();
	}

	@Override
	public Date getDisplayDate() {
		return _cpDefinition.getDisplayDate();
	}

	@Override
	public long getGroupId() {
		return _cpDefinition.getGroupId();
	}

	@Override
	public String getJspPath(
		HttpServletRequest httpServletRequest, String template) {

		if (template.equals(TEMPLATE_ABSTRACT) ||
			template.equals(TEMPLATE_FULL_CONTENT)) {

			return "/asset/" + template + ".jsp";
		}

		return null;
	}

	@Override
	public int getStatus() {
		return _cpDefinition.getStatus();
	}

	@Override
	public String getSummary(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		int abstractLength = AssetHelper.ASSET_ENTRY_ABSTRACT_LENGTH;

		if (portletRequest != null) {
			abstractLength = GetterUtil.getInteger(
				portletRequest.getAttribute(
					WebKeys.ASSET_ENTRY_ABSTRACT_LENGTH),
				AssetHelper.ASSET_ENTRY_ABSTRACT_LENGTH);
		}

		String summary = _cpDefinition.getDescription();

		if (Validator.isNull(summary)) {
			summary = HtmlUtil.stripHtml(
				StringUtil.shorten(
					_cpDefinition.getDescriptionMapAsXML(), abstractLength));
		}

		return summary;
	}

	@Override
	public String getTitle(Locale locale) {
		return _cpDefinition.getName(LanguageUtil.getLanguageId(locale));
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		Group group = GroupLocalServiceUtil.fetchGroup(
			_cpDefinition.getGroupId());

		PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
			liferayPortletRequest, group, CPPortletKeys.CP_DEFINITIONS, 0, 0,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");
		portletURL.setParameter(
			"cpDefinitionId",
			String.valueOf(_cpDefinition.getCPDefinitionId()));

		return portletURL;
	}

	@Override
	public String getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		AssetRendererFactory<CPDefinition> assetRendererFactory =
			getAssetRendererFactory();

		PortletURL portletURL = assetRendererFactory.getURLView(
			liferayPortletResponse, windowState);

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter(
			"cpDefinitionId",
			String.valueOf(_cpDefinition.getCPDefinitionId()));
		portletURL.setWindowState(windowState);

		return portletURL.toString();
	}

	@Override
	public String getURLViewInContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		String noSuchEntryRedirect) {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)liferayPortletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return _cpDefinitionHelper.getFriendlyURL(
				_cpDefinition.getCPDefinitionId(), themeDisplay);
		}
		catch (Exception e) {
			return noSuchEntryRedirect;
		}
	}

	@Override
	public long getUserId() {
		return _cpDefinition.getUserId();
	}

	@Override
	public String getUserName() {
		return _cpDefinition.getUserName();
	}

	@Override
	public String getUuid() {
		return _cpDefinition.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return _modelResourcePermission.contains(
			permissionChecker, _cpDefinition.getCommerceCatalog(),
			ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		return _modelResourcePermission.contains(
			permissionChecker, _cpDefinition.getCommerceCatalog(),
			ActionKeys.VIEW);
	}

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String template)
		throws Exception {

		httpServletRequest.setAttribute(CPWebKeys.CP_DEFINITION, _cpDefinition);

		return super.include(httpServletRequest, httpServletResponse, template);
	}

	@Override
	public boolean isPrintable() {
		return true;
	}

	private final CPDefinition _cpDefinition;
	private final CPDefinitionHelper _cpDefinitionHelper;
	private final ModelResourcePermission<CommerceCatalog>
		_modelResourcePermission;

}