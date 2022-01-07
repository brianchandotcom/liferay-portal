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

package com.liferay.object.internal;

import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.object.util.ObjectFieldUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Enable this component by going to Gogo Shell and executing this command:
 *
 * scr:enable com.liferay.object.internal.ObjectDefinitionSampleGenerator
 *
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 * @review
 */
@Component(enabled = false, immediate = true, service = {})
public class ObjectDefinitionSampleGenerator {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		_addSampleObjectDefinition();
	}

	private void _addSampleObjectDefinition() throws Exception {
		List<Company> companies = _companyLocalService.getCompanies();

		if (companies.size() != 1) {
			return;
		}

		Company company = companies.get(0);

		User user = _userLocalService.fetchUserByEmailAddress(
			company.getCompanyId(), "test@liferay.com");

		if (user == null) {
			return;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				company.getCompanyId(), "C_SampleObjectDefinition");

		if (objectDefinition != null) {
			return;
		}

		objectDefinition =
			_objectDefinitionLocalService.addCustomObjectDefinition(
				user.getUserId(),
				LocalizedMapUtil.getLocalizedMap("Sample Object Definition"),
				"SampleObjectDefinition", "100",
				PanelCategoryKeys.CONTROL_PANEL_SITES,
				LocalizedMapUtil.getLocalizedMap("Sample Object Definitions"),
				ObjectDefinitionConstants.SCOPE_COMPANY,
				Arrays.asList(
					ObjectFieldUtil.createObjectField(
						"LongInteger", "Long", true, false, null, "Able",
						"able", false),
					ObjectFieldUtil.createObjectField(
						"Boolean", "Boolean", true, false, null, "Baker",
						"baker", false),
					ObjectFieldUtil.createObjectField(
						"Date", "Date", true, false, null, "Charlie", "charlie",
						false),
					ObjectFieldUtil.createObjectField(
						"Text", "String", true, false, null, "Dog", "dog",
						false),
					ObjectFieldUtil.createObjectField(
						0, "Text", null, "String", true, true, null, "Easy",
						"easy", false),
					ObjectFieldUtil.createObjectField(
						0, "Text", null, "String", true, false, "en_US", "Fox",
						"fox", false),
					ObjectFieldUtil.createObjectField(
						0, "Text", null, "String", false, false, null, "George",
						"george", false),
					ObjectFieldUtil.createObjectField(
						"Decimal", "Double", true, false, null, "How", "how",
						false),
					ObjectFieldUtil.createObjectField(
						"Integer", "Integer", true, false, null, "Item", "item",
						false),
					ObjectFieldUtil.createObjectField(
						"PrecisionDecimal", "BigDecimal", true, false, null,
						"Jig", "jig", false)));

		objectDefinition =
			_objectDefinitionLocalService.publishCustomObjectDefinition(
				user.getUserId(), objectDefinition.getObjectDefinitionId());

		for (int i = 0; i < 100; i++) {
			_objectEntryLocalService.addObjectEntry(
				user.getUserId(), 0, objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"able", 10 + i
				).put(
					"baker", (i % 2) == 0
				).put(
					"charlie", new Date()
				).put(
					"dog",
					"The quick brown fox jumps over the lazy dog. " + i + "!"
				).put(
					"easy", "test" + i
				).put(
					"fox",
					"The english brown fox trusted the lazy dog. " + i + "!"
				).put(
					"george",
					"The unsearchable brown fox jumps over the lazy dog. " + i
				).put(
					"how", 180.5D + i
				).put(
					"item", 5 + i
				).put(
					"jig", BigDecimal.valueOf(45L + i)
				).build(),
				new ServiceContext());
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}