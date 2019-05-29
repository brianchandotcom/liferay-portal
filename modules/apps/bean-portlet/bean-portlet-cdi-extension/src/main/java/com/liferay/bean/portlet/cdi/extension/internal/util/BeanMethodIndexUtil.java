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

package com.liferay.bean.portlet.cdi.extension.internal.util;

import com.liferay.bean.portlet.cdi.extension.internal.BeanMethod;
import com.liferay.bean.portlet.cdi.extension.internal.MethodType;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.annotations.ActionMethod;
import javax.portlet.annotations.EventMethod;
import javax.portlet.annotations.PortletQName;

import javax.xml.namespace.QName;

/**
 * @author Shuyang Zhou
 */
public class BeanMethodIndexUtil {

	public static Map<MethodType, List<BeanMethod>> indexBeanMethods(
		Set<BeanMethod> beanMethods) {

		EnumMap<MethodType, List<BeanMethod>> beanMethodsEnumMap =
			new EnumMap<>(MethodType.class);

		for (BeanMethod beanMethod : beanMethods) {
			beanMethodsEnumMap.compute(
				beanMethod.getMethodType(),
				(methodType, valueBeanMethods) -> {
					if (valueBeanMethods == null) {
						valueBeanMethods = new ArrayList<>();
					}

					valueBeanMethods.add(beanMethod);

					return valueBeanMethods;
				});
		}

		beanMethodsEnumMap.forEach(
			(methodType, valueBeanMethods) -> Collections.sort(
				valueBeanMethods));

		return beanMethodsEnumMap;
	}

	public static void scanSupportedEvents(
		Map<MethodType, List<BeanMethod>> beanMethodsMap,
		Set<QName> supportedProcessingEvents,
		Set<QName> supportedPublishingEvents) {

		List<BeanMethod> eventBeanMethods = beanMethodsMap.get(
			MethodType.EVENT);

		if (eventBeanMethods != null) {
			for (BeanMethod beanMethod : eventBeanMethods) {
				Method beanEventMethod = beanMethod.getMethod();

				EventMethod eventMethod = beanEventMethod.getAnnotation(
					EventMethod.class);

				if (eventMethod == null) {
					continue;
				}

				for (PortletQName portletQName :
						eventMethod.processingEvents()) {

					supportedProcessingEvents.add(
						new QName(
							portletQName.namespaceURI(),
							portletQName.localPart()));
				}

				for (PortletQName portletQName :
						eventMethod.publishingEvents()) {

					supportedPublishingEvents.add(
						new QName(
							portletQName.namespaceURI(),
							portletQName.localPart()));
				}
			}
		}

		List<BeanMethod> actionBeanMethods = beanMethodsMap.get(
			MethodType.ACTION);

		if (actionBeanMethods != null) {
			for (BeanMethod beanMethod : actionBeanMethods) {
				Method method = beanMethod.getMethod();

				ActionMethod actionMethod = method.getAnnotation(
					ActionMethod.class);

				if (actionMethod == null) {
					continue;
				}

				for (PortletQName portletQName :
						actionMethod.publishingEvents()) {

					supportedPublishingEvents.add(
						new QName(
							portletQName.namespaceURI(),
							portletQName.localPart()));
				}
			}
		}
	}

}