/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.development.web.internal.portlet;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.search.experiences.development.web.internal.constants.SXPDevelopmentPortletKeys;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.search",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=search-experiences-development-portlet",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SXPDevelopmentPortletKeys.SEARCH_EXPERIENCES_DEVELOPMENT,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class SXPDevelopmentPortlet extends MVCPortlet {
}