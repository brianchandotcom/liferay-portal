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

package com.liferay.portal.portlet.container.embedded.test;

import com.liferay.portal.kernel.portletdisplaytemplate.BasePortletDisplayTemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.portlet.container.test.TestPortlet;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Manuel de la Peña
 */
@Component(immediate = true, service = TemplateHandler.class)
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
		return "/com/liferay/portal/portlet/container/embedded/test/template/" +
			"dependencies/portlet-display-templates.xml";
	}

}