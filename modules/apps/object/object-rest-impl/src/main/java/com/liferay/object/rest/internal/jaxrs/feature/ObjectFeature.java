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

package com.liferay.object.rest.internal.jaxrs.feature;

import com.liferay.object.rest.internal.jaxrs.param.converter.provider.ScopeKeyParamConverterProvider;
import com.liferay.portal.kernel.service.GroupLocalService;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Carlos Correa
 */
@Component(
	property = {
		"osgi.jaxrs.application.select=(liferay.objects=true)",
		"osgi.jaxrs.extension=true", "osgi.jaxrs.name=Liferay.Object"
	},
	scope = ServiceScope.PROTOTYPE, service = Feature.class
)
public class ObjectFeature implements Feature {

	@Override
	public boolean configure(FeatureContext featureContext) {
		featureContext.register(
			new ScopeKeyParamConverterProvider(_groupLocalService));

		return true;
	}

	@Reference
	private GroupLocalService _groupLocalService;

}