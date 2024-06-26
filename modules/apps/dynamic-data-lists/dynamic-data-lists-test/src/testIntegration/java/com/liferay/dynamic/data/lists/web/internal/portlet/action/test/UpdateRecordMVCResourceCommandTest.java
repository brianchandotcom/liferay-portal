/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.lists.helper.DDLRecordSetTestHelper;
import com.liferay.dynamic.data.lists.helper.DDLRecordTestHelper;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class UpdateRecordMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		DDLRecordSetTestHelper ddlRecordSetTestHelper =
			new DDLRecordSetTestHelper(_group);

		_ddlRecordSet = ddlRecordSetTestHelper.addRecordSet(
			DDMFormTestUtil.createDDMForm(RandomTestUtil.randomString()));

		DDLRecordTestHelper ddlRecordTestHelper = new DDLRecordTestHelper(
			_group, _ddlRecordSet);

		_ddlRecord = ddlRecordTestHelper.addRecord();
	}

	@Test
	public void testServeResource() throws Exception {
		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		DDMFormValues ddmFormValues =
			DDMFormValuesTestUtil.createDDMFormValuesWithRandomValues(
				_getUpdatedDDMForm());

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				_jsonDDMFormValuesSerializer.serialize(
					DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
						ddmFormValues
					).build());

		mockLiferayResourceRequest.addParameter(
			"ddmFormValues",
			ddmFormValuesSerializerSerializeResponse.getContent());

		mockLiferayResourceRequest.addParameter(
			"recordId", String.valueOf(_ddlRecord.getRecordId()));
		mockLiferayResourceRequest.addParameter(
			"recordSetId", String.valueOf(_ddlRecordSet.getRecordSetId()));
		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		_mvcResourceCommand.serveResource(
			mockLiferayResourceRequest, new MockLiferayResourceResponse());

		DDLRecord ddlRecord = _ddlRecordLocalService.getDDLRecord(
			_ddlRecord.getRecordId());

		Assert.assertEquals(ddmFormValues, ddlRecord.getDDMFormValues());
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.US);
		themeDisplay.setUser(
			_userLocalService.getUser(TestPropsValues.getUserId()));

		return themeDisplay;
	}

	private DDMForm _getUpdatedDDMForm() throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(DDLRecordSet.class), _group);

		DDMStructure ddmStructure = _ddlRecordSet.getDDMStructure();

		DDMForm ddmForm = ddmStructure.getDDMForm();

		DDMFormTestUtil.addTextDDMFormFields(
			ddmForm, RandomTestUtil.randomString());

		ddmStructure = ddmStructureTestHelper.updateStructure(
			_ddlRecordSet.getDDMStructureId(), ddmForm);

		return ddmStructure.getDDMForm();
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	private DDLRecord _ddlRecord;

	@Inject
	private DDLRecordLocalService _ddlRecordLocalService;

	private DDLRecordSet _ddlRecordSet;
	private Group _group;

	@Inject(filter = "ddm.form.values.serializer.type=json")
	private DDMFormValuesSerializer _jsonDDMFormValuesSerializer;

	@Inject(filter = "mvc.command.name=/dynamic_data_lists/update_record")
	private MVCResourceCommand _mvcResourceCommand;

	@Inject
	private UserLocalService _userLocalService;

}