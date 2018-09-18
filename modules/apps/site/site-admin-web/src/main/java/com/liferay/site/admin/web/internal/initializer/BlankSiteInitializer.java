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

package com.liferay.site.admin.web.internal.initializer;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.site.exception.InitializationException;
import com.liferay.site.initializer.SiteInitializer;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "site.initializer.key=" + BlankSiteInitializer.KEY,
	service = SiteInitializer.class
)
public class BlankSiteInitializer implements SiteInitializer {

	public static final String KEY = "blank-site-initializer";

	@Override
	public String getDescription(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getName(Locale locale) {
		return LanguageUtil.get(locale, "blank-site");
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

}