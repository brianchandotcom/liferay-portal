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

package com.liferay.headless.form.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.headless.form.client.dto.v1_0.FormStructure;
import com.liferay.headless.form.dto.v1_0.util.StructureUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.Inject;

import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class FormStructureResourceTest
	extends BaseFormStructureResourceTestCase {

	@Override
	protected FormStructure testGetFormStructure_addFormStructure()
		throws Exception {

		return _addFormStructure(testGroup.getGroupId());
	}

	@Override
	protected FormStructure testGetSiteFormStructuresPage_addFormStructure(
			Long siteId, FormStructure formStructure)
		throws Exception {

		return _addFormStructure(siteId);
	}

	@Override
	protected FormStructure testGraphQLFormStructure_addFormStructure()
		throws Exception {

		return _addFormStructure(testGroup.getGroupId());
	}

	private FormStructure _addFormStructure(Long groupId) throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			groupId, DDMFormInstance.class.getName());

		com.liferay.headless.form.dto.v1_0.FormStructure formStructure =
			StructureUtil.toFormStructure(
				true, ddmStructure,
				LocaleUtil.fromLanguageId(ddmStructure.getDefaultLanguageId()),
				_portal, _userLocalService);

		return FormStructure.toDTO(formStructure.toString());
	}

	@Inject(type = Portal.class)
	private Portal _portal;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}