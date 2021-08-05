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

package com.liferay.site.initializer.extender.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.util.Dictionary;
import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;

/**
 * @author Brian Wing Shun Chan
 */
public class BundleSiteInitializer implements SiteInitializer {

	public BundleSiteInitializer(Bundle bundle, ServletContext servletContext) {
		_bundle = bundle;
		_servletContext = servletContext;
	}

	@Override
	public String getDescription(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getKey() {
		return _bundle.getSymbolicName();
	}

	@Override
	public String getName(Locale locale) {
		Dictionary<String, String> headers = _bundle.getHeaders(
			StringPool.BLANK);

		return GetterUtil.getString(headers.get("Bundle-Name"));
	}

	@Override
	public String getThumbnailSrc() {
		return StringPool.BLANK;
	}

	@Override
	public void initialize(long groupId) throws InitializationException {
	}

	@Override
	public boolean isActive(long companyId) {
		return true;
	}

	private final Bundle _bundle;
	private final ServletContext _servletContext;

}