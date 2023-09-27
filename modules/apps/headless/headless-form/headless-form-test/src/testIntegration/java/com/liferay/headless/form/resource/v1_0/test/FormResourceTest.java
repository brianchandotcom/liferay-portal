/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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