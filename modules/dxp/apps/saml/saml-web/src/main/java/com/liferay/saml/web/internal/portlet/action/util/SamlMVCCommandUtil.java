/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.web.internal.portlet.action.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.saml.runtime.exception.StatusException;

import javax.portlet.PortletRequest;

/**
 * @author Arthur Chan
 */
public class SamlMVCCommandUtil {

	public static void handleException(
		Exception exception, PortletRequest portletRequest, Log log) {

		if (log.isDebugEnabled()) {
			log.debug(exception, exception);
		}
		else {
			log.error(exception.getMessage());
		}

		Class<?> clazz = exception.getClass();

		SessionErrors.add(portletRequest, clazz.getName());

		if (exception instanceof StatusException) {
			StatusException statusException = (StatusException)exception;

			SessionErrors.add(
				portletRequest, "statusCodeURI", statusException.getMessage());
		}
	}

	public static ClassLoader switchContextClassLoader(
		ClassLoader classLoader) {

		Thread currentThread = Thread.currentThread();

		ClassLoader currentClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(classLoader);

		return currentClassLoader;
	}

}