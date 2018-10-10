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
		Set<BeanMethod> beanMethods, Set<QName> supportedProcessingEvents,
		Set<QName> supportedPublishingEvents) {

		EnumMap<MethodType, List<BeanMethod>> beanMethodMap = new EnumMap<>(
			MethodType.class);

		for (BeanMethod beanMethod : beanMethods) {
			beanMethodMap.compute(
				beanMethod.getType(),
				(methodType, valueBeanMethods) -> {
					if (valueBeanMethods == null) {
						valueBeanMethods = new ArrayList<>();
					}

					if ((methodType == MethodType.HEADER) ||
						(methodType == MethodType.RENDER) ||
						(methodType == MethodType.SERVE_RESOURCE)) {

						int index = Collections.binarySearch(
							valueBeanMethods, beanMethod);

						if (index < 0) {
							index = -index - 1;
						}

						valueBeanMethods.add(index, beanMethod);
					}
					else {
						valueBeanMethods.add(beanMethod);
					}

					return valueBeanMethods;
				});
		}

		List<BeanMethod> eventBeanMethods = beanMethodMap.get(MethodType.EVENT);

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

		List<BeanMethod> actionMethods = beanMethodMap.get(MethodType.ACTION);

		if (actionMethods != null) {
			for (BeanMethod beanMethod : actionMethods) {
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

		return beanMethodMap;
	}

}