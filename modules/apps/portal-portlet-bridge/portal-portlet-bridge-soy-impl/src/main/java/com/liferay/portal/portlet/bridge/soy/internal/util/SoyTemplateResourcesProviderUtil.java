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
import com.liferay.portal.template.soy.util.SoyTemplateResourcesProvider;

import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Tambara
 */
@Component(immediate = true, service = {})
public class SoyTemplateResourcesProviderUtil {

	public static List<TemplateResource> getBundleTemplateResources(
		Bundle bundle, String templatePath) {

		return _soyTemplateResourcesProvider.getBundleTemplateResources(
			bundle, templatePath);
	}

	@Reference(unbind = "-")
	protected void setSoyTemplateResourcesProvider(
		SoyTemplateResourcesProvider soyTemplateResourcesProvider) {

		_soyTemplateResourcesProvider = soyTemplateResourcesProvider;
	}

	private static SoyTemplateResourcesProvider _soyTemplateResourcesProvider;

}