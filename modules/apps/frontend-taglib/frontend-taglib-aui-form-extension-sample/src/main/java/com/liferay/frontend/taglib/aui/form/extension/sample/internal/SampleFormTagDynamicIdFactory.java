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

package com.liferay.frontend.taglib.aui.form.extension.sample.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanProperties;
import com.liferay.portal.kernel.servlet.taglib.TagDynamicIdFactory;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true, property = "tagClassName=com.liferay.taglib.aui.FormTag",
	service = TagDynamicIdFactory.class
)
public class SampleFormTagDynamicIdFactory implements TagDynamicIdFactory {

	@Override
	public String getTagDynamicId(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, Object tag) {

		String portletId = _portal.getPortletId(httpServletRequest);

		if (Validator.isNull(portletId)) {
			return null;
		}

		String name = _beanProperties.getStringSilent(tag, "name");

		if (Validator.isNull(name)) {
			return null;
		}

		return StringBundler.concat(portletId, StringPool.DASH, name);
	}

	@Reference
	private BeanProperties _beanProperties;

	@Reference
	private Portal _portal;

}