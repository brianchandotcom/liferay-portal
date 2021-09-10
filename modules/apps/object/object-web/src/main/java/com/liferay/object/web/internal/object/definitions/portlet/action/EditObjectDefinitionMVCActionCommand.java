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

package com.liferay.object.web.internal.object.definitions.portlet.action;

import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.exception.DuplicateObjectDefinitionException;
import com.liferay.object.exception.ObjectDefinitionLabelException;
import com.liferay.object.exception.ObjectDefinitionNameException;
import com.liferay.object.exception.ObjectDefinitionPluralLabelException;
import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.exception.ObjectDefinitionStatusException;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"javax.portlet.name=" + ObjectPortletKeys.OBJECT_DEFINITIONS,
		"mvc.command.name=/object_definitions/edit_object_definition"
	},
	service = MVCActionCommand.class
)
public class EditObjectDefinitionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.PUBLISH)) {
			long objectDefinitionId = ParamUtil.getLong(
				actionRequest, "objectDefinitionId");

			_objectDefinitionService.publishCustomObjectDefinition(
				objectDefinitionId);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			_updateObjectDefinition(actionRequest, actionResponse);
		}
	}

	private void _updateObjectDefinition(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long objectDefinitionId = ParamUtil.getLong(
			actionRequest, "objectDefinitionId");

		boolean active = ParamUtil.getBoolean(actionRequest, "active");
		Map<Locale, String> labelMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "label");
		String name = ParamUtil.getString(actionRequest, "shortName");
		String panelCategoryOrder = ParamUtil.getString(
			actionRequest, "panelCategoryOrder");
		String panelCategoryKey = ParamUtil.getString(
			actionRequest, "panelCategoryKey");
		Map<Locale, String> pluralLabelMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "pluralLabel");
		String scope = ParamUtil.getString(actionRequest, "scope");

		try {
			_objectDefinitionService.updateCustomObjectDefinition(
				objectDefinitionId, active, labelMap, name, panelCategoryOrder,
				panelCategoryKey, pluralLabelMap, scope);
		}
		catch (Exception exception) {
			if (exception instanceof DuplicateObjectDefinitionException ||
				exception instanceof ObjectDefinitionLabelException ||
				exception instanceof ObjectDefinitionNameException ||
				exception instanceof ObjectDefinitionPluralLabelException ||
				exception instanceof ObjectDefinitionScopeException ||
				exception instanceof ObjectDefinitionStatusException) {

				SessionErrors.add(actionRequest, exception.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw exception;
			}
		}
	}

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

}