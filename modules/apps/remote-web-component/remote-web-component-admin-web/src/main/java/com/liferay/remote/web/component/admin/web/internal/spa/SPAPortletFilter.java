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

package com.liferay.remote.web.component.admin.web.internal.spa;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

import java.util.List;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.PortletFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.RenderResponseWrapper;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(factory = "spa.portlet.filter", service = PortletFilter.class)
public class SPAPortletFilter implements RenderFilter {

	@Override
	public void destroy() {

		// unused

	}

	@Override
	public void doFilter(
			RenderRequest renderRequest, RenderResponse renderResponse,
			FilterChain chain)
		throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			LayoutTypePortlet layoutTypePortlet =
				themeDisplay.getLayoutTypePortlet();

			String singlePageApplication = "spa";

			if ((layoutTypePortlet != null) &&
				singlePageApplication.equals(
					layoutTypePortlet.getLayoutTemplateId())) {

				LiferayPortletRequest liferayPortletRequest =
					_portal.getLiferayPortletRequest(renderRequest);

				List<Portlet> columnPortlets = layoutTypePortlet.getAllPortlets(
					"column-2");

				Portlet portlet = liferayPortletRequest.getPortlet();

				Stream<Portlet> portletStream = columnPortlets.stream();

				if (portletStream.anyMatch(portlet::equals) &&
					(WindowState.NORMAL == renderRequest.getWindowState())) {

					try {
						if (_layoutPermission.contains(
								PermissionThreadLocal.getPermissionChecker(),
								layoutTypePortlet.getLayout(),
								ActionKeys.UPDATE)) {

							// NO-OP response: do not display portlet BUT
							// make sure it's still called in case it uses
							// inline header sections approach.

							RenderResponseWrapper renderResponseWrapper =
								new RenderResponseWrapper(renderResponse) {

									@Override
									public OutputStream getPortletOutputStream()
										throws IOException {

										return new OutputStream() {

											@Override
											public void write(int b)
												throws IOException {
											}

										};
									}

									@Override
									public PrintWriter getWriter()
										throws IOException {

										return new PrintWriter(
											new Writer() {

												@Override
												public void close()
													throws IOException {
												}

												@Override
												public void flush()
													throws IOException {
												}

												@Override
												public void write(
														char[] arg0, int arg1,
														int arg2)
													throws IOException {
												}

											});
									}

								};

							chain.doFilter(
								renderRequest, renderResponseWrapper);
						}
					}
					catch (Exception exception) {
						_log.error(
							String.format(
								"Failure rendering alternate SPA view for %s",
								portlet),
							exception);
					}

					return;
				}
			}
		}

		chain.doFilter(renderRequest, renderResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws PortletException {

		// unused

	}

	private static final Log _log = LogFactoryUtil.getLog(
		SPAPortletFilter.class);

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private Portal _portal;

}