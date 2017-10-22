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

package com.liferay.layout.admin.web.internal.portlet;

import com.liferay.application.list.GroupProvider;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.constants.LayoutAdminWebKeys;
import com.liferay.layout.page.template.exception.DuplicateLayoutPageTemplateCollectionException;
import com.liferay.layout.page.template.exception.LayoutPageTemplateCollectionNameException;
import com.liferay.portal.kernel.exception.ImageTypeException;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLsException;
import com.liferay.portal.kernel.exception.LayoutNameException;
import com.liferay.portal.kernel.exception.LayoutParentLayoutIdException;
import com.liferay.portal.kernel.exception.LayoutSetVirtualHostException;
import com.liferay.portal.kernel.exception.LayoutTypeException;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RequiredLayoutException;
import com.liferay.portal.kernel.exception.SitemapChangeFrequencyException;
import com.liferay.portal.kernel.exception.SitemapIncludeException;
import com.liferay.portal.kernel.exception.SitemapPagePriorityException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.MultiSessionMessages;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.sites.action.ActionUtil;
import com.liferay.sites.kernel.util.SitesUtil;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.css-class-wrapper=portlet-layouts-admin",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.icon=/icons/default.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.system=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Layouts Admin",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + LayoutAdminPortletKeys.LAYOUT_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = {Portlet.class}
)
public class LayoutAdminPortlet extends MVCPortlet {

	public void deleteLayout(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long selPlid = ParamUtil.getLong(actionRequest, "selPlid");

		if (selPlid <= 0) {
			long groupId = ParamUtil.getLong(actionRequest, "groupId");
			boolean privateLayout = ParamUtil.getBoolean(
				actionRequest, "privateLayout");
			long layoutId = ParamUtil.getLong(actionRequest, "layoutId");

			Layout layout = layoutLocalService.getLayout(
				groupId, privateLayout, layoutId);

			selPlid = layout.getPlid();
		}

		Layout deleteLayout = layoutLocalService.getLayout(selPlid);

		String redirect = getRedirect(actionRequest, deleteLayout);

		SitesUtil.deleteLayout(actionRequest, actionResponse);

		MultiSessionMessages.add(actionRequest, "layoutDeleted", selPlid);

		actionRequest.setAttribute(WebKeys.REDIRECT, redirect);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		try {
			getGroup(renderRequest);
			getLayout(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof NoSuchLayoutException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());
			}
			else {
				throw new PortletException(e);
			}
		}

		if (SessionErrors.contains(
				renderRequest, NoSuchGroupException.class.getName()) ||
			SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else if (SessionErrors.contains(
					renderRequest, NoSuchLayoutException.class.getName())) {

			PortletURL redirectURL = portal.getControlPanelPortletURL(
				renderRequest, LayoutAdminPortletKeys.GROUP_PAGES,
				PortletRequest.RENDER_PHASE);

			redirectURL.setParameter("mvcPath", "/view.jsp");
			redirectURL.setParameter(
				"selPlid", String.valueOf(LayoutConstants.DEFAULT_PLID));

			renderRequest.setAttribute(
				WebKeys.REDIRECT, redirectURL.toString());

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			try {
				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(renderRequest);

				ServiceContextThreadLocal.pushServiceContext(serviceContext);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}

			renderRequest.setAttribute(
				ApplicationListWebKeys.GROUP_PROVIDER, groupProvider);

			renderRequest.setAttribute(
				LayoutAdminWebKeys.ITEM_SELECTOR, itemSelector);

			super.doDispatch(renderRequest, renderResponse);
		}
	}

	protected String getEmptyLayoutSetURL(
		PortletRequest portletRequest, long groupId, boolean privateLayout) {

		PortletURL emptyLayoutSetURL = portal.getControlPanelPortletURL(
			portletRequest, LayoutAdminPortletKeys.GROUP_PAGES,
			PortletRequest.RENDER_PHASE);

		emptyLayoutSetURL.setParameter("mvcPath", "/empty_layout_set.jsp");
		emptyLayoutSetURL.setParameter(
			"selPlid", String.valueOf(LayoutConstants.DEFAULT_PLID));
		emptyLayoutSetURL.setParameter("groupId", String.valueOf(groupId));
		emptyLayoutSetURL.setParameter(
			"privateLayout", String.valueOf(privateLayout));

		return emptyLayoutSetURL.toString();
	}

	protected Group getGroup(PortletRequest portletRequest) throws Exception {
		return ActionUtil.getGroup(portletRequest);
	}

	protected Layout getLayout(PortletRequest portletRequest) throws Exception {
		long selPlid = ParamUtil.getLong(
			portletRequest, "selPlid", LayoutConstants.DEFAULT_PLID);

		if (selPlid != LayoutConstants.DEFAULT_PLID) {
			return layoutLocalService.getLayout(selPlid);
		}

		return null;
	}

	protected long getNewPlid(Layout layout) {
		long newPlid = LayoutConstants.DEFAULT_PLID;

		if (layout.getParentLayoutId() !=
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			Layout parentLayout = layoutLocalService.fetchLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getParentLayoutId());

			if (parentLayout != null) {
				newPlid = parentLayout.getPlid();
			}
		}

		if (newPlid <= 0) {
			Layout firstLayout = layoutLocalService.fetchFirstLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

			if ((firstLayout != null) &&
				(firstLayout.getPlid() != layout.getPlid())) {

				newPlid = firstLayout.getPlid();
			}

			if (newPlid <= 0) {
				Layout otherLayoutSetFirstLayout =
					layoutLocalService.fetchFirstLayout(
						layout.getGroupId(), !layout.isPrivateLayout(),
						LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

				if ((otherLayoutSetFirstLayout != null) &&
					(otherLayoutSetFirstLayout.getPlid() != layout.getPlid())) {

					newPlid = otherLayoutSetFirstLayout.getPlid();
				}
			}
		}

		return newPlid;
	}

	protected String getRedirect(ActionRequest actionRequest, Layout layout)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		Layout refererLayout = layoutLocalService.fetchLayout(
			themeDisplay.getRefererPlid());

		if (refererLayout == null) {
			return redirect;
		}

		boolean ancestor = false;

		if (layout.getPlid() == themeDisplay.getRefererPlid()) {
			ancestor = true;
		}
		else {
			for (Layout parentLayout : refererLayout.getAncestors()) {
				if (parentLayout.getPlid() == layout.getPlid()) {
					ancestor = true;
				}
			}
		}

		if (!ancestor) {
			return redirect;
		}

		long newRefererPlid = getNewPlid(layout);

		Layout redirectLayout = layoutLocalService.fetchLayout(newRefererPlid);

		if (redirectLayout != null) {
			redirect = portal.getLayoutFullURL(redirectLayout, themeDisplay);
		}
		else {
			redirect = getEmptyLayoutSetURL(
				actionRequest, layout.getGroupId(), layout.isPrivateLayout());
		}

		return redirect;
	}

	@Override
	protected boolean isAlwaysSendRedirect() {
		return true;
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof AssetCategoryException ||
			cause instanceof DuplicateLayoutPageTemplateCollectionException ||
			cause instanceof ImageTypeException ||
			cause instanceof LayoutFriendlyURLException ||
			cause instanceof LayoutFriendlyURLsException ||
			cause instanceof LayoutNameException ||
			cause instanceof LayoutPageTemplateCollectionNameException ||
			cause instanceof LayoutParentLayoutIdException ||
			cause instanceof LayoutSetVirtualHostException ||
			cause instanceof LayoutTypeException ||
			cause instanceof NoSuchGroupException ||
			cause instanceof PrincipalException ||
			cause instanceof RequiredLayoutException ||
			cause instanceof SitemapChangeFrequencyException ||
			cause instanceof SitemapIncludeException ||
			cause instanceof SitemapPagePriorityException ||
			cause instanceof UploadException) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setGroupProvider(GroupProvider groupProvider) {
		this.groupProvider = groupProvider;
	}

	@Reference(unbind = "-")
	protected void setLayoutLocalService(
		LayoutLocalService layoutLocalService) {

		this.layoutLocalService = layoutLocalService;
	}

	protected GroupProvider groupProvider;

	@Reference
	protected ItemSelector itemSelector;

	protected LayoutLocalService layoutLocalService;

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutAdminPortlet.class);

}