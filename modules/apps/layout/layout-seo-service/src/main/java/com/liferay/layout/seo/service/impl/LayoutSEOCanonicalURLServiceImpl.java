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

package com.liferay.layout.seo.service.impl;

import com.liferay.layout.seo.model.LayoutSEOCanonicalURL;
import com.liferay.layout.seo.service.base.LayoutSEOCanonicalURLServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"json.web.service.context.name=layoutseo",
		"json.web.service.context.path=LayoutSEOCanonicalURL"
	},
	service = AopService.class
)
public class LayoutSEOCanonicalURLServiceImpl
	extends LayoutSEOCanonicalURLServiceBaseImpl {

	@Override
	public LayoutSEOCanonicalURL updateLayoutSEOCanonicalURL(
			long groupId, boolean privateLayout, long layoutId, boolean enabled,
			Map<Locale, String> canonicalURLMap, ServiceContext serviceContext)
		throws PortalException {

		Layout layout = layoutLocalService.getLayout(
			groupId, privateLayout, layoutId);

		LayoutPermissionUtil.check(
			getPermissionChecker(), layout, ActionKeys.UPDATE);

		return layoutSEOCanonicalURLLocalService.updateLayoutSEOCanonicalURL(
			getUserId(), groupId, privateLayout, layoutId, enabled,
			canonicalURLMap, serviceContext);
	}

}