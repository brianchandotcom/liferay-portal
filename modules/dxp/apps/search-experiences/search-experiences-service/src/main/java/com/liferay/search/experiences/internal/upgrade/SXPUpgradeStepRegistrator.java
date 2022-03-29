/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.upgrade;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.search.experiences.internal.model.listener.CompanyModelListener;
import com.liferay.search.experiences.internal.search.SXPElementSearchRegistrar;
import com.liferay.search.experiences.internal.upgrade.v1_0_0.SXPElementUpgradeProcess;
import com.liferay.search.experiences.service.SXPElementLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(
	enabled = true, immediate = true, service = UpgradeStepRegistrator.class
)
public class SXPUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialUpgradeSteps(
			new SXPElementUpgradeProcess(
				_companyLocalService, _companyModelListener,
				_sxpElementLocalService));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CompanyModelListener _companyModelListener;

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

	@Reference
	private SXPElementSearchRegistrar _sxpElementSearchRegistrar;

}