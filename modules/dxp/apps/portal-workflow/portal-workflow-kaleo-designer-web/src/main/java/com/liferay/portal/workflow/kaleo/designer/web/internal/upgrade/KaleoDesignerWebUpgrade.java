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

package com.liferay.portal.workflow.kaleo.designer.web.internal.upgrade;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
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
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class KaleoDesignerWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.0", "1.0.2", new DummyUpgradeStep());

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