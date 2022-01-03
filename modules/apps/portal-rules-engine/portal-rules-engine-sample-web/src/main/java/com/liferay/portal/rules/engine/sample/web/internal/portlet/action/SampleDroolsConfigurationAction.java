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

package com.liferay.portal.rules.engine.sample.web.internal.portlet.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.resource.StringResourceRetriever;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.rules.engine.RulesEngine;
import com.liferay.portal.rules.engine.RulesEngineException;
import com.liferay.portal.rules.engine.RulesLanguage;
import com.liferay.portal.rules.engine.RulesResourceRetriever;
import com.liferay.portal.rules.engine.sample.web.internal.constants.SampleDroolsPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + SampleDroolsPortletKeys.SAMPLE_DROOLS,
	service = ConfigurationAction.class
)
public class SampleDroolsConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/configuration.jsp";
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (!cmd.equals(Constants.UPDATE)) {
			return;
		}

		PortletPreferences preferences = actionRequest.getPreferences();

		_updatePreferences(actionRequest, preferences);

		if (SessionErrors.isEmpty(actionRequest)) {
			preferences.store();

			SessionMessages.add(
				actionRequest,
				_portal.getPortletId(actionRequest) +
					SessionMessages.KEY_SUFFIX_UPDATED_CONFIGURATION);
		}
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.portal.rules.engine.sample.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	private void _updatePreferences(
			ActionRequest actionRequest, PortletPreferences preferences)
		throws Exception {

		String domainName = ParamUtil.getString(actionRequest, "domainName");
		String rules = ParamUtil.getString(actionRequest, "rules");
		long[] classNameIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "classNameIds"), 0L);

		if (Validator.isNull(domainName)) {
			SessionErrors.add(actionRequest, "domainName");
		}
		else if (Validator.isNull(rules)) {
			SessionErrors.add(actionRequest, "rules");
		}
		else if (classNameIds.length == 0) {
			SessionErrors.add(actionRequest, "classNameIds");
		}
		else {
			RulesResourceRetriever rulesResourceRetriever =
				new RulesResourceRetriever(
					new StringResourceRetriever(rules),
					String.valueOf(RulesLanguage.DROOLS_RULE_LANGUAGE));

			try {
				_rulesEngine.update(domainName, rulesResourceRetriever);
			}
			catch (RulesEngineException rulesEngineException) {
				_log.error(rulesEngineException, rulesEngineException);

				SessionErrors.add(actionRequest, "rulesEngineException");
			}
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			preferences.setValue("rules", rules);
			preferences.setValue("domain-name", domainName);

			String userCustomAttributeNames = ParamUtil.getString(
				actionRequest, "userCustomAttributeNames");

			preferences.setValue(
				"user-custom-attribute-names", userCustomAttributeNames);

			preferences.setValues(
				"class-name-ids", ArrayUtil.toStringArray(classNameIds));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SampleDroolsConfigurationAction.class);

	@Reference
	private Portal _portal;

	@Reference
	private RulesEngine _rulesEngine;

}