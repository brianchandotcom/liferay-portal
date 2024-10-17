/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet;

import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.layout.content.page.editor.web.internal.portlet.constants.LayoutContentPageEditorWebPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.instanceable=false",
		"com.liferay.portlet.preferences-owned-by-group=true",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Noninstanciable Test",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.template-path=/META-INF/resources/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + LayoutContentPageEditorWebPortletKeys.LAYOUT_CONTENT_PAGE_EDITOR_WEB_NONINSTANCEABLE_TEST_PORTLET,
		"javax.portlet.version=3.0"
	},
	service = Portlet.class
)
public class LayoutContentPageEditorWebNoninstanceableTestPortlet
	extends MVCPortlet {

	@Activate
	protected void activate() {
		_portletRegistry.registerAlias(
			LayoutContentPageEditorWebPortletKeys.
				LAYOUT_CONTENT_PAGE_EDITOR_WEB_NONINSTANCEABLE_TEST_PORTLET_ALIAS,
			LayoutContentPageEditorWebPortletKeys.
				LAYOUT_CONTENT_PAGE_EDITOR_WEB_NONINSTANCEABLE_TEST_PORTLET);
	}

	@Deactivate
	protected void deactivate() {
		_portletRegistry.unregisterAlias(
			LayoutContentPageEditorWebPortletKeys.
				LAYOUT_CONTENT_PAGE_EDITOR_WEB_NONINSTANCEABLE_TEST_PORTLET_ALIAS);
	}

	@Reference
	private PortletRegistry _portletRegistry;

}