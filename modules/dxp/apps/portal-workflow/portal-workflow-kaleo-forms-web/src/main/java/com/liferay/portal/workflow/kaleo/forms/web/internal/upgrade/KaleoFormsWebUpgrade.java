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

package com.liferay.portal.workflow.kaleo.forms.web.internal.upgrade;

import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.workflow.kaleo.forms.web.internal.upgrade.v1_0_2.UpgradePortletId;
import com.liferay.portal.workflow.kaleo.forms.web.internal.upgrade.v1_0_3.UpgradeLayoutTypeSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class KaleoFormsWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.portal.workflow.kaleo.forms.web", "0.0.0", "1.0.2",
			new DummyUpgradeStep());

		registry.register(
			"com.liferay.portal.workflow.kaleo.forms.web", "0.0.1", "1.0.2",
			new UpgradePortletId());

		registry.register(
			"com.liferay.portal.workflow.kaleo.forms.web", "1.0.2", "1.0.3",
			new UpgradeLayoutTypeSettings());
	}

}