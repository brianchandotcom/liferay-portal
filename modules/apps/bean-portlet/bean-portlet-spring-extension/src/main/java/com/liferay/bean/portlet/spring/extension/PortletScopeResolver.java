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

package com.liferay.bean.portlet.spring.extension;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.List;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

import javax.portlet.PortletSession;
import javax.portlet.annotations.PortletRequestScoped;
import javax.portlet.annotations.PortletSerializable;
import javax.portlet.annotations.PortletSessionScoped;
import javax.portlet.annotations.RenderStateScoped;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.MultiValueMap;

/**
 * @author Neil Griffin
 */
public class PortletScopeResolver extends AnnotationScopeMetadataResolver {

	@Override
	public ScopeMetadata resolveScopeMetadata(BeanDefinition beanDefinition) {
		if (beanDefinition instanceof AnnotatedBeanDefinition) {
			AnnotatedBeanDefinition annotatedBeanDefinition =
				(AnnotatedBeanDefinition)beanDefinition;

			AnnotationMetadata annotationMetadata =
				annotatedBeanDefinition.getMetadata();

			Set<String> annotationTypes =
				annotationMetadata.getAnnotationTypes();

			ScopeMetadata scopeMetadata = null;

			if (annotationTypes.contains(Dependent.class.getName())) {
				scopeMetadata = new ScopeMetadata();

				scopeMetadata.setScopeName("prototype");
				scopeMetadata.setScopedProxyMode(ScopedProxyMode.NO);
			}
			else if (annotationTypes.contains(
						PortletRequestScoped.class.getName()) ||
					 annotationTypes.contains(RequestScoped.class.getName()) ||
					 _hasScopeAnnotation(
						 annotationMetadata, annotationTypes, "request")) {

				scopeMetadata = new ScopeMetadata();

				scopeMetadata.setScopeName("portletRequest");
				scopeMetadata.setScopedProxyMode(ScopedProxyMode.TARGET_CLASS);
			}
			else if (annotationTypes.contains(
						PortletSessionScoped.class.getName()) ||
					 annotationTypes.contains(SessionScoped.class.getName()) ||
					 _hasScopeAnnotation(
						 annotationMetadata, annotationTypes, "session")) {

				MultiValueMap<String, Object> annotationAttributes =
					annotationMetadata.getAllAnnotationAttributes(
						PortletSessionScoped.class.getName());

				int subscope = PortletSession.PORTLET_SCOPE;

				if (annotationAttributes != null) {
					List<Object> values = annotationAttributes.get("value");

					if ((values != null) && !values.isEmpty()) {
						subscope = GetterUtil.getInteger(
							values.get(0), PortletSession.PORTLET_SCOPE);
					}
				}

				scopeMetadata = new ScopeMetadata();

				if (subscope == PortletSession.PORTLET_SCOPE) {
					scopeMetadata.setScopeName("portletSession");
				}
				else {
					scopeMetadata.setScopeName("portletAppSession");
				}

				scopeMetadata.setScopedProxyMode(ScopedProxyMode.TARGET_CLASS);
			}
			else if (_hasScopeAnnotation(
						annotationMetadata, annotationTypes, "globalSession")) {

				scopeMetadata = new ScopeMetadata();

				scopeMetadata.setScopeName("portletAppSession");
				scopeMetadata.setScopedProxyMode(ScopedProxyMode.TARGET_CLASS);
			}
			else if (annotationTypes.contains(
						RenderStateScoped.class.getName())) {

				boolean implementsPortletSerializable = false;

				String[] interfaceNames =
					annotationMetadata.getInterfaceNames();

				for (String interfaceName : interfaceNames) {
					if (interfaceName.equals(
							PortletSerializable.class.getName())) {

						implementsPortletSerializable = true;

						break;
					}
				}

				if (implementsPortletSerializable) {
					scopeMetadata = new ScopeMetadata();

					scopeMetadata.setScopeName("portletRenderState");
					scopeMetadata.setScopedProxyMode(
						ScopedProxyMode.TARGET_CLASS);
				}
				else {
					_log.error(
						beanDefinition.getBeanClassName() +
							" does not implement " +
								PortletSerializable.class.getName());
				}
			}

			if (scopeMetadata != null) {
				return scopeMetadata;
			}
		}

		return super.resolveScopeMetadata(beanDefinition);
	}

	private boolean _hasScopeAnnotation(
		AnnotationMetadata annotationMetadata, Set<String> annotationTypes,
		String scopeName) {

		if (annotationTypes.contains(Scope.class.getName())) {
			MultiValueMap<String, Object> annotationAttributes =
				annotationMetadata.getAllAnnotationAttributes(
					Scope.class.getName());

			String annotatedScopeName = null;

			List<Object> values = annotationAttributes.get("scopeName");

			if ((values != null) && !values.isEmpty()) {
				annotatedScopeName = (String)values.get(0);
			}

			if (annotatedScopeName == null) {
				values = annotationAttributes.get("value");

				if ((values != null) && !values.isEmpty()) {
					annotatedScopeName = (String)values.get(0);
				}
			}

			if (scopeName.equals(annotatedScopeName)) {
				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletScopeResolver.class);

}