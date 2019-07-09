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

package com.liferay.oauth2.provider.web.internal.portlet.action;

import com.liferay.oauth2.provider.service.OAuth2ApplicationService;
import com.liferay.oauth2.provider.web.internal.constants.OAuth2ProviderPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 * @author Stian Sigvartsen
 */
@Component(
	property = {
		"javax.portlet.name=" + OAuth2ProviderPortletKeys.OAUTH2_ADMIN,
		"mvc.command.name=/admin/assign_scopes"
	},
	service = MVCActionCommand.class
)
public class AssignScopesMVCActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		long oAuth2ApplicationId = ParamUtil.getLong(
			actionRequest, "oAuth2ApplicationId");

		String[] scopeAliases = ParamUtil.getStringValues(
			actionRequest, "scopeAliases");

		Stream<String> scopeAliasesStream = Arrays.stream(scopeAliases);

		List<String> scopeAliasesList = scopeAliasesStream.flatMap(
			scopeAlias -> Arrays.stream(scopeAlias.split(StringPool.SPACE))
		).filter(
			Validator::isNotNull
		).collect(
			Collectors.toList()
		);

		try {
			_oAuth2ApplicationService.updateScopeAliases(
				oAuth2ApplicationId, scopeAliasesList);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			Class<?> peClass = pe.getClass();

			SessionErrors.add(actionRequest, peClass.getName(), pe);
		}

		String backURL = ParamUtil.get(
			actionRequest, "backURL", StringPool.BLANK);

		actionResponse.setRenderParameter("redirect", backURL);

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssignScopesMVCActionCommand.class);

	@Reference
	private OAuth2ApplicationService _oAuth2ApplicationService;

}