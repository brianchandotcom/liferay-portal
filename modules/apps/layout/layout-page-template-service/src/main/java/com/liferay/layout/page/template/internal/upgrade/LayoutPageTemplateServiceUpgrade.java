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

package com.liferay.layout.page.template.internal.upgrade;

import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.page.template.internal.upgrade.v1_1_0.UpgradeLayoutPrototype;
import com.liferay.layout.page.template.internal.upgrade.v1_1_1.UpgradeLayoutPageTemplateEntry;
import com.liferay.layout.page.template.internal.upgrade.v1_2_0.UpgradeLayoutPageTemplateStructure;
import com.liferay.layout.page.template.internal.upgrade.v2_0_0.util.LayoutPageTemplateCollectionTable;
import com.liferay.layout.page.template.internal.upgrade.v2_0_0.util.LayoutPageTemplateEntryTable;
import com.liferay.layout.page.template.internal.upgrade.v2_1_0.UpgradeLayout;
import com.liferay.layout.page.template.internal.upgrade.v3_0_1.util.LayoutPageTemplateStructureRelTable;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.upgrade.BaseUpgradeSQLServerDatetime;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.step.util.UpgradeStepFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class LayoutPageTemplateServiceUpgrade
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.1", "1.0.0", new DummyUpgradeStep());

		registry.register(
			"1.0.0", "1.1.0",
			new UpgradeLayoutPrototype(
				_companyLocalService, _layoutPrototypeLocalService));

		registry.register(
			"1.1.0", "1.1.1",
			new UpgradeLayoutPageTemplateEntry(_companyLocalService));

		registry.register(
			"1.1.1", "1.2.0",
			new UpgradeLayoutPageTemplateStructure(
				_fragmentEntryLinkLocalService, _layoutLocalService));

		registry.register(
			"1.2.0", "2.0.0",
			new BaseUpgradeSQLServerDatetime(
				new Class<?>[] {
					LayoutPageTemplateCollectionTable.class,
					LayoutPageTemplateEntryTable.class
				}));

		registry.register(
			"2.0.0", "2.1.0",
			new UpgradeLayout(
				_fragmentEntryLinkLocalService, _layoutLocalService,
				_layoutPrototypeLocalService));

		registry.register(
			"2.1.0", "3.0.0",
			new com.liferay.layout.page.template.internal.upgrade.v3_0_0.
				UpgradeLayoutPageTemplateStructure());

		registry.register(
			"3.0.0", "3.0.1",
			UpgradeStepFactory.alterColumnTypes(
				LayoutPageTemplateStructureRelTable.class, "TEXT null",
				"data_"));

		registry.register(
			"3.0.1", "3.1.0",
			new UpgradeMVCCVersion() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"LayoutPageTemplateCollection",
						"LayoutPageTemplateEntry",
						"LayoutPageTemplateStructure",
						"LayoutPageTemplateStructureRel"
					};
				}

			});
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

}