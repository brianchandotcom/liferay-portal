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

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
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
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = {})
public class ObjectDefinitionSampleGenerator {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		if (false) {
			_addSampleObjectDefinition();
		}
	}

	private void _addSampleObjectDefinition() throws Exception {
		List<Company> companies = _companyLocalService.getCompanies();

		if (companies.size() != 1) {
			return;
		}

		Company company = companies.get(0);

		int count = _objectDefinitionLocalService.getObjectDefinitionsCount(
			company.getCompanyId());

		if (count > 0) {
			return;
		}

		User user = _userLocalService.fetchUserByEmailAddress(
			company.getCompanyId(), "test@liferay.com");

		if (user == null) {
			return;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.addObjectDefinition(
				user.getUserId(), "SampleObjectDefinition",
				Arrays.asList(
					_createObjectField("able", "Long"),
					_createObjectField("baker", "Boolean"),
					_createObjectField("dog", "Date"),
					_createObjectField("easy", "String"),
					_createObjectField(true, true, null, "fox", "String"),
					_createObjectField(
						true, false, "en_US", "george", "String"),
					_createObjectField(false, false, null, "how", "String"),
					_createObjectField("item", "Double"),
					_createObjectField("jig", "Integer"),
					_createObjectField("king", "BigDecimal")));

		for (int i = 0; i < 100; i++) {
			_objectEntryLocalService.addObjectEntry(
				user.getUserId(), 0, objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"able", 10 + i
				).put(
					"baker", (i % 2) == 0
				).put(
					"dog", new Date()
				).put(
					"easy",
					"The quick brown fox jumps over the lazy dog. " + i + "!"
				).put(
					"fox", "test" + i
				).put(
					"george",
					"The english brown fox trusted the lazy dog. " + i + "!"
				).put(
					"how",
					"The unsearchable brown fox jumps over the lazy dog. " + i
				).put(
					"item", 180.5D + i
				).put(
					"jig", 5 + i
				).put(
					"king", BigDecimal.valueOf(45L + i)
				).build(),
				new ServiceContext());
		}
	}

	private ObjectField _createObjectField(
		boolean indexed, boolean indexedAsKeyword, String indexedLanguageId,
		String name, String type) {

		ObjectField objectField = _objectFieldLocalService.createObjectField(0);

		objectField.setIndexed(indexed);
		objectField.setIndexedAsKeyword(indexedAsKeyword);
		objectField.setIndexedLanguageId(indexedLanguageId);
		objectField.setName(name);
		objectField.setType(type);

		return objectField;
	}

	private ObjectField _createObjectField(String name, String type) {
		return _createObjectField(true, false, null, name, type);
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
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private UserLocalService _userLocalService;

}