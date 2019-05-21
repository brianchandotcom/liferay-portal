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

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;

import javax.portlet.EventPortlet;
import javax.portlet.HeaderPortlet;
import javax.portlet.Portlet;
import javax.portlet.PortletAsyncListener;
import javax.portlet.ResourceServingPortlet;
import javax.portlet.annotations.ActionMethod;
import javax.portlet.annotations.DestroyMethod;
import javax.portlet.annotations.EventMethod;
import javax.portlet.annotations.HeaderMethod;
import javax.portlet.annotations.InitMethod;
import javax.portlet.annotations.PortletLifecycleFilter;
import javax.portlet.annotations.PortletRequestScoped;
import javax.portlet.annotations.PortletSessionScoped;
import javax.portlet.annotations.RenderMethod;
import javax.portlet.annotations.RenderStateScoped;
import javax.portlet.annotations.ServeResourceMethod;
import javax.portlet.filter.ActionFilter;
import javax.portlet.filter.EventFilter;
import javax.portlet.filter.HeaderFilter;
import javax.portlet.filter.RenderFilter;
import javax.portlet.filter.ResourceFilter;

import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

/**
 * @author Neil Griffin
 */
public class PortletTypeFilter implements TypeFilter {

	@Override
	public boolean match(
			MetadataReader metadataReader,
			MetadataReaderFactory metadataReaderFactory)
		throws IOException {

		AnnotationMetadata metaData = metadataReader.getAnnotationMetadata();

		if (metaData.hasAnnotation(ApplicationScoped.class.getName()) ||
			metaData.hasAnnotation(Dependent.class.getName()) ||
			metaData.hasAnnotation(PortletLifecycleFilter.class.getName()) ||
			metaData.hasAnnotation(PortletRequestScoped.class.getName()) ||
			metaData.hasAnnotation(PortletSessionScoped.class.getName()) ||
			metaData.hasAnnotation(RenderStateScoped.class.getName()) ||
			metaData.hasAnnotation(RequestScoped.class.getName()) ||
			metaData.hasAnnotation(SessionScoped.class.getName()) ||
			metaData.hasAnnotatedMethods(ActionMethod.class.getName()) ||
			metaData.hasAnnotatedMethods(DestroyMethod.class.getName()) ||
			metaData.hasAnnotatedMethods(EventMethod.class.getName()) ||
			metaData.hasAnnotatedMethods(HeaderMethod.class.getName()) ||
			metaData.hasAnnotatedMethods(InitMethod.class.getName()) ||
			metaData.hasAnnotatedMethods(RenderMethod.class.getName()) ||
			metaData.hasAnnotatedMethods(ServeResourceMethod.class.getName())) {

			return true;
		}

		return _matchInterfacesRecurse(metadataReader, metadataReaderFactory);
	}

	private boolean _matchInterfacesRecurse(
			MetadataReader metadataReader,
			MetadataReaderFactory metadataReaderFactory)
		throws IOException {

		ClassMetadata classMetadata = metadataReader.getClassMetadata();

		if (classMetadata.isAnnotation() || classMetadata.isInterface() ||
			!classMetadata.isIndependent()) {

			return false;
		}

		String[] interfaceNames = classMetadata.getInterfaceNames();

		for (String interfaceName : interfaceNames) {
			if (interfaceName.equals(ActionFilter.class.getName()) ||
				interfaceName.equals(EventFilter.class.getName()) ||
				interfaceName.equals(EventPortlet.class.getName()) ||
				interfaceName.equals(HeaderFilter.class.getName()) ||
				interfaceName.equals(HeaderPortlet.class.getName()) ||
				interfaceName.equals(Portlet.class.getName()) ||
				interfaceName.equals(PortletAsyncListener.class.getName()) ||
				interfaceName.equals(RenderFilter.class.getName()) ||
				interfaceName.equals(ResourceFilter.class.getName()) ||
				interfaceName.equals(ResourceServingPortlet.class.getName())) {

				return true;
			}
		}

		String superClassName = classMetadata.getSuperClassName();

		if (superClassName.equals(Object.class.getName())) {
			return false;
		}

		return _matchInterfacesRecurse(
			metadataReaderFactory.getMetadataReader(superClassName),
			metadataReaderFactory);
	}

}