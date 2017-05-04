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

package com.liferay.commerce.product.definitions.web.asset;

import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.constants.CPWebKeys;
import com.liferay.commerce.product.definitions.web.internal.util.CPDefinitionUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionAssetRenderer
    extends BaseJSPAssetRenderer<CPDefinition> implements TrashRenderer {

    public CPDefinitionAssetRenderer(
        CPDefinition cpDefinition, ResourceBundleLoader resourceBundleLoader) {

        _cpDefinition = cpDefinition;
        _resourceBundleLoader = resourceBundleLoader;
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
    public String getPortletId() {
        AssetRendererFactory<CPDefinition> assetRendererFactory =
            getAssetRendererFactory();

        return assetRendererFactory.getPortletId();
    }

    @Override
    public int getStatus() {
        return _cpDefinition.getStatus();
    }

    @Override
    public String getTitle(Locale locale) {
        ResourceBundle resourceBundle =
            _resourceBundleLoader.loadResourceBundle(locale);

        return CPDefinitionUtil.getDisplayTitle(resourceBundle, _cpDefinition);
    }

    @Override
    public String getType() {
        return CPDefinitionAssetRendererFactory.TYPE;
    }

    @Override
    public PortletURL getURLEdit(
            LiferayPortletRequest liferayPortletRequest,
            LiferayPortletResponse liferayPortletResponse)
        throws Exception {

        Group group = GroupLocalServiceUtil.fetchGroup(_cpDefinition.getGroupId());

        PortletURL portletURL = PortalUtil.getControlPanelPortletURL(
            liferayPortletRequest, group, CPPortletKeys.COMMERCE_PRODUCT_DEFINITIONS, 0, 0,
            PortletRequest.RENDER_PHASE);

        portletURL.setParameter("mvcRenderCommandName", "/editProductDefinition");
        portletURL.setParameter("cpDefinitionId", String.valueOf(_cpDefinition.getCPDefinitionId()));

        return portletURL;
    }

    @Override
    public String getUrlTitle() {
        return _cpDefinition.getUrlTitle();
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
        portletURL.setParameter("cpDefinitionId", String.valueOf(_cpDefinition.getCPDefinitionId()));
        portletURL.setWindowState(windowState);

        return portletURL.toString();
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

    public boolean hasDeletePermission(PermissionChecker permissionChecker) throws PortalException {
        return CPDefinitionPermission.contains(
            permissionChecker, _cpDefinition, ActionKeys.DELETE);
    }

    @Override
    public boolean hasEditPermission(PermissionChecker permissionChecker) throws PortalException {
        return CPDefinitionPermission.contains(
            permissionChecker, _cpDefinition, ActionKeys.UPDATE);
    }

    @Override
    public boolean hasViewPermission(PermissionChecker permissionChecker) throws PortalException {
        return CPDefinitionPermission.contains(
            permissionChecker, _cpDefinition, ActionKeys.VIEW);
    }

    @Override
    public boolean include(
            HttpServletRequest request, HttpServletResponse response,
            String template)
        throws Exception {

        request.setAttribute(CPWebKeys.COMMERCE_PRODUCT_DEFINITION, _cpDefinition);

        return super.include(request, response, template);
    }

    @Override
    public boolean isPrintable() {
        return true;
    }

    private final CPDefinition _cpDefinition;
    private final ResourceBundleLoader _resourceBundleLoader;

}