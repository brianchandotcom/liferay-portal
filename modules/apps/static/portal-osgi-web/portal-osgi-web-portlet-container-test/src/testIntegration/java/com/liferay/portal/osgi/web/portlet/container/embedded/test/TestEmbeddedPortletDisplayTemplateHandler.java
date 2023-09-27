/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.portlet.container.embedded.test;

import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.osgi.web.portlet.container.test.TestPortlet;
import com.liferay.portlet.display.template.BasePortletDisplayTemplateHandler;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Manuel de la Peña
 */
@Component(service = TemplateHandler.class)
public class TestEmbeddedPortletDisplayTemplateHandler
	extends BasePortletDisplayTemplateHandler {

	@Override
	public String getClassName() {
		return TestPortlet.class.getName();
	}

	@Override
	public String getDefaultTemplateKey() {
		return "test-adt-multi-column-layout-ftl";
	}

	@Override
	public String getName(Locale locale) {
		return "TEST_ADT_PORTLET_ID";
	}

	@Override
	public String getResourceName() {
		return "TEST_ADT_PORTLET_ID";
	}

	@Override
	protected String getTemplatesConfigPath() {
		return "/com/liferay/portal/osgi/web/portlet/container/embedded/test" +
			"/template/dependencies/portlet-display-templates.xml";
	}

}