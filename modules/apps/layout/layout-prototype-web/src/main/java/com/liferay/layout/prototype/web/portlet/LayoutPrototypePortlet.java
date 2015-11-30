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

package com.liferay.layout.prototype.web.portlet;

import com.liferay.layout.prototype.web.constants.LayoutPrototypePortletKeys;
import com.liferay.portal.NoSuchLayoutPrototypeException;
import com.liferay.portal.RequiredLayoutPrototypeException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutPrototypeService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portlet.sites.util.SitesUtil;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
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
		"com.liferay.portlet.css-class-wrapper=portlet-users-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.icon=/icons/layout_prototypes.png",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.private-request-attributes=false",
		"com.liferay.portlet.private-session-attributes=false",
		"com.liferay.portlet.render-weight=50",
		"com.liferay.portlet.scopeable=true",
		"com.liferay.portlet.use-default-template=true",
		"javax.portlet.display-name=Page Templates",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + LayoutPrototypePortletKeys.LAYOUT_PROTOTYPE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)
public class LayoutPrototypePortlet extends MVCPortlet {

	public void deleteLayoutPrototypes(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] layoutPrototypeIds = null;

		long layoutPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutPrototypeId");

		if (layoutPrototypeId > 0) {
			layoutPrototypeIds = new long[] {layoutPrototypeId};
		}
		else {
			layoutPrototypeIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		for (long curLayoutPrototypeId : layoutPrototypeIds) {
			_layoutPrototypeService.deleteLayoutPrototype(curLayoutPrototypeId);
		}
	}

	public void editLayoutPrototype(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long layoutPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutPrototypeId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			LayoutPrototype.class.getName(), actionRequest);

		if (layoutPrototypeId <= 0) {

			// Add layout prototoype

			_layoutPrototypeService.addLayoutPrototype(
				nameMap, descriptionMap, active, serviceContext);
		}
		else {

			// Update layout prototoype

			_layoutPrototypeService.updateLayoutPrototype(
				layoutPrototypeId, nameMap, descriptionMap, active,
				serviceContext);
		}
	}

	/**
	 * Resets the number of failed merge attempts for the page template, which
	 * is accessed from the action request's <code>layoutPrototypeId</code>
	 * param.
	 *
	 * <p>
	 * No merge from the page template to the actual page(s) is performed at
	 * this point.
	 * </p>
	 *
	 * @param  actionRequest the action request
	 * @throws Exception if an exception occurred
	 */
	public void resetMergeFailCount(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long layoutPrototypeId = ParamUtil.getLong(
			actionRequest, "layoutPrototypeId");

		LayoutPrototype layoutPrototype =
			_layoutPrototypeService.getLayoutPrototype(layoutPrototypeId);

		SitesUtil.setMergeFailCount(layoutPrototype, 0);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		if (SessionErrors.contains(
				renderRequest, PrincipalException.getNestedClasses())) {

			include("/error.jsp", renderRequest, renderResponse);
		}
		else {
			super.doDispatch(renderRequest, renderResponse);
		}
	}

	@Override
	protected boolean isSessionErrorException(Throwable cause) {
		if (cause instanceof NoSuchLayoutPrototypeException ||
			cause instanceof PrincipalException ||
			cause instanceof RequiredLayoutPrototypeException) {

			return true;
		}

		return false;
	}

	@Reference(unbind = "-")
	protected void setLayoutPrototypeService(
		LayoutPrototypeService layoutPrototypeService) {

		_layoutPrototypeService = layoutPrototypeService;
	}

	private volatile LayoutPrototypeService _layoutPrototypeService;

}