/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.config.AbstractMessagingConfigurator;

import org.hibernate.property.BasicPropertyAccessor;
import org.hibernate.tuple.entity.EntityTuplizerFactory;
import org.hibernate.util.ReflectHelper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.SimpleInstantiationStrategy;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.util.ReflectionUtils;

import sun.reflect.Reflection;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public abstract class BaseReflectChecker extends BaseChecker {

	protected boolean hasReflect(String name, String actions) {
		/*for (int i = 6; i < 11; i++) {
			System.out.println(
				"caller class " + i + " " + Reflection.getCallerClass(i));
		}*/

		// JSP compiler

		if (isJSPCompiler(name, actions)) {
			return true;
		}

		// com.liferay.portal.kernel.messaging.config.
		// AbstractMessagingConfigurator

		Class<?> callerClass9 = Reflection.getCallerClass(9);

		if (callerClass9 == AbstractMessagingConfigurator.class) {
			logReflect(callerClass9, 9);

			return true;
		}

		// java.lang.Class

		Class<?> callerClass7 = Reflection.getCallerClass(7);

		if ((callerClass7.getEnclosingClass() == Class.class) &&
			CheckerUtil.isAccessControllerDoPrivileged(8)) {

			logReflect(callerClass7, 7);

			return true;
		}

		// java.lang.Thread

		if ((callerClass9.getEnclosingClass() == Thread.class) &&
			CheckerUtil.isAccessControllerDoPrivileged(10)) {

			logReflect(callerClass9, 9);

			return true;
		}

		// org.hibernate.property.BasicPropertyAccessor

		if (callerClass9 == BasicPropertyAccessor.class) {
			logReflect(callerClass9, 9);

			return true;
		}

		// org.hibernate.tuple.entity.EntityTuplizerFactory

		if (callerClass7 == EntityTuplizerFactory.class) {
			logReflect(callerClass7, 7);

			return true;
		}

		// org.hibernate.util.ReflectHelper

		if (callerClass9 == ReflectHelper.class) {
			logReflect(callerClass9, 9);

			return true;
		}

		// org.springframework.beans.BeanUtils

		if (callerClass9 == BeanUtils.class) {
			logReflect(callerClass9, 9);

			return true;
		}

		// org.springframework.beans.factory.support.SimpleInstantiationStrategy

		if (callerClass9.getEnclosingClass() ==
				SimpleInstantiationStrategy.class) {

			logReflect(callerClass9, 9);

			return true;
		}

		// org.springframework.core.LocalVariableTableParameterNameDiscoverer

		if (callerClass9.getEnclosingClass() ==
				LocalVariableTableParameterNameDiscoverer.class) {

			logReflect(callerClass9, 9);

			return true;
		}

		// org.springframework.util.ReflectionUtils

		if (callerClass7 == ReflectionUtils.class) {
			logReflect(callerClass7, 7);

			return true;
		}

		if (callerClass9 == ReflectionUtils.class) {
			logReflect(callerClass9, 9);

			return true;
		}

		// Reject

		if (_log.isDebugEnabled()) {
			_log.debug("Rejecting call stack:");

			for (int i = 6; i < 11; i++) {
				_log.debug("Frame " + i + " " + Reflection.getCallerClass(i));
			}
		}

		return false;
	}

	protected void logReflect(Class<?> callerClass, int frame) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Allowing frame " + frame + " with caller " + callerClass +
					" to reflect");
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BaseReflectChecker.class);

}