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

package com.liferay.dynamic.data.mapping.internal.model.listener;

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceReportException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceReportLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Marcos Martins
 */
public class DDMFormInstanceRecordVersionModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpDDMFormInstanceRecordVersionModelListener();

		_ddmFormInstanceRecordVersionModelListener.
			ddmFormInstanceReportLocalService =
				_ddmFormInstanceReportLocalService;
	}

	@Test
	public void testOnAfterUpdate() throws PortalException {
		Mockito.when(
			_ddmFormInstanceReportLocalService.
				getFormInstanceReportByFormInstanceId(Mockito.anyLong())
		).thenThrow(
			new NoSuchFormInstanceReportException()
		);

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			Mockito.mock(DDMFormInstanceRecordVersion.class);

		Mockito.when(
			ddmFormInstanceRecordVersion.getFormInstanceId()
		).thenReturn(
			0L
		);

		Mockito.when(
			ddmFormInstanceRecordVersion.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_APPROVED
		);

		_ddmFormInstanceRecordVersionModelListener.onAfterUpdate(
			null, ddmFormInstanceRecordVersion);
	}

	@Test
	public void testOnBeforeUpdate() throws PortalException {
		Mockito.when(
			_ddmFormInstanceReportLocalService.
				getFormInstanceReportByFormInstanceId(Mockito.anyLong())
		).thenThrow(
			new NoSuchFormInstanceReportException()
		);

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			Mockito.mock(DDMFormInstanceRecordVersion.class);

		Mockito.when(
			ddmFormInstanceRecordVersion.getFormInstanceId()
		).thenReturn(
			0L
		);

		Mockito.when(
			ddmFormInstanceRecordVersion.getStatus()
		).thenReturn(
			WorkflowConstants.STATUS_APPROVED
		);

		_ddmFormInstanceRecordVersionModelListener.onBeforeUpdate(
			null, ddmFormInstanceRecordVersion);
	}

	private void _setUpDDMFormInstanceRecordVersionModelListener() {
		_ddmFormInstanceRecordVersionModelListener =
			new DDMFormInstanceRecordVersionModelListener();
	}

	private DDMFormInstanceRecordVersionModelListener
		_ddmFormInstanceRecordVersionModelListener;
	private final DDMFormInstanceReportLocalService
		_ddmFormInstanceReportLocalService = Mockito.mock(
			DDMFormInstanceReportLocalService.class);

}