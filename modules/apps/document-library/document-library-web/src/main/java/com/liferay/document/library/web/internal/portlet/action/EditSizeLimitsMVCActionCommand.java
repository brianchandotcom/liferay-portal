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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.document.library.configuration.DLSizeLimitConfigurationProvider;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SITE_SETTINGS,
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
		"mvc.command.name=/instance_settings/edit_size_limits"
	},
	service = MVCActionCommand.class
)
public class EditSizeLimitsMVCActionCommand extends BaseMVCActionCommand {

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
				"Invalid scope primary key 0 for " + scope + " scope");
		}

		try {
			_updateSizeLimit(actionRequest, scope, scopePK);
		}
		catch (ConfigurationModelListenerException
					configurationModelListenerException) {

			SessionErrors.add(
				actionRequest, configurationModelListenerException.getClass());

			actionResponse.sendRedirect(
				ParamUtil.getString(actionRequest, "redirect"));
		}
	}

	private Map<String, Long> _getMimeTypeSizeLimits(
		ActionRequest actionRequest) {

		Map<String, Long> mimeTypeSizeLimits = new LinkedHashMap<>();

		Map<String, String[]> parameterMap = actionRequest.getParameterMap();

		for (int i = 0; parameterMap.containsKey("mimeType_" + i); i++) {
			String mimeType = null;

			String[] mimeTypes = parameterMap.get("mimeType_" + i);

			if ((mimeTypes.length != 0) && Validator.isNotNull(mimeTypes[0])) {
				mimeType = mimeTypes[0];
			}

			Long size = null;

			String[] sizes = parameterMap.get("size_" + i);

			if ((sizes.length != 0) && Validator.isNotNull(sizes[0])) {
				size = GetterUtil.getLong(sizes[0]);
			}

			if ((mimeType != null) || (size != null)) {
				mimeTypeSizeLimits.put(mimeType, size);
			}
		}

		return mimeTypeSizeLimits;
	}

	private void _updateSizeLimit(
			ActionRequest actionRequest, String scope, long scopePK)
		throws Exception {

		long fileMaxSize = ParamUtil.getLong(actionRequest, "fileMaxSize");
		long maxSizeToCopy = ParamUtil.getLong(actionRequest, "maxSizeToCopy");

		if (scope.equals(
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			_dlSizeLimitConfigurationProvider.updateCompanySizeLimit(
				scopePK, fileMaxSize, maxSizeToCopy,
				_getMimeTypeSizeLimits(actionRequest));
		}
		else if (scope.equals(
					ExtendedObjectClassDefinition.Scope.GROUP.getValue())) {

			_dlSizeLimitConfigurationProvider.updateGroupSizeLimit(
				scopePK, fileMaxSize, maxSizeToCopy,
				_getMimeTypeSizeLimits(actionRequest));
		}
		else if (scope.equals(
					ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			_dlSizeLimitConfigurationProvider.updateSystemSizeLimit(
				fileMaxSize, maxSizeToCopy,
				_getMimeTypeSizeLimits(actionRequest));
		}
		else {
			throw new PortalException("Unsupported scope: " + scope);
		}
	}

	@Reference
	private DLSizeLimitConfigurationProvider _dlSizeLimitConfigurationProvider;

}