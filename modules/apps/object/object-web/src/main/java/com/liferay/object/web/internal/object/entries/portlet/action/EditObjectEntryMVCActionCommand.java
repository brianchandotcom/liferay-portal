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

package com.liferay.object.web.internal.object.entries.portlet.action;

import com.liferay.object.exception.ObjectDefinitionScopeException;
import com.liferay.object.exception.ObjectEntryValuesException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.Serializable;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class EditObjectEntryMVCActionCommand extends BaseMVCActionCommand {

	public EditObjectEntryMVCActionCommand(
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryService objectEntryService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry,
		Portal portal) {

		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryService = objectEntryService;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
		_portal = portal;
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
			_addOrUpdateObjectEntry(actionRequest, actionResponse);
		}
	}

	private void _addOrUpdateObjectEntry(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		long objectDefinitionId = ParamUtil.getLong(
			actionRequest, "objectDefinitionId");

		long objectEntryId = ParamUtil.getLong(actionRequest, "objectEntryId");

		String ddmFormValues = ParamUtil.getString(
			actionRequest, "ddmFormValues");

		Map<String, Serializable> values =
			(Map)JSONFactoryUtil.looseDeserialize(ddmFormValues);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId);

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		try {
			if (objectEntryId == 0) {
				_objectEntryService.addObjectEntry(
					objectScopeProvider.getGroupId(httpServletRequest),
					objectDefinition.getObjectDefinitionId(), values,
					ServiceContextFactory.getInstance(
						objectDefinition.getClassName(), httpServletRequest));
			}
			else {
				_objectEntryService.updateObjectEntry(
					objectEntryId, values,
					ServiceContextFactory.getInstance(
						objectDefinition.getClassName(), httpServletRequest));
			}
		}
		catch (Exception exception) {
			if (exception instanceof ObjectDefinitionScopeException ||
				exception instanceof ObjectEntryValuesException) {

				SessionErrors.add(actionRequest, exception.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw new PortletException(exception);
			}
		}
	}

	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryService _objectEntryService;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;
	private final Portal _portal;

}