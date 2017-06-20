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

package com.liferay.portal.template.soy.internal;

import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.template.TemplateResourceParser;
import com.liferay.portal.template.URLResourceParser;
import com.liferay.portal.template.soy.utils.SoyTemplateUtil;

import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {"lang.type=" + TemplateConstants.LANG_TYPE_SOY},
	service =
		{SoyTemplateBundleResourceParser.class, TemplateResourceParser.class}
)
public class SoyTemplateBundleResourceParser extends URLResourceParser {

	@Override
	public URL getURL(String templateId) {
		long bundleId = SoyTemplateUtil.getBundleId(templateId);

		Bundle bundle = _bundleContext.getBundle(bundleId);

		int index = templateId.indexOf(TemplateConstants.BUNDLE_SEPARATOR);

		String templateName = templateId.substring(
			index + TemplateConstants.BUNDLE_SEPARATOR.length());

		return bundle.getResource(templateName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private BundleContext _bundleContext;

}