/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.date.facet.portlet;

import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(
	property = "featureFlagKey=LPS-153839", service = FeatureFlagListener.class
)
public class DateFacetPortletFeatureFlagHandler implements FeatureFlagListener {

	@Override
	public void onValue(
		long companyId, String featureFlagKey, boolean enabled) {

		_enable(enabled);
	}

	@Activate
	protected void activate(ComponentContext componentContext) {
		_componentContext = componentContext;

		//_enable(FeatureFlagManagerUtil.isEnabled("LPS-153839"));
	}

	private void _enable(boolean enabled) {
		if (_componentContext == null) {
			return;
		}

		if (enabled) {
			_componentContext.enableComponent(DateFacetPortlet.class.getName());
		}
		else {
			_componentContext.disableComponent(
				DateFacetPortlet.class.getName());
		}
	}

	private ComponentContext _componentContext;

}