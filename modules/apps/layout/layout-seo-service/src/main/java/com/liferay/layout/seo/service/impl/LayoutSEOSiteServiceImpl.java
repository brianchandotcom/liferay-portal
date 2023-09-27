/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.service.impl;

import com.liferay.layout.seo.service.base.LayoutSEOSiteServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=layoutseo",
		"json.web.service.context.path=LayoutSEOSite"
	},
	service = AopService.class
)
public class LayoutSEOSiteServiceImpl extends LayoutSEOSiteServiceBaseImpl {
}