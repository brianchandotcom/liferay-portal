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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

/**
 * @author Iván Zaera
 */
public class UpgradeShoppingPreferences extends BaseUpgradePortletPreferences {

	@Override
	protected String[] getPortletIds() {
		return new String[] {PortletKeys.SHOPPING};
	}

	protected void upgradeCcTypes(PortletPreferences portletPreferences)
		throws ReadOnlyException {

		String ccTypes = portletPreferences.getValue(
			"ccTypes", StringPool.BLANK);

		if (Validator.isNotNull(ccTypes)) {
			portletPreferences.setValues("ccTypes", StringUtil.split(ccTypes));
		}
	}

	protected void upgradeInsurance(PortletPreferences portletPreferences)
		throws ReadOnlyException {

		String insurance = portletPreferences.getValue(
			"insurance", StringPool.BLANK);

		if (Validator.isNotNull(insurance)) {
			portletPreferences.setValues(
				"insurance", StringUtil.split(insurance));
		}
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		upgradeCcTypes(portletPreferences);
		upgradeInsurance(portletPreferences);
		upgradeShipping(portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected void upgradeShipping(PortletPreferences portletPreferences)
		throws ReadOnlyException {

		String shipping = portletPreferences.getValue(
			"shipping", StringPool.BLANK);

		if (Validator.isNotNull(shipping)) {
			portletPreferences.setValues(
				"shipping", StringUtil.split(shipping));
		}
	}

}