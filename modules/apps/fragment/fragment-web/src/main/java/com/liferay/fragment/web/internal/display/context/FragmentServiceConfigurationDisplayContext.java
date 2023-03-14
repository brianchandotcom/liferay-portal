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

package com.liferay.fragment.web.internal.display.context;

import com.liferay.fragment.web.internal.configuration.admin.service.FragmentServiceManagedServiceFactory;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class FragmentServiceConfigurationDisplayContext {

	public FragmentServiceConfigurationDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse,
		FragmentServiceManagedServiceFactory
			fragmentServiceManagedServiceFactory,
		String scope) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_fragmentServiceManagedServiceFactory =
			fragmentServiceManagedServiceFactory;
		_scope = scope;
	}

	public String getEditFragmentServiceConfigurationURL() {
		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			"/instance_settings/edit_fragment_service_configuration"
		).setRedirect(
			PortalUtil.getCurrentURL(_httpServletRequest)
		).setParameter(
			"scope", _scope
		).setParameter(
			"scopePK", _getScopePk()
		).buildString();
	}

	public boolean isPropagateChangesEnabled() {
		return _fragmentServiceManagedServiceFactory.isPropagateChanges(
			_scope, _getScopePk());
	}

	public boolean isPropagateContributedFragmentChangesEnabled() {
		return _fragmentServiceManagedServiceFactory.
			isPropagateContributedFragmentChanges(_scope, _getScopePk());
	}

	private long _getScopePk() {
		if (Objects.equals(
				_scope,
				ExtendedObjectClassDefinition.Scope.COMPANY.getValue())) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return themeDisplay.getCompanyId();
		}
		else if (Objects.equals(
					_scope,
					ExtendedObjectClassDefinition.Scope.SYSTEM.getValue())) {

			return 0L;
		}

		throw new IllegalArgumentException("Unsupported scope: " + _scope);
	}

	private final FragmentServiceManagedServiceFactory
		_fragmentServiceManagedServiceFactory;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final String _scope;

}