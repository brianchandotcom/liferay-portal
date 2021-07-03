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

package com.liferay.portal.jsp.engine.internal;

import com.liferay.petra.lang.ClassLoaderPool;
import com.liferay.portal.asm.ASMWrapperUtil;
import com.liferay.portal.jsp.engine.internal.delegate.CheckEnabledServletDelegate;
import com.liferay.portal.jsp.engine.internal.delegate.JspConfigDescriptorServletContextDelegate;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.util.PropsImpl;
import com.liferay.shielded.container.Ordered;
import com.liferay.shielded.container.ShieldedContainerInitializer;
import com.liferay.taglib.servlet.JspFactorySwapper;

import java.io.File;
import java.io.IOException;

import java.util.EnumSet;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.FilterRegistration;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.jasper.servlet.JasperInitializer;
import org.apache.jasper.servlet.JspServlet;
import org.apache.tomcat.JarScanFilter;
import org.apache.tomcat.JarScanType;
import org.apache.tomcat.JarScanner;
import org.apache.tomcat.util.scan.StandardJarScanner;

/**
 * @author Shuyang Zhou
 */
@Ordered(1)
public class JSPEngineShieldedContainerInitializer
	implements ShieldedContainerInitializer {

	@Override
	public void initialize(ServletContext servletContext)
		throws ServletException {

		File shieldedContainerLib = new File(
			servletContext.getRealPath(SHIELDED_CONTAINER_LIB));

		try {
			System.setProperty(
				PropsKeys.LIFERAY_SHIELDED_CONTAINER_LIB_PORTAL_DIR,
				shieldedContainerLib.getCanonicalPath());
		}
		catch (IOException ioException) {
			throw new ServletException(ioException);
		}

		ClassLoaderPool.register(
			"ShieldedContainerClassLoader", servletContext.getClassLoader());

		PropsUtil.setProps(new PropsImpl());

		JarScanner jarScanner = new StandardJarScanner();

		jarScanner.setJarScanFilter(
			new JarScanFilter() {

				@Override
				public boolean check(JarScanType jarScanType, String jarName) {
					return false;
				}

				@Override
				public boolean isSkipAll() {
					return true;
				}

			});

		servletContext.setAttribute(JarScanner.class.getName(), jarScanner);

		JasperInitializer jasperInitializer = new JasperInitializer();

		jasperInitializer.onStartup(
			null,
			ASMWrapperUtil.createASMWrapper(
				servletContext.getClassLoader(), ServletContext.class,
				new JspConfigDescriptorServletContextDelegate(servletContext),
				servletContext));

		PortalLifecycleUtil.register(
			new PortalLifecycle() {

				@Override
				public void portalDestroy() {
				}

				@Override
				public void portalInit() {
					JspFactorySwapper.swap();
				}

			},
			PortalLifecycle.METHOD_INIT);

		Map<String, String> initParameters = PropertiesUtil.toMap(
			PropsUtil.getProperties("jsp.engine.", true));

		JspServlet jspServlet = new JspServlet();

		long checkInterval = GetterUtil.getLong(
			initParameters.get("checkInterval"));

		if (GetterUtil.getBoolean(initParameters.get("development"))) {
			checkInterval = 0;
		}

		Servlet portalJSPServlet;

		if (checkInterval > 0) {
			portalJSPServlet = ASMWrapperUtil.createASMWrapper(
				servletContext.getClassLoader(), Servlet.class,
				new CheckEnabledServletDelegate(
					jspServlet, servletContext, checkInterval),
				jspServlet);
		}
		else {
			portalJSPServlet = jspServlet;
		}

		ServletRegistration.Dynamic servletDynamic = servletContext.addServlet(
			"Portal Jasper Servlet", portalJSPServlet);

		servletDynamic.setInitParameters(initParameters);

		servletDynamic.setLoadOnStartup(1);

		if (ServerDetector.isTomcat()) {
			servletDynamic.addMapping("*.jsp", "*.jspx");
		}
		else {
			FilterRegistration.Dynamic filterDynamic = servletContext.addFilter(
				"Portal Jasper Filter",
				new Filter() {

					@Override
					public void destroy() {
					}

					@Override
					public void doFilter(
							ServletRequest servletRequest,
							ServletResponse servletResponse,
							FilterChain filterChain)
						throws IOException, ServletException {

						portalJSPServlet.service(
							servletRequest, servletResponse);
					}

					@Override
					public void init(FilterConfig filterConfig) {
					}

				});

			filterDynamic.addMappingForUrlPatterns(
				EnumSet.allOf(DispatcherType.class), true, "*.jsp", "*.jspx");
		}
	}

}