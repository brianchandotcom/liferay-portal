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

package com.liferay.fragment.web.internal.util;

import com.liferay.portal.template.soy.utils.SoyContext;
import com.liferay.portal.template.soy.utils.SoyContextFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Molina
 */
@Component(immediate = true)
public class SoyContextFactoryUtil {

	public static SoyContext createSoyContext() {
		return _soyContextFactory.createSoyContext();
	}

	@Reference(unbind = "-")
	protected void setSoyContextFactory(SoyContextFactory soyContextFactory) {
		_soyContextFactory = soyContextFactory;
	}

	private static SoyContextFactory _soyContextFactory;

}