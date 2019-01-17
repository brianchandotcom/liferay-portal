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

package com.liferay.mobile.device.rules.web.internal.upgrade;

import com.liferay.mobile.device.rules.web.internal.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class MDRWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.0", "1.0.2", new DummyUpgradeStep());

		registry.register("0.0.1", "1.0.0", new UpgradePortletId());

		registry.register(
			"1.0.0", "1.0.1",
			new MDRActionUpgrade(
				"com.liferay.portal.mobile.device.rulegroup.action.impl",
				"com.liferay.mobile.device.rules.rule.group.action"),
			new MDRRuleUpgrade(
				"com.liferay.portal.mobile.device.rulegroup.rule.impl." +
					"SimpleRuleHandler",
				"com.liferay.mobile.device.rules.rule.group.rule." +
					"SimpleRuleHandler"));

		registry.register(
			"1.0.1", "1.0.2",
			new MDRActionUpgrade(
				"com.liferay.mobile.device.rules.rule.group.action",
				"com.liferay.mobile.device.rules.web.internal.rule.group." +
					"action"),
			new MDRRuleUpgrade(
				"com.liferay.mobile.device.rules.rule.group.rule." +
					"SimpleRuleHandler",
				"com.liferay.mobile.device.rules.web.internal.rule.group." +
					"rule.SimpleRuleHandler"));
	}

	@Reference(
		target = "(release.bundle.symbolic.name=com.liferay.mobile.device.rules.service)",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

}