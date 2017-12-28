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

package com.liferay.commerce.product.definitions.web.internal.util;

import com.liferay.commerce.product.service.CPGroupLocalService;
import com.liferay.commerce.product.util.CommerceStarter;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.starter.key=" + EmptyCatalogCommerceStarterImpl.KEY,
		"commerce.starter.order:Integer=1"
	},
	service = CommerceStarter.class
)
public class EmptyCatalogCommerceStarterImpl implements CommerceStarter {

	public static final String KEY = "empty-catalog";

	@Override
	public void create(HttpServletRequest httpServletRequest) throws Exception {
		ServiceContext serviceContext = getServiceContext(httpServletRequest);

		_cpGroupLocalService.addCPGroup(serviceContext);
	}

	@Override
	public String getDescription(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "empty-catalog-description");
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "empty-catalog");
	}

	public ServiceContext getServiceContext(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		User user = _portal.getUser(httpServletRequest);

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	@Override
	public String getThumbnailSrc() {
		String contextPath = _servletContext.getContextPath();

		String thumbnailSrc = contextPath + "/images/thumbnail.png";

		return thumbnailSrc;
	}

	@Override
	public boolean isActive(HttpServletRequest httpServletRequest) {
		return true;
	}

	@Override
	public void renderPreview(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute(
			"render.jsp-servletContext", _servletContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/starter/render.jsp");
	}

	@Reference
	private CPGroupLocalService _cpGroupLocalService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.definitions.web)"
	)
	private ServletContext _servletContext;

}