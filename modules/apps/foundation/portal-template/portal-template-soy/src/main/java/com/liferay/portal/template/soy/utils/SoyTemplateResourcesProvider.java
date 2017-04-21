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

package com.liferay.portal.template.soy.utils;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.template.soy.internal.SoyTemplateResourcesCollector;
import com.liferay.portal.template.soy.internal.SoyTemplateResourcesTracker;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Pastor
 */
@Component(immediate = true)
public class SoyTemplateResourcesProvider {

	public static List<TemplateResource> getAllTemplateResources() {
		return Collections.unmodifiableList(
			_soyTemplateResourcesTracker.getAllTemplateResources());
	}

	public static List<TemplateResource> getBundleTemplateResources(
		Bundle bundle, String templatePath) {

		try {
			SoyTemplateResourcesCollector soyTemplateResourcesCollector =
				new SoyTemplateResourcesCollector(bundle, templatePath);

			return soyTemplateResourcesCollector.getTemplateResources();
		}
		catch (TemplateException te) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get template resources for bundle " +
						bundle.getBundleId(),
					te);
			}
		}

		return Collections.emptyList();
	}

	public static Bundle getTemplateResourceBundle(
		TemplateResource templateResource) {

		return _soyTemplateResourcesTracker.getTemplateBundle(
			templateResource.getTemplateId());
	}

	@Reference(unbind = "-")
	protected void setSoyTemplateResourcesTracker(
		SoyTemplateResourcesTracker soyTemplateResourcesTracker) {

		_soyTemplateResourcesTracker = soyTemplateResourcesTracker;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SoyTemplateResourcesProvider.class);

	private static SoyTemplateResourcesTracker _soyTemplateResourcesTracker;

}