/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.instance.lifecycle;

import com.liferay.change.tracking.internal.helper.CTConflictCheckerDispatchTriggerHelper;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Company;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddCTConflictCheckerDispatchTriggerPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled("LPD-11018")) {
			return;
		}

		_ctConflictCheckerDispatchTriggerHelper.addDispatchTrigger(company);
	}

	@Reference
	private CTConflictCheckerDispatchTriggerHelper
		_ctConflictCheckerDispatchTriggerHelper;

}