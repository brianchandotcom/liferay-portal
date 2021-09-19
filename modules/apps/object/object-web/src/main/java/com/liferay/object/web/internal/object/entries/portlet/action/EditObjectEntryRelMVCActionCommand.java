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
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

/**
 * @author Marco Leo
 */
public class EditObjectEntryRelMVCActionCommand extends BaseMVCActionCommand {

	public EditObjectEntryRelMVCActionCommand(
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		Portal portal) {

		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_portal = portal;
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ASSIGN)) {
			_addOrUpdateObjectEntryRel(actionRequest, actionResponse);
		}
	}

	private void _addOrUpdateObjectEntryRel(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			long objectEntryId = ParamUtil.getLong(
				actionRequest, "objectEntryId");

			long objectEntryRelId = ParamUtil.getLong(
				actionRequest, "objectEntryRelId");

			long objectRelationshipId = ParamUtil.getLong(
				actionRequest, "objectRelationshipId");

			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.getObjectRelationship(
					objectRelationshipId);

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectRelationship.getObjectDefinitionId2());

			_objectRelationshipLocalService.addObjectRelationshipMap(
				_portal.getUserId(actionRequest), objectRelationshipId,
				objectEntryId, objectEntryRelId,
				ServiceContextFactory.getInstance(
					objectDefinition.getClassName(), actionRequest));
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
				throw exception;
			}
		}
	}

	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final Portal _portal;

}