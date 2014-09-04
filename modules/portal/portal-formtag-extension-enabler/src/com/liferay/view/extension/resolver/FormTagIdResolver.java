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

package com.liferay.view.extension.resolver;

import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TagIdResolver;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carlos Sierra Andrés
 */
@Component(
	immediate = true, property = {"target=com.liferay.taglib.aui.FormTag"},
	service = TagIdResolver.class
)
public class FormTagIdResolver implements TagIdResolver {

	@Override
	public String getId(
		HttpServletRequest request, HttpServletResponse response,
		Object jspTag) {

		String portletId = PortalUtil.getPortletId(request);

		try {
			return portletId + "-" + PropertyUtils.getProperty(jspTag, "name");
		} catch (
			IllegalAccessException | InvocationTargetException |
				NoSuchMethodException e) {

			return null;
		}
	}

}