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

package com.liferay.portlet;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.Accessor;

import java.util.List;

/**
 * @author Carlos Sierra Andrés
 */
public interface PortletResourceAccessor
	extends Accessor<Portlet, List<String>> {

	public static PortletResourceAccessor FOOTER_PORTAL_CSS =
		new PortalPortletResourceAccessor() {

			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getFooterPortalCss();
			}

		};

	public static PortletResourceAccessor FOOTER_PORTAL_JAVASCRIPT =
		new PortalPortletResourceAccessor() {

			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getFooterPortalJavaScript();
			}

		};

	public static PortletResourceAccessor FOOTER_PORTLET_CSS =
		new DefaultPortletResourceAccessor() {

			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getFooterPortletCss();
			}

		};

	public static PortletResourceAccessor FOOTER_PORTLET_JAVASCRIPT =
		new DefaultPortletResourceAccessor() {

			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getFooterPortletJavaScript();
			}

		};

	public static PortletResourceAccessor HEADER_PORTAL_CSS =
		new PortalPortletResourceAccessor() {

			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getHeaderPortalCss();
			}

		};

	public static PortletResourceAccessor HEADER_PORTAL_JAVASCRIPT =
		new PortalPortletResourceAccessor() {

			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getHeaderPortalJavaScript();
			}

		};

	public static PortletResourceAccessor HEADER_PORTLET_CSS =
		new DefaultPortletResourceAccessor() {

			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getHeaderPortletCss();
			}

		};

	public static PortletResourceAccessor HEADER_PORTLET_JAVASCRIPT =
		new DefaultPortletResourceAccessor() {

			@Override
			public List<String> get(Portlet portlet) {
				return portlet.getHeaderPortletJavaScript();
			}

		};

	public boolean isPortalResource();

	public abstract static class DefaultPortletResourceAccessor
		implements PortletResourceAccessor {

		@Override
		@SuppressWarnings("rawtypes")
		public Class<List<String>> getAttributeClass() {
			return (Class)List.class;
		}

		@Override
		public Class<Portlet> getTypeClass() {
			return Portlet.class;
		}

		@Override
		public boolean isPortalResource() {
			return false;
		}

	}

	public abstract static class PortalPortletResourceAccessor
		implements PortletResourceAccessor {

		@Override
		@SuppressWarnings("rawtypes")
		public Class<List<String>> getAttributeClass() {
			return (Class)List.class;
		}

		@Override
		public Class<Portlet> getTypeClass() {
			return Portlet.class;
		}

		@Override
		public boolean isPortalResource() {
			return true;
		}

	}

}