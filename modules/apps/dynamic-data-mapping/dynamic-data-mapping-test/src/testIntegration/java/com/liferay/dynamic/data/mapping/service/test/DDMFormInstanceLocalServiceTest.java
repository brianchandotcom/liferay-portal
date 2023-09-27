/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class DDMFormInstanceLocalServiceTest extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Class<?> formInstanceClass = DDMFormInstance.class;

		_classNameId = PortalUtil.getClassNameId(formInstanceClass.getName());
	}

	@Test
	public void testAddFormInstanceShouldCreateApprovedFormInstanceVersion()
		throws Exception {

		DDMStructure structure = addStructure(_classNameId, "Test Structure");

		DDMForm settingsDDMForm = DDMFormTestUtil.createDDMForm();

		DDMFormValues settingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(settingsDDMForm);

		DDMFormInstance formInstance =
			DDMFormInstanceLocalServiceUtil.addFormInstance(
				structure.getUserId(), structure.getGroupId(),
				structure.getStructureId(), structure.getNameMap(),
				structure.getNameMap(), settingsDDMFormValues,
				ServiceContextTestUtil.getServiceContext(
					group, TestPropsValues.getUserId()));

		DDMFormInstanceVersion latestFormInstanceVersion =
			formInstance.getFormInstanceVersion(formInstance.getVersion());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			latestFormInstanceVersion.getStatus());
	}

	@Test
	public void testAddFormInstanceShouldCreateDraftFormInstanceVersion()
		throws Exception {

		DDMStructure structure = addStructure(_classNameId, "Test Structure");

		DDMForm settingsDDMForm = DDMFormTestUtil.createDDMForm();

		DDMFormValues settingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(settingsDDMForm);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		serviceContext.setAttribute("status", WorkflowConstants.STATUS_DRAFT);

		DDMFormInstance formInstance =
			DDMFormInstanceLocalServiceUtil.addFormInstance(
				structure.getUserId(), structure.getGroupId(),
				structure.getStructureId(), structure.getNameMap(),
				structure.getNameMap(), settingsDDMFormValues, serviceContext);

		DDMFormInstanceVersion latestFormInstanceVersion =
			formInstance.getFormInstanceVersion(formInstance.getVersion());

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			latestFormInstanceVersion.getStatus());
	}

	@Test
	public void testUpdateFormInstanceShouldCreateNewFormInstanceVersion1()
		throws Exception {

		DDMStructure structure = addStructure(_classNameId, "Test Structure");

		DDMForm settingsDDMForm = DDMFormTestUtil.createDDMForm();

		DDMFormValues settingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(settingsDDMForm);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		serviceContext.setAttribute("status", WorkflowConstants.STATUS_DRAFT);

		DDMFormInstance formInstance =
			DDMFormInstanceLocalServiceUtil.addFormInstance(
				structure.getUserId(), structure.getGroupId(),
				structure.getStructureId(), structure.getNameMap(),
				structure.getNameMap(), settingsDDMFormValues, serviceContext);

		DDMFormInstanceVersion firstFormInstanceVersion =
			formInstance.getFormInstanceVersion(formInstance.getVersion());

		serviceContext.setAttribute(
			"status", WorkflowConstants.STATUS_APPROVED);

		formInstance = DDMFormInstanceLocalServiceUtil.updateFormInstance(
			formInstance.getFormInstanceId(), formInstance.getStructureId(),
			formInstance.getNameMap(), formInstance.getDescriptionMap(),
			settingsDDMFormValues, serviceContext);

		DDMFormInstanceVersion secondFormInstanceVersion =
			formInstance.getFormInstanceVersion(formInstance.getVersion());

		Assert.assertNotEquals(
			firstFormInstanceVersion, secondFormInstanceVersion);
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			secondFormInstanceVersion.getStatus());
	}

	@Test
	public void testUpdateFormInstanceShouldCreateNewFormInstanceVersion2()
		throws Exception {

		DDMStructure structure = addStructure(_classNameId, "Test Structure");

		DDMForm settingsDDMForm = DDMFormTestUtil.createDDMForm();

		DDMFormValues settingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(settingsDDMForm);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		DDMFormInstance formInstance =
			DDMFormInstanceLocalServiceUtil.addFormInstance(
				structure.getUserId(), structure.getGroupId(),
				structure.getStructureId(), structure.getNameMap(),
				structure.getNameMap(), settingsDDMFormValues, serviceContext);

		DDMFormInstanceVersion firstFormInstanceVersion =
			formInstance.getFormInstanceVersion(formInstance.getVersion());

		formInstance = DDMFormInstanceLocalServiceUtil.updateFormInstance(
			formInstance.getFormInstanceId(), formInstance.getStructureId(),
			formInstance.getNameMap(), formInstance.getDescriptionMap(),
			settingsDDMFormValues, serviceContext);

		DDMFormInstanceVersion secondFormInstanceVersion =
			formInstance.getFormInstanceVersion(formInstance.getVersion());

		Assert.assertNotEquals(
			firstFormInstanceVersion, secondFormInstanceVersion);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			firstFormInstanceVersion.getStatus());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			secondFormInstanceVersion.getStatus());
	}

	@Test
	public void testUpdateFormInstanceShouldKeepFormInstanceVersion()
		throws Exception {

		DDMStructure structure = addStructure(_classNameId, "Test Structure");

		DDMForm settingsDDMForm = DDMFormTestUtil.createDDMForm();

		DDMFormValues settingsDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(settingsDDMForm);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		serviceContext.setAttribute("status", WorkflowConstants.STATUS_DRAFT);

		DDMFormInstance formInstance =
			DDMFormInstanceLocalServiceUtil.addFormInstance(
				structure.getUserId(), structure.getGroupId(),
				structure.getStructureId(), structure.getNameMap(),
				structure.getNameMap(), settingsDDMFormValues, serviceContext);

		DDMFormInstanceVersion firstFormInstanceVersion =
			formInstance.getFormInstanceVersion(formInstance.getVersion());

		formInstance = DDMFormInstanceLocalServiceUtil.updateFormInstance(
			formInstance.getFormInstanceId(), formInstance.getStructureId(),
			formInstance.getNameMap(), formInstance.getDescriptionMap(),
			settingsDDMFormValues, serviceContext);

		DDMFormInstanceVersion secondFormInstanceVersion =
			formInstance.getFormInstanceVersion(formInstance.getVersion());

		Assert.assertEquals(
			firstFormInstanceVersion, secondFormInstanceVersion);

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			firstFormInstanceVersion.getStatus());

		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			secondFormInstanceVersion.getStatus());
	}

	private static long _classNameId;

}