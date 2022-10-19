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

package com.liferay.segments.internal.upgrade.registry;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.segments.internal.upgrade.v2_0_0.SchemaUpgradeProcess;
import com.liferay.segments.internal.upgrade.v2_0_0.SegmentsExperienceUpgradeProcess;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = UpgradeStepRegistrator.class)
public class SegmentsServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("1.0.0", "1.0.1", new SchemaUpgradeProcess());

		registry.register(
			"1.0.1", "2.0.0",
			new SegmentsExperienceUpgradeProcess(_counterLocalService));

		registry.register(
			"2.0.0", "2.1.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"SegmentsEntry", "SegmentsEntryRel",
						"SegmentsExperience", "SegmentsExperiment",
						"SegmentsExperimentRel"
					};
				}

			});

		registry.register(
			"2.1.0", "2.2.0",
			new com.liferay.segments.internal.upgrade.v2_2_0.
				SchemaUpgradeProcess());

		registry.register(
			"2.2.0", "2.3.0",
			new CTModelUpgradeProcess(
				"SegmentsEntry", "SegmentsEntryRel", "SegmentsEntryRole",
				"SegmentsExperience", "SegmentsExperiment",
				"SegmentsExperimentRel"));

		registry.register(
			"2.3.0", "2.4.0",
			UpgradeProcessFactory.addColumns(
				"SegmentsExperience", "typeSettings VARCHAR(75) null"));

		registry.register("2.4.0", "2.5.0", new DummyUpgradeStep());

		registry.register(
			"2.5.0", "2.6.0",
			new com.liferay.segments.internal.upgrade.v2_6_0.
				SegmentsExperienceUpgradeProcess());
	}

	@Reference
	private CounterLocalService _counterLocalService;

}