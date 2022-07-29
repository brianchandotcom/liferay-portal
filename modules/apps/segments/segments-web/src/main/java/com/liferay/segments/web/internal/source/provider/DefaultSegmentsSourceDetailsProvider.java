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

package com.liferay.segments.web.internal.source.provider;

import com.liferay.portal.kernel.language.Language;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.source.provider.SegmentsSourceDetailsProvider;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "segments.source=" + SegmentsEntryConstants.SOURCE_DEFAULT,
	service = SegmentsSourceDetailsProvider.class
)
public class DefaultSegmentsSourceDetailsProvider
	implements SegmentsSourceDetailsProvider {

	@Override
	public String getIconSrc() {
		return _servletContext.getContextPath() + "/images/ce_icon.svg";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "source.ce");
	}

	@Reference
	private Language _language;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.segments.web)")
	private ServletContext _servletContext;

}