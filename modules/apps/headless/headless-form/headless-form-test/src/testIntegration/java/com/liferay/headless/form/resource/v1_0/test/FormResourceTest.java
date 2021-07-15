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
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceTestUtil;
import com.liferay.headless.form.client.dto.v1_0.Form;
import com.liferay.headless.form.dto.v1_0.util.FormUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class FormResourceTest extends BaseFormResourceTestCase {

	@Override
	protected void assertValid(Form form) {
		boolean valid = true;

		if (Validator.isNull(form.getDateCreated()) ||
			Validator.isNull(form.getDateModified()) ||
			Validator.isNull(form.getId())) {

			valid = false;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected Form testGetForm_addForm() throws Exception {
		return _addForm(testGroup.getGroupId());
	}

	@Override
	protected Form testGetSiteFormsPage_addForm(Long siteId, Form form)
		throws Exception {

		return _addForm(siteId);
	}

	@Override
	protected Form testGraphQLForm_addForm() throws Exception {
		return _addForm(testGroup.getGroupId());
	}

	private Form _addForm(Long groupId) throws Exception {
		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				_groupLocalService.getGroup(groupId),
				TestPropsValues.getUserId());

		com.liferay.headless.form.dto.v1_0.Form form = FormUtil.toForm(
			true, ddmFormInstance, _portal,
			LocaleUtil.fromLanguageId(ddmFormInstance.getDefaultLanguageId()),
			_userLocalService);

		return Form.toDTO(form.toString());
	}

	@Inject(type = GroupLocalService.class)
	private GroupLocalService _groupLocalService;

	@Inject(type = Portal.class)
	private Portal _portal;

	@Inject(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}