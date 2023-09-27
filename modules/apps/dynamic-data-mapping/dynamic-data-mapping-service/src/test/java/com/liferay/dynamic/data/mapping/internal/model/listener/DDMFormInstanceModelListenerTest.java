/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.model.listener;

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceException;
import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceReportException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceReportLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Marcos Martins
 */
public class DDMFormInstanceModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_setUpDDMFormInstanceModelListener();

		_ddmFormInstanceModelListener.ddmFormInstanceReportLocalService =
			_ddmFormInstanceReportLocalService;
	}

	@Test
	public void testOnAfterCreate() throws PortalException {
		Mockito.when(
			_ddmFormInstanceReportLocalService.addFormInstanceReport(
				Mockito.anyLong())
		).thenThrow(
			new NoSuchFormInstanceException()
		);

		DDMFormInstance ddmFormInstance = Mockito.mock(DDMFormInstance.class);

		Mockito.when(
			ddmFormInstance.getFormInstanceId()
		).thenReturn(
			0L
		);

		_ddmFormInstanceModelListener.onAfterCreate(ddmFormInstance);
	}

	@Test
	public void testOnBeforeRemove() throws PortalException {
		Mockito.when(
			_ddmFormInstanceReportLocalService.
				getFormInstanceReportByFormInstanceId(Mockito.anyLong())
		).thenThrow(
			new NoSuchFormInstanceReportException()
		);

		DDMFormInstance ddmFormInstance = Mockito.mock(DDMFormInstance.class);

		Mockito.when(
			ddmFormInstance.getFormInstanceId()
		).thenReturn(
			0L
		);

		_ddmFormInstanceModelListener.onBeforeRemove(ddmFormInstance);
	}

	private void _setUpDDMFormInstanceModelListener() {
		_ddmFormInstanceModelListener = new DDMFormInstanceModelListener();
	}

	private DDMFormInstanceModelListener _ddmFormInstanceModelListener;
	private final DDMFormInstanceReportLocalService
		_ddmFormInstanceReportLocalService = Mockito.mock(
			DDMFormInstanceReportLocalService.class);

}