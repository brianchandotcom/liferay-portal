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

package com.liferay.fragment.internal.upgrade;

import com.liferay.fragment.internal.upgrade.v1_1_0.PortletPreferencesUpgradeProcess;
import com.liferay.fragment.internal.upgrade.v2_0_0.util.FragmentCollectionTable;
import com.liferay.fragment.internal.upgrade.v2_0_0.util.FragmentEntryLinkTable;
import com.liferay.fragment.internal.upgrade.v2_0_0.util.FragmentEntryTable;
import com.liferay.fragment.internal.upgrade.v2_1_0.SchemaUpgradeProcess;
import com.liferay.fragment.internal.upgrade.v2_4_0.FragmentEntryLinkUpgradeProcess;
import com.liferay.fragment.internal.upgrade.v2_6_0.util.FragmentEntryVersionTable;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.kernel.view.count.ViewCountManager;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.step.util.UpgradeStepFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Ángel Jiménez
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class FragmentServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.0.1",
			UpgradeStepFactory.alterColumnTypes(
				"FragmentEntry", "TEXT null", "css", "html", "js"),
			UpgradeStepFactory.alterColumnTypes(
				"FragmentEntryLink", "TEXT null", "css", "html", "js",
				"editableValues"));

		registry.register("1.0.1", "1.0.2", new DummyUpgradeStep());

		registry.register(
			"1.0.2", "1.1.0",
			new PortletPreferencesUpgradeProcess(_layoutLocalService));

		registry.register(
			"1.1.0", "2.0.0",
			new BaseSQLServerDatetimeUpgradeProcess(
				new Class<?>[] {
					FragmentCollectionTable.class, FragmentEntryLinkTable.class,
					FragmentEntryTable.class
				}));

		registry.register("2.0.0", "2.1.0", new SchemaUpgradeProcess());

		registry.register("2.1.0", "2.1.1", new DummyUpgradeStep());

		registry.register(
			"2.1.1", "2.1.2",
			new com.liferay.fragment.internal.upgrade.v2_1_2.
				SchemaUpgradeProcess());

		registry.register(
			"2.1.2", "2.1.3",
			new com.liferay.fragment.internal.upgrade.v2_1_3.
				SchemaUpgradeProcess());

		registry.register(
			"2.1.3", "2.2.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"FragmentCollection", "FragmentEntry",
						"FragmentEntryLink"
					};
				}

			});

		registry.register(
			"2.2.0", "2.2.1",
			new com.liferay.fragment.internal.upgrade.v2_2_1.
				SchemaUpgradeProcess());

		registry.register(
			"2.2.1", "2.3.0",
			new com.liferay.fragment.internal.upgrade.v2_3_0.
				FragmentEntryUpgradeProcess(),
			new com.liferay.fragment.internal.upgrade.v2_3_0.
				SchemaUpgradeProcess());

		registry.register(
			"2.3.0", "2.4.0", new FragmentEntryLinkUpgradeProcess());

		registry.register(
			"2.4.0", "2.5.0",
			new com.liferay.fragment.internal.upgrade.v2_5_0.
				FragmentEntryLinkUpgradeProcess());

		registry.register(
			"2.5.0", "2.6.0",
			new com.liferay.fragment.internal.upgrade.v2_6_0.
				FragmentEntryUpgradeProcess(),
			FragmentEntryVersionTable.create(),
			new com.liferay.fragment.internal.upgrade.v2_6_0.
				FragmentEntryVersionUpgradeProcess());

		registry.register(
			"2.6.0", "2.7.0",
			new CTModelUpgradeProcess(
				"FragmentCollection", "FragmentComposition", "FragmentEntry",
				"FragmentEntryLink", "FragmentEntryVersion"),
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {"FragmentEntryVersion"};
				}

			});

		registry.register("2.7.0", "2.7.1", new DummyUpgradeStep());

		registry.register(
			"2.7.1", "2.8.0",
			new com.liferay.fragment.internal.upgrade.v2_8_0.
				FragmentEntryUpgradeProcess(),
			new com.liferay.fragment.internal.upgrade.v2_8_0.
				FragmentEntryVersionUpgradeProcess());
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private ViewCountManager _viewCountManager;

}