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

package com.liferay.commerce.product.content.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.content.web.internal.display.context.CPCompareContentDisplayContext;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueService;
import com.liferay.commerce.product.service.CPMeasurementUnitService;
import com.liferay.commerce.product.service.CPOptionCategoryService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CPPortletKeys.CP_COMPARE_CONTENT_WEB,
	service = ConfigurationAction.class
)
public class CPCompareContentConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		try {
			CPCompareContentDisplayContext cpCompareContentDisplayContext =
				new CPCompareContentDisplayContext(
					_cpDefinitionHelper, _cpDefinitionService,
					_cpDefinitionSpecificationOptionValueService,
					_cpMeasurementUnitService, _cpOptionCategoryService,
					_ddmFormFieldTypeServicesTracker, httpServletRequest);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				cpCompareContentDisplayContext);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return "/compare_products/configuration.jsp";
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.content.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPCompareContentConfigurationAction.class);

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPDefinitionSpecificationOptionValueService
		_cpDefinitionSpecificationOptionValueService;

	@Reference
	private CPMeasurementUnitService _cpMeasurementUnitService;

	@Reference
	private CPOptionCategoryService _cpOptionCategoryService;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private Portal _portal;

}