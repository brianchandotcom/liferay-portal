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

package com.liferay.social.user.statistics.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.BaseJSPSettingsConfigurationAction;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.social.user.statistics.constants.SocialUserStatisticsPortletKeys;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SocialUserStatisticsPortletKeys.SOCIAL_USER_STATISTICS,
	service = ConfigurationAction.class
)
public class SocialUserStatisticsConfigurationAction
	extends BaseJSPSettingsConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/configuration.jsp";
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.social.user.statistics.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected void updateMultiValuedKeys(ActionRequest actionRequest) {
		super.updateMultiValuedKeys(actionRequest);

		_setPreference(actionRequest, "displayActivityCounterName");
	}

	private void _setPreference(ActionRequest actionRequest, String key) {
		String[] displayActivityCounterNameIndexes = ParamUtil.getStringValues(
			actionRequest, "displayActivityCounterNameIndexes");

		List<String> values = new ArrayList<>(
			displayActivityCounterNameIndexes.length);

		for (String index : displayActivityCounterNameIndexes) {
			String value = ParamUtil.getString(actionRequest, key + index);

			if (Validator.isNotNull(value)) {
				values.add(value);
			}
		}

		setPreference(actionRequest, key, values.toArray(new String[0]));
	}

}