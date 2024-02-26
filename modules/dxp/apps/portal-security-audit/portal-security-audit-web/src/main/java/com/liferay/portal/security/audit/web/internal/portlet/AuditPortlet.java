/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.web.internal.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.security.audit.web.internal.constants.AuditPortletKeys;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Greenwald
 */
@Component(
	property = {
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.icon=/icons/audit.png",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=Audit", "javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + AuditPortletKeys.AUDIT,
		"javax.portlet.portlet-info.short-title=Audit",
		"javax.portlet.portlet-info.title=Audit",
		"javax.portlet.portlet-mode=text/html;view",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class AuditPortlet extends MVCPortlet {
}