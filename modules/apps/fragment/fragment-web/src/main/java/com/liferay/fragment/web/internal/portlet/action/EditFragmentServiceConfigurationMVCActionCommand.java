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

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.fragment.web.internal.configuration.admin.service.FragmentServiceManagedServiceFactory;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
		"mvc.command.name=/instance_settings/edit_fragment_service_configuration"
	},
	service = MVCActionCommand.class
)
public class EditFragmentServiceConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String scope = ParamUtil.getString(actionRequest, "scope");

		if (Validator.isNull(scope)) {
			throw new PortalException("Unsupported scope: " + scope);
		}

		long scopePK = ParamUtil.getLong(actionRequest, "scopePK");

		if ((scopePK == 0) &&
			!scope.equals(
				ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			throw new PortalException(
				"Invalid scope primary key 0 for scope " + scope);
		}

		try {
			boolean propagateChanges = ParamUtil.getBoolean(
				actionRequest, "propagateChanges");
			boolean propagateContributedFragmentChanges = ParamUtil.getBoolean(
				actionRequest, "propagateContributedFragmentChanges");

			_fragmentServiceManagedServiceFactory.updatePropagateChanges(
				propagateChanges, propagateContributedFragmentChanges, scope,
				scopePK);
		}
		catch (ConfigurationModelListenerException
					configurationModelListenerException) {

			SessionErrors.add(
				actionRequest, configurationModelListenerException.getClass(),
				configurationModelListenerException);

			actionResponse.sendRedirect(
				ParamUtil.getString(actionRequest, "redirect"));
		}
	}

	@Reference
	private FragmentServiceManagedServiceFactory
		_fragmentServiceManagedServiceFactory;

	@Reference
	private Portal _portal;

}