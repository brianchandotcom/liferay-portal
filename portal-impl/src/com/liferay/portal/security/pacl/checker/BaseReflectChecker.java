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
import com.liferay.portal.kernel.util.JavaDetector;
import com.liferay.portal.kernel.util.PathUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.security.pacl.PACLClassUtil;

import java.beans.Introspector;

import jodd.util.ReflectUtil;

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
		/*for (int i = 6; i <= 15; i++) {
			System.out.println(
				"Caller class " + i + " " + Reflection.getCallerClass(i));
		}*/

		// JSP compiler

		if (isJSPCompiler(name, actions)) {
			return true;
		}

		// com.caucho.config.reflect.ReflectionAnnotatedType

		Class<?> callerClass9 = Reflection.getCallerClass(9);

		if (isResinReflectionAnnotatedType(callerClass9)) {
			logReflect(callerClass9, 9);

			return true;
		}

		// com.caucho.server.session.JavaSessionSerializer

		if (isResinJavaSessionSerializer()) {
			return true;
		}

		// com.liferay.portal.kernel.messaging.config.
		// AbstractMessagingConfigurator

		if (callerClass9 == AbstractMessagingConfigurator.class) {
			logReflect(callerClass9, 9);

			return true;
		}

		// java.beans.Introspector

		//if (JavaDetector.isOpenJDK()) {
			if ((callerClass9.getEnclosingClass() == Introspector.class) &&
				CheckerUtil.isAccessControllerDoPrivileged(10)) {

				logReflect(callerClass9, 9);

				return true;
			}
		//}

		// jodd.util.ReflectUtil

		if (callerClass9 == ReflectUtil.class) {
			logReflect(callerClass9, 9);

			return true;
		}

		// java.lang.Class

		Class<?> callerClass7 = Reflection.getCallerClass(7);

		if (JavaDetector.isIBM() || JavaDetector.isJDK7()) {
			Class<?> callerClass8 = Reflection.getCallerClass(8);

			if ((callerClass8.getEnclosingClass() == Class.class) &&
				CheckerUtil.isAccessControllerDoPrivileged(9)) {

				logReflect(callerClass8, 8);

				return true;
			}
		}
		else {
			if ((callerClass7.getEnclosingClass() == Class.class) &&
				CheckerUtil.isAccessControllerDoPrivileged(8)) {

				logReflect(callerClass7, 7);

				return true;
			}
		}

		// java.lang.Thread

		if (JavaDetector.isJDK7() || JavaDetector.isOpenJDK()) {
			Class<?> callerClass10 = Reflection.getCallerClass(10);

			if ((callerClass10.getEnclosingClass() == Thread.class) &&
				CheckerUtil.isAccessControllerDoPrivileged(11)) {

				logReflect(callerClass10, 10);

				return true;
			}
		}
		else {
			if ((callerClass9.getEnclosingClass() == Thread.class) &&
				CheckerUtil.isAccessControllerDoPrivileged(10)) {

				logReflect(callerClass9, 9);

				return true;
			}
		}

		// org.apache.felix.framework.util.SecureAction

		if (isGlassfishSecureAction(
				callerClass7.getEnclosingClass()) &&
			CheckerUtil.isAccessControllerDoPrivileged(8)) {

			logReflect(callerClass7, 7);

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

		// weblogic.spring.monitoring.utils.AbstractApplicationContextDelegator

		if (isWebLogicAbstractApplicationContextDelegator(callerClass9)) {
			logReflect(callerClass9, 9);

			return true;
		}

		// weblogic.spring.monitoring.utils.AbstractBeanDefinitionDelegator

		if (isWebLogicAbstractBeanDefinitionDelegator(callerClass9)) {
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

	protected boolean isGlassfishSecureAction(Class<?> clazz) {
		if (!ServerDetector.isGlassfish()) {
			return false;
		}

		if (clazz == null) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_SECURE_ACTION)) {
			return false;
		}

		String classLocation = PACLClassUtil.getClassLocation(clazz);

		return classLocation.contains("/osgi/felix/bin/felix.jar!");
	}

	protected boolean isResinJavaSessionSerializer() {
		if (!ServerDetector.isResin()) {
			return false;
		}

		for (int i = 7;; i++) {
			Class<?> callerClass = Reflection.getCallerClass(i);

			if (callerClass == null) {
				return false;
			}

			String callerClassName = callerClass.getName();

			if (!callerClassName.equals(_CLASS_NAME_JAVA_SESSION_SERIALIZER)) {
				continue;
			}

			String actualClassLocation = PACLClassUtil.getClassLocation(
				callerClass);
			String expectedClassLocation = PathUtil.toUnixPath(
				System.getProperty("resin.home") + "/lib/resin.jar!/");

			if (actualClassLocation.contains(expectedClassLocation)) {
				logReflect(callerClass, i);

				return true;
			}

			return false;
		}
	}

	protected boolean isResinReflectionAnnotatedType(Class<?> clazz) {
		if (!ServerDetector.isResin()) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_REFLECTION_ANNOTATED_TYPE)) {
			return false;
		}

		String actualClassLocation = PACLClassUtil.getClassLocation(clazz);
		String expectedClassLocation = PathUtil.toUnixPath(
			System.getProperty("resin.home") + "/lib/resin.jar!/");

		return actualClassLocation.contains(expectedClassLocation);
	}

	protected boolean isWebLogicAbstractApplicationContextDelegator(
		Class<?> clazz) {

		if (!ServerDetector.isWebLogic()) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(
				_CLASS_NAME_ABSTRACT_APPLICATION_CONTEXT_DELEGATOR)) {

			return false;
		}

		String classLocation = PACLClassUtil.getClassLocation(clazz);

		if (classLocation.contains(
				"/modules/com.bea.core.weblogic.spring.instrument_") ||
			classLocation.contains("/patch_jars/BUG")) {

			return true;
		}

		return false;
	}

	protected boolean isWebLogicAbstractBeanDefinitionDelegator(
		Class<?> clazz) {

		if (!ServerDetector.isWebLogic()) {
			return false;
		}

		String className = clazz.getName();

		if (!className.equals(_CLASS_NAME_ABSTRACT_BEAN_DEFINITION_DELEGATOR)) {
			return false;
		}

		String classLocation = PACLClassUtil.getClassLocation(clazz);

		if (classLocation.contains(
				"/modules/com.bea.core.weblogic.spring.instrument_") ||
			classLocation.contains("/patch_jars/BUG")) {

			return true;
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

	private static final String _CLASS_NAME_ABSTRACT_BEAN_DEFINITION_DELEGATOR =
		"weblogic.spring.monitoring.utils.AbstractBeanDefinitionDelegator";

	private static final String
		_CLASS_NAME_ABSTRACT_APPLICATION_CONTEXT_DELEGATOR =
			"weblogic.spring.monitoring.utils." +
				"AbstractApplicationContextDelegator";

	private static final String _CLASS_NAME_JAVA_SESSION_SERIALIZER =
		"com.caucho.server.session.JavaSessionSerializer";

	private static final String _CLASS_NAME_REFLECTION_ANNOTATED_TYPE =
		"com.caucho.config.reflect.ReflectionAnnotatedType";

	private static final String _CLASS_NAME_SECURE_ACTION =
		"org.apache.felix.framework.util.SecureAction";

	private static Log _log = LogFactoryUtil.getLog(BaseReflectChecker.class);

}