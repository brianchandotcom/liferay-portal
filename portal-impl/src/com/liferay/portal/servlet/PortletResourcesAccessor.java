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

package com.liferay.portal.servlet;

import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.model.Portlet;

import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public interface PortletResourcesAccessor
	extends Accessor<Portlet, List<String>> {

	public static PortletResourcesAccessor footerPortalCss =
		new PortletPortalResourcesAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getFooterPortalCss();
			}
		};
	public static PortletResourcesAccessor footerPortalJavaScript =
		new PortletPortalResourcesAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getFooterPortalJavaScript();
			}
		};
	public static PortletResourcesAccessor footerPortletCss =
		new PortletResourceDefaultAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getFooterPortletCss();
			}
		};
	public static PortletResourcesAccessor footerPortletJavaScript =
		new PortletResourceDefaultAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getFooterPortletJavaScript();
			}
		};
	public static PortletResourcesAccessor headerPortalCss =
		new PortletPortalResourcesAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getHeaderPortalCss();
			}
		};
	public static PortletResourcesAccessor headerPortalJavaScript =
		new PortletPortalResourcesAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getHeaderPortalJavaScript();
			}
		};
	public static PortletResourcesAccessor headerPortletCss =
		new PortletResourceDefaultAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getHeaderPortletCss();
			}
		};
	public static PortletResourcesAccessor headerPortletJavaScript =
		new PortletResourceDefaultAccessor() {
			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getHeaderPortletJavaScript();
			}
		};

	public boolean isPortalResource();

	public static abstract class PortletPortalResourcesAccessor
		implements PortletResourcesAccessor {

		@Override
		public boolean isPortalResource() {
			return true;
		}
	}

	public static abstract class PortletResourceDefaultAccessor
		implements PortletResourcesAccessor {

		@Override
		public boolean isPortalResource() {
			return false;
		}
	}

}