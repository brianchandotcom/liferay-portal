/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.registration.portlet.util;

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
		Class<?> beanPortletClass,
		BeanPortletMethodFactory beanPortletMethodFactory,
		Set<BeanPortletMethod> beanPortletMethods) {

		if (Portlet.class.isAssignableFrom(beanPortletClass)) {
			try {
				Method processActionMethod = beanPortletClass.getMethod(
					"processAction", ActionRequest.class, ActionResponse.class);

				if (!processActionMethod.isAnnotationPresent(
						ActionMethod.class)) {

					beanPortletMethods.add(
						beanPortletMethodFactory.create(
							beanPortletClass, BeanPortletMethodType.ACTION,
							processActionMethod));
				}

				Method destroyMethod = beanPortletClass.getMethod("destroy");

				if (!destroyMethod.isAnnotationPresent(DestroyMethod.class)) {
					beanPortletMethods.add(
						beanPortletMethodFactory.create(
							beanPortletClass, BeanPortletMethodType.DESTROY,
							destroyMethod));
				}

				Method initMethod = beanPortletClass.getMethod(
					"init", PortletConfig.class);

				if (!initMethod.isAnnotationPresent(InitMethod.class)) {
					beanPortletMethods.add(
						beanPortletMethodFactory.create(
							beanPortletClass, BeanPortletMethodType.INIT,
							initMethod));
				}

				Method renderMethod = beanPortletClass.getMethod(
					"render", RenderRequest.class, RenderResponse.class);

				if (!renderMethod.isAnnotationPresent(RenderMethod.class)) {
					beanPortletMethods.add(
						beanPortletMethodFactory.create(
							beanPortletClass, BeanPortletMethodType.RENDER,
							renderMethod));
				}
			}
			catch (NoSuchMethodException noSuchMethodException) {
				_log.error(noSuchMethodException);
			}
		}

		if (EventPortlet.class.isAssignableFrom(beanPortletClass)) {
			try {
				Method eventMethod = beanPortletClass.getMethod(
					"processEvent", EventRequest.class, EventResponse.class);

				if (!eventMethod.isAnnotationPresent(EventMethod.class)) {
					beanPortletMethods.add(
						beanPortletMethodFactory.create(
							beanPortletClass, BeanPortletMethodType.EVENT,
							eventMethod));
				}
			}
			catch (NoSuchMethodException noSuchMethodException) {
				_log.error(noSuchMethodException);
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
							beanPortletClass, BeanPortletMethodType.HEADER,
							renderHeadersMethod));
				}
			}
			catch (NoSuchMethodException noSuchMethodException) {
				_log.error(noSuchMethodException);
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
							beanPortletClass,
							BeanPortletMethodType.SERVE_RESOURCE,
							serveResourceMethod));
				}
			}
			catch (NoSuchMethodException noSuchMethodException) {
				_log.error(noSuchMethodException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletScannerUtil.class);

}