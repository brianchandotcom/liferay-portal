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

package com.liferay.portal.template.soy.internal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.template.soy.internal.SoyManager;
import com.liferay.portal.template.soy.util.SoyTemplateResourcesProvider;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = SoyTemplateResourcesProvider.class)
public class SoyTemplateResourcesProviderImpl
	implements SoyTemplateResourcesProvider {

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public List<TemplateResource> getAllTemplateResources() {
		if (_soyManager == null) {
			return Collections.<TemplateResource>emptyList();
		}

		return Collections.unmodifiableList(
			_soyManager.getAllTemplateResources());
	}

	@Override
	public List<TemplateResource> getBundleTemplateResources(
		Bundle bundle, String templatePath) {

		try {
			return SoyTemplateResourcesCollectorUtil.getTemplateResources(
				bundle, templatePath);
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

	@Reference(unbind = "-")
	protected void setSoyManager(SoyManager soyManager) {
		_soyManager = soyManager;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SoyTemplateResourcesProviderImpl.class);

	private static SoyManager _soyManager;

}