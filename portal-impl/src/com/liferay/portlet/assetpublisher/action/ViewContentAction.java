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

package com.liferay.portlet.assetpublisher.action;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.journalcontent.action.WebContentAction;

import java.util.Locale;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class ViewContentAction extends WebContentAction {

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			String portletName = portletConfig.getPortletName();

			if (portletName.equals(PortletKeys.RELATED_ASSETS)) {
				AssetEntry layoutAssetEntry =
					(AssetEntry)renderRequest.getAttribute(
						WebKeys.LAYOUT_ASSET_ENTRY);

				if (layoutAssetEntry == null) {
					return actionMapping.findForward(
						"portlet.asset_publisher.view");
				}
			}

			getAssetEntry(
				renderRequest, renderResponse, portletName);
		}
		catch (Exception e) {
			SessionErrors.add(renderRequest, e.getClass());

			return actionMapping.findForward("portlet.asset_publisher.error");
		}

		return actionMapping.findForward(
			"portlet.asset_publisher.view_content");
	}

	protected AssetEntry getAssetEntry(
			RenderRequest renderRequest, long groupId, String portletName,
			String type, String urlTitle, long assetEntryId,
			long assetEntryVersionId, Locale locale)
		throws PortalException, SystemException {

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByType(
				type);
		AssetRenderer assetRenderer = null;
		AssetEntry assetEntry = null;

		if (Validator.isNotNull(urlTitle)) {
			assetRenderer = assetRendererFactory.getAssetRenderer(
				groupId, urlTitle);

			assetEntry = assetRendererFactory.getAssetEntry(
				assetRendererFactory.getClassName(),
				assetRenderer.getClassPK());
		}
		else {
			assetEntry = assetRendererFactory.getAssetEntry(assetEntryId);

			if (portletName.equals(PortletKeys.MY_WORKFLOW_INSTANCES) ||
				portletName.equals(PortletKeys.MY_WORKFLOW_TASKS) ||
				portletName.equals(PortletKeys.WORKFLOW_DEFINITIONS) ||
				portletName.equals(PortletKeys.WORKFLOW_INSTANCES) ||
				portletName.equals(PortletKeys.WORKFLOW_TASKS)) {

				assetRenderer = assetRendererFactory.getAssetRenderer(
					assetEntryVersionId, AssetRendererFactory.TYPE_LATEST);
			}
			else {
				assetRenderer = assetRendererFactory.getAssetRenderer(
					assetEntry.getClassPK(),
					AssetRendererFactory.TYPE_LATEST_APPROVED);
			}
		}

		if (!assetEntry.isVisible() &&
			(assetRenderer.getAssetRendererType() ==
				AssetRendererFactory.TYPE_LATEST_APPROVED)) {

			throw new NoSuchModelException();
		}

		renderRequest.setAttribute("view.jsp-assetEntry", assetEntry);
		renderRequest.setAttribute(
			"view.jsp-assetRendererFactory", assetRendererFactory);
		renderRequest.setAttribute("view.jsp-assetRenderer", assetRenderer);
		renderRequest.setAttribute(
			"view.jsp-title", assetRenderer.getTitle(locale));

		return assetEntry;
	}

	protected AssetEntry getAssetEntry(
			RenderRequest renderRequest, RenderResponse renderResponse,
			String portletName)
		throws PortalException, SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long assetEntryId = ParamUtil.getLong(renderRequest, "assetEntryId");
		long assetEntryVersionId = ParamUtil.getLong(
			renderRequest, "assetEntryVersionId");
		String type = ParamUtil.getString(renderRequest, "type");
		long groupId = ParamUtil.getLong(
			renderRequest, "groupId", themeDisplay.getScopeGroupId());
		String urlTitle = ParamUtil.getString(renderRequest, "urlTitle");

		return getAssetEntry(
			renderRequest, groupId, portletName, type, urlTitle, assetEntryId,
			assetEntryVersionId, themeDisplay.getLocale());
	}

}