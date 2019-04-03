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

package com.liferay.frontend.taglib.clay.internal;

import com.liferay.portal.url.builder.AbsolutePortalURLBuilder;
import com.liferay.portal.url.builder.AbsolutePortalURLBuilderFactory;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(immediate = true, service = {})
public class AbsolutePortalURLBuilderProvider {

	public static AbsolutePortalURLBuilder getAbsolutePortalURLBuilder(
		HttpServletRequest httpServletRequest) {

		if (_absolutePortalURLBuilderProvider == null) {
			return null;
		}

		AbsolutePortalURLBuilderFactory absolutePortalURLBuilderFactory =
			_absolutePortalURLBuilderProvider._absolutePortalURLBuilderFactory;

		return absolutePortalURLBuilderFactory.getAbsolutePortalURLBuilder(
			httpServletRequest);
	}

	public AbsolutePortalURLBuilderProvider() {
		_absolutePortalURLBuilderProvider = this;
	}

	private static AbsolutePortalURLBuilderProvider
		_absolutePortalURLBuilderProvider;

	@Reference
	private AbsolutePortalURLBuilderFactory _absolutePortalURLBuilderFactory;

}