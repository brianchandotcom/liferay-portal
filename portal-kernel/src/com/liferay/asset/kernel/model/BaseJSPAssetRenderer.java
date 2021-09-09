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

package com.liferay.asset.kernel.model;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.resource.bundle.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ClassResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 */
public abstract class BaseJSPAssetRenderer<T>
	extends BaseAssetRenderer<T> implements AssetRenderer<T> {

	public abstract String getJspPath(
		HttpServletRequest httpServletRequest, String template);

	@Override
	public boolean include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String template)
		throws Exception {

		String jspPath = getJspPath(httpServletRequest, template);

		if (Validator.isNull(jspPath)) {
			return false;
		}

		ResourceBundleLoader originalResourceBundleLoader =
			(ResourceBundleLoader)httpServletRequest.getAttribute(
				WebKeys.RESOURCE_BUNDLE_LOADER);

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(jspPath);

		try {
			httpServletRequest.setAttribute(
				WebKeys.RESOURCE_BUNDLE_LOADER, acquireResourceBundleLoader());

			requestDispatcher.include(httpServletRequest, httpServletResponse);

			return true;
		}
		catch (ServletException servletException) {
			_log.error("Unable to include JSP " + jspPath, servletException);

			throw new IOException(
				"Unable to include " + jspPath, servletException);
		}
		finally {
			httpServletRequest.setAttribute(
				WebKeys.RESOURCE_BUNDLE_LOADER, originalResourceBundleLoader);
		}
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected ResourceBundleLoader acquireResourceBundleLoader() {
		if (_servletContext != null) {
			ResourceBundleLoader resourceBundleLoader =
				ResourceBundleLoaderUtil.
					getResourceBundleLoaderByServletContextName(
						_servletContext.getServletContextName());

			if (resourceBundleLoader == null) {
				return ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
			}

			return resourceBundleLoader;
		}

		return new AggregateResourceBundleLoader(
			new ClassResourceBundleLoader("content.Language", getClass()),
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader());
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #acquireResourceBundleLoader}
	 */
	@Deprecated
	protected com.liferay.portal.kernel.util.ResourceBundleLoader
		getResourceBundleLoader() {

		ResourceBundleLoader resourceBundleLoader =
			acquireResourceBundleLoader();

		return locale -> resourceBundleLoader.loadResourceBundle(locale);
	}

	protected ServletContext getServletContext() {
		if (_servletContext != null) {
			return _servletContext;
		}

		PortletBag portletBag = PortletBagPool.get(
			getAssetRendererFactory().getPortletId());

		return portletBag.getServletContext();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseJSPAssetRenderer.class);

	private ServletContext _servletContext;

}