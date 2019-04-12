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

package com.liferay.portal.portlet.bridge.soy.internal.util;

import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.template.soy.SoyTemplateResource;
import com.liferay.portal.template.soy.SoyTemplateResourceFactory;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(immediate = true, service = {})
public class SoyTemplateResourceFactoryUtil {

	public static SoyTemplateResource createSoyTemplateResource(
		List<TemplateResource> templateResources) {

		return _soyTemplateResourceFactory.createSoyTemplateResource(
			templateResources);
	}

	@Reference(unbind = "-")
	protected void setSoyTemplateResourceFactory(
		SoyTemplateResourceFactory soyTemplateResourceFactory) {

		_soyTemplateResourceFactory = soyTemplateResourceFactory;
	}

	private static SoyTemplateResourceFactory _soyTemplateResourceFactory;

}