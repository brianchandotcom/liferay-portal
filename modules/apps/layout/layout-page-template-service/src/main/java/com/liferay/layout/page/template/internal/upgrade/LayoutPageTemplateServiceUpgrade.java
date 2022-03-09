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
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.layout.helper.CollectionPaginationHelper;
import com.liferay.layout.page.template.internal.upgrade.v1_1_0.LayoutPrototypeUpgradeProcess;
import com.liferay.layout.page.template.internal.upgrade.v1_1_1.LayoutPageTemplateEntryUpgradeProcess;
import com.liferay.layout.page.template.internal.upgrade.v1_2_0.LayoutPageTemplateStructureUpgradeProcess;
import com.liferay.layout.page.template.internal.upgrade.v2_0_0.util.LayoutPageTemplateCollectionTable;
import com.liferay.layout.page.template.internal.upgrade.v2_0_0.util.LayoutPageTemplateEntryTable;
import com.liferay.layout.page.template.internal.upgrade.v2_1_0.LayoutUpgradeProcess;
import com.liferay.layout.page.template.internal.upgrade.v3_1_3.ResourcePermissionUpgradeProcess;
import com.liferay.layout.page.template.internal.upgrade.v3_3_0.LayoutPageTemplateStructureRelUpgradeProcess;
import com.liferay.layout.page.template.internal.upgrade.v3_4_1.FragmentEntryLinkEditableValuesUpgradeProcess;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutPrototypeLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.kernel.util.Portal;
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
			new LayoutPrototypeUpgradeProcess(
				_companyLocalService, _layoutPrototypeLocalService));

		registry.register(
			"1.1.0", "1.1.1",
			new LayoutPageTemplateEntryUpgradeProcess(_companyLocalService));

		registry.register(
			"1.1.1", "1.2.0",
			new LayoutPageTemplateStructureUpgradeProcess(
				_fragmentEntryLinkLocalService, _layoutLocalService));

		registry.register(
			"1.2.0", "2.0.0",
			new BaseSQLServerDatetimeUpgradeProcess(
				new Class<?>[] {
					LayoutPageTemplateCollectionTable.class,
					LayoutPageTemplateEntryTable.class
				}));

		registry.register(
			"2.0.0", "2.1.0",
			new LayoutUpgradeProcess(
				_fragmentEntryLinkLocalService, _layoutLocalService,
				_layoutPrototypeLocalService));

		registry.register(
			"2.1.0", "3.0.0",
			new com.liferay.layout.page.template.internal.upgrade.v3_0_0.
				LayoutPageTemplateStructureUpgradeProcess());

		registry.register(
			"3.0.0", "3.0.1",
			UpgradeStepFactory.alterColumnTypes(
				"LayoutPageTemplateStructureRel", "TEXT null", "data_"));

		registry.register(
			"3.0.1", "3.1.0",
			new MVCCVersionUpgradeProcess() {

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

		registry.register("3.1.0", "3.1.1", new DummyUpgradeStep());

		registry.register("3.1.1", "3.1.2", new DummyUpgradeStep());

		registry.register(
			"3.1.2", "3.1.3", new ResourcePermissionUpgradeProcess());

		registry.register(
			"3.1.3", "3.2.0",
			new com.liferay.layout.page.template.internal.upgrade.v3_2_0.
				LayoutPageTemplateCollectionUpgradeProcess(),
			new com.liferay.layout.page.template.internal.upgrade.v3_2_0.
				LayoutPageTemplateEntryUpgradeProcess());

		registry.register(
			"3.2.0", "3.3.0",
			new LayoutPageTemplateStructureRelUpgradeProcess(
				_fragmentEntryLinkLocalService,
				_portletPreferencesLocalService));

		registry.register(
			"3.3.0", "3.3.1",
			new com.liferay.layout.page.template.internal.upgrade.v3_3_1.
				LayoutPageTemplateEntryUpgradeProcess(
					_layoutPrototypeLocalService));

		registry.register(
			"3.3.1", "3.4.0",
			new CTModelUpgradeProcess(
				"LayoutPageTemplateCollection", "LayoutPageTemplateEntry",
				"LayoutPageTemplateStructure",
				"LayoutPageTemplateStructureRel"));

		registry.register(
			"3.4.0", "3.4.1",
			new com.liferay.layout.page.template.internal.upgrade.v3_4_1.
				LayoutPageTemplateEntryUpgradeProcess(_portal),
			new FragmentEntryLinkEditableValuesUpgradeProcess());

		registry.register(
			"3.4.1", "3.4.2",
			new com.liferay.layout.page.template.internal.upgrade.v3_4_2.
				FragmentEntryLinkEditableValuesUpgradeProcess(),
			new com.liferay.layout.page.template.internal.upgrade.v3_4_2.
				LayoutPageTemplateStructureRelUpgradeProcess(
					_fragmentEntryConfigurationParser));

		registry.register(
			"3.4.2", "3.4.3",
			new com.liferay.layout.page.template.internal.upgrade.v3_4_3.
				ResourcePermissionUpgradeProcess(
					_resourcePermissionLocalService));

		registry.register(
			"3.4.3", "3.5.0",
			new com.liferay.layout.page.template.internal.upgrade.v3_5_0.
				LayoutPageTemplateStructureRelUpgradeProcess());

		registry.register(
			"3.5.0", "4.0.0",
			new com.liferay.layout.page.template.internal.upgrade.v4_0_0.
				LayoutPageTemplateStructureRelUpgradeProcess(
					_collectionPaginationHelper));
	}

	@Reference
	private CollectionPaginationHelper _collectionPaginationHelper;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}