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

package com.liferay.bean.portlet.registration.internal.util;

import com.liferay.bean.portlet.extension.BeanPortletMethod;
import com.liferay.bean.portlet.extension.BeanPortletMethodFactory;
import com.liferay.bean.portlet.extension.BeanPortletMethodType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Method;

import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventPortlet;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.HeaderPortlet;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceServingPortlet;
import javax.portlet.annotations.ActionMethod;
import javax.portlet.annotations.DestroyMethod;
import javax.portlet.annotations.EventMethod;
import javax.portlet.annotations.HeaderMethod;
import javax.portlet.annotations.InitMethod;
import javax.portlet.annotations.RenderMethod;
import javax.portlet.annotations.ServeResourceMethod;

/**
 * @author Neil Griffin
 */
public class PortletScannerUtil {

	public static void scanNonannotatedBeanMethods(
		BeanPortletMethodFactory beanPortletMethodFactory,
		Class<?> beanPortletClass, Set<BeanPortletMethod> beanPortletMethods) {

		if (Portlet.class.isAssignableFrom(beanPortletClass)) {
			try {
				Method processActionMethod = beanPortletClass.getMethod(
					"processAction", ActionRequest.class, ActionResponse.class);

				if (!processActionMethod.isAnnotationPresent(
						ActionMethod.class)) {

					beanPortletMethods.add(
						beanPortletMethodFactory.create(
							processActionMethod, BeanPortletMethodType.ACTION,
							beanPortletClass));
				}

				Method destroyMethod = beanPortletClass.getMethod("destroy");

				if (!destroyMethod.isAnnotationPresent(DestroyMethod.class)) {
					beanPortletMethods.add(
						beanPortletMethodFactory.create(
							destroyMethod, BeanPortletMethodType.DESTROY,
							beanPortletClass));
				}

				Method initMethod = beanPortletClass.getMethod(
					"init", PortletConfig.class);

				if (!initMethod.isAnnotationPresent(InitMethod.class)) {
					beanPortletMethods.add(
						beanPortletMethodFactory.create(
							initMethod, BeanPortletMethodType.INIT,
							beanPortletClass));
				}

				Method renderMethod = beanPortletClass.getMethod(
					"render", RenderRequest.class, RenderResponse.class);

				if (!renderMethod.isAnnotationPresent(RenderMethod.class)) {
					beanPortletMethods.add(
						beanPortletMethodFactory.create(
							renderMethod, BeanPortletMethodType.RENDER,
							beanPortletClass));
				}
			}
			catch (NoSuchMethodException nsme) {
				_log.error(nsme, nsme);
			}
		}

		if (EventPortlet.class.isAssignableFrom(beanPortletClass)) {
			try {
				Method eventMethod = beanPortletClass.getMethod(
					"processEvent", EventRequest.class, EventResponse.class);

				if (!eventMethod.isAnnotationPresent(EventMethod.class)) {
					beanPortletMethods.add(
						beanPortletMethodFactory.create(
							eventMethod, BeanPortletMethodType.EVENT,
							beanPortletClass));
				}
			}
			catch (NoSuchMethodException nsme) {
				_log.error(nsme, nsme);
			}
		}

		if (HeaderPortlet.class.isAssignableFrom(beanPortletClass)) {
			try {
				Method renderHeadersMethod = beanPortletClass.getMethod(
					"renderHeaders", HeaderRequest.class, HeaderResponse.class);

				if (!renderHeadersMethod.isAnnotationPresent(
						HeaderMethod.class)) {

					beanPortletMethods.add(
						beanPortletMethodFactory.create(
							renderHeadersMethod, BeanPortletMethodType.HEADER,
							beanPortletClass));
				}
			}
			catch (NoSuchMethodException nsme) {
				_log.error(nsme, nsme);
			}
		}

		if (ResourceServingPortlet.class.isAssignableFrom(beanPortletClass)) {
			try {
				Method serveResourceMethod = beanPortletClass.getMethod(
					"serveResource", ResourceRequest.class,
					ResourceResponse.class);

				if (!serveResourceMethod.isAnnotationPresent(
						ServeResourceMethod.class)) {

					beanPortletMethods.add(
						beanPortletMethodFactory.create(
							serveResourceMethod,
							BeanPortletMethodType.SERVE_RESOURCE,
							beanPortletClass));
				}
			}
			catch (NoSuchMethodException nsme) {
				_log.error(nsme, nsme);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletScannerUtil.class);

}