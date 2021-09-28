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

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.object.action.executor.ObjectActionExecutorRegistry;
import com.liferay.object.action.trigger.ObjectActionTriggerRegistry;
import com.liferay.object.constants.ObjectPortletKeys;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectActionService;
import com.liferay.object.service.ObjectDefinitionService;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.object.definitions.display.context.ObjectDefinitionsActionsDisplayContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"javax.portlet.name=" + ObjectPortletKeys.OBJECT_DEFINITIONS,
		"mvc.command.name=/object_definitions/edit_object_action"
	},
	service = MVCRenderCommand.class
)
public class EditObjectActionMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			ObjectAction objectAction = _objectActionService.getObjectAction(
				ParamUtil.getLong(renderRequest, "objectActionId"));

			renderRequest.setAttribute(
				ObjectWebKeys.OBJECT_ACTION, objectAction);
			renderRequest.setAttribute(
				ObjectWebKeys.OBJECT_DEFINITION,
				_objectDefinitionService.getObjectDefinition(
					objectAction.getObjectDefinitionId()));

			renderRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				new ObjectDefinitionsActionsDisplayContext(
					_ddmFormRenderer,
					_portal.getHttpServletRequest(renderRequest),
					_objectActionExecutorRegistry, _objectActionTriggerRegistry,
					_objectDefinitionModelResourcePermission, _jsonFactory));
		}
		catch (PortalException portalException) {
			SessionErrors.add(renderRequest, portalException.getClass());
		}

		return "/object_definitions/edit_object_action.jsp";
	}

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectActionExecutorRegistry _objectActionExecutorRegistry;

	@Reference
	private ObjectActionService _objectActionService;

	@Reference
	private ObjectActionTriggerRegistry _objectActionTriggerRegistry;

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectDefinition)"
	)
	private ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;

	@Reference
	private ObjectDefinitionService _objectDefinitionService;

	@Reference
	private Portal _portal;

}