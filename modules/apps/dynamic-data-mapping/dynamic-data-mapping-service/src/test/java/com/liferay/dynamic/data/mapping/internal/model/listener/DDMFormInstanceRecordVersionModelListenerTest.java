/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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