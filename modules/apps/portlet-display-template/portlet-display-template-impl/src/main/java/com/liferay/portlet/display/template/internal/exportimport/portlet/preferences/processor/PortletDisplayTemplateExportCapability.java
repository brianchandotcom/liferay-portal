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

package com.liferay.portlet.display.template.internal.exportimport.portlet.preferences.processor;

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.portlet.preferences.processor.Capability;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.portlet.display.template.exportimport.portlet.preferences.processor.PortletDisplayTemplateRegister;

import javax.portlet.PortletPreferences;

/**
 * @author Máté Thurzó
 */
public class PortletDisplayTemplateExportCapability implements Capability {

	public PortletDisplayTemplateExportCapability(
		Portal portal, PortletLocalService portletLocalService,
		PortletDisplayTemplate portletDisplayTemplate,
		PortletDisplayTemplateRegister portletDisplayTemplateExportRegister) {

		_portal = portal;
		_portletLocalService = portletLocalService;
		_portletDisplayTemplate = portletDisplayTemplate;
		_portletDisplayTemplateExportRegister =
			portletDisplayTemplateExportRegister;
	}

	@Override
	public PortletPreferences process(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		exportDisplayStyle(
			portletDataContext, portletDataContext.getPortletId(),
			portletPreferences);

		return portletPreferences;
	}

	protected void exportDisplayStyle(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws PortletDataException {

		String displayStyle =
			_portletDisplayTemplateExportRegister.getDisplayStyle(
				portletDataContext, portletId, portletPreferences);

		if (Validator.isNull(displayStyle) ||
			!displayStyle.startsWith(
				PortletDisplayTemplate.DISPLAY_STYLE_PREFIX)) {

			return;
		}

		long displayStyleGroupId =
			_portletDisplayTemplateExportRegister.getDisplayStyleGroupId(
				portletDataContext, portletId, portletPreferences);

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		if ((displayStyleGroupId != 0) &&
			(displayStyleGroupId != portletDataContext.getScopeGroupId())) {

			portletDataContext.setScopeGroupId(displayStyleGroupId);
		}

		DDMTemplate ddmTemplate =
			_portletDisplayTemplate.getPortletDisplayTemplateDDMTemplate(
				portletDataContext.getGroupId(),
				getClassNameId(portletDataContext, portletId), displayStyle,
				false);

		if (ddmTemplate != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, portletId, ddmTemplate);
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);
	}

	protected long getClassNameId(
		PortletDataContext portletDataContext, String portletId) {

		Portlet portlet = _portletLocalService.getPortletById(
			portletDataContext.getCompanyId(), portletId);

		TemplateHandler templateHandler = portlet.getTemplateHandlerInstance();

		if (templateHandler == null) {
			return 0;
		}

		return _portal.getClassNameId(templateHandler.getClassName());
	}

	private final Portal _portal;
	private final PortletDisplayTemplate _portletDisplayTemplate;
	private final PortletDisplayTemplateRegister
		_portletDisplayTemplateExportRegister;
	private final PortletLocalService _portletLocalService;

}