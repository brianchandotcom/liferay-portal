/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.designer.web.internal.upgrade.registry;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.kaleo.designer.web.internal.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.portal.workflow.kaleo.designer.web.internal.upgrade.v1_0_1.KaleoDefinitionVersionUpgradeProcess;
import com.liferay.portal.workflow.kaleo.designer.web.internal.upgrade.v1_0_2.KaleoDefinitionUpgradeProcess;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(service = UpgradeStepRegistrator.class)
public class KaleoDesignerWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register("0.0.1", "1.0.0", new UpgradePortletId());

		registry.register(
			"1.0.0", "1.0.1",
			new KaleoDefinitionVersionUpgradeProcess(
				_counterLocalService, _kaleoDefinitionLocalService,
				_kaleoDefinitionVersionLocalService, _userLocalService));

		registry.register(
			"1.0.1", "1.0.2",
			new KaleoDefinitionUpgradeProcess(
				_counterLocalService, _kaleoDefinitionLocalService,
				_userLocalService));
	}

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Reference
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.portal.workflow.kaleo.service)(release.schema.version>=1.4.1))"
	)
	private Release _release;

	@Reference
	private UserLocalService _userLocalService;

}