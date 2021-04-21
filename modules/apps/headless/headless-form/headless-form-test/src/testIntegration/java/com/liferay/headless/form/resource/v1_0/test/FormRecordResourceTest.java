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
import com.liferay.headless.form.client.dto.v1_0.FormFieldValue;
import com.liferay.headless.form.client.dto.v1_0.FormRecord;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class FormRecordResourceTest extends BaseFormRecordResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_ddmFormInstance = DDMFormInstanceTestUtil.addDDMFormInstance(
			_groupLocalService.getGroup(testGroup.getGroupId()),
			TestPropsValues.getUserId());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"draft"};
	}

	@Override
	protected FormRecord randomFormRecord() {
		return new FormRecord() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				draft = true;
				formFieldValues = new FormFieldValue[] {
					new FormFieldValue() {
						{
							name = "text";
							value = RandomTestUtil.randomString();
						}
					}
				};
				formId = _ddmFormInstance.getFormInstanceId();
			}
		};
	}

	@Override
	protected FormRecord testGetFormFormRecordByLatestDraft_addFormRecord()
		throws Exception {

		FormRecord formRecord = _addFormRecord(randomFormRecord());

		formRecord.setFormId(_ddmFormInstance.getFormInstanceId());

		return formRecord;
	}

	@Override
	protected Long testGetFormFormRecordsPage_getFormId() {
		return _ddmFormInstance.getFormInstanceId();
	}

	@Override
	protected FormRecord testGetFormRecord_addFormRecord() throws Exception {
		return _addFormRecord(randomFormRecord());
	}

	@Override
	protected FormRecord testGraphQLFormRecord_addFormRecord()
		throws Exception {

		FormRecord formRecord = _addFormRecord(randomFormRecord());

		formRecord.setFormId(_ddmFormInstance.getFormInstanceId());

		return formRecord;
	}

	@Override
	protected FormRecord testPutFormRecord_addFormRecord() throws Exception {
		return _addFormRecord(randomFormRecord());
	}

	private FormRecord _addFormRecord(FormRecord formRecord) throws Exception {
		return formRecordResource.postFormFormRecord(
			_ddmFormInstance.getFormInstanceId(), formRecord);
	}

	private DDMFormInstance _ddmFormInstance;

	@Inject(type = GroupLocalService.class)
	private GroupLocalService _groupLocalService;

}