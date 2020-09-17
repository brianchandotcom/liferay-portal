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

package com.liferay.portlet.social.util;

import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.language.LanguageResources;
import com.liferay.social.kernel.model.BaseSocialActivityInterpreter;
import com.liferay.social.kernel.model.SocialActivity;
import com.liferay.social.kernel.model.SocialActivityFeedEntry;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalActivityInterpreter extends BaseSocialActivityInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected ResourceBundleLoader acquireResourceBundleLoader() {
		return LanguageResources.PORTAL_RESOURCE_BUNDLE_LOADER;
	}

	@Override
	protected SocialActivityFeedEntry doInterpret(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		return null;
	}

	private static final String[] _CLASS_NAMES = {
		PortalActivityInterpreter.class.getName()
	};

}