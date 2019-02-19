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

package com.liferay.segments.content.targeting.upgrade.internal;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.segments.content.targeting.upgrade.internal.v1_0_0.UpgradeContentTargeting;
import com.liferay.segments.content.targeting.upgrade.internal.v1_0_0.util.RuleConverterRegistry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	service = {
		SegmentsContentTargetingUpgrade.class, UpgradeStepRegistrator.class
	}
)
public class SegmentsContentTargetingUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "1.0.0",
			new UpgradeContentTargeting(
				_counterLocalService, _ruleConverterRegistry));
	}

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private RuleConverterRegistry _ruleConverterRegistry;

}