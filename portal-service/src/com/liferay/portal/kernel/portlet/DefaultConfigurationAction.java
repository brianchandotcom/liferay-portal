/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

/**
 * @author Iván Zaera
 */
public class DefaultConfigurationAction
	extends BaseDefaultConfigurationAction<PortletPreferences>
	implements ConfigurationAction, ResourceServingConfigurationAction {

	public static final String PREFERENCES_PREFIX = "preferences--";

	public DefaultConfigurationAction() {
		setConfigurationParametersPrefix(PREFERENCES_PREFIX);
	}

	@Override
	protected PortletPreferences getConfiguration(ActionRequest actionRequest) {
		return actionRequest.getPreferences();
	}

	@Override
	@SuppressWarnings("unused")
	protected void postProcess(
			long companyId, PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws PortalException, SystemException {
	}

	@Override
	protected void reset(PortletPreferences portletPreferences, String key) {
		try {
			portletPreferences.reset(key);
		}
		catch (ReadOnlyException roe) {
			throw new RuntimeException(roe);
		}
	}

	@Override
	protected void setValue(
		PortletPreferences portletPreferences, String name, String value) {

		try {
			portletPreferences.setValue(name, value);
		}
		catch (ReadOnlyException roe) {
			throw new RuntimeException(roe);
		}
	}

	@Override
	protected void setValues(
		PortletPreferences portletPreferences, String name, String[] values) {

		try {
			portletPreferences.setValues(name, values);
		}
		catch (ReadOnlyException roe) {
			throw new RuntimeException(roe);
		}
	}

	@Override
	protected void store(PortletPreferences portletPreferences)
		throws IOException, ValidatorException {

		portletPreferences.store();
	}

}