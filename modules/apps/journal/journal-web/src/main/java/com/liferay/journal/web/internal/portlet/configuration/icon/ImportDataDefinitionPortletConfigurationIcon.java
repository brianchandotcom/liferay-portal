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

package com.liferay.journal.web.internal.portlet.configuration.icon;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.web.internal.configuration.FFImportDataDefinitionConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;

import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	configurationPid = "com.liferay.journal.web.internal.configuration.FFImportDataDefinitionConfiguration",
	immediate = true,
	property = {
		"javax.portlet.name=" + JournalPortletKeys.JOURNAL,
		"path=/view_ddm_structures.jsp"
	},
	service = PortletConfigurationIcon.class
)
public class ImportDataDefinitionPortletConfigurationIcon
	extends BaseJSPPortletConfigurationIcon {

	@Override
	public String getJspPath() {
		return "/configuration/icon/import_data_definition.jsp";
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		return _ffImportDataDefinitionConfiguration.enabled();
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.journal.web)", unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_ffImportDataDefinitionConfiguration =
			ConfigurableUtil.createConfigurable(
				FFImportDataDefinitionConfiguration.class, properties);
	}

	private FFImportDataDefinitionConfiguration
		_ffImportDataDefinitionConfiguration;

}